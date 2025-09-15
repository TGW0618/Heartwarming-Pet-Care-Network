<!--服务详细信息-->
<template>
  <div>
    <div>
      <navBarTop title="服务详细信息">
        <template #left>
          <van-icon name="arrow-left" @click="router.go(-1)"/>
        </template>
      </navBarTop>
    </div>
    <div>
      <!--    服务详情-->
      <div>
        <ServiceItemInfoCard :serviceDataInfo="data.serviceDetailData"></ServiceItemInfoCard>
      </div>
      <!--    底部按钮-->
      <div>
        <van-action-bar safe-area-inset-bottom style="z-index: 9999;height: 2rem;">
          <van-action-bar-icon icon="chat-o" text="客服"/>
          <van-action-bar-button text="预定" @click="onClickButton"/>
        </van-action-bar>
      </div>
    </div>
  </div>

</template>

<script setup>
import ServiceItemInfoCard from "@/components/ServiceItemInfoCard.vue";
import router from "@/router/index.js";
import navBarTop from "@/components/navBarTop.vue";
import {useRoute} from "vue-router";
import {onMounted, reactive} from "vue";
import serviceItemStores from "@/stores/serviceItemStores.js"
import userInfo from "@/stores/userInfo.js";
import {showConfirmDialog} from "vant";

const serviceDetailId = useRoute().params.id
const serviceItem = serviceItemStores()
const userIsLogin = userInfo()

const data = reactive({
  serviceDetailData: null
})

const onClickButton = () => {
  if (userIsLogin.token !== null) {
    router.push({
      name: 'serviceBooking',
      params: {
        id: serviceDetailId
      }
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

onMounted(async () => {
  await serviceItem.fetchServiceDetailData(serviceDetailId)
  data.serviceDetailData = serviceItem.getServiceDetail
  console.log(data.serviceDetailData)
})

</script>

<style scoped>

</style>