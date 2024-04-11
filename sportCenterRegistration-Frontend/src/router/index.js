import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import NewClassTable from '@/components/NewClassTable'
import ApproveNewClass from '@/components/ApproveNewClass'
import CustomerAddClass from '@/components/CustomerAddClass'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/app',
      name: 'NewClassTable',
      component: NewClassTable
    },
    {
      path: '/approvenewclass',
      name: 'ApproveNewClass',
      component: ApproveNewClass
    }
    //,
    // {
    // path: '/approve',
    // name: 'ApproveNewClass',
    // component: ApproveNewClass
    // }
  ]
})
