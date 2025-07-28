<template>
  <div class="user-management-container">
    <!-- 顶部操作栏 -->
    <div class="action-bar">
      <el-button
          type="primary"
          @click="addSysUsersBtn"
          class="add-user-btn"
      >
        <el-icon><Plus /></el-icon>
        新增用户
      </el-button>
    </div>

    <!-- 用户分类标签页 -->
    <el-tabs v-model="activeTab" class="user-tabs">
      <el-tab-pane label="客户管理" name="owners">
        <Owners class="user-table" />
      </el-tab-pane>
      <el-tab-pane label="员工管理" name="employees">
        <Employees class="user-table" />
      </el-tab-pane>
    </el-tabs>

    <!-- 新增用户对话框 -->
    <el-dialog
        v-model="data.dialogVisible"
        title="新增用户"
        width="600px"
        class="add-user-dialog"
    >
      <el-form
          :model="data.addSysUsersForm"
          :rules="addSysUsersFormRules"
          ref="addSysUsersFormRef"
          label-width="100px"
          label-position="left"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="账号" prop="username">
              <el-input
                  v-model="data.addSysUsersForm.username"
                  placeholder="请输入6-16位用户名"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="密码" prop="password">
              <el-input
                  v-model="data.addSysUsersForm.password"
                  type="password"
                  placeholder="请输入6-16位密码"
                  show-password
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="昵称" prop="realName">
              <el-input
                  v-model="data.addSysUsersForm.realName"
                  placeholder="请输入2-16位昵称"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="sex">
              <el-select
                  v-model="data.addSysUsersForm.sex"
                  placeholder="请选择性别"
                  class="w-full"
              >
                <el-option label="男" value="male" />
                <el-option label="女" value="female" />
                <el-option label="其他" value="other" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input
                  v-model="data.addSysUsersForm.phone"
                  placeholder="请输入手机号"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input
                  v-model="data.addSysUsersForm.email"
                  placeholder="请输入邮箱"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="角色" prop="role">
              <el-select
                  v-model="data.addSysUsersForm.role"
                  placeholder="选择角色"
                  class="w-full"
              >
                <el-option
                    v-for="item in optionsRole"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select
                  v-model="data.addSysUsersForm.status"
                  placeholder="请选择状态"
                  class="w-full"
              >
                <el-option label="启用" value="1" />
                <el-option label="禁用" value="0" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.dialogVisible = false">取消</el-button>
          <el-button
              type="primary"
              @click="handleAddUser"
              :loading="loading"
          >
            确认添加
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { Plus } from '@element-plus/icons-vue'
import Owners from './Users/Owners.vue'
import Employees from './Users/Employees.vue'
import { reactive, ref } from "vue"
import { ElMessage } from "element-plus"
import request from "@/Backend/utils/request.js"

const activeTab = ref('owners')
const loading = ref(false)
const addSysUsersFormRef = ref()

const data = reactive({
  dialogVisible: false,
  addSysUsersForm: {
    username: null,
    password: null,
    realName: null,
    sex: null,
    phone: null,
    email: null,
    role: null,
    status: null,
  },
})

const optionsRole = [
  { value: 'admin', label: '管理员' },
  { value: 'owner', label: '普通用户' },
  { value: 'veterinarian', label: '医生' },
  { value: 'foster_staff', label: '寄养员' },
]

const addSysUsersFormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 6, max: 16, message: '长度在6到16个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 16, message: '长度在6到16个字符', trigger: 'blur' }
  ],
  realName: [
    { required: true, message: '请输入昵称', trigger: 'blur' },
    { min: 2, max: 16, message: '长度在2到16个字符', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { min: 6, max: 16, message: '长度在6到16个字符', trigger: 'blur' }
  ],
  role: [
    { required: true, message: '请选择角色', trigger: 'change' },
  ],
  status: [
    { required: true, message: '请选择状态', trigger: 'change' },
  ],
}

const addSysUsersBtn = () => {
  if (addSysUsersFormRef.value) {
    addSysUsersFormRef.value.resetFields()
  }
  data.dialogVisible = true
}

const handleAddUser = () => {
  addSysUsersFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        loading.value = true
        const res = await request.post('/sysUser/addSysUsers', data.addSysUsersForm)

        if (res.code === 200) {
          ElMessage.success('用户添加成功')
          data.dialogVisible = false
          // 这里可以添加刷新用户列表的逻辑
        } else {
          ElMessage.error(res.message || '添加失败')
        }
      } catch (error) {
        ElMessage.error('网络错误，请稍后重试')
      } finally {
        loading.value = false
      }
    } else {
      ElMessage.warning('请填写完整且正确的信息')
    }
  })
}
</script>

<style scoped>
.user-management-container {
  padding: 20px;
  background-color: #f5f7fa;
}

.action-bar {
  margin-bottom: 20px;
}

.add-user-btn {
  padding: 10px 20px;
  border-radius: 6px;
  font-weight: 500;
}

.user-tabs {
  background: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
}

.user-table {
  margin-top: 20px;
}

.add-user-dialog :deep(.el-dialog__body) {
  padding: 20px 25px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.w-full {
  width: 100%;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .user-management-container {
    padding: 15px;
  }

  .user-tabs {
    padding: 15px;
  }

  :deep(.el-form-item) {
    margin-bottom: 18px;
  }
}
</style>