<template>
  <v-container id="element-to-convert" class="mt-4">
    <center><h2>INVOICE</h2></center>

    <v-row>
      <v-spacer></v-spacer>
      <v-col cols="12" md="6">
        <strong>Table-{{ getTable.table.tableName }}</strong>

        <br />
        <strong>Order ID #{{ getTable.table.order.orderId }}</strong>
        <p>Sale Date : {{ getTable.table.createdAt | formatLocalDate }}</p>

        <justify>
          <v-list>
            <v-divider color="black"></v-divider>
            <v-list-item>
              <v-row>
                <v-col cols="12" md="1"><strong>No.</strong> </v-col>
                <v-col cols="12" md="4"><strong>Item</strong> </v-col>
                <v-col cols="12" md="1"><strong>Qty</strong> </v-col>
                <v-col cols="12" md="3"><strong>Unit Price</strong> </v-col>
                <v-col cols="12" md="3"><strong>Amount</strong> </v-col>
              </v-row>
            </v-list-item>
            <v-divider color="black"></v-divider>
          </v-list>
          <v-list
            v-for="(item, index) in getTable.table.order.orderItems"
            :key="index"
          >
            <v-list-item>
              <v-row>
                <v-col cols="12" md="1"
                  ><strong>{{ index + 1 }}</strong>
                </v-col>
                <v-col cols="12" md="4"
                  ><strong>{{ item.food.foodName | capitalising }}</strong>
                </v-col>
                <v-col cols="12" md="1"
                  ><strong> {{ item.quantity }}</strong>
                </v-col>
                <v-col cols="12" md="3"
                  ><strong>{{ item.food.foodPrice }}</strong>
                </v-col>
                <v-col cols="12" md="3"
                  ><strong>{{ item.quantity * item.food.foodPrice }}</strong>
                </v-col>
              </v-row>
            </v-list-item>
          </v-list>

          <v-divider class="mb-2" color="black"></v-divider>
          <v-list>
            <v-list-item>
              <v-row>
                <v-col cols="12" md="1"><strong></strong> </v-col>
                <v-col cols="12" md="4"></v-col>
                <v-col cols="12" md="1"></v-col>
                <v-col cols="12" md="3"><strong>Sub Total : </strong></v-col>
                <v-col cols="12" md="3"
                  ><strong>{{
                    getTable.table.billTotal +
                    getTable.table.order.orderDiscountTotal
                  }}</strong>
                </v-col>
              </v-row>
            </v-list-item>
          </v-list>
          <v-list>
            <v-list-item>
              <v-row>
                <v-col cols="12" md="1"><strong></strong> </v-col>
                <v-col cols="12" md="4"></v-col>
                <v-col cols="12" md="1"></v-col>
                <v-col cols="12" md="3"><strong>Discount : </strong></v-col>
                <v-col cols="12" md="3"
                  ><strong>{{
                    getTable.table.order.orderDiscountTotal
                  }}</strong>
                </v-col>
              </v-row>
            </v-list-item>
          </v-list>
          <v-list>
            <v-list-item>
              <v-row>
                <v-col cols="12" md="1"><strong></strong> </v-col>
                <v-col cols="12" md="4"></v-col>
                <v-col cols="12" md="1"></v-col>
                <v-col cols="12" md="3"><strong>Total :</strong></v-col>
                <v-col cols="12" md="3"
                  ><strong>{{ getTable.table.billTotal }}</strong>
                </v-col>
              </v-row>
            </v-list-item>
          </v-list>
          <v-list>
            <v-list-item>
              <v-row>
                <v-col class="" cols="12">
                  <center>
                    <h5 class="text-center">
                      Thank you , Please come again :)
                    </h5>
                  </center>
                </v-col>
              </v-row>
            </v-list-item>
          </v-list>
        </justify>
      </v-col>
      <v-spacer></v-spacer>
    </v-row>
    <button @click="exportToPDF">Export to PDF</button>
  </v-container>
</template>

<script>
import html2pdf from "html2pdf.js";
import { mapGetters } from "vuex";
import utils from "@/utils/utils";

export default {
  data() {
    return {
      tableList: [],
      activeDiscount: {},
    };
  },

  async created() {
    await this.fetchAllTables();
    await this.fetchActiveDiscount();
  },

  // components: {
  //   BillPayment,
  // },
  computed: {
    ...mapGetters(["getTable", "totalPrice", "getFoodList"]),
  },

  methods: {
    exportToPDF() {
      html2pdf(document.getElementById("element-to-convert"), {
        margin: 1,
        filename: "bill-vouncher.pdf",
      });
    },

    async fetchAllTables() {
      const response = await utils.http.get("/table/list_with_order");
      if (response) {
        const data = await response.json();
        if (data) this.tableList = data;
      } else {
        console.log("sth wrong");
      }
    },

    async fetchActiveDiscount() {
      const resp2 = await utils.http.get("/discount/active/discount");

      if (resp2) {
        console.log(resp2);
        const data = await resp2.json();
        console.log(data);
        this.$store.commit("SELECT_DISCOUNT", data);
        if (data) this.activeDiscount = data;
      }
    },
  },
};
</script>

<style>
/* #app {
      margin-top: 60px;
      text-align: center;
    } */
</style>
