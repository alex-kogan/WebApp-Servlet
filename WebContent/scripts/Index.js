// Front end js
app.controller('IndexController',function ($rootScope, $scope, $http, $window)
{
	$scope.user = {ID:1,Name:'',Password:'',NickName:'',Description:'',Pic:''};
	$scope.login = function ()
	{
		$http(
		{
			method: 'POST',
			url: 'userservlet',
			headers: {'Content-Type': 'application/json'},
			data:  JSON.stringify($scope.user)
		}).success( function (response)
		{
			if (!('msg' in response))
			{
				$scope.user = response;
				$window.location = './#/home';
				$rootScope.user = $scope.user;
			}
			else
			{
				var msg = response.msg;
				if ($('ul.error-list').children().length)
				{
					$('ul.error-list').children().remove();
				}
				for (var i=0;i<msg.length; i++)
				{					
					$('ul.error-list').append('<li>' + msg + '</li>');
				}
			}
		});
	};
});