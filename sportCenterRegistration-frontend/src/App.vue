<template>
  <div id="app">
    <div class="sidebar">
      <div class="logo">
        <div>
          <img src="./assets/logo.png" alt="logo" />
        </div>
        <div>My SPC</div>
      </div>
      <div class="navigation">
        <div class="dashboard sidebar-item active">
          <a href="#/dashboard">Dashboard</a>
        </div>
        <div class="class-info sidebar-item">
          <a href="#/class-info">Class Information</a>
        </div>
        <div class="schedule sidebar-item">
          <a href="#/schedule">Schedule</a>
        </div>
        <div class="features sidebar-item">
          <a href="#/features">Features</a>
        </div>
        <div class="settings sidebar-item">
          <a href="#/settings">Settings</a>
        </div>
      </div>
      <div>
        <div class="logout sidebar-item">
          <a href="#/logout">Log Out</a>
        </div>
      </div>
    </div>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "app",
  mounted() {
      const linkColor = document.querySelectorAll('.sidebar-item');
      linkColor.forEach(l => l.addEventListener('click', this.colorLink));
    },
  methods: {
    colorLink() {
      const linkColor = document.querySelectorAll('.sidebar-item');
      linkColor.forEach(l => l.classList.remove('active'));
      event.currentTarget.classList.add('active');
    }
  }
};
</script>

<style>
#app {
  box-sizing: border-box;
  background: linear-gradient(
    180deg,
    rgba(97, 237, 237, 1) 0%,
    rgba(153, 153, 153, 1) 100%
  );
  height: 1015px;
  position: relative;
  overflow: hidden;
}

.sidebar {
  background: #ffffff;
  border-radius: 16px;
  border-style: solid;
  border-color: #d9d9d9;
  border-width: 1px;
  display: flex;
  flex-direction: column;
  gap: 0px;
  align-items: flex-start;
  justify-content: flex-start;
  width: 256px;
  height: 920px;
  position: absolute;
  left: 35px;
  top: 52px;
}

.logo {
  margin: 30px;
  display: flex;
  font-size: 24px;
  font-weight: bold;
  width: 256px;
}

.logo img {
  margin-left: 20px;
  margin-right: 20px;
  width: 40px;
  height: 40px;
}

.navigation {
  display: flex;
  flex-direction: column;
  gap: 0px;
  align-items: flex-start;
  justify-content: flex-start;
  width: 256px;
  height: 100%;
  position: relative;
  left: 0px;
  top: 0px;
}

.sidebar-item {
  display: flex;
  justify-content: space-around;
  flex-direction: column;
  gap: 0px;
  align-items: flex-start;
  justify-content: flex-start;
  height: 50px;
  width: 80%;
  position: relative;
  left: 10%;
  right: 10%;
  top: 0px;
  border-color: #d9d9d9;
  border-width: 1px;
  font-size: 24px;
}

.active {
  background-color: #f0f0f0;
  border-radius: 16px;
}

a, a:hover{
  color: #000000;
  text-decoration: none;
}

</style>
