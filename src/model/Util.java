package model;

import java.util.Arrays;
import java.util.List;


//为了随机图案创造的类
public class Util {
    public static <T> T RandomPick(T[] arr){
        int randomIndex = (int) (Math.random() * arr.length);
        return arr[randomIndex];
    }
    public static List<String> getThemePieces(String theme) {
        switch (theme) {
            case "Christmas":
                return Constant.christmasPieces;
            case "Halloween":
                return Constant.halloweenPieces;
            case "Spring":
                return Constant.springPieces;

            default:
                return Constant.christmasPieces;
        }
    }
}
