<!--<template>-->
<!--  <div>-->
<!--    <OrdersComponents :OrderList="medicalOrderList">-->
<!--      <template #title>-->
<!--        <h3>医疗订单</h3>-->
<!--      </template>-->
<!--    </OrdersComponents>-->
<!--  </div>-->
<!--</template>-->

<!--<script setup>-->
<!--import OrdersComponents from "@/Backend/components/OrdersComponents.vue";-->

<!--defineProps({-->
<!--  medicalOrderList: {-->
<!--    type: Array,-->
<!--    default: () => []-->
<!--  }-->
<!--})-->


<!--</script>-->


<!--<style scoped>-->

<!--</style>-->

<template>
  <div>
    <OrdersComponents :OrderList="medicalOrders">
      <template #title>
        <h3>医疗订单</h3>
      </template>
    </OrdersComponents>
  </div>
</template>

<script setup>
import OrdersComponents from "@/Backend/components/OrdersComponents.vue";
import {computed} from 'vue'
import {useOrderStore} from '@/Backend/stores/order'

const orderStore = useOrderStore()
const medicalOrders = computed(() => orderStore.medicalOrders)
</script>

<style scoped>
</style>
