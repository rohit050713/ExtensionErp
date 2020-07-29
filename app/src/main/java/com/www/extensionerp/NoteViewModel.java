package com.www.extensionerp;

import android.app.Application;
import android.view.Display;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    LiveData<List<ModelTutorial>> allnotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
//        noteRepository= new NoteRepository(application);
//        allnotes=noteRepository.getAllnotes();

    }

    public void insert(ModelTutorial note){
//        noteRepository.insert(note);
    }
    public void update(ModelTutorial note){
//        noteRepository.update(note);
    }
    public void delete(ModelTutorial note){
//        noteRepository.delete(note);
    }
    public void deleteall(){
//        noteRepository.deleteallnotes();
    }
    public LiveData<List<ModelTutorial>> getAllnotes(){
        return allnotes;
    }
}
