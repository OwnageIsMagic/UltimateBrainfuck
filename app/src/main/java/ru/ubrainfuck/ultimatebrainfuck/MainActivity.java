package ru.ubrainfuck.ultimatebrainfuck;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import ru.ubrainfuck.ultimatebrainfuck.interpreter.Interpreter;

public class MainActivity extends Activity {
    EditText source;
    EditText stdIn;
    TextView textView;
    TextView textView2;
    TextView textView3;
    CheckBox checkBox;
    TextView textView4;
    TextView textView5;
    TextView textView6;

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
        textView = (EditText) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        //textView4 = (TextView) findViewById(R.id.textView4);
        //textView5 = (TextView) findViewById(R.id.textView5);
        //textView6 = (TextView) findViewById(R.id.textView6);
        Interpreter.setContext(getApplicationContext());
    }

    public void redraw() {
        textView.setText(Interpreter.getStdOut());
        textView2.setText(Interpreter.getStack());
        textView3.setText(String.format("Stack pointer: %d\n" +
                "Cmd Pointer:  %d\n" +
                "Stdin Pointer: %d\n" +
                "Step: %d", Interpreter.getStackPointer(), Interpreter.getCmdPointer(), Interpreter.getStdinPointer(), Interpreter.getStep()));
    }

    public void clickRun(View view) {
        Interpreter.reset();
        if (checkBox.isChecked()) Interpreter.setDebuggable(true);
        Interpreter.setCmd(source.getText().toString());
        Interpreter.setStdIn(stdIn.getText().toString());
        Interpreter.eval();
        redraw();
    }

    public void clickView(View view) {
        //Toast.makeText(this, view.toString(), Toast.LENGTH_LONG).show();
        Interpreter.setCmd(source.getText().toString());
        Interpreter.step();
        redraw();
    }

    public void clickExit(View view) {
        finish();
    }

    public void startBFI(View v) {
        startActivity(new Intent(this, AnotherActivity.class));
    }

    public void reset(View v) {
        Interpreter.reset();
    }
}
