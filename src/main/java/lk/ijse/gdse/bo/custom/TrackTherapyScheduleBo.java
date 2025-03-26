package lk.ijse.gdse.bo.custom;

import lk.ijse.gdse.bo.SuperBo;
import lk.ijse.gdse.dto.TherapySessionDto;


import java.util.ArrayList;

public interface TrackTherapyScheduleBo extends SuperBo {
    ArrayList<TherapySessionDto> checkTherapySchedule(String therapistId) throws Exception;
}
