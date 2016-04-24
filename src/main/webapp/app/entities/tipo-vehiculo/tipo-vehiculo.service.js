(function() {
    'use strict';
    angular
        .module('siccApp')
        .factory('Tipo_vehiculo', Tipo_vehiculo);

    Tipo_vehiculo.$inject = ['$resource'];

    function Tipo_vehiculo ($resource) {
        var resourceUrl =  'api/tipo-vehiculos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
