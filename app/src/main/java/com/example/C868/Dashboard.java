package com.example.c196;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.c196.Entities.AssessmentModel;
import com.example.c196.Entities.CourseModel;
import com.example.c196.Entities.TermModel;
import com.example.c196.Repositories.AssessmentRepository;
import com.example.c196.Repositories.CourseRepository;
import com.example.c196.Repositories.TermRepository;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;


import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    String username, name;
    ImageView logout, search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Bundle extras = getIntent().getExtras();

        //Hooks
        logout = (ImageView) findViewById(R.id.dashboard_logout_btn);
        logout.setClickable(true);

        search = (ImageView) findViewById(R.id.dashboard_search_btn);
        search.setClickable(true);

        if (extras != null) {
            username = extras.getString("username");
            name = extras.getString("name");
        }
    }

    public void launchSearch(View view) {

        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);

    }

    public void logOutBtn(View view) {
        logout();
    }

    @Override
    public void onBackPressed() {
        logout();
    }

    private void logout() {
        //Opens dialog box to confirm the user would like to log out before closing this activity
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing()) {
                    new AlertDialog.Builder(Dashboard.this).setTitle("Log out").setMessage("Are you sure you would like to log out?").setCancelable(true).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Dashboard.super.finish();
                        }
                    }).show();
                }
            }
        });
    }

    public void launchTerms(View view) {
        Intent intent = new Intent(this, DisplayData.class);
        intent.putExtra("username", username);
        intent.putExtra("data type", "term");
        startActivity(intent);
    }

    public void launchCourses(View view) {
        Intent intent = new Intent(this, DisplayData.class);
        intent.putExtra("username", username);
        intent.putExtra("data type", "course");
        startActivity(intent);
    }

    public void launchAssessments(View view) {
        Intent intent = new Intent(this, DisplayData.class);
        intent.putExtra("username", username);
        intent.putExtra("data type", "assessment");
        startActivity(intent);
    }

    private String getCurrentDateTime() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Get the current date and time using LocalDateTime class
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm");
            String formattedDateTime = LocalDateTime.now().format(formatter);
            return formattedDateTime;
        } else {
            // Get the current date and time using the Calendar class
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
            String formattedDateTime = dateFormat.format(Calendar.getInstance().getTime());
            return formattedDateTime;
        }
    }


    public void export(View view) {

        // Creating the file name under which our generated PDF will be saved.
        // The name consists of the username and the current date and time appended.
        String fileName = username + "-report-" + getCurrentDateTime() +".pdf";

        // Define the content resolver and content values for inserting the PDF file into the downloads directory
        ContentResolver contentResolver = getApplicationContext().getContentResolver();
        ContentValues contentValues = new ContentValues();

        contentValues.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
        contentValues.put(MediaStore.Downloads.MIME_TYPE, "application/pdf");
        contentValues.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);


        // Creating the required entities repository and lists required for our generated PDF
        TermRepository termRepo = new TermRepository(getApplication());
        List<TermModel> termList = termRepo.getTermsByUsername(username);

        CourseRepository courseRepo = new CourseRepository(getApplication());
        List<CourseModel> courses = courseRepo.getCoursesByUser(username);

        AssessmentRepository assessmentRepo = new AssessmentRepository(getApplication());
        List<AssessmentModel> assessments = assessmentRepo.getAssessmentByUser(username);

        // Create the colors required for our generated PDF
        Color wguBlue = new DeviceRgb(13, 48, 80);
        Color white = new DeviceRgb(255, 255, 255);


        try {
            // Create a new PDF file using the path defined
            PdfWriter writer = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                writer = new PdfWriter(contentResolver.openOutputStream(contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)));
            }
            PdfDocument pdfDoc = new PdfDocument(writer);

            // Create a new page in the PDF document
            Document document = new Document(pdfDoc);

            // Define an array of floats to represent the widths of each column in the table
            float columnWidth[] = {140, 140, 140, 140};

            // Create a new Table object with the specified column widths
            Table table1 = new Table(columnWidth);


            // Retrieve the drawable resource for the WGU logo
            Drawable wguLogo = getDrawable(R.drawable.wgu_logo);

            // Convert the drawable to a bitmap
            Bitmap bitmap = ((BitmapDrawable) wguLogo).getBitmap();

            // Compress the bitmap as a PNG and store the resulting bytes in a ByteArrayOutputStream
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapData = stream.toByteArray();

            // Create an ImageData object from the compressed bitmap data
            ImageData imageData = ImageDataFactory.create(bitmapData);

            // Create an Image element from the ImageData object
            com.itextpdf.layout.element.Image image = new Image(imageData);

            // Set the height of the image to 60
            image.setHeight(60);

            //Table1-------01
            table1.addCell(new Cell(3, 1).add(image).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell(1, 2).add(new Paragraph("Academic Schedule").setFontSize(18f)).setBorder(Border.NO_BORDER));

            //Table1-------02
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

            //Table1-------03
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("Username")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph(username)).setBorder(Border.NO_BORDER));

            //Table1-------04
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("Name")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph(Utility.capitalizeString(name))).setBorder(Border.NO_BORDER));

            //Table1-------05
            table1.addCell(new Cell().add(new Paragraph("\n")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

            //Table1-------06
            table1.addCell(new Cell(1, 2).add(new Paragraph("Western Governors University")).setBorder(Border.NO_BORDER));
            //table1.addCell(new Cell().add(new Paragraph("")));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

            //Table1-------07
            table1.addCell(new Cell().add(new Paragraph("4001 S 700 E #300")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

            //Table1-------08
            table1.addCell(new Cell().add(new Paragraph("Millcreek, UT 84107")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

            //Table1-------09
            table1.addCell(new Cell().add(new Paragraph("United States")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table1.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

            float columnWidth2[] = {62, 162, 162, 162};

            Table table2 = new Table(columnWidth2);

            //Table2-------01
            table2.addCell(new Cell().add(new Paragraph("Term ID")).setBackgroundColor(wguBlue).setFontColor(white));
            table2.addCell(new Cell().add(new Paragraph("Term Title")).setBackgroundColor(wguBlue).setFontColor(white));
            table2.addCell(new Cell().add(new Paragraph("Term Start Date")).setBackgroundColor(wguBlue).setFontColor(white));
            table2.addCell(new Cell().add(new Paragraph("Term End Date")).setBackgroundColor(wguBlue).setFontColor(white));

            for (int i = 0; i < termList.size(); i++) {
                table2.addCell(new Cell().add(new Paragraph(String.valueOf(termList.get(i).getTermId()))));
                table2.addCell(new Cell().add(new Paragraph(Utility.capitalizeString(termList.get(i).getTermTitle()))));
                table2.addCell(new Cell().add(new Paragraph(termList.get(i).getTermStartDate())));
                table2.addCell(new Cell().add(new Paragraph(termList.get(i).getTermEndDate())));
            }

            float columnWidth3[] = {62, 112, 112, 112, 112, 112, 112, 112};

            Table table3 = new Table(columnWidth3);

            table3.addCell(new Cell().add(new Paragraph("Course Title")).setBackgroundColor(wguBlue).setFontColor(white));
            table3.addCell(new Cell().add(new Paragraph("Instructor Name")).setBackgroundColor(wguBlue).setFontColor(white));
            table3.addCell(new Cell().add(new Paragraph("Instructor Phone")).setBackgroundColor(wguBlue).setFontColor(white));
            table3.addCell(new Cell().add(new Paragraph("Instructor Email")).setBackgroundColor(wguBlue).setFontColor(white));
            table3.addCell(new Cell().add(new Paragraph("Course Status")).setBackgroundColor(wguBlue).setFontColor(white));
            table3.addCell(new Cell().add(new Paragraph("Term")).setBackgroundColor(wguBlue).setFontColor(white));
            table3.addCell(new Cell().add(new Paragraph("Start Date")).setBackgroundColor(wguBlue).setFontColor(white));
            table3.addCell(new Cell().add(new Paragraph("End Date")).setBackgroundColor(wguBlue).setFontColor(white));

            for (int i = 0; i < courses.size(); i++) {
                table3.addCell(new Cell().add(new Paragraph(courses.get(i).getCourseTitle().toUpperCase())));
                table3.addCell(new Cell().add(new Paragraph(Utility.capitalizeString(courses.get(i).getCourseInstructorName()))));
                table3.addCell(new Cell().add(new Paragraph(courses.get(i).getCourseInstructorPhoneNumber())));
                table3.addCell(new Cell().add(new Paragraph(courses.get(i).getCourseInstructorEmail())));
                table3.addCell(new Cell().add(new Paragraph(courses.get(i).getCourseStatus())));
                table3.addCell(new Cell().add(new Paragraph(Utility.capitalizeString(termRepo.getTermByID(courses.get(i).getTermId()).getTermTitle()))));
                table3.addCell(new Cell().add(new Paragraph(courses.get(i).getCourseStartDate())));
                table3.addCell(new Cell().add(new Paragraph(courses.get(i).getCourseEndDate())));
            }

            Table table4 = new Table(columnWidth2);

            //Table2-------01
            table4.addCell(new Cell().add(new Paragraph("Course")).setBackgroundColor(wguBlue).setFontColor(white));
            table4.addCell(new Cell().add(new Paragraph("Assessment Title")).setBackgroundColor(wguBlue).setFontColor(white));
            table4.addCell(new Cell().add(new Paragraph("Assessment Type")).setBackgroundColor(wguBlue).setFontColor(white));
            table4.addCell(new Cell().add(new Paragraph("Enter Due Date")).setBackgroundColor(wguBlue).setFontColor(white));

            for (int i = 0; i < assessments.size(); i++) {
                table4.addCell(new Cell().add(new Paragraph(courseRepo.getCoursesByID(assessments.get(i).getCourseID()).getCourseTitle().toUpperCase())));
                table4.addCell(new Cell().add(new Paragraph(Utility.capitalizeString(assessments.get(i).getAssessmentTitle()))));
                table4.addCell(new Cell().add(new Paragraph(assessments.get(i).getAssessmentType())));
                table4.addCell(new Cell().add(new Paragraph(assessments.get(i).getAssessmentDueDate())));
            }

            document.add(table1);
            document.add(new Paragraph("\n"));
            document.add(table2);
            document.add(new Paragraph("\n"));
            document.add(table3);
            document.add(new Paragraph("\n"));
            document.add(table4);

            // Closing the document
            document.close();

            // Display a toast message to indicate that the PDF file has been created and saved
            Toast.makeText(this, "PDF file saved to " + Environment.DIRECTORY_DOWNLOADS + "/" + fileName, Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            // Display a toast message to indicate that an error occurred while creating the PDF file
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
