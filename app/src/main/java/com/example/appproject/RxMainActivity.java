package com.example.appproject;

import android.os.Bundle;
import android.widget.Toast;

import com.example.appproject.view.PictureFragment;
import com.example.appproject.view.dummy.DummyContent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class RxMainActivity extends AppCompatActivity
            implements PictureFragment.OnListFragmentInteractionListener {

    private static final int COLUMN_COUNT_PIC = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_main);

        Fragment picFragment = PictureFragment.newInstance(COLUMN_COUNT_PIC);
        attachFragment(picFragment);
    }

    private void attachFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Toast.makeText(this, "item clicked", Toast.LENGTH_SHORT).show();
    }
}
