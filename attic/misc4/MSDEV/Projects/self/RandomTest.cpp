#include <iostream.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>

const rmax = 100; // Zahlen höchstens zwischen 0 und 99
const versuche = 20000;
const ra = rmax;

float chisquare(int N, int r)
{
	int i, t, f[rmax];
	for (i = 0; i < r; i++) f[i] = 0;
	for (i = 0; i < N; i++) f[rand() % r]++;
	for (i = 0, t = 0; i < r; i++) t += f[i]*f[i];
	return (float)((r*t/N) - N);
}

int main()
{
	srand((unsigned)time(NULL));

	cout << "Ergebnis sollte zwischen " << ra-(2 * sqrt(ra)) 
		<< " und" << ra+(2 * sqrt(ra)) << '\n';
	for (int i = 0; i < 20; i++)
		cout << "Ergebnis " << i+1 << " : " << chisquare(versuche, ra) << '\n';
	cin.get();
	return 0;
}
