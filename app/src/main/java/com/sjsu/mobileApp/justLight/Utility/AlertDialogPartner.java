package com.sjsu.mobileApp.justLight.Utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;



public class AlertDialogPartner
{
    public void showAlertDialog(Context context, String title, String message, Boolean status, final String userID, final String SolutionID)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle(title);


        alertDialog.setMessage(message);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                System.out.println("Inside ok :"+userID + SolutionID ); //TODO call api to un associate

            }
        });

        alertDialog.setButton3("Cancel", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
              System.out.println("Inside cancel: "+userID + SolutionID); //TODO nothing
            }
        });

        alertDialog.show();
    }
}
