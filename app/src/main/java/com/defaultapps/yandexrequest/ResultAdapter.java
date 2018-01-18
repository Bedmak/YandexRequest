package com.defaultapps.yandexrequest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {

    private final List<Result> items;

    ResultAdapter() {
        items = new ArrayList<>();
    }

    @Override
    public ResultViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_link, parent, false);
        final ResultViewHolder vh = new ResultViewHolder(v);
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(items.get(vh.getAdapterPosition()).getLink()));
                parent.getContext().startActivity(intent);
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {
        int aPosition = holder.getAdapterPosition();
        holder.title.setText(items.get(aPosition).getTitle());
        holder.link.setText(items.get(aPosition).getLink());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setData(List<Result> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView link;

        ResultViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.linkTitle);
            link = view.findViewById(R.id.link);
        }
    }
}
