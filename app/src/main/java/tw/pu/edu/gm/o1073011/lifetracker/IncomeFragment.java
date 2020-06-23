package tw.pu.edu.gm.o1073011.lifetracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import tw.pu.edu.gm.o1073011.lifetracker.Model.Data;

public class IncomeFragment extends Fragment {

    private DatabaseReference mIncomeDatabase;

    private RecyclerView recyclerView;

    private TextView incomeTotalSum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_income, container, false);
        //Firebase database
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();
        //System.out.println("mUser = " +mUser);
        String uid = mUser.getUid();
        //System.out.println("uid = " +uid);

        mIncomeDatabase = FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);

        recyclerView = myview.findViewById(R.id.recyler_id_income);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        incomeTotalSum = myview.findViewById(R.id.income_txt_result);

        mIncomeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalvalue = 0;
                System.out.println("totalvalue = "+totalvalue);
                for (DataSnapshot mysnapshot: snapshot.getChildren()){
                    Data data = mysnapshot.getValue(Data.class);
                    totalvalue +=data.getAmount();
                    String stTotalValue = String.valueOf(totalvalue);
                    System.out.println("totalvalue = "+totalvalue);
                    incomeTotalSum.setText(stTotalValue);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return myview;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Data, MyViewHolder>adapter = new FirebaseRecyclerAdapter<Data, MyViewHolder>
                (
                        Data.class,
                        R.layout.income_recycler_data,
                        MyViewHolder.class,
                        mIncomeDatabase
                ) {
            @Override
            protected void populateViewHolder(MyViewHolder myViewHolder, Data data, int i) {
                myViewHolder.setType(data.getType());
                myViewHolder.setNote(data.getNote());
                myViewHolder.setDate(data.getDate());
                myViewHolder.setAmmount(data.getAmount());
            }
        };
        recyclerView.setAdapter(adapter);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setType(String type) {
            TextView mType = mView.findViewById(R.id.type_txt_income);
            mType.setText(type);
        }

        public void setNote(String note) {
            TextView mNote = mView.findViewById(R.id.note_txt_income);
            mNote.setText(note);
        }

        public void setDate(String date) {
            TextView mDate = mView.findViewById(R.id.date_txt_income);
            mDate.setText(date);
        }

        public void setAmmount(int ammount) {

            TextView mAmmount = mView.findViewById(R.id.ammount_txt_income);
            String stammount = String.valueOf(ammount);
            mAmmount.setText(stammount);
        }

    }
}