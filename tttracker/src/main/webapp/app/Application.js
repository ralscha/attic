Ext.define('TTT.Application', {
	name: 'TTT',

	extend: 'Ext.app.Application',

	views: [
	// TODO: add views here
	],

	controllers: [
	// TODO: add controllers here
	],

	stores: [
	// TODO: add stores here
	],

	launch: function() {
		Ext.create('TTT.view.Viewport');
	}
});
