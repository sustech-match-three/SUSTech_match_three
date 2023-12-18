package controller;

import listener.GameListener;
import model.*;
import view.CellComponent;
import view.ChessComponent;
import view.ChessboardComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and
 * onPlayerClickChessPiece()]
 */
public class GameController implements GameListener {

    private Chessboard model;
    private ChessboardComponent view;
    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint1;
    private ChessboardPoint selectedPoint2;

    public int score=0;
    public int step=10;
    public int difficultyLevel=1;
    public int targetScore=100;
    private JLabel scoreLabel;
    private JLabel stepLabel;
    private JLabel difficultyLevelLabel;
    private JLabel targetScoreLabel;

    private boolean isAutoMode = false;

    public void setDifficultyLevelLabel(JLabel difficultyLevelLabel) {
        this.difficultyLevelLabel = difficultyLevelLabel;
    }

    public void setTargetScoreLabel(JLabel targetScoreLabel) {
        this.targetScoreLabel = targetScoreLabel;
    }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    public JLabel getStepLabel() {
        return stepLabel;
    }

    public JLabel getDifficultyLevelLabel() {
        return difficultyLevelLabel;
    }

    public JLabel getTargetScoreLabel() {
        return targetScoreLabel;
    }

    public ChessboardComponent getView() {
        return view;
    }

    public void setStepLabel(JLabel stepLabel) {
        this.stepLabel = stepLabel;
    }

    public void setScoreLabel(JLabel scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    public void setAutoMode(boolean isAutoMode) {
        this.isAutoMode = isAutoMode;
    }

    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;
        view.registerController(this);
        view.initiateChessComponent(model);
        view.repaint();
    }

    public void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                model = new Chessboard();
                model.getGrid()[i][j].setPiece(new ChessPiece(Util.RandomPick(new String[]{"\uD83E\uDDBF", "⚪", "▲", "🔶", "\uD83D\uDD3B"})));
                view.removeAllChessComponentsAtGrids();
                view.initiateChessComponent(model);
                view.repaint();
            }
        }
    }

    public void startNewGame(Level selectedLevel) {
        // 重置游戏状态
        this.score = 0;
        this.step = selectedLevel.getMoveLimit();
        this.difficultyLevel = selectedLevel.getDifficultyLevel();
        this.targetScore = selectedLevel.getTargetScore();

        // 更新显示的分数和步数
        scoreLabel.setText("Score: " + score);
        stepLabel.setText("Steps: " + step);
        difficultyLevelLabel.setText("Level: " + difficultyLevel);
        targetScoreLabel.setText("Target: " + targetScore);

        // 重置棋盘
        model.resetBoard();  // 假设 Chessboard 类有一个方法来重置棋盘
        view.removeAllChessComponentsAtGrids();
        view.initiateChessComponent(model);
        view.repaint();

    }

    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
    }

    @Override
    public void onPlayerSwapChess() {
//        model.swapChessPiece(selectedPoint1, selectedPoint2);
//        ChessComponent chess1 = view.removeChessComponentAtGrid(selectedPoint2);
//        ChessComponent chess2 = view.removeChessComponentAtGrid(selectedPoint1);
//        view.setChessComponentAtGrid(selectedPoint2, chess2);// TODO: Init your swap function here.
//        view.setChessComponentAtGrid(selectedPoint1, chess1);
//        chess1.repaint();
//        chess2.repaint();
//        selectedPoint1 = null;
//        selectedPoint2 = null;// TODO: Init your swap function here.
//        System.out.println("Implement your swap here.");

        if (selectedPoint1 != null && selectedPoint2 != null) {
//            model.swapChessPiece(selectedPoint1, selectedPoint2);
//
//            ChessComponent chess1 = view.removeChessComponentAtGrid(selectedPoint2);
//            ChessComponent chess2 = view.removeChessComponentAtGrid(selectedPoint1);
//            view.animateSwap(chess1, chess2, () -> {
//                // 动画完成后的回调逻辑
//                view.setChessComponentAtGrid(selectedPoint2, chess2);
//                view.setChessComponentAtGrid(selectedPoint1, chess1);
//                chess1.repaint();
//                chess2.repaint();
//
//                // 检查是否有匹配
//                List<ChessboardPoint> matches = detectMatches();
//                if (!matches.isEmpty()) {
//                    if (isAutoMode) {
//                        // 自动模式下自动处理匹配
//                        // handleMatches(matches);
//                    } else {
//                        // 手动模式下等待玩家进一步操作
//                    }
//                } else {
//                    // 如果没有匹配，可选择将棋子换回原位
//                    // ...
//                    model.swapChessPiece(selectedPoint1, selectedPoint2);
//
//                    ChessComponent chess3 = view.removeChessComponentAtGrid(selectedPoint2);
//                    ChessComponent chess4 = view.removeChessComponentAtGrid(selectedPoint1);
//                    view.animateSwap(chess1, chess2, () -> {
//                        // 动画完成后的回调逻辑
//                        view.setChessComponentAtGrid(selectedPoint2, chess4);
//                        view.setChessComponentAtGrid(selectedPoint1, chess3);
//                        chess3.repaint();
//                        chess4.repaint();
//
//                    });
//                }
//                selectedPoint1 = null;
//                selectedPoint2 = null;
//            });



            // 交换棋子
            model.swapChessPiece(selectedPoint1, selectedPoint2);
            ChessComponent chess1 = view.removeChessComponentAtGrid(selectedPoint2);
            ChessComponent chess2 = view.removeChessComponentAtGrid(selectedPoint1);
            view.setChessComponentAtGrid(selectedPoint2, chess2);// TODO: Init your swap function here.
            view.setChessComponentAtGrid(selectedPoint1, chess1);
            chess1.repaint();
            chess2.repaint();
            // 创建一个 500 毫秒（0.5 秒）延迟的 Timer
            Runnable delayedAction = () -> {
                // 执行一些操作...
                List<ChessboardPoint> matches = detectMatches();
                if (!matches.isEmpty()) {
                    // 如果有匹配
                    if (isAutoMode) {
                        // 自动模式下自动处理匹配
//                    handleMatches(matches);
                    } else {
                        // 手动模式下等待玩家进一步操作
                        removeMatchedPieces(matches);

                        // 计算并更新分数
                        int scoreEarned = calculateScore(matches);
                        updateScore(scoreEarned);

                        // 更新棋盘界面
                        updateBoard();
                    }

                } else {
                    // 如果没有匹配，通知玩家
                    // 这里可以选择是否将棋子换回原位
                    model.swapChessPiece(selectedPoint1, selectedPoint2);
                    ChessComponent chess3 = view.removeChessComponentAtGrid(selectedPoint2);
                    ChessComponent chess4 = view.removeChessComponentAtGrid(selectedPoint1);
                    view.setChessComponentAtGrid(selectedPoint2, chess4);// TODO: Init your swap function here.
                    view.setChessComponentAtGrid(selectedPoint1, chess3);
                    chess3.repaint();
                    chess4.repaint();
                    JOptionPane.showMessageDialog(null, "Illegal Swap", "Notice", JOptionPane.INFORMATION_MESSAGE);

                }
                selectedPoint1 = null;
                selectedPoint2 = null;
            };
            Timer timer = new Timer(500, e -> {
                delayedAction.run(); // 延迟结束后执行操作
                ((Timer) e.getSource()).stop(); // 停止 Timer
            });
            timer.setRepeats(false); // 确保 Timer 只执行一次
            timer.start(); // 启动 Timer
            // 检查是否有匹配

        } else {
            // 如果没有两个棋子被选中，通知玩家
            System.out.println("Not select two pieces");
        }
    }
    private List<ChessboardPoint> detectMatches() {
//        List<ChessboardPoint> matchedPoints = new ArrayList<>();
        Set<ChessboardPoint> matchedPoints = new HashSet<>();
        // 使用 chessboard.isMatch 方法检查匹配
        // 示例：检查水平方向的匹配
        for (int row = 0; row < model.getRow(); row++) {
            for (int col = 0; col < model.getCol() - 2; col++) {
                if (model.isMatch(row, col, row, col + 1, row, col + 2)) {
                    matchedPoints.add(new ChessboardPoint(row, col));
                    matchedPoints.add(new ChessboardPoint(row, col + 1));
                    matchedPoints.add(new ChessboardPoint(row, col + 2));
                }
            }
        }
        // 检查垂直方向的匹配
        for (int col = 0; col < model.getRow(); col++) {
            for (int row = 0; row < model.getCol() - 2; row++) {
                if (model.isMatch(row, col, row + 1, col, row + 2, col)) {
                    matchedPoints.add(new ChessboardPoint(row, col));
                    matchedPoints.add(new ChessboardPoint(row + 1, col));
                    matchedPoints.add(new ChessboardPoint(row + 2, col));
                }
            }
        }

        // 重复以上逻辑检查垂直方向的匹配
        // ...

        return new ArrayList<>(matchedPoints);
    }


    private void removeMatchedPieces(List<ChessboardPoint> matches) {
        for (ChessboardPoint point : matches) {
            // 从模型中移除棋子
            model.removePieceAt(point);

            // 从视图中移除相应的棋子组件
            view.removeChessComponentAtGrid(point);
            view.repaint();
        }
    }

    public ArrayList<Match> getMatches(ChessboardPoint point, List<ChessboardPoint> chessboardPoints){
        Match rowMatch = new Match();
        Match colMatch = new Match();
        colMatch.addPoint(point);
        rowMatch.addPoint(point);
        for (int y = 1; point.getCol() + y < model.getCol(); y++) {
            ChessboardPoint t1 = new ChessboardPoint(point.getRow(), point.getCol() + y);
            if (chessboardPoints.contains(t1))
                rowMatch.addPoint(t1);
            else break;
        }
        for (int y = 1; point.getCol() - y > 0; y++) {
            ChessboardPoint t1 = new ChessboardPoint(point.getRow(), point.getCol() - y);
            if (chessboardPoints.contains(t1))
                rowMatch.addPoint(t1);
            else break;
        }

        for (int x = 1; point.getRow() + x < model.getRow(); x++) {
            ChessboardPoint t1 = new ChessboardPoint(point.getRow() + x, point.getCol());
            if (chessboardPoints.contains(t1))
                colMatch.addPoint(t1);
            else break;
        }
        for (int x = 1; point.getRow() - x > 0; x++) {
            ChessboardPoint t1 = new ChessboardPoint(point.getRow() - x, point.getCol());
            if (chessboardPoints.contains(t1))
                colMatch.addPoint(t1);
            else break;
        }
        ArrayList<Match> matches = new ArrayList<>();
        if (rowMatch.getSize() >= 3)
            matches.add(rowMatch);
        if (colMatch.getSize() >= 3)
            matches.add(colMatch);
        return matches;

    }
    private int calculateScore(List<ChessboardPoint> matches) {
        // 根据匹配的数量计算分数
        // 例如：每个匹配 30 分
        int score = 0;
        HashSet<Match> matchesAll = new HashSet<>();
        for (int i = 0; i < matches.size(); i++) {
            ArrayList<Match> res = getMatches(matches.get(i), matches);
            matchesAll.addAll(res);
        }
        for (Match m:matchesAll){
            score += m.getSize() * 10;
        }
        return score;
    }

    private void updateScore(int scoreEarned) {
        // 更新游戏分数
        this.score += scoreEarned;
        step--;//score你再补充一下
        this.scoreLabel.setText("Score:" + score);
        this.stepLabel.setText("Step: " + step);
    }

    private void updateBoard() {
        // 在视图上更新棋盘，例如重绘或重新布局棋子
        view.repaint();
        // 可能还需要处理棋子的下落逻辑
    }

    public boolean dropPieces() {
        boolean res = false;
        for (int col = 0; col < model.getCol(); col++) {
            for (int row = model.getRow() - 1; row >= 0; row--) {
                ChessboardPoint point1 = new ChessboardPoint(row, col);
                ChessboardPoint point2 = new ChessboardPoint(row-1, col);
                ChessPiece currentPiece = model.getChessPieceAt(point1);
                if (currentPiece == null) {
                    if (model.getChessPieceAt(point2) != null)
                        res = true;
                    // 找到空位，将上方棋子下移
                    model.swapChessPiece(point1, point2);
                    ChessComponent chess1 = view.removeChessComponentAtGrid(point2);
                    ChessComponent chess2 = view.removeChessComponentAtGrid(point1);
                    view.setChessComponentAtGrid(point2, chess2);// TODO: Init your swap function here.
                    view.setChessComponentAtGrid(point1, chess1);
                    if (chess1!=null)
                        chess1.repaint();
                    if (chess2!=null)
                        chess2.repaint();
                }
                view.repaint();
            }
        }

        return res;
    }

    public boolean startDroppingPieces() {
        boolean res = dropPieces();
        // 创建一个 500 毫秒（0.5 秒）延迟的 Timer
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean result = dropPieces();
                if (!result) {
                    // 如果 dropPieces 返回 false，则停止 Timer
                    ((Timer) e.getSource()).stop();
                    afterDroppingPieces();
                }
                // 如果返回 true，Timer 将继续运行并再次调用 dropPieces
            }
        });

        timer.start(); // 启动 Timer
        return res;
    }

    public void afterDroppingPieces() {
        List<ChessboardPoint> matches = detectMatches();
        if (!matches.isEmpty()) {
            if (isAutoMode) {
                // 自动模式下的逻辑
            } else {
                // 手动模式下等待玩家进一步操作
                removeMatchedPieces(matches);

                // 计算并更新分数
                int scoreEarned = calculateScore(matches);
                updateScore(scoreEarned);

                // 更新棋盘界面
                updateBoard();
            }
        }
        // 其他可能的后续操作
    }

    @Override
    public void onPlayerNextStep() {
        // TODO: Init your next step function here.
        if (startDroppingPieces()){

        }else {
//            System.out.println("new");
            ArrayList<Point> points = model.fillEmptyCells();
            view.viewEmptyCells(model, points);
            view.repaint();
            this.afterDroppingPieces();

        }

    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        if (selectedPoint2 != null) {
            var distance2point1 = Math.abs(selectedPoint1.getCol() - point.getCol()) + Math.abs(selectedPoint1.getRow() - point.getRow());
            var distance2point2 = Math.abs(selectedPoint2.getCol() - point.getCol()) + Math.abs(selectedPoint2.getRow() - point.getRow());
            var point1 = (ChessComponent) view.getGridComponentAt(selectedPoint1).getComponent(0);
            var point2 = (ChessComponent) view.getGridComponentAt(selectedPoint2).getComponent(0);
            if (distance2point1 == 0 && point1 != null) {
                point1.setSelected(false);
                point1.repaint();
                selectedPoint1 = selectedPoint2;
                selectedPoint2 = null;
            } else if (distance2point2 == 0 && point2 != null) {
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = null;
            } else if (distance2point1 == 1 && point2 != null) {
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            } else if (distance2point2 == 1 && point1 != null) {
                point1.setSelected(false);
                point1.repaint();
                selectedPoint1 = selectedPoint2;
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            }
            return;
        }


        if (selectedPoint1 == null) {
            selectedPoint1 = point;
            component.setSelected(true);
            component.repaint();
            return;
        }

        var distance2point1 = Math.abs(selectedPoint1.getCol() - point.getCol()) + Math.abs(selectedPoint1.getRow() - point.getRow());

        if (distance2point1 == 0) {
            selectedPoint1 = null;
            component.setSelected(false);
            component.repaint();
            return;
        }

        if (distance2point1 == 1) {
            selectedPoint2 = point;
            component.setSelected(true);
            component.repaint();
        } else {
            selectedPoint2 = null;

            var grid = (ChessComponent) view.getGridComponentAt(selectedPoint1).getComponent(0);
            if (grid == null) return;
            grid.setSelected(false);
            grid.repaint();

            selectedPoint1 = point;
            component.setSelected(true);
            component.repaint();
        }


    }

}
