package hu.pte.frontendkeretrendszerekbackend.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class SentenceToList {

    public void sentenceToList(String txtPath) throws IOException {

        ArrayList<String> sentences = new ArrayList<>();
        String filePath = txtPath;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                if (line.endsWith(".")) {
                    sentences.add(sb.toString());
                    sb = new StringBuilder();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

       TranslateSentence.translateSentence(sentences);
    }
}