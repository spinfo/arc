package de.uni_koeln.spinfo.arc.generator;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.net.UnknownHostException;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created with IntelliJ IDEA.
 * User: andreasvogt
 * Date: 29/7/14
 * Time: 2:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class Vallader_VFGenerator {

    Map<String, TreeSet<String>> vollForms = new TreeMap<String, TreeSet<String>>();
    int count;

    public int getNumberOfVFEntries() {
        return vollForms.keySet().size();

    }

    public int getNumberOfDBEntries() {
        return count;
    }

    public Map<String, TreeSet<String>> generateFullforms(DBCollection collection)
            throws UnknownHostException {
        DBCursor cursor = collection.find();
        count = 0;
        for (DBObject doc : cursor) {
            count++;

            String entry = (String) doc.get("entry");
            System.out.println();
            DBObject pos = (BasicDBObject) doc.get("pos");
            String eagles_pos = (String) pos.get("eagles_pos");

            System.out.println("Entry: " + entry + "| POS: " + entry + "| eaglesPOS: " + eagles_pos);

            if (eagles_pos != null) {
                if (eagles_pos.equals("V_GVRB")) {
                    generateVerbForms(entry);
                    continue;
                }
                if (eagles_pos.equals("NN")) {
                    generateSubstantiveForms(entry);
                    continue;
                }
                if (eagles_pos.equals("ADJ")) {
                    generateAdjectiveForms(entry);
                    continue;
                }
                addVF(entry, eagles_pos);
                addIrregularVerbs();
//				addPrepositions();
                addPrep_Art();
                addPronouns();
//				addConjunctions();
                addArticles();
//				addAdjectives();

            }
        }
        return vollForms;
    }

    private void generateSubstantiveForms(String entry) {

        // maskulin singular
        addVF(entry, "NN");

        char lastChar = entry.charAt(entry.length() - 1);
        String endung = "";
        int length = entry.length();
        if (length > 2) {
            endung = entry.substring(entry.length() - 2);
        }

        //maskulin plural
        // unregelmäßige Formen nach Endung
        if (endung.compareTo("ur") == 0) {
            addVF(entry.substring(0, entry.length() - 2) + "uors", "NN");
        } else {
            switch (lastChar) {
                case 'à':
                    addVF(entry.substring(0, entry.length() - 1) + "ats", "NN");
                    break;
                case 'è':
                    addVF(entry.substring(0, entry.length() - 1) + "els", "NN");
                    addVF(entry.substring(0, entry.length() - 1) + "es", "NN");
                    break;
                case 'i':
                    addVF(entry.substring(0, entry.length() - 1) + "its", "NN");
                    break;
                case 'ü':
                    addVF(entry.substring(0, entry.length() - 1) + "üts", "NN");
                    break;
                default:
                    // regelmäßige Form maskulin plural
                    if (lastChar != 's' || endung.compareTo("ss") != 0) {
                        addVF(entry + "s", "NN");
                    }
            }
        }

        if (lastChar != 'a') {
            // augmentativ m
            addVF(entry + "un", "NN");
            // diminutiv m
            addVF(entry + "in", "NN");
            addVF(entry + "et", "NN");
            // pegiorativ m
            addVF(entry + "atsch", "NN");
        }

        if (!endsWithVocal(entry)) {
            // weitere autmentative Suffixe
            addVF(entry + "ada", "NN");
            addVF(entry + "agl", "NN");
            addVF(entry + "ezza", "NN");
            addVF(entry + "ezzas", "NN");
            addVF(entry + "ischem", "NN");
            // weitere diminutive Suffixe
            addVF(entry + "ical", "NN");
            addVF(entry + "ot", "NN");
            addVF(entry + "ottel", "NN");
            // weitere abwertende Suffixe
            addVF(entry + "aster", "NN");
            addVF(entry + "astra", "NN");
        }
        // abwertendes Suffix
        addVF(entry + "mainta", "NN");

        // feminin
        // Unregelmäßige Formen nach Endung
        String femEntry = "";
        if (length > 6) {
            endung = entry.substring(entry.length() - 6);

            switch (endung) {
                case "ainder":
                    femEntry = entry.substring(0, entry.length() - 6) + "aindra";
                    break;
                case "aister":
                    femEntry = entry.substring(0, entry.length() - 6) + "aistra";
                    break;
            }
        }

        if (length > 5 && femEntry.compareTo("") == 0) {
            endung = entry.substring(entry.length() - 5);

            switch (endung) {
                case "iccal":
                    femEntry = entry.substring(0, entry.length() - 5) + "icla";
                    break;
                case "aivel":
                    femEntry = entry.substring(0, entry.length() - 5) + "aivla";
                    break;
                case "immel":
                    femEntry = entry.substring(0, entry.length() - 5) + "imla";
                    break;
                case "ottel":
                    femEntry = entry.substring(0, entry.length() - 5) + "otla";
                    break;
                case "üttel":
                    femEntry = entry.substring(0, entry.length() - 5) + "ütla";
                    break;
                case "ossem":
                    femEntry = entry.substring(0, entry.length() - 5) + "osma";
                    break;
                case "igher":
                    femEntry = entry.substring(0, entry.length() - 5) + "igra";
                    break;
                case "aster":
                    femEntry = entry.substring(0, entry.length() - 5) + "astra";
                    break;
                case "ecter":
                    femEntry = entry.substring(0, entry.length() - 5) + "ectura";
                    break;
                case "ember":
                    femEntry = entry.substring(0, entry.length() - 5) + "embra";
                    break;
                case "ister":
                    femEntry = entry.substring(0, entry.length() - 5) + "istra";
                    break;
                case "oster":
                    femEntry = entry.substring(0, entry.length() - 5) + "ostra";
                    break;
                case "utter":
                    femEntry = entry.substring(0, entry.length() - 5) + "utra";
                    break;
                case "izzer":
                    femEntry = entry.substring(0, entry.length() - 5) + "izra";
                    break;
                case "ander":
                    femEntry = entry.substring(0, entry.length() - 5) + "andra";
                    break;
            }
        }

        if (length > 4 && femEntry.compareTo("") == 0) {
            endung = entry.substring(entry.length() - 4);

            switch (endung) {
                case "abel":
                    femEntry = entry.substring(0, entry.length() - 4) + "abla";
                    break;
                case "öbel":
                    femEntry = entry.substring(0, entry.length() - 4) + "öbla";
                    break;
                case "avel":
                    femEntry = entry.substring(0, entry.length() - 4) + "avla";
                    break;
                case "ider":
                    femEntry = entry.substring(0, entry.length() - 4) + "idra";
                    break;
                case "iker":
                    femEntry = entry.substring(0, entry.length() - 4) + "icra";
                    break;
                case "iner":
                    femEntry = entry.substring(0, entry.length() - 4) + "inra";
                    break;
                case "ster":
                    femEntry = entry.substring(0, entry.length() - 4) + "stra";
                    break;
                case "ader":
                    femEntry = entry.substring(0, entry.length() - 4) + "adra";
                    break;
                case "uven":
                    femEntry = entry.substring(0, entry.length() - 4) + "uvra";
                    break;
                case "eder":
                    femEntry = entry.substring(0, entry.length() - 4) + "edra";
                    break;
            }
        }

        if (length > 3 && femEntry.compareTo("") == 0) {
            endung = entry.substring(entry.length() - 3);

            switch (endung) {
                case "agl":
                    femEntry = entry.substring(0, entry.length() - 3) + "alla";
                    break;
            }
        }

        if (length > 2 && femEntry.compareTo("") == 0) {
            endung = entry.substring(entry.length() - 2);

            switch (endung) {
                case "gl":
                    femEntry = entry.substring(0, entry.length() - 2) + "glia";
                    break;
                case "öl":
                    femEntry = entry.substring(0, entry.length() - 2) + "oula";
                    break;
                case "an":
                    femEntry = entry.substring(0, entry.length() - 2) + "ogna";
                    break;
                case "zi":
                    femEntry = entry.substring(0, entry.length() - 2) + "cessa";
                    break;
            }
        }

        if (femEntry.compareTo("") == 0) {
            switch (lastChar) {
                case 'a':
                    String stamm = entry.substring(0, entry.length() - 1);
                    // augmentativ f
                    addVF(stamm + "una", "NN");
                    // diminutiv f
                    addVF(stamm + "ina", "NN");
                    addVF(stamm + "etta", "NN");
                    // pegiorativ f
                    addVF(entry + "tscha", "NN");
                    break;
                case 'à':
                    femEntry = entry.substring(0, entry.length() - 1) + "ada";
                    break;
                case 'è':
                    femEntry = entry.substring(0, entry.length() - 1) + "ella";
                    break;
                case 'i':
                    femEntry = entry + "da";
                    break;
                case 'l':
                    femEntry = entry + "la";
                    break;
                case 'p':
                    femEntry = entry + "pa";
                    break;
                case 'r':
                    femEntry = entry + "ra";
                    break;
                case 't':
                    femEntry = entry + "ta";
                    break;
                case 'ü':
                    femEntry = entry + "da";
                    break;
                // regelmäßige feminine Form
                default:
                    femEntry = entry + 'a';
                    // feminin plural
                    addVF(entry + "as", "NN");
            }
        }

        addVF(femEntry, "NN");
        addSubstantivPluralAndOtherSuffixes(femEntry);

    }

    private void addSubstantivPluralAndOtherSuffixes(String entry) {
        // feminin plural
        addVF(entry + "s", "NN");

        String stamm;
        if (entry.length() > 1) {
            stamm = entry.substring(0, entry.length() - 1);
        } else {
            stamm = entry;
        }

        // augmentativ f
        addVF(stamm + "una", "NN");
        // diminutiv f
        addVF(stamm + "ina", "NN");
        addVF(stamm + "etta", "NN");
        // pegiorativ f
        addVF(entry + "tscha", "NN");
    }

    private void generateAdjectiveForms(String entry) {
        addVF(entry, "ADJ");
        char lastChar = entry.charAt(entry.length() - 1);
        if (lastChar != 's') {
            // m pl
            addVF(entry + "s", "ADJ");
        }
        if (lastChar != 'a') {
            // f sg
            addVF(entry + "a", "ADJ");
            // f pl
            addVF(entry + "as", "ADJ");
            addVF(entry + "amaing", "ADV");
        }
        addVF(entry + "maing", "ADV");

        generateSpecialFemininForms(entry, "ADJ");
    }

    private void generateSpecialFemininForms(String entry, String pos) {
        // TODO: Diese Methode könnte auch auf Substantive angewendet werden. Einige Fälle könnten hiervon noch nicht erfasst werden.

        // Verdoppelung des letzten Konsonanten:
        if (!endsWithVocal(entry)) {
            addVF(entry + entry.charAt(entry.length() - 1) + 'a', pos);
        }

        // Adjektive, die auf g oder gl enden
        if (entry.charAt(entry.length() - 1) == 'g') {
            addVF(entry + "ia", pos);
        } else if (entry.substring(entry.length() - 1, entry.length()).compareTo("gl") == 0) {
            addVF(entry + "ia", pos);
        }

        // der letzte Vokal verschwindet
        /*
         * bel->bla, pel->pla, vel->vla, schem->schma, ter->tra, ver->vra, der->dra, cal->cla,
		 * mel->mla, tel->tla, sem->sma, der->dra, ner->nra, ber->bra, sen->sna, ven->vna
		 *
		 * *{Konsonant}{e}{l, m, n, r} -> daraus einen Algo mit Regulären Ausdrücken machen?
		 *
		 * tschen->tschna (hen->hna?)
		 * gher->gra
		 * zzer->zra
		 */

        if (entry.length() > 3) {
            String stamm = entry.substring(0, entry.length() - 3);
            String endung = entry.substring(entry.length() - 3);

            switch (endung) {
                case "bel":
                    addVF(stamm + "bla", pos);
                    break;
                case "ber":
                    addVF(stamm + "bra", pos);
                    break;
                case "cal":
                    addVF(stamm + "cla", pos);
                    break;
                case "der":
                    addVF(stamm + "dra", pos);
                    break;
                case "mel":
                    addVF(stamm + "mla", pos);
                    break;
                case "ner":
                    addVF(stamm + "nra", pos);
                    break;
                case "pel":
                    addVF(stamm + "pla", pos);
                    break;
                case "ser":
                    addVF(stamm + "sra", pos);
                    break;
                case "sen":
                    addVF(stamm + "sna", pos);
                    break;
                case "sem":
                    addVF(stamm + "sma", pos);
                    break;
                case "tel":
                    addVF(stamm + "tla", pos);
                    break;
                case "ter":
                    addVF(stamm + "tra", pos);
                    break;
                case "ven":
                    addVF(stamm + "vna", pos);
                    break;
                case "ver":
                    addVF(stamm + "vra", pos);
                    break;
                case "vel":
                    addVF(stamm + "vla", pos);
                    break;
            }
        }

        // vereinzelte Spezialfälle ...

    }

    private void addArticles() {
        addVF("il", "ART");
        addVF("ils", "ART");
        addVF("la", "ART");
        addVF("las", "ART");
        addVF("l'", "ART");
        addVF("ün", "ART");
        addVF("üna", "ART");
        addVF("ün'", "ART");
    }

    private void addPrep_Art() {
        addVF("al", "PREP_ART");
        addVF("als", "PREP_ART");
        addVF("dal", "PREP_ART");
        addVF("dals", "PREP_ART");
        addVF("cul", "PREP_ART");
        addVF("culs", "PREP_ART");
        addVF("i'l", "PREP_ART");
        addVF("i'ls", "PREP_ART");
        addVF("pel", "PREP_ART");
        addVF("pels", "PREP_ART");

        addVF("sül", "PREP_ART");
        addVF("süls", "PREP_ART");
        addVF("prols", "PREP_ART");
        addVF("illa", "PREP_ART");
        addVF("illas", "PREP_ART");
        addVF("culla", "PREP_ART");
        addVF("cullas", "PREP_ART");
        addVF("pella", "PREP_ART");
        addVF("pellas", "PREP_ART");
        addVF("sülla", "PREP_ART");
        addVF("süllas", "PREP_ART");
    }

    private void addPronouns() {
        //indefinite
        addVF("alch", "PRON_IND");
        addVF("bainquant", "PRON_IND");
        addVF("bainquanta", "PRON_IND");
        addVF("bler", "PRON_IND");
        addVF("blera", "PRON_IND");
        addVF("differents", "PRON_IND");
        addVF("differentas", "PRON_IND");
        addVF("gnanca", "PRON_IND"); // gnanca ün, üna
        addVF("ingün", "PRON_IND");
        addVF("ingüna", "PRON_IND");
        addVF("inguotta", "PRON_IND");
        addVF("minchün", "PRON_IND");
        addVF("minchüna", "PRON_IND");
        addVF("mical", "PRON_IND"); // ün mical
        addVF("nögli", "PRON_IND");
        addVF("oter", "PRON_IND");
        addVF("otra", "PRON_IND");
        addVF("pa", "PRON_IND"); // ün pa
        addVF("pêr", "PRON_IND"); // ün pêr
        addVF("païn", "PRON_IND");    // ün païn
        addVF("paet", "PRON_IND");
        addVF("ün", "PRON_IND");
        addVF("üna", "PRON_IND");
        addVF("pac", "PRON_IND");
        addVF("paca", "PRON_IND");
        addVF("plüs", "PRON_IND");
        addVF("plüssas", "PRON_IND");
        addVF("plüs", "PRON_IND");
        addVF("inchün", "PRON_IND");
        addVF("inchüna", "PRON_IND");
        addVF("qualchün", "PRON_IND");
        addVF("qualchüna", "PRON_IND");
        addVF("qualchüns", "PRON_IND");
        addVF("qualchünas", "PRON_IND");
        addVF("qualchedün", "PRON_IND");
        addVF("qualchedüns", "PRON_IND");
        addVF("qualchedüna", "PRON_IND");
        addVF("qualchedünas", "PRON_IND");
        addVF("quant", "PRON_IND");
        addVF("quanta", "PRON_IND");
        addVF("tant", "PRON_IND");
        addVF("tanta", "PRON_IND");
        addVF("tschel", "PRON_IND");
        addVF("tschella", "PRON_IND");
        addVF("tschert", "PRON_IND");
        addVF("tscherta", "PRON_IND");
        addVF("tuot", "PRON_IND");
        addVF("tuots", "PRON_IND");
        addVF("tuottas", "PRON_IND");
        addVF("varsaquants", "PRON_IND");
        addVF("varsaquantas", "PRON_IND");
        addVF("zich", "PRON_IND");    // ün zich
        addVF("ziccal", "PRON_IND");    // ün ziccal

        //demonstativ
        addVF("quist", "PRON_DEM");
        addVF("quista", "PRON_DEM");
        addVF("quel", "PRON_DEM");
        addVF("quella", "PRON_DEM");
        addVF("tschel", "PRON_DEM");
        addVF("tschella", "PRON_DEM");
        addVF("quai", "PRON_DEM");
        addVF("tschai", "PRON_DEM");
        addVF("medem", "PRON_DEM");
        addVF("medemma", "PRON_DEM");
        addVF("listess", "PRON_DEM");
        addVF("listessa", "PRON_DEM");
        addVF("oter", "PRON_DEM");
        addVF("l'otra", "PRON_DEM");
        addVF("simil", "PRON_DEM");
        addVF("simla", "PRON_DEM");
        addVF("tal", "PRON_DEM");
        addVF("tala", "PRON_DEM");
        addVF("quists", "PRON_DEM");
        addVF("quistas", "PRON_DEM");
        addVF("quels", "PRON_DEM");
        addVF("quellas", "PRON_DEM");
        addVF("tschels", "PRON_DEM");
        addVF("tschellas", "PRON_DEM");
        addVF("medems", "PRON_DEM");
        addVF("medemmas", "PRON_DEM");
        addVF("listess", "PRON_DEM");
        addVF("listessas", "PRON_DEM");
        addVF("oters", "PRON_DEM");
        addVF("otras", "PRON_DEM");
        addVF("simils", "PRON_DEM");
        addVF("simlas", "PRON_DEM");
        addVF("tals", "PRON_DEM");
        addVF("talas", "PRON_DEM");


        //interrogativ
        addVF("chi", "PRON_IES");
        addVF("che", "PRON_IES");
        addVF("chenün", "PRON_IES");
        addVF("chenüns", "PRON_IES");
        addVF("chenüna", "PRON_IES");
        addVF("chenünas", "PRON_IES");
        addVF("qual", "PRON_IES");
        addVF("quals", "PRON_IES");
        addVF("quala", "PRON_IES");
        addVF("qualas", "PRON_IES");
        addVF("quant", "PRON_IES");
        addVF("quants", "PRON_IES");
        addVF("quanta", "PRON_IES");
        addVF("quantas", "PRON_IES");


        //personal
        addVF("eu", "PRON_PER");
        addVF("tü", "PRON_PER");
        addVF("el", "PRON_PER");
        addVF("ella", "PRON_PER");
        addVF("El", "PRON_PER");    // Großschreibung von Bedeutung bei der Verarbeitung?
        addVF("Ella", "PRON_PER");
        addVF("i", "PRON_PER");
        addVF("i's", "PRON_PER");
        addVF("as", "PRON_PER");
        addVF("nus", "PRON_PER");
        addVF("vus", "PRON_PER");
        addVF("els", "PRON_PER");
        addVF("ellas", "PRON_PER");
        addVF("Els", "PRON_PER");
        addVF("Ellas", "PRON_PER");
        addVF("am", "PRON_PER");
        addVF("at", "PRON_PER");
        addVF("til", "PRON_PER");
        addVF("tilla", "PRON_PER");
        addVF("ans", "PRON_PER");
        addVF("as", "PRON_PER");
        addVF("tils", "PRON_PER");
        addVF("tillas", "PRON_PER");
        addVF("m'ha", "PRON_PER");
        addVF("t'ha", "PRON_PER");
        addVF("til'ha", "PRON_PER");
        addVF("till'ha", "PRON_PER");
        addVF("mai", "PRON_PER");
        addVF("tai", "PRON_PER");

        // reflexiv
        addVF("am", "PRON_REF");
        addVF("at", "PRON_REF");
        addVF("as", "PRON_REF");
        addVF("ans", "PRON_REF");
        addVF("i's", "PRON_REF");
        addVF("nu'm", "PRON_REF");
        addVF("nu't", "PRON_REF");
        addVF("nu's", "PRON_REF");
        addVF("nu'ns", "PRON_REF");

        //relativ
        addVF("chi", "PRON_REL");
        addVF("cha", "PRON_REL");

        //possesiv
        addVF("meis", "PRON_POS");
        addVF("teis", "PRON_POS");
        addVF("seis", "PRON_POS");
        addVF("nos", "PRON_POS");
        addVF("vos", "PRON_POS");
        addVF("lur", "PRON_POS");
        addVF("noss", "PRON_POS");
        addVF("voss", "PRON_POS");
        addVF("mia", "PRON_POS");
        addVF("tia", "PRON_POS");
        addVF("sia", "PRON_POS");
        addVF("nossa", "PRON_POS");
        addVF("vossa", "PRON_POS");
        addVF("mias", "PRON_POS");
        addVF("tias", "PRON_POS");
        addVF("sias", "PRON_POS");
        addVF("nossas", "PRON_POS");
        addVF("vossas", "PRON_POS");
        addVF("Seis", "PRON_POS");
        addVF("Lur", "PRON_POS");
        addVF("Sia", "PRON_POS");

    }

    private void generateVerbForms(String entry) {
        // Infinitiv
        addVF(entry, "V_GVRB");
        VerbClass verbClass;

        if (entry.length() > 3) {
            String endung = entry.substring(entry.length() - 2);
            String stamm = entry.substring(0, entry.length() - 2);
            if (endung.equals("ir")) {
                if (stamm.endsWith("a")) {
                    stamm = stamm.substring(0, stamm.length() - 1);
                    verbClass = VerbClass.AIR;
                } else {
                    verbClass = VerbClass.IR;

                    // Verben auf -gir
                    if (stamm.endsWith("g")) {
                        addConjugations(stamm + "i", verbClass);
                    }
                    // Verben auf -glir
                    else if (stamm.endsWith("gl")) {
                        addConjugations(stamm + "i", verbClass);
                    }
                }
            } else if (endung.equals("er")) {
                verbClass = VerbClass.ER;

                // Verben auf -ger
                if (stamm.endsWith("g")) {
                    addVF(stamm, "V_GVRB");
                    addConjugations(stamm + "i", verbClass);
                }

            } else if (endung.equals("ar")) {
                verbClass = VerbClass.AR;

                // Verben auf -giar
                if (stamm.endsWith("gi")) {
                    addVF(stamm.substring(0, stamm.length() - 1), "V_GVRB");
                    addConjugations(stamm, verbClass);
                }
                // Verben auf -gliar
                else if (stamm.endsWith("gli")) {
                    addVF(stamm.substring(0, stamm.length() - 1), "V_GVRB");
                    addConjugations(stamm, verbClass);
                }
                // Verben auf -iar
                else if (stamm.endsWith("i")) {
                    String iarStamm = stamm.substring(0, stamm.length() - 1);

                    addConjugations(iarStamm + "aj", verbClass);
                    // 1. und 2. Person Plural
                    addConjugations(stamm + "i", verbClass);

                }
            } else {
                System.out.println("unbekanntes Verb - falsche Endung: " + endung);
                return;
            }

            // zusätzliche Formen mit i anstatt j
            if (stamm.endsWith("j")) {
                addVF(stamm.substring(0, stamm.length() - 1) + "i", "V_GVRB");
            }

            addConjugations(stamm, verbClass);

            // Reflexivpronomen vor Verben beginnend mit einem Vokal
            // produziert massiv und unsinnig über, aber nur diejenigen zu generieren, die grammatisch sinnvoll sind, könnte unübersichtlich werden
            if (startsWithVocal(stamm)) {
                addConjugations("m'" + stamm, verbClass);
                addConjugations("t'" + stamm, verbClass);
                addConjugations("s'" + stamm, verbClass);
            }
        }
    }


    private void addConjugations(String stamm, VerbClass verbClass) {
        addPartizip(stamm, verbClass);
        addGerundium(stamm, verbClass, "V_GVRB");
        addImperativ(stamm, verbClass, "V_GVRB");
        addPraesensAugmentativ(stamm, verbClass, "V_GVRB");
        addIndikativPraesens(stamm, verbClass, "V_GVRB");
        addIndikativImperfekt(stamm, verbClass, "V_GVRB");
        addIndikativFuturI(stamm, verbClass, "V_GVRB");
        addKonjunktivPraesens(stamm, verbClass, "V_GVRB");
        addKonjunktivImperfekt(stamm, verbClass, "V_GVRB");
    }

    private void addPartizip(String stamm, VerbClass verbClass) {
        if (verbClass == VerbClass.AR) {
            addVF(stamm + "à", "V_PP");
            addVF(stamm + "ats", "V_PP");
            addVF(stamm + "ada", "V_PP");
            addVF(stamm + "adas", "V_PP");
        } else if (verbClass == VerbClass.AIR || verbClass == VerbClass.ER) {
            addVF(stamm + "ü", "V_PP");
            addVF(stamm + "ts", "V_PP");
            addVF(stamm + "üda", "V_PP");
            addVF(stamm + "üdas", "V_PP");
        } else if (verbClass == VerbClass.IR) {
            addVF(stamm + "i", "V_PP");
            addVF(stamm + "ida", "V_PP");
            addVF(stamm + "its", "V_PP");
            addVF(stamm + "idas", "V_PP");
        }
    }

    private void addGerundium(String stamm, VerbClass verbClass, String pos) {
        if (verbClass == VerbClass.AR || verbClass == VerbClass.AIR || verbClass == VerbClass.ER) {
            addVF(stamm + "ond", pos);
        } else if (verbClass == VerbClass.IR) {
            addVF(stamm + "ind", pos);
        }
    }

    private void addImperativ(String stamm, VerbClass verbClass, String pos) {
        if (verbClass == VerbClass.AR || verbClass == VerbClass.AIR || verbClass == VerbClass.ER) {
            addVF(stamm + "ai", pos);            // 2 Ps Pl Imperativ affirmativ
            // weitere Formen enden auf -a, -ain, -ar, -a und werden anderweitig schon gebildet
        } else {    // (verbClass == VerbClass.IR)
            addVF(stamm + "i", pos);            // 2 Ps Pl Imperativ affirmativ
            addVF(stamm + "ai", pos);            // 2 Ps Pl Imperativ verneinend
            // weitere Formen enden auf -a, -in, -an und werden anderweitig schon gebildet
        }
    }

    /**
     * VIELE Verben auf -ar bzw. -ir erhalten im indikativen und konjunktiven Präsens die Erweiterung -esch- bzw. -isch-.
     * Im Wörterbuch wird die 3. Ps in Klammern, z. B. -ischa, angegeben.
     * <p>
     * In dieser Methode wir von ALLEN Verben auf -ar und -ir solche augmentativen Formen gebildet
     */
    private void addPraesensAugmentativ(String stamm,
                                        VerbClass verbClass, String pos) {
        if (verbClass == VerbClass.AR) {
            stamm = stamm + "esch";
            addIndikativPraesens(stamm, verbClass, pos);
            addKonjunktivPraesens(stamm, verbClass, pos);
        } else if (verbClass == VerbClass.IR) {
            stamm = stamm + "isch";
            addIndikativPraesens(stamm, verbClass, pos);
            addKonjunktivPraesens(stamm, verbClass, pos);
        }

    }

    private void addIndikativPraesens(String stamm, VerbClass verbClass,
                                      String pos) {
        addVF(stamm, pos);                    // 1. Ps Sg
        addVF(stamm + "ast", pos);            // 2. Ps Sg
        addVF(stamm + "a", pos);            // 3. Ps Sg
        addVF(stamm + "an", pos);            // 3. Ps Pl
        if (verbClass == VerbClass.AR || verbClass == VerbClass.AIR || verbClass == VerbClass.ER) {
            addVF(stamm + "ain", pos);        // 1. Ps Pl
            addVF(stamm + "ais", pos);        // 2. Ps Pl
            // Inversion
            addVF(stamm + "aina", pos);
        } else {        // (verbClass == verbClass.IR)
            addVF(stamm + "in", pos);        // 1. Ps Pl
            addVF(stamm + "is", pos);        // 2. Ps Pl

            // Inversion
            addVF(stamm + "ina", pos);
        }

        // Inversion
        addVF(stamm + "a'l", pos);
        addVF(stamm + "'la", pos);
        addVF(stamm + "na", pos);
    }

    private void addIndikativImperfekt(String stamm, VerbClass verbClass,
                                       String pos) {
        if (verbClass == VerbClass.AR || verbClass == VerbClass.AIR || verbClass == VerbClass.ER) {
            stamm = stamm + "aiv";
        } else {        // (verbClass == verbClass.IR)
            stamm = stamm + "iv";
        }
        addVF(stamm + "a", pos);        // 1. Ps Sg, 3. Ps Sg
        addVF(stamm + "ast", pos);        // 2. Ps Sg
        addVF(stamm + "an", pos);        // 1. Ps Pl, 3. Ps Pl
        addVF(stamm + "at", pos);        // 2. Ps Pl

        // Inversion
        addVF(stamm + "a'l", pos);
        addVF(stamm + "'la", pos);
        addVF(stamm + "na", pos);
    }

    private void addIndikativFuturI(String stamm, VerbClass verbClass,
                                    String pos) {
        if (verbClass == VerbClass.AR || verbClass == VerbClass.AIR || verbClass == VerbClass.ER) {
            stamm = stamm + "ar";
        } else {        // (verbClass == verbClass.IR)
            stamm = stamm + "ir";
        }
        addVF(stamm + "à", pos);        // 1. Ps Sg
        addVF(stamm + "ast", pos);        // 2. Ps Sg
        addVF(stamm + "a", pos);        // 3. Ps Sg
        addVF(stamm + "an", pos);        // 1. Ps Pl, 3. Ps Pl
        addVF(stamm + "at", pos);        // 2. Ps Pl

        // Inversion
        addVF(stamm + "aja", pos);
        addVF(stamm + "à'l", pos);
        addVF(stamm + "à'la", pos);
        addVF(stamm + "ana", pos);
    }

    private void addKonjunktivPraesens(String stamm, VerbClass verbClass,
                                       String pos) {
        addVF(stamm + "a", pos);
        addVF(stamm + "ast", pos);
        addVF(stamm + "an", pos);
        addVF(stamm + "at", pos);
    }

    private void addKonjunktivImperfekt(String stamm, VerbClass verbClass,
                                        String pos) {
        if (verbClass == VerbClass.AR || verbClass == VerbClass.AIR || verbClass == VerbClass.ER) {
            stamm = stamm + "ess";
        } else {        // (verbClass == verbClass.IR)
            stamm = stamm + "iss";
        }
        addVF(stamm, pos);                // 1. Ps Sg, 3. Ps Sg
        addVF(stamm + "ast", pos);        // 2. Ps Sg
        addVF(stamm + "an", pos);        // 1. Ps Pl, 3. Ps Pl
        addVF(stamm + "at", pos);        // 2. Ps Pl

        // Inversion
        addVF(stamm + "a", pos);
        addVF(stamm + "'la", pos);
        addVF(stamm + "na", pos);

    }

    private void addIrregularVerbs() {

        String stamm = "";

        // esser
        // infinitiv
        addVF("esser", "V_ESSER");
        // Partizip
        addVF("stat", "V_PP");
        addVF("stats", "V_PP");
        addVF("statta", "V_PP");
        addVF("stattas", "V_PP");
        // Gerundium
        addVF("siond", "V_ESSER");
        // Imperativ
        addVF("sejast", "V_ESSER");
        addVF("sajan", "V_ESSER");
        addVF("sajat", "V_ESSER");
        addVF("sarai", "V_ESSER");
        // Indikativ Präsens
        addVF("sun", "V_ESSER");
        addVF("est", "V_ESSER");
        addVF("es", "V_ESSER");
        addVF("eschan", "V_ESSER");
        addVF("eschat", "V_ESSER");
        addVF("sun", "V_ESSER");
        // Indikativ Imperfekt
        addVF("d'eira", "V_ESSER");
        addVF("d'eirast", "V_ESSER");
        addVF("deira", "V_ESSER");
        addVF("d'eiran", "V_ESSER");
        addVF("d'eirat", "V_ESSER");
        // Indikativ Futur I
        addIndikativFuturI("s", VerbClass.ER, "V_ESSER");
        // Konjunktiv Präsens
        addKonjunktivPraesens("saj", VerbClass.ER, "V_ESSER");
        // Konjunktiv Imperfekt
        addVF("füss", "V_ESSER");
        addVF("füssast", "V_ESSER");
        addVF("füssan", "V_ESSER");
        addVF("füssat", "V_ESSER");


        // avair
        // infinitiv
        addVF("avair", "V_AVAIR");
        // Partizip
        addPartizip("gn", VerbClass.AIR);
        // Gerundium
        addVF("aviond", "V_AVAIR");
        // Imperativ
        addVF("avairai", "V_AVAIR");
        // Indikativ Präsens
        addVF("n'ha", "V_AVAIR");
        addVF("hast", "V_AVAIR");
        addVF("ha", "V_AVAIR");
        addVF("id'ha", "V_AVAIR");
        addVF("vain", "V_AVAIR");
        addVF("vais", "V_AVAIR");
        addVF("han", "V_AVAIR");
        // Indikativ Imperfekt
        addIndikativImperfekt("v", VerbClass.AIR, "V_AVAIR");
        // Indikativ Futur I
        addIndikativFuturI("v", VerbClass.AIR, "V_AVAIR");
        // Konjunktiv Präsens
        addVF("n'haja", "V_AVAIR");
        addKonjunktivPraesens("haj", VerbClass.AIR, "V_AVAIR");
        // Konjunktiv Imperfekt
        addKonjunktivImperfekt("v", VerbClass.AIR, "V_AVAIR");

        // star
        // infinitiv
        addVF("star", "V_GVRB");
        // Partizip
        addVF("stat", "V_PP");
        addVF("stats", "V_PP");
        addVF("statta", "V_PP");
        addVF("stattas", "V_PP");
        // Gerundium
        addVF("stond", "V_GVRB");
        // Imperativ
        addVF("stat", "V_GVRB");
        // Indikativ Präsens
        addVF("stun", "V_GVRB");
        addVF("stast", "V_GVRB");
        addVF("sta", "V_GVRB");
        addVF("stain", "V_GVRB");
        addVF("stais", "V_GVRB");
        addVF("stan", "V_GVRB");
        // Indikativ Imperfekt
        addIndikativImperfekt("st", VerbClass.AR, "V_GVRB");
        // Indikativ Futur I
        addIndikativFuturI("st", VerbClass.AR, "V_GVRB");
        // Konjunktiv Präsens
        addKonjunktivPraesens("stett", VerbClass.AR, "V_GVRB");
        // Konjunktiv Imperfekt
        addKonjunktivImperfekt("st", VerbClass.AR, "V_GVRB");


        // ir
        // infinitiv
        addVF("ir", "V_GVRB");
        // Partizip
        addVF("i", "V_PP");
        addVF("it", "V_PP");
        addVF("ida", "V_PP");
        addVF("idas", "V_PP");
        // Gerundium
        addVF("giond", "V_GVRB");
        // Imperativ
        addVF("it", "V_GVRB");
        addVF("gairai", "V_GVRB");
        // Indikativ Präsens
        addVF("vegn", "V_GVRB");
        addVF("vast", "V_GVRB");
        addVF("va", "V_GVRB");
        addVF("giain", "V_GVRB");
        addVF("giais", "V_GVRB");
        addVF("van", "V_GVRB");
        // Indikativ Imperfekt
        addIndikativImperfekt("gi", VerbClass.AR, "V_GVRB");
        // Indikativ Futur I
        addIndikativFuturI("gi", VerbClass.AR, "V_GVRB");
        // Konjunktiv Präsens
        addKonjunktivPraesens("giaj", VerbClass.AR, "V_GVRB");
        // Konjunktiv Imperfekt
        addKonjunktivImperfekt("g", VerbClass.AR, "V_GVRB");

        // gnir
        // infinitiv
        addVF("gnir", "V_GVRB");
        // Partizip
        addVF("gnü", "V_PP");
        addVF("gnüts", "V_PP");
        addVF("gnüda", "V_PP");
        addVF("gnüdas", "V_PP");
        // Gerundium
        addVF("gnind", "V_GVRB");
        // Imperativ
        addVF("vè", "V_GVRB");
        addVF("gnit", "V_GVRB");
        addVF("gnarai", "V_GVRB");
        // Indikativ Präsens
        addVF("vainst", "V_GVRB");
        addVF("vengst", "V_GVRB");
        addVF("vain", "V_GVRB");
        addVF("gnin", "V_GVRB");
        addVF("gnis", "V_GVRB");
        addVF("vegnan", "V_GVRB");
        // Indikativ Imperfekt - regelmäßig
        // Indikativ Futur I
        addIndikativFuturI("gn", VerbClass.AR, "V_GVRB");
        // Konjunktiv Präsens
        addKonjunktivPraesens("vegn", VerbClass.IR, "V_GVRB");
        // Konjunktiv Imperfekt - regelmäßig

        // dar
        // infinitiv
        addVF("dar", "V_GVRB");
        // Partizip
        addVF("dat", "V_PP");
        addVF("dats", "V_PP");
        addVF("datta", "V_PP");
        addVF("dattas", "V_PP");
        // Gerundium
        addVF("dond", "V_GVRB");
        // Imperativ
        addVF("dat", "V_GVRB");
        addVF("darai", "V_GVRB");
        // Indikativ Präsens
        addVF("dun", "V_GVRB");
        addVF("dast", "V_GVRB");
        addVF("dà", "V_GVRB");
        addVF("dain", "V_GVRB");
        addVF("dais", "V_GVRB");
        addVF("dan", "V_GVRB");
        // Indikativ Imperfekt - regelmäßig
        // Indikativ Futur I - regelmäßig
        // Konjunktiv Präsens
        addKonjunktivPraesens("dett", VerbClass.AR, "V_GVRB");
        // Konjunktiv Imperfekt - regelmäßig


        // dir
        // infinitiv
        addVF("dir", "V_GVRB");
        // Partizip
        addVF("dit", "V_PP");
        addVF("dits", "V_PP");
        addVF("ditta", "V_PP");
        addVF("dittas", "V_PP");
        // Gerundium
        addVF("dschond", "V_GVRB");
        // Imperativ
        addVF("dit", "V_GVRB");
        addVF("dscharai", "V_GVRB");
        // Indikativ Präsens
        addVF("di", "V_GVRB");
        addVF("dist", "V_GVRB");
        addVF("disch", "V_GVRB");
        addVF("dischain", "V_GVRB");
        addVF("dischais", "V_GVRB");
        addVF("dischan", "V_GVRB");
        // Indikativ Imperfekt
        addIndikativImperfekt("dsch", VerbClass.AR, "V_GVRB");
        // Indikativ Futur I
        addIndikativFuturI("dsch", VerbClass.AR, "V_GVRB");
        // Konjunktiv Präsens
        addKonjunktivPraesens("di", VerbClass.IR, "V_GVRB");
        // Konjunktiv Imperfekt
        addKonjunktivImperfekt("dsch", VerbClass.AR, "V_GVRB");


        // modale Verben

        // far
        // infinitiv
        addVF("dir", "V_MOD");
        // Partizip
        addVF("fat", "V_PP");
        addVF("fats", "V_PP");
        addVF("fatta", "V_PP");
        addVF("fattas", "V_PP");
        // Gerundium
        addVF("fond", "V_MOD");
        // Imperativ
        addVF("faina", "V_MOD");
        addVF("fat", "V_MOD");
        addVF("farai", "V_MOD");
        // Indikativ Präsens
        addVF("fetsch", "V_MOD");
        addVF("fast", "V_MOD");
        addVF("fa", "V_MOD");
        addVF("fain", "V_MOD");
        addVF("fais", "V_MOD");
        addVF("fain", "V_MOD");
        // Indikativ Imperfekt - regelmäßig
        // Indikativ Futur I - regelmäßig
        // Konjunktiv Präsens
        addKonjunktivPraesens("fetsch", VerbClass.AR, "V_MOD");
        // Konjunktiv Imperfekt - regelmäßig

        // vulair
        // infinitiv
        addVF("vulair", "V_MOD");
        // Partizip
        addVF("vuglü", "V_PP");
        addVF("vuglüts", "V_PP");
        addVF("vuglüda", "V_PP");
        addVF("vuglüdas", "V_PP");
        // Gerundium
        addVF("vuliond", "V_MOD");
        // Imperativ
        addVF("vularai", "V_MOD");
        // Indikativ Präsens
        addVF("vögl", "V_MOD");
        addVF("voust", "V_MOD");
        addVF("voul", "V_MOD");
        addVF("vulain", "V_MOD");
        addVF("vulais", "V_MOD");
        addVF("vöglian", "V_MOD");
        addVF("voulan", "V_MOD");
        addVF("lain", "V_MOD");
        addVF("lais", "V_MOD");
        // Indikativ Imperfekt
        addIndikativImperfekt("vul", VerbClass.AIR, "V_MOD");
        addIndikativImperfekt("l", VerbClass.AIR, "V_MOD");
        // Indikativ Futur I
        addIndikativFuturI("vul", VerbClass.AIR, "V_MOD");
        addIndikativFuturI("l", VerbClass.AIR, "V_MOD");
        // Konjunktiv Präsens
        addKonjunktivPraesens("vögli", VerbClass.AIR, "V_MOD");
        // Konjunktiv Imperfekt
        addKonjunktivImperfekt("vul", VerbClass.AIR, "V_MOD");
        addKonjunktivImperfekt("l", VerbClass.AIR, "V_MOD");


        // pudair
        // infinitiv
        addVF("pudair", "V_MOD");
        // Partizip
        addPartizip("pud", VerbClass.AIR);
        // Gerundium
        addVF("pudiond", "V_MOD");
        // Imperativ --

        // Indikativ Präsens
        addVF("poss", "V_MOD");
        addVF("poust", "V_MOD");
        addVF("po", "V_MOD");
        addVF("pudain", "V_MOD");
        addVF("pudais", "V_MOD");
        addVF("pon", "V_MOD");
        // Indikativ Imperfekt
        addIndikativImperfekt("pud", VerbClass.AIR, "V_MOD");
        // Indikativ Futur I
        addIndikativFuturI("pud", VerbClass.AIR, "V_MOD");
        // Konjunktiv Präsens
        addKonjunktivPraesens("poss", VerbClass.AIR, "V_MOD");
        // Konjunktiv Imperfekt
        addKonjunktivImperfekt("pud", VerbClass.AIR, "V_MOD");


        // stuvair
        stamm = "stuv";
        // infinitiv
        addVF("stuvair", "V_MOD");
        // Partizip
        addPartizip(stamm, VerbClass.AIR);
        // Gerundium
        addVF(stamm + "iond", "V_MOD");
        // Imperativ

        // Indikativ Präsens
        addVF("stögl", "V_MOD");
        addVF("stoust", "V_MOD");
        addVF("sto", "V_MOD");
        addVF("stuain", "V_MOD");
        addVF("stuvais", "V_MOD");
        addVF("ston", "V_MOD");
        // Indikativ Imperfekt
        addIndikativImperfekt("stuv", VerbClass.AIR, "V_MOD");
        // Indikativ Futur I
        addIndikativFuturI("stuv", VerbClass.AIR, "V_MOD");
        // Konjunktiv Präsens
        addKonjunktivPraesens("stögli", VerbClass.AIR, "V_MOD");
        addVF("stopcha", "V_MOD");
        addVF("stopchan", "V_MOD");
        // Konjunktiv Imperfekt
        addKonjunktivImperfekt(stamm, VerbClass.AIR, "V_MOD");

        // savair
        stamm = "sav";
        // infinitiv
        addVF("savair", "V_MOD");
        // Partizip
        addPartizip(stamm, VerbClass.AIR);
        // Gerundium
        addVF(stamm + "iond", "V_MOD");
        // Imperativ

        // Indikativ Präsens
        addVF("sa", "V_MOD");
        addVF("sast", "V_MOD");
        addVF(stamm + "ain", "V_MOD");
        addVF(stamm + "ais", "V_MOD");
        addVF("san", "V_MOD");
        // Indikativ Imperfekt
        addIndikativImperfekt(stamm, VerbClass.AIR, "V_MOD");
        // Indikativ Futur I
        addIndikativFuturI(stamm, VerbClass.AIR, "V_MOD");
        // Konjunktiv Präsens
        addKonjunktivPraesens("sapch", VerbClass.AIR, "V_MOD");
        // Konjunktiv Imperfekt
        addKonjunktivImperfekt(stamm, VerbClass.AIR, "V_MOD");

        // Defektive Modalverben
        // dovair
        // Indikativ Präsens
        addVF("dess", "V_GVRB");
        addVF("dessast", "V_GVRB");
        addVF("dessan", "V_GVRB");
        addVF("dessat", "V_GVRB");

        // solair
        // Indikativ Präsens
        addVF("soul", "V_GVRB");
        addVF("soulast", "V_GVRB");
        addVF("soula", "V_GVRB");
        addVF("solain", "V_GVRB");
        addVF("solais", "V_GVRB");
        addVF("soulan", "V_GVRB");
        // Indikativ Imperfekt
        addVF("solaiva", "V_GVRB");
        addVF("solaivast", "V_GVRB");
        addVF("solaivan", "V_GVRB");
        addVF("solaivat", "V_GVRB");

        // Verben mit Spezialendung

        //düer
        //adüer
        dueer("a");
        //ardüer
        dueer("ar");
        //condüer
        dueer("con");
        // dedüer
        dueer("de");
        // introdüer
        dueer("intro");
        // prodüer
        dueer("pro");
        // redüer
        dueer("re");
        // reintrodüer
        dueer("reintro");
        // reprodüer
        dueer("repro");
        // tradüer
        dueer("tra");

        // drüer
        // desdrüer
        drueer("des");

        // süar
        addVF("süj", "V_GVRB");


    }

    private void dueer(String prefix) {
        // 1 Ps Sg Indikativ Präsens
        addVF(prefix + "düj", "V_GVRB");
    }

    private void drueer(String prefix) {
        // 1 Ps Sg Indikativ Präsens
        addVF(prefix + "drüj", "V_GVRB");
    }

    private boolean startsWithVocal(String inf) {
        switch (inf.substring(0, 1)) {
            case "a":
            case "e":
            case "i":
            case "o":
            case "u":
            case "ä":
            case "ö":
            case "ü":
                return true;
            default:
                return false;
        }
    }

    private boolean endsWithVocal(String inf) {
        switch (inf.substring(inf.length() - 1)) {
            case "a":
            case "à":
            case "e":
            case "è":
            case "i":
            case "o":
            case "u":
            case "ä":
            case "ö":
            case "ü":
                return true;
            default:
                return false;
        }
    }


    private void addVF(String entry, String pos) {
        TreeSet<String> posTags = vollForms.get(entry);
        if (posTags == null) {
            posTags = new TreeSet<String>();
        }
        posTags.add(pos);
        vollForms.put(entry, posTags);
    }

}
