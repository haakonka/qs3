<template>
  <div class="assignmentsContainer">
    <button @click="returnToStart">Home</button>
    <h2>Navn</h2>
    <h2 id="assignmentsHeader"></h2>
    <div class="container">
      <div></div>
      <div class="activeSubjects" id="assignmentsDivContainer"></div>
      <div id="validDivContainer"></div>
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
      teacher: false,
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

      if (res != undefined) {
        let jsonArray = res.data;

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
      const header = document.getElementById("assignmentsHeader");
      header.textContent = localStorage.getItem("subjectCode") + " Øvinger";
      const assignmentsDivContainer = document.getElementById(
        "assignmentsDivContainer"
      );
      const validDivContainer = document.getElementById("validDivContainer");

      while (assignmentsDivContainer.firstChild) {
        assignmentsDivContainer.removeChild(assignmentsDivContainer.firstChild);
      }
      while (validDivContainer.firstChild) {
        validDivContainer.removeChild(validDivContainer.firstChild);
      }

      let assignmentDiv = null;
      let validDiv = null;

      let userArray = localStorage.getItem("user");

      let roleArrThing = userArray.split("exp");

      if (roleArrThing[0].includes("2")) {
        this.teacher = true;
      }

      for (var j = 0; j < this.assignments.length; j++) {
        assignmentDiv = document.createElement("div");
        validDiv = document.createElement("div");
        assignmentDiv.textContent =
          "Øving " + this.assignments[j].assignmentNumber;
        assignmentDiv.className = "inactiveSubject";
        validDiv.textContent = this.assignments[j].status;
        validDiv.className = "assignments inactiveAssignments";
        validDiv.classList.add(this.assignments[j].assignmentUserID);

        validDivContainer.appendChild(validDiv);
        assignmentsDivContainer.appendChild(assignmentDiv);
      }
    },
    returnToStart() {
      this.$router.push("/home");
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

      if (res != undefined) {
        let jsonArray = res.data;
        this.assignmentIntervals = jsonArray;
      }

      const passedRequirementsDiv = document.getElementById("passedReq");
      while (passedRequirementsDiv.firstChild) {
        passedRequirementsDiv.removeChild(passedRequirementsDiv.firstChild);
      }

      this.assignmentIntervals = this.removeDuplicates(
        this.assignmentIntervals
      );
      let passedRequirementParagraph = null;
      for (var i = 0; i < this.assignmentIntervals.length; i++) {
        passedRequirementParagraph = document.createElement("p");
        passedRequirementParagraph.textContent =
          "Du må ha bestått: " +
          this.assignmentIntervals[i].minAssignments +
          " av øving " +
          this.assignmentIntervals[i].intervalStart +
          " - " +
          this.assignmentIntervals[i].intervalEnd;
        passedRequirementsDiv.appendChild(passedRequirementParagraph);
      }
    },
    async getAllAssignments() {
      let tokenFromLocal = JSON.stringify(localStorage.getItem("token"));
      let subjectCodeFromLocal = JSON.stringify(
        localStorage.getItem("subjectCode")
      );
      let schoolYearFromLocal = JSON.stringify(
        localStorage.getItem("schoolYear")
      );
      for (var i = 0; this.assignments.length; i++) {
        let userId = this.assignments[i].userID;
        await axios
          .post(
            "http://localhost:8081/api/user/participantInQue/allInstances",
            {
              token: tokenFromLocal,
              subjectCode: subjectCodeFromLocal,
              schoolYear: schoolYearFromLocal,
              userId: userId,
            }
          )
          .catch((error) => {
            console.log(error);
          });
      }
    },

    removeDuplicates(a) {
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

#assignmentsDivContainer > div {
  background-color: #ebe8e8;
}
#validDivContainer > div {
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
