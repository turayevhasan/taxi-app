package uz.pdp.back.utils;

import java.util.Scanner;

public class Input {
    private final static Scanner scannerInt = new Scanner(System.in);
    private final static Scanner scannerStr = new Scanner(System.in);
    private final static Scanner scannerLong = new Scanner(System.in);

    public static int inputInt(String msg) {
        System.out.print(msg);
        return scannerInt.nextInt();
    }

    public static String inputStr(String msg) {
        System.out.print(msg);
        return scannerStr.nextLine();
    }

    public static Long inputLong(String msg) {
        System.out.print(msg);
        return scannerLong.nextLong();
    }
}
