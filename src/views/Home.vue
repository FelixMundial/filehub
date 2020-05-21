<template>
  <el-container>
<!--    导航栏-->
    <navigation></navigation>
<!--    -->
    <el-main class="global-container">
      <div class="library-list-container">
        <h1 class="library-list-title">
          <i class="el-icon-trophy" style="margin: 10px"></i>FileHub热门
        </h1>
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
                <el-button @click="starClick(index)" icon="el-icon-star-off"></el-button>
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
import MainHeader from "../components/MainHeader";
import MainFooter from "../components/MainFooter";
import Navigation from "../components/Navigation";

export default {
  name: "Home",
  components: { Navigation,MainFooter, MainHeader },
  data() {
    return {
      navList : [{

      }],
      isPlaceholderTextEnabled: true,
      library: {
        /*
        collaborators: [],
        files: [],
        followersCount: 899,
        libraryCreationTime: "2016-10-16T00:49:13",
        libraryCreationUid: 3,
        libraryDesc: "Leetcode in Animations",
        libraryId: 2,
        libraryLastUpdateTime: "2020-02-15T13:49:19",
        libraryLastUpdateUid: 3,
        libraryName: "leetcode-animations",
        libraryUrl: "/123456/leetcode_animations",
        ownerUid: 3,
        privacyType: false
      */
      },
      libraries: [],
      responseResult: {}
    }
  },
  created(){
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
  methods: {
    starClick(index){
      console.log("button clicked" );
      /*Star 实现*/
      // this.libraries[index].followersCount++;
      // this.axios.post("/library/explore/top",{});

    },
  }
};
</script>


<style>
  html,body,#app,.el-container{
    /*设置内部填充为0，几个布局元素之间没有间距*/
    padding: 0px;
    /*外部间距也是如此设置*/
    margin: 0px;
    /*统一设置高度为100%*/
    height: 100%;
  }
</style>

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
