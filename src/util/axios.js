// import Vue from "vue";
import Axios from "axios";
import router from "../router";
import { Message } from "element-ui";
import { showLoading, hideLoading } from "./loading";

// Axios.defaults.baseURL = "http://localhost:7000";
const axios = Axios.create({
  baseURL: "http://localhost:7000",
  withCredentials: true,
  timeout: 40000
});

axios.interceptors.request.use(
  config => {
    // 携带请求头
    if (sessionStorage.token) {
      config.headers["Authorization"] = /*"Bearer " + */ sessionStorage.token
        ? sessionStorage.token
        : "";
    }
    config.headers["Accept"] = "application/json";
    // 判断当前请求是否设置了不显示Loading
    if (!config.headers.noLoading) {
      showLoading(
        config.headers.loadingText,
        config.headers.loadingTarget,
        config.headers.loadingBackgroundColor
      );
      config.headers.loadingText = "";
      config.headers.loadingBackgroundColor = "";
    }
    return config;
  },
  //  后端接口请求失败
  err => {
    hideLoading();
    Message.error("网络暂时不可用，请稍后重试...");
    return Promise.resolve(err);
  }
);

axios.interceptors.response.use(
  response => {
    if (!response.config.headers.noLoading) {
      hideLoading();
    }
    let token;
    switch (response.data.statusCode) {
      case 200:
        // 判断响应中是否存在token，若存在则替换本地token
        // [后端返回的token应在headers中而非data中]
        // token = response.headers.authorization;
        if (response.data.data && response.data.data.token) {
          token = response.data.data.token;
          // axios.defaults.headers.common["Authorization"] = token;
          sessionStorage.setItem("token", token);
        }
        break;
      case 401:
        // 清除token
        sessionStorage.removeItem("token");
        Message.error(response.data.message);
        /* 跳转回认证页面，并将目标路由的url路径保存在query中 */
        router.push({
          path: "/",
          query: { redirect: router.path }
        });
        break;
      default:
        Message.error(response.data.message);
    }
    return response;
  },
  //  非法响应
  error => {
    console.log(error);
    if (!error.config.headers.noLoading) {
      hideLoading();
    }
    // if (error.response) {
    //   switch (error.response.status) {
    //     case 401:
    //       sessionStorage.removeItem("token");
    //       Message.error(error.response.statusText);
    //       router.replace({
    //         path: "/",
    //         query: { redirect: router.currentRoute.fullPath } // 登录成功后跳转浏览的当前页面
    //       });
    //   }
    // }
    Message.error("服务暂时不可用，请稍后重试...");
    return Promise.reject(error);
  }
);

export default axios;
