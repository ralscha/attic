Ext.define('Starter.model.PageHit', {
    extend: 'Ext.data.Model',
    requires: ['Ext.data.identifier.Uuid'],
    identifier: 'uuid',

    fields: [{
        name: 'month',
        type: 'string'
    }, {
        name: 'hit',
        type: 'int'
    }]

});
