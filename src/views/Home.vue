<template>
  <el-container>
    <el-header>
      <main-header />
    </el-header>
    <el-main class="global-container">
      <div v-for="(library, index) in libraries" :key="index">
        <el-tag>{{ library }}</el-tag>
      </div>
    </el-main>
    <el-footer>
      <main-footer />
    </el-footer>
  </el-container>
</template>

<script>
import MainHeader from "../components/MainHeader";
import MainFooter from "../components/MainFooter";

export default {
  name: "Home",
  components: { MainFooter, MainHeader },
  data() {
    return {
      library: {},
      libraries: [],
      responseResult: {}
    };
  },
  created() {
    this.axios
      .get("/library/explore/top", {
        headers: {
          loadingText: "努力加载中..."
        }
      })
      .then(validResponse => {
        this.responseResult = validResponse.data;
        if (this.responseResult.statusCode === 200) {
          this.responseResult.data.forEach(item => {
            this.library.libraryName = item.libraryName;
            this.library.libraryDesc = item.libraryDesc;
            this.library.ownerName = item.ownerUid;
            this.library.libraryUrl = item.libraryUrl;
            this.library.followersCount = item.followersCount;
            this.library.libraryCreationTime = item.libraryCreationTime;
            this.library.libraryCreationUser = item.libraryCreationUid;
            this.library.isPrivate = item.privacyType;
            this.libraries.push(this.library);
            this.library = {};
          });
        }
      });
  },
  methods: {}
};
</script>

<style scoped></style>
