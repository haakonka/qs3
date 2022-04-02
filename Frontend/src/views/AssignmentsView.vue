<template>
  <head> </head>
  <body>
    <div layout="column">Hello;</div>
    <button @click.prevent="onStart">Press to add assignment</button>
    <div
      v-for="assignment in assignments"
      :key="assignment"
      class="assignments"
    >
      <div>{{ assignment.subjectCode }} + this is the status</div>
    </div>
  </body>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      assignments: [],
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
        this.assignments = res.data;
      }
    },
  },
};
</script>
