<template>
  <div class="teacher">
    <h1>Velkommen Omar</h1>
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
    };
  },
  created() {
    this.showAllUsers();
  },

  methods: {
    goToSubjectPage(e) {
      console.log("her er jeg");
      console.log(e.target);
      let subjectThingy = e.target.classList;

      console.log(subjectThingy[1]);
      console.log(subjectThingy[2]);
      localStorage.setItem("subjectCode", JSON.stringify(subjectThingy[1]));
      localStorage.setItem("schoolYear", subjectThingy[2]);

      this.$router.push("/adminSubjects");
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

      console.log(res.data);

      console.log("kode" + res.data.at(0).subjectCode);

      const element = document.getElementById("subjects");

      while (element.firstChild) {
        element.removeChild(element.firstChild);
      }

      let subjectQue = null;

      for (var i = 0; i < res.data.length; i++) {
        subjectQue = document.createElement("div");
        let addStudents = document.createElement("button");
        addStudents.textContent = "Legg til studenter";
        addStudents.id = "addStudentsButton";

        addStudents.onclick = this.addStudentsToSubject;

        subjectQue.textContent =
          "" + res.data.at(i).subjectCode + "\n" + res.data.at(i).subjectName;

        subjectQue.appendChild(addStudents);
        if (res.data.at(i).statusQue == 1) {
          subjectQue.className = "adminSubject";
          element.appendChild(subjectQue);
        } else {
          subjectQue.className = "adminSubject";
          element.appendChild(subjectQue);
        }
        subjectQue.classList.add(res.data.at(i).subjectCode);
        subjectQue.classList.add(res.data.at(i).schoolYear);
        //subjectQue.onclick = this.goToSubjectPage;
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
        this.showAllUsers();
        //do stuff
        this.isClicked = false;
      }
    },
    changeClicked() {
      this.isClicked = true;
    },

    async addStudentsToSubject() {
      alert("adding students");
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

#addStudentsButton {
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
