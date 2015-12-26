package com.example.oriolburgaya.comandescambrer;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

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

    private final int[] mImatges;


    public ItemGridProductesAdapter(Context c, int[] mImatges) {
        mContext = c;
        this.mImatges = mImatges;
    }


    @Override
    public int getCount() {
        return mImatges.length;
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
            imageView.setImageResource(mImatges[i]);
        } else {
            grid = (View) view;
        }


        return grid;
    }
}
