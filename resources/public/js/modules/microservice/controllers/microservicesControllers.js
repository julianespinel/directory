var microservicesControllers = angular.module('microservicesControllers', []);

microservicesControllers.controller('microserviceController', ['$scope', 'Microservice', 
    function($scope, Microservice) {

    $scope.service = '';

    $scope.createMicroservice = function() {
        
        var serviceWithData = $scope.service;

        if (serviceWithData) {

            var microservice = new Microservice(serviceWithData);
            
            console.debug(microservice);

            microservice.$createMicroservice();

        } else {

            var errorMessage = 'El servicio no está definido';
            console.error(errorMessage);
        }
    };

    $scope.editMicroservice = function() {
        
        var serviceWithData = $scope.service;

        if (serviceWithData) {

            var microservice = new Microservice(serviceWithData);
            
            console.debug(microservice);

            microservice.$editMicroservice();

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
