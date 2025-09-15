<!--医生列表-->
<template>
  <div>
    <div>
      <navBarTop title="预约医生">
        <template #left>
          <div>
            <van-icon name="arrow-left" @click="()=>{router.go(-1)}"/>
          </div>
        </template>
      </navBarTop>
    </div>

    <div>
      <div>
        <div
            v-for="item in data.vetDataList"
            :key="item.id"
            class="vet-item"
            @click="onSubmitVet(item.id)"
        >
          <LImgRTextCard style="margin-bottom: 8px;"
                         :serviceItemImgUrl="item.avatar" >
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
  </div>
</template>

<script setup>
import {ref, computed, onMounted, reactive} from 'vue';
import {useRoute, useRouter} from 'vue-router';
import navBarTop from "@/components/navBarTop.vue";
import request from "@/utils/request.js";
import LImgRTextCard from "@/components/LImgRTextCard.vue";

const route = useRoute();
const router = useRouter();

// 数据
const data = reactive({
  vetDataList: []
})
const loading = ref(false);

// 计算属性：根据参数显示不同描述
const specialtyType = computed(() => {
  return route.params.specialty;
});


// 获取医生列表
const fetchVetList = () => {
  request.get('/sysUser/getAllSysUserEmployee', {
    params: {
      role: 'veterinarian',
      roleSpecialty: specialtyType.value,  // 根据路由参数筛选医生专业
      status: 1  // 只获取在职医生
    }
  }).then((res) => {
    if (res.code === 200) {
      data.vetDataList = res.data;
      loading.value = true;
    }
  })
}


onMounted(() => {
  fetchVetList();
});

const onSubmitVet = (id) => {
  router.push("/bookingsVetOrder/" + id)
  console.log("选择了" + id + "号医生,预约下单页")
}
</script>

<style scoped>


</style>
