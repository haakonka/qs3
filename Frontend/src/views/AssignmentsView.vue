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
  },
};
</script>
