<!--预约分类-->
<template>
  <div class="booking-home">
    <div class="header-section">
      <h1 class="page-title">服务预约</h1>
      <p class="page-subtitle">选择您需要的服务类型，为您的宠物预约专业服务</p>
    </div>

    <div class="service-categories">
      <div class="category-card" @click="goToMedical">
        <div class="icon-wrapper medical">
          <van-icon name="shield-o" size="36" color="#fff"/>
        </div>
        <div class="card-content">
          <h3 class="card-title">医疗服务</h3>
          <p class="card-description">疫苗接种、疾病诊疗、健康检查</p>
          <div class="card-footer">
            <van-tag color="#e6f0ff" text-color="#3e6ae1">专业医疗团队</van-tag>
          </div>
        </div>
      </div>

      <div class="category-card" @click="goToFoster">
        <div class="icon-wrapper foster">
          <van-icon name="hotel-o" size="36" color="#fff"/>
        </div>
        <div class="card-content">
          <h3 class="card-title">寄养服务</h3>
          <p class="card-description">专业宠物寄养、日常护理</p>
          <div class="card-footer">
            <van-tag color="#fff7e6" text-color="#fa8900">舒适环境</van-tag>
          </div>
        </div>
      </div>

      <div class="category-card" @click="goToGrooming">
        <div class="icon-wrapper grooming">
          <van-icon name="brush-o" size="36" color="#fff"/>
        </div>
        <div class="card-content">
          <h3 class="card-title">美容服务</h3>
          <p class="card-description">洗澡护理、造型修剪、毛发护理</p>
          <div class="card-footer">
            <van-tag color="#FFEEEEFF" text-color="#ef3c6e">专业美容师</van-tag>
          </div>
        </div>
      </div>

      <div class="category-card" @click="goToTraining">
        <div class="icon-wrapper training">
          <van-icon name="manager-o" size="36" color="#fff"/>
        </div>
        <div class="card-content">
          <h3 class="card-title">训练服务</h3>
          <p class="card-description">行为纠正、技能训练、社交培养</p>
          <div class="card-footer">
            <van-tag color="#f5f0ff" text-color="#8a6de9">专业训犬师</van-tag>
          </div>
        </div>
      </div>
    </div>

    <div class="info-section">
      <van-notice-bar
          left-icon="volume-o"
          text="亲爱的客户,你好呀! 预约成功后，请按时带宠物前来服务哦!"
          background="#ecf5ff"
          color="#3e6ae1"
          style="border-radius: 16px"
      />

      <div class="tips">
        <h3 class="tips-title">预约须知</h3>
        <ul class="tips-list">
          <li>请提前至少1天预约</li>
          <li>首次服务请携带宠物疫苗本</li>
          <li>如需取消预约，请提前12小时通知</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup>
import {useRouter} from 'vue-router'

const router = useRouter()

const goToMedical = () => {
  console.log('go to medical')
  router.push('/medicalServiceTypeItem')
}

const goToFoster = () => {
  router.push('/fosterServiceList')
}

const goToGrooming = () => {
  console.log('go to grooming')
  alert("开发中")
  // TODO: 跳转到美容服务页面
}

const goToTraining = () => {
  console.log('go to training')
  alert("开发中")

  // TODO: 跳转到训练服务页面
}
</script>

<style scoped>
.booking-home {
  background: linear-gradient(135deg, #e6f0ff 0%, #cce0ff 100%);
  padding: 20px;
  min-height: 100vh;
  box-sizing: border-box;
}

.header-section {
  text-align: center;
  padding: 20px 0;
  margin-bottom: 20px;
}

.page-title {
  font-size: 28px;
  color: #007aff;
  margin-bottom: 10px;
  font-weight: 600;
}

.page-subtitle {
  font-size: 16px;
  color: #666;
  max-width: 600px;
  margin: 0 auto;
  line-height: 1.5;
}

.service-categories {
  margin-bottom: 30px;
}

.category-card {
  background: white;
  border-radius: 16px;
  margin-bottom: 20px;
  box-shadow: 0 4px 12px rgba(0, 122, 255, 0.15);
  transition: all 0.3s ease;
  cursor: pointer;
  overflow: hidden;
  display: flex;
  align-items: center;
}

.category-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 16px rgba(0, 122, 255, 0.25);
}

.icon-wrapper {
  width: 80px;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 20px;
  border-radius: 16px;
}

.medical {
  background: linear-gradient(135deg, #0061ff 0%, #0079fd 100%);
}

.foster {
  background: linear-gradient(135deg, #fa8900 0%, #ffbd81 100%);
}

.grooming {
  background: linear-gradient(135deg, #ef3c6e 0%, #f8839e 100%);
}

.training {
  background: linear-gradient(135deg, #8e24aa 0%, #be7bff 100%);
}

.card-content {
  flex: 1;
  padding: 20px 20px 20px 0;
}

.card-title {
  margin: 0 0 8px 0;
  color: #333;
  font-size: 20px;
  font-weight: 600;
}

.card-description {
  color: #666;
  font-size: 14px;
  line-height: 1.5;
  margin: 0 0 15px 0;
}

.card-footer {
  display: flex;
}

.info-section {
  background: white;
  border-radius: 16px;
  padding: 20px;
  box-shadow: 0 4px 12px rgba(0, 122, 255, 0.15);
  margin-bottom: 2rem;

}

.tips-title {
  font-size: 18px;
  color: #333;
  margin: 0 0 15px 0;
  font-weight: 600;
}

.tips-list {
  padding-left: 20px;
  margin: 0;
}

.tips-list li {
  color: #666;
  margin-bottom: 10px;
  line-height: 1.5;
}

.tips-list li:last-child {
  margin-bottom: 0;
}

@media (max-width: 768px) {
  .booking-home {
    padding: 15px;
  }

  .page-title {
    font-size: 24px;
  }

  .category-card {
    flex-direction: column;
    text-align: center;
  }

  .icon-wrapper {
    margin: 20px auto;
  }

  .card-content {
    padding: 0 20px 20px;
  }
}
</style>
