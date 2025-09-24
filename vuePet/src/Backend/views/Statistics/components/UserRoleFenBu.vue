<template>
  <!--    用户角色分布 (饼图)
    数据：用户中，普通宠物主、寄养员、兽医、管理员的占比。-->
  <div>
    <div>
      <slot name="title"></slot>
    </div>
    <div ref="chartRef" class="userRoleChars"></div>
  </div>
</template>
<script setup>
import useChart from "@/Backend/composables/useChart.js";

const setUserRoleOption = () => ({
  title: {
    text: '用户角色分布',
    left:
        'center'
  }
  ,
  tooltip:
      {}
  ,
  legend: {
    orient: 'vertical',
    left:
        'left'
  }
  ,
  series: [
    {
      name: '用户角色分布',
      type: 'pie',
      radius: '50%',
      data: [
        {value: 1048, name: '普通宠物主'},
        {value: 735, name: '寄养员'},
        {value: 580, name: '兽医'},
      ]
    }
  ]

})
const {chartRef} = useChart(setUserRoleOption)


</script>

<style scoped>
.userRoleChars {
  width: 100%;
  height: 350px;
  min-width: 200px;
  max-height: 350px;
  border-radius: 15px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
  box-sizing: border-box; /* 确保边框和阴影不影响尺寸 */
}
</style>