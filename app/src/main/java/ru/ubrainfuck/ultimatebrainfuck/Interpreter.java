package ru.ubrainfuck.ultimatebrainfuck;

import android.app.AlertDialog.*;
import android.content.*;
import android.content.DialogInterface.*;
import android.widget.*;
import javax.xml.transform.*;

import android.content.DialogInterface.OnClickListener;
import java.lang.reflect.*;

/**
 * Created by
 */
public class Interpreter
	{
		private int stack[]={0};
		short stackPoiner=0;
		//int cmdPointer=0;
		String cmd="";
		String result="";
		String stdin="";
		int stdinPointer=0;
		boolean check=true;
		boolean strict=false;
		Context cont;
		
		public void toaster(String text) {
			Toast.makeText(cont, text, 0).show();
			}

		public Interpreter( String cmdd )
			{cmd = cmdd;}
		public Interpreter( )
			{}

		public String eval( String cmnd )
			{cmd = cmnd; return eval();}
		public String eval( )
			{
			if ( check ) if ( !check() ) return null;

			for ( int cmdPointer=0;cmdPointer <= cmd.length() - 1;cmdPointer++ )
				{
				switch ( cmd.charAt(cmdPointer) )
					{
					case '+': stack[stackPoiner]++;break;
					case '-': stack[stackPoiner]--;break;
					case '.': 
						result += (char) stack[stackPoiner];
						break;
					case ',':
						stack[stackPoiner]=stdin.charAt(stdinPointer);
						stdinPointer++;
						break;
					case '[':
						if ( stack[stackPoiner] == 0 ) 
							{
							int brDeep=1;
							cmdPointer++;
							for ( ;brDeep != 0;cmdPointer++ )
								{
								if ( cmd.charAt(cmdPointer) == '[' )brDeep++;
								else if ( cmd.charAt(cmdPointer) == ']' )brDeep--;
								}
							}
						break;
					case ']':
						if ( stack[stackPoiner] != 0 )
							{
							int brDeep=1;
							cmdPointer--;
							for ( ;brDeep != 0;cmdPointer-- )
								{
								if ( cmd.charAt(cmdPointer) == ']' )brDeep++;
								else if ( cmd.charAt(cmdPointer) == '[' )brDeep--;
								}
							}
						break;
					case '>':
						
						break;
					case '<':
						
						break;
					default:if ( strict ) return null;
					}
				}
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
