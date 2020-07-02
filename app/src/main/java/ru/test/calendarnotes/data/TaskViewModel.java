package ru.test.calendarnotes.data;

import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.annotation.Nullable;

import io.realm.Realm;
import io.realm.RealmResults;

public class TaskViewModel extends ViewModel {

    private Realm realm = Realm.getDefaultInstance();
    private RealmResults<TaskModel> tasksForDay;

    public RealmResults<TaskModel> getTasksForDay(long dayStart, long dayFinish) {
        tasksForDay = realm.where(TaskModel.class)
                .greaterThanOrEqualTo("dateStart", dayStart)
                .and()
                .lessThan("dateFinish", dayFinish)
                .findAll();

        return tasksForDay;
    }

    public RealmResults<TaskModel> getTasksForHour(long hourStart, long hourFinish){

        RealmResults<TaskModel> tasksForHour = tasksForDay.where()
                .greaterThanOrEqualTo("dateStart", hourStart)
                .and()
                .lessThan("dateFinish", hourFinish)
                .findAll();

        return tasksForHour;

    }

    public void setNewTaskData(String taskName, long timeStart, long timeFinish, String taskDescription) {
        realm.beginTransaction();

        TaskModel tm = realm.createObject(TaskModel.class, setPrimaryKey());
        tm.setName(taskName);
        tm.setDateStart(timeStart);
        tm.setDateFinish(timeFinish);
        tm.setDescription(taskDescription);

        realm.commitTransaction();
    }

    private long setPrimaryKey(){
        Number maxId = realm.where(TaskModel.class).max("id");
        long newId = maxId != null ? maxId.longValue() + 1 : 0;
        return newId;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        realm.close();
    }
}
