package ru.clevertec.cache.util.pdf.impl;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import ru.clevertec.cache.dto.UserResponseDto;
import ru.clevertec.cache.exception.PdfPrinterException;
import ru.clevertec.cache.util.pdf.UserPdfPrinter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class UserPdfPrinterImpl implements UserPdfPrinter {

    private static Long userPdfCounter = 1L;
    private static final String ROOT_PATH = System.getProperty("user.dir");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static final Font RED_BOLD = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.RED);

    @Override
    public void print(UserResponseDto userResponseDto) {
        try {
            Document document = new Document();

            Path filePath = Paths.get(ROOT_PATH, "pdf", "userPdf", "UserPDF" + userPdfCounter + ".pdf");
            Files.createDirectories(filePath.getParent());
            PdfWriter writer =
                    PdfWriter.getInstance(document, new FileOutputStream(String.valueOf(filePath)));

            document.open();
            setBackground(writer);
            addMetaData(document);

            Paragraph paragraph = new Paragraph("\n".repeat(9));
            document.add(paragraph);

            PdfPTable table = createTable(userResponseDto);
            document.add(table);

            document.close();
        } catch (Exception ex) {
            throw new PdfPrinterException("Unable to print PDF document for this user");
        }
        userPdfCounter++;
    }

    private void addMetaData(Document document) {
        document.addAuthor("Clevertec");
        document.addCreator("Yurkova Anastasia");
    }

    private void setBackground(PdfWriter writer) throws IOException {
        PdfReader reader = new PdfReader("pdf/Clevertec_Template.pdf");
        PdfImportedPage page = writer.getImportedPage(reader, 1);
        PdfContentByte directContent = writer.getDirectContent();
        directContent.addTemplate(page, 0, 0);
    }

    private PdfPTable createTable(UserResponseDto userResponseDto) {
        PdfPTable table = new PdfPTable(2);
        addHeader(table);
        addDate(table);
        addTime(table);
        addTableHeader(table);
        addRows(table, userResponseDto);
        return table;
    }

    private void addHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setColspan(2);
        cell.setPhrase(new Phrase("User information", RED_BOLD));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }

    private void addDate(PdfPTable table) {
        String date = LocalDate.now().toString();
        PdfPCell cell = new PdfPCell();
        cell.setColspan(2);
        cell.setPhrase(new Phrase("Date: " + date));
        table.addCell(cell);
    }

    private void addTime(PdfPTable table) {
        String time = LocalTime.now().format(TIME_FORMATTER);
        PdfPCell cell = new PdfPCell();
        cell.setColspan(2);
        cell.setPhrase(new Phrase("Time: " + time));
        table.addCell(cell);
    }


    private void addTableHeader(PdfPTable table) {
        Stream.of("User characteristics", "Value")
                .forEach(headerValue -> {
                    PdfPCell cell = new PdfPCell();
                    cell.setPhrase(new Phrase(headerValue, RED_BOLD));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                });
    }

    private void addRows(PdfPTable table, UserResponseDto userResponseDto) {
        Field[] fields = userResponseDto.getClass().getDeclaredFields();
        List<String> fieldNames = Arrays.stream(fields).map(Field::getName).toList();
        List<Object> fieldValues = getFieldValues(userResponseDto);

        for (int i = 0; i < fieldNames.size(); i++) {
            table.addCell(fieldNames.get(i));
            table.addCell(String.valueOf(fieldValues.get(i)));
        }
    }

    private List<Object> getFieldValues(Object obj) {
        List<Object> fieldValues = new ArrayList<>();
        Class<?> clazz = obj.getClass();

        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(obj);
                fieldValues.add(value);
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException("Failed to get field value");
            }
        }
        return fieldValues;
    }

}
