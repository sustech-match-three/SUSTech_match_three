package controller;

import com.google.gson.Gson;
import listener.GameListener;
import model.*;
import view.CellComponent;
import view.ChessComponent;
import view.ChessboardComponent;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.util.List;


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

    private boolean canSelectPieces = true;

    public int score = 0;
    public int step = 10;
    public int difficultyLevel = 1;
    public int targetScore = 100;

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
    private boolean isCrazyMode = false;

    private String theme = "Christmas";

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

    public void setScore(int score) {
        this.score = score;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public void setTargetScore(int targetScore) {
        this.targetScore = targetScore;
    }

    public void setShuffleTime(int shuffleTime) {
        this.shuffleTime = shuffleTime;
    }

    public void setPromptTime(int promptTime) {
        this.promptTime = promptTime;
    }

    public void setStep(int step) {
        this.step = step;
        stepLabel.setText("Steps: " + step);
        view.repaint();
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setCrazyMode(boolean crazyMode) {
        isCrazyMode = crazyMode;
    }

    public void setGameLevel(Level gameLevel) {
        this.gameLevel = gameLevel;
        this.difficultyLevel = gameLevel.getDifficultyLevel();
        this.step = gameLevel.getMoveLimit();
        this.targetScore = gameLevel.getTargetScore();
        scoreLabel.setText("Score: " + score);
        stepLabel.setText("Steps: " + step);
        difficultyLevelLabel.setText("Level: " + difficultyLevel);
        targetScoreLabel.setText("Target: " + targetScore);
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
        for (int i = 0; i < ChessboardSize.CHESSBOARD_ROW_SIZE; i++) {
            for (int j = 0; j < ChessboardSize.CHESSBOARD_COL_SIZE; j++) {
                model = new Chessboard(theme);
//                model.getGrid()[i][j].setPiece(new ChessPiece(Util.RandomPick(new String[]{"\uD83E\uDDBF", "⚪", "▲", "🔶", "\uD83D\uDD3B"})));
                model.getGrid()[i][j].setPiece(new ChessPiece(Util.RandomPick(Util.getThemePieces(theme).toArray(new String[0]))));
                view.removeAllChessComponentsAtGrids();
                view.initiateChessComponent(model);
                view.repaint();
            }
        }
    }


    public void startNewGame() {
        this.score = 0;
        this.step = gameLevel.getMoveLimit();
        this.difficultyLevel = gameLevel.getDifficultyLevel();
        this.targetScore = gameLevel.getTargetScore();
        this.shuffleTime = 0;
        this.promptTime = 0;
        canSelectPieces = true;

        scoreLabel.setText("Score: " + score);
        stepLabel.setText("Steps: " + step);
        difficultyLevelLabel.setText("Level: " + difficultyLevel);
        targetScoreLabel.setText("Target: " + targetScore);
        shuffleTimeLabel.setText("Shuffles: " + (3 - this.shuffleTime));
        promptTimeLabel.setText("Prompts: " + (5 - this.promptTime));

        model.resetBoard(theme);
        view.removeAllChessComponentsAtGrids();
        view.initiateChessComponent(model);
        view.repaint();

    }

    public void shuffleGame() {
        if (canSelectPieces) {
            if (this.shuffleTime < 3) {
                this.initialize();
                this.shuffleTime++;
                this.canSelectPieces = true;
                this.shuffleTimeLabel.setText("Shuffles: " + (3 - this.shuffleTime));
            } else {
                JOptionPane.showMessageDialog(null, "No shuffle attempts", "Notice", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Fail, please restart game!", "Notice", JOptionPane.INFORMATION_MESSAGE);
        }

    }




    private void removeMatchedPieces(List<ChessboardPoint> matches) {
        for (ChessboardPoint point : matches) {
            if (isCrazyMode) {
                if (model.getChessPieceAt(point) != null) {
                    String pieceName = model.getChessPieceAt(point).getName();
                    if (pieceName.equals("🔋")) {
                        this.setStep(this.step + 2);
                    } else if (pieceName.equals("💣")) {
                        removeSurroundingPieces(point);
                    }
                }
            }

            model.removePieceAt(point);

            view.removeChessComponentAtGrid(point);
            view.repaint();
        }
    }

    private void removeSurroundingPieces(ChessboardPoint bombPoint) {
        int row = bombPoint.getRow();
        int col = bombPoint.getCol();
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i >= 0 && i < model.getRow() && j >= 0 && j < model.getCol()) {
                    model.removePieceAt(new ChessboardPoint(i, j));
                    view.removeChessComponentAtGrid(new ChessboardPoint(i, j));
                }
            }
        }
    }

    public ArrayList<Match> getMatches(ChessboardPoint point, List<ChessboardPoint> chessboardPoints) {
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
        int score = 0;
        HashSet<Match> matchesAll = new HashSet<>();
        for (int i = 0; i < matches.size(); i++) {
            ArrayList<Match> res = getMatches(matches.get(i), matches);
            matchesAll.addAll(res);
        }
        for (Match m : matchesAll) {
            score += m.getSize() * 10;
            if (isCrazyMode) {
                score += (100 * containsBomb(m));
            }
        }
        return score;
    }

    private int containsBomb(Match match) {
        int cnt = 0;
        for (ChessboardPoint point : match.getPoints()) {
            String pieceName = model.getChessPieceAt(point).getName();
            if (pieceName.equals("💣")) {
                cnt++;
            }
        }
        return cnt;
    }

    private void handleAutoMode() {
        Runnable autoModeAction = () -> {
            if (detectAndHandleMatches()) {
                dropPiecesWithDelay(this::handleAutoMode);
            } else {
                System.out.println(" fill empty");
                fillEmptyCellsAndCheckMatches();
            }
        };
        SwingUtilities.invokeLater(autoModeAction);
    }

    private void fillEmptyCellsAndCheckMatches() {
        ArrayList<Point> points = model.fillEmptyCells(theme);
        view.viewEmptyCells(model, points);
        view.repaint();
//        model.fillEmptyCells();

        Timer timer = new Timer(500, e -> {
            if (!detectAndHandleMatches()) {
                ((Timer) e.getSource()).stop();
                checkGameStatus();

            } else {
                dropPiecesWithDelay(this::fillEmptyCellsAndCheckMatches);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private boolean detectAndHandleMatches() {
        List<ChessboardPoint> matches = detectMatches();
        HashSet<Match> matchesAll = new HashSet<>();
        for (int i = 0; i < matches.size(); i++) {
            ArrayList<Match> res = getMatches(matches.get(i), matches);
            matchesAll.addAll(res);
        }
        if (!matches.isEmpty()) {
            updateScore(calculateScore(matches));
            removeMatchedPieces(matches);
            if (isCrazyMode) {
                for (Match match : matchesAll) {
                    if (match.getSize() == 5) {
                        ChessboardPoint middlePoint = getMiddlePoint(match);
                        model.getGrid()[middlePoint.getRow()][middlePoint.getCol()].setPiece(new ChessPiece(Util.RandomPick(new String[]{"💣", "🔋"})));
                        ArrayList<Point> t = new ArrayList<>();
                        t.add(new Point(middlePoint.getRow(), middlePoint.getCol()));
                        view.viewEmptyCells(model, t);
                    } else if (match.getSize() == 4 && Math.random() < 0.50) {
                        ChessboardPoint randomPoint = Util.RandomPick(match.getPoints().toArray(new ChessboardPoint[0]));
                        model.getGrid()[randomPoint.getRow()][randomPoint.getCol()].setPiece(new ChessPiece(Util.RandomPick(new String[]{"💣", "🔋"})));
                        ArrayList<Point> t = new ArrayList<>();
                        t.add(new Point(randomPoint.getRow(), randomPoint.getCol()));
                        view.viewEmptyCells(model, t);
                    }
                }
            }
            return true;
        }
        return false;
    }

    private ChessboardPoint getMiddlePoint(Match match) {
        int sumRow = 0;
        int sumCol = 0;
        for (ChessboardPoint point : match.getPoints()) {
            sumRow += point.getRow();
            sumCol += point.getCol();
        }
        int middleRow = sumRow / match.getPoints().size();
        int middleCol = sumCol / match.getPoints().size();
        return new ChessboardPoint(middleRow, middleCol);
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
        this.score += scoreEarned;
        this.scoreLabel.setText("Score:" + score);
        this.stepLabel.setText("Step: " + step);
    }

    private void updateBoard() {
        view.repaint();
    }

    public boolean dropPieces() {
        boolean res = false;
        for (int col = 0; col < model.getCol(); col++) {
            for (int row = model.getRow() - 1; row >= 0; row--) {
                ChessboardPoint point1 = new ChessboardPoint(row, col);
                ChessboardPoint point2 = new ChessboardPoint(row - 1, col);
                ChessPiece currentPiece = model.getChessPieceAt(point1);
                if (currentPiece == null) {
                    if (model.getChessPieceAt(point2) != null)
                        res = true;
                    model.swapChessPiece(point1, point2);
                    ChessComponent chess1 = view.removeChessComponentAtGrid(point2);
                    ChessComponent chess2 = view.removeChessComponentAtGrid(point1);
                    view.setChessComponentAtGrid(point2, chess2);
                    view.setChessComponentAtGrid(point1, chess1);
                    if (chess1 != null)
                        chess1.repaint();
                    if (chess2 != null)
                        chess2.repaint();
                }
                view.repaint();
            }
        }

        return res;
    }

    public boolean startDroppingPieces() {
        boolean res = dropPieces();
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean result = dropPieces();
                if (!result) {

                    ((Timer) e.getSource()).stop();
                    afterDroppingPieces();
                }

            }
        });


        timer.start();

        return res;
    }

    public void afterDroppingPieces() {
        List<ChessboardPoint> matches = detectMatches();
        if (!matches.isEmpty()) {
            if (isAutoMode) {
            } else {
                int scoreEarned = calculateScore(matches);
                updateScore(scoreEarned);

                removeMatchedPieces(matches);


                updateBoard();
            }
        }
    }

    private List<ChessboardPoint> detectMatches() {
        Set<ChessboardPoint> matchedPoints = new HashSet<>();
        for (int row = 0; row < model.getRow(); row++) {
            for (int col = 0; col < model.getCol() - 2; col++) {
                if (model.isMatch(row, col, row, col + 1, row, col + 2)) {
                    matchedPoints.add(new ChessboardPoint(row, col));
                    matchedPoints.add(new ChessboardPoint(row, col + 1));
                    matchedPoints.add(new ChessboardPoint(row, col + 2));
                }
            }
        }
        for (int col = 0; col < model.getCol(); col++) {
            for (int row = 0; row < model.getRow() - 2; row++) {
                if (model.isMatch(row, col, row + 1, col, row + 2, col)) {
                    matchedPoints.add(new ChessboardPoint(row, col));
                    matchedPoints.add(new ChessboardPoint(row + 1, col));
                    matchedPoints.add(new ChessboardPoint(row + 2, col));
                }
            }
        }


        return new ArrayList<>(matchedPoints);
    }

    public boolean isDead() {
        for (int row = 0; row < model.getRow(); row++) {
            for (int col = 0; col < model.getCol(); col++) {
                if (canSwapLeadToMatch(row, col, row, col + 1) || canSwapLeadToMatch(row, col, row + 1, col)) {
                    return false;
                }
            }
        }
        return true;
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
        if (!isWithinBoard(row1, col1) || !isWithinBoard(row2, col2)) {
            return false;
        }
        return Math.abs(row1 - row2) + Math.abs(col1 - col2) == 1;
    }

    private boolean isWithinBoard(int row, int col) {
        return row >= 0 && row < model.getRow() && col >= 0 && col < model.getCol();
    }

    public void checkGameStatus() {
        boolean isDead = isDead();
        if (score >= targetScore) {
            int response = JOptionPane.showConfirmDialog(null,
                    "Congratulations! You've won! Do you want to proceed to the next level?",
                    "Game Over", JOptionPane.YES_NO_OPTION);

            if (response == JOptionPane.YES_OPTION) {
                goToNextLevel();
            } else {
                canSelectPieces = false; // 禁止选择棋子
            }

        } else if (step <= 0 || (isDead && this.shuffleTime >= 3)) {
            JOptionPane.showMessageDialog(null,
                    "Game Over!",
                    "Game Over", JOptionPane.INFORMATION_MESSAGE);
            canSelectPieces = false;
        }
        if (isDead) {
            JOptionPane.showMessageDialog(null, "You need to shuffle chessboard", "Notice", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void goToNextLevel() {
        int nextLevel = difficultyLevel + 1;
        gameLevel = new Level(nextLevel);
        startNewGame();
    }

    public ChessboardPoint[] findBestSwap() {
        canSelectPieces = true;
        int maxScore = 0;
        ChessboardPoint[] bestSwap = new ChessboardPoint[2];

        for (int row = 0; row < model.getRow(); row++) {
            for (int col = 0; col < model.getCol(); col++) {
                if (col + 1 < model.getCol()) {
                    int score = simulateSwapAndCalculateScore(row, col, row, col + 1);
                    if (score > maxScore) {
                        maxScore = score;
                        bestSwap[0] = new ChessboardPoint(row, col);
                        bestSwap[1] = new ChessboardPoint(row, col + 1);
                    } else if (score == maxScore) {
                        if (bestSwap[0] != null && bestSwap[0].getRow() + bestSwap[1].getRow() < row * 2) {
                            bestSwap[0] = new ChessboardPoint(row, col);
                            bestSwap[1] = new ChessboardPoint(row, col + 1);
                        }
                    }
                }
                if (row + 1 < model.getRow()) {
                    int score = simulateSwapAndCalculateScore(row, col, row + 1, col);
                    if (score > maxScore) {
                        maxScore = score;
                        bestSwap[0] = new ChessboardPoint(row, col);
                        bestSwap[1] = new ChessboardPoint(row + 1, col);
                    } else if (score == maxScore) {
                        if (bestSwap[0] != null && bestSwap[0].getRow() + bestSwap[1].getRow() < row * 2 + 1) {
                            bestSwap[0] = new ChessboardPoint(row, col);
                            bestSwap[1] = new ChessboardPoint(row + 1, col);
                        }
                    }
                }
            }
        }
        return bestSwap;
    }


    private int simulateSwapAndCalculateScore(int row1, int col1, int row2, int col2) {
        if (!isValidSwap(row1, col1, row2, col2)) return 0;

        model.swapChessPiece(new ChessboardPoint(row1, col1), new ChessboardPoint(row2, col2));

        List<ChessboardPoint> matches = detectMatches();
        int score = calculateScore(matches);

        model.swapChessPiece(new ChessboardPoint(row1, col1), new ChessboardPoint(row2, col2));

        return score;
    }

    public void makeCellsBlink(ChessboardPoint point1, ChessboardPoint point2) {
        CellComponent cell1 = view.getCellComponentAt(point1);
        CellComponent cell2 = view.getCellComponentAt(point2);

        int delay = 300;
        long startTime = System.currentTimeMillis();
        long duration = 2000;

        Timer timer = new Timer(delay, null);
        timer.addActionListener(e -> {
            if (System.currentTimeMillis() - startTime > duration) {
                cell1.restoreNormalAppearance();
                cell2.restoreNormalAppearance();
                timer.stop();
            } else {
                cell1.toggleBlinkAppearance();
                cell2.toggleBlinkAppearance();
            }
        });
        timer.start();
    }

    public void prompt() {
        if (promptTime < 5) {
            ChessboardPoint[] bestSwapPoints = findBestSwap();
            if (bestSwapPoints[0] != null && bestSwapPoints[1] != null) {
                makeCellsBlink(bestSwapPoints[0], bestSwapPoints[1]);
                this.promptTime++;
                this.promptTimeLabel.setText("Prompts: " + (5 - this.promptTime));
            }
            view.repaint();
        } else {
            JOptionPane.showMessageDialog(null, "No prompt attempts", "Notice", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void saveGame(String filePath) {
//        try {
//            GameState gameState = new GameState(model, score, step, difficultyLevel, targetScore, shuffleTime, promptTime, isAutoMode, isCrazyMode, theme);
//
//            Gson gson = new Gson();
//            String json = gson.toJson(gameState);
//
//            try (FileWriter writer = new FileWriter(filePath)) {
//                writer.write(json);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

            GameState gameState = new GameState(model, score, step, difficultyLevel, targetScore, shuffleTime, promptTime, isAutoMode, isCrazyMode, theme);

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(gameState.toString().getBytes(StandardCharsets.UTF_8));

            out.writeObject(gameState);
            out.writeObject(hash);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadGame(String filePath) {
//        try {
//            Gson gson = new Gson();
//            try (FileReader reader = new FileReader(filePath)) {
//                GameState gameState = gson.fromJson(reader, GameState.class);
//
//                score = gameState.getScore();
//                step = gameState.getStep();
//                difficultyLevel = gameState.getDifficultyLevel();
//                targetScore = gameState.getTargetScore();
//                this.setShuffleTime(gameState.getShuffleTime());
//                this.setPromptTime(gameState.getPromptTime());
//                this.setAutoMode(gameState.isAutoMode());
//                this.setCrazyMode(gameState.isCrazyMode());
//                this.setTheme(gameState.getTheme());
//
//                gameState.restoreToModel(model);
//                view.removeAllChessComponentsAtGrids();
//                view.initiateChessComponent(model);
//                view.repaint();
//
//
//                scoreLabel.setText("Score: " + score);
//                stepLabel.setText("Steps: " + step);
//                difficultyLevelLabel.setText("Level: " + difficultyLevel);
//                targetScoreLabel.setText("Target: " + targetScore);
//                shuffleTimeLabel.setText("Shuffles: " + (3-this.shuffleTime));
//                promptTimeLabel.setText("Prompts: " + (5-this.promptTime));
//                this.gameLevel = new Level(difficultyLevel);
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        canSelectPieces = true;
        try (FileInputStream fileIn = new FileInputStream(filePath);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {

            GameState gameState = (GameState) in.readObject();
            byte[] savedHash = (byte[]) in.readObject();

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(gameState.toString().getBytes(StandardCharsets.UTF_8));

            if (!Arrays.equals(savedHash, hash)) {
                throw new SecurityException("Game data has been tampered with");
            }
            score = gameState.getScore();
            step = gameState.getStep();
            difficultyLevel = gameState.getDifficultyLevel();
            targetScore = gameState.getTargetScore();
            this.setShuffleTime(gameState.getShuffleTime());
            this.setPromptTime(gameState.getPromptTime());
            this.setAutoMode(gameState.isAutoMode());
            this.setCrazyMode(gameState.isCrazyMode());
            this.setTheme(gameState.getTheme());

            gameState.restoreToModel(model);
            view.removeAllChessComponentsAtGrids();
            view.initiateChessComponent(model);
            view.repaint();


            scoreLabel.setText("Score: " + score);
            stepLabel.setText("Steps: " + step);
            difficultyLevelLabel.setText("Level: " + difficultyLevel);
            targetScoreLabel.setText("Target: " + targetScore);
            shuffleTimeLabel.setText("Shuffles: " + (3 - this.shuffleTime));
            promptTimeLabel.setText("Prompts: " + (5 - this.promptTime));
            this.gameLevel = new Level(difficultyLevel);

        }catch (SecurityException e){
            JOptionPane.showMessageDialog(null, "wrong file", "Notice", JOptionPane.INFORMATION_MESSAGE);

        }
        catch (Exception e) {
//            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "wrong file", "Notice", JOptionPane.INFORMATION_MESSAGE);

        }
    }


    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {

    }

    @Override
    public void onPlayerSwapChess() {

        if (selectedPoint1 != null && selectedPoint2 != null) {

            model.swapChessPiece(selectedPoint1, selectedPoint2);
            ChessComponent chess1 = view.removeChessComponentAtGrid(selectedPoint2);
            ChessComponent chess2 = view.removeChessComponentAtGrid(selectedPoint1);
            view.setChessComponentAtGrid(selectedPoint2, chess2);
            view.setChessComponentAtGrid(selectedPoint1, chess1);
            chess1.repaint();
            chess2.repaint();

            Runnable delayedAction = () -> {
                List<ChessboardPoint> matches = detectMatches();
                if (!matches.isEmpty()) {
                    if (!isAutoMode)
                        step--;

                    if (isAutoMode) {
                        handleAutoMode();
                    } else {
                        int scoreEarned = calculateScore(matches);
                        updateScore(scoreEarned);

                        removeMatchedPieces(matches);

                        updateBoard();
                    }

                } else {
                    if (isAutoMode)
                        step++;
                    model.swapChessPiece(selectedPoint1, selectedPoint2);
                    ChessComponent chess3 = view.removeChessComponentAtGrid(selectedPoint2);
                    ChessComponent chess4 = view.removeChessComponentAtGrid(selectedPoint1);
                    view.setChessComponentAtGrid(selectedPoint2, chess4);
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
                delayedAction.run();
                ((Timer) e.getSource()).stop();
            });
            timer.setRepeats(false);
            timer.start();


        } else {

            JOptionPane.showMessageDialog(null, "Not select two pieces", "Notice", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    @Override
    public void onPlayerNextStep() {

        if (startDroppingPieces()) {

        } else {

            ArrayList<Point> points = model.fillEmptyCells(theme);
            view.viewEmptyCells(model, points);
            view.repaint();
            this.afterDroppingPieces();
            checkGameStatus();
        }

    }


    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        if (canSelectPieces) {
            if (selectedPoint2 != null) {
                int distance2point1 = Math.abs(selectedPoint1.getCol() - point.getCol()) + Math.abs(selectedPoint1.getRow() - point.getRow());
                int distance2point2 = Math.abs(selectedPoint2.getCol() - point.getCol()) + Math.abs(selectedPoint2.getRow() - point.getRow());
                ChessComponent point1 = (ChessComponent) view.getGridComponentAt(selectedPoint1).getComponent(0);
                ChessComponent point2 = (ChessComponent) view.getGridComponentAt(selectedPoint2).getComponent(0);
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

            int distance2point1 = Math.abs(selectedPoint1.getCol() - point.getCol()) + Math.abs(selectedPoint1.getRow() - point.getRow());

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

                ChessComponent grid = (ChessComponent) view.getGridComponentAt(selectedPoint1).getComponent(0);
                if (grid == null) return;
                grid.setSelected(false);
                grid.repaint();

                selectedPoint1 = point;
                component.setSelected(true);
                component.repaint();
            }
            if (isAutoMode) {
                if (selectedPoint1 != null && selectedPoint2 != null) {
                    this.onPlayerSwapChess();
                    this.step--;
                }
            }
        }


    }


}
