<template>
  <div>
    <div>
      <el-button type="primary"
                 @click="onAddService"
                 text
                 bg
      >
        新增服务
      </el-button>
    </div>
    <!--    弹窗-->
    <div>
      <!-- 新增服务弹窗 -->
      <div>
        <el-dialog
            v-model="dialogVisibleAdd"
            title="新增服务"
            width="700px"
            class="order-edit-dialog"
        >
          <div class="edit-content">
            <el-scrollbar max-height="500px">
              <div class="edit-grid">
                <!-- 服务类型选择卡片 -->
                <div class="info-card">
                  <h3 class="card-title">选择服务类型</h3>
                  <div class="service-type-selection">
                    <el-radio-group v-model="selectedServiceType" @change="handleServiceTypeChange">
                      <el-radio label="foster" border>寄养服务</el-radio>
                      <el-radio label="medical" border>医疗服务</el-radio>
                    </el-radio-group>
                  </div>
                </div>

                <!-- 可编辑信息卡片 -->
                <div class="edit-card">
                  <h3 class="card-title">服务信息</h3>
                  <el-form
                      :model="data.serviceDataFrom"
                      :rules="rules"
                      ref="formRef"
                      label-width="100px"
                      class="edit-form"
                  >
                    <el-row :gutter="20">
                      <el-col :span="24">
                        <el-form-item label="服务名称" prop="serviceName" class="form-item">
                          <el-input
                              v-model="data.serviceDataFrom.serviceName"
                              placeholder="请输入服务名称"
                              class="full-width"
                          />
                        </el-form-item>
                      </el-col>

                      <el-col :span="24">
                        <el-form-item label="服务分类" prop="category" class="form-item">
                          <el-input
                              v-model="data.serviceDataFrom.category"
                              placeholder="请输入服务分类"
                              class="full-width"
                          />
                        </el-form-item>
                      </el-col>

                      <el-col :span="24" v-if="selectedServiceType === 'medical'">
                        <el-form-item label="医疗类型" prop="medicalType" class="form-item">
                          <el-select
                              v-model="data.serviceDataFrom.medicalType"
                              placeholder="请选择医疗类型"
                              class="full-width"
                          >
                            <el-option label="疫苗服务" value="vacation"></el-option>
                            <el-option label="诊疗服务" value="diagnosis"></el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>

                      <el-col :span="12">
                        <el-form-item label="服务价格" prop="price" class="form-item">
                          <el-input
                              v-model.number="data.serviceDataFrom.price"
                              placeholder="请输入服务价格"
                              class="full-width"
                          >
                            <template #prefix>¥</template>
                          </el-input>
                        </el-form-item>
                      </el-col>

                      <el-col :span="12" v-if="selectedServiceType === 'medical'">
                        <el-form-item label="预计时长" prop="duration" class="form-item">
                          <el-input
                              v-model.number="data.serviceDataFrom.duration"
                              placeholder="请输入预计时长"
                              class="full-width"
                          >
                            <template #append>分钟</template>
                          </el-input>
                        </el-form-item>
                      </el-col>

                      <el-col :span="24">
                        <el-form-item label="服务状态" prop="status" class="form-item">
                          <el-select
                              v-model="data.serviceDataFrom.status"
                              placeholder="请选择服务状态"
                              class="full-width"
                          >
                            <el-option label="下架" :value="0"></el-option>
                            <el-option label="上架" :value="1"></el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>

                      <el-col :span="24">
                        <el-form-item label="服务描述" prop="description" class="form-item">
                          <el-input
                              v-model="data.serviceDataFrom.description"
                              type="textarea"
                              :rows="4"
                              placeholder="请输入服务描述"
                              class="full-width"
                          />
                        </el-form-item>
                      </el-col>

                      <el-col :span="24">
                        <el-form-item label="服务图片" prop="imageUrl" class="form-item">
                          <div class="image-upload-container">
                            <el-upload
                                class="avatar-uploader"
                                action="http://localhost:8083/files/upload"
                                :show-file-list="false"
                                :on-success="handleAvatarSuccess"
                                :on-error="handleAvatarError"
                                :before-upload="beforeAvatarUpload"
                            >
                              <img
                                  v-if="data.serviceDataFrom.imageUrl"
                                  :src="data.serviceDataFrom.imageUrl"
                                  alt="服务图片"
                                  class="edit-image"
                              />
                              <el-icon v-else class="avatar-uploader-icon">
                                <Plus/>
                              </el-icon>
                            </el-upload>
                            <div class="upload-tip">点击上传服务图片，支持jpg/png格式</div>
                          </div>
                        </el-form-item>
                      </el-col>
                    </el-row>
                  </el-form>
                </div>
              </div>
            </el-scrollbar>
          </div>

          <template #footer>
            <div class="dialog-footer">
              <el-button @click="handleCancel" class="cancel-btn">取消</el-button>
              <el-button
                  type="primary"
                  @click="handleAdd"
                  class="confirm-btn"
                  :loading="submitLoading"
              >
                {{ submitLoading ? '提交中...' : '确认新增' }}
              </el-button>
            </div>
          </template>
        </el-dialog>
      </div>
    </div>
  </div>
</template>

<script setup>
import {Plus} from "@element-plus/icons-vue";
import {reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import useServiceStore from "@/Backend/stores/service.js"

const formRef = ref(null);
const dialogVisibleAdd = ref(false);
const selectedServiceType = ref('foster');
const submitLoading = ref(false);

const data = reactive({
  serviceDataFrom: {
    serviceName: '',
    serviceType: 'foster',
    medicalType: '',
    category: '',
    price: '',
    duration: '',
    status: 0,
    description: '',
    imageUrl: ''
  },
});
const serviceStore = useServiceStore();

// 表单验证规则
const rules = {
  serviceName: [
    {required: true, message: '请输入服务名称', trigger: 'blur'},
    {min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur'}
  ],
  category: [
    {required: true, message: '请输入服务分类', trigger: 'blur'},
    {min: 2, max: 30, message: '长度在 2 到 30 个字符', trigger: 'blur'}
  ],
  medicalType: [
    {required: true, message: '请选择医疗类型', trigger: 'change'}
  ],
  price: [
    {required: true, message: '请输入服务价格', trigger: 'blur'},
    {type: 'number', min: 0, message: '价格必须大于等于0', trigger: 'blur'}
  ],
  duration: [
    {type: 'number', min: 0, message: '时长必须大于等于0', trigger: 'blur'}
  ],
  status: [
    {required: true, message: '请选择服务状态', trigger: 'change'}
  ],
  description: [
    {required: true, message: '请输入服务描述', trigger: 'blur'},
    {min: 5, max: 500, message: '长度在 5 到 500 个字符', trigger: 'blur'}
  ]
};

// 打开新增服务弹窗
const onAddService = () => {
  // 重置表单数据
  resetForm();
  dialogVisibleAdd.value = true;
};

// 重置表单
const resetForm = () => {
  data.serviceDataFrom = {
    serviceName: '',
    serviceType: 'foster',
    medicalType: '',
    category: '',
    price: '',
    duration: '',
    status: 0,
    description: '',
    imageUrl: ''
  };
  selectedServiceType.value = 'foster';
  if (formRef.value) {
    formRef.value.resetFields();
  }
};

// 处理服务类型变化
const handleServiceTypeChange = (value) => {
  data.serviceDataFrom.serviceType = value;
  // 如果切换为寄养服务，清空医疗类型
  if (value === 'foster') {
    data.serviceDataFrom.medicalType = '';
  }
};

// 头像上传成功处理
const handleAvatarSuccess = (response) => {
  if (response.code === 200) {
    data.serviceDataFrom.imageUrl = response.data;
    ElMessage.success('图片上传成功');
  } else {
    ElMessage.error('图片上传失败: ' + response.message);
  }
};

// 头像上传失败处理
const handleAvatarError = (error) => {
  ElMessage.error('图片上传失败: ' + error.message);
};

// 上传前检查
const beforeAvatarUpload = (file) => {
  const isJPG = file.type === 'image/jpeg' || file.type === 'image/png';
  const isLt2M = file.size / 1024 / 1024 < 2;

  if (!isJPG) {
    ElMessage.error('上传头像图片只能是 JPG/PNG 格式!');
  }
  if (!isLt2M) {
    ElMessage.error('上传头像图片大小不能超过 2MB!');
  }
  return isJPG && isLt2M;
};

// 取消操作
const handleCancel = () => {
  dialogVisibleAdd.value = false;
};

// 新增服务
const handleAdd = async () => {
  if (!formRef.value) return;

  try {
    await formRef.value.validate();

    submitLoading.value = true;

    // 提交数据
    const serviceData = data.serviceDataFrom;
    console.log(serviceData)

    // 调用API提交数据
    await serviceStore.addServiceItem(serviceData);

    dialogVisibleAdd.value = false;


  } catch (error) {
    console.error('表单验证失败:', error);
    ElMessage.error('请检查表单填写是否正确');
  } finally {
    submitLoading.value = false;
  }
};

</script>

<style scoped>
/* 服务类型选择样式 */
.service-type-selection {
  padding: 20px 0;
  text-align: center;
}

:deep(.el-radio.is-bordered) {
  margin-right: 20px;
}

:deep(.el-radio.is-bordered.is-checked) {
  border-color: #b056c3;
}

:deep(.el-radio__input.is-checked .el-radio__inner) {
  border-color: #b056c3;
  background: #b056c3;
}

/* 图片上传样式 */
.image-upload-container {
  text-align: center;
  height: 250px;
  width: 250px;
}

.avatar-uploader .avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 250px;
  height: 178px;
  text-align: center;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .avatar-uploader-icon:hover {
  border-color: #b056c3;
}

.upload-tip {
  color: #999;
  font-size: 12px;
  margin-top: 10px;
}

.edit-image {
  width: 100%;
  height: 178px;
  border-radius: 8px;
}

/* 继承之前定义的样式 */
.detail-btn,
.edit-btn {
  background-color: #f0e6f5;
  border-color: #d9b3e6;
  color: #6a2c82;
  margin-right: 5px;
}

.detail-btn:hover,
.edit-btn:hover {
  background-color: #e6d4f0;
  border-color: #c9a2d9;
}

.order-edit-dialog {
  --el-dialog-title-font-size: 18px;
  --el-dialog-title-text-color: #6a2c82;
}

.order-edit-dialog :deep(.el-dialog__header) {
  border-bottom: 1px solid #e6d4f0;
}

.edit-content {
  padding: 20px 10px;
}

.edit-grid {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.info-card,
.edit-card {
  background: #fafafa;
  border-radius: 8px;
  padding: 20px;
  border: 1px solid #e6d4f0;
  box-shadow: 0 2px 8px rgba(176, 86, 195, 0.1);
}

.card-title {
  margin: 0 0 15px 0;
  padding: 0;
  color: #6a2c82;
  font-size: 16px;
  font-weight: 600;
  border-left: 4px solid #b056c3;
  padding-left: 10px;
}

.edit-form {
  width: 100%;
}

.form-item {
  margin-bottom: 20px;
}

.full-width {
  width: 100%;
}

:deep(.el-form-item__label) {
  color: #666;
  font-weight: 500;
}

.cancel-btn {
  background-color: #f5f5f5;
  border-color: #dcdfe6;
  color: #606266;
}

.cancel-btn:hover {
  background-color: #e6e6e6;
  border-color: #c0c4cc;
}

.confirm-btn {
  background-color: #b056c3;
  border-color: #b056c3;
}

.confirm-btn:hover {
  background-color: #9a4aa9;
  border-color: #9a4aa9;
}

:deep(.el-dialog__body) {
  padding: 15px 20px;
}

:deep(.el-dialog__header) {
  padding: 20px 20px 10px;
}

@media (max-width: 768px) {
  .edit-grid {
    gap: 15px;
  }

  .info-card,
  .edit-card {
    padding: 15px;
  }
}
</style>
