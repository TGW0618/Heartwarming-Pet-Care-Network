<template>
  <div class="login-container">
    <div class="login-card">
      <!-- 登录左侧装饰开始 -->
      <div class="login-decoration">
        <div class="pet-illustration">
          <div class="pet-shape"></div>
          <!--          <img src="@/assets/icon/pet-login.jpg" alt="宠物插图" class="pet-image">-->
        </div>
        <h2 class="welcome-text">欢迎回来</h2>
        <p class="welcome-subtext">专业宠物寄养管理平台</p>
      </div>
      <!-- 登录左侧装饰结束 -->
      <!-- 登录右侧开始 -->
      <div class="login-form">
        <!--        logo开始-->
        <div class="logo">
          <img src="@/assets/icon/宠物天地.png" alt="logo" class="logo-icon">
          <h1>宠物管家<span class="logo-pro">Pro</span></h1>
        </div>
        <!--        logo结束-->
        <!-- 登录表单开始 -->
        <el-form :model="data.loginForm" :rules="data.loginRules" ref="loginFormRef">
          <el-form-item prop="username">
            <el-input
                v-model="data.loginForm.username"
                placeholder="请输入账号"
                prefix-icon="User"
                size="large"
                @keyup.enter="handleLogin"
            />
          </el-form-item>
          <el-form-item prop="password">
            <el-input
                v-model="data.loginForm.password"
                type="password"
                placeholder="请输入密码"
                prefix-icon="Lock"
                size="large"
                show-password
                @keyup.enter="handleLogin"
            />
          </el-form-item>

          <div class="form-actions">
            <el-checkbox v-model="rememberMe">记住我</el-checkbox>
            <el-link type="primary" @click="forgetPassword">忘记密码?</el-link>
          </div>

          <el-button
              type="primary"
              size="large"
              @click="handleLogin"
              class="login-btn"
          >
            登录系统
          </el-button>
        </el-form>
        <!--        登录表单结束-->
        <div class="third-party-login">
          <p class="divider"><span>其他登录方式</span></p>
          <div class="social-icons">
            <el-icon class="social-icon" @click="handleLoginUser">
              <Avatar/>
            </el-icon>
            <el-icon class="social-icon" @click="handleLoginMessage">
              <Message/>
            </el-icon>
            <el-icon class="social-icon" @click="handleLoginPhone">
              <Cellphone/>
            </el-icon>
          </div>
        </div>
      </div>
    </div>
    <!-- 登录右侧结束 -->
    <!--    底部开始-->
    <div class="footer">
      <p>© 2025 宠物管家Pro 专业寄养管理系统 | 隐私政策 | 服务条款</p>
    </div>
    <!--    底部结束-->
  </div>
</template>

<script setup>
import {reactive, ref} from 'vue'
import {Avatar, Cellphone, Message} from '@element-plus/icons-vue'
import request from "@/Backend/utils/request.js";
import {ElMessage} from "element-plus";
import router from "@/Common/router/index.js";

const loginFormRef = ref(null)
const rememberMe = ref(false)

const data = reactive({
  loginForm: {},
  loginRules: {
    username: [
      {required: true, message: '请输入用户名', trigger: 'blur'},
      {min: 3, max: 16, message: '长度在 3 到 16 个字符', trigger: 'blur'}
    ],
    password: [
      {required: true, message: '请输入密码', trigger: 'blur'},
      {min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur'}
    ]
  },
  loginUser: [],
})


const handleLogin = () => {
  loginFormRef.value.validate(valid => {
    if (valid) {
      // 登录请求(后台管理系统)
      request.post('/admin/login', data.loginForm).then(res => {
        if (res.code === 200) {

          localStorage.setItem('petSysUser', JSON.stringify(res.data))// 把数据转为json字符串存储用户数据，
          ElMessage.success("登陆成功")
          //   跳转后台管理系统
          router.replace('/admin/home')
        } else {
          ElMessage.error(res.message)
        }
      })
    }
  })
}

const forgetPassword = () => {
  ElMessage.info('请联系管理员')
}
const handleLoginUser = () => {
  ElMessage.info('功能开发中')
}
const handleLoginMessage = () => {
  ElMessage.info('功能开发中')
}
const handleLoginPhone = () => {
  ElMessage.info('功能开发中')
}

</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #f8f5fa;
  background-image: url('#');
  background-size: 400px;
  background-position: center;
  background-blend-mode: overlay;
  padding: 20px;
}

.login-card {
  width: 900px;
  height: 550px;
  display: flex;
  background-color: white;
  border-radius: 16px;
  box-shadow: 0 10px 30px rgba(175, 84, 194, 0.15);
  overflow: hidden;
}

.login-decoration {
  width: 45%;
  background: linear-gradient(135deg, #af54c2, #8e44ad);
  padding: 40px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  position: relative;
  color: white;
}

.pet-illustration {
  width: 280px;
  height: 280px;
  position: relative;
  margin-bottom: 40px;
}

.pet-shape {
  position: absolute;
  width: 100%;
  height: 100%;
  border-radius: 50%;
  background-color: rgba(255, 255, 255, 0.1);
  transform: rotate(45deg);
}

.pet-image {
  width: 100%;
  height: 100%;
  object-fit: contain;
  position: relative;
  z-index: 1;
  filter: drop-shadow(0 10px 20px rgba(0, 0, 0, 0.1));
}

.welcome-text {
  font-size: 28px;
  font-weight: 600;
  margin-bottom: 10px;
  text-align: center;
}

.welcome-subtext {
  font-size: 16px;
  opacity: 0.9;
  text-align: center;
}

.login-form {
  width: 55%;
  padding: 60px 50px;
  display: flex;
  flex-direction: column;
}

.logo {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 40px;
}

.logo-icon {
  width: 40px;
  height: 40px;
  margin-right: 12px;
}

.logo h1 {
  font-size: 24px;
  font-weight: 600;
  color: #333;
}

.logo-pro {
  color: #af54c2;
  font-weight: 700;
}

:deep(.el-input__wrapper) {
  border-radius: 8px;
  padding: 0 15px;
  height: 48px;
  box-shadow: 0 0 0 1px #e2e8f0;
}

:deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 0 0 1px #af54c2;
}

:deep(.el-input__inner) {
  color: #333;
}

:deep(.el-input__inner::placeholder) {
  color: #a0aec0;
}

.form-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 25px;
}

:deep(.el-checkbox) {
  color: #718096;
}

:deep(.el-link) {
  color: #af54c2;
}

.login-btn {
  width: 100%;
  height: 48px;
  background-color: #af54c2;
  border: none;
  font-size: 16px;
  font-weight: 500;
  border-radius: 8px;
  transition: all 0.3s;
}

.login-btn:hover {
  background-color: #9b4db0;
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(175, 84, 194, 0.3);
}

.third-party-login {
  margin-top: 40px;
}

.divider {
  text-align: center;
  position: relative;
  color: #a0aec0;
  font-size: 14px;
  margin-bottom: 20px;
}

.divider span {
  padding: 0 10px;
  background-color: white;
  position: relative;
  z-index: 1;
}

.divider::before {
  content: "";
  position: absolute;
  top: 50%;
  left: 0;
  right: 0;
  height: 1px;
  background-color: #e2e8f0;
  z-index: 0;
}

.social-icons {
  display: flex;
  justify-content: center;
  gap: 20px;
}

.social-icon {
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  background-color: #f8f5fa;
  color: #af54c2;
  cursor: pointer;
  transition: all 0.3s;
}

.social-icon:hover {
  background-color: #af54c2;
  color: white;
  transform: translateY(-3px);
}

.footer {
  margin-top: 30px;
  color: #718096;
  font-size: 14px;
  text-align: center;
}

/* 响应式设计 */
@media (max-width: 992px) {
  .login-card {
    width: 700px;
    height: auto;
  }

  .login-decoration {
    display: none;
  }

  .login-form {
    width: 100%;
    padding: 40px 30px;
  }
}

@media (max-width: 768px) {
  .login-card {
    width: 100%;
    box-shadow: none;
    border-radius: 0;
  }

  .login-container {
    background-image: none;
    padding: 0;
  }
}
</style>