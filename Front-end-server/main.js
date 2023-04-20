import App from './App'
import store from 'store/index.js'

// #ifndef VUE3
import Vue from 'vue'
import cuCustom from 'colorui/components/cu-custom.vue'
import cusTab from 'components/cusTab.vue';
import uView from '@/uni_modules/uview-ui'

import axios from 'axios';

Vue.use(uView)
Vue.component('cu-custom',cuCustom)
Vue.component("cusTab",cusTab)

Vue.prototype.axios = axios;

Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
    ...App,
	store //注入状态机
})
app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
export function createApp() {
  const app = createSSRApp(App)
  return {
    app
  }
}
// #endif