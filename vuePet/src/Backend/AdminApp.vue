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
                <h2 class="logo-title">宠物管家</h2>
                <p class="logo-subtitle">专业寄养管理系统</p>
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
              <div class="menu-section">
                <el-menu-item index="/admin/home"

                >
                  <el-icon>
                    <House/>
                  </el-icon>
                  <span>首页</span>
                </el-menu-item>
                <el-menu-item index="/admin/bookingsHome">
                  <el-icon>
                    <Timer/>
                  </el-icon>
                  <span>预约处理</span>
                </el-menu-item>
                <el-menu-item index="/admin/orderHome">
                  <el-icon>
                    <Collection/>
                  </el-icon>
                  <span>订单管理</span>
                </el-menu-item>
                <el-menu-item index="/admin/serviceHome">
                  <el-icon>
                    <Notification/>
                  </el-icon>
                  <span>服务管理</span>
                </el-menu-item>
                <el-menu-item index="/admin/roomsHome"
                              v-if="data.user.role === 'admin' || data.user.role === 'foster_staff'">
                  <el-icon>
                    <OfficeBuilding/>
                  </el-icon>
                  <span>宠舍管理</span>
                </el-menu-item>
              </div>
              <div class="menu-section">
                <el-menu-item index="/admin/users"
                              v-if="data.user.role === 'admin'"
                >
                  <el-icon>
                    <UserFilled/>
                  </el-icon>
                  <span>用户管理</span>
                </el-menu-item>
                <el-menu-item index="/admin/statisticsHome"
                              v-if="data.user.role === 'admin'"
                >
                  <el-icon>
                    <List/>
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
              <el-input
                  v-model="searchText"
                  @keyup.enter="searchInfo"
                  :prefix-icon="Search"
                  class="search-input"
                  type="text"
                  placeholder="搜索宠物、客户或订单..."
              />
            </div>
            <div class="user-info">
              <div class="notification-icon">
                <el-icon @click="toUserMessage">
                  <Bell/>
                </el-icon>
              </div>
              <div class="message-icon">
                <el-icon @click="toUserMail">
                  <Message/>
                </el-icon>
              </div>
              <div class="avatar-wrapper" @click="toUserCenter">
                <!-- 当用户有头像时显示用户头像，否则显示默认头像 -->
                <img
                    v-if="data.user && userStore.userAvatar"
                    :src="userStore.userAvatar"
                    alt="头像"
                    class="user-avatar"
                >
                <el-avatar v-else class="user-avatar">
                  <DefaultAvatar/>
                </el-avatar>
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
import router from "@/Backend/router/index.js";
import {
  Bell, BellFilled, Box, Coin,
  Collection, DataLine, Film, HomeFilled,
  House, List,
  Message,
  Notification, OfficeBuilding,
  Phone,
  Search, SwitchFilled, TakeawayBox,
  Timer,
  Tools,
  TrendCharts, User,
  UserFilled
} from "@element-plus/icons-vue";
import {reactive, ref} from "vue";
import DefaultAvatar from "@/Common/components/DefaultAvatar.vue";
import {useUserStore} from "@/Backend/stores/userStore.js";
import {ElMessage} from "element-plus";

const userStore = useUserStore()


const data = reactive({
  user: JSON.parse(localStorage.getItem("petSysUser"))
})

const searchText = ref("")
const searchInfo = () => {
  if (searchText.value.trim()) {
    ElMessage.info("搜索功能开发中:" + searchText.value)
  } else {
    ElMessage.warning("请输入搜索内容")
  }

};

const toUserMessage = () => {
  router.push('InformHome')
};
const toUserMail = () => {
  router.push('MessageHome')
};

const toUserCenter = () => {
  router.push("/admin/userCenter");
};

const getAvatar = async () => {
  userStore.getUserAvatar(data.user.avatar)
}
getAvatar()


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
  position: fixed; /* 固定定位 */
  top: 0;
  left: 0;
  z-index: 1000; /* 确保在最上层 */
  overflow-y: auto; /* 允许滚动 */
}

/* 右侧主容器 */
.main-container {
  background-color: #f8f9fa;
  min-height: 100vh;
  margin-left: 240px; /* 为固定菜单留出空间 */
}

.sidebar-container {
  display: flex;
  flex-direction: column;
  height: 100%;
}

/* Logo区域样式 */
.logo-container {
  padding: 24px;
  border-bottom: 1px solid #e9ecef;
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

.menu-section {
  padding-bottom: 5px;
}

.custom-menu {
  border-right: none;
  font-weight: 500;
  color: var(--text-dark);
}

.custom-menu :deep(.el-menu-item) {
  height: 40px;
  border-radius: 0 8px 8px 0;
  margin: 2px 0;
}

.custom-menu :deep(.el-menu-item:hover) {
  background-color: #fbf6fc;
  color: #af54c2;
}

.custom-menu :deep(.el-menu-item.is-active) {
  background-color: #f3e6f6 !important;
  color: #af54c2;
}

.menu-title {
  font-size: 12px;
  color: #7f8c8d;
  margin: 15px 28px 5px;
  font-weight: 600;
}

/* 头部样式 */
.custom-header {
  height: 70px;
  background-color: #ffffff;
  box-shadow: 0 1px 5px rgba(0, 0, 0, 0.03);
  display: flex;
  align-items: center;
  padding: 0 24px;
  position: sticky; /* 粘性定位 */
  top: 0;
  z-index: 999;
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
  gap: 20px;
}

.notification-icon, .message-icon {
  font-size: 20px;
  color: #7f8c8d;
  cursor: pointer;
  transition: color 0.3s;
}

.notification-icon:hover, .message-icon:hover {
  color: #af54c2;

}

.avatar-wrapper {
  width: 38px;
  height: 38px;
  cursor: pointer;
  border-radius: 50%;
  overflow: hidden;
  transition: all 0.3s;
}

.avatar-wrapper:hover {
  transform: scale(1.1);
  box-shadow: 2px 2px 18px 0 #af54c2;
}

.user-avatar {
  width: 100%;
  height: 100%;
  display: block;
  object-fit: cover;
}

/* 内容区域 */
.content-main {
  padding: 20px;
  background-color: transparent;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .custom-aside {
    width: 70px;
  }

  .logo-title, .logo-subtitle, .menu-title, .custom-menu span {
    display: none;
  }

  .logo-container {
    padding: 24px 12px;
  }

  .logo-content {
    justify-content: center;
  }

  .logo-img {
    margin: 0 auto;
  }

  .main-container {
    margin-left: 70px;
  }
}


</style>
