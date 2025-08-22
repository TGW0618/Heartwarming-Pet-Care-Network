<!--编辑名字-->
<template>
  <nav-bar-top title="设置手机号">
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
        <van-field
            v-model="phone"
            name="phone"
            label="手机号"
            placeholder="手机号"
            :rules="[{ required: true, message: '请填写手机号' }]"
        />

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
import {onMounted, ref} from "vue";
import useUserInfoStore from "@/stores/userInfo.js";
import router from "@/router/index.js";

const userInfoStore = useUserInfoStore()
userInfoStore.fetchUserInfo()
const phone = ref('')


// 在组件挂载时初始化数据
onMounted(async () => {
  try {
    // 确保用户信息已加载
    if (!userInfoStore.userInfo) {
      await userInfoStore.fetchUserInfo();
    }
    // 设置默认值，添加空值检查
    phone.value = userInfoStore.userInfo?.phone || '';
  } catch (error) {
    console.error('获取用户信息失败:', error);
    phone.value = '';
  }
});

const onSubmit = (values) => {
  userInfoStore.userInfo.phone = values.phone
  userInfoStore.updateUserInfo(userInfoStore.userInfo)
  router.push('/userProfile')
}

const onClickLeft = () => {
  router.back();
}
</script>

<style scoped>

</style>