<template>
    <div>
      <el-container>
        <el-aside :width="asideWidth" style="min-height:100vh; background-color: #001529">
          <div style="height:60px; color:white; display:flex; align-items:center; justify-content:center">
            <img class="logo" :src="require('@/assets/logo.png')" alt="logoIcon"/>
            <span style="margin-left: 4px; font-size:18px" v-show="!isCollapse">SPCR</span>
          </div>
  
  
          <el-menu router :collapse="isCollapse" :collapse-transition="false" background-color=" #001529" text-color ="rgba(255,255,255,0.65)" active-text-color="#fff" style=" border:none" :default-active="$route.path">
            <el-menu-item index="/OwnerApp/Home">
                <i class="el-icon-house"></i>
                <span slot ="title">Home</span>
            </el-menu-item>
  
            <el-submenu>
              <template slot="title">
                <i class="el-icon-s-custom"></i>
                  <span>User Management</span>
              </template>
                <el-menu-item-group>
                  <el-menu-item index="/OwnerApp/ManageInstructor">
                    <!-- <i class="el-icon-s-custom"></i> -->
                    <span>Instructor Management</span>
                  </el-menu-item>
                  <el-menu-item index="/OwnerApp/ManageCustomer">
                    <!-- <i class="el-icon-user-solid"></i> -->
                    <span>Customer Management</span>
                  </el-menu-item>
                </el-menu-item-group>
              </el-submenu>
  
              <el-menu-item index="/OwnerApp/Classes">
                <i class="el-icon-menu"></i>
                <span slot="title">Approve New Class</span>
              </el-menu-item>

              <el-menu-item index="/OwnerApp/Sessions">
                <i class="el-icon-basketball"></i>
                <span slot="title">Sessions</span>
              </el-menu-item>       

              <el-menu-item index="/OwnerApp/Registrations">
                <i class="el-icon-s-management"></i>
                <span slot="title">Registrations</span>
              </el-menu-item>       
  
              <el-menu-item index="/OwnerApp/Settings">
                <i class="el-icon-setting"></i>
                <span slot="title">Settings</span>
              </el-menu-item>       

              <el-menu-item style="margin-top: 330px;" @click="logout">
                <i class="el-icon-switch-button"></i>
                <span slot="title">Log out</span>
              </el-menu-item>   
  
          </el-menu>

          <!-- <div style="height:60px; color:white; display:flex; align-items:center; justify-content:center">
            <img class="logo" :src="require('@/assets/logo.png')" alt="logoIcon"/>
            <span style="margin-left: 4px; font-size:18px" v-show="!isCollapse">SPCR</span>
          </div> -->
        </el-aside>
  
        <el-container>
          <el-header>
            <i :class="collapseIcon" style="font-size:25px ;" @click="handleCollapse"></i>
          </el-header>
  
          <el-main>
            <router-view/>

          </el-main>
  
        </el-container>
  
      </el-container>
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

  export default{
    data(){
      return{
        isCollapse: false,
        asideWidth: '250px',
        collapseIcon: 'el-icon-s-fold',
        classes:[],
        errorClass:""
      }
    },
    created: function(){
        AXIOS.get('/instructor/all').then(response => {this.classes = response.data}).catch(e => {this.errorClass = e});
    },
    methods:{
      handleCollapse(){
        this.isCollapse = !this.isCollapse
        this.asideWidth = this.isCollapse ? '65px': '250px'
        this.collapseIcon = this.isCollapse ? 'el-icon-s-unfold': 'el-icon-s-fold'
      },
      logout(){
        this.$confirm('Are you sure you want to log out?', 'Log out', {
          confirmButtonText: 'Yes',
          cancelButtonText: 'No',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: 'Log out successfully!'
          });
          localStorage.clear();
          this.$router.push('/');
        }).catch(() => {
          this.$message({
            type: 'info',
            message: 'Log out canceled'
          });
        });
      }
    }
  }
  </script>
  
  <style>
  .logo{
    width:40 px;
    height: 40px;
  }
  .el-menu--inline{
    background-color:#000c17 !important;
  }
  .el-menu--inline .el-menu-item{
    background-color:#000c17 !important;
    padding-left: 49px !important;
  }
  .el-menu-item:hover, el-submenu__title:hover {
    color: #fff !important;
  }
  .el-submenu__title:hover i{
    color: #fff !important;
  }
  .el-menu-item.is-active{
    background-color: #1890ff !important; 
    border-radius: 5px !important;
    width: calc(100% - 8px);
    margin-left: 4px;
  }
  .el-menu-item.is-active i, .el-menu-item.is-active .el-tooltip{
    margin-left: -4px;
  }
  .el-menu-item{
    height: 40px !important;
    line-height: 40px !important;
    
  }
  .el-submenu__title{
    height: 40px !important;
    line-height: 40px !important;
  }
  .el-submenu .el-menu-item{
    min-width:0 !important;
  }
  .el-menu--inline .el-menu-item.is-active{
    padding-left: 10px;
  }
  .el-aside{
    transition: width .5s;
    box-shadow: 2px 0 6px rgba(0,21,41,.35);
  }
  .el-header{
    box-shadow: 2px 0 6px rgba(0,21,41,.35);
    display: flex;
    align-items: center;
  }
  </style>