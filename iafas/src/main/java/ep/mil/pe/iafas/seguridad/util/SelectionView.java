package ep.mil.pe.iafas.seguridad.util;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

//import com.javacodegeeks.enterprise.jsf.treeselectionjsf.DocumentService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.model.TreeNode;

import lombok.Data;

@ManagedBean(name = "treeSelectionView")
@SessionScoped
@Data
public class SelectionView implements Serializable{

	private static final Logger logger = Logger.getLogger(SelectionView.class.getName());
	
	private static final long serialVersionUID = -7334247037232798503L;
	private TreeNode root3;
	private TreeNode[] selectedNodes2;
	
	@ManagedProperty("#{documentService}")
    private DocumentService service;
	
	@PostConstruct
    public void init() {
//		DocumentService service = new DocumentService(); 
 
        root3 = service.createCheckboxDocuments();
    }
	
	public void displaySelectedMultiple(TreeNode[] nodes) {
		logger.info("[INICIO:] Metodo : displaySelectedMultiple");
		
		if (nodes != null && nodes.length > 0) {
			StringBuilder builder = new StringBuilder();

			for (TreeNode node : nodes) {
				builder.append(node.getData().toString());
				builder.append("<br />");
			}

			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", builder.toString());
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		logger.info("[FIN:] Metodo : displaySelectedMultiple");
	}
}
