import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalLookupService;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChangeOwner extends JFrame{

    private final JTextField newOwnerTxt;
    static private File f;

    public static void main(String[] args) {
        new ChangeOwner();
    }

    public String getTextFromNewOwnerTextField () {
        return newOwnerTxt.getText();
    }

    ChangeOwner () {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,200);
        setLocationRelativeTo(null);
        JPanel changeOwnerPanel = new JPanel();

        changeOwnerPanel.setLayout(new BorderLayout());
        JLabel ownerLabel = new JLabel("Введите нового владельца:");
        JButton button = new JButton("Изменить");
        button.addActionListener((ActionEvent e) -> {
            try {
                changeOwner(f, getTextFromNewOwnerTextField());
            } catch (Exception ignore) {}
            finally {
                System.exit(0);
            }
        });
        newOwnerTxt = new JTextField();
        changeOwnerPanel.add(newOwnerTxt,BorderLayout.CENTER);
        changeOwnerPanel.add(ownerLabel, BorderLayout.NORTH);
        changeOwnerPanel.add(button, BorderLayout.SOUTH);
        add(changeOwnerPanel);
        setVisible(true);
        pack();
    }

    public void changeOwner(File file, String newOwner) throws IOException {

        if (!file.exists()) {
            System.out.printf("Файл не найден.", file.getCanonicalPath());
        }
        try {
            Path pathFile = Paths.get(file.getAbsolutePath());
            UserPrincipalLookupService lookupService = FileSystems.getDefault().getUserPrincipalLookupService();
            UserPrincipal owner = lookupService.lookupPrincipalByName(newOwner);
            Files.setOwner(pathFile, owner);
        }
        catch (Exception ex4) {
            System.out.println("Невозможно изменить владельца.");
        }
    }

}
