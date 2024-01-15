Ext.define('Sales.controller.bill.Edit', {
	extend: 'Sales.controller.Abstract',
	refs: [ {
		ref: 'editView',
		selector: 'myapp-bill-edit'
	} ],
	stores: [
	
	'Customer', 'BillItem' ],
	init: function() {
		var me = this;
		me.control({
			'myapp-bill-edit': {
				'myapp-show': me.onShow,
				'myapp-hide': me.onHide,
				'myapp-dirty': me.onDirty,
				'myapp-undirty': me.onUndirty
			},
			
			'myapp-bill-edit button[action=save]': {
				'click': me.onSave
			},
			
			'myapp-bill-edit button[action=add-item]': {
				'click': me.onAddItem
			},
			'myapp-bill-edit button[action=remove-item]': {
				'click': me.onRemoveItem
			}
		});
		me.setChangeFieldEvents({
			combo: [
			'customer' ],
			hidden: [ 'items' ],
			textarea: [ 'note' ]
		}, 'myapp-bill-edit', me.onChangeField, me);
		var store = me.getBillItemStore(), updateGridData;
		updateGridData = function(store) {
			var f = me.getEditView().query('hidden[name=items]')[0], out = [];
			store.data.each(function(r) {
				out.push(Ext.clone(r.data));
			});
			f.setValue(Ext.encode(out));
		};
		store.on('update', function(store, r) {
			r.set('sum', r.get('qty') * r.get('price'));
			updateGridData(store);
		});
		store.on('add', function(store, r) {
			updateGridData(store);
		});
		store.on('remove', function(store, r) {
			updateGridData(store);
		});
	},

	onSave: function() {
		var me = this, p = me.getEditView(), form = p.getForm(), format = Ext.String.format, id;
		p.setLoading();
		form.submit({
			success: function(form, action) {
				console.log(arguments);
				if (action.result.newid) {
					p.fireEvent('myapp-list-reload');
					location.href = format('#!/bill/id={0}', action.result.newid);
					id = action.result.newid;
					return;
				} else if (action.result.parentId) {
					id = action.result.parentId;
				}
				p.setLoading(false);
				form.load({
					params: {
						id: id
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

	onAddItem: function() {
		var me = this, store = me.getBillItemStore();
		store.add({
			desc: 'New Item',
			qty: 0,
			price: 0,
			sum: 0
		});
	},
	
	onRemoveItem: function() {
		var me = this, store = me.getBillItemStore(), editView = me.getEditView(), grid = editView.down('grid'), sm = grid.getSelectionModel();
		if (sm.getCount()) {
			var next = false;
			Ext.iterate(sm.getSelection(), function(item) {
				var flg = false;
				store.data.each(function(model) {
					if (flg) {
						next = model;
						flg = false;
					}
					if (model.id === item.id) {
						flg = true;
					}
				});
				store.remove(item);
			});
			if (next) {
				sm.select(next);
			} else {
				next = store.getAt(store.data.getCount() - 1);
				if (next) {
					sm.select(next);
				}
			}
		}
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
		var me = this, editView = me.getEditView(), form = editView.getForm(), id = params.id,
		hdn = editView.down('hidden[name=parentId]'), query = params.q, onEditShow,
		onNewShow, store = me.getBillItemStore();

		hdn.setValue(id);
		store.removeAll();

		onEditShow = function() {
			p.setLoading();
			form.trackResetOnLoad = true;
			form.isLoading = true;
			form.load({
				params: {
					id: id
				},
				success: function(form, ret) {
					me.getBillItemStore().add(Ext.decode(form.getValues()['items']));
					p.setLoading(false);
					p.fireEvent('myapp-undirty');
					form.isLoading = false;
				}
			});
		};
		onNewShow = function() {
			form.setValues({
				id: 'new'
			});
			editView.fireEvent('myapp-undirty');
		};
		if (id === 'new') {
			onNewShow();
		} else {
			onEditShow();
		}
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
