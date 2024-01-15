Ext.define('Sales.controller.bill.Bill', {
	extend: 'Sales.controller.Abstract',
	screenName: 'bill',
	refs: [ {
		ref: 'listView',
		selector: 'myapp-bill-list'
	}, {
		ref: 'editView',
		selector: 'myapp-bill-edit'
	} ],
	
	init: function() {
		var me = this, format = Ext.String.format;
		me.control({
			'myapp-bill-list': {
				'myapp-add': function() {
					location.href = format('#!/{0}/new', me.screenName);
				},
				'myapp-edit': function(itemid) {
					var query = this.requestParams.q;
					if (query) {
						location.href = format('#!/{0}/id={1}/q={2}', me.screenName, itemid, query);
					} else {
						location.href = format('#!/{0}/id={1}', me.screenName, itemid);
					}
				},
				'myapp-remove': me.onRemove
			},
			'myapp-bill': {
				'myapp-show': me.onShow,
				'myapp-hide': me.onHide
			}
		});
	},
	onShow: function(p) {
		var me = this, o = {}, hash = location.hash, layout = p.getLayout(), listView = me.getListView(), editView = me.getEditView(), params;

		params = hash.substr(('#!/' + me.screenName + '/').length);
		if (params === 'new') {
			o.id = params;
		} else {
			if (!params || !Ext.isString(params)) {
				params = '';
			}
			params = params.split('/');
			Ext.iterate(params, function(text) {
				if (!text || !Ext.isString(text)) {
					text = '';
				}
				text = text.split('=');
				o[text[0]] = text[1];
			});
		}
		
		me.requestParams = params = o;

		if (params.id === 'new' || Number(params.id)) {
			// Show Edit
			layout.setActiveItem(1);
		} else {
			// Show List
			layout.setActiveItem(0);
		}
		// fire event 'myapp-show'
		layout.activeItem.fireEvent('myapp-show', layout.activeItem, p, params);
	},
	onHide: function() {
		var me = this, listView = me.getListView(), editView = me.getEditView();
	},

	onRemove: function(records) {
		var me = this, format = Ext.String.format, listView = me.getListView(), ids = [];
		if (!Ext.isArray(records)) {
			records = [ records ];
		}
		Ext.iterate(records, function(r) {
			if (r.get) {
				ids.push(r.get('id'));
			} else {
				ids.push(r);
			}
		});
		listView.mask();
		MyAppBill.removeItems(ids, function() {
			me.getController(format('{0}.List', me.screenName.split('-').join('.'))).onStoreRefresh();
			listView.unmask();
		});
	}

});
