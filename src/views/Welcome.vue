<template>
  <el-container class="welcome-container">
    <el-header>
      <main-header />
    </el-header>
    <el-main>
      <el-form
        :model="loginInfo"
        :rules="loginRule"
        ref="loginForm"
        label-width="100px"
        class="registration-form"
      >
        <el-form-item label="登录方式" prop="loginType">
          <el-select v-model="loginInfo.loginType" placeholder="登录方式">
            <el-option label="手机号码登录" value="mobile"></el-option>
            <el-option label="邮箱登录" value="email"></el-option>
          </el-select>
        </el-form-item>
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
        </el-form-item>
        <el-form-item>
          还没有账号？<el-button
            @click="directToRegistration()"
            type="success"
            plain
            >立即注册</el-button
          >
        </el-form-item>
      </el-form>
    </el-main>
    <el-footer>
      <main-footer />
    </el-footer>
  </el-container>
</template>

<script>
/* eslint-disable no-unused-vars */

// @ is an alias to /src
import MainHeader from "../components/MainHeader";
import MainFooter from "../components/MainFooter";
import loading from "../element/loading";

export default {
  name: "Welcome",
  components: { MainFooter, MainHeader },
  data() {
    return {
      loginInfo: { loginType: "", loginName: "", loginPassword: "" },
      loginStateInfo: { loginType: "", loginName: "" },
      responseResult: {},
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
        .post("/uaa/login", {
          userLoginName: this.loginInfo.loginName,
          userLoginPassword: this.loginInfo.loginPassword
        })
        .then(validResponse => {
          // console.log(validResponse);
          this.responseResult = validResponse.data;
          // console.log(this.responseResult);
          if (this.responseResult.statusCode === 200) {
            let token = this.responseResult.data.token;

            // console.log(token);
            if (token) {
              sessionStorage.setItem("token", token);
            } else {
              sessionStorage.removeItem("token");
            }
            this.$message.success(this.responseResult.message);
            let redirect = this.$route.query.redirect;
            // console.log(redirect);
            if (redirect) {
              // this.$router.replace({ path: "/home" });
              this.$router.push(redirect);
            } else {
              this.$router.replace({ path: "/home" });
            }
          } else {
            this.$message.error(this.responseResult.message);
          }
        })
        .catch(invalidResponse => {
          console.log(invalidResponse);
          this.$message.error("服务暂时不可用，请稍后再试...");
        });
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          // this.$store.dispatch("updateUserInfo", this.loginStateField);

          let loading;
          this.axios.interceptors.request.use(
            request => {
              loading = this.$loading({
                lock: true,
                text: "登录中...请稍候...",
                background: "rgba(0, 0, 0, 0.7)"
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
              }, 1000);
              return response;
            },
            err => {
              setTimeout(() => {
                loading.close();
              }, 600);
              // if (err && err.response) {
              //   console.log(err.response.status);
              // } else {
              //   err.message = "连接服务器失败!";
              // }
              // console.error(err.message);
              return Promise.reject(err);
            }
          );

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
  min-height 800px
.registration-form
  width 30%
  margin 2rem auto
  text-align center
  padding 3rem
  border 1px #E4E7ED
  border-radius 10px
  box-shadow 0 2px 12px 0 rgba(0, 0, 0, 0.1)
.registration-submit-btn, .registration-reset-btn
  float left
  text-align center
</style>
