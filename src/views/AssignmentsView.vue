<template>
  <div class="assignmentsContainer">
    <h2 id="header1"></h2>
    <div class="container">
      <div></div>
      <div class="activeSubjects" id="assignmentsC"></div>
      <div class="assignments1" id="validC"></div>
    </div>
    <h2>For å få bestått i dette faget må du ha:</h2>
    <div id="passedReq"></div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      assignments: [],
      assignmentIntervals: [],
      studass: false,
    };
  },
  created() {
    this.onStart();
    this.getAssignmentIntervals();
  },
  methods: {
    async onStart() {
      let tokenFromLocal = JSON.stringify(localStorage.getItem("token"));
      let subjectCodeFromLocal = JSON.stringify(
        localStorage.getItem("subjectCode")
      );
      let schoolYearFromLocal = JSON.stringify(
        localStorage.getItem("schoolYear")
      );
      let res = await axios
        .post("http://localhost:8081/api/user/assignments", {
          token: tokenFromLocal,
          subjectCode: subjectCodeFromLocal,
          schoolYear: schoolYearFromLocal,
        })
        .catch((error) => {
          console.log(error);
        });
      console.log("Localstorage get item" + subjectCodeFromLocal);
      //Do a check on res to see if correct
      if (res != undefined) {
        console.log("Her er assignment array ting" + res.data);
        let jsonArray = res.data;
        console.log(jsonArray[0].schoolYear);
        for (var i = 0; i < jsonArray.length; i++) {
          if (jsonArray[i].status === 1) {
            jsonArray[i].status = "Godkjent";
          } else if (jsonArray[i].status === 0) {
            jsonArray[i].status = "Ikke godkjent";
          }
        }
        this.assignments = jsonArray;
      }

      this.listAssignments();
    },
    listAssignments() {
      const header = document.getElementById("header1");
      header.textContent = localStorage.getItem("subjectCode") + " Øvinger";
      const element = document.getElementById("assignmentsC");
      const validC = document.getElementById("validC");

      while (element.firstChild) {
        element.removeChild(element.firstChild);
      }
      while (validC.firstChild) {
        validC.removeChild(validC.firstChild);
      }

      let assignmentDiv = null;
      let validDiv = null;

      let userArray = localStorage.getItem("user");
      console.log("Her er bruker data" + userArray);
      let roleArrThing = userArray.split("exp");
      console.log(roleArrThing);
      console.log("Skal ha tilgang? " + roleArrThing[0].includes("1"));
      if (roleArrThing[0].includes("1")) {
        this.studass = true;
      }

      console.log("length of assignments" + this.assignments.length);
      for (var j = 0; j < this.assignments.length; j++) {
        assignmentDiv = document.createElement("div");
        validDiv = document.createElement("div");
        assignmentDiv.textContent =
          "Øving " + this.assignments[j].assignmentNumber;
        assignmentDiv.className = "inactiveSubject";
        validDiv.textContent = this.assignments[j].status;
        validDiv.className = "assignments inactiveAssignments";
        if (this.studass) {
          validDiv.onclick = this.changeValidStatus;
          validDiv.id = "studAss";
        }
        validC.appendChild(validDiv);
        element.appendChild(assignmentDiv);
      }
    },

    async getAssignmentIntervals() {
      let tokenFromLocal = JSON.stringify(localStorage.getItem("token"));
      let subjectCodeFromLocal = JSON.stringify(
        localStorage.getItem("subjectCode")
      );
      let schoolYearFromLocal = JSON.stringify(
        localStorage.getItem("schoolYear")
      );
      let res = await axios
        .post("http://localhost:8081/api/user/assignments/interval", {
          token: tokenFromLocal,
          subjectCode: subjectCodeFromLocal,
          schoolYear: schoolYearFromLocal,
        })
        .catch((error) => {
          console.log(error);
        });
      console.log(res);
      if (res != undefined) {
        let jsonArray = res.data;
        this.assignmentIntervals = jsonArray;
      }

      const elementor = document.getElementById("passedReq");
      while (elementor.firstChild) {
        elementor.removeChild(elementor.firstChild);
      }

      this.assignmentIntervals = this.uniq_fast(this.assignmentIntervals);
      let passedReq1 = null;
      for (var i = 0; i < this.assignmentIntervals.length; i++) {
        passedReq1 = document.createElement("p");
        passedReq1.textContent =
          "Du må ha bestått: " +
          this.assignmentIntervals[i].minAssignments +
          " av øving" +
          this.assignmentIntervals[i].intervalStart +
          " - " +
          this.assignmentIntervals[i].intervalEnd;
        elementor.appendChild(passedReq1);
      }
    },
    async changeValidStatus() {
      let tokenFromLocal = JSON.stringify(localStorage.getItem("token"));
      //finne ut den riktige
      //Fikse så denne endrer den den skal endre, ikke en helt random en, sette den i loopen over
      let assignmentId = this.assignments[0].assignmentUserID;
      console.log("assignment id" + assignmentId);
      let res = await axios.post(
        "http://localhost:8081/api/user/assignment/status",
        {
          token: tokenFromLocal,
          assignmentUserId: assignmentId,
        }
      );
      console.log(res.data);

      console.log("HEi jeg blir trykka på");
      /*
      if (e.target.textContent == "Ikke godkjent") {
        e.target.textContent = "Godkjent";
      } */
    },
    uniq_fast(a) {
      var seen = {};
      var out = [];
      var len = a.length;
      var j = 0;
      for (var i = 0; i < len; i++) {
        var item = a[i];
        if (seen[item] !== 1) {
          seen[item] = 1;
          out[j++] = item;
        }
      }
      return out;
    },
  },
};
</script>

<style>
.assignmentsContainer {
  font-size: 20px;
  height: 100vh;
  width: 100%;
}
.container {
  display: grid;
  grid-template-columns: 1fr 2fr 1fr 1fr;
  grid-column-gap: 5px;
  grid-row-gap: 20px;
  justify-items: stretch;
  align-items: stretch;
}
p {
  color: #cdcdcd;
}
#studAss:hover {
  background-color: #a7a4a4;
}
#assignmentsC > div {
  background-color: #ebe8e8;
}
#validC > div {
  background-color: #ebe8e8;
}
.assignments2 {
  display: flex;
  height: 2em;
  justify-content: center;
  align-items: center;
  text-align: center;
  color: cyan;
  margin: auto;
  margin-top: 2em;
  padding: 0.5em;
}
</style>
