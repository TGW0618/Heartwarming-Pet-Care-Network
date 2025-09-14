<template>
  <navBarTop title="新增宠物信息">
    <template #left>
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
        <!--        性别-->
        <!--        <van-field-->
        <!--            v-model="form.gender"-->
        <!--            name="gender"-->
        <!--            label="性别"-->
        <!--            placeholder="请输入性别"-->
        <!--            :rules="[{ required: true, message: '请填写性别' }]"-->
        <!--        />-->

        <van-field name="gender" label="性别">
          <template #input>
            <van-radio-group v-model="form.gender" direction="horizontal">
              <van-radio name="male">公</van-radio>
              <van-radio name="female">母</van-radio>
              <van-radio name="unknown">其他</van-radio>
            </van-radio-group>
          </template>
        </van-field>


        <!--        出生日期-->
        <van-field
            v-model="form.birthDate"
            is-link
            readonly
            name="birthDate"
            label="出生日期"
            placeholder="点击选择出生日期"
            @click="showPicker = true"
        />
        <!--        时间弹窗-->
        <van-popup v-model:show="showPicker" position="bottom">
          <van-date-picker @confirm="onConfirm" @cancel="showPicker = false"/>
        </van-popup>

        <van-field
            v-model="form.weight"
            name="weight"
            label="体重(kg)"
            placeholder="请输入体重"
            type="digit"
            :rules="weightRules"
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
          <template #input>
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
import {computed, reactive, ref} from 'vue';
import {useRouter} from "vue-router";
import {showToast} from 'vant';
import petsInfoStores from "@/stores/petsInfoStores.js";
import request from "@/utils/request.js";

const router = useRouter();
const petsInfo = petsInfoStores();

// 表单数据
const form = reactive({
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

// 日期选择
const showPicker = ref(false);
const onConfirm = ({selectedValues}) => {
  form.birthDate = selectedValues.join('-');
  showPicker.value = false;
};

// 体重字段的验证规则
const weightRules = computed(() => [
  { required: true, message: '请填写体重' },
  {
    validator: (val) => {
      // 检查是否为数字（包括小数）
      if (!val) return true;
      const regex = /^\d+(\.\d+)?$/;
      return regex.test(val);
    },
    message: '请输入有效的数字'
  },
  {
    validator: (val) => {
      // 检查数值是否合理（0.1kg到200kg之间）
      if (!val) return true;
      const num = parseFloat(val);
      return num >= 0.1 && num <= 200;
    },
    message: '请输入合理的体重范围（0.1-200kg）'
  }
]);


// 头像文件列表
const avatarFileList = ref([]);

// top返回按钮点击事件
const onClickLeft = () => {
  router.go(-1);
}

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

// 提交表单
const onSubmit = async (values) => {
  try {
    // 更新 form 对象中的值
    Object.assign(form, values, {avatar: form.avatar});
    console.log('提交的数据:', form);
    await petsInfo.addPetsInfo(form);
    router.back() ;
  } catch (error) {
    console.error(error);
  }
};
</script>

<style scoped>
</style>
