package lxbtdd.com;

import java.util.List;

public interface PoolDatabase {

    public List<FootballPool> getPoolWithStatus(String string);

    public void savePlayersPicks(PlayersPicks playerPicks);

    public PlayersPicks getPlayersPicks(String playersName, String poolDate);

    public List<PlayersPicks> getAllPicks(String poolDate);

    public List<FootballPool> getPoolWithDate(String poolDdate);

    public List<String> getPlayers(String poolDate);

}
