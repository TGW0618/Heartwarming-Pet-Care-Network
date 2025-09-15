<!--订单列表-->
<template>
  <div>
    <!--    头部导航-->
    <div>
      <navBarTop title="我的订单">
        <template #left>
          <div>

          </div>
        </template>
        <template #right>
          <div @click="onRightTop">
            <van-icon name="search" style="color:#000000;font-size: 0.45rem;"/>
          </div>
        </template>
      </navBarTop>
    </div>
    <div style="padding-bottom: 3rem">
      <van-tabs v-model:active="activeName" swipeable @click-tab="onClickTab">
        <!--        全部订单-->
        <van-tab title="全部" name="a" class="tab-center">
          <div>
            <div v-for="item in data.orderData" :key="item.id" style="padding-bottom: 0.2rem">
              <LupImgbuTextRupTextbuText>
                <template #leftTextTop>{{ item.serviceName }}</template>
                <template #rightTextTop>￥{{ item.servicePrice }}</template>
                <template #content>描述: {{ item.serviceDescription }}</template>
                <template #leftTextBottom>
                  <van-tag :type="getOrderStatusType(item.status)" size="mini">
                    {{ formatOrderStatus(item.status) }}
                  </van-tag>
                </template>
                <template #rightTextBottom>
                  <van-tag :type="getServiceTypeType(item.serviceType)" size="mini">
                    {{ formatServiceType(item.serviceType) }}
                  </van-tag>
                </template>
                <template #amount>应付金额：￥{{ item.amount }}</template>

                <!-- 自定义操作按钮 -->
                <template #actions>
                  <van-col>
                    <van-button
                        v-if="canCancelOrder(item.status, item.paymentStatus)"
                        @click="onCancelOrder(item)"
                        class="action-button"
                        plain
                        size="small"
                    >
                      取消订单
                    </van-button>
                  </van-col>
                  <van-col>
                    <van-button
                        v-if="canDeleteOrder(item.status)"
                        @click="onDeleteOrder(item.id)"
                        class="action-button"
                        plain
                        size="small"
                    >
                      删除订单
                    </van-button>
                  </van-col>
                  <van-col>
                    <van-button
                        @click="onViewOrderDetail(item.id)"
                        class="action-button"
                        plain
                        size="small"
                    >
                      查看详情
                    </van-button>
                  </van-col>
                </template>
              </LupImgbuTextRupTextbuText>
            </div>
          </div>
        </van-tab>
        <!--        待付款的订单-->
        <van-tab title="待付款" name="b" class="tab-center">
          <div>
            <div v-for="item in data.orderData" :key="item.id" style="padding-bottom: 0.2rem">
              <LupImgbuTextRupTextbuText>
                <template #leftTextTop>{{ item.serviceName }}</template>
                <template #rightTextTop>￥{{ item.servicePrice }}</template>
                <template #content>描述: {{ item.serviceDescription }}</template>
                <template #leftTextBottom>
                  <van-tag :type="getOrderStatusType(item.status)" size="mini">
                    {{ formatOrderStatus(item.status) }}
                  </van-tag>
                </template>
                <template #rightTextBottom>
                  <van-tag :type="getServiceTypeType(item.serviceType)" size="mini">
                    {{ formatServiceType(item.serviceType) }}
                  </van-tag>
                </template>
                <template #amount>应付金额：￥{{ item.amount }}</template>

                <!-- 自定义操作按钮 -->
                <template #actions>
                  <van-col>
                    <van-button
                        v-if="canCancelOrder(item.status, item.paymentStatus)"
                        @click="onCancelOrder(item.id)"
                        class="action-button"
                        plain
                        size="small"
                    >
                      取消订单
                    </van-button>
                  </van-col>
                  <van-col>
                    <van-button
                        v-if="canDeleteOrder(item.status)"
                        @click="onDeleteOrder(item.id)"
                        class="action-button"
                        plain
                        size="small"
                    >
                      删除订单
                    </van-button>
                  </van-col>
                  <van-col>
                    <van-button
                        @click="onViewOrderDetail(item.id)"
                        class="action-button"
                        plain
                        size="small"
                    >
                      查看详情
                    </van-button>
                  </van-col>
                </template>
              </LupImgbuTextRupTextbuText>
            </div>
          </div>
        </van-tab>
        <!--        处理中的订单-->
        <van-tab title="处理中" name="c" class="tab-center">
          <div>
            <div v-for="item in data.orderData" :key="item.id" style="padding-bottom: 0.2rem">
              <LupImgbuTextRupTextbuText>
                <template #leftTextTop>{{ item.serviceName }}</template>
                <template #rightTextTop>￥{{ item.servicePrice }}</template>
                <template #content>描述: {{ item.serviceDescription }}</template>
                <template #leftTextBottom>
                  <van-tag :type="getOrderStatusType(item.status)" size="mini">
                    {{ formatOrderStatus(item.status) }}
                  </van-tag>
                </template>
                <template #rightTextBottom>
                  <van-tag :type="getServiceTypeType(item.serviceType)" size="mini">
                    {{ formatServiceType(item.serviceType) }}
                  </van-tag>
                </template>
                <template #amount>应付金额：￥{{ item.amount }}</template>

                <!-- 自定义操作按钮 -->
                <template #actions>
                  <van-col>
                    <van-button
                        v-if="canCancelOrder(item.status, item.paymentStatus)"
                        @click="onCancelOrder(item.id)"
                        class="action-button"
                        plain
                        size="small"
                    >
                      取消订单
                    </van-button>
                  </van-col>
                  <van-col>
                    <van-button
                        v-if="canDeleteOrder(item.status)"
                        @click="onDeleteOrder(item.id)"
                        class="action-button"
                        plain
                        size="small"
                    >
                      删除订单
                    </van-button>
                  </van-col>
                  <van-col>
                    <van-button
                        @click="onViewOrderDetail(item.id)"
                        class="action-button"
                        plain
                        size="small"
                    >
                      查看详情
                    </van-button>
                  </van-col>
                </template>
              </LupImgbuTextRupTextbuText>
            </div>
          </div>
        </van-tab>
        <!--        已完成的订单-->
        <van-tab title="已完成" name="d" class="tab-center">
          <div>
            <div v-for="item in data.orderData" :key="item.id" style="padding-bottom: 0.2rem">
              <LupImgbuTextRupTextbuText>
                <template #leftTextTop>{{ item.serviceName }}</template>
                <template #rightTextTop>￥{{ item.servicePrice }}</template>
                <template #content>描述: {{ item.serviceDescription }}</template>
                <template #leftTextBottom>
                  <van-tag :type="getOrderStatusType(item.status)" size="mini">
                    {{ formatOrderStatus(item.status) }}
                  </van-tag>
                </template>
                <template #rightTextBottom>
                  <van-tag :type="getServiceTypeType(item.serviceType)" size="mini">
                    {{ formatServiceType(item.serviceType) }}
                  </van-tag>
                </template>
                <template #amount>应付金额：￥{{ item.amount }}</template>

                <!-- 自定义操作按钮 -->
                <template #actions>
                  <van-col>
                    <van-button
                        v-if="canCancelOrder(item.status, item.paymentStatus)"
                        @click="onCancelOrder(item.id)"
                        class="action-button"
                        plain
                        size="small"
                    >
                      取消订单
                    </van-button>
                  </van-col>
                  <van-col>
                    <van-button
                        v-if="canDeleteOrder(item.status)"
                        @click="onDeleteOrder(item.id)"
                        class="action-button"
                        plain
                        size="small"
                    >
                      删除订单
                    </van-button>
                  </van-col>
                  <van-col>
                    <van-button
                        @click="onViewOrderDetail(item.id)"
                        class="action-button"
                        plain
                        size="small"
                    >
                      查看详情
                    </van-button>
                  </van-col>
                </template>
              </LupImgbuTextRupTextbuText>
            </div>
          </div>
        </van-tab>
      </van-tabs>
    </div>
  </div>
</template>

<script setup>
import LupImgbuTextRupTextbuText from "@/components/LupImgbuTextRupTextbuText.vue";
import {onMounted, reactive, ref} from "vue";
import router from "@/router/index.js";
import request from "@/utils/request.js";
import navBarTop from "@/components/navBarTop.vue";
import {showConfirmDialog, showDialog, showToast} from "vant";

const onRightTop = () => {
  console.log("点击了搜索")
  router.push({name: 'orderSelect'})
}

const data = reactive({
  userId: JSON.parse(localStorage.getItem('user-info')),
  orderData: null,
})

const activeName = ref('a');

const onClickTab = () => {
  switch (activeName.value) {
    case 'a':
      selectOrderAll();//全部订单
      break;
    case 'b':
      selectOrderWaitPay();//待付款
      break;
    case 'c':
      selectOrderProcessing();//处理中
      break;
    case 'd':
      selectOrderCompleted();//已完成
      break;
  }
};

onMounted(() => {
  onClickTab()
})

// 订单请求api
const selectOrder = (orderStatusData) => {
  console.log('订单请求api')
  request.get('/sysOrder/getSysOrderByUserId', {
    params: {
      userId: data.userId.id,
      status: orderStatusData.orderStatus,
      paymentStatus: orderStatusData.paymentStatus
    }
  }).then(res => {
    console.log('查询订单:', res)
    data.orderData = res.data
  }).catch(err => {
    console.log('查询订单失败:', err)
  })
}

// 查询全部订单
const selectOrderAll = () => {
  console.log('查询全部订单')
  const orderStatusData = {//订单状态数据
    orderStatus: null,//订单状态
    paymentStatus: null//支付状态
  }
  selectOrder(orderStatusData)
};

// 查询待付款的订单
const selectOrderWaitPay = () => {
  console.log('查询待付款的订单')
  const orderStatusData = {//订单状态数据
    paymentStatus: 0 //支付状态
  }
  selectOrder(orderStatusData)
  console.log(data.userId.id)
}

// 查询处理中的订单
const selectOrderProcessing = () => {
  console.log('查询处理中的订单')
  const orderStatusData = {//订单状态数据
    orderStatus: 'processing',//订单状态
    paymentStatus: 1 //支付状态
  }
  selectOrder(orderStatusData)
}

// 查询已完成的订单
const selectOrderCompleted = () => {
  console.log('查询已完成的订单')
  const orderStatusData = {//订单状态数据
    orderStatus: 'completed',//订单状态
    paymentStatus: 1 //支付状态
  }
  selectOrder(orderStatusData)
}

// 格式化订单状态显示
const formatOrderStatus = (status) => {
  const statusMap = {
    'pending': '待确认',
    'confirmed': '已确认',
    'processing': '处理中',
    'completed': '已完成',
    'canceled': '已取消'
  };
  return statusMap[status] || status || '未知状态';
};

// 获取订单状态标签类型
const getOrderStatusType = (status) => {
  const typeMap = {
    'pending': 'warning',
    'confirmed': 'primary',
    'processing': 'primary',
    'completed': 'success',
    'canceled': 'danger'
  };
  return typeMap[status] || 'default';
};

// 格式化服务类型显示
const formatServiceType = (type) => {
  const typeMap = {
    'foster': '寄养服务',
    'medical': '医疗服务',
  };
  return typeMap[type] || type || '未知服务';
};

// 获取服务类型标签类型
const getServiceTypeType = (type) => {
  const typeMap = {
    'foster': 'warning',
    'medical': 'success',
  };
  return typeMap[type] || 'default';
};

// 判断是否可以取消订单
const canCancelOrder = (status, paymentStatus) => {
  // 只有未支付或已支付但未完成的订单才能取消
  return (paymentStatus === 0 || paymentStatus === 1) &&
      (status === 'pending' || status === 'confirmed' || status === 'processing');
};

// 判断是否可以删除订单
const canDeleteOrder = (status) => {
  // 已完成或已取消的订单可以删除
  return status === 'completed' || status === 'canceled';
};

// 取消订单
const onCancelOrder = (item) => {
  console.log("cancelOrder:" + item.id)

  showConfirmDialog({
    title: '取消订单',
    message: '确定取消该订单吗?',
  })
      .then(() => {
        // 创建只包含需要更新的字段的对象
        const updateData = {
          id: item.id,
          status: 'canceled',
          updateTime: new Date().toISOString().slice(0, 19).replace('T', ' ') // 格式化为 yyyy-MM-dd HH:mm:ss
        };

        request.put(`/sysOrder/updateSysOrder/${item.id}`, updateData).then(res => {
          if (res.code === 200) {
            showToast('订单已取消');
            onClickTab(); // 刷新列表
          } else {
            showToast('取消订单失败');
          }
        }).catch(error => {
          console.error('取消订单失败:', error);
          showToast('取消订单失败');
        });
      })
      .catch(() => {
        // 用户取消操作
      });
}

// 删除订单
const onDeleteOrder = (id) => {
  console.log("delOrder:" + id)
  showConfirmDialog({
    title: '删除订单',
    message: '确定删除该订单吗?',
  })
      .then(() => {
        request.delete(`/sysOrder/deleteSysOrder/${id}`).then(res => {
          if (res.code === 200) {
            showToast('删除成功');
            onClickTab()
          } else {
            showToast('删除失败');
          }
        })
      })
      .catch(() => {
        // 用户取消操作
      });
}

// 查看订单详情
const onViewOrderDetail = (id) => {
  router.push({
    name: 'orderDetail',
    params: {
      id: id
    }
  })
  console.log(id)
}
</script>

<style scoped>
.action-button {
  color: black;
}

.tab-center {
  padding: 0.2rem;
  min-height: 100vh;
  background-color: var(--van-background-2, #ffffff);
  border-radius: 0 0 8px 8px;
}
</style>
