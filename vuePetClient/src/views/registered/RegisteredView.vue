<template>
  <p style="text-align: center;">注册</p>
  <div>
    <van-form @submit="onSubmit">
      <van-cell-group inset>
        <van-field
            v-model="username"
            name="username"
            label="账号"
            placeholder="账号"
            autocomplete="username"
            :rules="[{ required: true, message: '请填写账号' }]"
        />
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
            autocomplete="new-password"
            :rules="[{ required: true, message: '请填写密码' }]"
        />
      </van-cell-group>
      <div style="margin: 16px;">
        <van-button round block native-type="submit" style="letter-spacing: 0.08em;">
          注册
        </van-button>
      </div>
    </van-form>
  </div>
  <div style="text-align: center;font-size: 12px;">
    <router-link to="/login" style="color: #474747;">已有账号？去登录</router-link>
  </div>
</template>

<script setup>
import request from "@/utils/request.js";
import {showFailToast, showToast} from "vant";
import router from "@/router/index.js";
import {ref} from "vue";

let username = ref('');
let phone = ref('');
let password = ref('');

const onSubmit = (values) => {
  request.post('/sysUser/addSysUsers', values).then(res => {
    if (res.code === 200) {
      showToast("注册成功");
      router.replace("/login");
    } else {
      showFailToast(res.message);
    }
  })
}

</script>

<style scoped>

</style>