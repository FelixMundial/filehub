/* eslint-disable no-unused-vars */
const Mock = require("mockjs");
const Random = Mock.Random;
Mock.setup({
  timeout: "500-1000"
});

Mock.mock("http://localhost:7000/uaa/login", (req, res) => {
  // 当post或get请求到/uaa/login路由时Mock会拦截请求并返回模拟数据
  return {
    statusCode: 200,
    message: "mock登录成功",
    data: {
      token: Random.string(20)
    }
  };
});

Mock.mock("http://localhost:7000/library/top", (req, res) => {
  let libraries = [];
  for (let i = 0; i < Random.integer(5, 20); i++) {
    let library = {
      libraryId: 1,
      followersCount: Random.integer(0, 999),
      libraryCreationTime: Random.datetime("yyyy-MM-dd HH:mm:ss"),
      libraryDesc: Random.sentence(8, 20),
      libraryName: Random.title(3),
      libraryUrl: Random.url("https", "localhost"),
      collaborators: [
        {
          userNickname: Random.name()
        }
      ]
    };
    libraries.push(library);
  }
  return {
    statusCode: 200,
    message: "success",
    data: libraries
  };
});

Mock.mock("http://localhost:7000/library/1/files", (req, res) => {
  let files = [];
  for (let i = 0; i < Random.integer(5, 20); i++) {
    let file = {
      fileDisplayName: Random.word() + "_" + Random.word() + ".json",
      fileUrl: "",
      fileType: "application.json",
      fileSize: Random.integer(20, 999),
      fileLastUpdateUser: {
        nickname: Random.name()
      },
      fileLastUpdateTime: Random.datetime("yyyy-MM-dd HH:mm:ss")
    };
    files.push(file);
  }
  return {
    statusCode: 200,
    message: "success",
    data: files
  };
});
