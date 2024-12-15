package com.example.demo.utilidades;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.models.ApiError;

public class Utilidades {
	public static ResponseEntity<Object> generateResponse(HttpStatus status, String mensaje) {
		List<String> details = new ArrayList<String>();
		details.add(mensaje);

		try {

			ApiError err = new ApiError(new Date(), HttpStatus.BAD_REQUEST, "ERROR", details);

			return new ResponseEntity<Object>(err, status);

		} catch (Exception e) {
			ApiError err = new ApiError(new Date(), HttpStatus.BAD_REQUEST, "ERROR", details);

			return new ResponseEntity<Object>(err, status);
		}
	}

	public static ResponseEntity<Object> generateResponseTrue(HttpStatus status, String mensaje) {
		List<String> details = new ArrayList<String>();
		details.add("");

		try {

			ApiError err = new ApiError(new Date(), HttpStatus.CREATED, mensaje, details);

			return new ResponseEntity<Object>(err, status);

		} catch (Exception e) {
			ApiError err = new ApiError(new Date(), HttpStatus.CREATED, mensaje, details);

			return new ResponseEntity<Object>(err, status);
		}
	}

	private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
	private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
	private static final Pattern EDGESDHASHES = Pattern.compile("(^-|-$)");

	public static String getUrl(String nombre) {

		String nowwhitespace = WHITESPACE.matcher(nombre).replaceAll("-");
		String normalized = Normalizer.normalize(nowwhitespace, Normalizer.Form.NFD);
		String url = NONLATIN.matcher(normalized).replaceAll("");
		url = EDGESDHASHES.matcher(url).replaceAll("");
		return url.toLowerCase(Locale.ENGLISH);
	}
	
    public static String getRol(String nombre) {
        String nowwhitespace = WHITESPACE.matcher(nombre).replaceAll("_");
        String normalized = Normalizer.normalize(nowwhitespace, Normalizer.Form.NFD);
        String url = NONLATIN.matcher(normalized).replaceAll("");
        url = EDGESDHASHES.matcher(url).replaceAll("");
        String rol = "ROLE_" + url.toUpperCase(Locale.ENGLISH);
        return rol;
    }



	public static boolean validaImagen(String mime) {

		boolean retorno = false;
		switch (mime) 
		{
			case "image/jpeg": retorno = true; break;
			case "image/jpg": retorno = true; break;
			case "image/png": retorno = true; break;
			default: retorno = false; break;
		}

		return retorno;
	}
	
	public static String getExtension(String mime) {
		String retorno = "";
		switch (mime) 
		{
			case "image/jpeg": retorno = ".jpg"; break;
			case "image/jpg": retorno = ".jpg"; break;
			case "image/png": retorno = ".png"; break;
		}

		return retorno;
	}
}
