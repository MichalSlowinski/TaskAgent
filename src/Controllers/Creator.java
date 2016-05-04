package Controllers;

import Models.Task;
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

public class Creator {

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