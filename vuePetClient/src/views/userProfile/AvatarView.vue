<template>
  <navBarTop title="个人头像">
    <template #left>
      <div @click="onClickLeft">
        <slot>
          <van-icon name="arrow-left"/>
        </slot>
      </div>
    </template>
    <template #right>
      <van-icon name="weapp-nav" @click="onClickRight"/>
    </template>
  </navBarTop>

  <div class="avatar">
    <img
        v-if="data.userInfo && data.userInfo.avatar"
        :src="userInfoStore.userInfo.avatar"
        alt="头像"
    >
    <img
        v-else
        src="@/assets/img/default-avatar.png"
        alt="默认头像"
    >
  </div>

  <!-- 圆角弹窗（底部） -->
  <van-popup
      v-model:show="show"
      round
      position="bottom"
      :style="{ height: '38%' }"
  >
    <div class="popup-content">
      <div class="uploader-container">
        <van-uploader
            v-model="data.fileList"
            :max-count="1"
            :after-read="afterRead"

        />
      </div>
      <div @click="upload" class="popup-button upload-button" :class="{ disabled: uploading || !data.dataForm }">
        {{ uploading ? '上传中...' : '上传' }}
      </div>
      <div @click="cancel" class="popup-button cancel-button">
        取消
      </div>
    </div>
  </van-popup>
</template>

<script setup>
import navBarTop from "@/components/navBarTop.vue";
import {reactive, ref} from "vue";
import request from "@/utils/request.js";
import {showFailToast, showToast} from 'vant';
import userInfo from "@/stores/userInfo.js";
import router from "@/router/index.js";

const userInfoStore = userInfo();

const data = reactive({
  userInfo: userInfoStore.userInfo,
  fileList: [],
  currentFile: [],// 保存文件对象
  imgUrl: '',
  dataForm: null,
});

const onClickLeft = () => {
  router.go(-1);
}

const onClickRight = () => {
  show.value = true;
};

const show = ref(false);
const uploading = ref(false);


// 读取文件内容
const afterRead = (file) => {
  data.imgUrl = file.file.name;
  data.currentFile = file.file;// 文件对象

};

// 上传
const upload = () => {
  if (!data.currentFile) {
    return;
  }

  uploading.value = true;

  // 创建新的FormData对象
  const formData = new FormData();
  // 确保使用正确的字段名'file'，并且传递的是File对象
  formData.append('file', data.currentFile);

  // 检查FormData内容
  for (let [key, value] of formData.entries()) {
    console.log(key, value);
  }

  request.post("/files/upload", formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    transformRequest: [function (data) {
      return data; // 确保不转换FormData
    }]
  }).then((res) => {
    if (res.code === 200) {
      // 更新用户头像URL（应该从后端返回的URL更新）
      data.userInfo.avatar = res.data; // 假设后端返回文件访问URL
      userInfoStore.updateUserInfo(JSON.parse(JSON.stringify(data.userInfo)))
      showToast('上传成功');
      data.fileList = []; // 清空文件列表
      data.currentFile = null; // 清空当前文件
      router.replace("/userProfile")
      cancel()
    }
  }).catch((err) => {
    showFailToast(err.message || '上传失败')
  }).finally(() => {
    uploading.value = false;
  })
};

// 取消
const cancel = () => {
  show.value = false;
  data.fileList = [];
  data.dataForm = null;
};

</script>

<style scoped>
.avatar {
  width: 100%;
  height: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.avatar img {
  width: 100%;
  height: 50%;
}

.popup-content {
  text-align: center;
  padding: 15px;
  font-size: 18px;
  letter-spacing: 1px;
}

.uploader-container {
  margin-bottom: 15px;
}

.popup-button {
  padding: 10px;
  margin: 8px 0;
  border-radius: 30px;
  background-color: #3e6ae1;
  cursor: pointer;

}

.upload-button {
  background-color: #3e6ae1;
  color: white;
}

.upload-button.disabled {
  cursor: not-allowed;
  color: #ffffff;
}

.cancel-button {
  background-color: #cacaca;
}
</style>