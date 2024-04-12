import axios from 'axios'
import config from '../../config'

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

const AXIOS = axios.create({
    baseURL: backendUrl,
    // headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function SportClassDto(name) {
    this.name = name;
}

export default {
    name: 'ApproveClass',
    data() {
        return {
            SportClasses: [],
            SportClassNames: [],
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
            if (this.SportClassName1 != this.SportClassName2) {
                this.errorSportClass = "Sport Class names do not match";
            }

            else if (this.SportClassNames.includes(this.SportClassName1)) {
                this.errorSportClass = "A Sport Class with this name already exists";
            }

            else if (this.SportClassName1 == this.SportClassName2 && this.errorSportClass == '') {
                this.createSportClass(this.SportClassName1);
            }

        },
        createSportClass: function (SportClassName) {

            AXIOS.put('/sport-class/approve/'.concat(SportClassName), {}, {})
                .then(response => {
                    // JSON responses are automatically parsed.
                    this.SportClasses.push(response.data)
                    this.errorSportClass = ''
                    this.SportClassName1 = ''
                    this.SportClassName2 = ''
                    this.SportClassNames.push(this.SportClassName1)
                })

                .catch(e => {
                    const errorMsg = e.response.data.message
                    console.log(errorMsg)
                    this.errorSportClass = errorMsg
                })
        }
    }
}

