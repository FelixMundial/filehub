<template>
  <div>
    <!--  暂时只进行单次单文件上传  -->
    <el-card class="file-uploading-container">
      <el-upload
        ref="upload"
        class="section-upload"
        action="http://filehub-oss.oss-cn-beijing.aliyuncs.com"
        :data="ossPolicyResponseResult"
        list-type="text"
        :show-file-list="true"
        :auto-upload="false"
        :file-list="fileList"
        :before-upload="beforeUpload"
        :on-preview="handlePreview"
        :on-success="handleUploadSuccess"
        :on-error="handleUploadFailure"
        :on-remove="handleRemove"
      >
        <el-button slot="trigger" size="small" type="primary"
          >选取文件</el-button
        >
        <el-button
          style="margin-left: 10px;"
          size="small"
          type="success"
          @click="submitUpload"
          >上传到服务器</el-button
        >
        <div slot="tip" class="el-upload__tip">
          只能上传XXX文件，且不超过1MB
        </div>
      </el-upload>
      <el-dialog :visible.sync="isPreviewDialogVisible">
        <!-- CORB Blocked -->
        <!-- <img width="100%" :src="fileUrl" alt="" /> -->
        <a :href="fileUrl" :download="fileName">
          <el-tag><i class="el-icon-document"></i> {{ fileName }}</el-tag>
        </a>
      </el-dialog>
    </el-card>
    <div v-for="(file, index) in files" :key="index">
      <el-tag>{{ file }}</el-tag>
    </div>
  </div>
</template>

<script>
import { getPolicy } from "../../util/policy";

export default {
  name: "FileUploading",
  computed: {
    fileList() {
      return [
        // {
        //   name: "oss-test.json",
        //   url:
        //     "https://filehub-oss.oss-cn-beijing.aliyuncs.com/archives/oss-test.json"
        // }
      ];
    }
  },
  data() {
    return {
      file: {},
      files: [],
      responseResult: {},
      ossPolicyResponseResult: {
        policy: "",
        signature: "",
        ossAccessKeyId: "",
        key: "",
        filePrefix: "",
        host: "",
        expiration: ""
        // callback: ""
      },
      isPreviewDialogVisible: false,
      fileUrl: "",
      fileName: ""
    };
  },
  mounted() {
    this.axios
      .get("/library/10/files", {
        headers: {
          loadingText: "努力加载中..."
        }
      })
      .then(validResponse => {
        this.responseResult = validResponse.data;
        if (this.responseResult.statusCode === 200) {
          this.responseResult.data.forEach(item => {
            this.file.fileDisplayName = item.fileDisplayName;
            this.file.fileUrl = item.fileUrl;
            this.file.fileType = item.fileType;
            this.file.fileSize = item.fileSize;
            this.file.fileLastUpdateTime = item.fileLastUpdateTime;
            this.files.push(this.file);
            this.file = {};
          });
        }
      });
    /* 不再直接通过oss api获取文件列表 */
    // this.axios
    //   .get("/file/filehub-oss", {
    //     headers: {
    //       loadingText: "努力加载中..."
    //     }
    //   })
    //   .then(validResponse => {
    //     this.responseResult = validResponse.data;
    //     if (this.responseResult.statusCode === 200) {
    //       this.responseResult.data.forEach(item => {
    //         this.file.key = item.key;
    //         this.file.lastModified = item.lastModified;
    //         this.file.size = item.size;
    //         this.file.etag = item.etag;
    //         this.files.push(this.file);
    //         this.file = {};
    //       });
    //     }
    //   });
  },
  methods: {
    emitInput(val) {
      this.$emit("input", val);
    },
    beforeUpload(file) {
      let _self = this;
      return new Promise((resolve, reject) => {
        getPolicy()
          .then(response => {
            // console.log(response);
            if (response.data.statusCode === 200) {
              response = response.data;
              _self.ossPolicyResponseResult.policy = response.data.policy;
              _self.ossPolicyResponseResult.signature = response.data.signature;
              _self.ossPolicyResponseResult.ossAccessKeyId =
                response.data.accessId;
              _self.ossPolicyResponseResult.key =
                response.data.filePrefix + "_${filename}";
              _self.ossPolicyResponseResult.filePrefix =
                response.data.filePrefix;
              _self.ossPolicyResponseResult.host = response.data.host;
              _self.ossPolicyResponseResult.expiration =
                response.data.expiration;
              // _self.ossPolicyResponseResult.callback = response.data.callback;
              resolve(true);
            } else {
              reject(false);
            }
          })
          .catch(err => {
            console.log(err);
            reject(false);
          });
      });
    },
    handlePreview(file) {
      console.log(file);
      this.isPreviewDialogVisible = true;
      this.fileUrl = file.url;
      this.fileName = file.name;
    },
    handleUploadSuccess(res, file, fileList) {
      this.$message.success("文件上传成功！");
      // 无法获取response
      console.log(res);
      this.fileList.pop();
      let fileUrl =
        this.ossPolicyResponseResult.host +
        "/" +
        this.ossPolicyResponseResult.key.replace("${filename}", file.name);
      this.fileList.push({
        name: file.name,
        url: fileUrl
      });
      // this.emitInput(this.fileList[0].url);

      this.axios
        .post(
          "/library/file/save",
          {
            fileName: file.name,
            fileUrl: fileUrl,
            fileSize: file.size,
            fileType: file.raw.type,
            fileStatus: file.status,
            fileLastModifiedDate: file.raw.lastModifiedDate
          },
          {
            headers: {
              noLoading: "true"
            }
          }
        )
        .then(validResponse => {
          this.responseResult = validResponse.data;
          if (this.responseResult.statusCode === 200) {
            this.$message.success(this.responseResult.message);
          }
        })
        .catch(invalidResponse => {
          console.log(
            "InvalidResponse in axios catch in handleUploadSuccess()"
          );
          console.log(invalidResponse);
        });
    },
    handleUploadFailure(res, file, fileList) {
      console.log(res);
      this.$message.error("文件上传失败，请稍后重试...");
    },
    handleRemove(file, fileList) {
      // this.emitInput("");
    },
    submitUpload() {
      this.$refs.upload.submit();
    }
  }
};
</script>

<style scoped>
.file-uploading-container {
  margin: 2rem auto;
  width: 60%;
  /* height: 200px; */
}
</style>
