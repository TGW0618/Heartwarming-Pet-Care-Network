<template>
  <navBarTop title="修改宠物信息">
    <template  #left>
      <div @click="onClickLeft">
        <slot>
          <van-icon name="arrow-left"/>
        </slot>
      </div>
    </template>
  </navBarTop>
  <div>
    <van-form @submit="onSubmit">
      <van-cell-group inset>
        <van-field
            v-model="form.petName"
            name="petName"
            label="宠物名称"
            placeholder="请输入宠物名称"
            :rules="[{ required: true, message: '请填写宠物名称' }]"
        />
        <van-field
            v-model="form.petType"
            name="petType"
            label="宠物类型"
            placeholder="请输入宠物类型"
            :rules="[{ required: true, message: '请填写宠物类型' }]"
        />
        <van-field
            v-model="form.breed"
            name="breed"
            label="品种"
            placeholder="请输入品种"
            :rules="[{ required: true, message: '请填写品种' }]"
        />
        <van-field
            v-model="form.gender"
            name="gender"
            label="性别"
            placeholder="请输入性别"
            :rules="[{ required: true, message: '请填写性别' }]"
        />
        <van-field
            v-model="form.birthDate"
            name="birthDate"
            label="出生日期"
            placeholder="请选择出生日期"
            :rules="[{ required: true, message: '请选择出生日期' }]"
        />
        <van-field
            v-model="form.weight"
            name="weight"
            label="体重(kg)"
            placeholder="请输入体重"
            type="number"
            :rules="[{ required: true, message: '请填写体重' }]"
        />
        <van-field
            v-model="form.healthStatus"
            name="healthStatus"
            label="健康状态"
            placeholder="请输入健康状态"
            :rules="[{ required: true, message: '请填写健康状态' }]"
        />
        <van-field
            v-model="form.allergies"
            name="allergies"
            label="过敏史"
            placeholder="请输入过敏史"
            type="textarea"
            autosize
            :rules="[{ required: true, message: '请填写过敏史' }]"
        />

        <!-- 头像上传字段 -->
        <van-field name="avatar" label="宠物头像">
          <template #input >
            <van-uploader
                v-model="avatarFileList"
                :after-read="afterReadAvatar"
                :max-count="1"
                :preview-size="[100, 100]"
                :before-delete="beforeDeleteAvatar"
            />
          </template>
        </van-field>

        <!-- 隐藏字段用于存储头像URL -->
        <van-field
            v-model="form.avatar"
            name="avatar"
            style="display: none;"
        />
      </van-cell-group>
      <div style="margin: 16px;">
        <van-button round block style="background-color: #007bff;color: white;" native-type="submit">
          提交
        </van-button>
      </div>
    </van-form>
  </div>
</template>

<script setup>
import navBarTop from "@/components/navBarTop.vue";
import {onMounted, reactive, ref} from 'vue';
import {useRoute, useRouter} from "vue-router";
import * as vant from 'vant';
import {showToast} from 'vant';
import petsInfoStores from "@/stores/petsInfoStores.js";
import request from "@/utils/request.js";

// 使用 useRoute 获取路由参数
const route = useRoute();
const router = useRouter();
const petId = route.params.id ? parseInt(route.params.id) : null;

const petsInfo = petsInfoStores();

// 表单数据
const form = reactive({
  id: '',
  petName: '',
  petType: '',
  breed: '',
  gender: '',
  birthDate: '',
  weight: '',
  healthStatus: '',
  allergies: '',
  avatar: ''
});

// 头像文件列表
const avatarFileList = ref([]);

// 上传头像到服务器
const uploadAvatarToServer = async (file) => {
  const formData = new FormData();
  formData.append('file', file.file);
  try {
    const response = await request.post('/files/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      },
      transformRequest: [function (data) {
        return data; // 确保不转换FormData
      }]
    });

    if (response.code === 200) {
      return response.data; // 返回上传后的URL
    } else {
      throw new Error(response.message || '上传失败');
    }
  } catch (error) {
    console.error('文件上传失败:', error);
    throw error;
  }
};

// 文件读取后的处理函数
const afterReadAvatar = async (file) => {
  console.log('选择的文件:', file);

  try {
    showToast('上传中...');
    // 上传文件到服务器
    // 更新表单中的头像URL
    form.avatar = await uploadAvatarToServer(file);
    showToast('上传成功');
  } catch (error) {
    showToast('上传失败');
    // 从文件列表中移除上传失败的文件
    avatarFileList.value = [];
    console.error('上传失败:', error);
  }
};

// 删除头像前的确认
const beforeDeleteAvatar = (file, detail) => {
  return new Promise((resolve) => {
    vant.showConfirmDialog({
      title: '确认删除',
      message: '确定要删除这张头像吗？',
    })
        .then(() => {
          // 用户确认删除，清除表单中的头像URL
          form.avatar = '';
          resolve(true);
        })
        .catch(() => {
          // 用户取消删除
          resolve(false);
        });
  });
};

const onClickLeft = () => {
  router.go(-1);
}

// 提交表单
const onSubmit = async (values) => {
  try {
    // 更新 form 对象中的值（除了avatar，因为avatar已经通过上传处理了）
    Object.assign(form, values, {avatar: form.avatar});

    console.log('提交的数据:', form);

    // 确保ID存在
    if (petId) {
      form.id = petId;
    }

    await petsInfo.updatePetsInfoById(form);
    showToast('修改成功');
    router.go(-1);
  } catch (error) {
    showToast('修改失败');
    console.error(error);
  }
};

onMounted(async () => {
  if (petId) {
    try {
      await petsInfo.getPetsInfoById(petId);
      // 数据加载完成后填充表单
      const petData = petsInfo.petsInfoByIdData;
      if (petData) {
        form.id = petData.id || '';
        form.petName = petData.petName || '';
        form.petType = petData.petType || '';
        form.breed = petData.breed || '';
        form.gender = petData.gender || '';
        form.birthDate = petData.birthDate || '';
        form.weight = petData.weight || '';
        form.healthStatus = petData.healthStatus || '';
        form.allergies = petData.allergies || '';
        form.avatar = petData.avatar || '';

        // 如果已有头像URL，初始化文件列表
        if (petData.avatar) {
          avatarFileList.value = [
            {
              url: petData.avatar
            }
          ];
        }
      }
      console.log('加载的宠物数据:', petData);
    } catch (error) {
      console.error('获取宠物信息失败:', error);
      showToast('获取宠物信息失败');
    }
  }
});
</script>

<style scoped>
</style>
