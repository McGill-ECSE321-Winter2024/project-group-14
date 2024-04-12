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
            currentSession: '',
            sessions: [],
            error: '',
            selectedSessions: [],
            response: []
        }
    },
    created() {
        this.getSessions();
    },
    methods: {
        makeSessionIntoDto: function (date, startTime, endTime, id, location, instructorName, sportClassName) {
            this.currentSession = new SessionDTO(date, startTime, endTime, id, location, instructorName, sportClassName);
            //storing session
            this.sessions.push(this.currentSession);

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
            AXIOS.get("/view_sessions/")
                .then(response => {
                    if (response.status === 200) {
                        const placeHolderSessions = response.data;

                        placeHolderSessions.forEach(session => {
                            const id = session.id;
                            const startTime = session.startTime;
                            const endTime = session.endTime;
                            const location = session.location;
                            const date = session.date;
                            const instructorName = session.instructor.username;
                            const sportClassName = session.sportClass.name;
                            this.makeSessionIntoDto(date, startTime, endTime, id, location, instructorName, sportClassName);
                        });
                    }
                })
                .catch(e => {
                    this.error = e.data;
                    console.log(this.error);
                });
        },
        //user clicks on the row with the session they want to add and it gets stored so when they press add it adds them all
        selectSession(session) {
            var placeHolderTable = document.getElementById("sessionTableList");
            let i = 0;

            //find row with the correct id
            for (i = 0; i < placeHolderTable.tBodies[0].rows.length && session.id != placeHolderTable.tBodies[0].rows[i].cells[0].innerText; i++) {
            }

            var placeHolderRow = '';
            //if table body is empty (no sessions)
            if (placeHolderTable.tBodies[0].rows.length > 0) {
                placeHolderRow = placeHolderTable.tBodies[0].rows[i];
            }

            //check if it is already added (bg would be red)
            if (placeHolderRow !== '' && placeHolderRow !== null
                && placeHolderRow !== undefined
                && placeHolderRow.style.backgroundColor !== "red") {

                placeHolderRow.style.backgroundColor = "red";

                //store in selected sessions so that when the user presses add
                //, it attempts to register them to all the selected sessions
                this.selectedSessions.push(session);
            }
            //remove it since user clicked it again to deselect it
            else {
                placeHolderRow.style.backgroundColor = "white";
                this.selectedSessions.splice(this.selectedSessions.indexOf(session), 1);
            }

        },

        registerToSessions() {
            this.selectedSessions.forEach(session => {
                //do a POST request with register
            })
        }
    }
}
