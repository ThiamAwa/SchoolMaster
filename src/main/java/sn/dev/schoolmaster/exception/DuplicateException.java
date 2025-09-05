package sn.dev.schoolmaster.exception;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DuplicateException extends RuntimeException{
    String message;
}
