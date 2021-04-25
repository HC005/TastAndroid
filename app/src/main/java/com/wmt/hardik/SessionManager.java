package com.wmt.hardik;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences pref, poster_pref, company_pref,company_pref_f, social_account_page_pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor, editor2;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREF_NAME = "Pref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";


    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    /**
     * Clear session details
     */
    public boolean logoutUser() {
        // Clearing all data from Shared Preferences
        //  editor2.clear();
        editor.clear();
        //  editor2.commit();
        boolean res = editor.commit();
        return res;
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }


    public boolean createRegSession() {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // commit changes
        boolean res = editor.commit();
        return res;
    }

}
