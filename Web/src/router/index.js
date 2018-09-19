import Vue from "vue";
import Router from 'vue-router';
import GraphPage from '../pages/GraphPage.vue';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'GraphPage',
      component: GraphPage,
      meta: {
        title: "Graphhhing"
      }
    },
  ]
})
