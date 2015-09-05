package ru.ubrainfuck.ultimatebrainfuck;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ru.ubrainfuck.ultimatebrainfuck.interpreter.Interpreter;

public class MainActivity extends Activity {
    EditText source;
    EditText stdIn;
    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    //Toast toast;
    Context context;

    /**
     * Called when the activity is first created.
     */
    @SuppressLint("ShowToast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //android:theme="@style/AppTheme>"
        source = (EditText) findViewById(R.id.mainEditTextInput);
        stdIn = (EditText) findViewById(R.id.mainStdin);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView4 = (TextView) findViewById(R.id.textView4);
        textView5 = (TextView) findViewById(R.id.textView5);
        textView6 = (TextView) findViewById(R.id.textView6);
        //toast = Toast.makeText(this, "", Toast.LENGTH_LONG);
        context = getApplicationContext();
        Interpreter.setContext(context);
    }

    public void clickRun(View view) {
        //Interpreter.setCmd();
        textView.setText(Interpreter.eval(source.getText().toString()));
        Interpreter.reset();
    }

    public void clickView(View view) {
        Toast.makeText(this, view.toString(), Toast.LENGTH_LONG).show();
    }

    public void clickExit(View view) {
        finish();
    }

    public void startBFI(View v) {
        startActivity(new Intent(this, AnotherActivity.class));
    }

}
