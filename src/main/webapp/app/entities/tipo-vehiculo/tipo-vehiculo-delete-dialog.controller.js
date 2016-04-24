(function() {
    'use strict';

    angular
        .module('siccApp')
        .controller('Tipo_vehiculoDeleteController',Tipo_vehiculoDeleteController);

    Tipo_vehiculoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tipo_vehiculo'];

    function Tipo_vehiculoDeleteController($uibModalInstance, entity, Tipo_vehiculo) {
        var vm = this;
        vm.tipo_vehiculo = entity;
        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        vm.confirmDelete = function (id) {
            Tipo_vehiculo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };
    }
})();
