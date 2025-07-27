<template>
  <!--  操作按钮开始-->
  <div>
    <!--      新增-->
    <el-button @click="addSysUsersBtn">新增</el-button>
  </div>
  <hr></hr>
  <!--  操作按钮结束-->

  <!--宠物主人-->
  <div>
    <h2>客户</h2>
    <Owners/>
  </div>
  <hr>
  <!--员工-->
  <div>
    <h2>员工</h2>
    <Employees/>
  </div>
  <!--  表单开始-->
  <div>
    <!--    新增表单开始-->
    <el-dialog
        v-model="data.dialogVisible"
        title="新增"
        width="500"

    >
      <div>
        <el-form :model="data.addSysUsersForm" :rules="addSysUsersFormRules" ref="addSysUsersFormRef"
                 label-width="80px">
          <el-form-item label="账号" prop="username">
            <el-input v-model="data.addSysUsersForm.username" type="text"/>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="data.addSysUsersForm.password" type="password"/>
          </el-form-item>
          <el-form-item label="名字" prop="realName">
            <el-input v-model="data.addSysUsersForm.realName" type="text"/>
          </el-form-item>
          <el-form-item label="性别" prop="sex">
            <el-select
                v-model="data.addSysUsersForm.sex"
                class="m-2"
                placeholder="请选择性别"
            >
              <el-option label="男" value="male"></el-option>
              <el-option label="女" value="female"></el-option>
              <el-option label="其他" value="other"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="手机号" prop="phone">
            <el-input v-model="data.addSysUsersForm.phone" type="text"/>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="data.addSysUsersForm.email" type="text"/>
          </el-form-item>
          <el-form-item label="角色" prop="role">
            <el-select v-model="data.addSysUsersForm.role" placeholder="选择角色">
              <el-option
                  v-for="item in optionsRole"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select
                v-model="data.addSysUsersForm.status"
                class="m-2"
                placeholder="请选择状态"
            >
              <el-option label="禁用" value="0"></el-option>
              <el-option label="启用" value="1"></el-option>
            </el-select>
          </el-form-item>

        </el-form>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.dialogVisible = false">关闭</el-button>
          <el-button type="primary" @click="handleAddUser">
            确定
          </el-button>
        </div>
      </template>
    </el-dialog>
    <!--    新增表单结束-->
  </div>
  <!--  表单结束-->


</template>

<script setup>
import Owners from './Users/Owners.vue'
import Employees from './Users/Employees.vue'
import {reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import request from "@/Backend/utils/request.js";

const data = reactive({
  users: null,
  dialogVisible: false,
  addSysUsersForm: {
    username: null,
    password: null,
    realName: null,
    sex: null,
    phone: null,
    email: null,
    role: null,
    status: null,
  },
})

// 添加表单引用
const addSysUsersFormRef = ref()

// 角色选择器
const optionsRole = [
  {
    value: 'admin',
    label: '管理员',
  },
  {
    value: 'owner',
    label: '普通用户',
  },
  {
    value: 'veterinarian',
    label: '医生',
  },
  {
    value: 'foster_staff',
    label: '寄养员',
  },

]

// 添加表单必填选项addSysUsersFormRules
const addSysUsersFormRules = {
  username: [
    {required: true, message: '请输入用户名', trigger: 'blur'},
    {min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur'}
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur'}
  ],
  realName: [
    {required: true, message: '请输入昵称', trigger: 'blur'},
    {min: 2, max: 16, message: '长度在 2 到 16 个字符', trigger: 'blur'}
  ],
  phone: [
    {required: true, message: '请输入手机号', trigger: 'blur'},
    {min: 6, max: 16, message: '长度在 6 到 16 个字符', trigger: 'blur'}
  ],
  role: [
    {required: true, message: '请选择角色', trigger: 'change'},
  ],
  status: [
    {required: true, message: '请选择状态', trigger: 'change'},
  ],

}

// 新增按钮
const addSysUsersBtn = () => {
  console.log('新增按钮')
  // 重置表单数据
  if (addSysUsersFormRef.value) {
    addSysUsersFormRef.value.resetFields()
  }
  data.dialogVisible = true
}
// 添加用户
const handleAddUser = () => {
  // 表单验证
  addSysUsersFormRef.value.validate((valid) => {
    if (valid) {
      console.log('确定添加用户')
      console.log(data.addSysUsersForm)
      request.post('/sysUser/addSysUsers', data.addSysUsersForm).then(res => {
        if (res.code === 200) {
          ElMessage.success('用户添加成功')
          data.dialogVisible = false
        } else {
          ElMessage.error(res.message)
        }
      })

    } else {
      ElMessage.error('请填写完整信息')
      return false
    }
  })
}

</script>

<style scoped>

</style>