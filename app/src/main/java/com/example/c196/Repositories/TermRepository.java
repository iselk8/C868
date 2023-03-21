package com.example.c196.Repositories;

import android.app.Application;

import com.example.c196.DAO.TermDAO;
import com.example.c196.Database;
import com.example.c196.Entities.TermModel;

import java.util.List;

public class TermRepository {

    private TermDAO termDAO;

    private List<TermModel> termList;

    public TermRepository(Application application) {
        Database db = Database.getDatabase(application);
        termDAO = db.getTermDAO();
        termList = termDAO.getAllTerms();
    }

    public List<TermModel> getTermsByUsername(String username){return termDAO.getTermByUser(username);}

    public TermModel getTermByID(int termID){
        return termDAO.getTermByID(termID);
    }

    public int getTermIDByTitle(String username, String termTitle){
       return termDAO.getTermIDByTitle(username, termTitle);
    }

    public boolean isTaken(String username, String termTitle){
        return termDAO.isTaken(username, termTitle);
    }

    public void insertTerm(TermModel term){
        termDAO.insertTerm(term);
    }

    public void deleteTerm(TermModel term){
        termDAO.deleteTerm(term);
    }

    public void deleteTerm(int termID){
        termDAO.deleteTermByID(termID);
    }
}
