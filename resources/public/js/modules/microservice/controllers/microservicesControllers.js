var microservicesControllers = angular.module('microservicesControllers', []);

microservicesControllers.controller('microserviceController', ['$scope', '$routeParams', '$location', 'Microservice', 
                                    'MicroservicesList',
    function($scope, $routeParams, $location, Microservice, MicroservicesList) {

        function cleanService() {
            $scope.service = { serviceName: ' ', host: ' ', port: ' ', protocol: ' ', prefix: ' ' };
        };

        function goToDirectories() {
            $location.path('/directory/microservices');
        };

        $scope.service = {};
        $scope.servicesList = MicroservicesList.getAllMicroservices();

        if ($routeParams.serviceName) {
            $scope.service = Microservice.getSpecificMicroservice({ serviceName: $routeParams.serviceName });
        }

        $scope.createMicroservice = function() {
            
            var serviceWithData = $scope.service;

            if (serviceWithData) {

                var microservice = new Microservice(serviceWithData);

                microservice.$createMicroservice();
                $scope.servicesList.push(microservice);
                cleanService();   

            } else {

                var errorMessage = 'El servicio no está definido';
                console.error(errorMessage);
            }
        };

        $scope.editMicroservice = function() {
            
            var serviceWithData = $scope.service;

            if (serviceWithData) {

                var microservice = new Microservice(serviceWithData);
                microservice.$editMicroservice({ serviceName: $routeParams.serviceName });
                goToDirectories();

            } else {

                var errorMessage = 'El servicio no está definido';
                console.error(errorMessage);
            }
        };

        $scope.deleteMicroservice = function() {

            var microservice = new Microservice();
            microservice.$deleteMicroservice({ serviceName: $routeParams.serviceName });
            goToDirectories();
        };
}]);
