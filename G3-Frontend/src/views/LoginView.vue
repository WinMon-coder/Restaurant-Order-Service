<template>
  <v-app id="inspire">
    <v-container class="fill-height" fluid>
      <v-row align="center" justify="center">
        <v-col cols="12" sm="8" md="6">
          <v-card class="elevation-12">
            <v-window v-model="step">
              <v-window-item :value="1">
                <v-row>
                  <v-col cols="12" md="7">
                    <v-card-text class="mt-14">
                      <h2 class="text-center black--text text--accent-3">
                        Welcome Back!
                      </h2>
                      <br />
                      <br />
                      <!-- <div class="text-center mt-4">
                        <v-btn class="mx-2" fab color="black" outlined>
                          <v-icon>fab fa-facebook-f</v-icon>
                        </v-btn>

                        <v-btn class="mx-2" fab color="black" outlined>
                          <v-icon>fab fa-google-plus-g</v-icon>
                        </v-btn>
                        <v-btn class="mx-2" fab color="black" outlined>
                          <v-icon>fab fa-linkedin-in</v-icon>
                        </v-btn>
                      </div> -->
                      <!-- <h4 class="text-center mt-4">Have a nice day</h4> -->
                      <v-form ref="loginForm" v-model="loginForm">
                        <v-text-field
                          label="Email"
                          v-model="userEmail"
                          :rules="[
                            (v) => !!v || 'Required',
                            (v) =>
                              /.+@.+\..+/.test(v) || 'E-mail must be valid',
                          ]"
                          prepend-icon="email"
                          type="text"
                          required
                        />

                        <v-text-field
                          v-model="userPassword"
                          :counter="10"
                          :rules="[
                            (v) => !!v || 'Required',
                            (v) =>
                              (v && v.length <= 10) ||
                              'Password must be less than 10 characters',
                          ]"
                          prepend-icon="lock"
                          label="Password"
                          required
                          color="#800015"
                          :type="passwordShow ? 'text' : 'password'"
                          :append-icon="
                            passwordShow ? 'mdi-eye' : 'mdi-eye-off'
                          "
                          @click:append="passwordShow = !passwordShow"
                        />
                        <!-- <h3 class="text-center mt-4">Forgot your password ?</h3> -->
                        <div class="text-center mt-3">
                          <v-btn
                            color="#800015"
                            dark
                            :disabled="!loginForm"
                            @click="login"
                          >
                            <span v-if="!loading">LOGIN</span>
                            <v-progress-circular
                              v-else
                              indeterminate
                              color="white"
                            ></v-progress-circular>
                          </v-btn>
                          <v-alert
                            class="mt-3"
                            v-show="errorAlert"
                            dense
                            type="error"
                          >
                            Login Failed! <br />
                            Email or Password is wrong!
                          </v-alert>
                        </div>
                        <br />
                      </v-form>
                    </v-card-text>
                  </v-col>
                  <v-col cols="12" md="5" class="primary">
                    <v-card-text class="white--text mt-12">
                      <h2 class="text-center"></h2>
                      <br />
                      <h5 class="text-center"></h5>
                      <br />
                      <br />
                    </v-card-text>
                    <div class="text-center">
                      <v-btn outlined dark @click="step++">SIGN UP</v-btn>
                    </div>
                  </v-col>
                </v-row>
              </v-window-item>
              <v-window-item :value="2">
                <v-row class="fill-height">
                  <v-col cols="12" md="5" class="primary">
                    <v-card-text class="white--text mt-12">
                      <h2 class="text-center">Welcome Back!</h2>
                      <br />
                      <!-- <h5 class="text-center">
                        To Keep connected with us please login with your
                        personnel info
                      </h5> -->
                    </v-card-text>
                    <div class="text-center">
                      <v-btn outlined dark @click="step--">Sign in</v-btn>
                    </div>
                  </v-col>

                  <v-col cols="12" md="7">
                    <v-card-text class="mt-12">
                      <h2 class="text-center black--text text--accent-3">
                        Create Account
                      </h2>
                      <!-- <div class="text-center mt-4">
                        <v-btn class="mx-2" fab color="black" outlined>
                          <v-icon>fab fa-facebook-f</v-icon>
                        </v-btn>

                        <v-btn class="mx-2" fab color="black" outlined>
                          <v-icon>fab fa-google-plus-g</v-icon>
                        </v-btn>
                        <v-btn class="mx-2" fab color="black" outlined>
                          <v-icon>fab fa-linkedin-in</v-icon>
                        </v-btn>
                      </div> -->
                      <!-- <h4 class="text-center mt-4">
                        Ensure your email for registration
                      </h4> -->
                      <v-form ref="registerForm" v-model="registerForm">
                        <v-text-field
                          label="Name"
                          v-model="userFullName"
                          :counter="10"
                          :rules="[
                            (v) => !!v || 'Required',
                            (v) =>
                              (v && v.length <= 10) ||
                              'Name must be less than 10 characters',
                          ]"
                          prepend-icon="person"
                          type="text"
                          required
                          color="#800015"
                        />
                        <v-text-field
                          label="Email"
                          v-model="userEmail"
                          :rules="[
                            (v) => !!v || 'Required',
                            (v) =>
                              /.+@.+\..+/.test(v) || 'E-mail must be valid',
                          ]"
                          prepend-icon="email"
                          type="text"
                          required
                          color="#800015"
                        />

                        <v-text-field
                          v-model="userPassword"
                          :counter="10"
                          :rules="[
                            (v) => !!v || 'Required',
                            (v) =>
                              (v && v.length >= 3) ||
                              'Password must be greater than 3 characters',
                            (v) =>
                              (v && v.length >= 3 && v.length <= 10) ||
                              'Password must be less than 10 characters',
                          ]"
                          prepend-icon="lock"
                          label="Password"
                          required
                          color="#800015"
                          :type="passwordShow ? 'text' : 'password'"
                          :append-icon="
                            passwordShow ? 'mdi-eye' : 'mdi-eye-off'
                          "
                          @click:append="passwordShow = !passwordShow"
                        />
                        <div class="text-center mt-5">
                          <v-btn @click="register" color="#800015" dark>
                            <span v-if="!loading">SIGN UP</span>
                            <v-progress-circular
                              v-else
                              indeterminate
                              color="white"
                            ></v-progress-circular
                          ></v-btn>
                          <v-alert
                            class="mt-3"
                            v-show="errorAlert"
                            dense
                            type="error"
                          >
                            Register Failed!
                          </v-alert>
                        </div>
                        <br />
                      </v-form>
                    </v-card-text>
                  </v-col>
                </v-row>
              </v-window-item>
            </v-window>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-app>
</template>

<script>
import utils from "@/utils/utils";
export default {
  name: "LoginView",
  props: {
    source: String,
  },
  data() {
    return {
      step: 1,
      loginForm: false,
      registerForm: false,
      passwordShow: false,
      userFullName: "",
      userEmail: "",
      userPassword: "",
      errorAlert: false,
      loading: false,
    };
  },

  async created() {},

  methods: {
    async register() {
      if (this.$refs.registerForm.validate()) {
        this.errorAlert = false;
        this.loading = true;
        const resp = await utils.http.post("/user/register", {
          userFullName: this.userFullName,
          userEmail: this.userEmail,
          userPassword: this.userPassword,
        });
        if (resp.status === 200) {
          const data = await resp.json();
          if (data) {
            this.$store.commit("setLoginUser", data);
            if (data.userRoles[0].roleName == "ADMIN") {
              this.$router.push({ path: "/admin/food" });
            } else {
              this.$router.push({ path: "/table" });
            }
          }
        } else {
          this.errorAlert = true;
        }
        this.loading = false;
      }
    },
    async login() {
      if (this.$refs.loginForm.validate()) {
        this.errorAlert = false;
        this.loading = true;
        const resp = await utils.http.post("/user/login", {
          userEmail: this.userEmail,
          userPassword: this.userPassword,
        });
        if (resp.status === 200) {
          const data = await resp.json();
          if (data) {
            this.$store.commit("setLoginUser", data);
            //console.log(data.userRoles[0].roleName);
            // if (data.userRoles[0].roleName == "ADMIN") {
            //   this.$router.push({ path: "/table" });
            // } else {
            //   this.$router.push({ path: "/table" });
            // }
            this.$router.push({ path: "/table" });
          }
        } else {
          this.errorAlert = true;
        }
        this.loading = false;
      }
    },
  },
};
</script>
