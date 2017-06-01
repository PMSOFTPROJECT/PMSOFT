package co.edu.uan.app.pmsoft.util;

import java.security.Principal;
import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FacesUtils {

	private static final Logger logger = LoggerFactory.getLogger(FacesUtils.class);

	public static Principal getPrincipal() {

		return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
	}

	public static void addMessageInfo(ActionEvent event, String summary, String detail) {
		addMessage(0, event, summary, detail);
	}

	public static void addMessageWarn(ActionEvent event, String summary, String detail) {
		addMessage(1, event, summary, detail);
	}

	public static void addMessageError(ActionEvent event, String summary, String detail) {
		addMessage(2, event, summary, detail);
	}

	public static void addMessageFatal(ActionEvent event, String summary, String detail) {
		addMessage(3, event, summary, detail);
	}

	private static void addMessage(int index, ActionEvent event, String summary, String detail){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		UIComponent component = event.getComponent();
		FacesMessage facesMessage = new FacesMessage((FacesMessage.Severity) FacesMessage.VALUES.get(index), summary,detail);
		facesContext.addMessage(component.getClientId(), facesMessage);
	}

	private static void removeExistingMessages(FacesContext facesContext) {
		// remove existing messages
		Iterator<FacesMessage> i = facesContext.getMessages();
		while (i.hasNext()) {
			i.next();
			i.remove();
		}
	}

}
