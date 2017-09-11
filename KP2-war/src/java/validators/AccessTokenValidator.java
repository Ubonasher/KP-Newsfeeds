package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("AccessTokenValidator")
public class AccessTokenValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        try {
            String if1 = value.toString().substring(0, 45);
            int if2 = value.toString().length();

            if ((!"https://oauth.vk.com/blank.html#access_token=".equals(if1)) || (if2 != 161)) {
                FacesMessage msg = new FacesMessage("Обратите внимание на формат вставленной адресной строки");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage("Обратите внимание на формат вставленной адресной строки");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
