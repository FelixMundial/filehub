<template>
  <el-container>
    <navigation></navigation>
    <el-main class="global-container">
      <div class="library-list-container">
        <div class="library-item-container">
          <h4 v-if="isPlaceholderTextEnabled">
            此地空无一物，请检查您的网络...
          </h4>
          <el-card
                  shadow="hover"
                  class="library-item"
                  v-for="(library, index) in libraries"
                  :key="index"
          >
            <!--            <a>{{libraries[index]}}</a>-->
            <h4 class="library-item-title">
              <router-link
                      :to="{
                  path: '/library',
                  query: { libraryId: library.libraryId }
                }"
              >
                <span
                >{{ library.ownerUser}}/{{
                    library.libraryName
                  }}</span
                >
              </router-link>
              <span>
                <el-button icon="el-icon-star-off"></el-button>
                {{ library.followersCount }}</span
              >
            </h4>
            <span class="library-item-desc">{{ library.libraryDesc }}</span>
          </el-card>
        </div>
      </div>
    </el-main>
    <el-footer>
      <main-footer />
    </el-footer>
  </el-container>
</template>

<script>

  import Navigation from "../components/Navigation";
  import MainFooter from "../components/MainFooter";

  export default {
    name: "Search",
    components: {MainFooter, Navigation},
    data(){
      return {
        isEmptyKey : false,
        isPlaceholderTextEnabled: true,
        data : [],
        library : {

        },
        libraries: [],
        responseResult: {}
      }
    },
    /*
    created:在模板渲染成html前调用，即通常初始化某些属性值，然后再渲染成视图。
    mounted:在模板渲染成html后调用，通常是初始化页面完成后，再对html的dom节点进行一些需要的操作
     */
    created() {
      this.axios
          .get("/library/user/" + this.$attrs.query, {
            headers: {
              loadingText: "努力加载中..."
            }
          })
          .then(validResponse => {
            this.responseResult = validResponse.data;
            if (this.responseResult.statusCode === 200) {
              this.responseResult.data.forEach(item => {
                this.library.libraryId = item.libraryId;
                this.library.libraryName = item.libraryName;
                this.library.libraryDesc = item.libraryDesc;
                this.library.libraryUrl = item.libraryUrl;
                this.library.followersCount = item.followersCount;
                // 通过uid查询用户名（一次联查返回&发送uid再次查询）
                this.library.ownerUid = item.ownerUid;
                this.library.ownerUser = item.collaborators[0];
                this.library.libraryCreationTime = item.libraryCreationTime;
                this.library.libraryCreationUser = item.libraryCreationUid;
                this.library.isPrivate = item.privacyType;
                this.libraries.push(this.library);
                this.library = {};
              });
              this.isPlaceholderTextEnabled = false;
            }
          });
    },
  }
</script>

<style scoped>
  .library-list-title {
    margin-bottom: 2rem;
  }
  .library-item-container {
    width: 90%;
    margin: 0 auto;
    display: flex;
    justify-content: space-evenly;
    flex-direction: row;
    flex-wrap: wrap;
  }
  .library-item {
    width: 40%;
    height: 200px;
    padding: 1rem;
    margin-bottom: 2rem;
  }
  .library-item-title {
    display: flex;
    justify-content: space-evenly;
    flex-direction: row;
    flex-wrap: wrap;
  }
  .library-item-desc {
    color: darkgrey;
    float: left;
    text-align: left;
    overflow-y: scroll;
  }
</style>