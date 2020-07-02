package tw.pu.edu.gm.o1073011.lifetracker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    private TextView totalIncomeResult;
    private TextView totalExpenseResult;

    private RecyclerView mRecyclerIncome;
    private RecyclerView mRecyclerExpense;

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        FirebaseRecyclerAdapter<Data, IncomeViewHolder> incomeAdapter = new FirebaseRecyclerAdapter<Data, IncomeViewHolder>
//                (
//                        Data.class,
//                        R.layout.dashboard_income,
//                        DashBoardFragment.IncomeViewHolder.class,
//                        mIncomeDatabase
//                ) {
//            @Override
//            protected void populateViewHolder(IncomeViewHolder incomeViewHolder, Data data, int i) {
//                incomeViewHolder.setIncomeType(data.getType());
//                incomeViewHolder.setIncomeAmount(data.getAmount());
//                incomeViewHolder.setIncomeDate(data.getDate());
//            }
//        };
//
//        FirebaseRecyclerAdapter<Data, ExpenseViewHolder> expenseAdapter = new FirebaseRecyclerAdapter<Data, ExpenseViewHolder>
//                (
//                        Data.class,
//                        R.layout.dashboard_expense,
//                        DashBoardFragment.ExpenseViewHolder.class,
//                        mExpenseDatabase
//                ) {
//            @Override
//            protected void populateViewHolder(ExpenseViewHolder expenseViewHolder, Data data, int i) {
//                expenseViewHolder.setExpenseType(data.getType());
//                expenseViewHolder.setExpenseAmount(data.getAmount());
//                expenseViewHolder.setExpenseDate(data.getDate());
//            }
//        };
//
//        mRecyclerIncome.setAdapter(incomeAdapter);
//        mRecyclerExpense.setAdapter(expenseAdapter);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        isOpen = false;
        View myview = inflater.inflate(R.layout.fragment_dash_board, container, false);
//
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser mUser = mAuth.getCurrentUser();
//        String uid = mUser.getUid();
//
//        mIncomeDatabase = FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);
//        mExpenseDatabase = FirebaseDatabase.getInstance().getReference().child("ExpenseData").child(uid);
//
//        //Floating button
//        fab_main_btn = myview.findViewById(R.id.fb_main_plus_btn);
//        fab_income_btn = myview.findViewById(R.id.income_Ft_btn);
//        fab_expense_btn = myview.findViewById(R.id.expense_Ft_btn);
//
//        fab_income_txt = myview.findViewById(R.id.income_ft_text);
//        fab_expense_txt = myview.findViewById(R.id.expense_ft_text);
//
//        FadeOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_open);
//        FadeClose = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_close);
//
//        totalIncomeResult = myview.findViewById(R.id.income_set_result);
//        totalExpenseResult = myview.findViewById(R.id.expense_set_result);
//
//        mRecyclerIncome = myview.findViewById(R.id.recycler_income);
//        mRecyclerExpense = myview.findViewById(R.id.recycler_expense);
//
//
//        fab_main_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addData();
//                if (isOpen) {
//                    fab_income_btn.setAnimation(FadeClose);
//                    fab_expense_btn.setAnimation(FadeClose);
//                    fab_income_btn.setVisibility(View.INVISIBLE);
//                    fab_expense_btn.setVisibility(View.INVISIBLE);
//
//                    fab_income_txt.setAnimation(FadeClose);
//                    fab_expense_txt.setAnimation(FadeClose);
//                    fab_income_txt.setVisibility(View.INVISIBLE);
//                    fab_expense_txt.setVisibility(View.INVISIBLE);
//                    isOpen = false;
//
//                } else {
//                    fab_income_btn.setAnimation(FadeOpen);
//                    fab_expense_btn.setAnimation(FadeOpen);
//                    fab_income_btn.setVisibility(View.VISIBLE);
//                    fab_expense_btn.setVisibility(View.VISIBLE);
//
//                    fab_income_txt.setAnimation(FadeOpen);
//                    fab_expense_txt.setAnimation(FadeOpen);
//                    fab_income_txt.setVisibility(View.VISIBLE);
//                    fab_expense_txt.setVisibility(View.VISIBLE);
//                    isOpen = true;
//                }
//            }
//        });
//
//        mIncomeDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                int totalvalue = 0;
//                for (DataSnapshot mysnapshot : snapshot.getChildren()) {
//                    Data data = mysnapshot.getValue(Data.class);
//                    totalvalue += data.getAmount();
//                    String stTotalValue = String.valueOf(totalvalue);
//                    totalIncomeResult.setText(stTotalValue);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        mExpenseDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                int totalvalue = 0;
//                for (DataSnapshot mysnapshot : snapshot.getChildren()) {
//                    Data data = mysnapshot.getValue(Data.class);
//                    totalvalue += data.getAmount();
//                    String stTotalValue = String.valueOf(totalvalue);
//                    totalExpenseResult.setText(stTotalValue);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        LinearLayoutManager layoutManagerIncome = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        layoutManagerIncome.setStackFromEnd(true);
//        layoutManagerIncome.setReverseLayout(true);
//        mRecyclerIncome.setHasFixedSize(true);
//        mRecyclerIncome.setLayoutManager(layoutManagerIncome);
//
//        LinearLayoutManager layoutManagerExpense = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        layoutManagerExpense.setStackFromEnd(true);
//        layoutManagerExpense.setReverseLayout(true);
//        mRecyclerExpense.setHasFixedSize(true);
//        mRecyclerExpense.setLayoutManager(layoutManagerExpense);
//
        return myview;
    }

//    private void ftAnimation() {
//        if (isOpen) {
//            fab_income_btn.startAnimation(FadeClose);
//            fab_expense_btn.startAnimation(FadeClose);
//            fab_income_btn.setVisibility(View.INVISIBLE);
//            fab_expense_btn.setVisibility(View.INVISIBLE);
//
//            fab_income_txt.startAnimation(FadeClose);
//            fab_expense_txt.startAnimation(FadeClose);
//            fab_income_txt.setVisibility(View.INVISIBLE);
//            fab_expense_txt.setVisibility(View.INVISIBLE);
//            isOpen = false;
//
//        } else {
//            fab_income_btn.startAnimation(FadeOpen);
//            fab_expense_btn.startAnimation(FadeOpen);
//            fab_income_btn.setVisibility(View.VISIBLE);
//            fab_expense_btn.setVisibility(View.VISIBLE);
//
//            fab_income_txt.startAnimation(FadeOpen);
//            fab_expense_txt.startAnimation(FadeOpen);
//            fab_income_txt.setVisibility(View.VISIBLE);
//            fab_expense_txt.setVisibility(View.VISIBLE);
//            isOpen = true;
//        }
//
//    }

//    private void addData() {
//        fab_income_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                incomeDataInsert();
//            }
//        });
//
//        fab_expense_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //System.out.println("uang keluar");
//                expenseDataInsert();
//            }
//        });
//    }

//    public void incomeDataInsert() {
//        AlertDialog.Builder myDialog = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = LayoutInflater.from(getActivity());
//        View myview = inflater.inflate(R.layout.custom_layout_for_insert, null);
//
//        myDialog.setView(myview);
//        final AlertDialog dialog = myDialog.create();
//        dialog.setCancelable(true);
//
//        final Calendar myCalendar = Calendar.getInstance();
//        final EditText edtdate = myview.findViewById(R.id.date_edt);
//
//        edtdate.setText(DateFormat.getDateInstance().format(new Date()));
//
//        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, month);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//                String myFormat = "MMM dd, yyyy";
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
//                edtdate.setText(simpleDateFormat.format(myCalendar.getTime()));
//            }
//        };
//
//        edtdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new DatePickerDialog(getActivity(), date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//
//        final EditText edtAmount = myview.findViewById(R.id.amount_edt);
//        final EditText edtType = myview.findViewById(R.id.type_edt);
//        final EditText edtNote = myview.findViewById(R.id.note_edt);
//
//        Button btnSave = myview.findViewById(R.id.btnSave);
//        Button btnCancel = myview.findViewById(R.id.btnCancel);
//
//        btnSave.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                String type = edtType.getText().toString().trim();
//                String amount = edtAmount.getText().toString().trim();
//                String note = edtNote.getText().toString().trim();
//
//                if (TextUtils.isEmpty(type)) {
//                    edtType.setError("Required Field");
//                    return;
//                }
//
//                if (TextUtils.isEmpty(amount)) {
//                    edtAmount.setError("Required Field");
//                    return;
//                }
//
//                int ourammontint = Integer.parseInt(amount);
//
//                if (TextUtils.isEmpty(note)) {
//                    edtNote.setError("Required Field");
//                    return;
//                }
//
//                String id = mIncomeDatabase.push().getKey();
//                String mDate = /*DateFormat.getDateInstance().format(new Date())*/ edtdate.getText().toString();
//
//                Data data = new Data(ourammontint, type, note, id, mDate);
//                data.setAmount(ourammontint);
//                data.setType(type);
//                data.setDate(mDate);
//                data.setNote(note);
//                data.setId(id);
//                //System.out.println(data);
//                mIncomeDatabase.child(id).setValue(data);
//                Toast.makeText(getActivity(), "DATA ADDED", Toast.LENGTH_SHORT).show();
//                ftAnimation();
//                dialog.dismiss();
//
//
//            }
//        });
//
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ftAnimation();
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//    }
//
//    public void expenseDataInsert() {
//        AlertDialog.Builder myDialog = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = LayoutInflater.from(getActivity());
//        View myview = inflater.inflate(R.layout.custom_layout_for_insert, null);
//
//        myDialog.setView(myview);
//        final AlertDialog dialog = myDialog.create();
//        dialog.setCancelable(true);
//
//        final Calendar myCalendar = Calendar.getInstance();
//        final EditText edtdate = myview.findViewById(R.id.date_edt);
//
//        edtdate.setText(DateFormat.getDateInstance().format(new Date()));
//
//        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, month);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//                String myFormat = "MMM dd, yyyy";
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
//                edtdate.setText(simpleDateFormat.format(myCalendar.getTime()));
//            }
//        };
//
//        edtdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new DatePickerDialog(getActivity(), date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });
//
//        final EditText amount = myview.findViewById(R.id.amount_edt);
//        final EditText type = myview.findViewById(R.id.type_edt);
//        final EditText note = myview.findViewById(R.id.note_edt);
//
//
//        Button btnSave = myview.findViewById(R.id.btnSave);
//        Button btnCancel = myview.findViewById(R.id.btnCancel);
//
//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //System.out.println("data 1");
//                String tmAmmount = amount.getText().toString().trim();
//                String tmtype = type.getText().toString().trim();
//                String tmnote = note.getText().toString().trim();
//
//                if (TextUtils.isEmpty(tmAmmount)) {
//                    amount.setError("Required field");
//                    return;
//                }
//
//                int inAmmount = Integer.parseInt(tmAmmount);
//
//                if (TextUtils.isEmpty(tmtype)) {
//                    type.setError("Required field");
//                    return;
//                }
//
//                if (TextUtils.isEmpty(tmnote)) {
//                    note.setError("Required field");
//                    return;
//                }
//
//                String id = mExpenseDatabase.push().getKey();
//                String mDate = edtdate.getText().toString()/*DateFormat.getDateInstance().format(new Date())*/;
//
//                Data data = new Data(inAmmount, tmtype, tmnote, id, mDate);
//                mExpenseDatabase.child(id).setValue(data);
//                Toast.makeText(getActivity(), "DATA ADDED", Toast.LENGTH_SHORT).show();
//
//                ftAnimation();
//                dialog.dismiss();
//            }
//        });
//
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ftAnimation();
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }
//
//
//
//    public static class IncomeViewHolder extends RecyclerView.ViewHolder {
//
//        View mIncomeView;
//
//        public IncomeViewHolder(@NonNull View itemView) {
//            super(itemView);
//            mIncomeView = itemView;
//        }
//
//        public void setIncomeType(String type) {
//            TextView mtype = mIncomeView.findViewById(R.id.type_Income_ds);
//            mtype.setText(type);
//        }
//
//        public void setIncomeAmount(int amount) {
//            TextView mAmount = mIncomeView.findViewById(R.id.amount_Income_ds);
//            String strAmount = String.valueOf(amount);
//            mAmount.setText(strAmount);
//        }
//
//        public void setIncomeDate(String date) {
//            TextView mDate = mIncomeView.findViewById(R.id.date_income_ds);
//            mDate.setText(date);
//        }
//    }
//
//    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {
//        View mExpenseView;
//
//        public ExpenseViewHolder(@NonNull View itemView) {
//            super(itemView);
//            mExpenseView = itemView;
//        }
//
//        public void setExpenseType(String type) {
//            TextView mtype = mExpenseView.findViewById(R.id.type_Expense_ds);
//            mtype.setText(type);
//        }
//
//        public void setExpenseAmount(int amount) {
//            TextView mAmount = mExpenseView.findViewById(R.id.amount_expense_ds);
//            String strAmount = String.valueOf(amount);
//            mAmount.setText(strAmount);
//        }
//
//        public void setExpenseDate(String date) {
//            TextView mDate = mExpenseView.findViewById(R.id.date_expense_ds);
//            mDate.setText(date);
//        }
//    }
}