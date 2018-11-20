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

public class AddCommentDialog extends AppCompatDialogFragment {
    private EditText comment;
    private AddCommentDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_comment,null);
        builder.setView(view)
                .setTitle("Login")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String commentEntered = comment.getText().toString();
                        listener.applyTexts(commentEntered);
                    }
                });
        comment = view.findViewById(R.id.enterComment);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener =(AddCommentDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+ "Must implement dialogListener");
        }
    }

    public interface AddCommentDialogListener{
        void applyTexts(String commentEntered);
    }
}
