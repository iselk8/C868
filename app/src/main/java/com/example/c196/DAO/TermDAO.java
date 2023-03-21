package com.example.c196.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.c196.Entities.TermModel;

import java.util.List;

@Dao
public interface TermDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTerm(TermModel term);

    @Query("SELECT * FROM term ORDER BY term_start_date")
    List<TermModel> getAllTerms();

    @Query("SELECT * FROM term WHERE term_id = :termID")
    TermModel getTermByID(int termID);

    @Query("SELECT term_id FROM term WHERE username = :username AND term_title = :termTitle")
    int getTermIDByTitle(String username, String termTitle);

    @Query("SELECT * FROM term WHERE username = :username")
    List<TermModel> getTermByUser(String username);

    @Query("DELETE FROM term WHERE term_id = :termID")
    void deleteTermByID(int termID);

    @Delete
    void deleteTerm(TermModel term);

    @Query("SELECT EXISTS(SELECT * FROM term WHERE username = :username AND term_title = :termTitle)")
    boolean isTaken(String username, String termTitle);

    @Query("DELETE FROM term")
    int deleteAllTerms();

    @Query("SELECT COUNT(*) FROM term")
    int getTermCount();

    @Query("SELECT COUNT(*) FROM term WHERE username = :username")
    int getTermCountByUser(String username);

}
