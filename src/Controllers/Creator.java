package Controllers;

import Logic.Querys;
import Models.Task;
import Models.User;
import static TaskAgent.TaskAgent.db;
import static TaskAgent.TaskAgent.task_state;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;

public class Creator {

    public Creator() {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("raport.pdf"));
            document.open();
            Font f = new Font(FontFamily.TIMES_ROMAN, 20.0f,Font.UNDERLINE,BaseColor.BLACK);
            Paragraph p = new Paragraph("Raport", f);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            document.add(new Paragraph("\n\n"));
            ResultSet tasks = db.Query("SELECT * FROM tasks");
            while(tasks.next()) {
                document.add(writeTask(tasks.getString("name"), tasks.getString("description"), tasks.getInt("status"), Querys.getUserNameById(tasks.getInt("id_supervisor")), Querys.getUserNameById(tasks.getInt("user_id")), tasks.getString("comment"), tasks.getString("date_start"), tasks.getString("date_end")));
                document.add(new Paragraph("\n\n"));
            }

            document.close();
            Desktop.getDesktop().open(new File("raport.pdf"));
        } catch(Exception e){
            System.out.println("Exception: "+e);
        }
    }
    
    public Creator(User user, boolean us, int sort) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("raport.pdf"));
            document.open();
            Font f = new Font(FontFamily.TIMES_ROMAN, 20.0f,Font.UNDERLINE,BaseColor.BLACK);
            Paragraph p = new Paragraph("Raport", f);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            document.add(new Paragraph("\n\n"));
            document.add(writeUser(user.getFirstname(), user.getLastname()));
            document.add(new Paragraph("\n\n"));
            ResultSet tasks;
            if(sort == 1)
                tasks = db.Query("SELECT * FROM tasks WHERE user_id = " + user.getId() + " ORDER BY status");
            else if(sort == 2)
                tasks = db.Query("SELECT * FROM tasks WHERE user_id = " + user.getId() + " ORDER BY date_start");
            else
                tasks = db.Query("SELECT * FROM tasks WHERE user_id = " + user.getId());
            while(tasks.next()) {
                document.add(writeTask(tasks.getString("name"), tasks.getString("description"), tasks.getInt("status"), Querys.getUserNameById(tasks.getInt("id_supervisor")), Querys.getUserNameById(tasks.getInt("user_id")), tasks.getString("comment"), tasks.getString("date_start"), tasks.getString("date_end")));
                document.add(new Paragraph("\n\n"));
            }

            document.close();
            Desktop.getDesktop().open(new File("raport.pdf"));
        } catch(Exception e){
            System.out.println("Exception: "+e);
        }
    }
    
    public Creator(int supervisor) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("raport.pdf"));
            document.open();
            Font f = new Font(FontFamily.TIMES_ROMAN, 20.0f,Font.UNDERLINE,BaseColor.BLACK);
            Paragraph p = new Paragraph("Raport", f);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            document.add(new Paragraph("\n\n"));
            ResultSet supvisor = db.Query("SELECT * FROM users WHERE id = " + supervisor);
            if(supvisor.next()) {
                document.add(writeUser(supvisor.getString("firstname"), supvisor.getString("lastname")));
            }
            document.add(new Paragraph("\n\n"));
            ResultSet tasks = db.Query("SELECT * FROM tasks WHERE id_supervisor = " + supervisor);
            while(tasks.next()) {
                document.add(writeTask(tasks.getString("name"), tasks.getString("description"), tasks.getInt("status"), Querys.getUserNameById(tasks.getInt("id_supervisor")), Querys.getUserNameById(tasks.getInt("user_id")), tasks.getString("comment"), tasks.getString("date_start"), tasks.getString("date_end")));
                document.add(new Paragraph("\n\n"));
            }

            document.close();
            Desktop.getDesktop().open(new File("raport.pdf"));
        } catch(Exception e){
            System.out.println("Exception: "+e);
        }
    }
    
    public PdfPTable writeUser(String name, String lastname) {
        PdfPTable table = new PdfPTable(2);
        table.addCell(new PdfPCell(new Paragraph("Imie pracownika:")));
        table.addCell(new PdfPCell(new Paragraph(name)));
        table.addCell(new PdfPCell(new Paragraph("Nazwisko pracownika:")));
        table.addCell(new PdfPCell(new Paragraph(lastname)));
        table.addCell(new PdfPCell(new Paragraph("Grupa pracownika:")));
        table.addCell(new PdfPCell(new Paragraph("Uzytkownik")));
        return table;
    }
    
    public PdfPTable writeTask(String name, String desc, int status, String supervisor, String user, String comment, String start, String end) {
        PdfPTable table = new PdfPTable(2);
        table.addCell(new PdfPCell(new Paragraph("Nazwa zadania:")));
        table.addCell(new PdfPCell(new Paragraph(name)));
        table.addCell(new PdfPCell(new Paragraph("Opis zadania:")));
        table.addCell(new PdfPCell(new Paragraph(desc)));
        table.addCell(new PdfPCell(new Paragraph("Status:")));
        table.addCell(new PdfPCell(new Paragraph(task_state[status - 1])));
        table.addCell(new PdfPCell(new Paragraph("Kierownik:")));
        table.addCell(new PdfPCell(new Paragraph(supervisor)));
        table.addCell(new PdfPCell(new Paragraph("Pracownik:")));
        table.addCell(new PdfPCell(new Paragraph(user)));
        table.addCell(new PdfPCell(new Paragraph("Komentarz:")));
        table.addCell(new PdfPCell(new Paragraph(comment)));
        table.addCell(new PdfPCell(new Paragraph("Data rozpoczęcia:")));
        table.addCell(new PdfPCell(new Paragraph(start)));
        table.addCell(new PdfPCell(new Paragraph("Data zakończenia:")));
        table.addCell(new PdfPCell(new Paragraph(end)));
        return table;
    }

    public Creator(Task task) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("raport.pdf"));
            document.open();
            Font f = new Font(FontFamily.TIMES_ROMAN, 20.0f,Font.UNDERLINE,BaseColor.BLACK);
            Paragraph p = new Paragraph("Raport", f);
            p.setAlignment(Element.ALIGN_CENTER);
            PdfPTable table = new PdfPTable(2);
            table.addCell(new PdfPCell(new Paragraph("Nazwa zadania:")));
            table.addCell(new PdfPCell(new Paragraph(task.getName())));
            table.addCell(new PdfPCell(new Paragraph("Opis zadania:")));
            table.addCell(new PdfPCell(new Paragraph(task.getDescription())));
            table.addCell(new PdfPCell(new Paragraph("Status:")));
            table.addCell(new PdfPCell(new Paragraph(task.getStatus())));
            table.addCell(new PdfPCell(new Paragraph("Kierownik:")));
            table.addCell(new PdfPCell(new Paragraph(task.getSupervisor())));
            table.addCell(new PdfPCell(new Paragraph("Pracownik:")));
            table.addCell(new PdfPCell(new Paragraph(task.getUser())));
            table.addCell(new PdfPCell(new Paragraph("Komentarz:")));
            table.addCell(new PdfPCell(new Paragraph(task.getComment())));
            table.addCell(new PdfPCell(new Paragraph("Data rozpoczęcia:")));
            table.addCell(new PdfPCell(new Paragraph(task.getStart())));
            table.addCell(new PdfPCell(new Paragraph("Data zakończenia:")));
            table.addCell(new PdfPCell(new Paragraph(task.getEnd())));

            document.add(p);
            document.add(new Paragraph("\n\n"));
            document.add(table);

            document.close();
            Desktop.getDesktop().open(new File("raport.pdf"));
        } catch(Exception e){
            System.out.println("Exception: "+e);
        }
    }
}