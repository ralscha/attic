#include <fstream.h>
#include <afx.h>


ifstream in("D:\\MSDEV\\PROJECTS\\CALCPI\\PI1.TXT");
CString calcpi;
CString help;
BOOL FEHLER;
char rchar;
int count;

int a=10000,b,c=2800,d,e,f[2801];

//Original
/*int a=10000,b,c=2800,d,e,f[2801],g;
int main()
{
	for(;b-c;) f[b++]=a/5;
	for(;d=0,g=c*2;c-=14,printf("%.4d",e+d/a),e=d%a)
		for(b=c;d+=f[b]*a,f[b]=d%--g,d/=g--,--b;d*=b);
	return 0;
}
*/	

int main()
{
	for (int i = 0; i < c; i++)
		f[i] = 2000;

	for (int g = c*2; g > 0; c -= 14, g = c*2)
	{
	   d = 0;
		b = c;

		for(; d += f[b] * a, f[b] = d % --g, d /= g--, --b; d*=b);
		help.Format("%.4d", (e+d/a));
		calcpi += help;
		e = d % a;
	}

	cout << calcpi;
	cout << endl;

	FEHLER = FALSE;
	count = 0;
	rchar = in.get();
	while (count < calcpi.GetLength() && rchar != EOF)
	{
		if (rchar != '\n')
		{
			if (calcpi[count] != rchar)
				FEHLER = TRUE;
			count++;
		}
		rchar = in.get();
	} 
	if (rchar == EOF)
		cout << "Datei kleiner als Berechnung\n";
	
	if (!FEHLER)
		cout << "Kein Fehler\n";
	else
		cout << "Es ist ein Fehler aufgetaucht\n";
	
	cout << "Es wurden " << count << " Stelen getestet\n";	
	in.close();
	return 0;
}
