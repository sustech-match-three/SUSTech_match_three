import controller.GameController;
import model.Chessboard;
import view.ChessGameFrame;

import javax.swing.*;

public class Main {
    // test
    // wst test
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
            mainFrame.setGameController(gameController);
            gameController.setScoreLabel(mainFrame.getScoreLabel());
            gameController.setStepLabel(mainFrame.getStepLabel());
            mainFrame.setVisible(true);
        });
    }
}
