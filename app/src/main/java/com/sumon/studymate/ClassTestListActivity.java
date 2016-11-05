package com.sumon.studymate;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;

public class ClassTestListActivity extends AppCompatActivity {

    private ListView classTestListView;
    private ClassTestManager classTestManager;
    private ArrayList<ClassTestModel> classTestModelArrayList;
    private AdapterForClassTestList adapterForClassTestList;
    private EditText inputSearchClassTestET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_test_list);
        setTitle("Class Test List");

        classTestListView = (ListView) findViewById(R.id.classTestListView);
        inputSearchClassTestET= (EditText) findViewById(R.id.inputSearchClassTestET);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabNewClassTest);

        classTestManager = new ClassTestManager(this);
        fillAdapter();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddAssignment();
            }
        });
        inputSearchClassTestET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = inputSearchClassTestET.getText().toString().toLowerCase(Locale.getDefault());
                adapterForClassTestList.filter(text);
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
