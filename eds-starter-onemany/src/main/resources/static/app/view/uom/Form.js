Ext.define('Starter.view.uom.Form',{
   extend:'Ext.form.Panel',

   requires:['Starter.Util'],

   glyph: 0xe803,
   defaultType: 'textfield',
   defaults: {
      anchor: '40%'
   },
   bodyPadding: 10,
   defaultFocus:'textfield[name=uomName]',

   items: [{

       name:'uomName',
       fieldLabel: 'UOM Name',
       allowBlank: false,
       emptyText: 'Create uom  name'
   }, {
       xtype:'textareafield',
       name:'notes',
       fieldLabel: 'Note',
       allowBlank: true,
       maxLength: 255,
       enforceMaxLength: true
   }],

   dockedItems: [ {
      xtype: 'toolbar',
      dock: 'top',
      items: [ {
         text: i18n.setup_uom,
         handler: 'back',
         glyph: 0xe818
      }, '->',{
         text: i18n.destroy,
         glyph: 0xe806,
         handler: 'destroyUom',
         bind: {
            hidden: '{newUom}'
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