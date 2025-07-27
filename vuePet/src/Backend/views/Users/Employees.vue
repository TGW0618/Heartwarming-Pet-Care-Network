<template>
  <div>
    <SysUsersComponent :users="data.users"/>
  </div>
</template>

<script setup>
import {ref} from 'vue'
import request from "@/Backend/utils/request.js";
import SysUsersComponent from '../../components/SysUsersComponents.vue'
import {ElMessage} from "element-plus";

const data = ref({
  users: [],
  pageNum: 1,
  pageSize: 10,
  total: 0,
  username: null,
})

const getAllSysUserEmployee = () => {
  request.get('/sysUser/getAllSysUserEmployee', {
    params: {
      pageNum: data.value.pageNum,
      pageSize: data.value.pageSize,
      username: data.value.username
    }
  }).then(res => {
    if (res.code === 200) {
      data.value.users = res.data || []
      data.value.total = res.data.total || 0
    } else {
      ElMessage.error(res.message)
    }
  })
}

getAllSysUserEmployee()
</script>

<style scoped>

</style>