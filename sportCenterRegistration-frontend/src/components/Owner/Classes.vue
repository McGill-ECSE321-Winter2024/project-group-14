<template>
    <div>
        <div> 
            <el-input v-model="sportclass" style="width:200px" placeholder="Input a sport class" ></el-input>
            <el-button type="primary" round @click="findByName"> Search </el-button>
        </div>
        <div style="margin:10 px 0; margin-top:10px; margin-bottom:10px">
            <el-button type="success" plain  round @click="add">Add</el-button>
            <el-button type="danger" plain  round @click="massDelete">Mass Delete</el-button>
            <el-button type="primary" round @click="showAll">Show All</el-button>
        </div >
        <el-table :data="tableData" stripe :header-cell-style="{backgroundColor:'aliceblue', color:'#666'}" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width ="50px" align="center"></el-table-column>
            <el-table-column prop="id" label="ID" align="center"></el-table-column>
            <el-table-column prop="name" label="Name" align="center"></el-table-column>
            <el-table-column prop="approved" label="Approved" align="center"></el-table-column>
            <el-table-column label="Operations" align="center" width:180px>
                <template v-slot="scope">
                    <el-button size="mini" type="primary" round @click="edit(scope.row)">Approve</el-button>
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
        <el-dialog title="Create New Sport Class" :visible.sync="userFormVisible" width="30%" align="center">
            <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="ref">
                <el-form-item label="Name:" prop="name">
                    <el-input v-model="form.name" placeholder="Name"></el-input>
                </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="save">Confirm</el-button>
                <el-button @click="userFormVisible = false">Cancel</el-button>
            </div>
        </el-dialog>

        <el-dialog title="Update New Sport Class" :visible.sync="editFormVisible" width="30%" align="center">
            <el-form :model="editForm" label-width="100px" style="padding-right: 50px" :rules="rules" ref="ref">
                <el-form-item label="Name:" prop="name">
                    <el-input v-model="form.toApprove" placeholder="Name"></el-input>
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

    export default {
      data() {
        return {
            input:"",
            tableData: [],
            error:"",
            id:"",
            userFormVisible: false,
            editFormVisible: false,
            form: {
                id:'',
                name:'',

            },
            editForm: {
                id:'',
                name:'',
        
            },
            row:{},
            rules:{
                name:[{required: true, message: "Please input a name", trigger:'blur'}],
            },
            selectedRows:[],
            sportclass:""
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
        findByName() {
        AXIOS.get('/sport-class/all')
            .then(response => {
                const filteredSessions = response.data.filter(sportClass => {
                        return sportClass.name === this.sportclass;
                    
                });
                this.tableData = filteredSessions;
            })
            .catch(e => {
                this.error = e;
            });
    },
                
        showAll(){
            AXIOS.get('/sport-class/all').then(response => {
                console.log(response.data);
                this.tableData = response.data.map(item => {
                    item.approved = item.approved ? "true" : "false";
                })
                this.tableData = response.data;
            }).catch(e => {this.error = e});
        },
        delete() {
            // Extract the name from the row
            const name = this.row.name;
            
            // Make the DELETE request with the correct URL
            AXIOS.delete(`/sport-class/${name}`)
            .then(() => {
                // Remove the deleted item from the tableData
                const index = this.tableData.findIndex(item => item.name === name);
                if (index !== -1) {
                    this.tableData.splice(index, 1);
                }
            })
            .catch((error) => {
                this.error = error.response.data;
                this.$message.error('Error deleting class');
            });
        },
        
        save() {
            // Extract the name from the form data
            const name = this.form.name;

            // Make the POST request with the correct URL
            AXIOS.post(`/sport-class/${name}`)
            .then((response) => {
                this.tableData.push(response.data);
                
                this.userFormVisible = false;
                
                this.form = {};
                this.showAll();
            })
            .catch((error) => {
                this.error = error.response.data; 
                this.$message.error('Error adding new class: Please input correct parameters');
            });
        },

        update(){
            // Extract the name from the form data
            const name = this.editForm.name;

            // Make the PUT request with the correct URL
            AXIOS.put(`/sport-class/approve/${this.form.toApprove}`)
            .then((response) => {
                if (response.status === 200){
                    this.editFormVisible = false;
                    this.editForm = {};
                    this.showAll();
                }
            })
            .catch((error) => {
                this.error = error.response.data; 
                this.$message.error('Error approving class: Please input correct parameters');
            });
        },

        add(){
            this.form = {}      // reset
            this.userFormVisible = true
        },

        edit(row) {
            this.editForm = Object.assign({}, row); 
            this.form.toApprove = row.name;
            console.log(this.editForm);
            this.$set(this.editForm, 'name', row.name); 
            this.editFormVisible = true;
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

        // Create an array to store promises for each deletion
        let deletePromises = [];

        // Iterate through selected rows and create a promise for each deletion
        this.selectedRows.forEach(row => {
            deletePromises.push(
                new Promise((resolve, reject) => {
                    AXIOS.delete(`/sport-class/${row.name}`)
                        .then(() => {
                            resolve();
                        })
                        .catch((error) => {
                            reject(error);
                        });
                })
            );
        });

        // Execute all deletion promises
        Promise.all(deletePromises)
            .then(() => {
                // Clear selectedRows array
                this.selectedRows = [];
                // Refresh table data
                this.showAll();
            })
            .catch((error) => {
                // Handle error if any deletion fails
                this.error = error;
            });
        }
      }
    }
</script>
