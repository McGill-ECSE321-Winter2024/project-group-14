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
    <span v-if="errorSportClass" style="color:red">Error: {{errorSportClass}} </span>    
    </p>
    </div>
  </div>
</div>
</template>
<script>
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
    name: 'AddNewClass',
    data() {
        return {
            SportClasses: [],
            SportClassName1: '',
            SportClassName2: '',
            errorSportClass: '',
            response: []
        }
    },
    created: function () {
        // Initializing persons from backend
        //AXIOS.get('/sport-class/all')
        //    .then(response => {
        //        this.SportClasses = response.data
        //   })
        //    .catch(e => {
        //        this.errorSportClass = e
        //    })
    },
    methods: {
        sameInput: function () {
            if (this.SportClassName1 == this.SportClassName2) {
                this.createSportClass(this.SportClassName1);
            }
            else {
                this.errorSportClass = "Sport Class Names do not Match";
            }
        },
        createSportClass: function (SportClassName) {

            AXIOS.post('/sport-class/'.concat(SportClassName), {}, {})
                .then(response => {
                    // JSON responses are automatically parsed.
                    this.SportClasses.push(response.data)
                    this.errorSportClass = ''
                    this.SportClassName1 = ''
                    this.SportClassName2 = ''
                })

                .catch(e => {
                    const errorMsg = e.response.data.message
                    console.log(errorMsg)
                    this.errorSportClass = errorMsg
                })
        }
    }
}


</script>

<style>
  body {
    background: white;
    align-items: center; 
    height: 100vh; 
  }

  #NewClassTable {
     overflow: hidden;
    background: white;
    border: 1px solid #ccc;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 50vh;
    text-align: center;
    width: 75%;
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



