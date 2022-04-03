<template>
  <div class="about">
    <h2>Aktiv kø</h2>
    <button v-on:click="showAllUsers">TestingShit</button>
    <!--//MEKKE FIL
    -->
    <label class="file-select">
      <div class="select-button">
        <span v-if="value">Selected File: {{ value.name }}</span>
        <span v-else>Select File</span>
      </div>
      <input type="file" @change="handleFileChange" />
    </label>

    <div class="container">
      <div class="activeSubjects">
        <div class="activeSubject">Emne 1</div>
        <div class="activeSubject">Emne 2</div>
        <div class="activeSubject">Emne 3</div>
      </div>
      <div class="assignments1">
        <div class="assignments" v-on:click="onAssignmentSubmit">Øvinger</div>
        <div class="assignments" v-on:click="onAssignmentSubmit">Øvinger</div>
        <div class="assignments" v-on:click="onAssignmentSubmit">Øvinger</div>
      </div>
    </div>
    <h2>Inaktiv kø</h2>
    <div class="container">
      <div class="activeSubjects">
        <div class="inactiveSubject">Emne 1</div>
        <div class="inactiveSubject">Emne 2</div>
        <div class="inactiveSubject">Emne 3</div>
      </div>
      <div class="assignments1">
        <div class="assignments inactiveAssignments">Øvinger</div>
        <div class="assignments inactiveAssignments">Øvinger</div>
        <div class="assignments inactiveAssignments">Øvinger</div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  props: {
    value: File,
  },

  setup() {},
  methods: {
    async onAssignmentSubmit() {
      let tokenFromLocal = JSON.stringify(localStorage.getItem("token"));
      let res = await axios
        .post("http://localhost:8081/api/user/subjects", {
          token: tokenFromLocal,
        })
        .catch((error) => {
          console.log(error);
        });
      console.log(res);

      //Do a check on res to see if correct
      if (res != undefined) {
        let jsonArray = res.data;
        console.log(jsonArray[0].subjectCode);
        localStorage.setItem(
          "subjectCode",
          JSON.stringify(jsonArray[0].subjectCode)
        );
        localStorage.setItem(
          "schoolYear",
          JSON.stringify(jsonArray[0].schoolYear)
        );
        this.$router.push("/assignments");
      }
    },

    async handleFileChange(e) {
      this.$emit("input", e.target.files[0]);
      const reader = new FileReader();

      this.file = e.target.files[0];
      console.log(this.file);
      reader.readAsText(this.file);

      let formData = new FormData();
      formData.append("token", this.tokenFromLocal);
      formData.append("file", this.file);

      let tokenFromLocal = JSON.stringify(localStorage.getItem("token"));
      console.log("token: " + tokenFromLocal);
      console.log("message: " + formData);

      // Send your file to your server and retrieve the response
      const res = await axios.post(
        "https://localhost:8081/api/admin/addUserFromFile",
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
      console.log(tokenFromLocal);
      console.log(res);
    },

    async showAllUsers() {
      let tokenFromLocal = JSON.stringify(localStorage.getItem("token"));
      console.log({
        token: tokenFromLocal,
      });

      let res = await axios
        .post("http://localhost:8081/api/user/subjects", {
          token: tokenFromLocal,
        })
        .catch((error) => {
          console.log(error);
        });

      console.log(res);
    },
  },
};
</script>

<style scoped>
.about {
  font-size: 20px;
  height: 100vh;
}
.container {
  display: flex;
  display: inline-block;
  justify-content: center;
}

.activeSubjects {
  float: left;
}
.assignments1 {
  float: right;
}
.activeSubject {
  display: flex;
  justify-content: center;
  align-items: center;
  background: wheat;
  margin-right: 10px;
  margin-top: 2em;
  height: 5em;
  width: 20em;
}
.inactiveSubject {
  display: flex;
  justify-content: center;
  align-items: center;
  background: lightpink;
  margin-right: 10px;
  margin-top: 2em;
  height: 5em;
  width: 20em;
}
.assignments {
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  background: wheat;
  margin: auto;
  margin-top: 2em;
  height: 5em;
  width: 8em;
}

.activeSubject:hover {
  background: lightgreen;
}
.assignments:hover {
  background: lightgreen;
}

.inactiveAssignments {
  background: lightpink;
}

.inactiveAssignments:hover {
  background: pink;
}

.file-select > .select-button {
  padding: 1rem;

  color: white;
  background-color: #2ea169;

  border-radius: 0.3rem;

  text-align: center;
  font-weight: bold;
}

/* Don't forget to hide the original file input! */
.file-select > input[type="file"] {
  display: none;
}
</style>
