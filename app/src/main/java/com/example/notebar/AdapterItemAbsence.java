package com.example.notebar;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterItemAbsence extends RecyclerView.Adapter<AdapterItemAbsence.ItemViewHolder> {
    Context activity;
    ArrayList<dataAbsence> absenceArrayList;
    ArrayList<Note> NoteArrayList;
    Button btn_simpan;
    Dialog dialog;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMMM-yyyy",new Locale("fr","MA"));

    public AdapterItemAbsence(Context activity, ArrayList<dataAbsence> absenceArrayList, ArrayList<Note> noteArrayList, Button btn_simpan,Dialog dialog) {
        this.activity = activity;
        this.absenceArrayList = absenceArrayList;
        this.NoteArrayList = noteArrayList;
        this.btn_simpan = btn_simpan;
        this.dialog=dialog;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_absence,parent,false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bindView(NoteArrayList.get(position));
        dataAbsence abs =absenceArrayList.get(position);


        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    absenceArrayList.set(position,new dataAbsence(
                            absenceArrayList.get(position).getClé_étudiant(),
                            absenceArrayList.get(position).getDate(),"yes"
                    ));
                }else{
                    absenceArrayList.set(position,new dataAbsence(
                            absenceArrayList.get(position).getClé_étudiant(),
                            absenceArrayList.get(position).getDate(),"no"
                    ));

                }
            }
        });
        if (abs.getIsAbsence().equals("yes")){
            holder.checkbox.setChecked(true);
        }else if (! abs.getIsAbsence().equals("no")){
            holder.checkbox.setChecked(false);
        }


    }

    @Override
    public int getItemCount() {
        return absenceArrayList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name;
        CheckBox checkbox;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name =itemView.findViewById(R.id.txt_name);
            checkbox =itemView.findViewById(R.id.checkbox);

        }

        public void bindView(Note note) {
            checkbox.setEnabled(true);
            txt_name.setText("Name :"+ note.getTitle());


            btn_simpan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i=0 ; i< absenceArrayList.size();i++){
                        database.child("Liste d'absence").child(absenceArrayList.get(i).getClé_étudiant()+"-"+simpleDateFormat.format(absenceArrayList.get(i).getDate()))
                                .setValue(new dataAbsence(
                                        absenceArrayList.get(i).getClé_étudiant(),
                                        absenceArrayList.get(i).getDate(),
                                        absenceArrayList.get(i).getIsAbsence()
                                ) );
                        if (i==absenceArrayList.size()-1){
                            Toast.makeText(activity, "données enregistrées avec succés", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                }
            });
        }
    }



}
