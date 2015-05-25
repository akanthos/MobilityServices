module.exports = function (context) {

	// Sub controllers
	var authenticator = context.component('controllers').module('authenticator');

	context.router
		// Authentication
		.post('/login', authenticator.authMiddleware)
		.post('/signup', authenticator.authMiddleware);
	

	return {
		authCtrl : authenticator
	};
}
