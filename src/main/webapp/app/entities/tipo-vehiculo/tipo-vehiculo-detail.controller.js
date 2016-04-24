(function() {
    'use strict';

    angular
        .module('siccApp')
        .controller('Tipo_vehiculoDetailController', Tipo_vehiculoDetailController);

    Tipo_vehiculoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Tipo_vehiculo'];

    function Tipo_vehiculoDetailController($scope, $rootScope, $stateParams, entity, Tipo_vehiculo) {
        var vm = this;
        vm.tipo_vehiculo = entity;
        vm.load = function (id) {
            Tipo_vehiculo.get({id: id}, function(result) {
                vm.tipo_vehiculo = result;
            });
        };
        var unsubscribe = $rootScope.$on('siccApp:tipo_vehiculoUpdate', function(event, result) {
            vm.tipo_vehiculo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    }
})();
