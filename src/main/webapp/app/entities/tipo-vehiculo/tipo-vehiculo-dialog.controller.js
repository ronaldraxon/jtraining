(function() {
    'use strict';

    angular
        .module('siccApp')
        .controller('Tipo_vehiculoDialogController', Tipo_vehiculoDialogController);

    Tipo_vehiculoDialogController.$inject = ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tipo_vehiculo'];

    function Tipo_vehiculoDialogController ($scope, $stateParams, $uibModalInstance, entity, Tipo_vehiculo) {
        var vm = this;
        vm.tipo_vehiculo = entity;
        vm.load = function(id) {
            Tipo_vehiculo.get({id : id}, function(result) {
                vm.tipo_vehiculo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('siccApp:tipo_vehiculoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        };

        var onSaveError = function () {
            vm.isSaving = false;
        };

        vm.save = function () {
            vm.isSaving = true;
            if (vm.tipo_vehiculo.id !== null) {
                Tipo_vehiculo.update(vm.tipo_vehiculo, onSaveSuccess, onSaveError);
            } else {
                Tipo_vehiculo.save(vm.tipo_vehiculo, onSaveSuccess, onSaveError);
            }
        };

        vm.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
    }
})();
