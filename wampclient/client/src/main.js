import * as autobahn from 'autobahn';

let wampSession;
const output = document.getElementById('output');
const outputPubSub = document.getElementById('outputPubSub');
document.getElementById('clearButton').addEventListener('click', () => {
    output.innerHTML = '';
    outputPubSub.innerHTML = '';
});
document.getElementById('submitButton').addEventListener('click', () => handleSubmit());

function handleSubmit() {
    output.innerHTML = ''

    const kv = {};
    const form = document.getElementById('inputForm');
    for (const el of form.elements) {
        if (el.name) {
            kv[el.name] = el.value;
        }
    }

    wampSession.call('getMyData', [kv.user, kv.pass, kv.extension]);    
}

const connection = new autobahn.Connection({url: 'ws://127.0.0.1:8080/ws', realm: 'realm'});
connection.onopen = session => {
    wampSession = session;

    session.subscribe('myData', args => {
        output.innerHTML = JSON.stringify(args[0]);
    });

    session.subscribe('location', args => {
        outputPubSub.innerHTML += JSON.stringify(args[0]);
    });
};

connection.open();

/*
connection.onopen = function (session) {

   // 1) subscribe to a topic
   session.subscribe('com.myapp.hello', args => console.log("Event:", args[0]));

   // 2) publish an event
   session.publish('com.myapp.hello', ['Hello, world!']);

   // 3) register a procedure for remoting
   session.register('com.myapp.add2', args => args[0] + args[1]);

   // 4) call a remote procedure
   session.call('com.myapp.add2', [2, 3]).then(res => console.log("Result:", res));
};
connection.open();
*/