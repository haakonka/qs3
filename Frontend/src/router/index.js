import { createRouter, createWebHashHistory } from "vue-router";

const routes = [
  {
    path: "/home",
    name: "about",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/AboutView.vue"),
  },
  {
    path: "/login",
    name: "login",
    component: () => import("../views/LoginView.vue"),
  },
  {
    path: "/assignments",
    name: "assignments",
    component: () => import("../views/AssignmentsView.vue"),
  },
  {
    path: "/active-que",
    name: "activeque",
    component: () => import("../views/ActiveQueView.vue"),
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});
router.beforeEach((to, from, next) => {
  if (
    to.name !== "login" &&
    JSON.stringify(localStorage.getItem("token")).length < 40
  ) {
    console.log("not allowed:");
    console.log(JSON.stringify(localStorage.getItem("token")));
    next({ name: "login" });
  } else {
    console.log("allowed");
    next();
  }
});

export default router;
