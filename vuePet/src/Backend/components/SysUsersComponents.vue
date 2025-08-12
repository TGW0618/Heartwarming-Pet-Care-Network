<template>
  <!-- 用户列表卡片 -->
  <div class="user-management-container">
    <!-- 搜索和操作栏 -->
    <div class="toolbar">
      <el-input
          v-model="searchQuery"
          placeholder="搜索用户..."
          class="search-input"
          clearable
          @clear="handleSearch"
          @keyup.enter="handleSearch"
      >
        <template #prefix>
          <el-icon>
            <Search/>
          </el-icon>
        </template>
      </el-input>
    </div>

    <!-- 用户列表 -->
    <el-card class="user-list-card">
      <el-table
          :data="filteredUsers"
          style="width: 100%"
          stripe
          v-loading="loading"
          empty-text="暂无用户数据"
      >
        <!--        sortable：启用该列的排序功能-->
        <el-table-column prop="id" label="ID" width="80" sortable/>

        <el-table-column label="用户信息" min-width="200">
          <template #default="{ row }">
            <div class="user-info-cell">
              <el-avatar :size="40" :src="row.avatar" class="user-avatar">
                {{ row.realName?.charAt(0) || row.username?.charAt(0) }}
              </el-avatar>
              <div class="user-text">
                <div class="username">{{ row.username }}</div>
                <div class="real-name">{{ row.realName || '未设置昵称' }}</div>
              </div>
            </div>
          </template>
        </el-table-column>

        <el-table-column prop="phone" label="手机号" width="150"/>
        <el-table-column prop="email" label="邮箱" width="200"/>

        <el-table-column prop="role" label="角色" width="120">
          <template #default="{ row }">
            <el-tag :type="getRoleTagType(row.role)" effect="light" round>
              {{ formatRole(row.role) }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-switch
                v-model="row.status"
                :active-value="1"
                :inactive-value="0"
                active-text="启用"
                inactive-text="禁用"
                @change="handleStatusChange(row)"
            />
          </template>
        </el-table-column>

        <el-table-column prop="createTime" label="创建时间" width="180" sortable>
          <template #default="{ row }">
            {{ formatDate(row.createTime) }}
          </template>
        </el-table-column>

        <el-table-column label="操作" width="180" fixed="right">
          <template #default="{ row }">
            <el-button
                size="small"
                type="primary"
                plain
                @click="updateUserBtn(row)"
                class="action-btn"
            >
              <el-icon>
                <Edit/>
              </el-icon>
              编辑
            </el-button>

            <el-popconfirm
                title="确定要删除此用户吗？"
                confirm-button-text="确定"
                cancel-button-text="取消"
                @confirm="deleteUserBtn(row)"
            >
              <template #reference>
                <el-button
                    size="small"
                    type="danger"
                    plain
                    class="action-btn"
                >
                  <el-icon>
                    <Delete/>
                  </el-icon>
                  删除
                </el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 编辑用户对话框 -->
    <el-dialog
        v-model="data.dialogVisible"
        :title="`编辑用户 - ${data.upSysUsersForm.username}`"
        width="600px"
        class="user-edit-dialog"
    >
      <el-form
          :model="data.upSysUsersForm"
          label-width="100px"
          label-position="left"
      >
        <!-- 基本信息 -->
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="用户ID">
              <el-input v-model="data.upSysUsersForm.id" disabled/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="账号" prop="username">
              <el-input v-model="data.upSysUsersForm.username"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="昵称" prop="realName">
              <el-input v-model="data.upSysUsersForm.realName"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="sex">
              <el-select v-model="data.upSysUsersForm.sex" placeholder="请选择性别" style="width: 100%">
                <el-option label="男" value="male"/>
                <el-option label="女" value="female"/>
                <el-option label="其他" value="other"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 联系方式 -->
        <el-divider content-position="left">联系方式</el-divider>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="data.upSysUsersForm.phone"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="data.upSysUsersForm.email"/>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 权限设置 -->
        <el-divider content-position="left">权限设置</el-divider>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="角色" prop="role">
              <el-select v-model="data.upSysUsersForm.role" placeholder="选择角色" style="width: 100%">
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
              <el-switch
                  v-model="data.upSysUsersForm.status"
                  :active-value="1"
                  :inactive-value="0"
                  active-text="启用"
                  inactive-text="禁用"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="savaUpdateUser">
            保存更改
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {computed, defineProps, reactive, ref} from 'vue'
import {ElMessage, ElMessageBox} from "element-plus"
import {Delete, Edit, Search} from '@element-plus/icons-vue'
import request from "@/Backend/utils/request.js"
import {useUserStore} from '@/Backend/stores/userStore.js'

const userStore = useUserStore()
const searchQuery = ref('')
const loading = ref(false)

const props = defineProps({
  users: {
    type: Array,
    required: true,
    default: () => []
  },
})

const data = reactive({
  dialogVisible: false,
  upSysUsersForm: {
    id: null,
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
  {value: 'admin', label: '管理员'},
  {value: 'owner', label: '普通用户'},
  {value: 'veterinarian', label: '医生'},
  {value: 'foster_staff', label: '寄养员'},
]

// 计算属性
const filteredUsers = computed(() => {
  return props.users.filter(user => {
    const query = searchQuery.value.toLowerCase()
    return (
        user.username.toLowerCase().includes(query) ||
        user.realName?.toLowerCase().includes(query) ||
        user.phone?.includes(query) ||
        user.email?.toLowerCase().includes(query)
    )
  })
})

// 方法
const formatDate = (dateString) => {
  if (!dateString) return '未知时间'
  return new Date(dateString).toLocaleString()
}

const formatRole = (role) => {
  const roleMap = {
    admin: '管理员',
    owner: '普通用户',
    veterinarian: '医生',
    foster_staff: '寄养员'
  }
  return roleMap[role] || role
}

const getRoleTagType = (role) => {
  switch (role) {
    case 'admin':
      return 'danger'
    case 'veterinarian':
      return 'warning'
    case 'foster_staff':
      return 'primary'
    default:
      return null
  }
}

// 搜索
const handleSearch = () => {

}

const deleteUserBtn = async (user) => {
  try {
    await ElMessageBox.confirm(
        `确定要永久删除用户 "${user.username}" 吗?`,
        '警告',
        {
          confirmButtonText: '确认删除',
          cancelButtonText: '取消',
          type: 'warning',
        }
    )

    loading.value = true
    const res = await request.delete('/sysUser/deleteSysUsers', {
      params: {id: user.id}
    })

    if (res.code === 200) {
      ElMessage.success('删除成功')
      userStore.triggerRefresh()
    } else {
      ElMessage.error(res.message || '删除失败')
    }
  } catch (error) {
    ElMessage.info('已取消删除')
  } finally {
    loading.value = false
  }
}

const updateUserBtn = (user) => {
  data.upSysUsersForm = JSON.parse(JSON.stringify(user))
  data.dialogVisible = true
}

const savaUpdateUser = async () => {
  try {
    await ElMessageBox.confirm("确定要保存这些更改吗？", "确认修改", {
      confirmButtonText: '确认保存',
      cancelButtonText: '取消',
      type: 'warning',
    })

    loading.value = true
    const res = await request.put('/sysUser/updateSysUsers', data.upSysUsersForm)

    if (res.code === 200) {
      ElMessage.success('修改成功')
      data.dialogVisible = false
      userStore.triggerRefresh()
    } else {
      ElMessage.error(res.message || '修改失败')
    }
  } catch (error) {
    ElMessage.info('已取消修改')
  } finally {
    loading.value = false
  }
}

// 状态修改请求
const handleStatusChange = async (user) => {
  console.log(user.id)
  console.log(user.status)
  try {
    const res = await request.put('/sysUser/updateSysUserStatus', {
      id: user.id,
      status: user.status
    })

    if (res.code === 200) {
      ElMessage.success(`用户已${user.status === 1 ? '启用' : '禁用'}`)
      userStore.triggerRefresh()
    } else {
      ElMessage.error(res.message || '操作失败')
      // 恢复原状态
      user.status = user.status === 1 ? 0 : 1
    }
  } catch (error) {
    ElMessage.error('操作失败')
    // 恢复原状态
    user.status = user.status === 1 ? 0 : 1
  }
}
</script>

<style scoped>
.user-management-container {
  padding: 20px;
  background-color: #f5f7fa;
}

.toolbar {
  margin-bottom: 20px;
}

.search-input {
  width: 300px;
}

.user-list-card {
  border-radius: 8px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
}

.user-info-cell {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-avatar {
  background-color: #f0e6f5;
  color: #af54c2;
  font-weight: bold;
}

.user-text {
  line-height: 1.4;
}

.username {
  font-weight: 500;
  color: #333;
}

.real-name {
  font-size: 12px;
  color: #999;
}

.action-btn {
  margin: 0 4px;
}

.user-edit-dialog :deep(.el-dialog__body) {
  padding: 20px 25px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .search-input {
    width: 100%;
  }

  .user-info-cell {
    flex-direction: column;
    align-items: flex-start;
    gap: 6px;
  }
}
</style>