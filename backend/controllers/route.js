module.exports = function (context) {

	var matchRoutes = function(route, insertId, userId) {
		context.connection.query('Select * from tbl_routes where routeId != ' + insertId + ' and userId != ' + userId + ' and weekday = ' + context.connection.escape(route.weekday), function(err, results) {
			if(err) {
				console.error(err);
			}
			else {
				var promises = new Array();
				for(var i = 0; i < results.length; ++i) {
					var routeTime = route.time;
					var time = results[i].time;
					if(parseInt(time.split(':')[0]) >= parseInt(routeTime.split(':')[0]) - 1 && parseInt(time.split(':')[0]) <= parseInt(routeTime.split(':')[0]) + 1) {
						var routes = compareRoute(route, results[i]);
						var start = {
							lat: routes.routeA.startlat,
							lng: routes.routeA.startlng
						};
						var end = {
							lat: routes.routeB.startlat,
							lng: routes.routeB.startlng
						};
						var dis1 = haversine(start, end);
						start = {
							lat: routes.routeA.endlat,
							lng: routes.routeA.endlng
						};
						end = {
							lat: routes.routeB.endlat,
							lng: routes.routeB.endlng
						};
						var dis2 = haversine(start, end);
						start = {
							lat: routes.routeA.startlat,
							lng: routes.routeA.startlng
						};
						end = {
							lat: routes.routeB.endlat,
							lng: routes.routeB.endlng
						};
						var dis3 = haversine(start, end);
						start = {
							lat: routes.routeB.startlat,
							lng: routes.routeB.startlng
						};
						end = {
							lat: routes.routeA.endlat,
							lng: routes.routeA.endlng
						};
						var dis4 = haversine(start, end);

						var matchValue = dis1 + dis2 + dis3 + dis4;
						if(matchValue < (routes.distanceA * 3)) {
							var deferred = context.q.defer();
							deferred.resolve(results[i]);
							var deferred1 = context.q.defer();
							context.rest.get('http://maps.googleapis.com/maps/api/geocode/json?latlng=' + route.startlat + ',' + route.startlng + '&sensor=false', function(data, response) {
								if(data.results.length == 0) {
									deferred1.reject();
								}
								else if(!data.results[0].formatted_address) {
									deferred1.reject();
								}
								else {
									deferred1.resolve(data.results[0].formatted_address);
								}
							});
							var deferred2 = context.q.defer();
							context.rest.get('http://maps.googleapis.com/maps/api/geocode/json?latlng=' + route.endlat + ',' + route.endlng + '&sensor=false', function(data, response) {
								if(data.results.length == 0) {
									deferred2.reject();
								}
								else if(!data.results[0].formatted_address) {
									deferred2.reject();
								}
								else {
									deferred2.resolve(data.results[0].formatted_address);
								}
							});
							var deferred3 = context.q.defer();
							context.rest.get('http://maps.googleapis.com/maps/api/geocode/json?latlng=' + results[i].startlat + ',' + results[i].startlng + '&sensor=false', function(data, response) {
								if(data.results.length == 0) {
									deferred3.reject();
								}
								else if(!data.results[0].formatted_address) {
									deferred3.reject();
								}
								else {
									deferred3.resolve(data.results[0].formatted_address);
								}
							});
							var deferred4 = context.q.defer();
							context.rest.get('http://maps.googleapis.com/maps/api/geocode/json?latlng=' + results[i].endlat + ',' + results[i].endlng + '&sensor=false', function(data, response) {
								if(data.results.length == 0) {
									deferred4.reject();
								}
								else if(!data.results[0].formatted_address) {
									deferred4.reject();
								}
								else {
									deferred4.resolve(data.results[0].formatted_address);
								}
							});
							promises.push(context.q.all([deferred.promise, deferred1.promise, deferred2.promise, deferred3.promise, deferred4.promise]));
							/*context.q.all([deferred1.promise, deferred2.promise, deferred3.promise, deferred4.promise]).then(function(data) {
								var routeStart1 = data[0];
								var routeEnd1 = data[1];
								var routeStart2 = data[2];
								var routeEnd2 = data[3];
								var values = insertId + ',' + results[i].routeId + ',' + routeStart1 + ',' + routeEnd1 + ',' + routeStart2 + ',' + routeEnd2 + ',' + context.connection.escape(route.time) + ',' + context.connection.escape(results[i].time) + ',' + context.connection.escape(route.weekday) + ',' + context.connection.escape(results[i].weekday) + ',' + userId + ',' + results[i].userId + ',' + matchValue;
								context.connection.query('Insert into tbl_matchings (routeId1, routeId2, routeStart1, routeEnd1, routeStart2, routeEnd2, time1, time2, weekday1, weekday2, userId1, userId2, value) VALUES(' + values + ')', function(err, results) {
									if(err) {
										console.error("Error on adding new match");
										console.error(err);
									}
									else {
										console.log("Added new match");
									}
								});
							}, function() {
								console.error("error");
							});*/
						}
					}
				}
				context.q.all(promises).then(function(data) {
					for(var i = 0; i < data.length; ++i) {
						var routeStart1 = data[i][1];
						var routeEnd1 = data[i][2];
						var routeStart2 = data[i][3];
						var routeEnd2 = data[i][4];
						var values = insertId + ',' + data[i][0].routeId + ',' + context.connection.escape(routeStart1) + ',' + context.connection.escape(routeEnd1) + ',' + context.connection.escape(routeStart2) + ',' + context.connection.escape(routeEnd2) + ',' + context.connection.escape(route.time) + ',' + context.connection.escape(data[i][0].time) + ',' + context.connection.escape(route.weekday) + ',' + context.connection.escape(data[i][0].weekday) + ',' + userId + ',' + data[i][0].userId + ',' + matchValue;
						context.connection.query('Insert into tbl_matchings (routeId1, routeId2, routeStart1, routeEnd1, routeStart2, routeEnd2, time1, time2, weekday1, weekday2, userId1, userId2, value) VALUES(' + values + ')', function(err, results) {
							if(err) {
								console.error("Error on adding new match");
								console.error(err);
							}
							else {
								console.log("Added new match");
							}
						});
					}
				});
			}
		});
	}

	var compareRoute = function(routeA, routeB) {
		var start = {
			lat: routeA.startlat,
			lng: routeA.startlng
		};
		var end = {
			lat: routeA.endlat,
			lng: routeA.endlng
		};
		var distanceA = haversine(start, end);
		start = {
			lat: routeB.startlat,
			lng: routeB.startlng
		};
		end = {
			lat: routeB.endlat,
			lng: routeB.endlng
		};
		var distanceB = haversine(start, end);
		var a, b;
		if(distanceA > distanceB) {
			a = routeA;
			b = routeB;
		}
		else {
			a = routeB;
			b = routeA;
		}
		return {routeA : a, routeB : b, distanceA : distanceA, distanceB : distanceB};
	}

	var haversine = function(start, end) {
		Number.prototype.toRad = function() {
   			return this * Math.PI / 180;
		};
		var R = 6371000;
		var phi1 = start.lat.toRad();
		var phi2 = parseFloat(end.lat).toRad();
		var deltaPhi = parseFloat(end.lat - start.lat).toRad();
		var deltaGamma = parseFloat(end.lng - start.lng).toRad();

		var a = Math.sin(deltaPhi / 2) * Math.sin(deltaPhi / 2) + Math.cos(phi1) * Math.cos(phi2) * Math.sin(deltaGamma / 2) * Math.sin(deltaGamma / 2);
		var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		var d = R * c;
		return d;
	}
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

				var values = startLat + ',' + startLng + ',' + endLat + ',' + endLng + ',' + context.connection.escape(time) + ',' + context.connection.escape(day) + ',' + req.userId;
				context.connection.query('Insert into tbl_routes (startlat, startlng, endlat, endlng, time, weekday, userId) VALUES (' + values + ')', function(err, results) {
					if(err) {
						console.error(err);
						return res.status(500).send({err: err});
					}
					else if(results.length == 0) {
						return res.status(500).send({msg: "Error on inserting route"});
					}
					else {
						var route = {
							startlat : startLat,
							startlng : startLng,
							endlat : endLat,
							endlng : endLng,
							time : time,
							weekday : day
						};
						matchRoutes(route, results.insertId, req.userId);
						return res.status(200).send();
					}
				});

			}, function() {
				return res.status(500).send({msg: "Error on geocoding the address"});
			});
		},

		getMatching : function(req, res) {
			context.connection.query('Select * from tbl_matchings where userId1 = ' + req.userId + ' or userId2 = ' + req.userId, function(err, results) {
				if(err) {
					console.error(err);
					return res.status(500).send({err: err});
				}
				else if(results.length == 0) {
					return res.status(200).send({numberMatchings: 0, matchings: null});
				}
				else {
					var matchings = new Array();
					for(var i = 0; i < results.length; ++i) {
						var match = {};
						if(results[i].userId1 == req.userId) {
							match = {
									startUser : results[i].routeStart1,
									destinationUser : results[i].routeEnd1,
									timeUser : results[i].time1,
									dayUser : results[i].weekday1,
									startPartner : results[i].routeStart2,
									destinationPartner : results[i].routeEnd2,
									timePartner : results[i].time2,
									dayPartner : results[i].weekday2,
									matchingValue : results[i].value
								};
						}
						else {
							match = {
									startUser : results[i].routeStart2,
									destinationUser : results[i].routeEnd2,
									timeUser : results[i].time2,
									dayUser : results[i].weekday2,
									startPartner : results[i].routeStart1,
									destinationPartner : results[i].routeEnd1,
									timePartner : results[i].time1,
									dayPartner : results[i].weekday1,
									matchingValue : results[i].value
								};
						}
						matchings.push(match);
					}
					return res.status(200).send({numberMatchings: matchings.length, matchings: matchings});
				}
			});
		}
	};
};