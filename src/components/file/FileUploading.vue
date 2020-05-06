<template>
  <div>
    <el-upload
      class="section-upload"
      action="http://filehub-oss.oss-cn-beijing.aliyuncs.com"
      :data="ossPolicyResponseResult"
      list-type="picture-card"
      multiple
      :limit="5"
      :show-file-list="true"
      :auto-upload="false"
      :file-list="fileList"
      :before-upload="beforeUpload"
      :on-preview="handlePreview"
      :on-success="handleUploadSuccess"
      :on-remove="handleRemove"
    >
      <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
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
      <img width="100%" :src="fileList[0].url" alt="" />
    </el-dialog>
  </div>
</template>

<script>
import { get_oss_policy } from "../../util/get_oss_policy";

export default {
  name: "FileUploading",
  computed: {
    fileList() {
      return [
        {
          name: "oss-test",
          url: this.imageUrl
        }
      ];
    }
  },
  data() {
    return {
      ossPolicyResponseResult: {
        policy: "",
        signature: "",
        accessId: "",
        key: "",
        filePrefix: "",
        host: ""
        // callback: "",
      },
      isPreviewDialogVisible: false
    };
  },
  methods: {
    emitInput(val) {
      this.$emit("input", val);
    },
    beforeUpload(file) {
      let _self = this;
      return new Promise((resolve, reject) => {
        get_oss_policy()
          .then(response => {
            _self.ossPolicyResponseResult.policy = response.data.policy;
            _self.ossPolicyResponseResult.signature = response.data.signature;
            _self.ossPolicyResponseResult.ossAccessKeyId =
              response.data.accessId;
            _self.ossPolicyResponseResult.key =
              response.data.filePrefix + "_${filename}";
            _self.ossPolicyResponseResult.filePrefix = response.data.filePrefix;
            _self.ossPolicyResponseResult.host = response.data.host;
            resolve(true);
          })
          .catch(err => {
            console.log(err);
            reject(false);
          });
      });
    },
    handlePreview(file) {
      this.isPreviewDialogVisible = true;
    },
    handleUploadSuccess(res, file) {
      console.log("上传成功...");
      console.log(file);
      // this.displayFileList = true;
      this.fileList.pop();
      this.fileList.push({
        name: file.name,
        url:
          this.ossPolicyResponseResult.host +
          "/" +
          this.ossPolicyResponseResult.key.replace("${filename}", file.name)
      });
      this.emitInput(this.fileList[0].url);
    },
    handleRemove(file, fileList) {
      this.emitInput("");
    },
    submitUpload() {
      this.$refs.upload.submit();
    }
  }
};
</script>

<style scoped></style>
