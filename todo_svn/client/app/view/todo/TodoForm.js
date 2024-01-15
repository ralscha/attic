Ext.define('Todo.view.todo.TodoForm', {
	extend : 'Ext.form.Panel',
	requires: ['Todo.util.FileDropper'],
	defaultFocus : 'textfield[name=title]',
	reference : 'todoForm',
	defaults : {
		labelAlign : 'top',
		anchor : '100%',
		readonly : true
	},

	modelValidation : true,
	scrollable: true,
	bind : {
		disabled : '{!selectedTodo}'
	},
    
	items : [ {
		xtype : 'combobox',
		fieldLabel : 'Type',
		queryMode : 'local',
		displayField : 'value',
		valueField : 'value',
		store : 'types',
		bind : {
			value : '{selectedTodo.type}'
		},
		forceSelection : true,
		editable : false
	}, {
        xtype: 'lantextfield',
        inputType : 'textfield',
	    fieldLabel : 'Title',
	    languages : ['de', 'fr', 'gb'],  
	    required: ['de'],
	    cfg : {
			maxLength : 50
		},
		bind : {
			json : '{selectedTodo.title}'
		}
	}, {
		xtype : 'datefield',
		bind : '{selectedTodo.due}',
		fieldLabel : 'Due',
		name : 'due',
		format : 'Y-m-d',
		minValue : new Date()
	}, {
        xtype: 'lantextfield',
        inputType : 'textarea',
	    fieldLabel : 'Description',
	    languages : ['de', 'fr', 'gb'],  
	    required: ['de'],
	    cfg : {
			maxLength : 255
		},
		bind : {
			json : '{selectedTodo.description}'
		}
	}, {
		xtype : 'panel',
		layout : 'vbox',
		items : [ {
	        xtype : 'label',
	        text : 'Image Preview:',
	        bind : {
	    		hidden : '{!selectedTodo}'
	    	},
	        margin : '5 0 5 0'
		}, {
			xtype : 'image',
			reference : 'thumbnail',
			plugins: [{
		        ptype: 'filedropper',
		        overCls: 'fileDropperOnMoveOver',
		        callback: function(files) {
					if (files[0].type.startsWith('image/')) {
			            var me = this;
			    		var fileReader = new FileReader();
			    		fileReader.fileName = files[0].name;
			    		
			    		fileReader.onload = function(fileLoadedEvent) {
			    			var controller = me.getCmp().up().up().up().getController();
			    			var imageData = fileLoadedEvent.target.result;
			    			var fileName = fileLoadedEvent.target.fileName;
			    			controller.lookup('thumbnail').setSrc(imageData);
			    			controller.getViewModel().get('selectedTodo').set('thumbnail64', imageData);
			    			controller.getViewModel().get('selectedTodo').set('imageName', fileName);
			    		};   		
			    		fileReader.readAsDataURL(files[0]);	
		    		}
		    	}
		    }],
			listeners : {
				el : {
					click : 'onImageClick'
				}
			},
			bind : {
				hidden : '{!selectedTodo}',
				src : 'data:image/jpg;base64,{selectedTodo.thumbnail64}'
			},
			height : 200,
			alt : 'image'
		} ]
	}, {
		xtype: 'splitter',
		minHeight : 40
	}, {
        xtype: 'qrpanel',
        bind : {
			hidden : '{!selectedTodo}',
        	textToEncode: '{selectedTodo.title}'
        },
        height : 250
	} ],

	dockedItems : [ {
		xtype : 'toolbar',
		dock : 'bottom',
		items : [ {
			text : 'Save',
			iconCls : 'x-fa fa-floppy-o',
			handler : 'onSaveClick',
			formBind : true
		}, {
			text : 'Cancel',
			iconCls : 'x-fa fa-ban',
			handler : 'onCancelClick'
		} ]
	} ]

});