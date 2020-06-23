package tw.pu.edu.gm.o1073011.lifetracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tw.pu.edu.gm.o1073011.lifetracker.Model.Note;

public class NoteFragment extends Fragment {

    private FloatingActionButton fab_add_note;

    private boolean isOpen;

    private FirebaseAuth mAuth;
    private DatabaseReference mNoteDataBase;

    private RecyclerView noteRecyclerView;

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Note, NoteViewHolder> noteAdapter = new FirebaseRecyclerAdapter<Note, NoteViewHolder>(
                Note.class,
                R.layout.note_view_layout,
                NoteViewHolder.class,
                mNoteDataBase
        ) {
            @Override
            protected void populateViewHolder(NoteViewHolder noteViewHolder, Note note, int i) {
                noteViewHolder.setTitle(note.getTitle());
                noteViewHolder.setContent(note.getContent());
            }
        };
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public NoteViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String title){
            TextView mTitle = mView.findViewById(R.id.titleNote);
            mTitle.setText(title);
        }

        public void setContent(String content){
            TextView mTitle = mView.findViewById(R.id.contentNote);
            mTitle.setText(content);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_note, container, false);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();
        mNoteDataBase = FirebaseDatabase.getInstance().getReference().child("Note").child(uid);

        fab_add_note = myView.findViewById(R.id.addNoteFloat);

        noteRecyclerView = myView.findViewById(R.id.recycler_note);

        fab_add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddNoteFragment();

                if (fragment != null) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.main_frame, fragment);
                    ft.commit();
                }
            }
        });
        return myView;
    }
}