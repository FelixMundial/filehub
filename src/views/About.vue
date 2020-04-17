<template>
  <el-container>
    <el-header>
      <main-header />
    </el-header>
    <el-main>
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
export default {
  components: { MainFooter, MainHeader }
};
</script>
