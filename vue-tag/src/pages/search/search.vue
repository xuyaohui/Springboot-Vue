<template>
    <div class="fillcontain">
        <head-top></head-top>
        <div class="table_container">
        <el-form :inline="true" :model="formInline" class="demo-form-inline" style="text-align:center">
            <el-form-item label="输入要搜索的内容">
              <el-input v-model="formInline.content" placeholder="输入要搜索的内容"></el-input>
            </el-form-item>
            <el-form-item class='searchFont'>
              <el-button type="primary" @click="onSubmit">搜索</el-button>
            </el-form-item>
        </el-form>

        <el-collapse v-model="activeNames" @change="handleChange">
		  <el-collapse-item v-for="(item,index) in searchResult" :title="item.title">
		    <div v-html='item.content'></div>
		  </el-collapse-item>
		</el-collapse>
        </div>
    </div>
</template>

<script>
    import headTop from '../../components/headTop'
    import http from '../../utils/http'
    import store from '../../store/'
	import { searchContent } from '@/api/search'


    export default {
        data(){
            return {
                loading:true,
                formInline:[{
                  content:''
                }],
                searchResult: [{
                	id:'',
                	title:'文章标题(测试)',
                	content:'文章内容(测试)'
                }]
            }
        },
	    beforeCreate(){
            
	    },
      mounted() {
          //this.initData();
      },
      computed: {
      },
    	components: {
    		headTop,
    	},
        methods: {
          	async onSubmit() {
          		return new Promise((resolve, reject) => {
          			var text={
          				text :  this.formInline.content
          			}
		            searchContent(text).then(response => {
		              const data = response.data
		              this.searchResult = data.data 
		              resolve()
		            }).catch(error => {
		              reject(error)
		            })
		          })
          	}
        },
    }
</script>

<style lang="less">
	@import '../../style/mixin';
    .table_container{
        padding: 20px;
    }
    .Pagination{
        display: flex;
        justify-content: flex-start;
        margin-top: 8px;
    }
</style>
