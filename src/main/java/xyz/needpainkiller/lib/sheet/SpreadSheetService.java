package xyz.needpainkiller.lib.sheet;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.github.sett4.dataformat.xlsx.XlsxGenerator;
import com.github.sett4.dataformat.xlsx.XlsxMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.needpainkiller.api.file.error.FileErrorCode;
import xyz.needpainkiller.api.file.error.FileException;

import java.io.*;
import java.util.Collection;

@Slf4j
@Service
public class SpreadSheetService {

    @Autowired
    private CsvMapper csvMapper;

    public void downloadCsv(Class<?> headerType, Collection<?> collection, HttpServletResponse response) throws FileException {
        response.setHeader("Content-Disposition",
                "attachment; filename=result.csv;");
        response.setHeader("Content-Type", "text/csv; charset=CP949");
        log.info("schema For : {}", collection.getClass());
        CsvSchema schema = csvMapper.schemaFor(headerType).withHeader().withColumnSeparator(',').withLineSeparator("\n");
        ObjectWriter objectWriter = csvMapper.writer(schema);
        try (ServletOutputStream sos = response.getOutputStream();
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(sos, "CP949")) {
            objectWriter.writeValues(outputStreamWriter).writeAll(collection);
        } catch (IOException e) {
            throw new FileException(FileErrorCode.FILE_CSV_PARSE_FAILED, e.getMessage());
        }
    }

    public synchronized void downloadExcel(Class<?> headerType, Collection<?> collection, HttpServletResponse response) throws FileException {
        response.setHeader("Content-Disposition",
                "attachment; filename=result.xlsx;");
        response.setHeader("Content-Type", "application/ms-excel;");
        XlsxMapper mapper = new XlsxMapper();

        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        mapper.enable(XlsxGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        CsvSchema schema = mapper.schemaFor(headerType).withHeader();
        try (ServletOutputStream sos = response.getOutputStream()
        ) {
            File file = File.createTempFile("download-excel", ".xlsx");
            try (FileOutputStream fos = new FileOutputStream(file);
                 FileInputStream fis = new FileInputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(fis);
                 BufferedOutputStream bos = new BufferedOutputStream(sos)
            ) {
                mapper.writer(schema).writeValue(fos, collection);
                byte[] buff = new byte[1024 * 1024];
                int bytesRead;
                while ((bytesRead = bis.read(buff)) != -1) {
                    bos.write(buff, 0, bytesRead);
                }
            }

            if (file.exists()) {
                file.delete();
            }

        } catch (IOException e) {
            log.error(e.getMessage());
            throw new FileException(FileErrorCode.FILE_CSV_PARSE_FAILED, e.getMessage());
        }
    }
}
