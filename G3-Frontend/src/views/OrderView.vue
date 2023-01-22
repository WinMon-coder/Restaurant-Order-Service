<template>
  <div>
    <SideBar />

    <v-container>
      <v-layout row wrap>
        <v-flex sm6 xs12 md6 lg4>
          <v-card class="ma-3">
            <v-list-item>
              <v-list-item-avatar tile>
                <v-icon dark color="success" x-large>local_atm</v-icon>
              </v-list-item-avatar>
              <v-list-item-content>
                <div class="text-right"><strong>Total Revenue</strong></div>
                <v-list-item-title class="headline mb-1 text-right">
                  <strong class="primary--text"> {{ totalAmount }} Ks</strong>
                </v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-card>
        </v-flex>
        <v-flex sm6 xs12 md6 lg4>
          <v-card class="ma-3">
            <v-list-item>
              <v-list-item-avatar tile>
                <v-icon dark color="error" x-large>hourglass_top</v-icon>
              </v-list-item-avatar>
              <v-list-item-content>
                <div class="text-right">
                  <strong>Order InProcess</strong>
                </div>
                <v-list-item-title class="headline mb-1 text-right">
                  <strong class="primary--text">
                    {{ orderInProcessing }}
                  </strong>
                </v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-card>
        </v-flex>
        <!-- <v-flex sm6 xs12 md6 lg3>
          <v-card class="ma-3">
            <v-list-item>
              <v-list-item-avatar tile >
                <v-sheet color="#F44336" width="80" height="80" elevation="10">
                  <v-icon dark large>person</v-icon>
                </v-sheet>
              </v-list-item-avatar>
              <v-list-item-content>
                <div class="overline text-right">Total Customer</div>
                <v-list-item-title class="headline mb-1 text-right"
                  >700</v-list-item-title
                >
              </v-list-item-content>
            </v-list-item>
          </v-card>
        </v-flex> -->
        <v-flex sm6 xs12 md6 lg4>
          <v-card class="ma-3">
            <v-list-item>
              <v-list-item-avatar tile>
                <v-icon dark color="indigo" x-large>add_shopping_cart</v-icon>
              </v-list-item-avatar>
              <v-list-item-content>
                <div class="text-right">
                  <strong>Total Ordered</strong>
                </div>
                <v-list-item-title class="headline mb-1 text-right"
                  ><strong class="primary--text">
                    {{ totalOrder }}
                  </strong></v-list-item-title
                >
              </v-list-item-content>
            </v-list-item>
          </v-card>
        </v-flex>
        <!-- <v-flex sm6 xs12 md6 lg3>
          <v-card class="ma-3">
            <v-list-item>
              <v-list-item-avatar tile >
                <v-sheet color="error" width="80" height="80" elevation="10">
                  <v-icon dark large>playlist_add_check_circle</v-icon>
                </v-sheet>
              </v-list-item-avatar>
              <v-list-item-content>
                <div class="overline text-right">Order Processing</div>
                <v-list-item-title class="headline mb-1 text-right">{{
                  orderInProcessing
                }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-card>
        </v-flex> -->
      </v-layout>
    </v-container>
    <template>
      <v-card>
        <v-card-title>
          <h3>Order Reports</h3>
          <v-spacer></v-spacer>
          <v-text-field
            v-model="search"
            append-icon="mdi-magnify"
            label="Search"
            single-line
            hide-details
          ></v-text-field>
        </v-card-title>
        <v-data-table :headers="headers" :items="orderList" :search="search"
          ><template v-slot:[`item.orderStatus`]="{ item }">
            <v-chip small label :color="getColor(item.orderStatus)">
              <h3>{{ item.orderStatus }}</h3>
            </v-chip>
          </template>
          <template v-slot:[`item.actions`]="{ item }">
            <router-link
              class="text-deco"
              :to="{ name: 'order_detail', params: { orderId: item.orderId } }"
            >
              <v-icon color="primary" class="mr-2"> pageview </v-icon>
            </router-link>
          </template>
        </v-data-table>
      </v-card>
    </template>
  </div>
</template>
<script>
import SideBar from "@/components/SideBar.vue";
import utils from "@/utils/utils";
export default {
  components: {
    SideBar,
  },
  data: () => ({
    search: "",
    headers: [
      { text: "ID", value: "orderId" },
      { text: "TableNo.", value: "orderTable.tableName" },
      { text: "Waiter Name", value: "orderUser.userFullName" },
      { text: "Status", value: "orderStatus" },
      { text: "Total (Ks)", value: "orderTotal" },
      { text: "Detail", value: "actions" },
    ],

    orderList: [],
  }),
  async created() {
    await this.fetchAllOrders();
  },
  computed: {
    totalAmount: function () {
      var sum = 0;
      this.orderList.forEach((e) => {
        if (e.orderStatus == "DONE") sum += e.orderTotal;
      });
      return sum;
    },
    totalOrder: function () {
      var count = 0;
      count += this.orderList.length;
      return count;
    },
    orderInProcessing: function () {
      var count = 0;
      this.orderList.forEach((e) => {
        if (e.orderStatus == "PROCESSING") count += 1;
      });
      return count;
    },
  },
  methods: {
    // <!-- -------------****** get all category from utils.http ******-------------------------- -->
    async fetchAllOrders() {
      const response = await utils.http.get("/user/order/list");
      if (response) {
        const data = await response.json();
        if (data) this.orderList = data;
      }
    },
    // <!-- -------------****** save Category from utils.http ******-------------------------- -->

    getColor(orderStatus) {
      if (orderStatus === "PROCESSING") return "error";
      else return "success";
    },
  },
};
</script>

<style>
.text-deco {
  text-decoration: none;
}
</style>
