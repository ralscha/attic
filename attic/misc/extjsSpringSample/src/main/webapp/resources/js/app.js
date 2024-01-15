Ext.namespace('App');

Ext.onReady(function() {

      Ext.Direct.addProvider(App.REMOTING_API);

      App.fields = [{
            name: 'id',
            type: 'integer'
          }, {
            name: 'firstName',
            type: 'string'
          }, {
            name: 'lastName',
            type: 'string'
          }, {
            name: 'dob',
            type: 'date',
            dateFormat: 'Y-m-d'
          }];

      App.writer = new Ext.data.JsonWriter({
            writeAllFields: true,
            listful: true,
            encode: false
          });

      App.myStore = new Ext.data.DirectStore({
            paramsAsHash: true,
            root: 'records',
            autoLoad: true,
            autoSave: false,
            successProperty: 'success',
            fields: App.fields,
            remoteSort: false,
            idProperty: 'id',
            writer: App.writer,
            api: {
              read: App.contactExtDirectService.read,
              create: App.contactExtDirectService.create,
              update: App.contactExtDirectService.update,
              destroy: App.contactExtDirectService.destroy
            }
          });

      App.myColumnModel = [{
            header: 'ID',
            dataIndex: 'id',
            sortable: true,
            width: 40
          }, {
            header: 'First Name',
            dataIndex: 'firstName',
            sortable: true,
            width: 200,
            editor: {
              xtype: 'textfield',
              allowBlank: false
            }
          }, {
            id: 'lastName',
            header: 'Last Name',
            dataIndex: 'lastName',
            sortable: true,
            editor: {
              xtype: 'textfield',
              allowBlank: false
            }
          }, {
            header: 'Date of Birth',
            xtype: 'datecolumn',
            dataIndex: 'dob',
            sortable: true,
            format: 'Y-m-d',
            editor: {
              xtype: 'datefield',
              allowBlank: false,
              format: 'Y-m-d'
            }
          }];
                  
      App.rowEditor = new Ext.ux.grid.RowEditor({
        saveText: 'Update',
        listeners: {
          afteredit: function(rowEditor, changes, r, rowIndex) {
            App.myStore.save();
          }
        }
      });          
          
      App.myGrid = new Ext.grid.GridPanel({
        columns: App.myColumnModel,
        store: App.myStore,
        loadMask: true,
        autoExpandColumn: 'lastName',
        viewConfig: {
          forceFit: false
        },
        tbar: [{
              text: 'Add Contact',
              iconCls: 'add_icon',
              handler: function() {
                var newRecord = new App.myStore.recordType({firstName: 'New First Name', lastName: 'New Last Name', dob: '2010-01-01'}); 
                App.myStore.insert(0, newRecord);
                App.rowEditor.startEditing(0, true);
              }
            },{
              text: 'Remove Contact',
              iconCls: 'delete_icon',
              handler: function() {
                var sm = App.myGrid.getSelectionModel(), 
                    sel = sm.getSelected();
                if (sm.hasSelection()) {
                  Ext.Msg.show({
                        title: 'Remove Contact',
                        buttons: Ext.MessageBox.YESNO,
                        msg: 'Remove ' + sel.data.lastName + ' ?',
                        fn: function(btn) {
                          if (btn === 'yes') {
                            App.myGrid.getStore().remove(sel);
                            App.myGrid.getStore().save();
                          }
                        }
                      });
                };
              }
            }],
        plugins: [App.rowEditor]
      });


      
      new Ext.Window({
            title: 'A simple Ext Direct Spring example',
            renderTo: Ext.getBody(),
            height: 350,
            width: 700,
            border: true,
            frame: true,
            layout: 'fit',
            items: App.myGrid
          }).show();

    });