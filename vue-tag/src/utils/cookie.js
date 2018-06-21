import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'
const TokenUserId = 'UserId-Token'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function getUserIdToken() {
  return Cookies.get(TokenUserId)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function setUserIdToken(userId){
	return Cookies.set(TokenUserId, userId)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
