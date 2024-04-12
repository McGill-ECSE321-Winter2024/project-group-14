<template>
    <div>
        <div> 
            <el-input v-model="username" style="width:200px" placeholder="Input a name" ></el-input>
            <el-button type="primary" round @click="findByName(name)"> Search</el-button>
        </div>
        <div style="margin:10 px 0; margin-top:10px; margin-bottom:10px">
            <el-button type="success" plain  round @click="add">Add</el-button>
            <el-button type="danger" plain  round>Mass Delete</el-button>
            <el-button type="primary" round @click="showAll">Show All</el-button>
        </div >
        <el-table :data="tableData" stripe :header-cell-style="{backgroundColor:'aliceblue', color:'#666'}">
            <el-table-column type="selection" width ="55" align="center"></el-table-column>
            <el-table-column prop="id" label="ID" align="center"></el-table-column>
            <el-table-column prop="name" label="Username" align="center"></el-table-column>
            <el-table-column prop="Approved Status" label="Email" align="center"></el-table-column>
            <el-table-column label="Operations" align="center" width:180px>
                <template v-slot="scope">
                    <el-button size="mini" type="primary" round @click="approve(scope.row)">Approve</el-button>
                    <el-button size="mini" type="danger" round @click="open(scope.row)">Delete</el-button>
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
        <el-dialog title="Create and Approve New Sport Class" :visible.sync="userFormVisible" width="30%" align="center">
            <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="ref">
                <el-form-item label="Name:" prop="name">
                    <el-input v-model="form.name" placeholder="name" ></el-input>
                </el-form-item>

                <el-form-item label="Approved Status" prop="approved">
                    <el-input v-model="form.approved" placeholder="approved"></el-input>
                </el-form-item>

            
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="save">Confirm</el-button>
                <el-button @click="userFormVisible = false">Cancel</el-button>
            </div>
        </el-dialog>

        <el-dialog title="Update Instructor" :visible.sync="editFormVisible" width="30%" align="center">
            <el-form :model="editForm" label-width="100px" style="padding-right: 50px" :rules="rules" ref="ref">
                <el-form-item label="Username:" prop="username">
                    <el-input v-model="editForm.username" placeholder="username" ></el-input>
                </el-form-item>

                <el-form-item label="Email:" prop="email">
                    <el-input v-model="editForm.email" placeholder="email" ></el-input>
                </el-form-item>

                <el-form-item label="Password:" prop="password">
                    <el-input v-model="editForm.password" placeholder="password" ></el-input>
                </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="update">Confirm</el-button>
                <el-button @click="editFormVisible = false">Cancel</el-button>
            </div>
        </el-dialog>

        
    </div>
</template>

<script>

import axios from 'axios'
    var config = require('../../config')

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
            name:"",
            userFormVisible: false,
            editFormVisible: false,
            form :{},
            editForm:{},
            row:{},
            rules:{
                name:[{required: true, message: "Please input a name", trigger:'blur'}],
            },
            olderUsername:""
            // pageNum:1, // current page
            // pageSize:5, // number of pages
            // username:"",
            // total:0
        }
      },
      created(){
        this.showAll();
      },
      methods:{
        findByName(name){
            AXIOS.get(`/sport-class/${name}`).then(response => {        
                this.tableData = []; // Clear previous data
                this.tableData.push(response.data);}).catch(e => {this.error = e});
        },
        showAll(){
            AXIOS.get('/sport-class/all').then(response => {this.tableData = response.data}).catch(e => {this.error = e});
        },

        delete(){
            this.name = this.row.name
            AXIOS.delete(`/sport-class/${this.row.name}`)
            .then(() => {
            const index = this.tableData.findIndex(this.name);
            if (index !== -1) {
                this.tableData.splice(index, 1);
            }
        })
        .catch((e) => {
            this.error = e;
        });
        },

        save() {
            AXIOS.post(`/sport-class/`, null, {
                params: {
                name: this.form.name
                },
            })
                .then((response) => {
                this.tableData.push(response.data);
                })
                .catch((e) => {
                this.error = e;
                });
            },

        approve(){
            AXIOS.put(`/sport-class/approve/${this.name}`)
        .then((response) => {
            const index = this.tableData.findIndex(this.name);
            if (index !== -1) {
                this.tableData[index] = response.data;
            }
        })
        .catch((e) => {
            this.error = e;
        });
        },

        add(){
            this.form = {}      // reset
            this.userFormVisible = true
        },
        open(row) {
            this.row = JSON.parse(JSON.stringify(row))

            this.$confirm('Do you want to delete?', 'Warning', {
                cancelButtonText: 'Cancel',
                confirmButtonText: 'Confirm',
            type: 'warning'
            }).then(() => {
            this.$message({
                type: 'success',
                message: 'Delete completed',
            }),this.delete();
            }).catch(() => {
            this.$message({
                type: 'info',
                message: 'Delete canceled'
            });          
            });
      }

      }
    }
</script>
