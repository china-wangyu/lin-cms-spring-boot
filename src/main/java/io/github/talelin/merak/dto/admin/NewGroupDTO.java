package io.github.talelin.merak.dto.admin;

import io.github.talelin.autoconfigure.validator.Length;
import io.github.talelin.autoconfigure.validator.LongList;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author pedro@TaleLin
 */
@Data
public class NewGroupDTO {
    @NotBlank(message = "{group.name.not-blank}")
    @Length(min = 1, max = 60, message = "{group.name.length}")
    private String name;

    @Length(min = 1, max = 255, message = "{group.info.length}")
    private String info;

    @LongList(allowBlank = true, message = "{permission.ids.long-list}")
    private List<Long> permissionIds;
}
