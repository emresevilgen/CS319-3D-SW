import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.*;

public class MainMenuPanel extends JPanel{
    private PreGameFrame currentFrame;
    private JButton createLobbyButton;
    private JButton joinLobbyButton;
    private JButton seeRankingButton;
    private JButton settingsButton;
    private JButton creditsButton;


    public MainMenuPanel(PreGameFrame frame) {
        setCurrentFrame(frame);
        createLobbyButton = new JButton("Create a Lobby");
        joinLobbyButton = new JButton("Join to the Existing Lobby");
        seeRankingButton = new JButton("See the Rankings");
        settingsButton = new JButton("Settings");
        creditsButton = new JButton("Credits");
        Handler handler = new Handler();
        createLobbyButton.addActionListener(handler);
        this.add(createLobbyButton);
        joinLobbyButton.addActionListener(handler);
        this.add(joinLobbyButton);
        seeRankingButton.addActionListener(handler);
        this.add(seeRankingButton);
        settingsButton.addActionListener(handler);
        this.add(settingsButton);
        creditsButton.addActionListener(handler);
        this.add(creditsButton);
    }

    public PreGameFrame getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(PreGameFrame currentFrame) {
        this.currentFrame = currentFrame;
    }

    public JButton getCreateLobbyButton() {
        return createLobbyButton;
    }

    public void setCreateLobbyButton(JButton createLobbyButton) {
        this.createLobbyButton = createLobbyButton;
    }

    public JButton getJoinLobbyButton() {
        return joinLobbyButton;
    }

    public void setJoinLobbyButton(JButton joinLobbyButton) {
        this.joinLobbyButton = joinLobbyButton;
    }

    public JButton getSeeRankingButton() {
        return seeRankingButton;
    }

    public void setSeeRankingButton(JButton seeRankingButton) {
        this.seeRankingButton = seeRankingButton;
    }

    public JButton getSettingsButton() {
        return settingsButton;
    }

    public void setSettingsButton(JButton settingsButton) {
        this.settingsButton = settingsButton;
    }

    public JButton getCreditsButton() {
        return creditsButton;
    }

    public void setCreditsButton(JButton creditsButton) {
        this.creditsButton = creditsButton;
    }

   private class Handler implements ActionListener {
       @Override
       public void actionPerformed(ActionEvent e) {
           if(e.getSource() == createLobbyButton)
           {
               currentFrame.moveToCreateLobbyScreen();
           }
           else if(e.getSource() == joinLobbyButton)
           {
                System.out.println( "add join to lobby pop up");
           }
           else if(e.getSource() == seeRankingButton)
           {
               currentFrame.moveToSeeThePlayersScreen();
           }
           else if(e.getSource() == settingsButton)
           {
               currentFrame.moveToSettingsScreen();
           }
           else if(e.getSource() == creditsButton)
           {
               currentFrame.moveToCreditsScreen();
           }
       }
   }

   public void joinLobby(String userId, String lobbyCode )
   {
       //implement later
   }
}
