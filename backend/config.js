/**
 * Config holder
 *
 * @type {Object}
 */
module.exports = {
	app : {
		dev : process.env.DEV || false,
		port : process.env.PORT || 3000,
		baseUrl : process.env.BASE_URL || 'http://localhost:3000'
	},
	mongodb : {
		uri: process.env.MONGO_URI
	},
	mysql: {
		host : 'localhost',
		user : 'whoelse-client',
		pwd  : 'whoelse-is-secret',
		db   : 'whoelse',
		ms   : true
	},
	createDB : false,
	google: {
		apiKey : 'AIzaSyCQdHibiEyMOEtLt2I2wytp6d0HA4C3rzo'
	}
};
