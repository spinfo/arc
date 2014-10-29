package de.uni_koeln.spinfo.arc.tagger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author geduldia
 *         <p/>
 *         Das ist die 'Mutter'-Klasse zu den Idiom-spezifischen Taggern
 *         Im Konstrukter MUSS eine VF-Liste übergeben werden und der CollectionName (für die Ausgabe in der .txt-datei)
 */
public abstract class ARCTagger {


    /**
     * @param fullForms
     * @param collectionName
     */

    public ARCTagger(Map<String, TreeSet<String>> fullForms, String collectionName) {
        this.fullForms = fullForms;
        this.collectionName = collectionName;
    }

    /**
     * Klassenvariablen:
     * - VollFormen-Liste
     * - ein Boolean der sich merkt, ob der vorherige Token am Satzende stand
     * - der Name der zu Grunde gelegten Collection
     * - vier booleans die speichern, welche Wortarten 'per Hand' nachgetaggt werden sollen
     * <p/>
     * Es können nachgetagg werden:
     * 1. Namen = Großgeschrieben, aber nicht am Satzanfang
     * 2. Infinitivverben = Endung auf -ar, -er oder -ir
     * 3. Adverbien = Wörter mit der Idiomspezifischen Adverbendung (wird in der entsprechenden Subklasse definiert)
     * 4. Indikativ Imperfekt Verbformen = Wörter mit der idiomspezifischen IndImperfekt-Endung (wird in der entsprechenden Subklasse definiert)
     * <p/>
     * Die Boolean-Werte werden über die Methode configure() festgelegt
     */
    private Map<String, TreeSet<String>> fullForms = new TreeMap<String, TreeSet<String>>();
    private boolean endOfSentence = false;
    private String collectionName = null;
    boolean tagAdverbs, tagInfinitives, tagNames, tagIndImperfect;


    /**
     * legt fest welche Wortarten nachgetaggt weren sollen, wenn sie in der VollFormenliste nicht vorkommen
     * Reigenfolge: Namenwörter, Infinitive, Adverben, IndikativImperfektFormen
     *
     * @param testKonfig {tagNames, tagInfinitives, tagAdverbs, tagIndImperfektForms}
     */
    public void configure(Boolean[] testKonfig) {
        this.tagNames = testKonfig[0];
        this.tagInfinitives = testKonfig[1];
        this.tagAdverbs = testKonfig[2];
        this.tagIndImperfect = testKonfig[3];
    }


    /**
     * taggt eine ganze Liste von Tokens
     *
     * @param tokens List of tokens to tag
     * @return Map of tokens and set of tags
     */
    public Map<String, Set<String>> tag(List<String> tokens) {
        Map<String, Set<String>> taggings = new HashMap<String, Set<String>>();
        endOfSentence = false;
        for (String nextToken : tokens) {
            nextToken = prepareToken(nextToken);
            if (nextToken == null) continue;
            Set<String> tags = tag(nextToken);
            if (tags != null) {
                taggings.put(nextToken, tags);
            }
        }

        return taggings;
    }

    /**
     * taggt einen einzelnen Token
     *
     * @param token to tag
     * @return set of tags
     */
    public Set<String> tag(String token) {
        token = prepareToken(token);
        if (token == null) return null;
        Set<String> toReturn = new TreeSet<String>();
        if (fullForms.containsKey(token)) {
            return fullForms.get(token);
        }
        if (tagNames) {
            String first = token.substring(0, 1);
            String[] split3 = first.split("[A-Z]");
            if (split3.length == 0) {
                toReturn.add("NN_P");
                return toReturn;
            }
        }
        if (tagInfinitives) {
            if (token.endsWith("ar") || token.endsWith("er") || token.endsWith("ir")) {
                toReturn.add("V_GVRB");
                return toReturn;
            }
        }
        if (tagAdverbs) {
            if (isAdverb(token)) {
                toReturn.add("ADV");
                return toReturn;
            }
        }

        if (tagIndImperfect) {
            if (isIndImperfect(token)) {
                toReturn.add("V_GVRB");
                return toReturn;
            }

        }
        return null;
    }

    /**
     * Recall test: legt eine Textdatei an die Auskunft über das Tagging-Ergebnis gibt
     *
     * @param tokens
     * @param words  =  number of example words in output file
     * @throws IOException
     */
    public void testRecall(String filesName, List<String> tokens, int words) throws IOException {


        DateFormat df = new SimpleDateFormat("yyyyMMdd");

        String date = df.format(new Date());


        StringBuilder sb = new StringBuilder();
        sb.append(filesName + "_");

        sb.append(collectionName + " (");
        sb.append(tagNames + "-");
        sb.append(tagInfinitives + "-");
        sb.append(tagAdverbs + "-");
        sb.append(tagIndImperfect + ")_" + date);

        File file = new File("../arc.data/output/" + sb.toString());

        File pos_tags = new File("../arc.data/output/" + date + "-pos_tags.txt");
        FileWriter fw_pos_tags = new FileWriter(pos_tags);
        BufferedWriter bw_tags = new BufferedWriter(fw_pos_tags);


        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);

        int fullFormMatches = 0;
        int missedTokens = 0;
        int allMatches = 0;
        int allMissedTokens = 0;
        int totalNumberOfTokens = 0;

        Set<String> adverbs = new HashSet<String>();
        Set<String> names = new HashSet<String>();
        Set<String> infinitives = new HashSet<String>();
        Set<String> indImperfectForms = new HashSet<String>();

        Map<String, Integer> missedTypes = new HashMap<String, Integer>();
        Map<String, Integer> allMissedTypes = new HashMap<String, Integer>();

        endOfSentence = false;

        for (String nextToken : tokens) {
            nextToken = prepareToken(nextToken);
            if (nextToken == null)
                continue;

            totalNumberOfTokens++;

            if (fullForms.containsKey(nextToken)) {

                StringBuilder builder = new StringBuilder();

                TreeSet<String> pos_tags_set = fullForms.get(nextToken);
                //1
                builder.append(nextToken + "\t");
                for (String s : pos_tags_set) {
                    builder.append(s + "_1");
                    builder.append(" ");
                }
                builder.append("\n");
                bw_tags.append(builder.toString());

                fullFormMatches++;
                allMatches++;


            } else {


                allMissedTokens++;
                Integer count = allMissedTypes.get(nextToken);
                if (count == null) {
                    count = 0;
                }
                count++;
                allMissedTypes.put(nextToken, count);


                if (tagNames) {
                    String first = nextToken.substring(0, 1);
                    String[] split3 = first.split("[A-Z]");
                    if (split3.length == 0) {
                        //2
                        bw_tags.append(nextToken + "\t" + "NN_P_2" + "\n");
                        names.add(nextToken);
                        allMatches++;
                        continue;
                    }
                }

                if (tagInfinitives) {
                    if (nextToken.endsWith("ir") || nextToken.endsWith("ar")
                            || nextToken.endsWith("er")) {
                        //3
                        infinitives.add(nextToken);
                        allMatches++;
                        bw_tags.append(nextToken + "\t" + "G_GVRB_3" + "\n");
                        continue;
                    }
                }
                if (tagAdverbs) {
                    if (isAdverb(nextToken)) {
                        adverbs.add(nextToken);
                        //4
                        bw_tags.append(nextToken + "\t" + "ADV_4" + "\n");

                        allMatches++;
                        continue;
                    }
                }
                if (tagIndImperfect) {
                    if (isIndImperfect(nextToken)) {
                        indImperfectForms.add(nextToken);
                        //5
                        bw_tags.append(nextToken + "\t" + "G_GVRB_5" + "\n");
                        allMatches++;
                        continue;
                    }
                }
                missedTokens++;
                count = missedTypes.get(nextToken);
                if (count == null) {
                    count = 0;
                }
                count++;
                missedTypes.put(nextToken, count);
            }
            endOfSentence = false;
        }

        bw_tags.close();


        bw.write("\r\n");

        bw.write("Datum                         : " + date + "\n");
        bw.write("Input file                    : " + filesName + "\n");
        bw.write("DBCollection                  : " + collectionName + "\r\n" + "\n");
        bw.write("total number of Tokens        : " + totalNumberOfTokens + "\r\n");
        bw.write("matches mit Vollformen-Liste  : " + fullFormMatches + "\r\n");
        bw.write("matches mit VFL & nachgetaggte: " + allMatches + "\r\n");
        bw.write("missedTokens (mit nachtaggen) : " + missedTokens + "\r\n");
        bw.write("missedTypes (mit nachtaggen)  : " + missedTypes.size() + "\r\n");
        bw.write("missedTokens(ohne nachtaggen) : " + allMissedTokens + "\r\n");
        bw.write("missedTypes (ohne nachtaggen) : " + allMissedTypes.size() + "\r\n" + "\n");


        Iterator<String> iterator;
        if (tagNames) {
            bw.write("\r\nnachgetaggte Eigennamen: " + names.size() + "\r\n");
            iterator = names.iterator();
            for (int i = 0; i < words && i < names.size(); i++) {
                bw.write(iterator.next() + "\r\n");
            }
        }
        if (tagInfinitives) {
            bw.write("\r\nnachgetaggte Infinitive: " + infinitives.size() + "\r\n");
            iterator = infinitives.iterator();
            for (int i = 0; i < words && i < infinitives.size(); i++) {
                bw.write(iterator.next() + "\r\n");
            }
        }
        if (tagAdverbs) {
            bw.write("\r\nnachgetaggte Adverbien: " + adverbs.size() + "\r\n");
            iterator = adverbs.iterator();
            for (int i = 0; i < words && i < adverbs.size(); i++) {
                bw.write(iterator.next() + "\r\n");
            }
        }
        if (tagIndImperfect) {
            bw.write("\r\nnachgetaggte Indikativ Imperfekt Formen: " + indImperfectForms.size() + "\r\n");
            iterator = indImperfectForms.iterator();
            for (int i = 0; i < words && i < indImperfectForms.size(); i++) {
                bw.write(iterator.next() + "\r\n");
            }
        }
        bw.write("\r\n\nmissedTypes: " + missedTypes.size() + "\r\n");
        iterator = missedTypes.keySet().iterator();

//        // Iterate à la keySet()
//        for (String key : missedTypes.keySet()) {
//
//            Integer value = missedTypes.get(key);
//
//            bw.append(key + " " + value + "\n");
//
//        }


        for (int i = 0; i < words && i < missedTypes.size(); i++) {
            bw.write(iterator.next() + "\r\n");
        }
        bw.close();


    }

    private String prepareToken(String token) {
        if (token.length() < 1)
            return null;

        if (tagNames) {
            if (endOfSentence) {
                token = token.toLowerCase();
            }
            char last = token.charAt(token.length() - 1);
            if (last == '.' || last == '!' || last == '?') {
                endOfSentence = true;
            }
        } else {
            token.toLowerCase();
        }

        // Entfernung aller Sonderzeichen
        String[] split = token.split("[\\W]");
        if (split.length < 1) {
            return null;
        }
        token = split[0];
        if (token.length() < 1)
            return null;

        // Entfernung aller Zahlen
        String[] split2 = token.split("[0-9]");
        if (split2.length < 1) {
            return null;
        }
        token = split2[0];
        if (token.length() < 1)
            return null;

        // Entfernung aller Einzelkonsonanten
        if (token.length() == 1) {
            if (!(token.equals("a") || token.equals("e") || token.equals("i")
                    || token.equals("o") || token.equals("u"))) {
                return null;
            }
        }
        return token;
    }

    abstract boolean isAdverb(String token);

    abstract boolean isIndImperfect(String token);

}
