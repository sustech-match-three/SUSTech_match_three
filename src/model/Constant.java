package model;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import java.util.List;


public enum Constant {
    CHESSBOARD_ROW_SIZE(8),CHESSBOARD_COL_SIZE(8);

    List<String> christmasPieces = Arrays.asList(
            "\uD83C\uDF84", // 🎄 Christmas Tree
            "\uD83C\uDF85", // 🎅 Santa Claus
            "\uD83E\uDD8C", // 🦌 Reindeer
            "\uD83C\uDF6C", // 🍬 Candy Cane
            "\u26C4",      // ⛄ Snowman
            "\uD83C\uDF1F", // 🌟 Star
            "\uD83C\uDF81", // 🎁 Gift
            "\uD83E\uDDE6"       // 🧦 Christmas Stocking
            );

    private final int num;
    Constant(int num){
        this.num = num;
    }//棋盘边长的初始化

    public int getNum() {
        return num;
    }

    static Map<String, Color> colorMap = new HashMap<>(){{
//        put("\uD83E\uDDBF",Color.blue);
//        put("⚪",Color.white);
//        put("▲",Color.green);
//        put("🔶",Color.orange);
//        put("\uD83D\uDD3B",Color.cyan);
        put("\uD83C\uDF84", new Color(0, 128, 0));     // 🎄
        put("\uD83C\uDF85", new Color(255, 0, 0));     // 🎅
        put("\uD83E\uDD8C", new Color(139, 69, 19));   // 🦌
        put("\uD83C\uDF6C", new Color(255, 0, 0));     // 🍬
        put("\u26C4", new Color(255, 255, 255));       // ⛄
        put("\uD83C\uDF1F", new Color(255, 215, 0));   // 🌟
        put("\uD83C\uDF81", new Color(255, 20, 147));  // 🎁
        put("\uD83E\uDDE6", new Color(178, 34, 34));       // 🧦 Christmas Stocking
        put("💣", new Color(105, 105, 105)); // 炸弹
        put("🔋", new Color(0, 100, 0));
    }};

}
