package com.mycompany.evaluablenominasmasivas;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.Locale;

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

        progressBar = new JProgressBar(0, 500000);
        progressBar.setStringPainted(true);

        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(logArea);

        btnProcesar = new JButton("Iniciar Procesamiento");
        lblStats = new JLabel("Resultados: Esperando inicio...");

        add(btnProcesar, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(progressBar, BorderLayout.NORTH);
        bottomPanel.add(lblStats, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

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
        int errores = 0;

        @Override
        protected Void doInBackground() throws Exception {
            File entrada = new File("dataset.csv");
            File salida = new File("resultado_nominas.csv");

            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(entrada), "UTF-8")); BufferedWriter bw = new BufferedWriter(new FileWriter(salida))) {

                String linea;

                while ((linea = br.readLine()) != null) {

                    procesados++;

                    if (linea.trim().isEmpty()) {
                        continue;
                    }

                    try {

                        String lineaLimpia = linea.replace(".", "").replace(",", ".");

                        Empleado emp = new Empleado(lineaLimpia);
                        double neto = emp.calcularSueldoNetoMensual();
                        gastoTotal += neto;

                        if (neto > maxSueldo) {
                            maxSueldo = neto;
                            empleadoTop = emp.getNombre();
                        }

                        bw.write(emp.toCSV() + ";" + String.format(Locale.US, "%.2f", neto));
                        bw.newLine();

                    } catch (Exception ex) {
                        errores++;
                    }
                    if (procesados % 1000 == 0) {
                        publish("Procesando registro " + procesados + "...");
                    }
                }
            }
            return null;
        }

        @Override
        protected void process(List<String> chunks) {
            progressBar.setValue(procesados);

            for (String msg : chunks) {
                logArea.append(msg + "\n");
                logArea.setCaretPosition(logArea.getDocument().getLength());
            }
        }

        @Override
        protected void done() {
            btnProcesar.setEnabled(true);
            progressBar.setValue(500000);
            logArea.append("¡Proceso Finalizado!\n");
            String resumen = String.format(
                    "Total Nóminas: %d | Gasto Total: %.2f€ | Más cobra: %s (%.2f€)",
                    procesados, gastoTotal, empleadoTop, maxSueldo
            );
            lblStats.setText(resumen);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EvaluableNominasMasivas().setVisible(true));
    }
}
