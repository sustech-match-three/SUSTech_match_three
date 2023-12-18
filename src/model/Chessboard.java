package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class store the real chess information.
 * The Chessboard has 8 * 8 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;

    public Chessboard() {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];

        initGrid();
        initPieces();
        while (checkForPreExistingMatches()) {
            initPieces();  // 如果有匹配，重新放置棋子
        }
    }//棋盘的初始化

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private void initPieces() {

        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j].setPiece(new ChessPiece( Util.RandomPick(new String[]{"\uD83E\uDDBF", "⚪", "▲", "🔶","\uD83D\uDD3B"})));
            }
        }

    }

    public void resetBoard() {
        initGrid();  // 重新初始化网格，为每个单元格创建新的 Cell 实例
        initPieces();  // 重新在网格中放置随机的棋子

        // 确保初始化后没有已经成立的匹配
        while (checkForPreExistingMatches()) {
            initPieces();  // 如果有匹配，重新放置棋子
        }
    }

    private boolean checkForPreExistingMatches() {
        // 检查水平方向的匹配
        for (int row = 0; row < Constant.CHESSBOARD_ROW_SIZE.getNum(); row++) {
            for (int col = 0; col < Constant.CHESSBOARD_COL_SIZE.getNum() - 2; col++) {
                if (isMatch(row, col, row, col + 1, row, col + 2)) {
                    return true;
                }
            }
        }

        // 检查垂直方向的匹配
        for (int col = 0; col < Constant.CHESSBOARD_COL_SIZE.getNum(); col++) {
            for (int row = 0; row < Constant.CHESSBOARD_ROW_SIZE.getNum() - 2; row++) {
                if (isMatch(row, col, row + 1, col, row + 2, col)) {
                    return true;
                }
            }
        }

        return false;
    }

    // 辅助方法：检查给定的三个位置是否有匹配
    public boolean isMatch(int row1, int col1, int row2, int col2, int row3, int col3) {
        ChessPiece piece1 = grid[row1][col1].getPiece();
        ChessPiece piece2 = grid[row2][col2].getPiece();
        ChessPiece piece3 = grid[row3][col3].getPiece();

        // 如果任何一个棋子为 null，则不构成匹配
        if (piece1 == null || piece2 == null || piece3 == null) {
            return false;
        }

        // 比较棋子的名称
        String name1 = piece1.getName();
        String name2 = piece2.getName();
        String name3 = piece3.getName();

        return name1.equals(name2) && name2.equals(name3);
    }



    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        if (getGridAt(point) != null)
            return getGridAt(point).getPiece();
        return null;
    }

    public Cell getGridAt(ChessboardPoint point) {
        if (isPointWithinBounds(point)) {
            return grid[point.getRow()][point.getCol()];
        }
        return null; // 或抛出一个异常
    }
    private boolean isPointWithinBounds(ChessboardPoint point) {
        return point.getRow() >= 0 && point.getRow() < grid.length &&
                point.getCol() >= 0 && point.getCol() < grid[0].length;
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }//距离等于水平距离加竖直距离

    public ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }//得到图形，去除该棋子

    public void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        if (getGridAt(point)!=null)
            getGridAt(point).setPiece(chessPiece);
    }

    public void removePieceAt(ChessboardPoint point){getGridAt(point).removePiece();}

    public ArrayList<Point> fillEmptyCells() {
        ArrayList<Point> points = new ArrayList<>();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col].getPiece() == null) { // 检查这个位置是否为空
                    points.add(new Point(row, col));
                    grid[row][col].setPiece(new ChessPiece( Util.RandomPick(new String[]{"\uD83E\uDDBF", "⚪", "▲", "🔶","\uD83D\uDD3B"}))); // 在空位生成新的棋子
                }
            }
        }
        return points;
    }


    public void swapChessPiece(ChessboardPoint point1, ChessboardPoint point2) {
//        System.out.println(point2);
//        System.out.println(point1);
        var p1 = getChessPieceAt(point1);
        var p2 = getChessPieceAt(point2);
        setChessPiece(point1, p2);
        setChessPiece(point2, p1);
    }//交换两个棋子的图案


    public Cell[][] getGrid() {
        return grid;
    }

    public int getRow(){
        return Constant.CHESSBOARD_ROW_SIZE.getNum();
    }

    public int getCol(){
        return Constant.CHESSBOARD_COL_SIZE.getNum();
    }





}
