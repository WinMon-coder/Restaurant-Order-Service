<template>
  <div>
    <AdminSideBar />
    <v-data-table
      :headers="headers"
      :items="categoryList"
      :items-per-page="10"
      class="elevation-1"
    >
      <!-- Poster Img -->
      <template v-slot:[`item.categoryPhoto`]="{ item }">
        <v-img
          :src="localDomain + item.categoryPhoto"
          width="50"
          height="50"
          contain
        ></v-img>
      </template>
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>Category</v-toolbar-title>
          <v-divider class="mx-4" inset vertical></v-divider>

          <!-- -------------****** create dialog ******-------------------------- -->
          <v-dialog v-model="dialog" max-width="400px">
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                color="primary"
                class="mb-2 widthoutupercase white--text"
                v-bind="attrs"
                v-on="on"
              >
                Add Category
              </v-btn>
            </template>
            <template
              ><v-form ref="createCategoryForm" v-model="createCategoryForm">
                <v-card>
                  <v-card-title>
                    <h3>Add New Category</h3>
                  </v-card-title>
                  <v-card-text>
                    <v-container>
                      <v-row>
                        <v-col cols="12" sm="6" md="6">
                          <v-text-field
                            v-model="createDemo.categoryName"
                            :rules="[
                              (v) => !!v || 'Required',
                              (v) =>
                                (v && v.length <= 50) ||
                                'Title must be less than 50 characters',
                            ]"
                            label="Category Name"
                          ></v-text-field>
                        </v-col>
                        <v-col cols="12" sm="6">
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
                      </v-row>
                      <v-row>
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
                      @click="closeCreate"
                      >Cancel
                    </v-btn>
                    <v-btn
                      color="primary"
                      class="mb-2 widthoutupercase white--text"
                      @click="save"
                      :disabled="!createCategoryForm"
                      ><span v-if="!loading">Save</span>
                      <v-progress-circular
                        v-else
                        indeterminate
                        color="primary"
                      ></v-progress-circular
                    ></v-btn>
                  </v-card-actions>
                  <v-alert class="mt-3" v-show="errorAlert" dense type="error">
                    Create Category Failed!
                  </v-alert>
                  <!-- Same Name Error Alert -->
                </v-card>
                <!-- Error Alert For Category -->
              </v-form></template
            >
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
      <!-- -------------****** update dialog ******-------------------------- -->
      <v-dialog v-model="update_dialog" max-width="400px">
        <v-card>
          <v-card-title>
            <h3>Edit Category</h3>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12" sm="6" md="6">
                  <v-text-field
                    v-model="updateDemo.categoryName"
                    :rules="[
                      (v) => !!v || 'Required',
                      (v) =>
                        (v && v.length <= 50) ||
                        'Title must be less than 50 characters',
                    ]"
                    label="Category Name"
                  ></v-text-field>
                </v-col>
                <!-- -------------****** poster start ******-------------------------- -->
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
              <v-row>
                <!-- Poster is not selected (null) -->
                <v-img
                  v-if="posterPreviewPath == null"
                  :src="localDomain + updateDemo.categoryPhoto"
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
              @click="updateItemConfirm(updateDemo.categoryId)"
              >Update</v-btn
            >
          </v-card-actions>
          <v-alert class="mt-3" v-show="errorAlert" dense type="error">
            Update Category Failed!
          </v-alert>
        </v-card>
      </v-dialog>
      <!-- -------------****** delete ******-------------------------- -->
      <v-container>
        <v-dialog v-model="delete_dialog" max-width="400px">
          <v-card>
            <v-card-title
              ><span>Are you sure you want to delete this?</span></v-card-title
            >

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn class="widthoutupercase mb-2" text @click="closeDelete"
                >Cancel</v-btn
              >
              <v-btn
                color="primary"
                class="mb-2 widthoutupercase white--text"
                @click="deleteItemConfirm(deleteDemo.categoryId)"
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
    createCategoryForm: false,
    dialog: false,
    update_dialog: false,
    delete_dialog: false,
    loading: false,
    errorAlert: false,
    poster: null,
    posterPreviewPath: null,
    deleteDemo: {},
    updateDemo: {},
    createDemo: {
      categoryName: "",
      categoryPhoto: null,
    },
    categoryList: [],
    headers: [
      { text: "Photo", value: "categoryPhoto", sortable: false },
      { text: "Category", value: "categoryName" },
      { text: "Created Date", value: "createdAt" },
      { text: "Modified Date", value: "updatedAt" },
      { text: "Actions", value: "actions", sortable: false },
    ],
  }),

  async created() {
    await this.fetchAllCategories();
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
    // <!-- -------------****** save Category from utils.http ******-------------------------- -->

    async save() {
      if (this.$refs.createCategoryForm.validate()) {
        this.errorAlert = false;

        let CategoryNameCheckOK = false;
        const respCategoryNameCheck = await utils.http.get(
          "/category/categoryName/" + this.createDemo.categoryName.trim()
        );
        if (respCategoryNameCheck && respCategoryNameCheck.status === 200) {
          const data = await respCategoryNameCheck.json();
          if (data && data === true) {
            swal.fire({
              icon: "error",
              title: "Sorry...",
              text: "Category name already exists!",
            });
          }
          //Return false (when category is the different name)
          else {
            CategoryNameCheckOK = true;
          }
        }
        if (CategoryNameCheckOK) {
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
            // Create Category utils.http
            const resp = await utils.http.post("/admin/category/create", {
              categoryName: this.createDemo.categoryName,
              categoryPhoto: respPosterData,
            });
            if (resp) {
              await this.fetchAllCategories();
              this.dialog = false;
              this.createDemo = {};
              this.poster = null;
              this.posterPreviewPath = null;
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
      let categoryPhoto = this.updateDemo.categoryPhoto;

      // Step 1 -> Update Poster
      // Null -> Poster is not selected
      if (this.updateDemo.poster != null) {
        // Update Poster utils.http
        const respPosterData = await utils.http.putMedia(
          "/admin/file/update",
          this.updateDemo.poster,
          this.updateDemo.poster.type,
          this.updateDemo.categoryPhoto
        );

        if (respPosterData && respPosterData.status === 200) {
          categoryPhoto = await respPosterData.text();
        } else {
          console.debug("Poster Update Failed");
        }
      }
      const resp = await utils.http.put("/admin/category/update/" + id, {
        categoryName: this.updateDemo.categoryName,
        categoryPhoto: categoryPhoto,
      });
      if (resp && resp.status === 200) {
        this.update_dialog = false;
        await this.fetchAllCategories();
      } else {
        swal.fire({
          icon: "error",
          title: "Sorry...",
          text: "Category cannot update!",
        });
      }

      this.loading = false;
    },
    // <!-- -------------****** delete table from utils.http ******-------------------------- -->
    async deleteItemConfirm(id) {
      const resp = await utils.http.del("/admin/category/delete/" + id);
      if (resp.status == 200) {
        await this.fetchAllCategories();
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
      this.categoryName = this.updateDemo.categoryName;
      this.categoryPhoto = this.updateDemo.categoryPhoto;

      this.poster = this.updateDemo.poster;
      this.posterPreviewPath = this.updateDemo.posterPreviewPath;
    },
    closeUpdate() {
      this.update_dialog = false;
    },
    closeDelete() {
      this.delete_dialog = false;
    },
    closeCreate() {
      this.dialog = false;
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
  background-color: primary;
  color: white !important;
}
</style>
