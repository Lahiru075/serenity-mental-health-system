package lk.ijse.gdse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SessionStatisticsTm {
    private String id;
    private String name;
    private int completedSessionCount;
    private int bookedSessionCount;
    private int rescheduleSessionCount;
    private int canceledSessionCount;
}
