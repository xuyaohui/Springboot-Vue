import http from '@/utils/http'

export  function loginByUsername(userId, userPass, picCode) {
  const data = {
    userId,
    userPass,
    picCode
  }
  return http.post('/login/login.do', data)
}

//登出操作，暂未实现
export function logout() {
  return request({
    url: '/login/logout',
    method: 'post'
  })
}

//根据token和userId获取用户信息
export function getUserInfo(userId) {
  const data={
    userId
  }
  return http.get('/user/api/getUserInfo',data)
}


export function getPicCode() {
  return http.post('/login/getPicCode')
}