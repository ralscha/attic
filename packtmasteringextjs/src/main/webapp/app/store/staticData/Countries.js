Ext.define('Packt.store.staticData.Countries', {
    extend: 'Packt.store.staticData.Abstract',

    requires: [
        'Packt.model.staticData.Country'
    ],

    model: 'Packt.model.staticData.Country',

    autoLoad: false,

    storeId: 'countries'
});