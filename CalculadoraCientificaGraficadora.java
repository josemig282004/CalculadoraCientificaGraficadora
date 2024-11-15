   
package calculadoracientificagraficadora;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Stack;

public class CalculadoraCientificaGraficadora extends JFrame implements ActionListener {
    private JTextField pantalla;
    private JPanel panelDibujo;
    private String funcionActual = "";
    
    public CalculadoraCientificaGraficadora() {
        setTitle("Calculadora Científica Graficadora");
        setSize(800, 600);
        setLayout(new BorderLayout());
        
        // Pantalla de la calculadora
        pantalla = new JTextField();
        add(pantalla, BorderLayout.NORTH);
        
        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(5, 5));

        String[] botones = {
            "7", "8", "9", "/", "sin",
            "4", "5", "6", "*", "cos",
            "1", "2", "3", "-", "tan",
            "0", ".", "=", "+", "log",
            "C", "(", ")", "^", "Graficar"
        };
        
        for (String texto : botones) {
            JButton boton = new JButton(texto);
            boton.addActionListener(this);
            panelBotones.add(boton);
        }
        add(panelBotones, BorderLayout.CENTER);
        
        // Panel para el dibujo
        panelDibujo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (!funcionActual.isEmpty()) {
                    dibujarFuncion(g, funcionActual);
                }
            }
        };
        panelDibujo.setPreferredSize(new Dimension(800, 300));
        add(panelDibujo, BorderLayout.SOUTH);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.equals("C")) {
            pantalla.setText("");
        } else if (comando.equals("=")) {
            // Evaluar expresión matemática
            String expresion = pantalla.getText();
            try {
                double resultado = evaluarExpresion(expresion);
                pantalla.setText(String.valueOf(resultado));
            } catch (Exception ex) {
                pantalla.setText("Error");
            }
        } else if (comando.equals("Graficar")) {
            funcionActual = pantalla.getText();
            panelDibujo.repaint();
        } else {
            pantalla.setText(pantalla.getText() + comando);
        }
    }
    
    // Método para evaluar la expresión ingresada
    private double evaluarExpresion(String expresion) {
        return Math.sin(Double.parseDouble(expresion)); // Solo un ejemplo con sin(x)
    }

    // Método para dibujar la función
    private void dibujarFuncion(Graphics g, String funcion) {
        g.setColor(Color.red);
        int ancho = panelDibujo.getWidth();
        int altura = panelDibujo.getHeight();
        g.drawLine(0, altura / 2, ancho, altura / 2); // Eje X
        g.drawLine(ancho / 2, 0, ancho / 2, altura); // Eje Y
        
        for (int x = -360; x < 360; x++) {
            double radianes = Math.toRadians(x);
            double y = 0;
            
            if (funcion.contains("sin")) {
                y = Math.sin(radianes);
            } else if (funcion.contains("cos")) {
                y = Math.cos(radianes);
            } else if (funcion.contains("tan")) {
                y = Math.tan(radianes);
            }
            
            int xPixel = x + ancho / 2;
            int yPixel = (int) (altura / 2 - y * 50);
            
            if (xPixel >= 0 && xPixel < ancho && yPixel >= 0 && yPixel < altura) {
                g.fillRect(xPixel, yPixel, 2, 2);
            }
        }
    }

    public static void main(String[] args) {
        CalculadoraCientificaGraficadora calculadora = new CalculadoraCientificaGraficadora();
        calculadora.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calculadora.setVisible(true);
    }
}

