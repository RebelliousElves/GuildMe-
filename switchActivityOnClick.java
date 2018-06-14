package org.tensorflow.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


public class switchActivityOnClick implements AlertDialog.OnClickListener {



    private Context mAct;

    public switchActivityOnClick(Context act)
    {
        switchActivityOnClick.this.mAct = act;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case DialogInterface.BUTTON_POSITIVE:
                Intent intent = new Intent(mAct, DetectorActivity.class);
                mAct.startActivity(intent);
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                //No button clicked
                dialog.dismiss();
                break;
        }
    }
}
