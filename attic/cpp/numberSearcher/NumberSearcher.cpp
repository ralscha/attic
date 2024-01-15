
#include <fstream.h>
#include <ctype.h>
#include <string.h>

//GLOBAL
int skip[10];
char buffer[500]; // Lesebuffer
int buflen = 500;
long pos = 0;

int index(char c)
{
	return c - 48;
}


void initskip(char* s)
{
	int i;
	int sl = strlen(s);
	for (i = 0; i < 10; i++)
		skip[i] = sl;
	for (i = 0; i < sl; i++)
		skip[index(s[i])] = sl-i-1;
}

int mischarsearch(char* p, char* a, int startpos)
{
	int i, j, t, M, N;
	M = strlen(p);
	N = strlen(a);
	
	for (i = startpos + (M-1), j = M-1; j >= 0; i--, j--)
	{
		while(a[i] != p[j])
		{
			t = skip[index(a[i])];
			i += (M-j > t) ? M-j : t;
			if (i >= N) 
				return N;
			j = M-1;			
		}
	}
	return i+1;
}

int readbuffer(ifstream& f, int mitnehmen)
{
	char ch;
	int p = 0;
	int r = 0;
	for (int i = 0; i < mitnehmen; i++)
		buffer[i] = buffer[buflen-mitnehmen+i];
	p = mitnehmen;
	
	while (!f.eof() && p < buflen)
	{
		ch = f.get();
		if (isdigit(ch))
		{
			buffer[p] = ch;
			p++;
			r++;
		}
	}
	if (f.eof())
		buffer[p] = '\0';

	return r;
}

int main()
{
	char* suchstr = "11";
	int lenss = strlen(suchstr);
	initskip(suchstr);
	ifstream in("PI.TXT");
	int read;
	int res;
	
	read = readbuffer(in, 0);
	pos = 0;

	while (read != 0)
	{
		res = mischarsearch(suchstr, buffer, 0);
		while (res < strlen(buffer))
		{
			cout << "Gefunden an Position: " << pos+res << endl;		
			res = mischarsearch(suchstr, buffer, res+1);
		}
		pos += strlen(buffer) - lenss + 1;
		read = readbuffer(in, lenss-1);
	}


	return 0;
}