import http from '@/utils/http'

export  function searchContent(text) {
  return http.get('/search/getAllFile', text)
}