package View;

import Controller.NasaApiController;
import Model.MarsPhoto;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarsPhotoViewer extends JFrame {
    private static final String[] ROVER_OPTIONS = {"Curiosity", "Opportunity", "Spirit"};
    private static final String[] CAMERA_OPTIONS = {"FHAZ", "RHAZ", "MAST", "CHEMCAM", "MAHLI", "MARDI", "NAVCAM", "PANCAM", "MINITES"};

    private JComboBox<String> roverComboBox;
    private JComboBox<String> cameraComboBox;
    private JTextField solTextField;
    private JTextField startDateField;
    private JTextField endDateField;
    private JButton fetchButton;
    private JPanel photoPanel;
    private JLabel statusLabel;

    private NasaApiController apiController;

    public MarsPhotoViewer() {
        setTitle("Mars Photo Viewer");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        roverComboBox = new JComboBox<>(ROVER_OPTIONS);
        cameraComboBox = new JComboBox<>(CAMERA_OPTIONS);
        solTextField = new JTextField(5);
        startDateField = new JTextField(10);
        startDateField.setText("YYYY-MM-DD");
        endDateField = new JTextField(10);
        endDateField.setText("YYYY-MM-DD");
        fetchButton = new JButton("Fetch Photos");

        controlPanel.add(new JLabel("Rover:"));
        controlPanel.add(roverComboBox);
        controlPanel.add(new JLabel("Camera:"));
        controlPanel.add(cameraComboBox);
        controlPanel.add(new JLabel("Sol:"));
        controlPanel.add(solTextField);
        controlPanel.add(new JLabel("Start Date:"));
        controlPanel.add(startDateField);
        controlPanel.add(new JLabel("End Date:"));
        controlPanel.add(endDateField);
        controlPanel.add(fetchButton);

        add(controlPanel, BorderLayout.NORTH);

        photoPanel = new JPanel();
        photoPanel.setLayout(new BoxLayout(photoPanel, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical alignment
        add(new JScrollPane(photoPanel), BorderLayout.CENTER);

        statusLabel = new JLabel();
        add(statusLabel, BorderLayout.SOUTH);

        fetchButton.addActionListener(e -> fetchPhotos());

        apiController = new NasaApiController();
    }

    private void fetchPhotos() {
        try {
            String rover = (String) roverComboBox.getSelectedItem();
            String camera = (String) cameraComboBox.getSelectedItem();
            String solText = solTextField.getText();
            String startDateText = startDateField.getText();
            String endDateText = endDateField.getText();

            if (solText.isEmpty() || startDateText.isEmpty() || endDateText.isEmpty()) {
                showErrorMessage("Por favor ingrese todos los datos requeridos.");
                return;
            }

            int sol = Integer.parseInt(solText);
            LocalDate startDate = parseDate(startDateText);
            LocalDate endDate = parseDate(endDateText);

            List<MarsPhoto> photos = apiController.getPhotos(rover, sol, camera, startDate, endDate);
            displayPhotos(photos);
            statusLabel.setText("Fotos cargadas correctamente.");
        } catch (Exception ex) {
            ex.printStackTrace();
            showErrorMessage("Error al obtener las fotos: " + ex.getMessage());
        }
    }

    private LocalDate parseDate(String dateText) {
        try {
            return LocalDate.parse(dateText);
        } catch (DateTimeParseException e) {
            showErrorMessage("Formato de fecha inválido. Utilice el formato YYYY-MM-DD.");
            throw e;
        }
    }

    private void displayPhotos(List<MarsPhoto> photos) {
        photoPanel.removeAll();

        // Crear un mapa para almacenar la información única de cada foto
        Map<String, MarsPhoto> uniquePhotos = new HashMap<>();

        // Iterar sobre todas las fotos
        for (MarsPhoto photo : photos) {
            String key = photo.getEarthDate() + "-" + photo.getRoverName() + "-" + photo.getMaxSol() + "-" + photo.getCameraName();

            // Si la foto no está en el mapa, agrégala al mapa y crea su panel correspondiente
            if (!uniquePhotos.containsKey(key)) {
                uniquePhotos.put(key, photo);
            }
        }

        // Iterar sobre las fotos únicas y crear los paneles correspondientes
        for (MarsPhoto uniquePhoto : uniquePhotos.values()) {
            JPanel singlePhotoPanel = new JPanel();
            singlePhotoPanel.setLayout(new BoxLayout(singlePhotoPanel, BoxLayout.Y_AXIS));

            JLabel dateLabel = new JLabel("Date: " + uniquePhoto.getEarthDate().toString());
            JLabel cameraLabel = new JLabel("Camera: " + uniquePhoto.getCameraName() + " (" + uniquePhoto.getCameraFullName() + ")");
            JLabel roverLabel = new JLabel("Rover: " + uniquePhoto.getRoverName() + " - Sol " + uniquePhoto.getMaxSol());
            JLabel linkLabel = new JLabel("<html><a href='" + uniquePhoto.getImgSrc() + "'>" + uniquePhoto.getImgSrc() + "</a></html>");
            JLabel imageLabel = new JLabel();

            singlePhotoPanel.add(dateLabel);
            singlePhotoPanel.add(cameraLabel);
            singlePhotoPanel.add(roverLabel);
            singlePhotoPanel.add(linkLabel);
            singlePhotoPanel.add(imageLabel);

            // Agregar acción al enlace para abrir la imagen en una nueva ventana
            linkLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    openImageInNewWindow(uniquePhoto.getImgSrc());
                }
            });

            photoPanel.add(singlePhotoPanel);
        }

        photoPanel.revalidate();
        photoPanel.repaint();
    }

    private void openImageInNewWindow(String imageUrl) {
        SwingUtilities.invokeLater(() -> {
            JFrame imageFrame = new JFrame("Image Viewer");
            imageFrame.setSize(800, 600);
            imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JLabel imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

            SwingWorker<ImageIcon, Void> worker = new SwingWorker<>() {
                @Override
                protected ImageIcon doInBackground() throws Exception {
                    try {
                        return new ImageIcon(new URL(imageUrl));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                        return null;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override
                protected void done() {
                    try {
                        ImageIcon icon = get();
                        if (icon != null) {
                            imageLabel.setIcon(icon);
                        } else {
                            imageLabel.setText("Error al cargar la imagen");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        imageLabel.setText("Error al cargar la imagen");
                    }
                }
            };
            worker.execute();

            imageFrame.add(new JScrollPane(imageLabel), BorderLayout.CENTER);
            imageFrame.setVisible(true);
        });
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        statusLabel.setText(message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MarsPhotoViewer viewer = new MarsPhotoViewer();
            viewer.setVisible(true);
        });
    }
}
