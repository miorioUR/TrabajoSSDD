import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class HiloUsuarioEscribir extends Thread {

	CountDownLatch cdl;
	Socket s;
	String nombre;
	String idioma;
	Thread HiloLeer = null;
	DataInputStream teclado = new DataInputStream(System.in);

	public HiloUsuarioEscribir(Socket soc,CountDownLatch c) { // En el constructor se ponen nombre e idioma
		try {
			this.s = soc;
			System.out.println("Inserte su nombre");
			this.nombre = teclado.readLine();
			
			System.out.println("Inserte su idioma (EN=english,ES=español)");
			this.idioma = teclado.readLine();
			
			HiloLeer = new HiloUsuarioLeer(s,nombre,idioma);
			HiloLeer.start();
			
			this.cdl=c;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {

		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(this.s.getOutputStream(), "UTF-8"));
			String enviado = teclado.readLine();

			while(!enviado.equalsIgnoreCase("/disconnect")) {
				bw.write(nombre + "(" + idioma + "): " + enviado + "\r\n");
				bw.flush();
				
				enviado=teclado.readLine();
			}
			//bw.write((String) null);bw.flush(); --> solo hace el mensaje de error mas pequeño
			//this.s.getOutputStream().close();  /---> close ocurre en chatclient PERO DA MENSAJE DE ERROR
			cdl.countDown();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}