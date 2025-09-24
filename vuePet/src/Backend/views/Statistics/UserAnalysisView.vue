<template>
  <div>
    <div>
      <el-row :gutter="15">
        <!--    用户增长趋势 (折线图)-->
        <el-col :span="8" class="chat_col">
          <user-zeng-zhang>
          </user-zeng-zhang>
        </el-col>
        <!--         用户角色分布 (饼图)-->
        <el-col :span="8" class="chat_col">
          <user-role-fen-bu/>
        </el-col>
        <!--         用户消费排名 (表格)-->
        <el-col :span="8" class="chat_col">
          <user-xiao-fei-table/>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script setup>
import UserZengZhang from "@/Backend/views/Statistics/components/UserZengZhang.vue";
import UserRoleFenBu from "@/Backend/views/Statistics/components/UserRoleFenBu.vue";
import UserXiaoFeiTable from "@/Backend/views/Statistics/components/UserXiaoFeiTable.vue";

</script>

<style scoped>
.chat_col {
  padding: 10px;
  box-sizing: border-box;
}
</style>