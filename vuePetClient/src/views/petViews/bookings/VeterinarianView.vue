<!--veterinarian医生页-->
<template>
  <div>
    <div>
      <navBarTop title="预约医生">
        <template #left>
          <div>
            <van-icon name="arrow-left" @click="()=>{router.go(-1)}"/>
          </div>
        </template>
        <template #right>
          <div @click="()=>{router.replace('/home')}">
            <van-icon name="home-o" />
          </div>
        </template>
      </navBarTop>
    </div>

    <div>
      <!--      医生卡片-->
      <div v-for="item in data.veterinarianData" style="padding-top: 0.1rem">
        <LImgRTextCard :serviceItemImgUrl="item.avatar" @click="onSubmitVet(item)">
          <template #leftTextTop>
            {{ item.realName }}
          </template>
          <template #rightTextTop>
            {{ item.sex }}
          </template>
          <template #content>
            擅长领域:{{ item.description }}
          </template>
        </LImgRTextCard>
      </div>
    </div>
  </div>
</template>

<script setup>
import LImgRTextCard from "@/components/LImgRTextCard.vue";
import router from "@/router/index.js";
import navBarTop from "@/components/navBarTop.vue";
import {onMounted, reactive, toRefs} from "vue";
import request from "@/utils/request.js";
import {useRoute} from "vue-router";
import booking from "@/stores/petService/booking.js";

const roleSpecialty = useRoute().params.roleSpecialty

const bookingStore = booking()

const data = reactive({
  veterinarianData: [],
})

onMounted(() => {
  request.get("/sysUser/getAllSysUserEmployee", {
    params: {
      roleSpecialty: roleSpecialty,
      role: "veterinarian"
    }
  }).then((res) => {
    if (res.code === 200) {
      data.veterinarianData = res.data
    }
  })

})

const onSubmitVet = (currentEmployee) => {
  bookingStore.setCurrentEmployee(currentEmployee)
  router.push("/vaccine/" + currentEmployee.id)
  console.log("选择了" + currentEmployee.id + "号医生,进入服务页")
}
</script>

<style scoped>

</style>