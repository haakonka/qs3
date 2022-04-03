<template>
  <head> </head>
  <body>
    <div layout="column">Hello this is the page for;</div>
    <button @click.prevent="onStart">
      Press this to get all assignments for this subject
    </button>
    <div
      v-for="assignment in assignments"
      :key="assignment"
      class="assignments"
    >
      <div>
        {{ assignment.subjectCode }} + {{ assignment.schoolYear }} +
        {{ assignment.assignmentNumber }} this is the status:
        {{ assignment.status }}
      </div>
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
  methods: {
    addToQue() {
      this.assignments.push(1);
    },
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
      console.log(res);

      //Do a check on res to see if correct
      if (res != undefined) {
        let jsonArray = res.data;
        console.log(jsonArray[0].schoolYear);
        for (var i = 0; i < jsonArray.length; i++) {
          if (jsonArray[i].status === 1) {
            jsonArray[i].status = "Godkjent";
          }
        }
        this.assignments = jsonArray;
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
  },
};
</script>
