var microservicesControllers = angular.module('microservicesControllers', []);

microservicesControllers.controller('microservicesListController', ['$scope', 'MicroservicesList', 
    function($scope, MicroservicesList) {

    $scope.servicesList = MicroservicesList.getAllMicroservices();
}]);
