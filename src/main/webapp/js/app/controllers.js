'use strict';

zenContactApp.controller('ContactListController', ['$scope', 'contactService', function ($scope, contactService) {
    contactService.getAllContacts(function (contacts) {
        $scope.contacts = contacts;
    });
}]);

zenContactApp.controller('ContactEditController', ['$scope', 'contactService', '$routeParams', '$location', function ($scope, contactService, $routeParams, $location) {
    if ($routeParams.id) {
    contactService.getContactById($routeParams.id, function (contact) {
        $scope.contact = contact;
    });

    } else {
        $scope.contact = {};
    }

    $scope.saveContact = function (contact) {
        contactService.saveContact(contact, function (err) {
            if (!err) {
                $location.path("/list");
            } else {
                console.log(err);
            }
        });
    }
    $scope.deleteContact = function (contact) {
        contactService.deleteContact(contact, function (err) {
            if (!err) {
                $location.path("/list");
            } else {
                console.log(err);
            }
        });
    }
}]);

zenContactApp.directive('myHeroUnit', function() {
    return {
        restrict: 'EA',
        transclude: true,
        template: '<div class="hero-unit">'+
            '<h1>ZenContacts</h1>'+
            '<h2 ng-transclude />'+
            '</div>'
      };
});