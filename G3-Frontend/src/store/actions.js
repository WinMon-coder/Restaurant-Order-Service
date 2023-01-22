import axios from "axios";

const FOOD_LIST = "http://localhost:8080/food/list";

//const ADD_TO_CART = "http://localhost:8080/user/cart/create";

export const getAllFoods = ({ commit }) => {
  axios.get(FOOD_LIST).then((response) => {
    commit("SET_FOODS", response.data);
  });
};

export const getOrder = ({ commit }, orderId) => {
  axios.get(`http://localhost:8080/order/${orderId}`).then((response) => {
    commit("SET_ORDER", response.data);
    //console.log(response.data);
  });
};

export const chooseTable = ({ commit }, table) => {
  commit("CHOOSE_TABLE", table);
};

export const addFoodToOrder = ({ commit }, { food, quantity }) => {
  commit("ADD_FOOD_TO_ORDER", { food, quantity });
};
export const updateFoodToOrder = ({ commit }, { food, quantity }) => {
  commit("UPDATE_FOOD_TO_ORDER", { food, quantity });
};
export const removeFoodFromOrder = ({ commit }, food) => {
  commit("REMOVE_FOOD_FROM_ORDER", food);
};
export const cancelOrder = ({ commit }) => {
  commit("CANCEL_ORDER");
};
export const increaseFoodItem = ({ commit }, product) => {
  setTimeout(() => {
    commit("INCREASE", product);
  }, 100);
};

export const decreaseFoodItem = ({ commit }, product) => {
  commit("DECREASE", product);
};

export const updateRemoveFoodFromOrder = ({ commit }, food) => {
  commit("UPDATE_REMOVE_FOOD_FROM_ORDER", food);
  // state.table.table.order.orderItems.forEach((item) => {
  //   if (item.food.foodId == food.foodId) {
  //     axios.delete(`http://localhost:8080/order_item/${item.orderItemId}`);
  //   }
  // });
};

export const updateIncreaseFoodItem = ({ commit }, product) => {
  setTimeout(() => {
    commit("UPDATE_INCREASE", product);
  }, 100);
};

export const updateDecreaseFoodItem = ({ commit }, product) => {
  commit("UPDATE_DECREASE", product);
};
