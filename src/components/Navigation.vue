<template>
  <div>
    <el-menu
            :default-active="navBarIndex"
            style="padding-left: 20px;width: 100%;margin-left: 0px;"
            class="el-menu"
            mode="horizontal"
            @select="handleSelect"
            background-color="#000000"
            text-color="#fff"
            active-text-color="#ffd04b">
      <el-menu-item index="1">FileHub</el-menu-item>
      <el-menu-item index="2">探索</el-menu-item>
      <el-menu-item index="3">关于</el-menu-item>
      <el-menu-item class="search-input-style">
         <div >
           <el-input placeholder="请输入内容" v-model="search" class="search-input-color">
           <el-button slot="append" icon="el-icon-search" type="info" @click="searchButton"></el-button>
         </el-input>
        </div>
     </el-menu-item>
      <el-menu-item style="float: right;margin-right: 5%;">
        <el-submenu index="4" >
       <template slot="title" >
        silas
      </template>
          <el-menu-item index="1000">创建仓库</el-menu-item>
          <el-menu-item index="1003">我的账户</el-menu-item>
          <el-menu-item index="1001">切换账户</el-menu-item>
          <el-menu-item index="1002">退出</el-menu-item>
        </el-submenu>
      </el-menu-item>
    </el-menu>
  </div>
</template>


<script>
  export default {
    name: "Navigation",
    data() {
      return {
        navBarIndex: '1',
        search : "",
      }
    },
    methods: {
      handleSelect(key, keyPath) {
        console.log(key , "KeyPath = " + keyPath);
        if(key == 1){
          this.$router.push("/home");
        }
        else if(key == 3){
          this.$router.push("/about");
        }
        //1001 切换账户
        else if(key == 1001){
          sessionStorage.removeItem('token');
          this.$router.push('/');
        }
        else if(key == 1002){
          sessionStorage.removeItem('token');
          this.$router.push("/");
        }
        else if(key==1003){
          console.log("我的仓库");
        }
      },
      searchButton(){
        console.log(this.input);
        // this.$router.push("/library/"+this.search);
      },

    },
  }
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
  .search-input-style{
    width: 20%;
    height: 80%;
    margin-left: 15%;
    margin-top: 0.8em;

  }
  .search-input-color .el-input__inner{
    background-color: black;
  }
  .search-button .el-button{
    background-color: #000000;
  }

</style>
