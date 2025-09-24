<template>
  <div>
    <el-row :gutter="18">
      <el-col :span="12">
        <div style="padding: 5px">
          <medical-c-r-top/>
        </div>
      </el-col>
      <el-col :span="12">
        <div style="padding: 5px">
          <medical-word-count/>
        </div>
      </el-col>
    </el-row>
  </div>


</template>
<script setup>
import MedicalCRTop from "@/Backend/views/Statistics/components/MedicalCRTop.vue";
import MedicalWordCount from "@/Backend/views/Statistics/components/MedicalWordCount.vue";
</script>

<style scoped>

</style>