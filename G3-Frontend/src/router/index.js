import Vue from "vue";
import VueRouter from "vue-router";
import TableView from "../views/TableView.vue";
import LoginView from "@/views/LoginView.vue";
import MenuView from "../views/MenuView.vue";
import OrderView from "../views/OrderView.vue";
import OrderDetail from "../views/OrderDetail.vue";
import InvoiceView from "../views/InvoiceView.vue";
import ProfileView from "../views/ProfileView.vue";
import AdminUserView from "../views/AdminUserView.vue";
import AdminView from "../views/AdminView.vue";
import AdminCategoryView from "../views/AdminCategoryView.vue";
import AdminTableView from "../views/AdminTableView.vue";
import AdminDiscountView from "../views/AdminDiscountView.vue";

import state from "@/store/state";
Vue.use(VueRouter);

const routes = [
  {
    path: "*",
    redirect: "/login",
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
  },
  {
    path: "/table",
    name: "table",
    component: TableView,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/menu",
    name: "menu",
    component: MenuView,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/profile",
    name: "profile",
    component: ProfileView,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/invoice",
    name: "invoice",
    component: InvoiceView,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/order",
    name: "order",
    component: OrderView,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/order/:orderId",
    name: "order_detail",
    props: true,
    component: OrderDetail,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/admin/food",
    name: "admin_food",
    component: AdminView,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },
  {
    path: "/admin/user",
    name: "admin_user",
    component: AdminUserView,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },
  {
    path: "/admin/discount",
    name: "admin_discount",
    component: AdminDiscountView,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },
  {
    path: "/admin/category",
    name: "admin_category",
    component: AdminCategoryView,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },
  {
    path: "/admin/table",
    name: "admin_table",
    component: AdminTableView,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});
router.beforeEach(async (to, from, next) => {
  let loginUser = state.loginUser;
  // let role = loginUser.userRoles[0].roleName;
  let isLogin = sessionStorage.getItem("isLogin");

  // from - Home
  // to - Profile
  // Need to be login, But is not login
  if (to.meta.requiresAuth == true && !isLogin) {
    next({ path: "/login" });
    console.log(loginUser);
  }

  //Need to be login, Need to be admin
  // else if (
  //   to.meta.requiresAuth == true &&
  //   to.meta.requiresAdmin == true &&
  //   loginUser.userRoles[0].roleName != "ADMIN"
  // ) {
  //   next({ path: "/table" });
  // }
  //If All Okay
  else {
    next();
  }
});

export default router;
