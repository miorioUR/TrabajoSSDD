import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class HiloUsuarioLeer extends Thread{

	DataInputStream teclado = new DataInputStream(System.in);
	Socket s;
	String nombre;
	
	public HiloUsuarioLeer(Socket soc, String nom) { //En el constructor se ponen nombre e idioma
			this.s = soc;
			this.nombre = nom;
	}
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(this.s.getInputStream(),"UTF-8"));
						
			String recibido = br.readLine();
			
			while(recibido!=null) {
				System.out.println(recibido);	
				
				recibido=br.readLine();
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
}
