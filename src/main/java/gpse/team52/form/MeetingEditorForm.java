package gpse.team52.form;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/**
 * MeetingEditorForm.
 */
public class MeetingEditorForm {
    @Getter
    @Setter
    @NotBlank
    private String name;


    public MeetingEditorForm() {

    }
}
