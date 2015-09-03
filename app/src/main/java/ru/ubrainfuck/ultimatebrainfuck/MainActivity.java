package ru.ubrainfuck.ultimatebrainfuck;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity
{
    EditText form;
    TextView resultView;
    Toast toast;
    Interpreter interpreter;
    /** Called when the activity is first created. */
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //android:theme="@style/AppTheme>"
        form=(EditText) findViewById(R.id.mainEditTextInput);
        resultView=(TextView) findViewById(R.id.mainTextViewResult);
        toast = Toast.makeText(this, "", Toast.LENGTH_LONG);
    }
    public void clickRun(View view)
    {
        String cmd=form.getText().toString();

        interpreter = new Interpreter(cmd);
        resultView.setText(interpreter.eval());
        toast.setText(interpreter.eval());
        toast.show();
    }
    public void clickExit(View view) {finish();}

//		public native String brainfF( );
//
//		static {
//			System.loadLibrary("brainF");
//			}
}
