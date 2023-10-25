package hu.pte.frontendkeretrendszerekbackend.service;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TranslateWords {
    public static void translateWords(ArrayList<WordFrequency.WordFrequencyObject> wordsList) throws IOException {
        TranslateOptions options = TranslateOptions.newBuilder().setApiKey("AIzaSyAe0LuuN7e2U9YhvXCiEs-jOxvCicsG5ug").build();
        Translate translate = options.getService();

        PrintWriter writer = new PrintWriter(new FileWriter("/Users/balintszekely/Downloads/studev-main/frontend-keretrendszerek-backend/src/temp/result.txt", true));

        for (WordFrequency.WordFrequencyObject wfo : wordsList) {
            Translation translation = translate.translate(wfo.word, Translate.TranslateOption.targetLanguage("hu"));
            writer.println(wfo.word + " - " + translation.getTranslatedText() + " : " + wfo.frequency);
        }
        writer.close();
    }
}