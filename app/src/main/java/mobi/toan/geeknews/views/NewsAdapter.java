package mobi.toan.geeknews.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mobi.toan.geeknews.R;
import mobi.toan.geeknews.models.net.NewsItem;

/**
 * Created by toantran on 10/20/15.
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private List<NewsItem> mDataSet;
    private Context mContext;

    public NewsAdapter(Context context, List<NewsItem> dataSet) {
        if(dataSet != null) {
            mDataSet = dataSet;
        } else {
            mDataSet = new ArrayList<>();
        }
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSet.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    public void updateDataSet(List<NewsItem> items) {
        mDataSet.clear();
        if(items != null) {
            mDataSet.addAll(items);
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;

        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(mContext)
                    .inflate(R.layout.news_item, parent, false);
            vh = new ViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.progress_item, parent, false);
            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsAdapter.ViewHolder) {
            NewsItem item = mDataSet.get(position);
            ViewHolder viewHolder = (ViewHolder)holder;
            viewHolder.description.setText(item.getDescription());
            viewHolder.title.setText(String.format("%s. %s", position + 1, item.getTitle()));
        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

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

    public NewsItem getItem(int position) {
        if(mDataSet.size() > position || position > -1) {
            return mDataSet.get(position);
        }
        return null;
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progress_bar);
        }
    }
}
