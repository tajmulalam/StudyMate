package com.sumon.studymate;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class ClassTestListActivity extends AppCompatActivity {

    private ListView classTestListView;
    private ClassTestManager classTestManager;
    private ArrayList<ClassTestModel> classTestModelArrayList;
    private AdapterForClassTestList adapterForClassTestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_test_list);
        setTitle("Class Test List");

        classTestListView = (ListView) findViewById(R.id.classTestListView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabNewClassTest);

        classTestManager = new ClassTestManager(this);
        fillAdapter();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddAssignment();
            }
        });


    }

    private void goToAddAssignment() {
        startActivity(new Intent(ClassTestListActivity.this, AddClassTestActivity.class));

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void fillAdapter() {
        classTestModelArrayList=new ArrayList<>();
        classTestModelArrayList = classTestManager.getAllClassTest();

        adapterForClassTestList = new AdapterForClassTestList(this, classTestModelArrayList);
        adapterForClassTestList.setFragmentManager(getFragmentManager());
        classTestListView.setAdapter(adapterForClassTestList);
        adapterForClassTestList.notifyDataSetChanged();

    }
}
