<template>
  <div>


    <div class="DataStatistics">
      <el-row :gutter="20">
        <el-col :span="6" v-for="item in 4">
          <div>
            <SquareComponents style="height: 210px"/>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="DataStatistics">
      <el-row :gutter="20">
        <el-col :span="12" v-for="item in 2">
          <div>
            <SquareComponents style="height: 250px"/>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
  <div>

  </div>

</template>

<script setup>

import SquareComponents from "@/Backend/components/DataStatistics/SquareComponents.vue";
import {reactive} from "vue";


const data = reactive({
  users: [
    {
      id: 1,
      username: 'admin',
      nickname: '管理员',
      role: 'admin',
    },
    {
      id: 2,
      username: 'user',
      nickname: '普通用户',
      role: 'user',
    },
    {
      id: 3,
      username: 'staff',
      nickname: '工作人员',
      role: 'staff',
    },
    {
      id: 4,
      username: 'guest',
      nickname: '访客',
      role: 'guest',
    }],
  orders: [
    {
      id: 1,
      username: 'admin',
      nickname: '管理员',
      role: 'admin',
      price: 10000,
    },
    {
      id: 2,
      username: 'user',
      nickname: '普通用户',
      role: 'user',
      price: 100,
    },
    {
      id: 3,
      username: 'staff',
      nickname: '工作人员',
      role: 'staff',
      price: 100,
    },
    {
      id: 4,
      username: 'guest',
      nickname: '访客',
      role: 'guest',
      price: 100,
    },
    {
      id: 5,
      username: 'admin',
      nickname: '管理员',
      role: 'admin',
      price: 100,
    },
    {
      id: 6,
      username: 'user',
      nickname: '普通用户',
      role: 'user',
      price: 100,
    },
    {
      id: 7,
      username: 'staff',
      nickname: '工作人员',
      role: 'staff',
      price: 100,
    },
  ]
})

const getUsersCount = data.users.length
const getOrdersCount = data.orders.length
const getOrdersPrice = data.orders.reduce((acc, cur) => acc + cur.price, 0)


</script>


<style scoped>
.DataStatistics {
  padding: 20px;
  background-color: #ffffff;
  margin-top: 15px;
  margin-bottom: 15px;
  border-radius: 8px
}

</style>