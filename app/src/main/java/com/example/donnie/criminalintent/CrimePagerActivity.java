package com.example.donnie.criminalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by donnie on 1/11/15.
 *
 * This will now host CrimeFragments.
 *
 */

/**
 * Controller
 * By making this class we are making the CrimeFragment more encapsulated.
 * If, for instance, in the onCreate of CrimeFragment, we accessed the Intent and got
 * the crime id from the intent, then we are assuming that every time CrimeFragment is utilized
 * in an activity that activity MUST define an extra name EXTRA_CRIME_ID
 *
 * The idea is to stash the crime_id elsewhere that isn't related to the hosting fragment. This
 * class is tied to the intent... think of it as an extension of the foundation. It gets the crimeId
 * and passes to newInstance in CrimeFragment (what we are trying to encapsulate). CrimeFragment
 * then stashes this id in it's arguments then returns an instance of itself.
 *
 * When the instance of itself is inflated, it looks in its argument bundle and gets the id so it
 * can tailor itself correctly.
 *
 * This allows crime fragment to get the initial id from whatever calls the instance then
 * as it returns itself around it prevents it from depending on the hosting activity to hold its
 * information. Eventually when inflated it looks in its arguments and everyting that needed
 * to be saved as it was being passed around was and it reinflates.
 *
 * activity_fragment.xml (foundation for all fragments)
 *   CrimeeActivity (loads a new CrimeFragment based on selected id in the list)
 *     CrimeFragment (what is returned from newInstance)
 */
public class CrimePagerActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private ArrayList<Crime> mCrimes;

    public CrimePagerActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        mCrimes = CrimeLab.get(this).getCrimes();

        FragmentManager fm = getSupportFragmentManager();

        // Define the agent that will facilitate communications between viewPager and Crimes
        // This is not displaying the first view on the page.
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            // These two methods must be overridden.
            @Override
            public Fragment getItem(int i) {
                Crime crime = mCrimes.get(i);
                return CrimeFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });

        // Set the title in the display bar to the title of the current crime
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {}

            @Override
            public void onPageSelected(int i) {
                Crime crime = mCrimes.get(i);
                if (crime.getTitle() != null){
                    setTitle(crime.getTitle());
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {}
        });

        // Get the ID of the selected crime fragment
        UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        for (int i = 0; i < mCrimes.size(); i++){
            if (mCrimes.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
