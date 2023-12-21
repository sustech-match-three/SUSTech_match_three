import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class LoginFrame extends JFrame {

    public LoginFrame() {
        setTitle("Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(3, 2));

        add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        add(passwordField);

        JButton loginButton = new JButton("Login");

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 打开注册窗口
                new RegisterFrame().setVisible(true);
            }
        });
        add(registerButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (validateLogin(username, password)) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Login Successful!");
                    new ModeSelectionFrame(); // 打开模式选择界面
                    setVisible(false); // 隐藏登录窗口
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Invalid Credentials", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(loginButton);

        setVisible(true);
    }

    private boolean validateLogin(String username, String password) {
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader("resource/users.json");
            List<Map<String, String>> users = gson.fromJson(reader, new TypeToken<List<Map<String, String>>>(){}.getType());
            return users.stream().anyMatch(user -> user.get("username").equals(username) && user.get("password").equals(password));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame();
            }
        });
    }
}
