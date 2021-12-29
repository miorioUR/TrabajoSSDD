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
	Thread HiloLeer = null;
	DataInputStream teclado = new DataInputStream(System.in);

	public HiloUsuarioEscribir(Socket soc,CountDownLatch c) { // En el constructor se ponen nombre e idioma
		try {
			this.s = soc;
			System.out.println("Inserte su nombre");
			this.nombre = teclado.readLine();
			
			HiloLeer = new HiloUsuarioLeer(s,nombre);
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
				bw.write(enviado + "\r\n");
				bw.flush();
				
				enviado=teclado.readLine();
			}
			cdl.countDown();
			//s.close();  ---> ocurre en chatclient
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


}
