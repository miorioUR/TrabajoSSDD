import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

//https://github.com/MoshDev/java-google-translate-text-to-speech

public class HiloUsuarioLeer extends Thread{

	DataInputStream teclado = new DataInputStream(System.in);
	CountDownLatch cdl;
	Socket s;
	String nombre;
	String edad;
	
	public HiloUsuarioLeer(Socket soc, String nom,String id,CountDownLatch c) { //En el constructor se ponen nombre e idioma
			this.s = soc;
			this.nombre = nom;
			this.edad = id;
			this.cdl=c;
	}
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(this.s.getInputStream(),"UTF-8"));
			
			String recibido = "Usa esta consola para escribir";
			boolean abierto = true;
			while(recibido!=null && abierto) {	
				
				System.out.println(recibido); 
				
				if(recibido.equals("Te has desconectado. Pulse enter para parar la conexión")) {
					abierto=false;
				}
				else {
					recibido=br.readLine();
				}
			}
			cdl.countDown();
			this.interrupt();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
}
