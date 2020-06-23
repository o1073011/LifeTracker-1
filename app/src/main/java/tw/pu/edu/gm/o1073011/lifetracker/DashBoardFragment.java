package tw.pu.edu.gm.o1073011.lifetracker;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.JsonUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Member;
import java.text.DateFormat;
import java.util.Date;

import tw.pu.edu.gm.o1073011.lifetracker.Model.Data;

public class DashBoardFragment extends Fragment {

    private FloatingActionButton fab_income_btn;
    private FloatingActionButton fab_expense_btn;
    private FloatingActionButton fab_main_btn;

    //Floating button textview
    private TextView fab_income_txt;
    private TextView fab_expense_txt;

    private boolean isOpen;

    //Animation
    private Animation FadeOpen, FadeClose;

    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDatabase;
    private DatabaseReference mExpenseDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        isOpen = false;
        View myview = inflater.inflate(R.layout.fragment_dash_board, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();

        mIncomeDatabase = FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);
        //System.out.println("m income database :"+mIncomeDatabase.toString());
        mExpenseDatabase = FirebaseDatabase.getInstance().getReference().child("ExpenseData").child(uid);
        //System.out.println("m expense database: "+mExpenseDatabase.toString());
        //Floating button
        fab_main_btn = myview.findViewById(R.id.fb_main_plus_btn);
        fab_income_btn = myview.findViewById(R.id.income_Ft_btn);
        fab_expense_btn = myview.findViewById(R.id.expense_Ft_btn);

        fab_income_txt = myview.findViewById(R.id.income_ft_text);
        fab_expense_txt = myview.findViewById(R.id.expense_ft_text);

        FadeOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_open);
        FadeClose = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_close);

        fab_main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
                if (isOpen) {
                    fab_income_btn.setAnimation(FadeClose);
                    fab_expense_btn.setAnimation(FadeClose);
                    fab_income_btn.setVisibility(View.INVISIBLE);
                    fab_expense_btn.setVisibility(View.INVISIBLE);

                    fab_income_txt.setAnimation(FadeClose);
                    fab_expense_txt.setAnimation(FadeClose);
                    fab_income_txt.setVisibility(View.INVISIBLE);
                    fab_expense_txt.setVisibility(View.INVISIBLE);
                    isOpen = false;

                } else {
                    fab_income_btn.setAnimation(FadeOpen);
                    fab_expense_btn.setAnimation(FadeOpen);
                    fab_income_btn.setVisibility(View.VISIBLE);
                    fab_expense_btn.setVisibility(View.VISIBLE);

                    fab_income_txt.setAnimation(FadeOpen);
                    fab_expense_txt.setAnimation(FadeOpen);
                    fab_income_txt.setVisibility(View.VISIBLE);
                    fab_expense_txt.setVisibility(View.VISIBLE);
                    isOpen = true;
                }
            }
        });

        return myview;
    }

    private void ftAnimation() {
        if (isOpen) {
            fab_income_btn.startAnimation(FadeClose);
            fab_expense_btn.startAnimation(FadeClose);
            fab_income_btn.setVisibility(View.INVISIBLE);
            fab_expense_btn.setVisibility(View.INVISIBLE);

            fab_income_txt.startAnimation(FadeClose);
            fab_expense_txt.startAnimation(FadeClose);
            fab_income_txt.setVisibility(View.INVISIBLE);
            fab_expense_txt.setVisibility(View.INVISIBLE);
            isOpen = false;

        } else {
            fab_income_btn.startAnimation(FadeOpen);
            fab_expense_btn.startAnimation(FadeOpen);
            fab_income_btn.setVisibility(View.VISIBLE);
            fab_expense_btn.setVisibility(View.VISIBLE);

            fab_income_txt.startAnimation(FadeOpen);
            fab_expense_txt.startAnimation(FadeOpen);
            fab_income_txt.setVisibility(View.VISIBLE);
            fab_expense_txt.setVisibility(View.VISIBLE);
            isOpen = true;
        }

    }

    private void addData() {
        fab_income_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incomeDataInsert();
            }
        });

        fab_expense_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println("uang keluar");
                expenseDataInsert();
            }
        });
    }


    public void incomeDataInsert() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View myview = inflater.inflate(R.layout.custom_layout_for_insert, null);

        myDialog.setView(myview);
        //System.out.println("masuk 1");
        final AlertDialog dialog = myDialog.create();
        //System.out.println("masuk 2");
        dialog.setCancelable(true);

        final EditText edtAmount = myview.findViewById(R.id.amount_edt);
        //System.out.println("amount:"+edtAmount.getText());
        final EditText edtType = myview.findViewById(R.id.type_edt);
        //System.out.println("type:"+edtType.getText());
        final EditText edtNote = myview.findViewById(R.id.note_edt);
        //System.out.println("note:"+edtNote.getText());

        Button btnSave = myview.findViewById(R.id.btnSave);
        Button btnCancel = myview.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //System.out.println("masuk on click");
                String type = edtType.getText().toString().trim();
                //System.out.println("type string:"+type);
                String amount = edtAmount.getText().toString().trim();
                //System.out.println("type amount:"+amount);
                String note = edtNote.getText().toString().trim();
                //System.out.println("type note:"+note);

                if (TextUtils.isEmpty(type)) {
                    edtType.setError("Required Field");
                    return;
                }

                if (TextUtils.isEmpty(amount)) {
                    edtAmount.setError("Required Field");
                    return;
                }

                int ourammontint = Integer.parseInt(amount);

                if (TextUtils.isEmpty(note)) {
                    edtNote.setError("Required Field");
                    return;
                }

                String id = mIncomeDatabase.push().getKey();
                //System.out.println("ID = "+id);
                String mDate = DateFormat.getDateInstance().format(new Date());

                Data data = new Data(ourammontint,type,note,id,mDate);
                data.setAmount(ourammontint);
                data.setType(type);
                data.setDate(mDate);
                data.setNote(note);
                data.setId(id);
                //System.out.println(data);
                mIncomeDatabase.child(id).setValue(data);
                Toast.makeText(getActivity(), "DATA ADDED", Toast.LENGTH_SHORT).show();
                ftAnimation();
                dialog.dismiss();


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftAnimation();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void expenseDataInsert() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View myview = inflater.inflate(R.layout.custom_layout_for_insert, null);

        myDialog.setView(myview);
        final AlertDialog dialog = myDialog.create();


        dialog.setCancelable(true);

        final EditText amount = myview.findViewById(R.id.amount_edt);
        final EditText type = myview.findViewById(R.id.type_edt);
        final EditText note = myview.findViewById(R.id.note_edt);



        Button btnSave = myview.findViewById(R.id.btnSave);
        Button btnCancel = myview.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println("data 1");
                String tmAmmount = amount.getText().toString().trim();
                String tmtype = type.getText().toString().trim();
                String tmnote = note.getText().toString().trim();

                if (TextUtils.isEmpty(tmAmmount)) {
                    amount.setError("Required field");
                    return;
                }

                int inAmmount = Integer.parseInt(tmAmmount);

                if (TextUtils.isEmpty(tmtype)) {
                    type.setError("Required field");
                    return;
                }

                if (TextUtils.isEmpty(tmnote)) {
                    note.setError("Required field");
                    return;
                }

                String id = mExpenseDatabase.push().getKey();
                String mDate = DateFormat.getDateInstance().format(new Date());

                Data data = new Data(inAmmount, tmtype, tmnote, id, mDate);
                mExpenseDatabase.child(id).setValue(data);
                Toast.makeText(getActivity(), "DATA ADDED", Toast.LENGTH_SHORT).show();

                ftAnimation();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ftAnimation();
                dialog.dismiss();
            }
        });
       dialog.show();
    }
}