Ext.define('Changelog.view.customer.Edit', {
	extend: 'Ext.window.Window',
	stateId: 'customerEdit',
	requires: [ 'Changelog.ux.form.field.ClearCombo' ],
	controller: 'Changelog.controller.CustomerEditController',
	title: i18n.customer_edit,
	layout: 'fit',
	autoShow: true,
	resizable: true,
	width: 650,
	modal: true,
	constrainHeader: true,

	icon: app_context_path + '/resources/images/customer_edit.png',

	getForm: function() {
		return this.getComponent('customerEditForm').getForm();
	},

	initComponent: function() {
		var me = this;
		me.items = [ {
			xtype: 'form',
			itemId: 'customerEditForm',
			padding: 5,
			bodyPadding: 10,
			bodyBorder: true,

			defaultType: 'textfield',
			defaults: {
				anchor: '100%'
			},

			fieldDefaults: {
				msgTarget: 'side',
				labelWidth: 120
			},
			
			items: [ {
				xtype: 'hidden',
				name: 'id'
			},{
				name: 'shortName',
				itemId: 'shortName',
				fieldLabel: i18n.customer_shortname,
				allowBlank: false
			}, {
				name: 'longName',
				fieldLabel: i18n.customer_longname,
				allowBlank: false
			}, {
				name: 'responsible',
				fieldLabel: i18n.customer_responsible,
				allowBlank: true
			}, {
				xtype: 'radiogroup',
	            fieldLabel: i18n.customer_installation,	            
	            items: [{
	              boxLabel: i18n.customer_installation_WAR,
	              name: 'installation',
	              inputValue: 'WAR'
	            }, {
	              boxLabel: i18n.customer_installation_JAR,  
	              name: 'installation',
                  inputValue: 'JAR'
	            }]
	        }, {
				name: 'ftpUser',
				fieldLabel: i18n.customer_ftpuser,
				allowBlank: true
			}, {
				name: 'testUrl',
				fieldLabel: i18n.customer_testurl,
				allowBlank: true
			}, {
				xtype: 'tabpanel',
				plain:true,
				items: [ {
					xtype: 'panel',
					title: i18n.customer_ansprechpersonen,
					layout: 'anchor',					
					style: {
					    borderColor: '#ADD2ED',
					    borderStyle: 'solid'
					},
					bodyPadding: 10,
					defaults: {
						anchor: '100%'
					},					
					items: [{
						name: 'ansprechperson',
						xtype: 'textareafield',
						rows: 10,
						fieldLabel: i18n.customer_ansprechperson,
						allowBlank: true
					}, {
						name: 'applikationsmgmt',
						xtype: 'textareafield',
						rows: 10,
						fieldLabel: i18n.customer_applikationsmgmt,
						allowBlank: true
					}]
				}, {
					xtype: 'panel',
					title: 'Info',
					layout: 'anchor',					
					style: {
					    borderColor: '#ADD2ED',
					    borderStyle: 'solid'
					},
					bodyPadding: 10,
					defaults: {
						anchor: '100%'
					},
					items: [ {
						allowBlank: true,
						xtype: 'fieldcontainer',
						fieldLabel: i18n.customer_integration,
						defaultType: 'radiofield',

						layout: 'hbox',
						items: [ {
							boxLabel: i18n.yes,
							name: 'integration',
							inputValue: '1',
							width: 50
						}, {
							boxLabel: i18n.no,
							name: 'integration',
							inputValue: '0',
							width: 50
						} ]

					}, {
						name: 'schnittstellen',
						xtype: 'textareafield',
						rows: 3,
						fieldLabel: i18n.customer_schnittstellen,
						allowBlank: true
					}, {
						allowBlank: true,
						xtype: 'fieldcontainer',
						fieldLabel: i18n.customer_standalone,
						defaultType: 'radiofield',

						layout: 'hbox',
						items: [ {
							boxLabel: i18n.yes,
							name: 'standalone',
							inputValue: '1',
							width: 50
						}, {
							boxLabel: i18n.no,
							name: 'standalone',
							inputValue: '0',
							width: 50
						} ]
					}, {
						name: 'url',
						xtype: 'textfield',
						fieldLabel: i18n.customer_url,
						allowBlank: true
					}, {
						name: 'noVertraege',
						xtype: 'textfield',
						fieldLabel: i18n.customer_noVertraege,
						allowBlank: true
					}, {
						name: 'bemerkungen',
						xtype: 'textareafield',
						rows: 6,
						fieldLabel: i18n.customer_bemerkungen,
						allowBlank: true
					} ]
				}, {
					xtype: 'panel',
					title: i18n.customer_systeminfo,
					layout: 'anchor',
					bodyPadding: 10,
					style: {
					    borderColor: '#ADD2ED',
					    borderStyle: 'solid'
					},
					defaults: {
						anchor: '100%'
					},
					items: [ {
						name: 'db',
						xtype: 'textfield',
						fieldLabel: i18n.customer_db,
						allowBlank: true
					}, {
						name: 'tomcat',
						xtype: 'textfield',
						fieldLabel: i18n.customer_tomcat,
						allowBlank: true
					}, {
						name: 'java',
						xtype: 'textfield',
						fieldLabel: i18n.customer_java,
						allowBlank: true
					}, {
						name: 'os',
						xtype: 'textfield',
						fieldLabel: i18n.customer_os,
						allowBlank: true
					}, {
						allowBlank: true,
						xtype: 'fieldcontainer',
						fieldLabel: i18n.customer_iis,
						defaultType: 'radiofield',

						layout: 'hbox',
						items: [ {
							boxLabel: i18n.yes,
							name: 'iis',
							inputValue: '1',
							width: 50
						}, {
							boxLabel: i18n.no,
							name: 'iis',
							inputValue: '0',
							width: 50
						} ]
					}, {
						name: 'browser',
						xtype: 'clearcombo',
						fieldLabel: i18n.customer_browser,
						allowBlank: true,
						store: Ext.create('Ext.data.ArrayStore', {
							fields: [ 'id', 'name' ],
							data: [ [ 'ie6', 'IE 6' ], 
							        [ 'ie7', 'IE 7' ],
									[ 'ie8', 'IE 8' ],
									[ 'ie9', 'IE 9' ],
									[ 'ie10', 'IE 10' ],
									[ 'ie11', 'IE 11' ],
									[ 'ff', 'Firefox' ],
									[ 'chrome', 'Chrome' ],
									[ 'safari', 'Safari' ]
									]
						}),
						valueField: 'id',
						displayField: 'name',
						queryMode: 'local',
						forceSelection: true					
					},{
						name: 'vpn',
						xtype: 'textareafield',
						rows: 3,
						fieldLabel: i18n.customer_vpn,
						allowBlank: true
					} ]
				}, {
					xtype: 'panel',
					itemId: 'features',
					name: 'features',
					title: i18n.customer_features,
					layout: 'anchor',
					bodyPadding: 10,
					style: {
					    borderColor: '#ADD2ED',
					    borderStyle: 'solid'
					},
					defaults: {
						anchor: '100%'
					},
					items: [ {
						xtype: 'checkbox',
						name: 'alogin',
						inputValue: true,
						boxLabel: i18n.tool_features_autologin,
						submitValue: false,
						listeners: {
							change: {
								fn: function() {
									this.next().setValue(this.getValue());
								}		
							}
						}
					}, {
						xtype: 'hidden',
						name: 'alogin'
					}, {
						xtype: 'checkbox',
						name: 'langdeCH',
						inputValue: true,
						boxLabel: i18n.tool_features_langDE,
						submitValue: false,
						listeners: {
							change: {
								fn: function() {
									this.next().setValue(this.getValue());
								}		
							}
						}

					}, {
						xtype: 'hidden',
						name: 'langdeCH'
					}, {
						xtype: 'checkbox',
						name: 'langfr',
						inputValue: true,
						boxLabel: i18n.tool_features_langFR,
						submitValue: false,
						listeners: {
							change: {
								fn: function() {
									this.next().setValue(this.getValue());
								}		
							}
						}
					}, {
						xtype: 'hidden',
						name: 'langfr'
					}, {
						xtype: 'checkbox',
						name: 'langen',
						inputValue: true,
						boxLabel: i18n.tool_features_langEN,
						submitValue: false,
						listeners: {
							change: {
								fn: function() {
									this.next().setValue(this.getValue());
								}		
							}
						}
					}, {
						xtype: 'hidden',
						name: 'langen'
					}, {
						xtype: 'checkbox',
						name: 'ct',
						inputValue: true,
						boxLabel: i18n.tool_features_ct,
						submitValue: false,
						listeners: {
							change: {
								fn: function() {
									this.next().setValue(this.getValue());
								}		
							}
						}
					}, {
						xtype: 'hidden',
						name: 'ct'
					}, {
						xtype: 'checkbox',
						name: 'lis',
						inputValue: true,
						boxLabel: i18n.tool_features_lis,
						submitValue: false,
						listeners: {
							change: {
								fn: function() {
									this.next().setValue(this.getValue());
								}		
							}
						}
					}, {
						xtype: 'hidden',
						name: 'lis'
					}, {
						xtype: 'checkbox',
						name: 'lms',
						inputValue: true,
						boxLabel: i18n.tool_features_lms,
						submitValue: false,
						listeners: {
							change: {
								fn: function() {
									this.next().setValue(this.getValue());
								}		
							}
						}
					}, {
						xtype: 'hidden',
						name: 'lms'
					}, {
						xtype: 'checkbox',
						name: 'lic',
						inputValue: true,
						boxLabel: i18n.tool_features_lic,
						submitValue: false,
						listeners: {
							change: {
								fn: function() {
									this.next().setValue(this.getValue());
								}		
							}
						}
					}, {
						xtype: 'hidden',
						name: 'lic'
					}, {
						xtype: 'checkbox',
						name: 'clm',
						inputValue: true,
						boxLabel: i18n.tool_features_clm,
						submitValue: false,
						listeners: {
							change: {
								fn: function() {
									this.next().setValue(this.getValue());
								}		
							}
						}
					}, {
						xtype: 'hidden',
						name: 'clm'
					}, {
						xtype: 'checkbox',
						name: 'inv',
						inputValue: true,
						boxLabel: i18n.tool_features_inv,
						submitValue: false,
						listeners: {
							change: {
								fn: function() {
									this.next().setValue(this.getValue());
								}		
							}
						}
					}, {
						xtype: 'hidden',
						name: 'inv'
					}, {
						xtype: 'checkbox',
						name: 'par',
						inputValue: true,
						boxLabel: i18n.tool_features_par,
						submitValue: false,
						listeners: {
							change: {
								fn: function() {
									this.next().setValue(this.getValue());
								}		
							}
						}
					}, {
						xtype: 'hidden',
						name: 'par'
					}, {
						xtype: 'checkbox',
						name: 'vertragAutoNr',
						inputValue: true,
						boxLabel: i18n.tool_features_autoNo,
						submitValue: false,
						listeners: {
							change: {
								fn: function() {
									this.next().setValue(this.getValue());
								}		
							}
						}
					}, {
						xtype: 'hidden',
						name: 'vertragAutoNr'
					}, {
						xtype: 'checkbox',
						name: 'gen',
						inputValue: true,
						boxLabel: i18n.tool_features_gen,
						submitValue: false,
						listeners: {
							change: {
								fn: function() {
									this.next().setValue(this.getValue());
								}		
							}
						}
					}, {
						xtype: 'hidden',
						name: 'gen'
					}, {
						xtype: 'displayfield',
						itemId: 'featuresKey',
						maxWidth: 600,
						style: {
				            wordWrap: 'break-word'
				        },		
						fieldLabel: i18n.tool_features_key,
						margin: '20 0 10 0'
					}, {
						xtype: 'fieldcontainer',
						fieldLabel: '&nbsp;',
		                labelSeparator: '',
						layout: 'hbox',
						items: [ {
							xtype: 'button',
							text: i18n.tool_features_clipboardcopy,
							itemId: 'copyButton',
							listeners: {
								boxready: {
									fn: function() {
										var clip = new ZeroClipboard(this.getEl().dom);
										clip.on('dataRequested', function(client, args) {
											var key = me.down('displayfield','featuresKey').getValue();
											clip.setText(key);		
										});
										clip.on('complete', function(client, args) {
											Changelog.ux.window.Notification.info(i18n.successful, i18n.tool_features_clipboardcopied);
										});
									}		
								}
							}
						} ]
					} ]
				}, {
					xtype: 'panel',
					title: i18n.documents_documents,
					layout: 'anchor',
					bodyPadding: 10,
					style: {
					    borderColor: '#ADD2ED',
					    borderStyle: 'solid'
					},
					defaults: {
						anchor: '100%'
					},
					items: [ {
						xtype: 'gridpanel',
						itemId: 'documentsGrid',
						store: Ext.create('Changelog.store.Documents'),
						autoScroll:'true',
						maxHeight: 400,
						columns: [ {
							dataIndex: 'fileName',
							flex: 2,
							text: i18n.documents_document
						}, {
							dataIndex: 'date',
							flex: 1,
							text: i18n.documents_date,
							renderer: Ext.util.Format.dateRenderer('d.m.Y H:i:s')
						}, {
							dataIndex: 'size',
							flex: 1,
							text: i18n.documents_size,
							renderer: Ext.util.Format.fileSize
						}
				      ],
				      dockedItems: [ {
				    	  	xtype: 'toolbar',
				    	  	dock: 'top',
							items: [ {
								xtype : 'button',
								tooltip: i18n.documents_delete,
								disabled: true,
								itemId: 'deleteButton',
								icon: app_context_path + '/resources/images/document_delete.png'
							},{
								xtype : 'button',
								tooltip: i18n.documents_download,
								disabled: true,
								itemId: 'downloadButton',
								icon: app_context_path + '/resources/images/document_down.png'
							}, {
						        xtype: 'tbfill'
					        }, {
						        xtype: 'filefield',
						        name: 'file',
							    buttonOnly: true,
						        buttonText: i18n.documents_select,
								onChange: function() {
						            var form = this.up('form').getForm();
					                form.submit({
					                	clientValidation: false,
					                    url: 'customerFileUpload',
					                    headers: {
					                    	'Content-Type' : 'multipart/form-data; charset=UTF-8'
					                    },
					                    waitMsg: i18n.documents_uploading,
					                    success: function(fp, o) {
					                    	Changelog.ux.window.Notification.info(i18n.successful, i18n.documents_upload_success);
					                    	me.down('gridpanel', 'documentsGrid').store.reload();
					                    }
					                });
						        }
							} ]
				      	} ]		
					} ]
				} ]
			} ],

			buttons: [ {
				xtype: 'button',
				text: i18n.save,
				action: 'save',
				icon: app_context_path + '/resources/images/save.png',
				disabled: true,
				formBind: true,
				handler: function() {
					me.controller.updateCustomer(me);
				}
			}, {
				text: i18n.cancel,
				scope: me,
				handler: me.close,
				icon: app_context_path + '/resources/images/cancel.png'
			} ]
		} ];

		me.callParent(arguments);
	}
});