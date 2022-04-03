<template>
  <div class="assignmentsContainer">
    <body>
    <h2 id="header1"></h2>
    <div class="container">
      <div></div>
      <div class="activeSubjects" id="assignmentsC"></div>
      <div class="assignments1" id="validC"></div>
    </div>
    <button @click.prevent="getAssignmentIntervals">
      Press this to get assignment intervals
    </button>
    <button @click.prevent="changeStatusOfAssignments">
      Change the status of all your assignments
    </button>
    <button @click.prevent="makeParticipantInQue">
      Add a participant to que
    </button>
    <button @click.prevent="checkOutOfQue">Remove participant once</button>
    <div
      v-for="assignmentIntervals in assignmentIntervals"
      :key="assignmentIntervals"
    >
      <div>
        Assignment number: {{ assignmentIntervals.assignmentNumber }} Start:
        {{ assignmentIntervals.intervalStart }} End:
        {{ assignmentIntervals.intervalEnd }} Min assignments:
        {{ assignmentIntervals.minAssignments }}
      </div>
    </div>
  </body>
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
      console.log("length of assignments" + this.assignments.length);
      for (var j = 0; j < this.assignments.length; j++) {
        assignmentDiv = document.createElement("div");
        validDiv = document.createElement("div");
        assignmentDiv.textContent =
          "Øving " + this.assignments[j].assignmentNumber;
        assignmentDiv.className = "inactiveSubject";
        validDiv.textContent = this.assignments[j].status;
        validDiv.className = "assignments inactiveAssignments";
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
    async changeStatusOfAssignments() {
      //This method does not work if user is not studass or admin
      let tokenFromLocal = JSON.stringify(localStorage.getItem("token"));
      for (var i = 0; i < this.assignments.length; i++) {
        let assignmentId = this.assignments[i].assignmentUserID;
        let res = await axios
          .post("http://localhost:8081/api/studass/assignment/status", {
            token: tokenFromLocal,
            uniqueId: assignmentId,
          })
          .catch((error) => {
            console.log(error);
          });
        console.log(res);
      }
    },
    async makeParticipantInQue() {
      let tokenFromLocal = JSON.stringify(localStorage.getItem("token"));
      let subjectCodeFromLocal = JSON.stringify(
        localStorage.getItem("subjectCode")
      );
      let schoolYearFromLocal = JSON.stringify(
        localStorage.getItem("schoolYear")
      );
      let assignmetLast =
        this.assignments[this.assignments.length - 1].assignmentUserID;
      assignmetLast++;
      var joined = new Date().getTime();
      console.log(joined);
      let res = await axios
        .post("http://localhost:8081/api/user/participantInQue/create", {
          token: tokenFromLocal,
          subjectCode: subjectCodeFromLocal,
          schoolYear: schoolYearFromLocal,
          assignmentNumber: assignmetLast,
          joinedQue: joined,
        })
        .catch((error) => {
          console.log(error);
        });
      console.log(res);
    },
    async checkOutOfQue() {
      let tokenFromLocal = JSON.stringify(localStorage.getItem("token"));
      let assignmetLast = this.assignments[0].assignmentUserID;
      console.log(assignmetLast);
      let res = await axios
        .post("http://localhost:8081/api/studass/assignment/status", {
          token: tokenFromLocal,
          uniqueId: assignmetLast,
        })
        .catch((error) => {
          console.log(error);
        });
      console.log(res);
      if (res.data === "The status was changed") {
        res = await axios
          .post("http://localhost:8081/api/user/participantInQue/delete", {
            token: tokenFromLocal,
            uniqueId: 7,
          })
          .catch((error) => {
            console.log(error);
          });
        console.log(res);
      }
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
