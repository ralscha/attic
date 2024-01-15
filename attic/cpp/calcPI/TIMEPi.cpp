#include <stdio.h>
#include <sys/timeb.h>
#include <time.h>

int a=10000,b,c=2800,d,e,f[2801],g;

int main()
{
	time_t st, fi;
	_timeb start, finish;

	time(&st);
	_ftime(&start);
	for(;b-c;) f[b++]=a/5;
	for(;d=0,g=c*2;c-=14,printf("%.4d",e+d/a),e=d%a)
		for(b=c;d+=f[b]*a,f[b]=d%--g,d/=g--,--b;d*=b);
	
	_ftime(&finish);
	time(&fi);
	printf("\n%ld   %u", st, start.millitm);
	printf("\n%ld   %u\n", fi, finish.millitm);


	return 0;
}

