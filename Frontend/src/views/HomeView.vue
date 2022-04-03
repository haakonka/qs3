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
      <div></div>
      <div class="activeSubjects" id="test"></div>
      <div class="assignments1" id="test2"></div>
    </div>
    <h2>Inaktiv kø</h2>
    <div class="container">
      <div></div>
      <div class="activeSubjects" id="inactiveSubjects">
        <div class="inactiveSubject">Emne 1</div>
        <div class="inactiveSubject">Emne 2</div>
        <div class="inactiveSubject">Emne 3</div>
      </div>
      <div class="assignments1" id="inactiveAssignmentsID">
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
  setup() {},
  created() {
    this.showAllUsers();
  },
  props: {
    // Using value here allows us to be v-model compatible.
    value: File,
  },
  methods: {
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

      //let array = JSON.parse(JSON.stringify(res.data));

      console.log("kode" + res.data.at(0).subjectCode);

      const element = document.getElementById("test");
      const element2 = document.getElementById("test2");
      const element3 = document.getElementById("inactiveSubjects");
      const element4 = document.getElementById("inactiveAssignmentsID");

      while (element.firstChild) {
        element.removeChild(element.firstChild);
      }
      while (element2.firstChild) {
        element2.removeChild(element2.firstChild);
      }

      while (element3.firstChild) {
        element3.removeChild(element3.firstChild);
      }

      while (element4.firstChild) {
        element4.removeChild(element4.firstChild);
      }

      let subjectQue = null;
      let subjectAssignment = null;

      for (var i = 0; i < res.data.length; i++) {
        subjectQue = document.createElement("div");
        subjectAssignment = document.createElement("div");

        subjectQue.textContent = "" + res.data.at(i).subjectCode + "";
        subjectAssignment.textContent = "Øvinger";
        subjectAssignment.className = "assignments";
        if (res.data.at(i).statusQue == 1) {
          subjectQue.className = "activeSubject";
          subjectQue.onclick = this.goToQue;
          element.appendChild(subjectQue);
          element2.appendChild(subjectAssignment);
        } else {
          subjectQue.className = "inactiveSubject";
          subjectAssignment.classList.add("inactiveAssignments");
          element3.appendChild(subjectQue);
          element4.appendChild(subjectAssignment);
        }

        subjectAssignment.classList.add(res.data.at(i).subjectCode);
        subjectAssignment.classList.add(res.data.at(i).schoolYear);
        subjectQue.classList.add(res.data.at(i).subjectCode);
        subjectQue.classList.add(res.data.at(i).schoolYear);
        subjectAssignment.onclick = this.onAssignmentSubmit;
      }
    },
    async onAssignmentSubmit(e) {
      console.log(e.target);
      let subjectThingy = e.target.classList;
      if (subjectThingy[1] != "inactiveAssignments") {
        console.log(subjectThingy[1]);
        console.log(subjectThingy[2]);
        localStorage.setItem("subjectCode", JSON.stringify(subjectThingy[1]));
        localStorage.setItem("schoolYear", subjectThingy[2]);
      } else {
        console.log(subjectThingy[2]);
        console.log(subjectThingy[3]);
        localStorage.setItem("subjectCode", JSON.stringify(subjectThingy[2]));
        localStorage.setItem("schoolYear", subjectThingy[3]);
      }

      this.$router.push("/assignments");
    },
    goToQue(e) {
      console.log(e.target);
      let subjectThingy = e.target.classList;
      console.log(subjectThingy[1]);
      console.log(subjectThingy[2]);
      localStorage.setItem("subjectCode", JSON.stringify(subjectThingy[1]));
      localStorage.setItem("schoolYear", subjectThingy[2]);
      this.$router.push("/que");
    },
  },
};
</script>

<style>
.about {
  font-size: 20px;
  height: 100vh;
  width: 100%;
}
h2 {
  color: #cdcdcd;
}
.container {
  display: grid;
  grid-template-columns: 1fr 2fr 1fr 1fr;
  grid-column-gap: 5px;
  grid-row-gap: 20px;
  justify-items: stretch;
  align-items: stretch;
}

.activeSubject {
  display: flex;
  justify-content: center;
  align-items: center;
  background: #ebe8e8;
  margin-right: 10px;
  margin-top: 2em;
  padding: 0.5em;
  height: 2em;
}
.inactiveSubject {
  display: flex;
  justify-content: center;
  align-items: center;
  background: darkgray;
  margin-right: 10px;
  margin-top: 2em;
  height: 2em;
  padding: 0.5em;
}
.assignments {
  display: flex;
  height: 2em;
  justify-content: center;
  align-items: center;
  text-align: center;
  background: #ebe8e8;
  margin: auto;
  margin-top: 2em;
  padding: 0.5em;
}
.activeSubject:hover {
  background: #a7a4a4;
}
.assignments:hover {
  background: #a7a4a4;
}
.inactiveAssignments {
  background: darkgray;
}
.inactiveAssignments:hover {
  background: #a7a4a4;
}
</style>
