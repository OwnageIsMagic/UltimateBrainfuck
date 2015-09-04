package ru.ubrainfuck.ultimatebrainfuck;

import android.*;
import android.app.*;
import android.app.AlertDialog.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.inputmethod.*;
import android.widget.*;
import java.util.*;
public class AnotherActivity extends Activity
 {
				String input = "";
				
				protected void onCreate(Bundle savedInstanceState) {
					super.onCreate(savedInstanceState);
					setContentView(R.layout.anotheractivity);
					setTitle("");
					EditText sourceedit = (EditText) findViewById(R.id.editText1);
					sourceedit.setBackgroundColor(-3355444);
					((EditText) findViewById(R.id.editText2)).setBackgroundColor(-3355444);
					hideKeyboard(sourceedit);
					}
				
				public void hideKeyboard(EditText mEditView) {
					((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(mEditView.getWindowToken(), 0);
					}
				
				public void Relist(ArrayList Arr) {
					((ListView) findViewById(R.id.listView1)).setAdapter(new ArrayAdapter(this, 17367043, Arr));
					}
				
				public void toaster(String text) {
					Toast.makeText(getApplicationContext(), text, 0).show();
					}
				
				public boolean checkcode(final String Source) {
					int count = 0;
					boolean need_in = false;
					for (Integer i = Integer.valueOf(0); i.intValue() < Source.length(); i = Integer.valueOf(i.intValue() + 1)) {
						if (Source.charAt(i.intValue()) == '[') {
							count++;
							} else if (Source.charAt(i.intValue()) == ']') {
							count--;
							}
						if (Source.charAt(i.intValue()) == ',') {
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
						
						alert.setPositiveButton("OK" , new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int whichButton) {
										AnotherActivity.this.input = inputName.getText().toString();
										AnotherActivity.this.interprete(Source);
										}
								});
						alert.show();
						} else {
						this.input = "";
						interprete(Source);
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
							newCode = new StringBuilder(String.valueOf(newCode)).append(Code.charAt(i)).toString();
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
				
				public void interprete(String Source) {
					Integer CurrentArr = Integer.valueOf(0);
					int InfiniteControl = 0;
					ArrayList<Integer> Arr = new ArrayList();
					EditText outputedit = (EditText) findViewById(R.id.editText2);
					outputedit.setText("");
					Arr.add(Integer.valueOf(0));
					Relist(Arr);
					int input_counter = 0;
					Integer i = Integer.valueOf(0);
					while (i.intValue() < Source.length()) {
						InfiniteControl++;
						if (InfiniteControl > Source.length() * 10000) {
							erroring_a_box("Timeout. " + InfiniteControl);
							return;
							}
						if (Source.charAt(i.intValue()) == '>') {
							if (Arr.size() - 1 == CurrentArr.intValue()) {
								CurrentArr = Integer.valueOf(CurrentArr.intValue() + 1);
								Arr.add(Integer.valueOf(0));
								} else {
								CurrentArr = Integer.valueOf(CurrentArr.intValue() + 1);
								}
							} else if (Source.charAt(i.intValue()) == '<') {
							if (CurrentArr.intValue() == 0) {
								CurrentArr = Integer.valueOf(Arr.size() - 1);
								} else {
								CurrentArr = Integer.valueOf(CurrentArr.intValue() - 1);
								}
							} else if (Source.charAt(i.intValue()) == '+') {
							Arr.set(CurrentArr.intValue(), Integer.valueOf(((Integer) Arr.get(CurrentArr.intValue())).intValue() + 1));
							} else if (Source.charAt(i.intValue()) == '-') {
							if (((Integer) Arr.get(CurrentArr.intValue())).intValue() != 0) {
								Arr.set(CurrentArr.intValue(), Integer.valueOf(((Integer) Arr.get(CurrentArr.intValue())).intValue() - 1));
								}
							} else if (Source.charAt(i.intValue()) == '.') {
							outputedit.setText(new StringBuilder(String.valueOf(outputedit.getText().toString())).append((char) ((Integer) Arr.get(CurrentArr.intValue())).intValue()).toString());
							} else if (Source.charAt(i.intValue()) == ',') {
							if (input_counter < this.input.length()) {
								Arr.set(CurrentArr.intValue(), Integer.valueOf(this.input.charAt(input_counter)));
								input_counter++;
								} else {
								Arr.set(CurrentArr.intValue(), Integer.valueOf(0));
								}
							} else if (Source.charAt(i.intValue()) == '[') {
							if (((Integer) Arr.get(CurrentArr.intValue())).intValue() == 0) {
								int loop = 1;
								while ( loop > 0 )
									{
									i = Integer.valueOf(i.intValue() + 1);
									if (Source.charAt(i.intValue()) == '[') {
										loop++;
										} else if (Source.charAt(i.intValue()) == ']') {
										loop--;
										}
									}
								}
							} else if (Source.charAt(i.intValue()) == ']') {
								int loop = 1;
								while ( loop > 0 )
									{
									i = Integer.valueOf(i.intValue() - 1);
								if (Source.charAt(i.intValue()) == '[') {
									loop--;
									} else if (Source.charAt(i.intValue()) == ']') {
									loop++;
									}
								}
							i = Integer.valueOf(i.intValue() - 1);
							}
						i = Integer.valueOf(i.intValue() + 1);
						}
					Relist(Arr);
					hideKeyboard(outputedit);
					}
				
				public void generatebfsource(String Eingabe) {
					String BFSource = "";
					char lastIn = '\u0000';
					for (Integer i = Integer.valueOf(0); i.intValue() < Eingabe.length(); i = Integer.valueOf(i.intValue() + 1)) {
						Integer Difference;
						Integer j;
						if (Eingabe.charAt(i.intValue()) > lastIn) {
							Difference = Integer.valueOf(Eingabe.charAt(i.intValue()) - lastIn);
							if (Difference.intValue() > 20) {
								for (j = Integer.valueOf(0); j.intValue() < ((int) Math.sqrt((double) Difference.intValue())); j = Integer.valueOf(j.intValue() + 1)) {
									BFSource = new StringBuilder(String.valueOf(BFSource)).append("+").toString();
									}
								BFSource = new StringBuilder(String.valueOf(BFSource)).append("[>").toString();
								for (j = Integer.valueOf(0); j.intValue() < Difference.intValue() / ((int) Math.sqrt((double) Difference.intValue())); j = Integer.valueOf(j.intValue() + 1)) {
									BFSource = new StringBuilder(String.valueOf(BFSource)).append("+").toString();
									}
								BFSource = new StringBuilder(String.valueOf(BFSource)).append("<-]>").toString();
								for (j = Integer.valueOf(0); j.intValue() < Difference.intValue() % ((int) Math.sqrt((double) Difference.intValue())); j = Integer.valueOf(j.intValue() + 1)) {
									BFSource = new StringBuilder(String.valueOf(BFSource)).append("+").toString();
									}
								BFSource = new StringBuilder(String.valueOf(BFSource)).append(".<").toString();
								} else {
								BFSource = new StringBuilder(String.valueOf(BFSource)).append(">").toString();
								for (j = Integer.valueOf(0); j.intValue() < Difference.intValue(); j = Integer.valueOf(j.intValue() + 1)) {
									BFSource = new StringBuilder(String.valueOf(BFSource)).append("+").toString();
									}
								BFSource = new StringBuilder(String.valueOf(BFSource)).append(".<").toString();
								}
							} else {
							Difference = Integer.valueOf(lastIn - Eingabe.charAt(i.intValue()));
							if (Difference.intValue() == 0) {
								BFSource = new StringBuilder(String.valueOf(BFSource)).append(">.<").toString();
								} else if (Difference.intValue() > 20) {
								for (j = Integer.valueOf(0); j.intValue() < ((int) Math.sqrt((double) Difference.intValue())); j = Integer.valueOf(j.intValue() + 1)) {
									BFSource = new StringBuilder(String.valueOf(BFSource)).append("+").toString();
									}
								BFSource = new StringBuilder(String.valueOf(BFSource)).append("[>").toString();
								for (j = Integer.valueOf(0); j.intValue() < Difference.intValue() / ((int) Math.sqrt((double) Difference.intValue())); j = Integer.valueOf(j.intValue() + 1)) {
									BFSource = new StringBuilder(String.valueOf(BFSource)).append("-").toString();
									}
								BFSource = new StringBuilder(String.valueOf(BFSource)).append("<-]>").toString();
								for (j = Integer.valueOf(0); j.intValue() < Difference.intValue() % ((int) Math.sqrt((double) Difference.intValue())); j = Integer.valueOf(j.intValue() + 1)) {
									BFSource = new StringBuilder(String.valueOf(BFSource)).append("-").toString();
									}
								BFSource = new StringBuilder(String.valueOf(BFSource)).append(".<").toString();
								} else {
								BFSource = new StringBuilder(String.valueOf(BFSource)).append(">").toString();
								for (j = Integer.valueOf(0); j.intValue() < Difference.intValue(); j = Integer.valueOf(j.intValue() + 1)) {
									BFSource = new StringBuilder(String.valueOf(BFSource)).append("-").toString();
									}
								BFSource = new StringBuilder(String.valueOf(BFSource)).append(".<").toString();
								}
							}
						lastIn = Eingabe.charAt(i.intValue());
						}
					((EditText) findViewById(R.id.editText1)).setText(optimize_Code(BFSource));
					}
				
				public void examplebox() {
					new Builder(this).setItems(new String[]{"e1","e2","e3","e4"}, new DialogInterface.OnClickListener() {
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
											return;
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
