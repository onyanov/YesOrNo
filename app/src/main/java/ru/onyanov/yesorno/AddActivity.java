package ru.onyanov.yesorno;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class AddActivity extends Activity {

	private Button button;
	private TextView questionView;
	private TextView commentView;
	private Spinner answerView;
	private String question;
	private String comment;
	private int answer = 0;
	private RadioButton answerYesView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		
		button = (Button) findViewById(R.id.add);
		questionView = (TextView) findViewById(R.id.editQuestion);
		answerYesView = (RadioButton) findViewById(R.id.radioButtonYes);
		RadioButton answerNoView = (RadioButton) findViewById(R.id.radioButtonNo);
		commentView = (TextView) findViewById(R.id.editComment);
		
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				question = (String) questionView.getText().toString();
				comment = (String) commentView.getText().toString();
				
				if (question.equals("")) {
					questionView.requestFocus();
				} else {
					
				}
			}
		});
		
		answerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		        answer = pos;
		    }
		    public void onNothingSelected(AdapterView<?> parent) {
		    }

		});


		//answerNoView.setOnCheckedChangeListener(answerListener);
	}
	
}
