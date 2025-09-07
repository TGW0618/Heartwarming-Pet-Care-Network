<template>
  <div>
    <!-- 订单列表表格 -->
    <div class="order-table-container">
      <el-table :data="orderData?.list || []" style="width: 100%" border size="small">
        <el-table-column prop="orderNo" label="订单编号" width="255">
          <template #default="scope">
            <el-tag type="primary" effect="dark">{{ scope.row.orderNo }}</el-tag>
          </template>
        </el-table-column>

        <el-table-column label="服务信息" min-width="130">
          <template #default="scope">
            <div class="service-info">
              <div class="service-name">{{ scope.row.serviceName }}</div>
              <div class="service-price">¥{{ scope.row.amount }}</div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="客户信息" min-width="130">
          <template #default="scope">
            <div class="customer-info">
              <div class="real-name">{{ scope.row.realName }}</div>
              <div class="username">({{ scope.row.username }})</div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="宠物信息" min-width="130">
          <template #default="scope">
            <div class="pet-info">
              <div class="pet-name">{{ scope.row.petName }}</div>
              <div class="pet-type">({{ scope.row.petType }})</div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="订单状态" prop="status"  width="90">
          <template #default="scope">
            <el-tag :type="getOrderStatusType(scope.row.status)" size="small" effect="light">
              {{ getOrderStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column label="支付状态" prop="paymentStatus"  width="90">
          <template #default="scope">
            <el-tag
                :type="getPaymentStatusType(scope.row.paymentStatus)"
                size="small"
                effect="plain"
            >{{ getPaymentStatusText(scope.row.paymentStatus) }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column  label="下单时间" prop="createTime" width="130">
          <template #default="scope">
            {{ formatDateTime(scope.row.createTime) }}
          </template>
        </el-table-column>

        <el-table-column label="操作" width="230" fixed="right">
          <template #default="scope">
            <el-button size="small" @click="handleDetail(scope.row)" class="detail-btn">
              <el-icon>
                <View/>
              </el-icon>
              详情
            </el-button>
            <el-button size="small" @click="handleEdit(scope.row)" class="edit-btn">
              <el-icon>
                <Edit/>
              </el-icon>
              编辑
            </el-button>
            <el-button size="small" @click="handleDelete(scope.row.id)" class="delete-btn">
              <el-icon>
                <Delete/>
              </el-icon>
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 订单详情对话框 -->
    <div>
      <el-dialog
          v-model="dialogVisible"
          title="订单详细信息"
          width="700px"
          class="order-detail-dialog"
      >
        <div v-if="Object.keys(data.orderDetails).length > 0" class="detail-content">
          <el-scrollbar max-height="500px">
            <div class="detail-grid">
              <!-- 相关记录信息 -->
              <div class="detail-section" v-if="data.recordInfo && Object.keys(data.recordInfo).length > 0">
                <h3 class="section-title">处理记录</h3>

                <!-- 医疗记录信息 -->
                <div v-if="data.orderDetails.serviceType === 'medical'">
                  <el-descriptions :column="2" border size="small">
                    <el-descriptions-item label="医生ID">
                      <el-tag type="primary" effect="dark">{{ data.recordInfo.employeeID || '暂无' }}</el-tag>
                    </el-descriptions-item>

                    <el-descriptions-item label="医生姓名">
                      <el-tag type="success" effect="light">
                        {{ data.recordInfo.employeeRealName || '暂无' }}
                      </el-tag>
                    </el-descriptions-item>

                    <el-descriptions-item label="症状描述" :span="2">
                      <div class="record-content">
                        {{ data.recordInfo.symptoms || '暂无' }}
                      </div>
                    </el-descriptions-item>

                    <el-descriptions-item label="诊断结果" :span="2">
                      <div class="record-content">
                        {{ data.recordInfo.diagnosis || '暂无' }}
                      </div>
                    </el-descriptions-item>

                    <el-descriptions-item label="治疗方案" :span="2">
                      <div class="record-content">
                        {{ data.recordInfo.treatment || '暂无' }}
                      </div>
                    </el-descriptions-item>

                    <el-descriptions-item label="处方信息" :span="2">
                      <div class="record-content">
                        {{ data.recordInfo.prescription || '暂无' }}
                      </div>
                    </el-descriptions-item>

                    <el-descriptions-item label="下次复诊" :span="2">
                      <div class="record-content">
                        {{ data.recordInfo.nextVisitDate ? formatDateTime(data.recordInfo.nextVisitDate) : '暂无' }}
                      </div>
                    </el-descriptions-item>

                    <el-descriptions-item label="记录时间" :span="2">
                      {{ data.recordInfo.createTime ? formatDateTime(data.recordInfo.createTime) : '暂无' }}
                    </el-descriptions-item>
                  </el-descriptions>
                </div>

                <!-- 饲养记录信息 -->
                <div v-else-if="data.orderDetails.serviceType === 'foster'">
                  <el-descriptions :column="2" border size="small">
                    <el-descriptions-item label="饲养员ID">
                      <el-tag type="primary" effect="dark">{{ data.recordInfo.employeeID || '暂无' }}</el-tag>
                    </el-descriptions-item>

                    <el-descriptions-item label="饲养员姓名">
                      <el-tag type="success" effect="light">
                        {{ data.recordInfo.employeeRealName || '暂无' }}
                      </el-tag>
                    </el-descriptions-item>

                    <el-descriptions-item label="房间编号">
                      <el-tag type="warning">{{ data.recordInfo.roomNumber || '暂无' }}</el-tag>
                    </el-descriptions-item>

                    <el-descriptions-item label="房间类型">
                      <el-tag type="info">{{ data.recordInfo.roomType || '暂无' }}</el-tag>
                    </el-descriptions-item>

                    <el-descriptions-item label="入住时间">
                      {{ data.recordInfo.checkInTime || '暂无' }}
                    </el-descriptions-item>

                    <el-descriptions-item label="退房时间">
                      {{ data.recordInfo.checkOutTime || '暂无' }}
                    </el-descriptions-item>

                    <el-descriptions-item label="饮食偏好" :span="2">
                      <div class="record-content">
                        {{ data.recordInfo.foodPreference || '暂无' }}
                      </div>
                    </el-descriptions-item>

                    <el-descriptions-item label="注意事项" :span="2">
                      <div class="record-content">
                        {{ data.recordInfo.notes || '暂无' }}
                      </div>
                    </el-descriptions-item>

                    <el-descriptions-item label="每日日志" :span="2">
                      <div class="record-content">
                        {{ data.recordInfo.dailyLog || '暂无' }}
                      </div>
                    </el-descriptions-item>

                    <el-descriptions-item label="记录时间" :span="2">
                      {{ data.recordInfo.createTime ? formatDateTime(data.recordInfo.createTime) : '暂无' }}
                    </el-descriptions-item>
                  </el-descriptions>
                </div>

                <!-- 其他类型记录 -->
                <div v-else>
                  <el-alert title="暂无相关处理记录" type="info" :closable="false"/>
                </div>
              </div>

              <!-- 订单基本信息 -->
              <div class="detail-section">
                <h3 class="section-title">订单信息</h3>
                <el-descriptions :column="2" border size="small">
                  <el-descriptions-item label="订单号" width="100px">
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

                  <el-descriptions-item label="下单时间">
                    {{ formatDateTime(data.orderDetails.createTime) }}
                  </el-descriptions-item>

                  <el-descriptions-item label="服务类型">
                    <el-tag type="success" size="small">{{ data.orderDetails.serviceType }}</el-tag>
                  </el-descriptions-item>

                  <el-descriptions-item label="医疗类型" v-if="data.orderDetails.medicalType">
                    <el-tag type="warning" size="small">{{ data.orderDetails.medicalType }}</el-tag>
                  </el-descriptions-item>
                </el-descriptions>
              </div>

              <!-- 宠物信息 -->
              <div class="detail-section">
                <h3 class="section-title">宠物信息</h3>
                <el-descriptions :column="2" border size="small">
                  <el-descriptions-item label="宠物名称">
                    {{ data.orderDetails.petName }}
                  </el-descriptions-item>

                  <el-descriptions-item label="宠物类型">
                    {{ data.orderDetails.petType }}
                  </el-descriptions-item>

                  <el-descriptions-item label="宠物品种">
                    {{ data.orderDetails.petBreed }}
                  </el-descriptions-item>

                  <el-descriptions-item label="宠物年龄">
                    {{ data.orderDetails.petAge }}岁
                  </el-descriptions-item>

                  <el-descriptions-item label="健康状况">
                    {{ data.orderDetails.petHealthStatus }}
                  </el-descriptions-item>

                  <el-descriptions-item label="过敏史">
                    {{ data.orderDetails.petAllergies }}
                  </el-descriptions-item>

                  <el-descriptions-item label="出生日期" :span="2">
                    {{ data.orderDetails.petBirthDate ? formatDateTime(data.orderDetails.petBirthDate) : '暂无' }}
                  </el-descriptions-item>
                </el-descriptions>
              </div>

              <!-- 服务信息 -->
              <div class="detail-section">
                <h3 class="section-title">服务信息</h3>
                <el-descriptions :column="2" border size="small">
                  <el-descriptions-item label="服务项目">
                    {{ data.orderDetails.serviceName }}
                  </el-descriptions-item>

                  <el-descriptions-item label="服务类别">
                    {{ data.orderDetails.serviceCategory }}
                  </el-descriptions-item>

                  <el-descriptions-item label="服务时长">
                    {{ data.orderDetails.serviceDuration }}分钟
                  </el-descriptions-item>

                  <el-descriptions-item label="服务费用">
                    <span class="amount-highlight">¥{{ data.orderDetails.servicePrice }}</span>
                  </el-descriptions-item>

                  <el-descriptions-item label="预约时间" :span="2">
                    {{
                      data.orderDetails.appointmentStartTime ? formatDateTime(data.orderDetails.appointmentStartTime) : '暂无'
                    }}
                  </el-descriptions-item>

                  <el-descriptions-item label="服务描述" :span="2">
                    {{ data.orderDetails.serviceDescription }}
                  </el-descriptions-item>
                </el-descriptions>
              </div>

              <!-- 客户信息 -->
              <div class="detail-section">
                <h3 class="section-title">客户信息</h3>
                <el-descriptions :column="2" border size="small">
                  <el-descriptions-item label="客户姓名">
                    {{ data.orderDetails.realName }}
                  </el-descriptions-item>

                  <el-descriptions-item label="联系电话">
                    {{ data.orderDetails.phone }}
                  </el-descriptions-item>

                  <el-descriptions-item label="用户名">
                    {{ data.orderDetails.username }}
                  </el-descriptions-item>

                  <el-descriptions-item label="电子邮箱">
                    {{ data.orderDetails.userEmail }}
                  </el-descriptions-item>
                </el-descriptions>
              </div>

              <!-- 支付信息 -->
              <div class="detail-section">
                <h3 class="section-title">支付信息</h3>
                <el-descriptions :column="2" border size="small">
                  <el-descriptions-item label="支付状态">
                    <el-tag :type="getPaymentStatusType(data.orderDetails.paymentStatus)" size="small">
                      {{ getPaymentStatusText(data.orderDetails.paymentStatus) }}
                    </el-tag>
                  </el-descriptions-item>

                  <el-descriptions-item label="支付方式">
                    {{ data.orderDetails.paymentMethod }}
                  </el-descriptions-item>

                  <el-descriptions-item label="支付时间">
                    {{ data.orderDetails.paymentTime ? formatDateTime(data.orderDetails.paymentTime) : '未支付' }}
                  </el-descriptions-item>

                  <el-descriptions-item label="交易流水号">
                    {{ data.orderDetails.transactionId || '暂无' }}
                  </el-descriptions-item>
                </el-descriptions>
              </div>

              <!-- 备注信息 -->
              <div class="detail-section" v-if="data.orderDetails.remark">
                <h3 class="section-title">备注信息</h3>
                <div class="remark-content">
                  {{ data.orderDetails.remark || '无备注' }}
                </div>
              </div>
            </div>
          </el-scrollbar>

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

    <!-- 编辑对话框 -->
    <div>
      <el-dialog
          v-model="dialogVisibleEdit"
          title="编辑订单"
          width="700px"
          class="order-edit-dialog"
      >
        <div class="edit-content">
          <el-scrollbar max-height="500px">
            <div class="edit-grid">
              <!-- 基本信息卡片 -->
              <div class="info-card">
                <h3 class="card-title">基本信息</h3>
                <div class="info-grid">
                  <div class="info-item">
                    <span class="info-label">订单号:</span>
                    <span class="info-value">{{ data.orderDataFrom?.orderNo }}</span>
                  </div>
                  <div class="info-item">
                    <span class="info-label">用户:</span>
                    <span class="info-value">{{ data.orderDataFrom?.realName }}({{
                        data.orderDataFrom?.username
                      }})</span>
                  </div>
                  <div class="info-item">
                    <span class="info-label">宠物:</span>
                    <span class="info-value">{{ data.orderDataFrom?.petName }}</span>
                  </div>
                  <div class="info-item">
                    <span class="info-label">服务项目:</span>
                    <span class="info-value">{{ data.orderDataFrom?.serviceName }}</span>
                  </div>
                  <div class="info-item">
                    <span class="info-label">服务类型:</span>
                    <span class="info-value">{{ data.orderDataFrom?.serviceType }}</span>
                  </div>
                  <div class="info-item">
                    <span class="info-label">员工:</span>
                    <span class="info-value">{{ data.orderDataFrom?.employeeUsername }}</span>
                  </div>
                </div>
              </div>

              <!-- 可编辑信息卡片 -->
              <div class="edit-card">
                <h3 class="card-title">可编辑信息</h3>
                <el-form :model="data.orderDataFrom" label-width="100px" class="edit-form">
                  <el-row :gutter="20">
                    <el-col :span="24">
                      <el-form-item label="预约开始时间" class="form-item">
                        <el-date-picker
                            v-model="data.orderDataFrom.appointmentStartTime"
                            type="datetime"
                            placeholder="请选择预约开始时间"
                            format="YYYY-MM-DD HH:mm:ss"
                            value-format="YYYY-MM-DD HH:mm:ss"
                            class="full-width"
                            :disabled-date="disabledBeforeCurrentTime"
                            :disabled-time="disabledBeforeCurrentTime"
                        />
                      </el-form-item>
                    </el-col>

                    <el-col :span="24">
                      <el-form-item label="预约结束时间" class="form-item">
                        <el-date-picker
                            v-model="data.orderDataFrom.appointmentEndTime"
                            type="datetime"
                            placeholder="请选择预约结束时间"
                            format="YYYY-MM-DD HH:mm:ss"
                            value-format="YYYY-MM-DD HH:mm:ss"
                            class="full-width"
                            :disabled-date="disabledBeforeCurrentTime"
                            :disabled-time="disabledBeforeCurrentTime"
                        />
                      </el-form-item>
                    </el-col>

                    <el-col :span="24">
                      <el-form-item label="订单金额" class="form-item">
                        <el-input
                            v-model="data.orderDataFrom.amount"
                            placeholder="请输入订单金额"
                            class="full-width"
                        >
                          <template #prefix>¥</template>
                        </el-input>
                      </el-form-item>
                    </el-col>

                    <el-col :span="24">
                      <el-form-item label="订单状态" class="form-item">
                        <el-select
                            v-model="data.orderDataFrom.status"
                            placeholder="请选择订单状态"
                            class="full-width"
                        >
                          <el-option
                              v-for="item in orderStatus"
                              :key="item.value"
                              :label="item.label"
                              :value="item.value"
                          />
                        </el-select>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-form>
              </div>
            </div>
          </el-scrollbar>
        </div>

        <template #footer>
          <div class="dialog-footer">
            <el-button @click="dialogVisibleEdit = false" class="cancel-btn">取消</el-button>
            <el-button
                type="primary"
                @click="handleUpdate"
                class="confirm-btn"
            >
              保存修改
            </el-button>
          </div>
        </template>
      </el-dialog>
    </div>

  </div>
</template>

<script setup>
import {useOrderStore} from '@/Backend/stores/order.js'
import {reactive, ref} from "vue"
import {ElMessage, ElMessageBox} from "element-plus"
import {Delete, Edit, View} from '@element-plus/icons-vue'
import request from "@/Backend/utils/request.js";


const props = defineProps({
  orderData: {
    type: Object,
    default: () => null
  }
})

const data = reactive({
  orderDetails: {},
  recordInfo: {},
  orderDataFrom: null,
})

const orderStore = useOrderStore()

const dialogVisible = ref(false)
const dialogVisibleEdit = ref(false)
const deleteLoading = ref(false)


/*api请求
* 获取订单相关记录
* 修改订单
* */
// 获取订单相关记录
const getRecord = (orderId) => {
  request.get(`/sysOrder/getRecordByOrderId/${orderId}`).then(res => {
    if (res.code === 200) {
      data.recordInfo = res.data[0]
      console.log(data.recordInfo)
    }
  })

}

// 删除订单
const handleDelete = (id) => {
  deleteLoading.value = true
  ElMessageBox.confirm('此操作将永久删除该订单, 是否继续?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    orderStore.deleteOrder(id)
  })
}

// 查看详情
const handleDetail = (order) => {
  data.orderDetails = order
  getRecord(order.id)
  dialogVisible.value = true
}

/*
* 编辑相关内容
* 编辑按钮
* 确定编辑按钮
* */
const handleEdit = (order) => {// 编辑按钮
  /*使用 JSON 方法进行深拷贝
  * 浅拷贝问题：如果只拷贝第一层属性，嵌套对象仍然会是引用
  *深拷贝优势：完全独立的副本，修改不会影响原始数据
  * */
  data.orderDataFrom = JSON.parse(JSON.stringify(order))
  dialogVisibleEdit.value = true
  console.log(data.orderDataFrom.status)
}
const handleUpdate = () => {// 确定编辑按钮
  console.log(data.orderDataFrom.status)
  orderStore.updateSysOrder(data.orderDataFrom)  // 修改订单
  dialogVisibleEdit.value = false
}

// 禁用当前时间之前的日期和时间
// 结束时间不能早于开始时间
const disabledBeforeCurrentTime = (date) => {
  if (!data.orderDataFrom.appointmentStartTime) {
    // 如果没有设置开始时间，则使用当前时间限制
    return date.getTime() < Date.now();
  } else {
    // 如果设置了开始时间，则结束时间不能早于开始时间
    return date.getTime() < new Date(data.orderDataFrom.appointmentStartTime).getTime();
  }
}

// 订单状态下拉选择
const orderStatus = [
  {
    value: 'pending',
    label: '待确认',
  },
  {
    value: 'confirmed',
    label: '已确认',
  },
  {
    value: 'processing',
    label: '处理中',
  },
  {
    value: 'completed',
    label: '已完成',
  },
  {
    value: 'canceled',
    label: '取消订单',
  }
]


// 获取订单状态类型
const getOrderStatusType = (status) => {
  const statusMap = {
    'pending': 'info',    // 待确认
    'confirmed': 'primary',      // 已确认
    'processing': 'warning',     // 处理中
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

// 格式化支付状态
const getPaymentStatusText = (status) => {
  const statusMap = {
    0: '未支付',
    1: '已支付'
  }
  return statusMap[status] || '未知状态'
}

// 获取支付状态类型
const getPaymentStatusType = (status) => {
  const statusMap = {
    0: 'info',
    1: 'success'
  }
  return statusMap[status] || '未知状态'
}
</script>

<style scoped>
.order-table-container {
  background: white;
  border-radius: 10px;
  padding: 20px;
  box-shadow: 0 2px 12px 0 rgba(176, 86, 195, 0.1);
  border: 1px solid #e6d4f0;
}

.service-info,
.customer-info,
.pet-info {
  display: flex;
  flex-direction: column;
}

.service-price {
  color: #b056c3;
  font-weight: bold;
}

.detail-btn {
  background-color: #f0e6f5;
  border-color: #d9b3e6;
  color: #6a2c82;
  margin-right: 5px;
}

.detail-btn:hover {
  background-color: #e6d4f0;
  border-color: #c9a2d9;
}

.edit-btn {
  background-color: #f0e6f5;
  border-color: #d9b3e6;
  color: #6a2c82;
  margin-right: 5px;
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
  color: #b056c3;
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

/* 表格样式优化 */
:deep(.el-table) {
  --el-table-border-color: #e6d4f0;
  --el-table-row-hover-bg-color: #f9f2fc;
}

:deep(.el-table__header th) {
  background-color: #f0e6f5;
  color: #6a2c82;
}

.detail-grid {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.detail-section {
  background: #fafafa;
  border-radius: 8px;
  padding: 15px;
  border: 1px solid #e6d4f0;
}

.detail-section:hover {
  box-shadow: 0 2px 8px rgba(176, 86, 195, 0.1);
}

.section-title {
  margin: 0 0 15px 0;
  padding: 0;
  color: #6a2c82;
  font-size: 16px;
  font-weight: 600;
  border-left: 4px solid #b056c3;
  padding-left: 10px;
}

.remark-content {
  background: white;
  border-radius: 4px;
  padding: 10px;
  border: 1px solid #e6e6e6;
  min-height: 40px;
  color: #666;
}

.amount-highlight {
  color: #b056c3;
  font-weight: bold;
  font-size: 16px;
}

/* 覆盖 Element Plus 样式 */
:deep(.el-descriptions__label) {
  background-color: #f8f0fa !important;
  font-weight: 500;
  width: 100px;
}

:deep(.el-descriptions__content) {
  background-color: white !important;
}

:deep(.el-dialog__body) {
  padding: 15px 20px;
}

:deep(.el-dialog__header) {
  padding: 20px 20px 10px;
}

.record-content {
  background: white;
  border-radius: 4px;
  padding: 8px 12px;
  border: 1px solid #e6e6e6;
  min-height: 24px;
  color: #333;
  white-space: pre-wrap;
  word-break: break-word;
}

/* 覆盖 Element Plus 样式 */
:deep(.el-descriptions__label) {
  background-color: #f8f0fa !important;
  font-weight: 500;
  width: 100px;
}

:deep(.el-descriptions__content) {
  background-color: white !important;
}

:deep(.el-dialog__body) {
  padding: 15px 20px;
}

:deep(.el-dialog__header) {
  padding: 20px 20px 10px;
}


.cell {
  height: 30px;
  padding: 3px 0;
  box-sizing: border-box;
}

.cell .text {
  width: 24px;
  height: 24px;
  display: block;
  margin: 0 auto;
  line-height: 24px;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  border-radius: 50%;
}

.cell.current .text {
  background: #626aef;
  color: #fff;
}

.cell .holiday {
  position: absolute;
  width: 6px;
  height: 6px;
  background: var(--el-color-danger);
  border-radius: 50%;
  bottom: 0px;
  left: 50%;
  transform: translateX(-50%);
}


/* 编辑对话框样式 */
.order-edit-dialog {
  --el-dialog-title-font-size: 18px;
  --el-dialog-title-text-color: #6a2c82;
}

.order-edit-dialog :deep(.el-dialog__header) {
  border-bottom: 1px solid #e6d4f0;
}

.edit-content {
  padding: 20px 10px;
}

.edit-grid {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

/* 卡片样式 */
.info-card,
.edit-card {
  background: #fafafa;
  border-radius: 8px;
  padding: 20px;
  border: 1px solid #e6d4f0;
  box-shadow: 0 2px 8px rgba(176, 86, 195, 0.1);
}

.card-title {
  margin: 0 0 15px 0;
  padding: 0;
  color: #6a2c82;
  font-size: 16px;
  font-weight: 600;
  border-left: 4px solid #b056c3;
  padding-left: 10px;
}

/* 基本信息网格 */
.info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 15px;
}

.info-item {
  display: flex;
  flex-direction: column;
}

.info-label {
  font-size: 12px;
  color: #999;
  margin-bottom: 4px;
}

.info-value {
  font-size: 14px;
  color: #333;
  font-weight: 500;
}

/* 表单样式 */
.edit-form {
  width: 100%;
}

.form-item {
  margin-bottom: 20px;
}

.full-width {
  width: 100%;
}

:deep(.el-form-item__label) {
  color: #666;
  font-weight: 500;
}

/* 按钮样式 */
.cancel-btn {
  background-color: #f5f5f5;
  border-color: #dcdfe6;
  color: #606266;
}

.cancel-btn:hover {
  background-color: #e6e6e6;
  border-color: #c0c4cc;
}

.confirm-btn {
  background-color: #b056c3;
  border-color: #b056c3;
}

.confirm-btn:hover {
  background-color: #9a4aa9;
  border-color: #9a4aa9;
}

/* 覆盖 Element Plus 样式 */
:deep(.el-dialog__body) {
  padding: 15px 20px;
}

:deep(.el-dialog__header) {
  padding: 20px 20px 10px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .info-grid {
    grid-template-columns: 1fr;
  }

  .edit-grid {
    gap: 15px;
  }

  .info-card,
  .edit-card {
    padding: 15px;
  }
}

</style>
