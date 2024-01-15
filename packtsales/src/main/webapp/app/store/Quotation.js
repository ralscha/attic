Ext.define('Sales.store.Quotation', {
    extend: 'Ext.data.Store',
    storeId: 'QuotationList',
    model: 'Sales.model.Quotation',
    remoteSort: true,
    pageSize: 100,
    proxy: {
        type: 'direct',
        directFn: 'MyAppQuotation.getGrid',
        reader: {
            type: 'json',
            root: 'items',
            totalProperty: 'total'
        }
    }
});
