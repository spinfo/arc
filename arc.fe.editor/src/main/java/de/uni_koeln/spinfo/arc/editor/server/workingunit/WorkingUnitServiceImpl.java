package de.uni_koeln.spinfo.arc.editor.server.workingunit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.uni_koeln.spinfo.arc.editor.server.security.PasswordStore;
import de.uni_koeln.spinfo.arc.editor.shared.service.WorkingUnitService;

public class WorkingUnitServiceImpl extends RemoteServiceServlet implements WorkingUnitService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public synchronized void writeToCommandLog(String text) {
		 
		String path = getServletContext().getRealPath("/") + File.separator + "log" + File.separator;
		Date date = new Date();
		String PREFIX = date.getDay()+"_"+date.getMonth()+"_"+date.getYear();
		
		try {
		    PrintWriter out = new PrintWriter(
		    		new BufferedWriter(
		    				new FileWriter(path + PREFIX + "_commandLog.txt", true)));
		    out.println(text);
		    out.close();
		} catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}
	}
	
	/** path to the textfile with filenames of serialized WorkingUnit wrappers */
	String filename = "/workingUnitFileNames.txt";
	List<String> workingUnitFileNames = null;
	/**
	 * gets the filenames of serialized WorkingUnit wrappers from a textfile
	 * 
	 * @return a List with the filenames
	 */
	private List<String>  loadWorkingUnitFileNames() {
		// only parse the file if it hasn't been done yet
		if (workingUnitFileNames != null) return workingUnitFileNames;
		else workingUnitFileNames = new ArrayList<>();
		ServletContext context = getServletContext();
		InputStream inp = context.getResourceAsStream(filename);
		
		if (inp != null) {
			InputStreamReader isr = new InputStreamReader(inp);
			BufferedReader reader = new BufferedReader(isr);
			String text = "";
			try {
				while ((text = reader.readLine()) != null){
					System.out.println("parsing line:"  + text);
					workingUnitFileNames.add(text);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					reader.close();
					isr.close();
					inp.close();
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("No file found");
		}
		return workingUnitFileNames;		
	}
	
	@Override
	public boolean isPasswordCorrect(String password) {
		return password.equals(PasswordStore.PASSWORD);
	}




}
