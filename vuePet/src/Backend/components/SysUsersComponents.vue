<template>
  <div>
    <div v-for="user in users" :key="user.id">
      <div>ID: {{ user.id }}</div>
      <div>用户名: {{ user.username }}</div>
      <div>密码: {{ user.password }}</div>
      <hr/>
    </div>
  </div>

</template>

<script setup>
import {defineProps} from 'vue'

// 接收父组件传来的用户列表
defineProps({
  users: {
    type: Array,
    required: true,
    default: () => []
  }
})

</script>


<style scoped>

</style>