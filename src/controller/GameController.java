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

    public int shuffleTime = 0;

    public int promptTime = 0;

    public Level gameLevel = new Level(1);
    private JLabel scoreLabel;
    private JLabel stepLabel;
    private JLabel difficultyLevelLabel;
    private JLabel targetScoreLabel;

    private JLabel shuffleTimeLabel;
    private JLabel promptTimeLabel;

    private boolean isAutoMode = true;

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

    public JLabel getShuffleTimeLabel() {
        return shuffleTimeLabel;
    }

    public JLabel getPromptTimeLabel() {
        return promptTimeLabel;
    }

    public ChessboardComponent getView() {
        return view;
    }

    public void setStepLabel(JLabel stepLabel) {
        this.stepLabel = stepLabel;
    }

    public void setShuffleTimeLabel(JLabel shuffleTimeLabel) {
        this.shuffleTimeLabel = shuffleTimeLabel;
    }

    public void setPromptTimeLabel(JLabel promptTimeLabel) {
        this.promptTimeLabel = promptTimeLabel;
    }

    public void setScoreLabel(JLabel scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    public void setAutoMode(boolean isAutoMode) {
        this.isAutoMode = isAutoMode;
    }

    public Level getGameLevel() {
        return gameLevel;
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
//                model.getGrid()[i][j].setPiece(new ChessPiece(Util.RandomPick(new String[]{"\uD83E\uDDBF", "⚪", "▲", "🔶", "\uD83D\uDD3B"})));
                model.getGrid()[i][j].setPiece(new ChessPiece( Util.RandomPick(new String[]{
                        "\uD83C\uDF84", // 🎄 Christmas Tree
                        "\uD83C\uDF85", // 🎅 Santa Claus
                        "\uD83E\uDD8C", // 🦌 Reindeer
                        "\uD83C\uDF6C", // 🍬 Candy Cane
                        "\u26C4",      // ⛄ Snowman
                        "\uD83C\uDF1F", // 🌟 Star
                        "\uD83C\uDF81", // 🎁 Gift
                        "\uD83E\uDDE6"       // ❄️ Snowflake
                })));
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
        this.shuffleTime = 0;
        this.promptTime = 0;

        // 更新显示的分数和步数
        scoreLabel.setText("Score: " + score);
        stepLabel.setText("Steps: " + step);
        difficultyLevelLabel.setText("Level: " + difficultyLevel);
        targetScoreLabel.setText("Target: " + targetScore);
        shuffleTimeLabel.setText("Shuffles: " + (3-this.shuffleTime));
        promptTimeLabel.setText("Prompts: " + (5-this.promptTime));

        // 重置棋盘
        model.resetBoard();  // 假设 Chessboard 类有一个方法来重置棋盘
        view.removeAllChessComponentsAtGrids();
        view.initiateChessComponent(model);
        view.repaint();

    }
    public void shuffleGame(){
        if (this.shuffleTime < 3){
            this.initialize();
            this.shuffleTime++;
            this.shuffleTimeLabel.setText("Shuffles: " + (3-this.shuffleTime));
        }else {
            JOptionPane.showMessageDialog(null, "No shuffle attempts", "Notice", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    // click an empty cell



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

    private void handleAutoMode() {
        Runnable autoModeAction = () -> {
            if (detectAndHandleMatches()) {
                dropPiecesWithDelay(this::handleAutoMode); // 如果有匹配，继续下落并重复检查
            } else {
//                System.out.println(" fill empty");
                fillEmptyCellsAndCheckMatches(); // 没有更多匹配时填充空白并再次检查匹配
            }
        };
        SwingUtilities.invokeLater(autoModeAction); // 在事件调度线程上执行
    }

    private void fillEmptyCellsAndCheckMatches() {
//        System.out.println("fill in function");
        ArrayList<Point> points = model.fillEmptyCells();
        // 填充空白位置
        view.viewEmptyCells(model, points);
        view.repaint();
//        model.fillEmptyCells();
        // 检查是否有新的匹配

        // 创建一个 500 毫秒（0.5 秒）延迟的 Timer
        Timer timer = new Timer(500, e -> {
            if (!detectAndHandleMatches()) {
                ((Timer) e.getSource()).stop();
                // 在没有更多匹配时检查游戏状态
                checkGameStatus();
            } else {
                // 如果还有匹配，继续下落并检查
                dropPiecesWithDelay(this::fillEmptyCellsAndCheckMatches);
            }
        });
        timer.setRepeats(false); // 确保 Timer 只执行一次
        timer.start(); // 启动 Timer
        // 如果没有更多匹配，游戏继续等待玩家操作
    }

    private boolean detectAndHandleMatches() {
        List<ChessboardPoint> matches = detectMatches();
        if (!matches.isEmpty()) {
            view.repaint();
            removeMatchedPieces(matches);
            updateScore(calculateScore(matches));
            return true;
        }
        return false;
    }

    private void dropPiecesWithDelay(Runnable onComplete) {
        Timer timer = new Timer(500, e -> {
            if (!dropPieces()) {
                ((Timer) e.getSource()).stop();
                onComplete.run();
            }
        });
        timer.setRepeats(true);
        timer.start();
    }


    private void updateScore(int scoreEarned) {
        // 更新游戏分数
        this.score += scoreEarned;
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

    public boolean isDead() {
        for (int row = 0; row < model.getRow(); row++) {
            for (int col = 0; col < model.getCol(); col++) {
                if (canSwapLeadToMatch(row, col, row, col + 1) || canSwapLeadToMatch(row, col, row + 1, col)) {
                    return false; // 如果任一交换可以产生匹配，则不是死局
                }
            }
        }
        return true; // 如果没有交换可以产生匹配，那么是死局
    }

    private boolean canSwapLeadToMatch(int row1, int col1, int row2, int col2) {
        if (isValidSwap(row1, col1, row2, col2)) {
            model.swapChessPiece(new ChessboardPoint(row1, col1), new ChessboardPoint(row2, col2));
            boolean hasMatch = !detectMatches().isEmpty();
            model.swapChessPiece(new ChessboardPoint(row1, col1), new ChessboardPoint(row2, col2)); // 换回原位
            return hasMatch;
        }
        return false;
    }

    private boolean isValidSwap(int row1, int col1, int row2, int col2) {
        // 验证棋子是否在棋盘范围内
        if (!isWithinBoard(row1, col1) || !isWithinBoard(row2, col2)) {
            return false;
        }

        // 检查两个棋子是否相邻
        return Math.abs(row1 - row2) + Math.abs(col1 - col2) == 1;
    }

    private boolean isWithinBoard(int row, int col) {
        return row >= 0 && row < model.getRow() && col >= 0 && col < model.getCol();
    }

    public void checkGameStatus() {
        boolean isDead = isDead();
        if (score >= targetScore) {
            // 游戏胜利
            int response = JOptionPane.showConfirmDialog(null,
                    "Congratulations! You've won! Do you want to proceed to the next level?",
                    "Game Over", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                // 玩家选择进入下一关
                goToNextLevel();
            }
            // 如果玩家选择否，或者关闭弹窗，可以在这里添加处理逻辑
        } else if (step <= 0 || (isDead && this.shuffleTime>=3)) {
            // 游戏失败
            JOptionPane.showMessageDialog(null,
                    "Game Over! You've run out of steps!",
                    "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
        if (isDead){
            JOptionPane.showMessageDialog(null, "You need to shuffle chessboard", "Notice", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void goToNextLevel() {
        // 增加难度级别
        int nextLevel = difficultyLevel + 1;
        startNewGame(new Level(nextLevel)); // 假设 Level 类可以接受一个整数作为难度等级
    }

    public ChessboardPoint[] findBestSwap() {
        int maxScore = 0;
        ChessboardPoint[] bestSwap = new ChessboardPoint[2];

        for (int row = 0; row < model.getRow(); row++) {
            for (int col = 0; col < model.getCol(); col++) {
                // 尝试与右侧和下方的棋子交换
                if (col + 1 < model.getCol()) { // 确保不超出棋盘边界
                    int score = simulateSwapAndCalculateScore(row, col, row, col + 1);
                    if (score > maxScore) {
                        maxScore = score;
                        bestSwap[0] = new ChessboardPoint(row, col);
                        bestSwap[1] = new ChessboardPoint(row, col + 1);
                    }else if (score == maxScore){
                        if (bestSwap[0] != null && bestSwap[0].getRow() + bestSwap[1].getRow() < row *2){
                            bestSwap[0] = new ChessboardPoint(row, col);
                            bestSwap[1] = new ChessboardPoint(row, col + 1);
                        }
                    }
                }
                if (row + 1 < model.getRow()) { // 确保不超出棋盘边界
                    int score = simulateSwapAndCalculateScore(row, col, row + 1, col);
                    if (score > maxScore) {
                        maxScore = score;
                        bestSwap[0] = new ChessboardPoint(row, col);
                        bestSwap[1] = new ChessboardPoint(row + 1, col);
                    }else if (score == maxScore){
                        if (bestSwap[0] != null && bestSwap[0].getRow() + bestSwap[1].getRow() < row * 2 + 1){
                            bestSwap[0] = new ChessboardPoint(row, col);
                            bestSwap[1] = new ChessboardPoint(row + 1, col);
                        }
                    }
                }
            }
        }
        return bestSwap; // 返回得分最高的交换位置
    }


    private int simulateSwapAndCalculateScore(int row1, int col1, int row2, int col2) {
        // 确保这是一个有效的交换
        if (!isValidSwap(row1, col1, row2, col2)) return 0;

        // 模拟交换
        model.swapChessPiece(new ChessboardPoint(row1, col1), new ChessboardPoint(row2, col2));

        // 检测匹配并计算分数
        List<ChessboardPoint> matches = detectMatches();
        int score = calculateScore(matches);

        // 将棋子换回原位
        model.swapChessPiece(new ChessboardPoint(row1, col1), new ChessboardPoint(row2, col2));

        return score;
    }

    public void makeCellsBlink(ChessboardPoint point1, ChessboardPoint point2) {
        CellComponent cell1 = view.getCellComponentAt(point1);
        CellComponent cell2 = view.getCellComponentAt(point2);

        // 定义闪烁周期和持续时间
        int delay = 300; // 闪烁间隔（毫秒）
        long startTime = System.currentTimeMillis();
        long duration = 2000; // 闪烁总时长（毫秒）

        Timer timer = new Timer(delay, null);
        timer.addActionListener(e -> {
            if (System.currentTimeMillis() - startTime > duration) {
                // 停止闪烁并恢复正常外观
                cell1.restoreNormalAppearance();
                cell2.restoreNormalAppearance();
                timer.stop();
            } else {
                // 切换闪烁外观
                cell1.toggleBlinkAppearance();
                cell2.toggleBlinkAppearance();
            }
        });
        timer.start();
    }

    public void prompt() {
        if (promptTime<5){
            ChessboardPoint[] bestSwapPoints = findBestSwap();
            if (bestSwapPoints[0] != null && bestSwapPoints[1] != null) {
                makeCellsBlink(bestSwapPoints[0], bestSwapPoints[1]);
                this.promptTime++;
                this.promptTimeLabel.setText("Prompts: " + (5-this.promptTime));
            }
            view.repaint();
        }else {
            JOptionPane.showMessageDialog(null, "No prompt attempts", "Notice", JOptionPane.INFORMATION_MESSAGE);
        }
    }



    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
    }

    @Override
    public void onPlayerSwapChess() {
        if (!isAutoMode)
            step--;//score你再补充一下
        if (selectedPoint1 != null && selectedPoint2 != null) {

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
                        handleAutoMode();
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
                    if (!isAutoMode)
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
//            System.out.println("Not select two pieces");
            JOptionPane.showMessageDialog(null, "Not select two pieces", "Notice", JOptionPane.INFORMATION_MESSAGE);
        }
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
            checkGameStatus();
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
        if (isAutoMode){
            if (selectedPoint1 != null && selectedPoint2 != null){
                this.onPlayerSwapChess();
                this.step--;
            }
        }

    }

}
