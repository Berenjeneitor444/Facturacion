package com.berenjeneitor.facturacion.presentacion;

import javax.swing.*;
import java.awt.*;

public class DialogUtils {
    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfo(Component parent, String message) {
        JOptionPane.showMessageDialog(parent,
                message,
                "Información",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean showConfirm(Component parent, String message) {
        return JOptionPane.showConfirmDialog(parent,
                message,
                "Confirmar",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static String showInput(Component parent, String message) {
        return JOptionPane.showInputDialog(parent,
                message,
                "Entrada",
                JOptionPane.QUESTION_MESSAGE);
    }
}