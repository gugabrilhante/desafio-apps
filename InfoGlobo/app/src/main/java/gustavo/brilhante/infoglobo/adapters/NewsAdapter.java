package gustavo.brilhante.infoglobo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import gustavo.brilhante.infoglobo.adapters.holders.NewsHolder;
import gustavo.brilhante.infoglobo.interfaces.AdapterCallback;
import gustavo.brilhante.infoglobo.models.Datum;
import gustavo.brilhante.infoglobo.models.DisplayObj;

/**
 * Created by Gustavo on 10/04/17.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    ArrayList<DisplayObj> list;

    AdapterCallback callback;

    public static Integer imageWitdh, layoutWitdh, height, heightToExpand;

    public NewsAdapter(ArrayList<DisplayObj> list, AdapterCallback callback)
    {
        this.list = list;
        this.callback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return NewsHolder.build(parent, callback);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewsHolder newsHolder = (NewsHolder) holder;
        newsHolder.bind(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
