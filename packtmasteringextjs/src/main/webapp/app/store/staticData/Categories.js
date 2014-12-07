Ext.define('Packt.store.staticData.Categories', {
    extend: 'Packt.store.staticData.Abstract',
    alias: 'store.categories',
    
    requires: [
        'Packt.model.staticData.Category'
    ],

    model: 'Packt.model.staticData.Category',

    autoLoad: false,

    storeId: 'categories'
});