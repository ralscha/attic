Ext.define('Sales.controller.bill.List', {
    extend: 'Sales.controller.Abstract',
    stores: [
        'Bill'
    ],
    refs: [{
        ref: 'listView', selector: 'myapp-bill-list' 
    }],
    init: function() {
        var me = this;
        me.control({
            'myapp-bill-list': {
                'myapp-show': me.onShow,
                'select': me.onSelect,
                'itemdblclick': me.onItemDblClick,
                'deselect': me.onDeselect
            },
            'myapp-bill-list button[action=add]': {
                'click': me.onItemAdd
            },
            'myapp-bill-list button[action=edit]': {
                'click': me.onItemEdit
            },
            'myapp-bill-list button[action=remove]': {
                'click': me.onItemRemove
            },
            'myapp-bill-list button[action=refresh]': {
                'click': me.onStoreRefresh
            }
        });
    },
    onShow: function(p, owner, params) {
        var me          = this,
            listView    = me.getListView(),
            btnAdd      = listView.down('button[action=add]'),
            btnEdit     = listView.down('button[action=edit]'),
            btnRemove   = listView.down('button[action=remove]'),
            btnRefresh  = listView.down('button[action=refresh]'),
            fieldSearch = listView.down('myapp-searchfield'),
            query       = params.q;
        btnAdd.disable();
        btnEdit.disable();
        btnRemove.disable();
        btnRefresh.disable();
        if(query) {
            fieldSearch.setValue(query);
            fieldSearch.triggerCell.item(0).setDisplayed(true);
            fieldSearch.hasSearch = true;
        }
        fieldSearch.urlRoot = '#!/bill/';
        fieldSearch.disable();
        listView.getStore().load({
            params: {
                query: query
            },
            callback: function(records, operation, success) {
                btnAdd.enable();
                btnRefresh.enable();
                fieldSearch.enable();
            }
        });
    },
    onSelect: function() {
        var me = this,
            listView = me.getListView(),
            btnEdit = listView.down('button[action=edit]'),
            btnRemove = listView.down('button[action=remove]'),
            sm = listView.getSelectionModel(),
            cnt = sm.getCount();
        if(cnt === 1) {
            btnEdit.enable();
        } else {
            btnEdit.disable();
        }
        if(cnt > 0) {
            btnRemove.enable();
        } else {
            btnRemove.disable();
        }
    },
    onDeselect: function() {
        var me = this,
            listView = me.getListView(),
            btnEdit = listView.down('button[action=edit]'),
            btnRemove = listView.down('button[action=remove]'),
            sm = listView.getSelectionModel(),
            cnt = sm.getCount();
        if(cnt === 1) {
            btnEdit.enable();
        } else {
            btnEdit.disable();
        }
        if(cnt > 0) {
            btnRemove.enable();
        } else {
            btnRemove.disable();
        }
    },
    onItemDblClick: function(p, record, item, index, e, eOpts) {
        var me          = this,
            listView    = me.getListView();
        listView.fireEvent('myapp-edit', record.data.id);
    },
    onItemAdd: function() {
        var me = this,
            listView = me.getListView();
        listView.fireEvent('myapp-add');
    },
    onItemEdit: function() {
        var me = this,
            listView = me.getListView(),
            sm = listView.getSelectionModel(),
            record = sm.getLastSelected();
        listView.fireEvent('myapp-edit', record.data.id);
    },
    onItemRemove: function() {
        var me          = this,
            listView    = me.getListView(),
            sm          = listView.getSelectionModel(),
            records     = sm.getSelection();
        Ext.MessageBox.confirm(
            'Remove Confirm',
            'May I delete that?',
            function(ret) {
                if(ret === 'yes') {
                    listView.fireEvent('myapp-remove', records);
                }
            }
        );
    },
    onStoreRefresh: function() {
        var me = this,
            listView = me.getListView(),
            tbar = listView.getDockedItems('toolbar[dock="bottom"]')[0];
        tbar.doRefresh();
    }
});

