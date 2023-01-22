<template>
  <!-- ---------------------------- Bill Payment SideBar  ---------------------------- -->
  <v-navigation-drawer app color="white" right width="320">
    <v-row>
      <v-col cols="12" md="7">
        <v-list subheader two-line class="mt-1">
          <v-list-item to="/profile">
            <v-list-item-avatar rounded>
              <img
                v-if="loginUser.userPhoto != null"
                :src="localDomain + loginUser.userPhoto"
                max-width="50"
                max-height="50"
                contain
              />
              <img v-else src="../assets/profile3.jpeg" />
            </v-list-item-avatar>
            <v-list-item-content>
              <div
                v-for="(role, index) in this.loginUser.userRoles"
                :key="index"
              >
                <v-list-item-subtitle>
                  <h5 class="primary--text">{{ role.roleName }}</h5>
                </v-list-item-subtitle>
              </div>
              <v-list-item-title
                ><strong>{{
                  loginUser.userFullName
                }}</strong></v-list-item-title
              >
            </v-list-item-content>
          </v-list-item>
        </v-list></v-col
      >
      <v-col cols="12" md="5">
        <v-container class="mt-3" v-if="getTable.table">
          <v-chip small color="success" label class="white--text"
            ><h3>Table - {{ getTable.table.tableName }}</h3></v-chip
          >
        </v-container>
        <v-container class="mt-3" v-else>
          <v-chip small color="error" label class="white--text"
            ><h3>No Table</h3></v-chip
          >
        </v-container>
      </v-col>
    </v-row>
    <div v-if="discount.isActive == true">
      <p class="got-dis-text">
        Got {{ discount.discountPercentage }} % discount. <br />
        If you spend mininum {{ discount.mininumOrderAmount }} Ks.
      </p>
    </div>

    <strong class="ml-3">INVOICE</strong><br />
    <!-- <span class="ml-3">Date : {{ timestamp }}</span> -->

    <!-- ---------------------------- Order Items ---------------------------- -->
    <!-- ************************************************************************************ -->

    <span v-if="getTable.table.order.orderItems != null">
      <div
        v-for="(item, index) in getTable.table.order.orderItems"
        :key="index"
      >
        <v-list subheader two-line>
          <v-list-item>
            <v-list-item-avatar rounded color="grey lighten-4">
              <v-img :src="localDomain + item.food.foodPhoto"></v-img>
            </v-list-item-avatar>
            <v-row>
              <v-col cols="12" md="6">
                <v-list-item-content>
                  <v-list-item-title>
                    {{ item.food.foodName | capitalising }}
                  </v-list-item-title>
                  <v-list-item-subtitle
                    >{{ item.quantity }} X {{ item.food.foodPrice }}Ks
                    <!-- <v-btn plain color="#800015" small
                >No Ice
                <v-icon right>mdi-pencil</v-icon>
              </v-btn> -->
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-col>
              <v-col cols="12" md="6">
                <v-list-item class="justify center text-center">
                  <v-btn
                    fab
                    dark
                    x-small
                    color="primary"
                    @click.prevent="updateIncreaseFoodItem(item.food)"
                  >
                    <v-icon dark>mdi-plus</v-icon>
                  </v-btn>

                  <v-btn
                    fab
                    dark
                    x-small
                    color="primary"
                    @click.prevent="updateDecreaseFoodItem(item.food)"
                  >
                    <v-icon dark>mdi-minus</v-icon>
                  </v-btn>
                </v-list-item>
              </v-col>
            </v-row>
            <v-list-item-action class="caption"
              >{{ item.quantity * item.food.foodPrice }}Ks
              <v-icon
                x-small
                class="red--text"
                @click.prevent="updateRemoveFoodFromOrder(item.food)"
              >
                fas fa-times
              </v-icon>
            </v-list-item-action>
          </v-list-item>
        </v-list>
      </div>

      <!-- ---------------------------- End Order Items  ---------------------------- -->
      <v-divider class="mx-4"></v-divider>

      <v-container
        class="mt-2"
        v-if="
          discount.isActive == true &&
          updateOrderTotalPrice >= discount.mininumOrderAmount
        "
      >
        <v-row class="ml-1 mr-1">
          <span>Sub Total :</span>
          <v-spacer></v-spacer>
          <span> {{ updateOrderTotalPrice }}Ks </span>
        </v-row>
        <v-row class="ml-1 mr-1">
          <span class="error--text"
            >Discount ({{ discount.discountPercentage }}% )</span
          >
          <v-spacer></v-spacer>
          <span class="error--text">
            {{
              (addOnDiscount =
                updateOrderTotalPrice * (discount.discountPercentage / 100))
            }}Ks
          </span>
        </v-row>
        <v-row class="ml-1 mr-1">
          <h2>Total :</h2>
          <v-spacer></v-spacer>
          <h2>{{ (addOnTotal = updateOrderTotalPrice - addOnDiscount) }}Ks</h2>
        </v-row>
      </v-container>

      <v-toolbar v-else flat>
        <strong>Total</strong><v-spacer></v-spacer
        ><strong>{{ (addOnTotal = updateOrderTotalPrice) }}Ks</strong>
      </v-toolbar>
      <!-- ---------------------------- End Total  ---------------------------- -->
      <v-item-group mandatory class="mt-n1">
        <v-container>
          <v-row class="mt-1">
            <v-col>
              <v-btn
                color="#F6EFEF"
                block
                dark
                class="widthoutupercase"
                @click.prevent="updateOrder()"
                >Order
              </v-btn>
            </v-col>
            <v-col>
              <v-btn
                color="#F6EFEF"
                block
                dark
                class="widthoutupercase"
                @click.prevent="backTo()"
                >Cancel</v-btn
              >
            </v-col>
          </v-row>
        </v-container>
      </v-item-group>
    </span>
    <!-- ************************************************************************************ -->
    <span v-else>
      <div v-for="(item, index) in cart" :key="index">
        <v-list subheader two-line>
          <v-list-item>
            <v-list-item-avatar rounded color="grey lighten-4">
              <v-img :src="localDomain + item.food.foodPhoto"></v-img>
            </v-list-item-avatar>
            <v-row>
              <v-col cols="12" md="6">
                <v-list-item-content>
                  <v-list-item-title>
                    {{ item.food.foodName | capitalising }}
                  </v-list-item-title>
                  <v-list-item-subtitle
                    >{{ item.quantity }} X {{ item.food.foodPrice }}Ks
                    <!-- <v-btn plain color="#800015" small
                >No Ice
                <v-icon right>mdi-pencil</v-icon>
              </v-btn> -->
                  </v-list-item-subtitle>
                </v-list-item-content>
              </v-col>
              <v-col cols="12" md="6">
                <v-list-item class="justify center text-center">
                  <v-btn
                    fab
                    dark
                    x-small
                    color="primary"
                    @click.prevent="increaseFoodItem(item.food)"
                  >
                    <v-icon dark>mdi-plus</v-icon>
                  </v-btn>

                  <v-btn
                    fab
                    dark
                    x-small
                    color="primary"
                    @click.prevent="decreaseFoodItem(item.food)"
                  >
                    <v-icon dark>mdi-minus</v-icon>
                  </v-btn>
                </v-list-item>
              </v-col>
            </v-row>
            <v-list-item-action class="caption"
              >{{ item.quantity * item.food.foodPrice }}Ks
              <v-icon
                x-small
                class="red--text"
                @click.prevent="removeFoodFromOrder(item.food)"
              >
                fas fa-times
              </v-icon>
            </v-list-item-action>
          </v-list-item>
        </v-list>
      </div>

      <!-- ---------------------------- End Order Items  ---------------------------- -->

      <v-divider class="mx-4"></v-divider>

      <v-container
        class="mt-2"
        v-if="
          discount.isActive == true &&
          orderTotalPrice >= discount.mininumOrderAmount
        "
      >
        <v-row class="ml-1 mr-1">
          <span>Sub Total :</span>
          <v-spacer></v-spacer>
          <span> {{ orderTotalPrice }}Ks </span>
        </v-row>
        <v-row class="ml-1 mr-1">
          <span class="error--text"
            >Discount ({{ discount.discountPercentage }}% )</span
          >
          <v-spacer></v-spacer>
          <span class="error--text">
            {{
              (discountAmount =
                orderTotalPrice * (discount.discountPercentage / 100))
            }}Ks
          </span>
        </v-row>
        <v-row class="ml-1 mr-1">
          <h2>Total :</h2>
          <v-spacer></v-spacer>
          <h2>{{ (total = orderTotalPrice - discountAmount) }}Ks</h2>
        </v-row>
      </v-container>

      <v-toolbar v-else flat>
        <strong>Total</strong><v-spacer></v-spacer
        ><strong>{{ (total = orderTotalPrice) }}Ks</strong>
      </v-toolbar>

      <!-- ---------------------------- End Total  ---------------------------- -->
      <v-item-group mandatory class="mt-n1">
        <v-container>
          <v-row class="mt-1">
            <v-col>
              <v-btn
                color="#F6EFEF"
                block
                dark
                class="widthoutupercase"
                @click.prevent="saveOrder()"
                >Order
              </v-btn>
            </v-col>
            <v-col>
              <v-btn
                color="#F6EFEF"
                block
                dark
                class="widthoutupercase"
                @click.prevent="cancelOrder()"
                >Cancel</v-btn
              >
            </v-col>
          </v-row>
        </v-container>
      </v-item-group>
    </span>
  </v-navigation-drawer>
</template>

<script>
import utils from "@/utils/utils";
import { mapGetters, mapActions, mapState } from "vuex";
export default {
  name: "SideBarRight",
  components: {},

  data: () => ({
    //  timestamp: "",
    localDomain: utils.constant.localDomain,
    loading: false,
    errorAlert: false,
    discountAmount: 0,
    addOnDiscount: 0,
    total: 0,
    addOnTotal: 0,
    orderItem: "",
    loginUser: {},
    discount: {},
  }),
  async created() {
    this.loginUser = this.$store.getters.loginUser;
    await this.fetchUserProfile();
    await this.fetchActiveDiscount();
    setInterval(this.getNow, 1000);
  },
  computed: {
    // orderTotalPrice() {
    //   return this.$store.getters.orderTotalPrice;
    // },
    ...mapGetters([
      "orderTotalPrice",
      "updateOrderTotalPrice",
      "getTable",
      "getFoodList",
    ]),
    ...mapState(["cart"]),
  },
  methods: {
    // getNow: function () {
    //   const today = new Date();
    //   const date =
    //     today.getFullYear() +
    //     "/" +
    //     (today.getMonth() + 1) +
    //     "/" +
    //     today.getDate();
    //   const dateTime = date;
    //   this.timestamp = dateTime;
    // },

    // increase(food) {
    //   this.$store.dispatch("IncreaseProdcut", food);
    // },
    // decrease(food) {
    //   this.$store.dispatch("DecreaseProduct", food);
    // },
    // removeFoodFromOrder(food) {
    //   this.$store.dispatch("removeFoodFromOrder", food);
    // },
    // cancelOrderItems() {
    //   this.$store.dispatch("cancelOrderItems");
    // },
    ...mapActions([
      "increaseFoodItem",
      "decreaseFoodItem",
      "removeFoodFromOrder",
      "updateIncreaseFoodItem",
      "updateDecreaseFoodItem",
      "updateRemoveFoodFromOrder",
      "cancelOrder",
    ]),
    backTo() {
      this.$router.go(-1);
    },
    async saveOrder() {
      if (!(this.orderTotalPrice <= 0) && this.getTable.table != null) {
        const resp = await utils.http.post("/user/order/create", {
          orderTable: this.getTable.table,
          orderItems: this.cart,
          orderUser: this.loginUser,
          orderTotal: this.total,
          orderDiscountTotal: this.discountAmount,
        });

        if (resp && resp.status === 200) {
          this.$router.push({ path: "/table" });
          this.$router.go(0);
        }
      }
    },

    async updateOrder() {
      const resp = await utils.http.put(
        "/user/order/update/" + this.getTable.table.order.orderId,
        {
          orderId: this.getTable.table.order.orderId,
          orderTable: this.getTable.table,
          orderItems: this.getTable.table.order.orderItems,
          orderUser: this.loginUser,
          orderTotal: this.addOnTotal,
          orderDiscountTotal: this.addOnDiscount,
        }
      );

      if (resp && resp.status === 200) {
        this.$router.push({ path: "/table" });
        this.$router.go(0);
      }
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
    async fetchActiveDiscount() {
      const response = await utils.http.get("/discount/active");

      if (response.status === 200) {
        const data = await response.json();
        if (data) this.discount = data;
        console.log(data);
      }
    },
  },
};
</script>

<style>
.v-card.borderme {
  border: 2px solid #800015 !important;
}
.col-12 {
  padding: 5px !important;
}
.v-btn.widthoutupercase {
  color: #800015 !important;
  text-transform: none !important;
}
.v-btn:hover {
  background-color: #800015 !important;
  color: white !important;
}
.v-btn--fab.v-size--x-small {
  height: 15px;
  width: 15px;
  margin: 2px;
}
div > p.got-dis-text {
  font-size: 14px;
  text-align: center;
  color: indigo;
  font-family: "Ubuntu";
}
</style>
