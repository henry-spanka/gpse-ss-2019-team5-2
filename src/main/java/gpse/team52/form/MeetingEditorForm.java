package gpse.team52.form;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

public class MeetingEditorForm {
    @Getter
    @Setter
    @NotBlank
    private String name;


    public MeetingEditorForm() {

    }
}
