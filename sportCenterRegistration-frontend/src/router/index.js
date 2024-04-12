import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import ManageInstructor from '@/components/Owner/ManageInstructor'
import ManageCustomer from '@/components/Owner/ManageCustomer'
import OwnerApp from '@/components/OwnerApp'
import CustomerApp from '@/components/CustomerApp'
import AddClasses from '@/components/Customer/AddClasses'
import ReviewClasses from '@/components/Customer/ReviewClasses'
import LoginPage from '@/components/LoginPage'
import SignupPage from '@/components/SignupPage'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/ownerApp/',
      name: 'OwnerApp',
      component: OwnerApp,
      // redirect: '/home',
      children: [
        { path: 'manageInstructor', name: 'ManageInstructor', component: ManageInstructor },
        { path: 'manageCustomer', name: 'ManageCustomer', component: ManageCustomer },
      ]
    },
    {
      path: '/customerApp/',
      name: 'CustomerApp',
      component: CustomerApp,
      // redirect: '/home',
      children: [
        { path: 'addClasses', name: 'AddClasses', component: AddClasses },
        { path: 'reviewClasses', name: 'ReviewClasses', component: ReviewClasses },
      ]
    },
    {
      path: '/login',
      name: 'Login',
      component: LoginPage
    },
    {
      path: '/signup',
      name: 'Signup',
      component: SignupPage
    }
  ]
})
