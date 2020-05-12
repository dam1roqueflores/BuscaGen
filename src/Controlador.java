import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Controlador {
	
	private static final String FILE_OUT="salida.txt";

	private static final byte FASTA_HEAD_LINES = 10;  //Líneas a leer desde el fichero para mostrar en la GUI
	
	private static Controlador singleton;

	private static Controlador controlador=null;
	
        //Constructor privado. No hay que meterle código ninguno...
	private Controlador() {
		
	}

        //Obtención del singleton	
	public static Controlador getSingleton() {
        if (controlador==null) {controlador=new Controlador();        }
        return controlador;
	}
	

        //Comportamiento principal de la aplicación	
	public void realizaBusqueda(String gen, String mutaciones,String fichero) {
			Gen miGen=new Gen(gen,Byte.parseByte(mutaciones));
			Fasta miFasta=new Fasta(miGen,fichero);

			
			miFasta.buscaGen(miGen,Byte.parseByte(mutaciones));
			escribeFichero(miFasta.resultado);
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Método auxiliar para escribir el String resultado de la búsqueda. Es llamado desde realizaBusqueda
	private void escribeFichero(String resultado) {
		
		try {
			// prepara el fichero de salida
			OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(new File(System.getProperty("user.dir"), FILE_OUT),true));

			//Anade resultado
			String lineaOut=resultado+"\n";
			ow.append(lineaOut);
			//cierra el fichero
			ow.close();
		} catch (Exception ex){
			System.out.println(ex.getMessage());
		}
	}
	

         //Comportamiento que proporciona las primeras FASTA_HEAD_LINES para meter en el JTextArea
	public String cargaHeadFasta(String fichero) {
		String resultado="";
		int contador=0;
		File miFichero=new File (fichero);

		
		try {
			Scanner miScanner=new Scanner(miFichero);
			// carga las primeras nlineas en resultado
			while (miScanner.hasNextLine() && contador<=FASTA_HEAD_LINES) {
				resultado+=miScanner.nextLine()+"\n";
	    		contador++;
		    	}	
			miScanner.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		return resultado;
	}
	
	
}
