package urjc.ist.playlist;

import java.util.ArrayList;

import urjc.ist.playlist.Cancion.Codecs;

public class PlayList {

	private String nombre;
	private int numAlbumes;
	private int numCanciones;
	private int duracionTotal;
	private ArrayList<Album> albumList;
	
	public PlayList() {
		nombre = "";
		numAlbumes = 0;
		numCanciones = 0;
		duracionTotal = 0;
		albumList = null;
	}
	
	public PlayList(String nombre) {
		this.nombre = nombre;
		numAlbumes = 0;
		numCanciones = 0;
		duracionTotal = 0;
		albumList = new ArrayList<Album>();
	}
	
	
//GETTERS Y SETTERS------------------------------------
	public String getNombre(){
		return nombre;
	}
	
	public void setNombre(String nombre){
		this.nombre = nombre;
	}
	
	public int getNumAlbumes() {
		return numAlbumes;
	}
	
	public void setNumAlbumes(int numAlbumes) {
		this.numAlbumes = numAlbumes;
	}
	
	public int getNumCanciones() {
		return numCanciones;
	}
	
	public void setNumCanciones(int numCanciones) {
		this.numCanciones = numCanciones;
	}
	
	public int getDuracionTotal() {
		return duracionTotal;
	}
	
	public void setDuracionTotal(int duracionTotal) {
		this.duracionTotal = duracionTotal;
	}
	
	public ArrayList<Album> getAlbumList() { //lista de canciones en album
		return albumList;
	}
	
	
	public Album getAlbum(int posicion) {
		return albumList.get(posicion);
	}
	
	
//ADD----------------------------------------------------------
	public void addAlbum(Album unAlbum) {
		albumList.add(unAlbum);
		duracionTotal += unAlbum.getDuracionTotal(); //actualizamos
		numAlbumes++;
		numCanciones += unAlbum.getNumCanciones();
	}
	
	
	public void addAlbum(int posicion, Album unAlbum) {
		try{
		albumList.add(posicion, unAlbum);
		duracionTotal += unAlbum.getDuracionTotal();
		numAlbumes++;
		numCanciones += unAlbum.getNumCanciones();
		}catch(IndexOutOfBoundsException excepcion){
			System.out.println("ERROR: Se ha intentado insertar un album en una posicion con valor nulo. No insertamos.");
		}
	}
	
	
//DELETE-------------------------------------------------------------
	public void removeAlbum() { //borra la ultima
		if (numAlbumes != 0){
		Album aux;
		aux = getAlbum(albumList.size()-1);
		
		albumList.remove(albumList.size() - 1);
		
		duracionTotal -= aux.getDuracionTotal();
		numAlbumes--;
		numCanciones -= aux.getNumCanciones();
		}else{
			System.out.println("ERROR: No podemos borrar. Playlist vacia");
		}
	}
	
	public void removeAlbum(int posicion) {
		try{ //tratamos el error de querer borrar en una posicion con valor nulo
			Album aux;
			aux = getAlbum(posicion);
			
			albumList.remove(posicion);
			
			numCanciones -= aux.getNumCanciones();
			duracionTotal -= aux.getDuracionTotal();
			numAlbumes--;
		}catch (IndexOutOfBoundsException excepcion){
			System.out.println("ERROR: Se ha intentado borrar un album en una posicion con valor nulo. No borramos.");
		}
	}
	
	public void clear() {
		albumList.clear();
		numAlbumes=0;
		numCanciones=0;
		duracionTotal = 0;
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
			
@ Override
	public String toString(){
		
		String out = String.join("\n", 
					"-------------",
					nombre,
					"-------------",
					"Albumes: " + numAlbumes,
					"Canciones: " + numCanciones,
					"Duración total: " + time(duracionTotal),
					"-------------");		
		Album aux;
		Cancion aux2;
		int x= 0;
		while (x <= albumList.size()-1){ //vamos recorriendo album a album
			aux= getAlbum(x);
				out += String.join("\n", "\n\n------------------",	//ALBUM
						"------------------",
						"Album titulo: " + aux.getTitulo(),
						"Autor: " + aux.getAutor(),
						"Grupo: " + aux.getGrupo(),
						"Duración: " + time(aux.getDuracionTotal()),
						"Canciones: " + (aux.getNumCanciones()),
						"----------",
						"",
						"---Canciones---");
				
				int y= 0;
				while (y <= aux.getNumCanciones()-1){ //vamos recorriendo cancion a cancion
					aux2= aux.getTrack(y);
					out += String.join("\n", "",	//CANCIONES
							"Título: " + aux2.getTitulo(),
							"Autor: " + aux2.getAutor(),
							"Duración: " + time(aux2.getDuracion()),
							"Formato: " + (aux2.getFormato() == Codecs.MP3 ? "MP3" : "FLAC"),
							"----------");
					y++;
				}
				x++;
		}
		return out;
	}
	
	
//MAIN--------------------------------------------------------------	
	public static void main(String[] args) {
		PlayList play = new PlayList ("CLASICOS");
		Album album1 = new Album("GAIA II", "Universal Studios", "Mago de Oz");
		Album album2 = new Album("GAIA I", "Universal Studios", "Mago de Oz");
		Album album3 = new Album("Finisterra", "Universal Studios", "Mago de Oz");
		Cancion track1 = new Cancion("Fiesta pagana", "Mago de Oz",250, Codecs.MP3);
		Cancion track2 = new Cancion("Desde mi cielo", "Mago de Oz",200, Codecs.FLAC);
		Cancion track3 = new Cancion("Atlantida", "Mago de Oz",300, Codecs.MP3);
		Cancion track4 = new Cancion("La voz dormida", "Mago de Oz",280, Codecs.MP3);
		Cancion track5 = new Cancion("Molinos de viento", "Mago de Oz",250, Codecs.MP3);
		Cancion track6 = new Cancion("La rosa de los vientos", "Mago de Oz",200, Codecs.FLAC);
		
		//album1.deleteLastTrack(); //IT WORKS!
		
		album1.addTrack(track1);
		album1.addTrack(track2);
				
		album2.addTrack(track3);
		album2.addTrack(track4);
				
		album3.addTrack(track5);
		album3.addTrack(track6);
			
		
		//album1.deleteTrack(1); //FUNCIONA
		//album1.deleteTrack(5); //FUNCIONA
		//album1.deleteLastTrack(); //FUNCIONA
		
		
		play.addAlbum(album1);
		play.addAlbum(album2);
		play.addAlbum(album3);
		
		//play.removeAlbum(); //FUNCIONA
		
		//play.clear(); //FUNCIONA
		
		System.out.println(play);
	}
}
