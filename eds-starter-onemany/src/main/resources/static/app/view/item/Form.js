Ext.define('Starter.view.item.Form',{
   extend:'Ext.form.Panel',

   requires:['Starter.Util'],

   glyph: 0xe803,
   defaultType: 'textfield',
   defaults: {
      anchor: '40%'
   },
   bodyPadding: 10,
   defaultFocus:'textfield[name=code]',

   items: [{

       name:'code',
       fieldLabel: 'Item Code',
       allowBlank: false,
       emptyText: 'Create item code '
   },{

      name:'name',
      fieldLabel: 'Item Name',
      allowBlank: false,
      emptyText: 'Create item  name'
   },{
      xtype: 'combobox',
      fieldLabel: 'Uom',
      name: 'uomId',
      allowBlank: false,
      emptyText: 'Select uom...',
      // forceSelection:true,
      displayField: 'uomName',
      valueField: 'id',
      queryMode: 'local',
      bind: {
         store: '{uoms}'
      }
   }, {
       xtype:'textareafield',
       name:'remark',
       fieldLabel: 'Remark',
       allowBlank: true,
       maxLength: 255,
       enforceMaxLength: true
   }],

   dockedItems: [ {
      xtype: 'toolbar',
      dock: 'top',
      items: [ {
         text: i18n.setup_item,
         handler: 'back',
         glyph: 0xe818
      }, '->',{
         text: i18n.destroy,
         glyph: 0xe806,
         handler: 'destroyItem',
         bind: {
            hidden: '{newItem}'
         }
      }, {
         text: Starter.Util.underline(i18n.save, 'S'),
         accessKey: 's',
         glyph: 0xe80d,
         formBind: true,
         handler: 'save'
      } ]
   } ]

});