var microservicesControllers = angular.module('microservicesControllers', []);

microservicesControllers.controller('microserviceController', ['$scope', '$routeParams', 'Microservice', 'MicroservicesList',
    function($scope, $routeParams, Microservice, MicroservicesList) {

    console.warn($routeParams.serviceName);

    $scope.service = {};
    $scope.servicesList = MicroservicesList.getAllMicroservices();

    var getServiceByNameFromList = function(serviceName, servicesList) {

        console.debug('************* 1');
        console.debug(serviceName);
        console.debug(servicesList);

        var answer = {};
        var found = false;

        console.debug('********** 2');
        console.debug(servicesList.length);

        for (var i = 0; i < servicesList.length && !found; i++) {

            var item = servicesList[i];

            console.debug('in');
            console.debug(item);

            if (serviceName == item.serviceName) {
                found = true;
                answer = item;
            }
        }

        return item;
    };

    if ($routeParams.serviceName) {

        // $scope.service = getServiceByNameFromList($routeParams.serviceName, $scope.servicesList);
        var microseriveSample = new Microservice();
        $scope.service = microseriveSample.$getSpecificMicroservice({ serviceName: $routeParams.serviceName });
        console.debug($scope.service);
    }

    $scope.createMicroservice = function() {
        
        var serviceWithData = $scope.service;

        if (serviceWithData) {

            var microservice = new Microservice(serviceWithData);
            
            console.debug(microservice);

            microservice.$createMicroservice();
            $scope.servicesList.push(microservice);
            $scope.service = {};

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
