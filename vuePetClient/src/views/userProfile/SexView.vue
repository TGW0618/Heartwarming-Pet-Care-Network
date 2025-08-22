<!--编辑性别-->
<template>
  <nav-bar-top title="设置性别">
    <template #left>
      <div @click="onClickLeft">
        <slot>
          <van-icon name="arrow-left"/>
        </slot>
      </div>
    </template>
  </nav-bar-top>
  <div>
    <van-form @submit="onSubmit">
      <van-cell-group inset>
        <van-cell title="性别" :value="selectedSexLabel" @click="showPicker = true"/>
        <van-popup v-model:show="showPicker" position="bottom">
          <van-picker
              title="选择性别"
              :columns="sexColumns"
              @confirm="onConfirm"
              @cancel="showPicker = false"
          />
        </van-popup>
      </van-cell-group>
      <div style="margin: 16px;">
        <van-button round block  native-type="submit">
          完成
        </van-button>
      </div>
    </van-form>
  </div>
</template>

<script setup>
import navBarTop from '@/components/navBarTop.vue'
import {computed, onMounted, ref} from "vue";
import useUserInfoStore from "@/stores/userInfo.js";
import router from "@/router/index.js";

const userInfoStore = useUserInfoStore()
const sex = ref('') // 组件内部存储英文值
const showPicker = ref(false)

const onClickLeft = () => {
  router.back();
}

// 性别选项
const sexColumns = [
  {text: '男', value: 'male'},
  {text: '女', value: 'female'},
  {text: '其他', value: 'other'}
];


// 英文到中文的映射
const englishToChinese = {
  'male': '男',
  'female': '女',
  'other': '其他'
};

// 计算属性：显示性别标签（显示中文）
const selectedSexLabel = computed(() => {
  return englishToChinese[sex.value] || '请选择';
});

// 在组件挂载时初始化数据
onMounted(async () => {
  try {
    if (!userInfoStore.userInfo) {
      await userInfoStore.fetchUserInfo();
    }
    sex.value = userInfoStore.userInfo?.sex || '';
  } catch (error) {
    console.error('获取用户信息失败:', error);
    sex.value = '';
  }
});

// 确认选择
const onConfirm = ({selectedOptions}) => {
  if (selectedOptions && selectedOptions.length > 0) {
    sex.value = selectedOptions[0].value;
  }
  showPicker.value = false;
};

const onSubmit = (values) => {
  // 确保userInfo存在
  if (!userInfoStore.userInfo) {
    userInfoStore.userInfo = {};
  }

  userInfoStore.userInfo.sex = sex.value;
  userInfoStore.updateUserInfo(userInfoStore.userInfo);
  router.push('/userProfile');
}
</script>

<style scoped>
</style>
