var context = {};
module.exports = {
	start : function (done) {

		// Imports
		var path            = require('path');
		var express 		= require('express');
		var bodyParser 	    = require('body-parser');
		var mysql 			= require('mysql');
		var fs				= require('fs');
		var uuid			= require('node-uuid');
		var rest 			= require('node-rest-client').Client;

		// Init context
		context = {};

		// Init config
		context.config = require('./config');
		var config = context.config;

		if (config.app.dev) {
			console.log('!!!');
			console.log('!!! WARNING: DEV MODE IS ON');
			console.log('!!!');
		}

		// Init promises
		context.q = require('q');

		// Add dependencies to the context
		context.fs = fs;
		context.uuid = uuid;

		// Init MySql
		console.log('Connection to MySQL...');
		context.connection = mysql.createConnection({
			host : context.config.mysql.host,
			user : context.config.mysql.user,
			password : context.config.mysql.pwd,
			database : context.config.mysql.db,
			multipleStatements : context.config.mysql.ms
		});
		context.connection.connect(function(err) {
  			if (err) {
    			console.error('error connecting: ' + err.stack);
    			return;
  			}
			console.log('CONNECTON ESTABLISHED');
			console.log('connected as id ' + context.connection.threadId);

			if(context.config.createDB) {
				console.log('Setting up database');
				var sql = fs.readFileSync('whoelse.sql').toString();
				context.connection.query(sql, function(err, results) {
					if(err) {
						console.log('Error setting up Database');
						throw err;
					}
					console.log('Database successfully created');
				});
			}

			// Init express app
			context.app = express();
			context.app.use(bodyParser.json());
			context.app.use(bodyParser.urlencoded());

			// Init router
			context.router = new express.Router();

			context.app.get('/', function(req, res) {
				res.send('<html><body>It works! The API is available under <strong>/api/*</strong></body></html>');
			});

			context.app.use('/api', context.router);
			context.router.use(function(req, res, next) {

				// log each request to the console
				console.log(req.method, req.url);

				// continue doing what we were doing and go to the route
				next();
			});

			// Init rest client
			var options = {
				mimetypes:{
        			json:["application/json","application/json; charset=utf-8"],
       				 xml:["application/xml","application/xml; charset=utf-8"]
    			} 
			};
			context.rest = new rest(options);

			context.component = function (compName) {

				if (!context[compName]) {
					context[compName] = {};
				}

				return {
					module : function (modName) {
						if (!context[compName][modName]) {
							context[compName][modName] = require(path.join(__dirname, compName, modName))(context, compName, modName);
							console.log('LOADED ' + compName + '.' + modName);
						}

						return context[compName][modName];
					}
				}
			};

			// Init routes module
			context.component('controllers').module('routes');

			// Done, start server actually
			console.log('Starting server...')
			context.server = context.app.listen(context.config.app.port, function () {
				console.log('    SERVER LISTENING ON PORT ' + context.config.app.port);
				console.log('---------- DONE BOOTSTRAPPING ----------');
				done(context);
			});
		});
	},
	stop: function (done) {
		if (!context || !context.server) {
			console.log('Server stopped.');
			return;
		}

		context.server.close(done);

	}
};