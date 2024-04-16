package xyz.needpainkiller.lib.sheet;

import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public interface CommaSeparatedValue {

    CsvSchema makeSchema();
}
