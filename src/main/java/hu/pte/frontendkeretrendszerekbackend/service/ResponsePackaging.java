package hu.pte.frontendkeretrendszerekbackend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class ResponsePackaging {

    private SaveMultipartFileToFolder saveMultipartFileToFolder;
    private SentenceToList sentenceToList;

    private WordFrequency wordFrequency;

    public ResponsePackaging(SaveMultipartFileToFolder saveMultipartFileToFolder, SentenceToList sentenceToList, WordFrequency wordFrequency) {
        this.saveMultipartFileToFolder = saveMultipartFileToFolder;
        this.sentenceToList = sentenceToList;
        this.wordFrequency = wordFrequency;
    }

    public void packaging (MultipartFile multipartFile) throws IOException {

        String tempFileLocation = "/Users/balintszekely/Downloads/studev-main/frontend-keretrendszerek-backend/src/temp/temp.txt";

        try {
            File file = new File("/Users/balintszekely/Downloads/studev-main/frontend-keretrendszerek-backend/src/temp/result.txt");
            FileWriter fw = new FileWriter(file);
            fw.write(""); // a fájl tartalmának törlése
            fw.close();
        } catch (IOException e) {
            System.out.println("Hiba történt: " + e.getMessage());
        }
        //Save to temp.txt
        saveMultipartFileToFolder.saveMultipartFileToFolder(multipartFile);

        //Translate every Sentences
        sentenceToList.sentenceToList(tempFileLocation);

        //Translate every word
        wordFrequency.WordFrequency(tempFileLocation);

    }
}
