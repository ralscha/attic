Ext.define('Starter.view.crud.UserModel', {
    extend: 'Ext.app.ViewModel',

    data: {
        selectedUser: null
    },

    stores: {
        users: {
            model: 'Starter.model.User',
            autoLoad: true,
            pageSize: 0,
            remoteSort: false,
            remoteFilter: false,
            autoSync: true,
            sorters: [{
                property: 'lastName',
                direction: 'ASC'
            }]
        }
    }

});
