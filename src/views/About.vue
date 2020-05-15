<template>
  <el-container>
    <navigation></navigation>
    <el-main class="global-container">
      <div class="about">
        <h1>This is an about page</h1>
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
  name: "About",
  components: {Navigation, MainFooter, MainHeader }
};
</script>
