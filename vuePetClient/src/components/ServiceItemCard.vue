<!--服务展示卡片-->
<template>
  <div class="service-card">
    <!--    服务图片-->
    <div class="image-wrapper">
      <img
          :src="props.serviceData.imageUrl || defaultImage"
          alt="服务图片"
          class="service-image"
          @error="handleImageError"
      />
    </div>
    <!--    服务名称-->
    <div class="service-name">
      <span>{{ serviceData.serviceName }}</span>
    </div>
    <!--    服务价格-->
    <div class="service-price">
      <span>{{ serviceData.price }}</span>
    </div>
  </div>
</template>

<script setup>

// 默认图片
import {ref} from "vue";

const defaultImage = ref('@/assets/img/default-service.png')

const props = defineProps({
  serviceData: {
    type: Object,
    default: () => ({
      id: '',
      serviceName: '默认服务',
      imageUrl: '@/assets/img/default-service.png',
      price: '0.00'
    })
  }
})

// 处理图片加载错误
const handleImageError = (event) => {
  event.target.src = defaultImage.value
}
</script>

<style scoped>
.service-card {
  background-color: var(--van-background-2, #ffffff);
  border-radius: 8px;
}

.image-wrapper {
  height: 4rem;
  width: 4rem;
  border-radius: 8px 8px 0 0;
}

.service-image {
  border-radius: 8px 8px 0 0;
  height: 100%;
  width: 100%;
  object-fit: cover
}

.service-name {
  font-size: 16px;
  padding: 0.1rem;
}

.service-price {
  font-size: 18px;
  font-weight: bold;
  padding-left: 0.1rem;
}
</style>
