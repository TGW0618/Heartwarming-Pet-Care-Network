<!--编辑邮箱-->
<template>
  <nav-bar-top title="设置邮箱">
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
            v-model="email"
            name="email"
            label="邮箱"
            placeholder="邮箱"
            :rules="[{ required: true, message: '请填写邮箱' }]"
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
const email = ref('')


// 在组件挂载时初始化数据
onMounted(async () => {
  try {
    // 确保用户信息已加载
    if (!userInfoStore.userInfo) {
      await userInfoStore.fetchUserInfo();
    }
    // 设置默认值，添加空值检查
    email.value = userInfoStore.userInfo?.email || '';
  } catch (error) {
    console.error('获取用户信息失败:', error);
    email.value = '';
  }
});

const onSubmit = (values) => {
  userInfoStore.userInfo.email = values.email
  userInfoStore.updateUserInfo(userInfoStore.userInfo)
  router.push('/userProfile')
}

const onClickLeft = () => {
  router.back();
}
</script>

<style scoped>

</style>