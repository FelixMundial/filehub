<template>
  <div id="app">
    <router-view />
  </div>
</template>

<style lang="stylus">
#app
  font-family "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
  -webkit-font-smoothing antialiased
  -moz-osx-font-smoothing grayscale
  text-align center
  color #2c3e50
  margin-top 60px
.global-container
  min-height 750px
pre
  display: block
  text-align left
  white-space: pre
  padding: .5rem
  line-height: 1.25
  overflow-x: scroll
  background-color cadetblue
  color aliceblue
  border-radius 5px
  font-family: Menlo, Monaco, "Courier New", monospace
</style>
