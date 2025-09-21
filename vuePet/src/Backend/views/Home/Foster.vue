<template>
  <div>
    <el-row :gutter="10">
      <!--         寄养任务列表-->
      <el-col :span="12">
        <div>
          <div class="Title">
            <title-composition>
              <template #icon>
                <el-icon class="iconY">
                  <Monitor/>
                </el-icon>
              </template>
              <template #title>
                寄养任务列表
              </template>
            </title-composition>
          </div>
          <div class="counter">
            寄养任务列表
          </div>
        </div>
      </el-col>
      <!--            待处理寄养申请-->
      <el-col :span="12">
        <div>
          <div class="Title">
            <title-composition>
              <template #icon>
                <el-icon class="iconY">
                  <Monitor/>
                </el-icon>
              </template>
              <template #title>
                待处理寄养申请
              </template>
            </title-composition>
          </div>
          <div class="counter">
            待处理寄养申请
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row :gutter="10" style="padding-top: 20px;">
      <!--         寄养房间状态-->
      <el-col :span="12">
        <div>
          <div class="Title">
            <title-composition>
              <template #icon>
                <el-icon class="iconY">
                  <Monitor/>
                </el-icon>
              </template>
              <template #title>
                寄养房间状态
              </template>
            </title-composition>
          </div>
          <div class="counter">
            寄养房间状态
          </div>
        </div>
      </el-col>
      <!--            个人工作统计-->
      <el-col :span="12">
        <div>
          <div class="Title">
            <title-composition>
              <template #icon>
                <el-icon class="iconY">
                  <Monitor/>
                </el-icon>
              </template>
              <template #title>
                个人工作统计
              </template>
            </title-composition>
          </div>
          <div class="counter">
            个人工作统计
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script setup>

import TitleComposition from "@/Backend/components/TitleComposition.vue";
import {Monitor} from "@element-plus/icons-vue";
</script>

<style scoped>
.Title {
  margin-bottom: 1px;
}

.iconY {
  font-size: 10px;
  color: #6a2c82;
  font-weight: bold
}

.counter {
  height: auto;
  border-radius: 9px;
  font-size: 12px;
  padding: 5px;
  max-height: 310px;
  min-height: 200px;
  box-shadow: rgba(106, 44, 130, 0.15) 1px 2px 6px 1px;
  border: 1px solid rgba(174, 84, 193, 0.16);
}
</style>