package com.example.appproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.appproject.model.Picture;
import com.example.appproject.view.PictureFragment;
import com.example.appproject.view.QuickPictureActivity;
import com.example.appproject.view.dummy.DummyContent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class RxMainActivity extends AppCompatActivity
            implements PictureFragment.OnListFragmentInteractionListener {

    private static final int COLUMN_COUNT_PIC = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_main);

        Fragment picFragment = PictureFragment.newInstance(COLUMN_COUNT_PIC);
        attachFragment(picFragment);
        setTitle("美图");
    }

    private void attachFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void onListFragmentInteraction(Picture item, int position) {
        Toast.makeText(this, "item clicked: " + item.getImgurl(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RxMainActivity.this, QuickPictureActivity.class);
        intent.setAction("com.example.app.SHOW");
        intent.putExtra(QuickPictureActivity.PICTURE_ID, item.getId());
        startActivity(intent);
    }
}
