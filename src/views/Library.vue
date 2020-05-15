<template>
  <el-container>
    <navigation></navigation>
    <el-main class="global-container">
      <FileUploading />
      <div class="file-list-container">
        <h1 class="file-list-title">
          <i class="el-icon-folder" style="margin: 10px"></i
          >{{ currentLibrary.libraryName }}
        </h1>
        <el-collapse
          class="file-item-container"
          v-model="activeCollapseItem"
          @change="handleChange"
        >
          <el-collapse-item
            class="file-item"
            v-for="(file, index) in files"
            :key="index"
            :name="index"
          >
            <!-- 除了可以通过 title 属性以外，还可以通过具名 slot 来实现自定义面板的标题内容，以实现增加图标等效果 -->
            <template slot="title">
              <span style="overflow: hidden">
                <i class="el-icon-document" style="margin: 10px"></i
                >{{ file.fileDisplayName }}</span
              >
              <span style="position: absolute; right: 20%">
                <i class="el-icon-time" style="margin: 10px"></i
                >{{ file.fileLastUpdateTime }}</span
              >
            </template>
            <div class="file-item-desc">
              <span>{{ file.fileLastUpdateUser }}</span>
              <span>{{ computedFileSize(file.fileSize)}}</span>
              <a :href="file.fileUrl" :download="file.fileDisplayName">
                <el-button
                  icon="el-icon-download"
                  type="primary"
                  plain
                  circle
                ></el-button>
              </a>
              <a :href="file.fileUrl" :download="file.fileDisplayName">
                <el-button
                 icon="el-icon-delete"
                 type="danger"
                 plain
                 circle>
                </el-button>
              </a>
            </div>
          </el-collapse-item>
        </el-collapse>
      </div>
    </el-main>
    <el-footer>
      <main-footer />
    </el-footer>
  </el-container>
</template>

<script>
import FileUploading from "../components/file/FileUploading";
import MainFooter from "../components/MainFooter";
import MainHeader from "../components/MainHeader";
import Navigation from "../components/Navigation";
export default {
  name: "Library",
  components: { Navigation,FileUploading, MainFooter, MainHeader },
  computed: {
    computedFileSize() {
      return size => (size === null ? "未知大小" : Math.floor((size/1024)*100)/100 + "MB");
    }
  },
  data() {
    return {
      currentLibrary: {},
      file: {
        /*
        fileDisplayName: "oss-test.json",
        fileUrl:
          "https://filehub-oss.oss-cn-beijing.aliyuncs.com/archives/oss-test.json",
        fileType: "application.json",
        fileSize: 24,
        fileLastUpdateUser: {
          nickname: "Admin"
        },
        fileLastUpdateTime: "2020-04-14 13:38:34"
      */
      },
      files: [],
      responseResult: {},
      activeCollapseItem: ["1"]
    };
  },
  mounted() {
    this.axios
      .get("/library/" + this.$route.query.libraryId + "/files", {
        headers: {
          loadingText: "努力加载中..."
        }
      })
      .then(validResponse => {
        this.responseResult = validResponse.data;
        if (this.responseResult.statusCode === 200) {
          this.responseResult.data.forEach(item => {
            this.currentLibrary.libraryName =
              item.parentLibraries[0].libraryName;
            this.file.fileDisplayName = item.fileDisplayName;
            this.file.fileUrl = item.fileUrl;
            this.file.fileType = item.fileType;
            this.file.fileSize = item.fileSize;
            this.file.fileLastUpdateUser = item.fileLastUpdateUid;
            this.file.fileLastUpdateTime = item.fileLastUpdateTime.replace(
              "T",
              " "
            );
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
    handleChange(val) {
      // console.log(val);
    }
  }
};
</script>

<style scoped>
.file-list-container {
}
.file-list-title {
  margin-bottom: 2rem;
}
.file-item-container {
  width: 75%;
  margin: 0 auto;
}
.file-item {
}
.file-item-title {
}
.file-item-desc {
  margin: 0 5rem;
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-direction: row;
  flex-wrap: wrap;
}
</style>
