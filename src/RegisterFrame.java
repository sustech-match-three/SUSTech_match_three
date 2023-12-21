import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class RegisterFrame extends JFrame {
    public RegisterFrame() {
        setTitle("Register");
        setSize(300, 180);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Username:"));
        JTextField usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField();
        add(passwordField);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (registerUser(username, password)) {
                    JOptionPane.showMessageDialog(RegisterFrame.this, "Registration Successful!");
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(RegisterFrame.this, "Registration Failed or User Already Exists", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(registerButton);
    }

    private boolean registerUser(String username, String password) {
        try {
            File file = new File("resource/users.json");
            Gson gson = new Gson();

            FileReader reader = new FileReader(file);
            List<Map<String, String>> users = gson.fromJson(reader, new TypeToken<List<Map<String, String>>>(){}.getType());
            reader.close();

            if (users == null) {
                users = new ArrayList<>();
            }

            // 检查用户是否已存在
            if (users.stream().anyMatch(user -> user.get("username").equals(username))) {
                return false;
            }

            Map<String, String> newUser = new HashMap<>();
            newUser.put("username", username);
            newUser.put("password", password); // 注意：这里应该使用哈希密码

            users.add(newUser);

            // 保存新用户列表到文件
            Writer writer = new FileWriter(file);
            gson.toJson(users, writer);
            writer.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
