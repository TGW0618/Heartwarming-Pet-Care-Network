<!--宠物信息组件-->
<template>
  <div class="pets-info">
    <div class="img-container">
      <img :src="finalImageUrl" alt="宠物图片" @error="handleImageError" class="pet-avatar"/>
    </div>
    <div class="text-container">
      <van-row justify="space-between" align="center">
        <van-col span="10" class="text-col">
          <div class="left-text">
            <slot name="leftText"></slot>
          </div>
        </van-col>
        <van-col span="4" class="spacer-col"></van-col>
        <van-col span="10" class="text-col">
          <div class="right-text">
            <slot name="rightText"></slot>
          </div>
        </van-col>
      </van-row>
    </div>
  </div>
</template>

<script setup>
import {computed} from 'vue'

// 定义 props
const props = defineProps({
  imageUrl: {
    type: String,
    default: ''
  }
})

const finalImageUrl = computed(() => {
  // 如果传入了imageUrl且不为空，则使用传入的图片
  if (props.imageUrl && props.imageUrl !== '@/assets/img/default-avatar.png') {
    return props.imageUrl
  }
  // 否则使用默认图片
  return new URL('../assets/img/default-avatar.png', import.meta.url).href
})

// 处理图片加载失败的情况
const handleImageError = (event) => {
  event.target.src = new URL('../assets/img/default-avatar.png', import.meta.url).href
}
</script>

<style scoped>
.pets-info {
  display: flex;
  flex-direction: column;
  background: #ffffff;
  border-radius: 18px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  height: 100%;
  width: 100%;
  transition: transform 150ms ease; /* 平滑过渡效果 */
}
.pets-info:active {
  transform: scale(0.95);
}

.img-container {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 10px 10px 5px 10px;
}

.pet-avatar {
  width: 100%;
  height: 100px;
  object-fit: cover;
  border-radius: 10px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.text-container {
  padding: 8px;
}

.text-col {
  display: flex;
  justify-content: center;
  align-items: center;
}

.spacer-col {
  /* 用于在左右文本之间创建间隔 */
}

.left-text, .right-text {
  font-size: 12px;
  text-align: center;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  width: 100%;
}

.left-text {
  font-weight: 600;
  color: #333333;
}

.right-text {
  color: #666666;
  background: #f0f0f0;
  padding: 2px 6px;
  border-radius: 8px;
}
</style>
