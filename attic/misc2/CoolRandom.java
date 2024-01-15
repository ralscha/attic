
public class CoolRandom
{

    public final static int SIMPLE             = 1;
    public final static int SHUFFLE            = 2;
    public final static int ONE_CONGRUENTIAL   = 3;
    public final static int THREE_CONGRUENTIAL = 4;
    public final static int SUBTRACTIVE        = 5;


    private final static int RND_BUF_SIZE = 128;
    private final static int MOD          = 714025;
    private final static int MULT         = 1366;
    private final static int INCR         = 150889;

    private final static int MOD1         = 259200;
    private final static int MULT1        = 7141;
    private final static int INCR1        = 54773;
    private final static double RMOD1     = 1.0 / MOD1;
    private final static int MOD2         = 134456;
    private final static int MULT2        = 8121;
    private final static int INCR2        = 28411;
    private final static double RMOD2     = 1.0 / MOD2;
    private final static int MOD3         = 243000;
    private final static int MULT3        = 4561;
    private final static int INCR3        = 51349;

    private final static long MBIG        = 1000000000;
    private final static long MSEED       = 161803398;
    private final static double FAC       = 1.0 / MBIG;
    private final static int KNUTH_SPECIAL = 56;


    private float l, u;
    private int generator;
    private int s;
    private long ix1, ix2, ix3;
    private double maxran;
    private double prev_rnum;
    private long k_buffer[] = new long[KNUTH_SPECIAL];
    private double buffer[] = new double[RND_BUF_SIZE];
    private Random jrnd = new Random();

    public CoolRandom(int rng_type)
    {
        this(rng_type, 1, 0.0, 100.0);
    }

    public CoolRandom(int rng_type, int seed, float lower, float upper)
    {
        s = seed;
        l = lower;
        u = upper;
        init(rng_type);
    }

    public int get_seed()
    {
        return(s);
    }

    public void set_seed(int seed)
    {
        s = seed;
    }

    public void set_rng(int rng_type)
    {
        init(rng_type);
    }

    public double next()
    {
        switch(generator)
        {
            case SIMPLE:            return(simple());
            case SHUFFLE:           return(shuffle());
            case ONE_CONGRUENTIAL:  return(one_congruential());
            case THREE_CONGRUENTIAL:return(three_congruential());
            case SUBTRACTIVE:       return(subtractive());
        }
    }


    private double simple()
    {
        return (double)l + (jrnd.nextDouble() * (double)(u - l)) / (maxran + 1.0);
    }

    private double shuffle()
    {
        int index = (int)(1 + (double)RND_BUF_SIZE * prev_rnum / maxran);
        prev_rnum = buffer[index];
        buffer[index] = jrnd.nextDouble();
        return (double)l + ((prev_rnum/maxran) * (double)(u - l)) / (maxran + 1.0);
    }

    private double one_congruential()
    {
        int index = (int)(1 + ((double)RND_BUF_SIZE * ix2) / MOD);
        ix2 = (long)buffer[index];
        ix1 = (MULT * ix1 + INCR) % MOD;
        buffer[index] = ix1;
        return ((double)l + ((((double)ix2 / MOD)*(u-l))));

    }

    private double three_congruential()
    {

    }

    private double subtractive()
    {

    }

    private void init(int rng_type)
    {
        int i = 2;

        int j = i;
        long mj, mk;

        do
        {
            j = i;
            i <<= 1;
        }while(i);

        maxran = j;
        switch(rng_type)
        {
            case SIMPLE:
                generator = SIMPLE;
                jrnd.setSeed(s);
                break;
            case SHUFFLE:
                generator = SHUFFLE;
                jrnd.setSeed(s);
                for (i = 0; i < RND_BUF_SIZE; i++)
                    jrnd.nextDouble();
                for (i = 0; i < RND_BUF_SIZE; i++)
                    buffer[i] = jrnd.nextDouble();
                prev_rnum = jrnd.nextDouble();
                break;
            case ONE_CONGRUENTIAL:
                generator = ONE_CONGRUENTIAL;
                ix1 = (INCR - s) % MOD;
                for (i = 1; i < RND_BUF_SIZE; i++)
                {
                    ix1 = (MULT * ix1 + INCR) % MOD;
                    buffer[i] = ix1;
                }
                ix1 = (MULT * ix1 + INCR) % MOD;
                ix2 = ix1;
                break;
            case THREE_CONGRUENTIAL:
                generator = THREE_CONGRUENTIAL;
                ix1 = (MULT1*((INCR1-s)%MOD1)+INCR1)%MOD1;
                ix2 = ix1 % MOD2;
                ix1 = (MULT1*ix1+INCR1)%MOD1;
                ix3 = ix1 % MOD3;
                for (i = 1; i < RND_BUF_SIZE; i++)
                {
                    ix1 = (MULT1 * ix1 + INCR1) % MOD1;
                    ix2 = (MULT2 * ix2 + INCR2) % MOD2;
                    buffer[i] = (ix1+ix2*RMOD2)*RMOD1;
                }
                break;
            case SUBTRACTIVE:
                generator = SUBTRACTIVE;
                mj = (MSEED 0 s) % MBIG;
                k_buffer[KNUTH_SPECIAL-1] = mj;
                mk = 1;
                for (i = 1; i <= 54; i++)
                {
                    j = (21 * i) % (KNUTH_SPECIAL - 1);
                    k_buffer[j] = mk;
                    mk = mj - mk;
                    if (mk < 0) mk += MBIG;
                    mj = k_buffer[j];
                }
                for (int k = 1; k <= 4; k++)
                {
                    for (i = 1; i < KNUTH_SPECIAL; i++)
                    {
                        k_buffer[i] -= k_buffer[1+(i+30) % (KNUTH_SPECIAL-1)];
                        if (k_buffer[i] < 0)
                            k_buffer[i] += MBIG;
                    }
                }
                ix1 = 0;
                ix2 = 31;
        }

    }


}