package ep.mil.pe.iafas.seguridad.util;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@ManagedBean(name = "documentService")
@ApplicationScoped
public class DocumentService {

	public TreeNode createCheckboxDocuments() {
		TreeNode root = new CheckboxTreeNode(new Document("Files", "-", "Folder"), null);

		TreeNode applications = new DefaultTreeNode(new Document("Applications", "100kb", "Folder"), root);
		TreeNode cloud = new DefaultTreeNode(new Document("Cloud", "20kb", "Folder"), root);
		TreeNode pictures = new DefaultTreeNode(new Document("Pictures", "150kb", "Folder"), root);
		TreeNode videos = new DefaultTreeNode(new Document("Videos", "1500kb", "Folder"), root);

		// Cloud
		TreeNode backup1 = new DefaultTreeNode("document", new Document("backup-1.zip", "10kb", "Zip"), cloud);
		TreeNode backup2 = new DefaultTreeNode("document", new Document("backup-2.zip", "10kb", "Zip"), cloud);
		TreeNode backup3= new DefaultTreeNode("document", new Document("backup-1.zip", "10kb", "Zip"), cloud);
		TreeNode backup4 = new DefaultTreeNode("document", new Document("backup-2.zip", "10kb", "Zip"), cloud);
		TreeNode backup5 = new DefaultTreeNode("document", new Document("backup-1.zip", "10kb", "Zip"), cloud);
		TreeNode backup6 = new DefaultTreeNode("document", new Document("backup-2.zip", "10kb", "Zip"), cloud);

		return root;
	}
}
