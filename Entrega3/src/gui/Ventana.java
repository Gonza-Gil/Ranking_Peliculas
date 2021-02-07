package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.*;

@SuppressWarnings("serial")
public class Ventana extends JFrame{
	private JLabel titulo;
	private Font fuente;
	private JButton procesar;
	private JProgressBar barraProgreso;
	private JLabel numUsuarios;
	private JLabel numPeliculas;
	private JLabel numVotos;
	private GridBagConstraints c;
	private ArrayList<Pelicula> peliculas; //arreglo donde se guardan las peliculas
	private int[] raiting = new int[6]; //arreglo donde se guardan la cantidad de votos de cada raiting [1.0,5.0]
	private Histograma hist;
	private JPanel panelGrafico;
	private JTable tabla = new JTable();
	private JScrollPane scrollPane;
	private JPanel panelTabla;
	private ModeloPelicula modelo;	//guarda el modelo para la tabla
	private int dimL = 0; //guarda la cantidad total de peliculas

	
	public Ventana() throws IOException {
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBackground(Color.white);
		c = new GridBagConstraints();
		
		//titulo
		titulo = new JLabel ("Tablero de control de peliculas");
		fuente = new Font("Serief",Font.BOLD,14);
		titulo.setFont(fuente);
		titulo.setAlignmentX(CENTER_ALIGNMENT);
		c.gridy=0;
		c.gridx=0;
		c.gridwidth=3;
		c.weightx=1;
		c.weighty=0.5;
		panel.add(titulo, c);
		
		//boton
		c = new GridBagConstraints();
		procesar = new JButton("Procesar datos");
		procesar.addMouseListener(new MouseAdapter() {	//listener para el boton de procesasr datos
			public void mouseClicked(MouseEvent arg0) {
				CargaDatos t = new CargaDatos();
				t.start();
			}
		});
		c.fill= GridBagConstraints.NONE;
		c.gridy=1;
		c.gridx=1;
		c.gridwidth=1;
		c.weightx=1.0;
		c.weighty=0.5;
		panel.add(procesar, c);
		
		//barra de progreso
		c = new GridBagConstraints();
		barraProgreso = new JProgressBar();
		barraProgreso.setValue(0);
		barraProgreso.setStringPainted(true);
		c.gridy=1;
		c.gridx=2;
		c.gridwidth=1;
		c.weightx=1.0;
		c.weighty=0.5;
		c.insets= new Insets(0,0,0,10);
		panel.add(barraProgreso, c);
		
	//Datos Generales
		//Panel usuarios 
		c = new GridBagConstraints();
		JPanel panel1 = new JPanel();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		panel1.setBorder(BorderFactory.createLineBorder(Color.black));
		panel1.setBackground(Color.cyan);
		numUsuarios = new JLabel("????");
		numUsuarios.setAlignmentX(CENTER_ALIGNMENT);
		panel1.add(numUsuarios);
		JLabel usuarios = new JLabel("Usuarios");
		usuarios.setFont(fuente);
		usuarios.setAlignmentX(CENTER_ALIGNMENT);
		panel1.add(usuarios);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy=2;
		c.gridx=0;
		c.weightx=1.0;
		c.weighty=0.5;
		c.gridwidth=1;
		c.ipady=10;
		c.insets= new Insets(0,10,0,0);
		panel.add(panel1, c);
		
		//Panel peliculas
		c = new GridBagConstraints();
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		panel2.setBorder(BorderFactory.createLineBorder(Color.black));
		panel2.setBackground(Color.cyan);
		numPeliculas = new JLabel("????");
		numPeliculas.setAlignmentX(CENTER_ALIGNMENT);
		panel2.add(numPeliculas);
		JLabel peliculas = new JLabel("Películas");
		peliculas.setFont(fuente);
		peliculas.setAlignmentX(CENTER_ALIGNMENT);
		panel2.add(peliculas);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady=10;
		c.gridy=2;
		c.gridx=1;
		c.weightx=1.0;
		c.weighty=0.5;
		c.gridwidth=1;
		panel.add(panel2, c);
		
		//Panel votos
		c = new GridBagConstraints();
		JPanel panel3 = new JPanel();
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
		panel3.setBorder(BorderFactory.createLineBorder(Color.black));
		panel3.setBackground(Color.cyan);
		numVotos = new JLabel("????");
		numVotos.setAlignmentX(CENTER_ALIGNMENT);
		panel3.add(numVotos);
		JLabel votos = new JLabel("Cant. de votos");
		votos.setFont(fuente);
		votos.setAlignmentX(CENTER_ALIGNMENT);
		panel3.add(votos);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady=10;
		c.gridy=2;
		c.gridx=2;
		c.weightx=1.0;
		c.weighty=0.5;
		c.gridwidth=1;
		c.insets= new Insets(0,0,0,10);
		panel.add(panel3, c);
		
	//Selector cantidad de datos
		//texto
		JPanel panelSelector = new JPanel();
		panelSelector.setLayout(new BoxLayout(panelSelector, BoxLayout.X_AXIS));
		panelSelector.setBackground(Color.white);
		c = new GridBagConstraints();
		JLabel lbl = new JLabel("Cantidad de resultados a mostrar: ");
		lbl.setFont(fuente);
		panelSelector.add(lbl);
		//selector
		String[] valores = {"5", "10", "20", "100", "1000", "TODOS"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox selector = new JComboBox(valores);
		selector.addActionListener(new ActionListener() {
			@Override
		    public void actionPerformed(ActionEvent e) {
				int cant = 0;	//guarda la cantidad de filas a mostrar segun lo indique el selector
				if (selector.getSelectedItem().toString() == "TODOS") {
					cant = dimL;	//si se selecciona todos, la cantidad de filas a mostrar es la dimension logica del arreglo de peliculas
				}
				else cant = Integer.parseInt(selector.getSelectedItem().toString());
				cambiarFilas(cant);	//setea la nueva cantidad de filas a mostrar
		    }
		});
		panelSelector.add(selector);
		c.gridy=3;
		c.gridx=0;
		c.weightx=1.0;
		c.weighty=0.5;
		c.gridwidth=2;
		panel.add(panelSelector, c);
		
		//Tabla
		c = new GridBagConstraints();
		panelTabla = new JPanel();
		panelTabla.setLayout(new GridLayout(1,1));
		String[] titulos = {"Nombre de Pelicula", "Usuarios", "Raiting"};
		Object[][] data = new Object[0][3];
		tabla = new JTable(data, titulos);
		tabla.setPreferredScrollableViewportSize(new Dimension(500, 70));
		tabla.setFillsViewportHeight(true);
		tabla.getTableHeader().setReorderingAllowed(false);
		scrollPane = new JScrollPane(tabla); 
		panelTabla.add(scrollPane);
		c.fill = GridBagConstraints.BOTH;
		c.gridy=4;
		c.gridx=0;
		c.weightx=0.0;
		c.weighty=0.5;
		c.gridwidth=3;
		c.insets= new Insets(0,10,0,10);
		panel.add(panelTabla, c);
		
		
	//Histograma
		panelGrafico = new JPanel();
		panelGrafico.setLayout(new BorderLayout(20, 20));
		panelGrafico.setBackground(Color.white);
		JLabel titulo = new JLabel("Histograma");
		titulo.setFont(fuente);
		titulo.setAlignmentX(CENTER_ALIGNMENT);
		panelGrafico.add(titulo, BorderLayout.NORTH);
		hist = new Histograma(raiting);
		panelGrafico.add(hist, BorderLayout.CENTER);	//crea el grafico vacio y lo agrega a la ventana
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.gridy=5;
		c.gridx=0;
		c.weightx=0.0;
		c.weighty=1;
		c.gridwidth=3;
		c.ipady=100;
		c.ipadx=400;
		c.insets= new Insets(0,10,10,10);
		panel.add(panelGrafico, c);
		
		
		//Ventana
		this.setTitle("Tablero de control");
		this.setContentPane(panel);
		this.setSize(700, 650);
		this.setVisible(true);
		//Esto es para poder cerrar la ventana
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	
	private void cambiarFilas(int cantFilas) {
		if(cantFilas != 0) {	//para evitar que se rompa si se selecciona TODOS al principio de la carga de datos
			Object [][] datos = new Object[cantFilas][3];	//matriz con los datos de la tabla, al principio esta vacio
			String[] titulos = {"Nombre de Pelicula", "Usuarios", "Raiting"};
			int f = 0;	//guarda el indice de las filas
			int c = 0;	//guarda el indice de las columnas
			if(peliculas != null) {		//si todavia no se procesaron los datos, no se carga la matriz de datos
				for(Pelicula n: peliculas) {
					datos[f][c] = n.getNombre();
					datos[f][c+1] = n.getVotos();
					datos[f][c+2] = Math.floor((n.promedioVotos() * 10))/10.0;
					f++;
					if(f == cantFilas)
						break;
				}
			}
			modelo = new ModeloPelicula(datos, titulos);	//crea el modelo de la tabla y pasa los arreglos con los datos y los titulos para crear la tabla
			tabla.setModel(modelo);		//setea el modelo de la tabla
		}
	}
	
	
	class CargaDatos extends Thread{
		public void run() {
			try {
				String [] linea; 	//guarda los campos de la linea actual
				String strLine;		//guarda la linea actual
				int id=0;
				float voto;
				String nombre = "";
				boolean primerLinea = true;
				int progreso = 0;
				try {
					//para hacer que la apertura de archivos funcione, marcamos la carpeta archivos como source folder
					InputStream is = Ventana.class.getClassLoader().getResourceAsStream("movies.csv");
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					peliculas = new ArrayList<Pelicula>();
					
					while((strLine = br.readLine()) != null) {	//recorro todo el archivo
						if(primerLinea) {		//esto es para controlar que no se procese la primer linea de los archivos
							primerLinea=false;
							continue;
						}
						linea = strLine.split(",");		//guardo en cada celda del vector, los campos de la linea del archivo separados por ","
						id = Integer.parseInt(linea[0]);
						for(int i =1; i<linea.length-1; i++) {
							nombre = nombre + linea[i]; //como hay nombres que tienen "," vamos concatenando el contenido de las celdas del medio del arreglo linea
						}
						peliculas.add(new Pelicula(nombre, id, 0));
						nombre="";
						progreso++;
						if((progreso % 195 == 0) && (barraProgreso.getValue() <= 50)){
							barraProgreso.setValue(barraProgreso.getValue()+1);
						}
					}
					progreso = 0;
					br.close();
				}catch (FileNotFoundException e) {
		            e.printStackTrace();
				}
				primerLinea=true;
				InputStream is1 = Ventana.class.getClassLoader().getResourceAsStream("ratings.csv");
				BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
				while((strLine = br1.readLine()) != null) {
					if(primerLinea) {		//esto es para no procesar la primer linea del archivo
						primerLinea=false;
						continue;
					}
					linea = strLine.split(",");			//guardo en cada celda del vector, los campos de la linea del archivo separados por ","
					id = Integer.parseInt(linea[1]);	//guarda el id de la pelicula actual
					voto = Float.parseFloat(linea[2]);	//guarda el voto de la pelicula actual [1.0,5.0]
					for(Pelicula n : peliculas) {		//recorro el arreglo de peliculas hasta encontrar la que se corresponde al id de la linea actual
						if(id == n.getId()) {
							n.setVotos(n.getVotos()+1);	//incremento la cantidad de votos de la pelicula
							n.incrementar((int) Math.floor(voto));
							break;
						}
					}
					raiting[(int) Math.floor(voto)] = raiting[(int) Math.floor(voto)]+1; //incremento la cantidad de votos del raiting correspondiente
					progreso++;
					if((progreso % 2016 == 0) && (barraProgreso.getValue() <= 100)){
						barraProgreso.setValue(barraProgreso.getValue()+1);
					}
				}
				barraProgreso.setValue(100);
				//fstream1.close(); 
				br1.close();
				numPeliculas.setText(""+peliculas.size());	//modifica el cuadro con la cantidad de peliculas
				dimL = peliculas.size();
				int aux=0;		//aux guarda la cantidad total de votos
				for(int i=0; i<raiting.length; i++) {
					aux=aux+raiting[i];
				}
				numVotos.setText(""+aux);		//modifica el cuadro con la cantidad de votos
				numUsuarios.setText(""+aux);	//modifica el cuadro con la cantidad de usuarios
				peliculas.sort(new Comparator<Pelicula>() {
			        @Override
			        public int compare(Pelicula o1, Pelicula o2) {
			        	if(o1.getVotos() != o2.getVotos()) {
			        		return -1*(o1.getVotos() - o2.getVotos());
			        	}
			        	return 0;
			        }
			    });
				hist.setAlturas(raiting);  //modifica el histograma
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

	public static void main(String[] args) throws IOException {
		@SuppressWarnings("unused")
		Ventana app = new Ventana();
	}
}
