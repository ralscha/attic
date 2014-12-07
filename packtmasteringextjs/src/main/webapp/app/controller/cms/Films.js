Ext.define('Packt.controller.cms.Films', {
	extend: 'Ext.app.Controller',

	requires: [ 'Packt.util.Alert', 'Packt.util.Util', 'Ext.ux.IFrame', 'Packt.ux.grid.Printer', 'Packt.model.film.Film',
	            'Packt.view.film.Films', 'Packt.view.film.FilmCategories', 'Packt.view.film.FilmActors',
	            'Packt.store.film.Films', 'Packt.store.film.Ratings', 'Packt.store.film.FilmCategories', 'Packt.store.film.FilmActors', 'Packt.store.film.SearchActors'],

	views: [ 'film.Films', 'film.FilmCategories', 'film.FilmActors' ],

	stores: [ 'film.Films', 'film.Ratings', 'film.FilmCategories', 'film.FilmActors', 'film.SearchActors' ],

	refs: [ {
		ref: 'filmActors',
		selector: 'filmactors'
	}, {
		ref: 'filmCategories',
		selector: 'filmcategories'
	}, {
		ref: 'filmsGrid',
		selector: 'filmsgrid'
	} ],

	init: function(application) {
		this.control({
			// ---GRID---
			"filmsgrid": {
				render: this.render
			},
			"filmsgrid button#add": {
				click: this.onButtonClickAdd
			},
			"filmsgrid button#edit": {
				click: this.onButtonClickEdit
			},
			"filmsgrid button#delete": {
				click: this.onButtonClickDelete
			},
			"filmwindow button#save": {
				click: this.onButtonClickSave
			},
			"filmwindow button#cancel": {
				click: this.onButtonClickCancel
			},

			"filmsgrid button#print": {
				click: this.onButtonClickPrint
			},
			"filmsgrid button#pdf": {
				click: this.onButtonClickPDF
			},
			"filmsgrid button#excel": {
				click: this.onButtonClickExcel
			},

			// ---INNER GRIDS---
			"filmcategories button#add": {
				click: this.onButtonClickAddCategory
			},
			"filmcategories button#delete": {
				click: this.onButtonClickDeleteCategory
			},
			"filmactors button#add": {
				click: this.onButtonClickAddActor
			},
			"filmactors button#delete": {
				click: this.onButtonClickDeleteActor
			},

			// ---SEARCH WINDOWS---
			"searchactor cancelclearadd button#cancel": {
				click: this.onButtonClickSearchActorCancel
			},			
			"searchactor cancelclearadd button#clear": {
				click: this.onButtonClickSearchActorClear
			},
			"searchactor cancelclearadd button#save": {
				click: this.onButtonClickSearchActorSave
			},

			"searchcategory cancelclearadd button#cancel": {
				click: this.onButtonClickSearchCategoryCancel
			},
			"searchcategory cancelclearadd button#clear": {
				click: this.onButtonClickSearchCategoryClear
			},
			"searchcategory cancelclearadd button#save": {
				click: this.onButtonClickSearchCategorySave
			}
		});

		this.listen({
			store: {
				'#films': {
					write: this.onStoreSync
				}
			}
		});
	},

	onStoreSync: function(store, operation, options) {
		Packt.util.Alert.msg('Success!', 'Your changes have been saved.');
	},

	render: function(component, options) {
		Ext.create('Packt.store.staticData.Languages').load({
			scope: this,
			callback: function() {
				component.getStore().load();
			}
		});
	},

	onButtonClickAdd: function(button, e, options) {
		var editWindow = Ext.create('Packt.view.film.FilmWindow');
		editWindow.setTitle('New Film');
		editWindow.setIconCls('film_add');
		
		editWindow.down('form filmcategories').getStore().loadData([],false);
		editWindow.down('form filmactors').getStore().loadData([],false);
		
		editWindow.down('form').loadRecord(Ext.create('Packt.model.film.Film', {releaseYear: null, languageId: null, originalLanguageId: null, rentalDuration: null, length: null,
			replacementCost: null, rentalRate: null}));
		
		editWindow.show();
	},

	onButtonClickEdit: function(button, e, options) {

		var grid = button.up('filmsgrid'), record = grid.getSelectionModel().getSelection();

		if (record[0]) {

			var editWindow = Ext.create('Packt.view.film.FilmWindow');

			var form = editWindow.down('form');

			var values = {};

			Ext.each(record[0].get('specialFeatures').split(','), function(feat) {
				if (feat === 'Trailers') {
					values.trailers = 'Trailers';
				} else if (feat === 'Commentaries') {
					values.commentaries = 'Commentaries';
				} else if (feat === 'Deleted Scenes') {
					values.deleted = 'Deleted Scenes';
				} else if (feat === 'Behind the Scenes') {
					values.behind = 'Behind the Scenes';
				}
			});

			var filmCategories = editWindow.down('form filmcategories');
			filmCategories.getStore().load({
				params: {
					filmId: record[0].get('id')
				}
			});

			var filmActors = editWindow.down('form filmactors');
			filmActors.getStore().load({
				params: {
					filmId: record[0].get('id')
				},
				callback: function() {
					form.loadRecord(record[0]);

					form.getForm().setValues(values);

					editWindow.setTitle(record[0].get('title'));
					editWindow.setIconCls('film_edit');
					editWindow.show();
				}
			});

		}
	},

	onButtonClickAddCategory: function(button, e, options) {
		var win = Ext.create('Packt.view.film.SearchCategory');
		win.show();
	},

	onButtonClickAddActor: function(button, e, options) {
		var win = Ext.create('Packt.view.film.SearchActor');
		win.show();
	},

	onButtonClickSearchActorCancel: function(button, e, options) {
		var searchWindow = button.up('searchactor');
		searchWindow.close();
	},
	
	onButtonClickSearchActorClear: function(button, e, options) {
		button.up('searchactor').down('combo').clearValue();
	},

	onButtonClickSearchActorSave: function(button, e, options) {

		var searchWindow = button.up('searchactor');
		var value = searchWindow.down('combo').getValue();

		if (this.getFilmActors().getStore().findExact('id', value) === -1) {
			Packt.model.staticData.Actor.load(value, {
				scope: this,
				success: function(model, operation) {
					if (model) {
						model.set('last_update', new Date());
						this.getFilmActors().getStore().add(model);
					}
				},
				callback: function() {
					searchWindow.close();
				}
			});
		} else {
			searchWindow.close();
		}

	},

	onButtonClickSearchCategoryCancel: function(button, e, options) {
		button.up('searchcategory').close();
	},
	
	onButtonClickSearchCategoryClear: function(button, e, options) {
		button.up('searchcategory').down('multiselect').clearValue();
	},

	onButtonClickSearchCategorySave: function(button, e, options) {
		var searchWindow = button.up('searchcategory');
		var values = searchWindow.down('multiselect').getValue();
		var store = Ext.getStore('categories');
		var filmCategoriesStore = this.getFilmCategories().getStore();

		Ext.each(values, function(value) {

			var model = store.findRecord('id', value);

			if (model) {
				model.set('last_update', new Date());
				if (filmCategoriesStore.findExact('id', model.getId()) === -1) {
					filmCategoriesStore.add(model);
				}
			}
		});

		searchWindow.close();
	},

	onButtonClickDeleteCategory: function(button, e, options) {
		var grid = this.getFilmCategories(), record = grid.getSelectionModel().getSelection(), store = grid.getStore();

		if (record[0]) {
			store.remove(record[0]);
		}
	},

	onButtonClickDeleteActor: function(button, e, options) {
		var grid = this.getFilmActors(), record = grid.getSelectionModel().getSelection(), store = grid.getStore();

		if (record[0]) {
			store.remove(record[0]);
		}
	},

	onButtonClickDelete: function(button, e, options) {
		var grid = this.getFilmsGrid(), record = grid.getSelectionModel().getSelection(), store = grid.getStore();

		if (record[0]) {

			Ext.Msg.show({
				title: 'Delete?',
				msg: 'Are you sure you want to delete?',
				buttons: Ext.Msg.YESNO,
				icon: Ext.Msg.QUESTION,
				fn: function(buttonId) {
					if (buttonId === 'yes') {

						store.remove(record[0]);
						store.sync({
							success: function() {
								Packt.util.Alert.msg('Success!', 'Film deleted.');
							},
							failure: function(records, operation) {
								store.rejectChanges();
								Packt.util.Util.showErrorMsg('Film not deleted');
							}
						});
					}
				}
			});
		}
	},

	onButtonClickPrint: function(button, e, options) {
		Packt.ux.grid.Printer.stylesheetPath = 'resources/gridPrinterCss/print.css';
		Packt.ux.grid.Printer.printAutomatically = false;
		Packt.ux.grid.Printer.print(button.up('filmsgrid'));
	},

	onButtonClickPDF: function(button, e, options) {
		var mainPanel = Ext.ComponentQuery.query('mainpanel')[0];

		var newTab = mainPanel.add({
			xtype: 'panel',
			closable: true,
			iconCls: 'pdf',
			title: 'Films PDF',
			layout: 'fit',
			html: 'loading PDF...',
			items: [ {
				xtype: 'uxiframe',
				src: 'exportFilms.pdf'
			} ]
		});

		mainPanel.setActiveTab(newTab);
	},

	onButtonClickExcel: function(button, e, options) {
		window.open('exportFilms.xlsx');
	},

	onButtonClickCancel: function(button, e, options) {
		var win = button.up('filmwindow');
		win.close();
	},

	onButtonClickSave: function(button, e, options) {
		var win = button.up('filmwindow');
		var form = win.down('form'), actorsTab = form.down('panel#filmcategories'), categoriesTab = form.down('panel#filmactors'), actors = this.getFilmActors()
				.getStore(), categories = this.getFilmCategories().getStore(), store = this.getFilmsGrid().getStore();
		var valid = true, values = form.getValues();

		if (actors.count() === 0) {
			actorsTab.setIconCls('x-form-invalid-icon');
			actorsTab.tab.setTooltip('A film must have at least one Category.');
			valid = false;
		}

		if (categories.count() === 0) {
			categoriesTab.setIconCls('x-form-invalid-icon');
			categoriesTab.tab.setTooltip('A film must have at least one Actor.');
			valid = false;
		}

		if (valid && form.getForm().isValid()) {

			form.getForm().updateRecord();
			var record = form.getForm().getRecord();

			// set special features
			var special = [];
			if (values.trailers) {
				special.push(values.trailers);
			}
			if (values.commentaries) {
				special.push(values.commentaries);
			}
			if (values.deleted) {
				special.push(values.deleted);
			}
			if (values.behind) {
				special.push(values.behind);
			}
			
			record.set('specialFeatures', special.toString());
			
			
			var actorIds = [];
			var categoryIds = [];
			this.getFilmActors().getStore().each(function(record) {
				actorIds.push(record.getId());
			});
			
			this.getFilmCategories().getStore().each(function(record) {
				categoryIds.push(record.getId());
			});
			
			record.set('filmActorIds', actorIds);
			record.set('filmCategoryIds', categoryIds);
			
			if (!record.dirty) {
				win.close();
				return;
			}

			if (record.phantom) {
				store.rejectChanges();
				store.add(record);
			}

			record.set('lastUpdate', new Date());

			store.sync({
				success: function(records, operation) {
					Packt.util.Alert.msg('Success!', 'Film saved.');
					// store.load();
					win.close();
				},
				failure: function(records, operation) {
					Packt.util.Util.showErrorMsg('Film not saved');
					store.rejectChanges();
				}
			});

		}
	}
});