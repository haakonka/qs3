<template>
  <head> </head>
  <body>
    <div layout="column">Hello;</div>
    <button @click.prevent="addToQue">Press to add assignment</button>
    <div
      v-for="participantsInQue in participantsInQue"
      :key="participantsInQue"
      class="participantsInQue"
    >
      {{ participantsInQue }}
    </div>
  </body>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      participantsInQue: [1, 2, 3, 4, 5, 6],
    };
  },
  methods: {
    addToQue() {
      this.participantsInQue.push(1);
    },
    async onStart() {
      let res = await axios.get("http://localhost:8081/api/user/assignments");

      //Do a check on res to see if correct
      if (res != undefined) {
        this.participantsInQue = JSON.stringify(res);
      }
    },
  },
};
</script>
