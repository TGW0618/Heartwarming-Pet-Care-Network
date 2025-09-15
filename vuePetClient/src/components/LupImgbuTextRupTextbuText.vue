<template>
  <div class="order-container">
    <!-- 主要内容区域 -->
    <div class="main-content">
      <van-row class="service-order-card">
        <van-col span="7" class="image-col">
          <div class="image-container">
            <img
                class="service-image"
                :src="serviceItemImgUrl"
                alt="服务图片"
            >
          </div>
        </van-col>
        <van-col span="17" class="info-col">
          <div class="info-content">
            <div class="top-section">
              <van-row>
                <!-- leftTextTop -->
                <van-col span="16">
                  <div class="left-text-top">
                    <slot name="leftTextTop"></slot>
                  </div>
                </van-col>
                <!-- rightTextTop -->
                <van-col span="8">
                  <div class="right-text-top">
                    <slot name="rightTextTop"></slot>
                  </div>
                </van-col>
              </van-row>
            </div>
            <!-- content -->
            <div class="middle-section">
              <slot name="content"></slot>
            </div>
            <div class="bottom-section">
              <van-row>
                <!-- leftTextBottom -->
                <van-col span="12">
                  <div class="left-text-bottom">
                    <slot name="leftTextBottom"></slot>
                  </div>
                </van-col>
                <!-- rightTextBottom -->
                <van-col span="12">
                  <div class="right-text-bottom">
                    <slot name="rightTextBottom"></slot>
                  </div>
                </van-col>
              </van-row>
            </div>
          </div>
        </van-col>
      </van-row>
    </div>

    <!-- 金额信息区域 -->
    <div class="amount-section">
      <van-row justify="end">
        <van-col span="24" class="amount-col">
          <slot name="amount">应付金额：￥666</slot>
        </van-col>
      </van-row>
    </div>

    <!-- 操作按钮区域 -->
    <div class="actions-section">
      <div class="actions-wrapper">
        <slot name="actions">
          <van-button class="action-button" plain size="small">删除订单</van-button>
          <van-button class="action-button" plain size="small">查看详情</van-button>
        </slot>
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  serviceItemData: {
    type: Object,
    default: () => ({})
  },
  serviceItemImgUrl: {
    type: String,
    default: '/files/1756151107650_ke-ai-de-xiao-chong-wu-pin-tie-gu-li.jpg'
  },
})
</script>

<style scoped>
/* 容器样式 */
.order-container {
  background-color: var(--van-background-2, #ffffff);
  box-shadow: 1px 1px 8px 1px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  overflow: hidden;
}

/* 主要内容区域 */
.main-content {
  padding: 0.2rem;
}

.service-order-card {
  background-color: var(--van-background-2, #ffffff);
}

/* 图片区域样式 */
.image-col {
  height: 3rem;
}

.image-container {
  width: 100%;
  height: 100%;
  text-align: center;
}

.service-image {
  width: 2.5rem;
  height: 2.5rem;
  margin-top: 0.25rem;
  border-radius: 18px;
  box-shadow: 1px 1px 8px 1px rgba(0, 0, 0, 0.1);
}

.info-col {
  height: 2rem;
  padding-left: 0.1rem;
  padding-right: 0.2rem;
  padding-top: 0.2rem;
}

.info-content {
  height: 100%;
}

.top-section {
  height: 0.5rem;
  font-size: 0.3rem;
  border-bottom: 1px solid #eaeaea;
}

.left-text-top {
  text-align: left;
  font-weight: bold;
}

.right-text-top {
  font-weight: bold;
}

.middle-section {
  height: 1.65rem;
  font-size: 0.3rem;
  color: #8a8a8e;
}

.bottom-section {
  height: 0.5rem;
  font-size: 0.3rem;
  color: #8a8a8e;
}

.right-text-top {
  text-align: right;
}

.right-text-bottom {
  text-align: right;
}

/* 金额信息区域 */
.amount-section {
  padding: 0.1rem 0.2rem;
  border-top: 1px solid #eaeaea;
  border-bottom: 1px solid #eaeaea;
}

.amount-col {
  font-size: 0.38rem;
  text-align: right;
  font-weight: bold;
}

/* 操作按钮区域 */
.actions-section {
  padding: 0.2rem;
  min-height: 1.2rem;
}

.actions-wrapper {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  height: 100%;
  gap: 0.1rem;
}

.action-button {
  color: black;
  border-radius: 8px;
  height: 0.8rem;
  min-width: 1.6rem;
  font-size: 0.3rem;
}

/* 响应式优化 */
@media (max-width: 350px) {
  .action-button {
    font-size: 0.28rem;
    padding: 0 0.1rem;
    min-width: 1.4rem;
  }
}

/* 确保在各种屏幕尺寸下的一致性 */
@media (max-width: 320px) {
  .actions-wrapper {
    gap: 0.05rem;
  }

  .action-button {
    min-width: 1.3rem;
    padding: 0 0.05rem;
  }
}
</style>
