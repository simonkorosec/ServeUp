import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import "./registerServiceWorker";



// @ts-ignore
import Print from 'vue-print-nb'
// @ts-ignore
import VueSession from 'vue-session'

Vue.config.productionTip = false;


new Vue({
  router,
  store,
  render: h => h(App)

}).$mount("#app");

const options = {
    name: '_blank',
    specs: [
        'fullscreen=yes',
        'titlebar=yes',
        'scrollbars=yes'
    ],
    styles: [
        'https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css',
        'https://unpkg.com/kidlat-css/css/kidlat.css'
    ]
};
Vue.use(VueSession);
Vue.use(Print);



