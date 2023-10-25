package hu.pte.frontendkeretrendszerekbackend.service;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Service
public class WordFrequency {
    static class WordFrequencyObject implements Comparable<WordFrequencyObject> {
        String word;
        int frequency;

        public WordFrequencyObject(String word, int frequency) {
            this.word = word;
            this.frequency = frequency;
        }

        @Override
        public int compareTo(WordFrequencyObject o) {
            return o.frequency - this.frequency;
        }
    }

    public void WordFrequency(String txtPath) {
        HashMap<String, Integer> wordCount = new HashMap<>();
        ArrayList<WordFrequencyObject> wordsList = new ArrayList<>();

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        try {
            Scanner sc = new Scanner(new File(txtPath));

            while (sc.hasNext()) {
                String line = sc.nextLine();

                Annotation document = new Annotation(line);
                pipeline.annotate(document);

                List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

                for (CoreMap sentence : sentences) {
                    for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                        String word = token.get(CoreAnnotations.TextAnnotation.class);
                        word = word.replaceAll("[^a-zA-Z]+", "").toLowerCase();

                        if (word.length() == 0) {
                            continue;
                        }

                        String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);

                        if (pos.startsWith("N") || pos.startsWith("J") || pos.startsWith("V")) {
                            if (wordCount.containsKey(word)) {
                                wordCount.put(word, wordCount.get(word) + 1);
                            } else {
                                wordCount.put(word, 1);
                            }
                        }
                    }
                }
            }

            for (String key : wordCount.keySet()) {
                wordsList.add(new WordFrequencyObject(key, wordCount.get(key)));
            }

            Collections.sort(wordsList);

            TranslateWords.translateWords(wordsList);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}