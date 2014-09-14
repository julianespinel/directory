define(['angular'], function(angular) {

    'use strict';

    var microservicesServices = angular.module('microservicesServices', ['ngResource']);

    microservicesServices.factory('Microservice', ['$resource', function($resource) {

        var specificMicroserviceUrl = 'http://localhost:3000/services/:serviceName';
        var microservicesUrl = 'http://localhost:4001/services';

        var url = '';
        var paramDefaults = '';

        var actions = {

            getSpecificMicroservice: { method: 'GET', url: specificMicroserviceUrl },
            createMicroservice: { method: 'POST', url: microservicesUrl },
            editMicroservice: { method: 'PUT', url: specificMicroserviceUrl },
            deleteMicroservice: { method: 'DELETE', url: specificMicroserviceUrl }
        };

        return $resource(url, paramDefaults, actions);
    }]);

    microservicesServices.factory('MicroservicesList', ['$resource', function($resource) {

        var url = 'http://localhost:3000/services';
        var paramDefaults = '';

        var actions = {

            getAllMicroservices: { method: 'GET', isArray: true }
        };

        return $resource(url, paramDefaults, actions);
    }]);

    return microservicesServices;
});
