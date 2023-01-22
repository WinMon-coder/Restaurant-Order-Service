<template>
  <div>
    <AdminSideBar />
    <v-data-table
      :headers="headers"
      :items="foodList"
      :items-per-page="10"
      class="elevation-1"
    >
      <!-- Poster Img -->
      <template v-slot:[`item.foodPhoto`]="{ item }">
        <v-img
          :src="localDomain + item.foodPhoto"
          width="100"
          height="100"
          contain
        ></v-img>
      </template>
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>Food</v-toolbar-title>
          <v-divider class="mx-4" inset vertical></v-divider>

          <!-- -------------****** create dialog ******-------------------------- -->
          <v-dialog v-model="dialog" max-width="450px">
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                color="primary"
                class="mb-2 widthoutupercase white--text"
                v-bind="attrs"
                v-on="on"
              >
                Add Food
              </v-btn>
            </template>
            <template
              ><v-form ref="createFoodForm" v-model="createFoodForm">
                <v-card>
                  <v-card-title>
                    <h3>Add New Food</h3>
                  </v-card-title>
                  <v-card-text>
                    <v-container>
                      <v-row>
                        <v-col cols="12" sm="6" md="6">
                          <v-text-field
                            v-model="createDemo.foodName"
                            :rules="[
                              (v) => !!v || 'Required',
                              (v) =>
                                (v && v.length <= 50) ||
                                'Title must be less than 50 characters',
                            ]"
                            label="Food Name"
                          ></v-text-field>
                        </v-col>
                        <v-col class="d-flex" cols="12" sm="6">
                          <!-- Category -->
                          <v-select
                            v-model="createDemo.foodCategory"
                            :items="categoryList"
                            item-text="categoryName"
                            item-value="categoryId"
                            :rules="[(v) => !!v || 'Required']"
                            label="Category"
                            required
                          >
                          </v-select>
                        </v-col>
                      </v-row>

                      <v-row>
                        <v-col class="d-flex" cols="12" sm="6">
                          <v-text-field
                            v-model="createDemo.foodPrice"
                            type="number"
                            suffix="MMK"
                            min="1"
                            :rules="[
                              (v) => !!v || 'Required',
                              (v) =>
                                (v && v > 0) ||
                                'Price must be greater than 0 MMK',
                            ]"
                            label="Price"
                            required
                          ></v-text-field>
                        </v-col>
                        <v-col class="d-flex" cols="12" sm="6">
                          <!-- Adult -->
                          <v-checkbox
                            v-model="createDemo.isAvaliable"
                            label="Avaliable"
                          ></v-checkbox>
                        </v-col>
                      </v-row>
                      <v-row>
                        <v-col class="d-flex" cols="12" sm="12">
                          <!-- Poster -->
                          <v-file-input
                            v-model="poster"
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
                        <!-- Poster Preview -->
                        <v-img
                          v-if="posterPreviewPath != null"
                          :src="posterPreviewPath"
                          width="200"
                          height="150"
                          contain
                        >
                        </v-img
                      ></v-row>
                    </v-container>
                  </v-card-text>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn
                      class="mb-2 widthoutupercase"
                      text
                      @click="saveCancel"
                      >Cancel
                    </v-btn>
                    <v-btn
                      color="primary"
                      class="mb-2 widthoutupercase white--text"
                      @click="save"
                      :disabled="!createFoodForm"
                      ><span v-if="!loading">Save</span>
                      <v-progress-circular
                        v-else
                        indeterminate
                        color="primary"
                      ></v-progress-circular
                    ></v-btn>
                  </v-card-actions>
                  <v-alert class="mt-3" v-show="errorAlert" dense type="error">
                    Create Food Failed!
                  </v-alert>
                </v-card>
                <!-- Error Alert For Category -->
              </v-form></template
            >
          </v-dialog>
        </v-toolbar>
      </template>
      <template v-slot:[`item.foodName`]="{ item }">
        {{ item.foodName | capitalising }}
      </template>
      <template v-slot:[`item.category`]="{ item }">
        <h3 class="primary--text">
          {{ item.foodCategory.categoryName }}
        </h3>
      </template>
      <template v-slot:[`item.isAvaliable`]="{ item }">
        <span
          v-if="item.isAvaliable == true"
          class="material-icons success--text"
        >
          check_circle
        </span>
        <span v-else class="material-icons error--text"> cancel </span>
      </template>
      <template v-slot:[`item.createdAt`]="{ item }">
        {{ item.createdAt | formatLocalDate }}
      </template>
      <template v-slot:[`item.updatedAt`]="{ item }">
        {{ item.updatedAt | formatLocalDate }}
      </template>
      <template v-slot:[`item.actions`]="{ item }">
        <v-icon color="indigo" dark class="mr-2" @click="editItem(item)">
          mdi-pencil
        </v-icon>
        <v-icon color="error" dark @click="deleteItem(item)">
          mdi-delete
        </v-icon>
      </template>
    </v-data-table>
    <v-toolbar>
      <!-- -------------****** update dialog ******-------------------------- -->
      <v-form ref="createFoodForm" v-model="createFoodForm">
        <v-dialog v-model="update_dialog" max-width="450px">
          <v-card>
            <v-card-title>
              <h3>Edit Food</h3>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="6">
                    <v-text-field
                      v-model="updateDemo.foodName"
                      :rules="[
                        (v) => !!v || 'Required',
                        (v) =>
                          (v && v.length <= 50) ||
                          'Title must be less than 50 characters',
                      ]"
                      label="Food Name"
                    ></v-text-field>
                  </v-col>
                  <v-col class="d-flex" cols="12" sm="6">
                    <!-- Category -->
                    <v-select
                      v-model="updateDemo.foodCategory.categoryId"
                      :items="categoryList"
                      item-text="categoryName"
                      item-value="categoryId"
                      :rules="[(v) => !!v || 'Required']"
                      label="Category"
                      required
                    >
                    </v-select>
                  </v-col>
                </v-row>

                <v-row>
                  <v-col class="d-flex" cols="12" sm="6">
                    <v-text-field
                      v-model="updateDemo.foodPrice"
                      type="number"
                      suffix="MMK"
                      min="1"
                      :rules="[
                        (v) => !!v || 'Required',
                        (v) =>
                          (v && v > 0) || 'Price must be greater than 0 MMK',
                      ]"
                      label="Price"
                      required
                    ></v-text-field>
                  </v-col>
                  <v-col class="d-flex" cols="12" sm="6">
                    <!-- Adult -->
                    <v-checkbox
                      v-model="updateDemo.isAvaliable"
                      label="Avaliable"
                    ></v-checkbox>
                  </v-col>
                </v-row>
                <!-- -------------****** poster start ******-------------------------- -->
                <v-row>
                  <v-col cols="12" sm="12">
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
                <!-- -------------****** Photo Preview start ******-------------------------- -->
                <v-row>
                  <!-- Poster is not selected (null) -->
                  <v-img
                    v-if="posterPreviewPath == null"
                    :src="localDomain + updateDemo.foodPhoto"
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
                <!-- -------------****** Photo Preview end ******-------------------------- -->
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
                @click="updateItemConfirm(updateDemo.foodId)"
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
                @click="deleteItemConfirm(deleteDemo.foodId)"
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
    localDomain: utils.constant.localDomain,
    createFoodForm: false,
    dialog: false,
    update_dialog: false,
    delete_dialog: false,
    loading: false,
    errorAlert: false,
    poster: null,
    posterPreviewPath: null,
    deleteDemo: {},
    updateDemo: {
      foodName: "",
      foodCategory: "",
    },
    createDemo: {
      foodName: "",
      foodCategory: 1,
      foodPrice: "",
      foodPhoto: null,
      isAvaliable: true,
    },
    categoryList: [],
    foodList: [],
    headers: [
      { text: "Photo", value: "foodPhoto" },
      { text: "Name", value: "foodName" },
      { text: "Price(Ks)", value: "foodPrice" },
      { text: "Category", value: "category" },
      { text: "Avaliable", value: "isAvaliable" },
      { text: "Created Date", value: "createdAt" },
      { text: "Modified Date", value: "updatedAt" },
      { text: "Actions", value: "actions", sortable: false },
    ],
  }),

  async created() {
    await this.fetchAllCategories();
    await this.fetchAllFoods();
  },

  methods: {
    // <!-- -------------****** get all category from utils.http ******-------------------------- -->
    async fetchAllCategories() {
      const response = await utils.http.get("/category/list");
      if (response) {
        const data = await response.json();
        if (data) this.categoryList = data;
      }
    },
    // <!-- -------------****** get all food from utils.http ******-------------------------- -->
    async fetchAllFoods() {
      const response = await utils.http.get("/food/list");
      if (response) {
        const data = await response.json();
        if (data) this.foodList = data;
      }
    },
    // <!-- -------------****** save Category from utils.http ******-------------------------- -->

    async save() {
      if (this.$refs.createFoodForm.validate()) {
        this.errorAlert = false;
        let FoodNameCheckOK = false;

        const respFoodNameCheck = await utils.http.get(
          "/food/foodName/" + this.createDemo.foodName.trim()
        );

        // console.log(respFoodNameCheck);
        if (respFoodNameCheck && respFoodNameCheck.status === 200) {
          const data = await respFoodNameCheck.json();
          if (data && data === true) {
            swal.fire({
              icon: "error",
              title: "Sorry...",
              text: "Food name already exists!",
            });
          } else {
            FoodNameCheckOK = true;
          }
        }

        if (FoodNameCheckOK) {
          const respPoster = await utils.http.postMedia(
            "/admin/file/create",
            this.poster,
            this.poster.type
          );
          let respPosterData = null;

          if (respPoster.status === 200) {
            respPosterData = await respPoster.text();
            //console.log(this.respPosterData);
          } else {
            this.errorAlert = true;
          }
          if (respPosterData) {
            // Create Food utils.http
            const resp = await utils.http.post("/admin/food/create", {
              foodName: this.createDemo.foodName,
              foodPrice: this.createDemo.foodPrice,
              foodCategory: { categoryId: this.createDemo.foodCategory },
              isAvaliable: this.createDemo.isAvaliable,
              foodPhoto: respPosterData,
            });
            console.log({ categoryId: this.createDemo.foodCategory });

            if (resp && resp.status === 200) {
              await this.fetchAllFoods();
              this.dialog = false;
              this.createDemo = {};
              this.poster = null;
              this.posterPreviewPath = null;
            } else {
              swal.fire({
                icon: "error",
                title: "Sorry...",
                text: "Food cannot created!",
              });
            }
          }
          this.loading = false;
        }
      }
    },
    // <!-- -------------****** update table from utils.http ******-------------------------- -->
    async updateItemConfirm(id) {
      this.errorAlert = false;
      this.loading = true;
      let foodPhoto = this.updateDemo.foodPhoto;
      // Step 1 -> Update Poster
      // Null -> Poster is not selected
      if (this.updateDemo.poster != null) {
        // Update Poster utils.http
        const respPosterData = await utils.http.putMedia(
          "/admin/file/update",
          this.updateDemo.poster,
          this.updateDemo.poster.type,
          this.updateDemo.foodPhoto
        );

        if (respPosterData && respPosterData.status === 200) {
          foodPhoto = await respPosterData.text();
        } else {
          console.debug("Poster Update Failed");
        }
      }
      const resp = await utils.http.put("/admin/food/update/" + id, {
        foodName: this.updateDemo.foodName,
        foodPrice: this.updateDemo.foodPrice,
        foodCategory: {
          categoryId: this.updateDemo.foodCategory.categoryId,
        },
        isAvaliable: this.updateDemo.isAvaliable,
        foodPhoto: foodPhoto,
      });
      console.log({
        categoryId: this.updateDemo.foodCategory.categoryId,
      });
      if (resp && resp.status === 200) {
        this.update_dialog = false;
        await this.fetchAllFoods();
      } else {
        swal.fire({
          icon: "error",
          title: "Sorry...",
          text: "Food cannot update!",
        });
      }

      this.loading = false;
    },
    // <!-- -------------****** delete table from utils.http ******-------------------------- -->
    async deleteItemConfirm(id) {
      const resp = await utils.http.del("/admin/food/delete/" + id);
      if (resp.status == 200) {
        await this.fetchAllFoods();
        this.delete_dialog = false;
      }
    },

    // <!-- -------------******  event handelar from dialog ******-------------------------- -->
    onChangePoster(poster) {
      this.posterPreviewPath = URL.createObjectURL(poster);
    },
    deleteItem(item) {
      this.delete_dialog = true;
      this.deleteDemo = item;
    },
    editItem(item) {
      this.update_dialog = true;
      // Copy Object
      this.updateDemo = Object.assign({}, item);
      this.foodName = this.updateDemo.foodName;
      this.foodPrice = this.updateDemo.foodPrice;
      this.foodCategory = {
        categoryId: this.updateDemo.foodCategory.categoryId,
      };
      this.isAvaliable = this.updateDemo.isAvaliable;
      this.foodPhoto = this.updateDemo.foodPhoto;
      this.poster = this.updateDemo.poster;
      this.posterPreviewPath = this.updateDemo.posterPreviewPath;
      console.log({ categoryId: this.updateDemo.foodCategory.categoryId });
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
      this.poster = null;
      this.posterPreviewPath = null;
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
