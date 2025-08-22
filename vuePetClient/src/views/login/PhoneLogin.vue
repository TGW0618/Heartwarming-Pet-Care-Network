<template>
  <div style="padding-top: 3rem;padding-left: 0.5rem;padding-right: 0.5rem;">
    <div class="logo-container">
      <img src="../../assets/logo.svg" alt="Pet Logo" class="logo">
    </div>
    <div>
      <van-form @submit="onSubmit">
        <van-cell-group inset>
          <van-field
              v-model="phone"
              name="phone"
              label="手机号"
              placeholder="手机号"
              autocomplete="current-phone"
              :rules="[{ required: true, message: '请填写手机号' }]"
          />
          <van-field
              v-model="password"
              type="password"
              name="password"
              label="密码"
              placeholder="密码"
              autocomplete="current-password"
              :rules="[{ required: true, message: '请填写密码' }]"
          />
        </van-cell-group>
        <div style="margin: 16px;">
          <van-button round block color="#3e6ae1" native-type="submit" >
            登录
          </van-button>
        </div>

      </van-form>
      <div style="margin: 16px;font-size: 12px;">
        <van-row type="flex" justify="space-between">
          <van-col @click="noLogin">无法登录?</van-col>
          <van-col @click="toRegister">注册</van-col>
        </van-row>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref} from "vue";
import request from "@/utils/request.js";
import {showFailToast, showLoadingToast} from "vant";
import router from "@/router/index.js";
import useUserInfoStore from '@/stores/userInfo.js';

const userInfoStore = useUserInfoStore();

const phone = ref('');
const password = ref('');

const onSubmit = (values) => {
  console.log('submit', values);
  request.post('/client/login', values).then(res => {
    console.log(res)
    if (res.code === 200) {
      // 加载提示
      showLoadingToast({
        message: '加载中...',
        forbidClick: true,
        duration: 400,
      });
      // 登录成功后设置用户信息和token
      userInfoStore.setUserInfo(res.data.user); // 启用这行
      userInfoStore.setToken(res.data.token);
      setTimeout(function () {
        router.replace("/userinfo"); // 统一跳转到个人中心
      }, 500)
    } else {
      showFailToast(res.message);
    }
  }).catch(error => {
    showFailToast(error.message);
  })
};

// 无法登录？
const noLogin = () => {
  showFailToast("无法登录？");
}
// 注册
const toRegister = () => {
  router.push("/register");
}
</script>

<style scoped>
.logo-container {
  text-align: center;
  margin-bottom: 50px;
}

</style>