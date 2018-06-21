// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store/index'
import ElementUI from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'element-ui/lib/theme-default/index.css'
import { getToken } from '@/utils/cookie' // getToken from cookie
//import './permission' // permission control

import axios from 'axios'

axios.defaults.withCredentials=true;

Vue.use(ElementUI)
Vue.config.productionTip = false

router.beforeEach((to, from, next) => {
  NProgress.start() // start progress bar
  if (getToken()) { // determine if there has token
    /* has token*/
    if (to.path === '/login') {
      //若不过滤，则会报异常
      next()
      NProgress.done() // if current page is dashboard will not trigger afterEach hook, so manually handle it
    } else {
     		console.log('获取用户的角色'+store.getters.roles)
        if (store.getters.roles.length === 0) { // 判断当前用户是否已拉取完user_info信息
          store.dispatch('GetUserInfo').then(res => { // 拉取user_info
            var roles = res.data.data[0].nickName // note: roles must be a array! such as: ['editor','develop']
            console.log('角色： '+roles)
            store.dispatch('GenerateRoutes', { roles }).then(() => { // 根据roles权限生成可访问的路由表
            	console.log('路径： '+store.getters.addRouters)
              router.addRoutes(store.getters.addRouters) // 动态添加可访问路由表
              next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
            })
          }).catch(() => {
            store.dispatch('FedLogOut').then(() => {
              Message.error('Verification failed, please login again')
              next({ path: '/login' })
            })
          })
        } else {
          next()
        }
    }
  } else {
    /* has no token*/
      next() // 否则全部重定向到登录页
      NProgress.done() // if current page is login will not trigger afterEach hook, so manually handle it
  }
})

router.afterEach(() => {
  NProgress.done() // finish progress bar
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  router,
  components: { App },
  template: '<App/>'
})
