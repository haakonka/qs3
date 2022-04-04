<template>
  <div class="que">
    <h2 id="id123"></h2>
    <button @click.prevent="returnToStart">Return to home</button>
    <button @click="displayQueueMenu">Still deg i kø</button>
    <div id="queueMenu" style="display: none"></div>
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
        .post("http://localhost:8081/api/user/participantsInQue", {
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
        let response = await axios
          .post("http://localhost:8081/api/user/assignments/instance", {
            token: tokenFromLocal,
            subjectCode: subjectCodeFromLocal,
            schoolYear: schoolYearFromLocal,
            userId: res.data.at(j).userID,
          })
          .catch((error) => {
            console.log(error);
          });
        console.log(response);
        var theAssignmentId = null;
        for (var i = 0; i < response.data.length; i++) {
          if (
            response.data.at(i).assignmentNumber ==
            res.data.at(j).assignmentNumber
          ) {
            theAssignmentId = response.data.at(i).assignmentUserID;
          }
        }
        studentInQueDiv.classList.add(res.data.at(j).participantInQueID + "!");
        console.log("The assignment id is: " + theAssignmentId);
        studentInQueDiv.classList.add(theAssignmentId + ",");
        console.log("the status is: " + res.data.at(j).status);
        studentInQueDiv.classList.add("," + res.data.at(j).status);
        studentInQueDiv.addEventListener("click", (e) => {
          var target = e.target;
          const valuesOfDiv = target.classList;
          console.log(valuesOfDiv);
          const participantId = valuesOfDiv[2].replace("!", "");
          const theAssignmentID = valuesOfDiv[3].replace(",", "");
          const participantStatus = valuesOfDiv[4].replace(",", "");

          if (participantStatus == 0) {
            this.changeQueStatus(participantId, 1);
          } else if (participantStatus == 1) {
            this.changeQueStatus(participantId, 2);
          } else if (participantStatus == 2) {
            this.checkOutOfQue(theAssignmentID, participantId);
          }
        });
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
    async makeParticipantInQue(assignmentNumb) {
      let tokenFromLocal = JSON.stringify(localStorage.getItem("token"));
      let subjectCodeFromLocal = JSON.stringify(
        localStorage.getItem("subjectCode")
      );
      let schoolYearFromLocal = JSON.stringify(
        localStorage.getItem("schoolYear")
      );
      let assignmetLast = assignmentNumb;
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
    async changeQueStatus(participantId, status) {
      let tokenFromLocal = JSON.stringify(localStorage.getItem("token"));
      let res = await axios
        .post("http://localhost:8081/api/studass/participantInQue/status", {
          token: tokenFromLocal,
          participantInQueId: participantId,
          statusChange: status,
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
            myActiveAssignmentsBox.setAttribute("name", "checkboxes");
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
        joinButton.onclick = this.onJoinQueue;
        myQueueMenu.appendChild(joinButton);
      }
    },
    //Må få denne til å virker for knappen over
    async onJoinQueue() {
      let checkboxes = document.querySelectorAll(
        'input[name="checkboxes"]:checked'
      );
      var assignmentsInTheQue = [];
      checkboxes.forEach((checkbox) => {
        assignmentsInTheQue.push(checkbox.value);
      });
      for (var i = 0; i < assignmentsInTheQue.length; i++) {
        this.makeParticipantInQue(assignmentsInTheQue[i]);
      }
      //Fix so the view updates after this, to get the new participants.
      //this.getQueParticipants;
    },
    async checkOutOfQue(assignmentUserID, queueParticipantId) {
      let tokenFromLocal = JSON.stringify(localStorage.getItem("token"));
      let assignmetLast = assignmentUserID;
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
        //Change the value of uniqueId to the actual value of participant in que
        res = await axios
          .post("http://localhost:8081/api/user/participantInQue/delete", {
            token: tokenFromLocal,
            uniqueId: queueParticipantId,
          })
          .catch((error) => {
            console.log(error);
          });
        console.log(res);
      }
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
