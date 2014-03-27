define(['angular', 'angularRoute', 'angularResource', 'modules/microservice/services/microservicesServices',
       'modules/microservice/controllers/microservicesControllers'], 

       function(angular, microservicesServices, microservicesControllers) {

           'use strict';

           return angular.module('directoryApp', [

               'ngRoute',
               'ngResource',

               'microservicesServices',
               'microservicesControllers'
           ]);

       });
