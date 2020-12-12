package com.example.myvote;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CandListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<CustomCandidate> candidateArrayList;

    public CandListAdapter(Context context, int layout, ArrayList<CustomCandidate> candidateArrayList) {
        this.context = context;
        this.layout = layout;
        this.candidateArrayList = candidateArrayList;
    }

    @Override
    public int getCount() {
        return candidateArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return candidateArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView editTextName, editTextID, editTextVotes;

    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.editTextID = (TextView) row.findViewById(R.id.star_id);
            holder.editTextName = (TextView) row.findViewById(R.id.star_name);
            holder.editTextVotes = (TextView) row.findViewById(R.id.star_vote);
            holder.imageView = (ImageView) row.findViewById(R.id.start_photo);
            row.setTag(holder);
        } else {
          holder = (ViewHolder) row.getTag();
        }

        CustomCandidate customCandidate = candidateArrayList.get(position);

        holder.editTextID.setText(customCandidate.getIds());
        holder.editTextName.setText(customCandidate.getNames());
        holder.editTextVotes.setText(customCandidate.getVotes());

        byte[] candImage = customCandidate.getImages();
        Bitmap bitmap = BitmapFactory.decodeByteArray(candImage, 0, candImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
