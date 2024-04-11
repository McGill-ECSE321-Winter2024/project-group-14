import axios from 'axios';
var config = require('../../config')

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

function SessionDTO(date, startTime, endTime, id, location, instructorName, sportClassName) {
    this.date = date;
    this.startTime = startTime;
    this.endTime = endTime;
    this.id = id;
    this.location = location;
    this.instructorName = instructorName;
    this.sportClassName = sportClassName;
}

export default {
    name: 'addClasses',
    data() {
        return {
            date: '',
            startTime: '',
            endTime: '',
            id: '',
            location: '',
            instructorName: '',
            sportClassName: '',
            session: '',
            sessions: [],
            error: '',
            response: []
        }
    },
    created() {
        this.getSessions();
    },
    methods: {
        makeSessionIntoDto: function (date, startTime, endTime, id, location, instructorName, sportClassName) {
            this.session = new SessionDTO(date, startTime, endTime, id, location, instructorName, sportClassName);
            //storing session
            this.sessions.push(this.session);

            //reseting fields for next sessions
            this.date = '';
            this.startTime = '';
            this.endTime = '';
            this.id = '';
            this.location = '';
            this.instructorName = '';
            this.sportClassName = '';
            this.session = '';
        },
        getSessions() {
            AXIOS.get("/view_session")
                .then(response => {
                    if (response.status === 200) {
                        let placeholder = [];
                        placeholder = response.data;
                        //for testing
                        // let testingSession = new SessionDTO("2024/04/06", "11:00:00", "12:00:00", 1, "gym", "loridy", "football");
                        // placeholder.push(testingSession);

                        for (let i = 0; i < placeholder.length; i++) {
                            this.makeSessionIntoDto(placeholder[i].date,
                                placeholder[i].startTime, placeholder[i].endTime, placeholder[i].id,
                                placeholder[i].location, placeholder[i].instructorName, placeholder[i].sportClassName);
                        }

                    }
                    location.reload();
                })
                .catch(e => {
                    this.error = e.data;
                    console.log(this.error)
                })
        }
    }
}