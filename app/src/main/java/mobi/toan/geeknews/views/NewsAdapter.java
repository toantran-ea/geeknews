package mobi.toan.geeknews.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mobi.toan.geeknews.R;
import mobi.toan.geeknews.models.net.GithubItem;

/**
 * Created by toantran on 10/20/15.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private List<GithubItem> mDataSet;
    private Context mContext;

    public NewsAdapter(Context context, List<GithubItem> dataSet) {
        if(dataSet != null) {
            mDataSet = dataSet;
        } else {
            mDataSet = new ArrayList<>();
        }
        mContext = context;
    }

    public void updateDataSet(List<GithubItem> items) {
        mDataSet.clear();
        mDataSet.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.news_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GithubItem item = mDataSet.get(position);
        holder.description.setText(item.getDescription());
        holder.title.setText(String.format("%s. %s", position + 1, item.getTitle()));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            title =  (TextView) itemView.findViewById(R.id.news_title);
            description = (TextView) itemView.findViewById(R.id.news_description);
        }
    }

    public GithubItem getItem(int position) {
        if(mDataSet.size() > position || position > -1) {
            return mDataSet.get(position);
        }
        return null;
    }
}
