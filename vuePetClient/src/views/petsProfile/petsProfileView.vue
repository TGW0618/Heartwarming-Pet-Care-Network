<!--宠物信息页-->
<template>
  <navBarTop title="宠物信息">
    <template #left>
      <div @click="onClickLeft">
        <slot>
          <van-icon name="arrow-left"/>
        </slot>
      </div>
    </template>
    <template #right>
      <div @click="goToAddPet">
        <van-icon name="plus"/>
        添加
      </div>
    </template>
  </navBarTop>

  <div v-if="hasPetsData" class="container">
    <van-row>
      <van-col
          v-for="item in petsInf.petsInfoData"
          :key="item.id"
          :span="8"
          @click="goToPetInfo(item.id)"
          class="pet-col"
      >
        <petsInfo
            :image-url="item.avatar || defaultAvatar"
        >
          <template #leftText>
            {{ item.petName }}
          </template>
          <template #rightText>
            {{ item.healthStatus }}
          </template>
        </petsInfo>
      </van-col>
    </van-row>
  </div>

  <div v-else>
    <van-empty description="暂无宠物数据" />
  </div>
</template>

<script setup>
import navBarTop from "@/components/navBarTop.vue"
import petsInfo from "@/components/petsInfo.vue"
import petsInfoStores from "@/stores/petsInfoStores.js"
import {onMounted, computed} from "vue"
import {useRouter} from "vue-router"
import defaultAvatarImage from '@/assets/img/default-avatar.png'

const petsInf = petsInfoStores()
const router = useRouter()

// 默认头像（静态引入，非响应式）
const defaultAvatar = defaultAvatarImage

// 使用计算属性判断是否有宠物数据
const hasPetsData = computed(() => {
  return petsInf.petsInfoData && petsInf.petsInfoData.length > 0
})

onMounted(() => {
  petsInf.getPetsInfoData()
})

const onClickLeft = () => {
  router.go(-1)
}

// 跳转到宠物详情页面
const goToPetInfo = (petId) => {
  if (petId == null) return
  router.push(`/petInfo/${petId}`)
}

// 跳转到添加宠物页面
const goToAddPet = () => {
  router.push('/petsProfileAdd')
}
</script>

<style scoped>
.container {
  padding: 10px;
  background-color: #f5f5f5;
  min-height: calc(100vh - 60px);
}

.pet-col {
  cursor: pointer;
  margin-bottom: 10px;
  transition: all 0.2s ease-in-out;
  padding: 0 5px; /* 左右各5px，合计10px间距 */
}

.pet-col:active {
  opacity: 0.8;
}

/* 第一列左边距调整 */
.pet-col:nth-child(3n+1) {
  padding-left: 0;
  padding-right: 5px;
}

/* 中间列左右间距保持 */
.pet-col:nth-child(3n+2) {
  padding: 0 5px;
}

/* 最后一列右边距调整 */
.pet-col:nth-child(3n) {
  padding-left: 5px;
  padding-right: 0;
}
</style>
