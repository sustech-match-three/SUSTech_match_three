import controller.GameController;
import model.Chessboard;
import view.ChessGameFrame;

import javax.swing.*;

public class Main {
    // test
    // wst test
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1300, 900);
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard("Christmas"));
            mainFrame.setGameController(gameController);
            gameController.setScoreLabel(mainFrame.getScoreLabel());
            gameController.setStepLabel(mainFrame.getStepLabel());
            gameController.setDifficultyLevelLabel(mainFrame.getDifficultyLevelLabel());
            gameController.setTargetScoreLabel(mainFrame.getTargetScoreLabel());
            gameController.setShuffleTimeLabel(mainFrame.getShuffleTimeLabel());
            gameController.setPromptTimeLabel(mainFrame.getPromptTimeLabel());
            mainFrame.setVisible(true);
//            MyRunnable myRunnable=new MyRunnable();
//            Thread thread=new Thread(myRunnable);
//            thread.start();
        });
    }
}
