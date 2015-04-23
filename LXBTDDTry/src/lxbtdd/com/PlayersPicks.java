package lxbtdd.com;

import java.util.List;

public class PlayersPicks {

    private String _playersName;
    private String _poolDate;
    private List<Game> _gameList;

    public PlayersPicks(String playersName, String poolDate, List<Game> gameList) {
        _gameList = gameList;
        _playersName = playersName;
        _poolDate = poolDate;
    }

    public void makePick(int i, String homeTeam) {
        _gameList.get(i).setPickedTeam(homeTeam);
    }

    public int size() {
        return _gameList.size();
    }

    public String getHomeTeam(int i) {
        return _gameList.get(i).getHomeTeam();
    }

    public String getPickedTeam(int i) {
        return _gameList.get(i).getPickedTeam();
    }

    public String getPlayersName() {
        return _playersName;
    }

    public String getPoolDate() {
        return _poolDate;
    }

    public void setTiebreakScore(int i) {
        // TODO Auto-generated method stub
        
    }
}
