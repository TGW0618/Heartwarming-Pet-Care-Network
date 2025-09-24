<template>
  <div class="infoBox">
    <p style="margin: 0;padding-left: 3px">
      <slot name="text"></slot>
    </p>
  </div>
</template>
<script setup>

</script>

<style scoped>
.infoBox {
  font-size: 16px;
}

infoBox p {
  transition: all 0.3s linear;
}

.infoBox:hover p {
  border-radius: 3px;
  box-shadow: rgba(106, 44, 130, 0.15) 1px 2px 6px 1px;
  transition: all 0.3s linear;
}

.infoBox:active p {
  background-color: #af54c2;
  box-shadow: rgba(106, 44, 130, 0.15) 1px 2px 6px 1px;
  transition: 0.5s;
  color: white;
  transform: scale(0.95);

}
</style>