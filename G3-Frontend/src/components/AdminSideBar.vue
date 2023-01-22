<template>
  <v-navigation-drawer
    v-model="drawer"
    app
    color="white"
    mini-variant
    mini-variant-width="90"
  >
    <v-list flat>
      <v-list-item-group v-model="selectedItem">
        <v-list-item to="/table">
          <v-list-item-content>
            <v-avatar class="d-block text-center mx-auto" size="40">
              <v-icon color="primary">fas fa-shop</v-icon>
            </v-avatar>
          </v-list-item-content>
        </v-list-item>
        <v-list-item
          v-for="(item, i) in items"
          :key="i"
          active-class="border"
          :ripple="false"
          :to="item.route"
        >
          <v-list-item-content>
            <v-icon v-text="item.icon"></v-icon>
            <v-list-item-subtitle
              align="center"
              v-text="item.text"
              class="mt-3 caption"
            ></v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </v-list-item-group>
    </v-list>
    <v-spacer></v-spacer>

    <div
      style="
        position: absolute;
        bottom: 20px;
        margin-left: auto;
        margin-right: auto;
        left: 0;
        right: 0;
        text-align: center;
      "
    >
      <a class="a-logout" href="" @click="logout()">
        <v-icon>logout</v-icon><br /><span>Logout</span></a
      >
    </div>
  </v-navigation-drawer>
</template>

<script>
import utils from "@/utils/utils";
export default {
  data: () => ({
    localDomain: utils.constant.localDomain,
    loginUser: {},
    isLogin: false,
    selectedItem: 0,
    drawer: null,
    items: [
      { icon: "fastfood", text: "Food", route: "/admin/food" },
      { icon: "menu_book", text: "Category", route: "/admin/category" },
      { icon: "deck", text: "Table", route: "/admin/table" },
      { icon: "discount", text: "Discount", route: "/admin/discount" },
      { icon: "manage_accounts", text: "User", route: "/admin/user" },
    ],
  }),
  created() {
    // Login User
    this.loginUser = this.$store.state.loginUser;
    this.$store.watch(
      () => {
        return this.$store.state.loginUser;
      },
      (newVal) => {
        this.loginUser = newVal;
      },
      {
        deep: true,
      }
    );
    // IsLogin
    this.isLogin = this.$store.state.isLogin;
    this.$store.watch(
      () => {
        return this.$store.state.isLogin;
      },
      (newVal) => {
        this.isLogin = newVal;
      },
      {
        deep: true,
      }
    );
  },

  methods: {
    logout() {
      this.$store.commit("logout");
      // If current path is not register page, Go to register page
      if (this.$route.path != "/login") {
        this.$router.push({ path: "/login" });
        this.$router.go();
      }
    },
  },
};
</script>

<style>
.sidebar-nav-list {
  text-decoration: none;
}
.border {
  margin-left: 8px;
  margin-right: 8px;
  background: #800015;
  border-radius: 20px;
  text-decoration: none;
}
.v-list-item-group .v-list-item--active,
.sidebar-nav-list {
  color: #ffffff !important;
}
.theme--light.v-list-item--active .v-list-item__subtitle,
.theme--light.v-list-item .v-list-item__action-text {
  color: #ffffff !important;
}
.a-logout {
  text-decoration: none;
  font-family: "Ubuntu", sans-serif !important;
  font-weight: bolder;
  font-size: small;
}
</style>
