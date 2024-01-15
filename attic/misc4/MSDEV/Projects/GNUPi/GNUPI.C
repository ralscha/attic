#include <stdio.h>

long	m,m1,ii,wk,wk1;
long	kf,ks,*mf,*ms;
long	cnt,i,n,temp,nd;
int		col,col1;
long	loc,stor[21];
FILE	*fp;

shift(l1,l2,lp,lmod)
long *l1,*l2,lp,lmod;
{
	long k;
	k=((*l2)>0 ? (*l2)/lmod : -(-(*l2)/lmod)-1);
	*l2 -= k*lmod;
	*l1 += k*lp;
}

main(argc,argv)
int argc;
char *argv[];
{
	stor[i++] = 0;
	if (argc < 2)
	{           
		fprintf(stdout,"Form: Gnupi <# of places>\n");
		exit();
	}
	n = atoi(argv[1]);
	mf = (long *)calloc((int)n+3, sizeof(long));
	ms = (long *)calloc((int)n+3, sizeof(long));
	if (mf==0 || ms==0)
	{
		fprintf(stderr,"Memory allocation failure!\n");
		exit();
	}
	fp = fopen("pi.out","w");
	if (fp==0)
	{
		fprintf(stderr,"\nError opening output file pi.out!\n");
		exit();
	}
	fprintf(stdout,"Gnupi v3.1\n");
	fprintf(fp,"Gnupi v3.1\n");
	fprintf(stdout,"\nThe following is an approximation of PI to %ld digits\n",(long)n);
	fprintf(fp,"\nThe following is an approximation of PI to %ld digits\n",(long)n);
	cnt = 0;
	kf = 25;
	ks = 57121L;
	mf[1] = 1;
	for (i=2; i<=n; i+=2)
	{
		mf[i] = -16;
		mf[i+1] = 16;
	}
	for (i=1; i<=n; i+=2)
	{
		ms[i] = -4;
		ms[i+1] = 4;
	}
	fprintf(stdout,"\n 3.");
	fprintf(fp,"\n 3.");
	while (cnt < n)
	{
		for (i=0; ++i<=n-cnt;)
		{
			mf[i] *= 10;
			ms[i] *= 10;
		}
		for (i=n-cnt+1; --i>=2;)
		{
			temp = 2*i-1;
			shift(&mf[i-1],&mf[i],temp-2,temp*kf);
			shift(&ms[i-1],&ms[i],temp-2,temp*ks);
		}
		nd = 0;
		shift(&nd,&mf[1],1L,5L);
		shift(&nd,&ms[1],1L,239L);
		m=nd;
		if (m<8)
		{
			for (ii=1; ii<=loc;)
			{
				m1=stor[ii++];
				if (cnt < n)
				{
					if (++col==11)
					{
						col = 1;
						if (++col1==6)
						{
							col1=0;
							fprintf(stdout,"\n%4ld",m1%10);
							fflush(stdout);
							fprintf(fp,"\n%4ld",m1%10);
						}
						else
						{
							fprintf(stdout,"%3ld",m1%10);
							fflush(stdout);
							fprintf(fp,"%3ld",m1%10);
						}
					}
					else
					{
						fprintf(stdout,"%ld",m1);
						fflush(stdout);
						fprintf(fp,"%ld",m1);
					}
					cnt++;
				}
			}
			loc=0;
		}
		else
		if (m>9)
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
	printf("\n\nCalculations Completed!\n");
	fclose(fp);
}
