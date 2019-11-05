import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {

    private PreGameFrame currentFrame;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox soundEffects;
    private JCheckBox audioDescription;
    private JButton cancelButton;
    private JButton saveButton;

    public SettingsPanel(PreGameFrame frame) {
        setCurrentFrame(frame);
        usernameLabel = new JLabel("Username: ");
        passwordLabel = new JLabel("Password: ");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        soundEffects = new JCheckBox("Sound Effects");
        audioDescription = new JCheckBox("Audio Description");
        cancelButton = new JButton("Cancel");
        saveButton = new JButton("Save");
        Handler handler = new Handler();
        cancelButton.addActionListener(handler);
        saveButton.addActionListener(handler);
        this.setLayout( new GridLayout(4,2));
        this.add(usernameLabel);
        this.add(usernameField);
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(soundEffects);
        this.add(audioDescription);
        this.add(cancelButton);
        this.add(saveButton);
    }

    public void saveUsername(String userId, String username)
    {
        //server
    }

    public void savePassword(String userId, String password)
    {
        //server
    }

    private class Handler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == saveButton)
            {
                //saveUsername
                //savePassword
                //server a data gönder
                //pop up response u göster
            }
            else if(e.getSource() == cancelButton)
            {
                System.out.println("2");
                currentFrame.moveToMainMenuScreen();
            }
        }
    }

    public PreGameFrame getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(PreGameFrame currentFrame) {
        this.currentFrame = currentFrame;
    }

    public JLabel getUsernameLabel() {
        return usernameLabel;
    }

    public void setUsernameLabel(JLabel usernameLabel) {
        this.usernameLabel = usernameLabel;
    }

    public JLabel getPasswordLabel() {
        return passwordLabel;
    }

    public void setPasswordLabel(JLabel passwordLabel) {
        this.passwordLabel = passwordLabel;
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public void setUsernameField(JTextField usernameField) {
        this.usernameField = usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public JCheckBox getSoundEffects() {
        return soundEffects;
    }

    public void setSoundEffects(JCheckBox soundEffects) {
        this.soundEffects = soundEffects;
    }

    public JCheckBox getAudioDescription() {
        return audioDescription;
    }

    public void setAudioDescription(JCheckBox audioDescription) {
        this.audioDescription = audioDescription;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(JButton cancelButton) {
        this.cancelButton = cancelButton;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(JButton saveButton) {
        this.saveButton = saveButton;
    }

}
