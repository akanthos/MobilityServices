module.exports = function (context) {

	return {
		addRoute : function(req, res) {
			var route = req.body.route;

			var startAddress = req.body.start;
			var endAddress = req.body.end;
			var time = req.body.time;
			var day = req.body.day;

			var deferred1 = context.q.defer();
			context.rest.get('https://maps.googleapis.com/maps/api/geocode/json?address=' + startAddress + '&key=' + context.config.google.apiKey, function(data, response) {
				if(data.results.length == 0) {
					deferred1.reject();
				}
				else if(!data.results[0].geometry) {
					deferred1.reject();
				}
				else if(!data.results[0].geometry.location) {
					deferred1.reject();
				}
				deferred1.resolve(data.results[0].geometry.location);
			});
			var deferred2 = context.q.defer();
			context.rest.get('https://maps.googleapis.com/maps/api/geocode/json?address=' + endAddress + '&key=' + context.config.google.apiKey, function(data, response) {
				if(data.results.length == 0) {
					deferred2.reject();
				}
				else if(!data.results[0].geometry) {
					deferred2.reject();
				}
				else if(!data.results[0].geometry.location) {
					deferred2.reject();
				}
				deferred2.resolve(data.results[0].geometry.location);
			});

			context.q.all([deferred1.promise,deferred2.promise]).then(function(data) {
				var startLat = data[0].lat;
				var startLng = data[0].lng;
				var endLat = data[1].lat;
				var endLng = data[1].lng;

				var values = context.connection.escape(startLat) + ',' + context.connection.escape(startLng) + ',' + context.connection.escape(endLat) + ',' + context.connection.escape(endLng) + ',' + context.connection.escape(time) + ',' + context.connection.escape(day) + ',' + req.userId;
				context.connection.query('Insert into tbl_routes (startlat, startlong, endlat, endlong, time, weekday, userId) VALUES (' + values + ')', function(err, results) {
					if(err) {
						console.error(err);
						return res.status(500).send({err: err});
					}
					else if(results.length == 0) {
						return res.status(500).send({msg: "Error on inserting route"});
					}
					else {
						return res.status(200).send();
					}
				});

			}, function() {
				return res.status(500).send({msg: "Error on geocoding the address"});
			});
		},

		getMatching : function(req, res) {
	
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