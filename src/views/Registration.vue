<template>
  <el-container class="welcome-container">
    <el-header>
      <main-header />
    </el-header>
    <el-main>
      <el-form
        :model="loginInfo"
        :rules="registrationRule"
        ref="registrationForm"
        label-width="100px"
        class="registration-form"
      >
        <el-form-item label="注册方式" prop="loginType">
          <el-select v-model="loginInfo.loginType" placeholder="登录方式">
            <el-option label="手机号码注册" value="mobile"></el-option>
            <el-option label="邮箱注册" value="email"></el-option>
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
            @click="submitForm('registrationForm')"
            type="primary"
            icon="el-icon-s-promotion"
            >立即注册</el-button
          >
          <el-button
            class="registration-reset-btn"
            @click="resetForm('registrationForm')"
            >重置信息</el-button
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
import MainFooter from "../components/MainFooter";
import MainHeader from "../components/MainHeader";
export default {
  name: "Registration",
  components: { MainHeader, MainFooter },
  data() {
    return {
      loginInfo: { loginType: "", loginName: "", loginPassword: "" },
      responseResult: {},
      registrationRule: {
        loginName: [
          { required: true, message: "请输入用户名", trigger: "blur" }
        ],
        loginPassword: [
          { required: true, message: "请输入密码", trigger: "change" },
          {
            min: 3,
            max: 10,
            message: "密码长度在3到10个字符之间",
            trigger: "change"
          }
        ]
      }
    };
  },
  methods: {
    register() {
      this.axios
        .post("/register", {
          userLoginName: this.loginInfo.loginName,
          userLoginPassword: this.loginInfo.loginPassword
        })
        .then(validResponse => {
          this.responseResult = validResponse.data;
          if (this.responseResult.statusCode === 200) {
            sessionStorage.setItem("token", "token111");
            this.$message.success(this.responseResult.message);
            let redirect = this.$route.query.redirect;
            if (redirect) {
              this.$router.push(redirect);
            } else {
              this.$router.push({ name: "/" });
            }
          } else {
            this.$message.error(this.responseResult.message);
          }
        })
        .catch(invalidResponse => {
          this.responseResult = invalidResponse;
          this.$message.error("服务暂时不可用，请稍后再试...");
        });
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          let loading;
          this.axios.interceptors.request.use(
            request => {
              loading = this.$loading({
                lock: true,
                text: "用户注册中...请稍候...",
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
              return Promise.reject(err);
            }
          );

          this.register();
        } else {
          this.$message.error("用户名或密码不合要求！");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
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
