Ext.define('Starter.view.dummy.Controller', {
	extend: 'Ext.app.ViewController',

	init: function() {
		new Ext.LoadMask({
			target: this.getView(),
			msg: i18n.loading,
			store: this.getStore('companies')
		});
	},
	
	percentRenderer: function perc(v) {
		return v + '%';
	},

	onCompaniesStoreLoad: function(store) {
		var option = {
			tooltip: {
				trigger: 'axis'
			},
			xAxis: [ {
				type: 'category',
				data: []
			} ],
			yAxis: [ {
				type: 'value'
			} ],
			series: [ {
				name: i18n.dummy_price,
				type: 'bar',
				data: []
			} ]
		};
		store.each(function(r) {
			option.xAxis[0].data.push(Ext.String.ellipsis(r.get('name'), 7));
			option.series[0].data.push(r.get('price'));
		});
		this.lookupReference('barchart').drawChart(option, true);
	},

	onGridSelect: function(grid, record) {
		var option = {
			polar: [ {
				indicator: [ {
					text: i18n.dummy_revenue + ' %',
					max: 100
				}, {
					text: i18n.dummy_growth + ' %',
					max: 100
				}, {
					text: i18n.dummy_product + ' %',
					max: 100
				}, {
					text: i18n.dummy_market + ' %',
					max: 100
				} ]
			} ],
			series: [ {
				type: 'radar',
				data: [ {
					value: [ record.get('revenue'), record.get('growth'), record.get('product'), record.get('market') ]
				} ]
			} ]
		};

		this.lookupReference('radarchart').drawChart(option, true);
	},

	formChange: function(field, newValue, oldValue, listener) {
		var companySelection = this.getViewModel().get('companySelection');
		this.onGridSelect(null, companySelection);
	},

	callSecuredService: function() {
		dummyService.notAllowedTest();
	}

});
