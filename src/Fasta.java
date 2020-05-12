import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;


public class Fasta {
	private static final String FILE_OUT1="salida.txt";
/////////////////////////////////////////
	//Estado
/////////////////////////////////////////
	Gen gen;
	String ficheroSeleccionado;
	String resultado;
	
	//Comportamiento
    //Constructor
    public Fasta(Gen migen, String fichero) {
 		gen=migen;
 		ficheroSeleccionado=fichero;
 		
    }
    
    //Comportamiento principal de búsqueda. Abrirá el fichero, creará la cola, la alimentará y le dará
    // la cola a gen para comparar el contenido y así obtener el resultado.
    public void buscaGen(Gen miGen, byte errores) {
    	//variable 
    	Queue <Character> cola=new LinkedList<Character>();
 		int caracter=0;
 		int contador=0;
 		boolean encontrado=false;
                String lineaOut="";	
 		
 		try {
 			// abrimos el lector de fichero
	    	FileReader lector = new FileReader(new File (ficheroSeleccionado));
	    	
	    	//Recorremos el fichero caracter a caracter y cargamos la cola
	    	do {
	    		caracter=lector.read();
	    		// si el caracter es > estamos en la primera linea que contiene el nombre Fasta la descartamos
	    		if (String.valueOf((char)caracter).contentEquals(">")) {
	    			do {
                                    //guardamos la primera linea
                                    lineaOut+=(char)caracter;
                                    // recorremos la linea hasta encontrar retorno de carro
                                    caracter=lector.read();                       
	    			} while (!String.valueOf((char)caracter).contentEquals(String.valueOf("\n")));
                                // prepara el fichero de salida
                                lineaOut+="\n";
                                OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(new File(System.getProperty("user.dir"), FILE_OUT1),true));
                                // añade la linea
                                ow.write(lineaOut);
                                //cierra el fichero
                                ow.close();
	    		} else {
	    			if (!String.valueOf((char)caracter).contentEquals(String.valueOf("\n"))) {
		    			//cola.add(Character.valueOf((char)caracter));
		    			cola.add((char) caracter);
			    		contador++;
	    			}
	    		}
	    	} while (caracter!=-1 && contador<miGen.getDatos().length());
	    	
	    	// mientras que no acabe el fichero llamamos a gen para comparar el resultado y remodelamos la cola
	    	do {

	    		// si el ultimo caracter devuelto no es un retorno de carro cambiamos la cola
	    		if (!String.valueOf((char)caracter).contentEquals(String.valueOf("\n"))) {

	    			// Si los errores son menos de los permitidos guardamos el resultado
	    			String[] listaParametros = gen.compara(cola).split(";");
	    			if (Integer.parseInt(listaParametros[1])<=errores) {
	    				resultado= gen.compara(cola);
	    				encontrado=true;
	    			}
	    			// avanzamos un caracter
                                cola.poll();
                                cola.add((char)caracter);
	    		}
	    		//leemos el siguiente caracter
	    		caracter=lector.read();
	    	}while (caracter!=-1 && !encontrado);
	    	
	    	//cerramos el lector
	 		lector.close();
	 		
 		} catch (Exception ex){
 			System.out.println(ex.getMessage());
 			
 		}
	}    

	
}
