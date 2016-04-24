'use strict';

describe('Tipo_vehiculo e2e test', function () {

    var username = element(by.id('username'));
    var password = element(by.id('password'));
    var entityMenu = element(by.id('entity-menu'));
    var accountMenu = element(by.id('account-menu'));
    var login = element(by.id('login'));
    var logout = element(by.id('logout'));

    beforeAll(function () {
        browser.get('/');
        browser.driver.wait(protractor.until.elementIsVisible(element(by.css('h1'))));

        accountMenu.click();
        login.click();

        username.sendKeys('admin');
        password.sendKeys('admin');
        element(by.css('button[type=submit]')).click();
    });

    it('should load Tipo_vehiculos', function () {
        entityMenu.click();
        element(by.css('[ui-sref="tipo-vehiculo"]')).click().then(function() {
            expect(element.all(by.css('h2')).first().getText()).toMatch(/Tipo_vehiculos/);
        });
    });

    it('should load create Tipo_vehiculo dialog', function () {
        element(by.css('[ui-sref="tipo-vehiculo.new"]')).click().then(function() {
            expect(element(by.css('h4.modal-title')).getText()).toMatch(/Create or edit a Tipo_vehiculo/);
            element(by.css('button.close')).click();
        });
    });

    afterAll(function () {
        accountMenu.click();
        logout.click();
    });
});
