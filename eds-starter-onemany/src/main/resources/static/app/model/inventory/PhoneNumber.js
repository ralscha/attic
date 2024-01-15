/**
 * Created by kmkywar on 17/02/2015.
 */

Ext.define('Starter.model.inventory.PhoneNumber', {
    extend: 'Ext.data.field.String',
    alias: 'data.field.phonenumber',

    validators: [ {
        type: 'format',
        matcher: /^\d{3}-?\d{3}-?\d{4}$/,
        message: 'Must be in the format xxx-xxx-xxxx'
    } ]
});