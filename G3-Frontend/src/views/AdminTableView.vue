<template>
  <div>
    <AdminSideBar />
    <v-data-table
      :headers="headers"
      :items="tableList"
      :items-per-page="10"
      class="elevation-1"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>Table</v-toolbar-title>
          <v-divider class="mx-4" inset vertical></v-divider>

          <!-- -------------****** create ******-------------------------- -->
          <v-dialog v-model="dialog" max-width="400px">
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                color="primary"
                class="mb-2 widthoutupercase white--text"
                v-bind="attrs"
                v-on="on"
              >
                Add Table
              </v-btn>
            </template>
            <v-card>
              <v-form ref="createTableForm" v-model="createTableForm">
                <v-card-title>
                  <h3>Add New Table</h3>
                </v-card-title>
                <v-card-text>
                  <v-container>
                    <v-row>
                      <v-col cols="12" sm="12" md="12">
                        <v-text-field
                          v-model="createDemo.tableName"
                          label="Table Name"
                          :rules="[
                            (v) => !!v || 'Required',
                            (v) =>
                              (v && v.length <= 2) ||
                              'Table name must be less than 2 characters',
                          ]"
                        ></v-text-field>
                      </v-col>
                    </v-row>
                  </v-container>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn class="mb-2 widthoutupercase" text @click="saveCancel"
                    >Cancle
                  </v-btn>
                  <v-btn
                    color="primary"
                    class="mb-2 widthoutupercase white--text"
                    @click="save"
                    :disabled="!createTableForm"
                  >
                    save
                  </v-btn>
                </v-card-actions>
              </v-form>
            </v-card>
          </v-dialog>
        </v-toolbar>
      </template>
      <template v-slot:[`item.actions`]="{ item }">
        <v-icon color="indigo" class="mr-2" @click="editItem(item)">
          mdi-pencil
        </v-icon>
        <v-icon color="error" @click="deleteItem(item)"> mdi-delete </v-icon>
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
      <v-form ref="createTableForm" v-model="createTableForm">
        <v-dialog v-model="update_dialog" max-width="400px">
          <v-card>
            <v-card-title>
              <h3>Edit Table</h3>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="12" md="12">
                    <v-text-field
                      v-model="updateDemo.tableName"
                      label="Table Name"
                      :rules="[
                        (v) => !!v || 'Required',
                        (v) =>
                          (v && v.length <= 2) ||
                          'Table name must be less than 2 characters',
                      ]"
                    ></v-text-field>
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
                color="primary"
                class="mb-2 widthoutupercase white--text"
                @click="updateItemConfirm(updateDemo.tableId)"
                :disabled="!createTableForm"
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
              <v-btn class="mb-2 widthoutupercase" text @click="closeDelete"
                >Cancel</v-btn
              >
              <v-btn
                color="primary"
                class="mb-2 widthoutupercase white--text"
                @click="deleteItemConfirm(deleteDemo.tableId)"
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
import swal from "sweetalert2";
window.swal = swal;
import utils from "@/utils/utils";
import AdminSideBar from "@/components/AdminSideBar.vue";
export default {
  components: {
    AdminSideBar,
  },
  data: () => ({
    createTableForm: false,
    dialog: false,
    update_dialog: false,
    delete_dialog: false,
    loading: false,
    errorAlert: false,
    deleteDemo: {},
    updateDemo: {},
    createDemo: {
      tableName: "",
    },
    tableList: [],
    headers: [
      { text: "Table Name", value: "tableName" },
      { text: "Created Date", value: "createdAt" },
      { text: "Modified Date", value: "updatedAt" },
      { text: "Actions", value: "actions", sortable: false },
    ],
  }),

  async created() {
    await this.fetchAllTables();
  },
  methods: {
    // <!-- -------------****** get all table from utils.http ******-------------------------- -->
    async fetchAllTables() {
      const response = await utils.http.get("/table/list");
      if (response) {
        const data = await response.json();
        if (data) this.tableList = data;
      } else {
        console.log("sth wromg");
      }
    },
    // <!-- -------------****** save table from utils.http ******-------------------------- -->
    async save() {
      if (this.$refs.createTableForm.validate()) {
        let tableNameCheckByOK = false;

        const respTableNameCheck = await utils.http.get(
          "/table/tableName/" + this.createDemo.tableName.trim()
        );
        if (respTableNameCheck && respTableNameCheck.status === 200) {
          const data = await respTableNameCheck.json();
          if (data && data == true) {
            swal.fire({
              icon: "error",
              title: "Sorry...",
              text: "Table name already exists!",
            });
          } else {
            tableNameCheckByOK = true;
          }
        }
        if (tableNameCheckByOK) {
          const resp = await utils.http.post("/admin/table/create", {
            tableName: this.createDemo.tableName,
          });

          if (resp) {
            await this.fetchAllTables();
            this.dialog = false;
            this.createDemo = {};
          } else {
            this.errorAlert = true;
          }
        }
      }
    },

    // <!-- -------------****** update table from utils.http ******-------------------------- -->
    async updateItemConfirm(id) {
      const resp = await utils.http.put("/admin/table/update/" + id, {
        tableName: this.updateDemo.tableName,
      });
      if (resp && resp.status === 200) {
        await this.fetchAllTables();
        this.update_dialog = false;
      } else {
        swal.fire({
          icon: "error",
          title: "Sorry...",
          text: "Table name already exists!",
        });
      }
    },
    // <!-- -------------****** delete table from utils.http ******-------------------------- -->
    async deleteItemConfirm(id) {
      const resp = await utils.http.del("/admin/table/delete/" + id);
      if (resp.status == 200) {
        await this.fetchAllTables();
        this.delete_dialog = false;
      }
    },
    // <!-- -------------******  event handelar from dialog ******-------------------------- -->

    deleteItem(item) {
      this.delete_dialog = true;
      this.deleteDemo = item;
    },
    editItem(item) {
      this.update_dialog = true;
      this.updateDemo = Object.assign({}, item);
      this.tableName = this.updateDemo.tableName;
    },
    closeUpdate() {
      this.update_dialog = false;
    },
    closeDelete() {
      this.delete_dialog = false;
    },
    saveCancel() {
      this.dialog = false;
      this.createDemo = {};
    },
  },
};
</script>

<style>
.v-btn:hover {
  background-color: primary;
  color: white !important;
}
</style>
