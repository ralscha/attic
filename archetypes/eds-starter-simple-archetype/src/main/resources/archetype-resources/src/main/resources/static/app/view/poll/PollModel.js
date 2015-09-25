Ext.define('${jsAppNamespace}.view.poll.PollModel', {
	extend: 'Ext.app.ViewModel',

	data: {
		running: false
	},

	stores: {
		pagehits: {
			model: '${jsAppNamespace}.model.PageHit'
		}
	}

});