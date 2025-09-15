<!--展示疫苗服务套餐-->
<template>
  <navBarTop title="疫苗服务">
    <template #left>
      <div>
        <van-icon name="arrow-left" @click="onTopLeft"/>
      </div>
    </template>
    <template #right>
      <div>
        <van-icon name="ellipsis"/>
      </div>
    </template>
  </navBarTop>
  <div
      style="padding-top: 16px;padding-left: 0.25rem;padding-right: 0.25rem;height: 100vh">
    <van-grid call="van-grid" direction="horizontal" :column-num="2" :border="false">
      <van-grid-item
          v-for="item in data.vaccineData"
          :key="item.id"
          @click="onServiceItem(item)"
      >

        <serviceItemCard :serviceData="item">

        </serviceItemCard>
      </van-grid-item>
    </van-grid>
  </div>
  <div>
    <div v-for="item in data.vaccineData" :key="item.id">
      {{ item.id }}<br/>
      {{ item.serviceName }}<br/>
    </div>
  </div>
</template>

<script setup>
import serviceItemCard from "@/components/ServiceItemCard.vue"
import {onMounted, reactive, watch} from "vue";
import router from "@/router/index.js";
import navBarTop from "@/components/navBarTop.vue";
import booking from "@/stores/petService/booking.js";
import {useRoute} from "vue-router";

const bookingStore = booking()

const vetId = useRoute().params.vetId
console.log("路由参数:" + vetId)

// console.log(sessionStorage.getItem("currentEmployee") + "号医生")

watch(() => bookingStore.VaccineData, (newVal) => {
  if (newVal) {
    data.vaccineData = newVal
  }
})

onMounted(async () => {
  await bookingStore.setVaccineData();
  if (bookingStore.VaccineData) {
    data.vaccineData = bookingStore.VaccineData
  }
})

const onTopLeft = () => {
  window.history.go(-1)
}

const data = reactive({
  vaccineData: null
})

const onServiceItem = (currentServiceItem) => {
  bookingStore.setCurrentServiceItem(currentServiceItem)
  console.log("进入" + currentServiceItem.id + "号疫苗服务详情页")
  router.push("/vaccineInfo/" + vetId + "/" + currentServiceItem.id)
}


</script>

<style scoped>

</style>

<style>

.van-grid {
  --van-grid-item-content-background: none;
}
</style>