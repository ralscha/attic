Ext.onReady(function() {

	var south = new Ext.panel.Panel({
		title: 'Log',
		region: 'south',
		xtype: 'panel',
		height: 100,
		split: true,
		bodyPadding: 5,
		tpl: '<p>{data}</p>',
		tplWriteMode: 'insertFirst',
		autoScroll: true,

		dockedItems: [ {
			xtype: 'toolbar',
			dock: 'top',
			items: [ {
				text: 'Start',
				handler: startPolling
			}, {
				text: 'Stop',
				handler: stopPolling
			} ]
		} ]
	});

	new Ext.panel.Panel({
		width: 700,
		height: 500,
		title: 'Map Track',
		layout: 'border',
		items: [ south, {
			title: 'West Region is collapsible',
			region: 'west',
			xtype: 'panel',
			width: 200,
			split: true,
			id: 'west-region-container',
			layout: 'fit'
		}, {
			title: 'Center Region',
			region: 'center',
			xtype: 'panel',
			layout: 'fit'
		} ],
		renderTo: Ext.getBody()
	});

	var source;

	function startPolling() {
		source = new EventSource('time');
		source.addEventListener('message', function(e) {
			south.update({
				data: e.data
			});
		}, false);

	}

	function stopPolling() {
		if (source) {
			source.close();
		}
	}

});