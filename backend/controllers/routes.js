module.exports = function (context) {

	// Sub controllers
	var authenticator = context.component('controllers').module('authenticator');
	var routeCtrl = context.component('controllers').module('route');

	context.router
		// Authentication
		.post('/login', authenticator.authMiddleware)
		.post('/signup', authenticator.authMiddleware);

	context.router
		// Routing
		//.get('/matching', authenticator.authMiddleware, routeCtrl.getMatching)
		.post('/route', authenticator.authMiddleware, routeCtrl.addRoute);
	

	return {
		authCtrl : authenticator,
		routeCtrl : routeCtrl
	};
}
