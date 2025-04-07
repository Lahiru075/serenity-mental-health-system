package lk.ijse.gdse.bo.custom.impl;

import lk.ijse.gdse.bo.BOFactory;
import lk.ijse.gdse.bo.custom.ReportAndAnalystBo;
import lk.ijse.gdse.bo.custom.TherapistBo;
import lk.ijse.gdse.bo.custom.TherapySessionBo;
import lk.ijse.gdse.dto.TherapistDto;
import lk.ijse.gdse.dto.TherapyProgramDto;
import lk.ijse.gdse.dto.TherapySessionDto;
import lk.ijse.gdse.entity.TherapyProgram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReportAndAnalystBoImpl implements ReportAndAnalystBo {
    TherapistBo therapistBo = BOFactory.getInstance().getBO(BOFactory.BOType.THERAPIST);
    TherapySessionBo therapySessionBo = BOFactory.getInstance().getBO(BOFactory.BOType.SESSION);

    @Override
    public ArrayList<TherapyProgramDto> findById(String id) {
        List<TherapyProgram> therapyPrograms = therapistBo.getProgramById(id);

        ArrayList<TherapyProgramDto> therapyProgramDtos = new ArrayList<>();

        for (TherapyProgram therapyProgram : therapyPrograms){
            TherapyProgramDto therapyProgramDto = new TherapyProgramDto();
            therapyProgramDto.setId(therapyProgram.getId());
            therapyProgramDto.setName(therapyProgram.getName());
            therapyProgramDto.setDuration(therapyProgram.getDuration());
            therapyProgramDto.setDescription(therapyProgram.getDescription());
            therapyProgramDto.setFee(therapyProgram.getFee());

            therapyProgramDtos.add(therapyProgramDto);
        }

        return therapyProgramDtos;
    }

    @Override
    public int[] getAllCounts(String therapistId, String therapyProgramId) {
        ArrayList<TherapySessionDto> therapyProgramDtos = therapySessionBo.getAllCounts(therapistId, therapyProgramId);

        int rescheduled = 0;
        int cancelled = 0;
        int completed = 0;
        int booked = 0;

        for (TherapySessionDto therapySessionDto : therapyProgramDtos){
            if (therapySessionDto.getStatus().equals("Booked")){
                booked++;
            } else if (therapySessionDto.getStatus().equals("Rescheduled")){
                rescheduled++;
            } else if (therapySessionDto.getStatus().equals("Completed")){
                completed++;
            } else if (therapySessionDto.getStatus().equals("Cancelled")){
                cancelled++;
            }
        }

        int[] counts = new int[4];
        counts[0] = rescheduled;
        counts[1] = cancelled;
        counts[2] =completed;
        counts[3] = booked;

        return counts;


    }
}
