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
	String edad;
	Thread HiloLeer = null;
	DataInputStream teclado = new DataInputStream(System.in);

	public HiloUsuarioEscribir(Socket soc,CountDownLatch c) { // En el constructor se ponen nombre e idioma
		try {
			this.s = soc;
			this.cdl=c;
			System.out.println("Inserte su nombre");
			this.nombre = teclado.readLine();
			
			System.out.println("Inserte su edad ");
			this.edad = teclado.readLine();
			
			System.out.println("***************************");
			System.out.println("BIENVENIDO A NUEVO WHATSAPP");
			System.out.println("***************************");
			
			HiloLeer = new HiloUsuarioLeer(s,nombre,edad,cdl);
			HiloLeer.start();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {

		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(this.s.getOutputStream(), "UTF-8"));
			String enviado = teclado.readLine();

			while(!enviado.equals(null) && cdl.getCount()==2) {
				bw.write(nombre + "(" + edad + "): " + enviado + "\r\n");
				bw.flush();
				
				
				enviado=teclado.readLine();
			}
			//bw.write((String) null);bw.flush(); --> solo hace el mensaje de error mas pequeño
			//this.s.getOutputStream().close();  /---> close ocurre en chatclient PERO DA MENSAJE DE ERROR
			cdl.countDown();
			this.interrupt();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}