package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBo;
import lk.ijse.gdse.dto.TherapyProgramDto;
import lk.ijse.gdse.dto.TherapySessionDto;

import java.util.ArrayList;

public interface ReportAndAnalystBo extends SuperBo {

    ArrayList<TherapyProgramDto> findById(String id);

    int[] getAllCounts(String therapistId, String therapyProgramId);
}
