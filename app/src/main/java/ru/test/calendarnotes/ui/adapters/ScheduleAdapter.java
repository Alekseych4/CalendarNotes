package ru.test.calendarnotes.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.test.calendarnotes.R;
import ru.test.calendarnotes.data.TaskModel;
import ru.test.calendarnotes.data.TaskViewModel;
import ru.test.calendarnotes.data.TimeIntervalsModel;
import ru.test.calendarnotes.ui.activities.DescriptionActivity;
import ru.test.calendarnotes.ui.activities.MainActivity;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder> implements MainActivity.OnDateChangedListener {

    protected List<TimeIntervalsModel> timeIntervalsModels;
    protected Context context;
    protected List<TaskModel> tasksModels;
    protected TaskViewModel taskViewModel;
    protected TasksListAdapter tasksListAdapter;

    public ScheduleAdapter(Context context, List<TimeIntervalsModel> timeIntervalsModels,
                           List<TaskModel> tasksModels, TaskViewModel taskViewModel){
        this.timeIntervalsModels = timeIntervalsModels;
        this.context = context;
        this.tasksModels = tasksModels;
        this.taskViewModel = taskViewModel;
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
        holder.bindDataWithView(timeIntervalsModels.get(position));
    }

    @Override
    public int getItemCount() {
        return timeIntervalsModels.size();
    }

    @Override
    public void onDateClick() {
        if (tasksListAdapter != null){

            tasksListAdapter.notifyDataSetChanged();
        }
    }

     class ScheduleViewHolder extends RecyclerView.ViewHolder {

        private TextView timeScope;
        private RecyclerView tasksRecycler;

        //private Context context;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            //this.context = context;
            timeScope = itemView.findViewById(R.id.time_scope);
            tasksRecycler = itemView.findViewById(R.id.inner_list);
        }

        void bindDataWithView(TimeIntervalsModel timeIntervals){
            Calendar c = Calendar.getInstance();
            Calendar f = Calendar.getInstance();
            c.setTimeInMillis(timeIntervals.getTimeStart());
            f.setTimeInMillis(timeIntervals.getTimeFinish());
            //Log.d("ScheduleAdapter", f.toString());

            timeScope.setText(DateUtils.formatDateTime(context,
                    c.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_TIME)
                    + " - " +
                    DateUtils.formatDateTime(context,
                    f.getTimeInMillis(),
                    DateUtils.FORMAT_SHOW_TIME));

            innerListBinding(timeIntervals.getTimeStart(), timeIntervals.getTimeFinish());

        }

        public void innerListBinding(long start, long finish){
            List<TaskModel> tasksForHour = taskViewModel.getTasksForHour(start, finish);

            if (tasksForHour == null){
                tasksForHour = new ArrayList<>();
            }

//            Log.d("innerListBinding",Integer.toString(tasksForHour.size()));
            tasksListAdapter = new TasksListAdapter(context, tasksForHour);
            tasksRecycler.setLayoutManager(new LinearLayoutManager(context));
            tasksRecycler.setAdapter(tasksListAdapter);
        }

     }


}
