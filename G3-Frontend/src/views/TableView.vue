<template>
  <!-- :to="{ name: 'menu' }" -->
  <v-app :style="{ background: $vuetify.theme.themes.dark.background }">
    <SideBar />

    <template>
      <div class="text-center mt-4">
        <v-dialog v-model="payment_dialog" width="500">
          <template v-slot:activator="{ attrs, on }">
            <v-container>
              <v-item-group mandatory class="mt-n1">
                <v-container>
                  <v-row justify="center" class="space">
                    <v-card
                      elevation="3"
                      class="mr-2 ml-2 mt-4 mb-2"
                      v-for="(table, index) in tableList"
                      :key="index"
                    >
                      <!-- ----------------------------If Payment Table ---------------------------- -->

                      <v-col v-if="table.billTotal > 0">
                        <v-item>
                          <v-card
                            class="d-flex align-center rounded-lg mx-2"
                            height="140"
                            width="140"
                            flat
                          >
                            <v-row>
                              <v-sheet
                                color="error"
                                elevation="5"
                                height="50"
                                width="50"
                              >
                                <h1 white--text>
                                  {{ table.tableName }}
                                </h1>
                              </v-sheet>
                              <v-spacer></v-spacer>
                              <h4 class="mt-4">{{ table.billTotal }} Ks</h4>
                              <v-col cols="12" sm="12">
                                <v-list-item three-line class="text-center">
                                  <v-list-item-content>
                                    <div align="center" justify="center">
                                      <v-img
                                        src="../assets/nuggets.gif"
                                        max-height="70"
                                        max-width="70"
                                        contain
                                      ></v-img>
                                    </div>

                                    <v-list-item-subtitle>
                                      <span
                                        color="#b6ecec "
                                        :ripple="false"
                                        class="material-icons mr-16 indigo--text"
                                        @click="chooseTable(table)"
                                        v-bind="attrs"
                                        v-on="on"
                                        >point_of_sale</span
                                      >
                                      <router-link to="/menu" class="tx-de">
                                        <span
                                          @click="chooseTable(table)"
                                          class="material-icons indigo--text"
                                        >
                                          post_add
                                        </span>
                                      </router-link>
                                    </v-list-item-subtitle>
                                  </v-list-item-content>
                                </v-list-item>
                              </v-col>
                            </v-row>
                          </v-card>
                        </v-item>
                      </v-col>

                      <!-- ----------------------------If Order Table ---------------------------- -->
                      <v-col v-else>
                        <router-link to="/menu" class="tx-de">
                          <v-item>
                            <v-card
                              @click="chooseTable(table)"
                              class="d-flex align-center rounded-lg mx-2"
                              height="140"
                              width="140"
                              flat
                              :ripple="false"
                            >
                              <v-row>
                                <v-sheet
                                  color="success"
                                  elevation="5"
                                  height="50"
                                  width="50"
                                >
                                  <h1>
                                    {{ table.tableName }}
                                  </h1>
                                </v-sheet>
                                <v-spacer></v-spacer>
                                <h4 class="mt-4">{{ table.billTotal }} Ks</h4>
                                <v-col class="" cols="12" sm="12">
                                  <v-list-item three-line class="text-center">
                                    <v-list-item-content>
                                      <div align="center" justify="center">
                                        <v-img
                                          src="../assets/table.gif"
                                          max-height="110"
                                          max-width="90"
                                          contain
                                        ></v-img>
                                      </div>
                                      <v-list-item-subtitle class="mt-3">
                                      </v-list-item-subtitle>
                                    </v-list-item-content>
                                  </v-list-item>
                                </v-col>
                              </v-row>
                            </v-card>
                          </v-item>
                        </router-link>
                      </v-col>
                    </v-card>
                    <!-- ---------------------------- End Table ---------------------------- -->
                  </v-row>
                </v-container>
              </v-item-group>
            </v-container>
          </template>
          <v-card>
            <v-card-title class="grey lighten-2">
              <strong>Payment</strong>
            </v-card-title>

            <v-card-text>
              <bill-payment v-on:payment_dialog="closeDialog"></bill-payment>
            </v-card-text>
          </v-card>
        </v-dialog>
      </div>
    </template>
  </v-app>
</template>

<script>
import SideBar from "../components/SideBar";
import BillPayment from "@/components/BillPayment.vue";
import utils from "@/utils/utils";
export default {
  components: {
    SideBar,

    BillPayment,
  },
  data() {
    return {
      payment_dialog: false,
      tableList: [],
      table: {},
    };
  },
  async created() {
    await this.fetchAllTables();
  },
  methods: {
    closeDialog() {
      this.payment_dialog = false;
    },
    chooseTable(table) {
      this.$store.dispatch("chooseTable", {
        table: table,
      });
    },
    // <!-- -------------****** get all table from utils.http ******-------------------------- -->
    async fetchAllTables() {
      const response = await utils.http.get("/table/list_with_order");
      if (response) {
        const data = await response.json();
        if (data) this.tableList = data;
      } else {
        console.log("sth wromg");
      }
    },
  },
};
</script>
<style>
.tx-de {
  text-decoration: none;
}
.v-list-item--link:before {
  background-color: white !important;
}
</style>
