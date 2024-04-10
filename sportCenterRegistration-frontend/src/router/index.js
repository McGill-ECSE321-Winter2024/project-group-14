import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import ManageInstructor from '@/components/ManageInstructor'
import ManageCustomer from '@/components/ManageCustomer'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/Home',
      name: 'Home',
      component: Home
    },
    {
      path: '/ManageInstructor',
      name: 'ManageInstructor',
      component: ManageInstructor
    },
    {
      path: '/ManageCustomer',
      name: 'ManageCustomer',
      component: ManageCustomer
    }
  ]
})
