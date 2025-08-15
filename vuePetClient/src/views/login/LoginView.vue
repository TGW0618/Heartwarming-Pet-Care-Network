<template>
  <!--  背景开始-->
  <div class="LoginBg">

  </div>
  <!--  背景结束-->
  <div style="position: relative;min-height: 100vh;">
    <!--    内容开始-->
    <div
        style="height:auto;color: #ffffff;padding-left: 1rem;padding-right: 1rem;">
      <div style="padding-top: 8.8rem;">
        <!--        登录标语-->
        <div>
          <p style="letter-spacing: 0.13rem;font-size: 0.5rem;margin:0">
            像您一样爱TA<br/>
            专业医疗×安心寄养<br/>
            24小时温柔守护</p>
        </div>
        <!--        登录按钮-->
        <div style="margin-top: 0.5rem;">
          <van-button to="/loginInfo"
                      style="background-color:#f8f8f8;border-radius: 3rem;width:3rem;font-weight: 600;">
            登录
          </van-button>
        </div>
      </div>
    </div>
    <!--    内容结束-->

  </div>
</template>


<script setup>


</script>


<style scoped>
.LoginBg {
  width: 100%;
  height: 100vh;
  background-image: url('/img/dog-trainer-bounding-with-dogs.jpg');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 0;
}

</style>