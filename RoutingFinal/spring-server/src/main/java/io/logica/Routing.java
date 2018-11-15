package io.logica;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Routing {
	public String devolverRuta(String nroFactura, String operacion) throws FileNotFoundException, IOException {
		String salida = "";
		String ruta="";
		String tipoServ="";
		String archivoOper ="";
		String convenio = nroFactura.substring(0,4);
		String cadena;
		String datosTransformacion = "";
		String tipoOperHom = homologarOperacion(operacion);
	      FileReader f = new FileReader("C:/modelado_validacion/Rutas.txt");
	      BufferedReader b = new BufferedReader(f);
	      while((cadena = b.readLine())!=null) {
	          //System.out.println(cadena);
	    	String[] rutas = cadena.split("=");
          	if (rutas[0].equals(convenio)) {
          		ruta = rutas[1];
          		tipoServ= rutas[2];
          		archivoOper = rutas[3];
          		
          		FileReader o = new FileReader(archivoOper);
          		BufferedReader bo = new BufferedReader(o);
          		String cadenaOper;
          		while((cadenaOper = bo.readLine())!=null) {
          			String[] operaciones = cadenaOper.split("=");
          			if(operaciones[0].equals(tipoOperHom)) {
          				datosTransformacion = operaciones[1];
          			}
          		}
          	}
	      }
	      b.close();
	      salida = ruta + "," + tipoServ + "," + datosTransformacion;
		return salida;
	}
	
	private String homologarOperacion (String operacion) {
		String res="";
		if(operacion.equals("Consultar")) {
			res = "1";
		}else if(operacion.equals("Pagar")) {
			res = "2";
		}else if(operacion.equals("Compensar")) {
			res = "3";
		}
	return res;
	}
	
}
