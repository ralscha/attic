/**
 * Form used for creating and editing Staff Members
 */
Ext.define('CarTracker.view.staff.edit.Form', {
	extend: 'Ext.form.Panel',
	alias: 'widget.staff.edit.form',
	requires: [ 'Ext.form.FieldContainer', 'Ext.form.field.Date', 'Ext.form.field.Text', 'Ext.form.field.ComboBox',
			'CarTracker.ux.form.field.RemoteComboBox' ],
	bodyPadding: 5,
	initComponent: function() {
		var me = this;
		Ext.applyIf(me, {
			fieldDefaults: {
				allowBlank: false,
				labelAlign: 'top',
				flex: 1,
				margins: 5
			},
			defaults: {
				layout: 'hbox',
				margins: '0 10 0 10'
			},
			items: [ {
				xtype: 'fieldcontainer',
				items: [ {
					xtype: 'textfield',
					name: 'firstName',
					fieldLabel: 'First Name'
				}, {
					xtype: 'textfield',
					name: 'lastName',
					fieldLabel: 'Last Name'
				}, {
					xtype: 'datefield',
					name: 'dob',
					fieldLabel: 'DOB'
				} ]
			}, {
				xtype: 'fieldcontainer',
				items: [ {
					xtype: 'textfield',
					name: 'username',
					fieldLabel: 'Username'
				}, {
					xtype: 'textfield',
					name: 'changePassword',
					fieldLabel: 'Password'
				} ]
			}, {
				xtype: 'fieldcontainer',
				items: [ {
					xtype: 'textfield',
					name: 'address1',
					fieldLabel: 'Address1'
				}, {
					xtype: 'textfield',
					name: 'address2',
					allowBlank: true,
					fieldLabel: 'Address2'
				} ]
			}, {
				xtype: 'fieldcontainer',
				items: [ {
					xtype: 'textfield',
					name: 'city',
					fieldLabel: 'City'
				}, {
					xtype: 'textfield',
					name: 'state',
					fieldLabel: 'State'
				}, {
					xtype: 'textfield',
					name: 'postalCode',
					fieldLabel: 'Postal Code'
				} ]
			}, {
				xtype: 'fieldcontainer',
				items: [ {
					xtype: 'textfield',
					name: 'phone',
					fieldLabel: 'Phone'
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
					editable: false,
					forceSelection: true
				}, {
					xtype: 'datefield',
					name: 'hireDate',
					allowBlank: false,
					fieldLabel: 'Hire Date'
				} ]
			}, {
				xtype: 'fieldset',
				title: 'Admin Roles',
				items: [ {
					xtype: 'itemselectorfield',
					name: 'userRoleIds',
					anchor: '100%',
					height: 150,
					store: {
						type: 'option.userrole'
					},
					displayField: 'longName',
					valueField: 'id',
					allowBlank: false,
					msgTarget: 'side',
					fromTitle: 'Available Roles',
					toTitle: 'Selected Roles',
					buttons: [ 'add', 'remove' ],
					delimiter: null
				} ]
			} ]
		});
		me.callParent(arguments);
	}
});