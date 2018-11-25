package numan947.com.hourcounter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private final String TASK_SAVE_STRING = "numan947.com.hourcounter.KEY_FOR_SAVED_TASK_LIST_IN_SHARED_PREFS";
    private final int NUMBER_PICKER_MIN_VAL = 2;
    private final int NUMBER_PICKER_MAX_VAL = 200;


    private ArrayList<TaskModel>tasks;
    private TaskAdapter taskAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //get taskList From Preferences here

        getArrayListFromPreference();


        final ListView taskList = (ListView)findViewById(R.id.taskList);
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);





        taskAdapter = new TaskAdapter(this,tasks);
        taskList.setAdapter(taskAdapter);
        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View customAlertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.list_item_long_click_dialog,null);
                builder.setView(customAlertView);

                final EditText longClickEditTitle = customAlertView.findViewById(R.id.long_click_title_text);
                final EditText longClickEditTotalHour = customAlertView.findViewById(R.id.long_click_total_hour_text);
                final EditText longClickEditRemainingHour = customAlertView.findViewById(R.id.long_click_remaining_hour_text);

                final TaskModel currentModel = tasks.get(position);

                longClickEditRemainingHour.setText(String.valueOf(currentModel.getTaskRemainingTime()));
                longClickEditTotalHour.setText(String.valueOf(currentModel.getTaskTotalTime()));
                longClickEditTitle.setText(String.valueOf(currentModel.getTaskTitle()));

                builder.setTitle("Edit: "+currentModel.getTaskTitle());



                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newTitle = longClickEditTitle.getText().toString();
                        String newRemHo = longClickEditRemainingHour.getText().toString();
                        String newTotal = longClickEditTotalHour.getText().toString();

//                        System.out.println(newTitle+" "+newRemHo+" "+newTotal);
//                        System.out.println(newRemHo.matches("[0-9]+"));

                        if(!(newRemHo.matches("[0-9]+")) || !(newTotal.matches("[0-9]+"))){
                            Snackbar.make(fab,"Invalid Numbers!!", Snackbar.LENGTH_LONG).show();
                            return;
                        }

                        currentModel.setTaskTitle(newTitle);
                        currentModel.setTaskRemainingTime(Integer.parseInt(newRemHo));
                        currentModel.setTaskTotalTime(Integer.parseInt(newTotal));

                        MainActivity.this.taskAdapter.notifyDataSetChanged();
                        MainActivity.this.updatePreferenceList();
                        Snackbar.make(fab,"Saved Edit!", Snackbar.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

                return true;
            }
        });



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View customAlertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.input_dialog,null);
                builder.setView(customAlertView);
                builder.setTitle("Create New Task");
                final NumberPicker np = customAlertView.findViewById(R.id.numberPicker);
                np.setMinValue(NUMBER_PICKER_MIN_VAL);
                np.setMaxValue(NUMBER_PICKER_MAX_VAL);
                final EditText et = customAlertView.findViewById(R.id.taskTitleInput);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String taskTitle = et.getText().toString();
                        TaskModel newTask = new TaskModel(taskTitle,np.getValue(),np.getValue());
                        MainActivity.this.tasks.add(newTask);
                        MainActivity.this.taskAdapter.notifyDataSetChanged();
                        MainActivity.this.updatePreferenceList();
                        Snackbar.make(fab, "Saved New Task", Snackbar.LENGTH_LONG).show();
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
    }


    private void getArrayListFromPreference(){
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String jsonTasks =  appSharedPrefs.getString(this.TASK_SAVE_STRING,"");

        if(jsonTasks=="") {
            this.tasks = new ArrayList<>();
            return;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<TaskModel>>(){}.getType();
        this.tasks = gson.fromJson(jsonTasks,type);
    }

    private void updatePreferenceList() {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();


        Gson gson = new Gson();
        String jsonTasks = gson.toJson(this.tasks);
        prefsEditor.putString(this.TASK_SAVE_STRING,jsonTasks);
        prefsEditor.apply();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
