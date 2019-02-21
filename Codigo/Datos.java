/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebitaprocesos;

/**
 *
 * @author a10
 */
public class Datos {
    
	int X, Y, Pocision, estado, Ocupado, Cuantum, r, g, b, posicionM, 
                posiciont,TiempoEjecucion,memoriaProceso;
	String Nombre, Recursos, Estado="Formado", transicion="Formado";
        boolean lugar;
	
	public Datos(
                    int x, int y, int P, int Est, int O,int TiempoEjecucion, 
                    int Cuantum, String Nombre, int R, int G, int B, int Posicion, 
                    String Recursos,String Estado,
                    String transicion, int Posiciont, int memoriaProceso,boolean lugar
                    ){
		this.X = x;
		this.Y= y;
		this.Pocision= P;
		this.estado= Est;
		this.Ocupado= O;
                this.TiempoEjecucion = TiempoEjecucion;
		this.Cuantum = Cuantum;
		this.Nombre = Nombre;
		this.r = R;
		this.g = G;
		this.b = B;
		this.posicionM = Posicion;
		this.Recursos = Recursos;
		this.estado = estado;
		this.transicion = transicion;
		this.posiciont = Posiciont;
                this.memoriaProceso=memoriaProceso;
                this.lugar = lugar;
	}
}
    

