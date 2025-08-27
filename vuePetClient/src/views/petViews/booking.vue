<template>
  <div class="booking-home">

    <div class="service-categories">
      <div class="category-card" @click="goToMedical">
        <van-icon name="shield-o" size="30" color="#3e6ae1"/>
        <h3>医疗服务</h3>
        <p>疫苗接种、疾病诊疗、健康检查</p>
      </div>

      <div class="category-card" @click="goToFoster">
        <van-icon name="hotel-o" size="30" color="#3e6ae1"/>
        <h3>寄养服务</h3>
        <p>专业宠物寄养、日常护理</p>
      </div>

      <div class="category-card" @click="goToGrooming">
        <van-icon name="brush-o" size="30" color="#3e6ae1"/>
        <h3>美容服务</h3>
        <p>洗澡、修剪、造型设计</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import {useRouter} from 'vue-router'

const router = useRouter()

const goToMedical = () => {
  console.log('go to medical')
  router.push('/medical')
}

const goToFoster = () => {
  // router.push('/foster')
  // 暂时跳转到404
  router.push('/404')
}

const goToGrooming = () => {
  router.push('/404')
}
</script>

<style scoped>
.booking-home {
  background-color: var(--van-background-2, #ffffff);
  padding: 16px;
  min-height: 100vh;

}

.service-categories {
  margin-top: 20px;
}

.category-card {
  border-radius: 12px;
  padding: 20px;
  margin-bottom: 16px;
  text-align: center;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  cursor: pointer;
}

.category-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.category-card h3 {
  margin: 12px 0 8px 0;
  color: var(--van-text-color, #333333);
  font-size: 18px;
}

.category-card p {
  color: #666;
  font-size: 14px;
  line-height: 1.4;
}
</style>
