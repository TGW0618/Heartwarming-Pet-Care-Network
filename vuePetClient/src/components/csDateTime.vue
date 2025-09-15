<template>
  <div>
    <van-picker-group
        title="预约时间"
        :tabs="['开始时间', '结束时间']"
        @confirm="onConfirm"
        @cancel="onCancel"
    >
      <van-time-picker v-model="startTime" />
      <van-time-picker v-model="endTime" />
    </van-picker-group>


  </div>

</template>
<script setup>
import {ref} from 'vue';
import {showToast} from "vant";

const startTime = ref(['12', '00']);
const endTime = ref(['12', '00']);

const onConfirm = () => {
  showToast(`${startTime.value.join(':')} ${endTime.value.join(':')}`);
};

const onCancel = () => {
  showToast('cancel');
};

</script>

<style scoped>

</style>