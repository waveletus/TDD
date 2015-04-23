package lxbtdd.com;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class PoolResults {
    public String poolDate;
    public List<PlayerResultsData> m_playerResultsList;
    public String m_winningPlayersName;
    public PoolResults(String poolDate, String winner, List<PlayerResultsData> resultsList) {
        this.poolDate = poolDate;
        this.m_winningPlayersName = winner;
        this.m_playerResultsList = new ArrayList(resultsList);
    }
}
