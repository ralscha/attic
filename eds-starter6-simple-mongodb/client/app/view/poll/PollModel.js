Ext.define('Starter.view.poll.PollModel', {
	extend: 'Ext.app.ViewModel',

	data: {
		running: false
	},

	stores: {
		pagehits: {
			model: 'Starter.model.PageHit'
		}
	}

});