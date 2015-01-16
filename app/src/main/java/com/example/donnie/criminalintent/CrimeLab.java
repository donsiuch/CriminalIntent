package com.example.donnie.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by donnie on 1/2/15.
 *
 * Singleton class. Holds a list of crimes (acts as database/repo for all crimes).
 */
public class CrimeLab {
    private ArrayList<Crime> mCrimes;

    private static CrimeLab sCrimeLab;
    private Context mAppContext;

    private CrimeLab(Context appContext){
        mAppContext = appContext;
        mCrimes = new ArrayList<Crime>();
        generateTestData();
    }

    public static CrimeLab get(Context c){
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for (Crime c : mCrimes){
            if (c.getId().equals(id))
                return c;
        }
        return null;
    }

    private void generateTestData(){
        for (int i = 0 ; i < 100 ; i++){
            Crime c = new Crime();
            c.setTitle("Crime #" + i);
            c.setSolved(i%2 == 0);
            mCrimes.add(c);
        }
    }
}
