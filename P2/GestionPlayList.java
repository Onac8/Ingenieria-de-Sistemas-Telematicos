package urjc.ist.playlist;

import java.util.Scanner;

import urjc.ist.playlist.Cancion.Codecs;

//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class GestionPlayList {
	
	private List<PlayList> list;
	
	
	public GestionPlayList() {
		list = new ArrayList<PlayList>();
	}
	
	
//ADD----------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------
	//AÑADIMOS NUEVA PLAYLIST------------------
	private void addPlayList(Scanner entrada){
		System.out.print("Ingrese nombre de Playlist: ");
		String name = entrada.next();
			
		PlayList playlist = new PlayList (name);
		list.add(playlist);
		System.out.println("\n" + "La playlist '" + name + "' se ha insertado en la lista.\n");
	}
	
	//AÑADE ALBUM A LA PLAYLIST DADA----------------------------------------------------
	private void addAlbum (Scanner entrada, int x){ //x es el index de la playlist dada
		System.out.print("Ingrese titulo, autor y grupo del nuevo Album: ");
		String name1 = entrada.next();
		String name2 = entrada.next();
		String name3 = entrada.next();
				
		Album album1 = new Album (name1,name2,name3);
		list.get(x).addAlbum(album1); //añadimos album en playlist dada, dentro de list
		System.out.println("El album '" + name1 + "' se ha insertado en la lista.\n");
	}
	
	//AÑADE CANCION AL ALBUM DADO-------------------------------------
	private void addCancion(Scanner entrada, int index, int indexAlb){
		System.out.print("Ingrese titulo, autor, duracion (int) y formato de la nueva Cancion: ");
		Codecs cod = null;
		
		String name1 = entrada.next();
		String name2 = entrada.next();
		String name3 = entrada.next();
		String name4 = entrada.next();	
		
		if (name4.equals("MP3")){
			cod =  Codecs.MP3;
		}else if (name4.equals("FLAC")){
			cod = Codecs.FLAC;
		}
		
		if (name3.equals("0")){
			System.out.println("Datos mal introducidos");
		}else{
			try{
				int duracion = Integer.valueOf(name3); //casting de argumento 3
				Cancion cancion1 = new Cancion (name1,name2,duracion,cod);
				list.get(index).getAlbum(indexAlb).addTrack(cancion1); //añadimos album en playlist dada, dentro de list
				list.get(index).setNumCanciones(list.get(index).getAlbum(indexAlb).getNumCanciones());
				list.get(index).setDuracionTotal(list.get(index).getAlbum(indexAlb).getDuracionTotal());
				System.out.println("La canción '" + name1 + "' se ha insertado en la lista.\n");
			}catch(NumberFormatException e){
				System.out.println("Introduzca bien la duración (tipo int). NO AÑADIMOS!");
			}
		}	
	}	
	
	
//DELETE------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------
	//BORRAMOS PLAYLIST----------------------------
	private void deletePlayList(Scanner entrada){
		//String[] names = new String[list.size()];
		String name;
		int x;
		
		
		System.out.print("Playlists existentes: ");
		//names = namesPlayList();
		//System.out.println(Arrays.toString(names)); //imprimos!!!
		System.out.println(namesPlayList()); //imprimimos
		System.out.print("Ingrese nombre a borrar: ");
		name = entrada.next();
			
		x = hasPlayList(name);
		if(x != -1){ //= encontrado
			list.remove(x); //borramos playlist
		}else{ // o no encontrado o ""
			System.out.println("Introduzca un nombre válido o coincidente\n");
		}
	}
	
	//BORRAMOS ALBUM----------------------------------------
	private void deleteAlbum(Scanner entrada, int index){
		String names;
		String alb;
		int x;
			
		System.out.println("Lista de albumes: ");
		names = namesAlbum(index);
		System.out.println(names); 
		System.out.print("Ingrese el album a borrar (titulo): ");
		alb = entrada.next();
				
		x = hasAlbum(alb,index);
		if(x != -1){ //= encontrado
			list.get(index).removeAlbum(x); //borramos album dado x
				
		}else{ // o no encontrado o ""
			System.out.println("Introduzca un nombre válido o coincidente\n");
		}
	}
	
	//BORRAMOS CANCION-------------------------------------------------------
	private void deleteCancion (Scanner entrada, int index, int indexAlb){
		String names;
		String song;
		int x;
		
		System.out.println("Lista de canciones: ");
		names = namesCancion(index,indexAlb);
		System.out.println(names);
		System.out.println("Ingrese la cancion a borrar (titulo): ");
		song = entrada.next();
		
		x = hasCancion(song,index,indexAlb);
		if (x != -1){ //encontrado
			list.get(index).getAlbum(indexAlb).deleteTrack(x); //borramos cancion en pos x
			
		}else{
			System.out.println("Introduzca un título de canción válido o coincidente\n");
		}
	}
	
	
	
	
//MODIF--------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------
	//MODIFICAMOS PLAYLIST (nombre)---------------
	private void modifPlayList (Scanner entrada){
		//String[] names = new String[list.size()];
		String v;
		int x;
		
		System.out.println("Playlist a modificar:");
		//names = namesPlayList();
		//System.out.println(Arrays.toString(names));
		System.out.println(namesPlayList());
		System.out.print("Escriba cual quiere modificar: ");
		v = entrada.next();
			
		x = hasPlayList(v); //comprobamos que lo introducido esté	
		if (x != -1){ // = encontrado
			System.out.print("Nombre nuevo: ");
			v = entrada.next();
			list.get(x).setNombre(v); //cambiamos el nombre
			System.out.println("Nombre modificado correctamente\n");
				
		}else{ //o no coincidente o ""
			System.out.println("Introduzca un nombre válido o coincidente\n");
		}
	}
	
	
	//MODIFICAMOS ALBUM (titulo, grupo y/o autor)----------
	private void modifAlbum (Scanner entrada, int index){
		String names;
		String v;
		String s;
		boolean exit=false;
		int x;
		
		System.out.println("Lista de albumes:");
		names = namesAlbum(index); //necesita index para buscar albumes
		System.out.println(names);
		System.out.print("Escriba cual quiere modificar (titulo del album): ");
		v = entrada.next();
				
		x = hasAlbum(v, index); //comprobamos que lo introducido esté	
		if (x != -1){ // = encontrado
			while (!exit){
				System.out.println("	Modificar titulo (1)");
				System.out.println("	Modificar autor  (2)");
				System.out.println("	Modificar grupo  (3)");
				System.out.println("	Salir            (4)\n");
				s = entrada.next();
					
				if (s.equals("1")){
					System.out.print("Nuevo titulo: ");
					s = entrada.next();
					list.get(index).getAlbum(x).setTitulo(s); //cambiamos titulo
					System.out.println("Titulo modificado correctamente\n");
					
				}else if (s.equals("2")){
					System.out.print("Nuevo autor: ");
					s = entrada.next();
					list.get(index).getAlbum(x).setAutor(s); //cambiamos autor
					System.out.println("Autor modificado correctamente\n");	
						
				}else if (s.equals("3")){
					System.out.print("Nuevo grupo: ");
					s = entrada.next();
					list.get(index).getAlbum(x).setGrupo(s); //cambiamos grupo
					System.out.println("Grupo modificado correctamente\n");	
						
				}else if (s.equals("4")){
					exit = true;
						
				}else{
					System.out.print("Introduzca numeros del 1..4\n");
				}
			}
				
		}else{ //o no coincidente o ""
			System.out.println("Introduzca un nombre válido o coincidente\n");
		}
	}
	
	
	//MODIFICAMOS CANCION (titulo, autor, duracion y/o formato)--------------
	private void modifCancion (Scanner entrada, int index, int indexAlb){
		String names;
		String v;
		String s;
		boolean exit=false;
		int x;
		Codecs cod = null;
		
		System.out.println("Lista de canciones:");
		names = namesCancion(index, indexAlb); //necesita ambos index para buscar canciones
		System.out.println(names);
		System.out.print("Escriba cual quiere modificar (titulo): ");
		v = entrada.next();
				
		x = hasCancion(v, index, indexAlb); //comprobamos que lo introducido esté	
		if (x != -1){ // = encontrado
			while (!exit){
				System.out.println("	Modificar titulo    (1)");
				System.out.println("	Modificar autor     (2)");
				System.out.println("	Modificar duracion  (3)");
				System.out.println("	Modificar formato   (4)");
				System.out.println("	Salir               (5)\n");
				s = entrada.next();
					
				if (s.equals("1")){
					System.out.print("Nuevo titulo: ");
					s = entrada.next();
					list.get(index).getAlbum(indexAlb).getTrack(x).setTitulo(s); //cambiamos titulo
					System.out.println("Titulo modificado correctamente\n");	
						
				}else if (s.equals("2")){
					System.out.print("Nuevo autor: ");
					s = entrada.next();
					list.get(index).getAlbum(indexAlb).getTrack(x).setAutor(s); //cambiamos autor
					System.out.println("Autor modificado correctamente\n");	
						
				}else if (s.equals("3")){
					System.out.print("Nueva duracion: ");
					s = entrada.next();
					int dur = Integer.valueOf(s); //convertimos en int
					list.get(index).getAlbum(indexAlb).getTrack(x).setDuracion(dur); //cambiamos duracion
					System.out.println("Duracion modificada correctamente\n");
						
				}else if (s.equals("4")){
					System.out.print("Nuevo formato: MP3(1) , FLAC(2) ");
					s = entrada.next();
					if (s.equals("1")){
						cod =  Codecs.MP3;
						list.get(index).getAlbum(indexAlb).getTrack(x).setFormato(cod); //cambiamos formato
						System.out.println("FLAC modificado correctamente\n");
					}else if (s.equals("2")){
						cod =  Codecs.FLAC;
						list.get(index).getAlbum(indexAlb).getTrack(x).setFormato(cod); //cambiamos formato
						System.out.println("FLAC modificado correctamente\n");
					}else{
						System.out.println("FLAC nuevo incorrecto\n");
					}
						
				}else if (s.equals("5")){
					exit = true;
					System.out.println("Salimos de 'Modificacion'\n");
					
				}else{
					System.out.print("Introduzca numeros del 1..4\n");
				}
			}
				
		}else{ //o no coincidente o ""
			System.out.println("Introduzca un nombre válido o coincidente\n");
		}
	}
			
		
	
	
//NAMES--------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------
	//ARRAY DE PLAYLIST (string de playlists)-------
	private String namesPlayList (){
		ListIterator<PlayList> it = list.listIterator();
		String titulos = "";	
		
		while (it.hasNext()){
			PlayList playListAux = it.next();
			titulos += "\n--\t--\t--\n";
			titulos += "Titulo: " + playListAux.getNombre() + "\n";
			titulos += "Albumes: " + playListAux.getNumAlbumes() + "\n";
			titulos += "Canciones: " + playListAux.getNumCanciones() + "\n";
			titulos += "Duracion total: " + time(playListAux.getDuracionTotal()) + "\n";
			titulos += "--\t--\t--\n";
		}
		return titulos;
	}
	
	//STRING CON ALBUMES (titulo, autor y grupo, canciones y duracion total)---------
	private String namesAlbum (int index){	
		ListIterator<Album> it = list.get(index).getAlbumList().listIterator();
		String titulos = "";
			
		while (it.hasNext()){
			Album albumAux = it.next(); //--> ahora podemos hacer album1.getTitulo(), etc.
			titulos += "\n--\t--\t--\n";
			titulos += "Titulo: " + albumAux.getTitulo() + "\n";
			titulos += "Autor: " + albumAux.getAutor() + "\n"; 
			titulos += "Grupo: " + albumAux.getGrupo() + "\n";
			titulos += "Canciones: " + albumAux.getNumCanciones() + "\n";
			titulos += "Duracion total: " + time(albumAux.getDuracionTotal()) + "\n";
			titulos += "--\t--\t--\n";
		}
		return titulos;
	}
		
	//STRING CON CANCIONES (TITULO, AUTOR, DURACION Y FORMATO)----------------
	private String namesCancion (int index, int indexAlb){	
		ListIterator<Cancion> it = list.get(index).getAlbum(indexAlb).getTrackList().listIterator();
		String titulos = "";
			
		while (it.hasNext()){
			Cancion cancionAux = it.next();
			titulos += "\n--\t--\t--\n";
			titulos += "Titulo: " + cancionAux.getTitulo() + "\n";
			titulos += "Autor: " + cancionAux.getAutor() + "\n";
			titulos += "Duracion: " + time(cancionAux.getDuracion()) + "\n";
			titulos += "Formato: " + cancionAux.getFormato() + "\n";
			titulos += "--\t--\t--\n";
		}
		return titulos;
	}
	
	
	
	
//HAS?------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------
	//EXISTE NUESTRA PLAYLIST? --> Si existe, nos devuelve su index---
	private int hasPlayList(String s){
		ListIterator<PlayList> x = list.listIterator();
			
		int z = 0;
		while (x.hasNext()){
			if (s.equals(list.get(z).getNombre())){ //list.get(posicion) + getNombre de la clase PlayList
				return z;
			}else{
				z++;
				x.next();
			}
		}
		return -1; //retorna -1 si no encuentra el nombre
	}
	
	//EXISTE NUESTRO ALBUM? --> Si existe, devuelve su index---
	private int hasAlbum(String s, int index){
		ListIterator<Album> it = list.get(index).getAlbumList().listIterator();
			
		
		int z = 0; //contador para el index del albumlist
		while (it.hasNext()){
			if (s.equals(it.next().getTitulo())){ //getTitulo de la clase Album
				return z;
			}
			z++;
		}
		return -1; //retorna -1 si no encuentra el titulo
	}
	
	//EXISTE NUESTRA CANCION? --> Si existe, devuelve su index---
	private int hasCancion(String s, int index, int indexAlb){
		ListIterator<Cancion> it = list.get(index).getAlbum(indexAlb).getTrackList().listIterator();
		
		int z = 0; //contador para el index de tracklist
		while (it.hasNext()){
			if (s.equals(it.next().getTitulo())){ //getTitulo de la clase Cancion
				return z;
			}
			z++;
		}
		return -1; //retorna -1 si no encuentra el titulo
	}
	
	
	
	
	
	
//-----------------------------------------------------------------------------------------------------------------------
//INTERFAZ PRINCIPAL-----------------------------------------------------------------------------------------------------
	public void interfaz(){
		Scanner entrada = new Scanner (System.in);
		boolean exit = false;
		String s;
		String out;
		
		while (exit != true){
			out = String.join("\n",
							"---------------------------------------",
							"GESTOR DE PLAYLISTS",
							"---------------------------------------",
							"¿Qué quiere añadir, borrar o modificar?",
							"	Playlist    (1)",
							"	Album       (2)",
							"	Cancion     (3)",
							"	Imprimir    (4)",
							"	Salir       (5)\n");
			System.out.println(out);
			
			s = entrada.next();
			if (s.equals("1")){
				interfazPlay(entrada);
				
				
			//ALBUM	
			}else if(s.equals("2")){
				if (list.size() == 0){
					System.out.println("No hay playlist. Cree primero una Playlist\n");
				}else{
				interfazAlbum(entrada);
				}
				
				
			//CANCION
			}else if (s.equals("3")){
				if (list.size() == 0){
					System.out.println("No hay playlist. Cree primero una Playlist\n");
				}else{
					interfazCancion(entrada);
				}
				
				
			//PRINT
			}else if (s.equals("4")){
				printGestor(entrada);	
				
				
			//EXIT   //llamamos a print
			}else if (s.equals("5")){
				System.out.println("\nSaliendo de nuestro gestor...\nSEE YOU SOON!"); 
				exit = true;
		
				
			}else{
				System.out.println("Ingrese un número, o números del 1 al 5 la próxima vez\n");
			}
		}
		entrada.close(); //cerramos sumidero
	}
	
	
	
//---------------------------------------------------------------------------------------------------------------	
//INTERFAZ PLAYLIST----------------------------------------------------------------------------------------------
	public void interfazPlay(Scanner entrada){
		String t;
		
		System.out.println("¿Que desea hacer?\n");
		System.out.println("	Nueva Playlist     (1)");
		System.out.println("	Modificar Playlist (2)");
		System.out.println("	Borrar Playlist    (3)");
		System.out.println("	Imprimir           (4)");
		System.out.println("	Salir              (5)\n");
			
		t = entrada.next();
		
		//NUEVA PLAYLIST
		if (t.equals("1")){
			addPlayList(entrada); //da igual si success es true o false, volvemos a la interfaz
				
				
		//MODIF PLAYLIST
		}else if (t.equals("2")){
			if (list.size() > 0){
				modifPlayList(entrada);
			}else{
				System.out.println("No hay Playlists existentes. Por favor, cree alguna antes de modificar\n");
			}
				
				
		//BORRAMOS PLAYLIST
		}else if (t.equals("3")){
			if (list.size() >0){
				deletePlayList(entrada);
			}else{
				System.out.println("No hay Playlists existentes. Por favor, cree alguna antes de borrar\n");
			}
				
				
		//IMPRIME NOMBRES PLAYLIST
		}else if (t.equals("4")){
			System.out.print("Playlist existentes: ");
			System.out.println(namesPlayList() + "\n");
			
			
		//EXIT
		}else if (t.equals("5")){
			System.out.println("Volvemos al gestor...\n");
			
				
		}else{
			System.out.println("Ingrese 1..5 la próxima vez...\n"); //si el num es incorrecto, volvemos a interfaz
		}
	}
	
	
//-----------------------------------------------------------------------------------------------------------------
//INTERFAZ ALBUM---------------------------------------------------------------------------------------------------
	public void interfazAlbum(Scanner entrada){
		String s;
		int x;
		
		System.out.println("Playlist existentes: ");
		System.out.println(namesPlayList());
		System.out.print("Escriba sobre cual quiere operar: ");
		s = entrada.next();
		
		x = hasPlayList(s); //comprobamos que lo introducido esté. nos devuelve el index
		if (x != -1){ // = encontrada playlist sobre la que operar
			System.out.println("¿Que desea hacer sobre Album?\n");
			System.out.println("	Nuevo Album      (1)");
			System.out.println("	Modificar Album  (2)");
			System.out.println("	Borrar Album     (3)");
			System.out.println("	Salir            (4)");
			s = entrada.next();
		
			if (s.equals("1")){
				addAlbum(entrada,x); //da igual si success es true o false, volvemos a la interfaz
				
				
			}else if (s.equals("2")){
				if (list.get(x).getNumAlbumes() == 0){
					System.out.println("No hay albumes. Cree uno antes de intentar modificar\n");
				}else{
					modifAlbum(entrada, x); //pasamos el index a modif
				}
				
			}else if (s.equals("3")){
				if (list.get(x).getNumAlbumes() == 0){
					System.out.println("No hay albumes. Cree uno antes de intentar borrar\n");
				}else{
					deleteAlbum(entrada,x);	 //pasamos el index tambien
				}	
				
			}else if (s.equals("4")){
				System.out.println("Salimos y volvemos al gestor...\n");
				
			}else{
				System.out.println("Introduzca numeros de 1..4 la próxima vez\n");
			}
			
		}else{ //o no coincidente o ""
			System.out.println("Introduzca un nombre válido o coincidente de Playlist\n");
		}
	}
	
	
//---------------------------------------------------------------------------------------------------------------	
//INTERFAZ CANCION----------------------------------------------------------------------------------------------	
	public void interfazCancion(Scanner entrada){ 
		String s;
		String v;
		String t;
		String alb;
		int index;
		int indexAlb;
		
		System.out.println("Playlist existentes: ");
		System.out.println(namesPlayList());
		System.out.print("Escriba sobre cual quiere operar: ");
		s = entrada.next();
		
		index = hasPlayList(s); //comprobamos que lo introducido esté. nos devuelve el index
		if (index != -1){ // = encontrada playlist sobre la que operar
			if (list.get(index).getNumAlbumes() == 0){
				System.out.println("No existe ningun Album. Cree uno primero");
				
			}else{
				System.out.println("Lista de albumes:");
				alb = namesAlbum(index); //necesita index para buscar albumes
				System.out.println(alb);
				System.out.print("Escriba sobre que album operar (titulo): ");
				v = entrada.next();
				
				indexAlb = hasAlbum(v,index); //necesita index para saber la playlist
				if (indexAlb != -1){
					System.out.println("¿Que desea hacer sobre su Canción?\n");
					System.out.println("	Nueva Canción     (1)");
					System.out.println("	Modificar Canción (2)");
					System.out.println("	Borrar Canción    (3)");
					System.out.println("	Salir             (4)");
					
					t = entrada.next();
					
					if (t.equals("1")){
						addCancion(entrada,index,indexAlb); //da igual si success es true o false, volvemos a la interfaz
						
					}else if (t.equals("2")){
						if (list.get(index).getAlbum(indexAlb).getNumCanciones() == 0){
							System.out.println("No hay canciones. Cree una antes de intentar modificar");
						}else{
							modifCancion(entrada, index, indexAlb); //pasamos los dos index
						}
						
					}else if (t.equals("3")){
						if (list.get(index).getAlbum(indexAlb).getNumCanciones() == 0){
							System.out.println("No hay canciones. Cree una antes de intentar borrar");
						}else{
							deleteCancion(entrada, index, indexAlb);	 //pasamos los dos index too
						}	
						
					}else if (t.equals("4")){
						System.out.println("Salimos y volvemos al gestor...");
						
					}else{
						System.out.println("Introduzca numeros de 1..4 la próxima vez");
					}
					
						
				}else{
					System.out.println("Introduzca un Album válido o coincidente");
				}
			}
		
		}else{ //o no coincidente o ""
			System.out.println("Introduzca una Playlist válida o coincidente\n");
		}
		
	}
	
	
	
	
	
//DIRECTORIES------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------
//	private void save (){
//		try{
//			Path ruta = Paths.get("playlist"); 
//			Files.createDirectory(ruta);
//		}catch(IOException e){
//			System.out.println("No ha sido posible crear directorio con nombre de PlayList");
//		}
//	}
	

	
//TIME--------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------------
	private String time (int dur){ //PASAMOS A HORAS,MIN,SEG
		
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

	
	
//PRINT------------------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------------------
	private void printGestor(Scanner entrada){
		int index;
		int indexAlb;
		
		if (list.size() == 0){
			System.out.println("NO HAY NADA EN ESTA LISTA");
			
		}else{
			System.out.println("\nElige que PLAYLIST desea ver (nombre): ");
			System.out.println(namesPlayList());
			String s = entrada.next();
			index = hasPlayList(s); //comprobamos que lo introducido esté. nos devuelve el index
			if (index != -1){ // = encontrada playlist sobre la que operar
				if (list.get(index).getNumAlbumes() == 0){
					System.out.println("No hay ALBUMES en esta PLAYLIST");
					
				}else{
					System.out.println("\nElige el ALBUM desea ver (titulo): ");
					System.out.println(namesAlbum(index));
					String v = entrada.next();
					indexAlb = hasAlbum(v,index); //comprobamos que album este. devuelve index del album
					
					if (indexAlb != -1){ //=encontrado album
						if (list.get(index).getNumCanciones() == 0){
							System.out.println("No hay CANCIONES en este ALBUM");
						
						}else{
							System.out.println(namesCancion(index,indexAlb));
						}
						
					}else{
						System.out.println("ALBUM NO COINCIDENTE");
					}
				}
			}else{
				System.out.println("PLAYLIST NO COINCIDENTE");
			}
		}
	}
	
	
	
	
//MAIN/////////////////////////////////////////////////////////////	
	public static void main(String[] args) {
		GestionPlayList gest = new GestionPlayList();
		gest.interfaz();
	}

}
