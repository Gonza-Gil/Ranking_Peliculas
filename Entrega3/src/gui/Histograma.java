package gui;


import java.awt.Color;
import java.awt.Graphics;


import javax.swing.JPanel;

public class Histograma extends JPanel{
	private int alto1, alto2, alto3, alto4, alto5;

	private static final long serialVersionUID = 1L;
	
	public Histograma(int[]raitings) {
		this.setBackground(Color.white);
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//int ejeX = this.getWidth();
		int ejeY = this.getHeight() - (this.getHeight()/6);
		//linea vertical
		g.drawLine(40, 0, 40, this.getHeight());
		//linea horizontal
		g.drawLine(0, ejeY, 280, ejeY);
		//lineas en el eje X
		g.drawLine(80, ejeY, 80, ejeY+5);
		g.drawLine(120, ejeY, 120, ejeY+5);
		g.drawLine(160, ejeY, 160, ejeY+5);
		g.drawLine(200, ejeY, 200, ejeY+5);
		g.drawLine(240, ejeY, 240, ejeY+5);
		//numeros en el eje X
		g.drawString("1", 77, ejeY+20);
		g.drawString("2", 117, ejeY+20);
		g.drawString("3", 157, ejeY+20);
		g.drawString("4", 197, ejeY+20);
		g.drawString("5", 237, ejeY+20);
		g.drawString("Score", 280, ejeY+20);
		//lineas en el eje Y
		g.drawLine(35, ejeY-25, 45, ejeY-25);
		g.drawLine(35, ejeY-50, 45, ejeY-50);
		g.drawLine(35, ejeY-75, 45, ejeY-75);
		g.drawLine(35, ejeY-100, 45, ejeY-100);
		g.drawLine(35, ejeY-125, 45, ejeY-125);
		//numeros en el eje Y
		g.drawString("10000", 0, ejeY-20);
		g.drawString("20000", 0, ejeY-45);
		g.drawString("30000", 0, ejeY-70);
		g.drawString("40000", 0, ejeY-95);
		g.drawString("50000", 0, ejeY-120);
		g.drawString("Views", 0, ejeY-141);
		//flecha eje Y
		g.fillPolygon(new int[] {35, 40, 45}, new int[] {ejeY-140, ejeY-152, ejeY-140}, 3);
		//flecha eje X
		g.fillPolygon(new int[] {268,  280, 268}, new int[] {ejeY+5, ejeY, ejeY-5}, 3);
		//rectangulos
		g.setColor(Color.CYAN);
		g.drawRect(70, ejeY, 20, (alto1*-25)/10000);
		g.drawRect(110, ejeY, 20, (alto2*-25)/10000);
		g.drawRect(150, ejeY, 20, (alto3*-25)/10000);
		g.drawRect(190, ejeY, 20, (alto4*-25)/10000);
		g.drawRect(230, ejeY, 20, (alto5*-25)/10000);
	}
	
	public void setAlturas(int[] raitings) {
		this.alto1=raitings[1];
		this.alto2=raitings[2];
		this.alto3=raitings[3];
		this.alto4=raitings[4];
		this.alto5=raitings[5];
		repaint();
	}
}
