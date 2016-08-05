package ru.onyanov.yesorno;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddActivity extends Activity {

	private static final String TAG = "AddActivity";

	@BindView(R.id.editQuestion) TextView questionView;
	@BindView(R.id.editComment) TextView commentView;
	@BindView(R.id.radioButtonYes) RadioButton answerYesView;

	@OnClick(R.id.add) void submit() {
		String question = questionView.getText().toString();
		String comment = commentView.getText().toString();
		boolean isCorrect = answerYesView.isChecked();

		if (question.equals("")) {
			questionView.requestFocus();
		} else {
			Log.d(TAG, "onClick: [" + question + ", " + comment + ", " + isCorrect + "]");
		}
		// TODO call server...
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		ButterKnife.bind(this);
	}
}
