<template>
  <div class="order-management-container">
    <slot name="title"></slot>

    <!-- 订单列表 -->
    <div class="order-list-container">
      <div v-for="item in OrderList" :key="item.orderNo" class="order-card">
        <div class="order-card-header">
          <div class="order-number">订单编号: {{ item.orderNo }}</div>
          <el-tag :type="getOrderStatusType(item.status)" size="small" effect="light">
            {{ getOrderStatusText(item.status) }}
          </el-tag>
        </div>

        <div class="order-card-body">
          <div class="order-info-section">
            <div class="order-info-item">
              <span class="info-label">服务:</span>
              <span class="info-value">{{ item.serviceName }}</span>
            </div>
            <div class="order-info-item">
              <span class="info-label">价格:</span>
              <span class="info-value highlight">¥{{ item.amount }}</span>
            </div>
            <div class="order-info-item">
              <span class="info-label">用户:</span>
              <span class="info-value">{{ item.realName }} ({{ item.username }})</span>
            </div>
            <div class="order-info-item">
              <span class="info-label">宠物:</span>
              <span class="info-value">{{ item.petName }} ({{ item.petType }})</span>
            </div>
          </div>

          <div class="order-time-section">
            <div class="order-time">{{ formatDateTime(item.createTime) }}</div>
            <el-tag
                :type="item.paymentStatus === '已支付' ? 'success' : 'danger'"
                size="small"
                effect="plain"
            >
              {{ item.paymentStatus }}
            </el-tag>
          </div>
        </div>

        <div class="order-card-actions">
          <el-button size="small" @click="handleDetail(item)" class="detail-btn">
            <el-icon><View /></el-icon>详情
          </el-button>
          <el-button size="small" @click="handleUpdate(item)" class="edit-btn">
            <el-icon><Edit /></el-icon>编辑
          </el-button>
          <el-button size="small" @click="handleDelete(item.id)" class="delete-btn">
            <el-icon><Delete /></el-icon>删除
          </el-button>
        </div>
      </div>
    </div>

    <!-- 订单详情对话框 -->
    <el-dialog
        v-model="dialogVisible"
        title="订单详细信息"
        width="600px"
        class="order-detail-dialog"
    >
      <div v-if="Object.keys(data.orderDetails).length > 0" class="detail-content">
        <el-descriptions :column="1" border size="small">
          <el-descriptions-item label="订单号">
            <el-tag type="primary" effect="dark">{{ data.orderDetails.orderNo }}</el-tag>
          </el-descriptions-item>

          <el-descriptions-item label="订单状态">
            <el-tag :type="getOrderStatusType(data.orderDetails.status)" effect="light">
              {{ getOrderStatusText(data.orderDetails.status) }}
            </el-tag>
          </el-descriptions-item>

          <el-descriptions-item label="订单金额">
            <span class="amount-highlight">¥{{ data.orderDetails.amount }}</span>
          </el-descriptions-item>

          <el-descriptions-item label="客户信息">
            {{ data.orderDetails.realName }} ({{ data.orderDetails.username }})
          </el-descriptions-item>

          <el-descriptions-item label="宠物信息">
            {{ data.orderDetails.petName }} ({{ data.orderDetails.petType }})
          </el-descriptions-item>

          <el-descriptions-item label="服务信息">
            {{ data.orderDetails.serviceName }} ({{ data.orderDetails.serviceType }})
          </el-descriptions-item>

          <el-descriptions-item label="预约时间">
            {{ data.orderDetails.appointmentStartTime || '暂无数据' }}
          </el-descriptions-item>

          <el-descriptions-item label="服务时间">
            {{ data.orderDetails.actualStartTime || '暂无数据' }} 至
            {{ data.orderDetails.actualEndTime || '暂无数据' }}
          </el-descriptions-item>

          <el-descriptions-item label="支付信息">
            <div class="payment-info">
              <el-tag :type="data.orderDetails.paymentStatus === '已支付' ? 'success' : 'danger'" size="small">
                {{ data.orderDetails.paymentStatus }}
              </el-tag>
              <span v-if="data.orderDetails.paymentMethod" class="payment-method">
                ({{ data.orderDetails.paymentMethod }})
              </span>
              <span v-if="data.orderDetails.paymentTime" class="payment-time">
                {{ formatDateTime(data.orderDetails.paymentTime) }}
              </span>
            </div>
          </el-descriptions-item>

          <el-descriptions-item label="下单时间">
            {{ formatDateTime(data.orderDetails.createTime) }}
          </el-descriptions-item>
        </el-descriptions>
      </div>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogVisible = false">关闭</el-button>
          <el-button
              type="danger"
              @click="handleDelete(data.orderDetails.id)"
              :loading="deleteLoading"
          >
            删除订单
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {useOrderStore} from '@/Backend/stores/order'
import {reactive, ref} from "vue"
import {ElMessage, ElMessageBox} from "element-plus"
import {Delete, Edit, View} from '@element-plus/icons-vue'

const props = defineProps({
  OrderList: {
    type: Array,
    default: () => []
  }
})

const data = reactive({
  orderDetails: {}
})
const dialogVisible = ref(false)
const deleteLoading = ref(false)
const orderStore = useOrderStore()

// 删除订单
const handleDelete = async (id) => {
  try {
    deleteLoading.value = true
    await ElMessageBox.confirm('确定要删除此订单吗?', '警告', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    })
    await orderStore.deleteOrder(id)
    ElMessage.success('订单删除成功')
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败: ' + (error.message || error))
    }
  } finally {
    deleteLoading.value = false
  }
}

// 查看详情
const handleDetail = (order) => {
  data.orderDetails = order
  dialogVisible.value = true
}

// 编辑订单
const handleUpdate = (order) => {
  ElMessage.info("功能开发中！")
}

// 获取订单状态类型
const getOrderStatusType = (status) => {
  const statusMap = {
    'pending': 'info',    // 待确认
    'confirmed': '',      // 已确认 (使用主色调)
    'processing': '',     // 处理中 (使用主色调)
    'completed': 'success', // 已完成
    'canceled': 'danger'   // 已取消
  }
  return statusMap[status] || 'info'
}

// 获取订单状态文本
const getOrderStatusText = (status) => {
  const statusTextMap = {
    'pending': '待确认',
    'confirmed': '已确认',
    'processing': '处理中',
    'completed': '已完成',
    'canceled': '已取消'
  }
  return statusTextMap[status] || '未知状态'
}

// 格式化日期时间
const formatDateTime = (dateString) => {
  if (!dateString) return '暂无数据'
  const date = new Date(dateString)
  return date.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}
</script>

<style scoped>
.order-management-container {
  padding: 20px;
  background-color: #f9f2fc; /* 浅紫色背景 */
}

.order-list-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 16px;
  margin-top: 20px;
}

.order-card {
  background: white;
  border-radius: 10px;
  padding: 16px;
  box-shadow: 0 2px 12px 0 rgba(176, 86, 195, 0.1);
  transition: all 0.3s ease;
  border: 1px solid #e6d4f0;
}

.order-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 4px 16px rgba(176, 86, 195, 0.2);
}

.order-card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
  padding-bottom: 8px;
  border-bottom: 1px dashed #e6d4f0;
}

.order-number {
  font-weight: bold;
  color: #6a2c82; /* 深紫色文字 */
}

.order-card-body {
  display: flex;
  margin-bottom: 12px;
}

.order-info-section {
  flex: 1;
}

.order-info-item {
  margin-bottom: 8px;
  display: flex;
}

.info-label {
  color: #9c6aad; /* 中等紫色 */
  margin-right: 6px;
  font-weight: 500;
  min-width: 40px;
}

.info-value {
  color: #333;
}

.highlight {
  color: #b056c3; /* 主色调 */
  font-weight: bold;
}

.order-time-section {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  justify-content: space-between;
}

.order-time {
  color: #666;
  font-size: 12px;
}

.order-card-actions {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
}

.detail-btn {
  background-color: #f0e6f5; /* 浅紫色背景 */
  border-color: #d9b3e6;
  color: #6a2c82; /* 深紫色文字 */
}

.detail-btn:hover {
  background-color: #e6d4f0;
  border-color: #c9a2d9;
}

.edit-btn {
  background-color: #f0e6f5;
  border-color: #d9b3e6;
  color: #6a2c82;
}

.edit-btn:hover {
  background-color: #e6d4f0;
  border-color: #c9a2d9;
}

.delete-btn:hover {
  opacity: 0.8;
}

/* 详情对话框样式 */
.order-detail-dialog {
  --el-dialog-title-font-size: 18px;
  --el-dialog-title-text-color: #6a2c82;
}

.order-detail-dialog :deep(.el-dialog__header) {
  border-bottom: 1px solid #e6d4f0;
}

.detail-content {
  padding: 10px;
}

.amount-highlight {
  color: #b056c3; /* 主色调 */
  font-weight: bold;
  font-size: 16px;
}

.payment-info {
  display: flex;
  align-items: center;
  gap: 8px;
}

.payment-method {
  color: #666;
}

.payment-time {
  color: #999;
  font-size: 12px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

/* 状态标签样式 */
:deep(.el-tag) {
  border-radius: 12px;
}

/* 主色调状态标签 */
:deep(.el-tag:not(.el-tag--info):not(.el-tag--success):not(.el-tag--warning):not(.el-tag--danger)) {
  background-color: rgba(176, 86, 195, 0.1);
  border-color: rgba(176, 86, 195, 0.2);
  color: #b056c3;
}

@media (max-width: 768px) {
  .order-list-container {
    grid-template-columns: 1fr;
  }

  .order-card-body {
    flex-direction: column;
  }

  .order-time-section {
    align-items: flex-start;
    margin-top: 12px;
  }
}
</style>