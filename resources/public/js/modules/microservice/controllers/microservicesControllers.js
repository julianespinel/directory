var microservicesControllers = angular.module('microservicesControllers', []);

microservicesControllers.controller('microserviceController', ['$scope', '$routeParams', '$location', 'Microservice', 
                                    'MicroservicesList',
    function($scope, $routeParams, $location, Microservice, MicroservicesList) {

        function cleanService() {
            $scope.service = { serviceName: '', host: '', port: '', protocol: '', prefix: '' };
        };

        function goToDirectories() {
            $location.path('/directory/microservices');
        };

        $scope.validator = { 
            errorMessage: '',
            isNotAValidService: true
        };

        function validateService(microservice, success) {

            var fieldsSuccess = function(errorMessage) {

                // If all fields are correct.
                if (!errorMessage) {

                    // Check if the service name is already registered in the back end.
                    var checkIfServiceExists = function(result) {

                        // If the name is registered into the db.
                        if (result.serviceName) {
                            errorMessage = 'The service with name ' + serviceName + ' already exists.';
                        }

                        success(errorMessage);
                    };

                    var serviceName = microservice.serviceName;
                    var serviceExists = Microservice.getSpecificMicroservice({ serviceName: serviceName });
                    serviceExists.$promise.then(checkIfServiceExists);
                }

                success(errorMessage);
            };

            verifyServiceFields(microservice, fieldsSuccess);
        };

        function verifyServiceFields(microservice, success) {

            var errorMessage = '';

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
            }

            success(errorMessage);
        };

        $scope.verifyServiceFields2 = function() {

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

            console.debug($scope.validator.isNotAValidService);

            if (!errorMessage) {
                console.debug('in');
                $scope.validator.isNotAValidService = false;
                console.debug($scope.validator.isNotAValidService);
            }

            console.debug('verifyServiceFields2');
            console.debug(errorMessage);
        };

        $scope.service = {};
        $scope.servicesList = MicroservicesList.getAllMicroservices();

        if ($routeParams.serviceName) {
            $scope.service = Microservice.getSpecificMicroservice({ serviceName: $routeParams.serviceName });
        }

        $scope.createMicroservice = function() {
            
            var serviceWithData = $scope.service;

            if (serviceWithData) {

                var success = function(errorMessage) {

                    console.debug('success');
                    console.debug(errorMessage);

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

                // validateService(serviceWithData);
                validateService(serviceWithData, success);

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
