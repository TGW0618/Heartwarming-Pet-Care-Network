<!--<template>-->
<!--  <div>-->
<!--    <OrdersComponents :OrderList="fosterOrderList">-->
<!--      <template #title>-->
<!--        <h3>寄养订单</h3>-->
<!--      </template>-->
<!--    </OrdersComponents>-->
<!--  </div>-->
<!--</template>-->

<!--<script setup>-->
<!--import OrdersComponents from "@/Backend/components/OrdersComponents.vue";-->

<!--defineProps({-->
<!--  fosterOrderList: {-->
<!--    type: Array,-->
<!--    default: () => []-->
<!--  }-->
<!--})-->


<!--</script>-->


<!--<style scoped>-->

<!--</style>-->

<template>
  <div>
    <OrdersComponents :OrderList="fosterOrders">
      <template #title>
        <h3>寄养订单</h3>
      </template>
    </OrdersComponents>
  </div>
</template>

<script setup>
import OrdersComponents from "@/Backend/components/OrdersComponents.vue";
import {computed} from 'vue'
import {useOrderStore} from '@/Backend/stores/order'

const orderStore = useOrderStore()
const fosterOrders = computed(() => orderStore.fosterOrders)
</script>

<style scoped>
</style>
