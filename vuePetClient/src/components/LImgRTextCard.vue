<template>
  <div style="height: 3rem;">
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
              <!--              leftTextTop-->
              <van-col span="16">
                <div class="left-text-top">
                  <slot name="leftTextTop">
                    {{ serviceItemData.serviceName }}
                  </slot>
                </div>
              </van-col>
              <!--              rightTextTop-->
              <van-col span="8">
                <div class="right-text-top">
                  <slot name="rightTextTop">
                    <van-icon name="gold-coin"/>
                    {{ serviceItemData.price }}
                  </slot>
                </div>
              </van-col>
            </van-row>
          </div>
          <!--          content-->
          <div class="middle-section">
            <slot name="content">
              {{ serviceItemData.description }}
            </slot>
          </div>
          <div class="bottom-section">
            <van-row>
              <!--              leftTextBottom-->
              <van-col span="12">
                <div class="left-text-bottom">
                  <slot name="leftTextBottom">
                    {{ serviceItemData.category }}
                  </slot>
                </div>
              </van-col>
              <!--              rightTextBottom-->
              <van-col span="12">
                <div class="right-text-bottom">
                  <slot name="rightTextBottom">
                    {{ formatServiceType(serviceItemData.serviceType) }}
                  </slot>
                </div>
              </van-col>
            </van-row>
          </div>
        </div>
      </van-col>
    </van-row>
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
    default: ''
  }
})

//格式化服务类别
function formatServiceType(serviceType) {
  switch (serviceType) {
    case 'foster':
      return '寄养'
    case 'medical':
      return '医疗'
  }
}

</script>

<style scoped>
.service-order-card {
  background-color: var(--van-background-2, #ffffff);
  box-shadow: 1px 1px 8px 1px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
}

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
</style>
