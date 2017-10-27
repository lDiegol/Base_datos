package com.example.diego.base_datos;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.diego.base_datos.modelos.Contacto;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    protected ArrayList<Contacto> summaryList  = new ArrayList <Contacto>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter reportListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    protected MainActivity.ContactListAdapter  contactListAdapter;
    protected LayoutManagerType mCurrentLayoutManagerType;

    static final String TAG = "RecyclerView";
    static final String KEY_LAYOUT_MANAGER = "LayoutManager";

    private enum LayoutManagerType{
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView  = (RecyclerView) findViewById(R.id.mPropertyRecyclerView);
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider, R.drawable.divider_light));
        mLayoutManager = new LinearLayoutManager(this);
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
        Contacto contact = new Contacto();
        contact.setNombre("Diego");
        contact.setApellido("Magallon");
        summaryList.add(contact);

        contactListAdapter = new MainActivity.ContactListAdapter(summaryList, this);
        mRecyclerView.setAdapter(contactListAdapter);
    }







    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        mLayoutManager = new LinearLayoutManager(this);
        mCurrentLayoutManagerType = MainActivity.LayoutManagerType.LINEAR_LAYOUT_MANAGER;


        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    } // setRecyclerViewLayoutManager






    class ContactListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        protected ArrayList <Contacto>  summaryList = new ArrayList <Contacto>();
        protected Activity activity;


        class ViewHolder2 extends RecyclerView.ViewHolder{
            public TextView mTextView;
            public TextView mTextView2;
            public ViewHolder2(View v){
                super(v);
                mTextView = (TextView) v.findViewById(R.id.mProjectCustomerLabel);
                mTextView2 = (TextView)v.findViewById(R.id.mProjectNumberLabel);
            }
            public TextView getTextView(){
                return mTextView;
            }
            public TextView getTextView2(){
                return mTextView2;
            }
        }

        public ContactListAdapter(){

        }
        public ContactListAdapter(ArrayList<Contacto> summaryList, Activity context){
            this.summaryList = summaryList;
            this.activity = context;

        }


        @Override
        public ContactListAdapter.ViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType){

            MainActivity.ContactListAdapter.ViewHolder2 vh =null;
            vh = new MainActivity.ContactListAdapter.ViewHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.line_contact_view, parent, false));
            return vh;
        }







        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position){
            MainActivity.ContactListAdapter.ViewHolder2 viewHolder2= (MainActivity.ContactListAdapter.ViewHolder2) holder;
            viewHolder2.getTextView().setText(summaryList.get(position).getNombre());
            viewHolder2.getTextView2().setText(summaryList.get(position).getApellido());




        }
        @Override
        public int getItemCount(){
            return summaryList.size();
        }

        @Override
        public int getItemViewType(int position){
            return position;

        }
    }




    }





