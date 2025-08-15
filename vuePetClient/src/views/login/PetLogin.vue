<template>
  <div style="padding-top: 3rem;padding-left: 0.5rem;padding-right: 0.5rem;">
    <div class="logo-container">
      <img src="../../assets/logo.svg" alt="Pet Logo" class="logo">
    </div>
    <div>
      <van-form @submit="onSubmit">
        <van-cell-group inset>
          <van-field
              v-model="username"
              name="username"
              label="用户名"
              placeholder="用户名"
              autocomplete="current-username"
              :rules="[{ required: true, message: '请填写用户名' }]"
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
          <van-button round block color="#3e6ae1" native-type="submit">
            登录
          </van-button>
        </div>
      </van-form>
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

const username = ref('');
const password = ref('');

userInfoStore.initializeStore();

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
      userInfoStore.setUserInfo(res.data.user);
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

</script>

<style scoped>
.logo-container {
  text-align: center;
  margin-bottom: 50px;
}

.logo {
  width: 150px;
  height: 150px;
}
</style>