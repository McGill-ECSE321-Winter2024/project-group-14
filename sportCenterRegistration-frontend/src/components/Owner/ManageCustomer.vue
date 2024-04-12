<template>
  <div>
      <div> 
          <el-input style="width:200px" placeholder="Input an username" v-model="username"></el-input>
          <el-button type="primary" round> Search</el-button>
      </div>
      <div style="margin:10 px 0; margin-top:10px; margin-bottom:10px">
          <el-button type="success" plain  round>Add</el-button>
          <el-button type="danger" plain  round>Mass Delete</el-button>
      </div >
      <el-table :data="tableData" stripe :header-cell-style="{backgroundColor:'aliceblue', color:'#666'}">
          <el-table-column type="selection" width ="55" align="center"></el-table-column>
          <el-table-column prop="id" label="ID" align="center"></el-table-column>
          <el-table-column prop="username" label="Username" align="center"></el-table-column>
          <el-table-column prop="email" label="Email" align="center"></el-table-column>
          <el-table-column label="Operations" align="center" width:180px>
              <template v-slot="scope">
                  <el-button size="mini" type="primary" round>Edit</el-button>
                  <el-button size="mini" type="danger" round>Delete</el-button>
              </template>
          </el-table-column>
      </el-table>
  <!-- //     <div class="block" style="margin-top: 10px;">
  //       <el-pagination
  //       @size-change="handleSizeChange"
  //       @current-change="handleCurrentChange"
  //       :current-page.sync="pageNum"
  //       :page-size="pageSize"
  //       layout="total, prev, pager, next, jumper"
  //       :total="total">
  //       </el-pagination>
  //     </div> -->
  </div>
</template>

<script>

import axios from 'axios'
  var config = require('../../../config')

  var backendConfigurer = function(){
      switch(process.env.NODE_ENV){
          case 'development':
              return 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;
          case 'production':
              return 'https://' + config.build.backendHost + ':' + config.build.backendPort ;
      }
  };

  var backendUrl = backendConfigurer();

  var AXIOS = axios.create({
  baseURL: backendUrl,
  //headers: { 'Access-Control-Allow-Origin': frontendUrl }
  })

  export default {
    data() {
      return {
          input:"",
          tableData: [],
          error:"",
          // pageNum:1, // current page
          // pageSize:5, // number of pages
          // username:"",
          // total:0
      }
    },
    created:function(){
      AXIOS.get('/customer/all').then(response => {this.tableData = response.data}).catch(e => {this.error = e});
    },
    methods:{

    }

  }
</script>
