import axios from 'axios';
import { sign } from 'crypto';
import { data } from 'jquery';
var config = require('../../../config')

// Setup frontend and backend urls
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
	name: 'signup',
	data() {
		return {
			email: '',
			password: '',
			username: '',
			errorSignUp: '',
			successSignUp: '',
			confirmPassword: '',
		}
	},

	// Watch the account type to ensure that it is one of O, I, C
	// watch: {
	// 	accounttype(newVal) {
	// 		if (!/^[OIC]*$/i.test(newVal)) {
	// 			this.accounttype = '';
	// 			this.errorSignUp = 'Account type must be one of O, I, C';
	// 		}
	// 	},
	// },


	methods: {
		/*** Function to sign up a customer into the system. */
		signup: function (email, password, confirmPassword, username) {
			if (password != confirmPassword) {
				this.errorSignUp = "Passwords do not match."
			} else {
				AXIOS.post('/customer', null, {
					params: {
						username: username,
						email: email,
						password: password
					}
				})
					.then(response => {
						if (response.status === 200) {
							// If the creatin of the customer was successfull, empty all the fields and display a success message!
							console.log(response.data)
							this.user = response.data
							// Store the customer information
							localStorage.setItem('username', this.user.username)
							localStorage.setItem('email', this.user.email)
							localStorage.setItem('type', this.user.type)

							window.location.href = "/#/customerapp"
							this.password = '',
								this.confirmPassword = '',
								this.username = '',
								this.email = '',
								this.successSignUp = 'Account created successfully!',
								localStorage.setItem('email', this.user.email)
						}
					})
					.catch(e => {
						// Display the error message.
						this.errorSignUp = e.data
						console.log(this.errorLogIn)
					})

			}

		}
	}
}