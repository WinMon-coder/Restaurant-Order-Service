import Vue from "vue";
import Vuetify from "vuetify/lib/framework";
import "@fortawesome/fontawesome-free/css/all.css";
import "material-design-icons-iconfont/dist/material-design-icons.css";
import colors from "vuetify/lib/util/colors";
Vue.use(Vuetify);

export default new Vuetify({
  icons: {
    iconfont: "fa" || "md",
  },
  theme: {
    themes: {
      light: {
        background: colors.grey.lighten2,
        primary: "#800015", // Not automatically applied  #ea4c89
        secondary: "#5a001a",
        accentpink: "#ffbfd1",
        lightpink: "#ff80a4",
        lightgray: "#F6EFEF",
        lightinfo: "#52d0d0",

        error: "#FF5252",
        info: "#2196F3",
        success: "#4CAF50",
        warning: "#FFC107",

        white: "#FFFFFF",
      },
      dark: {
        background: "#fbfbfb",
      },
    },
  },
});
