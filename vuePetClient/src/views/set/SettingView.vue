<template>
  <div class="setting-container">
    <div class="setting-bg"></div>
    <navBarTop title="我的设置">
      <template #left>
        <div @click="onClickLeft">
          <slot>
            <van-icon name="arrow-left"/>
          </slot>
        </div>
      </template>
    </navBarTop>

    <div class="setting-content">
      <!-- 用户信息卡片 -->
      <div class="user-card">
        <van-image
            round
            width="60px"
            height="60px"
            :src="userInfoStore.userInfo?.avatar || '/img/default-avatar.png'"
            class="user-avatar"
        />
        <div class="user-info">
          <div class="realName">{{ userInfoStore.userInfo?.realName || 'Hi' }}</div>
          <div class="user-id">ID: {{ userInfoStore.userInfo?.username || '--' }}</div>
        </div>
      </div>

      <!-- 设置选项列表 -->
      <div class="setting-list">
        <div class="setting-item" @click="goToAccount">
          <div class="item-icon">
            <van-icon name="user-circle-o" size="20" color="#c97536"/>
          </div>
          <div class="item-content">
            <span class="item-label">账户设置</span>
            <van-icon name="arrow" size="16" color="#958382"/>
          </div>
        </div>

        <div class="setting-item" @click="goToPrivacy">
          <div class="item-icon">
            <van-icon name="shield-o" size="20" color="#c97536"/>
          </div>
          <div class="item-content">
            <span class="item-label">隐私声明</span>
            <van-icon name="arrow" size="16" color="#958382"/>
          </div>
        </div>

        <div class="setting-item" @click="goToNotification">
          <div class="item-icon">
            <van-icon name="bullhorn-o" size="20" color="#c97536"/>
          </div>
          <div class="item-content">
            <span class="item-label">通知设置</span>
            <van-icon name="arrow" size="16" color="#958382"/>
          </div>
        </div>

        <div class="setting-item" @click="goToAbout">
          <div class="item-icon">
            <van-icon name="info-o" size="20" color="#c97536"/>
          </div>
          <div class="item-content">
            <span class="item-label">关于我们</span>
            <van-icon name="arrow" size="16" color="#958382"/>
          </div>
        </div>
      </div>
    </div>

    <!-- 退出登录按钮 -->
    <div class="action-area">
      <van-button
          round
          block
          type="default"
          @click="logout"
          class="logout-btn"
      >
        退出登录
      </van-button>
    </div>
  </div>
</template>

<script setup>
import {showConfirmDialog, showToast} from 'vant';
import useUserInfoStore from "@/stores/userInfo.js";
import router from "@/router/index.js";
import navBarTop from "@/components/navBarTop.vue";
import {onMounted} from "vue";

const userInfoStore = useUserInfoStore();

// 在组件挂载时获取用户信息
onMounted(async () => {
  // 确保在用户已登录的情况下获取用户信息
  if (userInfoStore.getToken && !userInfoStore.getUserData) {
    try {
      await userInfoStore.fetchUserInfo();
    } catch (error) {
      // 处理错误，可能需要重新登录
      console.error('获取用户信息失败，可能需要重新登录:', error);
      showToast('获取用户信息失败，请重新登录');
      await router.push('/login');
    }
  }
});

const onClickLeft = () => {
  router.go(-1)
};

const logout = () => {
  showConfirmDialog({
    title: '确认退出',
    message: '确定要退出当前账号吗？',
    closeOnClickOverlay: true,
    className: 'pet-delete-dialog' // 添加自定义类名
  }).then(() => {
    userInfoStore.clearUserInfo();
    router.push('/login');
    showToast({
      message: '已退出登录',
      icon: 'success',
    });
  }).catch(() => {
    // 取消操作
  });
};

const goToAccount = () => {
  showToast('开发中');
};

const goToPrivacy = () => {
  showToast('开发中');
};

const goToNotification = () => {
  showToast('开发中');
};

const goToAbout = () => {
  showToast('开发中');
};
</script>

<style scoped>
.setting-container {
  min-height: 100vh;
  position: relative;
}

.setting-bg {
  width: 100%;
  height: 100vh;
  /*background-image: url('/img/smiley-dog-laying-bed.jpg');*/
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 0;
}

.setting-content {
  position: relative;
  min-height: 100vh;
  backdrop-filter: blur(18px);
  padding: 0 16px;
  padding-top: 60px;
}

/* 用户卡片样式 */
.user-card {
  display: flex;
  align-items: center;
  background: rgba(255, 255, 255, 0.5);
  border-radius: 12px;
  padding: 20px;
  margin: 16px 0;
  color: #c97536;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.user-avatar {
  border: 3px solid rgba(201, 117, 54, 0.3);
}

.user-info {
  margin-left: 16px;
}

.realName {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 4px;
}

.user-id {
  font-size: 14px;
  color: #958382;
}

/* 设置列表样式 */
.setting-list {
  background-color: rgba(255, 255, 255, 0.5);
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.setting-item {
  display: flex;
  align-items: center;
  padding: 16px;
  position: relative;
}

.setting-item:not(:last-child)::after {
  content: '';
  position: absolute;
  left: 56px;
  right: 0;
  bottom: 0;
  height: 1px;
  background-color: rgba(89, 47, 17, 0.1);
}

.item-icon {
  width: 40px;
  height: 40px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 12px;
  background-color: rgba(201, 117, 54, 0.1);
}

.item-content {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.item-label {
  font-size: 16px;
  color: #141414;
  font-weight: 500;
}

/* 操作区域样式 */
.action-area {
  position: fixed;
  bottom: 30px;
  left: 16px;
  right: 16px;
}

.logout-btn {
  height: 48px;
  font-size: 16px;
  font-weight: 500;
  color: #c97536;
  background-color: rgba(255, 255, 255, 0.5);
}

.logout-btn:active {
  background-color: rgba(201, 117, 54, 0.1);
}
</style>
<style>
/* 非scoped样式，用于覆盖组件库样式 */
.pet-delete-dialog .van-dialog__confirm {
  background-color: #ffffff !important;
  font-weight: bold;
  color: #e5253d !important;
}

.pet-delete-dialog .van-dialog__cancel {
  color: #fa233b !important;
  font-weight: bold;
  background-color: #ffffff !important;
}

</style>