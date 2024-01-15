/*
  --- menu level scope settins structure --- 
  note that this structure has changed its format since previous version.
  Now this structure has the same layout as Tigra Menu GOLD.
  Format description can be found in product documentation.
*/
var MENU_POS = [
{
	// item sizes
	'height': 23,
	'width': 110,
	// menu block offset from the origin:
	//	for root level origin is upper left corner of the page
	//	for other levels origin is upper left corner of parent item
	'block_top': 24,
	'block_left': 20,
	// offsets between items of the same level
	'top': 0,
	'left': 109,
	// time in milliseconds before menu is hidden after cursor has gone out
	// of any items
	'hide_delay': 500,
	'expd_delay': 50,
	'css' : {
		'outer': ['m0l0oout', 'm0l0oover'],
		'inner': ['m0l0iout', 'm0l0iover']
	}
},
{
	'height': 20,
	'width': 110,
	'block_top': 23,
	'block_left': 0,
	'top': 21,
	'left': 0,
	'css': {
		'outer' : ['m0l1oout', 'm0l1oover'],
		'inner' : ['m0l1iout', 'm0l1iover']
	}
},
{
	'block_top': 5,
	'block_left': 100,
	'css': {
		'outer': ['m0l2oout', 'm0l2oover'],
		'inner': ['m0l1iout', 'm0l2iover']
	}
}
]
