package com.javainuse.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.javeriana.edu.co.constantes.Constantes;
import com.javeriana.edu.co.constantes.MetodosEnum;
import com.javeriana.entidades.Factura;
import com.javeriana.entidades.Resultado;
import com.javeriana.entidades.RetornoResultado;

@RestController
@RequestMapping(value = "/despacharServicio/")
public class DespachadorController {

	@GetMapping(value = "/transformarServicio")
	public RetornoResultado transformarServicio(@RequestParam("leerServicios") String leerServicios,
			@RequestParam("idfactura") String idfactura, @RequestParam("valorfactura") Double valorfactura) {
		System.out.println("*********************LLEGA SERVICIO*****************");
		System.out.println("*********************FACTURA:["+idfactura+"] *****************");
		System.out.println("*********************VALOR FACTURA:["+valorfactura+"] *****************");
		System.out.println("*********************RUTA:[  "+leerServicios+"   ] *****************");
		
		RetornoResultado retornoResult = new RetornoResultado();
		String split[] = leerServicios.split(",");
		// http://localhost:9090/servicios/pagos/v1/payments|1|Sin xml|1|1

		String url = split[0];
		url=url.replace("@@@@@@", idfactura);
		System.out.println("url: "+url);
		int tiposervicio = Integer.parseInt(split[1]);
		String formatoxml = split[2];
		String nombreMetodoExterno = Constantes.retornarNombreMetodo(split[3]);
		String operacionReal = Constantes.retornarOperacion(split[3]);
		String metodo_consumir = split[4].equals("1") ? "GET"
				: split[4].equals("2") ? "POST" : split[4].equals("3") ? "DELETE" : "PUT";
		try {
			if (operacionReal.equals("CONSULTA")) {
				System.out.println("Realiza una consulta");
				Factura factura = retornarConsultaServicio(tiposervicio, metodo_consumir, idfactura, url, formatoxml,
						nombreMetodoExterno);
				retornoResult.setIdFactura(factura.getIdFactura());
				retornoResult.setValorFactura(factura.getValorFactura());
				retornoResult.setMensaje("");

			} else if (operacionReal.equals("PAGAR")) {
				System.out.println("Realiza un pago");
				Resultado resultado = retornarPagarServicio(tiposervicio, metodo_consumir, idfactura, url, formatoxml,
						nombreMetodoExterno, valorfactura);
				retornoResult.setIdFactura(resultado.getIdFactura());
				retornoResult.setValorFactura(0.0);
				retornoResult.setMensaje(resultado.getMensaje());
			} else if (operacionReal.equals("COMPENSAR")) {
				System.out.println("realiza una compensacion");
				Resultado resultado = retornarCompensarServicio(tiposervicio, metodo_consumir, idfactura, url,
						formatoxml, nombreMetodoExterno, valorfactura);
				retornoResult.setIdFactura(resultado.getIdFactura());
				retornoResult.setValorFactura(0.0);
				retornoResult.setMensaje(resultado.getMensaje());
			}
		} catch (Exception e) {

			retornoResult.setIdFactura(Integer.parseInt(idfactura));
			retornoResult.setMensaje("");
			retornoResult.setValorFactura(0.1);
			e.printStackTrace();
		}

		return retornoResult;

	}

	private Resultado retornarCompensarServicio(int tiposervicio, String metodo_consumir, String idfactura, String url,
			String formatoxml, String nombreMetodoExterno, Double valorfactura) {

		Resultado resultado = new Resultado();
		try {

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod(metodo_consumir);
			FileInputStream fis = null;
			String xmlFormat = null;
			StreamSource input = null;
			StreamSource xslcode = null;
			File archivo = null;
			byte[] data = null;
			StreamResult output = null;
			if (tiposervicio == MetodosEnum.REST.getNumeroMetodo()) {
				System.out.println("realiza una peticion por rest "+metodo_consumir);
				con.setRequestProperty("Content-Type", "application/xml; charset=utf-8");
				con.setRequestProperty("Accept", "application/xml");

				archivo = new File("./src/main/resources/com/xml/entradaxml_" + formatoxml);
				fis = new FileInputStream(archivo);
				data = new byte[(int) archivo.length()];
				fis.read(data);
				fis.close();
				String xml = new String(data, "UTF-8");
				xml = xml.replace("||||", "" + idfactura);
				xml = xml.replace("@@@", "" + valorfactura);
				con.setDoOutput(true);
				if (!metodo_consumir.equals("GET")) {
					DataOutputStream wr = new DataOutputStream(con.getOutputStream());
					wr.writeBytes(xml);
					wr.flush();
					wr.close();
				}
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine + "\n");
				}
				in.close();

				xmlFormat = Constantes.reemplazarCaracteres(response.toString());
				input = Constantes.convertirToXML(xmlFormat);
				xslcode = new StreamSource(new File(new File("").getAbsolutePath()
						+ "./src/main/resources/com/xslt/transformadaxml_" + formatoxml));
				String salidaArchivo = new File("").getAbsolutePath() + "./src/main/resources/com/xslt/salidaxml_"
						+ formatoxml;

				archivo = new File(salidaArchivo);
				archivo.delete();
				output = new StreamResult(archivo);

			} else if (tiposervicio == MetodosEnum.SOAP.getNumeroMetodo()) {
				System.out.println("realiza una peticion por soap "+metodo_consumir);
				con.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
				con.setRequestProperty("SOAPAction", nombreMetodoExterno);

				archivo = new File("./src/main/resources/com/xml/entradaxml_" + formatoxml);
				fis = new FileInputStream(archivo);
				data = new byte[(int) archivo.length()];
				fis.read(data);
				fis.close();
				String xml = new String(data, "UTF-8");
				xml = xml.replace("||||", "" + idfactura);
				xml = xml.replace("@@@", "" + valorfactura);
				con.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.writeBytes(xml);
				wr.flush();
				wr.close();
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine + "\n");
				}
				in.close();

				xmlFormat = Constantes.reemplazarCaracteres(response.toString());
				input = Constantes.convertirToXML(xmlFormat);
				xslcode = new StreamSource("./src/main/resources/com/xslt/transformadaxml_" + formatoxml);
				String salidaArchivo = "./src/main/resources/com/xslt/salidaxml_" + formatoxml;

				archivo = new File(salidaArchivo);
				archivo.delete();
				output = new StreamResult(archivo);
			}

			try {
				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer trans = tf.newTransformer(xslcode);
				trans.transform(input, output);

				fis = new FileInputStream(archivo);
				data = new byte[(int) archivo.length()];
				fis.read(data);
				fis.close();
				String archivoLeido = new String(data, "UTF-8");

				JSONObject xmlJSONObj = XML.toJSONObject(archivoLeido);
				String jsonPrettyPrintString = xmlJSONObj.toString();
				JsonParser parser = new JsonParser();
				JsonObject rootObj = parser.parse(jsonPrettyPrintString).getAsJsonObject();
				JsonObject paymentObjw = rootObj.getAsJsonObject();

				String facturas = paymentObjw.get("facturas").toString();
				rootObj = parser.parse(facturas).getAsJsonObject();
				paymentObjw = rootObj.getAsJsonObject();
				facturas = paymentObjw.get("factura").toString();

				Gson gson = new Gson();
				resultado = gson.fromJson(facturas, Resultado.class);
				System.out.println("response gson java rest " + facturas);

			} catch (Exception e) {
				System.out.println(e.getMessage());
				resultado.setIdFactura(Integer.parseInt(idfactura));
				resultado.setMensaje(".");
			}

		} catch (Exception e) {
			resultado.setIdFactura(Integer.parseInt(idfactura));
			resultado.setMensaje(".");
			e.printStackTrace();
		}

		return resultado;
	}

	private Resultado retornarPagarServicio(int tiposervicio, String metodo_consumir, String idfactura, String url,
			String formatoxml, String nombreMetodoExterno, Double valorfactura) {
		Resultado resultado = new Resultado();
		try {

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod(metodo_consumir);
			FileInputStream fis = null;
			String xmlFormat = null;
			StreamSource input = null;
			StreamSource xslcode = null;
			File archivo = null;
			byte[] data = null;
			StreamResult output = null;
			if (tiposervicio == MetodosEnum.REST.getNumeroMetodo()) {
				System.out.println("realiza una peticion por rest "+metodo_consumir);
				con.setRequestProperty("Content-Type", "application/xml; charset=utf-8");
				con.setRequestProperty("Accept", "application/xml");

				archivo = new File("./src/main/resources/com/xml/entradaxml_" + formatoxml);
				fis = new FileInputStream(archivo);
				data = new byte[(int) archivo.length()];
				fis.read(data);
				fis.close();
				String xml = new String(data, "UTF-8");
				xml = xml.replace("||||", "" + idfactura);
				xml = xml.replace("@@@", "" + valorfactura);
				con.setDoOutput(true);
				if (!metodo_consumir.equals("GET")) {
					DataOutputStream wr = new DataOutputStream(con.getOutputStream());
					wr.writeBytes(xml);
					wr.flush();
					wr.close();
				}
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine + "\n");
				}
				in.close();

				xmlFormat = Constantes.reemplazarCaracteres(response.toString());
				input = Constantes.convertirToXML(xmlFormat);
				xslcode = new StreamSource(new File(new File("").getAbsolutePath()
						+ "./src/main/resources/com/xslt/transformadaxml_" + formatoxml));
				String salidaArchivo = new File("").getAbsolutePath() + "./src/main/resources/com/xslt/salidaxml_"
						+ formatoxml;

				archivo = new File(salidaArchivo);
				archivo.delete();
				output = new StreamResult(archivo);

			} else if (tiposervicio == MetodosEnum.SOAP.getNumeroMetodo()) {
				System.out.println("realiza una peticion por soap "+metodo_consumir);
				con.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
				con.setRequestProperty("SOAPAction", nombreMetodoExterno);

				archivo = new File("./src/main/resources/com/xml/entradaxml_" + formatoxml);
				fis = new FileInputStream(archivo);
				data = new byte[(int) archivo.length()];
				fis.read(data);
				fis.close();
				String xml = new String(data, "UTF-8");
				xml = xml.replace("||||", "" + idfactura);
				xml = xml.replace("@@@", "" + valorfactura);
				con.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.writeBytes(xml);
				wr.flush();
				wr.close();
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine + "\n");
				}
				in.close();

				xmlFormat = Constantes.reemplazarCaracteres(response.toString());
				input = Constantes.convertirToXML(xmlFormat);
				xslcode = new StreamSource("./src/main/resources/com/xslt/transformadaxml_" + formatoxml);
				String salidaArchivo = "./src/main/resources/com/xslt/salidaxml_" + formatoxml;

				archivo = new File(salidaArchivo);
				archivo.delete();
				output = new StreamResult(archivo);
			}

			try {
				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer trans = tf.newTransformer(xslcode);
				trans.transform(input, output);

				fis = new FileInputStream(archivo);
				data = new byte[(int) archivo.length()];
				fis.read(data);
				fis.close();
				String archivoLeido = new String(data, "UTF-8");

				JSONObject xmlJSONObj = XML.toJSONObject(archivoLeido);
				String jsonPrettyPrintString = xmlJSONObj.toString();
				JsonParser parser = new JsonParser();
				JsonObject rootObj = parser.parse(jsonPrettyPrintString).getAsJsonObject();
				JsonObject paymentObjw = rootObj.getAsJsonObject();

				String facturas = paymentObjw.get("facturas").toString();
				rootObj = parser.parse(facturas).getAsJsonObject();
				paymentObjw = rootObj.getAsJsonObject();
				facturas = paymentObjw.get("factura").toString();

				Gson gson = new Gson();
				resultado = gson.fromJson(facturas, Resultado.class);
				System.out.println("response gson java rest " + facturas);

			} catch (Exception e) {
				System.out.println(e.getMessage());
				resultado.setIdFactura(Integer.parseInt(idfactura));
				resultado.setMensaje(".");
			}

		} catch (Exception e) {
			resultado.setIdFactura(Integer.parseInt(idfactura));
			resultado.setMensaje(".");
			e.printStackTrace();
		}

		return resultado;

	}

	private Factura retornarConsultaServicio(Integer tiposervicio, String metodo_consumir, String idfactura, String url,
			String formatoxml, String nombreAconsumir) throws IOException {
		Factura factura = new Factura();
		try {

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod(metodo_consumir);
			FileInputStream fis = null;
			String xmlFormat = null;
			StreamSource input = null;
			StreamSource xslcode = null;
			File archivo = null;
			byte[] data = null;
			StreamResult output = null;
			if (tiposervicio == MetodosEnum.REST.getNumeroMetodo()) {
				System.out.println("realiza una peticion por rest "+metodo_consumir);
				con.setRequestProperty("Content-Type", "application/xml; charset=utf-8");
				con.setRequestProperty("Accept", "application/xml");

				archivo = new File("./src/main/resources/com/xml/entradaxml_" + formatoxml);
				fis = new FileInputStream(archivo);
				data = new byte[(int) archivo.length()];
				fis.read(data);
				fis.close();
				String xml = new String(data, "UTF-8");
				xml = xml.replace("||||", "" + idfactura);
				con.setDoOutput(true);
				if (!metodo_consumir.equals("GET")) {
					DataOutputStream wr = new DataOutputStream(con.getOutputStream());
					wr.writeBytes(xml);
					wr.flush();
					wr.close();
				}
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine + "\n");
				}
				in.close();

				xmlFormat = Constantes.reemplazarCaracteres(response.toString());
				input = Constantes.convertirToXML(xmlFormat);
				xslcode = new StreamSource(new File(new File("").getAbsolutePath()
						+ "./src/main/resources/com/xslt/transformadaxml_" + formatoxml));
				String salidaArchivo = new File("").getAbsolutePath() + "./src/main/resources/com/xslt/salidaxml_"
						+ formatoxml;

				archivo = new File(salidaArchivo);
				archivo.delete();
				output = new StreamResult(archivo);

			} else if (tiposervicio == MetodosEnum.SOAP.getNumeroMetodo()) {
				System.out.println("realiza una peticion por soap "+metodo_consumir);
				con.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
				con.setRequestProperty("SOAPAction", nombreAconsumir);

				archivo = new File("./src/main/resources/com/xml/entradaxml_" + formatoxml);
				fis = new FileInputStream(archivo);
				data = new byte[(int) archivo.length()];
				fis.read(data);
				fis.close();
				String xml = new String(data, "UTF-8");
				xml = xml.replace("||||", "" + idfactura);
				con.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.writeBytes(xml);
				wr.flush();
				wr.close();
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine + "\n");
				}
				in.close();

				xmlFormat = Constantes.reemplazarCaracteres(response.toString());
				input = Constantes.convertirToXML(xmlFormat);
				xslcode = new StreamSource("./src/main/resources/com/xslt/transformadaxml_" + formatoxml);
				String salidaArchivo = "./src/main/resources/com/xslt/salidaxml_" + formatoxml;

				archivo = new File(salidaArchivo);
				archivo.delete();
				output = new StreamResult(archivo);
			}

			try {
				TransformerFactory tf = TransformerFactory.newInstance();
				Transformer trans = tf.newTransformer(xslcode);
				trans.transform(input, output);

				fis = new FileInputStream(archivo);
				data = new byte[(int) archivo.length()];
				fis.read(data);
				fis.close();
				String archivoLeido = new String(data, "UTF-8");

				JSONObject xmlJSONObj = XML.toJSONObject(archivoLeido);
				String jsonPrettyPrintString = xmlJSONObj.toString();
				JsonParser parser = new JsonParser();
				JsonObject rootObj = parser.parse(jsonPrettyPrintString).getAsJsonObject();
				JsonObject paymentObjw = rootObj.getAsJsonObject();

				String facturas = paymentObjw.get("facturas").toString();
				rootObj = parser.parse(facturas).getAsJsonObject();
				paymentObjw = rootObj.getAsJsonObject();
				facturas = paymentObjw.get("factura").toString();

				Gson gson = new Gson();
				factura = gson.fromJson(facturas, Factura.class);
				System.out.println("response gson java rest " + facturas);

			} catch (Exception e) {
				System.out.println(e.getMessage());
				factura.setIdFactura(Integer.parseInt(idfactura));
				factura.setValorFactura(11.11);
				e.printStackTrace();
			}

		} catch (Exception e) {
			factura.setIdFactura(Integer.parseInt(idfactura));
			factura.setValorFactura(11.11);
			e.printStackTrace();
		}
//		if (tiposervicio == MetodosEnum.SOAP.getNumeroMetodo()) {
//			try {
//				URL obj = new URL(url);
//				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//				con.setRequestMethod("GET");
//				con.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
//				con.setRequestProperty("SOAPAction", nombreAconsumir);
//				File archivo = new File(formatoxml);
//				FileInputStream fis = new FileInputStream(archivo);
//				byte[] data = new byte[(int) archivo.length()];
//				fis.read(data);
//				fis.close();
//				String xml = new String(data, "UTF-8");
//				xml = xml.replace("||||", "" + idfactura);
//				con.setDoOutput(true);
//				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
//				wr.writeBytes(xml);
//				wr.flush();
//				wr.close();
//				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//				String inputLine;
//				StringBuffer response = new StringBuffer();
//				while ((inputLine = in.readLine()) != null) {
//					response.append(inputLine + "\n");
//				}
//				in.close();
//
//				String xmlFormat = Constantes.reemplazarCaracteres(response.toString());
//
//				StreamSource input = Constantes.convertirToXML(xmlFormat);
//				StreamSource xslcode = new StreamSource(new File(
//						new File("").getAbsolutePath() + "/src/main/resources/com/xslt/facturaTransformadora.xml"));
////				String salidaArchivo = "C:/tmp/salidaTransformada.xml";
//				String salidaArchivo = new File("").getAbsolutePath()
//						+ "/src/main/resources/com/xslt/salidaTransformada.xml";
//				archivo = new File(salidaArchivo);
//				archivo.delete();
//				StreamResult output = new StreamResult(archivo);
//
//				try {
//					TransformerFactory tf = TransformerFactory.newInstance();
//					Transformer trans = tf.newTransformer(xslcode);
//					trans.transform(input, output);
//
//					fis = new FileInputStream(archivo);
//					data = new byte[(int) archivo.length()];
//					fis.read(data);
//					fis.close();
//					String archivoLeido = new String(data, "UTF-8");
//
//					JSONObject xmlJSONObj = XML.toJSONObject(archivoLeido);
//					String jsonPrettyPrintString = xmlJSONObj.toString();
//					JsonParser parser = new JsonParser();
//					JsonObject rootObj = parser.parse(jsonPrettyPrintString).getAsJsonObject();
//					JsonObject paymentObjw = rootObj.getAsJsonObject();
//
//					String facturas = paymentObjw.get("facturas").toString();
//					rootObj = parser.parse(facturas).getAsJsonObject();
//					paymentObjw = rootObj.getAsJsonObject();
//					facturas = paymentObjw.get("factura").toString();
//
//					Gson gson = new Gson();
//					factura = gson.fromJson(facturas, Factura.class);
//					System.out.println("response gson java rest " + facturas);
//
//				} catch (Exception e) {
//					System.out.println(e.getMessage());
//					factura.setIdFactura(Integer.parseInt(idfactura));
//					factura.setValorFactura(11.0000);
//				}
//			} catch (Exception e) {
//				factura.setIdFactura(Integer.parseInt(idfactura));
//				factura.setValorFactura(11.0000);
//				e.printStackTrace();
//			}
//
//		}
		return factura;

	}

}
