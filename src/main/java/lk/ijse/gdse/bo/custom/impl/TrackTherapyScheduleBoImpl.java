package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.TherapySessionBo;
import lk.ijse.gdse.bo.custom.TrackTherapyScheduleBo;
import lk.ijse.gdse.dto.TherapySessionDto;
import lk.ijse.gdse.entity.TherapySession;

import java.util.ArrayList;

public class TrackTherapyScheduleBoImpl implements TrackTherapyScheduleBo {

    TherapySessionBo therapySessionBo = BOFactory.getInstance().getBO(BOFactory.BOType.SESSION);

    @Override
    public ArrayList<TherapySessionDto> checkTherapySchedule(String id) throws Exception {
        return therapySessionBo.checkByTherapistId(id);
    }
}
