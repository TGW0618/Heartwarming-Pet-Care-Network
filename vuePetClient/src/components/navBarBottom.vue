<template>
  <van-tabbar
      router
      style="--van-tabbar-height:1.8rem;--van-tabbar-item-icon-margin-bottom:5px"
      v-model="active"
      active-color="#171a20"
      inactive-color="#c4c4c4">
    <van-tabbar-item to="/home" icon="wap-home">首页</van-tabbar-item>
    <van-tabbar-item to="/chat" icon="chat">消息</van-tabbar-item>
    <van-tabbar-item to="order" icon="coupon">订单</van-tabbar-item>
    <van-tabbar-item to="/userinfo" icon="friends">我的</van-tabbar-item>
  </van-tabbar>

</template>

<script setup>
import {ref} from 'vue';


const active = ref(0);
</script>

<style scoped>

</style>