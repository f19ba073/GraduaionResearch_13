package com.example.graduaionresearch_13;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends BaseAdapter{

    static class ViewHolder {
        TextView textView;
        Button menuButton;
    }

    private LayoutInflater inflater;
    private int itemLayoutId;
    private List<VocabularyBook> titles;

    ListViewAdapter(Context context,
                    int itemLayoutId,
                    List<VocabularyBook> itemNames) {
        super();
        this.inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.itemLayoutId = itemLayoutId;
        this.titles = itemNames;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        // 最初だけ View を inflate して、それを再利用する
        if (convertView == null) {
            // activity_main.xml に list.xml を inflate して convertView とする
            convertView = inflater.inflate(itemLayoutId, parent, false);
            // ViewHolder を生成
            holder = new ViewHolder();
            holder.textView = convertView.findViewById(R.id.textView);
            holder.menuButton = convertView.findViewById(R.id.menu);
            convertView.setTag(holder);
        }
        // holder を使って再利用
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 現在の position にあるファイル名リストを holder の textView にセット
        holder.textView.setText(titles.get(position).getBook_name());
        holder.menuButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ((ListView)parent).performItemClick(v,position,R.id.menu);
            }

        });
        return convertView;

    }

    @Override
    public int getCount() {
        // texts 配列の要素数
        return titles.size();
    }

    @Override
    public VocabularyBook getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void delete(int position){
        titles.remove(position);
    }
}
