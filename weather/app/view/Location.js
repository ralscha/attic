Ext.define('Weather.view.Location', {
	requires: ['Weather.controller.Location'],
	controller: 'Weather.controller.Location',
	extend: 'Ext.form.Panel',
	xtype: 'location',
	config: {

		title: 'Location',
		iconCls: 'locate',

		items: [ {
			xtype: 'textfield',
			name: 'location',
			itemId: 'location',
			label: 'Location'
		}, {
			xtype: 'checkboxfield',
			name: 'currentLocation',
			itemId: 'currentLocation',
			label: 'Or use current location',
			labelWidth: '80%'
		}, {
			xtype: 'button',
			docked: 'bottom',
			itemId: 'saveButton',
			text: 'Save'
		} ]
	}

});