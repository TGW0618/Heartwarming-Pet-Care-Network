<!--服务详情页-->
<template>
  <navBarTop title="服务详情">
    <template #left>
      <div>
        <van-icon name="arrow-left" @click="onTopLeft"/>
      </div>
    </template>
    <template #right>
      <div @click="()=>{router.replace('/home')}">
        <van-icon name="home-o" />
      </div>
    </template>
  </navBarTop>
  <div>
    <!--    服务详情-->
    <div>
      <ServiceItemInfoCard :serviceDataInfo="data.vaccineDataInfo"></ServiceItemInfoCard>
    </div>
    <!--    服务评价-->
    <div>
      <!--      服务评价组件-->
    </div>
    <!--    底部按钮-->
    <div>
      <van-action-bar>
        <van-action-bar-icon icon="chat-o" text="客服" @click="onClickIcon"/>
        <van-action-bar-button text="立即预约" @click="onClickButton"/>
      </van-action-bar>
    </div>
  </div>
</template>

<script setup>
import navBarTop from "@/components/navBarTop.vue"
import ServiceItemInfoCard from "@/components/ServiceItemInfoCard.vue"
import bookingStore from "@/stores/petService/booking.js";
import userInfo from "@/stores/userInfo.js";
import {onMounted, reactive, watch} from "vue";
import {useRoute} from "vue-router";
import router from "@/router/index.js";
import {showConfirmDialog} from "vant";

const serviceId = useRoute().params.serviceId
const vetId = useRoute().params.vetId
console.log("路由参数:" + serviceId)
console.log(serviceId+"号服务详情")

const vaccine = bookingStore()
const userIsLogin = userInfo()


watch(() => vaccine.VaccineDataInfo, (newVal) => {
  if (newVal) {
    data.vaccineDataInfo = newVal
  }
})

onMounted(async () => {
  await vaccine.getServiceItemById(serviceId)
  data.vaccineDataInfo = vaccine.VaccineDataInfo
})

const data = reactive({
  vaccineDataInfo: null
})

const onTopLeft = () => {
  window.history.go(-1)
}

const onClickIcon = () => {
  alert("点击了客服")
}

const onClickButton = async () => {
  if (userIsLogin.token !== null) {
    await router.push({
      path: '/bookingNow/' + encodeURIComponent(vetId) + '/' + encodeURIComponent(serviceId)
    })
  } else {
    showConfirmDialog({
      message: '请先登录,再进行操作',
      className: 'pet-delete-dialog'
    })
        .then(() => {
          router.push('/login')
        })
        .catch(() => {
          // on cancel
        });
  }
}

</script>


<style scoped>

</style>

