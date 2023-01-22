<template>
  <v-container>
    <h3 class="ml-3">Bills For : Table - {{ getTable.table.tableName }}</h3>
    <!-- ---------------------------- Order Items ---------------------------- -->
    <v-list
      subheader
      two-line
      class="mt-1"
      v-for="item in getTable.table.order.orderItems"
      :key="item.orderItemId"
    >
      <v-list-item>
        <v-list-item-avatar rounded color="grey lighten-4">
          <v-img :src="localDomain + item.food.foodPhoto"></v-img>
        </v-list-item-avatar>
        <v-list-item-content>
          <v-list-item-title class="subtitle-2">
            {{ item.food.foodName | capitalising }}</v-list-item-title
          >
          <v-list-item-subtitle>
            {{ item.quantity }} X
            <strong>{{ item.food.foodPrice }} Ks</strong>
          </v-list-item-subtitle>
        </v-list-item-content>
        <v-list-item-action class="caption"
          >{{ item.quantity * item.food.foodPrice }}Ks
        </v-list-item-action>
      </v-list-item>
    </v-list>

    <!-- ---------------------------- End Order Items  ---------------------------- -->
    <v-divider class="mx-4"></v-divider>

    <v-container class="mt-2" v-if="discount.isActive == true">
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
          {{ getTable.table.order.orderDiscountTotal }}Ks
        </span>
      </v-row>
      <v-row class="ml-1 mr-1 mt-3">
        <strong class="black--text">Total :</strong>
        <v-spacer></v-spacer>
        <strong class="black--text">{{ getTable.table.billTotal }}Ks</strong>
      </v-row>
    </v-container>

    <v-toolbar v-else flat>
      <strong>Total</strong><v-spacer></v-spacer
      ><strong>{{ getTable.table.billTotal }}Ks</strong>
    </v-toolbar>

    <v-toolbar flat>
      <strong>Cash :</strong><v-spacer></v-spacer><v-spacer></v-spacer>
      <strong> {{ onPay }}Ks</strong>
    </v-toolbar>
    <v-divider class="mx-4"></v-divider>
    <v-toolbar flat>
      <strong>Change : </strong><v-spacer></v-spacer>

      <strong v-if="onPay <= 0"> 0Ks </strong>
      <strong v-else> {{ getTable.table.billTotal - onPay }}Ks </strong>
    </v-toolbar>
    <v-form ref="paymentForm" v-model="paymentForm">
      <v-text-field
        type="number"
        v-model="onPay"
        label="By Cash"
        outlined
        clearable
        suffix="MMK"
        min="1"
        :rules="[
          (v) => !!v || 'Required',
          (v) =>
            (v && v >= getTable.table.billTotal) ||
            'Cash must be greater than Order Total Amount',
        ]"
        required
      ></v-text-field>
    </v-form>
    <!-- ---------------------------- End Total  ---------------------------- -->

    <v-row class="mt-2">
      <router-link to="/invoice">Print Invoice</router-link>
      <v-spacer></v-spacer>
      <v-btn @click="closePayment()" text class="mb-2 widthoutupercase"
        >Cancel</v-btn
      >
      <v-btn
        @click="updateOrderStatus(getTable.table.order.orderId)"
        color="primary"
        class="mb-2 widthoutupercase white--text ml-4 mr-4"
        :disabled="!paymentForm"
        >Payment
      </v-btn>
    </v-row>
  </v-container>
</template>

<script>
// import swal from "sweetalert2";
// window.swal = swal;
import { mapGetters } from "vuex";
import utils from "@/utils/utils";
export default {
  data: () => ({
    paymentForm: false,
    discountAmount: 0,
    onPay: "",
    localDomain: utils.constant.localDomain,
    updateDemo: { orderId: "" },
    discount: {},
  }),

  computed: {
    ...mapGetters(["getTable", "updateOrderTotalPrice"]),
  },
  async created() {
    await this.fetchActiveDiscount();
  },
  methods: {
    async fetchActiveDiscount() {
      const response = await utils.http.get("/discount/active");

      if (response.status === 200) {
        const data = await response.json();
        if (data) this.discount = data;
        console.log(data);
      }
    },

    // <!-- -------------****** update user from utils.http ******-------------------------- -->
    async updateOrderStatus(id) {
      if (this.$refs.paymentForm.validate()) {
        this.updateDemo.orderId = id;
        const resp = await utils.http.put("/user/update/order_status/", {
          orderId: this.updateDemo.orderId,
        });

        if (resp && resp.status === 200) {
          this.$router.go(); // current page refresh
        }
      }
    },
    closePayment() {
      this.$emit("payment_dialog");
    },
  },
};
</script>

<style></style>
