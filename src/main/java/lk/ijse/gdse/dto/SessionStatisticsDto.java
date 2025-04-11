package lk.ijse.gdse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SessionStatisticsDto {
    private String id;
    private String name;
    private int completedSessionCount;
    private int bookedSessionCount;
    private int rescheduleSessionCount;
    private int canceledSessionCount;
}
