import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.*;

/**
 * Created by franciscomondaca on 7/16/14.
 */
public class Tarantino {

    private List<String> lemmata;
    private Set<String> pos;


    public List<String> getLemmata(String file) throws IOException {


        Set<String> pos = new HashSet<String>();

        pos.add("m");
        pos.add("f");
        pos.add("/");
        pos.add("art");
        pos.add("adv");
        pos.add("adj");
        pos.add("inter");
        pos.add("conj");
        pos.add("prep");
        pos.add("tr");
        pos.add("int");


        List<String> lemmata = new ArrayList<String>();

        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, "UTF8");
        LineNumberReader reader = new LineNumberReader(isr);


        String currentLine;

        while ((currentLine = reader.readLine()) != null) {

            String delims = " \n\t<E></E>";

            StringTokenizer tokenizer = new StringTokenizer(currentLine, delims);

            while (tokenizer.countTokens() > 3) {


                String lemma = tokenizer.nextToken();
                String li2 = tokenizer.nextToken();
                String li3 = tokenizer.nextToken();


                StringBuilder sb = new StringBuilder();

                sb.append(lemma);
                sb.append("\t");
                sb.append(li2);
                sb.append("\t");
                sb.append(li3);

                lemmata.add(sb.toString());
                break;
            }


        }

        return lemmata;
    }


    public List<String> cleanLemmata(List<String> lemmata) {

        List<String> cleanedLemmata = new ArrayList<String>();

        Set<String> pos = new HashSet<String>();

        pos.add("m");
        pos.add("f");
        pos.add("/");
        pos.add("mf");
        pos.add("art");
        pos.add("adv");
        pos.add("adj");
        pos.add("interj");
        pos.add("conj");
        pos.add("prep");
        pos.add("tr");
        pos.add("fr");
        pos.add("int");

        Set<String> wS = new HashSet<String>();

        wS.add("➀");
        wS.add("©");


        Set<String> suffix = new HashSet<String>();


        for (String s : lemmata) {

            StringTokenizer tokenizer = new StringTokenizer(s);

            String t1 = tokenizer.nextToken();
            String t2 = tokenizer.nextToken();
            String t3 = tokenizer.nextToken();

            StringBuilder builder = new StringBuilder();


            //Normal case (1)

            if (pos.contains(t2) && !t1.endsWith("(a)")) {

                builder.append(t1.replaceAll("\\d", ""));
                builder.append("$");
                builder.append(t2);
                builder.append("(1)");

                cleanedLemmata.add(builder.toString());

                continue;

            }

            //If there are symbols like ➀

            if (wS.contains(t2) && pos.contains(t3)) {

                builder.append(t1.replaceAll("\\d", ""));
                builder.append("$");
                builder.append(t3);
                builder.append("(2)");

                cleanedLemmata.add(builder.toString());
                continue;
            }


            //If there are commata
            //Like: bagnruaiọ,	-ada	adj

            if (t1.endsWith(",") && !pos.contains(t3)) {

                //First token

                //Clean first token

                t1 = t1.replaceAll(",", "").replaceAll("\\d", "");

                if (t1.endsWith("o")) {

                    //Remove 'o'
                    t1 = t1.substring(0, t1.length() - 1);

                    //Remove '-' from second Token
                    t2 = t2.substring(1);

                    //Join both
                    StringBuilder joinToken = new StringBuilder();
                    joinToken.append(t1);
                    joinToken.append(t2);
                    builder.append(joinToken.toString());

                    builder.append("$");
                    builder.append(t3);
                    builder.append("4");

                    cleanedLemmata.add(builder.toString());
                    continue;

                } else if (pos.contains(t3)) {

                    //Take the first token and add it to the list
                    builder.append(t1);
                    builder.append("$");
                    builder.append(t3);
                    builder.append("5");

                    cleanedLemmata.add(builder.toString());
                    continue;
                }


            }


            //For verbs with () as second tokens
            //Like: arcunar	(-una)	tr

            if (t2.startsWith("(") && t2.endsWith(")")) {
                //Take the first token and add it to the list
                builder.append(t1.replaceAll(",", "").replaceAll("\\d", ""));
                builder.append("$");

                if (pos.contains(t3)) {

                    builder.append(t3);
                    builder.append("6");
                    cleanedLemmata.add(builder.toString());


                } else {

                    continue;
                }


            }

            //For feminine nouns included in masc. ones
            //Like: batarlùnz(a)	m	(f)


            if (t1.endsWith("(a)")) {

                t1 = t1.replaceAll("\\(a\\)", "");

                builder.append(t1);
                builder.append("$");
                builder.append(t2);
                builder.append("7");
                //Add masculine token
                cleanedLemmata.add(builder.toString());


                //builder.setLength(0);
                StringBuilder sT = new StringBuilder();


                sT.append(t1);
                sT.append("a");
                sT.append("$");
                sT.append("f");
                sT.append("8");
                //Add feminine token
                cleanedLemmata.add(sT.toString());
                continue;

            }

        }

        return cleanedLemmata;
    }


}