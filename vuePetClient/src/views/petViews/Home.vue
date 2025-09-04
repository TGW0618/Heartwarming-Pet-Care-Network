<!--首页-->
<template>
  <div class="home-container">
    <div class="header">
      <h1 class="title">暖心宠物护理网络</h1>
      <p class="subtitle">为您提供专业、贴心的宠物服务</p>
    </div>

    <div class="quick-actions">
      <div class="feature-card">
        <van-icon name="calendar-o" class="feature-icon" />
        <h2 class="feature-title">服务预约</h2>
        <p class="feature-desc">在线预约寄养、美容、医疗等服务</p>
        <van-button
          to="/booking/bookingHome"
          class="feature-button"
          round
        >
          立即预约
        </van-button>
      </div>

      <div class="feature-card">
        <van-icon name="service-o" class="feature-icon" />
        <h2 class="feature-title">在线咨询</h2>
        <p class="feature-desc">专业宠物医生在线解答疑问</p>
        <van-button
          to="/consultation"
          class="feature-button"
          round
        >
          在线咨询
        </van-button>
      </div>
    </div>

    <!-- 服务分类展示 -->
    <div class="services-section">
      <h2 class="section-title">我们的服务</h2>
      <div class="services-grid">
        <div class="service-item" @click="$router.push('/booking/bookingHome')">
          <van-icon name="home-o" class="service-icon" />
          <div class="service-info">
            <h3 class="service-name">宠物寄养</h3>
            <p class="service-desc">专业舒适的寄养环境，让您放心出行</p>
          </div>
        </div>

        <div class="service-item" @click="$router.push('/booking/bookingHome')">
          <van-icon name="brush-o" class="service-icon" />
          <div class="service-info">
            <h3 class="service-name">美容护理</h3>
            <p class="service-desc">专业美容师，让您的宠物焕然一新</p>
          </div>
        </div>

        <div class="service-item" @click="$router.push('/booking/bookingHome')">
          <van-icon name="hospital-o" class="service-icon" />
          <div class="service-info">
            <h3 class="service-name">医疗服务</h3>
            <p class="service-desc">专业兽医团队，全面健康保障</p>
          </div>
        </div>

        <div class="service-item" @click="$router.push('/booking/bookingHome')">
          <van-icon name="food-o" class="service-icon" />
          <div class="service-info">
            <h3 class="service-name">营养咨询</h3>
            <p class="service-desc">科学饮食搭配，健康成长方案</p>
          </div>
        </div>
      </div>
    </div>

    <!-- 热门服务推荐 -->
    <div class="recommend-section">
      <h2 class="section-title">热门服务推荐</h2>
      <div class="recommend-list">
        <div
          class="recommend-item"
          v-for="item in recommendServices"
          :key="item.id"
          @click="$router.push(`/service/${item.id}`)"
        >
          <van-image
            :src="item.image"
            class="recommend-image"
            radius="8"
          />
          <div class="recommend-info">
            <h3 class="recommend-title">{{ item.name }}</h3>
            <p class="recommend-price">¥{{ item.price }}</p>
            <van-tag type="primary" class="recommend-tag">{{ item.category }}</van-tag>
          </div>
        </div>
      </div>
    </div>

    <div class="footer">
      <p class="footer-text">© 2025 暖心宠物护理网络</p>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

// 模拟热门服务数据
const recommendServices = ref([
  {
    id: 1,
    name: '基础美容套餐',
    price: '88.00',
    category: '美容',
    image: '/img/beauty-basic.jpg'
  },
  {
    id: 2,
    name: '全面体检服务',
    price: '168.00',
    category: '医疗',
    image: '/img/checkup.jpg'
  },
  {
    id: 3,
    name: '豪华寄养套餐',
    price: '128.00',
    category: '寄养',
    image: '/img/foster-luxury.jpg'
  },
  {
    id: 4,
    name: '疫苗接种服务',
    price: '98.00',
    category: '医疗',
    image: '/img/vaccine.jpg'
  }
])
</script>

<style scoped>
.home-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #e6f0ff 0%, #cce0ff 100%);
  padding: 20px;
  box-sizing: border-box;
}

.header {
  text-align: center;
  padding: 30px 0;
}

.title {
  font-size: 28px;
  font-weight: 600;
  color: #007aff;
  margin-bottom: 10px;
}

.subtitle {
  font-size: 16px;
  color: #666;
  margin-top: 0;
}

.quick-actions {
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin: 20px 0;
}

.feature-card {
  background: white;
  border-radius: 16px;
  padding: 30px 20px;
  text-align: center;
  box-shadow: 0 4px 12px rgba(0, 122, 255, 0.15);
  transition: all 0.3s ease;
}

.feature-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 16px rgba(0, 122, 255, 0.25);
}

.feature-icon {
  font-size: 48px;
  color: #007aff;
  margin-bottom: 15px;
}

.feature-title {
  font-size: 22px;
  font-weight: 600;
  color: #333;
  margin: 15px 0 10px;
}

.feature-desc {
  font-size: 14px;
  color: #666;
  margin-bottom: 20px;
  line-height: 1.5;
}

.feature-button {
  width: 80%;
  height: 45px;
  background: #007aff;
  border: none;
  font-size: 16px;
  font-weight: 500;
  color: white;
}

.feature-button::after {
  border: none;
}

/* 服务分类样式 */
.services-section {
  margin: 30px 0;
}

.section-title {
  font-size: 22px;
  font-weight: 600;
  color: #333;
  text-align: center;
  margin-bottom: 20px;
}

.services-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 15px;
}

.service-item {
  background: white;
  border-radius: 12px;
  padding: 20px 15px;
  display: flex;
  flex-direction: column;
  align-items: center;
  box-shadow: 0 2px 8px rgba(0, 122, 255, 0.1);
  transition: all 0.3s ease;
  cursor: pointer;
}

.service-item:hover {
  transform: translateY(-3px);
  box-shadow: 0 4px 12px rgba(0, 122, 255, 0.2);
}

.service-icon {
  font-size: 32px;
  color: #007aff;
  margin-bottom: 10px;
}

.service-info {
  text-align: center;
}

.service-name {
  font-size: 16px;
  font-weight: 500;
  color: #333;
  margin: 0 0 5px;
}

.service-desc {
  font-size: 12px;
  color: #666;
  margin: 0;
  line-height: 1.4;
}

/* 热门推荐样式 */
.recommend-section {
  margin: 30px 0;
}

.recommend-list {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.recommend-item {
  background: white;
  border-radius: 12px;
  padding: 15px;
  display: flex;
  box-shadow: 0 2px 8px rgba(0, 122, 255, 0.1);
  transition: all 0.3s ease;
  cursor: pointer;
}

.recommend-item:hover {
  transform: translateX(5px);
  box-shadow: 0 4px 12px rgba(0, 122, 255, 0.2);
}

.recommend-image {
  width: 80px;
  height: 80px;
  flex-shrink: 0;
}

.recommend-info {
  flex: 1;
  padding-left: 15px;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.recommend-title {
  font-size: 16px;
  font-weight: 500;
  color: #333;
  margin: 0 0 8px;
}

.recommend-price {
  font-size: 18px;
  font-weight: 600;
  color: #007aff;
  margin: 0 0 8px;
}

.recommend-tag {
  align-self: flex-start;
}

.footer {
  text-align: center;
  margin-top: 40px;
  padding-bottom: 20px;
}

.footer-text {
  font-size: 12px;
  color: #999;
}

/* 响应式设计 */
@media (min-width: 768px) {
  .quick-actions {
    flex-direction: row;
    justify-content: center;
  }

  .feature-card {
    width: 300px;
    margin: 0 10px;
  }

  .title {
    font-size: 36px;
  }

  .subtitle {
    font-size: 18px;
  }

  .services-grid {
    grid-template-columns: repeat(4, 1fr);
  }

  .recommend-list {
    flex-direction: row;
    flex-wrap: wrap;
  }

  .recommend-item {
    width: calc(50% - 10px);
  }
}
</style>
