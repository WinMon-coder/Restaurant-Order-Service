<template>
  <div>
    <SideBar />
    <!-- Profile View -->
    <v-container class="mt-2 pt-6 pb-6">
      <v-row>
        <v-spacer></v-spacer>
        <v-col cols="12" md="5">
          <v-card elevation="1">
            <v-toolbar color="#f6efef">
              <strong>Profile</strong>
              <v-spacer></v-spacer>
              <v-btn icon class="mr-4" @click="editItem(loginUser)">
                <v-icon>manage_accounts </v-icon>
              </v-btn>
              <v-btn icon @click.prevent="changePwd()">
                <v-icon> password </v-icon>
              </v-btn></v-toolbar
            >
            <v-img
              v-if="loginUser.userPhoto != null"
              :src="localDomain + loginUser.userPhoto"
              height="250px"
              dark
              flat
            >
            </v-img>
            <v-img
              v-else
              height="250px"
              dark
              flat
              src="../assets/profile3.jpeg"
            />

            <v-list two-line>
              <v-list-item>
                <v-list-item-icon>
                  <v-icon color="primary"> fa fa-user </v-icon>
                </v-list-item-icon>

                <v-list-item-content>
                  <v-list-item-subtitle>Name</v-list-item-subtitle>
                  <v-list-item-title
                    ><strong>{{
                      loginUser.userFullName
                    }}</strong></v-list-item-title
                  >
                </v-list-item-content>
              </v-list-item>

              <v-divider inset></v-divider>
              <v-list-item>
                <v-list-item-icon>
                  <v-icon color="primary"> mdi-phone </v-icon>
                </v-list-item-icon>

                <v-list-item-content>
                  <v-list-item-subtitle>Mobile</v-list-item-subtitle>
                  <v-list-item-title
                    ><strong>{{
                      loginUser.userPhone
                    }}</strong></v-list-item-title
                  >
                </v-list-item-content>
              </v-list-item>

              <v-divider inset></v-divider>

              <v-list-item>
                <v-list-item-icon>
                  <v-icon color="primary"> mdi-email </v-icon>
                </v-list-item-icon>

                <v-list-item-content>
                  <v-list-item-subtitle>Email</v-list-item-subtitle>
                  <v-list-item-title
                    ><strong>{{
                      loginUser.userEmail
                    }}</strong></v-list-item-title
                  >
                </v-list-item-content>
              </v-list-item>
              <v-divider inset></v-divider>

              <v-list-item>
                <v-list-item-icon>
                  <v-icon color="primary"> fa fa-map-location</v-icon>
                </v-list-item-icon>

                <v-list-item-content>
                  <v-list-item-subtitle>Address</v-list-item-subtitle>
                  <v-list-item-title>
                    <strong>{{
                      loginUser.userAddress
                    }}</strong></v-list-item-title
                  >
                </v-list-item-content>
              </v-list-item>
              <v-divider inset></v-divider>
              <v-list-item>
                <v-list-item-icon>
                  <v-icon color="primary"> fa fa-calendar </v-icon>
                </v-list-item-icon>

                <v-list-item-content>
                  <v-list-item-subtitle>Join Date</v-list-item-subtitle>
                  <v-list-item-title>
                    <strong>{{
                      loginUser.startJoinDate
                    }}</strong></v-list-item-title
                  >
                </v-list-item-content>
              </v-list-item>
              <v-list-item>
                <v-btn @click.prevent="backTo()" class="ma-2" color="primary ">
                  <v-icon left> mdi-arrow-left </v-icon>Back
                </v-btn>
              </v-list-item>
            </v-list>
          </v-card>
        </v-col>
        <v-spacer></v-spacer>
      </v-row>
    </v-container>

    <!-- ------------------------------------------------------------------------------------------------------ -->
    <!-- Update Dialog Box-->
    <v-dialog v-model="update_dialog" max-width="500px">
      <v-card>
        <v-card-title>
          <h3>Edit User Profile</h3>
        </v-card-title>
        <v-card-text>
          <v-container>
            <!-- User Name -->
            <v-row>
              <v-col cols="12" sm="6" md="6">
                <v-text-field
                  v-model="updateDemo.userFullName"
                  :rules="[
                    (v) => !!v || 'Required',
                    (v) =>
                      (v && v.length <= 50) ||
                      'Name must be less than 50 characters',
                  ]"
                  label="Name"
                ></v-text-field>
              </v-col>
              <!-- User Role -->
              <v-col cols="12" sm="6" md="6">
                <div
                  v-for="(role, index) in this.loginUser.userRoles"
                  :key="index"
                >
                  <v-text-field
                    label="UserRole"
                    v-model="role.roleName"
                    disabled
                  >
                  </v-text-field>
                </div>
              </v-col>
            </v-row>

            <!-- User Email -->
            <v-row>
              <v-col cols="12" sm="6" md="6">
                <v-text-field
                  label="Email"
                  v-model="updateDemo.userEmail"
                  :rules="[
                    (v) => !!v || 'Required',
                    (v) => /.+@.+\..+/.test(v) || 'E-mail must be valid',
                  ]"
                  type="text"
                  required
                />
              </v-col>
              <!-- User Photo -->
              <v-col cols="12" sm="6" md="6">
                <v-text-field
                  v-model="updateDemo.userPhone"
                  :rules="[
                    (v) => !!v || 'Required',
                    (v) =>
                      (v && v.length <= 11) ||
                      'Phone number must be less than 11 characters',
                  ]"
                  label="Phone no."
                ></v-text-field>
              </v-col>
            </v-row>

            <!-- User Address -->
            <v-row>
              <v-col cols="12" sm="6" md="6">
                <v-text-field
                  v-model="updateDemo.userAddress"
                  :rules="[
                    (v) => !!v || 'Required',
                    (v) =>
                      (v && v.length <= 50) ||
                      'Address must be less than 50 characters',
                  ]"
                  label="Address"
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="6">
                <v-file-input
                  v-model="updateDemo.poster"
                  label="Photo"
                  show-size
                  prepend-icon="mdi-camera"
                  placeholder="Choose Poster Image"
                  accept="image/png, image/jpeg"
                  :rules="[
                    (v) => !!v || 'Required',
                    (v) =>
                      !v ||
                      v.size < 10000000 ||
                      'Image size should be less than 10 MB!',
                  ]"
                  @change="onChangePoster"
                ></v-file-input>
              </v-col>
            </v-row>

            <!-- -------------****** poster start ******-------------------------- -->

            <!-- Poster is not selected (null) -->
            <v-row>
              <v-img
                v-if="posterPreviewPath == null"
                :src="localDomain + updateDemo.userPhoto"
                width="200"
                height="150"
                contain
              >
              </v-img>
              <!-- Poster is selected (not null) -->
              <v-img
                v-if="posterPreviewPath != null"
                :src="posterPreviewPath"
                width="200"
                height="150"
                contain
              >
              </v-img>
            </v-row>
            <!-- -------------****** poster end ******-------------------------- -->
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn class="mb-2 widthoutupercase" text @click="closeUpdate"
            >Cancel</v-btn
          >
          <v-btn
            color="primary"
            class="mb-2 widthoutupercase white--text"
            @click="updateUserProfile(loginUser.userId)"
          >
            Update</v-btn
          >
        </v-card-actions>
        <v-alert class="mt-3" v-show="errorAlert" dense type="error">
          Update Fail!!
        </v-alert>
      </v-card>
    </v-dialog>
    <!-- ------------------------------------------------------------------------------------------------------ -->
    <!-- Change Password Dialog Box-->
    <v-dialog v-model="changePw_dialog" max-width="500px">
      <v-card>
        <v-card-title>
          <h3>Change Password</h3>
        </v-card-title>
        <v-card-text>
          <v-container>
            <!-- Current Password -->
            <v-text-field v-show="false" v-model="ori_id" hidden></v-text-field>
            <v-row>
              <v-col>
                <v-text-field
                  v-model="current_pwd"
                  :rules="[(v) => !!v || 'Required']"
                  label="Current Password"
                  required
                >
                </v-text-field>
              </v-col>
            </v-row>

            <!-- New Password -->
            <v-row>
              <v-col>
                <v-text-field
                  v-model="new_pwd"
                  :rules="[
                    (v) => !!v || 'Required',
                    (v) =>
                      (v && v.length >= 3) ||
                      'Password must be greater than 3 characters',
                    (v) =>
                      (v && v.length >= 3 && v.length <= 10) ||
                      'Password must be less than 10 characters',
                  ]"
                  label="New Password"
                  required
                >
                </v-text-field>
              </v-col>
            </v-row>

            <!-- Confirm New Password -->
            <v-row>
              <v-col>
                <v-text-field
                  v-model="con_new_pwd"
                  :rules="[
                    (v) => !!v || 'Required',
                    (v) =>
                      (v && v.length >= 3) ||
                      'Password must be greater than 3 characters',
                    (v) =>
                      (v && v.length >= 3 && v.length <= 10) ||
                      'Password must be less than 10 characters',
                  ]"
                  label="Confirm New Password"
                  required
                >
                </v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-alert class="mt-3" v-show="errorAlert" dense type="error">
          {{ errMsg }}
        </v-alert>
        <v-card-actions>
          <v-spacer></v-spacer>

          <v-btn class="mb-2 widthoutupercase" text @click="clear">Clear</v-btn>
          <v-btn
            color="primary"
            class="mb-2 widthoutupercase white--text"
            @click="submit()"
          >
            Submit</v-btn
          >
          <!-- Error Alert -->
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import swal from "sweetalert2";
window.swal = swal;
import utils from "@/utils/utils";
import SideBar from "@/components/SideBar.vue";
export default {
  components: {
    SideBar,
  },
  data() {
    return {
      loginUser: {},
      localDomain: utils.constant.localDomain,
      updateDemo: {},
      dialog: false,
      changePw_dialog: false,
      update_dialog: false,
      poster: null,
      posterPreviewPath: null,
      errorAlert: false,
      changePasswordForm: false,
      show: false,
      con_new_pwd: "",
      new_pwd: "",
      ori_id: "",
      current_pwd: "",
      errMsg: "",
    };
  },

  async created() {
    this.loginUser = this.$store.getters.loginUser;
    await this.fetchUserProfile();
  },

  methods: {
    backTo() {
      this.$router.go(-1);
    },
    // Methods for Profile Update
    editItem(loginUser) {
      this.update_dialog = true;
      this.updateDemo = loginUser;
      this.poster = this.updateDemo.poster;
      this.posterPreviewPath = this.updateDemo.posterPreviewPath;
    },

    closeUpdate() {
      this.update_dialog = false;
    },

    onChangePoster(poster) {
      this.posterPreviewPath = URL.createObjectURL(poster);
    },

    async updateUserProfile(id) {
      this.errorAlert = false;

      // Step 1 -> Update Poster
      // Null -> Poster is not selected
      if (this.updateDemo.poster != null) {
        // Update Poster utils.http
        const respPosterData = await utils.http.putMedia(
          "/admin/file/update",
          this.updateDemo.poster,
          this.updateDemo.poster.type,
          this.updateDemo.userPhoto
        );

        if (respPosterData && respPosterData.status === 200) {
          this.updateDemo.userPhoto = await respPosterData.text();
        } else {
          console.debug("Poster Update Failed");
        }
      }

      const resp = await utils.http.put("/user/profile/update/" + id, {
        userId: this.updateDemo.userId,
        userFullName: this.updateDemo.userFullName,
        userEmail: this.updateDemo.userEmail,
        userPhone: this.updateDemo.userPhone,
        userAddress: this.updateDemo.userAddress,
        userPhoto: this.updateDemo.userPhoto,
      });

      if (resp && resp.status === 200) {
        this.update_dialog = false;
        this.fetchUserProfile();
        swal.fire({
          position: "top-end",
          icon: "success",
          title: "Update Profile Successfully",
          showConfirmButton: false,
          timer: 1500,
        });
      }
    },

    // Methods For Change Password
    changePwd() {
      this.changePw_dialog = true;
    },

    async submit() {
      const resp = await utils.http.post("/user/change_password", {
        oriId: this.loginUser.userId,
        currentPwd: this.current_pwd,
        newPwd: this.new_pwd,
        conNewPwd: this.con_new_pwd,
      });
      if (resp.status === 200) {
        this.$router.push({ path: "/login" });
      } else {
        swal.fire({
          icon: "error",
          title: "Oops...",
          text: "Something went wrong!",
        });
        const data = await resp.json();
        this.errMsg = data.message;
      }
    },
    clear() {
      this.current_pwd = "";
      this.new_pwd = "";
      this.con_new_pwd = "";
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

<style></style>
