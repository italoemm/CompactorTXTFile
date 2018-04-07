import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.org.apache.xerces.internal.impl.io.UTF8Reader;

import sun.nio.cs.UnicodeEncoder;

import java.util.zip.*;


/**
 * 
 */

/**
 * @italoemm 
 *
 */
public class Descompactar {

	
	public void descompactar(File origem, File destino) throws Exception{
		    List<String> lista = new ArrayList<String>();
		    Words p = new Words();
		    
		    BufferedReader in = new BufferedReader(new InputStreamReader(
	 			    new FileInputStream(origem), "UTF-8"));
		    String ler = null;
		    
		    while ((ler = in.readLine()) != null) {
		    	
		    	String[] cortar = ler.split(" "); // split the phrase
		    	

		    	//here it's going split each word
		    	for(int i = 0; i<cortar.length; i++){
				    		if(cortar[i].contains("³")){ /*Ex. (friends³²²²forever) words with a break line and a blank line
				    										   (friends³) words with only break line "final document"
				    										   (friends³forever) words with only break line and a simple word starting in next paragraph 
				    									 */				    			
		    		String[] cortarDeNovo = cortar[i].split("³"); // split the words when it reach to caracter"³" (friends³²²²forever) - to - (friends)
		    		lista.add(cortarDeNovo[0]); // assing into the list the words splited
		    		lista.add(System.getProperty("line.separator")); // and add break line corresponding "³"
		    		if(cortarDeNovo.length>1){ // if the array of words above is longer than 1 , that means has a blank line or a simple words
						if(cortarDeNovo[1].contains("²")){ 
							for (int j= 0;j<cortarDeNovo[1].length();j++){   //it's going check how many blank line it has (²²²forever)
			    			      if(cortarDeNovo[1].charAt(j)=='²'){   
			    			    	  lista.add(System.getProperty("line.separator")); // for each "²" it's going add a  break line
			    			      }
							}
							lista.add(cortarDeNovo[1].replace("²","")); // and after replace  all caracters that are "²" by nothing, Ex. ²²²forever - forever
		 				}else{
		 					lista.add(cortarDeNovo[1]); // otherwise if there's a simple words only add to list
		 				}
	 				}
		    		}else{
	 					lista.add(cortar[i]); // if not contain a "³" only add into list
	 				}
				
				}
		    } 
		   
		    
		    
		       for(int i = 0 ; i<lista.size();i++){
		    	
		    	   for(int j = 0 ; j < p.listaDePalavras.size();j++){
		    	   if(lista.get(i).equalsIgnoreCase(p.listaDeCE.get(j))){
		    		   lista.remove(i);
		    		   lista.add(i, p.listaDePalavras.get(j));
		    		   break;
		    	   }
		    	   }
		       }
		    
		       
		       
		    try {
		    	
		    	OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(destino), StandardCharsets.UTF_8);
		    	String str;
		    	
		    	// all the code below has the same logic of Class of Compactor.
		    	for (int i = 0; i < lista.size(); i++) {
		    		
    				for(int j = 0 ; j< p.fim.length;j++){
    					
    					
    					
    			
    					if(lista.get(i).endsWith(Integer.toString(j))){
    						String [] vet = lista.get(i).split(Integer.toString(j));
    						lista.remove(i);
    						try{
    						lista.add(i, vet[0]+p.fim[j]);	
    						}catch (Exception e) {
								System.out.println(e);
							}
    						break;
    					}
    				}
		    	
    				
    				
    				if(lista.get(i).contains(System.getProperty("line.separator"))){
    					str = lista.get(i);
    				}else{
    					str = lista.get(i)+" ";
    				}
	    		  		w.write(str);
			    		
    				}
		    	
		    	
		        
		        w.close();
		    
		        in.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }

		    
		
		System.out.println("descompactacao efetuada com sucesso ");
	}

	public static void main(String[] args) throws Exception {
		
		File origem = null;
		File destino= new File ("C:\\Users\\Agueda\\Desktop\\descompactado.txt");
	    FileFilter filter;
	    
	    {
	    JFileChooser    fc =  new JFileChooser();
    	
    	
    	filter = new FileNameExtensionFilter("All Text File", "txt");
    	
    
    	fc.addChoosableFileFilter(filter);
    	
    	fc.setFileFilter(filter);
    	
    	int result = fc.showOpenDialog(null);
    	
    	
    	if(result == fc.APPROVE_OPTION){
    		origem = fc.getSelectedFile();
    	}

	   Descompactar con = new Descompactar();
	   con.descompactar(origem, destino);
	} 
	    
	    
	    
	
	}
}
	
