import * as autobahn from 'autobahn';

let wampSession;

const output1 = document.getElementById('output1');
const output2 = document.getElementById('output2');
document.getElementById('clearButton').addEventListener('click', () => {
	output1.innerHTML = '';
	output2.innerHTML = '';
});

const clearUserOutputButton = document.getElementById('clearUserOutputButton');
clearUserOutputButton.addEventListener('click', () => {
	document.getElementById('userOutput').innerHTML = '';
	document.getElementById('userData1').value = '';
	document.getElementById('userData2').value = '';
	document.getElementById('userData3').value = '';
	document.getElementById('userData4').value = '';
	document.getElementById('userData5').value = '';
	document.getElementById('userData6').value = '';
	document.getElementById('userData7').value = '';
	document.getElementById('userData8').value = '';
	document.getElementById('userData9').value = '';
	document.getElementById('userData10').value = '';
});

const clearDialogOutputButton = document.getElementById('clearDialogOutputButton');
clearDialogOutputButton.addEventListener('click', () => {
	document.getElementById('dialogOutput').innerHTML = '';

	document.getElementById('dialogData1').value = '';
	document.getElementById('dialogData2').value = '';
	document.getElementById('dialogData3').value = '';
	document.getElementById('dialogData4').value = '';
	document.getElementById('dialogData5').value = '';
	document.getElementById('dialogData6').value = '';
	document.getElementById('dialogData7').value = '';
	document.getElementById('dialogData8').value = '';
	document.getElementById('dialogData9').value = '';
	document.getElementById('dialogData10').value = '';
	
	document.getElementById('callData1').value = '';
	document.getElementById('callData2').value = '';
	document.getElementById('callData3').value = '';
	document.getElementById('callData4').value = '';
	document.getElementById('callData5').value = '';
	document.getElementById('callData6').value = '';
	document.getElementById('callData7').value = '';
	document.getElementById('callData8').value = '';
	document.getElementById('callData9').value = '';
	document.getElementById('callData10').value = '';
	document.getElementById('customData1').value = '';
	document.getElementById('customData2').value = '';
	document.getElementById('customData3').value = '';
	document.getElementById('customData4').value = '';
	document.getElementById('customData5').value = '';
	document.getElementById('customData6').value = '';
});

document.getElementById('submit1Button').addEventListener('click', () => handleSubmit1());
async function handleSubmit1() {
    output1.innerHTML = ''

    const kv = {};
    const form = document.getElementById('inputForm');
    for (const el of form.elements) {
        if (el.name) {
            kv[el.name] = el.value;
        }
    }

	const response = await wampSession.call('getUserData', [kv.user, kv.pass, kv.extension]);
	document.getElementById('userData1').value = response.User.UserData1;
	document.getElementById('userData2').value = response.User.UserData2;
	document.getElementById('userData3').value = response.User.UserData3;
	document.getElementById('userData4').value = response.User.UserData4;
	document.getElementById('userData5').value = response.User.reason.UserData5;
	document.getElementById('userData6').value = response.User.reason.UserData6;
	document.getElementById('userData7').value = response.User.UserData7;
	document.getElementById('userData8').value = response.User.UserData8;
	document.getElementById('userData9').value = response.User.UserData9;
	document.getElementById('userData10').value = response.User.UserData10;

	document.getElementById('userOutput').innerHTML = JSON.stringify(response);
}

document.getElementById('submit2Button').addEventListener('click', () => handleSubmit2());
async function handleSubmit2() {
    output2.innerHTML = ''

    const kv = {};
    const form = document.getElementById('inputForm');
    for (const el of form.elements) {
        if (el.name) {
            kv[el.name] = el.value;
        }
    }

	const response = await wampSession.call('getDialogData', [kv.dialog, kv.other1, kv.other2]);    
	outDialog(response);
}

function outDialog(response) {
	document.getElementById('dialogData1').value = response.Dialog.DialogData1;
	document.getElementById('dialogData2').value = response.Dialog.DialogData2;
	document.getElementById('dialogData3').value = response.Dialog.DialogData3;
	document.getElementById('dialogData4').value = response.Dialog.mediaProperties.DialogData4;
	document.getElementById('dialogData5').value = response.Dialog.mediaProperties.DialogData5;
	document.getElementById('dialogData6').value = response.DialogData6;
	document.getElementById('dialogData7').value = response.DialogData7;
	document.getElementById('dialogData8').value = response.DialogData8;
	document.getElementById('dialogData9').value = response.DialogData9;
	document.getElementById('dialogData10').value = response.DialogData10;

	const cvs = response.Dialog.mediaProperties.callvariables.CallVariable;
	
	document.getElementById('callData1').value = cvs.find(v => v.name === 'CallData1').value;
	document.getElementById('callData2').value = cvs.find(v => v.name === 'CallData2').value;
	document.getElementById('callData3').value = cvs.find(v => v.name === 'CallData3').value;
	document.getElementById('callData4').value = cvs.find(v => v.name === 'CallData4').value;
	document.getElementById('callData5').value = cvs.find(v => v.name === 'CallData5').value;
	document.getElementById('callData6').value = cvs.find(v => v.name === 'CallData6').value;
	document.getElementById('callData7').value = cvs.find(v => v.name === 'CallData7').value;
	document.getElementById('callData8').value = cvs.find(v => v.name === 'CallData8').value;
	document.getElementById('callData9').value = cvs.find(v => v.name === 'CallData9').value;
	document.getElementById('callData10').value = cvs.find(v => v.name === 'CallData10').value;
	document.getElementById('customData1').value = cvs.find(v => v.name === 'CustomData1').value;
	document.getElementById('customData2').value = cvs.find(v => v.name === 'CustomData2').value;
	document.getElementById('customData3').value = cvs.find(v => v.name === 'CustomData3').value;
	document.getElementById('customData4').value = cvs.find(v => v.name === 'CustomData4').value;
	document.getElementById('customData5').value = cvs.find(v => v.name === 'CustomData5').value;
	document.getElementById('customData6').value = cvs.find(v => v.name === 'CustomData6').value;

	document.getElementById('dialogOutput').innerHTML = JSON.stringify(response);
}

document.getElementById('fetchButton').addEventListener('click', () => handleFetch());
for (let i = 1; i <= 6; i++) {
	document.getElementById(`rpc${i}Button`).addEventListener('click', () => {
		try {
			handleRpc(i);
		} catch(e) {
			console.log(e);
		}
	});
}

async function handleFetch() {
	try {
		const response = await fetch('http://localhost:8080/method7');
		const txt = await response.text();
		output1.innerHTML += "<br>" + txt;
	} catch (e) {
		console.log('handleFetch', e);
	}
}

async function handleRpc(ix) {	
	const response = await wampSession.call(`method${ix}`);
	output1.innerHTML += "<br>" + response;	
	
	if (ix === 1) {
		document.getElementById('rpc3Button').disabled = true;
		document.getElementById('rpc4Button').disabled = true;
	}
	else if (ix === 2) {
		document.getElementById('rpc3Button').disabled = false;
		document.getElementById('rpc4Button').disabled = false;
	}
}

const button5 = document.getElementById('rpc5Button');
const button6 = document.getElementById('rpc6Button');

const connection = new autobahn.Connection({url: 'ws://localhost:8080/ws', realm: 'realm'});
connection.onopen = session => {
    wampSession = session;

    session.subscribe('event1', args => {
        output2.innerHTML += '<br>' + JSON.stringify(args[0]);
        button5.disabled = false;
		button6.disabled = false;
		
		outDialog(args[0]);

    });
    session.subscribe('event2', args => {
        output2.innerHTML += '<br>' + JSON.stringify(args[0]);
        button5.disabled = true;
		button6.disabled = true;
    });    
};

connection.open();