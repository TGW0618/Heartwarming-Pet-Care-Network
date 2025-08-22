<!--横条数据组件-->
<!--左标题-----右数据 >-->
<template>
  <div class="horizontal-bar-data">
    <van-row type="flex" justify="space-between" align="center">
      <van-col>
        <span>
          {{ props.title }}
        </span>
      </van-col>
      <van-col style="display: flex; align-items: center;">
        <span v-if="props.isText">
          {{ props.isText }}
        </span>
        <span v-else-if="props.isImg">
          <img class="avatar-image" :src=props.isImg alt="头像">
        </span>
        &nbsp;&nbsp;
        <van-icon name="arrow"/>
      </van-col>
    </van-row>
  </div>
</template>

<script setup>

const props = defineProps({
  title: {
    type: String,
    default: ''
  },
  isText: {
    type: String,
    default: ''
  },
  isImg: {
    type: String,
    default: ''
  },

})

</script>

<style scoped>
.horizontal-bar-data {
  align-items: center;
}

.avatar-image {
  width: 1.3rem;
  height: 1.3rem;
  border-radius: 6px;
  object-fit: cover;
  vertical-align: middle;
}
</style>
