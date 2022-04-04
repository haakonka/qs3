<template>
  <div>
    <h2 id="header1"></h2>

    <div class="teacherSubjects" id="assignmentsC"></div>
    <button @click="addAssignmentActive">Legg til øving</button>
    <form @submit.prevent="addAssignment" v-show="addButtonClicked">
      <p>
        <label for="Assignment" class="label"> Øvingsnr </label>
        <input
          class="input"
          v-model="assignmentNumber"
          name="assigmmentNumber"
          type="number"
          placeholder="Øving"
        />
      </p>
      <p>
        <label for="startOfRange" class="label"> Start på intervallet </label>
        <input
          class="input"
          v-model="intervalStart"
          type="number"
          name="startOfRange"
          placeholder="Start på intervallet"
        />
      </p>
      <p>
        <label for="endOfRange" class="label"> Slutt på intervallet </label>
        <input
          class="input"
          v-model="intervalEnd"
          type="number"
          name="endOfRange"
          placeholder="Slutt på intervallet"
        />
      </p>
      <p>
        <label for="obligatorisk" class="label">
          Hvor mange av disse må være godkjent?
        </label>
        <input
          class="input"
          v-model="minAssignments"
          type="number"
          name="obligatorisk"
          placeholder="Antall godkjent"
        />
      </p>
      <button>Legg til</button>
    </form>

    <div id="forAddButton"></div>
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
      addButtonClicked: false,
      assignmentNumber: null,
      intervalStart: null,
      intervalEnd: null,
      minAssignments: null,
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
      while (element.firstChild) {
        element.removeChild(element.firstChild);
      }

      console.log("length of assignments" + this.assignments.length);
      for (var j = 0; j < this.assignments.length; j++) {
        let assignmentDiv = document.createElement("div");
        assignmentDiv.textContent =
          "Øving " + this.assignments[j].assignmentNumber;
        assignmentDiv.className = "teacherSubject";

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
    async changeValidStatus(e) {
      let tokenFromLocal = JSON.stringify(localStorage.getItem("token"));
      let assignmentId = e.target.classList[2];
      console.log("assignment id" + assignmentId);
      let res = await axios.post(
        "http://localhost:8081/api/studass/assignment/status",
        {
          token: tokenFromLocal,
          uniqueId: assignmentId,
        }
      );
      console.log(res.data);

      console.log("Hei jeg blir trykka på");
      this.onStart();
      this.listAssignments();
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
    addAssignmentActive() {
      this.addButtonClicked = true;
    },
    async addAssignment() {
      let tokenFromLocal = JSON.stringify(localStorage.getItem("token"));
      let schoolYearFromLocal = JSON.stringify(
        localStorage.getItem("schoolYear")
      );

      await axios.post("http://localhost:8081/api/admin/addAsignment", {
        token: tokenFromLocal,
        subjectCode: localStorage.getItem("subjectCode"),
        schoolYear: schoolYearFromLocal,
        assignmentNumber: this.assignmentNumber,
        intervalStart: this.intervalStart,
        intervalEnd: this.intervalEnd,
        minAssignments: this.minAssignments,
      });
      this.listAssignments();
      this.addButtonClicked = false;
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

#assignmentsC > div {
  font-size: 25px;
  width: 40%;
  display: block;
  margin: auto;
  background: #ebe8e8;
  margin-top: 20px;
  padding: 0.5em;
  height: 2em;
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
