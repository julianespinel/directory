var microservicesControllers = angular.module('microservicesControllers', []);

microservicesControllers.controller('microserviceController', ['$scope', 'Microservice', 
    function($scope, MicroservicesList) {

    $scope.createMicroservice = function() {
        
        $scope.service = '';
        var serviceWithData = $scope.service;

        if (serviceWithData) {

            var microservice = new Microservice(serviceWithData);
            microservice.createMicroservice();

        } else {

            var errorMessage = 'El servicio no está definido';
            console.error(errorMessage);
        }
    }
}]);

microservicesControllers.controller('microservicesListController', ['$scope', 'MicroservicesList', 
    function($scope, MicroservicesList) {

    $scope.servicesList = MicroservicesList.getAllMicroservices();
}]);
