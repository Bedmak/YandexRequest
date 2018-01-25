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

    private final List<Result> initialItems;
    private final List<Result> items;

    private int currentPosition;

    ResultAdapter() {
        initialItems = new ArrayList<>();
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
        initialItems.clear();
        initialItems.addAll(items);
        currentPosition = 0;
        fillItems();
    }

    public void previousPage() {
        currentPosition -= 6;
        fillItems();
    }

    public void fillItems() {
        items.clear();
        int itemsCounter = 0;
        for (int i=currentPosition; i < initialItems.size() && itemsCounter < 3; i++) {
            items.add(initialItems.get(i));
            itemsCounter++;
        }
        currentPosition += 3;
        notifyDataSetChanged();
    }

    public boolean hasNextPage() {
        return initialItems.size() > currentPosition + 1;
    }

    boolean hasPreviousPage() {
        return currentPosition > 3;
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
