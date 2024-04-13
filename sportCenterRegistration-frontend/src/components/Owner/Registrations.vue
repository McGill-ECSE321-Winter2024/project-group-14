<template>
  <div>
    <div>
      <el-input v-model="accountName" style="width:200px" placeholder="Input an account name"></el-input>
      <el-input v-model="sessionId" style="width:200px" placeholder="Input a session ID"></el-input>
      <el-button type="primary" round @click="searchRegistrations">Search</el-button>
    </div>
    <div style="margin:10 px 0; margin-top:10px; margin-bottom:10px">
            <el-button type="success" plain  round @click="add">Add</el-button>
            <el-button type="danger" plain  round @click="massDelete">Mass Delete</el-button>
            <el-button type="primary" round @click="showAll">Show All</el-button>
    </div >
    <el-table :data="tableData" stripe @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50px" align="center"></el-table-column>
      <el-table-column prop="id" label="ID" align="center"></el-table-column>
      <el-table-column prop="date" label="Date" align="center"></el-table-column>
      <el-table-column prop="account.username" label="Account" align="center"></el-table-column>
      <el-table-column prop="session.id" label="Session ID" align="center"></el-table-column>
      <el-table-column label="Operations" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="danger" round @click="open(scope.row)">Delete</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="Create New Registration" :visible.sync="userFormVisible" width="30%" align="center">
            <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="ref">
                <el-form-item label="Username:" prop="username">
                    <el-input v-model="form.username" placeholder="username"></el-input>
                </el-form-item>

                <el-form-item label="Session ID:" prop="sessionId">
                    <el-input v-model="form.sessionId" placeholder="session Id"></el-input>
                </el-form-item>
            
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="save">Confirm</el-button>
                <el-button @click="userFormVisible = false">Cancel</el-button>
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
      accountName: '',
      sessionId: '',
      tableData: [],
      selectedRegistrations: [],
      row:{},
      userFormVisible:false,
      form:{
        username:'',
        sessionId:''
      },
      rules:{
        username:[{required: true, message: "Please input an username", trigger:'blur'}],
        sessionId:[{required: true, message: "Please input a session ID", trigger:'blur'}],
      },
    };
  },
  created(){
        this.showAll();
      },

  methods: {
    showAll(){
      AXIOS.get('/registration').then(response => {
                console.log(response.data);
                this.tableData = response.data;}).catch(e => {this.error = e});
    },
    searchRegistrations() {
      AXIOS.get(`/registration?accountName=${this.accountName}&sessionId=${this.sessionId}`)
        .then(response => {
          this.tableData = response.data;
        })
        .catch(error => {
          console.error('Error fetching registrations:', error);
        });
    },

    delete() {
            this.id = this.row.id;
            AXIOS.delete(`/registration/${this.id}`)
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

      massDelete() {
        if (this.selectedRegistrations.length === 0) {
            // No rows selected
            return;
        }

        this.selectedRegistrations.forEach(row => {
            this.row = JSON.parse(JSON.stringify(row))
            this.delete();
        });


        this.selectedRegistrations = [];
        this.showAll();
        },

      handleSelectionChange(selection) {
        this.selectedRegistrations = selection;
      },
      add(){
            this.form = {}      // reset
            this.userFormVisible = true
        },
        save() {
            const name = this.form.username;
            const id = this.form.sessionId;

            AXIOS.post(`/registration/${name}/${id}`)
            .then((response) => {
                if (response.status === 200){
                    // AXIOS.put(`/registration/${name}/${id}`).then(() => {
                    //     this.tableData.push(response.data);
                    // this.userFormVisible = false;

                    // this.form = {};
                    // this.showAll();
                    // })
                    this.tableData.push(response.data);
                    this.userFormVisible = false;

                    this.form = {};
                    this.showAll();
                }
            })
            .catch((error) => {
                this.error = error.response.data; 
                this.$message.error('Error adding new registration: Please input correct parameters');
            });
        }
  }
};
</script>