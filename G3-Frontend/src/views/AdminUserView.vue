<template>
  <div>
    <AdminSideBar />
    <v-data-table
      :headers="headers"
      :items="userList"
      :items-per-page="5"
      class="elevation-1"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>User</v-toolbar-title>
        </v-toolbar>
      </template>
      <template v-slot:[`item.actions`]="{ item }">
        <v-icon
          color="indigo"
          class="mr-2"
          @click="onClickUpdateUserStatus(item)"
        >
          mdi-pencil
        </v-icon>
        <v-icon color="error darken-1" @click="deleteItem(item)">
          mdi-delete
        </v-icon>
      </template>
      <template v-slot:[`item.createdAt`]="{ item }">
        {{ item.createdAt | formatLocalDate }}
      </template>
      <template v-slot:[`item.updatedAt`]="{ item }">
        {{ item.updatedAt | formatLocalDate }}
      </template>
    </v-data-table>
    <v-toolbar>
      <!-- -------------****** update ******-------------------------- -->
      <v-form ref="updateForm" v-model="updateForm">
        <v-dialog v-model="update_dialog" max-width="700px">
          <v-card>
            <v-card-title>
              <h3>Edit User Status</h3>
            </v-card-title>
            <v-card-text>
              <v-container>
                <!-- User Name -->
                <v-row>
                  <v-col cols="12" md="6">
                    <v-text-field
                      v-model="updateDemo.userFullName"
                      label="User Name"
                      disabled
                    ></v-text-field>
                  </v-col>

                  <!-- User Email -->

                  <v-col cols="12" md="6">
                    <v-text-field
                      v-model="updateDemo.userEmail"
                      label="User Email"
                      disabled
                    ></v-text-field>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col>
                    <v-select
                      :items="userStatusList"
                      label="User Status"
                      v-model="updateDemo.userStatus"
                    >
                    </v-select>
                  </v-col>
                </v-row>

                <!-- Start Join Date -->
                <!-- Last Join Date -->
                <v-row>
                  <v-col>
                    <h3>Start Join Date</h3>
                    <template>
                      <v-date-picker
                        v-model="updateDemo.startJoinDate"
                      ></v-date-picker>
                    </template>
                  </v-col>
                  <v-col>
                    <h3>Last Join Date</h3>
                    <template>
                      <v-date-picker
                        v-model="updateDemo.lastJoinDate"
                      ></v-date-picker>
                    </template>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn class="mb-2 widthoutupercase" text @click="closeUpdate"
                >Cancel</v-btn
              >
              <v-btn
                :disabled="!updateForm"
                color="primary"
                class="mb-2 widthoutupercase white--text"
                @click="updateUserStatus()"
                >Update</v-btn
              >
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-form>
      <!-- -------------****** delete ******-------------------------- -->
      <v-container>
        <v-dialog v-model="delete_dialog" max-width="400px">
          <v-card>
            <v-card-title
              ><span>Are you sure you want to delete this?</span></v-card-title
            >

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn class="mb-2 widthoutupercase t" text @click="closeDelete"
                >Cancel</v-btn
              >
              <v-btn
                color="primary"
                class="mb-2 widthoutupercase white--text"
                @click="deleteItemConfirm(selectDemo.userId)"
                >Delete</v-btn
              >
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-container>
    </v-toolbar>
  </div>
</template>

<script>
import utils from "@/utils/utils";
import AdminSideBar from "@/components/AdminSideBar.vue";
export default {
  components: {
    AdminSideBar,
  },
  data: () => ({
    update_dialog: false,
    delete_dialog: false,
    updateForm: false,
    selectDemo: {},
    updateDemo: {
      userId: "",
      userStatus: "",
      startJoinDate: "",
      lastJoinDate: "",
    },
    headers: [
      { text: "Name", value: "userFullName" },
      { text: "Email", value: "userEmail" },
      { text: "Status", value: "userStatus" },
      { text: "Phone", value: "userPhone" },
      { text: "Address", value: "userAddress" },
      { text: "Start Join Date", value: "startJoinDate" },
      { text: "Last Join Date", value: "lastJoinDate" },
      { text: "Modified Date", value: "updatedAt", sortable: true },
      { text: "Actions", value: "actions", sortable: false },
    ],
    userList: [],
    userStatus: "",
    userStatusList: [],
  }),

  async created() {
    await this.fetchUserStatus();
    await this.fetchAllUsers();
  },
  methods: {
    async fetchUserStatus() {
      const resp = await utils.http.get("/admin/user_status");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.userStatusList = data;
        }
      }
    },
    // <!-- -------------****** get all users from utils.http ******-------------------------- -->
    async fetchAllUsers() {
      const resp = await utils.http.get("/admin/user/list");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.userList = data;
        }
      }
    },

    // <!-- -------------****** update user from utils.http ******-------------------------- -->
    async updateUserStatus(id) {
      const resp = await utils.http.put("/admin/update/user_status/" + id, {
        userId: this.updateDemo.userId,
        userStatus: this.updateDemo.userStatus,
        startJoinDate: this.updateDemo.startJoinDate,
        lastJoinDate: this.updateDemo.lastJoinDate,
      });

      console.log(this.userStatus);
      console.log(this.userId);
      if (resp && resp.status === 200) {
        // Refresh User List
        await this.fetchAllUsers();
        this.update_dialog = false;
      } else {
        this.errorAlert = true;
      }

      this.loading = false;
    },

    // <!-- -------------****** delete user from utils.http ******-------------------------- -->
    async deleteItemConfirm(id) {
      const resp = await utils.http.del("/admin/user/delete/" + id);
      if (resp.status == 200) {
        await this.fetchAllUsers();
        this.delete_dialog = false;
      }
    },
    // <!-- -------------******  event handelar from dialog ******-------------------------- -->

    deleteItem(item) {
      this.delete_dialog = true;
      this.selectDemo = item;
    },
    onClickUpdateUserStatus(item) {
      this.update_dialog = true;
      this.updateDemo = Object.assign({}, item);
      this.userStatus = this.updateDemo.userStatus;
      this.userId = this.updateDemo.userId;
      console.log(this.userStatus);
      console.log(this.userId);
    },
    closeUpdate() {
      this.update_dialog = false;
    },
    closeDelete() {
      this.delete_dialog = false;
    },
  },
};
</script>

<style>
.v-btn:hover {
  background-color: brown;
  color: white !important;
}
</style>
