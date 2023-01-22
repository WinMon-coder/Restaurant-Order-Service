<template>
  <v-app :style="{ background: $vuetify.theme.themes.dark.background }">
    <SideBar />
    <side-bar-right :foods="foods"></side-bar-right>

    <v-container>
      <v-toolbar color="rgba(0,0,0,0)" flat>
        <!-- ---------------------------- Label For Category ---------------------------- -->
        <v-toolbar-title class="mt-n3">Choose Category</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- ---------------------------- Category Search Bar ---------------------------- -->
        <v-text-field
          v-model="searchFoodName"
          label="Search category or menu..."
          class="mt-5 search-bar-round"
          color="primary"
          filled
          append-icon="mdi-magnify"
          dense
          solo
          flat
          background-color=" lighten-4"
        ></v-text-field>
        <!-- ---------------------------- End Category Search Bar ---------------------------- -->
      </v-toolbar>
      <!-- ---------------------------- Category Menu ---------------------------- -->
      <v-item-group mandatory class="mt-n1">
        <v-container>
          <v-row justify="center" class="space">
            <!-- ---------------------------- For All Category ---------------------------- -->

            <v-col cols="12" sm="2" @click="onClickAllCategories()">
              <v-item v-slot="{ active, toggle }">
                <v-card
                  :color="active ? '#F6EFEF' : 'white'"
                  :class="active ? 'borderme' : 'borderout'"
                  class="d-flex align-center rounded-lg mx-2"
                  dark
                  height="50"
                  @click="toggle"
                >
                  <v-row>
                    <v-col cols="12" sm="12">
                      <div
                        tabindex="-1"
                        class="v-list-item v-list-item--three-line"
                      >
                        <div>
                          <v-list-item-avatar>
                            <v-img
                              rounded
                              src="../assets/bibimbap.png"
                              max-height="60"
                              max-width="100"
                              flex
                            ></v-img>
                          </v-list-item-avatar>
                        </div>
                        <v-list-item-title>
                          <div align="center" justify="center"></div>
                          <v-list-item-subtitle
                            :class="active ? 'primary--text' : 'black--text'"
                            >All</v-list-item-subtitle
                          >
                        </v-list-item-title>
                      </div>
                    </v-col>
                  </v-row>
                </v-card>
              </v-item>
            </v-col>
            <!-- ----------------------------End For All Category ---------------------------- -->

            <!-- ---------------------------- Category Items ---------------------------- -->
            <v-col
              class="category-card"
              cols="12"
              sm="4"
              md="2"
              v-for="(category, index) in categoryList"
              :key="index"
              @click="onClickCategory(category)"
            >
              <v-item v-slot="{ active, toggle }">
                <v-card
                  :color="active ? '#F6EFEF' : 'white'"
                  :class="active ? 'borderme' : 'borderout'"
                  class="d-flex align-center rounded-lg mx-2"
                  dark
                  height="50"
                  @click="toggle"
                >
                  <v-row>
                    <v-col cols="12" sm="12">
                      <div
                        tabindex="-1"
                        class="v-list-item v-list-item--three-line"
                      >
                        <div>
                          <v-list-item-avatar>
                            <v-img
                              rounded
                              :src="localDomain + category.categoryPhoto"
                              max-height="60"
                              max-width="100"
                              flex
                            ></v-img>
                          </v-list-item-avatar>
                        </div>
                        <v-list-item-title>
                          <div align="center" justify="center"></div>
                          <v-list-item-subtitle
                            :class="active ? 'primary--text' : 'black--text'"
                            >{{
                              capitalized(category.categoryName)
                            }}</v-list-item-subtitle
                          >
                        </v-list-item-title>
                      </div>
                    </v-col>
                  </v-row>
                </v-card>
              </v-item>
            </v-col>

            <!-- ---------------------------- End Category Items ---------------------------- -->
          </v-row>
        </v-container>
      </v-item-group>

      <!-- ---------------------------- Label For Item List By Category ---------------------------- -->
      <v-toolbar color="#fbfbfb" flat>
        <v-toolbar-title>Our Menu </v-toolbar-title><v-spacer></v-spacer
        ><span color="grey"></span>
      </v-toolbar>
      <!-- ---------------------------- Show Item List By Category ---------------------------- -->
      <v-item-group mandatory class="mt-n1">
        <v-container>
          <v-row justify="center" class="space">
            <!-- ---------------------------- Item List ---------------------------- -->
            <v-col
              cols="12"
              md="2"
              v-for="food in filterFoods"
              :key="food.foodId"
            >
              <v-item v-slot="{ active, toggle }">
                <v-card
                  :color="active ? '#F6EFEF' : 'white'"
                  :class="active ? 'borderme' : 'borderout'"
                  height="200"
                  @click="toggle"
                >
                  <v-container
                    fluid
                    class="align-center"
                    @click="
                      getTable.table.order.orderTotal > 0
                        ? updateToOrder(food)
                        : addToOrder(food)
                    "
                  >
                    <v-img
                      class="mb-1"
                      :src="localDomain + food.foodPhoto"
                      max-height="120"
                    ></v-img>

                    <span>
                      {{ food.foodName | capitalising | truncate(15, "...") }}
                    </span>
                    <br />
                    <strong class="primary--text"
                      >{{ food.foodPrice }} Ks</strong
                    >
                  </v-container>
                </v-card>
              </v-item>
            </v-col>
            <!-- ----------------------------End Item List ---------------------------- -->
          </v-row>
        </v-container>
      </v-item-group>
    </v-container>
  </v-app>
</template>

<script>
import SideBar from "../components/SideBar";
import SideBarRight from "../components/SideBarRight";
import utils from "@/utils/utils";
import { mapGetters } from "vuex";
export default {
  components: {
    SideBar,
    SideBarRight,
  },

  data() {
    return {
      localDomain: utils.constant.localDomain,
      categoryList: [],
      foodList: [],
      // orderByTableAndStatusList: [],
      searchFoodName: "",
    };
  },

  mounted() {
    this.$store.dispatch("getAllFoods");
    //this.$store.dispatch("getFood", this.foodId);
  },

  computed: {
    filterFoods: function () {
      return this.foodList.filter((foods) => {
        return foods.foodName.match(this.searchFoodName);
      });
    },
    foods() {
      return this.$store.state.foods;
    },
    // food() {
    //   return this.$store.state.food;
    // },

    ...mapGetters(["getTable"]),
  },

  async created() {
    await this.fetchAllCategories();
    await this.fetchAllFoods();
    // await this.fetchOrderByTableAndStatus();
  },
  methods: {
    addToOrder(food) {
      this.$store.dispatch("addFoodToOrder", {
        food: food,
        quantity: 1,
      });
    },
    updateToOrder(food) {
      this.$store.dispatch("updateFoodToOrder", {
        food: food,
        quantity: 1,
      });
    },
    capitalized(name) {
      const capitalizedFirst = name[0].toUpperCase();
      const rest = name.slice(1);

      return capitalizedFirst + rest;
    },
    async fetchAllCategories() {
      const response = await utils.http.get("/category/list");
      if (response.status === 200) {
        const data = await response.json();
        this.categoryList = data;
      }
    },
    async fetchAllFoods() {
      const response = await utils.http.get("/food/list_with_avaliable");
      if (response.status === 200) {
        const data = await response.json();
        this.foodList = data;
      }
    },
    async onClickCategory(category) {
      const response = await utils.http.get(
        "/food/category/" + category.categoryId
      );
      if (response.status === 200) {
        const data = await response.json();
        if (data) {
          this.foodList = data;
        }
      }
    },
    async onClickAllCategories() {
      const response = await utils.http.get("/food/list_with_avaliable");
      if (response.status === 200) {
        const data = await response.json();
        this.foodList = data;
      }
    },
  },
};
</script>
<style>
.category-card {
  padding: 5px 0px !important;
}
.search-bar-round {
  border-radius: 8px;
}
v-card v-card--link v-sheet theme--light borderme {
  background-color: rgb(190 21 21);
  border-color: rgb(246, 239, 239);
}
</style>
