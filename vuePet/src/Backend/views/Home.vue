<template>
  <el-scrollbar height="85vh">
    <div style="padding-left: 5px;padding-right: 5px;padding-bottom: 10px">
      <!--    通用信息界面-->
      <div>
        <home-info></home-info>
      </div>

      <!--    boss界面-->
      <div style="padding-top: 20px">
        <div v-if="data.userInfo.role === 'admin'">
          <Admin></Admin>
        </div>
        <!--    员工界面-->
        <div v-if="data.userInfo.role === 'foster_staff'">
          <foster></foster>
        </div>
        <div v-if="data.userInfo.role === 'veterinarian'">
          <medical></medical>
        </div>
      </div>
    </div>
  </el-scrollbar>
</template>


<script setup>
import {onMounted, reactive} from "vue";
import Admin from "@/Backend/views/Home/Admin.vue";
import HomeInfo from "@/Backend/views/Home/HomeInfo.vue";
import Foster from "@/Backend/views/Home/Foster.vue";
import Medical from "@/Backend/views/Home/Medical.vue";

const data = reactive({
  userInfo: JSON.parse(localStorage.getItem("petSysUser"))
});


</script>


<style scoped>

</style>