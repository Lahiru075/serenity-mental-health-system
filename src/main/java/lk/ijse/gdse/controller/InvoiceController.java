package lk.ijse.gdse.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class InvoiceController {

    @FXML
    private Label lblFee;

    @FXML
    private Label lblInvoiceDate;

    @FXML
    private Label lblPatientId;

    @FXML
    private Label lblPatientName;

    @FXML
    private Label lblPaymentDate;

    @FXML
    private Label lblPaymentFee;

    @FXML
    private Label lblProgramName;

    @FXML
    private Label lblSessionDate;

    @FXML
    private Label lblSessionId;

    @FXML
    private Label lblStatus;

    private PaymentAndInvoiceManageController paymentAndInvoiceManageController;

    public void setInvoiceController(PaymentAndInvoiceManageController paymentAndInvoiceManageController) {
        this.paymentAndInvoiceManageController = paymentAndInvoiceManageController;

        lblFee.setText(String.valueOf(paymentAndInvoiceManageController.getInvoiceDto().getFee()));
        lblInvoiceDate.setText(String.valueOf(paymentAndInvoiceManageController.getInvoiceDto().getInvoiceDate()));
        lblPatientId.setText(String.valueOf(paymentAndInvoiceManageController.getInvoiceDto().getPatientId()));
        lblPatientName.setText(String.valueOf(paymentAndInvoiceManageController.getInvoiceDto().getPatientName()));
        lblPaymentDate.setText(String.valueOf(paymentAndInvoiceManageController.getInvoiceDto().getInvoiceDate()));
        lblPaymentFee.setText(String.valueOf(paymentAndInvoiceManageController.getInvoiceDto().getFee()));
        lblProgramName.setText(String.valueOf(paymentAndInvoiceManageController.getInvoiceDto().getProgramName()));
        lblSessionDate.setText(String.valueOf(paymentAndInvoiceManageController.getInvoiceDto().getSessionDate()));
        lblSessionId.setText(String.valueOf(paymentAndInvoiceManageController.getInvoiceDto().getSessionId()));
        lblStatus.setText(String.valueOf(paymentAndInvoiceManageController.getInvoiceDto().getStatus()));
    }
}
