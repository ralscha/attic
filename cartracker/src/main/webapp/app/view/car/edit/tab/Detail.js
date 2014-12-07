/**
 * Main panel for displaying details for {@link CarTracker.model.Car} records
 */
Ext.define('CarTracker.view.car.edit.tab.Detail', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.car.edit.tab.detail',
	layout: 'form',
	initComponent: function() {
		var me = this;
		Ext.applyIf(me, {
			items: [ {
				xtype: 'fieldset',
				title: 'Car Info',
				collapsible: true,
				defaults: {
					layout: 'hbox',
					margins: '0 10 0 10'
				},
				items: [ {
					xtype: 'fieldcontainer',
					items: [ {
						xtype: 'ux.form.field.remotecombobox',
						name: 'makeId',
						fieldLabel: 'Make',
						displayField: 'longName',
						valueField: 'id',
						store: {
							type: 'option.make',
							pageSize: -1
						},
						plugins: [ {
							ptype: 'cleartrigger'
						} ],
						editable: false,
						forceSelection: true
					}, {
						xtype: 'ux.form.field.remotecombobox',
						name: 'modelId',
						fieldLabel: 'Model',
						displayField: 'longName',
						valueField: 'id',
						store: {
							type: 'option.model',
							pageSize: -1
						},
						plugins: [ {
							ptype: 'cleartrigger'
						} ],
						editable: false,
						forceSelection: true
					}, {
						xtype: 'ux.form.field.remotecombobox',
						name: 'statusId',
						fieldLabel: 'Status',
						displayField: 'longName',
						valueField: 'id',
						store: {
							type: 'option.status',
							pageSize: -1
						},
						plugins: [ {
							ptype: 'cleartrigger'
						} ],
						editable: false,
						forceSelection: true
					} ]
				}, {
					xtype: 'fieldcontainer',
					items: [ {
						xtype: 'numberfield',
						name: 'year',
						fieldLabel: 'Year',
						minValue: 1920
					}, {
						xtype: 'ux.form.field.remotecombobox',
						name: 'categoryId',
						fieldLabel: 'Category',
						displayField: 'longName',
						valueField: 'id',
						store: {
							type: 'option.category',
							pageSize: -1
						},
						plugins: [ {
							ptype: 'cleartrigger'
						} ],
						editable: false,
						forceSelection: true
					}, {
						xtype: 'ux.form.field.remotecombobox',
						name: 'colorId',
						fieldLabel: 'Color',
						displayField: 'longName',
						valueField: 'id',
						store: {
							type: 'option.color',
							pageSize: -1
						},
						plugins: [ {
							ptype: 'cleartrigger'
						} ],
						editable: false,
						forceSelection: true
					} ]
				}, {
					xtype: 'fieldcontainer',
					items: [ {
						xtype: 'numberfield',
						name: 'mileage',
						fieldLabel: 'Mileage',
						minValue: 0
					}, {
						xtype: 'textfield',
						name: 'stockNumber',
						fieldLabel: 'Stock Number'
					}, {
						xtype: 'textfield',
						name: 'vin',
						fieldLabel: 'VIN'
					} ]
				}, {
					xtype: 'fieldcontainer',
					items: [ {
						xtype: 'textfield',
						name: 'fuel',
						fieldLabel: 'Fuel'
					}, {
						xtype: 'textfield',
						name: 'engine',
						fieldLabel: 'Engine'
					}, {
						xtype: 'textfield',
						name: 'transmission',
						fieldLabel: 'Transmission'
					}, {
						xtype: 'ux.form.field.remotecombobox',
						name: 'driveTrainId',
						fieldLabel: 'Drive Train',
						displayField: 'longName',
						valueField: 'id',
						store: {
							type: 'option.drivetrain',
							pageSize: -1
						},
						plugins: [ {
							ptype: 'cleartrigger'
						} ],
						editable: false,
						forceSelection: true
					} ]
				}, {
					xtype: 'fieldcontainer',
					items: [ {
						xtype: 'htmleditor',
						name: 'description',
						fieldLabel: 'Description',
						height: 150
					} ]
				} ]
			}, {
				xtype: 'fieldset',
				title: 'Sales Info',
				collapsible: true,
				defaults: {
					layout: 'hbox',
					margins: '0 10 0 10'
				},
				items: [ {
					xtype: 'fieldcontainer',
					items: [ {
						xtype: 'numberfield',
						name: 'listPrice',
						fieldLabel: 'List Price'
					}, {
						xtype: 'datefield',
						name: 'acquisitionDate',
						fieldLabel: 'Date Acquired'
					} ]
				}, {
					xtype: 'fieldcontainer',
					items: [ {
						xtype: 'numberfield',
						name: 'salePrice',
						fieldLabel: 'Sale Price',
						disabled: !CarTracker.loggedInUser.inRole('ADMIN')
					}, {
						xtype: 'datefield',
						name: 'saleDate',
						fieldLabel: 'Sale Date',
						disabled: !CarTracker.loggedInUser.inRole('ADMIN')
					} ]
				}, {
					xtype: 'fieldcontainer',
					items: [ {
						xtype: 'ux.form.field.remotecombobox',
						name: 'salesPeopleIds',
						fieldLabel: 'Sales People',
						displayField: 'lastName',
						valueField: 'id',
						store: {
							type: 'staff',
							pageSize: -1
						},
						plugins: [ {
							ptype: 'cleartrigger'
						} ],
						multiSelect: true,
						editable: false,
						forceSelection: true,
						disabled: !CarTracker.loggedInUser.inRole('ADMIN')
					} ]
				} ]
			} ]
		});
		me.callParent(arguments);
	}
});