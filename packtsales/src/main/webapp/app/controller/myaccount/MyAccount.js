Ext.define('Sales.controller.myaccount.MyAccount', {
	extend: 'Sales.controller.Abstract',
	screenName: 'myaccount',
	refs: [ {
		ref: 'editView',
		selector: 'myapp-myaccount-edit'
	} ],
	init: function() {
		var me = this;
		me.control({
			'myapp-myaccount': {
				'myapp-show': me.onShow,
				'myapp-hide': me.onHide
			}
		});
	},
	onShow: function() {
		var me = this, editView = me.getEditView();
		editView.fireEvent('myapp-show', editView);
	},
	onHide: function() {
		var me = this, editView = me.getEditView();
		editView.fireEvent('myapp-hide', editView);
	}
});
