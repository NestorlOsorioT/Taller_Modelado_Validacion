package com.javeriana.edu.co.constantes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.transform.stream.StreamSource;

public class Constantes {

	public static String reemplazarCaracteres(String formatxml) {
		formatxml = formatxml.replaceAll("S:", "");
		formatxml = formatxml.replaceAll("<ResultadoConsulta xmlns=\"http://www.servicios.co/pagos/schemas\">",
				"<ResultadoConsulta>");
		formatxml = formatxml.replaceAll("<Resultado xmlns=\"http://www.servicios.co/pagos/schemas\">", "<Resultado>");

		return formatxml;
	}

	public static StreamSource convertirToXML(String response) {
		StreamSource input = null;
		try {
			String rutaArchivo = "c:/tmp/archivoEntrada.xml";
			File archivo = new File(rutaArchivo);
			archivo.delete();
			input = new StreamSource(archivo);
			BufferedWriter bw;
			bw = new BufferedWriter(new FileWriter(archivo));
			bw.write(response.toString());
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return input;
	}

	public static String retornarOperacion(String tipo) {
		if (tipo.equals("1")) {
			return "CONSULTA";
		} else if (tipo.equals("2")) {
			return "PAGAR";
		} else {
			return "COMPENSAR";
		}

	}

	public static String retornarNombreMetodo(String variable) {
		BufferedReader br = null;
		FileReader fr = null;
		try {

			fr = new FileReader("C:\\modelado_validacion\\xml\\lecturaArchivoMetodo");
			br = new BufferedReader(fr);
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				String value[]=sCurrentLine.split(",");
				if(variable.equals(value[0])) {
					return value[1];
				}
			}
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();

			}

		}
		return "compensar";
	}


}
