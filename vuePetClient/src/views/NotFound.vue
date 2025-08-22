<template>
  <navBarTop title="404">
    <template #left>
      <div @click="onClickLeft">
        <slot>
          <van-icon name="arrow-left"/>
        </slot>
      </div>
    </template>
  </navBarTop>
  <div style="display: flex;width: 100%;height: 100vh;">
    <img src="@/assets/img/404.svg" alt="404" style="width: 100%;height: auto"/>
  </div>
</template>

<script setup>
import navBarTop from "@/components/navBarTop.vue";

const onClickLeft = () => {
  window.history.back();
};
</script>
<style scoped>

</style>