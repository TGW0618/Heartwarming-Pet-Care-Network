<!--订单搜索-->
<template>
  <div class="order-search-container">
    <!-- 搜索栏 -->
    <van-search
        v-model="searchKeyword"
        placeholder="请输入订单号、服务名称或宠物名称"
        show-action
        @search="onSearch"
        @cancel="onCancel"
        @clear="onClear"
    >
      <template #action>
        <div @click="onSearch">搜索</div>
      </template>
    </van-search>

    <!-- 搜索结果 -->
    <div class="search-results" v-if="searchResults.length > 0">
      <div class="result-count">共找到 {{ searchResults.length }} 条记录</div>
      <van-list finished>
        <div v-for="item in searchResults" :key="item.id" class="order-item">
          <LupImgbuTextRupTextbuText @click="viewOrderDetail(item.id)">
            <template #leftTextTop>{{ item.serviceName }}</template>
            <template #rightTextTop>￥{{ item.servicePrice }}</template>
            <template #content>订单号: {{ item.orderNo }}</template>
            <template #leftTextBottom>
              <van-tag :type="getOrderStatusType(item.status)" size="mini">
                {{ formatOrderStatus(item.status) }}
              </van-tag>
            </template>
            <template #rightTextBottom>{{ item.petName }}</template>
            <template #amount>应付金额：￥{{ item.amount }}</template>
          </LupImgbuTextRupTextbuText>
        </div>
      </van-list>
    </div>

    <!-- 空状态 -->
    <van-empty
      v-else-if="searchPerformed && searchResults.length === 0"
      description="暂无相关订单"
      class="empty-state"
    />

    <!-- 搜索历史 -->
    <div v-else class="search-history">
      <div class="history-header">
        <span>搜索历史</span>
        <van-icon name="delete-o" @click="clearSearchHistory" v-if="searchHistory.length > 0" />
      </div>
      <div class="history-tags">
        <van-tag
          v-for="(history, index) in searchHistory"
          :key="index"
          class="history-tag"
          @click="searchFromHistory(history)"
        >
          {{ history }}
        </van-tag>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import { showToast, showLoadingToast, closeToast } from "vant";
import router from "@/router/index.js";
import request from "@/utils/request.js";
import LupImgbuTextRupTextbuText from "@/components/LupImgbuTextRupTextbuText.vue";

const searchKeyword = ref('');
const searchResults = ref([]);
const searchPerformed = ref(false);
const searchHistory = ref([]);
const userId = JSON.parse(localStorage.getItem('user-info'))?.id;

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

// 执行搜索
const onSearch = async () => {
  if (!searchKeyword.value.trim()) {
    showToast('请输入搜索关键词');
    return;
  }

  // 添加到搜索历史
  addToSearchHistory(searchKeyword.value);

  try {
    showLoadingToast('搜索中...');
    const response = await request.get('/sysOrder/searchOrders', {
      params: {
        userId: userId,
        keyword: searchKeyword.value.trim()
      }
    });

    if (response.code === 200) {
      searchResults.value = response.data || [];
      searchPerformed.value = true;
      showToast(`找到 ${searchResults.value.length} 条记录`);
    } else {
      showToast('搜索失败: ' + response.message);
    }
  } catch (error) {
    console.error('搜索订单失败:', error);
    showToast('搜索失败，请稍后重试');
  } finally {
    closeToast();
  }
};

// 从历史记录搜索
const searchFromHistory = (keyword) => {
  searchKeyword.value = keyword;
  onSearch();
};

// 添加到搜索历史
const addToSearchHistory = (keyword) => {
  if (!keyword.trim()) return;

  // 移除重复的历史记录
  const index = searchHistory.value.indexOf(keyword);
  if (index > -1) {
    searchHistory.value.splice(index, 1);
  }

  // 添加到开头
  searchHistory.value.unshift(keyword);

  // 限制历史记录数量
  if (searchHistory.value.length > 10) {
    searchHistory.value.pop();
  }

  // 保存到本地存储
  saveSearchHistory();
};

// 清除搜索历史
const clearSearchHistory = () => {
  searchHistory.value = [];
  saveSearchHistory();
  showToast('已清除搜索历史');
};

// 保存搜索历史到本地存储
const saveSearchHistory = () => {
  try {
    localStorage.setItem('order-search-history', JSON.stringify(searchHistory.value));
  } catch (e) {
    console.error('保存搜索历史失败:', e);
  }
};

// 加载搜索历史
const loadSearchHistory = () => {
  try {
    const history = localStorage.getItem('order-search-history');
    if (history) {
      searchHistory.value = JSON.parse(history);
    }
  } catch (e) {
    console.error('加载搜索历史失败:', e);
  }
};

// 取消搜索
const onCancel = () => {
  router.go(-1);
};

// 清除搜索
const onClear = () => {
  searchKeyword.value = '';
  searchResults.value = [];
  searchPerformed.value = false;
};

// 查看订单详情
const viewOrderDetail = (orderId) => {
  router.push({
    name: 'orderDetail',
    params: { id: orderId }
  });
};

// 组件挂载时加载搜索历史
onMounted(() => {
  loadSearchHistory();
});

// 组件卸载前保存搜索历史
onUnmounted(() => {
  saveSearchHistory();
});
</script>

<style scoped>
.order-search-container {
  min-height: 100vh;
  background-color: var(--van-background-2, #ffffff);
}

.search-results {
  padding: 0 0.2rem;
}

.result-count {
  padding: 0.2rem;
  font-size: 0.28rem;
  color: #666;
}

.order-item {
  padding-bottom: 0.2rem;
}

.empty-state {
  margin-top: 2rem;
}

.search-history {
  padding: 0.3rem;
}

.history-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.3rem;
  font-size: 0.3rem;
  color: #333;
}

.history-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 0.2rem;
}

.history-tag {
  margin-bottom: 0.2rem;
  padding: 0.1rem 0.2rem;
  background-color: #f5f5f5;
  border-radius: 0.1rem;
  font-size: 0.28rem;
}

.history-tag:active {
  background-color: #e0e0e0;
}
</style>
