import Hello from '@/components/Hello'
import HomePage from '@/components/HomePage'
import LoginPage from '@/components/LoginPage'
import SignupPage from '@/components/SignupPage'
import Vue from 'vue'
import Router from 'vue-router'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/Home',
      name: 'HomePage',
      component: HomePage
    },
    {
      path: '/Login',
      name: 'LoginPage',
      component: LoginPage
    },
    {
      path: '/SignUp',
      name: 'SignupPage',
      component: SignupPage
    },
  ]
})
