import Runable.MyRunnable;
import controller.GameController;
import model.Chessboard;
import view.ChessGameFrame;

import javax.swing.*;

public class Main {
    // test
    // wst test
    //游戏界面和登陆界面，bgm 的变化（游戏界面和登陆界面）和暂停，人机对战，双人对战，联网对战，快速模式和普通模式，游戏道具（洗牌道具等），游戏的储存和重新载入，结算界面（胜利和失败），动画，音效
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
            MyRunnable myRunnable=new MyRunnable();
            Thread thread=new Thread(myRunnable);
            thread.start();
        });
    }
}
