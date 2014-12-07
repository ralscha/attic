/**
 * Form used for creating and editing Staff Members
 */
Ext.define('CarTracker.view.car.search.Form', {
	extend: 'Ext.form.Panel',
	alias: 'widget.car.search.form',
	requires: [ 'Ext.form.FieldContainer', 'Ext.form.FieldSet', 'Ext.form.field.Date', 'Ext.form.field.Text', 'Ext.form.field.ComboBox', 'Ext.slider.Multi',
			'CarTracker.ux.form.field.RemoteComboBox', 'CarTracker.ux.form.field.plugin.ClearTrigger' ],
	initComponent: function() {
		var me = this;
		Ext.applyIf(me, {
			fieldDefaults: {
				labelAlign: 'top',
				flex: 1,
				margins: 5
			},
			items: [ {
				xtype: 'fieldset',
				title: 'Car Details',
				collapsible: true,
				items: [ {
					xtype: 'fieldcontainer',
					layout: 'hbox',
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
						forceSelection: true,
						multiSelect: true
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
						forceSelection: true,
						multiSelect: true
					} ]
				}, {
					xtype: 'fieldcontainer',
					layout: 'hbox',
					items: [ {
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
						forceSelection: true,
						multiSelect: true
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
						forceSelection: true,
						multiSelect: true
					} ]
				}, {
					xtype: 'fieldcontainer',
					layout: 'hbox',
					items: [ {
						xtype: 'multislider',
						name: 'listPrice',
						fieldLabel: 'List Price',
						minValue: 0,
						maxValue: 60000,
						increment: 500,
						values: [ 5000, 30000 ]
					} ]
				}, {
					xtype: 'fieldcontainer',
					layout: 'hbox',
					items: [ {
						xtype: 'datefield',
						name: 'acquisitionStartDate',
						fieldLabel: 'Acquisition (Start)'
					}, {
						xtype: 'datefield',
						name: 'acquisitionEndDate',
						fieldLabel: 'Acquisition (End)'
					} ]
				} ]
			}, {
				xtype: 'fieldset',
				title: 'Car Options',
				collapsible: true,
				items: [ {
					xtype: 'fieldcontainer',
					layout: 'hbox',
					items: [ {
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
						forceSelection: true,
						multiSelect: true
					}, {
						xtype: 'ux.form.field.remotecombobox',
						name: 'featuresId',
						fieldLabel: 'Features',
						displayField: 'longName',
						valueField: 'id',
						store: {
							type: 'option.feature',
							pageSize: -1
						},
						plugins: [ {
							ptype: 'cleartrigger'
						} ],
						editable: false,
						forceSelection: true,
						multiSelect: true
					} ]
				} ]
			}, {
				xtype: 'fieldset',
				title: 'Sales',
				collapsible: true,
				items: [ {
					xtype: 'fieldcontainer',
					layout: 'hbox',
					items: [ {
						xtype: 'multislider',
						name: 'salePrice',
						fieldLabel: 'Sale Price',
						minValue: 0,
						maxValue: 60000,
						increment: 500,
						values: [ 5000, 30000 ]
					} ]
				}, {
					xtype: 'fieldcontainer',
					layout: 'hbox',
					items: [ {
						xtype: 'datefield',
						name: 'saleStartDate',
						fieldLabel: 'Sale Date (Start)'
					}, {
						xtype: 'datefield',
						name: 'saleEndDate',
						fieldLabel: 'Sale Date (End)'
					} ]
				}, {
					xtype: 'fieldcontainer',
					layout: 'hbox',
					items: [ {
						xtype: 'ux.form.field.remotecombobox',
						name: 'salesPeopleId',
						fieldLabel: 'Sales Person',
						displayField: 'lastName',
						valueField: 'id',
						store: {
							type: 'staff',
							pageSize: -1
						},
						plugins: [ {
							ptype: 'cleartrigger'
						} ],
						editable: false,
						forceSelection: true,
						multiSelect: true
					}, {
						xtype: 'ux.form.field.remotecombobox',
						name: 'positionId',
						fieldLabel: 'Position',
						displayField: 'longName',
						valueField: 'id',
						store: {
							type: 'option.position',
							pageSize: -1
						},
						plugins: [ {
							ptype: 'cleartrigger'
						} ],
						editable: false,
						forceSelection: true,
						multiSelect: true
					} ]
				} ]
			} ]
		});
		me.callParent(arguments);
	}
});