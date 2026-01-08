package com.mycompany.evaluablenominasmasivas;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;

public class EvaluableNominasMasivas extends JFrame {

    private JProgressBar progressBar;
    private JTextArea logArea;
    private JButton btnProcesar;
    private JLabel lblStats;

    public EvaluableNominasMasivas() {
        setTitle("Calculadora de Nóminas - RRHH");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Componentes
        progressBar = new JProgressBar(0, 500000); // [cite: 18]
        progressBar.setStringPainted(true);

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(logArea);

        btnProcesar = new JButton("Iniciar Procesamiento");
        lblStats = new JLabel("Esperando inicio...");

        add(btnProcesar, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(progressBar, BorderLayout.NORTH);
        bottomPanel.add(lblStats, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // Acción del botón
        btnProcesar.addActionListener(e -> {
            btnProcesar.setEnabled(false);
            new ProcesadorWorker().execute();
        });
    }

    class ProcesadorWorker extends SwingWorker<Void, String> {

 
        double gastoTotal = 0;
        String empleadoTop = "";
        double maxSueldo = 0;
        int procesados = 0;

        @Override
        protected Void doInBackground() throws Exception {
            File entrada = new File("dataset.csv");
            File salida = new File("resultado_nominas.csv");

            try (BufferedReader br = new BufferedReader(new FileReader(entrada)); BufferedWriter bw = new BufferedWriter(new FileWriter(salida))) {

                String linea;
            
                while ((linea = br.readLine()) != null) {

                    if (linea.trim().isEmpty()) {
                        continue;
                    }
                    try {
                    
                        Empleado emp = new Empleado(linea);
                        double neto = emp.calcularSueldoNetoMensual();

                 
                        gastoTotal += neto;
                        if (neto > maxSueldo) {
                            maxSueldo = neto;
                            empleadoTop = emp.getNombre();
                        }

                        bw.write(emp.toCSV() + ";" + String.format("%.2f", neto));
                        bw.newLine();

                        procesados++;
                        setProgress(procesados); 

                     
                        if (procesados % 1000 == 0) {
                            publish("Procesando registro " + procesados + "...");
                        }

                    } catch (Exception ex) {
                        publish("Error en línea " + procesados);
                    }
                }
            }
            return null;
        }

        @Override
        protected void process(List<String> chunks) {
            for (String msg : chunks) {
                logArea.append(msg + "\n");
                logArea.setCaretPosition(logArea.getDocument().getLength());
            }
            progressBar.setValue(procesados);
        }

        @Override
        protected void done() {
            btnProcesar.setEnabled(true);
            logArea.append("¡Proceso Finalizado!\n");

      
            String resumen = String.format(
                    "Total: %d | Gasto Total: %.2f€ | Más cobra: %s (%.2f€)",
                    procesados, gastoTotal, empleadoTop, maxSueldo
            );
            lblStats.setText(resumen);
            JOptionPane.showMessageDialog(EvaluableNominasMasivas.this, "Proceso completado.\n" + resumen);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EvaluableNominasMasivas().setVisible(true));
    }
}
