package model;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import java.util.List;


public enum Constant {
    CHESSBOARD_ROW_SIZE(8),CHESSBOARD_COL_SIZE(8);

    static List<String> christmasPieces = Arrays.asList(
            "\uD83C\uDF84", // 🎄 Christmas Tree
            "\uD83C\uDF85", // 🎅 Santa Claus
            "\uD83E\uDD8C", // 🦌 Reindeer
            "\uD83C\uDF6C", // 🍬 Candy Cane
            "\u26C4",      // ⛄ Snowman
            "\uD83C\uDF1F", // 🌟 Star
            "\uD83C\uDF81", // 🎁 Gift
            "\uD83E\uDDE6"       // 🧦 Christmas Stocking
            );

    static List<String> halloweenPieces = Arrays.asList(
            "\uD83C\uDF83", // 南瓜
            "\uD83D\uDC7B", // 幽灵
            "\uD83D\uDC80", // 骷髅
            "\uD83D\uDD77", // 蜘蛛网
            "\uD83D\uDC7D", // 外星人
            "\uD83C\uDF15", // 满月
            "\uD83D\uDD76", // 蝙蝠
            "\uD83D\uDC79"  // 僵尸
    );

    static List<String> springPieces = Arrays.asList(
            "\uD83C\uDF38", // 樱花
            "\uD83C\uDF3C", // 风信子
            "\uD83C\uDF37", // 郁金香
            "\uD83C\uDF3A", // 太阳花
            "\uD83C\uDF39", // 玫瑰
            "\uD83C\uDF3B", // 菊花
            "\uD83C\uDF3E", // 种子
            "\uD83C\uDF40"  // 四叶草
    );

    private final int num;
    Constant(int num){
        this.num = num;
    }//棋盘边长的初始化

    public int getNum() {
        return num;
    }

    static Map<String, Color> colorMap = new HashMap<String, Color>(){{
//        put("\uD83E\uDDBF",Color.blue);
//        put("⚪",Color.white);
//        put("▲",Color.green);
//        put("🔶",Color.orange);
//        put("\uD83D\uDD3B",Color.cyan);
        put("💣", new Color(105, 105, 105)); // 炸弹
        put("🔋", new Color(0, 100, 0));


        put("\uD83C\uDF84", new Color(0, 128, 0));     // 🎄
        put("\uD83C\uDF85", new Color(255, 0, 0));     // 🎅
        put("\uD83E\uDD8C", new Color(139, 69, 19));   // 🦌
        put("\uD83C\uDF6C", new Color(255, 0, 0));     // 🍬
        put("\u26C4", new Color(255, 255, 255));       // ⛄
        put("\uD83C\uDF1F", new Color(255, 215, 0));   // 🌟
        put("\uD83C\uDF81", new Color(255, 20, 147));  // 🎁
        put("\uD83E\uDDE6", new Color(178, 34, 34));       // 🧦 Christmas Stocking



        put("\uD83C\uDF83", new Color(255, 165, 0));
        put("\uD83D\uDC7B", new Color(255, 255, 255));
        put("\uD83D\uDC80", new Color(211, 211, 211));
        put("\uD83D\uDD77", new Color(128, 128, 128));
        put("\uD83D\uDC7D", new Color(152, 251, 152));
        put("\uD83C\uDF15", new Color(255, 215, 0));
        put("\uD83D\uDD76", new Color(0, 0, 0));
        put("\uD83D\uDC79", new Color(50, 205, 50));

        put("\uD83C\uDF38", new Color(255, 182, 193));
        put("\uD83C\uDF3C", new Color(173, 255, 47));
        put("\uD83C\uDF37", new Color(255, 99, 71));
        put("\uD83C\uDF3A", new Color(255, 215, 0));
        put("\uD83C\uDF39", new Color(255, 0, 0));
        put("\uD83C\uDF3B", new Color(255, 140, 0));
        put("\uD83C\uDF3E", new Color(124, 252, 0));
        put("\uD83C\uDF40", new Color(0, 255, 0));
    }};

}
