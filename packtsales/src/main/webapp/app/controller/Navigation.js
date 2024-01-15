Ext.define('Sales.controller.Navigation', {
	extend: 'Sales.controller.Abstract',

	init: function() {
		var me = this;
		me.control({
			'myapp-navigation': {
				itemclick: function(row, model) {
					if (!model.isLeaf()) {
						if (model.isExpanded()) {
							model.collapse();
						} else {
							model.expand();
						}
					} else {
						if (model.data.hrefTarget) {
							console.log('select:' + model.data.hrefTarget);
							me.history.location(model.data.hrefTarget);
						}
					}
				}
			}
		});
	}
});
