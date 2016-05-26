'use strict';

/**
 * @ngdoc function
 * @name webappApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the webappApp
 */
angular.module('webappApp')
  .controller('MainCtrl', function () {
    this.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });

$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/user/kikikoko"
    }).then(function(data, status, jqxhr) {
        $('.greeting-id').append(data.userId);
        $('.greeting-content').append(data.email);
        console.log(jqxhr);
    });
});
