package medigram.medigram;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * This dialog displays the add comment dialog
 * when press confirm, it will pass the
 * information in the recordActivity
 *
 * @author Jeremy Xie
 */
public class EditCommentDialog extends AppCompatDialogFragment {

    private EditText comment;
    private EditCommentDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_comment,null);
        Bundle args = getArguments();
        String editComment = args.getString("comment");
        comment = view.findViewById(R.id.enterComment);
        comment.setText(editComment);
        builder.setView(view)
                .setTitle("Edit Comment")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String commentEntered = comment.getText().toString();
                        listener.applyEditTexts(commentEntered);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener =(EditCommentDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+ "Must implement dialogListener");
        }
    }

    public interface EditCommentDialogListener{
        void applyEditTexts(String commentEntered);
    }
}
