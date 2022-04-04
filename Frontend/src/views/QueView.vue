<template>
  <div class="que">
    <h2 id="id123"></h2>
    <button @click.prevent="returnToStart">Return to home</button>
    <button @click="displayQueueMenu">Still deg i kø</button>
    <div id="queueMenu" style="display: none"></div>
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
  data() {
    return {
      AssignmentsToApprove: [],
      Assignments: [],
    };
  },
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
      console.log(res);
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
    async displayQueueMenu() {
      const myQueueMenu = document.getElementById("queueMenu");
      var displayQueueMenu = myQueueMenu.style.display;
      if (displayQueueMenu == "block") {
        myQueueMenu.style.display = "none";
      } else {
        myQueueMenu.style.display = "block";

        //Adds the assignments to the array.
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
        console.log(res.data);

        //Infinite loop, why????
        while (myQueueMenu.firstChild) {
          myQueueMenu.removeChild(myQueueMenu.firstChild);
        }

        for (var j = 0; j < res.data.length; j++) {
          if (res.data.at(j).status === 0) {
            var myActiveAssignmentsBox = document.createElement("INPUT");
            myActiveAssignmentsBox.setAttribute("type", "checkbox");
            myActiveAssignmentsBox.setAttribute(
              "value",
              res.data.at(j).assignmentNumber
            );
            myActiveAssignmentsBox.className = "Checkboxes";

            var label = document.createElement("label");
            label.htmlFor =
              "Assignment number: " + res.data.at(j).assignmentNumber;
            label.appendChild(
              document.createTextNode(
                "Øving " + res.data.at(j).assignmentNumber
              )
            );

            myQueueMenu.appendChild(label);
            myQueueMenu.appendChild(myActiveAssignmentsBox);
          }
        }

        var joinButton = document.createElement("BUTTON");
        joinButton.setAttribute("id", "joinTheQue");
        joinButton.textContet = "Join que now!";
        joinButton.onclick = "onJoinQueue";
        myQueueMenu.appendChild(joinButton);
      }
    },
    //Må få denne til å virker for knappen over
    async onJoinQueue() {
      let checkboxes = document.querySelectorAll(
        'input[name="Active assignments"]:checked'
      );
      checkboxes.forEach((checkbox) => {
        this.AssignmentsToApprove.push(checkbox.values);
      });
      this.makeParticipantInQue;
    },
    returnToStart() {
      this.$router.push("/home");
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
