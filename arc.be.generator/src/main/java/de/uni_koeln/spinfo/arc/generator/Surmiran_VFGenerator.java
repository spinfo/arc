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

public class Surmiran_VFGenerator {

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
				if(eagles_pos.equals("NN")){
					generateSubstantiveForms(entry);
					continue;
				}
				if(eagles_pos.equals("ADJ")){
					generateAdjectiveForms(entry);
					continue;
				}
		
				addVF(entry, eagles_pos);
			}
			addIrregularVerbs();
			addPräpositions();
			addPräp_Art();
			addNumbers();
			addPronouns();
			addArticles();
			addConjunctions();
			addAdjectives();
		}
		return vollForms;
	}
	
	private void addAdjectives() {
		//Demonstrativ
		addVF("chel", "ADJ_DEM");
		addVF("chella", "ADJ_DEM");
		addVF("chels", "ADJ_DEM");
		addVF("chellas", "ADJ_DEM");
		addVF("chest", "ADJ_DEM");
		addVF("chesta", "ADJ_DEM");
		addVF("chests", "ADJ_DEM");
		addVF("chestas", "ADJ_DEM");
		addVF("glez", "ADJ_DEM");
		//interrogativ
		addVF("cant", "ADJ_IES");
		addVF("tge", "ADJ_IES");
		addVF("qual", "ADJ_IES");
		addVF("quala", "ADJ_IES");
		addVF("quals", "ADJ_IES");
		addVF("qualas", "ADJ_IES");
		//indefinit
		addVF("enqualtgi", "ADJ_IND" );
		
		
	}

	private void addPräpositions(){
		addVF("a", "PREP");
		addVF("ad", "PREP");
		addVF("adancunter", "PREP");
		addVF("ancunter", "PREP");
		addVF("aan", "PREP");
		addVF("anfign", "PREP");
		addVF("anfignen", "PREP");
		addVF("ansemen", "PREP");
		addVF("anstagl", "PREP");
		addVF("antras", "PREP");
		addVF("anturn", "PREP");
		addVF("anvers", "PREP");
		addVF("anvezza", "PREP");
		addVF("arisguard", "PREP");
		addVF("aveir", "PREP");
		addVF("concernent", "PREP");
		addVF("conform", "PREP");
		addVF("cun", "PREP");
		addVF("cunter", "PREP");
		addVF("cuntrari", "PREP");
		addVF("da", "PREP");
		addVF("dafor", "PREP");
		addVF("dalos", "PREP");
		addVF("dantant", "PREP");
		addVF("davart", "PREP");
		addVF("davent", "PREP");		
		addVF("davos", "PREP");
		addVF("durant", "PREP");
		addVF("fign", "PREP");
		addVF("fignen", "PREP");
		addVF("malgro", "PREP");
		addVF("nunditgont", "PREP");
		addVF("nunobstant", "PREP");
		addVF("oter", "PREP");
		addVF("partenent", "PREP");
		addVF("per", "PREP");
		addVF("derposta", "PREP");
		addVF("perveia", "PREP");
		addVF("perversa", "PREP");
		addVF("resalvont", "PREP");
		addVF("sen", "PREP");
		addVF("siva", "PREP");
		addVF("sot", "PREP");
		addVF("supra", "PREP");
		addVF("sur", "PREP");
		addVF("tar", "PREP");
		addVF("tenor", "PREP");
		addVF("terma", "PREP");
		addVF("tranter", "PREP");
		addVF("ultra", "PREP");
		addVF("ve", "PREP");
		addVF("ved", "PREP");

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
		addVF("ad", "CONJ_C");
		addVF("ed", "CONJ_C");
		addVF("anfign", "CONJ_S");
		addVF("anfignen", "CONJ_S");
		addVF("apagna", "CONJ_S");
		addVF("bod", "CONJ_S");
		addVF("co", "CONJ_C");
		addVF("scu", "CONJ_S");
		addVF("cu", "CONJ_S");
		addVF("cumbagn", "CONJ_C");
		addVF("cun", "CONJ_S");
		addVF("cuntot", "CONJ_C");
		
		addVF("cura", "CONJ_S");
		addVF("curtgi", "CONJ_S");
		addVF("delunga", "CONJ_C");
		addVF("damai", "CONJ_C");
		addVF("dantant", "CONJ_S");
		
		addVF("durant", "CONJ_S");
		addVF("e", "CONJ_C");
		addVF("essend", "CONJ_S");
		addVF("tgi", "CONJ_S");
		addVF("fign", "CONJ_S");
		addVF("fignen", "CONJ_S");
		addVF("glo", "CONJ_S");
		addVF("ma", "CONJ_S");
		addVF("mabagn", "CONJ_C");
		
		addVF("minus", "CONJ_S");
		addVF("ni", "CONJ_C");
		addVF("nun", "CONJ_S");
		addVF("oter", "CONJ_S");
		addVF("per", "CONJ_C");
		addVF("perchegl", "CONJ_S");
		addVF("pertge", "CONJ_S");
		addVF("preness", "CONJ_S");
		addVF("quant", "CONJ_S");
		
		addVF("savens", "CONJ_S");
		addVF("schibagn", "CONJ_C");
		addVF("schidei", "CONJ_S");
		addVF("schigea", "CONJ_S");
		addVF("schigliunsch", "CONJ_C");
		addVF("schinavant", "CONJ_S");
		addVF("senachegl", "CONJ_S");
		addVF("siva", "CONJ_S");
		addVF("supponia", "CONJ_S");
		
		addVF("tant", "CONJ_S");
		addVF("u", "CONJ_C");
		addVF("avant", "CONJ_S");
		addVF("cunchegl", "CONJ_S");
		addVF("geda", "CONJ_S");
		addVF("na", "CONJ_S");
		addVF("uscheia", "CONJ_S");
	}

	private void addPronouns() {
		//Possesivpronomen
		addVF("mies", "PRON_POS");
		addVF("mia", "PRON_POS");
		addVF("ties", "PRON_POS");
		addVF("tia", "PRON_POS");
		addVF("sies", "PRON_POS");
		addVF("sia", "PRON_POS");
		addVF("noss", "PRON_POS");
		addVF("nossa", "PRON_POS");
		addVF("nossas", "PRON_POS");
		addVF("voss", "PRON_POS");
		addVF("vossa", "PRON_POS");
		addVF("vossas", "PRON_POS");
		addVF("lour", "PRON_POS");
		//Peronalpronomen
		addVF("ia", "PRON_PER");
		addVF("te", "PRON_PER");
		addVF("el", "PRON_PER");
		addVF("ella", "PRON_PER");
		addVF("nous", "PRON_PER");
		addVF("vous", "PRON_PER");
		addVF("els", "PRON_PER");
		addVF("ellas", "PRON_PER");
		addVF("me", "PRON_PER");
		addVF("tè", "PRON_PER");
		addVF("nusoters", "PRON_PER");
		addVF("nusotras", "PRON_PER");
		addVF("vusoters", "PRON_PER");
		addVF("vusotras", "PRON_PER");
		addVF("mi", "PRON_PER");
		addVF("m'", "PRON_PER");
		addVF("am", "PRON_PER");
		addVF("at", "PRON_PER");
		addVF("igl", "PRON_PER");
		addVF("la", "PRON_PER");
		addVF("ans", "PRON_PER");
		addVF("az", "PRON_PER");
		addVF("igls", "PRON_PER");
		addVF("las", "PRON_PER");
		addVF("mez", "PRON_PER");
		addVF("tez", "PRON_PER");
		addVF("sez", "PRON_PER");
		addVF("sezza", "PRON_PER");
		addVF("nusezs", "PRON_PER");
		addVF("vusezs", "PRON_PER");
		addVF("nusezzas", "PRON_PER");
		addVF("vusezzas", "PRON_PER");
		addVF("sezs", "PRON_PER");
		addVF("sezzas", "PRON_PER");
		addVF("mamez", "PRON_PER");
		addVF("tatez", "PRON_PER");
		addVF("sasez", "PRON_PER");
		addVF("sasezza", "PRON_PER");
		addVF("sasezs", "PRON_PER");
		addVF("sasezzas", "PRON_PER");
		//Demonstrativpronomen
		addVF("chel", "PRON_DEM");
		addVF("chels", "PRON_DEM");
		addVF("chella", "PRON_DEM");
		addVF("chellas", "PRON_DEM");
		addVF("chegl", "PRON_DEM");
		addVF("tschel", "PRON_DEM");
		addVF("tschels", "PRON_DEM");
		addVF("tschella", "PRON_DEM");
		addVF("tschellas", "PRON_DEM");
		addVF("tals", "PRON_DEM");
		addVF("talas", "PRON_DEM");
		addVF("lez", "PRON_DEM");
		addVF("lezza", "PRON_DEM");
		addVF("ezs", "PRON_DEM");
		addVF("ezzas", "PRON_DEM");
		addVF("glez", "PRON_DEM");
		addVF("chesta", "PRON_DEM");
		addVF("chest", "PRON_DEM");
		
		//Relativpronomen
		addVF("tgi", "PRON_REL");
		addVF("qual", "PRON_REL");
		addVF("quala", "PRON_REL");
		//Indefinitpronomen
		addVF("egn", "PRON_IND");
		addVF("egna", "PRON_IND");
		addVF("nign", "PRON_IND");
		addVF("ensatgi", "PRON_IND");
		addVF("egn", "PRON_IND");
		addVF("egn", "PRON_IND");
		addVF("ensatge", "PRON_IND");
		addVF("enqualtgign", "PRON_IND");
		addVF("enqualtgigna", "PRON_IND");
		//Interrogativpronomen
		addVF("tgi", "PRON_IES");
		addVF("tge", "PRON_IES");
		addVF("qual", "PRON_IES");
		addVF("quala", "PRON_IES");
	}

	private void addNumbers() {
		//Kardinalzahlen
		addVF("egn", "C_NUM");
		addVF("egna", "C_NUM");
		addVF("dus", "C_NUM");
		addVF("treis", "C_NUM");
		addVF("catter", "C_NUM");
		addVF("tschintg", "C_NUM");
		addVF("seis", "C_NUM");
		addVF("set", "C_NUM");
		addVF("otg", "C_NUM");
		addVF("nov", "C_NUM");
		addVF("diesch", "C_NUM");
		addVF("endesch", "C_NUM");
		addVF("dodesch", "C_NUM");
		addVF("tschient", "C_NUM");
		addVF("tschent", "C_NUM");
		addVF("mella", "C_NUM");
		addVF("null", "C_NUM");
		addVF("nolla", "C_NUM");
		addVF("milliung", "C_NUM");
		//Ordinalzahlen
		addVF("amprem", "ADJ_NUM");
		addVF("amprema", "ADJ_NUM");
		addVF("sagond", "ADJ_NUM");
		addVF("sagonda", "ADJ_NUM");
		addVF("terz", "ADJ_NUM");
		addVF("cart", "ADJ_NUM");
	}

	private void addPräp_Art() {
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

	private void generateAdjectiveForms(String entry) {
		addVF(entry, "ADJ");
		//maskulin plural
		addVF(entry+"s", "ADJ");
		if(isIrregularAdjective(entry)){
			return;
		}
		//feminim singular
		addVF(entry+"a", "ADJ");
	
		//feminim plural
		addVF(entry+"as", "ADJ");
		
		//Adverbien
		addVF(entry+"maintg", "ADV");
		addVF(entry+"amaintg", "ADV");
	}

		private boolean isIrregularAdjective(String entry) {
		if(entry.equals("pitschen")||entry.equals("gioven")){
			String stamm = entry.substring(0,entry.length()-2);
			addVF(stamm+"na", "ADJ");
			addVF(stamm+"nas", "ADJ");
			return true;
		}
		if(entry.equals("vigl") || entry.equals("stigl")){
			addVF(entry+"ia", "ADJ");
			addVF(entry+"ias", "ADJ");
			return true;
		}
		if(entry.equals("lartg")){
			addVF("largia", "ADJ");
			addVF("largias", "ADJ");
			return true;
		}
		if(entry.equals("cader")||entry.equals("sober")||entry.equals("legher")){
			String stamm = entry.substring(0,3);
			addVF(stamm+"ra", "ADJ");
			addVF(stamm+"ras", "ADJ");
			return true;
		}
		if(entry.equals("tschoff")||entry.equals("chiet")){
			addVF(entry+entry.charAt(entry.length()-1)+"a", "ADJ");
			addVF(entry+entry.charAt(entry.length()-1)+"as", "ADJ");
		}
		return false;
	}

	private void generateSubstantiveForms(String entry) {
		addVF("entry", "NN");
		char lastChar = entry.charAt(entry.length()-1);
		//maskulin plural
		if(lastChar!='s') addVF(entry+"s", "NN");
		
		if(lastChar!='a'){
			//feminim singular
			addVF(entry+"a", "NN");
			//feminim plural
			addVF(entry+"as", "NN");
			//Abschätziges Substantiv
			addVF(entry+"amainta", "NN");
		}	
		else{
			addVF(entry+ "mainta", "NN");
		}
		//unregelmäßige
		addVF(entry+"èla", "NN");
		addVF(entry+"ella", "NN");
		addVF(entry+"era", "NN");
		addVF(entry+"eglia", "NN");
		addVF(entry+"iglia", "NN");
		addVF(entry+"aglia", "NN");
		addVF(entry+"eira", "NN");
		addVF(entry+"deira", "NN");
		addVF(entry+"ereia", "NN");
		addVF(entry+"areia", "NN");
		addVF(entry+"enza", "NN");
		addVF(entry+"ada", "NN");
		
		//Vergrößerungsformen
		if(entry.charAt(entry.length()-1) !='u'){
			addVF(entry+"ung", "NN");
		}
		else{
			addVF(entry+"ng", "NN");
		}
		if(entry.charAt(entry.length()-1)!='a'){
			addVF(entry+"atsch", "NN");
		}
		else{
			addVF(entry+"tsch", "NN");
		}
	}

	private void generateVerbForms(String entry) {
		
		//Infinitiv
		addVF(entry, "V_GVRB");	
		VerbClass verbClass;
		String stamm = entry.substring(0, entry.length()-2);	
		String endung = entry.substring(entry.length()-2);
		
		if(endung.equals("er")){
			if(stamm.charAt(stamm.length()-1)== 'i'){
				verbClass = VerbClass.IER;
				stamm = stamm.substring(0, stamm.length()-1);
			}
			else{
				verbClass = VerbClass.ER;
			}
		}
		else if(endung.equals("ir")){
			verbClass = VerbClass.IR;
			stamm = entry.substring(0, entry.length()-3);
		}
		else if(endung.equals("ar")){
			verbClass = VerbClass.AR;
		}
		else{
			System.out.println("unbekanntes Verb: " + entry);
			return;
		}
		//Partizip 
		addPartizip(stamm, verbClass);
		//Gerundium
		addGerundium(stamm, "V_GVRB");
		//Imperativ
		addImperativ(stamm, verbClass, "V_GVRB");
		//Indikativ Präsens
		addIndikativPräsens(stamm, verbClass, "V_GVRB");
		//Indikativ Imperfekt
		addIndikativImperfekt(stamm, verbClass, "V_GVRB");
		//Konjunktiv Präsens
		addKonjunktivPräsens(stamm, "V_GVRB");
		//Konjunktiv Imperfekt
		addKonjunktivImperfekt(stamm, verbClass, "V_GVRB");
		//Futur I
		addFuturI(stamm, verbClass, "V_GVRB");

		
	}

		private void addPartizip(String stamm, VerbClass verbClass) {
		if(verbClass == VerbClass.AR){
			addVF(stamm+"o", "V_PP");
			addVF(stamm+"ada", "V_PP");
		}
		if(verbClass == VerbClass.EIR || verbClass == VerbClass.IER || verbClass == VerbClass.ER){
			addVF(stamm+"ia", "V_PP");
			addVF(stamm+"eida", "V_PP");
		}
		if(verbClass == VerbClass.EIR || verbClass == VerbClass.ER){
			addVF(stamm+"ea", "V_PP");
			addVF(stamm+"eda", "V_PP");
		}
	}

		private void addIndikativImperfekt(String stamm, VerbClass verbClass, String pos) {
		if(verbClass == VerbClass.EIR || verbClass == VerbClass.IER){
			addVF(stamm+"iva", pos);
			addVF(stamm+"ivas", pos);
			addVF(stamm+"ivan", pos);
		}
		if(verbClass == VerbClass.AR){
			addVF(stamm+"ava", pos);
			addVF(stamm+"avas", pos);
			addVF(stamm+"avan", pos);
		}
		if(verbClass == VerbClass.ER || verbClass == VerbClass.EIR){
			addVF(stamm+"eva", pos);
			addVF(stamm+"evas", pos);
			addVF(stamm+"evan", pos);
		}
	}

		private void addFuturI(String stamm, VerbClass verbClass, String pos) {
		addVF(stamm+"aro", pos);
		addVF(stamm+"arossas", pos);
		addVF(stamm+"aron", pos);
		if(verbClass == VerbClass.EIR){
			addVF(stamm+"iro", pos);
			addVF(stamm+"irossas", pos);
			addVF(stamm+"iron", pos);
		}
	}

		private void addIndikativPräsens(String stamm, VerbClass verbClass, String pos) {
		addVF(stamm+"as", pos);
		addVF(stamm+"a", pos);
		addVF(stamm+"agn", pos);
		addVF(stamm+"ez", pos);
		addVF(stamm+"an", pos);
		if(verbClass == VerbClass.EIR){
			addVF(stamm+"ign", pos);
			addVF(stamm+"iz", pos);
		}
	}

		private void addKonjunktivImperfekt(String stamm, VerbClass verbClass, String pos) {
		addVF(stamm+"ess", pos);
		addVF(stamm+"essas", pos);
		addVF(stamm+"essan", pos);
		if(verbClass == VerbClass.EIR){
			addVF(stamm+"iss", pos);
			addVF(stamm+"issas", pos);
			addVF(stamm+"issan", pos);
		}
	}

		private void addKonjunktivPräsens(String stamm, String pos) {
		addVF(stamm+"a", pos);
		addVF(stamm+"as", pos);
		addVF(stamm+"an", pos);
	}

		private void addImperativ(String stamm, VerbClass verbClass, String pos) {
		addVF(stamm+"a", pos);
		addVF(stamm+"agn", pos);
		addVF(stamm+"e", pos);
		if(verbClass == VerbClass.EIR){
			addVF(stamm + "ign", pos);
			addVF(stamm + "i", pos);
		}
	}

		private void addGerundium(String stamm, String pos) {
		addVF(stamm+"ond", pos);
	}

	private void addIrregularVerbs() {
			//Infinitiv
			addVF("esser", "V_ESSER");
			//Partizip
			addVF("sto", "V_ESSER");
			addVF("stada", "V_ESSER");
			addVF("stos", "V_ESSER");
			addVF("stoss", "V_ESSER");
			//Gerundium
			addVF("essend", "V_ESSER");
			addVF("siond", "V_ESSER");
			//Imperativ
			addVF("seias", "V_ESSER");
			addVF("seians", "V_ESSER");
			//Indikativ Präsens
			addVF("sung", "V_ESSER");
			addVF("ist", "V_ESSER");
			addVF("è", "V_ESSER");
			addVF("ischan", "V_ESSER");
			addVF("ischas", "V_ESSER");
			addVF("èn", "V_ESSER");
			//Indikativ Imperfekt
			addVF("era", "V_ESSER");
			addVF("eras", "V_ESSER");
			addVF("eran", "V_ESSER");
			//Konjunktiv Präsens
			addVF("seia", "V_ESSER");
			addVF("seias", "V_ESSER");
			addVF("seian", "V_ESSER");
			//Konjunktiv Imperfekt
			addVF("fiss", "V_ESSER");
			addVF("fissas", "V_ESSER");
			addVF("fissan", "V_ESSER");		
			//Futur I
			addVF("saro", "V_ESSER");
			addVF("sarossas", "V_ESSER");
			addVF("saron", "V_ESSER");
			
			//Infinitiv
			addVF("aveir", "V_AVAIR");
			//Partizip
			addVF("gia", "V_AVAIR");
			//Gerundium
			addVF("avend", "V_AVAIR");
			//Imperativ
			addVF("vegias", "V_AVAIR");
			addVF("vegians", "V_AVAIR");
			//Indikativ Präsens
			addVF("va", "V_AVAIR");
			addVF("ast", "V_AVAIR");
			addVF("ò", "V_AVAIR");
			addVF("vagn", "V_AVAIR");
			addVF("vez", "V_AVAIR");
			addVF("on", "V_AVAIR");
			//Indikativ Imperfekt
			addVF("veva", "V_AVAIR");
			addVF("vevas", "V_AVAIR");
			addVF("vevan", "V_AVAIR");
			//Konjunktiv Präsens
			addVF("vegia", "V_AVAIR");
			addVF("vegias", "V_AVAIR");
			addVF("vegian", "V_AVAIR");
			//Konjunktiv Imperfekt
			addVF("vess", "V_AVAIR");
			addVF("vessas", "V_AVAIR");
			addVF("vessan", "V_AVAIR");
			
			//Futur I
			addVF("varo", "V_AVAIR");
			addVF("varossas", "V_AVAIR");
			addVF("varon", "V_AVAIR");

			//Infinitiv
			addVF("dar", "V_GVRB");
			//Partizip
			addPartizip("d", VerbClass.AR);
			//Gerundium
			addGerundium("d", "V_GVRB");
			//Imperativ
			addVF("dò", "V_GVRB");
			addVF("dagn", "V_GVRB");
			addVF("de", "V_GVRB");
			//Indikativ Präsens
			addVF("dung", "V_GVRB");
			addVF("dast", "V_GVRB");
			addVF("dat", "V_GVRB");
			addVF("dagn", "V_GVRB");
			addVF("dez", "V_GVRB");
			addVF("dattan", "V_GVRB");
			//Indikativ Imperfekt
			addIndikativImperfekt("d", VerbClass.AR, "V_GVRB");
			//Konjunktiv Präsens
			addVF("detta", "V_GVRB");
			addVF("dettas", "V_GVRB");
			addVF("dettan", "V_GVRB");
			//Konjunktiv Imperfekt
			addKonjunktivImperfekt("d", VerbClass.AR, "V_GVRB");
			//Futur I
			addFuturI("d", VerbClass.AR, "V_GVRB");

			//Infinitiv
			addVF("deir", "V_GVRB");
			//Partizip
			addVF("detg", "V_PP");
			addVF("da", "V_PP");
			//Gerundium
			addVF("schond", "V_GVRB");
			//Imperativ
			addVF("dei", "V_GVRB");
			addVF("schagn", "V_GVRB");
			addVF("sche", "V_GVRB");
			//Indikativ Präsens
			addVF("dei", "V_GVRB");
			addVF("deist", "V_GVRB");
			addVF("schagn", "V_GVRB");
			addVF("schez", "V_GVRB");
			addVF("deian", "V_GVRB");
			//Indikativ Imperfekt
			addIndikativImperfekt("sch", VerbClass.ER, "V_GVRB");
			//Konjunktiv Präsens
			addKonjunktivPräsens("schei", "V_GVRB");
			//Konjunktiv Imperfekt
			addKonjunktivImperfekt("sch", VerbClass.AR, "V_GVRB");
			//Futur I
			addFuturI("sch", VerbClass.AR, "V_GVRB");

			//Infinitiv
			addVF("dueir", "V_GVRB");
			//Partizip
			addPartizip("du", VerbClass.IER);
			//Gerundium
			addGerundium("sch", "V_GVRB");
			//Imperativ
			addImperativ("du", VerbClass.EIR, "V_GVRB");
			//Indikativ Präsens
			addVF("duagn", "V_GVRB");
			addVF("duez", "V_GVRB");
			//Indikativ Imperfekt
			addIndikativImperfekt("du", VerbClass.ER, "V_GVRB");
			//Konjunktiv Präsens
			addKonjunktivPräsens("schei", "V_GVRB");
			//Konjunktiv Imperfekt
			addKonjunktivImperfekt("sch", VerbClass.AR, "V_GVRB");
			//Futur I
			addFuturI("du", VerbClass.AR, "V_GVRB");

			//Infinitiv
			addVF("eir", "V_GVRB");
			//Partizip
			addPartizip("", VerbClass.IER);
			//Gerundium
			addGerundium("gi", "V_GVRB");
			//Imperativ
			addVF("vo", "V_GVRB");
			addVF("giagn", "V_GVRB");
			addVF("ge", "V_GVRB");
			//Indikativ Präsens
			addVF("vign", "V_GVRB");
			addVF("vast", "V_GVRB");
			addVF("vo", "V_GVRB");
			addVF("giagn", "V_GVRB");
			addVF("gez", "V_GVRB");
			addVF("von", "V_GVRB");
			//Indikativ Imperfekt
			addIndikativImperfekt("g", VerbClass.ER, "V_GVRB");
			//Konjunktiv Präsens
			addKonjunktivPräsens("gei", "V_GVRB");
			//Konjunktiv Imperfekt
			addKonjunktivImperfekt("g", VerbClass.ER, "V_GVRB");
			//Futur I
			addFuturI("gi", VerbClass.ER, "V_GVRB");

			//Infinitiv
			addVF("far", "V_GVRB");
			//Partizip
			addVF("fatg", "V_PP");
			addVF("fa", "V_PP");
			//Gerundium
			addGerundium("fasch", "V_GVRB");
			//Imperativ
			addVF("fò", "V_GVRB");
			addVF("faschagn", "V_GVRB");
			addVF("fasche", "V_GVRB");
			//Indikativ Präsens
			addVF("fatsch", "V_GVRB");
			addVF("fast", "V_GVRB");
			addVF("fò", "V_GVRB");
			addVF("faschagn", "V_GVRB");
			addVF("faschez", "V_GVRB");
			addVF("fon", "V_GVRB");
			//Indikativ Imperfekt
			addIndikativImperfekt("fasch", VerbClass.AR, "V_GVRB");
			//Konjunktiv Präsens
			addKonjunktivPräsens("fetsch", "V_GVRB");
			//Konjunktiv Imperfekt
			addKonjunktivImperfekt("fasch", VerbClass.AR, "V_GVRB");
			//Futur I
			addFuturI("fasch", VerbClass.AR, "V_GVRB");

			//Infinitiv
			addVF("leir", "V_MOD");
			addVF("vuleir", "V_MOD");
			//Partizip
			addPartizip("l", VerbClass.ER);
			addPartizip("vul", VerbClass.ER);
			//Gerundium
			addVF("vulend", "V_MOD");
			//Imperativ
			addVF("viglias", "V_MOD");
			//Indikativ Präsens
			addVF("vi", "V_MOD");
			addVF("vot", "V_MOD");
			addVF("lagn", "V_MOD");
			addVF("lez", "V_MOD");
			addVF("vottan", "V_MOD");
			//Indikativ Imperfekt
			addIndikativImperfekt("l", VerbClass.ER, "V_MOD");
			//Konjunktiv Präsens
			addKonjunktivPräsens("vigli", "V_MOD");
			//Konjunktiv Imperfekt
			addKonjunktivImperfekt("l", VerbClass.ER, "V_MOD");

			//Infinitiv
			addVF("neir", "V_GVRB");
			//Partizp
			addPartizip("n", VerbClass.IER);
			//Gerundium
			addGerundium("gn", "V_GVRB");
			//Imperativ
			addVF("vea", "V_GVRB");
			addVF("nign", "V_GVRB");
			addVF("ni", "V_GVRB");
			//Indikativ Präsens
			addVF("vign", "V_GVRB");
			addVF("vignst", "V_GVRB");
			addVF("nign", "V_GVRB");
			addVF("niz", "V_GVRB");
			addVF("vignan", "V_GVRB");
			//Indikativ Imperfekt
			addIndikativImperfekt("n", VerbClass.IER, "V_GVRB");
			//Konjunktiv Präsens
			addKonjunktivPräsens("vign", "V_GVRB");
			//Konjunktiv Imperfekt
			addVF("niss", "V_GVRB");
			addVF("nissan", "V_GVRB");
			addVF("detg", "V_GVRB");
			//Futur
			addVF("niro", "V_GVRB");
			addVF("nirossas", "V_GVRB");
			addVF("niron", "V_GVRB");

			//Infinitiv
			addVF("pudeir", "V_MOD");
			//Partizip
			addPartizip("pud", VerbClass.IER);
			//Gerundium
			addGerundium("pud", "V_MOD");
			//Imperativ
			//Indikativ Präsent 
			addVF("poss", "V_MOD");
			addVF("post", "V_MOD");
			addVF("pò", "V_MOD");
			addVF("pudagn", "V_MOD");
			addVF("pudez", "V_MOD");
			addVF("pon", "V_MOD");
			//Indikativ Imperfekt
			addIndikativImperfekt("pud", VerbClass.AR, "V_MOD");
			//Konjunktiv Präsens
			addKonjunktivPräsens("poss", "V_MOD");
			//Konjunktiv Imperfekt
			addKonjunktivImperfekt("pud", VerbClass.AR, "V_MOD");
			//Futur
			addFuturI("pud", VerbClass.AR, "V_MOD");

			//Infinitiv
			addVF("saveir", "V_GVRB");
			//Partizip
			addPartizip("sav", VerbClass.IER);
			//Gerundium
			addGerundium("sav", "V_GVRB");
			//Imperativ
			addVF("saptgas", "V_GVRB");
			addVF("saptgans", "V_GVRB");
			//Indikativ Präsens
			addVF("sa", "V_GVRB");
			addVF("sast", "V_GVRB");
			addVF("so", "V_GVRB");
			addVF("savagn", "V_GVRB");
			addVF("savez", "V_GVRB");
			addVF("son", "V_GVRB");
			//Indikativ Imperfekt
			addIndikativImperfekt("sav", VerbClass.AR, "V_GVRB");
			//Konjunktiv Präsens
			addKonjunktivPräsens("saptg", "V_GVRB");
			//Konjunktiv Imperfekt
			addKonjunktivImperfekt("sav", VerbClass.AR, "V_GVRB");
			//Futur
			addFuturI("sav", VerbClass.AR, "V_GVRB");

			//Infinitiv
			addVF("scher", "V_GVRB");
			//Partizip
			addVF("schaschea", "V_GVRB");
			addVF("schascheida", "V_GVRB");
			//Gerundium
			addGerundium("schasch", "V_GVRB");
			//Imperativ
			addVF("schea", "V_GVRB");
			addVF("schaschagn", "V_GVRB");
			addVF("schasche", "V_GVRB");
			//Indikativ Präsens
			addVF("schei", "V_GVRB");
			addVF("scheas", "V_GVRB");
			addVF("schea", "V_GVRB");
			addVF("schaschagn", "V_GVRB");
			addVF("schaschez", "V_GVRB");
			addVF("schean", "V_GVRB");
			//Indikativ Imperfekt
			addIndikativImperfekt("schasch", VerbClass.AR, "V_GVRB");
			//Konjunktiv Präsens
			addKonjunktivPräsens("sche", "V_GVRB");
			//Konjunktiv Imperfekt
			addKonjunktivImperfekt("schasch", VerbClass.AR, "V_GVRB");
			//Futur
			addFuturI("schasch", VerbClass.AR, "V_GVRB");

			//Infinitiv
			addVF("star", "V_GVRB");
			//Partizip
			addPartizip("st", VerbClass.AR);
			//gerundium
			addGerundium("st", "V_GVRB");
			//Imperativ
			addVF("stò", "V_GVRB");
			addVF("stagn", "V_GVRB");
			addVF("ste", "V_GVRB");
			//Indikativ Präsens
			addVF("stung", "V_GVRB");
			addVF("stast", "V_GVRB");
			addVF("stat", "V_GVRB");
			addVF("stagn", "V_GVRB");
			addVF("stez", "V_GVRB");
			addVF("stattan", "V_GVRB");
			//Indikativ Imperfekt
			addIndikativImperfekt("st", VerbClass.AR, "V_GVRB");
			//Konjunktiv Präsens
			addKonjunktivPräsens("stett", "V_GVRB");
			//Konjunktiv Imperfekt
			addKonjunktivImperfekt("st", VerbClass.AR, "V_GVRB");
			//Futur
			addFuturI("st", VerbClass.AR, "V_GVRB");

			//Infinitiv
			addVF("stueir", "V_MOD");
			//Partzip
			addPartizip("stu", VerbClass.IER);
			//Gerundium
			addGerundium("stu", "V_MOD");
			//Imperativ
			//Indikativ Präsens
			addVF("stò", "V_MOD");
			addVF("stost", "V_MOD");
			addVF("stuagn", "V_MOD");
			addVF("stuez", "V_MOD");
			addVF("ston", "V_MOD");
			//Indikativ Imperfekt
			addIndikativImperfekt("stu", VerbClass.AR, "V_MOD");
			//Konjunktiv Präsens
			addKonjunktivPräsens("stoptg", "V_MOD");
			//Konjunktiv Imperfekt
			addKonjunktivImperfekt("stu", VerbClass.AR, "V_MOD");
			//Futur
			addFuturI("stu", VerbClass.AR, "V_MOD");
		
			//Infinitiv
			addVF("tascheir", "V_GVRB");
			//Partizip
			addPartizip("tasch", VerbClass.IER);
			//Gerundium
			addGerundium("tasch", "V_GVRB");
			//Imperativ
			addVF("tai", "V_GVRB");
			addVF("taschagn", "V_GVRB");
			addVF("tasche", "V_GVRB");
			//Indikativ Präsens
			addVF("tai", "V_GVRB");
			addVF("taist", "V_GVRB");
			addVF("taschagn", "V_GVRB");
			addVF("taschez", "V_GVRB");
			addVF("taian", "V_GVRB");
			//Indikativ Imperfekt
			addIndikativImperfekt("tasch", VerbClass.AR, "V_GVRB");
			//Konjunktiv Präsens
			addKonjunktivPräsens("tai", "V_GVRB");
			//Konjnktiv Imperfekt
			addKonjunktivImperfekt("tasch", VerbClass.AR, "V_GVRB");
			//Futur
			addFuturI("tasch", VerbClass.AR, "V_GVRB");

			//Infinitiv
			addVF("trer", "V_GVRB");
			//Partizip
			addVF("tratg", "V_GVRB");
			addVF("tratga", "V_GVRB");
			//Gerundium
			addGerundium("tir", "V_GVRB");
			//Imperativ
			addVF("teira", "V_GVRB");
			addVF("tiragn", "V_GVRB");
			addVF("tire", "V_GVRB");
			//Indikativ Präsens
			addVF("teir", "V_GVRB");
			addVF("teiras", "V_GVRB");
			addVF("teira", "V_GVRB");
			addVF("tirez", "V_GVRB");
			addVF("tiragn", "V_GVRB");
			addVF("teiran", "V_GVRB");
			//Indikatv Imperfekt
			addIndikativImperfekt("tasch", VerbClass.AR, "V_GVRB");
			//Konjunktiv Präsens
			addKonjunktivPräsens("teir", "V_GVRB");
			//Konjunktiv Imperfekt
			addKonjunktivImperfekt("tasch", VerbClass.AR, "V_GVRB");
			//Futur
			addFuturI("tasch", VerbClass.AR, "V_GVRB");

			//Infinitiv
			addVF("veir", "V_GVRB");
			addVF("vaseir", "V_GVRB");
			//Partizip
			addPartizip("v", VerbClass.IER);
			addPartizip("vas", VerbClass.IER);
			//Gerundium
			addGerundium("vas", "V_GVRB");
			//Imperativ
			//Indikativ Präsens
			addVF("vei", "V_GVRB");
			addVF("ves", "V_GVRB");
			addVF("veist", "V_GVRB");
			addVF("vesas", "V_GVRB");
			addVF("vei", "V_GVRB");
			addVF("vesa", "V_GVRB");
			addVF("vasagn", "V_GVRB");
			addVF("vasez", "V_GVRB");
			addVF("veian", "V_GVRB");
			addVF("vesan", "V_GVRB");		
			//Indikativ Imperfekt
			addIndikativImperfekt("tir", VerbClass.AR, "V_GVRB");
			//Konjunktiv Präsens
			addKonjunktivPräsens("teir", "V_GVRB");
			//Konjunktiv Imperfekt
			addKonjunktivImperfekt("tir", VerbClass.AR, "V_GVRB");
			//Futur
			addFuturI("vas", VerbClass.AR, "V_GVRB");

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
