package exception;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by luisresende on 07/05/15.
 */
public class GenericAlertDialogException {
    static AlertDialog alert;

    public void createAlert(Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Um erro inesperado ocorreu!");

        builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });

        alert = builder.create();
        alert.show();
    }
}
