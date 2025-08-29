<template>
  <van-tabbar
      router
      style="--van-tabbar-height:1.8rem;--van-tabbar-item-icon-margin-bottom:5px"
      v-model="active"
      active-color="#007aff"
      inactive-color="#c4c4c4">
    <van-tabbar-item to="/home" icon="wap-home">首页</van-tabbar-item>
    <van-tabbar-item @click="toBooking" icon="todo-list">预约</van-tabbar-item>
    <van-tabbar-item to="/consultation" icon="service">咨询</van-tabbar-item>
    <van-tabbar-item @click="onOrder" icon="bag">订单</van-tabbar-item>
    <van-tabbar-item @click="toUserInfo" icon="friends">我的</van-tabbar-item>
  </van-tabbar>

</template>

<script setup>
import {ref} from 'vue';
import router from "@/router/index.js";

const active = ref(0);

const toBooking = () => {
  console.log('点击了预约')
  router.push({
    name: 'bookingHome',
  });

};

const toUserInfo = () => {
  console.log('点击了用户信息')
  if (localStorage.getItem('token') || null) {
    window.location.href = '/userInfo';
  } else {
    window.location.href = '/login';
  }
};

const onOrder = () => {
  console.log('点击了订单')
  if (localStorage.getItem('token') || null) {
    router.push({
      name: 'orderList',
    });
  } else {
    window.location.href = '/login';
  }
};
</script>

<style scoped>

</style>