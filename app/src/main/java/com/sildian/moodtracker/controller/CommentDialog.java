package com.sildian.moodtracker.controller;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sildian.moodtracker.R;
import com.sildian.moodtracker.model.Mood;

public class CommentDialog extends Dialog {

    /**Attributes**/

    private EditText mCommentText;                  //Comment text
    private Button mValidateButton;                 //This button allows to validate the comment
    private Button mCancelButton;                   //This button allows to cancel the comment
    private Mood mMood;                             //The current mood

    /**
     * Constructor
     * @param context : the context
     * @param mood : the current mood
     */

    public CommentDialog(Context context, Mood mood){
        super(context);
        setContentView(R.layout.dialog_comment);

        /*Get the components from the layout*/

        mCommentText=findViewById(R.id.dialog_comment_text);
        mValidateButton=findViewById(R.id.dialog_comment_button_validate);
        mCancelButton=findViewById(R.id.dialog_comment_button_cancel);

        /*Get the mood*/

        mMood=mood;

        /*If the current mood already has a comment, then populates mCommentText with it*/

        if(mMood.getComment()!="")
            mCommentText.setText(mMood.getComment());

        /*If the user clicks on mValidateButton, validates the comment*/

        mValidateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMood.setComment(mCommentText.getText().toString());
                hide();
            }
        });

        /*If the user clicks on mCancelButton, cancels the comment*/

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
    }
}
