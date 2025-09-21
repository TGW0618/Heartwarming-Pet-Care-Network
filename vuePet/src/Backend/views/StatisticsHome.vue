<template>
  <el-scrollbar height="80vh">
    <div>
      <el-tabs v-model="activeName" class="demo-tabs">
        <el-tab-pane label="运营总览" name="Operations">
          <!--    运营总览-->
          <div>
            <OperationsView/>
          </div>
        </el-tab-pane>
        <el-tab-pane label="订单分析" name="OrderAnalysis" lazy>
          <!--    订单分析-->
          <div>
            <OrderAnalysisView/>
          </div>
        </el-tab-pane>
        <el-tab-pane label="用户分析" name="UserAnalysis" lazy>
          <!--    用户分析-->
          <div>
            <UserAnalysisView/>
          </div>
        </el-tab-pane>
        <el-tab-pane label="寄养业务分析" name="FosterBusiness" lazy>
          <!--    寄养业务分析-->
          <div>
            <FosterBusinessView/>
          </div>
        </el-tab-pane>
        <el-tab-pane label="医疗业务分析" name="MedicalBusiness" lazy>
          <!--     医疗业务分析-->
          <div>
            <MedicalBusinessView/>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </el-scrollbar>
</template>

<script setup>
import OperationsView from "@/Backend/views/Statistics/OperationsView.vue";
import OrderAnalysisView from "@/Backend/views/Statistics/OrderAnalysisView.vue";
import UserAnalysisView from "@/Backend/views/Statistics/UserAnalysisView.vue";
import FosterBusinessView from "@/Backend/views/Statistics/FosterBusinessView.vue";
import MedicalBusinessView from "@/Backend/views/Statistics/MedicalBusinessView.vue";
import {ref} from 'vue'

const activeName = ref('Operations')
</script>

<style scoped>
.demo-tabs {
  width: 100%;
}
</style>

<style>
/* 自定义el-tabs标签页样式 */
.demo-tabs .el-tabs__header {
  margin-bottom: 20px;
}

.demo-tabs .el-tabs__nav-wrap::after {
  background-color: #e4e7ed;
  height: 1px;
}

.demo-tabs .el-tabs__item {
  color: #8d8d8d;
  font-size: 15px;
  font-weight: 500;
  padding: 0 25px;
  position: relative;
  transition: all 0.3s ease;
}

.demo-tabs .el-tabs__item:hover {
  color: #ad52be;
  background-color: rgba(108, 92, 231, 0.05);
  border-radius: 8px 8px 0 0;
}

.demo-tabs .el-tabs__item.is-active {
  color: #ad52be;
  font-weight: 600;
}

.demo-tabs .el-tabs__item.is-active::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 68%;
  height: 3px;
  background: linear-gradient(90deg, #ad52be, #f8e3ff);
  border-radius: 3px 3px 0 0;
}

.demo-tabs .el-tabs__active-bar {
  display: none;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .demo-tabs .el-tabs__item {
    padding: 0 15px;
    font-size: 14px;
  }
}

@media (max-width: 480px) {
  .demo-tabs .el-tabs__item {
    padding: 0 10px;
    font-size: 13px;
  }

  .demo-tabs .el-tabs__header {
    margin-bottom: 15px;
  }
}
</style>
