Ext.define('Sales.store.BillItem',{
    extend: 'Ext.data.Store',
    storeId:'BillItem',
    fields:['desc', 'qty', 'price', 'sum'],
    data:{'items':[
    ]},
    proxy: {
        type: 'memory',
        reader: {
            type: 'json',
            root: 'items'
        }
    }
});

