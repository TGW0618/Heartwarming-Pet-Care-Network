<!--咨询页-->
<template>
  <div class="consultation-home">
    <!-- 头部标题区域 -->
    <div class="header-section">
      <h1 class="page-title">宠物健康咨询</h1>
      <p class="page-subtitle">专业兽医在线为您解答宠物健康问题</p>
    </div>

    <!-- 主要内容区域 -->
    <div class="content-wrapper">
      <!-- 快速咨询入口 -->
      <div class="quick-consult-section">
        <h2 class="section-title">快速咨询</h2>
        <div class="consult-options">
          <van-button class="consult-btn primary" round @click="goToConsult">
            立即咨询
          </van-button>
          <van-button class="consult-btn secondary" round @click="goToAppointment">
            预约专家
          </van-button>
        </div>
      </div>

      <!-- 常见问题 -->
      <div class="faq-section">
        <h2 class="section-title">常见问题</h2>
        <div class="faq-list">
          <div class="faq-item" v-for="faq in faqs" :key="faq.id">
            <h3 class="faq-question">{{ faq.question }}</h3>
            <p class="faq-answer">{{ faq.answer }}</p>
          </div>
        </div>
      </div>
    </div>

    <!-- 咨询弹窗 -->
    <van-popup
        v-model:show="showBottom"
        round
        position="bottom"
        :style="{ height: '55%' }"
        closeable
    >
      <div class="popup-content">
        <div class="popup-header">
          <van-icon name="phone-o" class="phone-icon" />
          <h3 class="popup-title">联系客服</h3>
        </div>
        <div class="popup-body">
          <p class="contact-text">如有紧急情况，请拨打我们的服务热线</p>
          <div class="phone-number">{{ SERVICE_PHONE }}</div>
          <p class="contact-time">服务时间：每天 9:00 - 21:00</p>
          <van-button
            class="call-button"
            round
            @click="callPhone"
          >
            立即拨打
          </van-button>
        </div>
      </div>
    </van-popup>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import {showConfirmDialog, showToast} from 'vant'

const router = useRouter()
const SERVICE_PHONE = '19572361926' // 服务电话号码

// 常见问题数据
const faqs = ref([
  {
    id: 1,
    question: '咨询需要多长时间能得到回复？',
    answer: '一般情况下，我们的兽医会在24小时内回复您的咨询。紧急情况可选择加急服务。'
  },
  {
    id: 2,
    question: '咨询费用是多少？',
    answer: '基础咨询免费，专家咨询根据医生资历收取不同费用，详情请查看价格页面。'
  },
  {
    id: 3,
    question: '可以咨询哪些宠物问题？',
    answer: '我们支持猫、狗、鸟类、小型哺乳动物等各类宠物的健康咨询。'
  }
])

const showBottom = ref(false)

const goToConsult = () => {
  // 显示弹窗
  showBottom.value = true
}

const goToAppointment = () => {
  // 跳转到预约页面
  router.push('/medicalServiceTypeItem')
}

const callPhone = () => {
  showConfirmDialog({
    title: '拨打电话',
    message: `确定要拨打 ${SERVICE_PHONE} 吗？`,
    className: 'custom-dialog'
  }).then(() => {
    // 用户确认拨打，调用系统拨号功能
    window.location.href = `tel:${SERVICE_PHONE}`
  }).catch(() => {
    // 用户取消操作
    console.log('用户取消拨打电话')
  })
}
</script>

<style scoped>



.consultation-home {
  background: linear-gradient(135deg, #e6f0ff 0%, #cce0ff 100%);
  min-height: 100vh;
  box-sizing: border-box;
  padding: 20px;
}

.header-section {
  text-align: center;
  padding: 30px 20px;
  border-radius: 16px;
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

.content-wrapper {
  max-width: 1200px;
  margin: 0 auto 2rem;
}

.section-title {
  font-size: 22px;
  color: #333;
  margin-bottom: 20px;
  text-align: center;
  font-weight: 600;
}

.quick-consult-section {
  background: white;
  border-radius: 16px;
  padding: 30px 20px;
  text-align: center;
  box-shadow: 0 4px 12px rgba(0, 122, 255, 0.15);
  margin-bottom: 20px;
}

.consult-options {
  display: flex;
  justify-content: center;
  gap: 20px;
  flex-wrap: wrap;
}

.consult-btn {
  width: 40%;
  height: 45px;
  font-size: 16px;
  border: none;
  font-weight: 500;
}

.primary {
  background: #007aff;
  color: white;
}

.secondary {
  background: white;
  color: #007aff;
  border: 1px solid #007aff;
}

.faq-section {
  background: white;
  border-radius: 16px;
  padding: 30px 20px;
  box-shadow: 0 4px 12px rgba(0, 122, 255, 0.15);
}

.faq-list {
  max-width: 800px;
  margin: 0 auto;
}

.faq-item {
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid #ecf0f1;
}

.faq-item:last-child {
  border-bottom: none;
  margin-bottom: 0;
}

.faq-question {
  font-size: 16px;
  color: #333;
  margin-bottom: 8px;
  font-weight: 500;
}

.faq-answer {
  color: #666;
  line-height: 1.5;
  font-size: 14px;
}

/* 弹窗样式 */
.popup-content {
  padding: 20px;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.popup-header {
  text-align: center;
  margin-bottom: 20px;
}

.phone-icon {
  font-size: 48px;
  color: #007aff;
  margin-bottom: 10px;
}

.popup-title {
  font-size: 22px;
  color: #333;
  margin: 0;
  font-weight: 600;
}

.popup-body {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
}

.contact-text {
  font-size: 16px;
  color: #666;
  margin-bottom: 15px;
}

.phone-number {
  font-size: 32px;
  font-weight: 700;
  color: #007aff;
  margin-bottom: 10px;
}

.contact-time {
  font-size: 14px;
  color: #999;
  margin-bottom: 30px;
}

.call-button {
  width: 80%;
  height: 45px;
  background: #007aff;
  color: white;
  font-size: 16px;
  border: none;
}




@media (max-width: 768px) {
  .consultation-home {
    padding: 15px;
  }

  .page-title {
    font-size: 24px;
  }

  .consult-options {
    flex-direction: column;
    align-items: center;
  }

  .consult-btn {
    width: 80%;
  }

  .phone-number {
    font-size: 24px;
  }
}
</style>
