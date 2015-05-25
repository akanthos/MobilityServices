module.exports = function (context) {

	return {
		login : function(email, pwd) {
			var deferred = context.q.defer();
			var sql = 'Select * from tbl_users where email = ' + context.connection.escape(email) + ' and password = ' + context.connection.escape(pwd) + ';';
			context.connection.query(sql, function(err, results) {
				if(err) {
					console.error(err);
                   	deferred.reject();
				}
				if(results.length == 0) {
					console.log('User does not exist in database...');
		 			deferred.reject();
				}
				else {
					var token = context.uuid.v4();
					context.connection.query('Update tbl_users SET token = ' + context.connection.escape(token) + ' where email = ' + context.connection.escape(email), function(err, results) {
						if(err) {
							console.error(err);
							deferred.reject();
						}
						deferred.resolve(token);
					});
				}
			});
			return deferred.promise;
		},

		signup : function(user) {
			var deferred = context.q.defer();
			var values =  context.connection.escape(user.name) + ',' + context.connection.escape(user.surname) + ','
						  + context.connection.escape(user.password) + ',' + context.connection.escape(user.email) + ',' + context.connection.escape(user.age) + ',' + context.connection.escape(user.gender) + ',' + context.connection.escape(user.about);
			context.connection.query('Insert into tbl_users (name, surname, password, email, age, gender, about) VALUES(' + values + ')', function(err, results) {
				if(err) {
					console.error(err);
					deferred.reject();
				}
				else if(results.length == 0) {
					deferred.reject();
				}
				else {
					deferred.resolve(results.insertId);
				}
			});
			return deferred.promise;
		},

		checkToken : function(token) {
			var deferred = context.q.defer();
			context.connection.query('Select * from tbl_users where token = ' + context.connection.escape(token), function(err, results) {
				if(err) {
					console.error(err);
					deferred.reject();
				}
				if(results.length == 0) {
					console.log('User is not authenticated');
					deferred.reject();
				}
				else {
					deferred.resolve(results[0].userId);
				}
			});
			return deferred.promise;
		}
	};
};