<template>
  <div style="width: 10rem;height: 100vh;background-color: #ffffff">
    <div>
      <router-view/>
    </div>
    <div style="position: fixed;bottom: 0;left: 0;right: 0;">
        <nav-bar-bottom />
    </div>
  </div>

</template>

<script setup>
import navBarBottom from "@/components/navBarBottom.vue"
</script>
