<!--医疗服务分类-->
<template>
  <div class="medical-service-page">
    <div class="nav-bar-wrapper">
      <navBarTop title="医疗服务">
        <template #left>
          <div>
            <van-icon name="arrow-left" @click="()=>{router.go(-1)}"/>
          </div>
        </template>
      </navBarTop>
    </div>
    <div class="container">
      <div class="content-wrapper">
        <div class="services-container">
          <div class="service-card vaccine-card" @click="onVaccine">
            <div class="card-icon">
              <van-icon name="completed" size="40" color="#fff"/>
            </div>
            <div class="card-content">
              <h2 class="card-title">疫苗接种</h2>
              <p class="card-description">预防疾病，守护宠物健康</p>
              <div class="card-features">
                <van-tag color="#e6f0ff" text-color="#3e6ae1" round>核心疫苗</van-tag>
                <van-tag color="#e6f0ff" text-color="#3e6ae1" round>进口疫苗</van-tag>
                <van-tag color="#e6f0ff" text-color="#3e6ae1" round>专业接种</van-tag>
              </div>
            </div>
          </div>

          <div class="service-card diagnosis-card" @click="onDiagnosis">
            <div class="card-icon">
              <van-icon name="hospital-o"/>
              <van-icon name="shield-o" size="40" color="#fff"/>
            </div>
            <div class="card-content">
              <h2 class="card-title">疾病诊疗</h2>
              <p class="card-description">专业诊断，科学治疗方案</p>
              <div class="card-features">
                <van-tag color="#fff7e6" text-color="#fa8900" round>专家诊断</van-tag>
                <van-tag color="#fff7e6" text-color="#fa8900" round>先进设备</van-tag>
                <van-tag color="#fff7e6" text-color="#fa8900" round>个性化治疗</van-tag>
              </div>
            </div>
          </div>
        </div>

        <div class="info-section">
          <van-notice-bar
              left-icon="volume-o"
              text="首次就诊请携带宠物疫苗本和健康档案"
              background="#ecf5ff"
              color="#3e6ae1"
              style="border-radius: 20px"
          />

          <div class="medical-tips">
            <h3 class="tips-title">医疗小贴士</h3>
            <ul class="tips-list">
              <li>幼犬幼猫建议6-8周开始接种疫苗</li>
              <li>成年宠物每年需要定期体检</li>
              <li>发现异常症状请及时就医</li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import navBarTop from "@/components/navBarTop.vue";
import router from "@/router/index.js";

const onVaccine = () => {
  router.push('/vetList/ordinary');  // 普通医生
  console.log("进入普通医生列表");
};

const onDiagnosis = () => {
  router.push('/vetList/specialized');  // 专业医生
  console.log("进入专业医生列表");
};
</script>

<style scoped>
.medical-service-page {
  background: linear-gradient(135deg, #e6f0ff 0%, #cce0ff 100%);
  min-height: 100vh;
  padding-bottom: 20px;

}

.container {
  padding:20px;
}


.content-wrapper {
  max-width: 1200px;
  margin: 0 auto 2rem;
}

.services-container {
  margin-bottom: 30px;
}

.service-card {
  border-radius: 16px;
  margin-bottom: 20px;
  box-shadow: 0 4px 12px rgba(0, 122, 255, 0.15);
  transition: all 0.3s ease;
  cursor: pointer;
  overflow: hidden;
  display: flex;
  align-items: center;
}

.service-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 16px rgba(0, 122, 255, 0.25);
}

.vaccine-card {
  background: linear-gradient(135deg, #3e6ae1 0%, #6a93f8 100%);
}

.diagnosis-card {
  background: linear-gradient(135deg, #fa8900 0%, #ffb84d 100%);
}

.card-icon {
  width: 100px;
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 20px;
  border-radius: 16px;
  background: rgba(255, 255, 255, 0.2);
}

.card-content {
  flex: 1;
  padding: 20px 20px 20px 0;
  color: white;
}

.card-title {
  margin: 0 0 10px 0;
  font-size: 24px;
  font-weight: 600;
}

.card-description {
  font-size: 16px;
  margin: 0 0 15px 0;
  opacity: 0.9;
}

.card-features {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.card-features .van-tag {
  margin: 0;
}

.info-section {
  background: white;
  border-radius: 16px;
  padding: 20px;
  box-shadow: 0 4px 12px rgba(0, 122, 255, 0.15);
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
  .header-section {
    margin-top: 60px;
  }

  .page-title {
    font-size: 24px;
  }

  .service-card {
    flex-direction: column;
    text-align: center;
  }

  .card-icon {
    margin: 20px auto;
  }

  .card-content {
    padding: 0 20px 20px;
  }

  .card-features {
    justify-content: center;
  }
}
</style>
