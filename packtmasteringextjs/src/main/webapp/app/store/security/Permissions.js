Ext.define('Packt.store.security.Permissions', {
    extend: 'Ext.data.TreeStore',

    root: {
        children : []
    },

    proxy: {
        type: 'direct',
        directFn: 'menuService.loadPermission'
    }
});