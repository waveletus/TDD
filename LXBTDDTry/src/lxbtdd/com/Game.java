package lxbtdd.com;

public class Game {
    private String homeTeam;
    private String awayTeam;
    private String pickedTeam;

    private int homeTeamScore;
    private int awayTeamScore;

    public Game(String awayTeam, String homeTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    String getHomeTeam() {
        return homeTeam;
    }

    String getAwayTeam() {
        return awayTeam;
    }

    public String getPickedTeam() {
        return pickedTeam;
    }

    public void setPickedTeam(String pickedTeam) {
        this.pickedTeam = pickedTeam;
    }

    public void postScore(int awayTeamScore, int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;

    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public String getWinningTeamName() {
        return homeTeamScore > awayTeamScore ? homeTeam : awayTeam;
    }
}
