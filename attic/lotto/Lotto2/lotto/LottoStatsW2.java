package lotto;


class LottoStatsW2 extends LottoStats
{
    protected boolean inRange(int nr, int jahr) {
        if (jahr >= 1997)
            return true;
        else
            return false;
    }
    

    
}
