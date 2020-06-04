<template>
  <el-container>
    <el-header>
      <main-header />
    </el-header>
    <el-main class="global-container">
      <div class="library-list-container">
        <h1 class="library-list-title">
          <i class="el-icon-trophy" style="margin: 10px"></i>Filehub热门
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
            <h4 class="library-item-title">
              <router-link
                :to="{
                  path: '/library',
                  query: { libraryId: library.libraryId }
                }"
              >
                <span class="library-item-title-name"
                  >{{ library.ownerUser.userNickname }}/{{
                    library.libraryName
                  }}</span
                >
              </router-link>
              <span class="library-item-title-star"
                ><i class="el-icon-star-off"></i>
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

export default {
  name: "Home",
  components: { MainFooter, MainHeader },
  data() {
    return {
      isPlaceholderTextEnabled: true,
      library: {},
      libraries: [],
      responseResult: {}
    };
  },
  created() {
    this.axios
      .get("/library/top", {
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
  methods: {}
};
</script>

<style scoped>
.library-list-container {
}
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
  text-align: left;
}
.library-item-title {
  display: flex;
  flex-wrap: nowrap;
  justify-content: space-between;
  flex-direction: row;
}
.library-item-title-name {
  padding-right: 1rem;
}
.library-item-title-star {
  display: flex;
  flex-wrap: nowrap;
}
.library-item-title-star i {
  padding-top: 2px;
  margin-right: 3px;
}
.library-item-desc {
  color: darkgrey;
  overflow-y: scroll;
  font-family: "American Typewriter", serif;
}
</style>
