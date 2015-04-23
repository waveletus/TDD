package lxbtdd.com;

import java.util.ArrayList;
import java.util.List;

public class PoolData {
    /** handle to the database access methods */
    PoolDatabase _poolDB;

    public FootballPool getOpenPool() {
        FootballPool fbPool = null;
        List<?> poolList = _poolDB.getPoolWithStatus("Open");
        if (poolList.size() == 1) {
            fbPool = (FootballPool) poolList.get(0);
        }
        return fbPool;
    }

    public FootballPool getSpecificDatePool(String poolDdate) {
        FootballPool fbPool = null;
        List<?> poolList = _poolDB.getPoolWithDate(poolDdate);
        if (poolList.size() == 1) {
            fbPool = (FootballPool) poolList.get(0);
        }
        return fbPool;
    }

    public PoolDatabase get_poolDB() {
        return _poolDB;
    }

    public void set_poolDB(PoolDatabase _poolDB) {
        this._poolDB = _poolDB;
    }

    public void savePlayersPicks(PlayersPicks playerPicks) {
        _poolDB.savePlayersPicks(playerPicks);
    }

    public PlayersPicks getPlayersPicks(String playersName, String poolDate) {
        return _poolDB.getPlayersPicks(playersName, poolDate);
    }

    public List<String> getPlayers(String poolDate) {
        return _poolDB.getPlayers(poolDate);
    }

    public PoolResults calcPoolResults(String poolDate) {
        String winner = "";
        int mostCorrectPicks = 0;
        List<PlayerResultsData> resultsList = new ArrayList<PlayerResultsData>();

        FootballPool fbPool = getSpecificDatePool(poolDate);
        List<PlayersPicks> pickList = _poolDB.getAllPicks(poolDate);
        // For each set of player picks calculate the correct number of picks
        // Also calculate the most number of correct picks
        for (PlayersPicks picks : pickList) {
            PlayerResultsData playerResults = calcResults(picks, fbPool);
            resultsList.add(playerResults);
            if (playerResults.m_correctPicks > mostCorrectPicks) {
                mostCorrectPicks = playerResults.m_correctPicks;
                winner = playerResults.m_playerName;
            }
        }
        // Create Pool Results
        PoolResults results = new PoolResults(poolDate, winner, resultsList);
        // Store results in database m_poolDB.savePoolResults(results);
        // Return results
        return (results);
    }

    /**
     * Given a set of player picks and a Football pool this methods calculates the number of correct picks made by the
     * player and returns the results in a PlayerResultsData object
     *
     * @param picks
     * @param pool
     * @return
     */
    private PlayerResultsData calcResults(PlayersPicks picks, FootballPool pool) {
        List<Game> gameList = pool.getGameList();
        int numCorrectPicks = 0;

        for (int i = 0; i < gameList.size(); i++) {
            Game game = (Game) gameList.get(i);
            String winningTeamName = game.getWinningTeamName();
            if (winningTeamName.equals(picks.getPickedTeam(i))) {
                numCorrectPicks++;
            }
        }
        // System.out.println("numCorrectPicks=" + numCorrectPicks);
        return (new PlayerResultsData(picks.getPlayersName(), numCorrectPicks));
    }
}
