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
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.zip.*;


/**
 * 
 */

/**
 * @italoemm 
 *
 */
public class Compactor {
    int cont = 0;

    public void compactar(File origem, File destino) throws Exception {
    	//list to store strings
        List < String > lista = new ArrayList < String > ();
        
        Words p = new Words();
        
        //read the file
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(origem), "UTF-8"));
        String ler = null;
        
        // for each space of each row it's going split assing the words into array
        while ((ler = in .readLine()) != null) {
            String[] cortar = ler.split(" ");
            
            //add phrase splited into list
            for (int i = 0; i < cortar.length; i++) {
                if (cortar.length == 1) { // for each blank line it's going set the caracter "²"
                    lista.add("²");
                } else {  
                    if ((i + 1) == cortar.length) {// for each break line it's going set the caracter "³"

                        lista.add(cortar[i] + "³");
                    } else { // otherwise only add the word
                        lista.add(cortar[i]);
                    }
                }
            }

        }


        // it's going compare the words of the "List<String> listaDePalavras" from class Words;
        for (int i = 0; i < lista.size(); i++) {
            for (int j = 0; j < p.listaDePalavras.size(); j++) {
                if (lista.get(i).equalsIgnoreCase(p.listaDePalavras.get(j))) { // if I found it
                    lista.remove(i); //it's going remove the currently word
                    lista.add(i, p.listaDeCE.get(j)); // and add a caracter of "List<String> listaDeCE" corresponding to the index of the "List<String> listaDePalavras"  
                    break;
                }
            }
        }


        try {
        	//set up the destiny of file compacted
            OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(destino), StandardCharsets.UTF_8);

            
            String str;
            /* after go through the list , it's going go throgh the list again but this time
             * it's going compare if the words end with the elements of array "String [] fim" from class Words
             */
            for (int i = 0; i < lista.size(); i++) {
                for (int j = 0; j < p.fim.length; j++) {
                    if (lista.get(i).endsWith(p.fim[j])) { //if yes , replace by index of array
                        String[] vet = lista.get(i).split(p.fim[j]);
                        lista.remove(i);
                        lista.add(i, vet[0] + j);
                        break;
                    }
                }

                
                
                if (lista.get(i).contains("²") || lista.get(i).contains("³")) { // if the element of list is a break line or blank line I don't need to put a space
                    str = lista.get(i); // assing into a string 
                } else { // if it's a simple words , assing inot string words of list with a space
                    str = lista.get(i) + " ";
                }
                w.write(str);

            }

            in .close();
            w.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("compactacao efetuada com sucesso ");
    }

    public static void main(String[] args) throws Exception {

        //directory of file.
        File origem = null;
        File destino = new File("C:\\Users\\Agueda\\Desktop\\destino2.txt");

        //filter of file.
        FileFilter filter;

        // box of user choose the input txt file
        JFileChooser fc = new JFileChooser();

        // set up the filters
        filter = new FileNameExtensionFilter("All Text File", "txt");

        //add the filter inot box
        fc.addChoosableFileFilter(filter);

        //make filter avaiable
        fc.setFileFilter(filter);

        // return the result of box
        int result = fc.showOpenDialog(null);

        if (result == fc.APPROVE_OPTION) {
            origem = fc.getSelectedFile();
        }

        Compactor con = new Compactor();
        con.compactar(origem, destino);

    }
}