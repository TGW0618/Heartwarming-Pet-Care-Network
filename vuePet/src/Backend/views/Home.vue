<template>
  <el-scrollbar height="80vh">
    <!--    通用界面-->
    <div>
      <HomeInfo></HomeInfo>
    </div>
    <div style="padding-top: 15px">
      <!--    admin界面-->
      <div v-if="data.userInfo.role === 'admin'">
        <Admin></Admin>
      </div>
      <!--    员工界面-->
    </div>
    <div>
      <div v-if="data.userInfo.role === 'foster_staff'">
        <Foster></Foster>
      </div>
      <div v-if="data.userInfo.role === 'veterinarian'">
        <Medical></Medical>
      </div>
    </div>
  </el-scrollbar>
</template>


<script setup>
import {reactive} from "vue";
import Admin from "@/Backend/views/Home/Admin.vue";
import Foster from "@/Backend/views/Home/Foster.vue";
import Medical from "@/Backend/views/Home/Medical.vue";
import HomeInfo from "@/Backend/views/Home/HomeInfo.vue";


const data = reactive({
  userInfo: JSON.parse(localStorage.getItem("petSysUser"))
});


</script>


<style scoped>

</style>