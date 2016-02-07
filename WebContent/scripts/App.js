var app = angular.module('my-app',[]);

app.controller('RouteConroller',function ($rootScope, $scope, $http, $window)
{
	$scope.view = 'index';
	
	$scope.$on('$locationChangeStart', function(event,next)
	{
		var siteView = next.split('#/');
		siteView = siteView[1];
		if (typeof siteView == 'undefined')
		{
			siteView = 'index';
		}
		$scope.view = siteView;
	});
});

app.factory('dataService', function()
{
	var savedData = {};
	function set(data)
    {
    	this.savedData = data;
    }
	function get()
	{
		return this.savedData;
	}
	return {
		set: set,
		get: get
	}
});
