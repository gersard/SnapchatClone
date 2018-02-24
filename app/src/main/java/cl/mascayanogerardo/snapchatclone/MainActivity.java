package cl.mascayanogerardo.snapchatclone;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cl.mascayanogerardo.snapchatclone.fragment.CameraFragment;
import cl.mascayanogerardo.snapchatclone.fragment.ChatFragment;
import cl.mascayanogerardo.snapchatclone.fragment.StoryFragment;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setCurrentItem(1, true);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter{

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return ChatFragment.newInstance();
                case 1:
                    return CameraFragment.newInstance();
                case 2:
                    return StoryFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

}
