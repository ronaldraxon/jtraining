'use strict';

describe('Controller Tests', function() {

    describe('Tipo_vehiculo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTipo_vehiculo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTipo_vehiculo = jasmine.createSpy('MockTipo_vehiculo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Tipo_vehiculo': MockTipo_vehiculo
            };
            createController = function() {
                $injector.get('$controller')("Tipo_vehiculoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'siccApp:tipo_vehiculoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
