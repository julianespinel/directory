var microservicesControllers = angular.module('microservicesControllers', []);

microservicesControllers.controller('microserviceController', ['$scope', '$routeParams', '$location', 'Microservice', 
                                    'MicroservicesList',
    function($scope, $routeParams, $location, Microservice, MicroservicesList) {

        function cleanService() {
            $scope.service = {};
        };

        function goToDirectories() {
            $location.path('/directory/microservices');
        };

        $scope.validator = { 
            errorMessage: '',
            isNotAValidService: false
        };

        function validateServiceDoesNotExistAtTheBackEnd(microservice, success) {

            // Check if the service name is already registered in the back end.
            var checkIfServiceExists = function(result) {

                // If the name is registered into the db.
                if (result.serviceName) {
                    errorMessage = 'The service with name ' + serviceName + ' already exists.';
                }

                success(errorMessage);
            };

            var errorMessage = '';
            var serviceName = microservice.serviceName;
            var serviceExists = Microservice.getSpecificMicroservice({ serviceName: serviceName });
            serviceExists.$promise.then(checkIfServiceExists);
        };

        $scope.verifyServiceFields = function() {

            var errorMessage = '';
            var microservice  = $scope.service;

            // If there is no error with the serviceName, then check the other fields.
            if (!microservice.serviceName) {

                errorMessage = 'The service name is required.';

            } else if (!microservice.host) {

                errorMessage = 'The service host is required.';

            } else if (!microservice.port) {

                errorMessage = 'The service port is required.';

            } else if (!microservice.protocol) {

                errorMessage = 'The service protocol is required.';

            } else if (!microservice.prefix) {

                errorMessage = 'The service prefix is required.';
            }

            if (!errorMessage) {

                $scope.validator.isNotAValidService = false;

            } else {

                $scope.validator.isNotAValidService = true;
                $scope.validator.errorMessage = errorMessage;
            }
        };

        $scope.service = {};
        $scope.servicesList = MicroservicesList.getAllMicroservices();

        if ($routeParams.serviceName) {
            $scope.service = Microservice.getSpecificMicroservice({ serviceName: $routeParams.serviceName });
        }

        $scope.createMicroservice = function() {
            
            var serviceWithData = $scope.service;

            if (serviceWithData) {

                // Check the service fields are valid.
                $scope.verifyServiceFields();

                var success = function(errorMessage) {

                    if (!errorMessage) {

                        var microservice = new Microservice(serviceWithData);
                        microservice.$createMicroservice();
                        $scope.servicesList.push(microservice);
                        cleanService();

                    } else {

                        $scope.validator.errorMessage = errorMessage;
                        $scope.validator.isNotAValidService = true;
                    }
                };

                validateServiceDoesNotExistAtTheBackEnd(serviceWithData, success);

            } else {

                var errorMessage = 'El servicio no está definido';
                console.error(errorMessage);
            }
        };

        $scope.editMicroservice = function() {
            
            var serviceWithData = $scope.service;

            if (serviceWithData) {

                // Check the service fields are valid.
                $scope.verifyServiceFields();

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
