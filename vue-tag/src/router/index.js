import Vue from 'vue'
import Router from 'vue-router'
import Login from '../pages/login/login'
import Manage from '../pages/home/manage'
import UserList from '../pages/user/UserList'
import Search from '../pages/search/search'

Vue.use(Router)

export const constantRouterMap = [
    {
      path: '/login',
      component: Login,
      name:'登录页',
      hidden : true
    },
    {
      path: '/manage',
      component: Manage,
      name: '用户管理',
      children: [
      {
        path: '',
        component: UserList,
        name:'用户信息管理',
        meta: { title: '用户信息管理1'},
      },
      {
        path: '/userList',
        component: UserList,
        name:'用户信息管理2',
        meta: { title: '用户信息管理2'},
      }]
    },
    {
      path: '/manage',
      component: Manage,
      name: '搜索帮助',
      children: [
      {
        path: '/search',
        component: Search,
        name:'搜索帮助',
        meta: { title: '搜索帮助'},
      }]
    },
    
  ]

export default new Router({
  mode:"history",
  base: '/vue-tag/',
  routes: constantRouterMap
})

//动态需要根据权限加载的路由表 
export const asyncRouterMap = [
  {
    path: '/manage',
    component: Manage,
    name: '权限测试',
    meta: { roles: ['超级管理员','客户服务中心'] }, //页面需要的权限
    children: [
    { 
      path: '/login',
      component: UserList,
      name: '权限测试页',
      meta: { roles: ['超级管理员','客户服务中心'] }  //页面需要的权限
    },
    { 
      path: '/login',
      component: UserList,
      name: '权限测试页2',
      meta: { roles: ['超级管理员','客户服务中心'] }  //页面需要的权限
    }]
  }
  // { path: '*', redirect: '/404', hidden: true }
];