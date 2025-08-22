<template>
  <van-config-provider :theme="theme">
    <RouterView/>
  </van-config-provider>
</template>

<script setup>
import {onBeforeUnmount, onMounted, ref} from "vue";

const theme = ref('light');

// 检查系统主题偏好
const checkSystemTheme = () => {
  if (window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches) {
    theme.value = 'dark';
  } else {
    theme.value = 'light';
  }
};

// 监听系统主题变化
const mediaQueryListener = (e) => {
  theme.value = e.matches ? 'dark' : 'light';
};

let mediaQuery;

onMounted(() => {
  // 初始化主题
  checkSystemTheme();

  // 监听系统主题变化
  if (window.matchMedia) {
    mediaQuery = window.matchMedia('(prefers-color-scheme: dark)');
    mediaQuery.addEventListener('change', mediaQueryListener);
  }
});

onBeforeUnmount(() => {
  // 清理事件监听器
  if (mediaQuery) {
    mediaQuery.removeEventListener('change', mediaQueryListener);
  }
});
</script>

<style>

</style>
