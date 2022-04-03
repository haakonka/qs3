<template>
  <div class="que">
    <h1 id="id123"></h1>
    <button>Still deg i kø</button>
    <h2>Din plass i køen: 3</h2>
    <h2 id="numOfStud"></h2>
    <div>Student 1 Ø6/G Ventet 4:20</div>
    <div>Student 2 Ø6/G Ventet 4:20</div>
    <button>Vis ekstra info</button>
    <h2>Estimert ventetid: 13:37</h2>
    <h2>Studentassistenter som retter: 3</h2>
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

      const element = document.getElementById("numOfStud");
      element.textContent = "Antall studenter i køen: " + res.data.length;

      const element3 = document.getElementById("id123");
      element3.textContent = localStorage.getItem("subjectCode");
      console.log("antall folk i køen" + res.data.length);
    },
  },
};
</script>

<style>
.que {
  color: #42b983;
}
</style>
