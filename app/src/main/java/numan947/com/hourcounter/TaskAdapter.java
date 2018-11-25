package numan947.com.hourcounter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author numan947
 * @since 11/25/18.<br>
 **/
public class TaskAdapter extends ArrayAdapter<TaskModel> {

    private ArrayList<TaskModel>taskModels;

    private Context context;


    public TaskAdapter(Context context, ArrayList<TaskModel>taskList) {
        super(context, 0, taskList);
        this.taskModels = taskList;
        this.context = context;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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

        return  convertView;
    }
}
