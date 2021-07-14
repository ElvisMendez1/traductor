package com.mycompany.traductor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javax.swing.JOptionPane;

public class Server {

    public static void main(String[] args) {

        try {
            JOptionPane.showMessageDialog(null,"Inicinado el servidor...");

           
            DatagramSocket ServerUdp = new DatagramSocket(5001);

            JOptionPane.showMessageDialog(null,"servidor Iniciado...");
            
            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket datos = new DatagramPacket(buffer, buffer.length);

                
                ServerUdp.receive(datos);

                
                dato ConvTR = (dato) serial.des(datos.getData());
           

                
                dato Recv_Trad = Busc_Trad(ConvTR);
               
                
                byte[] mensaje = serial.ser(Recv_Trad);        
                
                
                DatagramPacket responde = new DatagramPacket(mensaje, mensaje.length, datos.getAddress(), datos.getPort());

                
                ServerUdp.send(responde);
              
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
    }

    
     private static dato Busc_Trad(dato Conv_TR) {
        
         dato objeto = new dato();
        
        File fichero = new File("traduct.txt");
        boolean error = false;

        try {
            
            BufferedReader br = new BufferedReader(new FileReader(fichero));
            String linea = "";
            
            int limite = 0;

            while ((linea = br.readLine()) != null) {
                
                if (linea.contains(Conv_TR.palabra)) {
                    
                    limite = linea.indexOf(":");
                    objeto.tipo = 1;
                    error = true;

                    if (Conv_TR.tipo == 0) {
                        
                        objeto.palabra = linea.substring(limite + 1, linea.length());
                       
                    } else {
                        
                    
                          objeto.palabra = linea.substring(0, limite);
                    }

                    break;
                }
            }

            
            if (!error) {
                objeto.tipo = 2;
                objeto.palabra = "No se encontro esta palabra";
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }

       
        return objeto;
    }

}
