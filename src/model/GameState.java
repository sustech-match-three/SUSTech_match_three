package model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameState implements Serializable {{
    final long serialVersionUID = 1L;
}
    private List<List<String>> board; // 棋盘状态
    private int score;               // 玩家分数
    private int step;                // 剩余步数
    private int difficultyLevel;     // 难度级别
    private int targetScore;         // 目标分数
    private int shuffleTime;
    private int promptTime;
    private boolean isAutoMode;
    private boolean isCrazyMode;
    private String theme;

    // 构造函数用于初始化状态
    public GameState(Chessboard model, int score, int step, int difficultyLevel, int targetScore, int shuffleTime, int promptTime, boolean isAutoMode, boolean isCrazyMode, String theme) {
        this.board = convertBoardToSerializableFormat(model);
        this.score = score;
        this.step = step;
        this.difficultyLevel = difficultyLevel;
        this.targetScore = targetScore;
        this.shuffleTime = shuffleTime;
        this.promptTime = promptTime;
        this.isAutoMode = isAutoMode;
        this.isCrazyMode = isCrazyMode;
        this.theme = theme;
    }

    // 将棋盘对象转换为可序列化的格式
    private List<List<String>> convertBoardToSerializableFormat(Chessboard model) {
        return IntStream.range(0, model.getRow()).mapToObj(row ->
                IntStream.range(0, model.getCol()).mapToObj(col ->
                        model.getGrid()[row][col].getPiece() == null ? null : model.getGrid()[row][col].getPiece().getName()
                ).collect(Collectors.toList())
        ).collect(Collectors.toList());
    }
    public void restoreToModel(Chessboard model) {
        for (int row = 0; row < model.getRow(); row++) {
            for (int col = 0; col < model.getCol(); col++) {
                String pieceName = board.get(row).get(col);
                if (pieceName != null) {
                    // 根据 pieceName 创建相应的 ChessPiece
                    ChessPiece piece = new ChessPiece(pieceName);
                    model.getGrid()[row][col].setPiece(piece);
                } else {
                    model.getGrid()[row][col].setPiece(null);
                }
            }
        }
    }
    // Getter 和 Setter
    public List<List<String>> getBoard() {
        return board;
    }

    public int getScore() {
        return score;
    }

    public int getStep() {
        return step;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public int getTargetScore() {
        return targetScore;
    }

    public int getShuffleTime() {
        return shuffleTime;
    }

    public int getPromptTime() {
        return promptTime;
    }

    public boolean isAutoMode() {
        return isAutoMode;
    }

    public boolean isCrazyMode() {
        return isCrazyMode;
    }

    public String getTheme() {
        return theme;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Score: ").append(score)
                .append(", Step: ").append(step)
                .append(", DifficultyLevel: ").append(difficultyLevel)
                .append(", TargetScore: ").append(targetScore)
                .append(", ShuffleTime: ").append(shuffleTime)
                .append(", PromptTime: ").append(promptTime)
                .append(", IsAutoMode: ").append(isAutoMode)
                .append(", IsCrazyMode: ").append(isCrazyMode)
                .append(", Theme: ").append(theme);
        return sb.toString();
    }
    // 可能还需要其他方法来将 GameState 转换回游戏的实际状态
}
