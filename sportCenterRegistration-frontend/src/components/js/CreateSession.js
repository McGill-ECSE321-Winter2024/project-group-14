import axios from 'axios';
var config = require('../../../config')

// Setup the backend and frontend urls
var backendConfigurer = function () {
    switch (process.env.NODE_ENV) {
        case 'development':
            return 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;
        case 'production':
            return 'https://' + config.build.backendHost + ':' + config.build.backendPort;
    }
};

var frontendConfigurer = function () {
    switch (process.env.NODE_ENV) {
        case 'development':
            return 'http://' + config.dev.host + ':' + config.dev.port;
        case 'production':
            return 'https://' + config.build.host + ':' + config.build.port;

    }
};

var backendUrl = backendConfigurer();
var frontendUrl = frontendConfigurer();

var AXIOS = axios.create({
    baseURL: backendUrl
})

// function InstructorDto(id, username, email, password) {
//     this.username = username;
//     this.id = id;
//     this.email = email;
//     this.password = password;
// }

// function sportClassDto(name, approved) {
//     this.name = name;
//     this.approved = approved;
// }

export default {
    name: 'superviseSession',
    data() {
        return {
            instructorName: '',
            sportClassName: '',
            startTime: '',
            endTime: '',
            location: '',
            date: '',
            sessions: [],
            newSession: [],
            response: []
        }
    },
    created() {
        this.newSession = '';
    },
    methods: {
        realCreateSession: function () {
            // this.instructorName = document.getElementById("instructorInput").innerText;
            // this.sportClassName = document.getElementById("sportClassInput").innerText;
            // this.startTime = document.getElementById("startTimeInput").innerText;
            // this.endTime = document.getElementById("endTimeInput").innerText;
            // this.location = document.getElementById("locationInput").innerText;
            // this.date = document.getElementById("dateInput").innerText;

            // //testing
            // console.log(document.getElementById("instructorInput").innerHTML);
            // console.log(document.getElementById("sportClassInput").innerText);
            // console.log(document.getElementById("startTimeInput").innerText);
            // console.log(document.getElementById("endTimeInput").innerText);
            // console.log(document.getElementById("locationInput").innerText);
            // console.log(document.getElementById("dateInput").innerText);

            AXIOS.post('/create_session/', null, {
                params: {
                    startTime: this.startTime,
                    endTime: this.endTime,
                    location: this.location,
                    date: this.date,
                    instructorName: this.instructorName,
                    sportclassName: this.sportClassName
                }
            })
                .then(response => {
                    alert("success!");
                    //reseting vars
                    this.instructorName = '';
                    this.sportClassName = '';
                    this.startTime = '';
                    this.endTime = '';
                    this.location = '';
                    this.date = '';
                })
                .catch(e => {
                    const errorMsg = e.response.data.message;
                    console.log(errorMsg);
                    alert(errorMsg);
                })
        },
    }
}
