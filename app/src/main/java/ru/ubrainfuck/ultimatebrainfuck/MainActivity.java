package ru.ubrainfuck.ultimatebrainfuck;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import ru.ubrainfuck.ultimatebrainfuck.interpreter.Interpreter;

public class MainActivity extends Activity {
    EditText source;
    EditText stdIn;
    CheckBox checkBoxD;
    TextView stdout;
    TextView stackView;
    TextView propertiesView;
    Button buttonRun;
    Button buttonReset;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //android:theme="@style/AppTheme>"
        source = (EditText) findViewById(R.id.sourceEditText);
        stdIn = (EditText) findViewById(R.id.stdinEditText);
        stdout = (EditText) findViewById(R.id.stdoutTextView);
        stackView = (TextView) findViewById(R.id.stackTextView);
        propertiesView = (TextView) findViewById(R.id.propertiesTextView);
        checkBoxD = (CheckBox) findViewById(R.id.checkBoxD);
        buttonRun = (Button) findViewById(R.id.ButtonRun);
        buttonReset = (Button) findViewById(R.id.ButtonReset);

        Interpreter.setContext(getApplicationContext());
    }

    public void clickRun(View view) {
        if (!checkBoxD.isChecked()) Interpreter.reset();
        Interpreter.setCmd(source.getText().toString());
        Interpreter.setStdIn(stdIn.getText().toString());
        Interpreter.eval();
        redraw();
    }

    private void showCursor() {
        source.getText().clearSpans();
        source.setText(source.getText().toString());
        if (Interpreter.getCmdPointer() < source.getText().length()) {
            SpannableString sourceText = new SpannableString(source.getText());
            ForegroundColorSpan style = new ForegroundColorSpan(Color.RED);
            sourceText.setSpan(style, Interpreter.getCmdPointer(), Interpreter.getCmdPointer() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            source.setText(sourceText);
        }
    }

    public void redraw() {
        if (checkBoxD.isChecked()) showCursor();
        stdout.setText(Interpreter.getStdOut());
        stackView.setText(Interpreter.getStack());
        propertiesView.setText(String.format("Stack pointer: %d [%c]\n" +
                        "Cmd Pointer:  %d [%c]\n" +
                        "Stdin Pointer: %d [%c]\n" +
                        "Step: %d\n",
                Interpreter.getStackPointer(), (char) Interpreter.getStack(Interpreter.getStackPointer()),
                Interpreter.getCmdPointer(), Interpreter.getCmd().charAt(Interpreter.getCmdPointer()),
                Interpreter.getStdinPointer(), Interpreter.getStdIn().charAt(Interpreter.getStdinPointer()),
                Interpreter.getStep()));
    }

    public void clickExit(View view) {
        finish();
    }

    public void clickBFI(View v) {
        startActivity(new Intent(this, AnotherActivity.class));
    }

    public void clickReset(View v) {
        Interpreter.reset();
        redraw();
    }

    public void checkboxClick(View view) {
        if (checkBoxD.isChecked()) {
            buttonRun.setText("Step");
            Interpreter.setDebuggable(true);
            buttonReset.setVisibility(View.VISIBLE);

        } else {
            buttonRun.setText("Run");
            Interpreter.setDebuggable(false);
            buttonReset.setVisibility(View.INVISIBLE);
        }

    }
}
