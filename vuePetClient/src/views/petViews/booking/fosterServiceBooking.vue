<!--服务预约-->
<template>
  <div>
    <!-- 顶部导航栏 -->
    <navBarTop title="预定">
      <template #left>
        <van-icon name="arrow-left" @click="router.go(-1)"/>
      </template>
    </navBarTop>

    <!-- 主要内容 -->
    <div>
      <!-- 服务信息 -->
      <div>
        <LImgRTextCard
            v-if="data.serviceDetailData"
            :serviceItemData="data.serviceDetailData"
            :serviceItemImgUrl="data.serviceDetailData.imageUrl"
        />
        <van-loading v-else color="#007aff" size="24px" vertical>
          加载中...
        </van-loading>
      </div>

      <!-- 预约时间选择 -->
      <div>
        <!-- 日期选择 -->
        <div>
          <van-cell title="预约日期" :value="dateDisplay" @click="showCalendar = true" is-link/>
          <van-calendar
              v-model:show="showCalendar"
              type="range"
              :max-range="30"
              @confirm="onDateConfirm"
              :round="true"
          />
        </div>

        <!-- 时间选择 -->
        <div v-if="dateRange.start && dateRange.end">
          <van-cell-group>
            <van-cell
                title="开始时间"
                :value="formatTimeDisplay(startTime)"
                @click="showStartTimePicker = true"
                is-link
            />
            <van-cell
                title="结束时间"
                :value="formatTimeDisplay(endTime)"
                @click="showEndTimePicker = true"
                is-link
            />
          </van-cell-group>

          <!-- 开始时间选择器 -->
          <van-popup v-model:show="showStartTimePicker" position="bottom">
            <van-time-picker
                v-model="startTime"
                title="选择开始时间"
                :columns-type="['hour', 'minute']"
                @confirm="onStartTimeConfirm"
                @cancel="showStartTimePicker = false"
            />
          </van-popup>

          <!-- 结束时间选择器 -->
          <van-popup v-model:show="showEndTimePicker" position="bottom">
            <van-time-picker
                v-model="endTime"
                title="选择结束时间"
                :columns-type="['hour', 'minute']"
                @confirm="onEndTimeConfirm"
                @cancel="showEndTimePicker = false"
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
      <div>
        <div>
          <van-field
              v-model="fieldValue"
              is-link
              readonly
              label="宠物"
              placeholder="请选择宠物"
              @click="showPicker = true"
              :rules="[{ required: true, message: '请选择宠物' }]"
          />
          <van-popup v-if="petColumns.length > 0" v-model:show="showPicker" round position="bottom">
            <van-picker
                :columns="petColumns"
                @cancel="showPicker = false"
                @confirm="onPetConfirm"
                :loading="petPickerLoading"
            />
          </van-popup>
          <van-popup v-else v-model:show="showPicker" round position="bottom">
            <div>
              <p>暂无宠物数据</p>
              <van-button round type="primary" @click="toAddPet">先添加宠物</van-button>
            </div>
          </van-popup>
        </div>
      </div>

      <!-- 订单金额 -->
      <div>
        <van-cell-group>
          <van-cell center title="订单金额">
            <template #value>
              <van-icon name="gold-coin"/>
              {{ data.serviceDetailData?.price || 0 }}
            </template>
          </van-cell>
        </van-cell-group>
      </div>

      <!-- 用户备注 -->
      <div>
        <van-cell-group inset style="width: 100%; margin: 0; border-radius: 0">
          <van-field
              v-model="remark"
              rows="2"
              autosize
              label="备注"
              type="textarea"
              maxlength="50"
              placeholder="请输入备注"
              show-word-limit
          />
        </van-cell-group>
      </div>
    </div>

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
            <div class="amount-value">￥{{ data.serviceDetailData?.price || '0.00' }}</div>
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
              确认支付 ￥{{ data.serviceDetailData?.price || '0.00' }}
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
</template>

<script setup>
import {useRoute} from "vue-router";
import serviceItemStores from "@/stores/serviceItemStores.js";
import {computed, onMounted, reactive, ref, watch} from "vue";
import router from "@/router/index.js";
import navBarTop from "@/components/navBarTop.vue";
import LImgRTextCard from "@/components/LImgRTextCard.vue";
import petsInfoStores from "@/stores/petsInfoStores.js";
import {showToast} from "vant";
import request from "@/utils/request.js";

// 基础数据
const serviceDetailId = useRoute().params.id;
const serviceItem = serviceItemStores();
const petsInfoStoresData = petsInfoStores();

// 响应式数据
const data = reactive({
  serviceDetailData: null,
  petsData: null,
  formOrderData: {
    petId: '',
    serviceId: '',
    serviceType: '',
    appointmentStartTime: '',
    appointmentEndTime: '',
    amount: '',
    paymentStatus: '',
    paymentMethod: '',
    remark: ''
  }
});

// 预约时间相关
const showCalendar = ref(false);
const showStartTimePicker = ref(false);
const showEndTimePicker = ref(false);

const dateRange = reactive({
  start: null,
  end: null
});

const startTime = ref(['09', '00']);
const endTime = ref(['17', '00']);

// 宠物选择相关
const showPicker = ref(false);
const fieldValue = ref('');
const petPickerLoading = ref(false);
const selectedPetId = ref(null);

const toAddPet = () => {
  router.push('/petsProfileAdd')
  petPickerLoading.value = false
};

// 支付相关
const paying = ref(false);
const selectedPaymentMethod = ref(null);

// 备注
const remark = ref('');

// 计算属性
const dateDisplay = computed(() => {
  if (dateRange.start && dateRange.end) {
    return `${formatDate(dateRange.start)} 至 ${formatDate(dateRange.end)}`;
  } else if (dateRange.start) {
    return `从 ${formatDate(dateRange.start)} 开始`;
  }
  return '请选择预约日期';
});

const fullAppointmentTime = computed(() => {
  if (dateRange.start && dateRange.end) {
    const start = `${formatDate(dateRange.start)} ${formatTimeDisplay(startTime.value)}`;
    const end = `${formatDate(dateRange.end)} ${formatTimeDisplay(endTime.value)}`;
    return `${start} 至 ${end}`;
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

// 生命周期钩子
onMounted(async () => {
  await serviceItem.fetchServiceDetailData(serviceDetailId);
  data.serviceDetailData = serviceItem.getServiceDetail;

  await petsInfoStoresData.getPetsInfoData();
});

// 监听器
watch(() => petsInfoStoresData.petsInfoData, (newValue) => {
  data.petsData = newValue || [];

  if (newValue && newValue.length > 0 && !fieldValue.value && !selectedPetId.value) {
    const firstPet = newValue[0];
    fieldValue.value = firstPet.name || firstPet.petName || `宠物${firstPet.id}`;
    selectedPetId.value = firstPet.id;
    data.formOrderData.petId = firstPet.id;
  }
});

// 方法定义
// 日期时间相关方法
const formatDate = (date) => {
  if (!date) return '';
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
};

const formatTimeDisplay = (timeArray) => {
  if (!timeArray || timeArray.length < 2) return '00:00';
  return `${timeArray[0]}:${timeArray[1]}`;
};

const onDateConfirm = (values) => {
  const [start, end] = values;
  dateRange.start = start;
  dateRange.end = end;
  showCalendar.value = false;
  updateFormOrderTimes();
};

const onStartTimeConfirm = (value) => {
  startTime.value = value.selectedValues;
  showStartTimePicker.value = false;
  updateFormOrderTimes();
};

const onEndTimeConfirm = (value) => {
  endTime.value = value.selectedValues;
  showEndTimePicker.value = false;
  updateFormOrderTimes();
};

const updateFormOrderTimes = () => {
  if (dateRange.start && dateRange.end) {
    const startYear = dateRange.start.getFullYear();
    const startMonth = String(dateRange.start.getMonth() + 1).padStart(2, '0');
    const startDay = String(dateRange.start.getDate()).padStart(2, '0');
    const startHour = startTime.value[0] || '00';
    const startMinute = startTime.value[1] || '00';
    data.formOrderData.appointmentStartTime = `${startYear}-${startMonth}-${startDay} ${startHour}:${startMinute}:00`;

    const endYear = dateRange.end.getFullYear();
    const endMonth = String(dateRange.end.getMonth() + 1).padStart(2, '0');
    const endDay = String(dateRange.end.getDate()).padStart(2, '0');
    const endHour = endTime.value[0] || '00';
    const endMinute = endTime.value[1] || '00';
    data.formOrderData.appointmentEndTime = `${endYear}-${endMonth}-${endDay} ${endHour}:${endMinute}:00`;
  }
};

// 宠物选择相关方法
const onPetConfirm = ({selectedOptions}) => {
  if (selectedOptions && selectedOptions.length > 0) {
    fieldValue.value = selectedOptions[0].text;
    selectedPetId.value = selectedOptions[0].value;
    data.formOrderData.petId = selectedOptions[0].value;
  }
  showPicker.value = false;
};

// 订单数据相关方法
const setFormOrderData = () => {
  data.formOrderData.serviceId = serviceDetailId;
  data.formOrderData.serviceType = data.serviceDetailData.serviceType;
  data.formOrderData.petId = selectedPetId.value;
  data.formOrderData.amount = data.serviceDetailData?.price || '0.00';
  data.formOrderData.paymentStatus = 0;
  data.formOrderData.paymentMethod = null;
  data.formOrderData.remark = remark.value;
};

// 支付相关方法
const handlePayment = async () => {
  if (!selectedPetId.value) {
    showToast('请选择宠物');
    return;
  }

  if (!dateRange.start || !dateRange.end) {
    showToast('请选择预约日期');
    return;
  }

  if (!startTime.value || !endTime.value) {
    showToast('请选择预约时间');
    return;
  }

  const startDateTime = new Date(`${formatDate(dateRange.start)} ${formatTimeDisplay(startTime.value)}`);
  const endDateTime = new Date(`${formatDate(dateRange.end)} ${formatTimeDisplay(endTime.value)}`);

  if (endDateTime <= startDateTime) {
    showToast('结束时间必须晚于开始时间');
    return;
  }

  paying.value = true;
  setFormOrderData();
};

const onClickOverlay = () => {
  paying.value = false;
  createOrderPost(data.formOrderData);
  showToast('订单创建成功,请尽快支付');
  router.go(-1);
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

const handleWechatPayment = async () => {
  data.formOrderData.paymentMethod = 'wechat';
  data.formOrderData.paymentStatus = 1;
  createOrderPost(data.formOrderData);
};

const handleAlipayPayment = async () => {
  data.formOrderData.paymentMethod = 'alipay';
  data.formOrderData.paymentStatus = 1;
  createOrderPost(data.formOrderData);
};

const handleCashPayment = async () => {
  data.formOrderData.paymentMethod = 'cash';
  data.formOrderData.paymentStatus = 1;
  createOrderPost(data.formOrderData);
  await router.push('/orderSuccess');
};

// 订单创建接口
const createOrderPost = (formOrderData) => {
  // 清理数据，避免传递 undefined 值
  const cleanData = {};
  Object.keys(formOrderData).forEach(key => {
    if (formOrderData[key] !== undefined && formOrderData[key] !== null) {
      cleanData[key] = formOrderData[key];
    }
  });

  request.post('/sysOrder/createSysOrder', cleanData)
      .then(res => {
        if (res.code === 200) {
          router.go(-1);
        }
      })
      .catch(error => {
        console.error(error);
      });
};
</script>

<style scoped>
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
