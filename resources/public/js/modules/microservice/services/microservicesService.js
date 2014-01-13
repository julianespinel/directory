var microservicesServices = angular.module('microservicesServices', ['ngResource']);

microservicesServices.factory('Microservice', ['$resource', function($resource) {

    var url = 'http://localhost:3000/services/:serviceName';
    var paramDefaults = '';
    var actions = '';
    return $resource(url, paramDefaults, actions);
}]);

microservicesServices.factory('MicroservicesList', ['$resource', function($resource) {

    var url = 'http://localhost:3000/services';
    var paramDefaults = '';
    
    var actions = {

        getAllMicroservices: { method: 'GET' },
        isArray: true
    };

    return $resource(url, paramDefaults, actions);
}]);
