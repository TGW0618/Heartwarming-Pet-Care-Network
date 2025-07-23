<template>
  <div class="common-layout">
    <el-container>
      <!-- 左侧菜单开始 -->
      <el-aside width="240px" class="custom-aside">
        <div class="sidebar-container">
          <!-- logo区开始 -->
          <div class="logo-container">
            <div class="logo-content">
              <img src="../assets/icon/宠物天地.png" alt="logo" class="logo-img">
              <div>
                <h2 class="logo-title" style="color: #2c3e50">宠物管家</h2>
                <p class="logo-subtitle" style="color: #7f8c8d">专业寄养管理系统</p>
              </div>
            </div>
          </div>
          <!-- logo区结束 -->

          <!-- 导航菜单开始 -->
          <div class="menu-container">
            <el-menu
                active-text-color="#af54c2"
                text-color="#5a5e66"
                class="custom-menu"
                router
                :default-active="router.currentRoute.value.path"
            >
              <!--              主要-->
              <div style="padding-bottom: 5px">
                <div style="width: 100%;">
                  <p class="menu-title">主要</p>
                </div>
                <el-menu-item index="/admin/home">
                  <div>&nbsp;</div>
                  <el-icon>
                    <House/>
                  </el-icon>
                  <span>首页</span>
                </el-menu-item>
                <el-menu-item index="/admin/1">
                  <div>&nbsp;</div>
                  <el-icon>
                    <Timer/>
                  </el-icon>
                  <span>预约管理</span>
                </el-menu-item>
                <el-menu-item index="/admin/2">
                  <div>&nbsp;</div>
                  <el-icon>
                    <Collection/>
                  </el-icon>
                  <span>订单管理</span>
                </el-menu-item>
              </div>
              <!--              业务-->
              <div style="padding-bottom: 5px">
                <div style="width: 100%;">
                  <p class="menu-title">业务</p>
                </div>
                <el-menu-item index="/admin/3">
                  <div>&nbsp;</div>
                  <el-icon>
                    <Notification/>
                  </el-icon>
                  <span>宠物寄养</span>
                </el-menu-item>
                <el-menu-item index="/admin/4">
                  <div>&nbsp;</div>
                  <el-icon>
                    <Phone/>
                  </el-icon>
                  <span>医疗服务</span>
                </el-menu-item>
                <el-menu-item index="/admin/users">
                  <div>&nbsp;</div>
                  <el-icon>
                    <UserFilled/>
                  </el-icon>
                  <span>用户管理</span>
                </el-menu-item>
              </div>
              <!--              系统-->
              <div style="padding-bottom: 5px">
                <div style="width: 100%;">
                  <p class="menu-title">系统</p>
                </div>
                <el-menu-item index="/admin/5">
                  <div>&nbsp;</div>
                  <el-icon>
                    <Tools/>
                  </el-icon>
                  <span>系统管理</span>
                </el-menu-item>
                <el-menu-item index="/admin/6">
                  <div>&nbsp;</div>
                  <el-icon>
                    <TrendCharts/>
                  </el-icon>
                  <span>数据统计</span>
                </el-menu-item>
              </div>
            </el-menu>
          </div>
          <!-- 导航菜单结束 -->
        </div>
      </el-aside>
      <!-- 左侧菜单结束 -->

      <!-- 右侧内容开始 -->
      <el-container class="main-container">
        <!-- 右侧头部开始 -->
        <el-header class="custom-header">
          <div class="header-content">
            <div class="page-title">{{ $route.meta.title }}</div>
            <div class="search-bar">
              <el-input :prefix-icon="Search" class="search-input" type="text" placeholder="搜索宠物、客户或订单...">
              </el-input>
            </div>
            <div class="user-info">
              <div>
                <el-icon>
                  <Bell/>
                </el-icon>
              </div>
              <div style="padding: 28px">
                <el-icon>
                  <Message/>
                </el-icon>
              </div>
              <div @click="userCenter" style="width: 38px;height: 38px;">
                <img :src="data.user.avatar" alt="头像" class="user-avatar">
              </div>

            </div>
          </div>
        </el-header>
        <!-- 右侧头部结束 -->

        <!-- 右侧内容区域开始 -->
        <el-main class="content-main">
          <router-view></router-view>
        </el-main>
        <!-- 右侧内容区域结束 -->
      </el-container>
      <!-- 右侧内容结束 -->
    </el-container>
  </div>
</template>

<script setup>
import router from "@/Common/router/index.js";
import {
  Bell,
  Collection,
  House,
  Message,
  Notification,
  Phone,
  Search,
  Timer, Tools, TrendCharts,
  UserFilled
} from "@element-plus/icons-vue";
import {reactive,} from "vue";

const data = reactive({
  user: JSON.parse(localStorage.getItem("petSysUser")),
})


const userCenter = () => {
  console.log("个人中心")
  router.push("/admin/userCenter");
};

</script>

<style scoped>
/* 全局样式调整 */
:root {
  --primary-color: #af54c2;
  --primary-light: rgba(175, 84, 194, 0.1);
  --white-color: #ffffff;
  --text-dark: #2c3e50;
  --text-gray: #666666;
  --border-color: #e0e0e0;
}

/* 左侧菜单样式 */
.custom-aside {
  background-color: var(--white-color);
  box-shadow: 1px 0 10px rgba(0, 0, 0, 0.05);
  height: 100vh;
  transition: all 0.3s;

}


.sidebar-container {
  display: flex;
  flex-direction: column;
  height: 100%;
}

/* Logo区域样式 */
.logo-container {
  padding: 24px;
  border-bottom: 1px solid var(--border-color);
  border-bottom: #e9ecef 1px solid;
}

.logo-content {
  display: flex;
  align-items: center;
  gap: 12px;
}

.logo-img {
  width: 45px;
  height: 45px;
}

.logo-title {
  font-size: 18px;
  font-weight: 600;
  color: var(--text-dark);
  margin: 0;
  line-height: 1.2;
}

.logo-subtitle {
  font-size: 12px;
  color: var(--text-gray);
  margin: 0;
}

/* 菜单样式 */
.menu-container {
  flex: 1;
  overflow-y: auto;
  padding: 8px 0;
}

.custom-menu {
  border-right: none;
  font-weight: 500;
  color: var(--text-dark);
}

.custom-menu span {
  font-size: 16px;
  letter-spacing: 0.1rem;
}

.custom-menu > div > .el-menu-item {
  height: 40px;
  border-radius: 0 8px 8px 0;
}

.custom-menu > div > .el-menu-item:hover {
  background-color: #fbf6fc;
  color: #af54c2;
}

.custom-menu > .el-menu-item:active {
  background-color: #f3e6f6;
}

.el-menu .is-active {
  background-color: #f3e6f6 !important;
}

.menu-title {
  font-size: 12px;
  color: #7f8c8d;
  margin-left: 28px;
  margin-bottom: 5px;
}


/* 右侧主容器 */
.main-container {
  background-color: #f8f9fa;
  min-height: 100vh;
}

/* 头部样式 */
.custom-header {
  height: 70px;
  background-color: #ffffff;
  box-shadow: 0 1px 5px rgba(0, 0, 0, 0.03);
  display: flex;
  align-items: center;
  padding: 0 24px;
}

.header-content {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.page-title {
  font-size: 16px;
  font-weight: 500;
  color: #2c3e50;
  min-width: 100px;
}

.search-bar {
  flex: 1;
  max-width: 480px;
  margin: 0 20px;
  position: relative;
  padding-right: 100px;
}

/* 使用 :deep() 穿透 Element Plus 组件样式 */
.search-bar :deep(.el-input__wrapper) {
  width: 100%;
  height: 40px;
  padding-left: 10px;
  border: 1px solid #e2e8f0;
  border-radius: 6px;
  font-size: 14px;
  transition: all 0.2s;
  background-color: #f8f9fa;
  box-shadow: none;
}

.search-bar :deep(.el-input__wrapper.is-focus) {
  outline: none;
  border-color: #af54c2;
  background-color: #ffffff;
  box-shadow: 0 0 0 3px #fbf6fc;
}

.search-bar :deep(.el-input__inner) {
  color: #2d3748;
}

.search-bar :deep(.el-input__inner::placeholder) {
  color: #7f8c8d;
}

/* 用户信息区域 */
.user-info {
  display: flex;
  align-items: center;
  min-width: 160px;
  justify-content: flex-end;
}

.user-avatar {
  cursor: pointer;
  transition: all 0.3s;
  height: 100%;
  width: 100%;
  border-radius: 38px;
}

.user-avatar:hover {
  transform: scale(1.1);
  box-shadow: #987efb 3px 3px 8px 0px;
}


/* 内容区域 */
.content-main {
  padding: 20px;
  background-color: transparent;
}
</style>