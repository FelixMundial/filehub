module.exports = {
  outputDir: "dist", //build输出目录
  assetsDir: "assets", //静态资源目录（js, css, img）
  lintOnSave: false, //是否开启eslint
  filenameHashing: true,
  devServer: {
    open: true, //是否自动弹出浏览器页面
    host: "localhost",
    port: "8081",
    https: false,
    hotOnly: false, //是否开启热更新
    proxy: {
      "/": {
        target: "http://localhost:7000",
        ws: true,
        changeOrigin: true
      }
      // "/api": {
      //   target: "http://localhost:7000",
      //   ws: true,
      //   changeOrigin: true,
      //   pathRewrite: {
      //     //重写路径 比如'/api/aaa/ccc'重写为'/aaa/ccc'
      //     "^/api": ""
      //   }
      // }
    }
  }
};
