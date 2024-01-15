Ext.define('Sales.controller.myaccount.Edit', {
	extend: 'Sales.controller.Abstract',
	refs: [ {
		ref: 'editView',
		selector: 'myapp-myaccount-edit'
	} ],
	init: function() {
		var me = this;
		me.control({
			'myapp-myaccount-edit': {
				'myapp-show': me.onShow,
				'myapp-hide': me.onHide,
				'myapp-dirty': me.onDirty,
				'myapp-undirty': me.onUndirty
			},
			'myapp-myaccount-edit button[action=save]': {
				'click': me.onSave
			},
		});
		Ext.iterate([ {
			name: 'email',
			xtype: 'textfield',
			fn: me.onChangeField
		}, {
			name: 'firstname',
			xtype: 'textfield',
			fn: me.onChangeField
		}, {
			name: 'lastname',
			xtype: 'textfield',
			fn: me.onChangeField
		} ], function(f) {
			var scope = me, o = {}, format = Ext.String.format, key = format('{0} {1}[name="{2}"]', 'myapp-myaccount-edit', f.xtype, f.name);
			o[key] = {
				change: {
					fn: f.fn,
					scope: scope
				}
			};
			me.control(o);
		});
		me.setChangeFieldEvents({
			textfield: [ 'email', 'firstname', 'lastname' ]
		}, 'myapp-myaccount-edit', me.onChangeField, me);
	},

	onSave: function() {
		var me = this, p = me.getEditView(), form = p.getForm(), format = Ext.String.format, id;
		p.setLoading();
		form.submit({
			success: function(form, action) {
				console.log(arguments);
				p.setLoading(false);
				form.load({
					params: {
						id: form.getValues()['id']
					},
					success: function(form, ret) {
						p.fireEvent('myapp-loadform', p, ret);
						p.fireEvent('myapp-undirty');
						p.setLoading(false);
					},
					failure: function() {
						p.setLoading(false);
					}
				});
			},
			failure: function(form, action) {
				console.log('failure', arguments);
				p.setLoading(false);
			}
		});
	},

	onChangeField: function(field) {
		var p = field.findParentByType('form'), form = p.getForm();
		if (Ext.Object.getKeys(form.getFieldValues(true)).length > 0) {
			p.fireEvent('myapp-dirty');
		} else {
			p.fireEvent('myapp-undirty');
		}
	},
	
	onShow: function(p, owner, params) {
		var me = this, editView = me.getEditView(), form = editView.getForm();

		p.setLoading();
		form.trackResetOnLoad = true;
		form.isLoading = true;
		form.load({
			success: function(form, ret) {
				p.setLoading(false);
				p.fireEvent('myapp-undirty');
				form.isLoading = false;
			}
		});

	},
	onHide: function() {
	},
	onDirty: function() {
		var me = this, editView = me.getEditView(), btnSave = editView.down('button[action=save]');
		btnSave.enable();
	},
	onUndirty: function() {
		var me = this, editView = me.getEditView(), btnSave = editView.down('button[action=save]');
		btnSave.disable();
	}
});
