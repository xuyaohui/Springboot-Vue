<template>
  <div>
    <!-- <el-menu-item index="manage"><i class="el-icon-menu"></i>首页</el-menu-item>
          <el-submenu>
            <template slot="title"  v-for="item in routes"><i class="el-icon-document"></i>{{item.name}}
            <template>
              <el-menu-item index="userList">TEST</el-menu-item>
            </template>
            </template>
          </el-submenu> -->


  <template v-for="(item,index) in routes" v-if="!item.hidden">

      <el-submenu :index="index">
        <template slot="title">
          <span>{{item.name}}</span>
        </template>

        <template v-for="child in item.children" v-if="!child.hidden">
          <sidebar-item :is-nest="true" class="nest-menu" v-if="child.children&&child.children.length>0" :routes="[child]" :key="child.path"></sidebar-item>

          <router-link v-else :to="child.path" :key="child.name">
            <el-menu-item :index="item.path+'/'+child.path">
              <span>{{child.name}}</span>
            </el-menu-item>
          </router-link>
        </template>
      </el-submenu>

    </template>

    </div>
</template>

<script>
export default {
  name: 'SidebarItem',
  props: {
    routes: {
      type: Array
    }
  },
  methods: {
  }
}
</script>

