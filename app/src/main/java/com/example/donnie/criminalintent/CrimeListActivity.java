package com.example.donnie.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by donnie on 1/2/15.
 *
 * Creates the fragment.
 */
public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new CrimeListFragment();
    }
}
