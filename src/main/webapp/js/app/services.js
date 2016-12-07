'use strict';

var zenContactServices = angular.module('zenContact.services', []);

zenContactServices.constant("zenVersion", "v2");
zenContactServices.service('contactService', function ($http, zenVersion) {
    this.getAllContacts = function (callback) {
        $http.get('/api/' + zenVersion + '/users').success(function (contacts) {
            callback(contacts);
        });
    };

    this.getContactById = function (id, callback) {
        $http.get('/api/' + zenVersion + '/users/' + id)
            .success(function (contact) {
                callback(contact);
            }).error(function () {
                callback({});
            });
    };

    this.saveContact = function (contact, callback) {
        if (contact.id) {
            $http.put('/api/' + zenVersion + '/users/' + contact.id, contact)
                .success(function () {
                    callback(null);
                }).error(function () {
                    callback("Error");
                });
        } else {
            $http.post('/api/' + zenVersion + '/users', contact)
                .success(function () {
                    callback(null);
                }).error(function () {
                    callback("Error");
                });
        }
    };

    this.deleteContact = function (contact, callback) {
        $http.delete('/api/' + zenVersion + '/users/' + contact.id)
            .success(function () {
                callback(null);
            }).error(function () {
                callback("Error");
            });
    };

});