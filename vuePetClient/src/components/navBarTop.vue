<template>
  <!--    头部开始-->
  <div>
    <van-nav-bar
        class="custom-nav-bar"
        left-arrow
        text-light
        :title="props.title"
        :left-text="props.leftText"
        :right-text="props.rightText"
        @click-left="onClickLeft"
    >

    </van-nav-bar>
  </div>
  <!--    头部结束-->
</template>

<script setup>

const props = defineProps({
  title: {
    type: String,
    default: '标题'
  },
  leftText: {
    type: String,
    default: ''
  },
  rightText: {
    type: String,
    default: ''
  }
});

const onClickLeft = () => history.back();
</script>


<style scoped>

</style>