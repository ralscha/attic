Ext.define('Packt.store.staticData.Cities', {
    extend: 'Packt.store.staticData.Abstract',

    requires: [
        'Packt.model.staticData.City'
    ],

    model: 'Packt.model.staticData.City',

    groupField: 'countryId'
});