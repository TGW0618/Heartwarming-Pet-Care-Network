<!-- RectangleComponents.vue -->
<template>
  <div class="rectangle-wrapper">
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
.rectangle-wrapper {
  width: 100%;
  height: 120px;
}

.rectangle {
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #ffffff 0%, #f8f9fa 100%);
  border-radius: 12px;
  display: flex;
  color: #ae53c0;
  box-shadow: 0 4px 12px rgba(149, 157, 165, 0.1);
  transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
  position: relative;
  overflow: hidden;
}

.rectangle::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 4px;
  background: linear-gradient(90deg, #ad52be, #ffffff);
  transform: scaleX(0);
  transform-origin: left;
  transition: transform 0.4s ease;
}

.rectangle:hover {
  box-shadow: 0 8px 24px rgba(149, 157, 165, 0.2);
  transform: translateY(-4px) scale(1.02);
  border-color: rgba(108, 92, 231, 0.2);
}

.rectangle:hover::before {
  transform: scaleX(1);
}

.rectangle_left {
  width: 50%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  border-radius: 12px 0 0 12px;
  position: relative;
}

.rectangle_left::after {
  content: '';
  position: absolute;
  right: 0;
  top: 20%;
  height: 60%;
  width: 1px;
  background: linear-gradient(to bottom, transparent, rgba(108, 92, 231, 0.2), transparent);
}

.icon-container {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 8px;
  margin-bottom: 4px;
}

.icon-container :deep(svg),
.icon-container :deep(i) {
  font-size: 24px;
  color: #ae53c0;
  transition: all 0.3s ease;
}

.rectangle:hover .icon-container :deep(svg),
.rectangle:hover .icon-container :deep(i) {
  transform: scale(1.1);
  color: #a930c6;
}

.title-container {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 5px 10px;
  font-size: 14px;
  font-weight: 600;
  color: #5f6368;
  letter-spacing: 0.5px;
}

.rectangle_right {
  width: 50%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 28px;
  font-weight: 700;
  text-align: center;
  color: #2d3436;
  padding: 0 15px;
}

.rectangle_right :deep(*) {
  transition: all 0.3s ease;
}

.rectangle:hover .rectangle_right :deep(*) {
  color: #f1e4f4;
  transform: scale(1.05);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .rectangle-wrapper {
    height: 100px;
  }

  .rectangle {
    border-radius: 10px;
  }

  .rectangle_left {
    width: 40%;
    border-radius: 10px 0 0 10px;
  }

  .rectangle_right {
    width: 60%;
    font-size: 24px;
  }

  .title-container {
    font-size: 12px;
  }

  .icon-container :deep(svg),
  .icon-container :deep(i) {
    font-size: 20px;
  }
}

@media (max-width: 480px) {
  .rectangle-wrapper {
    height: 90px;
  }

  .rectangle_right {
    font-size: 20px;
  }

  .title-container {
    font-size: 11px;
  }
}
</style>
