<template>
  <div class="user-profile-container">
    <div class="user-profile">
      <!-- 用户信息卡片 -->
      <el-card class="user-card" shadow="hover">
        <!-- 头像和信息区域 -->
        <div class="user-info-container">
          <!-- 头像区域 -->
          <div class="avatar-section">
            <div class="avatar-wrapper">
              <el-avatar :src="data.user.avatar" :size="120" class="user-avatar">
                <DefaultAvatar/>
              </el-avatar>
              <div class="avatar-badge">
                <el-icon color="#67c23a">
                  <SuccessFilled/>
                </el-icon>
              </div>
            </div>
            <h3 class="user-name">{{ data.user.realName || data.user.username }}</h3>
            <p class="user-role">
              <el-tag :type="getRoleType(data.user.role)" effect="dark" size="small">
                {{ getRoleDisplayName(data.user.role) }}
              </el-tag>
            </p>
          </div>

          <!-- 详细信息区域 -->
          <div class="info-section">
            <div class="info-grid">
              <!--              账号-->
              <div class="info-item">
                <div class="info-icon">
                  <el-icon>
                    <User/>
                  </el-icon>
                </div>
                <div class="info-content">
                  <span class="info-label">账号</span>
                  <span class="info-value">{{ data.user.username }}</span>
                </div>
              </div>
              <!--密码-->
              <div class="info-item">
                <div class="info-icon">
                  <el-icon>
                    <Lock/>
                  </el-icon>
                </div>
                <div class="info-content">
                  <span class="info-label">密码</span>
                  <span class="info-value">••••••</span>
                </div>
              </div>
              <!--姓名-->
              <div class="info-item">
                <div class="info-icon">
                  <el-icon>
                    <UserFilled/>
                  </el-icon>
                </div>
                <div class="info-content">
                  <span class="info-label">姓名</span>
                  <span class="info-value">{{ data.user.realName || '未设置' }}</span>
                </div>
              </div>
              <!--性别-->
              <div class="info-item">
                <div class="info-icon">
                  <el-icon>
                    <Male/>
                  </el-icon>
                </div>
                <div class="info-content">
                  <span class="info-label">性别</span>
                  <span class="info-value">{{ getSexDisplayName(data.user.sex) }}</span>
                </div>
              </div>

              <div class="info-item">
                <div class="info-icon">
                  <el-icon>
                    <Phone/>
                  </el-icon>
                </div>
                <div class="info-content">
                  <span class="info-label">手机号</span>
                  <span class="info-value">{{ data.user.phone || '未设置' }}</span>
                </div>
              </div>

              <div class="info-item">
                <div class="info-icon">
                  <el-icon>
                    <Message/>
                  </el-icon>
                </div>
                <div class="info-content">
                  <span class="info-label">邮箱</span>
                  <span class="info-value">{{ data.user.email || '未设置' }}</span>
                </div>
              </div>

              <div class="info-item">
                <div class="info-icon">
                  <el-icon>
                    <Calendar/>
                  </el-icon>
                </div>
                <div class="info-content">
                  <span class="info-label">创建时间</span>
                  <span class="info-value">{{ formatDate(data.user.createTime) }}</span>
                </div>
              </div>

              <div class="info-item">
                <div class="info-icon">
                  <el-icon>
                    <Timer/>
                  </el-icon>
                </div>
                <div class="info-content">
                  <span class="info-label">更新时间</span>
                  <span class="info-value">{{ formatDate(data.user.updateTime) || '首次登录' }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </el-card>
    </div>

    <!-- 操作按钮区域 -->
    <div class="action-buttons">
      <el-button type="primary" @click="upPassword" round>
        <el-icon>
          <EditPen/>
        </el-icon>
        修改密码
      </el-button>
      <el-button type="warning" @click="editProfile" round>
        <el-icon>
          <User/>
        </el-icon>
        编辑资料
      </el-button>
      <el-button type="danger" @click="loginOut" round>
        <el-icon>
          <SwitchButton/>
        </el-icon>
        退出登录
      </el-button>
    </div>
  </div>
</template>

<script setup>
import {ElMessage, ElMessageBox} from "element-plus"
import {
  User,
  Lock,
  UserFilled,
  Male,
  Phone,
  Message,
  Calendar,
  Timer,
  EditPen,
  SwitchButton,
  SuccessFilled
} from '@element-plus/icons-vue'
import router from "@/Common/router/index.js"
import {reactive, ref} from "vue"
import DefaultAvatar from "@/Common/components/DefaultAvatar.vue"

const data = reactive({
  user: JSON.parse(localStorage.getItem("petSysUser")),
  userSex: ref(),
})

// 添加简单的日期格式化函数
const formatDate = (dateString) => {
  if (!dateString) return '未知时间'
  const date = new Date(dateString)
  return date.toLocaleDateString() + ' ' + date.toLocaleTimeString()
}

// 获取角色显示名称
const getRoleDisplayName = (role) => {
  const roleMap = {
    'admin': '系统管理员',
    'veterinarian': '医生',
    'foster_staff': '寄养员'
  }
  return roleMap[role] || role
}

// 获取角色标签类型
const getRoleType = (role) => {
  const typeMap = {
    'admin': 'danger',
    'veterinarian': 'success',
    'foster_staff': 'warning'
  }
  return typeMap[role] || 'info'
}

// 获取性别显示
const getSexDisplayName = (sex) => {
  const sexMap = {
    'male': '男',
    'female': '女',
    'other': '其他'
  }
  return sexMap[sex] || sex
}

// 修改密码
const upPassword = () => {
  ElMessageBox.alert("修改密码功能待开发", "提示", {
    type: "info"
  })
}

// 编辑资料
const editProfile = () => {
  ElMessageBox.alert("编辑资料功能待开发", "提示", {
    type: "info"
  })
}

// 退出登录
const loginOut = () => {
  ElMessageBox.confirm(
      "确定要退出登录吗？",
      "提示",
      {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
        center: true
      }
  ).then(() => {
    localStorage.removeItem("petSysUser")
    ElMessage.success("退出成功")
    router.push("/admin/login")
  }).catch(() => {
    ElMessage.info("取消退出")
  })
}

</script>

<style scoped>
.user-profile-container {
  max-width: 1000px;
  margin: 0 auto;
  padding: 20px;
}

.user-profile {
  margin-bottom: 30px;
}

.user-card {
  border-radius: 16px;
  border: none;
  background: linear-gradient(135deg, #ffffff, #f8f5fa);
  box-shadow: 0 8px 30px rgba(175, 84, 194, 0.1);
  transition: all 0.3s ease;
}

.user-card:hover {
  box-shadow: 0 12px 40px rgba(175, 84, 194, 0.15);
  transform: translateY(-2px);
}

.user-info-container {
  display: flex;
  gap: 40px;
  padding: 30px;
}

.avatar-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;

}

.avatar-wrapper {
  position: relative;
  padding: 5px;
  background: white;
  border-radius: 50%;
  box-shadow: 0 0 0 5px white, 0 0 0 8px #f0e6f5;

}

.user-avatar {
  width: 120px;
  height: 120px;
  border: 3px solid #f0e6f5;
  box-shadow: 0 4px 12px rgba(175, 84, 194, 0.15);
  transition: all 0.3s ease;
  background-color: white;
}

.user-avatar:hover {
  transform: scale(1.05);
  box-shadow: 0 6px 16px rgba(175, 84, 194, 0.25);
}

.avatar-badge {
  position: absolute;
  bottom: 5px;
  right: 5px;
  width: 30px;
  height: 30px;
  background: #67c23a;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  border: 2px solid white;
}

.user-name {
  margin: 15px 0 5px;
  font-size: 20px;
  font-weight: 600;
  color: #333;
}

.user-role {
  margin: 0;
}

.info-section {
  flex: 1;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 16px;
}

.info-item {
  display: flex;
  align-items: center;
  padding: 15px;
  background-color: #ffffff;
  border-radius: 12px;
  transition: all 0.2s ease;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.03);
  border: 1px solid #f0f0f0;
}

.info-item:hover {
  background-color: #f8f5fa;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

.info-icon {
  width: 40px;
  height: 40px;
  background: #f0e6f5;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 15px;
  color: #af54c2;
}

.info-content {
  flex: 1;
}

.info-label {
  display: block;
  font-weight: 500;
  color: #7f8c8d;
  font-size: 14px;
  margin-bottom: 4px;
}

.info-value {
  color: #2c3e50;
  font-weight: 500;
  font-size: 15px;
}

/* 操作按钮区域 */
.action-buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 20px;
}

.action-buttons .el-button {
  padding: 12px 24px;
  font-size: 15px;
  transition: all 0.3s ease;
}

.action-buttons .el-button:hover {
  transform: translateY(-3px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.1);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .user-info-container {
    flex-direction: column;
    gap: 20px;
    padding: 20px;
  }

  .info-grid {
    grid-template-columns: 1fr;
  }

  .action-buttons {
    flex-direction: column;
    align-items: center;
  }

  .action-buttons .el-button {
    width: 80%;
  }
}

@media (max-width: 480px) {
  .user-profile-container {
    padding: 10px;
  }

  .info-item {
    padding: 12px;
  }

  .info-icon {
    width: 36px;
    height: 36px;
    margin-right: 12px;
  }
}
</style>
