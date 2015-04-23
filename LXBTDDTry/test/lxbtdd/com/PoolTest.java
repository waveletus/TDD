package lxbtdd.com;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.junit.Test;

public class PoolTest {
    List<FootballPool> poolList;

    private List<FootballPool> createFbPoolList() {
        poolList = new Vector<FootballPool>();
        FootballPool pool = new FootballPool("9/17/2003");
        pool.addGame("Kansas City", "Green Bay");
        pool.addGame("Houston", "Tennessee");
        pool.addGame("Carolina", "Indianapolis");
        pool.addGame("NY Giants", "New England");
        pool.addGame("Chicago", "New Orleans");
        pool.addGame("Oakland", "Cleveland");
        pool.addGame("Philadelphia", "Dallas");
        pool.addGame("Tampa Bay", "Washington");
        pool.addGame("Miami", "Jacksonville");
        pool.addGame("Pittsburgh", "Denver");
        pool.addGame("Buffalo", "NY Jets");
        pool.addGame("Baltimore", "Arizona");
        pool.addGame("San Francisco", "Seattle");
        pool.addGame("Atlanta", "St. Louis");

        pool.postGameScore(0, 28, 31);
        pool.postGameScore(1, 8, 24);
        pool.postGameScore(2, 3, 17);
        pool.postGameScore(3, 21, 31);
        pool.postGameScore(4, 13, 21);
        pool.postGameScore(5, 28, 35);
        pool.postGameScore(6, 28, 3);
        pool.postGameScore(7, 14, 3);
        pool.postGameScore(8, 31, 28);
        pool.postGameScore(9, 13, 21);
        pool.postGameScore(10, 10, 17);
        pool.postGameScore(11, 21, 7);
        pool.postGameScore(12, 3, 13);
        pool.postGameScore(13, 24, 14);
        try {
            pool.setTieBreakerGame(13);
        } catch (NoSuchGameException e) {
            e.printStackTrace();
        }

        poolList.add(pool);

        return poolList;
    }

    private List<PlayersPicks> createPlayersPicks() {
        List<PlayersPicks> pickList = new ArrayList<PlayersPicks>();
        FootballPool fbPool = (FootballPool) createFbPoolList().get(0);
        List<Game> gameList = fbPool.getGameList();
        PlayersPicks playerPicks = new PlayersPicks("EddyC", "2003-09-18", gameList);
        for (int i = 0; i < gameList.size(); i++) {
            Game game = (Game) gameList.get(i);
            playerPicks.makePick(i, game.getHomeTeam());
        }

        playerPicks.setTiebreakScore(31);

        pickList.add(playerPicks);

        fbPool = (FootballPool) createFbPoolList().get(0);
        gameList = new ArrayList<>(fbPool.getGameList());
        playerPicks = new PlayersPicks("ScottM", "2003-09-18", gameList);
        for (int i = 0; i < gameList.size(); i++) {
            Game game = (Game) gameList.get(i);
            playerPicks.makePick(i, game.getHomeTeam());
        }
        playerPicks.setTiebreakScore(34);

        pickList.add(playerPicks);

        fbPool = (FootballPool) createFbPoolList().get(0);
        gameList = new ArrayList(fbPool.getGameList());
        playerPicks = new PlayersPicks("JenC", "2003-09-18", gameList);
        for (int i = 0; i < gameList.size(); i++) {
            Game game = (Game) gameList.get(i);
            playerPicks.makePick(i, game.getAwayTeam());
        }
        playerPicks.setTiebreakScore(21);
        pickList.add(playerPicks);
        return pickList;
    }

    /**
     * Create a set of Pool results to be used in the tests
     *
     * @return
     */
    private PoolResults createPoolResults() {
        List<PlayerResultsData> resultsList = new ArrayList<PlayerResultsData>();

        PlayerResultsData playerResults = new PlayerResultsData("EddyC", 9);
        resultsList.add(playerResults);

        playerResults = new PlayerResultsData("ScottM", 9);
        resultsList.add(playerResults);

        playerResults = new PlayerResultsData("JenC", 5);
        resultsList.add(playerResults);

        PoolResults results = new PoolResults("2003-09-18", "ScottM", resultsList);
        return (results);
    }

    @Test
    public void testPoolCreate() throws Exception {
        FootballPool pool = new FootballPool("9/07/2003");
        pool.addGame("New York Jets", "Miami Dolphins");
        pool.addGame("San Francisco 49ers", "New York Giants");

        assertEquals("Game 1 away team", "Miami Dolphins", pool.getHomeTeam(0));
        assertEquals("Game 2 away team", "New York Giants", pool.getHomeTeam(1));
        assertEquals("Game 1 home team", "New York Jets", pool.getAwayTeam(0));
        assertEquals("Game 2 home team", "San Francisco 49ers", pool.getAwayTeam(1));
    }

    @Test
    public void testTiebreakerSelect() throws Exception {
        FootballPool pool = new FootballPool("9/7/2003");
        pool.addGame("Jets", "Falcons");
        pool.addGame("Giants", "49ers");
        pool.setTieBreakerGame(1);
        try {
            pool.setTieBreakerGame(3);
            fail("Permitted selection of non-existent game as the tiebreaker");
        } catch (NoSuchGameException e) {
        }
    }

    @Test
    public void testSetPoolToOpen() {
        FootballPool pool = new FootballPool("9/07/2003");
        pool.addGame("Jets", "Dolphins");
        pool.addGame("49ers", "Giants");
        pool.openPool();
        assertEquals("Open", pool.getStatus());
    }

    @Test
    public void testGetOpenPoolGameList() {
        // arrange
        poolList = createFbPoolList();
        PoolDatabase mockPoolDB = mock(PoolDatabase.class);
        when(mockPoolDB.getPoolWithStatus("Open")).thenReturn(poolList);

        // act
        PoolData poolData = new PoolData();
        poolData.set_poolDB(mockPoolDB);
        FootballPool pool = poolData.getOpenPool();

        // assert
        assertEquals("Kansas City", pool.getAwayTeam(0));
        assertEquals("Green Bay", pool.getHomeTeam(0));
        assertEquals("Houston", pool.getAwayTeam(1));
        assertEquals("Tennessee", pool.getHomeTeam(1));
        assertEquals("Carolina", pool.getAwayTeam(2));
        assertEquals("Indianapolis", pool.getHomeTeam(2));
        assertEquals("NY Giants", pool.getAwayTeam(3));
        assertEquals("New England", pool.getHomeTeam(3));
        assertEquals("Chicago", pool.getAwayTeam(4));
        assertEquals("New Orleans", pool.getHomeTeam(4));
        assertEquals("Oakland", pool.getAwayTeam(5));
        assertEquals("Cleveland", pool.getHomeTeam(5));
        assertEquals("Philadelphia", pool.getAwayTeam(6));
        assertEquals("Dallas", pool.getHomeTeam(6));
        assertEquals("Tampa Bay", pool.getAwayTeam(7));
        assertEquals("Washington", pool.getHomeTeam(7));
        assertEquals("Miami", pool.getAwayTeam(8));
        assertEquals("Jacksonville", pool.getHomeTeam(8));
        assertEquals("Pittsburgh", pool.getAwayTeam(9));
        assertEquals("Denver", pool.getHomeTeam(9));
        assertEquals("Buffalo", pool.getAwayTeam(10));
        assertEquals("NY Jets", pool.getHomeTeam(10));
        assertEquals("Baltimore", pool.getAwayTeam(11));
        assertEquals("Arizona", pool.getHomeTeam(11));
        assertEquals("San Francisco", pool.getAwayTeam(12));
        assertEquals("Seattle", pool.getHomeTeam(12));
        assertEquals("Atlanta", pool.getAwayTeam(13));
        assertEquals("St. Louis", pool.getHomeTeam(13));

        verify(mockPoolDB).getPoolWithStatus("Open");
    }

    @Test
    public void testStorePlayersPicks() {
        // arrange
        poolList = createFbPoolList();
        PoolDatabase mockPoolDB = mock(PoolDatabase.class);
        when(mockPoolDB.getPoolWithStatus("Open")).thenReturn(poolList);

        PoolData poolData = new PoolData(); // Set PoolDB to mock object
        poolData.set_poolDB(mockPoolDB); // Get list of games for the open pool
        FootballPool pool = poolData.getOpenPool();
        List<Game> gameList = pool.getGameList();

        // act
        PlayersPicks playerPicks = new PlayersPicks("EddyC", "9/17/2003", gameList);
        for (int i = 0; i < gameList.size(); i++) {
            Game game = (Game) gameList.get(i);
            playerPicks.makePick(i, game.getHomeTeam());
        }
        poolData.savePlayersPicks(playerPicks);

        when(mockPoolDB.getPlayersPicks("EddyC", "9/17/2003")).thenReturn(playerPicks);

        // verify
        PlayersPicks savedPicks = poolData.getPlayersPicks("EddyC", "9/17/2003");
        for (int i = 0; i < savedPicks.size(); i++) {
            String homeTeam = savedPicks.getHomeTeam(i);
            assertEquals(homeTeam, savedPicks.getPickedTeam(i));
        }
    }

    // excercise
    @Test
    public void testPostGameScore() {
        Game game = new Game("Kansas City", "Green Bay");
        game.postScore(28, 31);
        int homeScore = game.getHomeTeamScore();
        assertEquals(31, homeScore);
        int awayScore = game.getAwayTeamScore();
        assertEquals(28, awayScore);
        String winningTeam = game.getWinningTeamName();
        assertEquals("Green Bay", winningTeam);
    }

    /**
     * Test used to ensure that a football pool can be updated with the scores * of the games and that the winning team
     * can be calculated correctly
     */
    @Test
    public void testPostGameScoresToPool() {
        // Get mock object
        // arrange
        poolList = createFbPoolList();
        PoolDatabase mockPoolDB = mock(PoolDatabase.class);
        when(mockPoolDB.getPoolWithStatus("Open")).thenReturn(poolList);

        PoolData poolData = new PoolData(); // Set PoolDB to mock object
        poolData.set_poolDB(mockPoolDB); // Get list of games for the open pool
        FootballPool pool = poolData.getOpenPool();

        // act
        pool.postGameScore(0, 28, 31);
        pool.postGameScore(3, 7, 3);
        pool.postGameScore(5, 8, 31);
        pool.postGameScore(10, 14, 7);

        // assert
        List<Game> gameList = pool.getGameList();
        Game game = gameList.get(0);
        assertEquals(28, game.getAwayTeamScore());
        assertEquals(31, game.getHomeTeamScore());
        assertEquals("Green Bay", game.getWinningTeamName());
        game = gameList.get(3);
        assertEquals(7, game.getAwayTeamScore());
        assertEquals(3, game.getHomeTeamScore());
        assertEquals("NY Giants", game.getWinningTeamName());
        game = gameList.get(5);
        assertEquals(8, game.getAwayTeamScore());
        assertEquals(31, game.getHomeTeamScore());
        assertEquals("Cleveland", game.getWinningTeamName());
        game = gameList.get(10);
        assertEquals(14, game.getAwayTeamScore());
        assertEquals(7, game.getHomeTeamScore());
        assertEquals("Buffalo", game.getWinningTeamName());
    }

    @Test
    /**
     * Test to make sure that the winning player is correctly picked after
     * posting the game scores
     */
    public void testCalcWinner() {
        // arrange
        poolList = createFbPoolList();
        PoolDatabase mockPoolDB = mock(PoolDatabase.class);
        when(mockPoolDB.getPoolWithDate("2003-09-18")).thenReturn(poolList);

        PoolData poolData = new PoolData();
        poolData.set_poolDB(mockPoolDB);
        
        List<PlayersPicks> playersPick = createPlayersPicks();
        when(mockPoolDB.getAllPicks("2003-09-18")).thenReturn(playersPick);

        // act
        PoolResults results = poolData.calcPoolResults("2003-09-18");

        // Check the winner and results for each player to make sure they
        // are correct
        //@TODO: Bug, not considering the tie break rule
        assertEquals("EddyC", results.m_winningPlayersName);
        List<PlayerResultsData> resultsList = results.m_playerResultsList;
        PlayerResultsData playerResults = resultsList.get(0);
        assertEquals("EddyC", playerResults.m_playerName);
        assertEquals(9, playerResults.m_correctPicks);
        playerResults = resultsList.get(1);
        assertEquals("ScottM", playerResults.m_playerName);
        assertEquals(9, playerResults.m_correctPicks);
        playerResults = resultsList.get(2);
        assertEquals("JenC", playerResults.m_playerName);
        assertEquals(5, playerResults.m_correctPicks);
    }

}