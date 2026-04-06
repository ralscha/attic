Ext.define('Starter.view.form.FormPanel', {
    extend: 'Ext.form.Panel',
    requires: ['Ext.data.validator.Date'],

    controller: {
        xclass: 'Starter.view.form.FormController'
    },

    title: 'FORM_LOAD, FORM_POST and SIMPLE',

    api: {
        load: 'formLoadService.getFormData',
        submit: 'formSubmitService.handleFormSubmit'
    },
    paramsAsHash: true,

    items: [{
        xtype: 'textfield',
        name: 'osName',
        label: 'OS Name',
        required: true
    }, {
        xtype: 'textfield',
        name: 'osVersion',
        label: 'OS Version',
        required: true
    }, {
        xtype: 'numberfield',
        name: 'availableProcessors',
        label: 'Available Processors',
        required: true
    }, {
        xtype: 'datefield',
        name: 'date',
        label: 'Date',
        dateFormat: 'Y-m-d',
        required: true
    }, {
        xtype: 'filefield',
        name: 'screenshot',
        label: 'Screenshot',
        accept: 'image'
    }, {
        xtype: 'textareafield',
        name: 'remarks',
        label: 'Remarks',
        clearable: true,
        maxRows: 4
    }],

    buttons: [{
        xtype: 'button',
        text: 'Call FORM_LOAD method',
        handler: 'load'
    }, {
        xtype: 'button',
        handler: 'fillRemark',
        text: 'Call SIMPLE method'
    }, {
        text: 'Submit',
        handler: 'submit'
    }]

});
