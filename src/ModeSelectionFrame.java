import controller.GameController;
import model.Chessboard;
import model.ChessboardSize;
import model.Constant;
import model.Level;
import view.ChessGameFrame;

import javax.swing.*;
import java.awt.*;

public class ModeSelectionFrame extends JFrame {

    public ModeSelectionFrame() {
        setTitle("Select Mode");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1));

        // Level selection
        add(new JLabel("Select Level:"));
//        String[] levels = {"Level 1", "Level 2", "Level 3"};
//        JComboBox<String> levelComboBox = new JComboBox<>(levels);
//        add(levelComboBox);

        JComboBox<Level> levelSelector = new JComboBox<>();
        levelSelector.addItem(new Level(1, 100, 10)); // Level 1
        levelSelector.addItem(new Level(2, 200, 10));  // Level 2
        levelSelector.addItem(new Level(3, 300, 9));  // Level 2
        levelSelector.addItem(new Level(4, 400, 9));  // Level 2
        levelSelector.addItem(new Level(5, 500, 8));  // Level 2
        levelSelector.addItem(new Level(6, 600, 8));  // Level 2
        // Add more levels as needed
        add(levelSelector);

        // Mode selection
        add(new JLabel("Select Mode:"));
        String[] modes = {"Manual", "Automatic", "Crazy", "I-beam"};
        JComboBox<String> modeComboBox = new JComboBox<>(modes);
        add(modeComboBox);

        // Theme selection
        add(new JLabel("Select Theme:"));
        String[] themes = {"Christmas", "Halloween", "Spring"};
        JComboBox<String> themeComboBox = new JComboBox<>(themes);
        add(themeComboBox);

        // New Game button
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                String selectedTheme = (String) themeComboBox.getSelectedItem();
                String selectedMode = (String) modeComboBox.getSelectedItem();
                if (selectedMode.equals("I-beam")){
                    ChessboardSize.CHESSBOARD_ROW_SIZE = 8;
                    ChessboardSize.CHESSBOARD_COL_SIZE = 4;
                }

                ChessGameFrame mainFrame = new ChessGameFrame(1300, 900);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(selectedTheme));

                // Set level and mode in GameController
                if (selectedMode.equals("Manual")){
                    gameController.setAutoMode(false);
                    gameController.setCrazyMode(false);
                }else if (selectedMode.equals("Automatic")){
                    gameController.setAutoMode(true);
                    gameController.setCrazyMode(false);
                }else if (selectedMode.equals("I-beam")){
                    gameController.setAutoMode(true);
                    gameController.setCrazyMode(false);

                }else {
                    gameController.setAutoMode(true);
                    gameController.setCrazyMode(true);
                }
                Level selectedLevel = (Level) levelSelector.getSelectedItem();
                gameController.setTheme(selectedTheme);

                // Set labels and other components
                mainFrame.setGameController(gameController);
                gameController.setScoreLabel(mainFrame.getScoreLabel());
                gameController.setStepLabel(mainFrame.getStepLabel());
                gameController.setDifficultyLevelLabel(mainFrame.getDifficultyLevelLabel());
                gameController.setTargetScoreLabel(mainFrame.getTargetScoreLabel());
                gameController.setShuffleTimeLabel(mainFrame.getShuffleTimeLabel());
                gameController.setPromptTimeLabel(mainFrame.getPromptTimeLabel());
                gameController.setGameLevel(selectedLevel);

                mainFrame.setVisible(true);
                setVisible(false);
            });
        });
        add(newGameButton);

        setVisible(true);
    }
}
