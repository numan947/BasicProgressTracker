package numan947.com.hourcounter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * @author numan947
 * @since 11/25/18.<br>
 **/
public class TaskAdapter extends ArrayAdapter<TaskModel> {

    private ArrayList<TaskModel>taskModels;

    private MainActivity context;


    public TaskAdapter(Context context, ArrayList<TaskModel>taskList) {
        super(context, 0, taskList);
        this.taskModels = taskList;
        this.context = (MainActivity) context;

    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater lift = LayoutInflater.from(context);
            convertView = lift.inflate(R.layout.task_item,null);
        }
        TaskModel currentModel = taskModels.get(position);

        TextView taskTitle = convertView.findViewById(R.id.taskTitle);
        TextView taskTotal = convertView.findViewById(R.id.taskTotal);
        TextView taskRemain = convertView.findViewById(R.id.taskRemaining);

        taskTitle.setText(currentModel.getTaskTitle());
        taskTotal.setText(currentModel.getTaskTotalTime()+"");
        taskRemain.setText(currentModel.getTaskRemainingTime()+"");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View customAlertView = LayoutInflater.from(context).inflate(R.layout.list_item_click_dialog,null);
                builder.setView(customAlertView);

                final EditText totalTimeSpentText = customAlertView.findViewById(R.id.list_item_click_total_time_spent);

                final TaskModel currentModel = taskModels.get(position);

                builder.setTitle(currentModel.getTaskTitle());



                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println(totalTimeSpentText.getText().toString());
                        if(!totalTimeSpentText.getText().toString().matches("[0-9]+")){
                            Toast.makeText(context,"Invalid Input!",Toast.LENGTH_LONG).show();
                            return;
                        }
                        currentModel.setTaskRemainingTime(currentModel.getTaskRemainingTime()-Integer.parseInt(totalTimeSpentText.getText().toString()));
                        notifyDataSetChanged();
                        context.updatePreferenceList();
                        Toast.makeText(context,"Saved Edit!", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        return  convertView;
    }
    @Override
    public boolean isEnabled(int position)
    {
        return true;
    }
}
