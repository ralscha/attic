Ext.define('Changelog.controller.TypeChartController', {
	extend: 'Deft.mvc.ViewController',

	control: {
		view: {
			activated: 'onActivated'
		},
		chart: true
	},

	onActivated: function() {
		this.getChart().getStore().load();
	}
});


