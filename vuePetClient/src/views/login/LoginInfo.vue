<template>
  <div>
    <navBarTop :title="navBarTopTitle"/>
  </div>
  <div class="login-container">
    <div class="logo-container">
      <img src="../../assets/logo.svg" alt="Pet Logo" class="logo">
    </div>
    <!--政策 条款 开始-->
    <div class="terms">
      <label class="checkbox-container">
        <input type="checkbox" :checked="isChecked" @click="handleCheckboxClick">
        <span class="checkmark"></span>
        我已阅读并同意 Pet 的
        <router-link to="/privacy" class="link">隐私政策</router-link>
        和
        <router-link to="/terms" class="link">使用条款</router-link>
      </label>
      <div style="height: 20px;padding-left:0.68rem;color:#b62121">
        <span>{{ isCheckedTip }}</span>
      </div>
    </div>
    <!--政策 条款 结束-->

    <!--登录按钮开始-->
    <div class="login-options">
      <div class="phone-login option-card" @click="phoneLogin">
        <i class="icon icon-phone"></i>
        <span style="font-weight: 500;letter-spacing: 0.08rem">手机号快捷登录</span>
      </div>

      <div class="account-login option-card" @click="petLogin">
        <i class="icon icon-account"></i>
        <span style="font-weight: 700">Pet <span style="letter-spacing: 0.08rem">账号登录</span></span>
      </div>
    </div>
    <!--登录按钮结束-->

    <div class="skip-login">
      <router-link to="/home" class="skip-link">暂不登录</router-link>
    </div>
  </div>
</template>

<script setup>
import navBarTop from "@/components/navBarTop.vue"
import {ref} from "vue";
import router from "@/router/index.js";

const navBarTopTitle = ref("登录")
const isChecked = ref(false);
const isCheckedTip = ref(null);

const handleCheckboxClick = () => {
  isChecked.value = !isChecked.value;
  if (isChecked.value) {
    isCheckedTip.value = null;
  } else {
    isCheckedTip.value = "请阅读并勾选";
  }
  console.log("复选框状态:", isChecked.value);
}

const phoneLogin = () => {
  if (isChecked.value) {
    router.push('/phoneLogin')
    console.log("手机号登录")
  } else {
    console.log("复选框未选中");
    isCheckedTip.value = "请阅读并勾选";
  }
}

const petLogin = () => {
  if (isChecked.value) {
    router.push('/petLogin')
    console.log("pet账号登录")
  } else {
    console.log("复选框未选中");
    isCheckedTip.value = "请阅读并勾选";
  }
}

</script>

<style scoped>
.login-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 90px 20px;
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 100vh;
  background-color: white;
}

.logo-container {
  text-align: center;
  margin-bottom: 150px;
}

.logo {
  width: 80px;
  height: 80px;
}

.login-options {
  width: 100%;
}

.option-card {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 15px;
  margin-bottom: 15px;
  border-radius: 8px;
  font-size: 16px;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.3s ease;
}

.option-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.phone-login {
  background-color: #3e6ae1;
  color: white;
}

.account-login {
  background-color: #f4f4f4;
  color: #333;
}

.icon {
  margin-right: 10px;
  font-size: 18px;
}

.terms {
  margin-bottom: 30px;
  font-size: 14px;
  color: #666;
}

.checkbox-container {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.checkbox-container input {
  position: absolute;
  opacity: 0;
  cursor: pointer;
}

.checkmark {
  position: relative;
  height: 16px;
  width: 16px;
  background-color: #fff;
  border: 1px solid #d9d9d9;
  border-radius: 5px;
  margin-right: 8px;
}

.checkbox-container input:checked ~ .checkmark {
  background-color: #3e6ae1;
  border-color: #f5f4f4;
}

.checkmark:after {
  content: "";
  position: absolute;
  display: none;
}

.checkbox-container input:checked ~ .checkmark:after {
  display: block;
}

.checkbox-container .checkmark:after {
  left: 5px;
  top: 2px;
  width: 4px;
  height: 8px;
  border: solid white;
  border-width: 0 2px 2px 0;
  transform: rotate(45deg);
}

.link {
  text-decoration: none;
  margin: 0 4px;
  font-weight: 600;
  color: #333333;
}

.skip-login {
  margin-top: 5px;
}

.skip-link {
  color: #2b2d30;
  text-decoration: none;
  font-size: 14px;
}

.skip-link:hover {
  color: #333;
}
</style>