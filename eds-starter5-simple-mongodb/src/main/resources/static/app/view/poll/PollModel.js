Ext.define('SimpleApp.view.poll.PollModel', {
	extend: 'Ext.app.ViewModel',

	data: {
		running: false
	},

	stores: {
		pagehits: {
			model: 'SimpleApp.model.PageHit'
		}
	}

});