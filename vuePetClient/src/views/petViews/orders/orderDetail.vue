<!--订单详情查看页-->
<template>
  <div class="order-detail-container">
    <!-- 头部导航 -->
    <div>
      <navBarTop title="订单详情">
        <template #left>
          <div>
            <van-icon name="arrow-left" @click="()=>{router.go(-1)}"/>
          </div>
        </template>
      </navBarTop>
    </div>

    <!-- 页面内容 -->
    <div class="content">
      <van-loading v-if="data.loading" vertical>加载中...</van-loading>

      <div v-else-if="data.orderDetail">
        <!-- 订单基本信息 -->
        <van-cell-group title="订单信息">
          <van-cell title="订单编号" :value="data.orderDetail.orderNo"/>
          <van-cell title="订单状态" :value="formatOrderStatus(data.orderDetail.status)"/>
          <van-cell title="支付状态" :value="formatPaymentStatus(data.orderDetail.paymentStatus)"/>
          <van-cell title="订单金额" :value="`¥${data.orderDetail.amount}`"/>
          <van-cell title="支付方式" :value="formatPaymentMethod(data.orderDetail.paymentMethod)"/>
          <van-cell
              title="支付时间"
              :value="formatDateTime(data.orderDetail.paymentTime)"
              v-if="data.orderDetail.paymentTime"
          />
          <van-cell title="创建时间" :value="formatDateTime(data.orderDetail.createTime)"/>
          <van-cell title="更新时间" :value="formatDateTime(data.orderDetail.updateTime)"/>
        </van-cell-group>

        <!-- 时间信息 -->
        <van-cell-group title="时间信息">
          <van-cell
              title="预约开始时间"
              :value="formatDateTime(data.orderDetail.appointmentStartTime)"
          />
          <van-cell
              title="预约结束时间"
              :value="formatDateTime(data.orderDetail.appointmentEndTime)"
              v-if="data.orderDetail.appointmentEndTime"
          />
          <van-cell
              title="实际开始时间"
              :value="formatDateTime(data.orderDetail.actualStartTime)"
              v-if="data.orderDetail.actualStartTime"
          />
          <van-cell
              title="实际结束时间"
              :value="formatDateTime(data.orderDetail.actualEndTime)"
              v-if="data.orderDetail.actualEndTime"
          />
        </van-cell-group>

        <!-- 用户信息 -->
        <van-cell-group title="用户信息">

          <van-cell title="账号" :value="data.orderDetail.username"/>
          <van-cell title="名称" :value="data.orderDetail.realName"/>
          <van-cell title="手机号" :value="data.orderDetail.phone"/>

          <van-cell title="头像">
            <van-image
                round
                width="40px"
                height="40px"
                :src="data.orderDetail.userAvatar || '/img/default-avatar.png'"
                v-if="data.orderDetail.userAvatar"
            />
            <span v-else>无头像</span>
          </van-cell>
        </van-cell-group>

        <!-- 宠物信息 -->
        <van-cell-group title="宠物信息">
          <van-cell title="宠物名称" :value="data.orderDetail.petName"/>
          <van-cell title="宠物类型" :value="data.orderDetail.petType"/>
          <van-cell title="宠物品种" :value="data.orderDetail.petBreed"/>
          <van-cell title="宠物年龄" :value="`${data.orderDetail.petAge}岁`"/>
          <van-cell title="宠物性别" :value="formatPetGender(data.orderDetail.petGender)"/>
          <van-cell title="宠物体重" :value="`${data.orderDetail.petWeight}kg`"/>
          <van-cell title="宠物头像">
            <van-image
                round
                width="40px"
                height="40px"
                :src="data.orderDetail.petImage || '/img/default-pet.png'"
                v-if="data.orderDetail.petImage"
            />
            <span v-else>无图片</span>
          </van-cell>
        </van-cell-group>

        <!-- 服务信息 -->
        <van-cell-group title="服务信息">
          <van-cell title="服务名称" :value="data.orderDetail.serviceName"/>
          <van-cell title="服务分类" :value="data.orderDetail.serviceCategory"/>
          <van-cell title="服务类型" :value="formatServiceType(data.orderDetail.serviceType)"/>
          <van-cell
              title="医疗分类"
              :value="formatMedicalType(data.orderDetail.medicalType)"
              v-if="data.orderDetail.medicalType"
          />
          <van-cell title="预计时长" :value="`${data.orderDetail.serviceDuration}分钟`"/>
          <van-cell title="服务价格" :value="`¥${data.orderDetail.servicePrice}`"/>
          <van-cell title="服务图片">
            <van-image
                width="60px"
                height="60px"
                :src="data.orderDetail.serviceImage || '/img/default-service.png'"
                v-if="data.orderDetail.serviceImage"
            />
            <span v-else>无图片</span>
          </van-cell>
        </van-cell-group>

        <!-- 服务描述 -->
        <van-cell-group title="服务描述">
          <van-cell :value="data.orderDetail.serviceDescription || '暂无描述'"/>
        </van-cell-group>

        <!-- 用户备注 -->
        <van-cell-group title="用户备注" v-if="data.orderDetail.remark && data.orderDetail.remark !== ''">
          <van-cell :value="data.orderDetail.remark"/>
        </van-cell-group>


        <!--    支付按钮-->
        <div v-if="data.orderDetail.paymentStatus===0">

          <!-- 底部支付按钮 -->
          <div>
            <!-- 支付方式选择弹窗 -->
            <van-popup
                @click-overlay="onClickOverlay"
                v-model:show="paying"
                round
                position="bottom"
                :style="{ height: 'auto' }"
                class="payment-popup"
            >
              <div class="payment-container">
                <!-- 弹窗头部 -->
                <div class="payment-header">
                  <div class="header-title">选择支付方式</div>
                  <van-icon name="cross" class="close-icon" @click="onClickOverlay"/>
                </div>

                <!-- 支付金额 -->
                <div class="payment-amount">
                  <div class="amount-label">支付金额</div>
                  <div class="amount-value">￥{{ data.orderDetail?.amount || '0.00' }}</div>
                </div>

                <!-- 支付方式列表 -->
                <div class="payment-methods">
                  <div
                      class="payment-item"
                      v-for="method in paymentMethods"
                      :key="method.id"
                      @click="selectPaymentMethod(method)"
                      :class="{ active: selectedPaymentMethod?.id === method.id }"
                  >
                    <div class="payment-icon">
                      <van-icon :name="method.icon" :color="method.color" size="24"/>
                    </div>
                    <div class="payment-info">
                      <div class="payment-name">{{ method.name }}</div>
                      <div class="payment-desc">{{ method.description }}</div>
                    </div>
                    <div class="payment-check">
                      <van-icon
                          name="success"
                          v-if="selectedPaymentMethod?.id === method.id"
                          color="#6742c4"
                          size="16"
                      />
                    </div>
                  </div>
                </div>

                <!-- 确认支付按钮 -->
                <div class="payment-footer">
                  <van-button
                      type="primary"
                      block
                      round
                      @click="confirmPayment"
                      :disabled="!selectedPaymentMethod"
                      class="confirm-button"
                  >
                    确认支付 ￥{{ data.orderDetail?.amount || '0.00' }}
                  </van-button>
                </div>
              </div>
            </van-popup>

            <van-action-bar style="z-index: 1; height: 2.5rem">
              <van-action-bar-button
                  type="primary"
                  text="立即支付"
                  @click="handlePayment"
              />
            </van-action-bar>
          </div>
        </div>
      </div>

      <van-empty v-else description="未找到订单信息"/>
    </div>
  </div>
</template>

<script setup>
import router from "@/router/index.js";
import navBarTop from "@/components/navBarTop.vue";
import {useRoute} from "vue-router";
import {onMounted, reactive, ref} from "vue";
import request from "@/utils/request.js";
import {showToast} from "vant";

const route = useRoute();
const orderId = route.params.id;

const data = reactive({
  orderDetail: null,
  loading: true
});

onMounted(() => {
  if (orderId != null) {
    getOrderDetail();
  }
});

const getOrderDetail = async () => {
  try {
    data.loading = true;
    const response = await request.get(`/sysOrder/getSysOrderById/${orderId}`);
    if (response.code === 200) {
      data.orderDetail = response.data;
    }
  } catch (err) {
    console.error('获取订单详情失败:', err);
  } finally {
    data.loading = false;
  }
};

// 格式化订单状态
const formatOrderStatus = (status) => {
  const statusMap = {
    'pending': '待确认',
    'confirmed': '已确认',
    'processing': '处理中',
    'completed': '已完成',
    'canceled': '已取消'
  };
  return statusMap[status] || status || '未知';
};

// 格式化支付状态
const formatPaymentStatus = (status) => {
  const paymentMap = {
    0: '未支付',
    1: '已支付',
    2: '已退款'
  };
  return paymentMap[status] || '未知';
};

// 格式化支付方式
const formatPaymentMethod = (method) => {
  const methodMap = {
    'wechat': '微信支付',
    'alipay': '支付宝',
    'cash': '现金支付'
  };
  return methodMap[method] || method || '未知';
};


// 格式化宠物性别
const formatPetGender = (gender) => {
  const genderMap = {
    'male': '雄性',
    'female': '雌性',
    'unknown': '未知'
  };
  return genderMap[gender] || gender || '未知';
};

// 格式化服务类型
const formatServiceType = (type) => {
  const typeMap = {
    'foster': '寄养服务',
    'medical': '医疗服务',
    'grooming': '美容服务'
  };
  return typeMap[type] || type || '未知';
};

// 格式化医疗分类
const formatMedicalType = (type) => {
  const typeMap = {
    'vacation': '疫苗接种',
    'diagnosis': '疾病诊疗'
  };
  return typeMap[type] || type || '未知';
};

// 格式化日期时间
const formatDateTime = (dateStr) => {
  if (!dateStr) return '未设置';
  try {
    const date = new Date(dateStr);
    return date.toLocaleString('zh-CN', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit'
    });
  } catch (e) {
    return dateStr;
  }
};

// 支付按钮相关
const paying = ref(false)
const selectedPaymentMethod = ref(null);
// 支付方式数据
const paymentMethods = [
  {
    id: 1,
    name: '微信支付',
    icon: 'wechat',
    color: '#07c160',
    description: '推荐使用微信支付'
  },
  {
    id: 2,
    name: '支付宝',
    icon: 'alipay',
    color: '#1677ff',
    description: '支持花呗分期'
  },
  {
    id: 3,
    name: '到店支付',
    icon: 'cash-on-deliver',
    color: '#ff6b00',
    description: '到店后现金支付'
  }
];

const onClickOverlay = () => {
  paying.value = false;
};
const handlePayment = () => {
  paying.value = true
  console.log(paying.value)
};

const selectPaymentMethod = (method) => {
  selectedPaymentMethod.value = method;
};

const confirmPayment = async () => {
  if (!selectedPaymentMethod.value) {
    showToast('请选择支付方式');
    return;
  }

  paying.value = false;

  switch (selectedPaymentMethod.value.id) {
    case 1:
      await handleWechatPayment();
      break;
    case 2:
      await handleAlipayPayment();
      break;
    case 3:
      await handleCashPayment();
      break;
  }
};

const updateSysOrder = (PaymentMethod) => {
  console.log("订单id:" + data.orderDetail.id)
  request.put(`/sysOrder/updateSysOrder/${data.orderDetail.id}`, {
    "paymentStatus": 1,
    "status": "confirmed",
    "paymentMethod":PaymentMethod
    // 不需要传递ID，由后端从路径参数中获取
  }).then(response => {
    console.log("更新订单成功:", response)
    if (response.code === 200) {
      // 更新成功后刷新订单详情
      getOrderDetail();
    }
  }).catch(error => {
    console.error("更新订单失败:", error)
  })
}



const handleWechatPayment = async () => {
  // 模拟微信支付
  updateSysOrder("wechat")
  await new Promise((resolve) => setTimeout(resolve, 1000));
  showToast('微信支付成功');
  router.go(-1);
};

const handleAlipayPayment = async () => {
  // 模拟支付宝支付
  updateSysOrder("alipay")
  await new Promise((resolve) => setTimeout(resolve, 1000));
  showToast('支付宝支付成功');
  router.go(-1);
};

const handleCashPayment = async () => {
  // 模拟现金支付
  updateSysOrder("cash")
  await new Promise((resolve) => setTimeout(resolve, 1000));
  showToast('现金支付成功');
  router.go(-1);
};


</script>

<style scoped>
.order-detail-container {
  min-height: 100vh;
  background-color: var(--van-background-2, #ffffff);
}

.content {
  padding-bottom: 3rem;
}


.payment-popup {
  max-height: 80%;
}

.payment-container {
  padding: 20px 16px 16px;
  display: flex;
  flex-direction: column;
  height: 100%;
}

.payment-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.header-title {
  font-size: 18px;
  font-weight: 600;
  color: #333;
}

.close-icon {
  font-size: 20px;
  color: #999;
  padding: 4px;
}

.payment-amount {
  text-align: center;
  margin-bottom: 24px;
  padding: 16px;
  background: linear-gradient(135deg, #f5f7fa 0%, #e4e7f4 100%);
  border-radius: 12px;
}

.amount-label {
  font-size: 14px;
  color: #666;
  margin-bottom: 4px;
}

.amount-value {
  font-size: 24px;
  font-weight: 700;
  color: #6742c4;
}

.payment-methods {
  flex: 1;
  overflow-y: auto;
  margin-bottom: 20px;
}

.payment-item {
  display: flex;
  align-items: center;
  padding: 16px;
  background: #fff;
  border-radius: 12px;
  margin-bottom: 12px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
  cursor: pointer;
}

.payment-icon {
  margin-right: 12px;
}

.payment-info {
  flex: 1;
}

.payment-name {
  font-size: 16px;
  font-weight: 500;
  color: #333;
  margin-bottom: 2px;
}

.payment-desc {
  font-size: 12px;
  color: #999;
}

.payment-check {
  width: 20px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.payment-footer {
  padding-top: 16px;
  border-top: 1px solid #f0f0f0;
}

.confirm-button {
  height: 48px;
  font-size: 16px;
  font-weight: 600;
}

.confirm-button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.full-time-display {
  margin-top: 10px;
}

:deep(.van-calendar) {
  background: #fff;
}

:deep(.van-time-picker) {
  background: #fff;
}

:deep(.van-popup) {
  background: #fff;
}

/* 响应式优化 */
@media (max-width: 768px) {
  .payment-container {
    padding: 16px 12px 12px;
  }

  .header-title {
    font-size: 16px;
  }

  .payment-item {
    padding: 12px;
  }

  .payment-name {
    font-size: 15px;
  }

  .amount-value {
    font-size: 20px;
  }
}
</style>
