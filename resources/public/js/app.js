'use strict';

/* App Module */

var directoryApp = angular.module('directoryApp', ['ngRoute']);

directoryApp.config(['$routeProvider', function($routeProvider) {

    $routeProvider.when('/directory', {

        templateUrl: 'js/modules/microservice/partials/show-microservices.html'
      }).

      when('/404', {

        templateUrl: 'js/modules/infrastructure/partials/404-template.html'
      }).

      otherwise({

        redirectTo: '/404'
      });
  }]);
