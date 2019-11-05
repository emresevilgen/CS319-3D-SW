import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignUpPanel extends JPanel{

    private PreGameFrame currentFrame;
    private JLabel nameLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField nameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton signUpButton;


    public SignUpPanel(PreGameFrame frame) {
        setCurrentFrame(frame);
        nameLabel = new JLabel("Name: ");
        usernameLabel = new JLabel("Username: ");
        passwordLabel = new JLabel("Password: ");
        nameField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        signUpButton = new JButton("Sign Up");
        Handler handler = new Handler();
        signUpButton.addActionListener(handler);
        this.setLayout( new GridLayout(4,2));
        this.add(nameLabel);
        this.add(nameField);
        this.add(usernameLabel);
        this.add(usernameField);
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(signUpButton);

    }

    public PreGameFrame getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(PreGameFrame currentFrame) {
        this.currentFrame = currentFrame;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(JLabel nameLabel) {
        this.nameLabel = nameLabel;
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

    public JTextField getNameField() {
        return nameField;
    }

    public void setNameField(JTextField nameField) {
        this.nameField = nameField;
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

    public JButton getSignUpButton() {
        return signUpButton;
    }

    public void setSignUpButton(JButton signUpButton) {
        this.signUpButton = signUpButton;
    }

    private class Handler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == signUpButton)
            {
                currentFrame.moveToMainMenuScreen();
            }
        }
    }

}
