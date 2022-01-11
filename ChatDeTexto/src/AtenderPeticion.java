import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

public class AtenderPeticion implements Runnable {

	List<Socket> users;
	Socket s;

	public AtenderPeticion(Socket s, List<Socket> u) {
		this.users = u;
		this.s = s;
	}

	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(this.s.getInputStream(), "UTF-8"));
			String mensaje = br.readLine();

			while (!mensaje.split(":")[1].equals(" /disconnect")) {
				for (int i = 0; i < users.size(); i++) {
					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(users.get(i).getOutputStream(), "UTF-8"));
					if(!users.get(i).equals(s)) {
						bw.write(mensaje + "\r\n");
						bw.flush();
					}
				}
				mensaje = br.readLine();
			}
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream(), "UTF-8"));
					bw.write("Te has desconectado. Pulse enter para parar la conexión" + "\r\n");
					bw.flush();
			
			users.remove(s);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}