<template>
  <div class="order-management-container">
    <div>
      <button @click="fosterOrders">测试订单数据</button>
    </div>
    <!-- 页面标题 -->
    <div class="page-header">
      <h1 class="page-title">订单管理中心</h1>
      <div class="header-actions">
        <el-button type="primary" icon="Refresh" @click="testOrder">刷新数据</el-button>
      </div>
    </div>

    <!-- 订单列表 -->
    <div class="orders-wrapper">
      <el-card v-if="data.order.length === 0" class="empty-card">
        <el-empty description="暂无订单数据">
          <el-button type="primary" @click="testOrder">重新加载</el-button>
        </el-empty>
      </el-card>

      <div v-else class="orders-grid">
        <el-card
            v-for="(item, index) in data.order"
            :key="index"
            class="order-card"
            :class="{ 'card-hover': hoveredCard === index }"
            @mouseenter="hoveredCard = index"
            @mouseleave="hoveredCard = -1"
        >
          <div class="order-header">
            <div class="order-basic">
              <div class="order-number">
                <el-tag type="primary" size="small">订单号</el-tag>
                <span class="number-text">{{ item.orderNo }}</span>
              </div>
              <el-tag
                  :type="getOrderStatusType(item.status)"
                  size="small"
                  class="status-tag"
              >
                {{ getOrderStatusText(item.status) }}
              </el-tag>
            </div>
            <div class="order-amount">
              ¥{{ item.amount }}
            </div>
          </div>

          <div class="order-details">
            <div class="detail-row">
              <i class="el-icon-user"></i>
              <span class="detail-label">客户:</span>
              <span class="detail-value">{{ item.realName }} ({{ item.username }})</span>
            </div>

            <div class="detail-row">
              <i class="el-icon-guide"></i>
              <span class="detail-label">宠物:</span>
              <span class="detail-value">{{ item.petName }} ({{ item.petType }})</span>
            </div>

            <div class="detail-row">
              <i class="el-icon-tickets"></i>
              <span class="detail-label">服务:</span>
              <span class="detail-value">{{ item.serviceName }}</span>
            </div>

            <div class="detail-row">
              <i class="el-icon-time"></i>
              <span class="detail-label">下单时间:</span>
              <span class="detail-value">{{ formatDateTime(item.createTime) }}</span>
            </div>

            <div class="detail-row">
              <i class="el-icon-bank-card"></i>
              <span class="detail-label">支付状态:</span>
              <el-tag
                  :type="item.paymentStatus === '已支付' ? 'success' : 'danger'"
                  size="small"
              >
                {{ item.paymentStatus }}
              </el-tag>
            </div>
          </div>

          <div class="card-actions">
            <el-button
                type="primary"
                size="small"
                @click="DetailsBtn(item)"
                icon="View"
            >
              查看详情
            </el-button>
            <el-button
                type="danger"
                size="small"
                @click="DeleteBtn(item.id)"
                icon="Delete"
            >
              删除订单
            </el-button>
          </div>
        </el-card>
      </div>
    </div>

    <!-- 订单详情对话框 -->
    <el-dialog
        v-model="dialogVisible"
        title="订单详细信息"
        width="600px"
        class="order-detail-dialog"
        :before-close="handleDialogClose"
    >
      <div v-loading="dialogLoading" class="dialog-content">
        <div v-if="Object.keys(data.orderDetails).length > 0" class="detail-grid">
          <el-descriptions :column="1" border size="small">
            <el-descriptions-item label="订单号">
              <el-tag type="primary">{{ data.orderDetails.orderNo }}</el-tag>
            </el-descriptions-item>

            <el-descriptions-item label="订单状态">
              <el-tag :type="getOrderStatusType(data.orderDetails.status)">
                {{ getOrderStatusText(data.orderDetails.status) }}
              </el-tag>
            </el-descriptions-item>

            <el-descriptions-item label="订单金额">
              <span class="amount-highlight">¥{{ data.orderDetails.amount }}</span>
            </el-descriptions-item>

            <el-descriptions-item label="客户姓名">
              {{ data.orderDetails.realName }}
            </el-descriptions-item>

            <el-descriptions-item label="客户账号">
              {{ data.orderDetails.username }}
            </el-descriptions-item>

            <el-descriptions-item label="宠物名称">
              {{ data.orderDetails.petName }}
            </el-descriptions-item>

            <el-descriptions-item label="宠物类型">
              {{ data.orderDetails.petType }}
            </el-descriptions-item>

            <el-descriptions-item label="服务项目">
              {{ data.orderDetails.serviceName }}
            </el-descriptions-item>

            <el-descriptions-item label="预约时间">
              {{ data.orderDetails.appointmentStartTime || '暂无数据' }}
            </el-descriptions-item>

            <el-descriptions-item label="服务开始时间">
              {{ data.orderDetails.actualStartTime || '暂无数据' }}
            </el-descriptions-item>

            <el-descriptions-item label="服务结束时间">
              {{ data.orderDetails.actualEndTime || '暂无数据' }}
            </el-descriptions-item>

            <el-descriptions-item label="支付时间">
              {{ data.orderDetails.paymentTime || '暂无数据' }}
            </el-descriptions-item>

            <el-descriptions-item label="下单时间">
              {{ formatDateTime(data.orderDetails.createTime) }}
            </el-descriptions-item>

            <el-descriptions-item label="支付状态">
              <el-tag :type="data.orderDetails.paymentStatus === '已支付' ? 'success' : 'danger'">
                {{ data.orderDetails.paymentStatus }}
              </el-tag>
            </el-descriptions-item>

            <el-descriptions-item label="支付方式">
              {{ data.orderDetails.paymentMethod || '暂无数据' }}
            </el-descriptions-item>
          </el-descriptions>
        </div>
      </div>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button
              type="danger"
              @click="DeleteBtn(data.orderDetails.id)"
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
import request from "@/Backend/utils/request.js";
import {onMounted, reactive, ref} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";

const data = reactive({
  order: [],
  fosterOrderList: {},
  orderDetails: {}
});

const dialogVisible = ref(false);
const hoveredCard = ref(-1);
const dialogLoading = ref(false);
const deleteLoading = ref(false);

// 获取订单数据
const testOrder = () => {
  request.get("/sysOrder/getAllSysOrder").then(res => {
    if (res.code === 200) {
      data.order = res.data;
    } else {
      ElMessage.error('获取订单数据失败: ' + res.message);
    }
  }).catch(error => {
    ElMessage.error('网络错误，请稍后重试');
    console.error(error);
  });
};

const fosterOrders = () => {
  data.fosterOrderList = data.order.filter(item => item.serviceType === "foster")
  console.log(data.fosterOrderList)
}

// 页面加载时获取数据
onMounted(() => {
  testOrder();
});

// 详情按钮
const DetailsBtn = (item) => {
  dialogLoading.value = true;
  data.orderDetails = item;
  dialogVisible.value = true;

  // 模拟加载延迟
  setTimeout(() => {
    dialogLoading.value = false;
  }, 300);
};

// 删除按钮
const DeleteBtn = (id) => {
  ElMessageBox.confirm(
      '确定要删除此订单吗？此操作不可恢复！',
      '删除确认',
      {
        confirmButtonText: '确定删除',
        cancelButtonText: '取消',
        type: 'warning',
        confirmButtonClass: 'el-button--danger'
      }
  ).then(() => {
    deleteLoading.value = true;
    request.delete("/sysOrder/deleteSysOrder?id=" + id).then(res => {
      if (res.code === 200) {
        if (res.data === 1) {
          ElMessage.success('订单删除成功');
          dialogVisible.value = false;
          testOrder();
        } else {
          ElMessage.error('删除失败，未找到该订单');
        }
      } else {
        ElMessage.error('删除失败: ' + res.message);
      }
    }).catch(error => {
      ElMessage.error('网络错误，请稍后重试');
      console.error(error);
    }).finally(() => {
      deleteLoading.value = false;
    });
  }).catch(() => {
    // 用户取消删除
  });
};

// 对话框关闭前处理
const handleDialogClose = (done) => {
  if (deleteLoading.value) {
    ElMessage.warning('正在删除订单，请稍候...');
    return;
  }
  done();
};

// 获取订单状态类型
const getOrderStatusType = (status) => {
  const statusMap = {
    'pending': 'info',//待确认
    'confirmed': 'warning',//已确认
    'processing': 'primary',//处理中
    'completed': 'success',//已完成
    'canceled': 'danger'//已取消
  };
  return statusMap[status] || 'info';
};

// 获取订单状态文本
const getOrderStatusText = (status) => {
  const statusTextMap = {
    'pending': '待确认',
    'confirmed': '已确认',
    'processing': '处理中',
    'completed': '已完成',
    'canceled': '已取消'
  };
  return statusTextMap[status] || '未知状态';
};

// 格式化日期时间
const formatDateTime = (dateString) => {
  if (!dateString) return '暂无数据';
  const date = new Date(dateString);
  return date.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  });
};
</script>

<style scoped>
.order-management-container {
  padding: 20px;
  background-color: #f9f0fc; /* 浅紫色背景 */
  min-height: 100%;
  border-radius: 18px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
  padding-bottom: 16px;
  border-bottom: 1px solid #e6d4f0; /* 浅紫色边框 */
}

.page-title {
  font-size: 28px;
  font-weight: 600;
  color: #b25ac5; /* 主色调 */
  margin: 0;
}

.orders-wrapper {
  margin-top: 20px;
}

.empty-card {
  border-radius: 12px;
  box-shadow: 0 2px 12px 0 rgba(178, 90, 197, 0.15); /* 主色调阴影 */
}

.orders-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(400px, 1fr));
  gap: 20px;
}

.order-card {
  border-radius: 12px;
  box-shadow: 0 2px 12px 0 rgba(178, 90, 197, 0.1); /* 主色调阴影 */
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  border: 1px solid #e6d4f0; /* 浅紫色边框 */
}

.card-hover {
  transform: translateY(-4px);
  box-shadow: 0 6px 16px 0 rgba(178, 90, 197, 0.2); /* 主色调阴影 */
  border-color: #d9bde8; /* 紫色边框 */
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding: 20px 20px 16px;
  border-bottom: 1px solid #f0e6f5; /* 浅紫色边框 */
}

.order-basic {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.order-number {
  display: flex;
  align-items: center;
  gap: 8px;
}

.number-text {
  font-weight: 600;
  color: #303133;
  font-size: 16px;
}

.status-tag {
  align-self: flex-start;
}

.order-amount {
  font-size: 22px;
  font-weight: 700;
  color: #b25ac5; /* 主色调 */
}

.order-details {
  padding: 16px 20px;
}

.detail-row {
  display: flex;
  align-items: center;
  margin-bottom: 12px;
}

.detail-row:last-child {
  margin-bottom: 0;
}

.detail-row i {
  font-size: 16px;
  color: #b25ac5; /* 主色调 */
  margin-right: 8px;
  width: 16px;
  text-align: center;
}

.detail-label {
  font-size: 14px;
  color: #9c6aad;
  margin-right: 8px;
  width: 70px;
  flex-shrink: 0;
}

.detail-value {
  font-size: 14px;
  color: #606266;
  flex: 1;
}

.card-actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  padding: 16px 20px;
  background-color: #faf6fc; /* 浅紫色背景 */
  border-top: 1px solid #f0e6f5; /* 浅紫色边框 */
}

/* Element Plus 主色调覆盖 */
:deep(.el-button--primary) {
  background-color: #b25ac5 !important; /* 主色调 */
  border-color: #b25ac5 !important;
}

:deep(.el-button--primary:hover) {
  background-color: #9a49ac !important; /* 主色调深色变体 */
  border-color: #9a49ac !important;
}

:deep(.el-button--primary:focus) {
  background-color: #b25ac5 !important;
  border-color: #b25ac5 !important;
}

:deep(.el-tag--primary) {
  background-color: #f0e6f5 !important; /* 浅紫色背景 */
  border-color: #e6d4f0 !important; /* 浅紫色边框 */
  color: #b25ac5 !important; /* 主色调文字 */
}

.dialog-content {
  min-height: 200px;
}

.detail-grid {
  padding: 10px 0;
}

.amount-highlight {
  font-size: 18px;
  font-weight: 700;
  color: #b25ac5; /* 主色调 */
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

/* 对话框样式 */
:deep(.order-detail-dialog .el-dialog__header) {
  background: linear-gradient(120deg, #f9f0fc, #f0e6f5); /* 浅紫色渐变 */
  padding: 20px;
  border-radius: 8px 8px 0 0 !important;
  border-bottom: 1px solid #e6d4f0;
}

:deep(.order-detail-dialog .el-dialog__title) {
  color: #b25ac5; /* 主色调 */
  font-weight: 600;
}

:deep(.el-descriptions__label) {
  color: #9c6aad; /* 紫色文字 */
  font-weight: 500;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .order-management-container {
    padding: 15px;
  }

  .page-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }

  .orders-grid {
    grid-template-columns: 1fr;
    gap: 16px;
  }

  .order-card {
    margin-bottom: 0;
  }

  .order-header {
    flex-direction: column;
    gap: 12px;
    align-items: flex-start;
  }

  .order-amount {
    font-size: 20px;
  }

  .card-actions {
    flex-direction: column;
  }

  .card-actions .el-button {
    width: 100%;
  }
}
</style>
