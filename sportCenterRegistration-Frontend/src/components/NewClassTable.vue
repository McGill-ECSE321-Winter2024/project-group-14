<template>
  <div id="Centering">
  <div id="NewClassTable">
    <div class="inputdata">
    <h2> Enter a new Class Name </h2>
            <input type="text" placeholder="New Class Name" v-model="SportClassName1">
    </div>
    <div class="inputdata">
    <h2> Confirm Class Name </h2>
          <input type="text" placeholder="Confirm Class Name" v-model="SportClassName2">
    </div>
    <div class="inputdata">
    <button v-bind:disabled="!SportClassName1 || !SportClassName2" @click="sameInput">
    Confirm
    </button>
    <p>
    <span v-if="errorSportClass" style="color:red">Error: {{errorSportClass}} </span>    </p>
    </div>
  </div>
</div>
</template>
<script src="./NewClass.js">
</script>

<style>
  body {
    background: linear-gradient(to bottom, #61EDED, #999999);
    display: flex;
    justify-content: center; 
    align-items: center; 
    height: 100vh; 
    overflow-x: hidden;
  }

  #NewClassTable {
    background: white;
    border: 1px solid #ccc;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 50vh;
    text-align: center;
    width: 200%;
    border-radius: 10px;
  }


   .inputdata {
    width: 75%; 
    padding: 5px; 
   
  }

  #NewClassTable input {
    border=color : white;
    border-radius: 5px;
    width: 100%;
  }

  #NewClassTable h2{
    width:200%;
    text-align:left;

  }

  #NewClassTable button {
    display:block;
    text-align: center;
    margin-top: 10px;
    padding: 5px 10px;

    width: 100%; 
  border-radius: 10px;
    border: none;
    background-color: #000; 
    color: white; 
    cursor: pointer;
  }


 
</style>



