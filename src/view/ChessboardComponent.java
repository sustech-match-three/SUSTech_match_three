package view;


import controller.GameController;
import event.Event;
import event.EventCenter;
import event.ExampleEvent;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JComponent {
    private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    private final int CHESS_SIZE;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();

    private GameController gameController;

    public ChessboardComponent(int chessSize) {
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 8;
        int height = CHESS_SIZE * 8;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);

        initiateGridComponents();

    }


    /**
     * This method represents how to initiate ChessComponent
     * according to Chessboard information
     */
    public void initiateChessComponent(Chessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                // TODO: Implement the initialization checkerboard

                if (grid[i][j].getPiece() != null) {
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    gridComponents[i][j].add(new ChessComponent(CHESS_SIZE, chessPiece));
                }
            }
        }

    }

    public void initiateGridComponents() {

        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp)) {
                    cell = new CellComponent(Color.CYAN, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                } else {
                    cell = new CellComponent(Color.LIGHT_GRAY, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                }
                gridComponents[i][j] = cell;
            }
        }
    }

    public void Restart() {
        gameController.initialize();
    }

    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setChessComponentAtGrid(ChessboardPoint point, ChessComponent chess) {
        if (chess != null)
            getGridComponentAt(point).add(chess);
    }

    public void removeAllChessComponentsAtGrids() {
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                this.removeChessComponentAtGrid(new ChessboardPoint(i, j));
            }
        }
    }

    public ChessComponent removeChessComponentAtGrid(ChessboardPoint point) {
        // Note re-validation is required after remove / removeAll.
        if (getGridComponentAt(point) == null || getGridComponentAt(point).getComponents().length == 0)
            return null;
        ChessComponent chess = (ChessComponent) getGridComponentAt(point).getComponents()[0];
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();
        chess.setSelected(false);
        return chess;
    }

    public CellComponent getGridComponentAt(ChessboardPoint point) {
        if (point.getRow() >= 0 && point.getRow() < CHESSBOARD_ROW_SIZE.getNum()
                && point.getCol() >= 0 && point.getCol() < CHESSBOARD_COL_SIZE.getNum())
            return gridComponents[point.getRow()][point.getCol()];
        return null;
    }

    public ChessComponent getChessComponentAtGrid(ChessboardPoint point) {
        CellComponent cellComponent = gridComponents[point.getRow()][point.getCol()];
        if (cellComponent.getComponentCount() > 0) {
            // 假设每个 CellComponent 只包含一个 ChessComponent
            return (ChessComponent) cellComponent.getComponent(0);
        }
        return null; // 如果该位置没有 ChessComponent
    }


    private ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y / CHESS_SIZE + ", " + point.x / CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y / CHESS_SIZE, point.x / CHESS_SIZE);
    }

    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void swapChess() {
        gameController.onPlayerSwapChess();
    }

    public void nextStep() {
        gameController.onPlayerNextStep();
    }

    public void viewEmptyCells(Chessboard chessboard, ArrayList<Point> points) {
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < points.size(); i++) {
            int row = points.get(i).x;
            int col = points.get(i).y;
            if (grid[row][col].getPiece() != null) {
                ChessPiece chessPiece = grid[row][col].getPiece();
                gridComponents[row][col].add(new ChessComponent(CHESS_SIZE, chessPiece));
            }

        }

    }


    public void animateSwap(ChessComponent chess1, ChessComponent chess2, Runnable onFinish) {
        // 定义动画的步骤和持续时间
        final int steps = 10;
        final int delay = 1; // 毫秒

        // 计算每一步的移动距离
        Point start1 = chess1.getLocation();
        Point start2 = chess2.getLocation();
        Point step1 = new Point((start2.x - start1.x) / steps, (start2.y - start1.y) / steps);
        Point step2 = new Point((start1.x - start2.x) / steps, (start1.y - start2.y) / steps);

        Timer timer = new Timer(delay, null);
        timer.addActionListener(new ActionListener() {
            private int currentStep = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentStep < steps) {
                    chess1.setLocation(start1.x + step1.x * currentStep, start1.y + step1.y * currentStep);
                    chess2.setLocation(start2.x + step2.x * currentStep, start2.y + step2.y * currentStep);
                    currentStep++;
                    chess1.repaint();
                    chess2.repaint();
                } else {
                    // 停止动画并执行完成后的操作
                    timer.stop();
                    onFinish.run();
                }
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else {
                System.out.print("One chess here and ");
                gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (ChessComponent) clickedComponent.getComponents()[0]);
            }
        }
    }
}
