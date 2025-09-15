<!--寄养服务列表-->
<template>
  <div>
    <navBarTop title="寄养服务">
      <template #left>
        <van-icon name="arrow-left" @click="router.go(-1)"/>
      </template>
    </navBarTop>
  </div>
  <div>
    <div style="padding-top: 16px;padding-left: 0.25rem;
        padding-right: 0.25rem;height: 100vh">
      <van-grid call="van-grid" direction="horizontal" :column-num="2" :border="false">
        <van-grid-item v-for="item in data.serviceItemData" :key="item.id">
          <serviceItemCard :serviceData="item" @click="onServiceItem(item.id)"
                           style="box-shadow: 0 0 0.5rem rgba(0,0,0,0.1);">

          </serviceItemCard>
        </van-grid-item>
      </van-grid>
    </div>
  </div>
</template>

<script setup>
import serviceItemCard from "@/components/ServiceItemCard.vue";
import {onMounted, reactive} from "vue";
import request from "@/utils/request.js";
import router from "@/router/index.js";
import navBarTop from "@/components/navBarTop.vue";


const data = reactive({
  serviceItemData: null,
})

const getServiceItemData = () => {
  request.get('/serviceItems/getAllServiceItem', {
    params: {
      serviceType: 'foster',
    }
  }).then(res => {
    if (res.code === 200) {
      console.log(res.data)
      data.serviceItemData = res.data
    }
  }).catch(err => {
    console.error(err)
  })
}

onMounted(() => {
  getServiceItemData()
})

const onServiceItem = (id) => {
  console.log("服务id:" + id)
  router.push({
    name: 'serviceDetail',
    params: {
      id: id
    }
  })
}

</script>

<style scoped>

</style>