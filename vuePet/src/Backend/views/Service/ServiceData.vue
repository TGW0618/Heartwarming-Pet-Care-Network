<template>
  <div>
    <div style="margin-bottom: 4.94vh">
      <el-table :data="data.tableData" stripe height="70vh" style="width: 100%">
        <el-table-column sortable prop="id" label="ID" width="80"/>
        <el-table-column prop="imageUrl" label="图片" width="120">
          <template #default="scope">
            <img v-if="scope.row.imageUrl" :src="scope.row.imageUrl" class="service-image" alt="服务图片"/>
            <span v-else>无图片</span>
          </template>
        </el-table-column>
        <el-table-column prop="serviceName" label="服务名称"/>
        <el-table-column sortable prop="serviceType" label="服务类型"/>
        <el-table-column sortable prop="price" label="价格"/>
        <el-table-column sortable prop="status" label="状态">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'info'">
              {{ scope.row.status === 1 ? '上架' : '下架' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="240" fixed="right">
          <template #default="scope">
            <el-button size="small" @click="handleDetail(scope.row)" class="detail-btn">
              <el-icon>
                <View/>
              </el-icon>
              详情
            </el-button>
            <el-button size="small" @click="handleEdit(scope.row)" class="edit-btn">
              <el-icon>
                <Edit/>
              </el-icon>
              编辑
            </el-button>
            <el-button size="small" @click="handleDelete(scope.row.id)" class="delete-btn">
              <el-icon>
                <Delete/>
              </el-icon>
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div>
      <div>
        <el-row>
          <el-col :span="22">
            <!-- 分页组件 -->
            <div>
              <el-pagination
                  v-if="data.serviceData"
                  background
                  small
                  layout="prev, pager, next"
                  :total="data.serviceData.total || 0"
                  :current-page="data.serviceData.pageNum || 1"
                  :page-size="data.serviceData.pageSize || 7"
                  @current-change="handlePageChange"
              />
            </div>
          </el-col>
          <el-col :span="2">
            <div>
              <service-add></service-add>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
    <!--    详情编辑弹窗-->
    <div>
      <!--      详情弹窗-->
      <div>
        <el-dialog
            v-model="dialogVisibleDetail"
            title="服务详细信息"
            width="700px"
            class="order-detail-dialog"
        >
          <div v-if="Object.keys(data.serviceDataDetail).length > 0" class="detail-content">
            <el-scrollbar max-height="500px">
              <div class="detail-grid">
                <!-- 服务基本信息 -->
                <div class="detail-section">
                  <h3 class="section-title">服务信息</h3>
                  <el-descriptions :column="2" border size="small">
                    <el-descriptions-item label="服务ID" width="100px">
                      <el-tag type="primary" effect="dark">{{ data.serviceDataDetail.id }}</el-tag>
                    </el-descriptions-item>

                    <el-descriptions-item label="服务名称">
                      <span class="service-name-highlight">{{ data.serviceDataDetail.serviceName }}</span>
                    </el-descriptions-item>

                    <el-descriptions-item label="服务类型">
                      <el-tag :type="getServiceTypeTag(data.serviceDataDetail.serviceType)" size="small">
                        {{ getServiceTypeText(data.serviceDataDetail.serviceType) }}
                      </el-tag>
                    </el-descriptions-item>

                    <el-descriptions-item label="医疗类型" v-if="data.serviceDataDetail.medicalType">
                      <el-tag type="warning" size="small">
                        {{ getMedicalTypeText(data.serviceDataDetail.medicalType) }}
                      </el-tag>
                    </el-descriptions-item>

                    <el-descriptions-item label="服务分类">
                      {{ data.serviceDataDetail.category || '暂无' }}
                    </el-descriptions-item>

                    <el-descriptions-item label="服务价格">
                      <span class="amount-highlight">¥{{ data.serviceDataDetail.price }}</span>
                    </el-descriptions-item>

                    <el-descriptions-item label="预计时长" v-if="data.serviceDataDetail.duration">
                      {{ data.serviceDataDetail.duration }}分钟
                    </el-descriptions-item>

                    <el-descriptions-item label="服务状态">
                      <el-tag :type="data.serviceDataDetail.status === 1 ? 'success' : 'info'">
                        {{ data.serviceDataDetail.status === 1 ? '上架' : '下架' }}
                      </el-tag>
                    </el-descriptions-item>

                    <el-descriptions-item label="创建时间" :span="2">
                      {{ formatDateTime(data.serviceDataDetail.createTime) }}
                    </el-descriptions-item>

                    <el-descriptions-item label="更新时间" :span="2">
                      {{ formatDateTime(data.serviceDataDetail.updateTime) }}
                    </el-descriptions-item>
                  </el-descriptions>
                </div>

                <!-- 服务描述 -->
                <div class="detail-section" v-if="data.serviceDataDetail.description">
                  <h3 class="section-title">服务描述</h3>
                  <div class="description-content">
                    {{ data.serviceDataDetail.description }}
                  </div>
                </div>

                <!-- 服务图片 -->
                <div class="detail-section" v-if="data.serviceDataDetail.imageUrl">
                  <h3 class="section-title">服务图片</h3>
                  <div class="image-preview">
                    <img :src="data.serviceDataDetail.imageUrl" alt="服务图片" class="detail-image"/>
                  </div>
                </div>
              </div>
            </el-scrollbar>
          </div>

          <template #footer>
            <div class="dialog-footer">
              <el-button @click="dialogVisibleDetail = false">关闭</el-button>
            </div>
          </template>
        </el-dialog>
      </div>
      <!-- 编辑弹窗 -->
      <div>
        <el-dialog
            v-model="dialogVisibleEdit"
            title="服务信息编辑"
            width="700px"
            class="order-edit-dialog"
        >
          <div class="edit-content">
            <el-scrollbar max-height="500px">
              <div class="edit-grid">
                <!-- 基本信息卡片 -->
                <div class="info-card">
                  <h3 class="card-title">基本信息</h3>
                  <div class="info-grid">
                    <div class="info-item">
                      <span class="info-label">服务ID:</span>
                      <span class="info-value">{{ data.serviceDataEdit?.id }}</span>
                    </div>
                    <div class="info-item">
                      <span class="info-label">服务类型:</span>
                      <span class="info-value">{{ getServiceTypeText(data.serviceDataEdit?.serviceType) }}</span>
                    </div>
                    <div class="info-item">
                      <span class="info-label">创建时间:</span>
                      <span class="info-value">{{ formatDateTime(data.serviceDataEdit?.createTime) }}</span>
                    </div>
                    <div class="info-item">
                      <span class="info-label">更新时间:</span>
                      <span class="info-value">{{ formatDateTime(data.serviceDataEdit?.updateTime) }}</span>
                    </div>
                  </div>
                </div>

                <!-- 可编辑信息卡片 -->
                <div class="edit-card">
                  <h3 class="card-title">可编辑信息</h3>
                  <el-form :model="data.serviceDataEdit" label-width="100px" class="edit-form">
                    <el-row :gutter="20">
                      <el-col :span="24">
                        <el-form-item label="服务名称" class="form-item">
                          <el-input
                              v-model="data.serviceDataEdit.serviceName"
                              placeholder="请输入服务名称"
                              class="full-width"
                          />
                        </el-form-item>
                      </el-col>

                      <el-col :span="24">
                        <el-form-item label="服务分类" class="form-item">
                          <el-input
                              v-model="data.serviceDataEdit.category"
                              placeholder="请输入服务分类"
                              class="full-width"
                          />
                        </el-form-item>
                      </el-col>

                      <el-col :span="24" v-if="data.serviceDataEdit.medicalType">
                        <el-form-item label="医疗类型" class="form-item">
                          <el-select
                              v-model="data.serviceDataEdit.medicalType"
                              placeholder="请选择医疗类型"
                              class="full-width"
                          >
                            <el-option label="疫苗服务" value="vacation"></el-option>
                            <el-option label="诊疗服务" value="diagnosis"></el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>

                      <el-col :span="12">
                        <el-form-item label="服务价格" class="form-item">
                          <el-input
                              v-model="data.serviceDataEdit.price"
                              placeholder="请输入服务价格"
                              class="full-width"
                          >
                            <template #prefix>¥</template>
                          </el-input>
                        </el-form-item>
                      </el-col>

                      <el-col :span="12" v-if="data.serviceDataEdit.duration !== null">
                        <el-form-item label="预计时长" class="form-item">
                          <el-input
                              v-model="data.serviceDataEdit.duration"
                              placeholder="请输入预计时长"
                              class="full-width"
                          >
                            <template #append>分钟</template>
                          </el-input>
                        </el-form-item>
                      </el-col>

                      <el-col :span="24">
                        <el-form-item label="服务状态" class="form-item">
                          <el-select
                              v-model="data.serviceDataEdit.status"
                              placeholder="请选择服务状态"
                              class="full-width"
                          >
                            <el-option label="下架" :value="0"></el-option>
                            <el-option label="上架" :value="1"></el-option>
                          </el-select>
                        </el-form-item>
                      </el-col>

                      <el-col :span="24">
                        <el-form-item label="服务描述" class="form-item">
                          <el-input
                              v-model="data.serviceDataEdit.description"
                              type="textarea"
                              :rows="4"
                              placeholder="请输入服务描述"
                              class="full-width"
                          />
                        </el-form-item>
                      </el-col>

                      <el-col :span="24">
                        <el-form-item label="服务图片" class="form-item">
                          <div class="image-preview-edit">
                            <el-upload
                                class="avatar-uploader"
                                action="http://localhost:8083/files/upload"
                                :show-file-list="false"
                                :on-success="handleAvatarSuccess"
                            >
                              <img v-if="data.serviceDataEdit.imageUrl" :src="data.serviceDataEdit.imageUrl"
                                   alt="服务图片"
                                   class="edit-image"/>
                              <el-icon v-else class="avatar-uploader-icon">
                                <Plus/>
                              </el-icon>

                            </el-upload>
                            <div v-if="!data.serviceDataEdit.imageUrl" class="upload-tip">
                              点击上传服务图片，支持jpg/png格式
                            </div>
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
              <el-button @click="dialogVisibleEdit = false" class="cancel-btn">取消</el-button>
              <el-button
                  type="primary"
                  @click="handleUpdate"
                  class="confirm-btn"
              >
                保存修改
              </el-button>
            </div>
          </template>
        </el-dialog>
      </div>

    </div>
  </div>
</template>

<script setup>
import {onMounted, reactive, ref, watch} from "vue"
import {useServiceStore} from "@/Backend/stores/service.js"
import {Delete, Edit, Plus, View} from "@element-plus/icons-vue";
import ServiceAdd from "@/Backend/views/Service/ServiceAdd.vue";

const data = reactive({
  serviceData: null,
  tableData: [],
  currentPag: 1,
  pageSize: 5,
  serviceDataEdit: null,
  serviceDataDetail: null,
})

const serviceStore = useServiceStore()

// 监听 store 数据变化
watch(() => serviceStore.getServiceData, (newVal) => {
  data.serviceData = newVal
  data.tableData = newVal?.list || []
})

// 处理分页变化
const handlePageChange = (pageNum) => {
  data.currentPag = pageNum
  serviceStore.getServiceItemAll(data.currentPag, data.pageSize)
}

onMounted(() => {
  serviceStore.getServiceItemAll(data.currentPag, data.pageSize)
})

/*
* 操作按钮
* */
// 删除
const handleDelete = (id) => {
  serviceStore.deleteServiceItem(id)
}
// 弹窗显示
const dialogVisibleEdit = ref(false)
// 编辑
const handleEdit = (service) => {
  dialogVisibleEdit.value = true
  data.serviceDataEdit = JSON.parse(JSON.stringify(service))
  console.log(data.serviceDataEdit)
}
// 头像上传
const handleAvatarSuccess = (res) => {
  console.log(res.data)
  data.serviceDataEdit.imageUrl = res.data
};
// 编辑更新操作
const handleUpdate = () => {
  console.log('更新服务信息:', data.serviceDataEdit)
  serviceStore.updateServiceItem(data.serviceDataEdit)
  dialogVisibleEdit.value = false
}

// 弹窗
const dialogVisibleDetail = ref(false)
// 详情操作
const handleDetail = (service) => {
  dialogVisibleDetail.value = true
  data.serviceDataDetail = service
  console.log(service)
}

// 格式化日期时间
const formatDateTime = (dateString) => {
  if (!dateString) return '暂无数据'
  const date = new Date(dateString)
  return date.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit'
  })
}

// 获取服务类型标签类型
const getServiceTypeTag = (type) => {
  const tagMap = {
    'foster': 'warning',
    'medical': 'success'
  }
  return tagMap[type] || 'info'
}

// 获取服务类型文本
const getServiceTypeText = (type) => {
  const textMap = {
    'foster': '寄养服务',
    'medical': '医疗服务'
  }
  return textMap[type] || '未知类型'
}

// 获取医疗类型文本
const getMedicalTypeText = (type) => {
  const textMap = {
    'vacation': '疫苗服务',
    'diagnosis': '诊疗服务'
  }
  return textMap[type] || '未知类型'
}
</script>

<style scoped>
.upload-tip {
  color: #999;
  font-size: 12px;
  margin-top: 10px;
}

.service-image {
  width: 50px;
  height: 50px;
  object-fit: cover;
}

.detail-btn {
  background-color: #f0e6f5;
  border-color: #d9b3e6;
  color: #6a2c82;
  margin-right: 5px;
}

.detail-btn:hover {
  background-color: #e6d4f0;
  border-color: #c9a2d9;
}

.edit-btn {
  background-color: #f0e6f5;
  border-color: #d9b3e6;
  color: #6a2c82;
  margin-right: 5px;
}

.edit-btn:hover {
  background-color: #e6d4f0;
  border-color: #c9a2d9;
}

.delete-btn:hover {
  opacity: 0.8;
  background-color: #ff003e;
  border-color: rgba(255, 255, 255, 0);
  color: #ffffff;
}

/* 详情对话框样式 */
.order-detail-dialog {
  --el-dialog-title-font-size: 18px;
  --el-dialog-title-text-color: #6a2c82;
}

.order-detail-dialog :deep(.el-dialog__header) {
  border-bottom: 1px solid #e6d4f0;
}

.detail-content {
  padding: 10px;
}

.amount-highlight {
  color: #b056c3;
  font-weight: bold;
  font-size: 16px;
}

.service-name-highlight {
  color: #6a2c82;
  font-weight: bold;
  font-size: 16px;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
}

/* 状态标签样式 */
:deep(.el-tag) {
  border-radius: 12px;
}

/* 主色调状态标签 */
:deep(.el-tag:not(.el-tag--info):not(.el-tag--success):not(.el-tag--warning):not(.el-tag--danger)) {
  background-color: rgba(176, 86, 195, 0.1);
  border-color: rgba(176, 86, 195, 0.2);
  color: #b056c3;
}

.detail-grid {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.detail-section {
  background: #fafafa;
  border-radius: 8px;
  padding: 15px;
  border: 1px solid #e6d4f0;
}

.detail-section:hover {
  box-shadow: 0 2px 8px rgba(176, 86, 195, 0.1);
}

.section-title {
  margin: 0 0 15px 0;
  padding: 0;
  color: #6a2c82;
  font-size: 16px;
  font-weight: 600;
  border-left: 4px solid #b056c3;
  padding-left: 10px;
}

.description-content {
  background: white;
  border-radius: 4px;
  padding: 10px;
  border: 1px solid #e6e6e6;
  min-height: 40px;
  color: #666;
  white-space: pre-wrap;
  word-break: break-word;
}

.image-preview {
  text-align: center;
}

.detail-image {
  max-width: 100%;
  max-height: 300px;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

/* 覆盖 Element Plus 样式 */
:deep(.el-descriptions__label) {
  background-color: #f8f0fa !important;
  font-weight: 500;
  width: 100px;
}

:deep(.el-descriptions__content) {
  background-color: white !important;
}

:deep(.el-dialog__body) {
  padding: 15px 20px;
}

:deep(.el-dialog__header) {
  padding: 20px 20px 10px;
}

/* 编辑对话框样式 */
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

/* 卡片样式 */
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

/* 基本信息网格 */
.info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 15px;
}

.info-item {
  display: flex;
  flex-direction: column;
}

.info-label {
  font-size: 12px;
  color: #999;
  margin-bottom: 4px;
}

.info-value {
  font-size: 14px;
  color: #333;
  font-weight: 500;
}

/* 表单样式 */
.edit-form {
  width: 100%;
}

.form-item {
  margin-bottom: 20px;
}

.edit-image {
  width: 100%;
  height: 200px;
  border-radius: 8px;
}

.full-width {
  width: 100%;
}

:deep(.el-form-item__label) {
  color: #666;
  font-weight: 500;
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

/* 按钮样式 */
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

/* 响应式设计 */
@media (max-width: 768px) {
  .info-grid {
    grid-template-columns: 1fr;
  }

  .edit-grid {
    gap: 15px;
  }

  .info-card,
  .edit-card {
    padding: 15px;
  }
}
</style>
