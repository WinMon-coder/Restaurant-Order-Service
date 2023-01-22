<template>
  <v-navigation-drawer
    v-model="drawer"
    app
    color="white"
    mini-variant
    mini-variant-width="90"
  >
    <v-avatar class="d-block text-center mx-auto mt-4" size="40">
      <v-icon color="primary">fas fa-shop</v-icon>
    </v-avatar>
    <v-list flat class="mt-4">
      <v-list-item-group v-model="selectedItem">
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

    <span v-for="(role, index) in this.loginUser.userRoles" :key="index">
      <div
        v-if="role.roleName == 'ADMIN'"
        style="
          bottom: 20px;
          margin-left: auto;
          margin-right: auto;
          left: 0;
          right: 0;
          text-align: center;
        "
      >
        <router-link class="a-logout" to="/admin/food">
          <v-icon>dashboard_customize</v-icon><br /><span>dashboard</span>
        </router-link>
      </div>
    </span>
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
      <a class="a-logout" @click="logout()">
        <v-icon>logout</v-icon><br />
        <span>Logout</span></a
      >
    </div>
  </v-navigation-drawer>
</template>

<script>
import utils from "@/utils/utils";
export default {
  data: () => ({
    loginUser: {},
    isLogin: false,
    selectedItem: 0,
    drawer: null,
    items: [
      { icon: "deck", text: "Table", route: "/table" },
      { icon: "list_alt", text: "Order", route: "/order" },
    ],
  }),

  async created() {
    // Login User
    this.loginUser = this.$store.getters.loginUser;
    await this.fetchUserProfile();
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
    async fetchUserProfile() {
      const response = await utils.http.get(
        "/user/profile?id=" + this.loginUser.userId
      );
      if (response) {
        const data = await response.json();
        if (data) this.loginUser = data;
      } else {
        console.log("sth wromg");
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

.a-logout {
  text-decoration: none;
  font-family: "Raleway", sans-serif !important;
  font-size: 14px !important;
}
.v-application a {
  color: gray !important;
}
</style>
