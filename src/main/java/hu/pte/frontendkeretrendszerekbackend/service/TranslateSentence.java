package hu.pte.frontendkeretrendszerekbackend.service;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class TranslateSentence {
    public static void translateSentence(ArrayList<String> sentenceList) throws IOException {
        TranslateOptions options = TranslateOptions.newBuilder().setApiKey("AIzaSyAe0LuuN7e2U9YhvXCiEs-jOxvCicsG5ug").build();
        Translate translate = options.getService();
        PrintWriter writer = new PrintWriter(new FileWriter("/Users/balintszekely/Downloads/studev-main/frontend-keretrendszerek-backend/src/temp/result.txt", true));
        for (String sl : sentenceList) {
            Translation translation = translate.translate(sl, Translate.TranslateOption.targetLanguage("hu"));
            writer.println(sl);
            writer.println();
            writer.println(translation.getTranslatedText());
            writer.println("----------------------------------");
        }

        writer.println("##########################");
        writer.close();
    }
}
