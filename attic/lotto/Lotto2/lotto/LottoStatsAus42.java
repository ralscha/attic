package lotto;


class LottoStatsAus42 extends LottoStats
{
    protected boolean inRange(int nr, int jahr) {
        if (jahr > 1979) return true;
        if ((jahr == 1979) && (nr >= 14)) return true;
        return false;
    }
    
    
}
