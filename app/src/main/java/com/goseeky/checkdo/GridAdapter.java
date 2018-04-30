package com.goseeky.checkdo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goseeky.checkdo.BusinessObjects.FolderBO;
import com.goseeky.checkdo.BusinessObjects.TaskBO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by abhay on 28/4/18.
 * Adapter for task and folder grid
 */

public class GridAdapter extends BaseAdapter {
    private Context mContext;
    // references to our images
    private List<FolderBO> folderBOS = new ArrayList<>();
    private List<TaskBO> taskBOS = new ArrayList<>();

    GridAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return this.folderBOS.size() + this.taskBOS.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single, null);

        } else {
            grid = (View) convertView;
        }

        TextView textView = grid.findViewById(R.id.grid_text);
        ImageView imageView = grid.findViewById(R.id.grid_image);
        Log.i("position",String.valueOf(position));
        if(position < folderBOS.size()){
            imageView.setImageResource(R.drawable.ic_folder_open_black_24dp);
            textView.setText(getFolderTitleFromDesc(this.folderBOS.get(position).getFolderDesc()));
        } else {
            imageView.setImageResource(android.R.drawable.ic_menu_add);
            textView.setText(
                    this.taskBOS.get(position - this.folderBOS.size()).getTaskDesc());
        }

        return grid;

    }

    private String getFolderTitleFromDesc(String desc){
        Log.i("desc",desc);
        List<String> tokens = Arrays.asList(desc.split("/"));
        return tokens.get(tokens.size()-1);
    }

    public void updateSubFolders(List<FolderBO> folderBOS) {
        this.folderBOS = folderBOS;
        Log.i("Folders in adapter",this.folderBOS.toString());
        //Triggers the list update
        notifyDataSetChanged();
    }

    public void updateSubTasks(List<TaskBO> taskBOS){
        this.taskBOS = taskBOS;
        notifyDataSetChanged();
    }

    public void reloadData(){
        //notifyDataSetChanged();
    }

    public FolderBO getFolderBOAtPos(int position){
        return this.folderBOS.get(position);
    }

    public TaskBO getTaskBOAtPos(int position){
        return this.taskBOS.get(position);
    }

    public int getNumFolders(){
        return this.folderBOS.size();
    }
}