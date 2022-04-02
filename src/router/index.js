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
    path: "/",
    name: "login",
    component: () => import("../views/LoginView.vue"),
  },
  {
    path: "/assignments",
    name: "assignments",
    component: () => import("../views/AssignmentsView.vue"),
  },
  {
    path: "/que",
    name: "que",
    component: () => import("../views/QueView.vue"),
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  let token = JSON.stringify(localStorage.getItem("token"));
  if (to.name !== "login" && token.split(".").length < 3) {
    console.log("not allowed:");
    console.log(JSON.stringify(localStorage.getItem("token")));
    next({ name: "login" });
  } else {
    console.log("allowed");
    next();
  }
});

export default router;
