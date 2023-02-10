package ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import service.BookingService;
import service.ConverterService;
import service.FlightService;

import static java.util.concurrent.TimeUnit.*;

public class MainPage extends javax.swing.JFrame {

    private final BookingService bookingService;
    private final FlightService flightService;
    private final ConverterService converterService;
    
    public MainPage(BookingService bookingService, FlightService flightService, ConverterService converterService) {
        this.bookingService = bookingService;
        this.flightService = flightService;
        this.converterService = converterService;
        
        initComponents();
        initOtherComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bookPageButton = new javax.swing.JButton();
        searchPageButton = new javax.swing.JButton();
        timeLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Travel Agency");
        setResizable(false);

        bookPageButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        bookPageButton.setText("Book Flight");
        bookPageButton.setFocusable(false);
        bookPageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bookPageButtonActionPerformed(evt);
            }
        });

        searchPageButton.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        searchPageButton.setText("Search/Cancel Reservation");
        searchPageButton.setFocusable(false);
        searchPageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchPageButtonActionPerformed(evt);
            }
        });

        timeLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        timeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        timeLabel.setText(getTime());
        timeLabel.setFocusable(false);

        dateLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        dateLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dateLabel.setText(getDate());
        dateLabel.setFocusable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bookPageButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchPageButton, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                    .addComponent(timeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                .addComponent(searchPageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bookPageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initOtherComponents() {
        runTime();
        this.setLocationRelativeTo(null);
    }

    private void bookPageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookPageButtonActionPerformed
        new OutboundBooking(bookingService, flightService, converterService)
                .setPreviousFrame(this)
                .setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_bookPageButtonActionPerformed

    private void searchPageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchPageButtonActionPerformed
        new BookingSearch(bookingService)
                .setPreviousFrame(this)
                .setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_searchPageButtonActionPerformed

    private void runTime() {
        Runnable timeRunnable = () -> {
            timeLabel.setText(getTime());
            dateLabel.setText(getDate());
        };

        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(timeRunnable, 0, 1, SECONDS);
    }

    private String getTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        return LocalTime.now().format(dtf);
    }

    private String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy");
        return LocalDate.now().format(dtf);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bookPageButton;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JButton searchPageButton;
    private javax.swing.JLabel timeLabel;
    // End of variables declaration//GEN-END:variables
}
