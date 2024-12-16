<template>
  <div>
    <div class="login-container">
      <div style="width: 350px" class="login-box">
        <div style="font-weight: bold; font-size: 20px; text-align: center; margin-bottom: 30px;" >登录</div>
        <el-form :model="data.form" ref="formRef" :rules="rules">
          <el-form-item label="账号" prop="username">
            <el-input prefix-icon="User" v-model="data.form.username" placeholder="请输入账号" />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input show-password prefix-icon="Lock" v-model="data.form.password" placeholder="请输入密码"/>
          </el-form-item>
          <el-form-item>
            <el-select style="width: 100%" v-model="data.form.role">
              <el-option label="学生登录" value="STUDENT"></el-option>
              <el-option label="管理员登录" value="ADMIN"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="width: 100%" @click="login">登 录</el-button>
          </el-form-item>

        </el-form>
        <div style="text-align: right; margin-top: 30px">
          <a href="/register">还没有账号？请注册</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {reactive,ref} from "vue";
import request from "../utils/request";
import {ElMessage} from "element-plus";

const data = reactive({
  form: {
    role: "STUDENT"
  },
});

const rules = ({
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
  ]
})

const formRef = ref()

const login = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      request.post('/login',data.form).then(res => {
        if (res.code === '200') {
          localStorage.setItem('student-user', JSON.stringify(res.data))
          ElMessage.success('登录成功')
          location.href = '/home'  // 跳转到主页
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })
}

</script>

<style scoped>
.login-container {
  min-height: 100vh;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  background-image: url("@/assets/imgs/background.png");
  background-size: cover;
}
</style>

<style scoped>
.login-box{
  background-color: rgba(255,255,255,0.7);
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
  padding: 30px;
  border-radius: 5px;
}
</style>