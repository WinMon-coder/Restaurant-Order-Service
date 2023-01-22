export const loginUser = (state) => {
  let loginUser = sessionStorage.getItem("loginUser");
  if (loginUser != null) {
    state.loginUser = JSON.parse(loginUser);
    return JSON.parse(loginUser);
  } else {
    return state.loginUser;
  }
};
export const isLogin = (state) => {
  let isLogin = sessionStorage.getItem("isLogin");
  if (isLogin != null) {
    state.isLogin = isLogin;
    return isLogin;
  } else {
    return state.isLogin;
  }
};

export const discount = (state) => {
  return state.discount;
};

export const getTable = (state) => {
  return state.table;
};
export const orderTotalPrice = (state) => {
  let total = 0;
  state.cart.forEach((item) => {
    total += item.food.foodPrice * item.quantity;
    // if (state.discount != null) {
    //   discountAmount = (total * state.discount.discountPercentage) / 100;
    // }
  });
  return total;
};
export const updateOrderTotalPrice = (state) => {
  let total = 0;
  state.table.table.order.orderItems.forEach((item) => {
    total += item.food.foodPrice * item.quantity;
    // if (state.discount != null) {
    //   discountAmount = (total * state.discount.discountPercentage) / 100;
    // }
  });
  return total;
};
