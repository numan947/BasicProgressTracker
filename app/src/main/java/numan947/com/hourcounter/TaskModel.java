package numan947.com.hourcounter;

/**
 * @author numan947
 * @since 11/25/18.<br>
 **/
public class TaskModel {
    private String taskTitle;
    private int taskTotalTime;
    private int taskRemainingTime;

    public TaskModel(String taskTitle, int taskTotalTime, int taskRemainingTime) {
        this.taskTitle = taskTitle;
        this.taskTotalTime = taskTotalTime;
        this.taskRemainingTime = taskRemainingTime;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public void setTaskTotalTime(int taskTotalTime) {
        this.taskTotalTime = taskTotalTime;
    }

    public void setTaskRemainingTime(int taskRemainingTime) {
        this.taskRemainingTime = taskRemainingTime;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public int getTaskTotalTime() {
        return taskTotalTime;
    }

    public int getTaskRemainingTime() {
        return taskRemainingTime;
    }


}
