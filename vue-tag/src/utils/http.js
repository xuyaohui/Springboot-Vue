'use strict'

import axios from 'axios'
import qs from 'qs'
import { Message } from 'element-ui'
import { baseUrl } from '../conf/env'

axios.interceptors.request.use(config => {
  // loading
  config.headers.Authorization='Bearer '+sessionStorage.getItem('token');
  return config
}, error => {
  return Promise.reject(error)
})

axios.interceptors.response.use(response => {
  return response
}, error => {
  Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
  })
  return Promise.resolve(error.response)
})

function checkStatus (response) {
  // loading
  // 如果http状态码正常，则直接返回数据
  if (response && (response.status === 200 || response.status === 304 || response.status === 400)) {
    return response
    // 如果不需要除了data之外的数据，可以直接 return response.data
  }
  // 异常状态下，把错误信息返回去
  return {
    status: -404,
    msg: '网络异常'
  }
}

function checkCode (res) {
  return res
}

export default {
  post (url, data) {
    return axios({
      method: 'post',
      baseURL: baseUrl,
      url,
      data: data,
      timeout: 10000,
      withCredentials : true
    }).then(
      (response) => {
        return checkStatus(response)
      }
    ).then(
      (res) => {
        return checkCode(res)
      }
    )
  },
  get (url, params) {
    return axios({
      method: 'get',
      baseURL: baseUrl,
      url,
      params, // get 请求时带的参数
      timeout: 10000,
      withCredentials : true
    }).then(
      (response) => {
        return checkStatus(response)
      }
    ).then(
      (res) => {
        return checkCode(res)
      }
    )
  }
}
