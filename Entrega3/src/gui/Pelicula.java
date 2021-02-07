package gui;

public class Pelicula {
	
	private String nombre;
	private int id;
	private int votos;
	private int[] raiting = new int[6];
	
	public Pelicula(String nombre, int id, int votos) {
		this.setId(id);
		this.setNombre(nombre);
		this.setVotos(votos);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVotos() {
		return votos;
	}

	public void setVotos(int votos) {
		this.votos = votos;
	}
	
	public void incrementar(int pos) {
		this.raiting[pos]++;
	}
	
	public float promedioVotos() {
		float aux = 0;
		for(int i = 0; i<raiting.length; i++) {
			aux += i*raiting[i];
		}
		if(this.getVotos() != 0)
			aux = aux/this.getVotos();
		else aux = 0;
		return aux;
	}

}
