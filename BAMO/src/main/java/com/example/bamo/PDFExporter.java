package com.example.bamo;

import javafx.collections.ObservableList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class PDFExporter {

    private static final int MARGIN = 15;
    private static final int CELL_HEIGHT = 30;
    private static final int HEADER_HEIGHT = 30;

    public static void exportToPDF(ObservableList<Student> students, String fileName) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            PDFont font = PDType1Font.HELVETICA;

            int pageHeight = (int) page.getTrimBox().getHeight();
            int pageWidth = (int) page.getTrimBox().getWidth();

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(font, 12);
            contentStream.setLineWidth(1);

            int intX = MARGIN;
            int intY = pageHeight - MARGIN;

            // Write headers
            intY = writeHeaders(contentStream, intX, intY, students);

            for (Student student : students) {
                // Check if we need a new page
                if (intY - CELL_HEIGHT < MARGIN) {
                    contentStream.stroke();
                    contentStream.close();
                    page = new PDPage();
                    document.addPage(page);
                    contentStream = new PDPageContentStream(document, page);
                    contentStream.setFont(font, 12);
                    contentStream.setLineWidth(1);
                    intX = MARGIN;
                    intY = pageHeight - MARGIN;

                    // Write headers on new page
                    intY = writeHeaders(contentStream, intX, intY, students);
                }

                // Write student data
                intX = writeStudentData(contentStream, intX, intY, student);

                intX = MARGIN;
                intY -= CELL_HEIGHT;
            }

            // Ensure the final page is stroked
            contentStream.stroke();
            contentStream.close();

            document.save(fileName + ".pdf");
            System.out.println("PDF saved successfully!");
        } catch (IOException e) {
            System.err.println("Error exporting to PDF: " + e.getMessage());
        }
    }

    private static int writeHeaders(PDPageContentStream contentStream, int intX, int intY, ObservableList<Student> students) throws IOException {
        int cellWidth = 100;

        contentStream.addRect(intX, intY, cellWidth - 40, -HEADER_HEIGHT);
        contentStream.beginText();
        contentStream.newLineAtOffset(intX + 10, intY - HEADER_HEIGHT + 10);
        contentStream.showText("ID");
        contentStream.endText();
        intX += (cellWidth - 40);

        contentStream.addRect(intX, intY, cellWidth - 25, -HEADER_HEIGHT);
        contentStream.beginText();
        contentStream.newLineAtOffset(intX + 10, intY - HEADER_HEIGHT + 10);
        contentStream.showText("Vardas");
        contentStream.endText();
        intX += (cellWidth - 25);

        contentStream.addRect(intX, intY, cellWidth - 5, -HEADER_HEIGHT);
        contentStream.beginText();
        contentStream.newLineAtOffset(intX + 10, intY - HEADER_HEIGHT + 10);
        contentStream.showText("Pavarde");
        contentStream.endText();
        intX += (cellWidth - 5);

        contentStream.addRect(intX, intY, cellWidth - 70, -HEADER_HEIGHT);
        contentStream.beginText();
        contentStream.newLineAtOffset(intX + 10, intY - HEADER_HEIGHT + 10);
        contentStream.showText("Gr");
        contentStream.endText();
        intX += (cellWidth - 70);

        // Assuming all students have the same dates
        Student stu = students.get(0);
        for (Date date : stu.getDateList()) {
            contentStream.addRect(intX, intY, cellWidth - 25, -HEADER_HEIGHT);
            contentStream.beginText();
            contentStream.newLineAtOffset(intX + 10, intY - HEADER_HEIGHT + 10);
            contentStream.showText(date.getDate());
            contentStream.endText();
            intX += (cellWidth - 25);
        }

        intX = MARGIN;
        intY -= HEADER_HEIGHT;

        return intY;
    }

    private static int writeStudentData(PDPageContentStream contentStream, int intX, int intY, Student student) throws IOException {
        int cellWidth = 100;

        contentStream.addRect(intX, intY, cellWidth - 40, -CELL_HEIGHT);
        contentStream.beginText();
        contentStream.newLineAtOffset(intX + 10, intY - CELL_HEIGHT + 10);
        contentStream.showText(Integer.toString(student.getId()));
        contentStream.endText();
        intX += (cellWidth - 40);

        contentStream.addRect(intX, intY, cellWidth - 25, -CELL_HEIGHT);
        contentStream.beginText();
        contentStream.newLineAtOffset(intX + 10, intY - CELL_HEIGHT + 10);
        contentStream.showText(student.getName());
        contentStream.endText();
        intX += (cellWidth - 25);

        contentStream.addRect(intX, intY, cellWidth - 5, -CELL_HEIGHT);
        contentStream.beginText();
        contentStream.newLineAtOffset(intX + 10, intY - CELL_HEIGHT + 10);
        contentStream.showText(student.getSurname());
        contentStream.endText();
        intX += (cellWidth - 5);

        contentStream.addRect(intX, intY, cellWidth - 70, -CELL_HEIGHT);
        contentStream.beginText();
        contentStream.newLineAtOffset(intX + 10, intY - CELL_HEIGHT + 10);
        contentStream.showText(Integer.toString(student.getGroup()));
        contentStream.endText();
        intX += (cellWidth - 70);

        for (Date date : student.getDateList()) {
            contentStream.addRect(intX, intY, cellWidth - 25, -CELL_HEIGHT);
            contentStream.beginText();
            contentStream.newLineAtOffset(intX + 10, intY - CELL_HEIGHT + 10);
            contentStream.showText(date.isAttended() ? "+" : " ");
            contentStream.endText();
            intX += (cellWidth - 25);
        }

        return intX;
    }
}
