<template>
  <el-container class="welcome-container">
    <el-main class="global-container">
      <el-form
              :model="loginInfo"
              :rules="loginRule"
              ref="loginForm"
              label-width="100px"
              class="registration-form"
      >
        <el-form-item label="登录账号" prop="loginName">
          <el-input
                  v-model="loginInfo.loginName"
                  clearable
                  prefix-icon="el-icon-user"
          ></el-input>
        </el-form-item>
        <el-form-item label="登录密码" prop="loginPassword">
          <el-input
                  v-model="loginInfo.loginPassword"
                  show-password
                  clearable
                  prefix-icon="el-icon-warning-outline"
                  @keyup.enter.native="submitForm('loginForm')"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
                  class="registration-submit-btn"
                  @click="submitForm('loginForm')"
                  type="primary"
                  icon="el-icon-s-promotion"
          >立即登录</el-button
          >
          <el-button
                  class="registration-reset-btn"
                  @click="resetForm('loginForm')"
          >重置信息</el-button
          >
          还没有账号？<el-button
                @click="directToRegistration()"
                type="success"
                plain
        >立即注册</el-button>
        </el-form-item>
      </el-form>
    </el-main>
  </el-container>
</template>

<script>

  export default {
    name: "login",
     // components: { MainFooter, MainHeader },
    components : {},
    data() {
      return {
        loginInfo: { loginType: "", loginName: "", loginPassword: "" },
        loginStateInfo: { loginType: "", loginName: "" },
        responseResult: { },
        loginRule: {
          loginName: [
            { required: true, message: "请输入用户名", trigger: "blur" }
          ],
          loginPassword: [
            { required: true, message: "请输入密码", trigger: "blur" }
          ]
        }
      };
    },
    methods: {
      login() {
        this.axios
            .post(
                "/uaa/login",
                {
                  userLoginName: this.loginInfo.loginName,
                  userLoginPassword: this.loginInfo.loginPassword
                },
                {
                  headers: {
                    loadingText: "登录中，请稍候...",
                    loadingBackgroundColor: "rgba(150, 150, 150, 0.5)"
                  }
                }
            )
            .then(validResponse => {
              this.responseResult = validResponse.data;
              if (this.responseResult.statusCode === 200) {
                this.$message.success(this.responseResult.message);
                let redirect = this.$route.query.redirect;
                if (redirect) {
                  this.$router.push(redirect);
                } else {
                  this.$router.replace({ path: "/home" });
                }
              }
            });
      },
      submitForm(formName) {
        this.$refs[formName].validate(valid => {
          if (valid) {
            // this.$store.dispatch("updateUserInfo", this.loginStateField);
            this.login();
          } else {
            this.$message.error("用户名或密码不合要求！");
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      directToRegistration() {
        this.$router.push("/register");
      }
    },
    /*
    提取一部分非敏感表单数据，以便放入store
    */
    watch: {
      loginField: {
        handler(newVal, oldVal) {
          this.loginStateInfo.loginType = oldVal.loginType;
          this.loginStateInfo.loginName = oldVal.loginName;
        },
        deep: true
      }
    }
  };
</script>

<style lang="stylus" scoped>
  .welcome-container
    width 95%
    margin 0 auto
  .registration-form
    width 40%
    margin 2rem auto
    text-align center
    padding 4rem
    border 1px #E4E7ED
    border-radius 10px
    box-shadow 0 2px 12px 0 rgba(0, 0, 0, 0.1)
  .registration-submit-btn, .registration-reset-btn
    float left
    text-align center
</style>
