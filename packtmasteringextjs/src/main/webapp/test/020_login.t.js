StartTest(function(t) {
	t.diag("Sanity test, loading classes on demand and verifying they were indeed loaded.");
	t.ok(Ext, 'ExtJS is here');
	t.ok(Packt, 'Packt namespace is here');
	t.requireOk('Packt.view.Login');
	t.waitForComponent('Packt.view.Login', true, function() {
		var submitButton = Ext.ComponentQuery.query('login button#submit')[0];
		var userTextField = Ext.ComponentQuery.query('login textfield[name=user]')[0];
		var passwordTextField = Ext.ComponentQuery.query('login textfield[name=password]')[0];
		
		t.chain({
		    action  : 'type',
		    target  : userTextField, 
		    text    : ''
		}, {
		    action  : 'type',
		    target  : userTextField, 
		    text    : 'admin'
		}, {
		    action  : 'type',
		    target  : passwordTextField, 
		    text    : 'admin'
		}, {
			action: 'click',
			target: submitButton 
		});
		
		t.waitForComponent('Packt.view.MyViewport', true, function() {
			t.ok(Packt.view.MyViewport, "Packt.view.MyViewport was rendered.");
			t.done();
		});
	});
});