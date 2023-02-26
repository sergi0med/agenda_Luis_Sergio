package agenda3;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

//import Agenda.Agenda.Contacto;

public class Agenda extends JFrame implements ActionListener{
	private ArrayList<Contacto> contactos;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaContactos;
    private JButton botonAgregar;
    private JButton botonBorrar;
    private JButton botonEditar;
    private JButton botonBuscar;
    private JButton mostrarDatos;
    private JLabel Label;
    

    public Agenda() {
        super("Agenda");
        
     // Crear un ImageIcon a partir de una imagen en un archivo
        ImageIcon icono = new ImageIcon("icon.png");

        // Establecer el icono de la ventana
        setIconImage(icono.getImage());
        
        setLocation(360, 200); // Establecer la posición de la ventana en (300, 200)
        

        // Inicializar la lista de contactos
        contactos = new ArrayList<Contacto>();

        // Crear el modelo para la lista de contactos
        modeloLista = new DefaultListModel<String>();

        // Crear la lista de contactos
        listaContactos = new JList<String>(modeloLista);

        // Agregar la lista de contactos a un panel con barra de desplazamiento
        JScrollPane scrollLista = new JScrollPane(listaContactos);
        JPanel panelCampos = new JPanel();
                
                mostrarDatos = new JButton("Mostrar Datos");
                mostrarDatos.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                        int indiceSeleccionado = listaContactos.getSelectedIndex();

                        Contacto contactoSeleccionado = contactos.get(indiceSeleccionado);
                		JTextField campoNombreEditar = new JTextField(contactoSeleccionado.getNombre());
                        JTextField campoNotasEditar = new JTextField(contactoSeleccionado.getNotas());
                        JOptionPane.showMessageDialog(null,"Nombre: "+campoNombreEditar.getText()
                        		+ "\nNúmero: "+campoNotasEditar.getText());
                        Label.setVisible(true);
                        JOptionPane.showMessageDialog(null, Label, "Título de la ventana", JOptionPane.PLAIN_MESSAGE);
                	}
                });
                panelCampos.add(mostrarDatos);
        
                // Crear los botones
                botonAgregar = new JButton("Agregar");
                panelCampos.add(botonAgregar);
                
                botonAgregar.addActionListener(this);
        botonBorrar = new JButton("Borrar");
        panelCampos.add(botonBorrar);
        botonBorrar.addActionListener(this);
        botonEditar = new JButton("Editar");
        panelCampos.add(botonEditar);
        botonEditar.setEnabled(false); // El botón "Editar" está desactivado al inicio
        botonEditar.addActionListener(this);
        botonBuscar = new JButton("Buscar");
        panelCampos.add(botonBuscar);
        botonBuscar.addActionListener(this);
        getContentPane().add(scrollLista, BorderLayout.CENTER);
        getContentPane().add(panelCampos, BorderLayout.SOUTH);
        
        Label = new JLabel("");
		Label.setVisible(false);

        panelCampos.add(Label);
        listaContactos.addListSelectionListener(e -> {
            // Activar el botón "Editar" si se ha seleccionado un contacto de la lista
            botonEditar.setEnabled(!listaContactos.isSelectionEmpty());
        });

        // Configurar la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(644, 375);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Agenda();
        
    }

    public void actionPerformed(ActionEvent e) {
    	
//    		JFileChooser jf = new JFileChooser();
//    		jf.setMultiSelectionEnabled(false);
//    		if(jf.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
//    		rsdragdropfiles.RSDragDropFiles.setCopiar(jf.getSelectedFile().toString(),"src/img/logo.png");
//    		}
    	 String nombre1 = null ;
         String numero1 = null ;
        if (e.getSource() == botonAgregar) {
        	//Añadir imagen
    		JFileChooser jf = new JFileChooser();
    		jf.setMultiSelectionEnabled(false);
    		if(jf.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
    		rsdragdropfiles.RSDragDropFiles.setCopiar(jf.getSelectedFile().toString(),"src/img/logo.png");
    		Label.setIcon(new ImageIcon(jf.getSelectedFile().toString())); 
    		Label.setPreferredSize(new Dimension(200, 200)); // Establecer un tamaño preferido de 200x200 píxeles
    		Label.setMaximumSize(new Dimension(300, 300)); // Establecer un tamaño preferido de 200x200 píxeles
    		}
        	 // Crear el panel con dos campos de entrada de texto
            JPanel panel = new JPanel(new GridLayout(2, 2));
            JTextField nombre = new JTextField();
            JTextField numero = new JTextField();
            panel.add(new JLabel("Nombre:"));
            panel.add(nombre);
            panel.add(new JLabel("Numero:"));
            panel.add(numero);

            // Mostrar la ventana de diálogo personalizada
            int result = JOptionPane.showConfirmDialog(null, panel, "Entrada de datos", JOptionPane.OK_CANCEL_OPTION);

            // Obtener los datos ingresados por el usuario
            if (result == JOptionPane.OK_OPTION) {
            	
                String dato1 = nombre.getText();
                String dato2 = numero.getText();
                 nombre1 = dato1;
                 numero1 = dato2;
                System.out.println("Nombre: " + dato1);
                System.out.println("Numero: " + dato2);
            } else {
                System.out.println("Cancelado");
            }
            // Obtener el nombre y las notas del contacto
        	
         

            // Verificar que se haya ingresado un nombre
            if (nombre1.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un nombre.");
                return;
            }

            // Agregar el contacto a la lista
            Contacto nuevoContacto = new Contacto(nombre1, numero1);
            contactos.add(nuevoContacto);

            // Actualizar la lista de contactos
            actualizarLista();

            // Limpiar los campos de texto
         //   campoNombre.setText("");
          //  campoNumero.setText("");
        } else if (e.getSource() == botonBorrar) {
            // Obtener el índice del contacto seleccionado
            int indiceSeleccionado = listaContactos.getSelectedIndex();

            // Verificar que se haya seleccionado un contacto
            if (indiceSeleccionado == -1) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un contacto.");
                return;
            }

            // Borrar el contacto de la lista
            contactos.remove(indiceSeleccionado);

            // Actualizar la lista de contactos
            actualizarLista();
        } else if (e.getSource() == botonEditar) {
            // Obtener el índice del contacto seleccionado
            int indiceSeleccionado = listaContactos.getSelectedIndex();

            // Obtener el contacto seleccionado
            Contacto contactoSeleccionado = contactos.get(indiceSeleccionado);

            // Crear una ventana emergente para editar el contacto seleccionado
            JTextField campoNombreEditar = new JTextField(contactoSeleccionado.getNombre());
            JTextField campoNotasEditar = new JTextField(contactoSeleccionado.getNotas());
            Object[] camposEditar = {
                    "Nombre:", campoNombreEditar,
                    "Numero:", campoNotasEditar
            };
            
            int resultado = JOptionPane.showConfirmDialog(this, camposEditar, "Editar contacto", JOptionPane.OK_CANCEL_OPTION);
            if (resultado == JOptionPane.OK_OPTION) {
                // Actualizar el nombre y las notas del contacto
                contactoSeleccionado.setNombre(campoNombreEditar.getText());
                contactoSeleccionado.setNotas(campoNotasEditar.getText());

                // Actualizar la lista de contactos
                actualizarLista();
            }
        } else if (e.getSource() == botonBuscar) {
            // Obtener el nombre del contacto a buscar
            String nombreBuscar = JOptionPane.showInputDialog(null, "Introduce el nombre que desea: ");

            // Verificar que se haya ingresado un nombre
            if (nombreBuscar.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un nombre.");
                return;
            } 

            // Buscar el contacto en la lista
            int indiceEncontrado = -1;
        	
            for (int i = 0; i < contactos.size(); i++) {
                if (contactos.get(i).getNombre().equals(nombreBuscar)) {
                    indiceEncontrado = i;
                    break;
                }
            }
         // Mostrar el resultado de la búsqueda
            if (indiceEncontrado != -1) {
                listaContactos.setSelectedIndex(indiceEncontrado);
                listaContactos.ensureIndexIsVisible(indiceEncontrado);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró ningún contacto con el nombre especificado.");
            }
        }
    }

    private void actualizarLista() {
        // Obtener la lista de nombres de los contactos
        String[] nombres = new String[contactos.size()];
        for (int i = 0; i < contactos.size(); i++) {
            nombres[i] = contactos.get(i).getNombre();
        }

        // Actualizar la lista de contactos
        listaContactos.setListData(nombres);
    }

    private class Contacto {
        private String nombre;
        private String notas;

        public Contacto(String nombre, String notas) {
            this.nombre = nombre;
            this.notas = notas;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getNotas() {
            return notas;
        }

        public void setNotas(String notas) {
            this.notas = notas;
        }

        @Override
        public String toString() {
            return nombre;
        }
    }
//
}
