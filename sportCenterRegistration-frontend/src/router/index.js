import Vue from 'vue'
import Router from 'vue-router'

import Homepage from '@/components/HomePage'
import Home from '@/components/Home'
import LoginPage from '@/components/LoginPage'
import SignupPage from '@/components/SignupPage'

import OwnerApp from '@/components/OwnerApp'
import ManageInstructor from '@/components/Owner/ManageInstructor'
import ManageCustomer from '@/components/Owner/ManageCustomer'


import CustomerApp from '@/components/CustomerApp'
import ViewRegistrations from '@/components/ViewRegistrations'
import ReviewClasses from '@/components/Customer/ReviewClasses'


import InstructorApp from '@/components/InstructorApp'
import AddNewClass from '@/components/Instructor/AddNewClass'
import ViewMySessions from '@/components/Instructor/ViewMySessions'
import ViewSportClasses from '@/components/Instructor/ViewSportClasses'



import Classes from '@/components/Owner/Classes'
import Sessions from '@/components/Owner/Sessions'
import Registrations from '@/components/Owner/Registrations'
import RegisterToSessions from '@/components/RegisterToSessions'

import Settings from '@/components/Settings'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Homepage',
      component: Homepage,
      // redirect: '/home',
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
        { path: 'settings', name: 'Settings', component: Settings }
      ]
    },
    {
      path: '/customerApp/',
      name: 'CustomerApp',
      component: CustomerApp,
      // redirect: '/home',
      children: [
        { path: 'home', name: 'Home', component: Home },
        { path: 'registerToSessions', name: 'RegisterToSessions', component: RegisterToSessions },
        { path: 'viewRegistrations', name: 'ViewRegistrations', component: ViewRegistrations },
        { path: 'settings', name: 'Settings', component: Settings }
      ]
    },
    {
      path: '/instructorApp/',
      name: 'InstructorApp',
      component: InstructorApp,
      // redirect: '/home',
      children: [
        { path: 'home', name: 'Home', component: Home },
        { path: 'addClasses', name: 'AddClasses', component: AddNewClass },
        { path: 'viewMySessions', name: 'ViewMySessions', component: ViewMySessions },
        { path: 'viewsportclasses', name: 'ViewSportClasses', component: ViewSportClasses },
        { path: 'settings', name: 'Settings', component: Settings },
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
