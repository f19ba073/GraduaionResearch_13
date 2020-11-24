package com.example.graduaionresearch_13;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

//結果表示画面のListViewに渡すAdapter
class GameResultAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private int resourcedId;
    private List<Result> results;

    private static class ViewHolder{
        TextView problemText;
        TextView userAnswerText;
        TextView answerText;
        ImageView correctOrWrongImage;
    }

    public GameResultAdapter(Context context, int itemLayoutId, List<Result> list){
        super();
        this.inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resourcedId = itemLayoutId;
        this.results = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GameResultAdapter.ViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(resourcedId, parent, false);

            holder = new GameResultAdapter.ViewHolder();
            holder.problemText         = convertView.findViewById(R.id.result_problem_text);
            holder.userAnswerText      = convertView.findViewById(R.id.result_user_answer_text);
            holder.answerText          = convertView.findViewById(R.id.result_answer_text);
            holder.correctOrWrongImage = convertView.findViewById(R.id.result_correct_or_wrong);

            convertView.setTag(holder);
        }else {
            holder = (GameResultAdapter.ViewHolder) convertView.getTag();
        }

        holder.problemText      .setText(results.get(position).getProblem());
        holder.userAnswerText   .setText(results.get(position).getUserAnswer());
        holder.answerText       .setText(results.get(position).getAnswer());

        boolean isCorrect = results.get(position).isCorrect();
        int image = isCorrect ? R.drawable.ic_correct : R.drawable.ic_wrong;
        holder.correctOrWrongImage.setImageResource(image);

        return convertView;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
