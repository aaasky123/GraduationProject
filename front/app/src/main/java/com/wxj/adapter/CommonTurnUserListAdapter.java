package com.wxj.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wxj.R;
import com.wxj.entity.CommonTurnUser;

import java.util.ArrayList;

public class CommonTurnUserListAdapter extends ArrayAdapter<CommonTurnUser> {
        ArrayList<CommonTurnUser> commonTurnUsers;
        Context context;
        int resource;

        public CommonTurnUserListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<CommonTurnUser> commonTurnUsers) {
            super(context, resource, commonTurnUsers);
            this.commonTurnUsers = commonTurnUsers ;
            this.context = context;
            this.resource = resource;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null){
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.item_contactlist, null, true);
            }
            CommonTurnUser commonTurnUser=getItem(position);
            ImageView imageView=(ImageView) convertView.findViewById(R.id.contact_list_user_picture);
            TextView textName=(TextView) convertView.findViewById(R.id.contact_list_user_realname);
            TextView textEmployeeId=(TextView) convertView.findViewById(R.id.contact_list_user_number);
            textName.setText(commonTurnUser.getRealname());
            textEmployeeId.setText(commonTurnUser.getEmployeeId()+"");
            return convertView;
        }

}
