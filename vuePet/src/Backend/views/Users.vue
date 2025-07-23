<template>
  <!--宠物主人-->
  <div>
    <h2>全部客户</h2>
    <Owners/>
  </div>

  <!--员工-->
  <div>
    <h2>全部员工</h2>
    <Employees/>
  </div>


</template>

<script setup>
import Owners from './Users/Owners.vue'
import Employees from './Users/Employees.vue'

</script>

<style scoped>

</style>