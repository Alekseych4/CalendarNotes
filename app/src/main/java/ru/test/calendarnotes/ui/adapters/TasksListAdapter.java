package ru.test.calendarnotes.ui.adapters;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

import ru.test.calendarnotes.R;
import ru.test.calendarnotes.data.TaskModel;

public class TasksListAdapter extends RecyclerView.Adapter<TasksListAdapter.TasksViewHolder> {

    private Context context;
    private List<TaskModel> taskModelList;

    public TasksListAdapter(Context context, List<TaskModel> taskModelList){
        this.context = context;
        this.taskModelList = taskModelList;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.tasks_list_item, parent, false);
        return new TasksViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksViewHolder holder, int position) {
        holder.bindTaskWithViews(taskModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return taskModelList.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder{

        private TextView taskTime, taskName;

        TasksViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTime = itemView.findViewById(R.id.task_time);
            taskName = itemView.findViewById(R.id.task_name);
        }

        void bindTaskWithViews(TaskModel task){
            taskName.setText(task.getName());

            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(task.getDateStart());
            Calendar f = Calendar.getInstance();
            f.setTimeInMillis(task.getDateFinish());

            taskTime.setText(DateUtils.formatDateTime(context,
                    c.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_TIME)
                    + " - " +
                    DateUtils.formatDateTime(context,
                            f.getTimeInMillis(),
                            DateUtils.FORMAT_SHOW_TIME));
        }
    }
}
