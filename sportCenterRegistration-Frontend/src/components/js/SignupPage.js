import axios from 'axios';
var config = require('../../../config')

// Setup frontend and backend urls 
var backendConfigurer = function(){
	switch(process.env.NODE_ENV){
    case 'development':
    return 'http://' + config.dev.backendHost + ':' + config.dev.backendPort;
    case 'production':
    return 'https://' + config.build.backendHost + ':' + config.build.backendPort ;
	}
};

var frontendConfigurer = function(){
	switch(process.env.NODE_ENV){
    case 'development':
    return 'http://' + config.dev.host + ':' + config.dev.port;
    case 'production':
    return 'https://' + config.build.host + ':' + config.build.port ;
	}
};

var backendUrl = backendConfigurer();
var frontendUrl = frontendConfigurer();

var AXIOS = axios.create({
	baseURL: backendUrl,
	headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
	name: 'signup',
	data() {
		return {
			email: '',
			password: '',
			accounttype: '',
			username: '',
			errorSignIn: '',
			successSignIn: '',
			confirmPassword: '',
		}
	},
    
	// Watch the account type to ensure that it is one of O, I, C
	watch: {
		accounttype(newVal) {
		if (!/^[OIC]*$/i.test(newVal)) {
			this.accounttype = '';
			this.errorSignIn = 'Account type must be one of O, I, C';
		}
		},
	},


	methods: {
		/*** Function to sign up a customer into the system. */
		signup: function (email, password, confirmPassword, username, accounttype) {
			if (password != confirmPassword) {
				this.errorSignIn = "Passwords do not match."
			} else {
				AXIOS.post('/create_customer/', {}, {
					params: {
						email: email,
						address: address,
						name: name,
						password: password,
					}
				})
					.then(response => {
						if (response.status === 200) {
							// If the creatin of the customer was successfull, empty all the fields and display a success message!
							this.password = '',
							this.confirmPassword = '',
							this.accounttype = '',
							this.username = '',
							this.email = '',
							this.successSignUp = 'Account created successfully!',
							localStorage.setItem('email', this.user.email)
							localStorage.setItem('type', this.type)
						}
					})
					.catch(e => {
						// Display the error message.
						this.errorSignUp = e.response.data
						console.log(this.errorLogIn)
					})

			}

		}
	}
}