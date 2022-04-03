<template>
  <div class="login">
    <h1>QS3</h1>
    <form @submit.prevent="onSubmit">
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
<style>
.login {
  overflow-y: hidden;
  height: 100vh;
  font-size: 20px;
  color: #cdcdcd;
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
import axios from "axios";
import jwt_decode from "jwt-decode";
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
    async onSubmit() {
      console.log("data :" + this.password + "   " + this.email);
      let gotError = false;
      let res = await axios
        .post("http://localhost:8081/api/login/authentication", {
          username: this.email,
          password: this.password,
        })
        .catch((error) => {
          console.log(error);
          gotError = true;
        });
      if (!gotError) {
        console.log(res);
        let token = res.data;
        localStorage.setItem("token", token);
        let decoded = jwt_decode(token);
        localStorage.setItem("user", JSON.stringify(decoded));
        console.log("user: " + decoded);
        this.$router.push("/home");
      }
    },
  },
};
</script>
