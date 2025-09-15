<template>
  <el-scrollbar height="80vh">
    <!--    boss界面-->
    <div v-if="data.userInfo.role === 'admin'">
      <boss-view></boss-view>
    </div>
    <!--    员工界面-->
    <div v-else>
      <employee-view></employee-view>
    </div>
  </el-scrollbar>
</template>


<script setup>
import {onMounted, reactive} from "vue";
import {ElMessage} from "element-plus";
import BossView from "@/Backend/views/Home/boss/bossView.vue";
import EmployeeView from "@/Backend/views/Home/employee/employeeView.vue";

const data = reactive({
  userInfo: JSON.parse(localStorage.getItem("petSysUser"))
});

onMounted(() => {
  if (data.userInfo) {
    welcomeMessage()
  }
  console.log(data.userInfo)
})

const welcomeMessage = () => {
  ElMessage({
    showClose: true,
    message: '欢迎！' + data.userInfo.realName + ',祝您上班开心',
    type: 'success',
  })
}

</script>


<style scoped>

</style>