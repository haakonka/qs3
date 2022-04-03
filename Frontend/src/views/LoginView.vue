<template>
  <title>QS3</title>
  <h1>QS3</h1>
  <div class="login">
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
<script>
import axios from "axios";
import jwt_decode from "jwt-decode";
export default {
  name: "LoginView",
  components: {},
  props: {
    value: File,
  },
  data() {
    return {
      email: "",
      password: "",
      userNotFound: false,
    };
  },
  methods: {
    async handleFileChange(e) {
      // Whenever the file changes, emit the 'input' event with the file data.
      this.$emit("input", e.target.files[0]);

      const reader = new FileReader();

      reader.readAsText(this.file);

      reader.onload = () => {
        console.log(reader.result); // contains the file content as a string
      };

      reader.onerror = () => {
        console.log(reader.error);
      };

      let message = localStorage.getItem("token");
      message = message + reader.result;

      // Send your file to your server and retrieve the response
      const res = await axios.post(
        "https://localhost/api/admin/addUserFromFile",
        {
          message: message,
        }
      );
      console.log(res);
    },

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
<style scoped>
.login {
  height: 100vh;
  font-size: 20px;
}
h1 {
  font-size: 75px;
  color: #2e5984;
}
button {
  background: pink;
  border-radius: 5px;
  display: block;
  margin: auto;
  padding: 0.5em 1.5em;
  margin-top: 20px;
  margin-bottom: 20px;
  transition: 0.2s;
}

button:hover {
  cursor: pointer;
  background-color: lightyellow;
}
a {
  margin: 3em;
  font-size: 14px;
}

label {
  display: block;
  margin: auto;
}
</style>
