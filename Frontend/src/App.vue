<template>
  <nav>
    <router-link to="/home"> Kø </router-link>
    <router-link to="/login"> Login </router-link>
  </nav>
  <router-view />
</template>

<style>
body {
  background: linear-gradient(-45deg, #6d327c, #485da6, #00a1ba);
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
