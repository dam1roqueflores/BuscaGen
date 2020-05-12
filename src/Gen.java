import java.util.Queue;

public class Gen {
	//Estado
	String datos;
	byte mutaciones;
	
	
	//Comportamiento
        
        //Constructor
    public Gen(String datos, byte mutaciones) {
		this.datos=datos;
		this.mutaciones=mutaciones;
	}

        //Getter
    public String getDatos() {
		return datos;
	}


    //Comportamiento clave. Se llamará cada vez que el fichero cambie la cola. Nos dan una cola y la comparamos con nuestro
    //String caracter a caracter contando los fallos/errores. Se debe devolver una cadena "cola;erroresencontrados"
    public String compara(Queue<Character> cola) {
    	int error=0;
    	int contador=0;
        String miString=null;
    	String resultado;
    	char caracter;
    	
        miString=cola.toString().replaceAll("[^a-zA-Z]","");
    	while(contador<cola.size() && error<=mutaciones) {
                    if(miString.charAt(contador)!=datos.charAt(contador)) {
                            error++;	
                    }
                contador++;
    	}
    	resultado=cola+";"+error;
    	return resultado;
    }
	
	
        //Getter
	public byte getMutaciones() {
		return mutaciones;
	}


	
	
	

}
