// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
// 引用api文件，并将api方法绑定到全局
import api from './api/index'
// 引入axios,并绑定到全局
import axios from 'axios'

Vue.config.productionTip = false
Vue.use(ElementUI)

Vue.prototype.$api = api
Vue.prototype.$axios = axios

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
