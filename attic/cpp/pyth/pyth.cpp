
#include <iostream.h>

long int a, b, c;
int x, y;

int main()
{

	for (x = 1; x <= 60; x++)
		for (y = 1; y <= 60; y++)
		{
			a = x * x - y * y;
			b = 2 * x * y;
			c = x * x + y * y;
			if (a > 1) 
			{
				cout.width(10); cout << a;
				cout.width(10); cout << b;
				cout.width(10); cout << c;
				cout << '\n';
			}
		}

	return 0;
}
