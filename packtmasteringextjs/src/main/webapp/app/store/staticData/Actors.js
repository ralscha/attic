Ext.define('Packt.store.staticData.Actors', {
    extend: 'Packt.store.staticData.Abstract',

    requires: [
        'Packt.model.staticData.Actor'
    ],

    model: 'Packt.model.staticData.Actor',

    storeId: 'actors',
    pageSize: -1
    
});