import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignInPanel extends JPanel {

    private PreGameFrame currentFrame;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel signUpLink;
    private JButton signInButton;


    public SignInPanel(PreGameFrame frame) {
        this.setCurrentFrame(frame);
        usernameLabel = new JLabel("Username: ");
        passwordLabel = new JLabel("Password: ");
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        signInButton = new JButton("Sign In");
        Handler handler = new Handler();
        signInButton.addActionListener(handler);
        signUpLink = new JLabel("If you don't have an account, please sign up.");
        this.setLayout( new GridLayout(3,2));
        this.add(usernameLabel);
        this.add(usernameField);
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(signInButton);
        this.add(signUpLink);
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

    public JLabel getSignUpLink() {
        return signUpLink;
    }

    public void setSignUpLink(JLabel signUpLink) {
        this.signUpLink = signUpLink;
    }

    public JButton getSignInButton() {
        return signInButton;
    }

    public void setSignInButton(JButton signInButton) {
        this.signInButton = signInButton;
    }

    private class Handler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == signInButton)
            {
                currentFrame.moveToMainMenuScreen();
            }
        }
    }

}