const UUID = (function() {
  var self = {};
  var lut = []; for (var i=0; i<256; i++) { lut[i] = (i<16?'0':'')+(i).toString(16); }
  self.generate = function() {
    var d0 = Math.random()*0xffffffff|0;
    var d1 = Math.random()*0xffffffff|0;
    var d2 = Math.random()*0xffffffff|0;
    var d3 = Math.random()*0xffffffff|0;
    return lut[d0&0xff]+lut[d0>>8&0xff]+lut[d0>>16&0xff]+lut[d0>>24&0xff]+'-'+
      lut[d1&0xff]+lut[d1>>8&0xff]+'-'+lut[d1>>16&0x0f|0x40]+lut[d1>>24&0xff]+'-'+
      lut[d2&0x3f|0x80]+lut[d2>>8&0xff]+'-'+lut[d2>>16&0xff]+lut[d2>>24&0xff]+
      lut[d3&0xff]+lut[d3>>8&0xff]+lut[d3>>16&0xff]+lut[d3>>24&0xff];
  }
  return self;
})();

const uuid = UUID.generate()
const eventSource = new EventSource(`register/${uuid}`);

eventSource.addEventListener('message', response => {
    const data = JSON.parse(response.data);
    const std = document.getElementById(`ries_${data.ries}_${data.spieler}`);
    if (data.nr) {
    	std.classList.add("nr");
    }
    else {
    	std.classList.remove("nr");
    }
    std.textContent = data.punkt;
    
    const td = document.getElementById(`spieler_${data.spieler}`).querySelectorAll("td");
    let total = getNumber(td[5].textContent) + getNumber(td[6].textContent) + 
    getNumber(td[7].textContent) + getNumber(td[8].textContent);
    td[9].textContent = total;
    
    
    this.calcTotal();
    
}, false);

eventSource.addEventListener('clear', response=> {
	this.clear();
}, false);

function clear() {
    const spielerBody = document.getElementById("spielerBody");
    const sbTrs = spielerBody.querySelectorAll("tr");
    
    for (let sbTr of sbTrs) {
    	const sbTds = sbTr.querySelectorAll("td");
    	
        for (let col = 5; col <= 9; col++) {
	    	sbTds[col].textContent = '';
	    	sbTds[col].classList.remove("nr");
        }
    }
    
    this.calcTotal();
}

function calcTotal() {
    const spielerBody = document.getElementById("spielerBody");
    const sbTrs = spielerBody.querySelectorAll("tr");
    
    for (let col = 5; col <= 9; col++) {
	    let colTotal = 0;
	    for (let sbTr of sbTrs) {
	    	const sbTds = sbTr.querySelectorAll("td");
	    	colTotal += getNumber(sbTds[col].textContent);
	    }
	    
	    document.getElementById(`col_${col}_total`).textContent = colTotal;
    }
}

function getNumber(str) {
	if (str) {
		return parseInt(str);
	}
	return 0;
}

const spielerTemplate = document.getElementById('spielerTemplate');

function insertData(data) {
	for (let regular of data[0]) {
		const clone = document.importNode(spielerTemplate.content, true);
		clone.children[0].setAttribute("id", "spieler_" + regular.id);

		const td = clone.querySelectorAll("td");
		td[0].textContent = regular.position;
		td[1].textContent = regular.lizenzNr;
		td[2].textContent = regular.nachname;
		td[3].textContent = regular.vorname;
		td[4].textContent = regular.jahrgang;
		td[5].textContent = regular.ries[0];
		td[5].setAttribute("id", "ries_0_" + regular.id);
		td[6].textContent = regular.ries[1];
		td[6].setAttribute("id", "ries_1_" + regular.id);
		td[7].textContent = regular.ries[2];
		td[7].setAttribute("id", "ries_2_" + regular.id);
		td[8].textContent = regular.ries[3];		
		td[8].setAttribute("id", "ries_3_" + regular.id);
		td[9].setAttribute("id", "ries_total_" + regular.id);
		document.getElementById('spielerBody').appendChild(clone);
	}
	
	for (let ueber of data[1]) {
		const clone = document.importNode(spielerTemplate.content, true);
		clone.children[0].setAttribute("id", "spieler_" + ueber.id);
		
		const td = clone.querySelectorAll("td");
		td[0].textContent = ueber.position;
		td[1].textContent = ueber.lizenzNr;
		td[2].textContent = ueber.nachname;
		td[3].textContent = ueber.vorname;
		td[4].textContent = ueber.jahrgang;
		td[5].textContent = ueber.ries[0];
		td[5].setAttribute("id", "ries_0_" + ueber.id);
		td[6].textContent = ueber.ries[1];
		td[6].setAttribute("id", "ries_1_" + ueber.id);
		td[7].textContent = ueber.ries[2];
		td[7].setAttribute("id", "ries_2_" + ueber.id);
		td[8].textContent = ueber.ries[3];		
		td[8].setAttribute("id", "ries_3_" + ueber.id);
		td[9].setAttribute("id", "ries_total_" + ueber.id);
		
		document.getElementById('ueberBody').appendChild(clone);
	}
}

fetch('spieler')
   .then(response => response.json())
   .then(data => insertData(data))
   .catch(err => console.log(err));

