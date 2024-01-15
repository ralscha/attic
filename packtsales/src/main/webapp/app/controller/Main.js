Ext.define('Sales.controller.Main', {
	extend: 'Sales.controller.Abstract',
	refs: [ {
		ref: 'centerView',
		selector: 'viewport panel[region=center]'
	} ],
	init: function() {
		var me = this;
		Ext.iterate(me.application.screens, function(name) {
			var o = {};
			o['myapp-' + name] = {
				'myapp-show': Ext.pass(me.onChangeScreen, [ name ])
			};
			me.control(o);
		});
	},
	onChangeScreen: function(name) {
		var me = this, center = me.getCenterView(), layout = center.getLayout(), preActiveItem = layout.activeItem;

		// $B%"%/%F%#%V%"%$%F%`@_Dj(B
		layout.setActiveItem('screen-' + name);

		if (preActiveItem) {

			// 'myapp-hide'$B%$%Y%s%HH/2P(B
			preActiveItem.fireEvent('myapp-hide');
		}
	}
});
