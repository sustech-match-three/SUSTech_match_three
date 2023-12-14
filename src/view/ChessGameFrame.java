package view;

import controller.GameController;
import event.EventCenter;
import event.ExampleEvent;

import javax.swing.*;
import java.awt.*;

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

    public ChessGameFrame(int width, int height) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addChessboard();
        addStepLabel();
        addGradeButton();
        addSwapConfirmButton();
        addRestartButton();
        addNextStepButton();
        addLoadButton();
        addScoreLabel();
        addTargetScoreLabel();
        addLevelLabel();
        EventCenter.subscribe("ScoreEvent", event -> {
            if (event instanceof ExampleEvent exampleEvent) {
                System.out.println("Number:" + exampleEvent.a);
            }
        });
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
        this.scoreLabel = new JLabel("Score");
        scoreLabel.setLocation(HEIGTH-20, HEIGTH / 8);
        scoreLabel.setSize(200, 60);
        scoreLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(scoreLabel);
    }

    private void addStepLabel() {
        this.stepLabel = new JLabel("Step");
        stepLabel.setLocation(HEIGTH-20, HEIGTH / 12);
        stepLabel.setSize(200, 60);
        stepLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(stepLabel);
    }

    private void addTargetScoreLabel() {
        this.targetScoreLabel = new JLabel("Target");
        targetScoreLabel.setLocation(HEIGTH + 120, HEIGTH / 8);
        targetScoreLabel.setSize(200, 60);
        targetScoreLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(targetScoreLabel);
    }

    private void addLevelLabel() {
        this.difficultyLevelLabel = new JLabel("Level");
        difficultyLevelLabel.setLocation(HEIGTH + 120, HEIGTH / 12);
        difficultyLevelLabel.setSize(200, 60);
        difficultyLevelLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(difficultyLevelLabel);
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
            EventCenter.publish("ScoreEvent", new ExampleEvent(1));
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
