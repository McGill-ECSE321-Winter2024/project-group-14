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
            <el-table-column prop="startTime" label="Start Time" align="center"></el-table-column>
            <el-table-column prop="endTime" label="End Time" align="center"></el-table-column>
            <el-table-column prop="location" label="Location" align="center"></el-table-column>
            <el-table-column prop="date" label="Date" align="center"></el-table-column>
            <el-table-column prop="instructor.username" label="Instructor" align="center"></el-table-column>
            <el-table-column prop="sportClass.name" label="Class" align="center"></el-table-column>
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
        <el-dialog title="Create New Session" :visible.sync="userFormVisible" width="30%" align="center">
            <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="ref">
                <el-form-item label="Start Time:" prop="startTime">
                    <el-input v-model="form.startTime" placeholder="endTime"></el-input>
                </el-form-item>

                <el-form-item label="End Time:" prop="endTime">
                    <el-input v-model="form.endTime" placeholder="StartTime"></el-input>
                </el-form-item>

                <el-form-item label="Location:" prop="location">
                    <el-input v-model="form.location" placeholder="Location"></el-input>
                </el-form-item>

                <el-form-item label="Date:" prop="date">
                    <el-date-picker type="date" placeholder="Pick a date" v-model="form.date" style="width: 100%;"></el-date-picker>
                </el-form-item>

                <el-form-item label="Instructor:" prop="instructorName">
                    <el-input v-model="form.instructorName" placeholder="InstructorName"></el-input>
                </el-form-item>

                <el-form-item label="Class:" prop="sportClassName">
                    <el-input v-model="form.sportClassName" placeholder="SportClassName" ></el-input>
                </el-form-item>
            
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="save">Confirm</el-button>
                <el-button @click="userFormVisible = false">Cancel</el-button>
            </div>
        </el-dialog>

        <el-dialog title="Update New Session" :visible.sync="editFormVisible" width="30%" align="center">
            <el-form :model="editForm" label-width="100px" style="padding-right: 50px" :rules="rules" ref="ref">
                <el-form-item label="Start Time:" prop="startTime">
                    <el-input v-model="editForm.startTime" placeholder="startTime"></el-input>
                </el-form-item>

                <el-form-item label="End Time:" prop="endTime">
                    <el-input v-model="editForm.endTime" placeholder="endTime"></el-input>
                </el-form-item>

                <el-form-item label="Location:" prop="location">
                    <el-input v-model="editForm.location" placeholder="Location"></el-input>
                </el-form-item>

                <el-form-item label="Date:" prop="date">
                    <el-date-picker v-model="editForm.date" type="date" format="yyyy/MM/dd" value-format="yyyy-MM-dd" placeholder="Pick a date"></el-date-picker>
                </el-form-item>

                <el-form-item label="Instructor:" prop="instructorName">
                    <el-input v-model="editForm.instructorName" placeholder="InstructorName"></el-input>
                </el-form-item>

                <el-form-item label="Class:" prop="sportClassName">
                    <el-input v-model="editForm.sportClassName" placeholder="SportClassName" ></el-input>
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
                startTime: '', 
                endTime: '',
                location: '',
                date: '',
                instructorName: '',
                sportClassName: ''
            },
            editForm: {
                id: '',
                startTime: '', // Initialize with default values if needed
                endTime: '',
                location: '',
                date: '',
                instructorName: '',
                sportClassName: ''
            },
            row:{},
            rules:{
                startTime:[{required: true, message: "Please input an start Time", trigger:'blur'}],
                endTime:[{required: true, message: "Please input an end Time", trigger:'blur'}],
                location:[{required: true, message: "Please input a location", trigger:'blur'}],
                date:[{required: true, message: "Please input a date", trigger:'blur'}],
                instructor:[{required: true, message: "Please input an instructor name", trigger:'blur'}],
                class:[{required: true, message: "Please input a class name", trigger:'blur'}]
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
        AXIOS.get('/view_sessions')
            .then(response => {
                const filteredSessions = response.data.filter(session => {
                        return session.sportClass.name === this.sportclass;
                    
                });
                this.tableData = filteredSessions;
            })
            .catch(e => {
                this.error = e;
            });
    },
                
        showAll(){
            AXIOS.get('/view_sessions').then(response => {
                console.log(response.data);
                this.tableData = response.data;}).catch(e => {this.error = e});
        },

        delete() {
            this.id = this.row.id;
            AXIOS.delete(`/delete_session?Id=${this.id}`)
            .then(() => {
                const index = this.tableData.findIndex(session => session.id === this.id);
                if (index !== -1) {
                    this.tableData.splice(index, 1);
                }
            })
            .catch((e) => {
                this.error = e;
            });
        },
        
        save() {
            
            const isoDate = new Date(this.form.date).toISOString().split('T')[0]; 

            const sessionData = {
                startTime: this.form.startTime,
                endTime: this.form.endTime,
                location: this.form.location,
                date: isoDate, 
                instructorName: this.form.instructorName,
                sportclassName: this.form.sportClassName
            };

            AXIOS.post('/create_session', null, {
                params: sessionData
            })
            .then((response) => {
                this.tableData.push(response.data);
                
                this.userFormVisible = false;
                
                this.form = {};
            })
            .catch((error) => {
                this.error = error.response.data; 
                this.$message.error('Error adding new session: Please input correct parameters');
            });
        },

        update(){
            const sessionData = {
                sessionId: this.editForm.id,
                startTime: this.editForm.startTime,
                endTime: this.editForm.endTime,
                location: this.editForm.location,
                date: new Date(this.editForm.date).toISOString().split('T')[0], 
                instructorName: this.editForm.instructorName
            };

            AXIOS.put('/update_session/${sessionId}', null, {
                params: sessionData
            })
            .then((response) => {
                
                const updatedSession = response.data;
                const index = this.tableData.findIndex(session => session.id === updatedSession.id);
                if (index !== -1) {
                    this.$set(this.tableData, index, updatedSession);
                }
                
                this.editFormVisible = false;
                
                this.editForm = {};
            })
            .catch((error) => {
                this.error = error.response.data; 
                this.$message.error('Error updating session: Please input correct parameters');
            });
        },

        add(){
            this.form = {}      // reset
            this.userFormVisible = true
        },

        edit(row) {
            this.editForm = Object.assign({}, row); 
            this.$set(this.editForm, 'instructorName', row.instructor.username); 
            this.$set(this.editForm, 'instructorName', row.instructor.username);
            this.$set(this.editForm, 'sportClassName', row.sportClass.name);
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
                    AXIOS.delete(`/delete_session?Id=${row.id}`)
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
