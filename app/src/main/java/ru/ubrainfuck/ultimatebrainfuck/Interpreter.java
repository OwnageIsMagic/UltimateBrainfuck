package ru.ubrainfuck.ultimatebrainfuck;

import android.app.Activity;

/**
 * Created by
 */
public class Interpreter
{
    private int stack[];
    short poiner=0;
    String cmd="q";
    String result="res";
    boolean check=true;
    Activity context1;

    public Interpreter(String cmdd){cmd=cmdd;}
    public Interpreter() {}

    public String eval(String cmnd) {this.cmd=cmnd; return eval();}
    public String eval()
    {
        if (check) check();

        return result;
    }

    public boolean check( )
    {
        int lBracket=0, rBracket=0;
        for ( char character : cmd.toCharArray() )
        {
            if ( character == '[' ) lBracket++;
            if ( character == ']' ) rBracket++;
        }
        return lBracket == rBracket;
    }
}
