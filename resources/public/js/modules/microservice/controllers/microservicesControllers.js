var microservicesControllers = angular.module('microservicesControllers', []);

microservicesControllers.controller('microserviceController', ['$scope', '$routeParams', 'Microservice', 
    function($scope, $routeParams, Microservice) {

    $scope.service = {};
    $scope.servicesList = [];

    $scope.createMicroservice = function() {
        
        var serviceWithData = $scope.service;

        if (serviceWithData) {

            var microservice = new Microservice(serviceWithData);
            
            console.debug(microservice);

            microservice.$createMicroservice();
            $scope.servicesList.push(microservice);

            console.debug($scope.servicesList);

        } else {

            var errorMessage = 'El servicio no está definido';
            console.error(errorMessage);
        }
    };

    $scope.editMicroservice = function() {
        
        var serviceWithData = $scope.service;

        if (serviceWithData) {

            var microservice = new Microservice(serviceWithData);
            
            console.debug($routeParams.serviceName);
            console.debug(microservice);

            microservice.$editMicroservice({ serviceName: $routeParams.serviceName });

        } else {

            var errorMessage = 'El servicio no está definido';
            console.error(errorMessage);
        }
    };

}]);

microservicesControllers.controller('microservicesListController', ['$scope', 'MicroservicesList', 
    function($scope, MicroservicesList) {

    $scope.servicesList = MicroservicesList.getAllMicroservices();
}]);
