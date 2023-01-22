<template>
  <div>
    <AdminSideBar />
    <template>
      <v-card>
        <v-card-title>
          <v-dialog v-model="discount_dialog" max-width="400px">
            <template v-slot:activator="{ on, attrs }">
              <v-row class="ml-1 mt-1">
                <v-btn
                  class="mr-2"
                  v-bind="attrs"
                  v-on="on"
                  tile
                  color="#F6EFEF"
                >
                  <v-icon left> discount </v-icon>
                  Set Discount
                </v-btn>
                <v-btn @click="removeDiscount()" tile color="#F6EFEF">
                  <v-icon left> delete </v-icon>
                  Remove Discount
                </v-btn>
              </v-row>
            </template>
            <v-card>
              <v-card-title>
                <h3>Set Discount</h3>
              </v-card-title>
              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col cols="12" sm="6" md="12">
                      <!-- Category -->

                      <v-select
                        v-model="chooseDiscountId"
                        :items="discountList"
                        item-text="discountName"
                        item-value="discountId"
                        :rules="[(v) => !!v || 'Required']"
                        label="Discount"
                        required
                      >
                      </v-select>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>

                <v-btn
                  class="mb-2 widthoutupercase"
                  text
                  @click="closeDiscount()"
                  >Cancle
                </v-btn>
                <v-btn
                  color="primary"
                  class="mb-2 widthoutupercase white--text"
                  @click.prevent="updateDiscountStatus(chooseDiscountId)"
                >
                  Confirm
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-card-title>

        <v-card-subtitle class="mt-3" v-if="discount.isActive == true">
          <strong
            >Today, We set
            <strong class="primary--text">
              " {{ discount.discountName }} - {{ discount.discountPercentage }}%
              with Mininum Amount - {{ discount.mininumOrderAmount }} Ks "
            </strong>
            Discount!</strong
          >
        </v-card-subtitle>
        <v-card-subtitle class="mt-3" v-else>
          <strong>Today, We don't have Discount!</strong>
        </v-card-subtitle>
        <v-data-table
          :headers="headers"
          :items="discountList"
          :items-per-page="10"
          class="elevation-1"
        >
          <template v-slot:top>
            <v-toolbar flat>
              <v-toolbar-title>Discount</v-toolbar-title>
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
                    Add Discount
                  </v-btn>
                </template>
                <v-card>
                  <v-form ref="createDiscountForm" v-model="createDiscountForm">
                    <v-card-title>
                      <h3>Add New Discount</h3>
                    </v-card-title>
                    <v-card-text>
                      <v-container>
                        <v-row>
                          <v-col cols="12" sm="12" md="12">
                            <v-text-field
                              v-model="createDemo.discountName"
                              label="Discount Name"
                              :rules="[
                                (v) => !!v || 'Required',
                                (v) =>
                                  (v && v.length <= 20) ||
                                  'Discount name must be less than 20 characters',
                              ]"
                            ></v-text-field>
                          </v-col>
                        </v-row>
                        <v-row>
                          <v-col class="d-flex" cols="12" sm="6">
                            <v-text-field
                              v-model="createDemo.mininumOrderAmount"
                              type="number"
                              :rules="[
                                (v) => !!v || 'Required',
                                (v) =>
                                  (v && v > 0) ||
                                  'Mininum Amount must be greater than 0 ',
                              ]"
                              label="Mininum Amount"
                              required
                            ></v-text-field>
                          </v-col>
                          <v-col class="d-flex" cols="12" sm="6">
                            <v-text-field
                              v-model="createDemo.discountPercentage"
                              type="number"
                              suffix="%"
                              :rules="[
                                (v) => !!v || 'Required',
                                (v) =>
                                  (v && v > 0) ||
                                  'Percentage must be greater than 0 %',
                              ]"
                              label="Percentage"
                              required
                            ></v-text-field>
                          </v-col>
                        </v-row>
                      </v-container>
                    </v-card-text>
                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <v-btn
                        class="mb-2 widthoutupercase"
                        text
                        @click="saveCancel"
                        >Cancle
                      </v-btn>
                      <v-btn
                        color="primary"
                        class="mb-2 widthoutupercase white--text"
                        @click="save"
                        :disabled="!createDiscountForm"
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
            <v-icon color="error" @click="deleteItem(item)">
              mdi-delete
            </v-icon>
          </template>
          <template v-slot:[`item.isActive`]="{ item }">
            <v-chip small label :color="getColor(item.isActive)">
              <h3>{{ item.isActive == true ? "ON" : "OFF" }}</h3>
            </v-chip>
          </template>
          <template v-slot:[`item.createdAt`]="{ item }">
            {{ item.createdAt | formatLocalDate }}
          </template>
          <template v-slot:[`item.updatedAt`]="{ item }">
            {{ item.updatedAt | formatLocalDate }}
          </template>
        </v-data-table>
      </v-card>

      <v-toolbar>
        <!-- -------------****** update ******-------------------------- -->
        <v-form ref="createDiscountForm" v-model="createDiscountForm">
          <v-dialog v-model="update_dialog" max-width="400px">
            <v-card>
              <v-card-title>
                <h3>Edit Discount</h3>
              </v-card-title>
              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col cols="12" sm="12" md="12">
                      <v-text-field
                        v-model="updateDemo.discountName"
                        label="Discount Name"
                        :rules="[
                          (v) => !!v || 'Required',
                          (v) =>
                            (v && v.length <= 20) ||
                            'Discount name must be less than 20 characters',
                        ]"
                      ></v-text-field>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col class="d-flex" cols="12" sm="6">
                      <v-text-field
                        v-model="updateDemo.mininumOrderAmount"
                        type="number"
                        :rules="[
                          (v) => !!v || 'Required',
                          (v) =>
                            (v && v > 0) ||
                            'Mininum Amount must be greater than 0 %',
                        ]"
                        label="Mininum Amount"
                        required
                      ></v-text-field>
                    </v-col>
                    <v-col class="d-flex" cols="12" sm="6">
                      <v-text-field
                        v-model="updateDemo.discountPercentage"
                        type="number"
                        suffix="%"
                        :rules="[
                          (v) => !!v || 'Required',
                          (v) =>
                            (v && v > 0) ||
                            'Percentage must be greater than 0 %',
                        ]"
                        label="Percentage"
                        required
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
                  @click="updateItemConfirm(updateDemo.discountId)"
                  :disabled="!createDiscountForm"
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
                ><span
                  >Are you sure you want to delete this?</span
                ></v-card-title
              >

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn class="mb-2 widthoutupercase" text @click="closeDelete"
                  >Cancel</v-btn
                >
                <v-btn
                  color="primary"
                  class="mb-2 widthoutupercase white--text"
                  @click="deleteItemConfirm(deleteDemo.discountId)"
                  >Delete</v-btn
                >
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-container>
      </v-toolbar>
    </template>
  </div>
</template>

<script>
import swal from "sweetalert2";
window.swal = swal;
import utils from "@/utils/utils";
import AdminSideBar from "@/components/AdminSideBar.vue";
import { mapState } from "vuex";
export default {
  components: {
    AdminSideBar,
  },
  data: () => ({
    createDiscountForm: false,
    dialog: false,
    discount_dialog: false,
    update_dialog: false,
    delete_dialog: false,
    loading: false,
    errorAlert: false,
    chooseDiscountId: "",
    deleteDemo: {},
    updateDemo: {},
    createDemo: {
      discountName: "",
      discountPercentage: "",
      mininumOrderAmount: "",
    },
    discountList: [],
    headers: [
      { text: "Discount", value: "discountName" },
      { text: "Percentage", value: "discountPercentage" },
      { text: "Mininum Amount", value: "mininumOrderAmount" },
      { text: "Status", value: "isActive" },
      { text: "Created Date", value: "createdAt" },
      { text: "Modified Date", value: "updatedAt" },
      { text: "Actions", value: "actions", sortable: false },
    ],
  }),

  async created() {
    await this.fetchAllDiscounts();
    await this.fetchActiveDiscount();
    // await this.fetchMakeDiscountOFF();
    //await this.fetchDiscountOn();
  },
  computed: {
    ...mapState(["discount"]),
  },

  methods: {
    getColor(isActive) {
      if (isActive == false) return "error";
      else return "success";
    },
    // <!-- -------------****** get all discount from utils.http ******-------------------------- -->
    async fetchAllDiscounts() {
      const response = await utils.http.get("/discount/list");
      if (response) {
        const data = await response.json();
        if (data) this.discountList = data;
      } else {
        console.log("sth wrong");
      }
    },
    // <!-- -------------****** update user from utils.http ******-------------------------- -->
    async updateDiscountStatus(chooseDiscountId) {
      this.fetchMakeDiscountOFF();
      const resp = await utils.http.put(
        "/admin/discount/update_active/" + chooseDiscountId,
        {
          discountId: this.chooseDiscountId,
        }
      );

      if (resp && resp.status === 200) {
        this.discount_dialog = false;
        this.$router.go(); // current page refresh
      }
    },
    async fetchMakeDiscountOFF() {
      const resp = await utils.http.put("/admin/discount/update_unactive");
      if (resp && resp.status == 200) {
        // console.log(resp);
      }
    },
    async fetchActiveDiscount() {
      const response = await utils.http.get("/discount/active");

      if (response.status === 200) {
        const data = await response.json();
        if (data) {
          this.$store.commit("discount", data);
        }
      }
    },

    // <!-- -------------****** save discount from utils.http ******-------------------------- -->
    async save() {
      if (this.$refs.createDiscountForm.validate()) {
        let discountNameCheckByOK = false;

        const respdiscountNameCheck = await utils.http.get(
          "/discount/discountName/" + this.createDemo.discountName.trim()
        );
        if (respdiscountNameCheck && respdiscountNameCheck.status == 200) {
          const data = await respdiscountNameCheck.json();
          if (data && data == true) {
            swal.fire({
              icon: "error",
              title: "Sorry...",
              text: "Discount name already exists!",
            });
          } else {
            discountNameCheckByOK = true;
          }
        }
        if (discountNameCheckByOK) {
          const resp = await utils.http.post("/admin/discount/create", {
            discountName: this.createDemo.discountName,
            discountPercentage: this.createDemo.discountPercentage,
            mininumOrderAmount: this.createDemo.mininumOrderAmount,
          });

          if (resp) {
            await this.fetchAllDiscounts();
            this.dialog = false;
            this.createDemo = {};
          } else {
            this.errorAlert = true;
          }
        }
      }
    },

    // <!-- -------------****** update discount from utils.http ******-------------------------- -->
    async updateItemConfirm(id) {
      const resp = await utils.http.put("/admin/discount/update/" + id, {
        discountName: this.updateDemo.discountName,
        discountPercentage: this.updateDemo.discountPercentage,
        mininumOrderAmount: this.updateDemo.mininumOrderAmount,
      });
      if (resp && resp.status === 200) {
        await this.fetchAllDiscounts();
        this.update_dialog = false;
      } else {
        swal.fire({
          icon: "error",
          title: "Sorry...",
          text: "Discount name already exists!",
        });
      }
    },
    // <!-- -------------****** delete discount from utils.http ******-------------------------- -->
    async deleteItemConfirm(id) {
      const resp = await utils.http.del("/admin/discount/delete/" + id);
      if (resp.status == 200) {
        await this.fetchAllDiscounts();
        this.delete_dialog = false;
      }
    },
    // <!-- -------------******  event handelar from dialog ******-------------------------- -->
    removeDiscount() {
      this.fetchMakeDiscountOFF();
      this.$store.commit("discount");
      this.discount_dialog = false;
      this.$router.go();
    },
    deleteItem(item) {
      this.delete_dialog = true;
      this.deleteDemo = item;
    },
    editItem(item) {
      this.update_dialog = true;
      this.updateDemo = Object.assign({}, item);
      this.discountName = this.updateDemo.discountName;
      this.discountPercentage = this.updateDemo.discountPercentage;
      this.mininumOrderAmount = this.updateDemo.mininumOrderAmount;
    },
    closeUpdate() {
      this.update_dialog = false;
    },
    closeDelete() {
      this.delete_dialog = false;
    },
    closeDiscount() {
      this.discount_dialog = false;
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
