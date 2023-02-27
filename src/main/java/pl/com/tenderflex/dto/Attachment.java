package pl.com.tenderflex.dto;

import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Attachment {

    private MultipartFile contract;
    private MultipartFile awardDecision;
    private MultipartFile rejectDecision;

}