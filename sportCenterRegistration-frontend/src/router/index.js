import Vue from 'vue'
import Router from 'vue-router'
import ManageInstructor from '@/components/Owner/ManageInstructor'
import ManageCustomer from '@/components/Owner/ManageCustomer'
import Classes from '@/components/Owner/Classes'
import Sessions from '@/components/Owner/Sessions'
import Registrations from '@/components/Owner/Registrations'
import Home from '@/components/Home'

import OwnerApp from '@/components/OwnerApp'

import Homepage from '@/components/HomePage'
import LoginPage from '@/components/LoginPage'
import SignupPage from '@/components/SignupPage'
import RegisterToSessions from '@/components/RegisterToSessions'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Homepage',
      component: Homepage
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
    },
    {
      path: '/registerToSessions',
      name: 'RegisterToSessions',
      component: RegisterToSessions
    },
    {
      path: '/ownerApp/',
      name: 'OwnerApp',
      component: OwnerApp,
      // redirect: '/home',
      children: [
        { path: 'home', name: 'Home', component: Home },
        { path: 'manageInstructor', name: 'ManageInstructor', component: ManageInstructor },
        { path: 'manageCustomer', name: 'ManageCustomer', component: ManageCustomer },
        { path: 'classes', name: 'Classes', component: Classes },
        { path: 'sessions', name: 'Sessions', component: Sessions },
        { path: 'registrations', name: 'Registrations', component: Registrations },
      ]
    },
  ]
})
