package iss.nus.edu.medipalappln.activity;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import iss.nus.edu.medipalappln.R;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder>  {

    private final List<dashboardcontent> dashboardcontentList;
    private Context mContext;
   // private List<dashboardcontent> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflowing);
        }
    }


    public DashboardAdapter(Context mContext, List<dashboardcontent> dashboardcontentList) {
        this.mContext = mContext;
        this.dashboardcontentList = dashboardcontentList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        dashboardcontent dashboardcontent = dashboardcontentList.get(position);
        holder.title.setText(dashboardcontent.getName());
       // holder.count.setText(dashboardcontent.getNumOfSongs() + " songs");

        // loading dashboardcontent cover using Glide library
        Glide.with(mContext).load(dashboardcontent.getThumbnail()).into(holder.thumbnail);

       /* holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dashboard d=new Dashboard();

                d.callICE(view);*/
               /* Fragment fragment=null;
                Class fragmentclass= IceDetails.class;
                try {
                    fragment = (Fragment) fragmentclass.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                Fragment fragment1=new Fragment();

                FragmentManager fragmentManager=.getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();*/

                //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                //drawer.closeDrawer(GravityCompat.START);

/*
            }
        })*/;
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return dashboardcontentList.size();
    }
}


/*

package iss.nus.edu.medipalappln.activity;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import iss.nus.edu.medipalappln.R;


public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.MyViewHolder> implements PopupMenu.OnMenuItemClickListener{

    private Context mContext;
    private List<dashboardcontent> dashboardcontentList;

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public DashboardAdapter(Context mContext, List<dashboardcontent> dashboardcontentList) {
        this.mContext = mContext;
        this.dashboardcontentList = dashboardcontentList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        dashboardcontent dashboardcontent = dashboardcontentList.get(position);
        holder.title.setText(dashboardcontent.getName());
        Glide.with(mContext).load(dashboardcontent.getThumbnail()).into(holder.thumbnail);
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(mContext,view);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_album);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_add_favourite:
                                //handle menu1 click
                                break;
                            case R.id.action_play_next:
                                //handle menu2 click
                                break;
                            // case R.id.menu3:
                            //handle menu3 click
                            //   break;

 holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
                showPopupMenu(holder.overflow);
            }
        });

                            return false;
                        }
                        }
                    });
                    //displaying the popup
                popup.show();


            });

                                        });
    }
*
     * Showing popup menu when tapping on 3 dots


    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        try{
        popup.show();}
        catch (Exception e){
            e.printStackTrace();
        }
    }

*
     * Click listener for popup menu items


    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.addPersonalBio:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.addICE:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }


    @Override
    public int getItemCount() {
        return dashboardcontentList.size();
    }
}
*/
