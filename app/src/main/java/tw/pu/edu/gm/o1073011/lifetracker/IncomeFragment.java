package tw.pu.edu.gm.o1073011.lifetracker;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;

import tw.pu.edu.gm.o1073011.lifetracker.Model.Data;

public class IncomeFragment extends Fragment {

    private DatabaseReference mIncomeDatabase;

    private RecyclerView recyclerView;

    private TextView incomeTotalSum;

    private EditText edtAmmount;
    private EditText edtType;
    private EditText edtNote;

    private Button btnUpdate;
    private Button btnDelete;

    private String type;
    private String note;
    private int amount;

    private String post_key;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_income, container, false);
        //Firebase database
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();

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
                for (DataSnapshot mysnapshot: snapshot.getChildren()){
                    Data data = mysnapshot.getValue(Data.class);
                    totalvalue +=data.getAmount();
                    String stTotalValue = String.valueOf(totalvalue);
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
            protected void populateViewHolder(MyViewHolder myViewHolder, final Data data, final int i) {
                myViewHolder.setType(data.getType());
                myViewHolder.setNote(data.getNote());
                myViewHolder.setDate(data.getDate());
                myViewHolder.setAmmount(data.getAmount());
                myViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        post_key = getRef(i).getKey();

                        type = data.getType();
                        note = data.getNote();
                        amount = data.getAmount();
                        updateDataItem();
                    }
                });
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

    private void updateDataItem(){
        AlertDialog.Builder mydialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View myview = inflater.inflate(R.layout.update_data_item, null);
        mydialog.setView(myview);

        edtAmmount=myview.findViewById(R.id.amount_edt);
        edtType=myview.findViewById(R.id.type_edt);
        edtNote=myview.findViewById(R.id.note_edt);

        edtType.setText(type);
        edtType.setSelection(type.length());

        edtNote.setText(note);
        edtNote.setSelection(note.length());

        edtAmmount.setText(String.valueOf(amount));
        edtAmmount.setSelection(String.valueOf(amount).length());

        btnUpdate=myview.findViewById(R.id.btn_upd_Update);
        btnDelete=myview.findViewById(R.id.btn_upd_Delete);

        final AlertDialog dialog=mydialog.create();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type=edtType.getText().toString().trim();
                note=edtNote.getText().toString().trim();

                String mdammount=String.valueOf(amount);
                mdammount=edtAmmount.getText().toString().trim();
                int myAmount=Integer.parseInt(mdammount);

                String mDate= DateFormat.getDateInstance().format(new Date());

                Data data = new Data(myAmount,type,note,post_key,mDate);
                mIncomeDatabase.child(post_key).setValue(data);

                dialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIncomeDatabase.child(post_key).removeValue();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}