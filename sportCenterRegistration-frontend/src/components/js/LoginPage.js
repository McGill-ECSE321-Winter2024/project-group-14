//1.click homepage button go to homepage
//2.Enter info, and click login button, login the user into the system
//3.click signup button, go to signup page


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
	baseURL: backendUrl,
	// headers: { 'Access-Control-Allow-Origin': frontendUrl }
})



export default {
	name: 'login',
	data() {
		return {
			user: '',
			type: '',
			email: '',
			username: '',
			password: '',
			errorLogin: '',
			response: []
		}
	},


	methods: {
		// Function to login the user
		login: function (email, password) {
			console.log(decodeURIComponent(email));
			var params;
			if (email.includes("@")) {
				params = {
					email: email,
					password: password
				}
			} else {
				params = {
					username: email,
					password: password
				}
			}
			AXIOS.get('/login', {
				params: params
			})
				.then(response => {
					if (response.status === 200) {
						this.user = response.data
						// Get the user type to determine the corresponding page it should be sent to.
						console.log(this.user)

						// Store the customer information
						localStorage.setItem('username', this.user.username)
						localStorage.setItem('email', this.user.email)
						localStorage.setItem('type', this.user.type)

						console.log(localStorage.getItem('type'))

						if (this.user.type.localeCompare("Customer") == 0) {

							window.location.href = "/#/customerapp"
						}
						else if (this.user.type.localeCompare("Instructor") == 0) {
							window.location.href = "/#/instructorApp"
						}
						else {
							window.location.href = "/#/ownerapp"
						}
						// Send the customer to the next page
						location.reload();
					}
				})
				.catch(e => {
					// Display the error
					this.errorLogin = e.data
					console.log(this.errorLogIn)
				})
		}
	}

}