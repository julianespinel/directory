'use strict';

/* App Module */

var directoryApp = angular.module('directoryApp', ['ngRoute', 'microservicesServices', 'microservicesControllers']);

directoryApp.config(['$routeProvider', function($routeProvider) {

    $routeProvider.when('/', {

        redirectTo: '/directory'
    })

    .when('/directory', {

        templateUrl: 'js/modules/infrastructure/partials/index.html'
    }).
    
    when('/directory/microservices', {

        controller: 'microservicesListController',
        templateUrl: 'js/modules/microservice/partials/microservices.html'
    }).

    when('/directory/microservices/:serviceName', {

        templateUrl: 'js/modules/microservice/partials/microservice-detail.html'
    }).

    when('/404', {

        templateUrl: 'js/modules/infrastructure/partials/404-error.html'
    }).

    otherwise({

        redirectTo: '/404'
    });
}]);
