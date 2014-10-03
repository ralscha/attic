Ext.onReady(function(){

	var store = new Ext.data.Store({
		fields : ['id', 'profileImageUrl', 'fromUser', 'text', 'isNew', {
			name: 'createdAt',
			convert: function(v) {
				return new Date(v);
			}
		}],
		autoLoad : false
	});		
	
	var panel = new Ext.Panel({
	    frame : true,
	    layout : 'fit',
	    title : 'Twitter Example: Protocol',
	    renderTo: Ext.getBody(),
	    bodyStyle: 'background-color: #DAE6C4;',
	    
	    items : new Ext.view.View({
	        store : store,
	        autoScroll : true,
	        tpl : new Ext.XTemplate(
				'<ul id="tweets">',
					'<tpl for=".">',
						'<li class="{[values.isNew ? "tweet isnew" : "tweet"]}">',   
							'<img class="profile-image" src="{profileImageUrl}" />',
							'<h2>{fromUser}</h2>',
							'<p>{text}<br>',
							'<span class="createdAt">Created at: {createdAt:date("Y-m-d H:i:s")}</span>',
							'</p>',					
						'</li>',
					'</tpl>',
				'</ul>'
			),
	        overClass : 'x-view-over',
	        itemSelector : 'li',
	        emptyText : 'No tweets to display'
	    })
	});
			
	
	var transports = ["sse", "stream", "longpoll"];
	if (!window.location.search || window.location.search.indexOf('nows') === -1) {
		transports.unshift("ws");
	}
	
	portal.open("../twitter", {transports: transports}).on({
		open: function() {
			panel.setTitle('Twitter Example TRANSPORT: ' + this.data("transport"));
		},
		newTweets: function(tweets) {
		  store.each(function(r) {
			  r.set('isNew', false);
		  });
		  for (var t = 0; t < tweets.length; t++) {
			  tweets[t].isNew = true;
		  }
		  store.insert(0, tweets);
		    if (store.getCount() > 100) {
		    	store.remove(100, store.getCount() - 100);
		    }
		}
	});
	
});