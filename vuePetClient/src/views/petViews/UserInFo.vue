<!--个人中心-->
<template>
  <div>
<!--    <div class="user-info-bg"></div>-->
    <div class="user-info">
      <!--        顶部盒子开始-->
      <div class="top-box-wrapper">
        <div class="top-box-container">
          <div>
            <van-row>
              <!--                头像开始-->
              <van-col span="8" class="avatar-col">
                <div class="avatar-container" @click="onAvatarClick">
                  <img
                      v-if="data.userInfo?.avatar && data.userInfo.avatar.trim() !== ''"
                      :src="data.userInfo.avatar"
                      alt="头像"
                      class="avatar-image"
                  />
                  <img
                      v-else
                      src="/img/default-avatar.png"
                      alt="默认头像"
                      class="avatar-image"
                  />
                </div>
              </van-col>
              <!--                头像结束-->
              <!--              顶部盒子内容开始-->
              <van-col span="16" class="info-content-col">
                <div>
                  <van-row>
                    <van-col span="24">
                      <!-- 条件渲染 -->
                      <p v-if="data.userInfo" class="welcome-text">HI,{{ data.userInfo.realName }}</p>
                      <p v-else class="welcome-text">HI,请先登录</p>

                      <!-- 条件渲染 -->
                      <p v-if="data.userInfo" class="account-text">
                        <span
                            style="background-color: rgba(12,12,12,0.95);border-radius: 0.11rem;padding: 0.06rem 0.15rem;">账号</span>
                        : {{ data.userInfo.username }}
                      </p>
                      <p v-else class="account-text">
                        <span
                            style="background-color: rgba(12,12,12,0.95);border-radius: 0.11rem;padding: 0.06rem 0.15rem;">状态</span>
                        : 未登录
                      </p>
                    </van-col>
                  </van-row>
                  <van-row class="stats-row">
                    <van-col span="8" class="stats-col">
                      <div>
                        <p class="stats-text">
                          <span class="stats-number">88</span><br/>
                          <span class="stats-label">积分</span>
                        </p>
                      </div>
                    </van-col>
                    <van-col span="8" class="stats-col">
                      <div>
                        <p class="stats-text">
                          <span class="stats-number">6</span><br/>
                          <span class="stats-label">优惠券</span>
                        </p>
                      </div>
                    </van-col>
                    <van-col span="8" class="stats-col">
                      <div>
                        <p class="stats-text">
                          <span class="stats-number">8.8</span><br/>
                          <span class="stats-label">余额</span>
                        </p>
                      </div>
                    </van-col>
                  </van-row>
                </div>
              </van-col>
              <!--              顶部盒子内容结束-->
            </van-row>
          </div>
        </div>
      </div>
      <!--        顶部盒子结束-->
      <!--      内容开始-->
      <div>
        <!--        内容盒子1开始-->
        <div class="content-wrapper">
          <div class="content-container">
            <van-grid
                class="content-container-second-grid"
                center :column-num="4" :gutter="20"
                :border="false"
            >

              <!--             资料-->
              <van-grid-item to="/userProfile">
                <template #icon>
                  <van-icon name="friends-o"/>
                </template>
                <template #text>
                  <div>
                    <p style="font-size: 0.2rem;color: #141414">
                      资料
                    </p>
                  </div>
                </template>
              </van-grid-item>
              <!--      爱宠        -->
              <van-grid-item to="/petsProfile">
                <template #icon>
                  <van-icon name="like-o"/>
                </template>
                <template #text>
                  <div>
                    <p style="font-size: 0.2rem;color: #141414">爱宠</p>
                  </div>
                </template>
              </van-grid-item>
              <!--              寄养-->
              <van-grid-item to="/foster">
                <template #icon>
                  <van-icon name="shop-collect-o"/>
                </template>
                <template #text>
                  <div>
                    <p style="font-size: 0.2rem;color: #141414">寄养</p>
                  </div>
                </template>
              </van-grid-item>
              <!--              医疗-->
              <van-grid-item to="/medical">
                <template #icon>
                  <van-icon name="shield-o"/>
                </template>
                <template #text>
                  <div>
                    <p style="font-size: 0.2rem;color: #141414">医疗</p>
                  </div>
                </template>
              </van-grid-item>

            </van-grid>
          </div>
        </div>
        <!--        内容盒子1结束-->

        <!--        内容盒子2开始-->
        <div class="content-wrapper-second">
          <div class="content-container-second">
            <van-grid
                class="content-container-second-grid"
                center :column-num="4" :gutter="20"
                :border="false"
            >

              <!--              设置-->
              <van-grid-item to="/setting">
                <template #icon>
                  <van-icon name="setting-o"/>
                </template>
                <template #text>
                  <div>
                    <p style="font-size: 0.2rem;color: #141414">设置</p>
                  </div>
                </template>
              </van-grid-item>
              <!--      反馈        -->
              <van-grid-item to="/feedback">
                <template #icon>
                  <van-icon name="orders-o"/>
                </template>
                <template #text>
                  <div>
                    <p style="font-size: 0.2rem;color: #141414">反馈</p>
                  </div>
                </template>
              </van-grid-item>
              <!--              评价-->
              <van-grid-item to="/appraise">
                <template #icon>
                  <van-icon name="edit"/>
                </template>
                <template #text>
                  <div>
                    <p style="font-size: 0.2rem;color: #141414">评价</p>
                  </div>
                </template>
              </van-grid-item>
              <!--              客服-->
              <van-grid-item to="/service">
                <template #icon>
                  <van-icon name="service-o"/>
                </template>
                <template #text>
                  <div>
                    <p style="font-size: 0.2rem;color: #141414">客服</p>
                  </div>
                </template>
              </van-grid-item>

            </van-grid>
          </div>
        </div>
        <!--        内容盒子2结束-->

      </div>
      <!--      内容结束-->
    </div>
  </div>
</template>

<script setup>
import useUserInfoStore from "@/stores/userInfo.js";
import {onMounted, reactive} from "vue";
import router from "@/router/index.js";
import {showToast} from "vant";

const userInfoStore = useUserInfoStore();

const data = reactive({
  userInfo: userInfoStore.userData || null,
});


// 在组件挂载时获取用户信息
onMounted(async () => {
  if (userInfoStore.getToken && !userInfoStore.getUserInfo) {
    try {
      await userInfoStore.fetchUserInfo();
      data.userInfo = userInfoStore.userInfo; // 使用 userInfo
    } catch (error) {
      // ...
    }
  } else if (userInfoStore.getUserInfo) {
    data.userInfo = userInfoStore.userInfo; // 使用 userInfo
  }
});


// 导航方法
const onAvatarClick = () => {
  if (userInfoStore.getToken) {
    router.push("/avatar")
  } else {
    showToast('请先登录');
  }
};

</script>

<style scoped>
.user-info-bg {
  width: 100%;
  height: 100vh;
  background-image: url('/img/smiley-dog-laying-bed.jpg');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 0;
}

.user-info {
  position: relative;
  min-height: 100vh;
  backdrop-filter: blur(18px);
}

.top-box-wrapper {
  width: 100%;
  height: auto;
  padding-top: 4rem;
}

.top-box-container {
  background-color: rgba(255, 255, 255, 0.5);
  width: 90%;
  height: 4rem;
  margin: 0 auto;
  border-radius: 9px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.avatar-col {
  height: 100%;
  padding-left: 0.45rem;
  margin-top: -0.6rem;
}

.avatar-container {
  width: 2.8rem;
  height: 2.8rem;
  border-radius: 2.8rem;
}

.avatar-image {
  height: 100%;
  width: 100%;
  border-radius: 2.8rem;
  object-fit: cover;
}

.info-content-col {
  height: 100%;
  padding-top: 0.2rem;
  padding-left: 1rem;
}

.welcome-text {
  font-size: 0.45rem;
  color: #c97536;
  font-weight: 500;
}

.account-text {
  font-size: 0.3rem;
  color: #958382;
  margin-top: -0.4rem;
}

.stats-row {
  padding-top: 0.5rem;
}

.stats-col {
  border-right: 0.01rem solid rgba(89, 47, 17, 0.1);
}

.stats-col:last-child {
  border-right: none;
}

.stats-text {
  font-size: 0.3rem;
  color: #958382;
  text-align: center;
}

.stats-number {
  color: #c97536;
}

.stats-label {
  color: #958382;
  padding-top: 1rem;
}

.stats-label:first-child {
  font-size: 0.35rem;
  color: #958382;
  background-color: rgba(6, 6, 6, 0.98);
  border-radius: 0.1rem;
  padding: 0.05rem 0.1rem;
}

.content-wrapper {
  width: 100%;
  height: auto;
  padding-top: 0.5rem;
}

.content-container {
  background-color: rgba(255, 255, 255, 0.5);
  width: 90%;
  margin: 0 auto;
  border-radius: 9px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.content-wrapper-second {
  width: 100%;
  height: auto;
  padding-top: 0.5rem;
}

.content-container-second {
  background-color: rgba(255, 255, 255, 0.5);
  width: 90%;
  height: auto;
  margin: 0 auto;
  border-radius: 9px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  text-align: center
}

.content-container-second-grid {
  --van-grid-item-content-background: none;
}

/* 添加网格项的点击效果 */
:deep(.van-grid-item__content) {
  transition: background-color 0.3s;
}

:deep(.van-grid-item__content:active) {
  background-color: rgba(255, 255, 255, 0.7);
}
</style>
