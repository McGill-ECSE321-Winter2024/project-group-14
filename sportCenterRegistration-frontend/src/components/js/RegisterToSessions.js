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
            selectedToRegisterSessions: [],
            toDeleteSession: '',
            //key value pairs of session ids and registrations
            successfulRegistrations: {},
            response: []
        }
    },
    mounted() {
        this.getSessions();

        setTimeout(() => {
            this.colorTable();
        }, 50);

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
        colorTable() {
            var placeHolderTable = document.getElementById("sessionTableList");

            let registrationList = '';
            if (localStorage.getItem(localStorage.getItem("username")) !== null
                && localStorage.getItem(localStorage.getItem("username")) !== undefined
                && localStorage.getItem(localStorage.getItem("username")) !== ''
                && Object.keys(localStorage.getItem(localStorage.getItem("username"))).length > 0) {
                registrationList = localStorage.getItem(localStorage.getItem("username"));
            } else {
                registrationList = this.successfulRegistrations;
            }

            for (let i = 0; i < placeHolderTable.rows.length; i++) {
                let currentId = placeHolderTable.rows[i].cells[0].innerText;

                if (registrationList == '') {
                    placeHolderTable.rows[i].style.backgroundColor = "white";
                } else if (registrationList[currentId] !== null && registrationList[currentId] !== undefined) {
                    placeHolderTable.rows[i].style.backgroundColor = "green";
                } else {
                    placeHolderTable.rows[i].style.backgroundColor = "white";
                }
            }
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
        //user clicks on the row with the session they want to add/delete and it gets stored so when they press add it adds them all
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

            //check if it is white (not added / successfully registered to / unsuccessfully registered to)
            if (placeHolderRow !== '' && placeHolderRow !== null
                && placeHolderRow !== undefined
                && placeHolderRow.style.backgroundColor !== "red"
                && placeHolderRow.style.backgroundColor !== "green"
                && placeHolderRow.style.backgroundColor !== "orange") {

                placeHolderRow.style.backgroundColor = "red";

                //store in selected sessions so that when the user presses add
                //, it attempts to register them to all the selected sessions
                this.selectedToRegisterSessions.push(session);
            }

            //remove it since user clicked it again to deselect it
            else if (placeHolderRow !== '' && placeHolderRow !== null
                && placeHolderRow !== undefined
                && placeHolderRow.style.backgroundColor === "red") {

                placeHolderRow.style.backgroundColor = "white";
                this.selectedToRegisterSessions.splice(this.selectedToRegisterSessions.indexOf(session), 1);
            }
            //green/orange that the user already registered for and can't register for respectively
            else {
                this.toDeleteSession = session;
            }

        },

        registerToSessions() {
            this.selectedToRegisterSessions.forEach(session => {

                //get the row to alter to signify successful registration or failed registration
                var placeHolderTable = document.getElementById("sessionTableList");
                let i = 0;

                //find row with the same id as session
                for (i = 0; i < placeHolderTable.tBodies[0].rows.length && session.id != placeHolderTable.tBodies[0].rows[i].cells[0].innerText; i++) {
                }

                var placeHolderRow = '';
                //if table body is empty (no sessions)
                if (placeHolderTable.tBodies[0].rows.length > 0) {
                    placeHolderRow = placeHolderTable.tBodies[0].rows[i];
                }

                console.log(session.id);
                console.log(localStorage.getItem("username"));
                //do a POST request with register
                AXIOS.post("/registration/" + localStorage.getItem("username") + "/" + session.id)
                    .then(response => {
                        //registration was a success

                        if (placeHolderRow !== '' && placeHolderRow !== null
                            && placeHolderRow !== undefined) {

                            placeHolderRow.style.backgroundColor = "green";

                            //saving successful registration
                            let idToSave = session.id;
                            console.log("this is response.data.id for registerations: " + response.data.id);
                            this.successfulRegistrations.idToSave = response.data.id;

                            //registrations already exist
                            if (localStorage.getItem(localStorage.getItem("username")) !== null
                                && localStorage.getItem(localStorage.getItem("username")) !== undefined
                                && localStorage.getItem(localStorage.getItem("username")) !== ''
                                && Object.keys(localStorage.getItem(localStorage.getItem("username"))) !== null
                                && Object.keys(localStorage.getItem(localStorage.getItem("username"))) !== undefined
                                && Object.keys(localStorage.getItem(localStorage.getItem("username"))) !== ''
                                && Object.keys(localStorage.getItem(localStorage.getItem("username"))).length > 0) {


                                localStorage.setItem(localStorage.getItem("username"),
                                    Object.assign({}, localStorage.getItem(localStorage.getItem("username"), this.successfulRegistrations)));

                                // console.log("this is if: " + localStorage.getItem(localStorage.getItem("username")));
                            }

                            else { //first registration saved to local storage
                                localStorage.setItem(localStorage.getItem("username"), this.successfulRegistrations);

                                // console.log("this is else: " + localStorage.getItem(localStorage.getItem("username")));
                            }
                        }
                    })
                    .catch(e => {
                        //failed to register
                        if (placeHolderRow !== '' && placeHolderRow !== null
                            && placeHolderRow !== undefined) {

                            placeHolderRow.style.backgroundColor = "orange";
                        }

                        this.error = e.data;
                        console.log(e);
                        console.log(e.data);
                        alert(e.data);
                    })
            })
            //reset the list of selected sessions
            this.selectedToRegisterSessions = [];
        },
        deleteRegistration() {
            let toDeleteSessionId = this.toDeleteSession.id;
            let registrations = Object.assign({}, localStorage.getItem(localStorage.getItem("username")), this.successfulRegistrations);
            this.successfulRegistrations = Object.assign({}, localStorage.getItem(localStorage.getItem("username")), this.successfulRegistrations);

            if (registrations[toDeleteSessionId] !== null && registrations[toDeleteSessionId] !== undefined) {
                //dictionary where value is the registrationid and the key is the sessionid
                AXIOS.delete("/registration/" + registrations[toDeleteSessionId])
                    .then(response => {
                        // Successful deletion
                        alert("Success!");
                        delete registrations[toDeleteSessionId];
                        //update localstorage
                        localStorage.setItem(localStorage.getItem("username"), registrations);

                        // Update the row color to white
                        let placeHolderTable = document.getElementById("sessionTableList");
                        for (let i = 0; i < placeHolderTable.rows.length; i++) {
                            if (placeHolderTable.rows[i].cells[0].innerText === toDeleteSessionId.toString()) {
                                placeHolderTable.rows[i].style.backgroundColor = "white";
                                break; // Exit the loop once the row is found and updated
                            }
                        }
                    })
                    .catch(e => {
                        this.error = e.data;
                        console.log(e);
                        console.log(e.data);
                        alert("error")
                    });
            }
        }
    }
}