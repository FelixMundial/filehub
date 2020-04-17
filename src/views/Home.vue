<template>
  <el-container>
    <el-header>
      <main-header />
    </el-header>
    <el-main>
      <el-card v-for="(library, index) in libraries" :key="index">
        <el-tag>{{ library }}</el-tag>
      </el-card>
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
    let loading;
    this.axios.interceptors.request.use(
      request => {
        loading = this.$loading({
          lock: true,
          text: "努力加载中...",
          background: "rgba(255, 255, 255, 0.5)"
        });
        return request;
      },
      err => {
        return Promise.reject(err);
      }
    );
    this.axios.interceptors.response.use(
      response => {
        setTimeout(() => {
          loading.close();
        }, 500);
        return response;
      },
      err => {
        loading.close();
        return Promise.reject(err);
      }
    );
    this.axios
      .get("/libraries/explore/top")
      .then(validResponse => {
        // console.log(validResponse);
        if (validResponse.status === 200) {
          // this.libraries = validResponse.data;
          validResponse.data.forEach(item => {
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
      })
      // eslint-disable-next-line no-unused-vars
      .catch(invalidResponse => {
        // console.log(invalidResponse);
      });
  },
  methods: {}
};
</script>

<style scoped></style>
