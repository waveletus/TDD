package lxbtdd.com;

import java.util.ArrayList;
import java.util.List;

public class FootballPool {
    ArrayList<Game> pool;
    String date;
    int tiebreakerGameId;
    private String status;

    public FootballPool(String date) {
        this.date = date;
        pool = new ArrayList<Game>();
        status = "close";
    }

    public void addGame(String awayTeam, String homeTeam) {
        Game game = new Game(awayTeam, homeTeam);
        pool.add(game);
    }

    public String getHomeTeam(int gameNum) {
        return pool.get(gameNum).getHomeTeam();
    }

    public String getAwayTeam(int gameNum) {
        return pool.get(gameNum).getAwayTeam();
    }

    public void openPool() {
        status = "Open";
    }

    public String getStatus() {
        return status;
    }

    public List<Game> getGameList() {
        return pool;
    }

    public void postGameScore(int gameNum, int awayTeamScore, int homeTeamScore) {
        pool.get(gameNum).postScore(awayTeamScore, homeTeamScore);
    }

    public void setTieBreakerGame(int tiebreakerGameId) throws NoSuchGameException {
        if (!isValidGameNumber(tiebreakerGameId)) {
            throw new NoSuchGameException();
        }

        this.tiebreakerGameId = tiebreakerGameId;
    }

    private boolean isValidGameNumber(int tiebreakerGameId) {
        return tiebreakerGameId > 0 && tiebreakerGameId < pool.size();
    }
}
