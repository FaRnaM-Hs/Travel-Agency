package ui;

import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import model.Reservation;
import service.BookingService;

import static javax.swing.JOptionPane.*;

public class BookingSearch extends javax.swing.JFrame {

    private final BookingService bookingService;
    private final InfoGenerator infoGenerator;
    private List<Reservation> reservations;

    private JFrame previousFrame;

    public BookingSearch(BookingService bookingService) {
        this.bookingService = bookingService;
        this.infoGenerator = new InfoGenerator();
        this.reservations = new ArrayList<>();

        initComponents();
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup = new javax.swing.ButtonGroup();
        jScrollPane2 = new javax.swing.JScrollPane();
        reservationsTable = new javax.swing.JTable();
        searchButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        nameRadioButton = new javax.swing.JRadioButton();
        nationalCodeRadioButton = new javax.swing.JRadioButton();
        flightRadioButton = new javax.swing.JRadioButton();
        searchTextField = new javax.swing.JTextField();
        searchLabel = new javax.swing.JLabel();
        searchLabel2 = new javax.swing.JLabel();
        searchTextField2 = new javax.swing.JTextField();
        reservationsLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Travel Agency");
        setResizable(false);

        reservationsTable.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        reservationsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "National Code", "Outbound Flight", "Return Flight", "Number of Tickets"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        reservationsTable.setFocusable(false);
        reservationsTable.setOpaque(false);
        reservationsTable.setRequestFocusEnabled(false);
        reservationsTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        reservationsTable.getTableHeader().setReorderingAllowed(false);
        reservationsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reservationsTableMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                reservationsTableMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(reservationsTable);
        if (reservationsTable.getColumnModel().getColumnCount() > 0) {
            reservationsTable.getColumnModel().getColumn(0).setResizable(false);
            reservationsTable.getColumnModel().getColumn(1).setResizable(false);
            reservationsTable.getColumnModel().getColumn(2).setResizable(false);
            reservationsTable.getColumnModel().getColumn(3).setResizable(false);
            reservationsTable.getColumnModel().getColumn(4).setResizable(false);
        }

        searchButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        searchButton.setText("Search");
        searchButton.setFocusable(false);
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        cancelButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        cancelButton.setEnabled(false);
        cancelButton.setFocusable(false);
        cancelButton.setLabel("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        backButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        backButton.setText("Back");
        backButton.setFocusable(false);
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("Search By:");

        buttonGroup.add(nameRadioButton);
        nameRadioButton.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        nameRadioButton.setSelected(true);
        nameRadioButton.setFocusable(false);
        nameRadioButton.setLabel("Name");
        nameRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup.add(nationalCodeRadioButton);
        nationalCodeRadioButton.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        nationalCodeRadioButton.setText("National Code");
        nationalCodeRadioButton.setFocusable(false);
        nationalCodeRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nationalCodeRadioButtonActionPerformed(evt);
            }
        });

        buttonGroup.add(flightRadioButton);
        flightRadioButton.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        flightRadioButton.setFocusable(false);
        flightRadioButton.setLabel("Flight");
        flightRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                flightRadioButtonActionPerformed(evt);
            }
        });

        searchTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        searchLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        searchLabel.setText("First Name:");

        searchLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        searchLabel2.setText("Last Name:");

        searchTextField2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        reservationsLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        reservationsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reservationsLabel.setText("Reservations");
        reservationsLabel.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nationalCodeRadioButton)
                                    .addComponent(nameRadioButton))
                                .addGap(116, 116, 116)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(searchLabel)
                                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(searchTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(searchLabel2)))
                            .addComponent(flightRadioButton))
                        .addContainerGap(194, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5))))
            .addGroup(layout.createSequentialGroup()
                .addGap(366, 366, 366)
                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(reservationsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nameRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(searchLabel)
                        .addComponent(searchLabel2)))
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nationalCodeRadioButton, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(flightRadioButton))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(searchTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(reservationsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public BookingSearch setPreviousFrame(JFrame previousFrame) {
        this.previousFrame = previousFrame;
        return this;
    }

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        try {
            reservations = getReservations();
            cancelButton.setEnabled(false);
            clearTable();
            if (reservations.isEmpty()) {
                showMessage("Nothing Found", "", PLAIN_MESSAGE);
            } else {
                addReservationsToTable();
            }
        } catch (Exception e) {
            String message = "Something went wrong! Please try again later\n\nError Message:\n" + e.getMessage();
            showMessage(message, "Error", ERROR_MESSAGE);
        }
    }//GEN-LAST:event_searchButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        Reservation reservation = getSelectedReservation();
        if (LocalDateTime.now().plusHours(2).isAfter(reservation.getOutboundFlight().getDeparture())) {
            showMessage("You can cancel the reservation up to 2 hours before the outbound flight", "", WARNING_MESSAGE);
        } else {
            try {
                if (confirm() == 1) {
                    bookingService.cancel(reservation);
                    showMessage("The reservation successfully canceled.", "Result", INFORMATION_MESSAGE);
                    removeSelectedReservation();
                    cancelButton.setEnabled(false);
                }
            } catch (Exception e) {
                String message = "Something went wrong! Please try again later\n\nError Message:\n" + e.getMessage();
                showMessage(message, "Error", ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        previousFrame.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    private void reservationsTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reservationsTableMousePressed
        if (!cancelButton.isEnabled() && !reservationsTable.getSelectionModel().isSelectionEmpty())
            cancelButton.setEnabled(true);
    }//GEN-LAST:event_reservationsTableMousePressed

    private void reservationsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reservationsTableMouseClicked
        if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1)
            showMessage(infoGenerator.generateReservationInfo(getSelectedReservation()), "Reservation", INFORMATION_MESSAGE);
    }//GEN-LAST:event_reservationsTableMouseClicked

    private void nameRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameRadioButtonActionPerformed
        searchLabel.setText("First Name:");
        searchTextField.setText("");
        searchTextField2.setVisible(true);
        searchLabel2.setVisible(true);
        searchTextField2.setText("");
    }//GEN-LAST:event_nameRadioButtonActionPerformed

    private void nationalCodeRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nationalCodeRadioButtonActionPerformed
        searchLabel.setText("National Code:");
        searchTextField2.setVisible(false);
        searchLabel2.setVisible(false);
        searchTextField.setText("");
    }//GEN-LAST:event_nationalCodeRadioButtonActionPerformed

    private void flightRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_flightRadioButtonActionPerformed
        searchLabel.setText("Flight Number:");
        searchTextField2.setVisible(false);
        searchLabel2.setVisible(false);
        searchTextField.setText("");
    }//GEN-LAST:event_flightRadioButtonActionPerformed

    private int confirm() {
        String[] options = {"No", "Yes"};
        return showOptionDialog(
                this,
                infoGenerator.generateReservationInfo(getSelectedReservation()) + "Do you sure to cancel this reservation?",
                "Confirmation",
                YES_NO_CANCEL_OPTION,
                QUESTION_MESSAGE,
                null,
                options,
                options[1]
        );
    }

    private void showMessage(String message, String title, int type) {
        showMessageDialog(this, message, title, type);
    }

    private void clearTable() {
        ((DefaultTableModel) reservationsTable.getModel()).setRowCount(0);
    }

    private void addReservationsToTable() {
        DefaultTableModel reservationsTableModel = (DefaultTableModel) reservationsTable.getModel();
        reservations.forEach(reservation ->
                reservationsTableModel.addRow(new Object[]{
                        reservation.getPassenger().getFirstName() + " " + reservation.getPassenger().getLastName(),
                        reservation.getPassenger().getNationalCode(),
                        reservation.getOutboundFlight().getFlightNumber(),
                        reservation.isRoundTrip() ? reservation.getReturnFlight().getFlightNumber() : "None",
                        reservation.getTicket()
                })
        );
    }

    private void removeSelectedReservation() {
        reservations.remove(reservationsTable.getSelectedRow());
        ((DefaultTableModel) reservationsTable.getModel()).removeRow(reservationsTable.getSelectedRow());
    }

    private Reservation getSelectedReservation() {
        return reservations.get(reservationsTable.getSelectedRow());
    }

    private List<Reservation> getReservations() {
        if (nameRadioButton.isSelected())
            return bookingService.findByName(searchTextField.getText(), searchTextField2.getText());
        if (nationalCodeRadioButton.isSelected())
            return bookingService.findByNationalCode(searchTextField.getText());
        if (flightRadioButton.isSelected())
            return bookingService.findByFlight(searchTextField.getText());
        return new ArrayList<>();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.ButtonGroup buttonGroup;
    private javax.swing.JButton cancelButton;
    private javax.swing.JRadioButton flightRadioButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton nameRadioButton;
    private javax.swing.JRadioButton nationalCodeRadioButton;
    private javax.swing.JLabel reservationsLabel;
    private javax.swing.JTable reservationsTable;
    private javax.swing.JButton searchButton;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JLabel searchLabel2;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JTextField searchTextField2;
    // End of variables declaration//GEN-END:variables
}