import axios from 'axios'
import config from '../../config'

const frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
const backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

const AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

function SportClassDto(name) {
    this.name = name;
}

export default {
    name: 'ApproveNewClass',
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
        AXIOS.get('/sport-class/all')
            .then(response => {
                this.SportClasses = response.data
            })
            .catch(e => {
                this.errorSportClass = e
            })
    },
    methods: {
        approveSportClass: function (SportClassName) {
            AXIOS.put('/sport-class/approve'.concat(SportClassName), {}, {})
                .then(response => {
                    // JSON responses are automatically parsed.
                    this.SportClasses.push(response.data)
                    this.errorSportClass = ''
                    this.SportClassName1 = ''
                    this.SportClassName2 = ''
                    this.errorSportClass = "test"
                })

                .catch(e => {
                    this.errorSportClass = "test3"
                    const errorMsg = e.response.data.message
                    console.log(errorMsg)
                    this.errorSportClass = errorMsg
                })
        }
    }
}