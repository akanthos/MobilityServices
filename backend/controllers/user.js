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
		},

		logout : function(req, res) {
			checkToken(req.get('token')).then(function(userId) {
				context.connection.query('Update tbl_users SET token = null where userId = ' + userId, function(err, results) {
					if(err) {
						console.error(err);
						return res.status(500).send();
					}
					else {
						return res.status(200).send();
					}
				});
			}, function() {
				return res.status(500).send({msg: 'User not found'});
			});
		},

		getUser : function(req, res) {
			var id = req.get('userId');
			context.connection.query('Select email, name, surname, age, gender, about from tbl_users where userId = ' + id, function(err, results) {
				if(err) {
					console.error(err);
					return res.status(500).send();
				}
				else if(results.length == 0) {
					console.log("user not found");
					return res.status(500).send({msg: "User not found"});
				}
				else {
					context.connection.query('Select comment from tbl_comments where userId = ' + id, function(err2, results2) {
						if(err2) {
							console.error(err2);
							return res.status(500).send();
						}
						else {
							results[0].comments = new Array();
							for(var i = 0; i < results2.length; ++i) {
								results[0].comments.push(results2[i]);
							}
							return res.status(200).send({user: results[0]});
						}
					});
				}
			})
		},

		comment : function(req, res) {
			var poster = req.userId;
			var userId = req.body.userId;
			var text = req.body.comment;
			var rating = req.body.rating;

			var values = poster + ',' + userId + ',' + context.connection.escape(text) + ',' + context.connection.escape(rating);
			context.connection.query('Insert into tbl_comments (userIdPost, userId, comment, rating) VALUES(' + values + ')', function(err, results) {
				if(err) {
					console.error(err);
					return res.status(500).send();
				}
				else {
					return res.status(200).send({commentId: results.insertId});
				}
			})
		}
	};
};