Ext.onReady(function(){

	var store = new Ext.data.Store({
		fields : ['id', 'profileImageUrl', 'fromUser', 'text'],
		autoLoad : false
	});		
	
	var panel = new Ext.Panel({
	    frame : true,
	    layout : 'fit',
	    title : 'Twitter Example',
	    renderTo: Ext.getBody(),
	    
	    items : new Ext.view.View({
	        store : store,
	        autoScroll : true,
	        tpl : new Ext.XTemplate(
				'<ul id="tweets">',
					'<tpl for=".">',
						'<li class="tweet">',
							'<img class="profile-image" src="{profileImageUrl}" />',
							'<h2>{fromUser}</h2>',
							'<p>{text}</p>',
						'</li>',
					'</tpl>',
				'</ul>'
			),
	        overClass : 'x-view-over',
	        itemSelector : 'li',
	        emptyText : 'No tweets to display'
	    })
	});
			
	
	eventSource = new EventSource('twittersse');
	eventSource.onmessage = function(event) {
		var feeds = event.data.toString();
		var data = JSON.parse(feeds);
		if (data) {
			for (var k = 0; k < data.length; k++) {
				store.insert(0, data[k]);
			}
		}		
	};

	
});