<template>
    <div class="fillcontain">
        <head-top></head-top>
        <div class="table_container">
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
            <el-form-item label="用户ID">
              <el-input v-model="formInline.user" placeholder="用户ID"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">查询</el-button>
            </el-form-item>
        </el-form>
            <el-table
            	v-loading="loading" 
            	element-loading-text="拼命加载中"
			    element-loading-spinner="el-icon-loading"
			    element-loading-background="rgba(0, 0, 0, 0.8)"
                :data="tableData"
                style="width: 100%">
                <el-table-column type="expand">
                  <template scope="props">
                    <el-form label-position="left" inline class="demo-table-expand">
                      <el-form-item label="用户ID">
                        <span>{{ props.row.userId }}</span>
                      </el-form-item>
                      <el-form-item label="用户名称">
                        <span>{{ props.row.userName }}</span>
                      </el-form-item>
                      <el-form-item label="密码">
                        <span>{{ props.row.userPass }}</span>
                      </el-form-item>
                      <el-form-item label="部门名称">
                        <span>{{ props.row.departName }}</span>
                      </el-form-item>
                      <el-form-item label="联系电话">
                        <span>{{ props.row.userPhone }}</span>
                      </el-form-item>
                      <el-form-item label="加入时间">
                        <span>{{ props.row.createTime }}</span>
                      </el-form-item>
                      <el-form-item label="用户邮箱">
                        <span>{{ props.row.userEmail }}</span>
                      </el-form-item>
                      <el-form-item label="IP地址">
                        <span>{{ props.row.userIp }}</span>
                      </el-form-item>
                    </el-form>
                  </template>
                </el-table-column>
                <el-table-column
                  label="用户ID"
                  prop="userId">
                </el-table-column>
                <el-table-column
                  label="用户名称"
                  prop="userName">
                </el-table-column>
                <el-table-column
                  label="邮箱"
                  prop="userEmail">
                </el-table-column>
                <el-table-column label="操作" width="200">
                  <template scope="scope">
                    <el-button
                      size="mini"
                      @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-button
                      size="mini"
                      type="danger"
                      @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                  </template>
                </el-table-column>
            </el-table>
            <div class="Pagination">
                <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page="currentPage"
                  :page-size="20"
                  layout="total, prev, pager, next"
                  :total="count">
                </el-pagination>
            </div>
            <el-dialog title="修改用户信息" v-model="dialogFormVisible">
                <el-form :model="selectTable">
                    <el-form-item label="用户名称" label-width="100px">
                        <el-input v-model="selectTable.userName" auto-complete="off"></el-input>
                    </el-form-item>
                    <!-- <el-form-item label="详细地址" label-width="100px">
                        <el-autocomplete
                          v-model="address.address"
                          :fetch-suggestions="querySearchAsync"
                          placeholder="请输入地址"
                          style="width: 100%;"
                          @select="addressSelect"
                        ></el-autocomplete>
                        <span>当前城市：{{city.name}}</span>
                    </el-form-item> -->
                    <el-form-item label="密码" label-width="100px">
                        <el-input v-model="selectTable.userPass"></el-input>
                    </el-form-item>
                    <el-form-item label="联系电话" label-width="100px">
                        <el-input v-model="selectTable.userPhone"></el-input>
                    </el-form-item>
                    <el-form-item label="所属部门" label-width="100px">
                        <el-cascader
                          :options="categoryOptions"
                          v-model="selectedCategory"
                          change-on-select
                        ></el-cascader>
                    </el-form-item>
                    <!-- <el-form-item label="商铺图片" label-width="100px">
                        <el-upload
                          class="avatar-uploader"
                          :show-file-list="false"
                          :on-success="handleServiceAvatarScucess"
                          :before-upload="beforeAvatarUpload">
                        </el-upload>
                    </el-form-item> -->
                </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="updateShop">确 定</el-button>
              </div>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import headTop from '../../components/headTop'
    import http from '../../utils/http'
    import store from '../../store/'
    export default {
        data(){
            return {
                city: {},
                offset: 0,
                limit: 20,
                count: 0,
                tableData: [],
                currentPage: 1,
                selectTable: {},
                dialogFormVisible: false,
                categoryOptions: [],
                selectedCategory: [],
                address: {},
                formInline:[{
                  user:''
                }],
                loading:true,
            }
        },
	    beforeCreate(){
            
	    },
      mounted() {
          this.initData();
      },
      computed: {
      },
    	components: {
    		headTop,
    	},
        methods: {
          	initData(){
          		var info={
          			currentPage:this.currentPage,
          			limit:this.limit,
          			userId:''
          		};
          		this.getUsers(info);
          	},
          	async getUsers(info){
          		const res = await http.get('/user/api/getUsers', info);
          		this.tableData=res.data.data;
          		this.count=res.data.total;
          		this.loading=false
          	},
          	onSubmit(){
          		var info={
          			currentPage:this.currentPage,
          			limit:this.limit,
          			userId:this.formInline.user
          		};
          		this.loading=false
          		this.getUsers(info);
          	},
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.currentPage = val;
                this.loading=true
                this.initData();
            },
            handleEdit(index, row) {
                this.selectTable = row;
                this.address.address = row.address;
                this.dialogFormVisible = true;
                this.selectedCategory = row.category.split('/');
                if (!this.categoryOptions.length) {
                    this.getCategory();
                }
            },
            async updateShop(){
                this.dialogFormVisible = false;
                try{
                    Object.assign(this.selectTable, this.address);
                    this.selectTable.category = this.selectedCategory.join('/');
                }catch(err){
                    console.log('更新餐馆信息失败', err);
                }
            },
            async handleDelete(index, row) {
            this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
		          confirmButtonText: '确定',
		          cancelButtonText: '取消',
		          type: 'warning'
		        }).then(() => {
		          this.$message({
		            type: 'success',
		            message: '删除成功!'
		          });
		        }).catch(() => {
		          this.$message({
		            type: 'info',
		            message: '已取消删除'
		          });          
		        });
            },
        },
    }
</script>

<style lang="less">
	@import '../../style/mixin';
    .demo-table-expand {
        font-size: 0;
    }
    .demo-table-expand label {
        width: 90px;
        color: #99a9bf;
    }
    .demo-table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }
    .table_container{
        padding: 20px;
    }
    .Pagination{
        display: flex;
        justify-content: flex-start;
        margin-top: 8px;
    }
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #20a0ff;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 120px;
        height: 120px;
        line-height: 120px;
        text-align: center;
    }
    .avatar {
        width: 120px;
        height: 120px;
        display: block;
    }
</style>
