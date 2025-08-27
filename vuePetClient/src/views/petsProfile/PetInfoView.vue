<!-- 宠物详情 -->
<template>
  <navBarTop title="宠物详情">
    <template #left>
      <div @click="onClickLeft">
        <slot>
          <van-icon name="arrow-left"/>
        </slot>
      </div>
    </template>
    <template #right>
      <div @click="showPopup">
        <van-icon name="ellipsis"/>
      </div>

    </template>
  </navBarTop>

  <div v-if="currentPetInfo" class="pet-detail-container">
    <div class="pet-header">
      <img
          :src="currentPetInfo.avatar || defaultAvatar"
          :alt="currentPetInfo.petName"
          class="pet-avatar"
          @error="handleImageError"
      />
      <h2>{{ currentPetInfo.petName }}</h2>
      <p class="pet-type">{{ currentPetInfo.petType }}</p>
    </div>

    <div class="pet-details">
      <van-cell-group>
        <van-cell title="宠物ID" :value="currentPetInfo.id.toString()"/>
        <van-cell title="品种" :value="currentPetInfo.breed || '未知'"/>
        <van-cell title="性别" :value="getGenderText(currentPetInfo.gender)"/>
        <van-cell title="出生日期" :value="currentPetInfo.birthDate || '未知'"/>
        <van-cell title="体重" :value="(currentPetInfo.weight ? currentPetInfo.weight + 'kg' : '未知')"/>
        <van-cell title="健康状态" :value="currentPetInfo.healthStatus || '未知'"/>
        <van-cell
            title="过敏史"
            :value="currentPetInfo.allergies || '无'"
            :label="currentPetInfo.allergies"
        />
      </van-cell-group>
    </div>
  </div>

  <div v-else class="loading-container">
    <van-loading size="24px">加载中...</van-loading>
  </div>

  <!-- 右上角单击弹窗 -->
  <div>
    <!-- 弹窗 -->
    <van-popup
        v-model:show="showRight"
        round
        overlay:false
        class="custom-popup"
    >
      <van-cell @click="delPetInfo" title="删除该宠物资料" center style="color:#e5253d;">
        <!-- 使用 right-icon 插槽来自定义右侧图标 -->
        <template #right-icon>
          <van-icon name="delete-o"/>
        </template>
      </van-cell>
      <van-cell @click="editPetInfo" title="修改宠物信息" center>
        <!-- 使用 right-icon 插槽来自定义右侧图标 -->
        <template #right-icon>
          <van-icon name="edit"/>
        </template>
      </van-cell>
    </van-popup>
  </div>

</template>

<script setup>
import navBarTop from '@/components/navBarTop.vue'
import {computed, onMounted, ref} from 'vue'
import {useRoute} from 'vue-router'
import petsInfoStores from "@/stores/petsInfoStores.js"
import defaultAvatarImage from '@/assets/img/default-avatar.png'
import {showConfirmDialog} from "vant";
import router from "@/router/index.js";

// 使用 useRoute 获取路由参数
const route = useRoute()
const petId = route.params.id ? parseInt(route.params.id) : null

const petsInfo = petsInfoStores()
const defaultAvatar = defaultAvatarImage

const currentPetInfo = computed(() => petsInfo.petsInfoByIdData)

// 右弹窗显示
const showRight = ref(false);

onMounted(async () => {
  if (petId) {
    await petsInfo.getPetsInfoById(petId)
  } else {
  }
})

// 处理图片加载失败
const handleImageError = (event) => {
  event.target.src = defaultAvatar
}

// 性别文本转换
const getGenderText = (gender) => {
  const genderMap = {
    'male': '雄性',
    'female': '雌性',
    'unknown': '未知'
  }
  return genderMap[gender] || '未知'
}

const onClickLeft = () => {
  router.push("/petsProfile");
}

//右上角弹窗事件
const showPopup = () => {
  showRight.value = true
}

// 删除宠物信息
const delPetInfo = () => {
  showRight.value = false
  showConfirmDialog({
    message: '你确定要删除该宠物信息吗?',
    confirmButtonText: '删除该宠物信息',
    closeOnClickOverlay: true,
    className: 'pet-delete-dialog' // 添加自定义类名
  })
      .then(() => {
        // on confirm
        petsInfo.deletePetsInfoById(petId)
        router.push('/petsProfile')

      })
      .catch(() => {
        // on cancel

      });

}

// 修改宠物信息
const editPetInfo = () => {
  router.push(`/petsProfileEdit/${petId}`)
}
</script>

<style scoped>
.pet-detail-container {
  padding: 16px;
}

.pet-header {
  text-align: center;
  margin-bottom: 20px;
  padding-bottom: 20px;
  border-bottom: 1px solid #f0f0f0;
}

.pet-avatar {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  object-fit: cover;
  border: 3px solid #ffffff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  margin-bottom: 15px;
}


.pet-header h2 {
  margin: 10px 0 5px 0;
  font-size: 24px;
  color: #333;
}

.pet-type {
  font-size: 16px;
  color: #666;
  background: #f0f5ff;
  display: inline-block;
  padding: 4px 12px;
  border-radius: 16px;
}

.loading-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 200px;
}

.custom-popup {
  width: 70%;
  height: auto;
  position: fixed;
  top: 95px; /* 距离顶部固定位置 */
  right: -100px; /* 距离右侧固定位置 */
  --van-popup-background: rgb(70, 70, 70);
}
</style>

<style>
/* 非scoped样式，用于覆盖组件库样式 */
.pet-delete-dialog .van-dialog__confirm {
  background-color: #ffffff !important;
  font-weight: bold;
  color: #e5253d !important;
}
.pet-delete-dialog .van-dialog__cancel {
  color: #fa233b !important;
  font-weight: bold;
  background-color: #ffffff !important;
}


</style>
