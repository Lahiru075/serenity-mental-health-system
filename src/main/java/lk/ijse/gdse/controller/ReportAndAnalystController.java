package lk.ijse.gdse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.ReportAndAnalystBo;
import lk.ijse.gdse.bo.custom.TherapistBo;
import lk.ijse.gdse.bo.custom.TherapyProgramBo;
import lk.ijse.gdse.bo.custom.TherapySessionBo;
import lk.ijse.gdse.dto.TherapistDto;
import lk.ijse.gdse.dto.TherapyProgramDto;
import lk.ijse.gdse.dto.TherapySessionDto;
import lk.ijse.gdse.entity.Therapist;
import lk.ijse.gdse.entity.TherapyProgram;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReportAndAnalystController implements Initializable {

    @FXML
    private CategoryAxis X;

    @FXML
    private NumberAxis Y;

    @FXML
    private BarChart<String, Number> barChartPerformance;

    @FXML
    private Button btnSearch;

    @FXML
    private ComboBox<String> cmbProgramName;

    @FXML
    private ComboBox<String> cmbTherapistId;

    @FXML
    private TableColumn<?, ?> colBookingCount;

    @FXML
    private TableColumn<?, ?> colCancelledCount;

    @FXML
    private TableColumn<?, ?> colCompletedCount;

    @FXML
    private TableColumn<?, ?> colProgramId;

    @FXML
    private TableColumn<?, ?> colProgramName;

    @FXML
    private Label lblTherapistName;

    @FXML
    private TableView<?> tblSessionStats;

    TherapistBo therapistBo = BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);
    ReportAndAnalystBo reportAndAnalystBo = BOFactory.getInstance().getBO(BOFactory.BOType.REPORT_AND_ANALYSIS);
    TherapyProgramBo therapyProgramBo = BOFactory.getInstance().getBO(BOFactory.BOType.THERAPY_PROGRAM);

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String therapistId = cmbTherapistId.getValue();
        String sessionName = cmbProgramName.getValue();

        if (therapistId.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please select a therapist").showAndWait();
            return;
        }

        if (sessionName.isEmpty()){
            new Alert(Alert.AlertType.ERROR, "Please select a session").showAndWait();
            return;
        }

        TherapyProgramDto therapyProgramDto = therapyProgramBo.findByName(sessionName);

        int[] arr = reportAndAnalystBo.getAllCounts(therapistId , therapyProgramDto.getId());

        if (arr[0] == 0 && arr[1] == 0 && arr[2] == 0 && arr[3] == 0){
            new Alert(Alert.AlertType.ERROR, "No data");
            return;
        }

        loadBarChat(arr);

    }

    private void loadBarChat(int[] arr){

        NumberAxis yAxis = (NumberAxis) barChartPerformance.getYAxis();
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(20);
        yAxis.setTickUnit(5);

        XYChart.Series series = new XYChart.Series<>();

        barChartPerformance.setAnimated(false);

        series.getData().add(new XYChart.Data<>("Rescheduled Sessions", arr[0]));
        series.getData().add(new XYChart.Data<>("Cancelled Sessions", arr[1]));
        series.getData().add(new XYChart.Data<>("Completed Sessions", arr[2]));
        series.getData().add(new XYChart.Data<>("Booked Sessions", arr[3]));

        barChartPerformance.getData().clear();
        barChartPerformance.getData().addAll(series);

        barChartPerformance.requestLayout();
    }

    private void loadTherapistIds() throws SQLException {
        ArrayList<TherapistDto> therapists = therapistBo.getAll();

        ArrayList<String> therapistIds = new ArrayList<>();

        for ( TherapistDto therapistDto : therapists) {
            therapistIds.add(therapistDto.getId());
        }

        ObservableList<String> therapistsIds = FXCollections.observableArrayList(therapistIds);
        cmbTherapistId.setItems(therapistsIds);
    }

    @FXML
    void cmbTherapistIdOnAction(ActionEvent event) {

        barChartPerformance.getData().clear();

        String id = cmbTherapistId.getValue();

        if (id == null){
            return;
        }

        TherapistDto therapistDto = therapistBo.findById(id);
        lblTherapistName.setText(therapistDto.getName());

        ArrayList<TherapyProgramDto> therapyProgramDtoList = reportAndAnalystBo.findById(id);

        if (therapyProgramDtoList == null){
            return;
        }

        ArrayList<String> names = new ArrayList<>();

        for (TherapyProgramDto therapyProgramDto : therapyProgramDtoList) {
            names.add(therapyProgramDto.getName());
        }

        ObservableList<String> programNames = FXCollections.observableArrayList(names);
        cmbProgramName.setItems(programNames);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            loadTherapistIds();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
