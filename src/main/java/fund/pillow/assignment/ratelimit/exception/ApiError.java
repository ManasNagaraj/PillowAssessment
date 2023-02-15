package fund.pillow.assignment.ratelimit.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class ApiError implements Serializable {

    private static final long serialVersionUID = 1L;
    private String code;

    private String message;


}
