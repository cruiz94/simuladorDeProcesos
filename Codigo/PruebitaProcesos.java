package pruebitaprocesos;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author a10
 */
public class PruebitaProcesos extends JPanel {
   Timer timer;
   DefaultTableModel datos;
   static int RoOPM, posicionM=-1, pinta, posiciont=-1, contadort=0, contadoelimt=0, Vartime=0;	
   //variables para porcentajes
   static int SB = (int)(Math.random() * (100) +1);
   static int Liberar = (int)(Math.random() * (100) +1); 
   //auxiliares calcula de ram utilizada
   static int auxSumaRam = 0;
   static int auxSumaRam2 = 0;
   static int auxSuminta2 =0;
   static boolean lugar; //si va a memoria ram o a disco
   //coordenadas ovalos
   static int x = 43, y= 200, Cuantum, inix = x+10, iniy = y+10, x1=30, y1=50, x2=170, y2=170, x3=220, y3=10, xe = 330, ye =170;
   //arreglo de objetos de procesos
   static ArrayList<Datos> Proceso = new ArrayList<Datos>();
   //declaracion de las etiquetas
   JLabel label,lbtecla,lbrat,lbmon,lbimp,lbboci;
   //declaracion de los botones
   final JButton Biniciar, Bdetener, Bcontinuar, Bproceso,Bdestroza,Bram;
   //declaracion de campos
   JTextField Cproceso,Cram;
   //aleatorio de cantidad de procesos
   static int Numpro = (int)(Math.random() * (50-15 +1 ) + 15);//
   //variables utilitarias
   static int op = 0, limiteRAM=300, memoriavirtual=0, desicion = 0;
   boolean permitir = false;
   //variables para aleatorio de colores
   static  int R = (int)(Math.random() * 255+1);
   static  int G = (int)(Math.random() * 255+1);
   static  int B = (int)(Math.random() * 255+1);
   Color Aleatorio, AleatorioSpool;
   //declaraciones para la tabla de procesos
   final JScrollPane scrollPane;
   final JTable table;
   
   String Recursos="_____________",mensaje = "";
   
   public PruebitaProcesos(){
        setLayout(null);
	    setBackground(Color.WHITE);		
	    setLayout(null);
		
	    //*************************************************************Labels del spool**********************************************************
	    lbtecla = new JLabel("Teclado");
		lbtecla.setForeground(Color.BLACK);
		lbtecla.setBounds(x3+50,y3,100,30);
		add(lbtecla);
		
		lbrat = new JLabel("Raton");
		lbrat.setForeground(Color.BLACK);
		lbrat.setBounds(x3+50,y3+10,100,30);
		add(lbrat);
		
		lbmon = new JLabel("Monitor");
		lbmon.setForeground(Color.BLACK);
		lbmon.setBounds(x3+50,y3+20,100,30);
		add(lbmon);
		
		lbimp = new JLabel("Impresora");
		lbimp.setForeground(Color.BLACK);
		lbimp.setBounds(x3+50,y3+30,100,30);
		add(lbimp);
		
		lbboci = new JLabel("Bocinas");
		lbboci.setForeground(Color.BLACK);
		lbboci.setBounds(x3+50,y3+40,100,30);
		add(lbboci);
		//*************************************************Botones*******************************************************
		Biniciar = new JButton("Iniciar");
		Biniciar.setBounds(new Rectangle(0,365, 112, 25)); 
		Biniciar.addActionListener(iniciar);
		add(Biniciar);
                
                Bdestroza = new JButton("Terminar");
		Bdestroza.setBounds(new Rectangle(112,365, 112, 25)); 
		Bdestroza.addActionListener(termina);
		Bdestroza.setEnabled(false);
		add(Bdestroza);
		
		Bdetener = new JButton("Detener");
		Bdetener.setBounds(new Rectangle(224,365, 112, 25)); 
		Bdetener.addActionListener(detener);
		Bdetener.setEnabled(false);
		add(Bdetener);
		
		Bcontinuar = new JButton("Reanudar");
		Bcontinuar.setBounds(new Rectangle(336,365, 114, 25)); 
		Bcontinuar.addActionListener(continuar);
		Bcontinuar.setEnabled(false);
		add(Bcontinuar);

		Bproceso = new JButton("Procesos");
		Bproceso.setBounds(new Rectangle(0,391, 112, 25)); 
		Bproceso.addActionListener(proceso);
		add(Bproceso);
                
                Bram = new JButton("Mem. RAM");
                Bram.setBounds(new Rectangle(0,417,112,25));
                Bram.addActionListener(ram);
                add(Bram);
		
		//**********************************Cajas de texto**********************************************************************
	
		
                Cram = new JTextField();
                Cram.setBounds(112,417,60,25);
                Cram.setText(""+limiteRAM);
                Cram.setHorizontalAlignment(JTextField.CENTER);
                add(Cram);
                
		Cproceso = new JTextField();
		Cproceso.setBounds(112,391,60,25);
		Cproceso.setText(""+Numpro);
		Cproceso.setHorizontalAlignment(JTextField.CENTER);
		add(Cproceso);
		//*********************************************************************************************************************
		String[] columnNames = {"PROCESO", "ESTADO", "TRANSICION","TIEMPO DE EJEC",
                                   "TIEMPO EJEC RESTANTE", "RECURSO","MEMORIA"};
        
                //tabla de procesos
                datos = new DefaultTableModel(null,columnNames);
		table = new JTable(datos);
        scrollPane = new JScrollPane(table);
		scrollPane.setBounds(451,0,745,442);
		scrollPane.setVisible(true);
		add(scrollPane);
   }
   //********************************Escuchadores de botones*********************************************************************
	ActionListener iniciar = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{
					Crear();
					JOptionPane.showMessageDialog(null,"El proceso ya se inicio");
					Cproceso.setEditable(false);
					Bproceso.setEnabled(false);
					Bcontinuar.setEnabled(false);
					Bdetener.setEnabled(true);
					Cram.setEditable(false);
                                        Bram.setEnabled(false);
					Biniciar.setEnabled(false);
					Bdestroza.setEnabled(true);
				}catch(Exception evt){}
			}
	};
        
        
        //metodo encargado de generar por cada proceso, su tiempo requerido de ejecucion,
        //su cuantum,ya que se simulara sera necesario a cada proceso un color para poder visualizarlo
        //y tambien se calcula la cantidad de ram utilizada, si tiene espacio en la ram se le asigna una bandera
        //si la ram se encuentra llena y aun  hay procesos estos s emandan ala memoria virtual 
        //y se le asigna una bandera distinta para que se mas facil el manejos de los procesos
        public static void Crear(){
           
	  for(int i = 0; i < Numpro; i++){
		 Datos pro;
                  //variable que calcula el tiempo de ejecucion
		 Cuantum = (int)(Math.random()* 150-50+1)+50;
		 int TiempoEjecucion = Cuantum;
                 //variable que da un aleatorio para la cantidad de ram necesaria del proceso
                 int memoriaProceso = (int)(Math.random() * 50 - 3 +1) +3;
                 //variables para calcular el color del proceso para su graficacion
		 R = (int)(Math.random() * 255+1);
		 G = (int)(Math.random() * 255+1);
		 B = (int)(Math.random() * 255+1);
		 auxSuminta2 = auxSuminta2;
                 if(i==0){
                     //genera el primer proceso
                 pro = new Datos(x,y,i,-1,1,TiempoEjecucion,Cuantum, ""+(i+1),R,G,B,posicionM, "Formado","Formado","",posiciont,/*pinta,resmemo,MV,*/memoriaProceso,false);
                 //lo añade al arreglo
                 Proceso.add(pro);
                 //auxiliar para calculo de uso de ram
                 auxSuminta2 = auxSumaRam2 +memoriaProceso;
                 }else{
                     //auxiliar para calculo de uso de ram
              int  auxSuminta3 = auxSuminta2 + memoriaProceso;
                        //si hay espacio guarda en ram y le sasigna su bandera
                            if( auxSuminta3<= limiteRAM){
                        auxSuminta2 = auxSuminta2 + memoriaProceso;
                        lugar = false;
                        boolean auxlugar = lugar;
                        pro = new Datos(x,y,i,-1,1,TiempoEjecucion,Cuantum, ""+(i+1),R,G,B,posicionM, "Formado","Formado","",posiciont,/*pinta,resmemo,MV,*/memoriaProceso,auxlugar);
                        Proceso.add(pro);
                        //si no hay espacio se manda a memoria virtual y se le asigna la otra bandera
                        }else{
                            lugar = true;
                            boolean auxlugar = lugar;
                          pro = new Datos(x,y,i,-1,1,TiempoEjecucion,Cuantum, ""+(i+1),R,G,B,posicionM, "Formado","Formado","",posiciont,/*pinta,resmemo,MV,*/memoriaProceso,auxlugar);
                          Proceso.add(pro);
                        }
                        
                 }       		
	  }	
   }
	
	ActionListener detener = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{
					op=1;
					JOptionPane.showMessageDialog(null,"El proceso ya esta detenido");
					Bcontinuar.setEnabled(true);
					Bdetener.setEnabled(false);
				}catch(Exception evt){}
			}
	};
	
	ActionListener continuar = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{
					op=0;
					Bdetener.setEnabled(true);
					Bcontinuar.setEnabled(false);
					JOptionPane.showMessageDialog(null,"El proceso ya esta en marcha");
				}catch(Exception evt){}
			}
	};
	
	ActionListener proceso = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{
					int resp=Numpro;
					String texto = Cproceso.getText();
					Numpro = Integer.parseInt(texto);
					
					if(Numpro<=0 || Numpro > 200){
						JOptionPane.showMessageDialog(null,"Solo cantidades mayores a cero o cantidades menores iguales a 200");
						Numpro=resp;
					}
					if(Numpro>0){
						JOptionPane.showMessageDialog(null,"Procesos modificados a: "+Numpro);
						Cproceso.setText(""+Numpro);
					}
				}catch(Exception evt){}
			}
	};
	
        ActionListener ram = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{
					int resp=limiteRAM;
					String texto = Cram.getText();
					limiteRAM = Integer.parseInt(texto);
					
					if(limiteRAM<=0 || limiteRAM > 99999){
						JOptionPane.showMessageDialog(null,"Solo cantidades mayores a cero o cantidades menores iguales a 99999 mb (Aprox 97.6 gb)");
						limiteRAM=resp;
					}
					if(limiteRAM>0){
						JOptionPane.showMessageDialog(null,"Procesos modificados a: "+limiteRAM);
						Cram.setText(""+limiteRAM);
					}
				}catch(Exception evt){}
			}
	};
	
	ActionListener termina = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try{
					Proceso.clear();
					terminarprograma();
				}catch(Exception evt){}
			}
	};
   //*************************************************************************************************************************************************************
    //agrega el proceso ala tabla
    public void agregaT(int j){                                 
     datos.insertRow(datos.getRowCount(),new Object[]{Proceso.get(j).Nombre, Proceso.get(j).Estado, Proceso.get(j).transicion,Proceso.get(j).TiempoEjecucion, Proceso.get(j).Cuantum, Proceso.get(j).Recursos, Proceso.get(j).memoriaProceso+" Mb" + Proceso.get(j).lugar});
   }
    //metodo para terminar el programa
   public void terminarprograma(){
	int codigo=JOptionPane.showConfirmDialog(null, "Deseas volver a ejecutarlo?", "Terminar programa", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (codigo==JOptionPane.YES_OPTION){
			JOptionPane.showMessageDialog(null,"Para reiniciar ingresa manualmente el numero de procesos\n o deja en automatico,Presiona INICIAR SISTEMA");repaint();
			Numpro= (int)(Math.random() * (50-15 +1 ) + 15);
                        limiteRAM = 300;
			Cproceso.setEditable(true);
			Bproceso.setEnabled(true);
                        Cram.setEditable(true);
			Bram.setEnabled(true);
			Cproceso.setText(""+Numpro);
                        Cram.setText(""+limiteRAM);
			Bdetener.setEnabled(false);
			Biniciar.setEnabled(true);
			contadoelimt=0;
			contadort=0;
			op=0;
			desicion=0;
                        auxSumaRam2=0;
                        auxSuminta2=0;
                        auxSumaRam=0;
			memoriavirtual=0;
			Bdestroza.setEnabled(false);
			Bcontinuar.setEnabled(false);
			Refresh();
			repaint();
        }else if(codigo==JOptionPane.NO_OPTION){
           
            System.exit(0);
        }
        repaint();
   }
   //refresca los procesos de la tabla
   public void Refresh(){
		datos.setNumRows(0);
      	for (int j = 0; j < (contadort-contadoelimt) ; j++) {
         	datos.addRow(new Object[]{Proceso.get(j).Nombre, Proceso.get(j).Estado, Proceso.get(j).transicion,Proceso.get(j).TiempoEjecucion, Proceso.get(j).Cuantum, Proceso.get(j).Recursos, Proceso.get(j).memoriaProceso+" Mb"}); 
      	         
        }
	}
   
   public void acomodar(Graphics pelota, int j) throws InterruptedException{
      //movimiento de los procesos
         if(Proceso.get(j).estado == -1){
            if(j == 0){//CAMINA A LISTO
               if(Proceso.get(j).estado == -1 && Proceso.get(j).Y > y1+13){
                  Proceso.get(j).Y --;
               }
               if(Proceso.get(j).estado == -1 && Proceso.get(j).X > x1+13){
                  Proceso.get(j).X --;
               }
               if(Proceso.get(j).estado == -1 && Proceso.get(j).Y < y1+13){
                  Proceso.get(j).Y ++;
               }
               if(Proceso.get(j).estado == -1 && Proceso.get(j).X < x1+13){
                  Proceso.get(j).X ++;
               }
            }								
            if(j != 0){// PARA LAS DEMAS PELOTAS
               if(Proceso.get(j-1).estado >= 0 && Proceso.get(j).Y > y1+13){
                  Proceso.get(j).Y --;
               }
               if(Proceso.get(j-1).estado >= 0 && Proceso.get(j).X > x1+13){
                  Proceso.get(j).X --;
               }
               if(Proceso.get(j-1).estado >= 0 && Proceso.get(j).Y < y1+13){
                  Proceso.get(j).Y ++;
               }
               if(Proceso.get(j-1).estado >= 0 && Proceso.get(j).X < x1+13){
                  Proceso.get(j).X ++;
               }
            }
      }
      if(Proceso.get(j).X == (x1+13) && Proceso.get(j).Y == (y1+13)){//LLEGA A LISTO
         Proceso.get(j).estado = 0;//CAMBIA RUMBO A BLOQUEADO
         Proceso.get(j).Estado="Listo";
		 Proceso.get(j).transicion="Despachado";
		 Proceso.get(j).Recursos="_____________";
		 Refresh();

		if(Proceso.get(j).posiciont == -1){
			Proceso.get(j).posiciont = contadort;
                        //suma de los procesos
                        int auxSumita = auxSumaRam +Proceso.get(j).memoriaProceso;
                        if(auxSumita <= limiteRAM){
                        auxSumaRam += Proceso.get(j).memoriaProceso;
                        }else{
                          memoriavirtual+=Proceso.get(j).memoriaProceso;  
                        }
                        //se agregan ala tabla
			agregaT(j);	
			contadort++;
		}
      }
      //ejecucucion de metodos segun el estado del proceso se activara una funcion
      Ejecucion(pelota,j);
      Bloqueado(pelota,j);
      Formar(pelota,j);
      EliminarT(pelota,j);
      Sus_Listo(pelota,j);
      Sus_Bloqueado(pelota,j);
   }
		
   public void Ejecucion(Graphics pelota, int j){//CAMINA A EJECUCION
   	
       //si ele stado del proceso es igual a cero este se movera hacia ejecucion
      if(Proceso.get(j).X < x2+13 && Proceso.get(j).estado == 0)
         Proceso.get(j).X++;
         
      if(Proceso.get(j).Y < y2+13 && Proceso.get(j).estado == 0)
         Proceso.get(j).Y++;
   	    
      if(Proceso.get(j).X == (x2+13) && Proceso.get(j).Y == (y2+13)){//LLEGA A EJECUCION
		
                //Aleatorio si el proceso realizara una operacion matematica o hara uso de un recurso
                RoOPM = (int)(Math.random() * 1000+1);
		 //20% de que ocupe un recurso
		 if (RoOPM <= 200){
			Proceso.get(j).estado = 1;//CAMBIA RUMBO A BLOQUEADO
			//*****************************************************************************************************************************************
            //aleatorio que determina que tipo de recurso necesitara
                        int spool = (int)(Math.random() *5+1);
            AleatorioSpool = new Color(Proceso.get(j).r, Proceso.get(j).g, Proceso.get(j).b);
			switch(spool){
				case 1:	   lbrat.setForeground(AleatorioSpool);
						   Proceso.get(j).Recursos="Raton";
						break;
				case 2:		lbtecla.setForeground(AleatorioSpool);
							Proceso.get(j).Recursos="Teclado";
						break;
				case 3:		Proceso.get(j).Recursos="Monitor";
							lbmon.setForeground(AleatorioSpool);
						break;
				case 4:		Proceso.get(j).Recursos="Impresora";
							lbimp.setForeground(AleatorioSpool);
						break;
				case 5:		Proceso.get(j).Recursos="Bocinas";
							lbboci.setForeground(AleatorioSpool);
						break;
			}
            //***************************************************************************************************************************************** 
			Proceso.get(j).Estado="Ejecucion";
			Proceso.get(j).transicion="Bloqueado";
                        //se refresca la tabla para cambiar el estado de los procesos
			Refresh();
			
                        //80% de que sea una operacion matematica
		 }else{
			Proceso.get(j).estado = 2;//CAMBIA RUMBO A PUNTO CERCA DEL INICIAL
			Proceso.get(j).Recursos="Operacion matematica";
			Proceso.get(j).Estado="Ejecucion";
			Proceso.get(j).transicion="Formardo";
			Refresh();
			
		}
                 //aleatorio para determinar el quantum que tendra al momento de ejcutarse
		int alea =(int)(Math.random() * 15-5+1)+5;
         Proceso.get(j).Cuantum = Proceso.get(j).Cuantum - alea;//DESCUENTA un tiempo aleatorio AL CUANTUM

         //si el cuantum es menor o igual a cero se elimia el proceso
         if(Proceso.get(j).Cuantum <= 0){
			LiberaRecurso();
			Proceso.get(j).Recursos="_____________";
		    Proceso.get(j).estado = 6;//CAMBIA RUMBO A ELIMINART
		    Proceso.get(j).Cuantum= 0;
                    if(Proceso.get(j).lugar == false){
                    auxSumaRam -= Proceso.get(j).memoriaProceso;
                    }
                    if(Proceso.get(j).lugar == true){
                        memoriavirtual -= Proceso.get(j).memoriaProceso;
                    }
		    Proceso.get(j).Estado="Ejecucion";
			Proceso.get(j).transicion="Terminado";
			
                        Refresh();
			
         }         
         Refresh();
         
      }		
   }
//***************************************************************BLOQUEADO********************************************************
   //metodo encargado de lo que hara el proceso cuando entre a bloqueado
   public void Bloqueado(Graphics pelota, int j){//CAMINA A BLOQUEADO
      if(Proceso.get(j).estado == 1){ 
         if( Proceso.get(j).X < x3+13)
             Proceso.get(j).X ++;
            	
         if(Proceso.get(j).Y < y3+13) 
            Proceso.get(j).Y++;
         
         if( Proceso.get(j).X > x3+13)
             Proceso.get(j).X --;
            	
         if(Proceso.get(j).Y > y3+13) 
            Proceso.get(j).Y --;
          
         if((Proceso.get(j).X == (x3+13)) && (Proceso.get(j).Y == (y3+13))){//LLEGA A BLOQUEADO
            LiberaRecurso();
            Proceso.get(j).Recursos="_____________";
            
            //aleatorio para saber si se queda en bloqueado o se vuelve a formar el proceso
            SB = (int)(Math.random() * (1000) +1);
            
            Proceso.get(j).Estado="Bloqueado";
			Refresh();	      
			//20% de que se quede en bloqueado      	
               if(SB <= 200 ){
                  Proceso.get(j).estado = 8;//SE VA A SUSPENDIDO BLOQUEADO
				  Proceso.get(j).Recursos="_____________";
				  Proceso.get(j).transicion="Suspendido-bloqueado";
				  Refresh();	
				  
				}
               //80% de que se vuelva a formar el proceso
               if(SB > 200){
                  Proceso.get(j).estado = 2;//SE VA A FORMAR DE NUEVO AL PUNTO INICIAL
                  Proceso.get(j).Recursos="_____________";
                  Proceso.get(j).transicion="Despertar";
				  Refresh();	
				  
			   } 		       
         }
      }
   }
//*****************************************************************PUNTO DE ELIMINACION********************************************
   public void EliminarT(Graphics pelota, int j){
      if(Proceso.get(j).estado == 6){//camina a eliminar
         if(Proceso.get(j).Y < ye+13)
            Proceso.get(j).Y ++;
            
         if(Proceso.get(j).X < xe+13)
            Proceso.get(j).X ++;
            
         if(Proceso.get(j).Y > ye+13)
            Proceso.get(j).Y --;
            
         if(Proceso.get(j).X > xe+13)
            Proceso.get(j).X --;
      	
         if((Proceso.get(j).X == (xe+13)) && (Proceso.get(j).Y == (ye+13))){
			Numpro = Numpro-1;    
		    Refresh();
		    
		    contadoelimt++;
		    
	           //se elimina el proceso		
		    Proceso.remove(j);
		    Proceso.get(j).Estado="Terminado";
			Proceso.get(j).transicion="Terminado";
		    Refresh();
		   
		}
	  }
   }
//******************************************************************SUSPENDIDO LISTO*********************************************
   public void Sus_Listo(Graphics g, int j){
      if(Proceso.get(j).estado == 7){
         if(Proceso.get(j).X > x3+14)
            Proceso.get(j).X --;
            	
         if(Proceso.get(j).Y > y3+14)
            Proceso.get(j).Y --;
            
         if(Proceso.get(j).X < x3+14)
            Proceso.get(j).X ++;
            	
         if(Proceso.get(j).Y < y3+14)
            Proceso.get(j).Y ++;
            
         if((Proceso.get(j).X == (x3+14)) && (Proceso.get(j).Y == (y3+14)))
            Proceso.get(j).estado = 2;
         
         Proceso.get(j).Estado="Suspendido-Listo";
		 Proceso.get(j).transicion="Formado";
		 Refresh();
		 
      }
   }
//*****************************************************************FORMAR******************************************************	
   public void Formar(Graphics pelota, int j){// CAMINA A PUNTO CERCANO DEL INICIAL
      if(Proceso.get(j).estado == 2){
         if(Proceso.get(j).X  > x)
            Proceso.get(j).X  --;
            	
         if(Proceso.get(j).Y > y)
            Proceso.get(j).Y --;
            
         if(Proceso.get(j).X  < x)
            Proceso.get(j).X  ++;
            	
         if(Proceso.get(j).Y < y)
            Proceso.get(j).Y ++;
            
         if((Proceso.get(j).X  == x) && (Proceso.get(j).Y == y)){//LLEGA A PUNTO CERCANO AL INICIAL 
			Proceso.add(Proceso.size(), Proceso.get(j));
            Proceso.get(j).Recursos="_____________";
            Proceso.get(j).estado = -1;
            Proceso.get(j).Estado="Formado";
			Proceso.get(j).transicion="Formado";
			Proceso.remove(j);
			Refresh();
			
         }        		
      }
   }
//***************************************************************SUSPENDIDO-BLOQUEADO************************************
   public void Sus_Bloqueado(Graphics g, int j){
      if(Proceso.get(j).estado == 8){ 
         if(Proceso.get(j).Y > y3+12)
            Proceso.get(j).Y --;
            
         if(Proceso.get(j).X > x3+12)
            Proceso.get(j).X --;
         
         if(Proceso.get(j).Y < y3+12)
            Proceso.get(j).Y ++;
            
         if(Proceso.get(j).X < x3+12)
            Proceso.get(j).X ++;
             	
         if(Proceso.get(j).X  == (x3+12) && Proceso.get(j).Y == (y3+12) ){
            Liberar = (int)(Math.random() * 1000 +1);
           	Proceso.get(j).Estado="Suspendido-bloqueado";		
           		
            if(Liberar > 200){
               Proceso.get(j).estado = 7;
            Proceso.get(j).transicion="Suspendido-listo";
			Refresh();
            }
            if(Liberar <= 200){
               Proceso.get(j).estado = 1;
            Proceso.get(j).transicion="Bloqueado";
			Refresh();
            }
         }
      }
   }
//******************************************************Libera recurso*******************************************************************
   public void LiberaRecurso(){
		lbrat.setForeground(Color.WHITE);
		lbtecla.setForeground(Color.WHITE);
		lbmon.setForeground(Color.WHITE);
		lbimp.setForeground(Color.WHITE);
		lbboci.setForeground(Color.WHITE);
	}	  
 //*****************************************************************Paint*****************************************************************
   //metodo que se encarga de generar los graficos
   @Override
   public void paint(Graphics pelota) {
      super.paint(pelota);
      Escenario(pelota);
      try{
          
			 for(int i = 0; i < Proceso.size(); i++){
                             
				if(op==0){
                                
					acomodar(pelota,i);
                                        
				}
				Aleatorio = new Color(Proceso.get(i).r, Proceso.get(i).g, Proceso.get(i).b);
				
				pelota.setColor(Aleatorio); 
				pelota.fillOval(Proceso.get(i).X,Proceso.get(i).Y,15,15);
				
				pelota.setColor(Color.BLACK); 
				pelota.drawString(Proceso.get(i).Nombre, (Proceso.get(i).X+4),(Proceso.get(i).Y));
				  
			 } 
                         Refresh(); 
                         repaint();
	  }catch(Exception e){}
		 
   }
 //*********************************************INTERFAZ*********************************************************	
   //metodo que se encarga de mostrar etiquetas a cada zona por done va a psar los procesos
   //y mostrar cuantod procesos hay y la cantidad de ram utilizada
   public void Escenario(Graphics pelota){
       
       
      super.paint(pelota); 

      pelota.setColor(Color.BLACK);
      pelota.drawString("LISTO", x1,y1);
      pelota.drawString("EJECUCION", x2-8,y2);
      pelota.drawString("BLOQUEADO", x3-10,y3+1);
      pelota.drawString("ELIMINADO/TERMINADO", xe-30,ye);
      pelota.drawString("Procesos actuales  : "+Numpro, 10, 265);
      pelota.drawString("Procesos eliminados: "+contadoelimt, 10, 295);
      pelota.drawString("Uso de memoria virtual: "+memoriavirtual+" Mb", 10, 325);
      pelota.drawString("Memoria RAM utilizada: " + auxSumaRam+ " / "+ limiteRAM + " Mb",10,355);
      //*********************************************************************************************************************************************		
	  pelota.setColor(Color.BLACK);
          pelota.drawLine(0, 0, 450, 0);
          pelota.setColor(Color.BLACK);
	  pelota.drawLine(450,500,450,0);
          pelota.setColor(Color.BLACK);
          pelota.drawLine(0, 249, 450, 249);
          pelota.setColor(Color.BLACK);
          pelota.drawLine(0, 250, 450, 250);
	  pelota.setColor(Color.gray);
	  pelota.fillOval(x1,y1,40,40);
	  pelota.fillOval(x2,y2,40,40);
	  pelota.fillOval(x3,y3,40,40);
	  pelota.fillOval(xe,ye,40,40);
	  
	  
	  if(Numpro == 0){
		terminarprograma();repaint();
		Bdestroza.setEnabled(false);
	  }
	  repaint();    
   }

   public static void main(String[] args){
       //mensaje de inicio del programa
	  JOptionPane.showMessageDialog(null,"Se iniciaran "+Numpro+" procesos al presionar el boton INICIAR SISTEMA por default ");
      //genera la pantalla principal
          JFrame Ventana = new JFrame("Procesos de CPU");
      Ventana.setSize(1200,471);
      Ventana.getContentPane().add( new PruebitaProcesos(),BorderLayout.CENTER);
      Ventana.setVisible(true);
      Ventana.setResizable(false);
	  Ventana.setLocationRelativeTo(null);
      Ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

}
