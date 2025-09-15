<template>
  <el-scrollbar height="80vh">
    <div>
      <!-- 房间数据信息 -->
      <div>
        <div>总房间数: {{ data.roomsList.length }}</div>
        <div>可用房间数: {{ data.availableRooms }}</div>
        <div>已用房间数: {{ data.occupiedRooms }}</div>
      </div>

      <!-- 房间列表 -->
      <div>
        <el-table :data="data.roomsList" stripe style="width: 100%">
          <el-table-column prop="roomNumber" label="房间号" width="150"/>
          <el-table-column prop="roomType" label="类型" width="180"/>
          <el-table-column prop="capacity" label="容量" width="100"/>
          <el-table-column prop="status" label="状态" width="180"/>
          <el-table-column prop="roomName" label="房间名称" width="180"/>
          <el-table-column label="操作" width="180">
            <template #default="scope">
              <el-button @click="handleAssign(scope.row)">分配</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </el-scrollbar>
</template>

<script setup>
import {onMounted, reactive} from "vue"
import request from "@/Backend/utils/request.js"

const data = reactive({
  roomsList: [],
  availableRooms: 0,
  occupiedRooms: 0
})

const getAllRooms = () => {
  request.get('/getAllRooms').then(res => {
    if (res.code === 200) {
      console.log(res.data)
      data.roomsList = res.data
      // 计算统计信息
      data.availableRooms = res.data.filter(room => room.status === 'available').length
      data.occupiedRooms = res.data.filter(room => room.status === 'occupied').length
    }
  })
}

const handleAssign = (row) => {
  console.log('分配房间:', row)
  // 添加分配逻辑
}

onMounted(() => {
  getAllRooms()
})
</script>

<style scoped>

</style>
