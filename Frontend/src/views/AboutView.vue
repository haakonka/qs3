<template>
  <div class="about">
    <h2>Aktiv kø</h2>
    <div class="container">
      <div class="activeSubjects">
        <div class="activeSubject">Emne 1</div>
        <div class="activeSubject">Emne 2</div>
        <div class="activeSubject">Emne 3</div>
      </div>
      <div class="assignments1">
        <div class="assignments" v-on:click="onAssignmentSubmit">Øvinger</div>
        <div class="assignments" v-on:click="onAssignmentSubmit">Øvinger</div>
        <div class="assignments" v-on:click="onAssignmentSubmit">Øvinger</div>
      </div>
    </div>
    <h2>Inaktiv kø</h2>
    <div class="container">
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
export default {
  setup() {},
  methods: {
    onAssignmentSubmit() {
      this.$router.push("/assignments");
    },
  },
};
</script>

<style scoped>
.about {
  font-size: 20px;
  height: 100vh;
}
.container {
  display: flex;
  display: inline-block;
  justify-content: center;
}

.activeSubjects {
  float: left;
}
.assignments1 {
  float: right;
}
.activeSubject {
  display: flex;
  justify-content: center;
  align-items: center;
  background: wheat;
  margin-right: 10px;
  margin-top: 2em;
  height: 5em;
  width: 20em;
}
.inactiveSubject {
  display: flex;
  justify-content: center;
  align-items: center;
  background: lightpink;
  margin-right: 10px;
  margin-top: 2em;
  height: 5em;
  width: 20em;
}
.assignments {
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  background: wheat;
  margin: auto;
  margin-top: 2em;
  height: 5em;
  width: 8em;
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
