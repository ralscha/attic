Ext.define('Sales.store.Customer',{
    extend: 'Ext.data.Store',
    storeId:'Customer',
    fields: ['id', 'name'],
    data:{'items':[
        {"id": "0", "name": "Sencha"},
        {"id": "1", "name": "Xenophy"}
    ]},
    proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: 'items'
        }
    }
});

