<template>
  <div class="about">
    <h2>Velkommen Navn</h2>
    <h2>Aktiv kø</h2>
    <div class="container">
      <div></div>
      <div class="activeSubjects" id="activeSubjectsDiv"></div>
      <div id="activeAssignmentsDiv"></div>
    </div>
    <h2>Inaktiv kø</h2>
    <div class="container">
      <div></div>
      <div class="activeSubjects" id="inactiveSubjects"></div>
      <div id="inactiveAssignmentsDiv"></div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  setup() {},
  data() {
    return {
      subjectCodeForNewStudents: "",
      subjectYearForStudents: "",
    };
  },

  created() {
    this.showAllUsers();
  },
  methods: {
    csvToArray(str, delimiter = ",") {
      const headers = str.slice(0, str.indexOf("\n")).split(delimiter);

      const rows = str.slice(str.indexOf("\n") + 1).split("\n");
      const arr = rows.map(function (row) {
        const values = row.split(delimiter);
        const el = headers.reduce(function (object, header, index) {
          object[header] = values[index];
          return object;
        }, {});
        return el;
      });

      // return the array
      return arr;
    },

    async showAllUsers() {
      let tokenFromLocal = JSON.stringify(localStorage.getItem("token"));
      let res = await axios
        .post("http://localhost:8081/api/user/subjects", {
          token: tokenFromLocal,
        })
        .catch((error) => {
          console.log(error);
        });

      const activeSubjectsDiv = document.getElementById("activeSubjectsDiv");
      const activeAssignmentsDiv = document.getElementById(
        "activeAssignmentsDiv"
      );
      const inactiveSubjectsDiv = document.getElementById("inactiveSubjects");
      const inactiveAssignmentsDiv = document.getElementById(
        "inactiveAssignmentsDiv"
      );

      while (activeSubjectsDiv.firstChild) {
        activeSubjectsDiv.removeChild(activeSubjectsDiv.firstChild);
      }
      while (activeAssignmentsDiv.firstChild) {
        activeAssignmentsDiv.removeChild(activeAssignmentsDiv.firstChild);
      }

      while (inactiveSubjectsDiv.firstChild) {
        inactiveSubjectsDiv.removeChild(inactiveSubjectsDiv.firstChild);
      }

      while (inactiveAssignmentsDiv.firstChild) {
        inactiveAssignmentsDiv.removeChild(inactiveAssignmentsDiv.firstChild);
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
          activeSubjectsDiv.appendChild(subjectQue);
          activeAssignmentsDiv.appendChild(subjectAssignment);
        } else {
          subjectQue.className = "inactiveSubject";
          subjectAssignment.classList.add("inactiveAssignments");
          inactiveSubjectsDiv.appendChild(subjectQue);
          inactiveAssignmentsDiv.appendChild(subjectAssignment);
        }

        subjectAssignment.classList.add(res.data.at(i).subjectCode);
        subjectAssignment.classList.add(res.data.at(i).schoolYear);
        subjectQue.classList.add(res.data.at(i).subjectCode);
        subjectQue.classList.add(res.data.at(i).schoolYear);
        subjectAssignment.onclick = this.onAssignmentSubmit;
      }
    },
    async onAssignmentSubmit(e) {
      let subjectArray = e.target.classList;
      if (subjectArray[1] != "inactiveAssignments") {
        localStorage.setItem("subjectCode", JSON.stringify(subjectArray[1]));
        localStorage.setItem("schoolYear", subjectArray[2]);
      } else {
        localStorage.setItem("subjectCode", JSON.stringify(subjectArray[2]));
        localStorage.setItem("schoolYear", subjectArray[3]);
      }

      this.$router.push("/assignments");
    },
    goToQue(e) {
      let subjectArray = e.target.classList;

      localStorage.setItem("subjectCode", JSON.stringify(subjectArray[1]));
      localStorage.setItem("schoolYear", subjectArray[2]);
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
