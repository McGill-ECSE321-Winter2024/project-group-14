<template>
  <div id="welcome">
      <div style="margin-bottom:10px">
          <el-input v-model="input" style="width: 200px" placeholder="Input an username"></el-input>
          <el-button type="info" style="margin-left: 10px">Search</el-button>
          <el-button type="success" style="margin-left: 10px; width: 80px" @click="Add">Add</el-button>
      
      </div>
      <el-table :data="tableData" style="width: 100%">
          <el-table-column prop="id" label="ID" width="180"></el-table-column>
          <el-table-column prop="username" label="username" width="180"></el-table-column>
          <el-table-column prop="email" label="Email"></el-table-column>
          <el-table-column label="Operations">
              <el-button type="primary" icon="el-icon-edit" round @click="Edit">Edit</el-button>
              <el-button type="danger" icon="el-icon-delete" round @click="Delete">Delete</el-button>
          </el-table-column>
      </el-table>
      <div class="block" style="margin-top: 10px;">
          <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page.sync="currentPage4"
          :page-sizes="[100, 200, 300, 400]"
          :page-size="100"
          layout="total, sizes, prev, pager, next, jumper"
          :total="400">
          </el-pagination>
      </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
          input:"",
          tableData: []
      }
    },
    created(){
      
    },
    methods:{
      load(){
          
      }
    }

  }
</script>

<style>
</style>