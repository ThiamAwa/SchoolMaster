package sn.dev.schoolmaster.exception;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EntityNotFoundException extends RuntimeException {
    String message;
}
