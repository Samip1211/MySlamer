package com.example.hp.myslamer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by HP on 01-08-2015.
 */
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<QuestionInfo> questionInfos;

    public QuestionAdapter(List<QuestionInfo> questionInfos) {
        this.questionInfos = questionInfos;
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static  class QuestionViewHolder extends RecyclerView.ViewHolder {
        public QuestionViewHolder(View itemView) {
            super(itemView);
        }
    }
}
