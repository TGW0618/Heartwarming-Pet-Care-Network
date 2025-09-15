<!--服务详情组件-->
<template>
  <div class="service-info-card">
    <!-- 服务图片 -->
    <div class="service-image">
      <img
          :src="serviceDataInfo?.imageUrl || defaultImage"
          :alt="serviceDataInfo?.serviceName || '服务图片'"
          @error="handleImageError"
      />
    </div>

    <div style="padding: 10px;">
      <!-- 价格、时长 -->
      <div class="service-details">
        <van-row>
          <van-col span="10" class="price">
            <span style="font-size: 0.3rem;">￥</span>{{ serviceDataInfo?.price || '0.00' }}
          </van-col>
          <van-col span="4"></van-col>
          <van-col span="10" class="duration">服务类别：{{ serviceDataInfo?.category || '类别' }}</van-col>
        </van-row>
      </div>

      <!--服务名称-->
      <div class="service-name">
        {{ serviceDataInfo?.serviceName || '服务名称' }}
      </div>

      <!-- 详情描述 -->
      <div class="service-description">
        <van-row>
          <van-col span="3">
            <div>详情:</div>
          </van-col>
          <van-col span="21">
            <div>
              {{ serviceDataInfo?.description || '详情描述' }}
            </div>
          </van-col>
        </van-row>


      </div>
    </div>
  </div>
</template>

<script setup>
import defaultServiceImage from '@/assets/img/default-service.png'

const props = defineProps({
  serviceDataInfo: {
    type: Object,
    default: () => null
  }
})

// 默认图片
const defaultImage = defaultServiceImage

// 处理图片加载失败
const handleImageError = (event) => {
  event.target.src = defaultImage
}
</script>

<style scoped>
.service-info-card {
  background-color: var(--van-background-2, #ffffff);
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.service-image {
  text-align: center;
  margin-bottom: 16px;
  height: 8rem;
  background-color: var(--van-background-2, #ffffff);
}

.service-image img {
  max-width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 4px;
}

.service-details {
  margin-bottom: 12px;
  font-size: 14px;
}

.price {
  font-weight: bold;
  font-size: 0.8rem;
  color: var(--van-text-color, #333333);
}

.duration {
  text-align: right;
  color: #8a8a8e;
  font-size: 0.2rem;
}

.service-name {
  font-size: 0.8rem;
  font-weight: 600;
  margin-bottom: 8px;
  color: var(--van-text-color, #333333);
}

.service-description {
  color: #8a8a8e;
  line-height: 1.5;
  font-size: 0.38rem;
}
</style>
