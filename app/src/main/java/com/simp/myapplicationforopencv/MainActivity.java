package com.simp.myapplicationforopencv;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.simp.myapplicationforopencv.list.CommandConstants;
import com.simp.myapplicationforopencv.list.CommandData;
import com.simp.myapplicationforopencv.list.MyListViewAdapter;



public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private MyListViewAdapter myListViewAdapter;
    private String Command;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button btn = (Button)findViewById(R.id.envTestbutton);
//        btn.setOnClickListener(this);
        LoadViewData();
    }

    private void LoadViewData() {
        myListViewAdapter = new MyListViewAdapter(this.getApplicationContext());
        ListView listView = (ListView)findViewById(R.id.command_ListView);
        listView.setAdapter(myListViewAdapter);
        listView.setOnItemClickListener(this);
        myListViewAdapter.getModel().add(new CommandData(CommandConstants.TEST_ENV_COMMAND,1));
        myListViewAdapter.getModel().add(new CommandData(CommandConstants.MAT_PIXEL_INVERT_COMMAND,2));
        myListViewAdapter.getModel().add(new CommandData(CommandConstants.BITMAP_PIXEL_INVERT_COMMAND,3));
        myListViewAdapter.getModel().add(new CommandData(CommandConstants.PIXEL_SUBSTRACT_COMMAND,4));
        myListViewAdapter.getModel().add(new CommandData(CommandConstants.PIXEL_ADD_COMMAND,5));
        myListViewAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object obj=view.getTag();
        if(obj instanceof CommandData){
            CommandData cmddata = (CommandData)obj;
            Command = cmddata.getCommand();
        }
        ProcessCommand();
    }

    private void ProcessCommand() {
        if(CommandConstants.TEST_ENV_COMMAND.equals(Command)
                ||CommandConstants.MAT_PIXEL_INVERT_COMMAND.equals(Command)
                ||CommandConstants.BITMAP_PIXEL_INVERT_COMMAND.equals(Command)
                ||CommandConstants.PIXEL_SUBSTRACT_COMMAND.equals(Command)
                ||CommandConstants.PIXEL_ADD_COMMAND.equals(Command)

                ){
            Intent intent = new Intent(this.getApplicationContext(),processImageActivity.class);
            intent.putExtra("Command",Command);
            startActivity(intent);
        }
    }

//
}
