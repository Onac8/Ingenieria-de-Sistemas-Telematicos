package urjc.ist.playlist;

import java.util.ArrayList;
import urjc.ist.playlist.Cancion;
import urjc.ist.playlist.Cancion.Codecs;

public class Album {
	private String titulo;
	private String autor;
	private String grupo;
	private int duracionTotal;
	private int numCanciones;
	private ArrayList<Cancion> trackList; //array dinamico de canciones

	public Album() {
		titulo = "";
		autor = "";
		grupo = "";
		duracionTotal = 0;
		numCanciones = 0;
		trackList = null;
	}
	
	public Album(String titulo, String autor, String grupo) {
		this.titulo = titulo;
		this.autor = autor;
		this.grupo = grupo;
		duracionTotal = 0;
		numCanciones = 0;
		trackList = new ArrayList<Cancion>();
	}
	
	
//GETTERS Y SETTERS-----------------------------
	public String getTitulo(){
		return titulo;
	}
	
	public void setTitulo(String titulo){
		this.titulo = titulo;
	}
	
	public String getAutor() {
		return autor;
	}
	
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	public String getGrupo() {
		return grupo;
	}
	
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	
	public int getDuracionTotal() {
		return duracionTotal;
	}
	
	public void setDuracionTotal(int duracionTotal) {
		this.duracionTotal = duracionTotal;
	}
	
	
	public int getNumCanciones() {
		return numCanciones;
	}
	
	public void setNumCanciones(int numCanciones) {
		this.numCanciones = numCanciones;
	}
	
	public ArrayList<Cancion> getTrackList() { //lista de canciones en album
		return trackList;
	}

	public Cancion getTrack(int posicion) { //devuelve cancion dada posicion
		/**
		 * EXCEPCIONES
		 * -----------
		 * try{
		 *    sentencias que pueden dar la excepcion
		 * } catch { (Tipo de excepcion X) }
		 * sentencias despues de excepcion
		 */
		return trackList.get(posicion); //arraylist tiene funciones como get predefinidas
	}
	
	
	
//ADD------------------------------------------------------------
	public void addTrack(Cancion unaCancion) { //inserta al final, actualiza hora
		trackList.add(unaCancion);
		duracionTotal += unaCancion.getDuracion();
		numCanciones++;
	}
	
	public void addTrack(int posicion, Cancion unaCancion) { //inserta en pos(debe ser !=null); act hora
		try{ //tratamos el error de querer insertar en una posicion con valor nulo
			trackList.add(posicion, unaCancion);
			duracionTotal += unaCancion.getDuracion();
			numCanciones++;
		}catch (IndexOutOfBoundsException excepcion){
			System.out.println("ERROR: Se ha intentado insertar una canción en una posicion con valor nulo. No insertamos.");
		}
	}
	
//DELETE-------------------------------------------------------------
	public void deleteLastTrack() { //borra la ultima
		if (numCanciones != 0){
			Cancion track; //aux
			track = getTrack(trackList.size()-1);
			duracionTotal -= track.getDuracion();
			numCanciones--;
			trackList.remove(trackList.size() - 1);
		}else{
			System.out.println("ERROR: No podemos borrar. Album vacio");
		}
	}
	
	public void deleteTrack(int posicion) { //borra la dada
		try{ //tratamos el error de querer borrar en una posicion con valor nulo
			Cancion track; //aux
			track = getTrack(posicion);
			
			trackList.remove(posicion); //tratar exception
			
			duracionTotal -= track.getDuracion();
			numCanciones--;
		}catch (IndexOutOfBoundsException excepcion){
			System.out.println("ERROR: Se ha intentado borrar una canción en una posicion con valor nulo. No borramos.");
		}
	}
	
	public void clearAlbum() {
		trackList.clear();
		duracionTotal= 0;
		numCanciones= 0;
	}
	

//IMPRIMIR-----------------------------------------------------------------------
	
	
	public String time (int dur){ //PASAMOS A HORAS,MIN,SEG
		
		int hours = dur / 3600;
		int minutes = (dur % 3600) / 60;
		int seconds = dur % 60;

		String timeString;
		if (hours > 0) {
			timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
		} else {
			timeString = String.format("%02d:%02d", minutes, seconds);
		}
		return timeString;
	}
	
	
	public String toString(){
			
		String out = String.join("\n", "----------",
					"Título: " + titulo,
					"Autor: " + autor,
					"Grupo: " + grupo,
					"Duración: " + time(duracionTotal),
					"Num canciones: " + numCanciones,
					"----------",
					"\n",
					"CANCIONES: ",
					"----------",
					"----------");
		
		Cancion aux;
		int x= 0;
		while (x <= trackList.size()-1){
			aux= getTrack(x);
			out += String.join("\n", "----------",
					"Título: " + aux.getTitulo(),
					"Autor: " + aux.getAutor(),
					"Duración: " + time(aux.getDuracion()),
					"Formato: " + (aux.getFormato() == Codecs.MP3 ? "MP3" : "FLAC"), 
					"----------");
			x++;
		}
		return out;
	}
	

//HASH--------------------------------------------------------
	
	//equals y hash
	

	
	public static void main(String[] args) {
		Album album1 = new Album("GAIA II", "Universal Studios", "Mago de Oz");
		Cancion track1 = new Cancion("Fiesta pagana", "Mago de Oz",250, Codecs.MP3);
		Cancion track2 = new Cancion("Desde mi cielo", "Mago de Oz",200, Codecs.FLAC);
		Cancion track3 = new Cancion("Atlantida", "Mago de Oz",300, Codecs.MP3);
		Cancion track4 = new Cancion("La voz dormida", "Mago de Oz",280, Codecs.MP3);
		//album1.deleteLastTrack(); IT WORKS!
		album1.addTrack(track1);
		album1.addTrack(track2);
		album1.addTrack(track3);
		album1.addTrack(9,track4);
		album1.addTrack(1,track4);
		album1.deleteTrack(2);
		album1.deleteTrack(5);
		album1.deleteLastTrack();
		//album1.clearAlbum(); FUNCIONA
		System.out.println(album1);
	}
}