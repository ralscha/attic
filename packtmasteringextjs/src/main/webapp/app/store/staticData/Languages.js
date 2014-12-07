Ext.define('Packt.store.staticData.Languages', {
    extend: 'Packt.store.staticData.Abstract',

    requires: [
        'Packt.model.staticData.Language'
    ],

    model: 'Packt.model.staticData.Language',

    storeId: 'languages',

    autoLoad: false
});