package model;


public class Player {
    String secretSkill;
    Card[] playerCards;
    Board board;
    String playerId;
    String leftPlayerId;
    String rightPlayerId;
    int victoryPoints;
    int militaryPoints;
    int coin;

    public Player()
    {
        //secretSkill = ;
        playerCards = new Card[7];
        board = new Board();
        //playerId = ;
        //leftPlayerId = ;
        //rightPlayerId = ;
        victoryPoints = 0;
        militaryPoints = 0;
        coin = 3 ;
    }

    public String getSecretSkill() {
        return secretSkill;
    }

    public void setSecretSkill(String secretSkill) {
        this.secretSkill = secretSkill;
    }

    public Card[] getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(Card[] playerCards) {
        this.playerCards = playerCards;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getLeftPlayerId() {
        return leftPlayerId;
    }

    public void setLeftPlayerId(String leftPlayerId) {
        this.leftPlayerId = leftPlayerId;
    }

    public String getRightPlayerId() {
        return rightPlayerId;
    }

    public void setRightPlayerId(String rightPlayerId) {
        this.rightPlayerId = rightPlayerId;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public int getMilitaryPoints() {
        return militaryPoints;
    }

    public void setMilitaryPoints(int militaryPoints) {
        this.militaryPoints = militaryPoints;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

}
