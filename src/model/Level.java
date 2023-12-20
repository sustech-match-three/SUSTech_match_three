package model;

public class Level {
    private int targetScore;
    private int moveLimit;
    private int difficultyLevel;

    public Level(int difficultyLevel, int targetScore, int moveLimit) {
        this.targetScore = targetScore;
        this.moveLimit = moveLimit;
        this.difficultyLevel = difficultyLevel;
    }
    public Level(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
        this.targetScore = calculateTargetScore(difficultyLevel);
        this.moveLimit = calculateMoveLimit(difficultyLevel);
    }

    private int calculateTargetScore(int level) {
        // 根据等级计算目标分数
        // 示例：每个等级增加100分
        return 100 * level;
    }

    private int calculateMoveLimit(int level) {
        // 根据等级计算步数限制
        // 示例：从10步开始，每增加一个等级减少1步
        return 10 - level + 1;
    }

    public int getMoveLimit() {
        return moveLimit;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }


    public int getTargetScore() {
        return targetScore;
    }

    @Override
    public String toString() {
        return "Level " + difficultyLevel;
    }
    // Getters and setters
}
