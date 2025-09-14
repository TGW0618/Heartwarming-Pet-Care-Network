<template>
  <van-tabbar
      style="--van-tabbar-height:1.8rem;--van-tabbar-item-icon-margin-bottom:5px"
      v-model="active"
      active-color="#007aff"
      inactive-color="#c4c4c4"
      @change="onChange">
    <van-tabbar-item icon="wap-home">首页</van-tabbar-item>
    <van-tabbar-item icon="todo-list">预约</van-tabbar-item>
    <van-tabbar-item icon="service">咨询</van-tabbar-item>
    <van-tabbar-item icon="bag">订单</van-tabbar-item>
    <van-tabbar-item icon="friends">我的</van-tabbar-item>
  </van-tabbar>
</template>

<script setup>
import {ref, watch} from 'vue';
import {useRouter, useRoute} from 'vue-router';

const active = ref(0);
const router = useRouter();
const route = useRoute();

// 监听路由变化，设置对应的激活项
watch(() => route.path, (newPath) => {
  updateActiveIndex(newPath);
}, { immediate: true });

// 页面加载时也更新一次
updateActiveIndex(route.path);

function updateActiveIndex(path) {
  if (path === '/home' || path === '/') {
    active.value = 0;
  } else if (path.startsWith('/bookingHome')) {
    active.value = 1;
  } else if (path === '/consultationHome') {
    active.value = 2;
  } else if (path.startsWith('/ordersHome')) {
    active.value = 3;
  } else if (path === '/userCenterHome') {
    active.value = 4;
  }
}

// 处理底部导航切换
const onChange = (index) => {
  switch (index) {
    case 0:
      router.push('/home');
      break;
    case 1:
      router.push('/bookingHome');
      break;
    case 2:
      router.push('/consultationHome');
      break;
    case 3:
      router.push('/ordersHome');
      break;
    case 4:
      router.push('/userCenterHome');
      break;
  }
};
</script>

<style scoped>
</style>
