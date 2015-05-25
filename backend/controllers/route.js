module.exports = function (context) {

	return {
		addRoute : function(req, res) {
			var route = req.body.route;
			var token = req.get('token');

			var startAddress = req.body.start;
			var startAddress = req.body.start;
			var startAddress = req.body.start;

			context.connection.query('Insert into tbl_routes () VALUES ()', function(err, results) {
				if(err) {
					console.error(err);
					return res.status(500).send({err: err});
				}
				else if(results.length == 0) {
					return res.status(500).send();
				}
				else {
					// TODO matching algorithm
				}
			});
		},

		getMachting : function(req, res) {
	
			context.connection.query('Select tbl_routes.routeId from tbl_routes INNER JOIN tbl_matchings ON (tbl_matchings.routeId1 = tbl_routes.routeId OR tbl_matchings.routeId2 = tbl_routes.routeId) AND tbl_routes.userId = ' + req.userId, function(err, results) {
				if(err) {
					console.error(err);
					return res.status(500).send({err: err});
				}
				else if(results.length == 0) {
					return res.status(200).send({matching: null});
				}
				else {
					console.log(results);
					return res.status(200).send({matching: results[0]});
				}
			});
		}
	};
};