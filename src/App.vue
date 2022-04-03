<template>
  <title>QS3</title>
  <nav></nav>
  <router-view />
</template>

<style>
body {
  background: #121212;
  color: #cdcdcd;
  /* background: linear-gradient(-45deg, #ff6ec7, #feeb75);*/
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
nav {
  padding: 30px;
}
nav a {
  font-weight: bold;
  color: #2c3e50;
}
nav a.router-link-exact-active {
  color: #42b983;
}
</style>
