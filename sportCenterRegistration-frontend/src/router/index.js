import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import ManageInstructor from '@/components/ManageInstructor'
import ManageCustomer from '@/components/ManageCustomer'
import ManageSportClass from '@/components/ManageSportClass'

import OwnerApp from '@/components/OwnerApp'

import ReviewClasses from '@/components/Customer/ReviewClasses'
import AddClasses from '@/components/Customer/AddClasses'
import CustomerApp from '@/components/CustomerApp'

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
        { path: 'manageSportClass', name: 'ManageSportClass', component: ManageSportClass },
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
        { path: 'addClass', name: 'AddClasses', component: NewClassTable },
        { path: 'reviewClasses', name: 'ReviewClasses', component: ReviewClasses },
      ]
    }




  ]
})
