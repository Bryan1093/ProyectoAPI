package View;

import Controller.NasaApiController;
import Model.MarsPhoto;
import Model.MusicPlayer;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
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
    private JButton playMusicButton;
    private JPanel photoPanel;
    private JLabel statusLabel;
    private JDialog loadingDialog;
    private MusicPlayer musicPlayer;

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
        playMusicButton = new JButton("Play Music");

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
        controlPanel.add(playMusicButton);

        add(controlPanel, BorderLayout.NORTH);

        photoPanel = new JPanel();
        photoPanel.setLayout(new BoxLayout(photoPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(photoPanel), BorderLayout.CENTER);

        statusLabel = new JLabel();
        add(statusLabel, BorderLayout.SOUTH);

        fetchButton.addActionListener(e -> fetchPhotos());

        playMusicButton.addActionListener(e -> playMusic());

        apiController = new NasaApiController();

        // Initialize loading dialog
        loadingDialog = new JDialog(this, "Loading", Dialog.ModalityType.APPLICATION_MODAL);
        JPanel loadingPanel = new JPanel(new BorderLayout());
        JLabel loadingLabel = new JLabel("Cargando...", JLabel.CENTER);
        loadingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        loadingPanel.add(loadingLabel, BorderLayout.CENTER);
        loadingDialog.getContentPane().add(loadingPanel);
        loadingDialog.setSize(300, 200);
        loadingDialog.setLocationRelativeTo(this);

        musicPlayer = new MusicPlayer();
    }

    private void setLoading(boolean isLoading) {
        SwingUtilities.invokeLater(() -> {
            if (isLoading) {
                loadingDialog.setVisible(true);
            } else {
                loadingDialog.setVisible(false);
            }
        });
    }

    private void fetchPhotos() {
        setLoading(true);

        new Thread(() -> {
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
                SwingUtilities.invokeLater(() -> {
                    if (photos.isEmpty()) {
                        showErrorMessage("No se encontraron fotos para los filtros seleccionados.");
                    } else {
                        displayPhotos(photos);
                        statusLabel.setText("Fotos cargadas correctamente.");
                    }
                    setLoading(false);
                });
            } catch (Exception ex) {
                ex.printStackTrace();
                SwingUtilities.invokeLater(() -> {
                    showErrorMessage("Error al obtener las fotos: " + ex.getMessage());
                    setLoading(false);
                });
            }
        }).start();
    }

    private LocalDate parseDate(String dateText) {
        try {
            return LocalDate.parse(dateText);
        } catch (DateTimeParseException e) {
            SwingUtilities.invokeLater(() -> showErrorMessage("Formato de fecha inválido. Utilice el formato YYYY-MM-DD."));
            throw e;
        }
    }

    private void displayPhotos(List<MarsPhoto> photos) {
        photoPanel.removeAll();

        for (MarsPhoto photo : photos) {
            JPanel singlePhotoPanel = new JPanel();
            singlePhotoPanel.setLayout(new BoxLayout(singlePhotoPanel, BoxLayout.Y_AXIS));
            singlePhotoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel dateLabel = new JLabel("Date: " + photo.getEarthDate().toString());
            JLabel cameraLabel = new JLabel("Camera: " + photo.getCameraName() + " (" + photo.getCameraFullName() + ")");
            JLabel roverLabel = new JLabel("Rover: " + photo.getRoverName() + " - Sol " + photo.getRoverMaxSol());

            dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            cameraLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            roverLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel idLabel = new JLabel("ID: " + photo.getId());
            JLabel solLabel = new JLabel("Sol: " + photo.getSol());
            JLabel imgUrlLabel = new JLabel("Image URL: " + photo.getImgSrc());

            idLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            solLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            imgUrlLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            JLabel roverStatusLabel = new JLabel("Rover Status: " + photo.getRoverStatus());
            JLabel roverLaunchDateLabel = new JLabel("Launch Date: " + photo.getRoverLaunchDate());
            JLabel roverLandingDateLabel = new JLabel("Landing Date: " + photo.getRoverLandingDate());
            JLabel maxSolLabel = new JLabel("Max Sol: " + photo.getRoverMaxSol());
            JLabel maxDateLabel = new JLabel("Max Date: " + photo.getRoverMaxDate());
            JLabel totalPhotosLabel = new JLabel("Total Photos: " + photo.getRoverTotalPhotos());

            roverStatusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            roverLaunchDateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            roverLandingDateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            maxSolLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            maxDateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            totalPhotosLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

            singlePhotoPanel.add(dateLabel);
            singlePhotoPanel.add(cameraLabel);
            singlePhotoPanel.add(roverLabel);
            singlePhotoPanel.add(idLabel);
            singlePhotoPanel.add(solLabel);
            singlePhotoPanel.add(imgUrlLabel);
            singlePhotoPanel.add(roverStatusLabel);
            singlePhotoPanel.add(roverLaunchDateLabel);
            singlePhotoPanel.add(roverLandingDateLabel);
            singlePhotoPanel.add(maxSolLabel);
            singlePhotoPanel.add(maxDateLabel);
            singlePhotoPanel.add(totalPhotosLabel);

            JEditorPane linkPane = new JEditorPane("text/html", "<a href='" + photo.getImgSrc() + "'>" + photo.getImgSrc() + "</a>");
            linkPane.setEditable(false);
            linkPane.setOpaque(false);
            linkPane.setAlignmentX(Component.LEFT_ALIGNMENT);
            linkPane.addHyperlinkListener(e -> {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    try {
                        Desktop.getDesktop().browse(e.getURL().toURI());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

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

    private void playMusic() {
        musicPlayer.play();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MarsPhotoViewer viewer = new MarsPhotoViewer();
            viewer.setVisible(true);
        });
    }
}