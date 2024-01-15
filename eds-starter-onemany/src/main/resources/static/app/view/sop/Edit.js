/**
 * Created by kmkywar on 27/07/2015.
 */

Ext.define('Starter.view.sop.Edit', {
    extend: 'Ext.form.Panel',
    requires: ['Starter.Util'],

    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    bodyPadding: 10,
    defaults: {
        anchor: '50%'
    },
    defaultFocus:'textfield[name=name]',

    items: [ {
        xtype: 'textfield',
        fieldLabel: 'Name',
        name: 'name',
        msgTarget: 'side',
        bind: '{theSopheader.name}'
    }, {
        xtype: 'textfield',
        fieldLabel: 'Phone',
        name: 'phone',
        msgTarget: 'side',
        bind: '{theSopheader.phone}'
    }, {
        xtype: 'grid',
        flex: 1,
        reference: 'detailGrid',
        pageSize: 10,
        margin: '10 0 0 0',
        title: 'Orders',
        bind: {
            store: '{theSopheader.sopDetails}'
        },
        tbar: [ {
            text: 'Add Order',
            glyph: 0xec42,
            handler: 'onAddOrderClick'
        } ],
        columns: [ {
            text: 'Id',
            dataIndex: 'id',
            width: 50
        }, {
            xtype: 'datecolumn',
            text: 'Date',
            dataIndex: 'orderdate',
            format: 'Y-m-d',
            flex: 1
        }, {
            xtype: 'checkcolumn',
            text: 'Shipped',
            dataIndex: 'shipped'
        }, {
            xtype: 'widgetcolumn',
            width: 120,
            widget: {
                xtype: 'button',
                text: 'Remove',
                glyph: 0xefd1,
                handler: 'onRemoveOrderClick'
            }
        } ]
    } ],

    dockedItems: [ {
        xtype: 'toolbar',
        dock: 'top',
        items: [ {
            text:i18n.sop_trans,
            handler: 'back',
            glyph: 0xe818
        }, '->', {
            text: i18n.destroy,
            glyph: 0xe806,
            handler: 'destroySop',
            bind: {
                hidden: '{newSop}'
            }
        }, {
            text: Starter.Util.underline(i18n.save, 'S'),
            accessKey: 's',
            glyph: 0xe80d,
            formBind: true,
            handler: 'saveSop'
        } ]
    } ]


});
