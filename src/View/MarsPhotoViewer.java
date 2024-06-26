package View;

import Controller.NasaApiController;
import Model.MarsPhoto;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

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
        setSize(800, 600);
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

        for (MarsPhoto photo : photos) {
            JPanel singlePhotoPanel = new JPanel();
            singlePhotoPanel.setLayout(new BoxLayout(singlePhotoPanel, BoxLayout.Y_AXIS));

            JLabel dateLabel = new JLabel("Date: " + photo.getEarthDate().toString());
            JLabel cameraLabel = new JLabel("Camera: " + photo.getCameraName() + " (" + photo.getCameraFullName() + ")");
            JLabel roverLabel = new JLabel("Rover: " + photo.getRoverName() + " - Sol " + photo.getMaxSol());

            JEditorPane linkPane = new JEditorPane("text/html", "<a href='" + photo.getImgSrc() + "'>" + photo.getImgSrc() + "</a>");
            linkPane.setEditable(false);
            linkPane.setOpaque(false);
            linkPane.addHyperlinkListener(new HyperlinkListener() {
                @Override
                public void hyperlinkUpdate(HyperlinkEvent e) {
                    if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                        try {
                            Desktop.getDesktop().browse(e.getURL().toURI());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });

            singlePhotoPanel.add(dateLabel);
            singlePhotoPanel.add(cameraLabel);
            singlePhotoPanel.add(roverLabel);
            singlePhotoPanel.add(linkPane);

            photoPanel.add(singlePhotoPanel);
        }

        photoPanel.revalidate();
        photoPanel.repaint();
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
