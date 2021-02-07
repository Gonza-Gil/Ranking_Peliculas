package gui;

import javax.swing.table.DefaultTableModel;

public class ModeloPelicula extends DefaultTableModel{
	
	private static final long serialVersionUID = 1L;

	public ModeloPelicula(final Object[][] datos, final String[] titulos) {
		super(datos, titulos);
	}
}
