package com.example.student.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.student.R;
import com.example.student.model.Student;

import java.util.List;

public class StudentAdapter extends BaseAdapter {

    Context context;
    List<Student> studentList;

    public StudentAdapter(Context context, List<Student> studentList){
        this.context = context;
        this.studentList = studentList;
    }

    @Override
    public int getCount() {
        return studentList.size();
    }

    @Override
    public Object getItem(int position) {
        return studentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.student_item, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.avatar = view.findViewById(R.id.avatar);
            viewHolder.name = view.findViewById(R.id.ten_sv);
            viewHolder.mssv = view.findViewById(R.id.mssv);
            viewHolder.email = view.findViewById(R.id.email_sv);
            viewHolder.checkBox = view.findViewById(R.id.checkbox);
            view.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder)view.getTag();

        Student student = studentList.get(i);
        viewHolder.avatar.setText(""+student.getName().charAt(0));
        viewHolder.name.setText(student.getName());
        viewHolder.mssv.setText("mssv: " + student.getMssv());
        viewHolder.email.setText(student.getEmail());

        Drawable backgound = viewHolder.avatar.getBackground();
        backgound.setColorFilter(Color.parseColor(student.getAvatarColor()), PorterDuff.Mode.SRC_ATOP);

        if(student.isChecked()){
            viewHolder.checkBox.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_box_24, 0, 0, 0);
        }else{
            viewHolder.checkBox.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_check_box_outline_blank_24, 0, 0, 0);
        }

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                student.setChecked(!student.isChecked());
                notifyDataSetChanged();
            }
        });

        return view;
    }

    private class ViewHolder {
        TextView avatar;
        TextView name;
        TextView email;
        TextView mssv;
        TextView checkBox;
    }
}
