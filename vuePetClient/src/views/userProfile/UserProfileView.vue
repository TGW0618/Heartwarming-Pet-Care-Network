<!--个人资料页-->
<template>
  <navBarTop title="个人资料">
    <template #left>
      <div @click="onClickLeft">
        <slot>
          <van-icon name="arrow-left"/>
        </slot>
      </div>
    </template>
  </navBarTop>
  <div>
    <!--    头像-->
    <div class="Horizontal-bar-data" @click="onAvatar">
      <Horizontal-bar-data title="头像" :isImg="data.userInfo.avatar"></Horizontal-bar-data>
    </div>
    <!--    名字-->
    <div class="Horizontal-bar-data" @click="onRealName">
      <Horizontal-bar-data title="名字" :isText="data.userInfo.realName"></Horizontal-bar-data>
    </div>
    <div class="Horizontal-bar-data">
      <Horizontal-bar-data title="账号" :isText="data.userInfo.username"></Horizontal-bar-data>
    </div>
    <div class="Horizontal-bar-data" @click="onSex">
      <Horizontal-bar-data title="性别" :isText="data.userInfo.sex"></Horizontal-bar-data>
    </div>
    <div class="Horizontal-bar-data" @click="onPhone">
      <Horizontal-bar-data title="手机号" :isText="data.userInfo.phone"></Horizontal-bar-data>
    </div>
    <div class="Horizontal-bar-data" @click="onEmail">
      <Horizontal-bar-data title="邮箱" :isText="data.userInfo.email"></Horizontal-bar-data>
    </div>
  </div>

</template>

<script setup>
import navBarTop from "@/components/navBarTop.vue"
import request from "@/utils/request.js";
import {reactive} from "vue";
import HorizontalBarData from "@/components/HorizontalBarData.vue";
import router from "@/router/index.js";

const onClickLeft = () => {
  router.push('/userinfo')
}

const data = reactive({
  userInfo: {}
});

const getUserInfo = () => {
  request.get('/ClientUserInfo/getUserById').then(res => {
    console.log(res)
    if (res.code === 200) {
      data.userInfo = res.data
      console.log(data.userInfo.username)
    }
  }).catch(err => {

  })
}
getUserInfo()

const onAvatar = () => {
  router.push('/avatar')
}

const onRealName = () => {
  router.push('/realName')
}

const onSex = () => {
  router.push('/sex')
}

const onPhone = () => {
  router.push('/phone')
}

const onEmail = () => {
  router.push('/email')
}

</script>

<style scoped>
.Horizontal-bar-data {
  font-size: 16px;
  box-shadow: #edebeb 0.5px 0.5px 0.5px;
  padding: 16px;
  border-radius: 6px;
  align-items: center;
}
</style>