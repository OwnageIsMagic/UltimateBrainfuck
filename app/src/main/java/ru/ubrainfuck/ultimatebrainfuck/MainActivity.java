package ru.ubrainfuck.ultimatebrainfuck;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.*;

public class MainActivity extends Activity
{
    EditText form;
    TextView resultView;
	EditText stdin;
    Toast toast;
    Interpreter interpreter;
	Context context;
    /** Called when the activity is first created. */
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //android:theme="@style/AppTheme>"
        form=(EditText) findViewById(R.id.mainEditTextInput);
        resultView=(TextView) findViewById(R.id.mainTextViewResult);
		stdin=(EditText) findViewById(R.id.mainStdin);
        toast = Toast.makeText(this, "", Toast.LENGTH_LONG);
		context=getApplicationContext();
    }
    public void clickRun(View view)
    {
	
        String cmd=form.getText().toString();
try {
        interpreter = new Interpreter(cmd);
		interpreter.stdin=this.stdin.getText().toString();
        resultView.setText("v+"+interpreter.eval());
        toast.setText(interpreter.eval());
        toast.show();
    } catch (NullPointerException e) {toast.setText(e.toString());toast.show();}
	}
public void clickExit( View view )
 {finish();}
 
		public void startBFI (View v) { startActivity(new Intent(this, AnotherActivity.class));}

//		public native String brainfF( );
//
//		static {
//			System.loadLibrary("brainF");
//			}
}
