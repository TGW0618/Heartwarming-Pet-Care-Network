<template>
  <!--宠物主人-->
  <div>
    <h2>客户</h2>
    <Owners/>
  </div>
  <hr>
  <!--员工-->
  <div>
    <h2>员工</h2>
    <Employees/>
  </div>


</template>

<script setup>
import Owners from './Users/Owners.vue'
import Employees from './Users/Employees.vue'

</script>

<style scoped>

</style>