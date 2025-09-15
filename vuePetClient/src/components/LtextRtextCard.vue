<template>
  <div class="ltext-rrtext-card">
    <van-row class="card-row">
      <van-col span="12">
        <div class="left-content">
          <slot name="leftText"></slot>
        </div>
      </van-col>
      <van-col span="12">
        <div class="right-content">
          <slot name="rightText"></slot>
        </div>
      </van-col>
    </van-row>
  </div>
</template>

<script setup>
</script>

<style scoped>
.ltext-rrtext-card {
  padding: 12px 16px;
  background-color: var(--van-background-2, #ffffff);
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  margin-bottom: 10px;
  transition: all 0.3s ease;
}

.ltext-rrtext-card:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transform: translateY(-2px);
}

.card-row {
  align-items: center;
}

.left-content {
  text-align: left;
  font-size: 14px;
  font-weight: 500;
}

.right-content {
  text-align: right;
  font-size: 14px;
}

/* 响应式优化 */
@media (max-width: 768px) {
  .ltext-rrtext-card {
    padding: 10px 12px;
    margin-bottom: 8px;
  }

  .left-content,
  .right-content {
    font-size: 13px;
  }
}
</style>
