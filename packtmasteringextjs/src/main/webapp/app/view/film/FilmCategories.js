Ext.define('Packt.view.film.FilmCategories', {
	extend: 'Packt.view.sakila.SakilaGrid',
	alias: 'widget.filmcategories',

	requires: [ 'Packt.view.toolbar.SearchAddDelete' ],

	store: 'film.FilmCategories',

	columns: [ {
		text: 'Category Id',
		width: 100,
		dataIndex: 'id'
	}, {
		text: 'Category Name',
		flex: 1,
		dataIndex: 'name'
	} ],

	dockedItems: [ {
		xtype: 'searchadddelete'
	} ]
});