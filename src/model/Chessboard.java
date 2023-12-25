package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * This class store the real chess information.
 * The Chessboard has 8 * 8 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;

    public Chessboard(String theme) {
        this.grid =
                new Cell[ChessboardSize.CHESSBOARD_ROW_SIZE][ChessboardSize.CHESSBOARD_COL_SIZE];

        initGrid();
        initPieces(theme);
        while (checkForPreExistingMatches()) {
            initPieces(theme);
        }
    }

    private void initGrid() {
        for (int i = 0; i < ChessboardSize.CHESSBOARD_ROW_SIZE; i++) {
            for (int j = 0; j < ChessboardSize.CHESSBOARD_COL_SIZE; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    private void initPieces(String theme) {
        int cnt = 0;
        for (int i = 0; i < ChessboardSize.CHESSBOARD_ROW_SIZE; i++) {
            for (int j = 0; j < ChessboardSize.CHESSBOARD_COL_SIZE; j++) {
//                if (cnt<3)
//                    grid[i][j].setPiece(new ChessPiece( "0" + 0));
//                else
//                    grid[i][j].setPiece(new ChessPiece( "" + cnt));
//
//                cnt++;
                grid[i][j].setPiece(new ChessPiece( Util.RandomPick(Util.getThemePieces(theme).toArray(new String[0]))));
//                grid[i][j].setPiece(new ChessPiece( Util.RandomPick(new String[]{"\uD83E\uDDBF", "⚪", "▲", "🔶","\uD83D\uDD3B","\uD83E\uDDBE","\uD83E\uDEBF","\uD83E\uDDBD","\uD83E\uDDB1","\uD83E\uDDB2","\uD83E\uDDB3","\uD83E\uDDB4","\uD83E\uDDB5","\uD83E\uDDB6","\uD83E\uDDB7","\uD83E\uDDB8","\uD83E\uDDB9","\uD83E\uDDBA","\uD83E\uDDBB","\uD83E\uDDBC","\uD83E\uDD1F","\uD83E\uDD2F","\uD83E\uDD3F","\uD83E\uDD4F","\uD83E\uDD5F"})));
            }
        }

    }

    public void resetBoard(String theme) {
        initGrid();
        initPieces(theme);


        while (checkForPreExistingMatches()) {
            initPieces(theme);
        }
    }

    public void reLoadBoard() {
        initGrid();
        for (int i = 0; i < ChessboardSize.CHESSBOARD_ROW_SIZE; i++) {
            for (int j = 0; j < ChessboardSize.CHESSBOARD_COL_SIZE; j++) {
                grid[i][j].setPiece(null);
            }
        }

    }


    private boolean checkForPreExistingMatches() {

        for (int row = 0; row < ChessboardSize.CHESSBOARD_ROW_SIZE; row++) {
            for (int col = 0; col < ChessboardSize.CHESSBOARD_COL_SIZE - 2; col++) {
                if (isMatch(row, col, row, col + 1, row, col + 2)) {
                    return true;
                }
            }
        }


        for (int col = 0; col < ChessboardSize.CHESSBOARD_COL_SIZE; col++) {
            for (int row = 0; row < ChessboardSize.CHESSBOARD_ROW_SIZE - 2; row++) {
                if (isMatch(row, col, row + 1, col, row + 2, col)) {
                    return true;
                }
            }
        }

        return false;
    }

    // 辅助方法：检查给定的三个位置是否有匹配
//    public boolean isMatch(int row1, int col1, int row2, int col2, int row3, int col3) {
//        ChessPiece piece1 = grid[row1][col1].getPiece();
//        ChessPiece piece2 = grid[row2][col2].getPiece();
//        ChessPiece piece3 = grid[row3][col3].getPiece();
//
//        // 如果任何一个棋子为 null，则不构成匹配
//        if (piece1 == null || piece2 == null || piece3 == null) {
//            return false;
//        }
//
//        // 比较棋子的名称
//        String name1 = piece1.getName();
//        String name2 = piece2.getName();
//        String name3 = piece3.getName();
//
//        return name1.equals(name2) && name2.equals(name3);
//    }

    public boolean isMatch(int row1, int col1, int row2, int col2, int row3, int col3) {
        ChessPiece piece1 = grid[row1][col1].getPiece();
        ChessPiece piece2 = grid[row2][col2].getPiece();
        ChessPiece piece3 = grid[row3][col3].getPiece();

        // 如果任何一个棋子为 null，则不构成匹配
        if (piece1 == null || piece2 == null || piece3 == null) {
            return false;
        }

        // 获取棋子的名称
        String name1 = piece1.getName();
        String name2 = piece2.getName();
        String name3 = piece3.getName();

        // 检查是否有炸弹或电源
        boolean isSpecialPiece = name1.equals("💣") || name1.equals("🔋") ||
                name2.equals("💣") || name2.equals("🔋") ||
                name3.equals("💣") || name3.equals("🔋");

        // 如果包含特殊棋子，只需其他两个棋子名称相同即可
        if (isSpecialPiece) {
            return (name1.equals(name2) || name1.equals(name3) || name2.equals(name3));
        }

        // 普通情况下，需要所有棋子名称相同
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

    public ArrayList<Point> fillEmptyCells(String theme) {
        ArrayList<Point> points = new ArrayList<>();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col].getPiece() == null) { // 检查这个位置是否为空
                    points.add(new Point(row, col));
//                    grid[row][col].setPiece(new ChessPiece( Util.RandomPick(new String[]{"\uD83E\uDDBF", "⚪", "▲", "🔶","\uD83D\uDD3B"}))); // 在空位生成新的棋子

                    grid[row][col].setPiece(new ChessPiece( Util.RandomPick(Util.getThemePieces(theme).toArray(new String[0]))));
                }
            }
        }
        return points;
    }


    public void swapChessPiece(ChessboardPoint point1, ChessboardPoint point2) {
//        System.out.println(point2);
//        System.out.println(point1);
        ChessPiece p1 = getChessPieceAt(point1);
        ChessPiece p2 = getChessPieceAt(point2);
        setChessPiece(point1, p2);
        setChessPiece(point2, p1);
    }//交换两个棋子的图案


    public Cell[][] getGrid() {
        return grid;
    }

    public int getRow(){
        return ChessboardSize.CHESSBOARD_ROW_SIZE;
    }

    public int getCol(){
        return ChessboardSize.CHESSBOARD_COL_SIZE;
    }





}
