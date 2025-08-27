<template>
  <!--    头部开始-->
  <div>
    <van-sticky>
      <van-nav-bar
          class="custom-nav-bar"
          left-arrow
          text-light
          :title="props.title"
          :left-text="props.leftText"
          :right-text="props.rightText"
      >
        <template #left>
          <slot name="left">
            {{ props.leftText }}
          </slot>
        </template>
        <template #right>
          <slot name="right">
            {{ props.rightText }}
          </slot>
        </template>
      </van-nav-bar>
    </van-sticky>
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
    default: '<'
  },
  rightText: {
    type: String,
    default: ''
  }
});


</script>


<style scoped>

</style>