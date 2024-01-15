Ext.define('Sales.controller.quotation.Import', {
	extend: 'Sales.controller.Abstract',
	refs: [ {
		ref: 'importView',
		selector: 'myapp-quotation-import'
	} ],
	stores: [ 'QuotationImport', 'QuotationImportItems' ],
	init: function() {
		var me = this;
		me.control({
			'myapp-quotation-import': {
				'myapp-show': me.onShow,
				'myapp-hide': me.onHide
			},
			'myapp-quotation-import [action=upload]': {
				'change': me.onUploaded
			},
			'myapp-quotation-import [action=execute]': {
				// Add execute handler.
				'click': me.onExecute
			}
		});
	},
	onShow: function(p, owner, params) {
	},
	onHide: function() {
	},
	onUploaded: function(fb, v) {
		var me = this, p = me.getImportView(), form = p.getForm(), importView = me.getImportView(), btnExecute = importView.down('button[action=execute]');

		p.setLoading();
		btnExecute.disable();
		form.submit({
			success: function(form, action) {
				Ext.StoreManager.lookup('QuotationImport').loadData(action.result.quotation.items);
				Ext.StoreManager.lookup('QuotationImportItems').loadData(action.result.quotations.items);
				p.setLoading(false);
				btnExecute.enable();
			}
		});
	},
	onExecute: function() {
		var data = {
			quotation: [],
			quotations: []
		}, store = Ext.StoreManager.lookup('QuotationImport'), items = Ext.StoreManager.lookup('QuotationImportItems');

		Ext.iterate(store.data.items, function(item) {
			data.quotation.push(Ext.clone(item.data));
		});

		Ext.iterate(items.data.items, function(item) {
			data.quotations.push(Ext.clone(item.data));
		});

		MyAppQuotation.executeImport(data, function() {
			location.href = '#!/quotation';
		});
	}
});
