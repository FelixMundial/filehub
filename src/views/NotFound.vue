<template>
  <el-container>
    <el-header>
      <main-header />
    </el-header>
    <el-main class="global-container">
      <h1 class="title">
        404
      </h1>
      <blockquote class="msg">
        <img src="../assets/chouju.gif" width="80" height="80" alt="" />
        {{ current404Msg }}
      </blockquote>
      <div class="home-link-container">
        <router-link to="/">
          <div class="home-link">
            👉 Country road, take me home{{ selectedSuspensionPoint }} 👈
          </div>
        </router-link>
      </div>
    </el-main>
    <el-footer>
      <main-footer />
    </el-footer>
  </el-container>
</template>

<script>
import MainFooter from "../components/MainFooter";
import MainHeader from "../components/MainHeader";

const _404MsgPool = [
  `此地空无一物～`,
  `我是谁？我从哪里来？要到哪里去？`,
  `走丢啦～`,
  `咦，页面不见了？`
];

export default {
  name: "NotFound",
  components: { MainHeader, MainFooter },
  data() {
    return {
      current404Msg: "",
      suspensionPoints: [".", "..", "..."],
      selectedSuspensionPoint: ".",
      currentTimeInSeconds: 0
    };
  },
  methods: {
    get404Msg() {
      this.current404Msg =
        _404MsgPool[Math.floor(Math.random() * _404MsgPool.length)];
    },
    getSuspensionPointsPeriodically() {
      this.selectedSuspensionPoint = this.suspensionPoints[
        this.currentTimeInSeconds++ % 3
      ];
    }
  },
  mounted() {
    this.get404Msg();
    setInterval(this.getSuspensionPointsPeriodically, 800);
  }
};
</script>

<style scoped>
.content {
  text-align: center;
  margin: 10%;
}
.title {
  color: red;
  font-size: xx-large;
  font-family: American Typewriter, sans-serif;
}
.msg {
  font-size: 120%;
  font-family: "Weibei SC", serif;
}
.home-link-container {
  margin: 8rem;
}
.home-link {
  font-size: 110%;
  margin: 20px;
}
</style>
