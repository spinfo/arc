package de.uni_koeln.spinfo.arc.generator;

import java.net.UnknownHostException;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class Puter_VFGenerator {
	
	Map<String, TreeSet<String>> vollForms = new TreeMap<String, TreeSet<String>>();
	int count;
		
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
//					addPrepositions();
					addPrep_Art();
					addPronouns();
//					addConjunctions();
					addArticles();
//					addAdjectives();
					
				}
			}
			return vollForms;
		}
		
	private void generateSubstantiveForms(String entry) {
			
			// maskulin bzw. Lexikoneintrag singular
			addVF(entry, "NN");
			
			char lastChar = entry.charAt(entry.length()-1);
			String endung = "";
			int length = entry.length();
			if (length > 2) {
				endung = entry.substring(entry.length()-2);
			}
			
			// maskulin plural
			// unregelmäßige Formen nach Endung
			if (endung.compareTo("ur")==0) {
				addVF(entry.substring(0, entry.length()-2)+"uors", "NN");
			} else {
				switch (lastChar) {
//				case 'à':  
//					addVF(entry.substring(0, entry.length()-1)+"ats", "NN");
//					break;
				case 'è': 
					addVF(entry.substring(0, entry.length()-1)+"els", "NN");
					break;
//				case 'i': 
//					addVF(entry.substring(0, entry.length()-1)+"its", "NN");
//					break;
//				case 'ü': 
//					addVF(entry.substring(0, entry.length()-1)+"üts", "NN");
//					break;
				default:
					// regelmäßige Form maskulin plural
					if (lastChar!='s' || endung.compareTo("ss")!=0){
						addVF(entry+"s", "NN");
					}
				}
			}
			
			if(lastChar!='a') {
				// augmentativ m
				addVF(entry+"un", "NN");
				// diminutiv m 
				addVF(entry+"in", "NN");
				addVF(entry+"et", "NN");
				// pegiorativ m
				addVF(entry+"atsch", "NN");
			}
//			
//			if (!endsWithVocal(entry)) {
//				// weitere autmentative Suffixe
//				addVF(entry+"ada", "NN");
//				addVF(entry+"agl", "NN");
//				addVF(entry+"ezza", "NN");
//				addVF(entry+"ezzas", "NN");
//				addVF(entry+"ischem", "NN");
//				// weitere diminutive Suffixe
//				addVF(entry+"ical", "NN");
//				addVF(entry+"ot", "NN");
//				addVF(entry+"ottel", "NN");
//				// weitere abwertende Suffixe
//				addVF(entry+"aster", "NN");
//				addVF(entry+"astra", "NN");
//			}
//			// abwertendes Suffix
//			addVF(entry+"mainta", "NN");
			
			
			
			// feminin
			// Unregelmäßige Formen nach Endung
			String femEntry = "";
//			if (length < 6) {
//				endung = entry.substring(entry.length()-6);
//
//				switch (endung) {
//					case "ainder":
//						femEntry = entry.substring(0, entry.length()-6)+"aindra";
//						break;
//					case "aister":
//						femEntry = entry.substring(0, entry.length()-6)+"aistra";
//						break;
//				}
//			}
//			
//			if (length > 5 && femEntry.compareTo("")==0) {
//				endung = entry.substring(entry.length()-5);
//				
//				switch (endung) {
//				case "iccal":
//					femEntry = entry.substring(0, entry.length()-5)+"icla";
//					break;
//				case "aivel":
//					femEntry = entry.substring(0, entry.length()-5)+"aivla";
//					break;
//				case "immel":
//					femEntry = entry.substring(0, entry.length()-5)+"imla";
//					break;
//				case "ottel":
//					femEntry = entry.substring(0, entry.length()-5)+"otla";
//					break;
//				case "üttel":
//					femEntry = entry.substring(0, entry.length()-5)+"ütla";
//					break;
//				case "ossem":
//					femEntry = entry.substring(0, entry.length()-5)+"osma";
//					break;
//				case "igher":
//					femEntry = entry.substring(0, entry.length()-5)+"igra";
//					break;
//				case "aster":
//					femEntry = entry.substring(0, entry.length()-5)+"astra";
//					break;
//				case "ecter":
//					femEntry = entry.substring(0, entry.length()-5)+"ectura";
//					break;
//				case "ember":
//					femEntry = entry.substring(0, entry.length()-5)+"embra";
//					break;
//				case "ister":
//					femEntry = entry.substring(0, entry.length()-5)+"istra";
//					break;
//				case "oster":
//					femEntry = entry.substring(0, entry.length()-5)+"ostra";
//					break;
//				case "utter":
//					femEntry = entry.substring(0, entry.length()-5)+"utra";
//					break;
//				case "izzer":
//					femEntry = entry.substring(0, entry.length()-5)+"izra";
//					break;
//				case "ander":
//					femEntry = entry.substring(0, entry.length()-5)+"andra";
//					break;
//				}
//			}
//			
//			if (length > 4 && femEntry.compareTo("")==0) {
//				endung = entry.substring(entry.length()-4);
//				
//				switch (endung) {
//				case "abel":
//					femEntry = entry.substring(0, entry.length()-4)+"abla";
//					break;
//				case "öbel":
//					femEntry = entry.substring(0, entry.length()-4)+"öbla";
//					break;
//				case "avel":
//					femEntry = entry.substring(0, entry.length()-4)+"avla";
//					break;
//				case "ider":
//					femEntry = entry.substring(0, entry.length()-4)+"idra";
//					break;
//				case "iker":
//					femEntry = entry.substring(0, entry.length()-4)+"icra";
//					break;
//				case "iner":
//					femEntry = entry.substring(0, entry.length()-4)+"inra";
//					break;
//				case "ster":
//					femEntry = entry.substring(0, entry.length()-4)+"stra";
//					break;
//				case "ader":
//					femEntry = entry.substring(0, entry.length()-4)+"adra";
//					break;
//				case "uven":
//					femEntry = entry.substring(0, entry.length()-4)+"uvra";
//					break;
//				case "eder":
//					femEntry = entry.substring(0, entry.length()-4)+"edra";
//					break;
//				}
//			}
//			
//			if (length > 3 && femEntry.compareTo("")==0) {
//				endung = entry.substring(entry.length()-3);
//				
//				switch (endung) {
//				case "agl":
//					femEntry = entry.substring(0, entry.length()-3)+"alla";
//					break;
//				}
//			}
//			
//			if (length > 2 && femEntry.compareTo("")==0) {
//				endung = entry.substring(entry.length()-2);
//				
//				switch (endung) {
//				case "gl":
//					femEntry = entry.substring(0, entry.length()-2)+"glia";
//					break;
//				case "öl":
//					femEntry = entry.substring(0, entry.length()-2)+"oula";
//					break;
//				case "an":
//					femEntry = entry.substring(0, entry.length()-2)+"ogna";
//					break;
//				case "zi":
//					femEntry = entry.substring(0, entry.length()-2)+"cessa";
//					break;
//				}
//			}
//			
			if (femEntry.compareTo("")==0) {
				switch(lastChar) {
				case 'a': 
					String stamm = entry.substring(0, entry.length()-1);
					// augmentativ f
					addVF(stamm+"una", "NN");
					// diminutiv f
					addVF(stamm+"ina", "NN");
					addVF(stamm+"etta", "NN");
					// pegiorativ f
					addVF(entry+"tscha", "NN");
					break;
//				case 'à':
//					femEntry = entry.substring(0, entry.length()-1)+"ada";
//					break;
//				case 'è':
//					femEntry = entry.substring(0, entry.length()-1)+"ella";
//					break;
//				case 'i':
//					femEntry = entry+"da";
//					break;
//				case 'l':
//					femEntry = entry+"la";
//					break;
//				case 'p':
//					femEntry = entry+"pa";
//					break;
//				case 'r':
//					femEntry = entry+"ra";
//					break;
//				case 't':
//					femEntry = entry+"ta";
//					break;
//				case 'ü':
//					femEntry = entry+"da";
//					break;
//				// regelmäßige feminine Form
				default: 
					femEntry = entry+'a';
					// feminin plural
					addVF(entry+"as", "NN");	
				}
			}
			
			addVF(femEntry, "NN");
			addSubstantivPluralAndOtherSuffixes(femEntry);
			
		}
			
		private void addSubstantivPluralAndOtherSuffixes(String entry) {
			// feminin plural
			addVF(entry+"s", "NN");
			
			String stamm = entry.substring(0, entry.length()-1);
			
			// augmentativ f
			addVF(stamm+"una", "NN");
			// diminutiv f
			addVF(stamm+"ina", "NN");
			addVF(stamm+"etta", "NN");
			// pegiorativ f
			addVF(entry+"tscha", "NN");
		}
		
		private void generateAdjectiveForms(String entry) {
			addVF(entry, "ADJ");
			char lastChar = entry.charAt(entry.length()-1);
			if(lastChar!='s'){
				// pl
				addVF(entry+"s", "ADJ");
			}
			
			if(lastChar=='o'){
				addVF(entry.substring(0, entry.length()-1)+"eda", "ADJ");
				addVF(entry.substring(0, entry.length()-1)+"edas", "ADJ");
			}
			
			if(lastChar!='a'){
				// f sg
				addVF(entry+"a", "ADJ");
				// f pl
				addVF(entry+"as", "ADJ");
				
			
				// augmentativ
				addVF(entry+"un", "ADJ");
				addVF(entry+"una", "ADJ");
				// diminutiv m 
				addVF(entry+"in", "ADJ");
				addVF(entry+"ina", "ADJ");
				addVF(entry+"et", "ADJ");
				addVF(entry+"etta", "ADJ");
				// pegiorativ m
				addVF(entry+"atsch", "ADJ");
				addVF(entry+"atscha", "ADJ");

				
			}
			
			generateSpecialFemininForms(entry, "ADJ");
		}
		
		private void generateSpecialFemininForms(String entry, String pos) {
			String femEntry = "";
			// TODO: Diese Methode könnte auch auf Substantive angewendet werden. Einige Fälle könnten hiervon noch nicht erfasst werden.
			
			// Verdoppelung des letzten Konsonanten:
			if(!endsWithVocal(entry)) {
				femEntry = entry + entry.charAt(entry.length()-1) + 'a';
			}
			
			// Adjektive, die auf g oder gl enden
			if (entry.charAt(entry.length()-1) == 'g') {
				femEntry = entry + "ia";
			} else if (entry.substring(entry.length()-1, entry.length()).compareTo("gl") == 0) {
				femEntry = entry + "ia";
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
			String stamm = entry.substring(0, entry.length()-3);
			String endung = entry.substring(entry.length()-3);
			
			switch(endung) {
			case "bel":
				femEntry = stamm + "bla"; break;
			case "ber":
				femEntry = stamm + "bra"; break;
			case "cal":
				femEntry = stamm + "cla"; break;
			case "der":
				femEntry = stamm + "dra"; break;
			case "mel":
				femEntry = stamm + "mla"; break;
			case "ner":
				femEntry = stamm + "nra"; break;
			case "pel":
				femEntry = stamm + "pla"; break;
			case "ser":
				femEntry = stamm + "sra"; break;
			case "sen":
				femEntry = stamm + "sna"; break;
			case "sem":
				femEntry = stamm + "sma"; break;
			case "tel":
				femEntry = stamm + "tla"; break;
			case "ter":
				femEntry = stamm + "tra"; break;
			case "ven":
				femEntry = stamm + "vna"; break;
			case "ver":
				femEntry = stamm + "vra"; break;
			case "vel":
				femEntry = stamm + "vla"; break;
			case "ieu":
				femEntry = stamm + "ida"; break;
				
			}
			
			addVF(femEntry, pos);
			addVF(femEntry + "s", pos);
			
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
			addVF("al", "PREP_A");
			addVF("als", "PREP_A");
			addVF("dal", "PREP_A");
			addVF("dals", "PREP_A");
			addVF("cul", "PREP_A");
			addVF("culs", "PREP_A");
			addVF("i'l", "PREP_A");
			addVF("i'ls", "PREP_A");
			addVF("pel", "PREP_A");
			addVF("pels", "PREP_A");
			
			addVF("sül", "PREP_A");
			addVF("süls", "PREP_A");
			addVF("prols", "PREP_A");
			addVF("illa", "PREP_A");
			addVF("illas", "PREP_A");
			addVF("culla", "PREP_A");
			addVF("cullas", "PREP_A");
			addVF("pella", "PREP_A");
			addVF("pellas", "PREP_A");
			addVF("sülla", "PREP_A");
			addVF("süllas", "PREP_A");
		}
		
		private void addPronouns() {
//			//indefinite

			addVF("bainquaunts", "PRON_IND");
			addVF("bainquauntas", "PRON_IND");
			addVF("bger", "PRON_IND");
			addVF("bgera", "PRON_IND");
			addVF("bgers", "PRON_IND");
			addVF("bgeras", "PRON_IND");
			addVF("differents", "PRON_IND");
			addVF("differentas", "PRON_IND");
			addVF("divers", "PRON_IND");
			addVF("diversas", "PRON_IND");
			addVF("minchün", "PRON_IND");
			addVF("minchüna", "PRON_IND");
			addVF("scudün", "PRON_IND");
			addVF("scudüna", "PRON_IND");
			addVF("oter", "PRON_IND");
			addVF("otra", "PRON_IND");
			addVF("oters", "PRON_IND");
			addVF("otras", "PRON_IND");
			addVF("poch", "PRON_IND");
			addVF("pocha", "PRON_IND");
			addVF("pochs", "PRON_IND");
			addVF("pochas", "PRON_IND");
			addVF("püs", "PRON_IND");
			addVF("püssas", "PRON_IND");
			addVF("qualchün", "PRON_IND");
			addVF("qualchüna", "PRON_IND");
			addVF("qualchüns", "PRON_IND");
			addVF("qualchünas", "PRON_IND");
			addVF("qualchün", "PRON_IND");
			addVF("qualchüna", "PRON_IND");
			addVF("qualchüns", "PRON_IND");
			addVF("qualchünas", "PRON_IND");
			addVF("qualchedün", "PRON_IND");
			addVF("qualchedüna", "PRON_IND");
			addVF("qualchedüns", "PRON_IND");
			addVF("qualchedünas", "PRON_IND");
			addVF("qualunque", "PRON_IND");
			addVF("quaunt", "PRON_IND");
			addVF("quaunta", "PRON_IND");
			addVF("quaunts", "PRON_IND");
			addVF("quauntas", "PRON_IND");
			addVF("taunt", "PRON_IND");
			addVF("taunta", "PRON_IND");
			addVF("taunts", "PRON_IND");
			addVF("tauntas", "PRON_IND");
			addVF("tschertüns", "PRON_IND");
			addVF("tschertünas", "PRON_IND");
			addVF("tschertadüns", "PRON_IND");
			addVF("tschertadünas", "PRON_IND");
			addVF("tuot", "PRON_IND");
			addVF("tuotta", "PRON_IND");
			addVF("tuots", "PRON_IND");
			addVF("tuottas", "PRON_IND");
			addVF("üngün", "PRON_IND");
			addVF("üngüna", "PRON_IND");
			addVF("üngüns", "PRON_IND");
			addVF("üngünas", "PRON_IND");
			addVF("varsaquaunts", "PRON_IND");
			addVF("varsaquauntas", "PRON_IND");
			addVF("iminchün", "PRON_IND");
			addVF("iminchüna", "PRON_IND");

			
			//demonstativ
			addVF("quist", "PRON_DIM");
			addVF("quaist", "PRON_DIM");
			addVF("quista", "PRON_DIM");
			addVF("quists", "PRON_DIM");
			addVF("quistas", "PRON_DIM");
			addVF("que", "PRON_DIM");
			addVF("quel", "PRON_DIM");
			addVF("quella", "PRON_DIM");
			addVF("quels", "PRON_DIM");
			addVF("quellas", "PRON_DIM");
			addVF("medem", "PRON_DIM");
			addVF("medemma", "PRON_DIM");
			addVF("medems", "PRON_DIM");
			addVF("medemmas", "PRON_DIM");
			addVF("listess", "PRON_DIM");
			addVF("listessa", "PRON_DIM");
			addVF("listessas", "PRON_DIM");

			addVF("simil", "PRON_DIM");
			addVF("simla", "PRON_DIM");
			addVF("simils", "PRON_DIM");
			addVF("similas", "PRON_DIM");
			addVF("tel", "PRON_DIM");
			addVF("tela", "PRON_DIM");
			addVF("tels", "PRON_DIM");
			addVF("telas", "PRON_DIM");


			

			
			//interrogativ
			addVF("che", "PRON_IES");
			addVF("quaunt", "PRON_IES");
//			addVF("quaunts", "PRON_IES");
			addVF("quaunta", "PRON_IES");
			addVF("quauntas", "PRON_IES");
			addVF("quauntevel", "PRON_IES");
			addVF("quauntevla", "PRON_IES");
			addVF("quauntevels", "PRON_IES");
			addVF("quauntevlas", "PRON_IES");
			addVF("chenün", "PRON_IES");
			addVF("chenüna", "PRON_IES");
			addVF("chenünas", "PRON_IES");
			addVF("quêl", "PRON_IES");
			addVF("quela", "PRON_IES");
			addVF("quêls", "PRON_IES");
			addVF("quelas", "PRON_IES");
				
			
			//personal
			addVF("eau", "PRON_PER");
			addVF("am", "PRON_PER");
//			addVF("'m", "PRON_PER");
//			addVF("m'", "PRON_PER");
			addVF("el", "PRON_PER");
			addVF("al", "PRON_PER");
//			addVF("'l", "PRON_PER");
//			addVF("l'", "PRON_PER");
			addVF("ella", "PRON_PER");
			addVF("la", "PRON_PER");
			addVF("nus", "PRON_PER");
			addVF("ans", "PRON_PER");
//			addVF("'ns", "PRON_PER");
//			addVF("s'", "PRON_PER");
			addVF("vus", "PRON_PER");
			addVF("as", "PRON_PER");
//			addVF("'s", "PRON_PER");
			addVF("els", "PRON_PER");
			addVF("als", "PRON_PER");
//			addVF("'ls", "PRON_PER");
			addVF("ellas", "PRON_PER");
			addVF("las", "PRON_PER");
			addVF("tillas", "PRON_PER");
			addVF("m'ha", "PRON_PER");
			addVF("t'ha", "PRON_PER");
			addVF("til'ha", "PRON_PER");
			addVF("till'ha", "PRON_PER");
			addVF("mai", "PRON_PER");
			addVF("tai", "PRON_PER");
			
			// reflexiv
			//TODO: Reflexivpronomen? Es gibt noch kein eigenes pos PRON_REF
			addVF("am", "PRON_REF");
			addVF("at", "PRON_REF");
			addVF("as", "PRON_REF");
			addVF("ans", "PRON_REF");
			addVF("me", "PRON_REF");
			addVF("te", "PRON_REF");
			addVF("se", "PRON_REF");
			addVF("nus", "PRON_REF");
			addVF("vus", "PRON_REF");
			
			//relativ
			addVF("chi", "PRON_REL");
			addVF("cha", "PRON_REL");

			//possesiv
			addVF("mieu", "PRON_POS");
			addVF("mieus", "PRON_POS");
			addVF("mia", "PRON_POS");
			addVF("mias", "PRON_POS");
			addVF("mas", "PRON_POS");
			addVF("tieu", "PRON_POS");
			addVF("tia", "PRON_POS");
			addVF("ta", "PRON_POS");
			addVF("tieus", "PRON_POS");
			addVF("tias", "PRON_POS");
			addVF("tas", "PRON_POS");
			addVF("sieu", "PRON_POS");
			addVF("sia", "PRON_POS");
			addVF("sa", "PRON_POS");
			addVF("sieus", "PRON_POS");
			addVF("sias", "PRON_POS");
			addVF("sas", "PRON_POS");
			addVF("nos", "PRON_POS");
			addVF("nossa", "PRON_POS");
			addVF("noss", "PRON_POS");
			addVF("nossas", "PRON_POS");
			addVF("vos", "PRON_POS");
			addVF("vossa", "PRON_POS");
			addVF("voss", "PRON_POS");
			addVF("vossas", "PRON_POS");
			addVF("lur", "PRON_POS");
			addVF("Sieu", "PRON_POS");
			addVF("Sia", "PRON_POS");
			addVF("Sieus", "PRON_POS");
			addVF("Sias", "PRON_POS");
			addVF("Lur", "PRON_POS");
			
	
		}
		
		private void generateVerbForms(String entry) {
			// Infinitiv
			addVF(entry, "V_GVRB");
			VerbClass verbClass;
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
						addConjugations(stamm+"i", verbClass);
						addInversion(stamm, verbClass);
					}
					// Verben auf -glir
					else if  (stamm.endsWith("gl")) {
						addConjugations(stamm+"i", verbClass);
						addInversion(stamm, verbClass);
					}
				}
			} else if (endung.equals("er")) {
				verbClass = VerbClass.ER;
				
				// Verben auf -ger
				if (stamm.endsWith("g")) {
					addVF(stamm, "V_GVRB");
					addConjugations(stamm+"i", verbClass);
					addInversion(stamm+"i", verbClass);
				}
				
			} else {
				System.out.println("unbekanntes Verb - falsche Endung: " + endung);
				return;
			}
			
			if (stamm.endsWith("j")) {
				addVF(stamm.substring(0, stamm.length()-1)+"i", "V_GVRB");
				addInversion(stamm, verbClass);
			}
			
			addConjugations(stamm, verbClass);
			
			// Reflexivpronomen vor Verben beginnend mit einem Vokal
			// produziert massiv und unsinnig über, aber nur diejenigen zu generieren, die grammatisch sinnvoll sind, könnte unübersichtlich werden
			if (startsWithVocal(stamm)) {
				addConjugations("m'"+stamm, verbClass);
				addConjugations("t'"+stamm, verbClass);
				addConjugations("s'"+stamm, verbClass);
			}
		}
		
			
		private void addConjugations(String stamm, VerbClass verbClass) {
			addPartizip(stamm, verbClass);
			addGerundium(stamm, verbClass, "V_GVRB");
			addImperativ(stamm, verbClass, "V_GVRB");
			addPraesensAugmentativ(stamm, verbClass, "V_GVRB");
			addIndikativPraesens(stamm, verbClass, "V_GVRB");
			addIndikativImperfekt(stamm, verbClass, "V_GVRB");
			addIndikativPassoDefinieu(stamm, verbClass, "V_GVRB");
			addIndikativFuturI(stamm, verbClass, "V_GVRB");
			addIndikativFuturDubitativ(stamm, verbClass, "V_GVRB");
			addKonjunktivPraesens(stamm, verbClass, "V_GVRB");
			addKonjunktivImperfekt(stamm, verbClass, "V_GVRB");
		}
		
		private void addPartizip(String stamm, VerbClass verbClass) {
			if (verbClass == VerbClass.ER) {
				addVF(stamm + "o", "V_PP");
				addVF(stamm + "os", "V_PP");
				addVF(stamm + "eda", "V_PP");
				addVF(stamm + "edas", "V_PP");
				
				addVF(stamm + "ieu", "V_PP");
				addVF(stamm + "ieus", "V_PP");
				addVF(stamm + "ida", "V_PP");
				addVF(stamm + "idas", "V_PP");
			} else if (verbClass == VerbClass.AIR || verbClass == VerbClass.IR){
				addVF(stamm + "ieu", "V_PP");
				addVF(stamm + "ieus", "V_PP");
				addVF(stamm + "ida", "V_PP");
				addVF(stamm + "idas", "V_PP");
			}
		}
		
		private void addGerundium(String stamm, VerbClass verbClass, String pos) {
			if (verbClass == VerbClass.ER || verbClass == VerbClass.AIR) {
				addVF(stamm + "and", pos);	
			} else if (verbClass == VerbClass.IR) {
				addVF(stamm + "ind", pos);	
			}
		}
		
		private void addImperativ(String stamm, VerbClass verbClass, String pos) {
			if (verbClass == VerbClass.ER || verbClass == VerbClass.AIR) {
				addVF(stamm + "a", pos);			// 2 Ps Pl Imperativ affirmativ
				addVF(stamm + "ainsa", pos);
				addVF(stamm + "è", pos);
				
				// mit  Personalpronomen
				addVF(stamm + "a'm", pos);
				addVF(stamm + "a'l", pos);
				addVF(stamm + "'la", pos);
				addVF(stamm + "a'ns", pos);
				addVF(stamm + "a'ls", pos);
				addVF(stamm + "a'las", pos);
				
			} else {	// (verbClass == VerbClass.IR)
				addVF(stamm + "a", pos);			
				addVF(stamm + "insa", pos);
				addVF(stamm + "i", pos);	
				
				// mit  Personalpronomen
				addVF(stamm + "a'm", pos);
				addVF(stamm + "a'l", pos);
				addVF(stamm + "'la", pos);
				addVF(stamm + "a'ns", pos);
				addVF(stamm + "a'ls", pos);
				addVF(stamm + "'las", pos);
				
			} 
		}
		
		/**
		 *  VIELE Verben auf -ar bzw. -ir erhalten im indikativen und konjunktiven Präsens die Erweiterung -esch- bzw. -isch-.
		 *	Im Wörterbuch wird die 3. Ps in Klammern, z. B. -escha, angegeben.
		 *
		 *	In dieser Methode wir von ALLEN Verben auf -ar und -ir solche augmentativen Formen gebildet
		 **/
		private void addPraesensAugmentativ(String stamm,
				VerbClass verbClass, String pos) {
			if (verbClass == VerbClass.ER || verbClass == VerbClass.IR) {
				stamm = stamm + "esch";
				addIndikativPraesens(stamm, verbClass, pos);
				addKonjunktivPraesens(stamm, verbClass, pos);
			}
			
		}

		private void addIndikativPraesens(String stamm, VerbClass verbClass,
				String pos) {
			addVF(stamm, pos); 					// 1. Ps Sg
			addVF(stamm + "ast", pos);			// 2. Ps Sg
			addVF(stamm + "a", pos);			// 3. Ps Sg
			addVF(stamm + "an", pos);			// 3. Ps Pl
			if (verbClass == VerbClass.ER || verbClass == VerbClass.AIR) {
				addVF(stamm + "ains", pos);		// 1. Ps Pl
				addVF(stamm + "ais", pos);		// 2. Ps Pl
			} else {		// (verbClass == verbClass.IR)
				addVF(stamm + "ins", pos);		// 1. Ps Pl
				addVF(stamm + "is", pos);		// 2. Ps Pl
			}
		}
		
		private void addIndikativImperfekt(String stamm, VerbClass verbClass,
				String pos) {
			if (verbClass == VerbClass.ER || verbClass == VerbClass.AIR) {
				stamm = stamm + "aiv";
			} else {		// (verbClass == verbClass.IR)
				stamm = stamm + "iv";
			}
			addVF(stamm + "a", pos);		// 1. Ps Sg, 3. Ps Sg
			addVF(stamm + "ast", pos);		// 2. Ps Sg
			addVF(stamm + "ans", pos);		// 1. Ps Pl
			addVF(stamm + "as", pos);		// 2. Ps Pl
			addVF(stamm + "an", pos);		// 3. Ps Pl	
		}
		
		private void addIndikativPassoDefinieu(String stamm, VerbClass verbClass, String pos) {
			if (verbClass == VerbClass.ER || verbClass == VerbClass.AIR) {
				stamm = stamm + "e";
			} else {		// (verbClass == verbClass.IR)
				stamm = stamm + "i";
			}
			addVF(stamm + "t", pos);		// 1. Ps Sg, 3. Ps Sg
			addVF(stamm + "ttast", pos);		// 2. Ps Sg
			addVF(stamm + "ttans", pos);		// 1. Ps Pl
			addVF(stamm + "ttas", pos);		// 2. Ps Pl
			addVF(stamm + "ttan", pos);		// 3. Ps Pl	
		}
		
		private void addIndikativFuturI(String stamm, VerbClass verbClass,
				String pos) {
			if (verbClass == VerbClass.ER || verbClass == VerbClass.AIR) {
				stamm = stamm + "ar";
			} else {		// (verbClass == verbClass.IR)
				stamm = stamm + "ir";
			}
			addVF(stamm + "o", pos);		// 1. Ps Sg, 3. Ps Sg
			addVF(stamm + "ost", pos);		// 2. Ps Sg
			addVF(stamm + "ons", pos);		// 1. Ps Pl
			addVF(stamm + "os", pos);		// 2. Ps Pl
			addVF(stamm + "on", pos);		// 3. Ps Pl
		}
		
		private void addIndikativFuturDubitativ(String stamm, VerbClass verbClass, String pos) {
			if (verbClass == VerbClass.ER || verbClass == VerbClass.AIR) {
				stamm = stamm + "a";
			} else {		// (verbClass == verbClass.IR)
				stamm = stamm + "i";
			}
			addVF(stamm + "regia", pos);		// 1. Ps Sg, 3. Ps Sg
			addVF(stamm + "regiast", pos);		// 2. Ps Sg
			addVF(stamm + "regians", pos);		// 1. Ps Pl
			addVF(stamm + "regias", pos);		// 2. Ps Pl
			addVF(stamm + "regian", pos);		// 3. Ps Pl	
		}
		
		private void addKonjunktivPraesens(String stamm, VerbClass verbClass,
				String pos) {
			addVF(stamm + "a", pos);
			addVF(stamm + "ast", pos);
			addVF(stamm + "ans", pos);
			addVF(stamm + "as", pos);
			addVF(stamm + "an", pos);
		}
		
		private void addKonjunktivImperfekt(String stamm, VerbClass verbClass,
				String pos) {
			if (verbClass == VerbClass.ER || verbClass == VerbClass.AIR) {
				stamm = stamm + "ess";
			} else {		// (verbClass == verbClass.IR)
				stamm = stamm + "iss";
			}
			addVF(stamm, pos);				// 1. Ps Sg, 3. Ps Sg
			addVF(stamm + "ast", pos);		// 2. Ps Sg
			addVF(stamm + "ans", pos);		// 1. Ps Pl
			addVF(stamm + "as", pos);		// 2. Ps Pl
			addVF(stamm + "an", pos);		// 3. Ps Pl	
		}
		
		
		private void addInversion(String stamm, VerbClass verbClass) {
			
			String klStamm = stamm.substring(0, stamm.length()-1);
			
			if (verbClass == VerbClass.ER){
				addVF(stamm+"i", "V_GVRB"); addVF(klStamm+"i", "V_GVRB"); addVF(stamm+"ast", "V_GVRB");addVF(stamm+"a'l", "V_GVRB");addVF(klStamm+"'la", "V_GVRB");addVF(stamm+"ainsa", "V_GVRB");addVF(stamm+"ais", "V_GVRB");addVF(stamm+"ane", "V_GVRB");
			
			} else if (verbClass == VerbClass.IR) {
				addVF(stamm+"i", "V_GVRB"); addVF(stamm+"ast", "V_GVRB");addVF(stamm+"a'l", "V_GVRB");addVF(klStamm+"'la", "V_GVRB");addVF(stamm+"insa", "V_GVRB");addVF(stamm+"is", "V_GVRB");addVF(klStamm+"ane", "V_GVRB");
			}
			
		}

		private void addIrregularVerbs() {
			String stamm = "";
			
			// esser
			// infinitiv
			addVF("esser", "V_ESSER");
			// Partizip
			addVF("sto", "V_PP");addVF("stos", "V_PP");addVF("steda", "V_PP");addVF("stedas", "V_PP");
			// Gerundium
			addVF("siand", "V_ESSER");
			// Imperativ
			addVF("sajast", "V_ESSER");addVF("sajans", "V_ESSER");addVF("sajas", "V_ESSER");addVF("saja", "V_ESSER");addVF("sajan", "V_ESSER");
			// Indikativ Präsens
			addVF("sun", "V_ESSER");addVF("est", "V_ESSER");addVF("es", "V_ESSER");addVF("essans", "V_ESSER");addVF("essas", "V_ESSER");
			// Indikativ Imperfekt
			addVF("d'eira", "V_ESSER");addVF("d'eirast", "V_ESSER");addVF("eira", "V_ESSER");addVF("d'eira", "V_ESSER");addVF("eirans", "V_ESSER");addVF("d'eirans", "V_ESSER");addVF("eiras", "V_ESSER");addVF("d'eiras", "V_ESSER");addVF("eiran", "V_ESSER");addVF("d'eiran", "V_ESSER");
			// Indikativ passo definieu
			addVF("füt", "V_ESSER");addVF("füttast", "V_ESSER");addVF("füttans", "V_ESSER");addVF("füttas", "V_ESSER");addVF("füttan", "V_ESSER");
			// Indikativ Futur I
			addVF("saro", "V_ESSER");addVF("sarost", "V_ESSER");addVF("sarons", "V_ESSER");addVF("saros", "V_ESSER");addVF("saron", "V_ESSER");
			// Indikativ futur dubitativ
			addVF("saregia", "V_ESSER");addVF("saregiast", "V_ESSER");addVF("saregians", "V_ESSER");addVF("saregias", "V_ESSER");addVF("saregian", "V_ESSER");
			// Konjunktiv Präsens
			addVF("saja", "V_ESSER");addVF("sajast", "V_ESSER");addVF("sajans", "V_ESSER");addVF("sajas", "V_ESSER");addVF("sajan", "V_ESSER");
			// Konjunktiv Imperfekt
			addVF("füss", "V_ESSER");addVF("füssast", "V_ESSER");addVF("füssans", "V_ESSER");addVF("füssas", "V_ESSER");addVF("füssan", "V_ESSER");
			
			
			// avair
			// infinitiv
			addVF("avair", "V_AVAIR");
			// Partizip
			addVF("gieu", "V_PP");
			// Gerundium
			addVF("aviand", "V_AVAIR");
			// Imperativ
			addVF("hegiast", "V_AVAIR");addVF("hegians", "V_AVAIR");addVF("hegias", "V_AVAIR");addVF("hegia", "V_AVAIR");addVF("hegian", "V_AVAIR");
			// Indikativ Präsens
			addVF("d'he", "V_AVAIR");addVF("hest", "V_AVAIR");addVF("ho", "V_AVAIR");addVF("vains", "V_AVAIR");addVF("vais", "V_AVAIR");addVF("haun", "V_AVAIR");
			addVF("avains", "V_AVAIR");addVF("avais", "V_AVAIR");
			// Indikativ Imperfekt
			addVF("vaiva", "V_AVAIR");addVF("vaivast", "V_AVAIR");addVF("vaivans", "V_AVAIR");addVF("vaivas", "V_AVAIR");addVF("vaivan", "V_AVAIR");
			addVF("avaiva", "V_AVAIR");addVF("avaivast", "V_AVAIR");addVF("avaivans", "V_AVAIR");addVF("avaivas", "V_AVAIR");addVF("avaivan", "V_AVAIR");
			// Indikativ passo definieu
			addVF("avet", "V_ESSER");addVF("avettast", "V_ESSER");addVF("avettans", "V_ESSER");addVF("avettas", "V_ESSER");addVF("avettan", "V_ESSER");
			// Indikativ Futur I
			addVF("varo", "V_AVAIR");addVF("varost", "V_AVAIR");addVF("varons", "V_AVAIR");addVF("varos", "V_AVAIR");addVF("varon", "V_AVAIR");
			addVF("avaro", "V_AVAIR");addVF("avarost", "V_AVAIR");addVF("avarons", "V_AVAIR");addVF("avaros", "V_AVAIR");addVF("avaron", "V_AVAIR");
			// Indikativ futur dubitativ
			addVF("varegia", "V_ESSER");addVF("varegiast", "V_ESSER");addVF("varegians", "V_ESSER");addVF("varegias", "V_ESSER");addVF("varegian", "V_ESSER");
			addVF("avaregia", "V_ESSER");addVF("avaregiast", "V_ESSER");addVF("avaregians", "V_ESSER");addVF("avaregias", "V_ESSER");addVF("avaregian", "V_ESSER");
			// Konjunktiv Präsens
			addVF("hegia", "V_AVAIR");addVF("hegiast", "V_AVAIR");addVF("hegians", "V_AVAIR");addVF("hegias", "V_AVAIR");addVF("hegian", "V_AVAIR");
			// Konjunktiv Imperfekt
			addVF("vess", "V_AVAIR");addVF("vessast", "V_AVAIR");addVF("vess", "V_AVAIR");addVF("vessans", "V_AVAIR");addVF("vessas", "V_AVAIR");addVF("vessan", "V_AVAIR");
			addVF("avess", "V_AVAIR");addVF("avessast", "V_AVAIR");addVF("avess", "V_AVAIR");addVF("avessans", "V_AVAIR");addVF("avessas", "V_AVAIR");addVF("avessan", "V_AVAIR");
			
			// ster
			// infinitiv
			addVF("ster", "V_GVRB");
			// Partizip
			addVF("sto", "V_PP");addVF("stos", "V_PP");addVF("steda", "V_PP");addVF("stedas", "V_PP");
			// Gerundium
			addVF("stand", "V_GVRB");
			// Imperativ
			addVF("stainsa", "V_GVRB");addVF("stè", "V_GVRB");addVF("sto'm", "V_GVRB");addVF("sto'l", "V_GVRB");addVF("sto'ns", "V_GVRB");addVF("sto'ls", "V_GVRB");addVF("stettan", "V_GVRB");
			// Indikativ Präsens
			addVF("stun", "V_GVRB");addVF("stest", "V_GVRB");addVF("sto", "V_GVRB");addVF("stains", "V_GVRB");addVF("stais", "V_GVRB");addVF("staun", "V_GVRB");
			// Indikativ Imperfekt
			stamm = "staiv";
			addVF(stamm + "a", "V_GVRB");addVF(stamm + "ast", "V_GVRB");addVF(stamm + "ans", "V_GVRB");addVF(stamm + "as", "V_GVRB");addVF(stamm + "an", "V_GVRB");	
			// Indikativ passo definieu
			stamm = "ste";
			addVF(stamm + "t", "V_GVRB");addVF(stamm + "ttast", "V_GVRB");addVF(stamm + "ttans", "V_GVRB");addVF(stamm + "ttas", "V_GVRB");addVF(stamm + "ttan", "V_GVRB");
			// Indikativ Futur I
			stamm = "star";
			addVF(stamm + "o", "V_GVRB");addVF(stamm + "ost", "V_GVRB");addVF(stamm + "ons", "V_GVRB");	addVF(stamm + "os", "V_GVRB");addVF(stamm + "on", "V_GVRB");
			// Indikativ futur dubitativ
			stamm = "sta";
			addVF(stamm + "regia", "V_GVRB");addVF(stamm + "regiast", "V_GVRB");addVF(stamm + "regians", "V_GVRB");addVF(stamm + "regias", "V_GVRB");addVF(stamm + "regian", "V_GVRB");
			// Konjunktiv Präsens
			addVF("stetta", "V_GVRB");addVF("stettast", "V_GVRB");addVF("stettans", "V_GVRB");addVF("stettas", "V_GVRB");
			// Konjunktiv Imperfekt
			stamm = "ste";
			addVF(stamm + "ss", "V_GVRB");addVF(stamm + "ssast", "V_GVRB");addVF(stamm + "ssans", "V_GVRB");addVF(stamm + "ssas", "V_GVRB");addVF(stamm + "ssan", "V_GVRB");
			
			
			// ir
			// infinitiv
			addVF("ir", "V_GVRB");
			// Partizip
			addVF("ieu", "V_PP");addVF("ieus", "V_PP");addVF("ida", "V_PP");addVF("idas", "V_PP");
			// Gerundium
			addVF("giand", "V_GVRB");
			// Imperativ
			addVF("vo", "V_GVRB");addVF("giainsa", "V_GVRB");addVF("gè", "V_GVRB");addVF("gaijan", "V_GVRB");
			stamm = "vo";
			addVF(stamm + "'m", "V_GVRB");addVF(stamm + "'l", "V_GVRB");addVF(stamm + "'ns", "V_GVRB");addVF(stamm + "'ls", "V_GVRB");
			// Indikativ Präsens
			addVF("vegn", "V_GVRB");addVF("vest", "V_GVRB");addVF("vo", "V_GVRB");addVF("giains", "V_GVRB");addVF("giais", "V_GVRB");addVF("vaun", "V_GVRB");
			// Indikativ Imperfekt
			stamm = "giaiv";
			addVF(stamm + "a", "V_GVRB");addVF(stamm + "ast", "V_GVRB");addVF(stamm + "ans", "V_GVRB");addVF(stamm + "as", "V_GVRB");addVF(stamm + "an", "V_GVRB");	
			// Indikativ passo definieu
			stamm = "ge";
			addVF(stamm + "t", "V_GVRB");addVF(stamm + "ttast", "V_GVRB");addVF(stamm + "ttans", "V_GVRB");addVF(stamm + "ttas", "V_GVRB");addVF(stamm + "ttan", "V_GVRB");
			// Indikativ Futur I
			stamm = "giar";
			addVF(stamm + "o", "V_GVRB");addVF(stamm + "ost", "V_GVRB");addVF(stamm + "ons", "V_GVRB");	addVF(stamm + "os", "V_GVRB");addVF(stamm + "on", "V_GVRB");
			// Indikativ futur dubitativ
			stamm = "gia";
			addVF(stamm + "regia", "V_GVRB");addVF(stamm + "regiast", "V_GVRB");addVF(stamm + "regians", "V_GVRB");addVF(stamm + "regias", "V_GVRB");addVF(stamm + "regian", "V_GVRB");
			// Konjunktiv Präsens
			addVF("giaja", "V_GVRB");addVF("giajast", "V_GVRB");addVF("giajans", "V_GVRB");addVF("giajas", "V_GVRB");
			// Konjunktiv Imperfekt
			stamm = "ge";
			addVF(stamm + "ss", "V_GVRB");addVF(stamm + "ssast", "V_GVRB");addVF(stamm + "ssans", "V_GVRB");addVF(stamm + "ssas", "V_GVRB");addVF(stamm + "ssan", "V_GVRB");
			
			// gnir
			// infinitiv
			addVF("gnir", "V_GVRB");
			// Partizip
			addVF("gnieu", "V_PP");addVF("gnieus", "V_PP");addVF("gnida", "V_PP");addVF("gnidas", "V_PP");
			// Gerundium
			addVF("gnind", "V_GVRB");
			// Imperativ
			addVF("vè", "V_GVRB");addVF("gninsa", "V_GVRB");addVF("gni", "V_GVRB");addVF("gnir", "V_GVRB");addVF("vegnan", "V_GVRB");
			addVF("vè'm", "V_GVRB");addVF("vè'ns", "V_GVRB");
			// Indikativ Präsens
			addVF("vegn", "V_GVRB");addVF("vest", "V_GVRB");addVF("vo", "V_GVRB");addVF("giains", "V_GVRB");addVF("giais", "V_GVRB");addVF("vaun", "V_GVRB");
			// Indikativ Imperfekt
			stamm = "gniv";
			addVF(stamm + "a", "V_GVRB");addVF(stamm + "ast", "V_GVRB");addVF(stamm + "ans", "V_GVRB");addVF(stamm + "as", "V_GVRB");addVF(stamm + "an", "V_GVRB");	
			// Indikativ passo definieu
			stamm = "gni";
			addVF(stamm + "t", "V_GVRB");addVF(stamm + "ttast", "V_GVRB");addVF(stamm + "ttans", "V_GVRB");addVF(stamm + "ttas", "V_GVRB");addVF(stamm + "ttan", "V_GVRB");
			// Indikativ Futur I
			stamm = "gnar";
			addVF(stamm + "o", "V_GVRB");addVF(stamm + "ost", "V_GVRB");addVF(stamm + "ons", "V_GVRB");	addVF(stamm + "os", "V_GVRB");addVF(stamm + "on", "V_GVRB");
			// Indikativ futur dubitativ
			stamm = "gna";
			addVF(stamm + "regia", "V_GVRB");addVF(stamm + "regiast", "V_GVRB");addVF(stamm + "regians", "V_GVRB");addVF(stamm + "regias", "V_GVRB");addVF(stamm + "regian", "V_GVRB");
			// Konjunktiv Präsens
			addVF("vegna", "V_GVRB");addVF("vegnast", "V_GVRB");addVF("vegnans", "V_GVRB");addVF("vegnas", "V_GVRB");
			// Konjunktiv Imperfekt
			stamm = "gni";
			addVF(stamm + "ss", "V_GVRB");addVF(stamm + "ssast", "V_GVRB");addVF(stamm + "ssans", "V_GVRB");addVF(stamm + "ssas", "V_GVRB");addVF(stamm + "ssan", "V_GVRB");
			
			
			// der
			// infinitiv
			addVF("der", "V_GVRB");
			// Partizip
			addVF("do", "V_PP");addVF("dos", "V_PP");addVF("deda", "V_PP");addVF("dedas", "V_PP");
			// Gerundium
			addVF("dand", "V_GVRB");
			// Imperativ
			addVF("dainsa", "V_GVRB");addVF("dè", "V_GVRB");addVF("dettan", "V_GVRB");
			stamm = "do";
			addVF(stamm + "'m", "V_GVRB");addVF(stamm + "'l", "V_GVRB");addVF(stamm + "'ns", "V_GVRB");addVF(stamm + "'ls", "V_GVRB");
			// Indikativ Präsens
			addVF("dun", "V_GVRB");addVF("dest", "V_GVRB");addVF("do", "V_GVRB");addVF("dains", "V_GVRB");addVF("dais", "V_GVRB");addVF("daun", "V_GVRB");
			// Indikativ Imperfekt
			stamm = "daiv";
			addVF(stamm + "a", "V_GVRB");addVF(stamm + "ast", "V_GVRB");addVF(stamm + "ans", "V_GVRB");addVF(stamm + "as", "V_GVRB");addVF(stamm + "an", "V_GVRB");	
			// Indikativ passo definieu
			stamm = "de";
			addVF(stamm + "t", "V_GVRB");addVF(stamm + "ttast", "V_GVRB");addVF(stamm + "ttans", "V_GVRB");addVF(stamm + "ttas", "V_GVRB");addVF(stamm + "ttan", "V_GVRB");
			// Indikativ Futur I
			stamm = "dar";
			addVF(stamm + "o", "V_GVRB");addVF(stamm + "ost", "V_GVRB");addVF(stamm + "ons", "V_GVRB");	addVF(stamm + "os", "V_GVRB");addVF(stamm + "on", "V_GVRB");
			// Indikativ futur dubitativ
			stamm = "da";
			addVF(stamm + "regia", "V_GVRB");addVF(stamm + "regiast", "V_GVRB");addVF(stamm + "regians", "V_GVRB");addVF(stamm + "regias", "V_GVRB");addVF(stamm + "regian", "V_GVRB");
			// Konjunktiv Präsens
			addVF("detta", "V_GVRB");addVF("dettast", "V_GVRB");addVF("dettans", "V_GVRB");addVF("dettas", "V_GVRB");
			// Konjunktiv Imperfekt
			stamm = "de";
			addVF(stamm + "ss", "V_GVRB");addVF(stamm + "ssast", "V_GVRB");addVF(stamm + "ssans", "V_GVRB");addVF(stamm + "ssas", "V_GVRB");addVF(stamm + "ssan", "V_GVRB");
			
			// dir
			// infinitiv
			addVF("dir", "V_GVRB");
			// Partizip
			addVF("dit", "V_PP");addVF("dits", "V_PP");addVF("ditta", "V_PP");addVF("dittas", "V_PP");
			// Gerundium
			addVF("dschand", "V_GVRB");
			// Imperativ
			addVF("dschainsa", "V_GVRB");addVF("dschè", "V_GVRB");
			stamm = "di";
			addVF(stamm + "'m", "V_GVRB");addVF(stamm + "'l", "V_GVRB");addVF(stamm + "'ns", "V_GVRB");addVF(stamm + "'ls", "V_GVRB");
			// Indikativ Präsens
			addVF("di", "V_GVRB");addVF("dist", "V_GVRB");addVF("disch", "V_GVRB");addVF("dschains", "V_GVRB");addVF("dschais", "V_GVRB");addVF("dian", "V_GVRB");
			// Indikativ Imperfekt
			stamm = "dschaiv";
			addVF(stamm + "a", "V_GVRB");addVF(stamm + "ast", "V_GVRB");addVF(stamm + "ans", "V_GVRB");addVF(stamm + "as", "V_GVRB");addVF(stamm + "an", "V_GVRB");	
			// Indikativ passo definieu
			stamm = "dsche";
			addVF(stamm + "t", "V_GVRB");addVF(stamm + "ttast", "V_GVRB");addVF(stamm + "ttans", "V_GVRB");addVF(stamm + "ttas", "V_GVRB");addVF(stamm + "ttan", "V_GVRB");
			// Indikativ Futur I
			stamm = "dschar";
			addVF(stamm + "o", "V_GVRB");addVF(stamm + "ost", "V_GVRB");addVF(stamm + "ons", "V_GVRB");	addVF(stamm + "os", "V_GVRB");addVF(stamm + "on", "V_GVRB");
			// Indikativ futur dubitativ
			stamm = "dscha";
			addVF(stamm + "regia", "V_GVRB");addVF(stamm + "regiast", "V_GVRB");addVF(stamm + "regians", "V_GVRB");addVF(stamm + "regias", "V_GVRB");addVF(stamm + "regian", "V_GVRB");
			// Konjunktiv Präsens
			addVF("dia", "V_GVRB");addVF("diast", "V_GVRB");addVF("dians", "V_GVRB");addVF("dias", "V_GVRB");
			// Konjunktiv Imperfekt
			stamm = "dsche";
			addVF(stamm + "ss", "V_GVRB");addVF(stamm + "ssast", "V_GVRB");addVF(stamm + "ssans", "V_GVRB");addVF(stamm + "ssas", "V_GVRB");addVF(stamm + "ssan", "V_GVRB");
			
			// modale Verben
			
			// fer
			// infinitiv
			addVF("fer", "V_MOD");
			// Partizip
			addVF("fat", "V_PP");addVF("fats", "V_PP");addVF("fatta", "V_PP");addVF("fattas", "V_PP");
			// Gerundium
			addVF("fand", "V_MOD");
			// Imperativ
			addVF("fainsa", "V_MOD");addVF("fè", "V_MOD");addVF("fatscha", "V_MOD");addVF("fatschan", "V_MOD");
			stamm = "fo";
			addVF(stamm + "'m", "V_MOD");addVF(stamm + "'l", "V_MOD");addVF(stamm + "'ns", "V_MOD");addVF(stamm + "'ls", "V_MOD");
			// Indikativ Präsens
			addVF("fatsch", "V_MOD");addVF("fest", "V_MOD");addVF("fo", "V_MOD");addVF("fains", "V_MOD");addVF("fais", "V_MOD");addVF("faun", "V_MOD");
			// Indikativ Imperfekt
			stamm = "faiv";
			addVF(stamm + "a", "V_MOD");addVF(stamm + "ast", "V_MOD");addVF(stamm + "ans", "V_MOD");addVF(stamm + "as", "V_MOD");addVF(stamm + "an", "V_MOD");	
			// Indikativ passo definieu
			stamm = "fe";
			addVF(stamm + "t", "V_MOD");addVF(stamm + "ttast", "V_MOD");addVF(stamm + "ttans", "V_MOD");addVF(stamm + "ttas", "V_MOD");addVF(stamm + "ttan", "V_MOD");
			// Indikativ Futur I
			stamm = "far";
			addVF(stamm + "o", "V_MOD");addVF(stamm + "ost", "V_MOD");addVF(stamm + "ons", "V_MOD");	addVF(stamm + "os", "V_MOD");addVF(stamm + "on", "V_MOD");
			// Indikativ futur dubitativ
			stamm = "fa";
			addVF(stamm + "regia", "V_MOD");addVF(stamm + "regiast", "V_MOD");addVF(stamm + "regians", "V_MOD");addVF(stamm + "regias", "V_MOD");addVF(stamm + "regian", "V_MOD");
			// Konjunktiv Präsens
			addVF("fatscha", "V_MOD");addVF("fatschast", "V_MOD");addVF("fatschans", "V_MOD");addVF("fatschas", "V_MOD");
			// Konjunktiv Imperfekt
			stamm = "fe";
			addVF(stamm + "ss", "V_MOD");addVF(stamm + "ssast", "V_MOD");addVF(stamm + "ssans", "V_MOD");addVF(stamm + "ssas", "V_MOD");addVF(stamm + "ssan", "V_MOD");
					
			// vulair
			// infinitiv
			addVF("vulair", "V_MOD");
			// Partizip
			addVF("vulieu", "V_PP");addVF("vulieus", "V_PP");addVF("vulida", "V_PP");addVF("vuglidas", "V_PP");
			// Gerundium
			addVF("vuliand", "V_MOD");
			// Imperativ
			// --
			// Indikativ Präsens
			addVF("vögl", "V_MOD");addVF("voust", "V_MOD");addVF("voul", "V_MOD");addVF("vulains", "V_MOD");addVF("vulais", "V_MOD");addVF("vöglian", "V_MOD");
			// Indikativ Imperfekt
			stamm = "vulaiv";
			addVF(stamm + "a", "V_MOD");addVF(stamm + "ast", "V_MOD");addVF(stamm + "ans", "V_MOD");addVF(stamm + "as", "V_MOD");addVF(stamm + "an", "V_MOD");	
			// Indikativ passo definieu
			stamm = "vule";
			addVF(stamm + "t", "V_MOD");addVF(stamm + "ttast", "V_MOD");addVF(stamm + "ttans", "V_MOD");addVF(stamm + "ttas", "V_MOD");addVF(stamm + "ttan", "V_MOD");
			// Indikativ Futur I
			stamm = "vular";
			addVF(stamm + "o", "V_MOD");addVF(stamm + "ost", "V_MOD");addVF(stamm + "ons", "V_MOD");	addVF(stamm + "os", "V_MOD");addVF(stamm + "on", "V_MOD");
			// Indikativ futur dubitativ
			stamm = "vula";
			addVF(stamm + "regia", "V_MOD");addVF(stamm + "regiast", "V_MOD");addVF(stamm + "regians", "V_MOD");addVF(stamm + "regias", "V_MOD");addVF(stamm + "regian", "V_MOD");
			// Konjunktiv Präsens
			addVF("vöglia","V_MOD");addVF("vögliast", "V_MOD");addVF("vöglians", "V_MOD");addVF("vöglias", "V_MOD");
			// Konjunktiv Imperfekt
			stamm = "vule";
			addVF(stamm + "ss", "V_MOD");addVF(stamm + "ssast", "V_MOD");addVF(stamm + "ssans", "V_MOD");addVF(stamm + "ssas", "V_MOD");addVF(stamm + "ssan", "V_MOD");
			
			
			// pudair
			// infinitiv
			addVF("pudair", "V_MOD");
			// Partizip
			addVF("pudieu", "V_PP");addVF("pudieus", "V_PP");addVF("pudida", "V_PP");addVF("pudidas", "V_PP");
			// Gerundium
			addVF("pudiand", "V_MOD");
			// Imperativ
			// --
			// Indikativ Präsens
			addVF("poss", "V_MOD");addVF("poust", "V_MOD");addVF("po", "V_MOD");addVF("pudains", "V_MOD");addVF("pudais", "V_MOD");addVF("paun", "V_MOD");
			// Indikativ Imperfekt
			stamm = "pudaiv";
			addVF(stamm + "a", "V_MOD");addVF(stamm + "ast", "V_MOD");addVF(stamm + "ans", "V_MOD");addVF(stamm + "as", "V_MOD");addVF(stamm + "an", "V_MOD");	
			// Indikativ passo definieu
			stamm = "pude";
			addVF(stamm + "t", "V_MOD");addVF(stamm + "ttast", "V_MOD");addVF(stamm + "ttans", "V_MOD");addVF(stamm + "ttas", "V_MOD");addVF(stamm + "ttan", "V_MOD");
			// Indikativ Futur I
			stamm = "pudar";
			addVF(stamm + "o", "V_MOD");addVF(stamm + "ost", "V_MOD");addVF(stamm + "ons", "V_MOD");	addVF(stamm + "os", "V_MOD");addVF(stamm + "on", "V_MOD");
			// Indikativ futur dubitativ
			stamm = "puda";
			addVF(stamm + "regia", "V_MOD");addVF(stamm + "regiast", "V_MOD");addVF(stamm + "regians", "V_MOD");addVF(stamm + "regias", "V_MOD");addVF(stamm + "regian", "V_MOD");
			// Konjunktiv Präsens
			addVF("possa","V_MOD");addVF("possast", "V_MOD");addVF("possans", "V_MOD");addVF("possas", "V_MOD");
			// Konjunktiv Imperfekt
			stamm = "pude";
			addVF(stamm + "ss", "V_MOD");addVF(stamm + "ssast", "V_MOD");addVF(stamm + "ssans", "V_MOD");addVF(stamm + "ssas", "V_MOD");addVF(stamm + "ssan", "V_MOD");
			
			
			// stuvair
			stamm = "stuv";
			// infinitiv
			addVF("stuvair", "V_MOD");
			// Partizip
			addVF(stamm+"ieu", "V_PP");addVF(stamm+"ieus", "V_PP");addVF(stamm+"ida", "V_PP");addVF(stamm+"idas", "V_PP");
			// Gerundium
			addVF(stamm+"iand", "V_MOD");
			// Imperativ
			// --		
			// Indikativ Präsens
			addVF("stögl", "V_MOD");addVF("stust", "V_MOD");addVF("stu", "V_MOD");addVF("stuains", "V_MOD");addVF("stuvais", "V_MOD");addVF("stöglian", "V_MOD");
			// Indikativ Imperfekt
			stamm = "stuvaiv";
			addVF(stamm + "a", "V_MOD");addVF(stamm + "ast", "V_MOD");addVF(stamm + "ans", "V_MOD");addVF(stamm + "as", "V_MOD");addVF(stamm + "an", "V_MOD");	
			// Indikativ passo definieu
			stamm = "stuve";
			addVF(stamm + "t", "V_MOD");addVF(stamm + "ttast", "V_MOD");addVF(stamm + "ttans", "V_MOD");addVF(stamm + "ttas", "V_MOD");addVF(stamm + "ttan", "V_MOD");
			// Indikativ Futur I
			stamm = "stuvar";
			addVF(stamm + "o", "V_MOD");addVF(stamm + "ost", "V_MOD");addVF(stamm + "ons", "V_MOD");	addVF(stamm + "os", "V_MOD");addVF(stamm + "on", "V_MOD");
			// Indikativ futur dubitativ
			stamm = "stuva";
			addVF(stamm + "regia", "V_MOD");addVF(stamm + "regiast", "V_MOD");addVF(stamm + "regians", "V_MOD");addVF(stamm + "regias", "V_MOD");addVF(stamm + "regian", "V_MOD");
			// Konjunktiv Präsens
			addVF("stöglia","V_MOD");addVF("stögliast", "V_MOD");addVF("stöglians", "V_MOD");addVF("stöglias", "V_MOD");
			// Konjunktiv Imperfekt
			stamm = "stuve";
			addVF(stamm + "ss", "V_MOD");addVF(stamm + "ssast", "V_MOD");addVF(stamm + "ssans", "V_MOD");addVF(stamm + "ssas", "V_MOD");addVF(stamm + "ssan", "V_MOD");
						
			
			// savair
			stamm = "sav";
			// infinitiv
			addVF("savair", "V_MOD");
			// Partizip
			addVF(stamm+"ieu", "V_PP");addVF(stamm+"ieus", "V_PP");addVF(stamm+"ida", "V_PP");addVF(stamm+"idas", "V_PP");
			// Gerundium
			addVF(stamm+"iand", "V_MOD");
			// Imperativ
			// --	
			// Indikativ Präsens
			addVF("se", "V_MOD");addVF("sest", "V_MOD");addVF("so", "V_MOD");addVF(stamm+"ains", "V_MOD");addVF(stamm+"ais", "V_MOD");addVF("saun", "V_MOD");
			// Indikativ Imperfekt
			stamm = "savaiv";
			addVF(stamm + "a", "V_MOD");addVF(stamm + "ast", "V_MOD");addVF(stamm + "ans", "V_MOD");addVF(stamm + "as", "V_MOD");addVF(stamm + "an", "V_MOD");	
			// Indikativ passo definieu
			stamm = "save";
			addVF(stamm + "t", "V_MOD");addVF(stamm + "ttast", "V_MOD");addVF(stamm + "ttans", "V_MOD");addVF(stamm + "ttas", "V_MOD");addVF(stamm + "ttan", "V_MOD");
			// Indikativ Futur I
			stamm = "savar";
			addVF(stamm + "o", "V_MOD");addVF(stamm + "ost", "V_MOD");addVF(stamm + "ons", "V_MOD");	addVF(stamm + "os", "V_MOD");addVF(stamm + "on", "V_MOD");
			// Indikativ futur dubitativ
			stamm = "sava";
			addVF(stamm + "regia", "V_MOD");addVF(stamm + "regiast", "V_MOD");addVF(stamm + "regians", "V_MOD");addVF(stamm + "regias", "V_MOD");addVF(stamm + "regian", "V_MOD");
			// Konjunktiv Präsens
			addVF("sapcha","V_MOD");addVF("sapchast", "V_MOD");addVF("sapchans", "V_MOD");addVF("sapchas", "V_MOD");
			// Konjunktiv Imperfekt
			stamm = "save";
			addVF(stamm + "ss", "V_MOD");addVF(stamm + "ssast", "V_MOD");addVF(stamm + "ssans", "V_MOD");addVF(stamm + "ssas", "V_MOD");addVF(stamm + "ssan", "V_MOD");
			
			// dovair
			// Indikativ Präsens
			addVF("dess", "V_GVRB");addVF("dessast", "V_GVRB");addVF("dessans", "V_GVRB");addVF("dessas", "V_GVRB");addVF("dessa", "V_GVRB");addVF("dessan", "V_GVRB");
			
			// solair
			// Indikativ Präsens
			addVF("soul", "V_GVRB");addVF("soulast", "V_GVRB");addVF("soula", "V_GVRB");addVF("solain", "V_GVRB");addVF("solais", "V_GVRB");addVF("soulan", "V_GVRB");
			// Indikativ Imperfekt
			addVF("solaiva", "V_GVRB");addVF("solaivast", "V_GVRB");addVF("solaiva", "V_GVRB");addVF("solaivans", "V_GVRB");addVF("solaivas", "V_GVRB");addVF("solaivan", "V_GVRB");
			// Konjunktiv Präsens
			addVF("soula", "V_GVRB");addVF("soulast", "V_GVRB");addVF("soulans", "V_GVRB");addVF("soulas", "V_GVRB");addVF("soulan", "V_GVRB");addVF("solaivan", "V_GVRB");
			
			
//			// Verben mit Spezialendung
//			
//			//dür
//			//adür
//			dür("a");
//			//ardüer
//			dür("ar");
//			//condüer
//			dür("con");
//			// dedüer
//			dür("de");
//			// introdüer
//			dür("intro");
//			// prodüer
//			dür("pro");
//			// redüer
//			dür("re");
//			// reintrodüer
//			dür("reintro");
//			// reprodüer
//			dür("repro");
//			// tradüer
//			dür("tra");
//			
//			// drür
//			// desdrür
//			drueer("des");
//			
		}
//		
//		private void dür(String prefix) {
//			String stamm = "dür";
//			//infinitiv
//			addVF(prefix+"dür", "V_GVRB");
//			//partizip
//			addPartizip(stamm, VerbClass.ER);
//			//gerundium
//			addGerundium(stamm, VerbClass.ER, "V_GVRB");
//			//Imperativ
//			addVF(prefix+"via", "V_GVRB");addImperativ(stamm, VerbClass.ER, "V_GVRB");
//			//Indikativ Präsens
//			addVF(prefix+"vignt", "V_GVRB");addVF(prefix+"vegn", "V_GVRB");addVF(prefix+"veans", "V_GVRB");addVF(prefix+"vean", "V_GVRB");addVF(stamm+"agn", "V_GVRB");addVF(stamm+"ez", "V_GVRB");addVF(prefix+"vignan", "V_GVRB");addVF(prefix+"vegnan", "V_GVRB");
//			//Indikativ Imperfekt
//			addIndikativImperfekt(stamm, VerbClass.ER, "V_GVRB");
//			//Konjunktiv Präsens
//			addVF(prefix+"vigni", "V_GVRB");addVF(prefix+"vignias", "V_GVRB");addVF(prefix+"vignas", "V_GVRB");addVF(stamm+"eian", "V_GVRB");addVF(stamm+"eias", "V_GVRB");addVF(stamm+"evan", "V_GVRB");
//			//Konjunktiv Imperfekt
//			addKonjunktivImperfekt(stamm, VerbClass.ER, "V_GVRB");
//		}
//		
//		private void drueer(String prefix) {
//			// 1 Ps Sg Indikativ Präsens
//			addVF(prefix+"drüj", "V_GVRB");
//		}
		
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
			switch (inf.substring(inf.length()-1)) {
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

