// Front end js
app.controller('HomeController',function ($rootScope, $scope, $http, $window)
{
	$scope.user = {};
	$scope.user = $rootScope.user;
	if (typeof $scope.user == 'undefined')
	{
		$window.location = './';
	}
});