package management;

import dao.ExhibitionDAO;
import model.Exhibition;
import ui.ExhibitionDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Optional;

public class ExhibitionManagementPanel extends JPanel {
    private JTable exhibitionTable;
    private DefaultTableModel model;
    private ExhibitionDAO exhibitionDAO;

    public ExhibitionManagementPanel() {
        setLayout(new BorderLayout());

        // Set FlatLaf Look and Feel
        try {
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        exhibitionDAO = new ExhibitionDAO();
        model = new DefaultTableModel(new String[]{"Title", "Start Date", "End Date", "Description", "Active"}, 0);
        exhibitionTable = new JTable(model);
        customizeTable();
        JScrollPane scrollPane = new JScrollPane(exhibitionTable);

        loadExhibitions();

        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        addButton.addActionListener(this::addExhibition);
        editButton.addActionListener(this::editExhibition);
        deleteButton.addActionListener(this::deleteExhibition);

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add a title at the top
        JLabel titleLabel = new JLabel("Exhibition Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);
    }

    private void loadExhibitions() {
        List<Exhibition> exhibitions = exhibitionDAO.getAllExhibitions();
        for (Exhibition exhibition : exhibitions) {
            model.addRow(new Object[]{
                    exhibition.getTitle(),
                    exhibition.getStartDate(),
                    exhibition.getEndDate(),
                    exhibition.getDescription(),
                    exhibition.isActive()
            });
        }
    }

    private void addExhibition(ActionEvent event) {
        ExhibitionDialog dialog = new ExhibitionDialog(null, "Add Exhibition", true, null, false, model, -1);
        dialog.setVisible(true);
    }

    private void editExhibition(ActionEvent event) {
        int selectedRow = exhibitionTable.getSelectedRow();
        if (selectedRow != -1) {
            String title = (String) model.getValueAt(selectedRow, 0);
            Optional<Exhibition> optionalExhibition = exhibitionDAO.getAllExhibitions().stream()
                    .filter(e -> e.getTitle().equals(title))
                    .findFirst();
            if (optionalExhibition.isPresent()) {
                Exhibition exhibition = optionalExhibition.get();
                ExhibitionDialog dialog = new ExhibitionDialog(null, "Edit Exhibition", true, exhibition, true, model, selectedRow);
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Exhibition not found.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an exhibition to edit.");
        }
    }

    private void deleteExhibition(ActionEvent event) {
        int selectedRow = exhibitionTable.getSelectedRow();
        if (selectedRow != -1) {
            String title = (String) model.getValueAt(selectedRow, 0);
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this exhibition?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                Optional<Exhibition> optionalExhibition = exhibitionDAO.getAllExhibitions().stream()
                        .filter(e -> e.getTitle().equals(title))
                        .findFirst();
                if (optionalExhibition.isPresent()) {
                    Exhibition exhibition = optionalExhibition.get();
                    exhibitionDAO.deleteExhibition(exhibition.getExhibitionId());
                    model.removeRow(selectedRow);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "No exhibition selected for deletion.");
        }
    }

    private void customizeTable() {
        exhibitionTable.setFillsViewportHeight(true);
        exhibitionTable.setRowHeight(30);
        exhibitionTable.setFont(new Font("Arial", Font.PLAIN, 14));
        exhibitionTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        exhibitionTable.setShowGrid(true);
        exhibitionTable.setGridColor(Color.LIGHT_GRAY);
    }
}