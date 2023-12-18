package model;

import java.util.Objects;

/**
 * This class represents positions on the checkerboard, such as (0, 0), (0, 7), and so on
 * Where, the upper left corner is (0, 0), the lower left corner is (7, 0), the upper right corner is (0, 7), and the lower right corner is (7, 7).
 */
public class ChessboardPoint {
    private final int row;
    private final int col;

    public ChessboardPoint(int row, int col) {
        this.row = row;
        this.col = col;
    }//初始化棋盘一个点的位置

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }


//    @Override
//    @SuppressWarnings("ALL")
//    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        }
//        ChessboardPoint temp = (ChessboardPoint) obj;
//        return (temp.getRow() == this.row) && (temp.getCol() == this.col);
//    }//是否在同一点

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ChessboardPoint that = (ChessboardPoint) obj;
        return row == that.row && col == that.col;
    }


    @Override
    public String toString() {
        return "("+row + ","+col+") " + "on the chessboard is clicked!";
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
