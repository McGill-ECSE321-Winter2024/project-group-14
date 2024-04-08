<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
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
  flex-wrap: wrap;
  gap: 0px;
  align-content: flex-start;
  align-items: flex-start;
  justify-content: flex-start;
  height: 50px;
  width: 90%;
  position: relative;
  left: 10%;
  /* right: 10%; */
  top: 0px;
  border-color: #d9d9d9;
  border-width: 1px;
  font-size: 24px;
}

svg{
  margin-left: 5px;
  margin-top: 5px;
}

.active {
  background-color: #f0f0f0;
  border-radius: 8px;
}

a, a:hover{
  margin-left: 10px;
  color: #000000;
  text-decoration: none;
}

</style>
