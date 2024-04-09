<template>
  <div id="app">
    <el-container>
      <el-header style="background-color: #4c535a;">
        <img src="@/assets/logo.png" alt="" style="width: 50px; vertical-align: middle;">
        <span style="font-size:20px; color:white; vertical-align: middle;">MY SPCR</span>
      </el-header>
    </el-container>
    <el-container>
      <el-aside style="overflow:hidden ; min-height: 100vh; background-color: #545c64; width: 250px">
            <el-menu
          default-active="1"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b">
          <el-menu-item index = "1">
            <i class="el-icon-s-home"></i>
            <span slot="title">Home</span>  
          </el-menu-item>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>User Management</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="2-1">Instructor Management</el-menu-item>
              <el-menu-item index="2-2">Customer Management</el-menu-item>
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
  </div>
</template>

<script>
</script>

<style>
.el-menu{
  border-right: none !important;
}
</style>