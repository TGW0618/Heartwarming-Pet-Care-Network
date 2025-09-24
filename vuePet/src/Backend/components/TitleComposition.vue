<template>
  <div class="titleBox">
    <p class="title">
      <span class="titleIcon">
        <slot name="icon"></slot>
      </span>
      <span class="titleText">
        <slot name="title"></slot>
      </span>
    </p>
  </div>
</template>
<script setup>

</script>

<style scoped>
.titleBox {
  width: 100%;
  height: 20px;
}

.title {
  margin: 0;
  font-size: 12px;
  padding-left: 5px;
  line-height: 20px;
}

.titleIcon {
  float: left;
  padding-right: 3px;
}
</style>