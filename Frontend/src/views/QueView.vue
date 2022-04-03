<template>
  <div class="que">
    <h2 id="id123"></h2>
    <button>Still deg i kø</button>
    <h2>Din plass i køen: 3</h2>

    <div id="que"></div>
    <h2 id="numOfStud"></h2>
    <button>Vis ekstra info</button>
    <h2 id="time"></h2>
  </div>
</template>

<script>
import axios from "axios";
export default {
  created() {
    this.getQueParticipants();
  },
  methods: {
    async getQueParticipants() {
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

      const queDiv = document.getElementById("que");

      while (queDiv.firstChild) {
        queDiv.remove(queDiv.firstChild);
      }

      for (var j = 0; j < res.data.length; j++) {
        let studentInQueDiv = document.createElement("div");
        studentInQueDiv.className = "assignments inactiveAssignments";
        studentInQueDiv.textContent =
          "Navn " +
          "Øving " +
          res.data.at(j).assignmentNumber +
          " " +
          res.data.at(j).status;

        queDiv.appendChild(studentInQueDiv);
      }

      const element = document.getElementById("numOfStud");
      const element2 = document.getElementById("time");
      element.textContent = "Antall studenter i køen: " + res.data.length;
      element2.textContent = "Estimert ventetid: " + res.data.length * 3;
      const element3 = document.getElementById("id123");
      element3.textContent = localStorage.getItem("subjectCode");
      console.log("antall folk i køen" + res.data.length);
    },
  },
};
</script>

<style>
.que {
  font-size: 25px;
}
#que > div {
  width: 50%;
}
</style>
