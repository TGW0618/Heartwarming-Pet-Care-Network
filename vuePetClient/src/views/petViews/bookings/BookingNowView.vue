<!--立即预约确认订单页 -->
<template>
  <div style="height: 100vh;">
    <!--  头部导航开始-->
    <div>
      <navBarTop :title=null>
        <template #left>
          <van-icon name="arrow-left" @click="onLeftTop"/>
        </template>
      </navBarTop>
    </div>
    <!--  头部导航结束-->
    <!--      添加宠物-->
    <div class="add-pet-section">
      <div class="add-pet-prompt">
        <van-icon name="plus" class="plus-icon" @click="goToAddPet"/>
        <span class="prompt-text">还没有宠物档案？</span>
        <van-button
            round
            size="small"
            class="add-pet-btn"
            @click="goToAddPet"
        >
          立即添加
        </van-button>
      </div>
    </div>
    <div style="padding: 0.3rem 0.3rem 2rem;">
      <!--    订单详情开始-->
      <!--      选择的宠物-->
      <div style="margin-top: 0.1rem">
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
          <van-popup v-model:show="showPicker" round position="bottom">
            <van-picker
                :columns="petColumns"
                @cancel="showPicker = false"
                @confirm="onConfirm"
                :loading="petPickerLoading"
            />
          </van-popup>
        </div>
      </div>
      <!-- 预约时间 -->
      <div style="margin-top: 0.1rem">
        <van-field
            v-model="selectedDateTime"
            is-link
            readonly
            label="预约时间"
            placeholder="请选择预约时间"
            @click="openDatePicker"
        />
        <van-popup v-model:show="showDatePicker" round position="bottom">
          <van-picker-group
              v-model:active-tab="activeTab"
              title="预约日期"
              :tabs="['选择日期', '选择时间']"
              @confirm="onConfirmTime"
              @cancel="onCancel"
          >
            <van-date-picker
                v-model="selectedDate"
                :min-date="minDate"
                :max-date="maxDate"
            />
            <van-time-picker
                :min-hour="9"
                :max-hour="22"
                v-model="selectedTime"
            />
          </van-picker-group>
        </van-popup>
      </div>

      <!--      选择的订单服务-->
      <div style="margin-top: 0.1rem">
        <lImgRTextCard :serviceItemImgUrl="data.serviceItemData?.imageUrl">
          <template #leftTextTop>
            {{ data.serviceItemData?.serviceName || '暂无标题' }}
          </template>
          <template #rightTextTop>
            ￥{{ data.serviceItemData?.price || '0.00' }}
          </template>
          <template #content>
            描述: {{ data.serviceItemData?.description || '暂无描述' }}
          </template>
          <template #leftTextBottom>
            分类: {{ data.serviceItemData?.category || '暂无分类' }}
          </template>
          <template #rightTextBottom>
            服务类型: {{ data.serviceItemData?.serviceType || '暂无类型' }}
          </template>
        </lImgRTextCard>
      </div>

      <!--      选择的医生-->
      <div style="margin-top: 0.1rem">
        <LtextRtextCard>
          <template #leftText>
            医生
          </template>
          <template #rightText>
            {{ data.employeeData?.realName || '暂无' }}
          </template>
        </LtextRtextCard>
      </div>

      <!--      订单金额-->
      <div style="margin-top: 0.1rem">
        <LtextRtextCard>
          <template #leftText>
            订单金额
          </template>
          <template #rightText>
            ￥{{ data.serviceItemData?.price || '0.00' }}
          </template>
        </LtextRtextCard>
      </div>

      <!-- 客户订单备注 -->
      <div class="remark-section">
        <div class="remark-header">
          <van-icon name="edit" class="remark-icon"/>
          <span class="remark-title">订单备注</span>
        </div>
        <div class="remark-content">
          <van-field
              v-model="remark"
              rows="3"
              autosize
              type="textarea"
              placeholder="请填写宠物特殊需求或注意事项&#10;例如：宠物性格敏感、需要额外关注等"
              class="remark-textarea"
              maxlength="200"
              show-word-limit
          />
          <div class="remark-tips">
            <van-icon name="info-o" class="tips-icon"/>
            <span class="tips-text">请详细描述宠物的特殊情况，以便医生更好地提供服务</span>
          </div>
        </div>
      </div>
      <!--    订单详情结束-->
    </div>
    <!--    立即支付-->
    <div>
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
              <div class="amount-value">￥{{ data.serviceItemData?.price || '0.00' }}</div>
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
                确认支付 ￥{{ data.serviceItemData?.price || '0.00' }}
              </van-button>
            </div>
          </div>
        </van-popup>
      </div>
      <van-action-bar>
        <van-action-bar-button type="primary"
                               text="立即支付"
                               @click="handlePayment"
        />
      </van-action-bar>
    </div>
  </div>
</template>

<script setup>
import navBarTop from "@/components/navBarTop.vue";
import lImgRTextCard from "@/components/LImgRTextCard.vue";
import {computed, onMounted, reactive, ref, watch} from "vue";
import router from "@/router/index.js";
import {useRoute} from "vue-router";
import request from "@/utils/request.js";
import LtextRtextCard from "@/components/LtextRtextCard.vue";
import petsInfoStores from "@/stores/petsInfoStores.js";
import {showLoadingToast, showToast} from "vant";


const onLeftTop = () => {
  router.go(-1)
}

const serviceId = useRoute().params.serviceId
const vetId = useRoute().params.vetId
console.log(vetId + ' ' + serviceId)

const petsInfoStoresData = petsInfoStores()


const data = reactive({
  serviceItemData: null,
  employeeData: null,
  petsData: null,
  orderData: null,
})

onMounted(() => {
  // 获取服务详情
  request.get('/serviceItems/getServiceItemById', {
    params: {
      id: serviceId
    }
  }).then(res => {
    if (res.code === 200) {
      console.log(res.data)
      data.serviceItemData = res.data
    }
  }).catch(error => {
    console.error(error);
  });

//   获取员工详情
  request.get('/sysUser/getSysUserById', {
    params: {
      id: vetId
    }
  }).then(res => {
    if (res.code === 200) {
      console.log(res.data)
      data.employeeData = res.data
    }
  }).catch(error => {
    console.error(error);
  });

//   获取宠物数据
  petsInfoStoresData.getPetsInfoData()
})

data.petsData = petsInfoStoresData.petsInfoData
watch(() => petsInfoStoresData.petsInfoData, (newValue, oldValue) => {
  data.petsData = newValue
})

const fieldValue = ref('');
const showPicker = ref(false);
const petPickerLoading = ref(false);

// 构建宠物选择器数据
const petColumns = computed(() => {
  if (!data.petsData || data.petsData.length === 0) {
    return [];
  }

  return data.petsData.map(pet => ({
    text: pet.name || pet.petName || `宠物${pet.id}`,
    value: pet.id
  }));
});

// 监听宠物数据变化，设置默认选项
watch(() => data.petsData, (newPetsData) => {
  if (newPetsData && newPetsData.length > 0) {
    // 如果还没有选择宠物，设置第一个为默认选项
    if (!fieldValue.value && !selectedPetId.value) {
      const firstPet = newPetsData[0];
      fieldValue.value = firstPet.name || firstPet.petName || `宠物${firstPet.id}`;
      selectedPetId.value = firstPet.id;
    }
  }
});

const selectedPetId = ref(null);

const onConfirm = ({selectedOptions}) => {
  if (selectedOptions && selectedOptions.length > 0) {
    fieldValue.value = selectedOptions[0].text;
    selectedPetId.value = selectedOptions[0].value;
  }
  showPicker.value = false;
};

//去添加宠物
const goToAddPet = () => {
  // 跳转到添加宠物页面
  router.push('/petsProfileAdd');
};

// 预约时间
const activeTab = ref(0);
const minDate = new Date();
const maxDate = new Date(2030, 11, 31);
const showDatePicker = ref(false);
const selectedDateTime = ref('');

// 使用响应式数据来获取选择的日期和时间
const selectedDate = ref([new Date().getFullYear(), new Date().getMonth() + 1, new Date().getDate()]);
const selectedTime = ref(['09', '00']);

// 格式化日期显示
const formatDate = (dateArray, timeArray) => {
  const year = dateArray[0];
  const month = String(dateArray[1]).padStart(2, '0');
  const day = String(dateArray[2]).padStart(2, '0');
  const hour = String(timeArray[0]).padStart(2, '0');
  const minute = String(timeArray[1]).padStart(2, '0');
  // 使用空格
  return `${year}-${month}-${day} ${hour}:${minute}:00`;
};


// 确认选择时间
const onConfirmTime = () => {
  try {
    selectedDateTime.value = formatDate(selectedDate.value, selectedTime.value);
    showToast(`已选择: ${selectedDateTime.value}`);
  } catch (error) {
    console.error('处理日期时间出错:', error);
    showToast('选择时间出错，请重新选择');
  }

  showDatePicker.value = false;
};

// 取消选择时间
const onCancel = () => {
  showDatePicker.value = false;
  console.log('取消选择时间');
};

// 打开时间选择器
const openDatePicker = () => {
  showDatePicker.value = true;
};


// 备注
const remark = ref('');


// 支付
const paying = ref(false);//支付弹窗显示

//点击立即支付
const handlePayment = async () => {
  // 验证是否选择了宠物
  if (!selectedPetId.value) {
    showToast('请选择宠物');
    return;
  }
  // 验证是否选择时间
  if (!selectedDateTime.value) {
    showToast('请选择预约时间');
    return;
  }
  paying.value = true;
  data.orderData = {
    employeeId: vetId,//员工id
    serviceId: serviceId,//服务id
    petId: selectedPetId.value,//宠物id
    appointmentStartTime: selectedDateTime.value,//预约时间
    amount: data.serviceItemData?.price || '0.00',// 订单金额
    remark: remark.value,//备注
    paymentMethod: null,//支付方式(1：微信，2：支付宝，3：现金)
    paymentStatus: 0,//支付状态(0：未支付，1：已支付，2：已退款)
  }
};

//关闭支付方式弹(创建订单，后续操作都是修改订单)
const onClickOverlay = () => {
  paying.value = false;
  console.log(data.orderData)
  createOrderPost(data.orderData)//创建订单接口
  showToast('订单创建成功,请尽快支付');
  router.replace('/booking');
};

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

const selectedPaymentMethod = ref(null);

// 选择支付方式
const selectPaymentMethod = (method) => {
  selectedPaymentMethod.value = method;
  console.log("选择支付方式:" + method.name)
};

// 确认支付
const confirmPayment = async () => {
  if (!selectedPaymentMethod.value) {
    showToast('请选择支付方式');
    return;
  }

  // 关闭支付弹窗
  paying.value = false;

  // 根据选择的支付方式执行相应逻辑
  switch (selectedPaymentMethod.value.id) {
    case 1:
      // 微信支付逻辑
      await handleWechatPayment();
      console.log('支付成功')
      break;
    case 2:
      // 支付宝支付逻辑
      await handleAlipayPayment();
      console.log('支付宝支付成功')
      break;
    case 3:
      // 到店支付逻辑
      await handleCashPayment();
      console.log('到店支付成功')
      break;
  }
};

// 各种支付方式处理函数（需要根据实际后端接口实现）
// 微信支付
const handleWechatPayment = async () => {
  console.log('微信支付handleWechatPayment')
  //支付逻辑
  data.orderData.paymentMethod = 'wechat'
  data.orderData.paymentStatus = 1
  createOrderPost(data.orderData)//创建订单接口

};
// 支付宝支付
const handleAlipayPayment = async () => {
  console.log('支付宝支付handleAlipayPayment')
  //支付逻辑
  data.orderData.paymentMethod = 'alipay'
  data.orderData.paymentStatus = 1
  createOrderPost(data.orderData)//创建订单接口

};
//现金支付
const handleCashPayment = async () => {
  console.log('现金支付handleCashPayment')
  //支付逻辑
  data.orderData.paymentMethod = 'cash'
  data.orderData.paymentStatus = 1
  createOrderPost(data.orderData)//创建订单接口
  // 跳转到订单详情页
  await router.push('/orderSuccess');
};

// 创建订单接口
const createOrderPost = (orderData) => {
  const token = localStorage.getItem('token');
  console.log(token)
  request.post('/sysOrder/createSysOrder', orderData).then(res => {
    console.log(res)
    if (res.code === 200) {
      console.log(res.data)
      router.go(-1);
    }
  }).catch(error => {
    console.error(error);
  });
};
</script>


<style scoped>

.add-pet-section {
  margin: 20px 0;
  padding: 16px;
  background: linear-gradient(135deg, #6742c4 0%, #1b0035 100%);
  border-radius: 12px;
  text-align: center;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.add-pet-prompt {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.plus-icon {
  font-size: 20px;
  color: #fff;
  background-color: rgba(255, 255, 255, 0.2);
  border-radius: 50%;
  width: 32px;
  height: 32px;
  line-height: 32px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.prompt-text {
  font-size: 16px;
  color: #fff;
  font-weight: 500;
}

.add-pet-btn {
  background-color: #fff !important;
  color: #667eea !important;
  border: none !important;
  font-weight: 600;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  transition: all 0.3s ease;
}

.add-pet-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}

.remark-section {
  margin-top: 0.2rem;
  background-color: var(--van-background-2, #ffffff);
  border-radius: 12px;
  padding: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.remark-header {
  display: flex;
  align-items: center;
  margin-bottom: 12px;
}

.remark-icon {
  font-size: 12px;
  color: #6742c4;
  margin-right: 8px;
}

.remark-title {
  font-size: 12px;
}

.remark-content {
  width: 100%;
}

.remark-textarea {
  border: 1px solid #ebedf0;
  border-radius: 8px;
  padding: 12px;
  background-color: #fafafa;
  height: 2.6rem;
  transition: all 0.3s ease;
}

.remark-textarea:focus {
  border-color: #6742c4;
  background-color: #fff;
  box-shadow: 0 0 0 2px rgba(103, 66, 196, 0.1);
}

.remark-textarea :deep(.van-field__control) {
  font-size: 14px;
  line-height: 1.5;
  color: #555;
}

.remark-tips {
  display: flex;
  align-items: flex-start;
  margin-top: 12px;
  padding: 8px 12px;
  background-color: #f8f9fa;
  border-radius: 6px;
  border-left: 3px solid #6742c4;
}

.tips-icon {
  font-size: 14px;
  color: #6742c4;
  margin-right: 6px;
  margin-top: 2px;
}

.tips-text {
  font-size: 12px;
  color: #666;
  line-height: 1.4;
}

/* 文本域字数统计样式 */
:deep(.van-field__word-limit) {
  font-size: 12px;
  color: #999;
  text-align: right;
  margin-top: 4px;
}

/* 响应式优化 */
@media (max-width: 768px) {
  .remark-section {
    padding: 12px;
    margin-top: 0.15rem;
  }

  .remark-title {
    font-size: 15px;
  }

  .remark-textarea {
    padding: 10px;
  }

  .tips-text {
    font-size: 11px;
  }
}


</style>

<style>

.van-action-bar {
  --van-action-bar-background: rgba(255, 255, 255, 0);
}

/* 非scoped样式，用于覆盖组件库样式 */
.pet-delete-dialog .van-dialog__confirm {
  background-color: #ffffff !important;
  font-weight: bold;
  color: #000000 !important;
}

.pet-delete-dialog .van-dialog__cancel {
  color: #000000 !important;
  font-weight: bold;
  background-color: #ffffff !important;
}


</style>

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