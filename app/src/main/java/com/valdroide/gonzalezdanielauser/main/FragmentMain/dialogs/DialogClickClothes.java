package com.valdroide.gonzalezdanielauser.main.FragmentMain.dialogs;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.valdroide.gonzalezdanielauser.R;
import com.valdroide.gonzalezdanielauser.entities.Clothes;
import com.valdroide.gonzalezdanielauser.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoViewAttacher;

public class DialogClickClothes {

    Context context;
    public AlertDialog alertDialog;
    @Bind(R.id.imageClothes)
    ImageView imageClothes;
    @Bind(R.id.textViewDescription)
    TextView textViewDescription;
    @Bind(R.id.textViewTitle)
    TextView textViewTitle;
    @Bind(R.id.buttonCerrar)
    Button buttonCerrar;


    public DialogClickClothes(Context context, Clothes clothes, String title) {
        this.context = context;
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.dialog_click_clothes, null);
        builder.setView(layout);
        ButterKnife.bind(this, layout);
        if(clothes != null) {
            textViewTitle.setText(title);

//            PhotoViewAttacher photoView = new PhotoViewAttacher(imageClothes);
//            photoView.update();

            if (clothes.getURL_PHOTO() != null)
                Utils.setPicasso(context, clothes.getURL_PHOTO(), R.mipmap.ic_imge_clothes, imageClothes);

            textViewDescription.setText(clothes.getDESCRIPTION());
        }
        buttonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog = builder.create();
        alertDialog.getWindow().setLayout(550, 500);
        alertDialog.show();
    }
}
