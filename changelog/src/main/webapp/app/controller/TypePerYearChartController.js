Ext.define('Changelog.controller.TypePerYearChartController', {
	extend: 'Deft.mvc.ViewController',

	control: {
		view: {
			activated: 'onActivated'
		},
		chart: true,
		yearCB: {
			select: 'onYearSelect'
		}
	},

	onYearSelect: function(combo, selectedItem) {
		this.getChart().getStore().load({
			params: {
				year: selectedItem[0].data.year
			}
		});
	},
	
	onActivated: function() {
		var me = this;
		
		var yearStore = me.getYearCB().getStore();
		
		yearStore.load({
			callback: function() {
				me.getYearCB().select(yearStore.first());
				
				me.getChart().getStore().load({
					params: {
						year: yearStore.first().data.year
					}
				});
			}
		});

	}
});
