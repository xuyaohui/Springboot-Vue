<template>

  <div class="login_page fillcontain">
      <transition name="form-fade" mode="in-out">
        <section class="form_contianer">
<!--           <div class="manage_tip">
            <p>后台管理系统</p>
          </div> -->
          <el-form :model="info"  ref="info">
          <el-form-item >
            <el-input v-model="info.username" placeholder="用户名"><span>dsfsf</span></el-input>
          </el-form-item>
          <el-form-item >
            <el-input type="password" placeholder="密码" v-model="info.password"></el-input>
          </el-form-item>
          <el-form-item >
            <slice-pic></slice-pic>
          </el-form-item>
          <el-form-item>
              <el-button type="primary" @click="submit()" class="submit_btn">登陆</el-button>
            </el-form-item>
        </el-form>
        </section>
      </transition>
    </div>
</template>

<style lang="less">
    @import '../../style/mixin';
  .login_page{
    background-color: #324057;
  }
  .manage_tip{
    position: absolute;
    width: 100%;
    top: -100px;
    left: 0;
    p{
      font-size: 34px;
      color: #fff;
    }
  }
  .form_contianer{
    .wh(320px, 210px);
    .ctp(320px, 210px);
    padding: 25px;
    border-radius: 5px;
    text-align: center;
    background-color: #fff;
    .submit_btn{
      width: 100%;
      font-size: 16px;
    }
  }
  .tip{
    font-size: 12px;
    color: red;
  }
  .form-fade-enter-active, .form-fade-leave-active {
      transition: all 1s;
  }
  .form-fade-enter, .form-fade-leave-active {
      transform: translate3d(0, -50px, 0);
      opacity: 0;
  }
</style>

<script>
import login from '../../api/login'
import slicePic from '../../components/slicePic'
import { Message, Loading } from 'element-ui'
import { mapActions,mapState } from 'vuex' 
import store from '../../store/'
import { getPicCode } from '@/api/login'

export default {

  data() {
    return {
      info: {
        username: '',
        password: ''
      },
      error: '',
      loading:true, //用于显示加载界面

    }
  },
  created(){

  },
  components: {
      slicePic,
  },
  computed: {

  },
  methods: {
    //提交数据
    async submit() {
      let loadingInstance = Loading.service({ fullscreen: true });
      if ($('.txt').text()=='验证通过') {
          //验证通过后，向后台发送请求，后台返回

          return new Promise((resolve, reject) => {
            getPicCode().then(response => {
              const data = response.data
               console.log("picCode的值为： "+data)
                var info = {
                  userId: this.info.username,
                  userPass: this.info.password,
                  picCode : data
                }

                store.dispatch('LoginByUsername', info).then(() => {
                  this.$nextTick(() => { // 以服务的方式调用的 Loading 需要异步关闭
                    loadingInstance.close();
                  });
                    this.$router.push('manage');
                  }).catch(() => {
                    console.log('登录出错')
                  })
              resolve()
            }).catch(error => {
              reject(error)
            })
          })


          const res = http.post('/login/getPicCode', );
          console.log("picCode的值为： "+res.data)
          var info = {
            userId: this.info.username,
            userPass: this.info.password,
            picCode : res.data
          }

          store.dispatch('LoginByUsername', info).then(() => {
            this.$nextTick(() => { // 以服务的方式调用的 Loading 需要异步关闭
              loadingInstance.close();
            });
              this.$router.push('manage');
            }).catch(() => {
              console.log('登录出错')
            })

          
      }else{
          Message({
              message: '请先拖动图形验证码',
              type: 'error',
              duration: 5 * 1000
          })
      }
    },
  }
}
</script>
