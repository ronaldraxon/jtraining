(function() {
    'use strict';

    angular
        .module('siccApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tipo-vehiculo', {
            parent: 'entity',
            url: '/tipo-vehiculo?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Tipo_vehiculos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-vehiculo/tipo-vehiculos.html',
                    controller: 'Tipo_vehiculoController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }]
            }
        })
        .state('tipo-vehiculo-detail', {
            parent: 'entity',
            url: '/tipo-vehiculo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Tipo_vehiculo'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-vehiculo/tipo-vehiculo-detail.html',
                    controller: 'Tipo_vehiculoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Tipo_vehiculo', function($stateParams, Tipo_vehiculo) {
                    return Tipo_vehiculo.get({id : $stateParams.id});
                }]
            }
        })
        .state('tipo-vehiculo.new', {
            parent: 'tipo-vehiculo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-vehiculo/tipo-vehiculo-dialog.html',
                    controller: 'Tipo_vehiculoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                tipo: null,
                                descripcion_uso: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tipo-vehiculo', null, { reload: true });
                }, function() {
                    $state.go('tipo-vehiculo');
                });
            }]
        })
        .state('tipo-vehiculo.edit', {
            parent: 'tipo-vehiculo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-vehiculo/tipo-vehiculo-dialog.html',
                    controller: 'Tipo_vehiculoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tipo_vehiculo', function(Tipo_vehiculo) {
                            return Tipo_vehiculo.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-vehiculo', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-vehiculo.delete', {
            parent: 'tipo-vehiculo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-vehiculo/tipo-vehiculo-delete-dialog.html',
                    controller: 'Tipo_vehiculoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tipo_vehiculo', function(Tipo_vehiculo) {
                            return Tipo_vehiculo.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-vehiculo', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
