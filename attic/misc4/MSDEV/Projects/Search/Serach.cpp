#include <iostream.h>
#include <string.h>


int index(char c)
{
	return c - 48;
}

int mischarsearch(char* p, char* a)
{
	int skip[9];
	int i, j, t, M, N;
	M = strlen(p);
	N = strlen(a);
	
	for (i = 0; i < 9; i++)
		skip[i] = M;

	for (j = 0; j < M; j++)
		skip[index(p[j])] = M-j-1;

	for (i = M-1, j = M-1; j >= 0; i--, j--)
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

int main()
{

	char* nadel  = "9149";
	char* heu[2];
	heu[0] = "223461129821273";
			  //012345678901234
	heu[1] = "273439812909149";
			  //234567890123456
					   
	int pos = 0;
	int res = 0;
	
	for (int i = 0; i < 2; i++)
	{
		res = mischarsearch(nadel, heu[i]);
		if (res < strlen(heu[i]))
			cout << "Gefunden an Position: " << pos+res << endl;

		pos = strlen(heu[i]) - strlen(nadel) + 1;
			
	}
	
	return 0;
}
