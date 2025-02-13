package model;


import java.awt.*;

public class ChessPiece {
    // Diamond, Circle, ...
    private String name;

    private Color color;

    public ChessPiece(String name) {
        this.name = name;
//        this.color = Constant.colorMap.get(name);
        this.color = Constant.colorMap.getOrDefault(name, Color.BLACK);
    }

    public String getName() {
        return name;
    }

    public Color getColor(){return color;}

}
