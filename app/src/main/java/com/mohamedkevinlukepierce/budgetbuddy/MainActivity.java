package com.mohamedkevinlukepierce.budgetbuddy;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ListFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mohamedkevinlukepierce.budgetbuddy.BudgetContent;

import java.util.List;

import static com.mohamedkevinlukepierce.budgetbuddy.BudgetContent.HOLD;
import static com.mohamedkevinlukepierce.budgetbuddy.BudgetContent.ITEMS;
import static com.mohamedkevinlukepierce.budgetbuddy.BudgetContent.createBudgetItem;

public class MainActivity
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OverviewFragment.OnListFragmentInteractionListener
        {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private OverviewFragment overviewFragment;
    private ReportsFragment reportsFragment;
    private Intent intent;
    private static SharedPreferences generalSharedPreferences;
    private static SharedPreferences.Editor generalEditor;
    public static Context contextOfApplication;

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

    @Override
    public void onRestart() { //When back button is pressed on Android device the layout is refreshed
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        generalSharedPreferences = getSharedPreferences("General Preference", MODE_PRIVATE);
        generalEditor = generalSharedPreferences.edit();
        contextOfApplication = getApplicationContext();

        if(generalSharedPreferences.getBoolean("darkThemeEnabled", false))
            setTheme(R.style.AppTheme_Dark_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.

        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // listeners for the two floating action sub buttons
        FloatingActionButton fabExpense = (FloatingActionButton) findViewById(R.id.fabExpense);
        FloatingActionButton fabSaving = (FloatingActionButton) findViewById(R.id.fabSaving);
        fabExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addListItem(view, "expense");
            }
        });
        fabSaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addListItem(view, "savings");
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().equals("Reports")) {
                    reportsFragment.refreshPie();
                    reportsFragment.animation();
                }
                if (tab.getText().equals("Overview")) {
                    reportsFragment.hidePie();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    // method to add an item to the overview list
    public void addListItem(View view, final String type) {
        FloatingActionMenu fab = (FloatingActionMenu) findViewById(R.id.fab);
        fab.close(true);
        LinearLayout layout = new LinearLayout(MainActivity.this);
        TextInputLayout name = new TextInputLayout(MainActivity.this);
        TextInputLayout value = new TextInputLayout(MainActivity.this);
        final EditText editTextValue = new EditText(MainActivity.this);
        final EditText editTextName = new EditText(MainActivity.this);
        editTextName.setInputType(InputType.TYPE_TEXT_FLAG_AUTO_CORRECT);
        // disable non integer inputs and uses numeric keyboard
        editTextValue.setInputType(InputType.TYPE_CLASS_NUMBER);
        editTextName.setMaxLines(1);
        editTextValue.setMaxLines(1);
        editTextName.setHint("Enter name of item");
        editTextValue.setHint("Enter amount");
        // set the enter button to be done
        editTextValue.setImeOptions(EditorInfo.IME_ACTION_DONE);
        // sets the min length of the field
        editTextName.setMinEms(10);
        editTextValue.setMinEms(10);
        // adds edit text objects to a container for floating label
        name.addView(editTextName);
        value.addView(editTextValue);
        layout.addView(name);
        layout.addView(value);
        layout.setPadding(10, 50, 10, 10);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder .setView(layout)
                .setCancelable(true)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String stringName = editTextName.getText().toString();
                        String stringValue = editTextValue.getText().toString();
                        if (stringName.isEmpty()) {
                            // alert the user with a toast message if the name field is empty
                            Toast.makeText(MainActivity.this, "Please enter a name.", Toast.LENGTH_SHORT).show();

                        }
                        else if (stringValue.isEmpty()) {
                            // alert the user with a toast message if the number field is empty
                            Toast.makeText(MainActivity.this, "Please enter a number.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            BudgetContent.addItem(createBudgetItem(stringName, stringValue, type));
                            overviewFragment.refreshList();
                            reportsFragment.refreshPie();
                            reportsFragment.animation();
                            Toast.makeText(MainActivity.this, stringName + " was added to your list.", Toast.LENGTH_SHORT).show();

                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing when canceled
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        overviewFragment = new OverviewFragment();
        reportsFragment = new ReportsFragment();
        adapter.addFragment(overviewFragment, "Overview");
        adapter.addFragment(reportsFragment, "Reports");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        TextView navName = (TextView) findViewById(R.id.profileNameNav);
        navName.setText(generalSharedPreferences.getString(String.format("name%d", ProfileActivity.state),String.format("Profile %d", ProfileActivity.state)));
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem Item) {
        // Handle navigation view item clicks here.
        int id = Item.getItemId();

        if (id == R.id.nav_home) {

            while(!HOLD.isEmpty()){
                ITEMS.add(HOLD.remove(0));
            }
            OverviewFragment.refreshList();

        } else if (id == R.id.nav_expenses) {


            while(!HOLD.isEmpty()){
                ITEMS.add(HOLD.remove(0));
            }

            int i = 0;
            while(i < ITEMS.size()){
                if(ITEMS.get(i).getType().equalsIgnoreCase("savings")){
                    HOLD.add(ITEMS.remove(i));
                }
                else{
                    i++;
                }

            }
            OverviewFragment.refreshList();

        } else if (id == R.id.nav_savings) {

            while(!HOLD.isEmpty()){
                ITEMS.add(HOLD.remove(0));
            }

            int i = 0;
            while(i < ITEMS.size()){
                if(ITEMS.get(i).getType().equalsIgnoreCase("expense")){
                    HOLD.add(ITEMS.remove(i));
                }
                else{
                    i++;
                }

            }
            OverviewFragment.refreshList();

        } else if (id == R.id.nav_settings) {
            intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            intent = new Intent(getApplicationContext(), ProfileActivity.class);
            Toast.makeText(getApplicationContext(), "Profile logged out.", Toast.LENGTH_LONG).show();
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // starts the list fragment for the overview tab
    @Override
    public void onListFragmentInteraction(BudgetContent.BudgetItem item) {

    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }

    }
}
