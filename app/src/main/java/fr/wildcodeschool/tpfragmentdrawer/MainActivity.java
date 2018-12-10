package fr.wildcodeschool.tpfragmentdrawer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;

import fr.wildcodeschool.tpfragmentdrawer.fragments.AboutFragment;
import fr.wildcodeschool.tpfragmentdrawer.fragments.CategoryFragment;
import fr.wildcodeschool.tpfragmentdrawer.fragments.HomeFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static class PageNavigation {
        private int title;
        private Fragment fragment;

        PageNavigation(int title, Fragment fragment) {
            this.title = title;
            this.fragment = fragment;
        }

        public int getTitle() {
            return title;
        }

        public Fragment getFragment() {
            return fragment;
        }
    }

    private static final SparseArray<PageNavigation> mapNavigation = new SparseArray<>();

    static {
        mapNavigation.put(R.id.nav_home, new PageNavigation(R.string.home_title, new HomeFragment()));
        mapNavigation.put(R.id.nav_about, new PageNavigation(R.string.about_title, new AboutFragment()));
        mapNavigation.put(R.id.nav_category, new PageNavigation(R.string.category_title, new CategoryFragment()));
        mapNavigation.put(R.id.nav_item, new PageNavigation(R.string.item_title, new HomeFragment()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationToPage(R.id.nav_home);
    }

    public void navigationToPage(int id) {
        PageNavigation pageNavigation = mapNavigation.get(id);
        if (pageNavigation != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_home, pageNavigation.getFragment());
            transaction.commit();
            setTitle(pageNavigation.getTitle());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        navigationToPage(id);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
