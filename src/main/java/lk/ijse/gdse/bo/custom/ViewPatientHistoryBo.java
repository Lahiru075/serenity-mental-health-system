package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBo;
import lk.ijse.gdse.dto.ViewPatientHistoryDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ViewPatientHistoryBo extends SuperBo {
    ArrayList<String> getAllPatientIds() throws SQLException;

    ArrayList<ViewPatientHistoryDto> loadPatientHistory(String id) throws SQLException;
}
