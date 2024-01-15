var tempDay;
var isRed;
var isRedAmount;

function calcDaySum (input, value) {
	//auf Zahl prüfen
	if ( !isNaN( parseFloat(value) ) || value == '' ) { // ist eine Zahl od. leer
		// Style auf Weiss setzen
		input.style.backgroundColor='#FFFFFF';
		
		d = document;
		var totDiv = d.getElementById('totalDay').innerHTML;
		if( isNaN(totDiv) ) { totDiv = 0; }
		if( isNaN(value) ) { value = 0; }
		if( isNaN(tempDay) ) { tempDay = 0; }
		var totalDay = Number(totDiv) + Number(value) - Number(tempDay);
		d.getElementById('totalDay').innerHTML = totalDay;
	} else {
		// Style auf Rot setzen
		input.style.backgroundColor='#FF0000';
		isRed = true;
	} 
}

function setYellow(input) {
	input.style.backgroundColor='#FFFF00';
	tempDay = input.value;
}

function setWhite(input) {
	if ( !isRed && !isRedAmount ) {
		input.style.backgroundColor='#FFFFFF';
	}
	isRed = false;
	isRedAmount = false;
}

function checkAmount (input, value) {
	//auf Zahl prüfen
	if ( isNaN(parseFloat(value))) { // ist keine Zahl
		// Style auf Rot setzen
		if (value != "") {
			input.style.backgroundColor='#FF0000';
			isRedAmount = true;
		}
	}
}

function totalDaySum(){
	d = document;
	var totalSum = d.getElementById('totalDay').getElementsByTagName("span").item(0).firstChild.data;

	var arr = new Array();
	arr = document.getElementsByTagName('input');
	
	for(var i = 0; i < arr.length; i++)
    {
    	var element = arr[i];
    	var name = element.getAttribute("name");
    	var inputName = /(workinhour)/g; //PROPERTYNAME "workinhour" muss in Element-Name enthalten sein.
    	
    	var contains = inputName.test( name );
    	
    	if(contains){
	    	var value = element.value;
	    	if ( value = '') { value = 0; }
	   		if ( !isNaN( parseFloat(value) )){
	   			totalSum = Number(totalSum) + Number(value);
	   		}
    	}    	
	}
	
	d.getElementById('totalDay').innerHTML = totalSum;
}
