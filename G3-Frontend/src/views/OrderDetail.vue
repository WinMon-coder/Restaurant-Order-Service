<template>
  <div>
    <SideBar />
    <v-row>
      <v-spacer></v-spacer>
      <v-col cols="12" md="5">
        <v-card elevation="2" class="mt-6">
          <v-container>
            <span class="ml-3">
              Date:
              {{ order.updatedAt | formatDate }}
            </span>
            <v-row class="ml-2 mr-2 mt-1">
              <v-col cols="12" md="8">
                <strong class="primary--text"
                  >Bills For : Table - {{ order.orderTable.tableName }}
                  <v-divider class="mx-4" vertical></v-divider>
                  Order Id :
                  {{ order.orderId }}
                </strong>
              </v-col>

              <v-col cols="12" md="4" class="status">
                <v-chip
                  small
                  v-if="order.orderStatus == 'DONE'"
                  color="success"
                  label
                  ><h3>Status : {{ order.orderStatus }}</h3></v-chip
                ><v-chip small v-else color="error" label
                  ><h3>Status : {{ order.orderStatus }}</h3></v-chip
                >
              </v-col>
            </v-row>

            <!-- ---------------------------- Order Items ---------------------------- -->
            <v-list
              subheader
              two-line
              class="mt-1"
              v-for="item in order.orderItems"
              :key="item.orderItemId"
            >
              <v-list-item>
                <v-list-item-avatar rounded color="grey lighten-4">
                  <v-img :src="localDomain + item.food.foodPhoto"></v-img>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title class="subtitle-2">{{
                    item.food.foodName | capitalising
                  }}</v-list-item-title>
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
            <!-- <v-toolbar flat>
      <strong>Subtotal</strong><v-spacer></v-spacer><strong>Ks</strong>
    </v-toolbar>
    <v-toolbar flat class="mt-n6">
      <span>Tax(10%)</span><v-spacer></v-spacer><span>350Ks</span>
    </v-toolbar> -->
            <v-divider class="mx-4"></v-divider>

            <v-container class="mt-2" v-if="order.orderDiscountTotal > 0">
              <v-row class="ml-1 mr-1">
                <span>Sub Total :</span>
                <v-spacer></v-spacer>
                <span>
                  {{ order.orderTotal + order.orderDiscountTotal }}Ks
                </span>
              </v-row>
              <v-row class="ml-1 mr-1">
                <span class="error--text">Discount :</span>
                <v-spacer></v-spacer>
                <span class="error--text">
                  {{ order.orderDiscountTotal }}Ks
                </span>
              </v-row>
              <v-row class="ml-1 mr-1 mt-6 mb-2">
                <strong class="black--text">Total :</strong>
                <v-spacer></v-spacer>
                <strong class="black--text">{{ order.orderTotal }}Ks</strong>
              </v-row>
            </v-container>

            <v-toolbar v-else flat>
              <strong>Total</strong><v-spacer></v-spacer
              ><strong>{{ order.orderTotal }}Ks</strong>
            </v-toolbar>

            <v-btn @click.prevent="backTo()" class="ma-2" color="primary ">
              <v-icon left> mdi-arrow-left </v-icon>Back
            </v-btn>

            <!-- ---------------------------- End Total  ---------------------------- -->

            <!-- ---------------------------- Payment Method ---------------------------- -->
          </v-container>

          <!-- <v-list
            subheader
            two-line
            class="mt-1"
            v-for="item in order.orderItems"
            :key="item.orderItemId"
          >
            <v-list-item>
              <v-list-item-avatar rounded color="grey lighten-4">
                <v-img :src="localDomain + item.food.foodPhoto"></v-img>
              </v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title class="subtitle-2">{{
                  item.food.foodName
                }}</v-list-item-title>
                <v-list-item-subtitle>
                  {{ item.quantity }} X
                  <strong>{{ item.food.foodPrice }} Ks</strong>
                </v-list-item-subtitle>
              </v-list-item-content>
              <v-list-item-action class="caption"
                >{{ item.quantity * item.food.foodPrice }}Ks
              </v-list-item-action>
            </v-list-item>
           
          </v-list> -->
        </v-card>
      </v-col>
      <v-spacer></v-spacer>
    </v-row>
  </div>
</template>

<script>
import SideBar from "@/components/SideBar.vue";
import utils from "@/utils/utils";
export default {
  props: ["orderId"],
  components: {
    SideBar,
  },
  data: () => ({
    localDomain: utils.constant.localDomain,
  }),

  mounted() {
    this.$store.dispatch("getOrder", this.orderId);
  },
  computed: {
    order() {
      return this.$store.state.order;
    },
  },
  methods: {
    backTo() {
      this.$router.go(-1);
    },
  },
};
</script>

<style>
.status {
  text-align: right;
}
</style>
