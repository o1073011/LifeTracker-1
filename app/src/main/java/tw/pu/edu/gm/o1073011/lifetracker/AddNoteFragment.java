package tw.pu.edu.gm.o1073011.lifetracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import tw.pu.edu.gm.o1073011.lifetracker.Model.Note;

public class AddNoteFragment extends Fragment {

    private FloatingActionButton fab_save_btn;

    private FirebaseAuth mAuth;
    private DatabaseReference noteDatabase;

    private EditText edtTitle;
    private EditText edtContent;

    private String title;
    private String content;
    private String date;

    private FloatingActionButton fab_save;

    Calendar myCalendar;

    @Override
    public void onStart() {
        super.onStart();

//        FirebaseRecyclerAdapter<Note, NoteViewHolder>adapter = new FirebaseRecyclerAdapter<Note, NoteViewHolder>(
//                Note.class,
//                R.layout.note_view_layout,
//                NoteViewHolder.class,
//                noteDatabase
//        ) {
//            @Override
//            protected void populateViewHolder(NoteViewHolder noteViewHolder, Note note, int i) {
//                noteViewHolder.setTitle(note.getTitle());
//                noteViewHolder.setContent(note.getContent());
//            }
//        };
    }

//    public static class NoteViewHolder extends RecyclerView.ViewHolder{
//        View mView;
//
//        public NoteViewHolder(View itemView){
//            super(itemView);
//            mView = itemView;
//        }
//
//        public void setTitle(String title){
//            TextView mTitle = mView.findViewById(R.id.titleNote);
//            mTitle.setText(title);
//        }
//
//        public void setContent(String content){
//            TextView mContent = mView.findViewById(R.id.contentNote);
//            mContent.setText(content);
//        }
//    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        mAuth = FirebaseAuth.getInstance();
//        FirebaseUser mUser = mAuth.getCurrentUser();
//        String uid = mUser.getUid();
//        noteDatabase = FirebaseDatabase.getInstance().getReference().child("NoteData").child(uid);
//        View myView =  inflater.inflate(R.layout.fragment_add_note, container, false);
//        edtTitle = myView.findViewById(R.id.addNoteTitle);
//        edtContent = myView.findViewById(R.id.addNoteContent);
//
//        fab_save = myView.findViewById(R.id.fab_save);
//
//        fab_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                title = edtTitle.getText().toString().trim();
//                content = edtContent.getText().toString().trim();
//
//                if (TextUtils.isEmpty(title)){
//                    edtTitle.setError("Required Field");
//                    return;
//                }
//
//                String id = noteDatabase.push().getKey();
//                date = DateFormat.getInstance().format(new Date());
//                Note note = new Note(title, content, date);
//                noteDatabase.child(id).setValue(note);
//
//            }
//        });
//
//        getActivity().getFragmentManager().popBackStack();
//        return myView;
//
//    }
}