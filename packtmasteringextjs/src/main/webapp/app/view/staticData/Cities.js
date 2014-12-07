Ext.define('Packt.view.staticData.Cities', {
	extend: 'Packt.view.staticData.AbstractGrid',
	alias: 'widget.citiesgrid',
	requires: ['Packt.store.staticData.Cities'],
	store: 'staticData.Cities',

	features: [ {
		ftype: 'grouping',
		itemId: 'grouping',
		enableGroupingMenu: false,
		groupHeaderTpl: Ext.create('Ext.XTemplate', '{columnName}: ', '{name:this.formatName}', {
			formatName: function(name) {
				var countriesStore = Ext.getStore('countries');
				var country = countriesStore.findRecord('id', name);
				return country !== null ? country.get('country') : name;
			}
		})
	} ],

	columns: [ {
		text: 'City Id',
		width: 100,
		dataIndex: 'id',
		filter: {
			type: 'numeric'
		}
	}, {
		text: 'City Name',
		flex: 1,
		dataIndex: 'city',
		editor: {
			allowBlank: false,
			maxLength: 45
		},
		filter: {
			type: 'string'
		}
	}, {
		text: 'Country',
		width: 200,
		dataIndex: 'countryId',
		editor: {
			xtype: 'combobox',
			allowBlank: false,
			displayField: 'country',
			valueField: 'id',
			queryMode: 'local',
			store: 'countries'
		},
		renderer: function(value, metaData, record) {
			var countriesStore = Ext.getStore('countries');
			var country = countriesStore.findRecord('id', value);
			return country !== null ? country.get('country') : value;
		}
	} ]
});