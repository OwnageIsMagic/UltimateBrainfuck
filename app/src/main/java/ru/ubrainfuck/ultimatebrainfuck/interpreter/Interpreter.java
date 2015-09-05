package ru.ubrainfuck.ultimatebrainfuck.interpreter;

import android.content.Context;
import android.widget.Toast;

/**
 * Created on 04.09.2015.
 */
public class Interpreter {
    private static Interpreter ourInstance = new Interpreter();
    private static int stackSize = 30000;
    private static int[] stack = new int[stackSize];
    private static short stackPointer = 0;
    private static int cmdPointer = 0;
    private static String cmd = "";
    private static String stdOut = "";
    private static String stdIn = "";
    private static int stdinPointer = 0;
    private static boolean strict = false;
    private static boolean debuggable = false;
    public static Toast toast;
    private static Context context;

    private Interpreter() {
    }

    public static Interpreter getInstance() {
        return ourInstance;
    }

    public static int getStackSize() {
        return stackSize;
    }

    public static void setStackSize(int stackSize) {
        Interpreter.stackSize = stackSize;
    }

    public static short getStackPointer() {
        return stackPointer;
    }

    public static void setStackPointer(short stackPointer) {
        Interpreter.stackPointer = stackPointer;
    }

    public static int getCmdPointer() {
        return cmdPointer;
    }

    public static String getCmd() {
        return cmd;
    }

    public static void setCmd(String cmd) {
        Interpreter.cmd = cmd;
    }

    public static String getStdOut() {
        return stdOut;
    }

    public static void setStdOut(String stdOut) {
        Interpreter.stdOut = stdOut;
    }

    public static String getStdIn() {
        return stdIn;
    }

    public static void setStdIn(String stdIn) {
        Interpreter.stdIn = stdIn;
    }

    public static int getStdinPointer() {
        return stdinPointer;
    }

    public static boolean isStrict() {
        return strict;
    }

    public static void setStrict(boolean strict) {
        Interpreter.strict = strict;
    }

    public static boolean isDebuggable() {
        return debuggable;
    }

    public static void setDebuggable(boolean debuggable) {
        Interpreter.debuggable = debuggable;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        Interpreter.context = context;
    }

    public static void toaster(String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }


    public static void reset() {
        stack = new int[stackSize];
        cmdPointer = 0;
        stackPointer = 0;
        stdinPointer = 0;
        stdOut = "";
    }

    public static String eval(String cmnd) {
        cmd = cmnd;
        return eval();
    }

    public static String eval() {
        if (!check()) {
            toaster("BRACKET ERROR");
            return "";
        }
        for (int cmdPointer = 0; cmdPointer <= cmd.length() - 1; cmdPointer++) {
            switch (cmd.charAt(cmdPointer)) {
                case '+':
                    stack[stackPointer]++;
                    break;
                case '-':
                    stack[stackPointer]--;
                    break;
                case '.':
                    stdOut += (char) stack[stackPointer];
                    break;
                case ',':
                    stack[stackPointer] = stdIn.charAt(stdinPointer);
                    if (stdinPointer < stdIn.length() - 1) stdinPointer++;
                    else break;
                    break;
                case '[':
                    if (stack[stackPointer] == 0) {
                        int brDeep = 1;
                        cmdPointer++;
                        for (; brDeep != 0; cmdPointer++) {
                            if (cmd.charAt(cmdPointer) == '[') brDeep++;
                            else if (cmd.charAt(cmdPointer) == ']') brDeep--;
                        }
                    }
                    break;
                case ']':
                    if (stack[stackPointer] != 0) {
                        int brDeep = 1;
                        cmdPointer--;
                        for (; brDeep != 0; cmdPointer--) {
                            if (cmd.charAt(cmdPointer) == ']') brDeep++;
                            else if (cmd.charAt(cmdPointer) == '[') brDeep--;
                        }
                    }
                    break;
                case '>':
                    stackPointer++;
                    break;
                case '<':
                    if (!(stackPointer == 0)) stackPointer--;
                    break;
                default:
                    if (strict) return null;
            }
        }
        return stdOut;
    }

    public static boolean check() {
        int lBracket = 0, rBracket = 0;
        for (char character : cmd.toCharArray()) {
            if (character == '[') lBracket++;
            if (character == ']') rBracket++;
        }
        return lBracket == rBracket;
    }
}
