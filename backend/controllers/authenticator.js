module.exports = function (context) {

	// Imports
	var userCtrl = context.component('controllers').module('user');

	return {
		authMiddleware : function (req, res, next) {
			res.header('Access-Control-Allow-Origin', '*');
    		res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
			if(req.url == '/login') {
				userCtrl.login(req.body.email, req.body.password).then(function(respo) {
					return res.status(200).send(respo);
				},
				function(error) {
					return res.status(401).send();
				});
			}
			else if(req.url == '/signup') {
				userCtrl.signup(req.body).then(function(userId) {
					return res.status(200).send({userId: userId});
				},
				function(error) {
					return res.status(500).send({ msg : 'Error creating user.'});
				});
			}
			else {
				var token = req.get('token');
				if(!token) {
					return res.status(401).send();
				}
				userCtrl.checkToken(token).then(function(userId) {
					req.userId = userId
					next();
				},
				function(error) {
					return res.status(401).send();
				});
			}
		}
	};
};
