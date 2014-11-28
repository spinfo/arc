package de.uni_koeln.spinfo.arc.tagger;

import de.uni_koeln.spinfo.arc.common.DictUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author geduldia
 *         <p/>
 *         Das ist die 'Mutter'-Klasse zu den Idiom-spezifischen Taggern
 *         Im Konstrukter MUSS eine VF-Liste übergeben werden und der CollectionName (für die Ausgabe in der .txt-datei)
 */
public abstract class POSMatcher {


    /**
     * @param fullForms
     * @param collectionName
     */

    public POSMatcher(Map<String, TreeSet<String>> fullForms, String collectionName) {
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
    private String collectionName = null;
    boolean tagAdverbs, tagInfinitives, tagNames, tagIndImperfect;
    boolean currentDot;
    boolean previousDot;
    int distanceToDot;

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
     * @param tokens List of tokens to match
     * @return Map of tokens and set of tags
     */
    public Map<String, Set<String>> match(List<String> tokens) {
        Map<String, Set<String>> matches = new LinkedHashMap<>();
        this.currentDot = false;
        this.previousDot = false;
        for (String nextToken : tokens) {
            if (nextToken == null) continue;
            Set<String> tags = getPOS(nextToken);
            if (tags != null) {
                matches.put(nextToken, tags);
            }
        }

        return matches;
    }


    public Set<String> getPOS(String token) {

        token = prepareToken(token);

        if (token == null) {
            updateDots();
            return null;
        }


        Set<String> posSet = new TreeSet<String>();


        //Base case
        if (fullForms.containsKey(token)) {

            posSet = fullForms.get(token);

            if (checkIfOneLetterAndNoun(token, posSet)) {

                updateDots();
                return null;

            }

            updateDots();

            return posSet;


        }
        //If the token starts with uppercase
        else if (Character.isUpperCase(token.charAt(0))) {


            // Go lower case
            String toLowerCase = token.toLowerCase();

            //Check if it's in the db

            if (fullForms.containsKey(toLowerCase)) {

                posSet = fullForms.get(toLowerCase);

                if (checkIfOneLetterAndNoun(toLowerCase, posSet)) {

                    updateDots();

                    return null;

                }

                updateDots();

                return posSet;


            }

            // if it's uppercase and was not found in the db
            else {


                if (tagNames) {


                    //If there is only one letter and wasn't already tagged || if it's a roman numeral
                    if (token.length() == 1 || token.matches("^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$")) {
                        updateDots();
                        return null;

                    }


                    if (currentDot && !previousDot) {

                        posSet.add("NN_P" + " " + "current");
                        currentDot = false;
                        previousDot = true;
                        return posSet;

                    }

                    if (currentDot && previousDot) {

                        posSet.add("NN" + " " + "current and previous");
                        currentDot = false;
                        previousDot = true;
                        return posSet;

                    }
                    if (!currentDot && previousDot) {

                        posSet.add("NN" + " " + "previous");
                        previousDot = false;
                        return posSet;

                    }

                    if (!currentDot && !previousDot) {

                        posSet.add("NN_P" + " " + "nothing");
                        return posSet;
                    }


                }
            }


        }


        //If lowercase and not found
        else

        {


            if (tagNames) {

                posSet.add("NN_3");
                updateDots();
                return posSet;


            }


            if (tagInfinitives) {
                if (token.endsWith("ir") || token.endsWith("ar")
                        || token.endsWith("er")) {

                    posSet.add("V_GVRB");
                    updateDots();
                    return posSet;
                }
            }
            if (tagAdverbs) {
                if (isAdverb(token)) {
                    posSet.add("ADV");
                    updateDots();
                    return posSet;
                }
            }
            if (tagIndImperfect) {
                if (isIndImperfect(token)) {
                    posSet.add("V_GVRB");
                    updateDots();
                    return posSet;
                }
            }

        }


        return null;
    }


    private String prepareToken(String token) {

        //If there is actually no token
        if (token.length() < 1) {
            updateDots();
            return null;
        }
        //Remove all but chars
        token = removeEverythingButChars(token);

        //If there is no token left
        if (token.length() < 1) {
            updateDots();
            return null;
        }

        return token;
    }


    private String removeEverythingButChars(String token) {


        Pattern p = Pattern.compile("[.!?]$");
        Matcher m = p.matcher(token);


        if (m.find()) {

            currentDot = true;

        } else {

            currentDot = false;
        }

        //Remove everything but letters
        token = token.replaceAll("\\P{L}", "");

        // Remove whitespaces on the borders
        token = token.replaceAll("^\\s+|\\s+$", "");

        return token;

    }


    void updateDots() {

        if (currentDot) {

            currentDot = false;
            previousDot = true;

        } else {
            currentDot = false;
            previousDot = false;

        }


    }

    /**
     * Recall test: legt eine Textdatei an die Auskunft über das Tagging-Ergebnis gibt
     *
     * @param tokens
     * @param output =  number of output words in the generated file.
     * @throws IOException
     */
    public void testRecall(String filesName, List<String> tokens, int output) throws IOException {


        String date = DictUtils.getISO8601StringForCurrentDate();
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
        int countSingleLetters = 0;

        Set<String> adverbs = new HashSet<String>();
        Set<String> names = new HashSet<String>();
        Set<String> infinitives = new HashSet<String>();
        Set<String> indImperfectForms = new HashSet<String>();

        Map<String, Integer> missedTypes = new HashMap<String, Integer>();
        Map<String, Integer> allMissedTypes = new HashMap<String, Integer>();

        currentDot = false;

        for (String nextToken : tokens) {
            nextToken = prepareToken(nextToken);
            if (nextToken == null)
                continue;

            totalNumberOfTokens++;


            //Base case
            if (fullForms.containsKey(nextToken)) {

                TreeSet<String> pos_tags_set = fullForms.get(nextToken);

                if (checkIfOneLetterAndNoun(nextToken, pos_tags_set)) {
                    countSingleLetters++;
                    continue;

                }
                //1
                StringBuilder builder = new StringBuilder();
                builder.append(nextToken + "\t");
                for (String s : pos_tags_set) {
                    builder.append(s + "_1");
                    builder.append(" ");
                }
                builder.append("\n");
                bw_tags.append(builder.toString());

                fullFormMatches++;
                allMatches++;


            }
            //If the token starts with uppercase
            else if (Character.isUpperCase(nextToken.charAt(0))) {

                // Go lower case
                String uct = nextToken.toLowerCase();

                //See if it's in the db

                if (fullForms.containsKey(uct)) {


                    TreeSet<String> pos_tags_set = fullForms.get(uct);


                    if (checkIfOneLetterAndNoun(uct, pos_tags_set)) {
                        countSingleLetters++;
                        continue;

                    }


                    //1
                    StringBuilder builder = new StringBuilder();
                    builder.append(nextToken + "\t");
                    for (String s : pos_tags_set) {
                        builder.append(s + "_1_b");
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


                        //If there is only one letter and wasn't already tagged
                        // Or if it's a roman numeral
                        if (nextToken.length() == 1 || nextToken.matches("^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$")) {

                            continue;

                        }

                        //2
                        bw_tags.append(nextToken + "\t" + "NN_P_2" + "\n");
                        names.add(nextToken);
                        allMatches++;
                        continue;


                    }


                }


            }

            //Then, do some after tagging
            else {


                allMissedTokens++;
                Integer count = allMissedTypes.get(nextToken);
                if (count == null) {
                    count = 0;
                }
                count++;
                allMissedTypes.put(nextToken, count);


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
        bw.write("missedTypes (ohne nachtaggen) : " + allMissedTypes.size() + "\r\n");
        bw.write("singleLetters: " + countSingleLetters + "\r\n");

        Iterator<String> iterator;
        if (tagNames) {
            bw.write("\r\nnachgetaggte Eigennamen: " + names.size() + "\r\n");
            iterator = names.iterator();
            for (int i = 0; i < output && i < names.size(); i++) {
                bw.write(iterator.next() + "\r\n");
            }
        }
        if (tagInfinitives) {
            bw.write("\r\nnachgetaggte Infinitive: " + infinitives.size() + "\r\n");
            iterator = infinitives.iterator();
            for (int i = 0; i < output && i < infinitives.size(); i++) {
                bw.write(iterator.next() + "\r\n");
            }
        }
        if (tagAdverbs) {
            bw.write("\r\nnachgetaggte Adverbien: " + adverbs.size() + "\r\n");
            iterator = adverbs.iterator();
            for (int i = 0; i < output && i < adverbs.size(); i++) {
                bw.write(iterator.next() + "\r\n");
            }
        }
        if (tagIndImperfect) {
            bw.write("\r\nnachgetaggte Indikativ Imperfekt Formen: " + indImperfectForms.size() + "\r\n");
            iterator = indImperfectForms.iterator();
            for (int i = 0; i < output && i < indImperfectForms.size(); i++) {
                bw.write(iterator.next() + "\r\n");
            }
        }
        bw.write("\r\n\nmissedTypes: " + missedTypes.size() + "\r\n");
        iterator = missedTypes.keySet().iterator();


        for (int i = 0; i < output && i < missedTypes.size(); i++) {
            bw.write(iterator.next() + "\r\n");
        }
        bw.close();


    }


    private boolean checkIfOneLetterAndNoun(String s, Set<String> pos) {
        if (s.length() == 1 && pos.size() == 1 && pos.contains("NN")) {
            return true;
        }
        return false;
    }


    abstract boolean isAdverb(String token);

    abstract boolean isIndImperfect(String token);

}
