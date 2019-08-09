Ext.define('Golb.Util', {
	singleton: true,
	requires: [ 'Ext.window.Toast' ],

	getCsrfToken: function() {
		return Ext.Ajax.request({
			url: serverUrl + 'csrf',
			method: 'GET'
		}).then(function(r) {
			var csrfToken = JSON.parse(r.responseText);
			Ext.Ajax.setDefaultHeaders({
				'X-CSRF-TOKEN': csrfToken.token
			});
		});
	},

	successToast: function(msg) {
		Ext.toast({
			html: msg,
			title: i18n.successful,
			align: 'br',
			shadow: true,
			width: 200,
			height: 100,
			paddingX: 20,
			paddingY: 20,			
			slideInDuration: 100,
			hideDuration: 100,
			bodyStyle: {
				background: '#90b962',
				color: 'white',
				textAlign: 'center',
				fontWeight: 'bold'
			}
		});
	},

	errorToast: function(msg) {
		Ext.toast({
			html: msg,
			title: i18n.error,
			align: 'br',
			shadow: true,
			width: 200,
			height: 100,
			paddingX: 20,
			paddingY: 20,
			slideInDuration: 100,
			hideDuration: 100,
			bodyStyle: {
				background: '#d24352',
				color: 'white',
				textAlign: 'center',
				fontWeight: 'bold'
			}
		});
	},

	underline: function(str, c) {
		var pos = str.indexOf(c);
		if (pos !== -1) {
			return str.substring(0, pos) + '<u>' + c + '</u>' + str.substring(pos + 1);
		}
		return str;
	},

	markInvalidFields: function(form, validations) {
		validations.forEach(function(validation) {
			var field = form.findField(validation.field);
			if (field) {
				field.markInvalid(validation.messages);
			}
		});
	},

	b: /^(b|B)$/,
	si: {
		bits: [ "b", "kb", "Mb", "Gb", "Tb", "Pb", "Eb", "Zb", "Yb" ],
		bytes: [ "B", "kB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB" ]
	},

	filesize: function(arg) {
		var descriptor = arguments.length <= 1 || arguments[1] === undefined ? {} : arguments[1];

		var result = [], skip = false, val = 0, e = undefined, base = undefined, bits = undefined, ceil = undefined, neg = undefined, num = undefined, output = undefined, round = undefined, unix = undefined, spacer = undefined, suffixes = undefined;

		if (isNaN(arg)) {
			throw new Error("Invalid arguments");
		}

		bits = descriptor.bits === true;
		unix = descriptor.unix === true;
		base = descriptor.base !== undefined ? descriptor.base : 2;
		round = descriptor.round !== undefined ? descriptor.round : unix ? 1 : 2;
		spacer = descriptor.spacer !== undefined ? descriptor.spacer : unix ? "" : " ";
		suffixes = descriptor.suffixes !== undefined ? descriptor.suffixes : {};
		output = descriptor.output !== undefined ? descriptor.output : "string";
		e = descriptor.exponent !== undefined ? descriptor.exponent : -1;
		num = Number(arg);
		neg = num < 0;
		ceil = base > 2 ? 1000 : 1024;

		// Flipping a negative number to determine the size
		if (neg) {
			num = -num;
		}

		// Zero is now a special case because bytes divide by 1
		if (num === 0) {
			result[0] = 0;
			result[1] = unix ? "" : !bits ? "B" : "b";
		}
		else {
			// Determining the exponent
			if (e === -1 || isNaN(e)) {
				e = Math.floor(Math.log(num) / Math.log(ceil));
			}

			// Exceeding supported length, time to reduce & multiply
			if (e > 8) {
				e = 8;
			}

			val = base === 2 ? num / Math.pow(2, e * 10) : num / Math.pow(1000, e);

			if (bits) {
				val = val * 8;

				if (val > ceil) {
					val = val / ceil;
					e++;
				}
			}

			result[0] = Number(val.toFixed(e > 0 ? round : 0));
			result[1] = this.si[bits ? "bits" : "bytes"][e];

			if (!skip && unix) {
				result[1] = result[1].charAt(0);

				if (this.b.test(result[1])) {
					result[0] = Math.floor(result[0]);
					result[1] = "";
				}
				else if (!bits && result[1] === "k") {
					result[1] = "K";
				}
			}
		}

		// Decorating a 'diff'
		if (neg) {
			result[0] = -result[0];
		}

		// Applying custom suffix
		result[1] = suffixes[result[1]] || result[1];

		// Returning Array, Object, or String (default)
		if (output === "array") {
			return result;
		}

		if (output === "exponent") {
			return e;
		}

		if (output === "object") {
			return {
				value: result[0],
				suffix: result[1]
			};
		}

		return result.join(spacer);
	},
	
	charMap: {
		  // latin
		  'À': 'A', 'Á': 'A', 'Â': 'A', 'Ã': 'A', 'Ä': 'A', 'Å': 'A', 'Æ': 'AE',
		  'Ç': 'C', 'È': 'E', 'É': 'E', 'Ê': 'E', 'Ë': 'E', 'Ì': 'I', 'Í': 'I',
		  'Î': 'I', 'Ï': 'I', 'Ð': 'D', 'Ñ': 'N', 'Ò': 'O', 'Ó': 'O', 'Ô': 'O',
		  'Õ': 'O', 'Ö': 'O', 'Ő': 'O', 'Ø': 'O', 'Ù': 'U', 'Ú': 'U', 'Û': 'U',
		  'Ü': 'U', 'Ű': 'U', 'Ý': 'Y', 'Þ': 'TH', 'ß': 'ss', 'à': 'a', 'á': 'a',
		  'â': 'a', 'ã': 'a', 'ä': 'a', 'å': 'a', 'æ': 'ae', 'ç': 'c', 'è': 'e',
		  'é': 'e', 'ê': 'e', 'ë': 'e', 'ì': 'i', 'í': 'i', 'î': 'i', 'ï': 'i',
		  'ð': 'd', 'ñ': 'n', 'ò': 'o', 'ó': 'o', 'ô': 'o', 'õ': 'o', 'ö': 'o',
		  'ő': 'o', 'ø': 'o', 'ù': 'u', 'ú': 'u', 'û': 'u', 'ü': 'u', 'ű': 'u',
		  'ý': 'y', 'þ': 'th', 'ÿ': 'y', 'ẞ': 'SS',
		  // greek
		  'α': 'a', 'β': 'b', 'γ': 'g', 'δ': 'd', 'ε': 'e', 'ζ': 'z', 'η': 'h', 'θ': '8',
		  'ι': 'i', 'κ': 'k', 'λ': 'l', 'μ': 'm', 'ν': 'n', 'ξ': '3', 'ο': 'o', 'π': 'p',
		  'ρ': 'r', 'σ': 's', 'τ': 't', 'υ': 'y', 'φ': 'f', 'χ': 'x', 'ψ': 'ps', 'ω': 'w',
		  'ά': 'a', 'έ': 'e', 'ί': 'i', 'ό': 'o', 'ύ': 'y', 'ή': 'h', 'ώ': 'w', 'ς': 's',
		  'ϊ': 'i', 'ΰ': 'y', 'ϋ': 'y', 'ΐ': 'i',
		  'Α': 'A', 'Β': 'B', 'Γ': 'G', 'Δ': 'D', 'Ε': 'E', 'Ζ': 'Z', 'Η': 'H', 'Θ': '8',
		  'Ι': 'I', 'Κ': 'K', 'Λ': 'L', 'Μ': 'M', 'Ν': 'N', 'Ξ': '3', 'Ο': 'O', 'Π': 'P',
		  'Ρ': 'R', 'Σ': 'S', 'Τ': 'T', 'Υ': 'Y', 'Φ': 'F', 'Χ': 'X', 'Ψ': 'PS', 'Ω': 'W',
		  'Ά': 'A', 'Έ': 'E', 'Ί': 'I', 'Ό': 'O', 'Ύ': 'Y', 'Ή': 'H', 'Ώ': 'W', 'Ϊ': 'I',
		  'Ϋ': 'Y',
		  // turkish
		  'ş': 's', 'Ş': 'S', 'ı': 'i', 'İ': 'I', 'ç': 'c', 'Ç': 'C', 'ü': 'u', 'Ü': 'U',
		  'ö': 'o', 'Ö': 'O', 'ğ': 'g', 'Ğ': 'G',
		  // russian
		  'а': 'a', 'б': 'b', 'в': 'v', 'г': 'g', 'д': 'd', 'е': 'e', 'ё': 'yo', 'ж': 'zh',
		  'з': 'z', 'и': 'i', 'й': 'j', 'к': 'k', 'л': 'l', 'м': 'm', 'н': 'n', 'о': 'o',
		  'п': 'p', 'р': 'r', 'с': 's', 'т': 't', 'у': 'u', 'ф': 'f', 'х': 'h', 'ц': 'c',
		  'ч': 'ch', 'ш': 'sh', 'щ': 'sh', 'ъ': 'u', 'ы': 'y', 'ь': '', 'э': 'e', 'ю': 'yu',
		  'я': 'ya',
		  'А': 'A', 'Б': 'B', 'В': 'V', 'Г': 'G', 'Д': 'D', 'Е': 'E', 'Ё': 'Yo', 'Ж': 'Zh',
		  'З': 'Z', 'И': 'I', 'Й': 'J', 'К': 'K', 'Л': 'L', 'М': 'M', 'Н': 'N', 'О': 'O',
		  'П': 'P', 'Р': 'R', 'С': 'S', 'Т': 'T', 'У': 'U', 'Ф': 'F', 'Х': 'H', 'Ц': 'C',
		  'Ч': 'Ch', 'Ш': 'Sh', 'Щ': 'Sh', 'Ъ': 'U', 'Ы': 'Y', 'Ь': '', 'Э': 'E', 'Ю': 'Yu',
		  'Я': 'Ya',
		  // ukranian
		  'Є': 'Ye', 'І': 'I', 'Ї': 'Yi', 'Ґ': 'G', 'є': 'ye', 'і': 'i', 'ї': 'yi', 'ґ': 'g',
		  // czech
		  'č': 'c', 'ď': 'd', 'ě': 'e', 'ň': 'n', 'ř': 'r', 'š': 's', 'ť': 't', 'ů': 'u',
		  'ž': 'z', 'Č': 'C', 'Ď': 'D', 'Ě': 'E', 'Ň': 'N', 'Ř': 'R', 'Š': 'S', 'Ť': 'T',
		  'Ů': 'U', 'Ž': 'Z',
		  // polish
		  'ą': 'a', 'ć': 'c', 'ę': 'e', 'ł': 'l', 'ń': 'n', 'ó': 'o', 'ś': 's', 'ź': 'z',
		  'ż': 'z', 'Ą': 'A', 'Ć': 'C', 'Ę': 'e', 'Ł': 'L', 'Ń': 'N', 'Ś': 'S',
		  'Ź': 'Z', 'Ż': 'Z',
		  // latvian
		  'ā': 'a', 'č': 'c', 'ē': 'e', 'ģ': 'g', 'ī': 'i', 'ķ': 'k', 'ļ': 'l', 'ņ': 'n',
		  'š': 's', 'ū': 'u', 'ž': 'z', 'Ā': 'A', 'Č': 'C', 'Ē': 'E', 'Ģ': 'G', 'Ī': 'i',
		  'Ķ': 'k', 'Ļ': 'L', 'Ņ': 'N', 'Š': 'S', 'Ū': 'u', 'Ž': 'Z',
		  // currency
		  '€': 'euro', '₢': 'cruzeiro', '₣': 'french franc', '£': 'pound',
		  '₤': 'lira', '₥': 'mill', '₦': 'naira', '₧': 'peseta', '₨': 'rupee',
		  '₩': 'won', '₪': 'new shequel', '₫': 'dong', '₭': 'kip', '₮': 'tugrik',
		  '₯': 'drachma', '₰': 'penny', '₱': 'peso', '₲': 'guarani', '₳': 'austral',
		  '₴': 'hryvnia', '₵': 'cedi', '¢': 'cent', '¥': 'yen', '元': 'yuan',
		  '円': 'yen', '﷼': 'rial', '₠': 'ecu', '¤': 'currency', '฿': 'baht',
		  '$': 'dollar',
		  // symbols
		  '©': '(c)', 'œ': 'oe', 'Œ': 'OE', '∑': 'sum', '®': '(r)', '†': '+',
		  '“': '"', '”': '"', '‘': "'", '’': "'", '∂': 'd', 'ƒ': 'f', '™': 'tm',
		  '℠': 'sm', '…': '...', '˚': 'o', 'º': 'o', 'ª': 'a', '•': '*',
		  '∆': 'delta', '∞': 'infinity', '♥': 'love', '&': 'and', '|': 'or',
		  '<': 'less', '>': 'greater'
		},
			
		slugify: function(string, replacement) {
			var me = this;
		  return string.split('').reduce(function (result, ch) {
			    if (me.charMap[ch]) {
			      ch = me.charMap[ch]
			    }
			    // allowed
			    ch = ch.replace(/[^\w\s$\*_\+~\.\(\)'"!\-:@]/g, '')
			    result += ch
			    return result
			  }, '')
			    // trim leading/trailing spaces
			    .replace(/^\s+|\s+$/g, '')
			    // convert spaces
			    .replace(/[-\.\s]+/g, replacement || '-')
			    // remove trailing separator
			    .replace('#{replacement}$', '');
		}

});