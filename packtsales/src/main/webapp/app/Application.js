Ext.define('Sales.Application', {
    name: 'MyApp',

    extend: 'Ext.app.Application',

    views: [
        'Main'
    ],

    controllers: [
        'Main',
        'Header',
        'Navigation',
        'dashboard.DashBoard',
        'myaccount.MyAccount',
        'myaccount.Edit',
        'quotation.Quotation',
        'quotation.List',
        'quotation.Edit',
        'quotation.Import',
        'bill.Bill',
        'bill.List',
        'bill.Edit'
    ],

    stores: [
        'Navigation'
    ],

    screens: [
        'dashboard',
        'myaccount',
        'quotation',
        'bill'
    ],
    launch: function() {
        MyApp.util.History.init({
            screens: this.screens
        });
    }
});
