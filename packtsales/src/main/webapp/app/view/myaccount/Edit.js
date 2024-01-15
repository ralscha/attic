Ext.define('Sales.view.myaccount.Edit', {
	extend: 'Sales.form.Panel',
	alias: 'widget.myapp-myaccount-edit',
	itemId: 'screen-myaccount-edit',
	api: {
		load: 'MyAppMyAccount.readForm',
		submit: 'MyAppMyAccount.writeForm'
	},
	initComponent: function() {
		var me = this;
		// Fields
		Ext.apply(me, {
			bodyPadding: 20,
			defaultType: 'textfield',
			items: [ {
				fieldLabel: 'email',
				name: 'email'
			}, {
				fieldLabel: 'firstname',
				name: 'firstname'
			}, {
				fieldLabel: 'lastname',
				name: 'lastname'
			} ]
		});
		// TopToolbar
		Ext.apply(me, {
			tbar: [ {
				text: 'Save',
				action: 'save',
				disabled: true
			}, {
				text: 'Reset',
				action: 'reset'
			} ]
		});
		me.callParent(arguments);
	}
});
