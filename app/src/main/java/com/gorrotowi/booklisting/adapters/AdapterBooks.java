package com.gorrotowi.booklisting.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gorrotowi.booklisting.R;
import com.gorrotowi.booklisting.entitys.ItemBook;

import java.util.List;

/**
 * @author @gorrotowi
 */

public class AdapterBooks extends ArrayAdapter<ItemBook> {

    public AdapterBooks(Context context, List<ItemBook> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.item_book, parent, false);
        }

        ItemBook book = getItem(position);

        ImageView imgBook = (ImageView) listItemView.findViewById(R.id.imgItemBook);
        TextView txtTitle = (TextView) listItemView.findViewById(R.id.txtItemTitle);
        TextView txtAuthor = (TextView) listItemView.findViewById(R.id.txtItemAuthor);
        TextView txtYear = (TextView) listItemView.findViewById(R.id.txtItemYear);
        TextView txtPcount = (TextView) listItemView.findViewById(R.id.txtItemPcount);

        Glide.with(getContext()).load(book.getImgUrl()).into(imgBook);
        txtTitle.setText(book.getTitle());
        txtAuthor.setText(book.getAuthors());
        txtYear.setText(book.getYear());
        txtPcount.setText(String.format(getContext().getString(R.string.pagecount), book.getpCount()));

        return listItemView;

    }
}
