<!--医生预约-->
<template>
  <div class="vet-booking-container">
    <!-- 顶部导航栏 -->
    <navBarTop title="预约医生">
      <template #left>
        <van-icon name="arrow-left" @click="goBack"/>
      </template>
    </navBarTop>

    <!-- 主要内容 -->
    <div class="content">
      <!-- 预约时间选择 -->
      <div class="time-selection-section">
        <h3 class="section-title">预约时间</h3>
        <!-- 日期选择 -->
        <div>
          <van-cell title="预约日期" :value="dateDisplay" @click="showCalendar = true"  is-link/>
          <van-calendar style="z-index: 30000;padding-bottom: 100px"
              v-model:show="showCalendar"
              :min-date="minDate"
              :max-date="maxDate"
              @confirm="onDateConfirm"
              :round="true"
          />
        </div>

        <!-- 时间选择 -->
        <div v-if="selectedDate">
          <van-cell-group>
            <van-cell
                title="预约时间"
                :value="formatTimeDisplay(selectedTime)"
                @click="showTimePicker = true"
                is-link
            />
          </van-cell-group>

          <!-- 时间选择器 -->
          <van-popup v-model:show="showTimePicker" position="bottom">
            <van-time-picker
                v-model="selectedTime"
                title="选择预约时间"
                :columns-type="['hour', 'minute']"
                @confirm="onTimeConfirm"
                @cancel="showTimePicker = false"
                :formatter="timeFormatter"
            />
          </van-popup>
        </div>

        <!-- 完整预约时间显示 -->
        <div v-if="fullAppointmentTime" class="full-time-display">
          <van-cell-group>
            <van-cell title="预约时间" :label="fullAppointmentTime"/>
          </van-cell-group>
        </div>
      </div>

      <!-- 宠物选择 -->
      <div class="pet-selection-section">
        <h3 class="section-title">选择宠物</h3>
        <van-field
            v-model="fieldValue"
            is-link
            readonly
            label="宠物"
            placeholder="请选择宠物"
            @click="showPicker = true"
            :rules="[{ required: true, message: '请选择宠物' }]"
        />
        <van-popup v-model:show="showPicker" round position="bottom">
          <van-picker
              :columns="petColumns"
              @cancel="showPicker = false"
              @confirm="onPetConfirm"
              :loading="petPickerLoading"
          />
        </van-popup>
      </div>

      <!-- 订单金额 -->
      <div>
        <van-cell-group>
          <van-cell center title="订单金额">
            <template #value>
              <van-icon name="gold-coin"/>
              {{ price }}
            </template>
          </van-cell>
        </van-cell-group>
      </div>
    </div>

    <!-- 底部支付按钮 -->
    <div>
      <van-action-bar style="z-index: 1;height: 2.5rem">
        <van-action-bar-button
            type="primary"
            text="立即支付"
            @click="handlePayment"
        />
      </van-action-bar>
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
            <div class="amount-value">￥{{ price || '0.00' }}</div>
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
              确认支付 ￥{{ price || '0.00' }}
            </van-button>
          </div>
        </div>
      </van-popup>
    </div>
  </div>
</template>

<script setup>
import {useRoute, useRouter} from "vue-router";
import {computed, onMounted, reactive, ref, watch} from "vue";
import navBarTop from "@/components/navBarTop.vue";
import petsInfoStores from "@/stores/petsInfoStores.js";
import {showToast} from "vant";
import request from "@/utils/request.js";

// 路由和存储
const route = useRoute();
const router = useRouter();
const petsStore = petsInfoStores();

// 基础数据
const vetId = route.params.id;
console.log(vetId)

// 响应式数据
const data = reactive({
  petsData: [],
  formBookingData: {
    petId: '',
    employeeId: '',
    appointmentStartTime: '',
    price: '',
  }
});


// 预约时间相关
const showCalendar = ref(false);
const showTimePicker = ref(false);
const selectedDate = ref(null);
const selectedTime = ref(['09', '00']);

// 宠物选择相关
const showPicker = ref(false);
const fieldValue = ref('');
const petPickerLoading = ref(false);
const selectedPetId = ref(null);

// 费用
const price = ref(15)

// 日期限制
const minDate = new Date();
const maxDate = new Date(minDate);
maxDate.setMonth(maxDate.getMonth() + 3); // 限制预约时间在3个月内

// 计算属性
const dateDisplay = computed(() => {
  if (selectedDate.value) {
    return formatDate(selectedDate.value);
  }
  return '请选择预约日期';
});

const fullAppointmentTime = computed(() => {
  if (selectedDate.value) {
    return `${formatDate(selectedDate.value)} ${formatTimeDisplay(selectedTime.value)}`;
  }
  return '';
});

const petColumns = computed(() => {
  if (!data.petsData || data.petsData.length === 0) {
    return [];
  }

  return data.petsData.map(pet => ({
    text: pet.name || pet.petName || `宠物${pet.id}`,
    value: pet.id
  }));
});

// 生命周期钩子
onMounted(() => {
  fetchVetInfo();
  fetchPetsInfo();
});

// 方法定义
// 获取医生信息
const fetchVetInfo = () => {
  request.get("/sysUser/getSysUserById", {
    params: {
      id: vetId
    }
  }).then(res => {
    if (res.code === 200) {
      data.vetData = res.data;
    }
  }).catch(err => {
    console.error('获取医生信息失败:', err);
    showToast('获取医生信息失败');
  });
};

// 获取宠物信息
const fetchPetsInfo = async () => {
  try {
    petPickerLoading.value = true;
    await petsStore.getPetsInfoData();
    data.petsData = petsStore.petsInfoData || [];
  } catch (error) {
    console.error('获取宠物信息失败:', error);
    showToast('获取宠物信息失败');
  } finally {
    petPickerLoading.value = false;
  }
};

// 日期时间相关方法
/**
 * 格式化日期为 YYYY-MM-DD 字符串
 * @param {Date} date - 要格式化的日期对象
 * @returns {string} 格式化后的日期字符串，如果输入为空则返回空字符串
 */
const formatDate = (date) => {
  if (!date) return '';
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
};

/**
 * 格式化时间数组为 HH:MM 字符串
 * @param {Array} timeArray - 包含小时和分钟的数组
 * @returns {string} 格式化后的时间字符串，格式为 HH:MM，如果输入无效则返回 '00:00'
 */
const formatTimeDisplay = (timeArray) => {
  if (!timeArray || timeArray.length < 2) return '00:00';
  return `${timeArray[0]}:${timeArray[1]}`;
};

/**
 * 时间选择器格式化函数，用于限制可选时间范围
 * @param {string} type - 时间类型，如 'hour' 表示小时
 * @param {Object} option - 时间选项对象
 * @returns {Object} 处理后的时间选项对象
 */
const timeFormatter = (type, option) => {
  // 限制时间范围，工作时间 08:00 - 20:00
  if (type === 'hour') {
    const hour = parseInt(option.text);
    if (hour < 8 || hour > 20) {
      option.disabled = true;
    }
  }
  return option;
};

/**
 * 日期确认回调函数
 * @param {Date} date - 用户选择的日期
 */
const onDateConfirm = (date) => {
  selectedDate.value = date;
  showCalendar.value = false;
};

/**
 * 时间确认回调函数
 * @param {Object} value - 时间选择器的值对象
 */
const onTimeConfirm = (value) => {
  selectedTime.value = value.selectedValues;
  showTimePicker.value = false;
};


// 宠物选择相关方法
const onPetConfirm = ({selectedOptions}) => {
  if (selectedOptions && selectedOptions.length > 0) {
    fieldValue.value = selectedOptions[0].text;
    selectedPetId.value = selectedOptions[0].value;
  }
  showPicker.value = false;
};

// 表单数据设置
const setFormBookingData = () => {
  data.formBookingData.employeeId = vetId;
  data.formBookingData.petId = selectedPetId.value;
  data.formBookingData.appointmentStartTime = formatDate(selectedDate.value) + ' ' + formatTimeDisplay(selectedTime.value);
  data.formBookingData.price = price.value;
};

// 支付相关
const paying = ref(false);
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

const handlePayment = () => {
  if (selectedDate.value === null) {
    showToast('请选择预约日期');
    return;
  }
  if (!selectedTime.value) {
    showToast('请选择预约时间');
    return;
  }

  if (!selectedPetId.value) {
    showToast('请选择宠物');
    return;
  }


  setFormBookingData();
  paying.value = true;
}
// 支付相关方法
const onClickOverlay = () => {
  paying.value = false;
}

const selectPaymentMethod = (method) => {
  selectedPaymentMethod.value = method;
}

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
}

const handleWechatPayment = async () => {
  data.formBookingData.paymentMethod = 'wechat';
  data.formBookingData.paymentStatus = 1;
  createOrderPost(data.formBookingData);
}

const handleAlipayPayment = async () => {
  data.formBookingData.paymentMethod = 'alipay';
  data.formBookingData.paymentStatus = 1;
  createOrderPost(data.formBookingData);
}

const handleCashPayment = async () => {
  data.formBookingData.paymentMethod = 'cash';
  data.formBookingData.paymentStatus = 1;
  createOrderPost(data.formBookingData);
  await router.push('/orderSuccess');
}

// 订单创建接口
const createOrderPost = (formBookingData) => {

  console.log(formBookingData)
  // 清理数据，避免传递 undefined 值
  const cleanData = {};
  Object.keys(formBookingData).forEach(key => {
    if (formBookingData[key] !== undefined && formBookingData[key] !== null) {
      cleanData[key] = formBookingData[key];
    }
  });
  request.post('/sysOrder/createSysOrderForVet', cleanData)
      .then(res => {
        if (res.code === 200) {
          showToast('订单创建成功');
          router.go(-1);
        } else {
          showToast('订单创建失败');
        }
      })
      .catch(error => {
        console.error(error);
        showToast('订单创建失败');
      });
}


// 返回方法
const goBack = () => {
  if (window.history.length > 1) {
    router.go(-1);
  } else {
    router.push('/bookingHome');
  }
};

// 监听器
watch(() => petsStore.petsInfoData, (newValue) => {
  data.petsData = newValue || [];

  if (newValue && newValue.length > 0 && !fieldValue.value && !selectedPetId.value) {
    const firstPet = newValue[0];
    fieldValue.value = firstPet.name || firstPet.petName || `宠物${firstPet.id}`;
    selectedPetId.value = firstPet.id;
  }
});
</script>

<style scoped>
.vet-booking-container {
  min-height: 100vh;
  background-color: var(--van-background-2, #ffffff);
  display: flex;
  flex-direction: column;
  padding-bottom: 8vh;
}

.content {
  flex: 1;
  overflow-y: auto;
  padding: 16px;
  padding-bottom: 80px;
}

.section-title {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin: 20px 0 10px 0;
  padding-left: 10px;
  border-left: 4px solid #007aff;
}

.time-selection-section,
.pet-selection-section {
  z-index: 1;
  margin-top: 20px;
  background: white;
  border-radius: 12px;
  padding: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.full-time-display {
  margin-top: 10px;
}

.footer {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background: white;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.1);
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
  .content {
    padding: 12px;
  }

  .section-title {
    font-size: 15px;
  }
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

