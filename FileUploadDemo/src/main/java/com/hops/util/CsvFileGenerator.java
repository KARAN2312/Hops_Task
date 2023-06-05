package com.hops.util;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import com.hops.model.User;


@Component
public class CsvFileGenerator {
public void writeUsersToCsv(List<User> users, Writer writer) {
try {

CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
for (User user : users) {
printer.printRecord(user.getId(), user.getuName(), user.getImage(),
user.getCode(),user.isActive());
}
} catch (IOException e) {
e.printStackTrace();
}
}
}