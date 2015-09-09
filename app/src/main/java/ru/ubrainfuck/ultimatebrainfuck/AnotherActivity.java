package ru.ubrainfuck.ultimatebrainfuck;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class AnotherActivity extends Activity {
	String input = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anotheractivity);
		setTitle("");
		EditText sourceEdit = (EditText) findViewById(R.id.editText1);
		sourceEdit.setBackgroundColor(0xFFCCCCCC);
		findViewById(R.id.editText2).setBackgroundColor(0xFFCCCCCC);
		hideKeyboard(sourceEdit);
	}

	public void hideKeyboard(EditText mEditView) {
		((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(mEditView.getWindowToken(), 0);
	}

	public void Relist(ArrayList<Integer> Arr) {
		((ListView) findViewById(R.id.listView1)).setAdapter(new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, Arr));
	}

	public boolean checkcode(final String Source) {
		int count = 0;
		boolean need_in = false;
		for (Integer i = 0; i < Source.length(); i = i + 1) {
			if (Source.charAt(i) == '[') {
				count++;
			} else if (Source.charAt(i) == ']') {
				count--;
			}
			if (Source.charAt(i) == ',') {
				need_in = true;
			}
		}
		if (count != 0) {
			return false;
		}
		if (need_in) {
			Builder alert = new Builder(this);
			final EditText inputName = new EditText(this);
			alert.setView(inputName);
			alert.setMessage("input");

			alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					AnotherActivity.this.input = inputName.getText().toString();
					AnotherActivity.this.interpret(Source);
				}
			});
			alert.show();
		} else {
			this.input = "";
			interpret(Source);
		}
		return true;
	}

	public String optimize_Code(String Code) {
		String newCode = "";
		int i = 0;
		while (i < Code.length() - 1) {
			if (Code.charAt(i) == '>' && Code.charAt(i + 1) == '<') {
				i++;
			} else if (Code.charAt(i) == '<' && Code.charAt(i + 1) == '>') {
				i++;
			} else if (Code.charAt(i) == '+' && Code.charAt(i + 1) == '-') {
				i++;
			} else if (Code.charAt(i) == '-' && Code.charAt(i + 1) == '+') {
				i++;
			} else {
				newCode = String.valueOf(newCode) + Code.charAt(i);
			}
			i++;
		}
		return newCode;
	}

	public void erroring_a_box(String Errormessage) {
		AlertDialog alertDialog = new Builder(this).create();
		alertDialog.setTitle("Error.");
		alertDialog.setMessage(Errormessage);
		alertDialog.show();
	}

	public void interpret(String Source) {
		int CurrentArr = 0;
		ArrayList<Integer> Arr = new ArrayList<Integer>();
		EditText outputedit = (EditText) findViewById(R.id.editText2);
		outputedit.setText("");
		Arr.add(0);
		Relist(Arr);
		int inputCounter = 0;
		int i = 0;
		int infiniteControl = 0;
		while (i < Source.length()) {
			infiniteControl++;
			if (infiniteControl > Source.length() * 10000) {
				erroring_a_box("Timeout. " + infiniteControl);
				return;
			}
			switch (Source.charAt(i)) {
				case '>':
					if (Arr.size() - 1 == CurrentArr) {
						CurrentArr++;
						Arr.add(0);
					} else {
						CurrentArr++;
					}
					break;
				case '<':
					if (CurrentArr == 0) {
						CurrentArr = Arr.size() - 1;
					} else {
						CurrentArr--;
					}
					break;
				case '+':
					Arr.set(CurrentArr, Arr.get(CurrentArr) + 1);
					break;
				case '-':
					if (Arr.get(CurrentArr) != 0) {
						Arr.set(CurrentArr, Arr.get(CurrentArr) - 1);
					}
					break;
				case '.':
					outputedit.setText(String.valueOf(outputedit.getText().toString()) + (char) Arr.get(CurrentArr).intValue());
					break;
				case ',':
					if (inputCounter < input.length()) {
						Arr.set(CurrentArr, (int) input.charAt(inputCounter));
						inputCounter++;
					} else {
						Arr.set(CurrentArr, 0);
					}
					break;
				case '[':
					if (Arr.get(CurrentArr) == 0) {
						int loop = 1;
						while (loop > 0) {
							i++;
							if (Source.charAt(i) == '[') {
								loop++;
							} else if (Source.charAt(i) == ']') {
								loop--;
							}
						}
					}
					break;
				case ']':
					int loop = 1;
					while (loop > 0) {
						i--;
						if (Source.charAt(i) == '[') {
							loop--;
						} else if (Source.charAt(i) == ']') {
							loop++;
						}
					}
					i--;
					break;
			}
			i++;


		}
		Relist(Arr);
		hideKeyboard(outputedit);
	}

	public void generatebfsource(String Eingabe) {
		String BFSource = "";
		char lastIn = '\u0000';
		for (Integer i = 0; i < Eingabe.length(); i = i + 1) {
			Integer Difference;
			Integer j;
			if (Eingabe.charAt(i) > lastIn) {
				Difference = Eingabe.charAt(i) - lastIn;
				if (Difference > 20) {
					for (j = 0; j < (int) Math.sqrt((double) Difference); j = j + 1) {
						BFSource += "+";
					}
					BFSource += "[>";
					for (j = 0; j < Difference / (int) Math.sqrt((double) Difference); j = j + 1) {
						BFSource += "+";
					}
					BFSource += "<-]>";
					for (j = 0; j < Difference % (int) Math.sqrt((double) Difference); j = j + 1) {
						BFSource += "+";
					}
					BFSource += ".<";
				} else {
					BFSource += ">";
					for (j = 0; j < Difference; j = j + 1) {
						BFSource += "+";
					}
					BFSource += ".<";
				}
			} else {
				Difference = lastIn - Eingabe.charAt(i);
				if (Difference == 0) {
					BFSource += ">.<";
				} else if (Difference > 20) {
					for (j = 0; j < (int) Math.sqrt((double) Difference); j = j + 1) {
						BFSource += "+";
					}
					BFSource += "[>";
					for (j = 0; j < Difference / (int) Math.sqrt((double) Difference); j = j + 1) {
						BFSource += "-";
					}
					BFSource += "<-]>";
					for (j = 0; j < Difference % (int) Math.sqrt((double) Difference); j = j + 1) {
						BFSource += "-";
					}
					BFSource += ".<";
				} else {
					BFSource += ">";
					for (j = 0; j < Difference; j = j + 1) {
						BFSource += "-";
					}
					BFSource += ".<";
				}
			}
			lastIn = Eingabe.charAt(i);
		}
		((EditText) findViewById(R.id.editText1)).setText(optimize_Code(BFSource));
	}

	public void examplebox() {
		new Builder(this).setItems(new String[]{"e1", "e2", "e3", "e4"}, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialoginterface, int i) {
				EditText sourceedit = (EditText) findViewById(R.id.editText1);
				switch (i) {
					case 0:
						sourceedit.setText("+[,+++.---]");
						return;
					case 1:
						sourceedit.setText("++++++++++[>++++++++++>++++++++++++<<-]>--.>------.<-.++++++++.>----.<---.>+++++++.<---.++++++++.  ");
						return;
					case 2:
						sourceedit.setText("+++++[>+++++<-]>");
						return;
					case 3:
						sourceedit.setText(",[>,]<[.<]  ");
						return;
					default:
				}
			}
		}).show();
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		String Source = ((EditText) findViewById(R.id.editText1)).getText().toString();
		switch (item.getItemId()) {
			case R.id.menu_examples /*2131230725*/:
				examplebox();
				return true;
			case R.id.menu_interprete /*2131230726*/:
				if (checkcode(Source)) {
					return true;
				}
				erroring_a_box("Syntax is not correct.");
				return true;
			case R.id.menu_settings /*2131230727*/:

				return true;
			case R.id.menu_customtextbf /*2131230728*/:
				Builder alert = new Builder(this);
				final EditText inputName = new EditText(this);
				alert.setView(inputName);
				alert.setMessage("input");
				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						generatebfsource(inputName.getText().toString());
					}
				});
				alert.show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.anothermenu, menu);
		return true;
	}

}
