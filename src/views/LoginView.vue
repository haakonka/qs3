<template>
  <div class="login">
    <title>QS3</title>
    <h1>QS3</h1>
    <form @submit="onSubmit">
      <p>
        <label for="email"> E-post </label>
        <input v-model="email" name="email" type="email" placeholder="E-post" />
      </p>
      <p>
        <label for="password"> Passord </label>
        <input
          v-model="password"
          type="password"
          name="password"
          placeholder="Passord"
        />
      </p>
      <button>Logg inn</button>
      <a href="#"> Glemt passord? </a>
    </form>
  </div>
</template>
<style scoped>
.login {
  overflow-y: hidden;
  height: 100vh;
  font-size: 20px;
  color: darkgray;
  font-size: 30px;
}
h1 {
  font-size: 75px;
}
button {
  font-size: 20px;
  background: #cde8e8;
  border-radius: 5px;
  display: block;
  margin: auto;
  padding: 0.5em 1.5em;
  margin-top: 20px;
  transition: 0.2s;
}
button:hover {
  cursor: pointer;
  background-color: #158487;
}
a {
  color: #158487;
  margin: 3em;
  font-size: 14px;
}
a:hover {
  color: rebeccapurple;
}
label {
  display: block;
  margin: auto;
}
</style>

<script>
export default {
  name: "LoginView",
  components: {},
  data() {
    return {
      email: "",
      password: "",
      userNotFound: false,
    };
  },
  methods: {
    onSubmit() {
      /*
      // send a POST request
      axios({
        method: "post",
        url: "/login",
        data: {
          email: this.email,
          password: this.password,
        },
      }); */
      this.$router.push("/home");
    },
  },
};
</script>
