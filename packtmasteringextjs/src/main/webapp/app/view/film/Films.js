Ext.define('Packt.view.film.Films', {
	extend: 'Packt.view.sakila.SakilaGrid',
	alias: 'widget.filmsgrid',

	requires: [ 'Ext.grid.plugin.RowExpander', 'Packt.view.sakila.SakilaGrid' ],

	store: 'film.Films',

	columns: [ {
		text: 'Film Id',
		width: 100,
		dataIndex: 'id'
	}, {
		text: 'Title',
		flex: 1,
		dataIndex: 'title'
	}, {
		text: 'Language',
		width: 100,
		dataIndex: 'languageId',
		renderer: function(value, metaData, record) {
			var languagesStore = Ext.getStore('languages');
			var lang = languagesStore.findRecord('id', value);
			return lang !== null ? lang.get('name') : value;
		}
	}, {
		text: 'Release Year',
		width: 90,
		dataIndex: 'releaseYear'
	}, {
		text: 'Lenght',
		width: 80,
		dataIndex: 'length'
	}, {
		text: 'Rating',
		width: 70,
		dataIndex: 'rating'
	} ],

	dockedItems: [ {
		dock: 'bottom',
		xtype: 'pagingtoolbar',
		store: 'film.Films',
		displayInfo: true,
		displayMsg: 'Displaying films {0} - {1} of {2}',
		emptyMsg: "No films to display"
	}, {
		xtype: 'addeditdelete'
	} ],

	plugins: [ {
		ptype: 'rowexpander',
        rowBodyTpl : [
                      '<p><b>Description:</b> {description}</p>',
                      '<p><b>Special Features:</b> {specialFeatures}</p>',
                      '<p><b>Rental Duration:</b> {rentalDuration}</p>',
                      '<p><b>Rental Rate:</b> {rentalRate}</p>',
                      '<p><b>Replacement Cost:</b> {replacementCost}</p>'
                  ]
	} ]
});