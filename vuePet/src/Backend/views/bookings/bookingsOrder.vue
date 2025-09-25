<!--预约订单-->
<template>
  <div>
    <!-- 表格 -->
    <div class="order-table-container">
      <el-table :data="filteredOrderList" style="width: 100%" border size="small">
        <el-table-column prop="orderNo" label="订单编号" width="220">
          <template #default="scope">
            <el-tag type="primary" effect="dark">{{ scope.row.orderNo }}</el-tag>
          </template>
        </el-table-column>

        <el-table-column label="服务信息" min-width="180">
          <template #default="scope">
            <div class="service-info">
              <div class="service-name">{{ scope.row.serviceName }}</div>
              <div class="service-type">
                <el-tag :type="getServiceTypeTag(scope.row.serviceType)" size="small">
                  {{ getServiceTypeText(scope.row.serviceType) }}
                </el-tag>
              </div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="客户信息" min-width="120">
          <template #default="scope">
            <div class="customer-info">
              <div class="real-name">{{ scope.row.realName }}</div>
              <div class="username">({{ scope.row.username }})</div>
            </div>
          </template>
        </el-table-column>

        <el-table-column label="宠物信息" min-width="120">
          <template #default="scope">
            <div class="pet-info">
              <div class="pet-name">{{ scope.row.petName }}</div>
              <div class="pet-type">({{ scope.row.petType }})</div>
            </div>
          </template>
        </el-table-column>

        <el-table-column sortable prop="status" label="订单状态" width="100">
          <template #default="scope">
            <el-tag :type="getOrderStatusType(scope.row.status)" size="small" effect="light">
              {{ getOrderStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column sortable prop="createTime" label="下单时间" width="160">
          <template #default="scope">
            {{ formatDateTime(scope.row.createTime) }}
          </template>
        </el-table-column>

        <el-table-column label="操作" width="180" fixed="right">
          <template #default="scope">
            <el-button size="small" @click="handleDetail(scope.row)" class="detail-btn">
              <el-icon>
                <View/>
              </el-icon>
              详情
            </el-button>
            <el-button size="small" @click="handleDispose(scope.row)" class="edit-btn">
              <el-icon>
                <Edit/>
              </el-icon>
              处理
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 详情弹窗 -->
    <div>
      <el-dialog
          v-model="dialogVisible"
          title="订单详细信息"
          width="700px"
          class="order-detail-dialog"
      >
        <div v-if="Object.keys(data.OrderDetail).length > 0" class="detail-content">
          <el-scrollbar max-height="500px">
            <div class="detail-grid">
              <!-- 订单基本信息 -->
              <div class="detail-section">
                <h3 class="section-title">订单信息</h3>
                <el-descriptions :column="2" border size="small">
                  <el-descriptions-item label="订单号" width="100px">
                    <el-tag type="primary" effect="dark">{{ data.OrderDetail.orderNo }}</el-tag>
                  </el-descriptions-item>

                  <el-descriptions-item label="订单状态">
                    <el-tag :type="getOrderStatusType(data.OrderDetail.status)" effect="light">
                      {{ getOrderStatusText(data.OrderDetail.status) }}
                    </el-tag>
                  </el-descriptions-item>

                  <el-descriptions-item label="订单金额">
                    <span class="amount-highlight">¥{{ data.OrderDetail.amount }}</span>
                  </el-descriptions-item>

                  <el-descriptions-item label="下单时间">
                    {{ formatDateTime(data.OrderDetail.createTime) }}
                  </el-descriptions-item>

                  <el-descriptions-item label="服务类型">
                    <el-tag :type="getServiceTypeTag(data.OrderDetail.serviceType)" size="small">
                      {{ getServiceTypeText(data.OrderDetail.serviceType) }}
                    </el-tag>
                  </el-descriptions-item>

                  <el-descriptions-item label="医疗类型" v-if="data.OrderDetail.medicalType">
                    <el-tag type="warning" size="small">{{ data.OrderDetail.medicalType }}</el-tag>
                  </el-descriptions-item>
                </el-descriptions>
              </div>

              <!-- 宠物信息 -->
              <div class="detail-section">
                <h3 class="section-title">宠物信息</h3>
                <el-descriptions :column="2" border size="small">
                  <el-descriptions-item label="宠物名称">
                    {{ data.OrderDetail.petName }}
                  </el-descriptions-item>

                  <el-descriptions-item label="宠物类型">
                    {{ data.OrderDetail.petType }}
                  </el-descriptions-item>

                  <el-descriptions-item label="宠物品种">
                    {{ data.OrderDetail.petBreed }}
                  </el-descriptions-item>

                  <el-descriptions-item label="宠物年龄">
                    {{ data.OrderDetail.petAge }}岁
                  </el-descriptions-item>

                  <el-descriptions-item label="健康状况">
                    {{ data.OrderDetail.petHealthStatus }}
                  </el-descriptions-item>

                  <el-descriptions-item label="过敏史">
                    {{ data.OrderDetail.petAllergies || '无' }}
                  </el-descriptions-item>

                  <el-descriptions-item label="出生日期" :span="2">
                    {{ data.OrderDetail.petBirthDate ? formatDateTime(data.OrderDetail.petBirthDate) : '暂无' }}
                  </el-descriptions-item>
                </el-descriptions>
              </div>

              <!-- 服务信息 -->
              <div class="detail-section">
                <h3 class="section-title">服务信息</h3>
                <el-descriptions :column="2" border size="small">
                  <el-descriptions-item label="服务项目">
                    {{ data.OrderDetail.serviceName || 'null' }}
                  </el-descriptions-item>

                  <el-descriptions-item label="服务类别">
                    {{ data.OrderDetail.serviceType || '暂无' }}
                  </el-descriptions-item>

                  <el-descriptions-item label="服务时长">
                    {{ data.OrderDetail.serviceDuration || 'null' }}分钟
                  </el-descriptions-item>

                  <el-descriptions-item label="服务费用">
                    <span class="amount-highlight">{{ data.OrderDetail.servicePrice || 'null' }}¥</span>
                  </el-descriptions-item>

                  <el-descriptions-item label="预约时间" :span="2">
                    {{
                      data.OrderDetail.appointmentStartTime ? formatDateTime(data.OrderDetail.appointmentStartTime) : '暂无'
                    }}
                    {{
                      data.OrderDetail.appointmentEndTime ? ' 至 ' + formatDateTime(data.OrderDetail.appointmentEndTime) : ''
                    }}
                  </el-descriptions-item>

                  <el-descriptions-item label="服务描述" :span="2">
                    {{ data.OrderDetail.serviceDescription || '暂无描述' }}
                  </el-descriptions-item>
                </el-descriptions>
              </div>

              <!-- 客户信息 -->
              <div class="detail-section">
                <h3 class="section-title">客户信息</h3>
                <el-descriptions :column="2" border size="small">
                  <el-descriptions-item label="客户姓名">
                    {{ data.OrderDetail.realName }}
                  </el-descriptions-item>

                  <el-descriptions-item label="联系电话">
                    {{ data.OrderDetail.phone }}
                  </el-descriptions-item>

                  <el-descriptions-item label="用户名">
                    {{ data.OrderDetail.username }}
                  </el-descriptions-item>

                  <el-descriptions-item label="电子邮箱">
                    {{ data.OrderDetail.userEmail || '暂无' }}
                  </el-descriptions-item>
                </el-descriptions>
              </div>

              <!-- 支付信息 -->
              <div class="detail-section">
                <h3 class="section-title">支付信息</h3>
                <el-descriptions :column="2" border size="small">
                  <el-descriptions-item label="支付状态">
                    <el-tag :type="getPaymentStatusType(data.OrderDetail.paymentStatus)" size="small">
                      {{ getPaymentStatusText(data.OrderDetail.paymentStatus) }}
                    </el-tag>
                  </el-descriptions-item>

                  <el-descriptions-item label="支付方式">
                    {{ getPaymentMethodText(data.OrderDetail.paymentMethod) }}
                  </el-descriptions-item>

                  <el-descriptions-item label="支付时间">
                    {{ data.OrderDetail.paymentTime ? formatDateTime(data.OrderDetail.paymentTime) : '未支付' }}
                  </el-descriptions-item>

                  <el-descriptions-item label="交易流水号">
                    {{ data.OrderDetail.transactionId || '暂无' }}
                  </el-descriptions-item>
                </el-descriptions>
              </div>

              <!-- 备注信息 -->
              <div class="detail-section" v-if="data.OrderDetail.remark">
                <h3 class="section-title">备注信息</h3>
                <div class="remark-content">
                  {{ data.OrderDetail.remark || '无备注' }}
                </div>
              </div>
            </div>
          </el-scrollbar>
        </div>

        <template #footer>
          <div class="dialog-footer">
            <el-button @click="dialogVisible = false">关闭</el-button>
          </div>
        </template>
      </el-dialog>
    </div>

    <!-- 处理弹窗 -->
    <div>
      <!-- 寄养 -->
      <div>
        <el-dialog
            v-model="dialogVisibleDisposeFoster"
            title="订单处理"
            width="80%"
            @close="resetFosterForm"
        >
          <div class="dispose-container">
            <!-- 订单信息区域 -->
            <el-card class="info-card" shadow="hover">
              <div class="card-header">
                <h3>订单基本信息</h3>
              </div>
              <div class="card-content">
                <el-row :gutter="20">
                  <el-col :span="12">
                    <div class="info-item">
                      <span class="info-label">订单ID:</span>
                      <span class="info-value">{{ data.handleDisposeRowData.id }}</span>
                    </div>
                  </el-col>
                  <el-col :span="12">
                    <div class="info-item">
                      <span class="info-label">服务名称:</span>
                      <span class="info-value">{{ data.handleDisposeRowData.serviceName }}</span>
                    </div>
                  </el-col>
                </el-row>
              </div>
            </el-card>

            <!-- 房间信息区域 -->
            <el-card class="info-card" shadow="hover">
              <div class="card-header">
                <h3>房间信息</h3>
              </div>
              <div class="card-content">
                <el-row :gutter="20">
                  <el-col :span="12">
                    <div class="info-item">
                      <span class="info-label">空余房间数:</span>
                      <span class="info-value">{{ data.availableRoomsCount }}</span>
                    </div>
                  </el-col>
                </el-row>

                <!-- 可用房间列表 -->
                <div class="rooms-section">
                  <div class="section-title">可用房间列表</div>
                  <div v-if="data.availableRooms && data.availableRooms.length > 0">
                    <el-table :data="data.availableRooms" style="width: 100%" size="small" max-height="200">
                      <el-table-column prop="roomNumber" label="房间号" width="180" align="center"/>
                      <el-table-column prop="roomName" label="房间名称" width="180"/>
                      <el-table-column prop="roomType" label="房间类型" width="180" align="center"/>
                      <el-table-column prop="capacity" label="容量" width="180" align="center"/>
                      <el-table-column label="操作" width="250" align="center">
                        <template #default="scope">
                          <el-button
                              size="small"
                              :type="data.fosterOrderDisposeForm.roomId === scope.row.roomId ? 'info' : ''"
                              @click="selectRoom(scope.row)"
                          >
                            {{ data.fosterOrderDisposeForm.roomId === scope.row.roomId ? '已选择' : '选择' }}
                          </el-button>
                        </template>
                      </el-table-column>
                    </el-table>
                  </div>
                  <div v-else class="no-data">
                    暂无可用房间
                  </div>
                </div>
              </div>
            </el-card>

            <!-- 时间 -->
            <el-card class="info-card" shadow="hover">
              <div class="card-header">
                <h3>选择入住/退房时间</h3>
              </div>
              <div class="card-content">
                <div class="allocation-section">
                  <div class="time-selection">
                    <el-row :gutter="20">
                      <el-col :span="12">
                        <div class="time-item">
                          <span class="time-label">入住时间:</span>
                          <el-date-picker
                              v-model="data.fosterOrderDisposeForm.checkInTime"
                              type="datetime"
                              placeholder="选择入住时间"
                              format="YYYY-MM-DD HH:mm"
                              value-format="YYYY-MM-DD HH:mm"
                              style="width: 100%"
                          />
                        </div>
                      </el-col>
                      <el-col :span="12">
                        <div class="time-item">
                          <span class="time-label">退房时间:</span>
                          <el-date-picker
                              v-model="data.fosterOrderDisposeForm.checkOutTime"
                              type="datetime"
                              placeholder="选择退房时间"
                              format="YYYY-MM-DD HH:mm"
                              value-format="YYYY-MM-DD HH:mm"
                              style="width: 100%"
                          />
                        </div>
                      </el-col>
                    </el-row>
                  </div>
                </div>
              </div>
            </el-card>
          </div>

          <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogVisibleDisposeFoster = false">取消</el-button>
              <el-button type="primary" @click="onDispose">确认</el-button>
            </span>
          </template>
        </el-dialog>
      </div>

      <!-- 医疗 -->
      <div>
        <el-dialog
            v-model="dialogVisibleDisposeVet"
            title="订单处理"
            width="80%"
            @close="resetVetForm"
        >
          <div class="dispose-container">
            <!-- 订单信息区域 -->
            <el-card class="info-card" shadow="hover">
              <div class="card-header">
                <h3>订单基本信息</h3>
              </div>
              <div class="card-content">
                <el-row :gutter="20">
                  <el-col :span="12">
                    <div class="info-item">
                      <span class="info-label">订单ID:</span>
                      <span class="info-value">{{ data.handleDisposeRowData.orderNo }}</span>
                    </div>
                  </el-col>
                  <el-col :span="12">
                    <div class="info-item">
                      <span class="info-label">服务名称:</span>
                      <span class="info-value">{{ data.handleDisposeRowData.serviceName }}</span>
                    </div>
                  </el-col>
                </el-row>
              </div>
            </el-card>

            <el-card class="info-card" shadow="hover">
              <div>
                <el-form :model="data.vetOrderDisposeForm" :rules="vetRules">
                  <el-form-item label="症状描述" prop="symptoms">
                    <el-input
                        v-model="data.vetOrderDisposeForm.symptoms"
                        :rows="2"
                        type="textarea"
                        placeholder="症状描述"
                    />
                  </el-form-item>
                  <el-form-item label="诊断结果" prop="diagnosis">
                    <el-input
                        v-model="data.vetOrderDisposeForm.diagnosis"
                        :rows="2"
                        type="textarea"
                        placeholder="诊断结果"
                    />
                  </el-form-item>
                  <el-form-item label="治疗方案" prop="treatment">
                    <el-input
                        v-model="data.vetOrderDisposeForm.treatment"
                        :rows="2"
                        type="textarea"
                        placeholder="治疗方案"
                    />
                  </el-form-item>
                  <el-form-item label="处方" prop="prescription">
                    <el-input
                        v-model="data.vetOrderDisposeForm.prescription"
                        :rows="2"
                        type="textarea"
                        placeholder="处方"
                    />
                  </el-form-item>
                  <el-form-item label="下次复诊时间" prop="nextVisitDate">
                    <el-date-picker
                        v-model="data.vetOrderDisposeForm.nextVisitDate"
                        type="date"
                        placeholder="选择复诊时间"
                        format="YYYY-MM-DD"
                        style="width: 100%"
                    />
                  </el-form-item>
                </el-form>
              </div>
            </el-card>
          </div>

          <template #footer>
            <span class="dialog-footer">
              <el-button @click="dialogVisibleDisposeVet = false">取消</el-button>
              <el-button type="primary" @click="onDisposeVet">确认</el-button>
            </span>
          </template>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script setup>
import request from "@/Backend/utils/request.js";
import {computed, onMounted, reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import {useUserStore} from "@/Backend/stores/userStore.js";
import {View, Edit} from '@element-plus/icons-vue'

// 获取用户存储和角色信息
const userStore = useUserStore()
const isRole = computed(() => userStore.userRole)

// 响应式数据定义
const data = reactive({
  OrderList: [],           // 订单列表
  OrderDetail: {},         // 订单详情
  size: 'medium',          // 表格大小
  handleDisposeRowData: {}, // 处理订单的数据
  availableRooms: null,    // 可用房间列表
  availableRoomsCount: null, // 可用房间数量
  fosterOrderDisposeForm: { // 寄养订单处理表单
    orderId: '',
    roomId: '',
    checkInTime: '',    // 入住时间
    checkOutTime: '',   // 退房时间
  },
  vetOrderDisposeForm: { // 医疗订单处理表单
    orderId: '',// 订单ID
    symptoms: '',// 症状
    diagnosis: '',// 诊断
    treatment: '',// 治疗方案
    prescription: '',// 处方
    nextVisitDate: '',// 下次复诊日期
  },
})

/**
 * 根据用户角色获取相应的订单列表
 * 不同角色调用不同的后端接口获取数据
 */
const getOrderByRole = () => {
  switch (isRole.value) {
    case 'admin':
    case 'foster_staff':
      getOrderList()
      break
    case 'veterinarian':
      getVetOrderList()
      break
    default:
      getOrderList()
  }
}

/**
 * 根据角色过滤订单列表（前端过滤）
 * admin: 显示所有订单
 * foster_staff: 只显示寄养订单
 * veterinarian: 只显示医疗订单
 */
const filteredOrderList = computed(() => {
  switch (isRole.value) {
    case 'admin':
      return data.OrderList
    case 'foster_staff':
      return data.OrderList.filter(item => item.serviceType === 'foster')
    case 'veterinarian':
      return data.OrderList.filter(item => item.serviceType === 'medical')
    default:
      return []
  }
})

/**
 * 获取所有预约订单列表
 * 适用于管理员和寄养员工角色
 */
const getOrderList = () => {
  request.get("/sysOrder/getSysOrderByStatus").then(res => {
    if (res.code === 200) {
      data.OrderList = res.data
    } else {
      ElMessage.error(res.msg || '获取订单列表失败')
      data.OrderList = []
    }
  }).catch(err => {
    ElMessage.error('获取订单列表失败：' + (err.message || '网络错误'))
    data.OrderList = []
  })
}

/**
 * 获取医疗预约订单列表（根据员工ID）
 * 适用于兽医角色，只获取分配给该兽医的订单
 */
const getVetOrderList = () => {
  request.get("/sysOrder/getSysOrderByEmployeeId").then(res => {
    if (res.code === 200) {
      data.OrderList = res.data || []
    } else {
      ElMessage.error(res.msg || '获取医疗订单失败')
      data.OrderList = []
    }
  }).catch(err => {
    ElMessage.error('获取医疗订单失败：' + (err.message || '网络错误'))
    data.OrderList = []
  })
}

/**
 * 获取房间统计数据
 * 包括总房间数、可用房间数、占用房间数
 */
const getRoomsStatistics = () => {
  request.get("/getRoomsStatistics").then(res => {
    if (res.code === 200 && res.data && res.data.length > 0) {
      data.availableRoomsCount = JSON.parse(res.data[0].availableRooms)
    } else {
      ElMessage.error(res.msg || '获取房间统计失败')
      data.availableRoomsCount = null
    }
  }).catch(err => {
    ElMessage.error('获取房间统计失败：' + (err.message || '网络错误'))
    data.availableRoomsCount = null
  })
}

/**
 * 获取可用房间列表
 * 用于订单处理时选择房间
 */
const getAvailableRooms = () => {
  request.get("/getAvailableRooms").then(res => {
    if (res.code === 200) {
      data.availableRooms = res.data
    } else {
      ElMessage.error(res.msg || '获取可用房间失败')
      data.availableRooms = []
    }
  }).catch(err => {
    ElMessage.error('获取可用房间失败：' + (err.message || '网络错误'))
    data.availableRooms = []
  })
}

/**
 * 组件挂载时初始化数据
 * 1. 确保用户角色已加载
 * 2. 根据角色获取订单列表
 * 3. 获取房间相关信息
 */
onMounted(() => {
  // 确保用户角色已加载
  if (!userStore.userRole) {
    userStore.getUserRole()
  }
  getOrderList()
  getVetOrderList()
  getRoomsStatistics()
  getAvailableRooms()
  // 延迟一点时间确保角色信息加载完成
  setTimeout(() => {
    getOrderByRole()
  }, 60)
})

// 详情弹窗控制
const dialogVisible = ref(false)

// 处理弹窗控制
const dialogVisibleDisposeFoster = ref(false)
const dialogVisibleDisposeVet = ref(false)

/**
 * 处理订单详情按钮点击事件
 * @param {Object} row - 当前订单数据
 */
const handleDetail = (row) => {
  dialogVisible.value = true
  data.OrderDetail = row
  console.log(data.OrderDetail)
}

/**
 * 处理订单处理按钮点击事件
 * @param {Object} row - 当前订单数据
 */
const handleDispose = (row) => {
  data.fosterOrderDisposeForm.orderId = row.id
  if (row.serviceType === 'foster') {
    getRoomsStatistics()
    getAvailableRooms()
    dialogVisibleDisposeFoster.value = true
    updateOrderStatus(data.fosterOrderDisposeForm.orderId, 'processing')
    data.handleDisposeRowData = row
  } else if (row.serviceType === 'medical') {
    updateOrderStatus(data.fosterOrderDisposeForm.orderId, 'processing')
    dialogVisibleDisposeVet.value = true
    data.handleDisposeRowData = row
  }
}

/**
 * 选择房间
 * @param {Object} row - 当前房间数据
 */
const selectRoomBtnText = ref('选择房间')
const selectRoom = (row) => {
  data.fosterOrderDisposeForm.roomId = row.roomId
  selectRoomBtnText.value = '已选'
}

/**
 * 寄养订单处理确认
 * 1. 创建寄养记录
 * 2. 更新房间状态为占用
 * 3. 更新订单状态为完成
 */
const onDispose = () => {
  // 校验必填字段
  if (!data.fosterOrderDisposeForm.roomId) {
    ElMessage.warning('请选择房间')
    return
  }
  if (!data.fosterOrderDisposeForm.checkInTime || !data.fosterOrderDisposeForm.checkOutTime) {
    ElMessage.warning('请选择入住和退房时间')
    return
  }

  request.post("/addFosterRecord", data.fosterOrderDisposeForm).then(res => {
    if (res.code === 200) {
      ElMessage.success('订单已处理')
      updateRoomStatus(data.fosterOrderDisposeForm.roomId, 'occupied')
      updateOrderStatus(data.fosterOrderDisposeForm.orderId, 'completed')
      dialogVisibleDisposeFoster.value = false
    } else {
      ElMessage.error(res.msg || '订单处理失败')
    }
  }).catch(err => {
    ElMessage.error('订单处理失败：' + (err.message || '网络错误'))
  })
}

/**
 * 医疗订单必填内容
 */
const vetRules = {
  symptoms: [
    {required: true, message: '请填写症状描述', trigger: 'change'}
  ],
  diagnosis: [
    {required: true, message: '请填写诊断结果', trigger: 'change'}
  ],
  treatment: [
    {required: true, message: '请填写治疗方案', trigger: 'blur'}
  ]
}

/**
 * 医疗订单处理确认
 * 1. 创建医疗记录
 * 2. 更新订单状态为完成
 */
const onDisposeVet = () => {
  data.vetOrderDisposeForm.orderId = data.handleDisposeRowData.id

  request.post("/addMedicalRecord", data.vetOrderDisposeForm).then(res => {
    if (res.code === 200) {
      ElMessage.success(res.message)
      dialogVisibleDisposeVet.value = false
      getOrderByRole()
    } else {
      ElMessage.error(res.message || '订单处理失败')
    }
  }).catch(err => {
    ElMessage.error('订单处理失败：' + (err.message || '网络错误'))
  })
}

/**
 * 更新订单状态
 * @param {Number} orderId - 订单ID
 * @param {String} status - 新状态
 */
const updateOrderStatus = (orderId, status) => {
  request.put(`/sysOrder/updateSysOrder/${orderId}`, {status: status}).then(res => {
    if (res.code === 200) {
      getOrderByRole()
    } else {
      ElMessage.error(res.msg || '更新订单状态失败')
    }
  }).catch(err => {
    ElMessage.error('更新订单状态失败：' + (err.message || '网络错误'))
  })
}

/**
 * 更新房间状态
 * @param {Number} roomId - 房间ID
 * @param {String} status - 新状态
 */
const updateRoomStatus = (roomId, status) => {
  request.put(`/updateRoomsStatus/${roomId}`, {status: status}).then(res => {
    if (res.code === 200) {
      getAvailableRooms()
    } else {
      ElMessage.error(res.msg || '更新房间状态失败')
    }
  }).catch(err => {
    ElMessage.error('更新房间状态失败：' + (err.message || '网络错误'))
  })
}

/**
 * 将订单状态代码转换为中文显示
 * @param {String} status - 订单状态代码
 * @returns {String} 中文状态描述
 */
const getOrderStatusText = (status) => {
  const statusMap = {
    'pending': '待确认',
    'confirmed': '已确认',
    'processing': '处理中',
    'completed': '已完成',
    'cancelled': '已取消'
  }
  return statusMap[status] || '未知状态'
}

/**
 * 将支付方式代码转换为中文显示
 * @param {String} method - 支付方式代码
 * @returns {String} 中文支付方式描述
 */
const getPaymentMethodText = (method) => {
  switch (method) {
    case 'wechat':
      return '微信支付';
    case 'alipay':
      return '支付宝';
    case 'cash':
      return '现金支付';
    default:
      return '未知';
  }
}

/**
 * 将服务类型代码转换为中文显示
 * @param {String} type - 服务类型代码
 * @returns {String} 中文服务类型描述
 */
const getServiceTypeText = (type) => {
  switch (type) {
    case 'foster':
      return '寄养';
    case 'medical':
      return '医疗';
    default:
      return '未知';
  }
}

/**
 * 获取订单状态类型
 * @param {String} status - 订单状态
 * @returns {String} Element Plus 标签类型
 */
const getOrderStatusType = (status) => {
  const statusMap = {
    'pending': 'info',    // 待确认
    'confirmed': 'primary',      // 已确认
    'processing': 'warning',     // 处理中
    'completed': 'success', // 已完成
    'cancelled': 'danger'   // 已取消
  }
  return statusMap[status] || 'info'
}

/**
 * 获取服务类型标签类型
 * @param {String} type - 服务类型
 * @returns {String} Element Plus 标签类型
 */
const getServiceTypeTag = (type) => {
  const typeMap = {
    'foster': 'warning',
    'medical': 'success'
  }
  return typeMap[type] || 'info'
}

/**
 * 格式化日期时间
 * @param {String} dateString - 日期时间字符串
 * @returns {String} 格式化后的日期时间
 */
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

/**
 * 格式化支付状态
 * @param {Number} status - 支付状态代码
 * @returns {String} 中文支付状态描述
 */
const getPaymentStatusText = (status) => {
  const statusMap = {
    0: '未支付',
    1: '已支付'
  }
  return statusMap[status] || '未知状态'
}

/**
 * 获取支付状态类型
 * @param {Number} status - 支付状态代码
 * @returns {String} Element Plus 标签类型
 */
const getPaymentStatusType = (status) => {
  const statusMap = {
    0: 'info',
    1: 'success'
  }
  return statusMap[status] || '未知状态'
}

/**
 * 重置寄养订单处理表单
 */
const resetFosterForm = () => {
  data.fosterOrderDisposeForm = {
    orderId: '',
    roomId: '',
    checkInTime: '',
    checkOutTime: ''
  }
}

/**
 * 重置医疗订单处理表单
 */
const resetVetForm = () => {
  // 可根据需要重置医疗订单相关数据
  data.vetOrderDisposeForm = {
    orderId: '',
    symptoms: '',
    diagnosis: '',
    treatment: '',
    prescription: '',
    nextVisitDate: '',
  }
}

/*
 * 轮询查询订单
 */
setInterval(() => {
  getOrderByRole()
}, 60000 * 5)
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

/* 处理部分样式 */
.dispose-container {
  padding: 20px;
}

.info-card {
  margin-bottom: 20px;
  border-radius: 8px;
}

.card-header {
  border-bottom: 1px solid #ebeef5;
  padding-bottom: 15px;
  margin-bottom: 15px;
}

.card-header h3 {
  margin: 0;
  color: #303133;
  font-size: 16px;
  font-weight: 600;
}

.info-item {
  display: flex;
  margin-bottom: 10px;
}

.info-label {
  font-weight: 500;
  color: #606266;
  width: 100px;
  flex-shrink: 0;
}

.info-value {
  color: #303133;
}

.rooms-section {
  margin-top: 15px;
}

.no-data {
  text-align: center;
  color: #909399;
  padding: 20px;
}

.time-selection {
  width: 100%;
}

.time-item {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.time-label {
  font-weight: 500;
  color: #606266;
  width: 80px;
  margin-right: 10px;
  flex-shrink: 0;
}

.allocation-section {
  padding: 10px 0;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>
