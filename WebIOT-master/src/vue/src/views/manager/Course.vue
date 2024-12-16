<template>
  <div>
    <div class="card" style="margin-bottom: 10px">

      <el-select v-model="value" class="m-2" placeholder="查询条件" style="margin-right: 10px">
        <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        />
      </el-select>

      <el-input style="width:300px" v-model="data.name" placeholder="请输入课程名称" :prefix-icon="Search"/>
      <el-button type="primary" style="margin-left:15px" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <div>
        <el-button type="primary" @click="addEvent">新增</el-button>
      </div>

      <el-table :data="data.tableData" style="width: 100%">
        <el-table-column prop="id" label="id" v-if="false"/>
        <el-table-column prop="name" label="课程名称" width="150"/>
        <el-table-column prop="no" label="课程编号" width="120"/>
        <el-table-column prop="term" label="学期" width="80"/>
        <el-table-column prop="times" label="上课时间" width="250"/>
        <el-table-column prop="teacher" label="授课教师" width="100"/>
        <el-table-column prop="position" label="上课地点" width="150"/>

        <el-table-column>
          <template #default="scope">
            <el-button type="primary" style="margin-right:10px" plain @click="editEvent(scope.row)">编辑</el-button>
            <el-button type="danger" plain @click="deleteEvent(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

    </div>

    <div class="card">
      <el-pagination v-model:page-size="data.pageSize" v-model:current-page="data.pageNumber"
                     @current-change="pageChangeEvent"
                     background layout="prev, pager, next" :total="data.total"/>
    </div>

    <el-dialog width="35%" v-model="data.formVisible" title="新增课程信息">
      <el-form :model="data.form" lable-width="100px" :rules="rules" :validate-on-rule-change="false">
        <el-form-item label="课程名称" prop="name" style="margin-left: 20px">
          <el-input v-model="data.form.name" autocomplete="off" style="margin-right: 30px"/>
        </el-form-item>
        <el-form-item label="课程编号" prop="no" style="margin-left: 20px">
          <el-input v-model="data.form.no" autocomplete="off" style="margin-right: 30px"/>
        </el-form-item>
        <el-form-item label="上课学期" prop="term" style="margin-left: 30px">
          <el-input v-model="data.form.term" autocomplete="off" style="margin-right: 30px"/>
        </el-form-item>
        <el-form-item label="上课时间" prop="times" style="margin-left: 30px">
          <el-input v-model="data.form.times" autocomplete="off" style="margin-right: 30px"/>
        </el-form-item>
        <el-form-item label="授课教师" prop="teacher" style="margin-left: 30px">
          <el-input v-model="data.form.teacher" autocomplete="off" style="margin-right: 30px"/>
        </el-form-item>
        <el-form-item label="上课地点" prop="position" style="margin-left: 30px">
          <el-input v-model="data.form.position" autocomplete="off" style="margin-right: 30px"/>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="data.formVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 认</el-button>
      </span>
      </template>
    </el-dialog>

  </div>
</template>


<script setup>
import {reactive, ref} from "vue";
import {Search} from '@element-plus/icons-vue'
import request from "../../utils/request";
import {ElMessage, ElMessageBox} from "element-plus";

//缓存数据
const data = reactive({
  name: '',
  tableData: [],
  total: 1,
  pageSize: 5,
  pageNumber: 1,
  formVisible: false,
  form: {},
})

//select展示数据
const value = ref()
value.value = '课程名称'

//选择搜索条件
const options = [
  {
    value: '课程名称',
    label: '课程名称',
  },
  {
    value: '课程编号',
    label: '课程编号',
  },
  {
    value: '授课教师',
    label: '授课教师',
  },
]


//新增界面条件
const rules = ({
  name: [
    {required: true, message: '请输入课程名称', trigger: 'blur'},
  ],
  no: [
    {required: true, message: '请输入课程编号', trigger: 'blur'},
  ]
})

//加载界面函数
const load = () => {
  request.get('/course/selectPage', {
    params: {
      pageNumber: data.pageNumber,
      pageSize: data.pageSize,
      name: data.name,
      value: value.value
    }
  }).then(res => {
    data.tableData = res.data?.list || []
    data.total = res.data?.total || 0
  })
}

load()


//翻页
const pageChangeEvent = () => {
  load()
}

//重置
const reset = () => {
  data.name = ''
  load();
}

//新增
const addEvent = () => {
  data.form = {}
  data.formVisible = true
}

//新增-保存
const save = () => {
  request.request({
    url: data.form.id ? '/course/update' : '/course/add',
    method: data.form.id ? 'PUT' : 'POST',
    data: data.form,
  }).then(res => {
    if (res.code === '200') {
      load()    // 重新获取数据
      data.formVisible = false  // 关闭弹窗
      ElMessage.success("操作成功")
    } else {
      console.log(res)
      ElMessage.error(res.msg)
    }
  })
}

//编辑
const editEvent = (row) => {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}

//删除
const deleteEvent = (id) => {
  ElMessageBox.confirm(
      '数据删除后无法复原，您确认要删除吗？',
      '删除确认',
      {
        type: 'warning',
      }
  ).then(res => {
    request.delete('/course/delete/' + id).then(res => {
      if (res.code === '200') {
        load()    // 重新获取数据
        ElMessage.success("删除成功")
      } else {
        console.log(res)
        ElMessage.error(res.msg)
      }
    })
  }).catch(res=>{ElMessage({
    type: 'info',
    message: '取消删除',
  })})
}

</script>