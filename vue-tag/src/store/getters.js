const getters = {
  token: state => state.user.token,
  name: state => state.user.name,
  roles: state => state.user.roles,
  addRouters: state => state.permission.addRouters,
  permission_routers: state => state.permission.routers
}
export default getters
