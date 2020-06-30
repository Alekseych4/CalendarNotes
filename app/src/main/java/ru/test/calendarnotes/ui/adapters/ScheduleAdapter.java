package ru.test.calendarnotes.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.test.calendarnotes.R;
import ru.test.calendarnotes.data.TaskModel;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> {

    protected List<TaskModel> tasksList;
    protected Context context;

    public ScheduleAdapter(Context context, List<TaskModel> tasksList){
        this.tasksList = tasksList;
        this.context = context;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.schedule_list_item, parent, false);

        return new ScheduleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        holder.bindDataWithView(tasksList.get(position));
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    class ScheduleViewHolder extends RecyclerView.ViewHolder{

        private TextView timeScope, briefTaskName;
        private RecyclerView tasksRecycler;
        //private Context context;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            //this.context = context;
            timeScope = itemView.findViewById(R.id.time_scope);
            briefTaskName = itemView.findViewById(R.id.brief_task_name);
            tasksRecycler = itemView.findViewById(R.id.inner_list);
        }

        void bindDataWithView(TaskModel task){
            timeScope.setText("14:00-15:00");
            briefTaskName.setText(task.getName());

            TasksListAdapter tasksListAdapter = new TasksListAdapter(context, tasksList);
            tasksRecycler.setLayoutManager(new LinearLayoutManager(context));
            tasksRecycler.setAdapter(tasksListAdapter);
        }
    }
}
