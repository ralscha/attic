#include <stdlib.h>
#include <iostream.h>
#include <iomanip.h>
#include <fstream.h>
#include <afx.h>

long	m, m1, ii, wk, wk1;
long	kf, ks, *mf, *ms;
long	cnt, i, n, temp, nd;
int		col, col1;
long	loc, stor[21];
ofstream fout;
ifstream in("D:\\MSDEV\\PROJECTS\\CALCPI\\PI.TXT");
CString calcpi;
CString help;
char rchar;
int count;

			  
void shift(long& l1, long& l2, long lp, long lmod)
{
	long k;
	k=(l2 > 0 ? l2/lmod : -(-l2/lmod)-1);
	l2 -= k*lmod;
	l1 += k*lp;
}

int main(int argc, char* argv[])
{
	stor[i++] = 0;
	
	if (argc < 2)
	{           
		cout << "Form: Gnupi <# of places>\n";
		return 0;
	}
	n = atoi(argv[1]);
	mf = (long *)calloc((int)n+3, sizeof(long));
	ms = (long *)calloc((int)n+3, sizeof(long));
	if (mf==0 || ms==0)
	{
		cerr << "Memory allocation failure!\n";
		return 0;
	}
	
	fout.open("pi.out");
	cout << "Gnupi v3.1\n";
	fout << "Gnupi v3.1\n";
	cout << "\nThe following is an approximation of PI to " << long(n) << " digits\n";
	fout << "\nThe following is an approximation of PI to " << long(n) << " digits\n";
	cnt = 0;
	kf = 25;
	ks = 57121L;
	mf[1] = 1;
	
	for (i = 2; i <= n; i += 2)
	{
		mf[i] = -16;
		mf[i+1] = 16;
	}

	for ( i = 1; i <= n; i += 2)
	{
		ms[i] = -4;
		ms[i+1] = 4;
	} 

	cout << "\n 3.";
	fout << "\n 3.";
	calcpi += "3";

	while (cnt < n)
	{
		for (i = 0; ++i <= n - cnt; )
		{
			mf[i] *= 10;
			ms[i] *= 10;
		}
		for (i = n - cnt + 1; --i >= 2; )
		{
			temp = 2*i-1;
			shift(mf[i-1], mf[i], temp-2, temp*kf);
			shift(ms[i-1], ms[i], temp-2, temp*ks);
		}
		nd = 0;
		shift(nd, mf[1], 1L, 5L);
		shift(nd, ms[1], 1L, 239L);
		m = nd;
		
		if (m < 8)
		{
			for (ii = 1; ii <= loc; )
			{
				m1 = stor[ii++];
				if (cnt < n)
				{
					if (++col == 11)
					{
						col = 1;
						if (++col1 == 6)
						{
							col1 = 0;
							cout << setw(4) << m1 % 10;
							cout.flush();
							fout << setw(4) << m1 % 10;
							help.Format("%d", (m1 % 10));
		               calcpi += help;
						}
						else
						{
							cout << setw(3) << m1 % 10;
							cout.flush();
							fout << setw(3) << m1 % 10;
                     help.Format("%d", (m1 % 10));
		               calcpi += help;
						}
					}
					else
					{
						cout << m1;
						cout.flush();
						fout << m1;
					   help.Format("%d", m1);
		            calcpi += help;
					}
					cnt++;
				}
			}
			loc = 0;
		}
		else if (m > 9)
		{
			wk = m/10;
			m %= 10;
			for (wk1=loc; wk1>=1;wk1--)
			{
				wk += stor[wk1];
				stor[wk1] = wk % 10;
				wk /= 10;
			}
		}
		stor[++loc] = m;
	}
	cout << "\n\nCalculations Completed!\n";
	fout.close();

	count = 0;
	rchar = in.get();
	while (count < calcpi.GetLength() && rchar != EOF)
	{
		if (rchar != '\n')
		{
			if (calcpi[count] != rchar)
			{
				cout << "FEHLER an der " << count+1 << " Stelle\n";
				cout << "Referenz : " << rchar << '\n';
				cout << "Berechnet: " << calcpi[count] << '\n';			
			}
			count++;
		}
		rchar = in.get();
	} 
	if (rchar == EOF)
		cout << "Datei kleiner als Berechnung\n";
	
	
	cout << "Es wurden " << count << " Stelen getestet\n";	
	in.close();
	return 0;
}
