import java.awt.*;


public class WaTorCanvas extends Canvas implements Runnable
{
    Image wtImg = null;
    Graphics wtG = null;
    Thread runner;
    Dimension d;
    int gx;
    int gy;
    int nf;
    int ns;
    int ax, ay, a2x, a2y, cx, cy, lenx, leny;
    int fische[][];
    int haie[][];
    int hunger[][];
    boolean fischzug[][];
    boolean haizug[][];
    int fbrut;
    boolean firsttime = true;

    int hbrut;
    int afisch,afischinit;
    int ahai,ahaiinit;
    int fasten;
    int chrononen;
    WaTorChartCanvas wtcc;
    WaTorStatusPanel wtsp;

    public WaTorCanvas(int gx, int gy, int fbrut, int hbrut,
                       int afisch, int ahai, int fasten,
                       WaTorChartCanvas wtcc, WaTorStatusPanel wtsp)
    {
        super();
        this.gx = gx;
        this.gy = gy;
        this.fbrut = fbrut;
        this.hbrut = hbrut;
        this.afisch = afisch;
        this.ahai   = ahai;
        this.afischinit = afisch;
        this.ahaiinit   = ahai;
        this.fasten = fasten;
        this.wtcc = wtcc;
        this.wtsp = wtsp;

    }


    public void start()
    {
        runner = new Thread(this);
        runner.setPriority(Thread.MIN_PRIORITY);

        /*
        wtImg = createImage(size().width, size().height);
        wtG   = wtImg.getGraphics();
        wtG.setColor(Color.black);
        */
        initialize();
        drawGrid();
        drawCreatures();
        repaint();
        runner.start();
    }

    public void stop()
    {
        if (runner != null)
        {
            runner.stop();
            runner = null;
        }
    }

    public synchronized void reshape(int x, int y, int width, int height)
    {
        super.reshape(x, y, width, height);
        if ((wtImg == null) || (width != wtImg.getWidth(null))
              || (height != wtImg.getHeight(null)))
        {
            stop();
            wtImg = createImage(width, height);
            wtG   = wtImg.getGraphics();
            wtG.setColor(Color.black);
            runner = new Thread(this);
            if (fische != null)
            {
                drawGrid();
                drawCreatures();
            }
            start();
        }

    }
    public void paint(Graphics g)
    {
        if (wtImg != null)
        {
            g.drawImage(wtImg, 0, 0, this);
        }
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    void clearImage()
    {
        if (wtImg != null)
        {
            wtG.setColor(getBackground());
            wtG.fillRect(0, 0, size().width, size().height);
            wtG.setColor(Color.black);
        }
    }


    public void run()
    {
        int x = 0;
        repaint();

        while((Thread.currentThread() == runner) && (afisch > 0) && (ahai > 0))
        {
            chrononen++;

            for (int i = 0; i < gx; i++)
            {
                for (int j = 0; j < gy; j++)
                {
                    fischzug[i][j] = false;
                    haizug[i][j]   = false;
                }
            }

            for (int i = 0; i < gx; i++)
            {
                for (int j = 0; j < gy; j++)
                {
                    if ((fische[i][j] != -1) && (fischzug[i][j] == false)
                            && (Thread.currentThread() == runner))
                        goFish(i, j);
                }
            }

            for (int i = 0; i < gx; i++)
            {
                for (int j = 0; j < gy; j++)
                {
                    if ((haie[i][j] != -1) && (haizug[i][j] == false))
                    {
                        if (!goShark(i,j) && (Thread.currentThread() == runner))
                        {
                            goShark2(i, j);
                        }
                    }
                }
            }

            clearImage();
            drawGrid();
            drawCreatures();
            repaint();

            wtcc.drawPoints(afisch, ahai);
            wtsp.setValues(afisch, ahai, chrononen);
	    }

    }

    void goFish(int x, int y)
    {
        boolean found = false;
        boolean[] nei = {true, true, true, true};
        boolean f = true;
        int zufall;
        int nx = 0;
        int ny = 0;
        int n = 0;

        if ((fische[x][((y-1)+gy) % gy] == -1) && (haie[x][((y-1)+gy) % gy] == -1))
        {
            nei[0] = false;
            found  = true;
        }
        if ((fische[(x+1)%gx][y] == -1) && (haie[(x+1)%gx][y] == -1))
        {
            nei[1] = false;
            found  = true;
        }
        if ((fische[x][(y+1) % gy] == -1) && (haie[x][(y+1) % gy] == -1))
        {
            nei[2] = false;
            found  = true;
        }
        if ((fische[((x-1)+gx) % gx][y] == -1) && (haie[((x-1)+gx) % gx][y] == -1))
        {
            nei[3] = false;
            found  = true;
        }

        if (!found)
        {
            fische[x][y]++;
            return;
        }

        while(f)
        {
            zufall = (int)(Math.random() * 4);
            if (nei[zufall] == false)
            {
                n = zufall;
                f = false;
            }
        }

        switch(n)
        {
            case 0 : nx = x; ny = ((y-1)+gy) % gy; break;
            case 1 : nx = (x+1) % gx; ny = y;      break;
            case 2 : nx = x; ny = (y+1) % gy;      break;
            case 3 : nx = ((x-1)+gx) % gx; ny = y;      break;
        }

        fische[nx][ny] = fische[x][y] + 1;
        if (fische[nx][ny] >= fbrut)
        {
            fische[nx][ny] = 0;
            fische[x][y]   = 0;
            fischzug[nx][ny] = true;
            fischzug[x][y]   = true;
            afisch++;
        }
        else
        {
            fische[x][y] = -1;
            fischzug[nx][ny] = true;
        }
    }

    /* true = fisch gefressen */
    boolean goShark(int x, int y)
    {
        boolean found = false;
        boolean[] nei = {false, false, false, false};
        boolean f = true;
        int zufall;
        int nx = 0;
        int ny = 0;
        int n = 0;

        if ((fische[x][((y-1)+gy) % gy] != -1))
        {
            nei[0] = true;
            found  = true;
        }
        if ((fische[(x+1) % gx][y] != -1))
        {
            nei[1] = true;
            found  = true;
        }
        if ((fische[x][(y+1) % gy] != -1))
        {
            nei[2] = true;
            found  = true;
        }
        if ((fische[((x-1)+gx) % gx][y] != -1))
        {
            nei[3] = true;
            found  = true;
        }

        if (!found)
        {
            return (false);
        }

        while(f)
        {
            zufall = (int)(Math.random() * 4);
            if (nei[zufall])
            {
                n = zufall;
                f = false;
            }
        }

        switch(n)
        {
            case 0 : nx = x; ny = ((y-1)+gy) % gy; break;
            case 1 : nx = (x+1) % gx; ny = y;      break;
            case 2 : nx = x; ny = (y+1) % gy;      break;
            case 3 : nx = ((x-1)+gx) % gx; ny = y; break;
        }


        haie[nx][ny]   = haie[x][y] + 1;
        hunger[nx][ny] = hunger[x][y];
        if (haie[nx][ny] >= hbrut)
        {
            haie[nx][ny] = 0;
            haie[x][y]   = 0;
            haizug[nx][ny] = true;
            haizug[x][y]   = true;
            hunger[x][y]   = 0;
            ahai++;
        }
        else
        {
            haie[x][y] = -1;
            haizug[nx][ny] = true;
            hunger[x][y] = -1;
        }

        fische[nx][ny] = -1;
        afisch--;
        return (true);
    }

    void goShark2(int x, int y)
    {

        boolean found = false;
        boolean[] nei = {true, true, true, true};
        boolean f = true;
        int zufall;
        int nx = 0;
        int ny = 0;
        int n = 0;

        if ((haie[x][((y-1)+gy) % gy] == -1))
        {
            nei[0] = false;
            found  = true;
        }
        if ((haie[(x+1) % gx][y] == -1))
        {
            nei[1] = false;
            found  = true;
        }
        if ((haie[x][(y+1) % gy] == -1))
        {
            nei[2] = false;
            found  = true;
        }
        if ((haie[((x-1)+gx) % gx][y] == -1))
        {
            nei[3] = false;
            found  = true;
        }

        if (!found)
        {
            haie[x][y]++;
            hunger[x][y]++;
            if (hunger[x][y] >= fasten)
            {
                haie[x][y] = -1;
                hunger[x][y] = -1;
                ahai--;
            }
            return;
        }

        while(f)
        {
            zufall = (int)(Math.random() * 4);
            if (nei[zufall] == false)
            {
                n = zufall;
                f = false;
            }
        }

        switch(n)
        {
            case 0 : nx = x; ny = ((y-1)+gy) % gy; break;
            case 1 : nx = (x+1) % gx; ny = y;      break;
            case 2 : nx = x; ny = (y+1) % gy;      break;
            case 3 : nx = ((x-1)+gx) % gx; ny = y; break;
        }

        haie[nx][ny]   = haie[x][y]+1;
        hunger[nx][ny] = hunger[x][y]+1;
        if (haie[nx][ny] >= hbrut)
        {
            haie[nx][ny] = 0;
            haie[x][y]   = 0;
            haizug[nx][ny] = true;
            haizug[x][y]   = true;
            hunger[x][y]   = 0;
            ahai++;
        }
        else
        {
            haie[x][y] = -1;
            haizug[nx][ny] = true;
            hunger[x][y] = -1;
        }

        if (hunger[nx][ny] >= fasten)
        {
            haie[nx][ny] = -1;
            hunger[nx][ny] = -1;
            ahai--;
        }
    }

    void initialize()
    {

        fische = new int[gx][gy];
        haie   = new int[gx][gy];
        hunger = new int[gx][gy];
        fischzug = new boolean[gx][gy];
        haizug = new boolean[gx][gy];

        for(int i = 0; i < gx; i++)
        {
            for (int j = 0; j < gy; j++)
            {
                fische[i][j] = -1;
                haie[i][j]   = -1;
                hunger[i][j] = -1;
                fischzug[i][j] = false;
                haizug[i][j]   = false;
            }
        }

        boolean f = true;
        int x,y,fi;
        int z = 0;

        while(f)
        {
            x = (int)(Math.random() * gx);
            y = (int)(Math.random() * gy);
            fi = (int)(Math.random() * 3);
            if (fische[x][y] == -1)
            {
                fische[x][y] = fi;
                z++;
                if (z == afischinit) f = false;
            }
        }
        z = 0;
        f = true;
        int ha;
        while(f)
        {
            x = (int)(Math.random() * gx);
            y = (int)(Math.random() * gy);
            ha = (int)(Math.random() * 10);
            if ((fische[x][y] == -1) && (haie[x][y] == -1))
            {
                haie[x][y] = ha;
                hunger[x][y] = 0;
                z++;
                if (z == ahaiinit) f = false;
            }
        }

        d = size();
        ax = d.width  / (gx+1);
        ay = d.height / (gy+1);
        a2x = ax / 2;
        a2y = ay / 2;
        ax = (d.width -ax) / (gx-1);
        ay = (d.height-ay) / (gy-1);

        lenx = ax * (gx-1) + a2x*2;
        leny = ay * (gy-1) + a2y*2;

        chrononen = 0;
        afisch = afischinit;
        ahai   = ahaiinit;
        if (!firsttime)
            wtcc.restart(gx*gy, afischinit, ahaiinit);
        firsttime = false;
    }

    void drawGrid()
    {
        cx = a2x;
        cy = a2y;
        wtG.setColor(Color.black);

        for (int i = 0; i < gx; i++)
        {
            wtG.drawLine(cx, 0, cx, leny);
            cx += ax;
        }
        if (runner == null) return;
        for (int i = 0; i < gy; i++)
        {
            wtG.drawLine(0, cy, lenx, cy);
            cy += ay;
        }

    }


    void drawCreatures()
    {
        nf = 0;
        ns = 0;

        for (int i = 0; i < gx; i++)
        {
            for (int j = 0; j < gy; j++)
            {
                if (fische[i][j] != -1)
                {
                    drawFish(i, j);
                    nf++;
                }
                else if (haie[i][j] != -1)
                {
                    drawShark(i, j);
                    ns++;
                }
            }
        }
    }

    void drawShark(int x, int y)
    {
        int cx = a2x + (x*ax) - 4;
        int cy = a2y + (y*ay) - 4;

        wtG.setColor(Color.white);
        wtG.fillRect(cx, cy, 9, 9);
    }

    void drawFish(int x, int y)
    {
        int cx = a2x + (x*ax) - 3;
        int cy = a2y + (y*ay) - 3;
        wtG.setColor(Color.blue);
        wtG.fillOval(cx, cy, 6, 6);
    }


}