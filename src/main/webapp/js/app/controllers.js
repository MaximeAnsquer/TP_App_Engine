'use strict';

zenContactApp.controller('ContactListController', ['$scope', 'contactService', function ($scope, contactService) {
    contactService.getAllContacts(function (contacts) {
        $scope.contacts = contacts;
    });
}]);

zenContactApp.controller('ContactEditController', ['$scope', 'contactService', '$routeParams', '$location', 'Upload', function ($scope, contactService, $routeParams, $location, Upload) {
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
    
    $scope.uploadContactFile = function (files) {
        if (files && files.length) {
            for (var i = 0; i < files.length; i++) {
                var file = files[i];
                Upload.upload({
                    url: $scope.contact.uploadURL,
                    file: file
                }).progress(function (evt) {
                    var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
                }).success(function (data, status, headers, config) {
                    console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
                    //reload the contact
                    contactService.getContactById($routeParams.id, function (contact) {
                        $scope.contact = contact;
                    });
                });
            }
        }
    };
    
    $scope.$watch('files', function () {
        $scope.uploadContactFile($scope.files);
    });

    
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