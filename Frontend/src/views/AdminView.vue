<template>
  <div class="teacher">
    <h2>Dette er nettsiden for læreren</h2>
    <button></button>
  </div>
</template>

<script>
export default {
  data() {
    return { teacher: false };
  },

  methods: {
    async onStart() {
      let userArray = localStorage.getItem("user");
      console.log("Her er bruker data" + userArray);
      let roleArrThing = userArray.split("exp");
      console.log(roleArrThing);
      console.log("Skal ha tilgang? " + roleArrThing[0].includes("1"));
      if (roleArrThing[0].includes("2")) {
        this.teacher = true;
      }
    },
  },
};
</script>

<style></style>
