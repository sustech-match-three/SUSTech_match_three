package view;

import controller.GameController;
import event.EventCenter;
import event.ExampleEvent;
import model.Level;
import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;
/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;

    private final int ONE_CHESS_SIZE;

    private GameController gameController;

    private ChessboardComponent chessboardComponent;

    public JLabel scoreLabel;
    public JLabel stepLabel;
    private JLabel difficultyLevelLabel;
    private JLabel targetScoreLabel;

    private JLabel shuffleTimeLabel;

    private JLabel promptTimeLabel;

    public ChessGameFrame(int width, int height) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / (CHESSBOARD_ROW_SIZE.getNum()+1);

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addChessboard();
        addStepLabel();

//        addGradeButton();
        initLevelSelection();
        addSwapConfirmButton();
        addRestartButton();
        addNextStepButton();
        addShuffleButton();
        addPromptButton();
        addLoadButton();
        addScoreLabel();
        addTargetScoreLabel();
        addLevelLabel();
        addShuffleTimeLabel();
        addPromptTimeLabel();

//        EventCenter.subscribe("ScoreEvent", event -> {
//            if (event instanceof ExampleEvent exampleEvent) {
//                System.out.println("Number:" + exampleEvent.a);
//            if (exampleEvent.a==1){
//                JOptionPane.showMessageDialog(this, "Victory");
//            }else {
//                JOptionPane.showMessageDialog(this, "Defeat");
//            }
//            }
//        });
    }

    public JLabel getStepLabel() {
        return stepLabel;
    }

    public void setStepLabel(JLabel stepLabel) {
        this.stepLabel = stepLabel;
    }

    public JLabel getDifficultyLevelLabel() {
        return difficultyLevelLabel;
    }

    public JLabel getTargetScoreLabel() {
        return targetScoreLabel;
    }

    public JLabel getShuffleTimeLabel() {
        return shuffleTimeLabel;
    }

    public JLabel getPromptTimeLabel() {
        return promptTimeLabel;
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addScoreLabel() {
        this.scoreLabel = new JLabel("Score:0");
        scoreLabel.setLocation(HEIGTH-20, HEIGTH / 8);
        scoreLabel.setSize(200, 60);
        scoreLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(scoreLabel);
    }

    private void addStepLabel() {
        this.stepLabel = new JLabel("Step:10");
        stepLabel.setLocation(HEIGTH-20, HEIGTH / 12);
        stepLabel.setSize(200, 60);
        stepLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(stepLabel);
    }

    private void addTargetScoreLabel() {
        this.targetScoreLabel = new JLabel("Target:100");
        targetScoreLabel.setLocation(HEIGTH + 120, HEIGTH / 8);
        targetScoreLabel.setSize(200, 60);
        targetScoreLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(targetScoreLabel);
    }

    private void addLevelLabel() {
        this.difficultyLevelLabel = new JLabel("Level:1");
        difficultyLevelLabel.setLocation(HEIGTH + 120, HEIGTH / 12);
        difficultyLevelLabel.setSize(200, 60);
        difficultyLevelLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(difficultyLevelLabel);
    }

    private void addShuffleTimeLabel() {
        this.shuffleTimeLabel = new JLabel("Shuffles: " + "3");
        // 设定标签位置和大小
        shuffleTimeLabel.setLocation(HEIGTH + 120, HEIGTH / 6); // 位置可能需要调整以适应您的界面布局
        shuffleTimeLabel.setSize(200, 60);
        shuffleTimeLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(shuffleTimeLabel);
    }

    private void addPromptTimeLabel() {
        this.promptTimeLabel = new JLabel("Prompt: " + "5");
        // 设定标签位置和大小
        promptTimeLabel.setLocation(HEIGTH - 20, HEIGTH / 6); // 位置可能需要调整以适应您的界面布局
        promptTimeLabel.setSize(200, 60);
        promptTimeLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(promptTimeLabel);
    }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    public void setScoreLabel(JLabel scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addGradeButton() {
        JButton button = new JButton("Difficulty selection");
        button.addActionListener(e -> {
            Frame sb = new Frame();
            sb.setTitle("Difficulty selection");
            sb.setLocation(HEIGTH, HEIGTH / 10);
            sb.setSize(250, 180);
            sb.setVisible(true);
            for (int i = 1; i <8 ; i++) {
                sb.addNumberButton(gameController,i);
            }
            sb.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 20));
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    public void initLevelSelection() {
        // Create a dropdown for level selection
        JComboBox<Level> levelSelector = new JComboBox<>();
        levelSelector.addItem(new Level(1, 100, 10)); // Level 1
        levelSelector.addItem(new Level(2, 200, 10));  // Level 2
        levelSelector.addItem(new Level(3, 300, 9));  // Level 2
        levelSelector.addItem(new Level(4, 400, 9));  // Level 2
        levelSelector.addItem(new Level(5, 500, 8));  // Level 2
        levelSelector.addItem(new Level(6, 600, 8));  // Level 2
        // Add more levels as needed

        levelSelector.addActionListener(e -> {
            Level selectedLevel = (Level) levelSelector.getSelectedItem();
            gameController.startNewGame(selectedLevel);
        });

        levelSelector.setLocation(HEIGTH, HEIGTH / 10 + 120);
        levelSelector.setSize(200, 30); // 设置合适的大小
        levelSelector.setFont(new Font("Rockwell", Font.BOLD, 16)); // 设置字体，如果需要
        add(levelSelector); // 将下拉菜单添加到窗体或面板中
    }

    private void addShuffleButton(){
        JButton shuffleButton = new JButton("Shuffle");
        shuffleButton.addActionListener((e) -> gameController.shuffleGame());
        shuffleButton.setLocation(HEIGTH, HEIGTH / 10 + 520);
        shuffleButton.setSize(200, 60);
        shuffleButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(shuffleButton);
    }

    private void addPromptButton(){
        JButton promptButton = new JButton("Prompt");
        promptButton.addActionListener((e) -> gameController.prompt());
        promptButton.setLocation(HEIGTH, HEIGTH / 10 + 600);
        promptButton.setSize(200, 60);
        promptButton.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(promptButton);
    }



    private void addSwapConfirmButton() {
        JButton button = new JButton("Confirm Swap");
        button.addActionListener((e) -> chessboardComponent.swapChess());
        button.setLocation(HEIGTH, HEIGTH / 10 + 200);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addNextStepButton() {
        JButton button = new JButton("Next Step");
        button.addActionListener((e) -> {
            chessboardComponent.nextStep();
           if(this.gameController.step>=0&&this.gameController.score>=gameController.targetScore) {
               EventCenter.publish("ScoreEvent", new ExampleEvent(1));
           } else if (gameController.step == 0) {
               EventCenter.publish("ScoreEvent", new ExampleEvent(-1));
           }
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 280);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addRestartButton() {
        JButton button = new JButton("Restart");
        button.addActionListener((e) -> chessboardComponent.Restart());
        button.setLocation(HEIGTH, HEIGTH / 10 + 440);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            System.out.println(path);
//            gameController.loadGameFromFile(path);
        });
    }



}
