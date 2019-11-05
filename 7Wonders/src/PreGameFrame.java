import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PreGameFrame extends JFrame{

    //Lobby lobby;
    SignInPanel signInPanel;
    SignUpPanel signUpPanel;
    MainMenuPanel mainMenuPanel;
    CreateLobbyPanel createLobbyPanel;
    SeePlayersPanel seePlayersPanel;
    SettingsPanel settingsPanel;
    RankingsPanel rankingsPanel;
    CreditsPanel creditsPanel;
    JPanel currentPanel;

    public PreGameFrame()  {
        //private lobby = new Lobby;
        signInPanel = new SignInPanel(this);
        currentPanel = signInPanel;
        this.add(signInPanel);
    }

    public void getLobbyDataFromServer(){
        // implement later
    }

    public void moveToSignUpScreen()
    {
        this.remove(signInPanel);
        signUpPanel = new SignUpPanel(this);
        this.add(signUpPanel);
        currentPanel = signUpPanel;
        this.validate();
    }

    public void moveToMainMenuScreen()
    {
        System.out.println("1");
        this.remove(currentPanel);
        mainMenuPanel = new MainMenuPanel(this);
        this.add(mainMenuPanel);
        currentPanel = mainMenuPanel;
        this.validate();
    }

    public void moveToCreateLobbyScreen()
    {
        this.remove(mainMenuPanel);
        createLobbyPanel = new CreateLobbyPanel(this);
        this.add(createLobbyPanel);
        currentPanel = createLobbyPanel;
        this.validate();
    }

    public void moveToSeeThePlayersScreen()
    {
        this.remove(mainMenuPanel);
        seePlayersPanel = new SeePlayersPanel(this);
        this.add(seePlayersPanel);
        currentPanel = seePlayersPanel;
        this.validate();
    }

    public void moveToSettingsScreen()
    {
        this.remove(mainMenuPanel);
        settingsPanel = new SettingsPanel(this);
        this.add(settingsPanel);
        currentPanel = settingsPanel;
        this.validate();
    }

    public void moveToCreditsScreen()
    {
        this.remove(mainMenuPanel);
        creditsPanel = new CreditsPanel(this);
        this.add(creditsPanel);
        currentPanel = creditsPanel;
        this.validate();
    }

    public void moveToRankingsScreen()
    {
        this.remove(mainMenuPanel);
        rankingsPanel = new RankingsPanel(this);
        this.add(rankingsPanel);
        currentPanel = rankingsPanel;
        this.validate();
    }

    public SignInPanel getSignInPanel() {
        return signInPanel;
    }

    public void setSignInPanel(SignInPanel signInPanel) {
        this.signInPanel = signInPanel;
    }

    public SignUpPanel getSignUpPanel() {
        return signUpPanel;
    }

    public void setSignUpPanel(SignUpPanel signUpPanel) {
        this.signUpPanel = signUpPanel;
    }

    public MainMenuPanel getMainMenuPanel() {
        return mainMenuPanel;
    }

    public void setMainMenuPanel(MainMenuPanel mainMenuPanel) {
        this.mainMenuPanel = mainMenuPanel;
    }

    public CreateLobbyPanel getCreateLobbyPanel() {
        return createLobbyPanel;
    }

    public void setCreateLobbyPanel(CreateLobbyPanel createLobbyPanel) {
        this.createLobbyPanel = createLobbyPanel;
    }

    public SeePlayersPanel getSeePlayersPanel() {
        return seePlayersPanel;
    }

    public void setSeePlayersPanel(SeePlayersPanel seePlayersPanel) {
        this.seePlayersPanel = seePlayersPanel;
    }

    public SettingsPanel getSettingsPanel() {
        return settingsPanel;
    }

    public void setSettingsPanel(SettingsPanel settingsPanel) {
        this.settingsPanel = settingsPanel;
    }

    public RankingsPanel getRankingPanel() {
        return rankingsPanel;
    }

    public void setRankingPanel(RankingsPanel rankingPanel) {
        this.rankingsPanel = rankingPanel;
    }

    public CreditsPanel getCreditsPanel() {
        return creditsPanel;
    }

    public void setCreditsPanel(CreditsPanel creditsPanel) {
        this.creditsPanel = creditsPanel;
    }
}

