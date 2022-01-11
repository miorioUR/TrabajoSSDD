import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;

//https://github.com/MoshDev/java-google-translate-text-to-speech

public class HiloUsuarioLeer extends Thread{

	DataInputStream teclado = new DataInputStream(System.in);
	Socket s;
	String nombre;
	String idioma;
	
	public HiloUsuarioLeer(Socket soc, String nom,String id) { //En el constructor se ponen nombre e idioma
			this.s = soc;
			this.nombre = nom;
			this.idioma = id;
	}
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(this.s.getInputStream(),"UTF-8"));
			
			//Translator translate = Translator.getInstance();
			//quiero hacer referencia a java-google-text-to-speech-master/bin/com/gtranslate/Translator.class
			
			String recibido = br.readLine();
			
			while(recibido!=null) {	
				
				//String text = translate.translate(recibido, Language.ENGLISH, Language.PORTUGUESE); 
				//System.out.println(text);
				
				System.out.println(recibido); //Se esta ejecutando 2 veces, pero no he creado 2 hiloleer?
				
				recibido=br.readLine();
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public Object getIdioma(String s) { //Quiero que en vez de string me devuelva Language.IDIOMA
		switch(s) {
		case "EN":
			return "Language.ENGLISH";
			
			//etc añadir añadir
		
		}
		return "Language.ENGLISH";
	}
}
