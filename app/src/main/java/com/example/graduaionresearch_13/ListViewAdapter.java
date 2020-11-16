package com.example.graduaionresearch_13;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import static com.example.graduaionresearch_13.DBNames.*;

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

    public void delete(Context context, int position){
        titles.get(position).delete(context);
        titles.remove(position);
    }

    public void edit(Context context, int position, String title){
        VocabularyBook item = titles.get(position);
        item.setBook_name(title);
        titles.get(position).update(context);
    }

    public VocabularyBook addList(Context context, String title){
        int book_id = VocabularyBook.getNewId(context);
        DBOpenHelper helper = new DBOpenHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues bookValues = new ContentValues();

        bookValues.put(COLUMN_NAME_BOOK_NAME, title);
        bookValues.put(COLUMN_NAME_ID, book_id);
        db.insert(TABLE_NAME_BOOKS, null, bookValues);
        this.titles.clear();
        this.titles.addAll(VocabularyBook.getList(context));
        return new VocabularyBook(book_id,title);
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
}
