package com.example.graduaionresearch_13;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.*;

public class ProblemAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private int resourcedId;
    private List<Problem> problemList;

    private static class ViewHolder {
        TextView textView;
        Button menuButton;
    }

    public ProblemAdapter(Context context, int resourcedId, List<Problem> list){
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resourcedId = resourcedId;
        this.problemList = list;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(resourcedId,parent,false);

            holder = new ViewHolder();

            holder.textView = convertView.findViewById(R.id.problem_text);
            holder.menuButton = convertView.findViewById(R.id.problem_menu);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(problemList.get(position).getProblem());
        holder.menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, R.id.problem_menu);
            }
        });

        return convertView;
    }

    public void delete(Context context, int position){
        problemList.get(position).delete(context);
        problemList.remove(position);
    }

    public void edit(Context context, int position, String problem, String answer){
        Problem item = problemList.get(position);
        item.setProblem(problem);
        item.setAnswer(answer);
    }

    @Override
    public int getCount() {
        return problemList.size();
    }

    @Override
    public Object getItem(int position) {
        return problemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return problemList.get(position).getId();
    }

}
