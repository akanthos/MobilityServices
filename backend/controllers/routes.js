module.exports = function (context) {

	// Sub controllers
	var authenticator = context.component('controllers').module('authenticator');
	var routeCtrl = context.component('controllers').module('route');
	var userCtrl = context.component('controllers').module('user');

	context.router
		// Authentication
		.post('/login', authenticator.authMiddleware)
		.post('/signup', authenticator.authMiddleware)
		.post('/logout', authenticator.authMiddleware, userCtrl.logout);

	context.router
		// Routing
		.get('/matching', authenticator.authMiddleware, routeCtrl.getMatching)
		.post('/route', authenticator.authMiddleware, routeCtrl.addRoute);

	context.router
		.get('/user', authenticator.authMiddleware, userCtrl.getUser)
		.post('/comment', authenticator.authMiddleware, userCtrl.comment);
	

	return {
		authCtrl : authenticator,
		routeCtrl : routeCtrl,
		userCtrl : userCtrl
	};
}
