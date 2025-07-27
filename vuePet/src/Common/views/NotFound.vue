<template>
  <div class="not-found-container">
    <div class="not-found-content">
      <!-- 插图区域 -->
      <div class="illustration">
        <div class="pet-animation">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100" class="pet-icon">
            <circle cx="50" cy="50" r="45" fill="#f8f5fa" stroke="#af54c2" stroke-width="2"/>
            <circle cx="35" cy="40" r="3" fill="#333"/>
            <circle cx="65" cy="40" r="3" fill="#333"/>
            <path d="M 30 65 Q 50 80 70 65" stroke="#333" stroke-width="2" fill="none"/>
            <path d="M 40 30 Q 45 25 50 30 Q 55 25 60 30" stroke="#333" stroke-width="1.5" fill="none"/>
          </svg>
        </div>
        <div class="error-animation">
          <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" class="error-icon">
            <path fill="#af54c2"
                  d="M11,15H13V17H11V15M11,7H13V13H11V7M12,2C6.48,2 2,6.48 2,12C2,17.52 6.48,22 12,22C17.52,22 22,17.52 22,12C22,6.48 17.52,2 12,2M12,20C7.59,20 4,16.41 4,12C4,7.59 7.59,4 12,4C16.41,4 20,7.59 20,12C20,16.41 16.41,20 12,20Z"/>
          </svg>
        </div>
      </div>

      <!-- 文字内容 -->
      <div class="text-content">
        <h1 class="error-code">404</h1>
        <h2 class="error-title">哎呀，迷路了！</h2>
        <p class="error-message">您要找的页面好像被小猫咪藏起来了<br/>或者去和小狗玩耍了</p>

        <!-- 操作按钮 -->
        <div class="action-buttons">
          <router-link to="/admin/home" class="home-button">
            <el-icon>
              <HomeFilled/>
            </el-icon>
            返回首页
          </router-link>
        </div>


      </div>
    </div>

    <!-- 装饰元素 -->
    <div class="decoration decoration-1"></div>
    <div class="decoration decoration-2"></div>
    <div class="decoration decoration-3"></div>
  </div>
</template>

<script setup>
import {HomeFilled,} from '@element-plus/icons-vue'


</script>

<style scoped>
.not-found-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #f8f5fa 0%, #e6e0f0 100%);
  padding: 20px;
  position: relative;
  overflow: hidden;
}

/* 装饰元素 */
.decoration {
  position: absolute;
  width: 120px;
  height: 120px;
  border-radius: 50%;
  background: linear-gradient(135deg, rgba(175, 84, 194, 0.1) 0%, rgba(175, 84, 194, 0.05) 100%);
  z-index: 0;
}

.decoration-1 {
  top: 10%;
  left: 10%;
}

.decoration-2 {
  bottom: 15%;
  right: 10%;
  width: 80px;
  height: 80px;
}

.decoration-3 {
  top: 20%;
  right: 15%;
  width: 60px;
  height: 60px;
  background: linear-gradient(135deg, rgba(155, 77, 176, 0.15) 0%, rgba(155, 77, 176, 0.05) 100%);
}

.not-found-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 700px;
  text-align: center;
  background: white;
  padding: 50px 40px;
  border-radius: 20px;
  box-shadow: 0 15px 50px rgba(175, 84, 194, 0.15);
  position: relative;
  z-index: 1;
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.5);
}

.illustration {
  position: relative;
  margin-bottom: 30px;
  width: 200px;
  height: 150px;
}

.pet-animation {
  position: absolute;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
  animation: float 3s ease-in-out infinite;
}

.pet-icon {
  width: 80px;
  height: 80px;
}

.error-animation {
  position: absolute;
  bottom: 0;
  right: 0;
  animation: pulse 2s ease-in-out infinite;
}

.error-icon {
  width: 70px;
  height: 70px;
  opacity: 0.8;
}

@keyframes float {
  0%, 100% {
    transform: translateX(-50%) translateY(0);
  }
  50% {
    transform: translateX(-50%) translateY(-15px);
  }
}

@keyframes pulse {
  0%, 100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.1);
  }
}

.error-code {
  font-size: 90px;
  font-weight: 800;
  color: #af54c2;
  margin: 0;
  line-height: 1;
  text-shadow: 3px 3px 0 rgba(175, 84, 194, 0.1);
  background: linear-gradient(135deg, #af54c2 0%, #8e44ad 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.error-title {
  font-size: 32px;
  color: #2c3e50;
  margin: 20px 0 15px;
  font-weight: 600;
}

.error-message {
  font-size: 18px;
  color: #7f8c8d;
  margin-bottom: 40px;
  line-height: 1.6;
}

.action-buttons {
  display: flex;
  gap: 16px;
  justify-content: center;
  margin-top: 30px;
}

.action-btn {
  position: relative;
  padding: 12px 24px;
  border-radius: 30px;
  font-weight: 500;
  overflow: hidden;
  transition: all 0.4s cubic-bezier(0.165, 0.84, 0.44, 1);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  border: none;
}

.action-btn:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.12);
}

.action-btn:active {
  transform: translateY(1px);
}

.btn-icon {
  margin-right: 8px;
  font-size: 16px;
  transition: transform 0.3s;
}

.action-btn:hover .btn-icon {
  transform: scale(1.1);
}

.btn-text {
  position: relative;
  z-index: 2;
}

.btn-hover-effect {
  position: absolute;
  top: 0;
  left: 0;
  width: 0;
  height: 100%;
  background: rgba(255, 255, 255, 0.2);
  transition: width 0.4s ease;
  z-index: 1;
}

.action-btn:hover .btn-hover-effect {
  width: 100%;
}

/* 修改密码按钮特殊样式 */
.password-btn {
  background: linear-gradient(135deg, #272527, #8e44ad);
  color: white;
}

.password-btn:hover {
  background: linear-gradient(135deg, #9b4db0, #7d3c98);
}

/* 编辑资料按钮特殊样式 */
.profile-btn {
  background: linear-gradient(135deg, #ffb347, #ff8c00);
  color: white;
}

.profile-btn:hover {
  background: linear-gradient(135deg, #e6a142, #e67e00);
}

/* 退出登录按钮特殊样式 */
.logout-btn {
  background: linear-gradient(135deg, #ff6b6b, #ee5253);
  color: white;
}

.logout-btn:hover {
  background: linear-gradient(135deg, #e65a5a, #d44a4a);
}

/* 响应式调整 */
@media (max-width: 768px) {
  .action-buttons {
    flex-direction: column;
    gap: 12px;
  }

  .action-btn {
    width: 100%;
  }
}

.home-button {
  display: inline-flex;
  align-items: center;
  padding: 12px 25px;
  background: linear-gradient(135deg, #af54c2 0%, #8e44ad 100%);
  color: white;
  border-radius: 10px;
  text-decoration: none;
  transition: all 0.3s ease;
  font-size: 16px;
  font-weight: 500;
  box-shadow: 0 4px 15px rgba(175, 84, 194, 0.3);
  border: none;
  cursor: pointer;
}

.home-button:hover {
  transform: translateY(-3px);
  box-shadow: 0 8px 20px rgba(175, 84, 194, 0.4);
}

.home-button:active {
  transform: translateY(-1px);
}

.home-button .el-icon {
  margin-right: 8px;
  font-size: 18px;
}


.back-button .el-icon {
  margin-right: 8px;
  font-size: 18px;
}


/* 响应式设计 */
@media (max-width: 768px) {
  .not-found-content {
    padding: 40px 25px;
    margin: 20px;
  }

  .illustration {
    width: 180px;
    height: 140px;
  }

  .pet-icon {
    width: 70px;
    height: 70px;
  }

  .error-icon {
    width: 60px;
    height: 60px;
  }

  .error-code {
    font-size: 70px;
  }

  .error-title {
    font-size: 26px;
  }

  .error-message {
    font-size: 16px;
  }

  .action-buttons {
    flex-direction: column;
    gap: 15px;
    width: 100%;
  }

  .home-button, .back-button {
    width: 100%;
    justify-content: center;
  }

  .search-section {
    max-width: 100%;
  }
}

@media (max-width: 480px) {
  .not-found-container {
    padding: 10px;
  }

  .not-found-content {
    padding: 30px 20px;
  }

  .error-code {
    font-size: 60px;
  }

  .error-title {
    font-size: 22px;
  }

  .error-message {
    font-size: 15px;
  }

  .decoration {
    display: none;
  }
}
</style>
