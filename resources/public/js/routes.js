define(['angular', 'app'], function(angular, app) {

    'use strict';

    var directoryApp = angular.module('directoryApp', ['ngRoute', 'microservicesServices', 'microservicesControllers']);

    return directoryApp.config(['$routeProvider', function($routeProvider) {

        $routeProvider.when('/', {

            redirectTo: '/directory'
        })

        .when('/directory', {

            templateUrl: 'js/modules/infrastructure/partials/index.html'
        }).

            when('/directory/microservices', {

            controller: 'microserviceController',
            templateUrl: 'js/modules/microservice/partials/list-microservices.html'
        }).

            when('/directory/microservices/:serviceName', {

            controller: 'microserviceController',
            templateUrl: 'js/modules/microservice/partials/detail-microservice.html'
        }).

            when('/404', {

            templateUrl: 'js/modules/infrastructure/partials/404-error.html'
        }).

            otherwise({

            redirectTo: '/404'
        });
    }]);
});
