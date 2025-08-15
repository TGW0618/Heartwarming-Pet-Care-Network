<template>
  <!--    系统用户统计开始-->
  <div class="DataStatistics">
    <el-row :gutter="20" style="margin-bottom: 20px">
      <!--         总用户-->
      <el-col @click="testBtn" :span="6">
        <div>
          <RectangleComponents :data-statistics="StatisticsData.totalSysUser">
            <template #icon>
              <el-icon>
                <UserFilled/>
              </el-icon>
            </template>
            <template #title>
              总用户
            </template>
            <template #data>
              {{ StatisticsData.totalSysUser }}
            </template>
          </RectangleComponents>
        </div>
      </el-col>
      <!--        总客户-->
      <el-col @click="testBtn" :span="6">
        <div>
          <RectangleComponents :data-statistics="StatisticsData.totalCustomers">
            <template #icon>
              <el-icon>
                <Tickets/>
              </el-icon>
            </template>
            <template #title>
              客户
            </template>
            <template #data>
              {{ StatisticsData.totalCustomers }}
            </template>
          </RectangleComponents>
        </div>
      </el-col>
      <!--        总员工-->
      <el-col @click="testBtn" :span="6">
        <div>
          <RectangleComponents :data-statistics="StatisticsData.totalStaff">
            <template #icon>
              <el-icon>
                <UserFilled/>
              </el-icon>
            </template>
            <template #title>
              员工
            </template>
            <template #data>
              {{ StatisticsData.totalStaff }}
            </template>
          </RectangleComponents>
        </div>
      </el-col>
      <!--        总医生-->
      <el-col @click="testBtn" :span="6">
        <div>
          <RectangleComponents :data-statistics="StatisticsData.totalVeterinarian">
            <template #icon>
              <el-icon>
                <UserFilled/>
              </el-icon>
            </template>
            <template #title>
              医生
            </template>
            <template #data>
              {{ StatisticsData.totalVeterinarian }}
            </template>
          </RectangleComponents>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <!--        总寄养员-->
      <el-col @click="testBtn" :span="6">
        <div>
          <RectangleComponents :data-statistics="StatisticsData.totalFosterStaff">
            <template #icon>
              <el-icon>
                <UserFilled/>
              </el-icon>
            </template>
            <template #title>
              寄养员
            </template>
            <template #data>
              {{ StatisticsData.totalFosterStaff }}
            </template>
          </RectangleComponents>
        </div>
      </el-col>
    </el-row>
  </div>
  <!--    系统用户统计结束-->
</template>

<script setup>
import RectangleComponents from "@/Backend/components/DataStatistics/RectangleComponents.vue";
import {Tickets, UserFilled} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";

const props = defineProps({
  StatisticsData: {
    type: Object,
    required: true,
  },
})

const testBtn = () => {
  ElMessage.info("功能开发中")
}
</script>

<style scoped>
.DataStatistics {
  padding: 20px;
  background-color: #ffffff;
  margin-top: 15px;
  margin-bottom: 15px;
  border-radius: 8px
}
</style>
