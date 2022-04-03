<template>
  <div class="que">
    <h2 id="id123"></h2>
    <button>Still deg i kø</button>
    <h2>Din plass i køen: 3</h2>

    <div id="que"></div>

    <button>Vis ekstra info</button>
    <h2 id="numOfStud"></h2>
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
        .post("http://localhost:8081/api/user/participantsInQue", {
          token: tokenFromLocal,
          subjectCode: subjectCodeFromLocal,
          schoolYear: schoolYearFromLocal,
        })
        .catch((error) => {
          console.log(error);
        });

      console.log(res.data);
      const queDiv = document.getElementById("que");

      while (queDiv.firstChild) {
        queDiv.remove(queDiv.firstChild);
      }

      for (var j = 0; j < res.data.length; j++) {
        if (res.data.at(j).status == 0) {
          res.data.at(j).status = "Godkjenning";
        } else if (res.data.at(j).status == 1) {
          res.data.at(j).status = "Hjelp";
        } else {
          res.data.at(j).status = "Betjent";
        }
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
</style>
