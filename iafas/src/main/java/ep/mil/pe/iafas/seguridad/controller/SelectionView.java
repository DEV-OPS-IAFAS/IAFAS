package ep.mil.pe.iafas.seguridad.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.model.TreeNode;

import ep.mil.pe.iafas.seguridad.util.DocumentService;
import lombok.Data;

@ManagedBean(name = "treeSelectionView")
@ViewScoped
@Data
public class SelectionView implements Serializable {

	
	private static final long serialVersionUID = 1069219686131798860L;

	private static final Logger logger = Logger.getLogger(SelectionView.class.getName());
	
    private TreeNode root3;
    private TreeNode selectedNode;
    private TreeNode[] selectedNodes1;
    private TreeNode[] selectedNodes2;
    
//    @ManagedProperty(value = "#{service}")
//    private DocumentService service;
    
    @PostConstruct
    public void init() {
    	DocumentService service = new DocumentService(); 
        root3 = service.createCheckboxDocuments();
    }
    
    public void displaySelectedSingle() {
    	logger.info("[INICIO:] Metodo : displaySelectedSingle");
        if(selectedNode != null) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", selectedNode.getData().toString());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        logger.info("[FIN:] Metodo : displaySelectedSingle");
	}
    
    public void displaySelectedMultiple(TreeNode[] nodes) {
    	logger.info("[INICIO:] Metodo : displaySelectedMultiple");
        if(nodes != null && nodes.length > 0) {
            StringBuilder builder = new StringBuilder();

            for(TreeNode node : nodes) {
                builder.append(node.getData().toString());
                builder.append("<br />");
                
                logger.info("datos a grabar::::"+node.getData().toString());
            }

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", builder.toString());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        logger.info("[FIN:] Metodo : displaySelectedMultiple");
	}
}
