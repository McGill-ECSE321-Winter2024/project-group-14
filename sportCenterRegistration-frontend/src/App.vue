<template>
  <div>
    <el-container>
      <el-aside :width="asideWidth" style="min-height:100vh; background-color: #001529">
        <div style="height:60px; color:white; display:flex; align-items:center; justify-content:center">
        logo
          <span style="margin-left: 4px; font-size:18px" v-show="!isCollapse">SPCR</span>
        </div>


        <el-menu :collapse="isCollapse":collapse-transition="false" router background-color=" #001529" text-color ="rgba(255,255,255,0.65)" active-text-color="#fff" style=" border:none" :default-active="$route.path">
          <el-menu-item index="/Home">
              <i class="el-icon-house"></i>
              <span slot ="title">Home</span>
          </el-menu-item>

          <el-submenu>
            <template slot="title">
              <i class="el-icon-s-custom"></i>
                <span>User Management</span>
            </template>
              <el-menu-item-group>
                <el-menu-item index="/ManageInstructor">
                  <!-- <i class="el-icon-s-custom"></i> -->
                  <span>Instructor Management</span>
                </el-menu-item>
                <el-menu-item index="/ManageCustomer">
                  <!-- <i class="el-icon-user-solid"></i> -->
                  <span>Customer Management</span>
                </el-menu-item>
              </el-menu-item-group>
            </el-submenu>

            <el-menu-item index="3">
              <i class="el-icon-menu"></i>
              <span slot="title">Schedules</span>
            </el-menu-item>

            <el-menu-item index="4">
              <i class="el-icon-setting"></i>
              <span slot="title">Settings</span>
            </el-menu-item>       

        </el-menu>
      </el-aside>

      <el-container>
        <el-header>
          <i :class="collapseIcon" style="font-size:25px ;" @click="handleCollapse"></i>
          <el-breadcrumb separator-class="el-icon-arrow-right" style="margin-left:20px">
            <el-breadcrumb-item :to="{ path: '/' }">Home</el-breadcrumb-item>
            <el-breadcrumb-item :to="{ path: '/ManageInstructor' }">Instructor Management</el-breadcrumb-item>
          </el-breadcrumb>
        </el-header>

        <el-main>
          Main
        </el-main>

      </el-container>

    </el-container>
  </div>
  <!-- <div id="app">
    <el-container>
      <el-header style="background-color: #4c535a;">
        <img src="@/assets/logo.png" alt="" style="width: 50px; vertical-align: middle;">
        <span style="font-size:20px; color:white; vertical-align: middle;">MY SPCR</span>
      </el-header>
    </el-container>
    <el-container>
      <el-aside style="overflow:hidden ; min-height: 100vh; background-color: #545c64; width: 250px">
            <el-menu :default-active="$route.path" router background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b">
          <el-menu-item index = "/Home">
            <i class="el-icon-s-home"></i>
            <span slot="title">Home</span>  
          </el-menu-item>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>User Management</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/ManageInstructor">
                <i class="el-icon-s-custom"></i>
                <span>Instructor Management</span>
              </el-menu-item>
              <el-menu-item index="/ManageCustomer">
                <i class="el-icon-user-solid"></i>
                <span>Customer Management</span>
              </el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-menu-item index="3">
            <i class="el-icon-menu"></i>
            <span slot="title">Schedules</span>
          </el-menu-item>
          <el-menu-item index="4">
            <i class="el-icon-setting"></i>
            <span slot="title">Settings</span>
          </el-menu-item>
        </el-menu>
        
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div> -->
</template>

<script>
export default{
  data(){
    return{
      isCollapse: false,
      asideWidth: '200px',
      collapseIcon: 'el-icon-s-fold'
    }
  },
  methods:{
    handleCollapse(){
      this.isCollapse = !this.isCollapse
      this.asideWidth = this.isCollapse ? '64px': '200px'
      this.collapseIcon = this.isCollapse ? 'el-icon-s-unfold': 'el-icon-s-fold'
    }
  }
}
</script>

<style>
.el-menu{
  border-right: none !important;
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