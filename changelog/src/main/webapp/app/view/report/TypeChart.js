Ext.define('Changelog.view.report.TypeChart', {
	extend: 'Ext.panel.Panel',
	controller: 'Changelog.controller.TypeChartController',
	
	layout: {
		type: 'fit'
	},

	title: i18n.report_type,
	closable: true,
	
	initComponent: function() {
		var me = this;
		var total = 0;
		
		var typeChartStore = Ext.create('Changelog.store.TypeChart'); 
		typeChartStore.on('load', function() {	
			total = 0;
			typeChartStore.each(function(rec) {
				total += rec.get('number');				
			});
		});		
				
		me.items = [ {
			xtype: 'chart',
			itemId: 'chart',
			animate: true,
			store: typeChartStore,
			insetPadding: 20,
			series: [ {
				type: 'pie',
				label: {
					field: 'type',
					display: 'middle',
					contrast: true,
					font: '12px Arial'
				},
				tips: {
					trackMouse: true,
					width: 150,
					renderer: function(storeItem, item) {
						this.setTitle(storeItem.get('type') + ': ' + Math.round(storeItem.get('number') / total * 1000) / 10
								+ ' %');
					}
				},
				showInLegend: true,
				angleField: 'number',
				highlight: {
					segment: {
						margin: 20
					}
				}
			} ]
		} ];

		me.callParent(arguments);
	}

});