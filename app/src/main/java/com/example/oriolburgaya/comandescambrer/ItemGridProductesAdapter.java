package com.example.oriolburgaya.comandescambrer;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oriolburgaya.comandescambrer.models.Producte;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static android.view.View.inflate;
import static android.widget.ImageView.ScaleType;

/**
 * Created by oriolbv on 26/12/15.
 */
public class ItemGridProductesAdapter extends BaseAdapter {

    private Context mContext;
    // references to our images
//    private int[] mImatges = {
//            R.drawable.bistecPatates, R.drawable.coca,
//            R.drawable.espaguetis_bolonyesa, R.drawable.pastis_chocolata
//    };

    private final ArrayList<Bitmap> mImatges;
    private ArrayList<Producte> productes;


    public ItemGridProductesAdapter(Context c, ArrayList<Bitmap> mImatges, ArrayList<Producte> productes) {
        mContext = c;
        this.mImatges = mImatges;
        this.productes = productes;
    }


    @Override
    public int getCount() {
        return mImatges.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.productes_grid_item, null);

            ImageView imageView = (ImageView) grid.findViewById(R.id.gridImage);
            imageView.setImageBitmap(mImatges.get(i));
            TextView tv_NomProducte = (TextView) grid.findViewById(R.id.tv_NomProducteMostrar);
            TextView tv_QttProducte = (TextView) grid.findViewById(R.id.tv_QttProducte);
            TextView tv_PreuProducte = (TextView) grid.findViewById(R.id.tv_PreuUnitatProducte);
            Button btn_decrementarProducte = (Button) grid.findViewById(R.id.btn_DecrementarQtt);
            tv_NomProducte.setText(productes.get(i).getNom());
            tv_PreuProducte.setText(String.valueOf(productes.get(i).getPreu()) + " €");
        } else {
            grid = (View) view;
        }
        final View row = grid;
        row.setId(i);
        Button btn_decrementarProducte = (Button) row.findViewById(R.id.btn_DecrementarQtt);
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv_qttProducte = (TextView) row.findViewById(R.id.tv_QttProducte);
                int qttActual = Integer.parseInt(tv_qttProducte.getText().toString());
                int novaQtt = qttActual + 1;
                tv_qttProducte.setText("" + novaQtt);

            }
        });

        btn_decrementarProducte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv_qttProducte = (TextView) row.findViewById(R.id.tv_QttProducte);
                int qttActual = Integer.parseInt(tv_qttProducte.getText().toString());
                if (qttActual > 0) {
                    int novaQtt = qttActual - 1;
                    tv_qttProducte.setText("" + novaQtt);
                }
            }
        });
        return row;
    }
}
