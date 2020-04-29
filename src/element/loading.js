/* eslint-disable no-unused-vars */
const loadingUtil = {
  startLoading(loading) {
    loading = this.$loading({
      lock: true,
      text: "登录中...请稍候...",
      background: "rgba(0, 0, 0, 0.7)"
    });
  },
  endLoading(loading) {
    loading.close();
  }
};

export default { loadingUtil };
