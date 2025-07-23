<template>
  <div>
    <h1>数据展示</h1>
    <div>
      <div class="avatar-wrapper">
        <el-avatar :src="data.user.avatar" class="user-avatar">
          <DefaultAvatar/>
        </el-avatar>
      </div>
      账号：{{ data.user.username }}<br>
      密码：{{ data.user.password }}<br>
      姓名：{{ data.user.realName }}<br>
      性别：{{ data.user.sex }}<br>
      手机号：{{ data.user.phone }}<br>
      邮箱：{{ data.user.email }}<br>
      角色：{{ data.user.role }}<br>
      创建时间：{{ data.user.createTime }}<br>

    </div>
    <!-- 修改密码-->
    <div>
      <el-button type="primary" @click="upPassword">修改密码</el-button>
    </div>
    <!--  退出登录-->
    <div>
      <el-button type="info" @click="loginOut">退出登录</el-button>
    </div>
  </div>

  <div>

  </div>

</template>


<script setup>
import {ElMessage, ElMessageBox} from "element-plus";
import router from "@/Common/router/index.js";
import {reactive} from "vue";
import DefaultAvatar from "@/Common/components/DefaultAvatar.vue";

const data = reactive({
  user: JSON.parse(localStorage.getItem("petSysUser"))
})

const loginOut = () => {
  ElMessageBox.confirm("确定要退出登录吗？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "info",
  }).then(() => {
    localStorage.removeItem("petSysUser");
    ElMessage.success("退出成功")
    router.push("/admin/login")
  }).catch(() => {
    ElMessage.info("取消退出")
  })
};

const upPassword = () => {
  alert("修改密码")
};

console.log(data.user)
</script>


<style scoped>
.avatar-wrapper {
  padding: 5px;
  width: 120px;
  height: 120px;
}

.user-avatar {
  background-color: white;
  width: 100%;
  height: 100%;
}


</style>