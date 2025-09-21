<template>
  <div>
    <el-row :gutter="18">
      <el-col :span="12">
        <div style="border-radius: 15px;padding: 5px">
          <foster-room-type></foster-room-type>
        </div>
      </el-col>
      <el-col :span="12">
        <div style="border-radius: 15px;padding: 5px">
          <foster-time></foster-time>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script setup>

import FosterRoomType from "@/Backend/views/Statistics/components/FosterRoomType.vue";
import FosterTime from "@/Backend/views/Statistics/components/FosterTime.vue";
</script>

<style scoped>

</style>