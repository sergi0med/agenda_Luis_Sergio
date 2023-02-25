package agenda3;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Agenda extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Contacto> contactos;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaContactos;
    private JButton botonAgregar;
    private JButton botonBorrar;
    private JButton botonEditar;
    private JButton botonBuscar;
    private JTextField campoNombre;
    private JTextField campoNotas;

    public Agenda() {
        super("Agenda");

        // Inicializar la lista de contactos
        contactos = new ArrayList<Contacto>();

        // Crear el modelo para la lista de contactos
        modeloLista = new DefaultListModel<String>();

        // Crear la lista de contactos
        listaContactos = new JList<String>(modeloLista);

        // Agregar la lista de contactos a un panel con barra de desplazamiento
        JScrollPane scrollLista = new JScrollPane(listaContactos);

        // Crear los paneles para los botones y los campos de texto
        JPanel panelBotones = new JPanel();
        JPanel panelCampos = new JPanel();
                
                        // Crear las etiquetas para los campos de texto
                        JLabel Nombre = new JLabel("Nombre:");
                        panelBotones.add(Nombre);
        
                // Crear los campos de texto para ingresar el nombre y las notas del contacto
                campoNombre = new JTextField(20);
                panelBotones.add(campoNombre);
        
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

        // Agregar los paneles a la ventana
        getContentPane().add(panelBotones, BorderLayout.NORTH);
        JLabel etiquetaNotas = new JLabel("Numero:");
        panelBotones.add(etiquetaNotas);
        campoNotas = new JTextField(20);
        panelBotones.add(campoNotas);
        getContentPane().add(scrollLista, BorderLayout.CENTER);
        getContentPane().add(panelCampos, BorderLayout.SOUTH);
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
        if (e.getSource() == botonAgregar) {
            // Obtener el nombre y las notas del contacto
            String nombre = campoNombre.getText();
            String notas = campoNotas.getText();

            // Verificar que se haya ingresado un nombre
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un nombre.");
                return;
            }

            // Agregar el contacto a la lista
            Contacto nuevoContacto = new Contacto(nombre, notas);
            contactos.add(nuevoContacto);

            // Actualizar la lista de contactos
            actualizarLista();

            // Limpiar los campos de texto
            campoNombre.setText("");
            campoNotas.setText("");
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
            String nombreBuscar = campoNombre.getText();

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