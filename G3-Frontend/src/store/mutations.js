export const setLoginUser = (state, user) => {
  sessionStorage.setItem("loginUser", JSON.stringify(user));
  sessionStorage.setItem("isLogin", true);
  state.loginUser = user;
  state.isLogin = true;
};
export const logout = (state) => {
  sessionStorage.removeItem("loginUser");
  sessionStorage.removeItem("isLogin");
  state.loginUser = {};
  state.isLogin = false;
};
export const removeDiscount = (state) => {
  sessionStorage.removeItem("discount");
  state.discount = {};
};
export const discount = (state, discount) => {
  sessionStorage.setItem("discount", JSON.stringify(discount));
  state.discount = discount;
};
export const SET_FOODS = (state, foods) => {
  state.foods = foods;
};
export const SET_ORDER = (state, order) => {
  state.order = order;
};

export const CHOOSE_TABLE = (state, table) => {
  // state.order.table = { ...table };
  state.table = table;
};
export const ADD_FOOD_TO_ORDER = (state, { food, quantity }) => {
  let foodInCart = state.cart.find((item) => {
    return item.food.foodId == food.foodId;
  });
  if (foodInCart) {
    foodInCart.quantity += quantity;
    return;
  }
  state.cart.push({
    food,
    quantity,
  });
};
export const REMOVE_FOOD_FROM_ORDER = (state, food) => {
  state.cart = state.cart.filter((item) => {
    return item.food.foodId !== food.foodId;
  });
};
export const INCREASE = (state, food) => {
  state.cart = state.cart.filter((item) => {
    if (item.food.foodId == food.foodId) item.quantity++;
    return item;
  });
};

export const DECREASE = (state, food) => {
  state.cart = state.cart.filter((item) => {
    if (item.food.foodId == food.foodId && item.quantity > 1) item.quantity--;
    return item;
  });
};

export const UPDATE_FOOD_TO_ORDER = (state, { food, quantity }) => {
  let foodInCart = state.table.table.order.orderItems.find((item) => {
    return item.food.foodId == food.foodId;
  });
  if (foodInCart) {
    state.table.table.order.orderItems.quantity += quantity;
    return;
  }
  state.table.table.order.orderItems.push({
    food,
    quantity,
  });
  // let total = 0;
  // state.table.table.order.orderItems.forEach((item) => {
  //   total += item.food.foodPrice * item.quantity;
  // });
  // return total;
};

export const UPDATE_REMOVE_FOOD_FROM_ORDER = (state, food) => {
  state.table.table.order.orderItems =
    state.table.table.order.orderItems.filter((item) => {
      return item.food.foodId !== food.foodId;
    });
};
export const UPDATE_INCREASE = (state, food) => {
  state.table.table.order.orderItems =
    state.table.table.order.orderItems.filter((item) => {
      if (item.food.foodId == food.foodId) item.quantity++;
      return item;
    });
};

export const UPDATE_DECREASE = (state, food) => {
  state.table.table.order.orderItems =
    state.table.table.order.orderItems.filter((item) => {
      if (item.food.foodId == food.foodId && item.quantity > 1) item.quantity--;
      return item;
    });
};
export const CANCEL_ORDER = (state) => {
  state.cart = [];
};
