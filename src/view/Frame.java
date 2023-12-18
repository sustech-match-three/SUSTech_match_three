package view;

import controller.GameController;

import javax.swing.*;

public class Frame extends JFrame {
    public void addNumberButton(GameController gameController, int number) {
        JButton button = new JButton(String.valueOf(number));
        button.addActionListener(e -> {
            gameController.targetScore = number * 100;
            gameController.difficultyLevel = number;
            gameController.step = 10;
            gameController.score = 0;
            gameController.getScoreLabel().setText("Score:" + gameController.score);
            gameController.getStepLabel().setText("Step: " + gameController.step);
            gameController.getDifficultyLevelLabel().setText("Level:" + gameController.difficultyLevel);
            gameController.getTargetScoreLabel().setText("target:" + gameController.targetScore);
            this.dispose();
        });

        button.setSize(10, 10);
        //  button.setFont(new Font("Rockwell", Font.BOLD, 20));
        this.add(button);
    }
}
