<template>
    <div>
        <div> 
            <el-input v-model="name" style="width:200px" placeholder="Input an username" ></el-input>
            <el-button type="primary" round @click="findByName(name)"> Search</el-button>
        </div>
        <div style="margin:10 px 0; margin-top:10px; margin-bottom:10px">
            <el-button type="success" plain  round @click="add">Add</el-button>
            <el-button type="danger" plain  round @click="massDelete">Mass Delete</el-button>
            <el-button type="primary" round @click="showAll">Show All</el-button>
        </div >
        <el-table :data="tableData" stripe :header-cell-style="{backgroundColor:'aliceblue', color:'#666'}" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width ="50px" align="center"></el-table-column>
            <el-table-column prop="id" label="ID" align="center"></el-table-column>
            <el-table-column prop="username" label="Username" align="center"></el-table-column>
            <el-table-column prop="email" label="Email" align="center"></el-table-column>
            <el-table-column label="Operations" align="center" width:180px>
                <template v-slot="scope">
                    <el-button size="mini" type="primary" round @click="edit(scope.row)">Edit</el-button>
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
        <el-dialog title="Create New Instructor" :visible.sync="userFormVisible" width="30%" align="center">
            <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="ref">
                <el-form-item label="Username:" prop="username">
                    <el-input v-model="form.username" placeholder="username" ></el-input>
                </el-form-item>

                <el-form-item label="Email:" prop="email">
                    <el-input v-model="form.email" placeholder="email"></el-input>
                </el-form-item>

                <el-form-item label="Password:" prop="password">
                    <el-input v-model="form.password" placeholder="password" ></el-input>
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

    const crypto = require('crypto');

function hashPassword(password) {
	const hash = crypto.createHash('sha256');
	hash.update(password);
	return hash.digest('hex');
}


    export default {
      data() {
        return {
            input:"",
            tableData: [],
            error:"",
            username:"",
            userFormVisible: false,
            editFormVisible: false,
            form :{},
            editForm:{},
            row:{},
            rules:{
                username:[{required: true, message: "Please input an username", trigger:'blur'}],
                email:[{required: true, message: "Please input an email", trigger:'blur'}],
                password:[{required: true, message: "Please input a password", trigger:'blur'}]
            },
            olderUsername:"",
            selectedRows:[],
            name:""
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
            AXIOS.get(`/instructor/${name}`).then(response => {        
                this.tableData = []; // Clear previous data
                this.tableData.push(response.data);}).catch(e => {this.error = e});
        },
        showAll(){
            AXIOS.get('/instructor/all').then(response => {this.tableData = response.data}).catch(e => {this.error = e});
        },

        delete(){
            this.username = this.row.username
            AXIOS.delete(`/instructor/${this.row.username}`)
            .then(() => {
            const index = this.tableData.findIndex(instructor => instructor.username === this.username);
            if (index !== -1) {
                this.tableData.splice(index, 1);
            }
        })
        .catch((e) => {
            this.error = e;
        });
        },

        save() {
            AXIOS.post(`/instructor/`, null, {
                params: {
                username: this.form.username,
                email: this.form.email,
                password: hashPassword(this.form.password),
                },
            })
                .then((response) => {
                    this.tableData.push(response.data);
                    // Close the user form after saving
                    this.userFormVisible = false;
                    // Reset the form fields
                    this.form = {};
                },)
                .catch((e) => {
                this.error = e;
                this.$message.error('Error adding new instructor: Please input correct parameters');
                });
            },

        update(){
            AXIOS.put(`/instructor/update/${this.oldUsername}/${this.editForm.username}/${this.editForm.email}/${this.editForm.password}`, null)
        .then((response) => {
            const index = this.tableData.findIndex(instructor => instructor.username === this.form.oldUsername);
            if (index !== -1) {
                this.tableData[index] = response.data;
            }
            this.editFormVisible = false;
                    // Reset the form fields
            this.editForm = {};
            this.showAll();
        })
        .catch((e) => {
            this.error = e;
            this.$message.error('Error editing instructor: Please input correct parameters');
        });
        console.log(this.oldUsername)
        console.log(this.editForm.username)
        console.log(this.editForm.email)
        console.log(this.editForm.password)
        },

        add(){
            this.form = {}      // reset
            this.userFormVisible = true
        },
        edit(row){
            this.editForm = JSON.parse(JSON.stringify(row))
            this.oldUsername=this.editForm.username
            this.editFormVisible = true
        },
        open(row) {
            this.row = JSON.parse(JSON.stringify(row))

            this.$confirm('Do you want to delete?', 'Warning', {
                confirmButtonText: 'Confirm',
                cancelButtonText: 'Cancel',
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
      },
      handleSelectionChange(selection){
        this.selectedRows = selection;
      },

      massDelete() {
        if (this.selectedRows.length === 0) {
            // No rows selected
            return;
        }

        // Iterate through selected rows and call delete() for each one
        this.selectedRows.forEach(row => {
            this.row = JSON.parse(JSON.stringify(row))
            // Call delete() method to delete the current row
            this.delete();
        });

        // Clear selectedRows array
        this.selectedRows = [];
        this.showAll();
        }
      }
    }
</script>
