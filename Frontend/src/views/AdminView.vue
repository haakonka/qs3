<template>
  <div class="teacher">
    <h1>Velkommen {{ this.username }}</h1>
    <h2>Dine emner</h2>
    <div id="subjects"></div>
    <button @click="changeClicked">Legg til emne</button>
    <form @submit.prevent="addSubject" v-show="isClicked">
      <p>
        <label for="subjectName" class="label"> Emnenavn </label>
        <input
          class="input"
          v-model="subjectName"
          name="subjectName"
          type="text"
          placeholder="Emnenavn"
        />
      </p>
      <p>
        <label for="subjectCode" class="label"> Emnekode </label>
        <input
          class="input"
          v-model="subjectCode"
          type="text"
          name="emnekode"
          placeholder="Emnekode"
        />
      </p>

      <button>Legg til emne</button>
    </form>
    <button @click="addStudentsToSubject">Legg til studenter</button>
    <form v-show="addingStudents">
      <label class="file-select">
        <div class="select-button">
          <span v-if="value">Selected File: {{ value.name }}</span>
          <span v-else>Select File</span>
        </div>
        <input id="fileinput" type="file" @change="handleFileChange" />
        <input
          type="text"
          v-model="subjectCodeForNewStudents"
          placeholder="SubjectCode"
        />
        <input
          type="text"
          v-model="subjectYearForStudents"
          placeholder="Year of subject (2011)"
        />
      </label>
    </form>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      teacher: false,
      subjectCode: "",
      subjectName: "",
      isClicked: false,
      addingStudents: false,
      subjectCodeForNewStudents: "",
      subjectYearForStudents: "",
      username: "Omar", // should fix for other teachers
    };
  },
  created() {
    this.getSubjects();
  },

  methods: {
    goToSubjectPage(e) {
      let subjectArray = e.target.classList;
      localStorage.setItem("subjectCode", JSON.stringify(subjectArray[0]));
      localStorage.setItem("schoolYear", subjectArray[1]);
      this.$router.push("/adminSubjects");
    },
    async getSubjects() {
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

      const subjectsDiv = document.getElementById("subjects");

      while (subjectsDiv.firstChild) {
        subjectsDiv.removeChild(subjectsDiv.firstChild);
      }

      let subjectDiv = null;

      for (var i = 0; i < res.data.length; i++) {
        subjectDiv = document.createElement("div");
        let addStudents = document.createElement("button");
        let goToSubjectsButton = document.createElement("button");

        goToSubjectsButton.textContent = "Gå til emne";

        goToSubjectsButton.classList.add(res.data.at(i).subjectCode);
        goToSubjectsButton.classList.add(res.data.at(i).schoolYear);
        goToSubjectsButton.onclick = this.goToSubjectPage;

        addStudents.textContent = "Legg til studenter";
        goToSubjectsButton.id = "goToSubject";

        addStudents.onclick = this.addStudentsToSubject;

        subjectDiv.textContent =
          "" + res.data.at(i).subjectCode + "\n" + res.data.at(i).subjectName;

        subjectDiv.appendChild(goToSubjectsButton);
        if (res.data.at(i).statusQue == 1) {
          subjectDiv.className = "adminSubject";
          subjectsDiv.appendChild(subjectDiv);
        } else {
          subjectDiv.className = "adminSubject";
          subjectsDiv.appendChild(subjectDiv);
        }
        subjectDiv.classList.add(res.data.at(i).subjectCode);
        subjectDiv.classList.add(res.data.at(i).schoolYear);
      }
    },
    async onStart() {
      let userArray = localStorage.getItem("user");
      console.log("Her er bruker data" + userArray);
      let roleArrThing = userArray.split("exp");
      console.log(roleArrThing);
      console.log("Skal ha tilgang? " + roleArrThing[0].includes("1"));
      if (roleArrThing[0].includes("2")) {
        this.teacher = true;
      }
    },
    async addSubject() {
      console.log(
        "data :" +
          this.subjectCode +
          "   " +
          this.subjectName +
          " år" +
          new Date().getFullYear()
      );
      let tokenFromLocal = JSON.stringify(localStorage.getItem("token"));
      let gotError = false;
      let res = await axios
        .post("http://localhost:8081/api/admin/addSubject", {
          token: tokenFromLocal,
          subjectCode: this.subjectCode,
          subjectName: this.subjectName,
          schoolYear: new Date().getFullYear(),
        })
        .catch((error) => {
          console.log(error);
          gotError = true;
        });
      if (!gotError) {
        console.log(res);
        this.getSubjects();
        this.isClicked = false;
      }
    },
    changeClicked() {
      this.isClicked = true;
    },

    async addStudentsToSubject() {
      this.addingStudents = !this.addingStudents;
    },
    async handleFileChange(e) {
      this.$emit("input", e.target.files[0]);
      let tokenFromLocal = JSON.stringify(localStorage.getItem("token"));
      let file = e.target.files[0];
      var reader = new FileReader();

      reader.onload = async () => {
        let testInFile = reader.result;
        console.log("token: " + tokenFromLocal);
        testInFile = JSON.stringify(testInFile);

        console.log("fileinput: " + testInFile);

        let line = testInFile.split("\\r\\n")[0];
        console.log(
          "hvor mange folk skal addes" + testInFile.split("\\r\\n").length - 1
        );
        //first user
        console.log("firstline: " + line);
        console.log("lastname: " + line.split(",")[0].substring(1));
        console.log("firstname: " + line.split(",")[1]);
        console.log("emailadre: " + line.split(",")[2].split('\\"')[0]);
        console.log("subjectCode: " + this.subjectCodeForNewStudents);
        console.log("subjectYear: " + this.subjectYearForStudents);

        let res = await axios.post(
          "http://localhost:8081/api/admin/addUserToSubject",
          {
            token: tokenFromLocal,
            lastname: line.split(",")[0].substring(1),
            firstname: line.split(",")[1],
            email: line.split(",")[2].split('\\"')[0],
            subjectCode: this.subjectCodeForNewStudents,
            subjectYear: this.subjectYearForStudents,
          }
        );
        console.log(res);

        //rest of the users
        for (let i = 1; i < testInFile.split("\\r\\n").length - 1; i++) {
          line = testInFile.split("\\r\\n")[i]; //1 is the next user i
          console.log("genreal: " + i + " : " + line);
          console.log("lastname: " + line.split(",")[0]);
          console.log("firstname: " + line.split(",")[1]);
          console.log("emailadre: " + line.split(",")[2].split('\\"')[0]);

          res = await axios.post(
            "http://localhost:8081/api/admin/addUserToSubject",
            {
              token: tokenFromLocal,
              lastname: line.split(",")[0],
              firstname: line.split(",")[1],
              email: line.split(",")[2].split('\\"')[0],
              subjectCode: this.subjectCodeForNewStudents,
              subjectYear: this.subjectYearForStudents,
            }
          );
          console.log(res);
        }
      };

      reader.readAsText(file);

      // Send your file to your server and retrieve the response
    },
  },
};
</script>

<style>
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

#goToSubject {
  display: flex;
  float: right;
  margin: 0px;
  margin-right: 10px;
}
.input {
  font-size: 25px;
}
.label {
  font-size: 25px;
  display: block;
  margin-bottom: 30px;
  color: #cdcdcd;
}
span {
  font-size: 25px;
}

#fileinput {
  font-size: 25px;
}

h1 {
  color: #cdcdcd;
}
h2 {
  color: #cdcdcd;
}
#subjects {
  display: block;
  align-items: center;
  justify-content: center;
}
#subjects > div {
  font-size: 25px;
  width: 50%;
  display: block;
  margin: auto;
  background: #ebe8e8;
  margin-top: 20px;
  padding: 0.5em;
  height: 2em;
}
</style>
