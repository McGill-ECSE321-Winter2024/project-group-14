import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import Welcome from '@/components/Welcome'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/hello',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/welcome',
      name: 'Welcome',
      component: Welcome
    }
  ]
})
