package net.cwmediagroup.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileService {

    public void writeJsonFile(String filePath, String json) {
        File file = new File(filePath);

        try(FileWriter fileWriter = new FileWriter(filePath)){
            fileWriter.write(json);
            fileWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
