#include <iostream.h>
#include <fstream.h>


char buf[80];
ifstream in("D:\\MSVC20\\SELF\\TEST\\TEST.UUE");
ofstream out("D:\\MSVC20\\SELF\\TEST\\OUT.TXT");

int bp, n;

int DEC(char c)
{
	return (c - 32) % 64;
}

void outdec()
{
	int c1, c2, c3;
	c1 = DEC(buf[bp]) << 2 | DEC(buf[bp+1]) >> 4;
	c2 = DEC(buf[bp+1]) << 4 | DEC(buf[bp+2]) >> 2;
	c3 = DEC(buf[bp+2]) << 6 | DEC(buf[bp+3]);
	if (n >= 1) out << char(c1);
	if (n >= 2) out << char(c2);
	if (n >= 3) out << char(c3); 
}


int main()
{

	for (int j = 0; j < 4; j++)
	{
		in.getline(buf, 80);
		n = DEC(buf[0]);
		if (n > 0)
		{
			bp = 1;
			while (n > 0)
			{		
				outdec();
				bp += 4;
				n -= 3;
			}
		}
	}

	in.close();
	out.close();
	return 0;
}
