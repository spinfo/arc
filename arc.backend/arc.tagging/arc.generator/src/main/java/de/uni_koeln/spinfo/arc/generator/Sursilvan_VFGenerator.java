package de.uni_koeln.spinfo.arc.generator;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author geduldia
 */
public class Sursilvan_VFGenerator {

    Map<String, TreeSet<String>> fullForms = new TreeMap<String, TreeSet<String>>();
    int count;

    /**
     * @return fullForms
     */
    public Set<String> getFullForms() {
        return fullForms.keySet();
    }

    /**
     * @return total number of FullForms
     */
    public int getNumberOfVFEntries() {
        return fullForms.keySet().size();
    }

    /**
     * @return number of DBEntries
     */
    public int getNumberOfDBEntries() {
        return count;
    }

    private void addVF(String key, String pos) {
        TreeSet<String> posTags = fullForms.get(key);
        if (posTags == null) {
            posTags = new TreeSet<String>();
        }
        posTags.add(pos);
        fullForms.put(key, posTags);
    }


    /**
     * @param collection
     * @return fullForms
     * @throws UnknownHostException
     */
    public Map<String, TreeSet<String>> generateFullforms(DBCollection collection) throws UnknownHostException {
        DBCursor cursor = collection.find();
        count = 0;
        for (DBObject doc : cursor) {
            count++;
            String entry = (String) doc.get("entry");
            DBObject pos = (BasicDBObject) doc.get("pos");
            String eagles_pos = (String) pos.get("eagles_pos");
            String nvs_pos = (String) pos.get("nvs_pos");

            if (eagles_pos != null) {
                if (eagles_pos.equals("V_GVRB")) {
                    generateVerbForms(entry, nvs_pos);
                    continue;
                }
                if (eagles_pos.equals("NN")) {
                    addVF(entry, "NN");
                    generateSubstantiveForms(entry);
                    continue;
                }
                if (eagles_pos.equals("ADJ")) {
                    addVF(entry, "ADJ");
                    generateAdjectiveForms(entry);
                    continue;
                }
                addVF(entry, eagles_pos);
            }
        }
        addIrregularVerbs();
        addIrregularSubstantives();
        addIrregularAdjectives();
        addModalVerbs();
        addPräpositions();
        addConjunctions();
        addNumbers();
        addPronouns();
        addArticles();
        addAdjectives();
        addParticles();
        return fullForms;
    }

    private void addAdjectives() {
        //demonstrative
        addVF("quest", "ADJ_DEM");
        addVF("questa", "ADJ_DEM");
        addVF("quests", "ADJ_DEM");
        addVF("questas", "ADJ_DEM");
        addVF("quei", "ADJ_DEM");
        addVF("quel", "ADJ_DEM");
        addVF("quella", "ADJ_DEM");
        addVF("quels", "ADJ_DEM");
        addVF("quellas", "ADJ_DEM");
        addVF("tal", "ADJ_DEM");
        addVF("tala", "ADJ_DEM");
        addVF("tanien", "ADJ_DEM");
        addVF("tanienta", "ADJ_DEM");
        addVF("gliez", "ADJ_DEM");
        addVF("lezza", "ADJ_DEM");
        addVF("tschei", "ADJ_DEM");
        addVF("tschella", "ADJ_DEM");
        addVF("mintga", "ADJ_DEM");
        addVF("scadin", "ADJ_DEM");
        addVF("scadina", "ADJ_DEM");
        addVF("tanienta", "ADJ_DEM");

        //indefinite
        addVF("cert", "ADJ_IND");
        addVF("certa", "ADJ_IND");
        addVF("certas", "ADJ_IND");
        addVF("negin", "ADJ_IND");
        addVF("negina", "ADJ_IND");
        addVF("neginas", "ADJ_IND");
        addVF("qualche", "ADJ_IND");
        addVF("tanien", "ADJ_IND");
        addVF("tanienta", "ADJ_IND");
        addVF("tanientas", "ADJ_IND");
        addVF("taniens", "ADJ_IND");

        //interrogativ
        addVF("contavel", "ADJ_IES");
        addVF("contavla", "ADJ_IES");
        addVF("con", "ADJ_IES");
        addVF("cons", "ADJ_IES");
        addVF("contas", "ADJ_IES");
    }

    private void addIrregularAdjectives() {
        //bien
        addVF("buns", "ADJ");
        addVF("buna", "ADJ");
        addVF("bunas", "ADJ");
        addVF("meglier", "ADJ");
        addVF("bunamein", "ADV");
        //mal
        addVF("mala", "ADJ");
        addVF("mals", "ADJ");
        addVF("malas", "ADJ");
        addVF("mender", "ADJ");
        addVF("plir", "ADJ");
        addVF("malamein", "ADV");
        //bi
        addVF("bials", "ADJ");
        addVF("biala", "ADJ");
        addVF("bialas", "ADJ");
        addVF("bialmein", "ADV");
        //tgietschen
        addVF("cotschens", "ADJ");
        addVF("cotschna", "ADJ");
        addVF("cotschnas", "ADJ");
        addVF("cotschnamein", "ADV");
        //detschiert
        addVF("detscharts", "ADJ");
        addVF("detscharta", "ADJ");
        addVF("detschartas", "ADJ");
        addVF("detschartamein", "ADV");
        //miert
        addVF("morts", "ADJ");
        addVF("morta", "ADJ");
        addVF("mortas", "ADJ");
        addVF("mortamein", "ADV");
        //agen
        addVF("atgna", "ADJ");
        addVF("atgnas", "ADJ");
        addVF("agens", "ADJ");
        addVF("atgnamein", "ADV");
        //pign
        addVF("pigns", "ADJ");
        addVF("pintga", "ADJ");
        addVF("pintgas", "ADJ");
        addVF("pintgamein", "ADV");
        //sogn
        addVF("sogns", "ADJ");
        addVF("sontga", "ADJ");
        addVF("sontgas", "ADJ");
        addVF("sontgamein", "ADV");
    }

    private void addConjunctions() {
        addVF("e", "CONJ_C");
        addVF("è", "CONJ_C");
        addVF("et", "CONJ_C");
        addVF("era", "CONJ_C");
        addVF("schizun", "CONJ_C");
        addVF("baul", "CONJ_C");
        addVF("schi", "CONJ_C");
        addVF("bein", "CONJ_C");
        addVF("sco", "CONJ_C");
        addVF("scò", "CONJ_C");
        addVF("ni", "CONJ_C");
        addVF("mo", "CONJ_C");
        addVF("mò", "CONJ_C");
        addVF("denton", "CONJ_C");
        addVF("perencunter", "CONJ_C");
        addVF("u", "CONJ_C");
        addVF("cass", "CONJ_C");
        addVF("cuntrari", "CONJ_C");
        addVF("tonaton", "CONJ_C");
        addVF("nuotatonmeins", "CONJ_C");
        addVF("pertgei", "CONJ_C");
        addVF("perquei", "CONJ_C");
        addVF("pia", "CONJ_C");
        addVF("cuntut", "CONJ_C");
        addVF("nua", "CONJ_S");
        addVF("che", "CONJ_S");
        //Alt. to che
        addVF("cha", "CONJ_S");
        //Meaning: House
        addVF("cha", "NN");
        addVF("danunder", "CONJ_S");
        addVF("cu", "CONJ_S");
        addVF("cura", "CONJ_S");
        addVF("duront", "CONJ_S");
        addVF("dapi", "CONJ_S");
        addVF("tochen", "CONJ_S");
        addVF("avon", "CONJ_S");
        addVF("sche", "CONJ_S");
        addVF("schebein", "CONJ_S");
        addVF("sco", "CONJ_S");
        addVF("scò", "CONJ_S");
        addVF("pli", "CONJ_S");
        addVF("ton", "CONJ_S");
        addVF("aschia", "CONJ_S");
        addVF("schinavon", "CONJ_S");
        addVF("aschi", "CONJ_S");
        addVF("lunsch", "CONJ_S");
        addVF("senza", "CONJ_S");
        addVF("perquei", "CONJ_S");
        addVF("essend", "CONJ_S");
        addVF("cunquei", "CONJ_S");
        addVF("damai", "CONJ_S");
        addVF("sinaquei", "CONJ_S");
        addVF("per", "CONJ_S");
        addVF("schegie", "CONJ_S");
        addVF("schebi", "CONJ_S");
        addVF("cuntut", "CONJ_S");
        addVF("abstrahau", "CONJ_S");
        addVF("abstrahond", "CONJ_S");
        addVF("afin", "CONJ_S");
        addVF("aschia", "CONJ_S");
        addVF("aschiditg", "CONJ_S");
        addVF("aschilunsch", "CONJ_S");
        addVF("aschiprest", "CONJ_S");
        addVF("aunc", "CONJ_S");
        addVF("aunca", "CONJ_S");
        addVF("auter", "CONJ_S");
        addVF("baul", "CONJ_S");
        addVF("bein", "CONJ_S");
        addVF("beincapiu", "CONJ_S");
        addVF("canun", "CONJ_S");
        addVF("capeivel", "CONJ_S");
        addVF("casamei", "CONJ_S");
        addVF("cheutras", "CONJ_S");
        addVF("condanavon", "CONJ_S");
        addVF("crer", "CONJ_S");
        addVF("cumbein", "CONJ_S");
        addVF("cunquei", "CONJ_S");
        addVF("cuntut", "CONJ_S");
        addVF("cunzun", "CONJ_S");
        addVF("cura", "CONJ_S");
        addVF("daco", "CONJ_S");
        addVF("daferton", "CONJ_S");
        addVF("dafertonier", "CONJ_S");
        addVF("damai", "CONJ_S");
        addVF("dano", "CONJ_S");
        addVF("danunder", "CONJ_S");
        addVF("dapi", "CONJ_S");
        addVF("dapia", "CONJ_S");
        addVF("dar", "CONJ_S");
        addVF("davontier", "CONJ_S");
        addVF("demai", "CONJ_S");
        addVF("denton", "CONJ_S");
        addVF("depia", "CONJ_S");
        addVF("diassa", "CONJ_S");
        addVF("dir", "CONJ_S");
        addVF("ditg", "CONJ_S");
        addVF("duer", "CONJ_S");
        addVF("duront", "CONJ_S");
        addVF("e", "CONJ_S");
        addVF("et", "CONJ_S");
        addVF("è", "CONJ_S");
        addVF("empei", "CONJ_S");
        addVF("enaquei", "CONJ_S");
        addVF("enaquella", "CONJ_S");
        addVF("enfin", "CONJ_S");
        addVF("enstagl", "CONJ_S");
        addVF("entocca", "CONJ_S");
        addVF("essend", "CONJ_S");
        addVF("eunca", "CONJ_S");
        addVF("far", "CONJ_S");
        addVF("ferton", "CONJ_S");
        addVF("fin", "CONJ_S");
        addVF("finton", "CONJ_S");
        addVF("forsa", "CONJ_S");
        addVF("gest", "CONJ_S");
        addVF("gie", "CONJ_S");
        addVF("gleiti", "CONJ_S");
        addVF("gliez", "CONJ_S");
        addVF("insumma", "CONJ_S");
        addVF("ma", "CONJ_S");
        addVF("mache", "CONJ_S");
        addVF("magari", "CONJ_S");
        addVF("mai", "CONJ_S");
        addVF("maina", "CONJ_S");
        addVF("malgrad", "CONJ_S");
        addVF("mess", "CONJ_S");
        addVF("mo", "CONJ_S");
        addVF("mò", "CONJ_S");
        addVF("mobein", "CONJ_S");
        addVF("ne", "CONJ_S");
        addVF("ner", "CONJ_S");
        addVF("ni", "CONJ_S");
        addVF("num", "CONJ_S");
        addVF("nunche", "CONJ_S");
        addVF("nunquei", "CONJ_S");
        addVF("nunstuend", "CONJ_S");
        addVF("ont", "CONJ_S");
        addVF("onz", "CONJ_S");
        addVF("oramai", "CONJ_S");
        addVF("pauc", "CONJ_S");
        addVF("percass", "CONJ_S");
        addVF("cass", "CONJ_S");
        addVF("pernot", "CONJ_S");
        addVF("peron", "CONJ_S");
        addVF("perquei", "CONJ_S");
        addVF("persort", "CONJ_S");
        addVF("pertgei", "CONJ_S");
        addVF("pia", "CONJ_S");
        addVF("posta", "CONJ_S");
        addVF("premess", "CONJ_S");
        addVF("promt", "CONJ_S");
        addVF("quei", "CONJ_S");
        addVF("sche", "CONJ_S");
        addVF("schebein", "CONJ_S");
        addVF("schebi", "CONJ_S");
        addVF("schibein", "CONJ_S");
        addVF("schebuc", "CONJ_S");
        addVF("schegie", "CONJ_S");
        addVF("schemo", "CONJ_S");
        addVF("scher", "CONJ_S");
        addVF("schbein", "CONJ_S");
        addVF("schiditg", "CONJ_S");
        addVF("schigleiti", "CONJ_S");
        addVF("schiglioc", "CONJ_S");
        addVF("schinavon", "CONJ_S");
        addVF("schiprest", "CONJ_S");
        addVF("schispert", "CONJ_S");
        addVF("schitost", "CONJ_S");
        addVF("scochemei", "CONJ_S");
        addVF("senza", "CONJ_S");
        addVF("sinaquei", "CONJ_S");
        addVF("strusch", "CONJ_S");
        addVF("suenter", "CONJ_S");
        addVF("supponiu", "CONJ_S");
        addVF("tgunsch", "CONJ_S");
        addVF("tochen", "CONJ_S");
        addVF("ton", "CONJ_S");
        addVF("tonaton", "CONJ_S");
        addVF("tonca", "CONJ_S");
        addVF("tondanavon", "CONJ_S");
        addVF("trer", "CONJ_S");
        addVF("u", "CONJ_S");
        addVF("ual", "CONJ_S");
        addVF("umpia", "CONJ_S");
    }

    private void addPräpositions() {

        addVF("a", "PREP");
        addVF("à", "PREP");
        addVF("ad", "PREP");

        addVF("al", "PREP_A");
        addVF("als", "PREP_A");
        addVF("alla", "PREP_A");
        addVF("allas", "PREP_A");

        addVF("adual", "PREP");
        addVF("amiez", "PREP");
        addVF("arisguard", "PREP");
        addVF("atras", "PREP");
        addVF("atraviers", "PREP");
        addVF("avon", "PREP");
        addVF("da", "PREP");
        addVF("dil", "PREP_A");
        addVF("del", "PREP_A");
        addVF("digl", "PREP_A");
        addVF("dalla", "PREP_A");
        addVF("della", "PREP_A");
        addVF("dils", "PREP_A");
        addVF("dels", "PREP_A");
        addVF("dallas", "PREP_A");
        addVF("dellas", "PREP_A");

        addVF("cun", "PREP");
        addVF("cul", "PREP_A");
        addVF("cugl", "PREP_A");
        addVF("culla", "PREP_A");
        addVF("culs", "PREP_A");
        addVF("cullas", "PREP_A");
        addVF("concernent", "PREP");
        addVF("cunter", "PREP");
        addVF("cuntrari", "PREP");

        addVF("dadens", "PREP");
        addVF("dado", "PREP");
        addVF("damaneivel", "PREP");
        addVF("dano", "PREP");
        addVF("dapi", "PREP");
        addVF("dasperas", "PREP");
        addVF("datier", "PREP");
        addVF("davart", "PREP");
        addVF("daven", "PREP");
        addVF("davon", "PREP");
        addVF("davontier", "PREP");
        addVF("davos", "PREP");
        addVF("devart", "PREP");
        addVF("ded", "PREP");
        addVF("dem", "PREP");
        addVF("deno", "PREP");
        addVF("denter", "PREP");

        addVF("en", "PREP");
        addVF("el", "PREP_A");
        addVF("egl", "PREP_A");
        addVF("ella", "PREP_A");
        addVF("els", "PREP_A");
        addVF("ellas", "PREP_A");
        addVF("eifer", "PREP");
        addVF("empei", "PREP");
        addVF("empéi", "PREP");
        addVF("en", "PREP");
        addVF("enadem", "PREP");
        addVF("enagiu", "PREP");
        addVF("enamiez", "PREP");
        addVF("enasi", "PREP");
        addVF("enasisum", "PREP");
        addVF("encunter", "PREP");
        addVF("endadens", "PREP");
        addVF("endatier", "PREP");
        addVF("endenter", "PREP");
        addVF("enstagl", "PREP");
        addVF("enta", "PREP");
        addVF("entafuns", "PREP");
        addVF("entagiu", "PREP");
        addVF("entasi", "PREP");
        addVF("entasisum", "PREP");
        addVF("entasum", "PREP");
        addVF("enteifer", "PREP");
        addVF("enten", "PREP");
        addVF("entier", "PREP");
        addVF("entocca", "PREP");
        addVF("entras", "PREP");
        addVF("entuorn", "PREP");
        addVF("enviers", "PREP");

        addVF("fin", "PREP");

        addVF("giu", "PREP");
        addVF("giuaden", "PREP");
        addVF("giubass", "PREP");
        addVF("giud", "PREP");
        addVF("giudapeis", "PREP");
        addVF("giudem", "PREP");
        addVF("giufuns", "PREP");
        addVF("giun", "PREP");
        addVF("giusum", "PREP");
        addVF("giusut", "PREP");
        addVF("giutier", "PREP");
        addVF("giuper", "PREP");

        addVF("leudem", "PREP");
        addVF("malgrad", "PREP");
        addVF("malgrau", "PREP");
        addVF("maneivel", "PREP");
        addVF("muort", "PREP");

        addVF("naven", "PREP");
        addVF("neuagiudem", "PREP");
        addVF("neudem", "PREP");
        addVF("neunsum", "PREP");
        addVF("neusum", "PREP");
        addVF("neutier", "PREP");
        addVF("nunditgont", "PREP");
        addVF("nunimporta", "PREP");

        addVF("o", "PREP");
        addVF("ò", "PREP");
        addVF("or", "PREP");
        addVF("odem", "PREP");
        addVF("on", "PREP");
        addVF("onca", "PREP");
        addVF("ont", "PREP");
        addVF("onsum", "PREP");
        addVF("ora", "PREP");
        addVF("oradem", "PREP");
        addVF("oragiu", "PREP");
        addVF("orasi", "PREP");
        addVF("orasisum", "PREP");
        addVF("orasum", "PREP");
        addVF("orasut", "PREP");
        addVF("ord", "PREP");
        addVF("orda", "PREP");
        addVF("ordado", "PREP");
        addVF("ordadora", "PREP");
        addVF("ordadoragiu", "PREP");
        addVF("ordamiez", "PREP");
        addVF("ordavon", "PREP");
        addVF("ordavos", "PREP");
        addVF("ordeifer", "PREP");
        addVF("ordentuorn", "PREP");
        addVF("ordvart", "PREP");
        addVF("osum", "PREP");

        addVF("partenent", "PREP");
        addVF("persum", "PREP");
        addVF("pertenent", "PREP");
        addVF("pertuccont", "PREP");
        addVF("pervia", "PREP");
        addVF("puncto", "PREP");
        addVF("per", "PREP");
        addVF("pil", "PREP_A");
        addVF("pigl", "PREP_A");
        addVF("pella", "PREP_A");
        addVF("pils", "PREP_A");
        addVF("pellas", "PREP_A");


        addVF("resalvont", "PREP");
        addVF("reservau", "PREP");

        addVF("secund", "PREP");
        addVF("senza", "PREP");
        addVF("siaden", "PREP");
        addVF("siper", "PREP");
        addVF("sisu", "PREP");
        addVF("sisum", "PREP");
        addVF("sper", "PREP");
        addVF("stagl", "PREP");
        addVF("suenter", "PREP");
        addVF("sum", "PREP");
        addVF("sur", "PREP");
        addVF("sura", "PREP");
        addVF("sut", "PREP");
        addVF("si", "PREP");
        addVF("sin", "PREP");
        addVF("sil", "PREP_A");
        addVF("sigl", "PREP_A");
        addVF("silla", "PREP_A");
        addVF("sils", "PREP_A");
        addVF("sillas", "PREP_A");
        addVF("spel", "PREP_A");
        addVF("spegel", "PREP_A");
        addVF("spella", "PREP_A");
        addVF("spels", "PREP_A");
        addVF("spellas", "PREP_A");
        addVF("sul", "PREP_A");
        addVF("sugl", "PREP_A");
        addVF("sulla", "PREP_A");
        addVF("suls", "PREP_A");
        addVF("sullas", "PREP_A");
        addVF("sutta", "PREP_A");

        addVF("tier", "PREP");
        addVF("tiel", "PREP_A");
        addVF("tiegl", "PREP_A");
        addVF("tiella", "PREP_A");
        addVF("tiels", "PREP_A");
        addVF("tiellas", "PREP_A");
        addVF("tenor", "PREP");
        addVF("tenter", "PREP");
        addVF("tiers", "PREP");
        addVF("tochen", "PREP_A");
        addVF("tras", "PREP");
        addVF("trenter", "PREP_A");

        addVF("ultra", "PREP");
        addVF("vi", "PREP_A");
        addVF("via", "PREP");
        addVF("viaden", "PREP_A");
        addVF("viadora", "PREP");
        addVF("viado", "PREP_A");
        addVF("viagiu", "PREP");
        addVF("viasi", "PREP_A");
        addVF("vid", "PREP");
        addVF("vida", "PREP_A");
        addVF("vidad", "PREP");
        addVF("videifer", "PREP_A");
        addVF("videm", "PREP");
        addVF("vivart", "PREP_A");
        addVF("vieifer", "PREP");
        addVF("viers", "PREP_A");
        addVF("visavi", "PREP");
        addVF("visum", "PREP_A");
        addVF("vitier", "PREP");
        addVF("vin", "PREP");


    }


    private void addParticles() {

        //co
        addVF("co", "ADV");
        addVF("cò", "ADV");

        //Gradaprikel

        addVF("absolut", "PART");
        addVF("absolutamein", "PART");
        addVF("nunusitau", "PART");
        addVF("extraordinari", "PART");
        addVF("ordvart", "PART");
        addVF("mender", "PART");
        addVF("pli u meins", "PART");
        addVF("detg", "PART");
        addVF("enorm", "PART");
        addVF("enzatgei", "PART");
        addVF("extrem", "PART");
        addVF("entir", "PART");
        addVF("diltut", "PART");
        addVF("sil pli ault grad", "PART");
        addVF("strusch", "PART");
        addVF("cumplet", "PART");
        addVF("dengrau", "PART");
        addVF("fetg", "PART");
        addVF("zun", "PART");
        addVF("buca aschi strusch", "PART");
        addVF("total", "PART");
        addVF("entiramein", "PART");
        addVF("tuttavia", "PART");
        addVF("ord mesira", "PART");
        addVF("fetg", "PART");
        addVF("nundeg", "PART");
        addVF("entir ed entratg", "PART");
        addVF("cumplettamein", "PART");
        addVF("lunsch", "PART");
        addVF("lontan", "PART");
        addVF("vast", "PART");
        addVF("per lunsch", "PART");
        addVF("lunscho", "PART");
        addVF("ualti", "PART");
        addVF("detg", "PART");
        addVF("memia", "PART");


        //Fokuspartikel

        addVF("era", "PART");
        addVF("perfin", "PART");
        addVF("medemamein", "PART");
        addVF("ual", "PART");
        addVF("gest", "PART");
        addVF("gia", "PART");
        addVF("cunzun", "PART");
        addVF("surtut", "PART");
        addVF("spezialmein", "PART");
        addVF("silpli", "PART");
        addVF("mo", "PART");
        addVF("mò", "PART");
        addVF("sulettamein", "PART");
        addVF("silmeins", "PART");
        addVF("schizun", "PART");
        addVF("surtut", "PART");
        addVF("sil pli pauc", "PART");


        //Abtönungspartikel

        addVF("sulettamein", "PART");
        addVF("pomai", "PART");
        addVF("bein", "PART");
        addVF("gest", "PART");
        addVF("gest uss", "PART");
        addVF("atgnamein", "PART");
        addVF("ver", "PART");
        addVF("matei", "PART");
        addVF("numdadui", "PART");
        addVF("gie", "PART");
        addVF("ga", "PART");
        addVF("senza quitaus", "PART");
        addVF("senz'auter", "PART");
        addVF("insumma", "PART");
        addVF("forsa", "PART");

        //Negationspartikel

        addVF("buc", "PART");
        addVF("mai", "PART");
        addVF("mai e pli mai", "PART");
        addVF("negliu", "PART");
        addVF("maina pli", "PART");

    }

    ;


    private void generateAdjectiveForms(String entry) {

        String ending = entry.substring(entry.length() - 2);
        if (ending.equals("er") || ending.equals("el") || ending.equals("en")) {
            String stamm = entry.substring(0, entry.length() - 2);
            char last = ending.charAt(1);
            addVF(stamm + last + "a", "ADJ");
            addVF(stamm + last + "as", "ADJ");
            addVF(stamm + last + "amein", "ADV");
            return;
        }
        // regelmässige Form
        addVF(entry + "a", "ADJ");
        addVF(entry + "s", "ADJ");
        addVF(entry + "as", "ADJ");
        if (ending.equals("ar") || ending.equals("al")) {
            addVF(entry + "mein", "ADV");
            return;
        }
        addVF(entry + "a" + "mein", "ADV");

        //
        addVF("bien", "ADJ");

    }

    public void generateVerbForms(String inf, String nvs_pos) {
        addVF(inf, "V_GVRB");
        VerbClass verbClass;
        String stamm = inf.substring(0, inf.length() - 2);
        String ending = inf.substring(inf.length() - 2);
        if (ending.equals("ar")) {
            verbClass = VerbClass.AR;
        } else if (ending.equals("ir")) {
            verbClass = VerbClass.IR;
        } else if (ending.equals("er")) {
            verbClass = VerbClass.ER;
        } else {
            System.out.println("falsche Verb-endung: " + inf);
            return;
        }
        addConjugations(stamm, verbClass);

        if (nvs_pos.equals("refl")) {
            if (startsWithVocal(inf)) {
                addVF("ses" + inf, "V_GVRB");
                addConjugations("ses" + stamm, verbClass);
            } else {
                addVF("se" + inf, "V_GVRB");
                addConjugations("se" + stamm, verbClass);
            }
        }
    }

    private void addConjugations(String stamm, VerbClass verbClass) {
        //Partizip
        addPartizip(stamm, verbClass);
        //Gerundium
        addGerundium(stamm, verbClass, "V_GVRB");
        //Imperativ
        addImperativ(stamm, verbClass, "V_GVRB");
        //Indikativ Präsens
        addIndikativPräsens(stamm, verbClass, "V_GVRB");
        //Indikativ Imperfekt
        addIndikativImperfekt(stamm, verbClass, "V_GVRB");
        //konjunktiv präsens
        addKonjunktivPräsens(stamm, verbClass, "V_GVRB");
        //konjunktiv imperfekt
        addKonjunktivImperfekt(stamm, verbClass, "V_GVRB");
        //koditional präsens direkt
        addKonditionalDirekt(stamm, verbClass, "V_GVRB");
        //konditional präsens indirekt
        addKonditionalIndirekt(stamm, verbClass, "V_GVRB");
    }

    private void addKonditionalIndirekt(String stamm, VerbClass verbClass, String pos) {
        if (verbClass == VerbClass.AR) {
            addVF(stamm + "assi", pos);
            addVF(stamm + "assies", pos);
            addVF(stamm + "assien", pos);
        } else {
            addVF(stamm + "essi", pos);
            addVF(stamm + "essies", pos);
            addVF(stamm + "essien", pos);
        }
    }

    private void addKonditionalDirekt(String stamm, VerbClass verbClass, String pos) {
        if (verbClass == VerbClass.AR) {
            addVF(stamm + "ass", pos);
            addVF(stamm + "asses", pos);
            addVF(stamm + "assen", pos);
        } else {
            addVF(stamm + "ess", pos);
            addVF(stamm + "esses", pos);
            addVF(stamm + "essen", pos);
        }
    }

    private void addKonjunktivImperfekt(String stamm, VerbClass verbClass, String pos) {
        if (verbClass == VerbClass.AR) {
            addVF(stamm + "avi", pos);
            addVF(stamm + "avies", pos);
            addVF(stamm + "avien", pos);
        } else {
            addVF(stamm + "evi", pos);
            addVF(stamm + "evies", pos);
            addVF(stamm + "evien", pos);
        }
    }

    private void addKonjunktivPräsens(String stamm, VerbClass verbClass, String pos) {
        if (verbClass.equals(VerbClass.ER)) {
            addVF(stamm + "i", pos);
            addVF(stamm + "ies", pos);
            addVF(stamm + "eien", pos);
            addVF(stamm + "eies", pos);
            addVF(stamm + "ien", pos);
        } else {
            addVF(stamm + "i", pos);
            addVF(stamm + "ies", pos);
            addVF(stamm + "îen", pos);
            addVF(stamm + "îes", pos);
            addVF(stamm + "ien", pos);
        }
    }

    private void addIndikativImperfekt(String stamm, VerbClass verbClass, String pos) {
        if (verbClass == VerbClass.AR) {
            addVF(stamm + "avel", pos);
            addVF(stamm + "avas", pos);
            addVF(stamm + "ava", pos);
            addVF(stamm + "avan", pos);
        } else {
            addVF(stamm + "evel", pos);
            addVF(stamm + "evas", pos);
            addVF(stamm + "eva", pos);
            addVF(stamm + "evan", pos);
        }
    }

    private void addIndikativPräsens(String stamm, VerbClass verbClass, String pos) {
        addVF(stamm + "el", pos);
        addVF(stamm + "as", pos);
        addVF(stamm + "a", pos);
        if (verbClass == VerbClass.IR) {
            addVF(stamm + "in", pos);
            addVF(stamm + "is", pos);
            addVF(stamm + "an", pos);
        } else {
            addVF(stamm + "ein", pos);
            addVF(stamm + "eis", pos);
            addVF(stamm + "an", pos);
        }
    }

    private void addImperativ(String stamm, VerbClass verbClass, String pos) {
        addVF(stamm + "a", pos);
        addVF(stamm + "ien", pos);
        if (verbClass == VerbClass.IR) {
            addVF(stamm + "i", pos);
        } else {
            addVF(stamm + "eien", pos);
            addVF(stamm + "ei", pos);
        }
    }

    private void addGerundium(String stamm, VerbClass verbClass, String pos) {
        if (verbClass == VerbClass.AR) {
            addVF(stamm + "ond", pos);
        } else {
            addVF(stamm + "end", pos);
        }
    }

    private void addPartizip(String stamm, VerbClass verbClass) {
        if (verbClass == VerbClass.AR) {
            addVF(stamm + "au", "V_PP");
            addVF(stamm + "ada", "V_PP");
            addVF(stamm + "ai", "V_PP");
            addVF(stamm + "adas", "V_PP");
        } else {
            addVF(stamm + "iu", "V_PP");
            addVF(stamm + "ida", "V_PP");
            addVF(stamm + "i", "V_PP");
            addVF(stamm + "idas", "V_PP");
        }
    }

    private void generateSubstantiveForms(String singular) {
        String plural;
        char last = singular.charAt(singular.length() - 1);
        //unregelmäßige Plurale auf Grund lautlicher Entwicklung
        if (last == 'i') {
            //Betontes "i" nach "sch" wird zu "als"
            if (singular.endsWith("schi")) {
                plural = new String(
                        singular.substring(0, singular.length() - 2) + "als");
            }
            //betontes "i" wird zu "ials"
            else {
                plural = new String(singular + "als");
                addVF(plural, "NN");
            }
        }
        if (singular.contains("ie")) {
            //betontes "ie" wird zu "o"
            plural = new String(singular.replace("ie", "o"));
            addVF(plural + "s", "NN");
            //betontes "ie" wird zu "ia"
            plural = singular.replace("ie", "ia");
            addVF(plural + "s", "NN");
            //betontes "ie" wird zu eu
            plural = singular.replace("ie", "eu");
            addVF(plural + "s", "NN");
        }
        //regelmäßie und unregelmäßige Plurale erhalten ein "s" sofern sie nicht schon im Singular eins haben
        if (last != 's') {
            plural = new String(singular + "s");
            addVF(plural, "NN");
        }
        // unbest. koll. Mehrzahl
        if (last != 'a') {
            if (last == 'p') {
                singular = singular + "p";
            }
            plural = new String(singular + "a");
            addVF(plural, "NN");
            addVF(plural + "s", "NN");
        }

    }

    private void addIrregularSubstantives() {
        addVF("um", "NN");
        addVF("umens", "NN");
        addVF("dunna", "NN");
        addVF("dunnauns", "NN");
        addVF("matta", "NN");
        addVF("mattauns", "NN");
        addVF("liug", "NN");
        addVF("loghens", "NN");
        addVF("cavegl", "NN");
        addVF("cavels", "NN");
        addVF("cavagl", "NN");
        addVF("cavals", "NN");
        addVF("tretgal", "NN");
        addVF("tretgauls", "NN");
        addVF("armal", "NN");
        addVF("armauls", "NN");
        addVF("deputau", "NN");
        addVF("deputai", "NN");
        addVF("deputaus", "NN");
    }

    private void addArticles() {
        //bestimmte Artikel
        addVF("il", "ART");
        addVF("igl", "ART");
        addVF("gl'", "ART");
        addVF("ils", "ART");
        addVF("la", "ART");
        addVF("l'", "ART");
        addVF("las", "ART");

        //unbestimmte Artikel
        addVF("in", "ART");
        addVF("ina", "ART");
        addVF("un", "ART");
        addVF("una'", "ART");
        addVF("ün", "ART");
        addVF("üna", "ART");

    }

    private void addPronouns() {
        // Personalproomen
        addVF("jeu", "PRON_PER");
        addVF("ieu", "PRON_PER");

        addVF("ti", "PRON_PER");
        addVF("el", "PRON_PER");
        addVF("ella", "PRON_PER");
        addVF("nus", "PRON_PER");
        addVF("vus", "PRON_PER");
        addVF("els", "PRON_PER");
        addVF("ellas", "PRON_PER");
        addVF("els", "PRON_PER");
        addVF("mez", "PRON_PER");
        addVF("nusauters", "PRON_PER");
        addVF("nusautri", "PRON_PER");
        addVF("mei", "PRON_PER");
        addVF("tei", "PRON_PER");
        addVF("nusezs", "PRON_PER");
        addVF("nusezzi", "PRON_PER");
        addVF("mi", "PRON_PER");
        addVF("ti", "PRON_PER");
        addVF("agli", "PRON_PER");
        addVF("ali", "PRON_PER");
        addVF("sesez", "PRON_PER");
        addVF("memez", "PRON_PER");
        addVF("sez", "PRON_PER");
        addVF("'l", "PRON_PER");
        addVF("la", "PRON_PER");
        addVF("ei", "PRON_PER");
        addVF("las", "PRON_PER");
        addVF("'i", "PRON_PER");
        addVF("ei", "PRON_PER");
        addVF("gli", "PRON_PER");
        addVF("ta", "PRON_PER");
        addVF("te", "PRON_PER");
        addVF("tetez", "PRON_PER");
        addVF("tez", "PRON_PER");
        addVF("tgi", "PRON_PER");
        addVF("vusauters", "PRON_PER");
        addVF("vusautri", "PRON_PER");
        addVF("vusezs", "PRON_PER");

        // Possesivpronomen
        addVF("miu", "PRON_POS");
        addVF("tiu", "PRON_POS");
        addVF("siu", "PRON_POS");
        addVF("nies", "PRON_POS");
        addVF("vies", "PRON_POS");
        addVF("lur", "PRON_POS");
        addVF("mia", "PRON_POS");
        addVF("tia", "PRON_POS");
        addVF("sia", "PRON_POS");
        addVF("nossa", "PRON_POS");
        addVF("vossa", "PRON_POS");
        addVF("mes", "PRON_POS");
        addVF("mias", "PRON_POS");
        addVF("tes", "PRON_POS");
        addVF("tias", "PRON_POS");
        addVF("ses", "PRON_POS");
        addVF("sias", "PRON_POS");
        addVF("nos", "PRON_POS");
        addVF("noss", "PRON_POS");
        addVF("nossas", "PRON_POS");
        addVF("vos", "PRON_POS");
        addVF("nvossas", "PRON_POS");

        // Demonstrativpronomen
        addVF("custi", "PRON_DEM");
        addVF("quest", "PRON_DEM");
        addVF("questa", "PRON_DEM");
        addVF("quests", "PRON_DEM");
        addVF("questas", "PRON_DEM");
        addVF("quei", "PRON_DEM");
        addVF("quel", "PRON_DEM");
        addVF("quella", "PRON_DEM");
        addVF("quels", "PRON_DEM");
        addVF("quellas", "PRON_DEM");
        addVF("tal", "PRON_DEM");
        addVF("tschei", "PRON_DEM");
        addVF("tschella", "PRON_DEM");
        addVF("tschels", "PRON_DEM");
        addVF("tschel", "PRON_DEM");
        addVF("tschellas", "PRON_DEM");
        addVF("gliez", "PRON_DEM");
        addVF("lez", "PRON_DEM");
        addVF("lezza", "PRON_DEM");
        addVF("lezs", "PRON_DEM");
        addVF("lezzas", "PRON_DEM");
        addVF("sias", "PRON_DEM");
        addVF("nos", "PRON_DEM");
        addVF("nossas", "PRON_DEM");
        addVF("vos", "PRON_DEM");
        addVF("nvossas", "PRON_DEM");

        // Relativpronomen
        addVF("che", "PRON_REL");
        addVF("qual", "PRON_REL");
        addVF("quala", "PRON_REL");
        addVF("quals", "PRON_REL");
        addVF("qualas", "PRON_REL");
        addVF("quel", "PRON_REL");
        addVF("quei", "PRON_REL");

        // Interrogativpronomen
        addVF("tgi", "PRON_IES");
        addVF("tgei", "PRON_IES");
        addVF("tgeinin", "PRON_IES");
        addVF("tgeinina", "PRON_IES");
        addVF("tgeinins", "PRON_IES");
        addVF("tgeininas", "PRON_IES");

        // Indefinite Pronomen
        addVF("ei", "PRON_IND");
        addVF("igl", "PRON_IND");
        addVF("ins", "PRON_IND");
        addVF("mintga", "PRON_IND");
        addVF("enzatgei", "PRON_IND");
        addVF("enzatgi", "PRON_IND");
        addVF("nuot", "PRON_IND");
        addVF("auter", "PRON_IND");
        addVF("autra", "PRON_IND");
        addVF("auters", "PRON_IND");
        addVF("autras", "PRON_IND");
        addVF("beinenqual", "PRON_IND");
        addVF("beinenqualin", "PRON_IND");
        addVF("beinenqualtgin", "PRON_IND");
        addVF("bia", "PRON_IND");
        addVF("biar", "PRON_IND");
        addVF("biara", "PRON_IND");
        addVF("biars", "PRON_IND");
        addVF("biaras", "PRON_IND");
        addVF("certin", "PRON_IND");
        addVF("certins", "PRON_IND");
        addVF("certinas", "PRON_IND");
        addVF("con", "PRON_IND");
        addVF("conta", "PRON_IND");
        addVF("cons", "PRON_IND");
        addVF("contas", "PRON_IND");
        addVF("dabia", "PRON_IND");
        addVF("debia", "PRON_IND");
        addVF("divers", "PRON_IND");
        addVF("ei", "PRON_IND");
        addVF("enqual", "PRON_IND");
        addVF("enquala", "PRON_IND");
        addVF("enqualas", "PRON_IND");
        addVF("enqualin", "PRON_IND");
        addVF("enqualtgin", "PRON_IND");
        addVF("enquals", "PRON_IND");
        addVF("enqualche", "PRON_IND");
        addVF("entgin", "PRON_IND");
        addVF("entgins", "PRON_IND");
        addVF("entgina", "PRON_IND");
        addVF("antginas", "PRON_IND");
        addVF("enzacons", "PRON_IND");
        addVF("enzacontas", "PRON_IND");
        addVF("enzatgei", "PRON_IND");
        addVF("enzatgi", "PRON_IND");
        addVF("enzatgeinins", "PRON_IND");
        addVF("finadin", "PRON_IND");
        addVF("finadina", "PRON_IND");
        addVF("inaparts", "PRON_IND");
        addVF("ins", "PRON_IND");
        addVF("inton", "PRON_IND");
        addVF("medem", "PRON_IND");
        addVF("medema", "PRON_IND");
        addVF("medems", "PRON_IND");
        addVF("medemas", "PRON_IND");
        addVF("mintga", "PRON_IND");
        addVF("mintgin", "PRON_IND");
        addVF("mintgina", "PRON_IND");
        addVF("naparts", "PRON_IND");
        addVF("negin", "PRON_IND");
        addVF("negina", "PRON_IND");
        addVF("negins", "PRON_IND");
        addVF("neginas", "PRON_IND");
        addVF("omisdus", "PRON_IND");
        addVF("omisduas", "PRON_IND");
        addVF("omidua", "PRON_IND");
        addVF("pauc", "PRON_IND");
        addVF("pauca", "PRON_IND");
        addVF("paucs", "PRON_IND");
        addVF("paucas", "PRON_IND");
        addVF("plirs", "PRON_IND");
        addVF("pliras", "PRON_IND");
        addVF("scadin", "PRON_IND");
        addVF("tal", "PRON_IND");
        addVF("tala", "PRON_IND");
        addVF("tals", "PRON_IND");
        addVF("talas", "PRON_IND");
        addVF("tanien", "PRON_IND");
        addVF("tanienta", "PRON_IND");
        addVF("taniens", "PRON_IND");
        addVF("tanientas", "PRON_IND");
        addVF("ton", "PRON_IND");
        addVF("tut", "PRON_IND");
        addVF("tutta", "PRON_IND");
        addVF("tuts", "PRON_IND");
        addVF("tuttas", "PRON_IND");
        addVF("entir", "PRON_IND");
        addVF("entira", "PRON_IND");
        addVF("zacons", "PRON_IND");
        addVF("zatgei", "PRON_IND");
    }

    private void addNumbers() {
        //Ordinalzahlen
        addVF("emprem", "ADJ_NUM");
        addVF("emprema", "ADJ_NUM");
        addVF("emprems", "ADJ_NUM");
        addVF("empremas", "ADJ_NUM");
        addVF("secund", "ADJ_NUM");
        addVF("secunda", "ADJ_NUM");
        addVF("secundas", "ADJ_NUM");
        addVF("secunds", "ADJ_NUM");
        addVF("tierz", "ADJ_NUM");
        addVF("tiarzs", "ADJ_NUM");
        addVF("tiarza", "ADJ_NUM");
        addVF("tiarzas", "ADJ_NUM");
        addVF("quart", "ADJ_NUM");
        addVF("quarta", "ADJ_NUM");
        addVF("quarts", "ADJ_NUM");
        addVF("quartas", "ADJ_NUM");
        addVF("quint", "ADJ_NUM");
        addVF("quinta", "ADJ_NUM");
        addVF("quints", "ADJ_NUM");
        addVF("quintas", "ADJ_NUM");
        addVF("tschunavel", "ADJ_NUM");
        addVF("tschunavela", "ADJ_NUM");
        addVF("tschunavels", "ADJ_NUM");
        addVF("tschunavelas", "ADJ_NUM");
        addVF("sisavel", "ADJ_NUM");
        addVF("sisavla", "ADJ_NUM");
        addVF("sisavels", "ADJ_NUM");
        addVF("sivavlas", "ADJ_NUM");
        addVF("dieschavel", "ADJ_NUM");
        addVF("dieschavla", "ADJ_NUM");
        addVF("dieschavels", "ADJ_NUM");
        addVF("dieschavlas", "ADJ_NUM");
        addVF("vegnavel", "ADJ_NUM");
        addVF("vegnalva", "ADJ_NUM");
        addVF("vegnavels", "ADJ_NUM");
        addVF("vegnalvas", "ADJ_NUM");

        //Numerale
        addVF("in", "C_NUM");
        addVF("ina", "C_NUM");
        addVF("duas", "C_NUM");
        addVF("dus", "C_NUM");
        addVF("dua", "C_NUM");
        addVF("treis", "C_NUM");
        addVF("trei", "C_NUM");
        addVF("ina", "C_NUM");
        addVF("quater", "C_NUM");
        addVF("tschun", "C_NUM");
        addVF("sis", "C_NUM");
        addVF("siat", "C_NUM");
        addVF("otg", "C_NUM");
        addVF("nov", "C_NUM");
        addVF("diesch", "C_NUM");
        addVF("endisch", "C_NUM");
        addVF("dudisch", "C_NUM");
        addVF("tredisch", "C_NUM");
        addVF("quitordisch", "C_NUM");
        addVF("quendisch", "C_NUM");
        addVF("sedisch", "C_NUM");
        addVF("gissiat", "C_NUM");
        addVF("schotg", "C_NUM");
        addVF("scheniv", "C_NUM");
        addVF("vegn", "C_NUM");
        addVF("ventgin", "C_NUM");
        addVF("ventgadus", "C_NUM");
        addVF("ventgatreis", "C_NUM");
        addVF("curonta", "C_NUM");
        addVF("sissonta", "C_NUM");
        addVF("tschien", "C_NUM");
        addVF("duatschien", "C_NUM");
        addVF("treitschien", "C_NUM");
        addVF("melli", "C_NUM");
        addVF("duamelli", "C_NUM");
        addVF("treimelli", "C_NUM");
        addVF("milliun", "C_NUM");
        addVF("ventgadus", "C_NUM");
        addVF("ventgatreis", "C_NUM");
        addVF("curonta", "C_NUM");
        addVF("sissonta", "C_NUM");
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

    private void addIrregularVerbs() {
        String stamm;

        //HAVER
        //infinitivformen
        addVF("ver", "V_AVAIR");
        addVF("haver", "V_AVAIR");
        // partizip perfekt
        addVF("giu", "V_AVAIR");
        // gerundium
        addGerundium("hav", VerbClass.ER, "V_AVAIR");
        // Imperativ
        addVF("hagies", "V_AVAIR");
        addVF("veies", "V_AVAIR");
        addVF("haveies", "V_AVAIR");
        addVF("hagien", "V_AVAIR");
        // Indikativ Präsens
        addVF("hai", "V_AVAIR");
        addVF("vai", "V_AVAIR");
        addVF("has", "V_AVAIR");
        addVF("ha", "V_AVAIR");
        addVF("havein", "V_AVAIR");
        addVF("vein", "V_AVAIR");
        addVF("haveis", "V_AVAIR");
        addVF("veis", "V_AVAIR");
        addVF("han", "V_AVAIR");
        // Indikativ Imperfekt
        addIndikativImperfekt("hav", VerbClass.ER, "V_AVAIR");
        addIndikativImperfekt("h", VerbClass.ER, "V_AVAIR");
        // Konjunktiv Präsens
        addVF("hagi", "V_AVAIR");
        addVF("hagies", "V_AVAIR");
        addVF("haveien", "V_AVAIR");
        addVF("veien", "V_AVAIR");
        addVF("haveies", "V_AVAIR");
        addVF("veies", "V_AVAIR");
        addVF("hagien", "V_AVAIR");
        // Konjunktiv Imperfekt
        addKonjunktivImperfekt("hav", VerbClass.ER, "V_AVAIR");
        addKonjunktivImperfekt("v", VerbClass.ER, "V_AVAIR");
        // konditional präsens direkt
        addKonditionalDirekt("hav", VerbClass.ER, "V_AVAIR");
        addKonditionalDirekt("v", VerbClass.ER, "V_AVAIR");
        // konditional präsens indirekt
        addKonditionalIndirekt("hav", VerbClass.ER, "V_AVAIR");
        addKonditionalIndirekt("v", VerbClass.ER, "V_AVAIR");

        //ESSER
        //infinitiv
        addVF("esser", "V_ESSER");
        // partizip perfekt
        addVF("staus", "V_ESSER");
        addVF("stau", "V_ESSER");
        addVF("stada", "V_ESSER");
        addVF("stai", "V_ESSER");
        addVF("stadas", "V_ESSER");
        // gerundium
        addGerundium("ess", VerbClass.ER, "V_ESSER");
        // imperativ
        addVF("seigies", "V_ESSER");
        addVF("seigien", "V_ESSER");
        // indikativ präsens
        addVF("sun", "V_ESSER");
        addVF("eis", "V_ESSER");
        addVF("ei", "V_ESSER");
        addVF("essan", "V_ESSER");
        addVF("assas", "V_ESSER");
        addVF("ein", "V_ESSER");
        // indikativ imperfekt
        addVF("erel", "V_ESSER");
        addVF("eras", "V_ESSER");
        addVF("era", "V_ESSER");
        addVF("eran", "V_ESSER");
        addVF("fuvel", "V_ESSER");
        addVF("fuvas", "V_ESSER");
        addVF("fuva", "V_ESSER");
        addVF("fuvan", "V_ESSER");
        // konjunktiv präsens
        addVF("seigi", "V_ESSER");
        addVF("seigies", "V_ESSER");
        addVF("seigien", "V_ESSER");
        // konjunktiv imperfekt
        addVF("eri", "V_ESSER");
        addVF("eries", "V_ESSER");
        addVF("erien", "V_ESSER");
        addVF("fuvi", "V_ESSER");
        addVF("fuvies", "V_ESSER");
        addVF("fuvien", "V_ESSER");
        // konditional präsens direkt
        addVF("fuss", "V_ESSER");
        addVF("fusses", "V_ESSER");
        addVF("fussen", "V_ESSER");
        // konditional präsens indirekt
        addVF("fussi", "V_ESSER");
        addVF("fussies", "V_ESSER");
        addVF("fussien", "V_ESSER");

        //VEGNIR
        stamm = "vegn";
        //infinitiv
        addVF("vegnir", "V_GVRB");
        // kurzformen
        addVF("gnir", "V_GVRB");
        addVF("gnin", "V_GVRB");
        addVF("gnis", "V_GVRB");
        addVF("gneva", "V_GVRB");
        addVF("gnius", "V_GVRB");
        // partizip perfekt
        addVF(stamm + "ius", "V_PP");
        addPartizip(stamm, VerbClass.IR);
        // gerundium
        addGerundium(stamm, VerbClass.IR, "V_GVRB");
        // imperativ
        addVF(stamm + "eu", "V_GVRB");
        addVF(stamm + "îen", "V_GVRB");
        addVF(stamm + "i", "V_GVRB");
        addVF(stamm + "ien", "V_GVRB");
        // indikativ präsens
        addVF(stamm + "el", "V_GVRB");
        addVF(stamm, "V_GVRB");
        addVF(stamm + "as", "V_GVRB");
        addVF(stamm + "s", "V_GVRB");
        addVF(stamm + "in", "V_GVRB");
        addVF(stamm + "is", "V_GVRB");
        addVF(stamm + "an", "V_GVRB");
        // indikativ imperfekt
        addIndikativImperfekt(stamm, VerbClass.IR, "V_GVRB");
        // konjunktiv präsens
        addKonjunktivPräsens(stamm, VerbClass.IR, "V_GVRB");
        // konjunktiv imperfekt
        addKonjunktivImperfekt(stamm, VerbClass.IR, "V_GVRB");
        // konditional präsens direkt
        addVF(stamm + "essel", "V_GVRB");
        addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");
        // konditional präsens indirekt
        addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");

        //CRER
        stamm = "cart";
        //infinitiv
        addVF("crer", "V_GVRB");
        // partizip perfekt
        addVF(stamm + "iu", "V_PP");
        // gerundium
        addGerundium(stamm, VerbClass.ER, "V_GVRB");
        // imperativ
        addVF("crei", "V_GVRB");
        addVF(stamm + "ei", "V_GVRB");
        // indikativ präsens
        addVF("creiel", "V_GVRB");
        addVF("creigel", "V_GVRB");
        addVF("creis", "V_GVRB");
        addVF("creias", "V_GVRB");
        addVF("crei", "V_GVRB");
        addVF(stamm + "ein", "V_GVRB");
        addVF(stamm + "eis", "V_GVRB");
        addVF("crein", "V_GVRB");
        addVF("creian", "V_GVRB");
        // indikativ imperfekt
        addIndikativImperfekt(stamm, VerbClass.ER, "V_GVRB");
        // konjunktiv präsens
        addVF("creigi", "V_GVRB");
        addVF("creigies", "V_GVRB");
        addVF(stamm + "eien", "V_GVRB");
        addVF(stamm + "eies", "V_GVRB");
        addVF("creigien", "V_GVRB");
        // konjunktiv imperfekt
        addKonjunktivImperfekt(stamm, VerbClass.ER, "V_GVRB");
        // konditional präsens direkt
        addKonditionalDirekt(stamm, VerbClass.ER, "V_GVRB");
        // konditional präsens indirekt
        addKonditionalIndirekt(stamm, VerbClass.ER, "V_GVRB");

        //CUER
        //infinitiv
        addVF("cuer", "V_GVRB");
        // partizip perfekt
        addVF("cotg", "V_PP");
        // gerundium
        addGerundium("cug", VerbClass.ER, "V_GVRB");
        // imperativ
        addVF("coi", "V_GVRB");
        addVF("cui", "V_GVRB");
        addVF("cugei", "V_GVRB");
        // indikativ präsens
        addVF("coiel", "V_GVRB");
        addVF("coias", "V_GVRB");
        addVF("cois", "V_GVRB");
        addVF("coi", "V_GVRB");
        addVF("cuin", "V_GVRB");
        addVF("cuis", "V_GVRB");
        addVF("coian", "V_GVRB");
        addVF("coin", "V_GVRB");
        addVF("cueiel", "V_GVRB");
        addVF("cueias", "V_GVRB");
        addVF("cueis", "V_GVRB");
        addVF("cuei", "V_GVRB");
        addVF("cugein", "V_GVRB");
        addVF("cugeis", "V_GVRB");
        addVF("cueian", "V_GVRB");
        addVF("cuein", "V_GVRB");
        // Indikativ imperfekt
        addIndikativImperfekt("cug", VerbClass.ER, "V_GVRB");
        // konjunktiv präsens
        addVF("coigi", "V_GVRB");
        addVF("coigies", "V_GVRB");
        addVF("cuîen", "V_GVRB");
        addVF("cuîes", "V_GVRB");
        addVF("coigien", "V_GVRB");
        addVF("cueigi", "V_GVRB");
        addVF("cueigies", "V_GVRB");
        addVF("cugeien", "V_GVRB");
        addVF("cugeies", "V_GVRB");
        addVF("cueigien", "V_GVRB");
        // konjunktiv imperfekt
        addKonjunktivImperfekt("cug", VerbClass.ER, "V_GVRB");
        // konditional präsens direkt
        addKonditionalDirekt("cug", VerbClass.ER, "V_GVRB");
        // konditional präsens indirekt
        addKonditionalIndirekt("cug", VerbClass.ER, "V_GVRB");

        //DAR
        //infinitiv
        addVF("dar", "V_GVRB");
        // partizp perfekt
        addVF("dau", "V_PP");
        addVF("daus", "V_PP");
        addVF("dada", "V_PP");
        addVF("dai", "V_PP");
        addVF("dadas", "V_PP");
        // gerundium
        addGerundium("d", VerbClass.AR, "V_GVRB");
        addGerundium("d", VerbClass.IR, "V_GVRB");
        // imperativ
        addVF("dai", "V_GVRB");
        addVF("dei", "V_GVRB");
        // indikativ präsens
        addVF("dun", "V_GVRB");
        addVF("dundel", "V_GVRB");
        addVF("das", "V_GVRB");
        addVF("dat", "V_GVRB");
        addVF("dein", "V_GVRB");
        addVF("deis", "V_GVRB");
        addVF("dattan", "V_GVRB");
        // indikativ imperfekt
        addIndikativImperfekt("d", VerbClass.AR, "V_GVRB");
        addIndikativImperfekt("d", VerbClass.ER, "V_GVRB");
        // konjunktiv präsens
        addVF("detti", "V_GVRB");
        addVF("detties", "V_GVRB");
        addVF("deien", "V_GVRB");
        addVF("deies", "V_GVRB");
        addVF("dettien", "V_GVRB");
        // Konjunktiv imperfekt
        addKonjunktivImperfekt("d", VerbClass.AR, "V_GVRB");
        addKonjunktivImperfekt("d", VerbClass.ER, "V_GVRB");
        // Konditional präsens direkt
        addKonditionalDirekt("d", VerbClass.AR, "V_GVRB");
        addKonditionalDirekt("d", VerbClass.ER, "V_GVRB");
        // konditional präsens indirekt
        addKonditionalIndirekt("d", VerbClass.AR, "V_GVRB");
        addKonditionalIndirekt("d", VerbClass.ER, "V_GVRB");

        //DIR
        //infinitiv
        addVF("dir", "V_GVRB");
        // partizip perfekt
        addVF("detg", "V_PP");
        // gerundium
        addGerundium("sch", VerbClass.IR, "V_GVRB");
        // imperativ
        addVF("di", "V_GVRB");
        addVF("schei", "V_GVRB");
        // indikativ präsnes
        addVF("ditgel", "V_GVRB");
        addVF("dias", "V_GVRB");
        addVF("dis", "V_GVRB");
        addVF("di", "V_GVRB");
        addVF("schein", "V_GVRB");
        addVF("scheis", "V_GVRB");
        addVF("dian", "V_GVRB");
        addVF("din", "V_GVRB");
        // indikativ imperfekt
        addIndikativImperfekt("sch", VerbClass.IR, "V_GVRB");
        // konjunktiv präsens
        addVF("ditgi", "V_GVRB");
        addVF("ditgies", "V_GVRB");
        addVF("scheien", "V_GVRB");
        addVF("scheies", "V_GVRB");
        addVF("ditgien", "V_GVRB");
        // konjunktiv imperfekt
        addKonjunktivImperfekt("sch", VerbClass.IR, "V_GVRB");
        // konditional präsens direkt
        addKonditionalDirekt("sch", VerbClass.IR, "V_GVRB");
        // konditional präsens indirekt
        addKonditionalIndirekt("sch", VerbClass.IR, "V_GVRB");

        //FAR
        //infinitiv
        addVF("far", "V_GVRB");
        // partizip perfekt
        addVF("fatg", "V_PP");
        // gerundium
        addGerundium("fag", VerbClass.ER, "V_GVRB");
        // imperativ
        addVF("fai", "V_GVRB");
        addVF("fagei", "V_GVRB");
        // indikativ präsens
        addVF("fetsch", "V_GVRB");
        addVF("fetschel", "V_GVRB");
        addVF("fas", "V_GVRB");
        addVF("fa", "V_GVRB");
        addVF("fagein", "V_GVRB");
        addVF("fageis", "V_GVRB");
        addVF("fan", "V_GVRB");
        // indikativ imperfekt
        addIndikativImperfekt("fag", VerbClass.ER, "V_GVRB");
        // konjunktiv präsens
        addVF("fetschi", "V_GVRB");
        addVF("fetschies", "V_GVRB");
        addVF("fageien", "V_GVRB");
        addVF("fageies", "V_GVRB");
        addVF("fetschien", "V_GVRB");
        // konjunktiv imperfekt
        addKonjunktivImperfekt("fag", VerbClass.ER, "V_GVRB");
        // konditional präsens direkt
        addKonditionalDirekt("fag", VerbClass.ER, "V_GVRB");
        // konditional präsens indirekt
        addKonditionalIndirekt("fag", VerbClass.ER, "V_GVRB");

        //FUGIR
        //infinitiv
        addVF("fugir", "V_GVRB");
        // partizip perfekt
        addVF("fugiu", "V_PP");
        // partizip präsens
        addVF("fugent", "V_PP");
        // gerundium
        addGerundium("fug", VerbClass.IR, "V_GVRB");
        // imperativ
        addVF("fui", "V_GVRB");
        addVF("fugi", "V_GVRB");
        // indikativ präsens
        addVF("fuiel", "V_GVRB");
        addVF("fuias", "V_GVRB");
        addVF("fuis", "V_GVRB");
        addVF("fui", "V_GVRB");
        addVF("fugin", "V_GVRB");
        addVF("fugis", "V_GVRB");
        addVF("fuian", "V_GVRB");
        addVF("fuin", "V_GVRB");
        // indikativ imperfekt
        addIndikativImperfekt("fug", VerbClass.IR, "V_GVRB");
        // konjunktiv präsens
        addVF("fuigi", "V_GVRB");
        addVF("fuigies", "V_GVRB");
        addVF("fugîen", "V_GVRB");
        addVF("fugîes", "V_GVRB");
        addVF("fuigien", "V_GVRB");
        // konjunktiv imperfekt
        addKonjunktivImperfekt("fug", VerbClass.IR, "V_GVRB");
        // konditional präsens direkt
        addKonditionalDirekt("fug", VerbClass.ER, "V_GVRB");
        // konditional präsens indirekt
        addKonditionalIndirekt("fug", VerbClass.ER, "V_GVRB");

        //IR
        //infinitiv
        addVF("ir", "V_GVRB");
        // partizip perfekt
        addVF("iu", "V_PP");
        addVF("ida", "V_PP");
        addVF("i", "V_PP");
        addVF("idas", "V_PP");
        // gerundium
        addGerundium("m", VerbClass.AR, "V_GVRB");
        // imperativ
        addVF("va", "V_GVRB");
        addVF("mei", "V_GVRB");
        // indikativ präsens
        addVF("mon", "V_GVRB");
        addVF("mondel", "V_GVRB");
        addVF("vom", "V_GVRB");
        addVF("vomel", "V_GVRB");
        addVF("vas", "V_GVRB");
        addVF("va", "V_GVRB");
        addVF("mein", "V_GVRB");
        addVF("meis", "V_GVRB");
        addVF("van", "V_GVRB");
        // indikativ imperfekt
        addIndikativImperfekt("m", VerbClass.AR, "V_GVRB");
        // konjunktiv präsens
        addVF("mondi", "V_GVRB");
        addVF("mondies", "V_GVRB");
        addVF("meien", "V_GVRB");
        addVF("meies", "V_GVRB");
        addVF("mondien", "V_GVRB");
        addVF("vomi", "V_GVRB");
        addVF("vomies", "V_GVRB");
        addVF("vomien", "V_GVRB");
        // konjunktiv imperfekt
        addKonjunktivImperfekt("m", VerbClass.AR, "V_GVRB");
        // konditional präsens direkt
        addKonditionalDirekt("m", VerbClass.AR, "V_GVRB");
        // konditional präsens indirekt
        addKonditionalIndirekt("m", VerbClass.AR, "V_GVRB");

        //PLASCHER
        stamm = "plasch";
        //infinitiv
        addVF("plascher", "V_GVRB");
        // partizip perfekt
        addVF(stamm + "iu", "V_PP");
        // gerundium
        addGerundium(stamm, VerbClass.ER, "V_GVRB");
        // indikativ präsens
        addVF("plaiel", "V_GVRB");
        addVF("plaias", "V_GVRB");
        addVF("plais", "V_GVRB");
        addVF("plai", "V_GVRB");
        addVF("plaschein", "V_GVRB");
        addVF("plascheis", "V_GVRB");
        addVF("plaian", "V_GVRB");
        addVF("plain", "V_GVRB");
        // indikativ imperfekt
        addIndikativImperfekt("plasch", VerbClass.ER, "V_GVRB");
        // konjunktiv präsens
        addVF("plaigi", "V_GVRB");
        addVF("plaigies", "V_GVRB");
        addVF("plascheien", "V_GVRB");
        addVF("plascheies", "V_GVRB");
        addVF("plaigien", "V_GVRB");
        // konjunktiv imperfekt
        addKonjunktivImperfekt(stamm, VerbClass.ER, "V_GVRB");
        // konditional präsens direkt
        addKonditionalDirekt(stamm, VerbClass.ER, "V_GVRB");
        // konditional präsens indirekt
        addKonditionalIndirekt(stamm, VerbClass.ER, "V_GVRB");

        //PRUIR
        //infinitiv
        addVF("pruir", "V_GVRB");
        // partizip perfekt
        addVF("pruiu", "V_PP");
        // partizip präsens
        addVF("pruient", "V_PP");
        // gerundium
        addGerundium("prui", VerbClass.ER, "V_GVRB");
        // imperativ
        addVF("prui", "V_GVRB");
        // indikativ präsens
        addVF("pruiel", "V_GVRB");
        addVF("pruias", "V_GVRB");
        addVF("pruis", "V_GVRB");
        addVF("prui", "V_GVRB");
        addVF("pruin", "V_GVRB");
        addVF("pruian", "V_GVRB");
        // indikativ imperfekt
        addIndikativImperfekt("prui", VerbClass.IR, "V_GVRB");
        // konjunktiv präsens
        addVF("pruigi", "V_GVRB");
        addVF("pruigies", "V_GVRB");
        addVF("pruîen", "V_GVRB");
        addVF("pruîes", "V_GVRB");
        addVF("pruigien", "V_GVRB");
        // konjunktiv imperfekt
        addKonjunktivImperfekt("prui", VerbClass.IR, "V_GVRB");
        // konditional präsens direkt
        addKonditionalDirekt("prui", VerbClass.IR, "V_GVRB");
        // konditional präsens indirekt
        addKonditionalIndirekt("prui", VerbClass.IR, "V_GVRB");

        //RIR
        //infinitiv
        addVF("rir", "V_GVRB");
        // partizip perfekt
        addVF("ris", "V_PP");
        // partizip präsens
        addVF("rient", "V_PP");
        // gerundium
        addGerundium("ri", VerbClass.IR, "V_GVRB");
        // imperativ
        addVF("ri", "V_GVRB");
        addVF("riei", "V_GVRB");
        // indikativ präsens
        addVF("riel", "V_GVRB");
        addVF("rias", "V_GVRB");
        addVF("ris", "V_GVRB");
        addVF("ri", "V_GVRB");
        addVF("riein", "V_GVRB");
        addVF("rieis", "V_GVRB");
        addVF("rian", "V_GVRB");
        addVF("rin", "V_GVRB");
        // indikativ imperfekt
        addIndikativImperfekt("ri", VerbClass.IR, "V_GVRB");
        addVF("rievel", "V_GVRB");
        addVF("rievas", "V_GVRB");
        addVF("rieva", "V_GVRB");
        addVF("rievan", "V_GVRB");
        // konjunktiv präsens
        addVF("rigi", "V_GVRB");
        addVF("rigies", "V_GVRB");
        addVF("rieien", "V_GVRB");
        addVF("rieies", "V_GVRB");
        addVF("rigien", "V_GVRB");
        // konjunktiv imperfekt
        addKonjunktivImperfekt("ri", VerbClass.IR, "V_GVRB");
        // konditional präsens direkt
        addKonditionalDirekt("ri", VerbClass.IR, "V_GVRB");
        // konditional präsens indirekt
        addKonditionalIndirekt("ri", VerbClass.IR, "V_GVRB");

        //RUIR
        //infinitiv
        addVF("ruir", "V_GVRB");
        // weitere infinitivform
        addVF("ruer", "V_GVRB");
        // partizip perfekt
        addVF("ruis", "V_PP");
        addVF("ruissa", "V_PP");
        // partizip präsens
        addVF("ruient", "V_PP");
        // gerundium
        addGerundium("rui", VerbClass.IR, "V_GVRB");
        // imperativ
        addVF("rui", "V_GVRB");
        addVF("ruiei", "V_GVRB");
        // indikativ präsens
        addVF("ruiel", "V_GVRB");
        addVF("ruias", "V_GVRB");
        addVF("ruis", "V_GVRB");
        addVF("rui", "V_GVRB");
        addVF("ruiein", "V_GVRB");
        addVF("ruin", "V_GVRB");
        addVF("ruieis", "V_GVRB");
        addVF("ruian", "V_GVRB");
        // indikativ imperfekt
        addIndikativImperfekt("rui", VerbClass.IR, "V_GVRB");
        // konjunktiv präsens
        addVF("ruigi", "V_GVRB");
        addVF("ruigies", "V_GVRB");
        addVF("ruieien", "V_GVRB");
        addVF("ruieies", "V_GVRB");
        addVF("ruigien", "V_GVRB");
        // konjunktiv imperfekt
        addKonjunktivImperfekt("rui", VerbClass.IR, "V_GVRB");
        // konditional präsens direkt
        addKonditionalDirekt("rui", VerbClass.IR, "V_GVRB");
        // konditional präsens indirekt
        addKonditionalIndirekt("rui", VerbClass.IR, "V_GVRB");

        //SAVER
        //infinitiv
        addVF("saver", "V_GVRB");
        // partizip perfekt
        addVF("saviu", "V_PP");
        // gerundium
        addGerundium("sav", VerbClass.ER, "V_GVRB");
        // imperativ
        addVF("sappies", "V_GVRB");
        addVF("saveies", "V_GVRB");
        // indikativ präsens
        addVF("sai", "V_GVRB");
        addVF("sas", "V_GVRB");
        addVF("sa", "V_GVRB");
        addVF("savein", "V_GVRB");
        addVF("saveis", "V_GVRB");
        addVF("san", "V_GVRB");
        // indikativ imperfekt
        addIndikativImperfekt("sav", VerbClass.ER, "V_GVRB");
        // konjunktiv präsens
        addVF("sappi", "V_GVRB");
        addVF("sappies", "V_GVRB");
        addVF("saveien", "V_GVRB");
        addVF("saveies", "V_GVRB");
        addVF("sappien", "V_GVRB");
        // konjunktiv imperfekt
        addKonjunktivImperfekt("sav", VerbClass.ER, "V_GVRB");
        // konditional präsens direkt
        addKonditionalDirekt("sav", VerbClass.ER, "V_GVRB");
        // konditional präsens indirekt
        addKonditionalIndirekt("sav", VerbClass.ER, "V_GVRB");

        //SCHAR
        //infinitiv
        addVF("schar", "V_GVRB");
        // weitere infinitivform
        addVF("laschar", "V_GVRB");
        // partizip perfekt
        addVF("schau", "V_PP");
        // gerundium
        addGerundium("sch", VerbClass.AR, "V_GVRB");
        addGerundium("lasch", VerbClass.AR, "V_GVRB");
        // imperativ
        addVF("lai", "V_GVRB");
        addVF("schei", "V_GVRB");
        addVF("laschei", "V_GVRB");
        // indikativ präsens
        addVF("lasch", "V_GVRB");
        addVF("laias", "V_GVRB");
        addVF("lais", "V_GVRB");
        addVF("lai", "V_GVRB");
        addVF("schein", "V_GVRB");
        addVF("scheis", "V_GVRB");
        addVF("laian", "V_GVRB");
        addVF("lain", "V_GVRB");
        // indikativ imperfekt
        addIndikativImperfekt("lasch", VerbClass.AR, "V_GVRB");
        addIndikativImperfekt("sch", VerbClass.AR, "V_GVRB");
        // konjunktiv präsens
        addVF("laschi", "V_GVRB");
        addVF("laschies", "V_GVRB");
        addVF("lascheien", "V_GVRB");
        addVF("scheien", "V_GVRB");
        addVF("lascheies", "V_GVRB");
        addVF("scheies", "V_GVRB");
        addVF("laschien", "V_GVRB");
        // konjunktiv imperfekt
        addKonjunktivImperfekt("lasch", VerbClass.AR, "V_GVRB");
        addKonjunktivImperfekt("sch", VerbClass.AR, "V_GVRB");
        // konditional präsens direkt
        addKonditionalDirekt("lasch", VerbClass.AR, "V_GVRB");
        addKonditionalDirekt("sch", VerbClass.AR, "V_GVRB");
        addVF("schess", "V_GVRB");
        addVF("schesses", "V_GVRB");
        addVF("schessen", "V_GVRB");
        // konditional präsens indirekt
        addKonditionalIndirekt("lasch", VerbClass.AR, "V_GVRB");
        addKonditionalIndirekt("sch", VerbClass.AR, "V_GVRB");
        addVF("schond", "V_GVRB");
        addVF("schessi", "V_GVRB");
        addVF("schessies", "V_GVRB");
        addVF("schessien", "V_GVRB");

        //SCHER
        //infinitiv
        addVF("scher", "V_GVRB");
        // partizip perfekt
        addVF("schischiu", "V_PP");
        // partizip präsent
        addVF("schischent", "V_PP");
        // gerundium
        addGerundium("schisch", VerbClass.ER, "V_GVRB");
        // imperativ
        addVF("schai", "V_GVRB");
        addVF("schischei", "V_GVRB");
        // indikativ präsens
        addVF("schaiel", "V_GVRB");
        addVF("schaias", "V_GVRB");
        addVF("schais", "V_GVRB");
        addVF("schai", "V_GVRB");
        addVF("schischein", "V_GVRB");
        addVF("schischeis", "V_GVRB");
        addVF("schaian", "V_GVRB");
        addVF("schain", "V_GVRB");
        // indikativ imperfekt
        addIndikativImperfekt("schisch", VerbClass.ER, "V_GVRB");
        // konjunktiv präsens
        addVF("schaigi", "V_GVRB");
        addVF("schaigies", "V_GVRB");
        addVF("schischeien", "V_GVRB");
        addVF("schischeies", "V_GVRB");
        addVF("schaigien", "V_GVRB");
        // konjunktiv imperfekt
        addKonjunktivImperfekt("schisch", VerbClass.ER, "V_GVRB");
        // konditional präsens direkt
        addKonditionalDirekt("schisch", VerbClass.ER, "V_GVRB");
        // konditional präsens indirekt
        addKonditionalIndirekt("schisch", VerbClass.ER, "V_GVRB");

        //SFUIR
        //infinitiv
        addVF("sfuir", "V_GVRB");
        // weitere infinitivform
        addVF("sfuer", "V_GVRB");
        // partizip perfekt
        addVF("sfois", "V_PP");
        // partizip präsent
        addVF("sfuient", "V_PP");
        // gerundium
        addGerundium("sfui", VerbClass.IR, "V_GVRB");
        // imperativ
        addVF("sfoi", "V_GVRB");
        addVF("sfuiei", "V_GVRB");
        // indikativ präsens
        addVF("sfoiel", "V_GVRB");
        addVF("sfoias", "V_GVRB");
        addVF("sfois", "V_GVRB");
        addVF("sfoi", "V_GVRB");
        addVF("sfuiein", "V_GVRB");
        addVF("sfuieis", "V_GVRB");
        addVF("sfoian", "V_GVRB");
        addVF("sfoin", "V_GVRB");
        // indikativ imperfekt
        addIndikativImperfekt("sfui", VerbClass.IR, "V_GVRB");
        // konjunktiv präsens
        addVF("sfoigi", "V_GVRB");
        addVF("sfoigies", "V_GVRB");
        addVF("sfuieien", "V_GVRB");
        addVF("sfuieies", "V_GVRB");
        addVF("sfoigien", "V_GVRB");
        // konjunktiv imperfekt
        addKonjunktivImperfekt("sfui", VerbClass.IR, "V_GVRB");
        // konditional präsens direkt
        addKonditionalDirekt("sfui", VerbClass.IR, "V_GVRB");
        // konditional präsens indirekt
        addKonditionalIndirekt("sfui", VerbClass.IR, "V_GVRB");

        //STAR
        //infinitiv
        addVF("star", "V_GVRB");
        // partizp perfekt
        addVF("stau", "V_PP");
        addVF("staus", "V_PP");
        addVF("stada", "V_PP");
        addVF("stai", "V_PP");
        addVF("stadas", "V_PP");
        // gerundium
        addGerundium("st", VerbClass.AR, "V_GVRB");
        addGerundium("st", VerbClass.ER, "V_GVRB");
        // imperativ
        addVF("stai", "V_GVRB");
        addVF("stei", "V_GVRB");
        // indikativ präsens
        addVF("stun", "V_GVRB");
        addVF("stundel", "V_GVRB");
        addVF("stas", "V_GVRB");
        addVF("stat", "V_GVRB");
        addVF("stein", "V_GVRB");
        addVF("steis", "V_GVRB");
        addVF("stattan", "V_GVRB");
        // indikativ imperfekt
        addIndikativImperfekt("st", VerbClass.AR, "V_GVRB");
        addIndikativImperfekt("st", VerbClass.ER, "V_GVRB");
        // konjunktiv präsens
        addVF("stetti", "V_GVRB");
        addVF("stetties", "V_GVRB");
        addVF("steien", "V_GVRB");
        addVF("steies", "V_GVRB");
        addVF("stettien", "V_GVRB");
        // Konjunktiv imperfekt
        addKonjunktivImperfekt("st", VerbClass.AR, "V_GVRB");
        addKonjunktivImperfekt("st", VerbClass.ER, "V_GVRB");
        // Konditional präsnes direkt
        addKonditionalDirekt("st", VerbClass.AR, "V_GVRB");
        addKonditionalDirekt("st", VerbClass.ER, "V_GVRB");
        // konditional präsens indirekt
        addKonditionalIndirekt("st", VerbClass.AR, "V_GVRB");
        addKonditionalIndirekt("st", VerbClass.ER, "V_GVRB");

        //TRER
        //infinitiv
        addVF("trer", "V_GVRB");
        // partizp perfekt
        addVF("tratg", "V_PP");
        // gerundium
        addGerundium("targ", VerbClass.ER, "V_GVRB");
        // imperativ
        addVF("trai", "V_GVRB");
        addVF("tila", "V_GVRB");
        addVF("targei", "V_GVRB");
        // indikativ präsens
        addVF("traiel", "V_GVRB");
        addVF("trai", "V_GVRB");
        addVF("trais", "V_GVRB");
        addVF("targein", "V_GVRB");
        addVF("targeis", "V_GVRB");
        addVF("train", "V_GVRB");
        addVF("tilel", "V_GVRB");
        addVF("tilas", "V_GVRB");
        addVF("tila", "V_GVRB");
        addVF("tilan", "V_GVRB");
        // indikativ imperfekt
        addIndikativImperfekt("targ", VerbClass.ER, "V_GVRB");
        // konjunktiv präsens
        addVF("traigi", "V_GVRB");
        addVF("traigies", "V_GVRB");
        addVF("targeien", "V_GVRB");
        addVF("targeies", "V_GVRB");
        addVF("tragien", "V_GVRB");
        addVF("tili", "V_GVRB");
        addVF("tilies", "V_GVRB");
        addVF("tilien", "V_GVRB");
        // Konjunktiv imperfekt
        addKonjunktivImperfekt("targ", VerbClass.ER, "V_GVRB");
        // Konditional präsnes direkt
        addKonditionalDirekt("targ", VerbClass.ER, "V_GVRB");
        // konditional präsens indirekt
        addKonditionalIndirekt("targ", VerbClass.ER, "V_GVRB");

    }


    private void addModalVerbs() {
        //Puder: Können
        //infinitiv
        addVF("puder", "V_MOD");
        // partizip perfekt
        addVF("pudiu", "V_MOD");
        // gerundium
        addGerundium("pud", VerbClass.ER, "V_MOD");
        // indikativ präsens
        addVF("pos", "V_MOD");
        addVF("po", "V_MOD");
        addVF("pudein", "V_MOD");
        addVF("pudeis", "V_MOD");
        addVF("pon", "V_MOD");
        // indikativ imperfekt
        addIndikativImperfekt("pud", VerbClass.ER, "V_MOD");
        // konjunktiv präsens
        addVF("possi", "V_MOD");
        addVF("possies", "V_MOD");
        addVF("pudeien", "V_MOD");
        addVF("pudeies", "V_MOD");
        addVF("possien", "V_MOD");
        // konjunktiv imperfekt
        addKonjunktivImperfekt("pud", VerbClass.ER, "V_MOD");
        // konditional präsens direkt
        addKonditionalDirekt("pud", VerbClass.ER, "V_MOD");
        // konditional präsens indirekt
        addKonditionalIndirekt("pud", VerbClass.ER, "V_MOD");


        //Vuler : Wollen
        //infinitiv
        addVF("vuler", "V_MOD");
        // partizp perfekt
        addVF("vuliu", "V_MOD");
        // gerundium
        addGerundium("vul", VerbClass.ER, "V_MOD");
        // imperativ
        addVF("veglies", "V_MOD");
        addVF("vuleies", "V_MOD");
        // indikativ präsens
        addVF("vi", "V_MOD");
        addVF("vul", "V_MOD");
        addVF("vulein", "V_MOD");
        addVF("lein", "V_MOD");
        addVF("vuleis", "V_MOD");
        addVF("leis", "V_MOD");
        addVF("vulan", "V_MOD");
        // indikativ imperfekt
        addIndikativImperfekt("vul", VerbClass.ER, "V_MOD");
        addIndikativImperfekt("l", VerbClass.ER, "V_MOD");
        // konjunktiv präsens
        addVF("vegli", "V_MOD");
        addVF("veglies", "V_MOD");
        addVF("vuleien", "V_MOD");
        addVF("leien", "V_MOD");
        addVF("vuleies", "V_MOD");
        addVF("leies", "V_MOD");
        addVF("veglien", "V_MOD");
        // Konjunktiv imperfekt
        addKonjunktivImperfekt("vul", VerbClass.ER, "V_MOD");
        addKonjunktivImperfekt("l", VerbClass.ER, "V_MOD");
        // Konditional präsnes direkt
        addKonditionalDirekt("vul", VerbClass.ER, "V_MOD");
        addKonditionalDirekt("l", VerbClass.ER, "V_MOD");
        // konditional präsens indirekt
        addKonditionalIndirekt("vul", VerbClass.ER, "V_MOD");
        addKonditionalDirekt("l", VerbClass.ER, "V_MOD");


        //Stuer : Müssen
        //infinitiv
        addVF("stuer", "V_MOD");
        // partizip perfekt
        addVF("stuiu", "V_MOD");
        // gerundium
        addGerundium("stu", VerbClass.ER, "V_MOD");
        // indikativ präsens
        addVF("stoi", "V_MOD");
        addVF("stos", "V_MOD");
        addVF("sto", "V_MOD");
        addVF("stuein", "V_MOD");
        addVF("stueis", "V_MOD");
        addVF("ston", "V_MOD");
        // indikativ imperfekt
        addIndikativImperfekt("stu", VerbClass.ER, "V_MOD");
        // konjunktiv präsens
        addVF("stoppi", "V_MOD");
        addVF("stoppies", "V_MOD");
        addVF("stueien", "V_MOD");
        addVF("stueies", "V_MOD");
        addVF("stoppien", "V_MOD");
        // konjunktiv imperfekt
        addKonjunktivImperfekt("stu", VerbClass.ER, "V_MOD");
        // konditional präsens direkt
        addKonditionalDirekt("stu", VerbClass.ER, "V_MOD");
        // konditional präsens indirekt
        addKonditionalIndirekt("stu", VerbClass.ER, "V_MOD");


        //Duer : Sollen
        //infinitiv
        addVF("duer", "V_MOD");
        // partizip perfekt
        addVF("duiu", "V_MOD");
        // gerundium
        addGerundium("du", VerbClass.ER, "V_MOD");
        // indikativ präsens
        addVF("duei", "V_MOD");
        addVF("dueis", "V_MOD");
        addVF("duein", "V_MOD");
        addVF("dueis", "V_MOD");
        addVF("duein", "V_MOD");
        // indikativ imperfekt
        addIndikativImperfekt("du", VerbClass.ER, "V_MOD");
        addIndikativImperfekt("d", VerbClass.ER, "V_GVRB");

        // konjunktiv präsens
        addVF("dueigi", "V_MOD");
        addVF("dueigies", "V_MOD");
        addVF("dueigien", "V_MOD");
        addVF("dueigies", "V_MOD");
        addVF("dueigien", "V_MOD");
        // konjunktiv imperfekt
        addKonjunktivImperfekt("du", VerbClass.ER, "V_MOD");
        addKonjunktivImperfekt("d", VerbClass.ER, "V_GVRB");

        // konditional präsens direkt
        addKonditionalDirekt("du", VerbClass.ER, "V_MOD");
        addKonditionalDirekt("d", VerbClass.ER, "V_GVRB");

        // konditional präsens indirekt
        addKonditionalIndirekt("du", VerbClass.ER, "V_MOD");
        addKonditionalIndirekt("d", VerbClass.ER, "V_GVRB");

        //Astgar : Dürfen
        //infinitiv
        addVF("astgar", "V_MOD");
        // partizip perfekt
        addVF("astgau", "V_MOD");
        // gerundium
        addGerundium("astg", VerbClass.AR, "V_MOD");
        // indikativ präsens
        addVF("astgel", "V_MOD");
        addVF("astgas", "V_MOD");
        addVF("astga", "V_MOD");
        addVF("astgein", "V_MOD");
        addVF("astgeis", "V_MOD");
        addVF("astgan", "V_MOD");
        // indikativ imperfekt
        addIndikativImperfekt("astg", VerbClass.AR, "V_MOD");
        // konjunktiv präsens
        addVF("astgi", "V_MOD");
        addVF("astgies", "V_MOD");
        addVF("astgeien", "V_MOD");
        addVF("astgeies", "V_MOD");
        addVF("astgien", "V_MOD");
        // konjunktiv imperfekt
        addKonjunktivImperfekt("astg", VerbClass.AR, "V_MOD");
        // konditional präsens direkt
        addKonditionalDirekt("astg", VerbClass.AR, "V_MOD");
        // konditional präsens indirekt
        addKonditionalIndirekt("astg", VerbClass.AR, "V_MOD");


    }


}
