<template>
  <div>
    <div>
      <el-row :gutter="10">
        <!--          系统概况-->
        <el-col :span="6">
          <div>
            <div class="Title">
              <title-composition>
                <template #icon>
                  <el-icon class="iconY">
                    <Monitor/>
                  </el-icon>
                </template>
                <template #title>
                  系统概况
                </template>
              </title-composition>
            </div>
            <div class="Box">
              <info-item>
                <template #text>
                  欢迎！{{ data.userInfo.username }}
                </template>
              </info-item>
            </div>
          </div>
        </el-col>
        <!--        快捷操作入口-->
        <el-col :span="6">
          <div>
            <div class="Title">
              <title-composition>
                <template #icon>
                  <el-icon class="iconY">
                    <Pointer/>
                  </el-icon>
                </template>
                <template #title>
                  快捷操作入口
                </template>
              </title-composition>
            </div>
            <div class="Box">
              <div v-if="data.userInfo.role === 'admin'">
                <info-item @click="toStatisticsHome">
                  <template #text>
                    数据统计
                  </template>
                </info-item>
                <info-item @click="toUsersHome">
                  <template #text>
                    用户管理
                  </template>
                </info-item>
              </div>
              <div>
                <info-item @click="toBookingsHome">
                  <template #text>
                    处理订单
                  </template>
                </info-item>
                <info-item v-if="data.userInfo.role === 'admin' || data.userInfo.role === 'foster_staff' "
                           @click="toRoomsHome">
                  <template #text>
                    宠舍管理
                  </template>
                </info-item>
                <info-item @click="toUserCenter">
                  <template #text>
                    个人中心
                  </template>
                </info-item>
              </div>
            </div>
          </div>
        </el-col>
        <!--        关键业务预览-->
        <el-col :span="6">
          <div>
            <div class="Title">
              <title-composition>
                <template #icon>
                  <el-icon class="iconY">
                    <CollectionTag/>
                  </el-icon>
                </template>
                <template #title>
                  关键业务预览
                </template>
              </title-composition>
            </div>
            <div class="Box">
              <div v-if="data.userInfo.role === 'admin'">
                <RectangleComponents @click="toStatisticsHome">
                  <template #icon>
                    <el-icon>
                      <UserFilled/>
                    </el-icon>
                  </template>
                  <template #title>
                    今日营收
                  </template>
                  <template #data>
                    {{ data.overviewData?.revenueToday || 0 }}￥
                  </template>
                </RectangleComponents>
                <RectangleComponents @click="toStatisticsHome">
                  <template #icon>
                    <el-icon>
                      <UserFilled/>
                    </el-icon>
                  </template>
                  <template #title>
                    今日订单
                  </template>
                  <template #data>
                    {{ data.overviewData?.orderCountToday || 0 }}单
                  </template>
                </RectangleComponents>
              </div>
              <div v-if="data.userInfo.role">

              </div>
            </div>
          </div>
        </el-col>
        <!--        通知公告-->
        <el-col :span="6">
          <div>
            <div class="Title">
              <title-composition>
                <template #icon>
                  <el-icon class="iconY">
                    <Bell/>
                  </el-icon>
                </template>
                <template #title>
                  通知公告
                </template>
              </title-composition>
            </div>
            <div class="Box">
              系统公告、重要提醒等信息
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script setup>
import TitleComposition from "@/Backend/components/TitleComposition.vue";
import {Bell, CollectionTag, Monitor, Pointer, Tools, UserFilled} from "@element-plus/icons-vue";
import InfoItem from "@/Backend/views/Home/components/InfoItem.vue";


import {nextTick, onMounted, reactive} from "vue";
import router from "@/Backend/router/index.js";
import RectangleComponents from "@/Backend/components/DataStatistics/RectangleComponents.vue";
import request from "@/Backend/utils/request.js";
import OrdersList from "@/Backend/views/Order/OrdersList.vue";

const data = reactive({
  userInfo: JSON.parse(localStorage.getItem("petSysUser")),
  overviewData: null,
});

const toStatisticsHome = () => {
  setTimeout(router.push('StatisticsHome'), 1000)
}
const toUsersHome = () => {
  setTimeout(router.push('Users'), 1000)
}
const toBookingsHome = () => {
  setTimeout(router.push('BookingsHome'), 1000)
}
const toRoomsHome = () => {
  setTimeout(router.push('RoomsHome'), 1000)
}
const toUserCenter = () => {
  setTimeout(router.push('userCenter'), 1000)
}

const getOverviewData = () => {
  // 获取数据
  request.get('/getStatistics/getOverviewData').then((res) => {
    console.log(res)
    data.overviewData = res.data;
  });
};

onMounted(() => {
  getOverviewData()
})


</script>

<style scoped>
.Title {
  margin-bottom: 1px;
}

.Box {
  border-radius: 9px;
  font-size: 12px;
  padding: 5px;
  max-height: 200px;
  min-height: 200px;
  box-shadow: rgba(106, 44, 130, 0.15) 1px 2px 6px 1px;
  border: 1px solid rgba(174, 84, 193, 0.16);

}

.iconY {
  font-size: 10px;
  color: #6a2c82;
  font-weight: bold
}

</style>