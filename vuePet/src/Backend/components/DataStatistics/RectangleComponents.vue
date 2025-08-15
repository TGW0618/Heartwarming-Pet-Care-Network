<!-- RectangleComponents.vue -->
<template>
  <div>
    <div class="rectangle">
      <div class="rectangle_left">
        <div class="icon-container">
          <slot name="icon"></slot>
        </div>
        <div class="title-container">
          <slot name="title"></slot>
        </div>
      </div>
      <div class="rectangle_right">
        <slot name="data"></slot>
      </div>
    </div>
  </div>
</template>

<script setup>


</script>

<style scoped>
/* 样式保持不变 */
.rectangle {
  width: 100%;
  height: 120px;
  background-color: #ffffff;
  border-radius: 8px;
  display: flex;
  color: #8d43ac;
  box-shadow: #f6f7f8 1px 3px 6px 1px;
  transition: all 0.5s ease;
}

.rectangle:hover {
  box-shadow: #eff1f2 1px 3px 6px 1px;
  cursor: pointer;
  transition: all 0.5s ease;
  transform: scale(1.05);
}

.rectangle_left {
  width: 30%;
  height: 100%;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
}

.icon-container {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 5px;
}

.title-container {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 5px;
  font-size: 15px;
  font-weight: bold;
}

.rectangle_right {
  width: 70%;
  height: 100%;
  border-radius: 8px;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 28px;
  font-weight: bold;
  text-align: right;
}
</style>
