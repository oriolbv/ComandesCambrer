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

import com.example.oriolburgaya.comandescambrer.BD.ProductesComandaDataSource;
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

    private final ArrayList<Bitmap> mImatges;
    private ArrayList<Producte> productes;
    private int idComanda;


    public ItemGridProductesAdapter(Context c, ArrayList<Bitmap> mImatges, ArrayList<Producte> productes, int idComanda) {
        mContext = c;
        this.mImatges = mImatges;
        this.productes = productes;
        this.idComanda = idComanda;
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
            TextView tv_idProducte = (TextView) grid.findViewById(R.id.tv_idProducte);
            TextView tv_PreuTotalProducte = (TextView) grid.findViewById(R.id.tv_PreuTotalProducte);

            ProductesComandaDataSource productesComandaDataSource = new ProductesComandaDataSource(mContext);
            int qttProducte = productesComandaDataSource.getQttProductesComanda(Integer.parseInt(productes.get(i).getId()), idComanda);
            double preuTotal = productesComandaDataSource.getPreuTotalProductesComanda(Integer.parseInt(productes.get(i).getId()), idComanda);
            Log.i("Info Product: ", "idProducte: "+ productes.get(i).getId() +" idComanda: "+ idComanda + " -------- " + "qtt: " + qttProducte + " preu: "+ preuTotal);

            tv_idProducte.setText(productes.get(i).getId());
            tv_NomProducte.setText(productes.get(i).getNom());
            tv_PreuProducte.setText(String.valueOf(productes.get(i).getPreu()) + " €");
            tv_QttProducte.setText(Integer.toString(qttProducte));
            tv_PreuTotalProducte.setText(String.valueOf(preuTotal));

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
                TextView tv_PreuProducte = (TextView) row.findViewById(R.id.tv_PreuUnitatProducte);
                TextView tv_PreuTotalProducte = (TextView) row.findViewById(R.id.tv_PreuTotalProducte);
                String sPreuUnitat = tv_PreuProducte.getText().toString().replace("€", "").replace(" ", "");
                double preuUnitat = Double.parseDouble(sPreuUnitat);
                double preuTotal = preuUnitat*novaQtt;
                tv_PreuTotalProducte.setText(String.format("%.2f", preuTotal) + " €");

                ProductesComandaDataSource productesComandaDataSource = new ProductesComandaDataSource(mContext);
                TextView tv_idProducte = (TextView) row.findViewById(R.id.tv_idProducte);
                Log.i("ID", tv_idProducte.getText().toString());
                productesComandaDataSource.modificarProductesComanda(Integer.parseInt(tv_idProducte.getText().toString()), idComanda, novaQtt);

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
                    TextView tv_PreuProducte = (TextView) row.findViewById(R.id.tv_PreuUnitatProducte);
                    TextView tv_PreuTotalProducte = (TextView) row.findViewById(R.id.tv_PreuTotalProducte);
                    String sPreuUnitat = tv_PreuProducte.getText().toString().replace("€", "").replace(" ", "");
                    double preuUnitat = Double.parseDouble(sPreuUnitat);
                    double preuTotal = preuUnitat*novaQtt;
                    tv_PreuTotalProducte.setText(String.format("%.2f", preuTotal) + " €");

                    ProductesComandaDataSource productesComandaDataSource = new ProductesComandaDataSource(mContext);
                    TextView tv_idProducte = (TextView) row.findViewById(R.id.tv_idProducte);
                    Log.i("ID", tv_idProducte.getText().toString());
                    productesComandaDataSource.modificarProductesComanda(Integer.parseInt(tv_idProducte.getText().toString()), idComanda, novaQtt);
                }

            }
        });
        return row;
    }
}
