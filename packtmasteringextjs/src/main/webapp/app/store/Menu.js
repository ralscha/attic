Ext.define('Packt.store.Menu', {
    extend: 'Ext.data.Store',

    requires: [
        'Packt.model.menu.Root'
    ],

    model: 'Packt.model.menu.Root'    
    
});