package com.wxj.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wxj.R;
import com.wxj.entity.File;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FileAdapter extends ArrayAdapter<File> {
    ArrayList<File> files;
    Context context;
    int resources;

    public FileAdapter(@NonNull Context context, int resource, @NonNull ArrayList<File> files){
        super(context, resource, files);
        this.context=context;
        this.resources=resource;
        this.files=files;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.itrm_file, null, true);
        }
        File file=getItem(position);
        TextView fileName=(TextView)convertView.findViewById(R.id.file_list_name);
        fileName.setText(file.getFileName());
        return convertView;
    }
}
