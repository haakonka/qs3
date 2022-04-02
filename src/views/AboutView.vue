<template>
  <div class="about">
    <h2>Aktiv kø</h2>
    <div class="container">
      <div></div>
      <div class="activeSubjects" id="test">
        <div class="activeSubject" @click="goToQue">Emne 1</div>
        <div class="activeSubject">Emne 2</div>
        <div class="activeSubject">Emne 3</div>
      </div>
      <div class="assignments1" id="test2">
        <div class="assignments" v-on:click="onAssignmentSubmit">Øvinger</div>
        <div class="assignments" v-on:click="onAssignmentSubmit">Øvinger</div>
        <div class="assignments" v-on:click="onAssignmentSubmit">Øvinger</div>
      </div>
    </div>
    <h2>Inaktiv kø</h2>
    <div class="container">
      <div></div>
      <div class="activeSubjects">
        <div class="inactiveSubject">Emne 1</div>
        <div class="inactiveSubject">Emne 2</div>
        <div class="inactiveSubject">Emne 3</div>
      </div>
      <div class="assignments1">
        <div class="assignments inactiveAssignments">Øvinger</div>
        <div class="assignments inactiveAssignments">Øvinger</div>
        <div class="assignments inactiveAssignments">Øvinger</div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  setup() {},
  created() {
    this.showAllUsers();
  },
  methods: {
    async showAllUsers() {
      let tokenFromLocal = JSON.stringify(localStorage.getItem("token"));
      console.log({
        token: tokenFromLocal,
      });

      let res = await axios
        .post("http://localhost:8081/api/user/subjects", {
          token: tokenFromLocal,
        })
        .catch((error) => {
          console.log(error);
        });

      console.log(res.data);

      //let array = JSON.parse(JSON.stringify(res.data));

      console.log("kode" + res.data.at(0).subjectCode);

      const element = document.getElementById("test");
      const element2 = document.getElementById("test2");
      let subjectQue = null;
      let subjectAssignment = null;
      for (var i = 0; i < res.data.length; i++) {
        subjectQue = document.createElement("div");
        subjectAssignment = document.createElement("div");

        subjectQue.textContent = "" + res.data.at(i).subjectCode + "";

        subjectAssignment.textContent = "Øvinger";
        subjectQue.className = "activeSubject";
        subjectAssignment.className = "assignments";
        subjectAssignment.classList.add(res.data.at(i).subjectCode);
        subjectAssignment.classList.add(res.data.at(i).schoolYear);
        subjectAssignment.onclick = this.onAssignmentSubmit;
        element.appendChild(subjectQue);
        element2.appendChild(subjectAssignment);
      }
    },
    async onAssignmentSubmit(e) {
      console.log(e.target);
      let subjectThingy = e.target.classList;
      console.log(subjectThingy[1]);
      console.log(subjectThingy[2]);
      localStorage.setItem("subjectCode", JSON.stringify(subjectThingy[1]));
      localStorage.setItem("schoolYear", subjectThingy[2]);
      this.$router.push("/assignments");
    },
    goToQue() {
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
  /*
  display: flex;
  display: inline-block;
  justify-content: center;
  */
}
.activeSubjects {
  /*justify-self: end;
  float: left;*/
}
.assignments1 {
  /*justify-self: start;
  /* float: right; */
}
.activeSubject {
  display: flex;
  justify-content: center;
  align-items: center;
  background: wheat;
  margin-right: 10px;
  margin-top: 2em;
  padding: 0.5em;
  height: 2em;
  /*
  height: 5em;
  width: 20em; */
}
.inactiveSubject {
  display: flex;
  justify-content: center;
  align-items: center;
  background: lightpink;
  margin-right: 10px;
  margin-top: 2em;
  height: 2em;
  padding: 0.5em;
  /*
  height: 5em;
  width: 20em; */
}
.assignments {
  display: flex;
  height: 2em;
  justify-content: center;
  align-items: center;
  text-align: center;
  background: wheat;
  margin: auto;
  margin-top: 2em;
  padding: 0.5em;
}
.activeSubject:hover {
  background: lightgreen;
}
.assignments:hover {
  background: lightgreen;
}
.inactiveAssignments {
  background: lightpink;
}
.inactiveAssignments:hover {
  background: pink;
}
</style>
