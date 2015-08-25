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

public class Sutsilvan_VFGenerator {

	Map<String, TreeSet<String>> vollForms = new TreeMap<String, TreeSet<String>>();
	int count;

	public Set<String> getEntrys() {
		return vollForms.keySet();
	}

	public int getNumberOfVFEntries() {
		return vollForms.keySet().size();

	}

	public int getNumberOfDBEntries() {
		return count;
	}

	public Map<String, TreeSet<String>> generateVollForms(DBCollection collection)
			throws UnknownHostException {
		DBCursor cursor = collection.find();
		count = 0;
		for (DBObject doc : cursor) {
			count++;

			String entry = (String) doc.get("entry");
			DBObject pos = (BasicDBObject) doc.get("pos");
			String eagles_pos = (String) pos.get("eagles_pos");

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
				addPräpositions();
				addPräp_Art();
				addPronouns();
				addConjunctions();
				addArticles();
				addAdjectives();
				
			}
		}
		return vollForms;
	}

	private void addAdjectives() {
		//Demonstrative
		addVF("tschel", "ADJ_DEM");
		addVF("tschella", "ADJ_DEM");
		addVF("tschels", "ADJ_DEM");
		addVF("tschellas", "ADJ_DEM");
		addVF("quest", "ADJ_DEM");
		addVF("questa", "ADJ_DEM");
		addVF("quests", "ADJ_DEM");
		addVF("questas", "ADJ_DEM");
		addVF("quel", "ADJ_DEM");
		addVF("quella", "ADJ_DEM");
		addVF("quels", "ADJ_DEM");
		addVF("quellas", "ADJ_DEM");
		addVF("tal", "ADJ_DEM");
		addVF("tala", "ADJ_DEM");
		addVF("tanien", "ADJ_DEM");
		addVF("tanienta", "ADJ_DEM");
		addVF("gliez", "ADJ_DEM");
		
		//Indefinite
		addVF("varsaquànts", "ADJ_IND");
		addVF("anzaquànts", "ADJ_IND");
		addVF("zaquànts", "ADJ_IND");
		
		//interrogativ
		addVF("quànt", "ADJ_IES");
		addVF("quàntavel", "ADJ_IES");
	}

	private void generateAdjectiveForms(String entry) {
		addVF(entry, "ADJ");
		char lastChar = entry.charAt(entry.length()-1);
		if(lastChar!='s'){
			addVF(entry+"s", "ADJ");
		}
		if(lastChar!='a'){
			addVF(entry+"a", "ADJ");
			addVF(entry+"as", "ADJ");
			addVF(entry+"amaing", "ADV");
		}
		addVF(entry+"maing", "ADV");
	}

	private void generateSubstantiveForms(String entry) {
		addVF(entry, "NN");
		//maskulin plural
		char lastChar = entry.charAt(entry.length()-1);
		if(lastChar!='s'){
			addVF(entry+"s", "NN");
		}
		//feminim 
		if(lastChar!='a'){
			addVF(entry+'a', "NN");
			addVF(entry+"as", "NN");
		}
	}

	private void addArticles() {
		addVF("igl", "ART");
		addVF("la", "ART");
		addVF("gl'", "ART");
		addVF("l'", "ART");
		addVF("en", "ART");
		addVF("ena", "ART");
		addVF("en'", "ART");
		addVF("igls", "ART");
		addVF("las", "ART");
	}

	private void addConjunctions() {
		addVF("a", "CONJ_C");
		addVF("aschianavànt", "CONJ_S");
		addVF("schianavànt", "CONJ_S");
		addVF("aschigî", "CONJ_S");
		addVF("bagn", "CONJ_C");
		addVF("ca", "CONJ_S");
		addVF("cumbagn", "CONJ_S");
		addVF("cunquegl", "CONJ_S");
		addVF("cur", "CONJ_S");
		addVF("damena", "CONJ_S");
		addVF("dantànt", "CONJ_C");
		addVF("ear", "CONJ_C");
		addVF("laancùnter", "CONJ_S");
		addVF("mo", "CONJ_C");
		addVF("mobagn", "CONJ_C");
		addVF("ne", "CONJ_S");
		addVF("neader", "CONJ_S");
		addVF("near", "CONJ_S");
		addVF("ni", "CONJ_S");
		addVF("parancùnter", "CONJ_S");
		addVF("pargir", "CONJ_S");
		addVF("parquegl", "CONJ_S");
		addVF("peia", "CONJ_S");
		addVF("scha", "CONJ_S");
		addVF("schabagn", "CONJ_S");
		addVF("schagea", "CONJ_S");
		addVF("schigea", "CONJ_S");
		addVF("schibagn", "CONJ_S");
		addVF("schinavànt", "CONJ_S");
		addVF("sco", "CONJ_C");
		addVF("tànatànt", "CONJ_C");
		addVF("tutegna", "CONJ_C");

		
	}

	private void addPronouns() {
		//indefinite
		addVF("ampo", "PRON_IND");
		addVF("anqual", "PRON_IND");
		addVF("anqualegn", "PRON_IND");
		addVF("anzaquànts", "PRON_IND");
		addVF("zaquànts", "PRON_IND");
		addVF("anzatge", "PRON_IND");
		addVF("zatge", "PRON_IND");
		addVF("anzatgeet", "PRON_IND");
		addVF("zatgeet", "PRON_IND");
		addVF("anzatgi", "PRON_IND");
		addVF("zatgi", "PRON_IND");
		addVF("bagnanqual", "PRON_IND");
		addVF("cix", "PRON_IND");
		addVF("cot", "PRON_IND");
		addVF("egn", "PRON_IND");
		addVF("fegnadegn", "PRON_IND");
		addVF("ign", "PRON_IND");
		addVF("mintga", "PRON_IND");
		addVF("mintgegna", "PRON_IND");
		addVF("mintgegn", "PRON_IND");
		addVF("nigna", "PRON_IND");
		addVF("niegna", "PRON_IND");
		addVF("nign", "PRON_IND");
		addVF("niegn", "PRON_IND");
		addVF("nut", "PRON_IND");
		addVF("oter", "PRON_IND");
		addVF("plirs", "PRON_IND");
		addVF("pliras", "PRON_IND");
		addVF("scadegna", "PRON_IND");
		addVF("scadegn", "PRON_IND");
		addVF("tschearts", "PRON_IND");
		addVF("tscheartas", "PRON_IND");
		addVF("tscheartegns", "PRON_IND");
		addVF("tut", "PRON_IND");
		addVF("varsaquànts", "PRON_IND");
		addVF("", "PRON_IND");
		
		//demonstativ
		addVF("gliez", "PRON_DEM");
		addVF("igl", "PRON_DEM");
		addVF("lez", "PRON_DEM");
		addVF("leza", "PRON_DEM");
		addVF("lasezas", "PRON_DEM");
		addVF("iglsezs", "PRON_DEM");
		addVF("madem", "PRON_DEM");
		addVF("que", "PRON_DEM");
		addVF("quegl", "PRON_DEM");
		addVF("quel", "PRON_DEM");
		addVF("quest", "PRON_DEM");
		addVF("questa", "PRON_DEM");
		addVF("sasez", "PRON_DEM");
		addVF("sez", "PRON_DEM");
		addVF("tal", "PRON_DEM");
		addVF("tschegl", "PRON_DEM");
		addVF("tschel", "PRON_DEM");
		addVF("tschella", "PRON_DEM");
		addVF("gliez", "PRON_DEM");
		
		//interrogativ
		addVF("qual", "PRON_IES");
		addVF("qualas", "PRON_IES");
		addVF("tge", "PRON_IES");
		addVF("tgenegna", "PRON_IES");
		addVF("tgenegn", "PRON_IES");
		addVF("tgi", "PRON_IES");
		addVF("qual", "PRON_IES");
		addVF("qual", "PRON_IES");
		addVF("qual", "PRON_IES");
		addVF("qual", "PRON_IES");
		
			
		
		//personal
		addVF("al", "PRON_PER");
		addVF("as", "PRON_PER");
		addVF("el", "PRON_PER");
		addVF("ella", "PRON_PER");
		addVF("eis", "PRON_PER");
		addVF("gli", "PRON_PER");
		addVF("i", "PRON_PER");
		addVF("igl", "PRON_PER");
		addVF("jou", "PRON_PER");
		addVF("la", "PRON_PER");
		addVF("me", "PRON_PER");
		addVF("mi", "PRON_PER");
		addVF("nus", "PRON_PER");
		addVF("sasez", "PRON_PER");
		addVF("sez", "PRON_PER");
		addVF("ta", "PRON_PER");
		addVF("te", "PRON_PER");
		addVF("tei", "PRON_PER");
		addVF("tgei", "PRON_PER");
		addVF("tgi", "PRON_PER");
		addVF("ti", "PRON_PER");
		addVF("vus", "PRON_PER");
		
		//relativ
		addVF("ca", "PRON_REL");
		addVF("gli", "PRON_REL");
		addVF("qual", "PRON_REL");
		addVF("quegl", "PRON_REL");
		addVF("que", "PRON_REL");
		addVF("tgi", "PRON_REL");
		
		//possesiv
		addVF("lur", "PRON_POS");
		addVF("mi", "PRON_POS");
		addVF("mia", "PRON_POS");
		addVF("mieus", "PRON_POS");
		addVF("mieu", "PRON_POS");
		addVF("noss", "PRON_POS");
		addVF("nus", "PRON_POS");
		addVF("seas", "PRON_POS");
		addVF("sieus", "PRON_POS");
		addVF("si", "PRON_POS");
		addVF("sia", "PRON_POS");
		addVF("sieu", "PRON_POS");
		addVF("teas", "PRON_POS");
		addVF("tieus", "PRON_POS");
		addVF("ti", "PRON_POS");
		addVF("tia", "PRON_POS");
		addVF("tieu", "PRON_POS");
		addVF("vies", "PRON_POS");
		addVF("voss", "PRON_POS");
		addVF("", "PRON_POS");
		addVF("", "PRON_POS");
		addVF("", "PRON_POS");
	}

	private void addPräpositions() {
		addVF("a", "PREP");
		addVF("an", "PREP");
		addVF("aintamiez", "PREP");
		addVF("ampe", "PREP");
		addVF("ancunter", "PREP");
		addVF("cunter", "PREP");
		addVF("anstagl", "PREP");
		addVF("antocen", "PREP");
		addVF("tocen", "PREP");
		addVF("antras", "PREP");
		addVF("tras", "PREP");
		addVF("antrocen", "PREP");
		addVF("trocen", "PREP");
		addVF("anturn", "PREP");
		addVF("anviers", "PREP");
		addVF("ànz", "PREP");
		addVF("arisguard", "PREP");
		addVF("avànt", "PREP");
		addVF("cun", "PREP");
		addVF("da", "PREP");
		addVF("dafor", "PREP");
		addVF("damiez", "PREP");
		addVF("amiez", "PREP");
		addVF("dano", "PREP");
		addVF("datier", "PREP");
		addVF("davains", "PREP");
		addVF("davànt", "PREP");
		addVF("davart", "PREP");
		addVF("davent", "PREP");
		addVF("davo", "PREP");
		addVF("durànt", "PREP");
		addVF("giod", "PREP");
		addVF("gipu", "PREP");
		addVF("gioudvart", "PREP");
		addVF("malgro", "PREP");
		addVF("nadvart", "PREP");
		addVF("nanvart", "PREP");
		addVF("or", "PREP");
		addVF("ora", "PREP");
		addVF("ord", "PREP");
		addVF("ordainfer", "PREP");
		addVF("ordamiez", "PREP");
		addVF("orpar", "PREP");
		addVF("ortier", "PREP");
		addVF("par", "PREP");
		addVF("partanànt", "PREP");
		addVF("partutgànt", "PREP");
		addVF("parve", "PREP");
		addVF("parveia", "PREP");
		addVF("resalvànt", "PREP");
		addVF("sagund", "PREP");
		addVF("savund", "PREP");
		addVF("sainza", "PREP");
		addVF("se", "PREP");
		addVF("sed", "PREP");
		addVF("sen", "PREP");
		addVF("setier", "PREP");
		addVF("sper", "PREP");
		addVF("suainter", "PREP");
		addVF("sur", "PREP");
		addVF("sut", "PREP");
		addVF("tanor", "PREP");
		addVF("tar", "PREP");
		addVF("tier", "PREP");
		addVF("tràmper", "PREP");
		addVF("trànter", "PREP");
		addVF("tras", "PREP");
		addVF("ultra", "PREP");
		addVF("ve", "PREP");
		addVF("ved", "PREP");
		addVF("veda", "PREP");
		addVF("vedvart", "PREP");
		addVF("via", "PREP");
		addVF("viers", "PREP");
		addVF("visavi", "PREP");
	}
	
	private void addPräp_Art(){
		addVF("agl", "PREP_ART");
		addVF("digl", "PREP_ART");
		addVF("digls", "PREP_ART");
		addVF("dalla", "PREP_ART");
		addVF("dallas", "PREP_ART");
		addVF("agl", "PREP_ART");
		addVF("agls", "PREP_ART");
		addVF("alla", "PREP_ART");
		addVF("allas", "PREP_ART");
		addVF("segl", "PREP_ART");
		addVF("segls", "PREP_ART");
		addVF("cugl", "PREP_ART");
		addVF("cugls", "PREP_ART");
		addVF("pigl", "PREP_ART");
		addVF("pigls", "PREP_ART");
		addVF("tigl", "PREP_ART");
		addVF("tigls", "PREP_ART");
	}

	private void addIrregularVerbs() {
		// quietar
		String stamm = "quiet";
		// Infinitiv
		addVF("quietar", "V_GVRB");
		// Partizip
		addPartizip(stamm, VerbClass.AR);
		// Gerundium
		addGerundium(stamm, "V_GVRB");
		// Imperativ
		addVF(stamm + "escha", "V_GVRB"); addVF(stamm + "ainsa", "V_GVRB"); addVF(stamm + "ad", "V_GVRB"); addVF(stamm + "à", "V_GVRB");
		// indikativ präsens
		addVF(stamm + "esch", "V_GVRB");addVF(stamm + "eschel", "V_GVRB");addVF(stamm + "eschas", "V_GVRB");addVF(stamm + "escha", "V_GVRB");addVF(stamm + "agn", "V_GVRB");addVF(stamm + "az", "V_GVRB");addVF(stamm + "eschan", "V_GVRB");
		// indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.AR, "V_GVRB");
		// Konjunktiv Präsens
		addVF(stamm + "eschi", "V_GVRB");addVF(stamm + "eschas", "V_GVRB");addVF(stamm + "eschias", "V_GVRB");addVF(stamm + "eian", "V_GVRB");addVF(stamm + "eias", "V_GVRB");addVF(stamm + "eschian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.AR, "V_GVRB");
		// Conditional direkt
		addKonditionalDirekt(stamm, VerbClass.AR, "V_GVRB");
		// Conditional indirekt
		addKonditionalIndirekt(stamm, VerbClass.AR, "V_GVRB");
		// Inversion
		addVF(stamm + "eschiou", "V_GVRB");addVF(stamm + "aviou", "V_GVRB");addVF(stamm + "assiou", "V_GVRB");addVF(stamm + "ainsa", "V_GVRB");addVF(stamm + "eschani", "V_GVRB");addVF(stamm + "avani", "V_GVRB");addVF(stamm + "assani", "V_GVRB");

		// pitir
		stamm = "pit";
		// infinitiv
		addVF("pitir", "V_GVRB");
		// Partizip
		addPartizip(stamm, VerbClass.ER);
		// Gerundium
		addGerundium(stamm, "V_GVRB");
		// Imperativ
		addVF(stamm + "escha", "V_GVRB");addVF(stamm + "ainsa", "V_GVRB");addVF(stamm + "ed", "V_GVRB");addVF(stamm + "é", "V_GVRB");
		// Indikativ Präsens
		addVF(stamm + "esch", "V_GVRB");addVF(stamm + "eschel", "V_GVRB");addVF(stamm + "eschas", "V_GVRB");addVF(stamm + "agn", "V_GVRB");addVF(stamm + "ez", "V_GVRB");addVF(stamm + "eschan", "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konjunktiv Präsens
		addVF(stamm + "eschi", "V_GVRB");addVF(stamm + "eschas", "V_GVRB");addVF(stamm + "eschias", "V_GVRB");addVF(stamm + "eian", "V_GVRB");addVF(stamm + "eias", "V_GVRB");addVF(stamm + "eschian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");
		// Inversion
		addVF(stamm + "eschiou", "V_GVRB");addVF(stamm + "eviou", "V_GVRB");addVF(stamm + "essiou", "V_GVRB");addVF(stamm + "ainsa", "V_GVRB");addVF(stamm + "eschani", "V_GVRB");addVF(stamm + "avani", "V_GVRB");addVF(stamm + "assani", "V_GVRB");

		// aver
		// Infinitiv
		addVF("aver", "V_AVAIR");	addVF("ver", "V_AVAIR");
		// Partizip
		addVF("gieu", "V_PP");
		// Gerundium
		addVF("avànt", "V_AVAIR");
		// Imperativ
		addVF("vegias", "V_AVAIR");addVF("veian", "V_AVAIR");addVF("veias", "V_AVAIR");
		// Indikativ Präsent
		addVF("ve", "V_AVAIR");addVF("âs", "V_AVAIR");addVF("â", "V_AVAIR");addVF("vagn", "V_AVAIR");addVF("vez", "V_AVAIR");addVF("ân", "V_AVAIR");
		// Indikativ Imperfekt
		addIndikativImperfekt("v", VerbClass.ER, "V_AVAIR");
		// Konjunktiv Präsens
		addVF("vegi", "V_AVAIR");addVF("vegias", "V_AVAIR");addVF("veian", "V_AVAIR");addVF("veias", "V_AVAIR");addVF("vegian", "V_AVAIR");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt("v", VerbClass.ER, "V_AVAIR");
		// Konditional direkt
		addKonditionalDirekt("v", VerbClass.ER, "V_AVAIR");
		// Konditional indirekt
		addKonditionalIndirekt("v", VerbClass.ER, "V_AVAIR");
		// Inversion
		addVF("veiou", "V_AVAIR");addVF("vegiou", "V_AVAIR");addVF("veviou", "V_AVAIR");addVF("vessiou", "V_AVAIR");addVF("vainsa", "V_AVAIR");addVF("âni", "V_AVAIR");addVF("vegiani", "V_AVAIR");addVF("vevani", "V_AVAIR");addVF("vessani", "V_AVAIR");

		// antalir
		antalir("");
		// malantalir
		antalir("m");
		// surantalir
		antalir("sur");
		
		
		// crer
		stamm = "cart";
		// Infinitiv
		addVF("crer", "V_GVRB");
		// Partizip
		addPartizip(stamm, VerbClass.ER);
		// Gerundium
		addGerundium(stamm, "V_GVRB");
		// Imperativ
		addVF("cre", "V_GVRB");
		addImperativ(stamm, VerbClass.ER, "V_GVRB");
		// Indikativ Präsens
		addVF("creg", "V_GVRB");addVF("cres", "V_GVRB");addVF("cre", "V_GVRB");addVF("cartagn", "V_GVRB");addVF("cartez", "V_GVRB");addVF("cren", "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.ER, "V_GVRB");
		// Konjunktiv Präsens
		addVF(stamm + "cregi", "V_GVRB");addVF(stamm + "cregias", "V_GVRB");addVF(stamm + "carteian", "V_GVRB");addVF(stamm + "carteias", "V_GVRB");addVF(stamm + "cregian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");

		// cuir
		stamm = "cuj";
		// Infinitiv
		addVF("cuir", "V_GVRB");
		// Partizip
		addVF("cotg", "V_PP");
		// Gerundium
		addGerundium(stamm, "V_GVRB");
		// Imperativ
		addVF("cui", "V_GVRB");
		addImperativ(stamm, VerbClass.IR, "V_GVRB");
		// Indikativ Präsens
		addVF("cuig", "V_GVRB");addVF("cuis", "V_GVRB");addVF("cujagn", "V_GVRB");addVF("cujez", "V_GVRB");addVF("cuin", "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konjunktiv Präsens
		addVF(stamm + "cuigi", "V_GVRB");addVF(stamm + "cuigias", "V_GVRB");addVF(stamm + "cujeian", "V_GVRB");addVF(stamm + "cujeias", "V_GVRB");addVF(stamm + "cuigian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");

		// cuvrir
		cuvrir("");
		// sacuvrir
		cuvrir("sa");
		// sascuvrir
		cuvrir("sas");

		// dar
		ar("d");

		// drir
		stamm = "darg";
		// infinitiv
		addVF("drir", "V_GVRB");
		// Partizip
		addVF("dretg", "V_PP");
		// Gerundium
		addGerundium("dargi", "V_GVRB");
		// Imperativ
		addVF("dri", "V_GVRB");addVF("dargiainsa", "V_GVRB");addVF("dargé", "V_GVRB");
		// Indikativ Präsens
		addVF("dring", "V_GVRB");addVF("dris", "V_GVRB");addVF("dri", "V_GVRB");addVF("dargiagn", "V_GVRB");addVF("dargez", "V_GVRB");addVF("drin", "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konjunktiv Präsens
		addVF("dregi", "V_GVRB");addVF("dregias", "V_GVRB");addVF("dargeian", "V_GVRB");addVF("dargeias", "V_GVRB");addVF("dregian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");

		// duer
		stamm = "du";
		// infinitiv
		addVF("duer", "V_GVRB");
		// Partizip
		addVF("duieu", "V_PP");
		// Gerundium
		addGerundium("du", "V_GVRB");
		// Indikativ Präsens
		addVF("de", "V_GVRB");addVF("des", "V_GVRB");addVF("duagn", "V_GVRB");addVF("duez", "V_GVRB");addVF("den", "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.ER, "V_GVRB");
		// Konjunktiv Präsens
		addVF("degi", "V_GVRB");addVF("degias", "V_GVRB");addVF("dueian", "V_GVRB");addVF("dueias", "V_GVRB");addVF("degian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.ER, "V_GVRB");
		// Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");

		// duncrir
		stamm = "duncr";
		// infinitiv
		addVF("duncrir", "V_GVRB");
		// Partizip
		addVF("duncretg", "V_PP");
		// Gerundium
		addGerundium(stamm, "V_GVRB");
		// Imperativ
		addVF("dunquiera", "V_GVRB");
		addImperativ(stamm, VerbClass.IR, "V_GVRB");
		// Indikativ Präsens
		addVF("dunquier", "V_GVRB");addVF("dunquieras", "V_GVRB");addVF("dunquiera", "V_GVRB");addVF("duncragn", "V_GVRB");addVF("duncrez", "V_GVRB");addVF("duncrevan", "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konjunktiv Präsens
		addVF("dunquieri", "V_GVRB");addVF("dunquierias", "V_GVRB");addVF("duncreian", "V_GVRB");addVF("duncreias", "V_GVRB");addVF("dunquierian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.ER, "V_GVRB");
		// Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");

		// duvierer
		stamm = "duvier";
		// infinitiv
		addVF("duvierer", "V_GVRB");
		// Partizip
		addVF("duviert", "V_PP");addVF("duvearta", "V_PP");
		// Gerundium
		addGerundium(stamm, "V_GVRB");
		// Imperativ
		addVF("duviera", "V_GVRB");
		addImperativ(stamm, VerbClass.IR, "V_GVRB");
		// Indikativ Präsens
		addVF("duvier", "V_GVRB");addVF("duvieras", "V_GVRB");addVF("duviera", "V_GVRB");addVF("duvieagn", "V_GVRB");addVF("duvierez", "V_GVRB");addVF("duvieran", "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konjunktiv Präsens
		addVF("duvieri", "V_GVRB");addVF("duvierias", "V_GVRB");addVF("duviereian", "V_GVRB");addVF("duviereias", "V_GVRB");addVF("duvierian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.ER, "V_GVRB");
		// Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");

		// duvrir
		stamm = "duvr";
		// infinitiv
		addVF("duvrir", "V_GVRB");
		// Partizip
		addVF("duvretg", "V_PP");
		// Gerundium
		addGerundium(stamm, "V_GVRB");
		// Imperativ
		addVF("duvriera", "V_GVRB");
		addImperativ(stamm, VerbClass.IR, "V_GVRB");
		// Indikativ Präsens
		addVF("duvrier", "V_GVRB");addVF("duvrieras", "V_GVRB");addVF("duvriera", "V_GVRB");addVF("duvragn", "V_GVRB");addVF("duvrez", "V_GVRB");addVF("dunvrevan", "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konjunktiv Präsens
		addVF("duvrieri", "V_GVRB");addVF("duvrierias", "V_GVRB");addVF("duvreian", "V_GVRB");addVF("duvreias", "V_GVRB");addVF("duvrierian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.ER, "V_GVRB");
		// Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");

		// easser
		// infinitiv
		addVF("easser", "V_ESSER");
		// Partizip
		addVF("sto", "V_PP");addVF("stada", "V_PP");
		// Gerundium
		addGerundium("si", "V_ESSER");
		// Imperativ
		addVF("segias", "V_ESSER");addVF("seian", "V_ESSER");addVF("seias", "V_ESSER");
		// Indikativ Präsens
		addVF("sund", "V_ESSER");addVF("es", "V_ESSER");addVF("e", "V_ESSER");addVF("eassan", "V_ESSER");addVF("eassas", "V_ESSER");addVF("en", "V_ESSER");
		// Indikativ Imperfekt
		addVF("eara", "V_ESSER");addVF("fova", "V_ESSER");addVF("earas", "V_ESSER");addVF("fovas", "V_ESSER");addVF("earan", "V_ESSER");addVF("foran", "V_ESSER");
		// Konjunktiv Präsens
		addVF("segi", "V_ESSER");addVF("segias", "V_ESSER");addVF("seian", "V_ESSER");addVF("seias", "V_ESSER");addVF("segian", "V_ESSER");
		// Konjunktiv Imperfekt
		addVF("eari", "V_ESSER");addVF("fovi", "V_ESSER");addVF("earias", "V_ESSER");addVF("fovias", "V_ESSER");addVF("earian", "V_ESSER");addVF("fovian", "V_ESSER");
		// Konditional direkt
		addVF("fuss", "V_ESSER");addVF("fussas", "V_ESSER");addVF("fussan", "V_ESSER");
		// Konditional Indirekt
		addVF("fussi", "V_ESSER");addVF("fussias", "V_ESSER");addVF("fussian", "V_ESSER");
		// Inversion
		addVF("sundiou", "V_ESSER");addVF("segiou", "V_ESSER");addVF("eariou", "V_ESSER");addVF("foviou", "V_ESSER");addVF("fussiou", "V_ESSER");addVF("eni", "V_ESSER");addVF("segiani", "V_ESSER");addVF("earani", "V_ESSER");addVF("fovani", "V_ESSER");addVF("fussani", "V_ESSER");

	    //far
		far("");
		// refar
		far("re");
		// cunterfar
		far("cunter");
		// dasfar
		far("das");
		// sacunfar
		far("sacun");
		// sadasfar
		far("sadas");
		// safar
		far("sa");
		// sarafar
		far("sara");
		// sasurfar
		far("sasur");
		// satisfar
		far("satis");
		
		// fierer
		fierer("");
		// rafierer
		fierer("ra");
		// safierer
		fierer("sa");
		// sasfierer
		fierer("sas");
		//sfierer
		fierer("s");

		// fugir
		stamm = "fug";
		// infinitiv
		addVF("fugier", "V_GVRB");
		// Partizip
		addPartizip(stamm, VerbClass.IR);
		// Gerundium
		addGerundium(stamm + "i", "V_GVRB");
		// Imperativ
		addVF("fui", "V_GVRB");addImperativ(stamm + "i", VerbClass.IR, "V_GVRB");
		// Indikativ Präsens
		addVF("fuig", "V_GVRB");addVF("fuis", "V_GVRB");addVF("fui", "V_GVRB");addVF("fugiagn", "V_GVRB");addVF("fugez", "V_GVRB");addVF("fuin", "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konjunktiv Präsens
		addVF("fuigi", "V_GVRB");addVF("fuigias", "V_GVRB");addVF("fugeian", "V_GVRB");addVF("fugeias", "V_GVRB");addVF("fugian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.ER, "V_GVRB");
		// Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");
		
		// sfugir
		stamm = "sfug";
		// infinitiv
		addVF("sfugier", "V_GVRB");
		// Partizip
		addPartizip(stamm, VerbClass.IR);
		// Gerundium
		addGerundium(stamm + "i", "V_GVRB");
		// Imperativ
		addVF("sfui", "V_GVRB");addImperativ(stamm + "i", VerbClass.IR, "V_GVRB");
		// Indikativ Präsens
		addVF("sfuig", "V_GVRB");addVF("sfuis", "V_GVRB");addVF("sfui", "V_GVRB");addVF("sfugiagn", "V_GVRB");addVF("sfugez", "V_GVRB");addVF("sfuin", "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konjunktiv Präsens
		addVF("sfuigi", "V_GVRB");addVF("sfuigias", "V_GVRB");addVF("sfugeian", "V_GVRB");addVF("sfugeias", "V_GVRB");addVF("sfugian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.ER, "V_GVRB");
		// Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");

		// gir
		gir("");
		// pregir
		gir("pre");
		// cuntergir
		gir("cunter");
		// disgir
		gir("dis");
		// sacuntergir
		gir("sacunter");
		// sagir
		gir("sa");
		
		// ir
		// infinitiv
		addVF("ir", "V_GVRB");
		// Partizip
		addVF("ieu", "V_PP");addVF("ida", "V_PP");
		// Gerundium
		addGerundium("n", "V_GVRB");addGerundium("m", "V_GVRB");
		// Imperativ
		addVF("va", "V_GVRB");addVF("nainsa", "V_GVRB");addVF("mainsa", "V_GVRB");addVF("nad", "V_GVRB");addVF("mad", "V_GVRB");
		// Indikativ Präsens
		addVF("vont", "V_GVRB");addVF("vom", "V_GVRB");addVF("vas", "V_GVRB");addVF("va", "V_GVRB");addVF("nagn", "V_GVRB");addVF("naz", "V_GVRB");addVF("van", "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt("n", VerbClass.IR, "V_GVRB");addIndikativImperfekt("m", VerbClass.IR, "V_GVRB");
		// Konjunktiv Präsens
		addVF("vomi", "V_GVRB");addVF("vomas", "V_GVRB");addVF("vomias", "V_GVRB");addVF("neian", "V_GVRB");addVF("neias", "V_GVRB");addVF("voman", "V_GVRB");addVF("vomian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt("n", VerbClass.ER, "V_GVRB");addKonjunktivImperfekt("m", VerbClass.ER, "V_GVRB");
		// Konditional direkt
		addKonditionalDirekt("n", VerbClass.IR, "V_GVRB");	addKonditionalDirekt("m", VerbClass.IR, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt("n", VerbClass.IR, "V_GVRB");addKonditionalIndirekt("m", VerbClass.IR, "V_GVRB");

		// ler
		// Infinitiv
		addVF("ler", "V_GVRB");
		// Partizip
		addPartizip("l", VerbClass.ER);
		// Gerundium
		addGerundium("l", "V_GVRB");
		// Imperativ
		addVF("viglias", "V_GVRB");addVF("lainsa", "V_GVRB");addVF("leias", "V_GVRB");
		// Indikativ Präsens
		addVF("vi", "V_GVRB");addVF("vol", "V_GVRB");addVF("vut", "V_GVRB");addVF("lagn", "V_GVRB");addVF("lez", "V_GVRB");addVF("vutan", "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt("l", VerbClass.ER, "V_GVRB");
		// Konjunktiv Präsens
		addVF("vigli", "V_GVRB");addVF("vegli", "V_GVRB");addVF("leian", "V_GVRB");addVF("leias", "V_GVRB");addVF("viglian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt("l", VerbClass.ER, "V_GVRB");
		// Konditional Direkt
		addKonditionalDirekt("l", VerbClass.ER, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt("l", VerbClass.ER, "V_GVRB");

		// pruir
		stamm = "pruj";
		// Infinitiv
		addVF("pruir", "V_GVRB");
		// Partizip
		addPartizip(stamm, VerbClass.ER);
		// Gerundium
		addGerundium(stamm, "V_GVRB");
		// indikativ präsens
		addVF("prui", "V_GVRB");addVF("pruin", "V_GVRB");addVF("pruel", "V_GVRB");addVF("pruas", "V_GVRB");addVF("pruagn", "V_GVRB");addVF("pruez", "V_GVRB");
		// indikativ imperfekt
		addIndikativImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// conjunktiv präsens
		addKonjunktivPräsens(stamm, VerbClass.IR, "V_GVRB");
		// conjunktiv imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional indirekt
		addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");

		// puder
		stamm = "pud";
		// Infinitiv
		addVF("puder", "V_MOD");
		// Partizip
		addPartizip(stamm, VerbClass.ER);
		// Gerundium
		addGerundium(stamm, "V_MOD");
		// Indikativ präsens
		addVF("pos", "V_MOD");addVF("po", "V_MOD");addVF("pudagn", "V_MOD");addVF("pudez", "V_MOD");addVF("pon", "V_MOD");
		// Indikativ imperfekt
		addIndikativImperfekt(stamm, VerbClass.ER, "V_MOD");
		// Konjunktiv Präsens
		addVF("possi", "V_MOD");addVF("possas", "V_MOD");addVF("possias", "V_MOD");addVF("pudeian", "V_MOD");addVF("pudeias", "V_MOD");addVF("possan", "V_MOD");addVF("possian", "V_MOD");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.ER, "V_MOD");
		// Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.ER, "V_MOD");
		// konditional indirekt
		addKonditionalIndirekt(stamm, VerbClass.ER, "V_MOD");

		// radir
		stamm = "radig";
		// Infinitiv
		addVF("radir", "V_GVRB");
		// Partizip
		addVF("radetg", "V_GVRB");
		// Gerundium
		addGerundium(stamm + "i", "V_GVRB");
		// Imperativ
		addVF("radi", "V_GVRB");addVF(stamm + "iainsa", "V_GVRB");addVF("radiged", "V_GVRB");
		// Indikativ Präsens
		addVF("radig", "V_GVRB");addVF("radis", "V_GVRB");addVF("radi", "V_GVRB");addVF("radigiagn", "V_GVRB");addVF("radigez", "V_GVRB");addVF("radin", "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konjunktiv Präsens
		addKonjunktivPräsens(stamm, VerbClass.IR, "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional Direkt
		addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");

		// rir
		// Infinitiv
		addVF("rir", "V_GVRB");
		// Partizip
		addVF("ris", "V_GVRB");
		// Gerundium
		addGerundium("ri", "V_GVRB");
		// Imperativ
		addVF("ri", "V_GVRB");addVF(stamm + "riainsa", "V_GVRB");addVF("ried", "V_GVRB");addVF("rié", "V_GVRB");
		// Indikativ Präsens
		addVF("rig", "V_GVRB");addVF("ris", "V_GVRB");addVF("ri", "V_GVRB");addVF("riagn", "V_GVRB");addVF("riez", "V_GVRB");addVF("rin", "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt("ri", VerbClass.IR, "V_GVRB");
		// Konjunktiv Präsens
		addVF("rigi", "V_GVRB");addVF("risi", "V_GVRB");addVF("risas", "V_GVRB");addVF("rigias", "V_GVRB");addVF("rieian", "V_GVRB");addVF("rieias", "V_GVRB");addVF("risan", "V_GVRB");addVF("rigian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt("ri", VerbClass.IR, "V_GVRB");
		// Konditional Direkt
		addKonditionalDirekt("ri", VerbClass.IR, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt("ri", VerbClass.IR, "V_GVRB");

		//star
		ar("st");
		// cunstar
		ar("cunst");
		//cunterstar
		ar("cunterst");
		//surstar
		ar("surst");
		//sutastar
		ar("sutast");

		// ruir
		stamm = "ruj";
		// Infinitiv
		addVF("ruir", "V_GVRB");
		// Partizip
		addVF("ruis", "V_PP");
		// Gerundium
		addGerundium(stamm, "V_GVRB");
		// Imperativ
		addVF("cui", "V_GVRB");addImperativ(stamm, VerbClass.IR, "V_GVRB");
		// Indikativ Präsens
		addVF("ruig", "V_GVRB");addVF("ruis", "V_GVRB");addVF("rujagn", "V_GVRB");addVF("rujez", "V_GVRB");addVF("ruin", "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konjunktiv Präsens
		addVF(stamm + "ruigi", "V_GVRB");addVF(stamm + "ruigias", "V_GVRB");addVF(stamm + "rujeian", "V_GVRB");addVF(stamm + "rujeias", "V_GVRB");addVF(stamm + "ruigian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");

		// ampuder
		stamm = "ampud";
		// Infinitiv
		addVF("ampuder", "V_GVRB");
		// Partizip
		addPartizip(stamm, VerbClass.ER);
		// Gerundium
		addGerundium(stamm, "V_GVRB");
		// Indikativ präsens
		addVF("ampos", "V_GVRB");addVF("ampo", "V_GVRB");addVF("ampudagn", "V_GVRB");addVF("ampudez", "V_GVRB");addVF("ampon", "V_GVRB");
		// Indikativ imperfekt
		addIndikativImperfekt(stamm, VerbClass.ER, "V_GVRB");
		// Konjunktiv Präsens
		addVF("ampossi", "V_GVRB");addVF("ampossas", "V_GVRB");addVF("ampossias", "V_GVRB");addVF("ampudeian", "V_GVRB");addVF("ampudeias", "V_GVRB");addVF("ampossan", "V_GVRB");addVF("ampossian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.ER, "V_GVRB");
		// Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.ER, "V_GVRB");
		// konditional indirekt
		addKonditionalIndirekt(stamm, VerbClass.ER, "V_GVRB");

		//trer
		trer("");
		// atrer
		trer("a");
		// saratrer
		trer("sara");
		// sastrer
		trer("sas");
		// sasurtrer
		trer("sasur");
		// satrer
		trer("sa");
		// surtrer
		trer("sur");
		//strer
		trer("s");
			
		// sacrer
		stamm = "sacart";
		// Infinitiv
		addVF("sacrer", "V_GVRB");
		// Partizip
		addPartizip(stamm, VerbClass.ER);
		// Gerundium
		addGerundium(stamm, "V_GVRB");
		// Imperativ
		addVF("sacre", "V_GVRB");
		addImperativ(stamm, VerbClass.ER, "V_GVRB");
		// Indikativ Präsens
		addVF("sacreg", "V_GVRB");addVF("sacres", "V_GVRB");addVF("sacre", "V_GVRB");addVF("sacartagn", "V_GVRB");addVF("sacartez", "V_GVRB");addVF("sacren", "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.ER, "V_GVRB");
		// Konjunktiv Präsens
		addVF(stamm + "sacregi", "V_GVRB");addVF(stamm + "sacregias", "V_GVRB");addVF(stamm + "sacarteian", "V_GVRB");addVF(stamm + "sacarteias", "V_GVRB");addVF(stamm + "sacregian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");

		// sadar
		ar("sad");

		// saduvierer
		stamm = "saduvier";
		// infinitiv
		addVF("saduvierer", "V_GVRB");
		// Partizip
		addVF("sadunvieretg", "V_PP");
		// Gerundium
		addGerundium(stamm, "V_GVRB");
		// Imperativ
		addVF("saduviera", "V_GVRB");
		addImperativ(stamm, VerbClass.IR, "V_GVRB");
		// Indikativ Präsens
		addVF("saduvier", "V_GVRB");addVF("saduvieras", "V_GVRB");addVF("saduviera", "V_GVRB");addVF("saduvieagn", "V_GVRB");addVF("saduvierez", "V_GVRB");addVF("saduvieran", "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konjunktiv Präsens
		addVF("saduvieri", "V_GVRB");addVF("saduvierias", "V_GVRB");addVF("saduviereian", "V_GVRB");addVF("saduviereias", "V_GVRB");addVF("saduvierian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.ER, "V_GVRB");
		// Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");

		// saduvrir
		stamm = "saduvr";
		// infinitiv
		addVF("saduvrir", "V_GVRB");
		// Partizip
		addVF("saduvretg", "V_PP");
		// Gerundium
		addGerundium(stamm, "V_GVRB");
		// Imperativ
		addVF("saduvriera", "V_GVRB");
		addImperativ(stamm, VerbClass.IR, "V_GVRB");
		// Indikativ Präsens
		addVF("saduvrier", "V_GVRB");addVF("saduvrieras", "V_GVRB");addVF("saduvriera", "V_GVRB");addVF("saduvragn", "V_GVRB");addVF("saduvrez", "V_GVRB");addVF("sadunvrevan", "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konjunktiv Präsens
		addVF("saduvrieri", "V_GVRB");addVF("saduvrierias", "V_GVRB");addVF("saduvreian", "V_GVRB");addVF("saduvreias", "V_GVRB");addVF("saduvrierian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.ER, "V_GVRB");
		// Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");	

		// sasurdar
		ar("sasurd");
	
		// saver
		stamm = "sav";
		//Infinitiv
		addVF("saver", "V_GVRB");
		//Partizip
		addPartizip(stamm, VerbClass.ER);
		//Gerundium
		addGerundium(stamm, "V_GVRB");
		//Imperativ
		addVF("setgas", "V_GVRB");addVF("setgias", "V_GVRB");addVF("saveian", "V_GVRB");addVF("saveias", "V_GVRB");
		//Indikativ Präsens
		addVF("se", "V_GVRB");addVF("sas", "V_GVRB");addVF("savagn", "V_GVRB");addVF("savez", "V_GVRB");addVF("san", "V_GVRB");
		//Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.ER, "V_GVRB");
		//Konjunktiv Präsens
		addVF("setgi", "V_GVRB");addVF("setgas", "V_GVRB");addVF("setgias", "V_GVRB");addVF("setgan", "V_GVRB");addVF("setgian", "V_GVRB");
		//Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.ER, "V_GVRB");
		//Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.ER, "V_GVRB");
		//Konditional indirekt
		addKonditionalIndirekt(stamm, VerbClass.ER, "V_GVRB");

		// schar
		schar("");
		// laschar
		schar("la");
		// relaschar
		schar("rela");
		// surlaschar
		schar("surla");
		// surschar
		schar("sur");
		// tralaschar
		schar("tralar");	
		// saschar
		schar("sa");
		
		// scher
		stamm = "schasch";
		//Infinitiv
		addVF("scher", "V_GVRB");
		//Partizip
		addPartizip(stamm, VerbClass.IR);
		//Gerundium
		addGerundium(stamm, "V_GVRB");
		//Imperativ
		addVF("sche", "V_GVRB");addImperativ(stamm, VerbClass.ER, "V_GVRB");
		//Indikativ Präsens
		addVF("schesch", "V_GVRB");addVF("sches", "V_GVRB");addVF("sche", "V_GVRB");addVF("schaschagn", "V_GVRB");addVF("schaschez", "V_GVRB");addVF("schen", "V_GVRB");
		//Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.ER, "V_GVRB");
		//Konjunktiv Präsens
		addVF("scheschi", "V_GVRB");addVF("scheschas", "V_GVRB");addVF("scheschias", "V_GVRB");addVF("schascheian", "V_GVRB");addVF("schascheias", "V_GVRB");addVF("scheschan", "V_GVRB");addVF("scheschian", "V_GVRB");
		//Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.ER, "V_GVRB");
		//Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.ER, "V_GVRB");
		//Konditional indirekt
		addKonditionalIndirekt(stamm, VerbClass.ER, "V_GVRB");

		// sdrir
		stamm = "sdri";
		//Infinitiv
		addVF("sdrir", "V_GVRB");
		//Partizip
		addVF("sdretg", "V_GVRB");
		//Gerundium
		addGerundium(stamm, "V_GVRB");
		//Imperativ
		addVF("sdri", "V_GVRB");addImperativ(stamm, VerbClass.IR, "V_GVRB");
		//Indikativ Präsens
		addVF("sdri", "V_GVRB");addVF("sdris", "V_GVRB");addVF("sdriagn", "V_GVRB");addVF("sdriez", "V_GVRB");addVF("sdrin", "V_GVRB");
		//Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.IR, "V_GVRB");
		//Konjunktiv Präsens
		addVF("sdrigi", "V_GVRB");addVF("sdrigias", "V_GVRB");addVF("sdrigeian", "V_GVRB");addVF("sdrigeias", "V_GVRB");addVF("sdrigian", "V_GVRB");
		//Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.IR, "V_GVRB");
		//Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");
		//Konditional indirekt
		addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");

		// sear
		stamm = "sas";
		//Infinitiv
		addVF("sear", "V_GVRB");
		//Partizip
		addPartizip(stamm, VerbClass.IR);
		//Gerundium
		addGerundium(stamm, "V_GVRB");
		//Imperativ
		addVF("sea", "V_GVRB");addImperativ(stamm, VerbClass.ER, "V_GVRB");
		//Indikativ Präsens
		addVF("seas", "V_GVRB");addVF("sea", "V_GVRB");addVF("sasagn", "V_GVRB");addVF("sasez", "V_GVRB");addVF("sean", "V_GVRB");
		//Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.ER, "V_GVRB");
		//Konjunktv Präsens
		addVF("seasi", "V_GVRB");addVF("seasias", "V_GVRB");addVF("seasas", "V_GVRB");addVF("saseian", "V_GVRB");addVF("saseias", "V_GVRB");addVF("seasian", "V_GVRB");addVF("seasan", "V_GVRB");
		//Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.ER, "V_GVRB");
		//Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.ER, "V_GVRB");
		//Konditional indirekt
		addKonditionalIndirekt(stamm, VerbClass.ER, "V_GVRB");

		// sfuir
		stamm = "sfuj";
		//Infinitiv
		addVF("sfuir", "V_GVRB");
		// Partizip
		addPartizip(stamm, VerbClass.ER);
		// Gerundium
		addGerundium(stamm, "V_GVRB");
		// indikativ präsens
		addVF("sfui", "V_GVRB");addVF("sfuis", "V_GVRB");addVF("sfujagn", "V_GVRB");addVF("sfuin", "V_GVRB");addVF("sfujez", "V_GVRB");
		// indikativ imperfekt
		addIndikativImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// conjunktiv präsens
		addVF("sfuji", "V_GVRB");addVF("sfujias", "V_GVRB");addVF("sfujas", "V_GVRB");addVF("sfujeian", "V_GVRB");addVF("sfujeias", "V_GVRB");addVF("sfujian", "V_GVRB");addVF("sfujan", "V_GVRB");
		// conjunktiv imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional indirekt
		addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");

		// stuer
		stamm = "stu";
		//Infinitiv
		addVF("stuer", "V_MOD");
		//Partizip
		addPartizip(stamm, VerbClass.ER);
		//Gerundium
		addGerundium(stamm, "V_MOD");
		//Indikativ Präsens
		addVF("sto", "V_MOD");addVF("stos", "V_MOD");addVF("stuagn", "V_MOD");addVF("stuez", "V_MOD");addVF("ston", "V_MOD");
		//Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.ER, "V_MOD");
		//Konjunktiv Präsens
		addVF("stotgi", "V_MOD");addVF("stotgias", "V_MOD");addVF("stotgas", "V_MOD");addVF("stueian", "V_MOD");addVF("stueias", "V_MOD");addVF("stotgan", "V_MOD");addVF("stotgian", "V_MOD");
		//Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.ER, "V_MOD");
		//Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.ER, "V_MOD");
		//Konditional indirekt
		addKonditionalIndirekt(stamm, VerbClass.ER, "V_MOD");

		// surdar
		ar("surd");

		// surir
		stamm = "sur";
		// Infinitiv
		addVF("surir", "V_GVRB");
		// Partizip
		addVF("suris", "V_GVRB");
		// Gerundium
		addGerundium(stamm+"i", "V_GVRB");
		// Imperativ
		addVF("suri", "V_GVRB");addImperativ(stamm+"i", VerbClass.IR, "V_GVRB");
		// Indikativ Präsens
		addVF("surig", "V_GVRB");addVF("suris", "V_GVRB");addVF("suri", "V_GVRB");addVF("suriagn", "V_GVRB");addVF("suriez", "V_GVRB");addVF("surin", "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt(stamm+"i", VerbClass.IR, "V_GVRB");
		// Konjunktiv Präsens
		addVF("surigi", "V_GVRB");addVF("surisi", "V_GVRB");addVF("surisas", "V_GVRB");addVF("surigias", "V_GVRB");addVF("surieian", "V_GVRB");addVF("surieias", "V_GVRB");addVF("surisan", "V_GVRB");addVF("surigian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm+"i", VerbClass.IR, "V_GVRB");
		// Konditional Direkt
		addKonditionalDirekt(stamm+"i", VerbClass.IR, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt(stamm+"i", VerbClass.IR, "V_GVRB");

		// ver
		ver("");
		// prever
		ver("pre");
		// parver
		ver("par");
		// surver
		ver("sur");

		// tascher
		ascher("t");
		// plascher
		ascher("pl");
		// displascher
		ascher("displ");
		// saplascher
		ascher("sapl");

		// vagnir
		vagnir("");
		// prevagnir
		vagnir("pre");
		// intervagnir
		vagnir("inter");
		// sacunvagnir
		vagnir("sacun");
		// saravagnir
		vagnir("sara");
		// survagnir
		vagnir("sur");

		// vaser
		//Infinitiv
		addVF("vaser", "V_GVRB");
		//Partizip
		addVF("vasieu", "V_GVRB");addVF("vasazida", "V_GVRB");addVF("vasasida", "V_GVRB");
		//gerundium
		addGerundium("vasaz", "V_GVRB"); addGerundium("vasas", "V_GVRB");
		//Indikativ Präsens
		addVF("vasez", "V_GVRB");addVF("vasezas", "V_GVRB");addVF("vaseza", "V_GVRB");addVF("vasazagn", "V_GVRB");addVF("vasasagn", "V_GVRB");addVF("vasus", "V_GVRB");addVF("vasazez", "V_GVRB");addVF("vasasez", "V_GVRB");addVF("vasezan", "V_GVRB");
		//Indikativ Imperfekt
		addIndikativImperfekt("vasaz", VerbClass.ER, "V_GVRB");addIndikativImperfekt("vasas", VerbClass.ER, "V_GVRB");
		//Konjunktiv Präsens
		addVF("vasezi", "V_GVRB");addVF("vasezas", "V_GVRB");addVF("vasezias", "V_GVRB");addVF("vasazeian", "V_GVRB");addVF("vasaseian", "V_GVRB");addVF("vasazeias", "V_GVRB");addVF("vasaseias", "V_GVRB");addVF("vasezan", "V_GVRB");addVF("vasezian", "V_GVRB");
		//Konjunktiv Imperfekt
		addKonjunktivImperfekt("vasaz", VerbClass.ER, "V_GVRB");addVF("vasasevi", "V_GVRB");addVF("vasasevias", "V_GVRB");addVF("vasasevian", "V_GVRB");
		//konditional direkt
		addKonditionalDirekt("vasaz", VerbClass.ER, "V_GVRB");addKonditionalDirekt("vasas", VerbClass.ER, "V_GVRB");
		//konditional indirekt
		addKonditionalIndirekt("vasaz", VerbClass.ER, "V_GVRB");addKonditionalIndirekt("vasas", VerbClass.ER, "V_GVRB");

		// parvaser
		//Infinitiv
		addVF("parvaser", "V_GVRB");
		//Partizip
		addVF("parvasieu", "V_GVRB");addVF("parvasazida", "V_GVRB");addVF("parvasasida", "V_GVRB");
		//gerundium
		addGerundium("parvasaz", "V_GVRB");addGerundium("parvasas", "V_GVRB");
		//Indikativ Präsens
		addVF("parvasez", "V_GVRB");addVF("parvasezas", "V_GVRB");addVF("parvaseza", "V_GVRB");addVF("parvasazagn", "V_GVRB");addVF("parvasasagn", "V_GVRB");addVF("parvasus", "V_GVRB");addVF("parvasazez", "V_GVRB");addVF("parvasasez", "V_GVRB");addVF("parvasezan", "V_GVRB");
		//Indikativ Imperfekt
		addIndikativImperfekt("parvasaz", VerbClass.ER, "V_GVRB");addIndikativImperfekt("parvasas", VerbClass.ER, "V_GVRB");
		//Konjunktiv Präsens
		addVF("parvasezi", "V_GVRB");addVF("parvasezas", "V_GVRB");addVF("parvasezias", "V_GVRB");addVF("parvasazeian", "V_GVRB");addVF("parvasaseian", "V_GVRB");addVF("parvasazeias", "V_GVRB");addVF("parvasaseias", "V_GVRB");addVF("parvasezan", "V_GVRB");addVF("parvasezian", "V_GVRB");
		//Konjunktiv Imperfekt
		addKonjunktivImperfekt("vasaz", VerbClass.ER, "V_GVRB");addVF("parvasasevi", "V_GVRB");addVF("parvasasevias", "V_GVRB");addVF("parvasasevian", "V_GVRB");
		//konditional direkt
		addKonditionalDirekt("parvasaz", VerbClass.ER, "V_GVRB");addKonditionalDirekt("parvasas", VerbClass.ER, "V_GVRB");
		//konditional indirekt
		addKonditionalIndirekt("parvasaz", VerbClass.ER, "V_GVRB");addKonditionalIndirekt("parvasas", VerbClass.ER, "V_GVRB");

		// vuler
		// Infinitiv
		addVF("vuler", "V_MOD");
		// Partizip
		addPartizip("vul", VerbClass.ER);
		// Gerundium
		addGerundium("vul", "V_MOD");
		// Imperativ
		addVF("vuviglias", "V_MOD");addVF("vulainsa", "V_MOD");addVF("vuleias", "V_MOD");
		// Indikativ Präsens
		addVF("vuvi", "V_MOD");addVF("vuvol", "V_MOD");addVF("vuvut", "V_MOD");addVF("vulagn", "V_MOD");addVF("vulez", "V_MOD");addVF("vuvutan", "V_MOD");
		// Indikativ Imperfekt
		addIndikativImperfekt("vul", VerbClass.ER, "V_MOD");
		// Konjunktiv Präsens
		addVF("vuvigli", "V_MOD");addVF("vuvegli", "V_MOD");addVF("vuleian", "V_MOD");addVF("vuleias", "V_MOD");addVF("vuviglian", "V_MOD");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt("vul", VerbClass.ER, "V_MOD");
		// Konditional Direkt
		addKonditionalDirekt("vul", VerbClass.ER, "V_MOD");
		// Konditional Indirekt
		addKonditionalIndirekt("vul", VerbClass.ER, "V_MOD");

		// zircumdar
		ar("zircumd");

	}
	
		private void ascher(String präfix){
		String stamm = präfix+"asch";
		//infinitiv
		addVF(präfix+"ascher", "V_GVRB");
		//Partizip
		addPartizip(stamm, VerbClass.ER);
		//gerundium
		addGerundium(stamm, "V_GVRB");
		//Imperativ
		addVF(präfix+"e", "V_GVRB");addImperativ(stamm, VerbClass.ER, "V_GVRB");
		//Indikativ präsens
		addVF(präfix+"esch", "V_GVRB");addVF(präfix+"es", "V_GVRB");addVF(präfix+"e", "V_GVRB");addVF(präfix+"aschagn", "V_GVRB");addVF(präfix+"aschez", "V_GVRB");addVF(präfix+"en", "V_GVRB");
		//Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.ER, "V_GVRB");
		//Konjunktiv Präsens
		addVF(präfix+"eschi", "V_GVRB");addVF(präfix+"eschias", "V_GVRB");addVF(präfix+"eschas", "V_GVRB");addVF(stamm+"eian", "V_GVRB");addVF(stamm+"eias", "V_GVRB");addVF(präfix+"eschian", "V_GVRB");addVF(präfix+"eschan", "V_GVRB");
		//Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.ER, "V_GVRB");
		//konditional direkt
		addKonditionalDirekt(stamm, VerbClass.ER, "V_GVRB");
		//konditional indirekt
		addKonditionalIndirekt(stamm, VerbClass.ER, "V_GVRB");
	}
	
		private void schar(String präfix){
		String stamm = präfix+"sch";
		//Infinitiv
		addVF(präfix+"schar", "V_GVRB");
		//Partizip
		addVF(präfix+"scho", "V_GVRB");
		//Gerundium
		addGerundium(stamm, "V_GVRB");addGerundium("la"+stamm, "V_GVRB");
		//Imperativ
		addVF(präfix+"le", "V_GVRB");addVF(stamm+"ainsa", "V_GVRB");addVF(stamm+"a", "V_GVRB");addVF(stamm+"é", "V_GVRB");
		//Indikativ Präsens
		addVF(präfix+"lasch", "V_GVRB");addVF(präfix+"lesch", "V_GVRB");addVF(präfix+"laschas", "V_GVRB");addVF(präfix+"lascha", "V_GVRB");addVF(präfix+"schagn", "V_GVRB");addVF(präfix+"schaz", "V_GVRB");addVF(präfix+"schez", "V_GVRB");addVF(präfix+"laschan", "V_GVRB");addVF(präfix+"len", "V_GVRB");
		//Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.AR, "V_GVRB");addIndikativImperfekt(stamm, VerbClass.ER, "V_GVRB");
		//Konjunktiv Präsens
		addVF(präfix+"laschi", "V_GVRB");addVF(präfix+"laschas", "V_GVRB");addVF(präfix+"laschias", "V_GVRB");addVF(präfix+"scheian", "V_GVRB");addVF(präfix+"scheias", "V_GVRB");addVF(präfix+"laschian", "V_GVRB");addVF(präfix+"laschan", "V_GVRB");
		//Konunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.AR, "V_GVRB");addKonjunktivImperfekt(stamm, VerbClass.ER, "V_GVRB");
		//Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.AR, "V_GVRB");addKonditionalDirekt(stamm, VerbClass.ER, "V_GVRB");
		//Konditional indirekt
		addKonditionalIndirekt(stamm, VerbClass.AR, "V_GVRB");addKonditionalIndirekt(stamm, VerbClass.ER, "V_GVRB");
	}
	
		private void trer(String präfix){
		String stamm = präfix+"targ";
		// Infinitiv
		addVF(präfix+"trer", "V_GVRB");
		// Partizip
		addVF(präfix+"tratg", "V_GVRB");
		//Gerundium
		addGerundium(stamm + "i", "V_GVRB");
		//Imperativ
		addVF(präfix+"tira", "V_GVRB");addVF(präfix+"targiainsa", "V_GVRB");addVF(präfix+"targed", "V_GVRB");addVF(präfix+"targé", "V_GVRB");
		//Indikativ Präsens
		addVF(präfix+"tir", "V_GVRB");addVF(präfix+"tiras", "V_GVRB");addVF(präfix+"tira", "V_GVRB");addVF(präfix+"targiagn", "V_GVRB");addVF(präfix+"targez", "V_GVRB");addVF(präfix+"tiran", "V_GVRB");
		//Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.ER, "V_GVRB");
		//Konjunktiv Präsens
		addVF(präfix+"tiri", "V_GVRB");addVF(präfix+"tiras", "V_GVRB");addVF(präfix+"tirias", "V_GVRB");addVF(präfix+"targeian", "V_GVRB");addVF(präfix+"targeias", "V_GVRB");addVF(präfix+"tirian", "V_GVRB");
		//Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.ER, "V_GVRB");
		//konditional direkt
		addKonditionalDirekt(stamm, VerbClass.ER, "V_GVRB");
		//konditional indirekt
		addKonditionalIndirekt(stamm, VerbClass.ER, "V_GVRB");
	}
	
		private void ver(String präfix){
		String stamm1 = präfix+"vas";
		String stamm2 = präfix+"vaz";
		//Infinitiv
		addVF(präfix+"ver", "V_GVRB");
		//Partizip
		addVF(präfix+"vieu", "V_GVRB");addVF(stamm1+"ida", "V_GVRB");addVF(stamm2+"ida", "V_GVRB");
		//gerundium
		addGerundium(stamm1, "V_GVRB");addGerundium(stamm2, "V_GVRB");
		//Indikativ Präsens
		addVF(präfix+"vez", "V_GVRB");addVF(präfix+"vezas", "V_GVRB");addVF(präfix+"veza", "V_GVRB");addVF(stamm2+"agn", "V_GVRB");addVF(stamm1+"agn", "V_GVRB");addVF(präfix+"vus", "V_GVRB");addVF(stamm2+"ez", "V_GVRB");addVF(stamm1+"ez", "V_GVRB");addVF(präfix+"vezan", "V_GVRB");
		//Indikativ Imperfekt
		addIndikativImperfekt(stamm2, VerbClass.ER, "V_GVRB");addIndikativImperfekt(stamm1, VerbClass.ER, "V_GVRB");
		//Konjunktiv Präsens
		addVF(präfix+"vezi", "V_GVRB");addVF(präfix+"vezas", "V_GVRB");addVF(präfix+"vezias", "V_GVRB");addVF(stamm2+"eian", "V_GVRB");addVF(stamm1+"eian", "V_GVRB");addVF(stamm2+"eias", "V_GVRB");addVF(stamm1+"eias", "V_GVRB");addVF(präfix+"vezan", "V_GVRB");addVF(präfix+"vezian", "V_GVRB");
		//Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm2, VerbClass.ER, "V_GVRB");addVF(stamm1+"evi", "V_GVRB");addVF(stamm1+"evias", "V_GVRB");addVF(stamm1+"evian", "V_GVRB");
		//Konditional direkt
		addKonditionalDirekt(stamm2, VerbClass.ER, "V_GVRB");addKonditionalDirekt(stamm1, VerbClass.ER, "V_GVRB");
		//Konditional indirekt
		addKonditionalIndirekt(stamm2, VerbClass.ER, "V_GVRB");addKonditionalIndirekt(stamm1, VerbClass.ER, "V_GVRB");
	}
	
		private void antalir(String präfix){
		String stamm = präfix+"antal";
		// Infinitiv
		addVF(präfix+"antalir", "V_GVRB");
		// Partizip
		addVF(stamm + "etg", "V_PP");
		// Gerundium
		addGerundium(stamm+"gi", "V_GVRB");
		// Imperativ
		addVF(stamm + "i", "V_GVRB");addVF(stamm + "giagn", "V_GVRB");addVF(stamm + "ged", "V_GVRB");
		// Indikativ Präsens
		addVF(stamm + "ig", "V_GVRB");addVF(stamm + "is", "V_GVRB");addVF(stamm + "i", "V_GVRB");addVF(stamm + "giagn", "V_GVRB");addVF(stamm + "gez", "V_GVRB");addVF(stamm + "in", "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt(stamm + "g", VerbClass.IR, "V_GVRB");
		// Konjunktiv Präsens
		addVF(stamm + "igi", "V_GVRB");addVF(stamm + "igias", "V_GVRB");addVF(stamm + "geian", "V_GVRB");addVF(stamm + "geias", "V_GVRB");addVF(stamm + "geian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm + "g", VerbClass.IR, "V_GVRB");
		// Konditional direkt
		addKonditionalDirekt(stamm + "g", VerbClass.IR, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt(stamm + "g", VerbClass.IR, "V_GVRB");
		// Inversion
		addVF(stamm + "igiou", "V_GVRB");addVF(stamm + "geviou", "V_GVRB");addVF(stamm + "gessiou", "V_GVRB");addVF(stamm + "giagnsa", "V_GVRB");addVF(stamm + "ini", "V_GVRB");addVF(stamm + "geiani", "V_GVRB");addVF(stamm + "gevani", "V_GVRB");addVF(stamm + "gessani", "V_GVRB");
	}
	
		private void ar(String präfix){
		// ar
		String stamm = präfix;
		// infinitiv
		addVF(stamm+"ar", "V_GVRB");
		// Partizip
		addVF(stamm+"o", "V_PP");addVF(stamm+"ada", "V_PP");	
		// Gerundium
		addGerundium(stamm, "V_GVRB");
		// Imperativ
		addVF(stamm+"e", "V_GVRB");addImperativ(stamm, VerbClass.AR, "V_GVRB");
		// Indikativ Präsens
		addVF(stamm+"und", "V_GVRB");addVF(stamm+"as", "V_GVRB");addVF(stamm+"at", "V_GVRB");addVF(stamm+"agn", "V_GVRB");addVF(stamm+"ez", "V_GVRB");addVF(stamm+"atan", "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.AR, "V_GVRB");
		// Konjunktiv Präsens
		addVF(stamm + "eti", "V_GVRB");addVF(stamm + "etas", "V_GVRB");addVF(stamm + "etias", "V_GVRB");addVF(stamm + "eian", "V_GVRB");addVF(stamm + "eias", "V_GVRB");addVF(stamm + "etan", "V_GVRB");addVF(stamm + "etian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");
	}
	
		private void far(String präfix){
		// far
		String stamm = präfix+"fag";
		// infinitiv
		addVF(präfix+"far", "V_GVRB");
		// Partizip
		addVF(präfix+"fatg", "V_PP");
		// Gerundium
		addGerundium(stamm+"i", "V_GVRB");
		// Imperativ
		addVF("fe", "V_GVRB");
		addImperativ(präfix+"f", VerbClass.AR, "V_GVRB");addImperativ(stamm, VerbClass.AR, "V_GVRB");
		// Indikativ Präsens
		addVF(präfix+"fetsch", "V_GVRB");addVF(präfix+"fas", "V_GVRB");addVF(präfix+"fa", "V_GVRB");addVF(präfix+"fagiagn", "V_GVRB");addVF(präfix+"fagn", "V_GVRB");addVF(präfix+"fagez", "V_GVRB");addVF(präfix+"fez", "V_GVRB");addVF(präfix+"fan", "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.AR, "V_GVRB");addIndikativImperfekt(präfix+"f", VerbClass.AR, "V_GVRB");
		// Konjunktiv Präsens
		addVF(präfix+"fetschi", "V_GVRB");addVF(präfix+"fetschas", "V_GVRB");addVF(präfix+"fetschias", "V_GVRB");addVF(präfix+"fageian", "V_GVRB");addVF(präfix+"feian", "V_GVRB");addVF(präfix+"fageias", "V_GVRB");addVF(präfix+"feias", "V_GVRB");addVF(präfix+"fetschan", "V_GVRB");addVF(präfix+"fetschian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.ER, "V_GVRB");addKonjunktivImperfekt(präfix+"f", VerbClass.ER, "V_GVRB");
		// Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");addKonditionalDirekt(präfix+"f", VerbClass.IR, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");addKonditionalIndirekt(präfix+"f", VerbClass.IR, "V_GVRB");
	}

		private void fierer(String präfix){
		String stamm = präfix+"fier";
		// infinitiv
		addVF(präfix+"fierer", "V_GVRB");
		// Partizip
		addVF(stamm+"s", "V_PP");
		// Gerundium
		addGerundium(stamm, "V_GVRB");
		// Imperativ
		addImperativ(stamm, VerbClass.ER, "V_GVRB");
		// Indikativ Präsens
		addIndikativPräsens(stamm, VerbClass.IR, "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konjunktiv Präsens
		addKonjunktivPräsens(stamm, VerbClass.IR, "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.ER, "V_GVRB");
		// Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");
	}
	
		private void gir(String präfix){
		String stamm = präfix+"g";
		// infinitiv
		addVF(präfix+"gir", "V_GVRB");
		// Partizip
		addVF(präfix+"getg", "V_PP");
		// Gerundium
		addGerundium(stamm+"i", "V_GVRB");
		// Imperativ
		addVF(stamm+"i", "V_GVRB");
		addVF(präfix+"giainsa", "V_GVRB");
		addVF(präfix+"ged", "V_GVRB");
		addVF(präfix+"gé", "V_GVRB");
		// Indikativ Präsens
		addVF(stamm+"itg", "V_GVRB");addVF(stamm+"is", "V_GVRB");addVF(stamm+"i", "V_GVRB");addVF(stamm+"iagn", "V_GVRB");addVF(stamm+"ez", "V_GVRB");addVF(stamm+"in", "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konjunktiv Präsens
		addVF(stamm+"itgi", "V_GVRB");addVF(stamm+"itgias", "V_GVRB");addVF(stamm+"itgas", "V_GVRB");addVF(stamm+"eian", "V_GVRB");addVF(stamm+"eias", "V_GVRB");addVF(stamm+"itgan", "V_GVRB");addVF(stamm+"itgian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.ER, "V_GVRB");
		// Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");
	}
	
		private void cuvrir(String präfix){
		String stamm = präfix+"cuvr";
		// Infinitiv
		addVF(präfix+"cuvrir", "V_GVRB");
		// Partizip
		addVF(präfix+"cuviert", "V_PP");
		// Gerundium
		addGerundium(stamm, "V_GVRB");
		// Imperativ
		addVF(präfix+"cuviera", "V_GVRB");
		addImperativ(stamm, VerbClass.IR, "V_GVRB");
		// Indikativ Präsens
		addVF(präfix+"cuvier", "V_GVRB");addVF(präfix+"cuvieras", "V_GVRB");addVF(präfix+"cuviera", "V_GVRB");addVF(präfix+"cuvragn", "V_GVRB");addVF(präfix+"cuvrez", "V_GVRB");addVF(präfix+"cuvieran", "V_GVRB");
		// Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konjunktiv Präsens
		addVF(präfix+"cuvieri", "V_GVRB");addVF(präfix+"cuvierias", "V_GVRB");addVF(präfix+"cuvreian", "V_GVRB");addVF(präfix+"cuvreias", "V_GVRB");addVF(präfix+"cuvierian", "V_GVRB");
		// Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional direkt
		addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");
		// Konditional Indirekt
		addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");
	}
	
		private void vagnir(String präfix){
		String stamm = präfix+"vagn";
		//infinitiv
		addVF(präfix+"vagnir", "V_GVRB");
		//partizip
		addPartizip(stamm, VerbClass.IR);
		//gerundium
		addGerundium(stamm, "V_GVRB");
		//Imperativ
		addVF(präfix+"via", "V_GVRB");addImperativ(stamm, VerbClass.IR, "V_GVRB");
		//Indikativ Präsens
		addVF(präfix+"vignt", "V_GVRB");addVF(präfix+"vegn", "V_GVRB");addVF(präfix+"veans", "V_GVRB");addVF(präfix+"vean", "V_GVRB");addVF(stamm+"agn", "V_GVRB");addVF(stamm+"ez", "V_GVRB");addVF(präfix+"vignan", "V_GVRB");addVF(präfix+"vegnan", "V_GVRB");
		//Indikativ Imperfekt
		addIndikativImperfekt(stamm, VerbClass.IR, "V_GVRB");
		//Konjunktiv Präsens
		addVF(präfix+"vigni", "V_GVRB");addVF(präfix+"vignias", "V_GVRB");addVF(präfix+"vignas", "V_GVRB");addVF(stamm+"eian", "V_GVRB");addVF(stamm+"eias", "V_GVRB");addVF(stamm+"evan", "V_GVRB");
		//Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, VerbClass.IR, "V_GVRB");
		//konditional direkt
		addKonditionalDirekt(stamm, VerbClass.IR, "V_GVRB");
		//konditional indirekt
		addKonditionalIndirekt(stamm, VerbClass.IR, "V_GVRB");
	}
	

	
	private void generateVerbForms(String entry) {
		// Infinitiv
		addVF(entry, "V_GVRB");
		VerbClass verbClass;
		String endung = entry.substring(entry.length() - 2);
		String stamm = entry.substring(0, entry.length() - 2);
		if (endung.equals("ar")) {
			if (stamm.charAt(stamm.length() - 1) == 'e') {
				stamm = stamm.substring(0, stamm.length() - 1);
				verbClass = VerbClass.EAR;
			} else {
				verbClass = VerbClass.AR;
			}
		} else if (endung.equals("er")) {
			verbClass = VerbClass.ER;
		} else if (endung.equals("ir")) {
			verbClass = VerbClass.IR;
		} else {
			System.out.println("unbekanntes Verb "+entry+" - falsche Endung: -" + endung+ "-");
			return;
		}
		// Partizip
		addPartizip(stamm, verbClass);
		addGerundium(stamm, "V_GVRB");
		addImperativ(stamm, verbClass, "V_GVRB");
		addIndikativPräsens(stamm, verbClass, "V_GVRB");
		addIndikativImperfekt(stamm, verbClass, "V_GVRB");
		addKonjunktivPräsens(stamm, verbClass, "V_GVRB");
		addKonjunktivImperfekt(stamm, verbClass, "V_GVRB");
		addKonditionalDirekt(stamm, verbClass, "V_GVRB");
		addKonditionalIndirekt(stamm, verbClass, "V_GVRB");
		addInversion(stamm, verbClass, "V_GVRB");

	}

		private void addInversion(String stamm, VerbClass verbClass, String pos) {
		addVF(stamm + "iou", pos);
		addVF(stamm + "ani", pos);
		addVF(stamm + "ainsa", pos);
		if (verbClass == VerbClass.AR) {
			addVF(stamm + "aviou", pos);
			addVF(stamm + "assiou", pos);
			addVF(stamm + "avani", pos);
			addVF(stamm + "assani", pos);
		} else if (verbClass == VerbClass.EAR) {
			addVF(stamm + "eaviou", pos);
			addVF(stamm + "essiou", pos);
			addVF(stamm + "eavani", pos);
			addVF(stamm + "essani", pos);
		} else {
			addVF(stamm + "eviou", pos);
			addVF(stamm + "essiou", pos);
			addVF(stamm + "evani", pos);
			addVF(stamm + "essani", pos);
		}
	}

		private void addKonditionalIndirekt(String stamm, VerbClass verbClass,
			String pos) {
		if (verbClass == VerbClass.AR) {
			addVF(stamm + "assi", pos);
			addVF(stamm + "assas", pos);
			addVF(stamm + "assias", pos);
			addVF(stamm + "assan", pos);
			addVF(stamm + "assian", pos);
		}
	}

		private void addKonditionalDirekt(String stamm, VerbClass verbClass,
			String pos) {
		if (verbClass == VerbClass.AR) {
			addVF(stamm + "ass", pos);
			addVF(stamm + "assas", pos);
			addVF(stamm + "assan", pos);
		} else {
			addVF(stamm + "ess", pos);
			addVF(stamm + "essas", pos);
			addVF(stamm + "essan", pos);
		}
	}

		private void addKonjunktivImperfekt(String stamm, VerbClass verbClass,
			String pos) {
		if (verbClass == VerbClass.AR) {
			addVF(stamm + "avi", pos);
			addVF(stamm + "avas", pos);
			addVF(stamm + "avias", pos);
			addVF(stamm + "avan", pos);
			addVF(stamm + "avian", pos);
		} else {
			addVF(stamm + "evi", pos);
			addVF(stamm + "evas", pos);
			addVF(stamm + "evias", pos);
			addVF(stamm + "evan", pos);
			addVF(stamm + "evian", pos);
		}
	}

		private void addKonjunktivPräsens(String stamm, VerbClass verbClass,
			String pos) {
		addVF(stamm + "i", pos);
		addVF(stamm + "as", pos);
		addVF(stamm + "ias", pos);
		addVF(stamm + "eian", pos);
		addVF(stamm + "eias", pos);
		addVF(stamm + "an", pos);
		addVF(stamm + "ian", pos);
	}

		private void addIndikativImperfekt(String stamm, VerbClass verbClass,
			String pos) {
		if (verbClass == VerbClass.EAR) {
			stamm = stamm + "e";
		}
		if (verbClass == VerbClass.AR || verbClass == VerbClass.EAR) {
			addVF(stamm + "ava", pos);
			addVF(stamm + "avas", pos);
			addVF(stamm + "avan", pos);
		} else {
			addVF(stamm + "eva", pos);
			addVF(stamm + "evas", pos);
			addVF(stamm + "evan", pos);
		}
	}

		private void addIndikativPräsens(String stamm, VerbClass verbClass,
			String pos) {
		addVF(stamm, pos);
		addVF(stamm + "el", pos);
		addVF(stamm + "as", pos);
		addVF(stamm + "a", pos);
		addVF(stamm + "agn", pos);
		addVF(stamm + "an", pos);
		if (verbClass == VerbClass.AR) {
			addVF(stamm + "az", pos);
		} else if (verbClass == VerbClass.EAR) {
			addVF(stamm + "eaz", pos);
		} else {
			addVF(stamm + "ez", pos);
		}
	}

		private void addImperativ(String stamm, VerbClass verbClass, String pos) {
		addVF(stamm + "a", pos);
		addVF(stamm + "ainsa", pos);
		if (verbClass == VerbClass.AR) {
			addVF(stamm + "ad", pos);
			addVF(stamm + "à", pos);
		}
		if (verbClass == VerbClass.EAR) {
			addVF(stamm + "ead", pos);
			addVF(stamm + "é", pos);
		} else {
			addVF(stamm + "é", pos);
			addVF(stamm + "ed", pos);
		}
	}

		private void addGerundium(String stamm, String pos) {
		addVF(stamm + "ànt", pos);
	}

		private void addPartizip(String stamm, VerbClass verbClass) {
		if (verbClass == VerbClass.AR) {
			addVF(stamm + "o", "V_PP");
		} else {
			addVF(stamm + "ieu", "V_PP");
		}
		if (verbClass == VerbClass.IR) {
			addVF(stamm + "ida", "V_PP");
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
