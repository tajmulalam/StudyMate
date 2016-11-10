package com.sumon.studymate.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sumon.studymate.util.CustomToast;
import com.sumon.studymate.util.MyAlert;
import com.sumon.studymate.util.MySharedPrefManager;
import com.sumon.studymate.R;
import com.sumon.studymate.manager.RoutineManager;
import com.sumon.studymate.model.RoutineModel;
import com.sumon.studymate.manager.SemesterManager;
import com.sumon.studymate.adapter.AdapterFoRoutineList;

import java.util.ArrayList;

public class RoutineInfoListActivity extends AppCompatActivity implements MyAlert.WhichBtnClicked {

    private int semesterID = -1;

    private AdapterFoRoutineList adapterFoRoutineList;
    private ArrayList<RoutineModel> allRoutine;
    private RoutineManager routineManager;
    private ListView routineInfoListView;
    private int routineID = -1;
    private FloatingActionButton fabNewRoutine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_info_list);
        semesterID = getIntent().getIntExtra("semesterID", 0);
        setTitle(new SemesterManager(this).getSemesterByID(semesterID).getSemesterTitle() + " Routine");
        if (semesterID == -1 || semesterID == 0) {
            semesterID = new MySharedPrefManager(this).getIntFromPreference("semesterID");
            setTitle(new SemesterManager(this).getSemesterByID(semesterID).getSemesterTitle() + "Routine");
        }
        routineInfoListView = (ListView) findViewById(R.id.routineInfoListView);

        routineManager = new RoutineManager(this);
        allRoutine = new ArrayList<>();
        allRoutine = routineManager.getAllRoutineBySemesterID(semesterID);
        adapterFoRoutineList = new AdapterFoRoutineList(this, allRoutine);
        routineInfoListView.setAdapter(adapterFoRoutineList);
         fabNewRoutine= (FloatingActionButton) findViewById(R.id.fabNewRoutine);

        fabNewRoutine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddRoutine();
            }
        });
        routineInfoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                routineID = allRoutine.get(position).getRoutineID();
                apearDialog();
            }
        });



    }

    private void apearDialog() {

        MyAlert myAlert = new MyAlert();
        myAlert.setWhichBtnClicked(this);
        myAlert.setTitle("Confirm Dialog");
        myAlert.setMsg("This will deleted with its related data. Are You sure? To delete this");
        myAlert.show(getFragmentManager(), "SemesterListDialog");
    }

    private void goToAddRoutine() {
        Intent addRoutineInten = new Intent(RoutineInfoListActivity.this, AddRoutineActivity.class);
        addRoutineInten.putExtra("semesterID", semesterID);
        new MySharedPrefManager(this).insertIntoPreferenceInt("semesterID", semesterID);
        startActivity(addRoutineInten);
        finish();

    }


    @Override
    public void okBtnClicked(boolean isOk) {
        if (isOk) {
            boolean isDeleted = new RoutineManager(this).deleteRoutineByID(routineID);
            if (isDeleted) {
                CustomToast.SuccessToast(this, " Delete Successful");
                allRoutine.clear();
                allRoutine.addAll(new RoutineManager(this).getAllRoutineBySemesterID(semesterID));
                adapterFoRoutineList.notifyDataSetChanged();

            } else
                CustomToast.FailToast(this, "Delete failed");
        }
    }

    @Override
    public void cancelkBtnClicked(boolean isCancel) {

    }
}
