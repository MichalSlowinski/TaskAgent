package Controllers;

import Logic.Querys;
import Models.Task;
import Models.User;
import static TaskAgent.TaskAgent.db;
import static TaskAgent.TaskAgent.task_state;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Creator {

    public Creator(int sort, int[] options) {
        Document document = new Document();

        try {
            String file = "raports/raport"+System.currentTimeMillis()+".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            Font f = new Font(FontFamily.TIMES_ROMAN, 20.0f,Font.UNDERLINE,BaseColor.BLACK);
            Paragraph p = new Paragraph("Raport", f);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            document.add(new Paragraph("\n\n"));
            ResultSet tasks = null;
            if(sort == 1)
                tasks = db.Query("SELECT * FROM tasks ORDER BY status");
            else if(sort == 2)
                tasks = db.Query("SELECT * FROM tasks ORDER BY date_start");
            else if(sort == 4)
                tasks = db.Query("SELECT * FROM tasks ORDER BY id_supervisor");
            else
                tasks = db.Query("SELECT * FROM tasks");
            while(tasks.next()) {
                document.add(writeTask(tasks.getString("name"), tasks.getString("description"), tasks.getInt("status"), Querys.getUserNameById(tasks.getInt("id_supervisor")), Querys.getUserNameById(tasks.getInt("user_id")), tasks.getString("comment"), tasks.getString("date_start"), tasks.getString("date_end"), options));
                document.add(new Paragraph("\n\n"));
            }

            document.close();
            Desktop.getDesktop().open(new File(file));
        } catch(Exception e){
            System.out.println("Exception: "+e);
        }
    }
    
    public Creator(User user, boolean us, int sort, int[] options) {
        try {
            Document document = new Document();
            String file = "raports/raport"+System.currentTimeMillis()+".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            Font f = new Font(FontFamily.TIMES_ROMAN, 20.0f,Font.UNDERLINE,BaseColor.BLACK);
            Paragraph p = new Paragraph("Raport", f);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            if(options[0] == 1) {
                document.add(new Paragraph("\n\n"));
                document.add(writeUser(user.getFirstname(), user.getLastname(), user.getGroupname()));
            }
            document.add(new Paragraph("\n\n"));
            ResultSet tasks;
            if(sort == 1)
                tasks = db.Query("SELECT * FROM tasks WHERE user_id = " + user.getId() + " ORDER BY status");
            else if(sort == 2)
                tasks = db.Query("SELECT * FROM tasks WHERE user_id = " + user.getId() + " ORDER BY date_start");
            else if(sort == 4)
                tasks = db.Query("SELECT * FROM tasks WHERE user_id = " + user.getId() + " ORDER BY id_supervisor");
            else
                tasks = db.Query("SELECT * FROM tasks WHERE user_id = " + user.getId());
            while(tasks.next()) {
                document.add(writeTask(tasks.getString("name"), tasks.getString("description"), tasks.getInt("status"), Querys.getUserNameById(tasks.getInt("id_supervisor")), Querys.getUserNameById(tasks.getInt("user_id")), tasks.getString("comment"), tasks.getString("date_start"), tasks.getString("date_end"), options));
                document.add(new Paragraph("\n\n"));
            }

            document.close();
            Desktop.getDesktop().open(new File(file));
        } catch (Exception ex) {
            Logger.getLogger(Creator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public Creator(int supervisor) {
        Document document = new Document();

        try {
            String file = "raports/raport"+System.currentTimeMillis()+".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            Font f = new Font(FontFamily.TIMES_ROMAN, 20.0f,Font.UNDERLINE,BaseColor.BLACK);
            Paragraph p = new Paragraph("Raport", f);
            p.setAlignment(Element.ALIGN_CENTER);
            document.add(p);
            document.add(new Paragraph("\n\n"));
            ResultSet supvisor = db.Query("SELECT * FROM users WHERE id = " + supervisor);
            if(supvisor.next()) {
                String[] groups = {"Użytkownik", "Kierownik", "Administrator"};
                document.add(writeUser(supvisor.getString("firstname"), supvisor.getString("lastname"), groups[supvisor.getInt("id_groups") - 1]));
            }
            document.add(new Paragraph("\n\n"));
            int[] options = {1,1,1,1,1,1};
            ResultSet tasks = db.Query("SELECT * FROM tasks WHERE id_supervisor = " + supervisor);
            while(tasks.next()) {
                document.add(writeTask(tasks.getString("name"), tasks.getString("description"), tasks.getInt("status"), Querys.getUserNameById(tasks.getInt("id_supervisor")), Querys.getUserNameById(tasks.getInt("user_id")), tasks.getString("comment"), tasks.getString("date_start"), tasks.getString("date_end"), options));
                document.add(new Paragraph("\n\n"));
            }

            document.close();
            Desktop.getDesktop().open(new File(file));
        } catch(Exception e){
            System.out.println("Exception: "+e);
        }
    }
    
    public PdfPTable writeUser(String name, String lastname, String group) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(2);
        BaseFont bf = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.CP1250, BaseFont.EMBEDDED); 
        Font font = new Font(bf, 12); 
        table.addCell(new PdfPCell(new Paragraph("Imie pracownika:")));
        table.addCell(new PdfPCell(new Paragraph(name, font)));
        table.addCell(new PdfPCell(new Paragraph("Nazwisko pracownika:")));
        table.addCell(new PdfPCell(new Paragraph(lastname, font)));
        table.addCell(new PdfPCell(new Paragraph("Grupa pracownika:")));
        table.addCell(new PdfPCell(new Paragraph(group,font)));
        return table;
    }
    
    public PdfPTable writeTask(String name, String desc, int status, String supervisor, String user, String comment, String start, String end, int[] options) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(2);
        BaseFont bf = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.CP1250, BaseFont.EMBEDDED); 
        Font font = new Font(bf, 12); 
        table.addCell(new PdfPCell(new Paragraph("Nazwa zadania:")));
        table.addCell(new PdfPCell(new Paragraph(name,font)));
        if(options[4] == 1) {
            table.addCell(new PdfPCell(new Paragraph("Opis zadania:")));
            table.addCell(new PdfPCell(new Paragraph(desc,font)));
        }
        if(options[3] == 1) {
            table.addCell(new PdfPCell(new Paragraph("Status:")));
            table.addCell(new PdfPCell(new Paragraph(task_state[status - 1],font)));
        }
        if(options[2] == 1) {
            table.addCell(new PdfPCell(new Paragraph("Kierownik:")));
            table.addCell(new PdfPCell(new Paragraph(supervisor,font)));
        }
        table.addCell(new PdfPCell(new Paragraph("Pracownik:")));
        table.addCell(new PdfPCell(new Paragraph(user,font)));
        if(!comment.equals("")) {
            table.addCell(new PdfPCell(new Paragraph("Komentarz:")));
            table.addCell(new PdfPCell(new Paragraph(comment,font)));
        }
        if(options[1] == 1) {
            table.addCell(new PdfPCell(new Paragraph("Data rozpoczęcia:",font)));
            table.addCell(new PdfPCell(new Paragraph(start)));
            table.addCell(new PdfPCell(new Paragraph("Data zakończenia:",font)));
            table.addCell(new PdfPCell(new Paragraph(end)));
        }
        return table;
    }

    public Creator(Task task) {
        Document document = new Document();

        try {
            String file = "raports/raport"+System.currentTimeMillis()+".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            Font f = new Font(FontFamily.TIMES_ROMAN, 20.0f,Font.UNDERLINE,BaseColor.BLACK);
            BaseFont bf = BaseFont.createFont("c:/windows/fonts/arial.ttf", BaseFont.CP1250, BaseFont.EMBEDDED); 
            Font font = new Font(bf, 12); 
            Paragraph p = new Paragraph("Raport", f);
            p.setAlignment(Element.ALIGN_CENTER);
            PdfPTable table = new PdfPTable(2);
            table.addCell(new PdfPCell(new Paragraph("Nazwa zadania:")));
            table.addCell(new PdfPCell(new Paragraph(task.getName())));
            table.addCell(new PdfPCell(new Paragraph("Opis zadania:")));
            table.addCell(new PdfPCell(new Paragraph(task.getDescription())));
            table.addCell(new PdfPCell(new Paragraph("Status:")));
            table.addCell(new PdfPCell(new Paragraph(task_state[task.getStatus() - 1],font)));
            table.addCell(new PdfPCell(new Paragraph("Kierownik:")));
            table.addCell(new PdfPCell(new Paragraph(task.getSupervisor())));
            table.addCell(new PdfPCell(new Paragraph("Pracownik:")));
            table.addCell(new PdfPCell(new Paragraph(task.getUser())));
            if(!task.getComment().equals("")) {
                table.addCell(new PdfPCell(new Paragraph("Komentarz:")));
                table.addCell(new PdfPCell(new Paragraph(task.getComment(),font)));
            }
            table.addCell(new PdfPCell(new Paragraph("Data rozpoczęcia:")));
            table.addCell(new PdfPCell(new Paragraph(task.getStart())));
            table.addCell(new PdfPCell(new Paragraph("Data zakończenia:")));
            table.addCell(new PdfPCell(new Paragraph(task.getEnd())));

            document.add(p);
            document.add(new Paragraph("\n\n"));
            document.add(table);

            document.close();
            Desktop.getDesktop().open(new File(file));
        } catch(Exception e){
            System.out.println("Exception: "+e);
        }
    }//our raports generator 
}