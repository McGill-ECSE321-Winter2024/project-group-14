import Vue from 'vue'
import Router from 'vue-router'

import Homepage from '@/components/HomePage'
import LoginPage from '@/components/LoginPage'
import SignupPage from '@/components/SignupPage'

import OwnerApp from '@/components/OwnerApp'
import ManageInstructor from '@/components/Owner/ManageInstructor'
import ManageCustomer from '@/components/Owner/ManageCustomer'


import CustomerApp from '@/components/CustomerApp'
import AddClasses from '@/components/Customer/AddClasses'
import ReviewClasses from '@/components/Customer/ReviewClasses'


import InstructorApp from '@/components/InstructorApp'
import AddNewClass from '@/components/Instructor/AddNewClass'
import ViewMySessions from '@/components/Instructor/ViewMySessions'
import ViewSportClasses from '@/components/Instructor/ViewSportClasses'

import ApprovingClass from '@/components/ApprovingClass'


import Classes from '@/components/Owner/Classes'
import Sessions from '@/components/Owner/Sessions'
import Registrations from '@/components/Owner/Registrations'


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
        { path: 'manageInstructor', name: 'ManageInstructor', component: ManageInstructor },
        { path: 'manageCustomer', name: 'ManageCustomer', component: ManageCustomer },
        { path: 'classes', name: 'Classes', component: Classes },
        { path: 'sessions', name: 'Sessions', component: Sessions },
        { path: 'registrations', name: 'Registrations', component: Registrations },
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
      path: '/instructorApp/',
      name: 'InstructorApp',
      component: InstructorApp,
      // redirect: '/home',
      children: [
        { path: 'addClasses', name: 'AddClasses', component: AddNewClass },
        { path: 'viewMySessions', name: 'ViewMySessions', component: ViewMySessions },
        { path: 'viewsportclasses', name: 'ViewSportClasses', component: ViewSportClasses },
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
