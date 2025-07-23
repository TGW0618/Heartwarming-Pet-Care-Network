<template>
  <div>
    <div v-for="user in users" :key="user.id">
      <div>ID: {{ user.id }}</div>
      <div>头像: {{ user.avatar }}</div>
      <div>账号: {{ user.username }}</div>
      <div>密码: {{ user.password }}</div>
      <div>昵称: {{ user.realName }}</div>
      <div>性别: {{ user.sex }}</div>
      <div>手机: {{ user.phone }}</div>
      <div>邮箱: {{ user.email }}</div>
      <div>角色：{{ user.role }}</div>
      <div>状态：{{ user.status }}</div>
      <div>创建时间：{{ user.createTime }}</div>
      <div>更新时间：{{ user.updateTime }}</div>
      <div>操作：
        <button @click="deleteUser(user.id)">删除</button>
        <button @click="updateUser(user.id)">修改</button>
      </div>
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