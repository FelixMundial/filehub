<template>
  <el-container>
    <el-header>
      <main-header />
    </el-header>
    <el-main class="global-container">
      <div class="about">
        <h1>This is an about page</h1>
        <FileUploading />
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
import FileUploading from "../components/file/FileUploading";

export default {
  components: { FileUploading, MainFooter, MainHeader }
};
</script>
