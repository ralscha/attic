
#include <stdio.h>
#include <string.h>
#include <ctype.h>
#include <malloc.h>



void main (int argc, char *argv[])
{

	unsigned char *s;
	s = argv[1];

	if (strchr ("(-<^$", *s) == NULL) {
		printf("ok");
	} else {
		printf("nok");
	}
}
