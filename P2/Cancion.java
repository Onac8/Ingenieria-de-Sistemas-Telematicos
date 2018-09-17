package urjc.ist.playlist;

public class Cancion {
	
	public enum Codecs {
		MP3, FLAC
	}

	private String titulo;  // Título de la canción
	private String autor;  // Autor(a) de la canción
	private int duracion;  // Duración en segundos
	private Codecs formato;  // Codificación de la canción
	
	public Cancion() {
		titulo = "";
		autor = "";
		duracion = 0;
		formato = null;
	}
	
	public Cancion(String titulo, String autor, int duracion, Codecs formato) {
		this.titulo = titulo;
		this.autor = autor;
		this.duracion = duracion;
		this.formato = formato;
	}
	
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
	
	public int getDuracion() {
		return duracion;
	}
	
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	
	public Codecs getFormato() {
		return formato;
	}
	
	public void setFormato(Codecs formato) {
		this.formato = formato;
	}
	
	
//IMPRIMIR--------------------------------------------------------
	@Override
	/*para indicar al compilador que sustituya el metodo
	 *de mismo nombre de la clase superior (.object de java)
	 *@deprecated: para indicar que un metodo puede desaparecer.
	  el metodo obsoleto aparecera tachado*/
	
	public String toString() {
		/**
		 * Creación de una representación del contenido de la
		 * Canción en formato String
		 */
		int hours = duracion / 3600;
		int minutes = (duracion % 3600) / 60;
		int seconds = duracion % 60;

		String timeString;
		if (hours > 0) {
			timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
			//.format(): dado un formato(arg1) y unos args, devuelve un str con ese formato
			//"%02d --> el % para el argumento a pasar, el 02 (dos digitos), d=digito.
		} else {
			timeString = String.format("%02d:%02d", minutes, seconds);
		}
		return String.join("\n", "----------",
				"Título: " + titulo,
				"Autor: " + autor,
				"Duración: " + timeString,
				"Formato: " + (formato == Codecs.MP3 ? "MP3" : "FLAC"), // ?--> operador ternario 
				"----------");
	}
	
	
//IGUALAR---------------------------------------------------------------
	@Override
	/*
	 *Sobreescribe al metodo equals de la clase object de java. 
	 *Este compara direcciones de memoria, el nuestro compara atributos
	 *Cada metodo equals de su propia clase compara 
	 */
	
	public boolean equals(Object other) {
		/**
		 * Implementación de un método de comparación del contenido
		 * de dos canciones
		 */
		if (other == null) return false;
	    if (other == this) return true; //yo mismo?
	    if (!(other instanceof Cancion)) return false; //si no es una instancia de cancion, return F
	    
	    Cancion otherCancion = (Cancion)other; //hacemos casting de other a cancion
	    if (this.titulo == otherCancion.titulo &&
	    		this.autor == otherCancion.autor &&
	    		this.duracion == otherCancion.duracion &&
	    		this.formato == otherCancion.formato) {
	    	return true;
	    } else {
	    	return false;
	    }
	}
	
	
//	@Override
//	
//	public int hashCode(){
//	/**
//	* Desde Java 7, es obligatorio sobreescribir el método hashCode
//	* para computar el código hash de los contenidos del objeto
//	* Nota: el hashCode es el código que devuelve el método toString() de
//	* la clase Object: NombreClaseObjeto<hashCodeObjeto>.
//	* En el caso de Object, el código hash devuelto corresponde a la
//	* dirección de memoria del objeto:
//	* http://docs.oracle.com/javase/tutorial/java/IandI/objectclass.html
//	*/
//	return Objects.hash(titulo, autor, duracion, formato);
//	}

//MAIN------------------------------------------------	
	public static void main(String[] args) {
		Cancion track1 = new Cancion("The Song of the Sun", "Mike Olfield", 273, Codecs.MP3);
		Cancion track2 = new Cancion("The Song of the Sun", "Mike Olfield", 273, Codecs.MP3);
		System.out.println(track1);
		System.out.println(track1.equals(track2));
	}

}