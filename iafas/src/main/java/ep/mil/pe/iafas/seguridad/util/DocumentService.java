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

		TreeNode applications = new CheckboxTreeNode(new Document("Applications", "100kb", "Folder"), root);
		TreeNode cloud = new CheckboxTreeNode(new Document("Cloud", "20kb", "Folder"), root);
		TreeNode desktop = new CheckboxTreeNode(new Document("Desktop", "150kb", "Folder"), root);
		TreeNode documents = new CheckboxTreeNode(new Document("Documents", "75kb", "Folder"), root);
		TreeNode downloads = new CheckboxTreeNode(new Document("Downloads", "25kb", "Folder"), root);
		TreeNode main = new CheckboxTreeNode(new Document("Main", "50kb", "Folder"), root);
		TreeNode other = new CheckboxTreeNode(new Document("Other", "5kb", "Folder"), root);
		TreeNode pictures = new CheckboxTreeNode(new Document("Pictures", "150kb", "Folder"), root);
		TreeNode videos = new CheckboxTreeNode(new Document("Videos", "1500kb", "Folder"), root);

		//Applications
		TreeNode primeface = new CheckboxTreeNode(new Document("Primefaces", "25kb", "Folder"), applications);
		TreeNode primefacesapp = new CheckboxTreeNode("app", new Document("primefaces.app", "10kb", "Application"), primeface);
		TreeNode nativeapp = new CheckboxTreeNode("app", new Document("native.app", "10kb", "Application"), primeface);
		TreeNode mobileapp = new CheckboxTreeNode("app", new Document("mobile.app", "5kb", "Application"), primeface);
		TreeNode editorapp = new CheckboxTreeNode("app", new Document("editor.app", "25kb", "Application"), applications);
		TreeNode settingsapp = new CheckboxTreeNode("app", new Document("settings.app", "50kb", "Application"), applications);

		//Cloud
		TreeNode backup1 = new CheckboxTreeNode("document", new Document("backup-1.zip", "10kb", "Zip"), cloud);
		TreeNode backup2 = new CheckboxTreeNode("document", new Document("backup-2.zip", "10kb", "Zip"), cloud);

		//Desktop
		TreeNode note1 = new CheckboxTreeNode("document", new Document("note-meeting.txt", "50kb", "Text"), desktop);
		TreeNode note2 = new CheckboxTreeNode("document", new Document("note-todo.txt", "100kb", "Text"), desktop);

		//Documents
		TreeNode work = new CheckboxTreeNode(new Document("Work", "55kb", "Folder"), documents);
		TreeNode expenses = new CheckboxTreeNode("document", new Document("Expenses.doc", "30kb", "Document"), work);
		TreeNode resume = new CheckboxTreeNode("document", new Document("Resume.doc", "25kb", "Resume"), work);
		TreeNode home = new CheckboxTreeNode(new Document("Home", "20kb", "Folder"), documents);
		TreeNode invoices = new CheckboxTreeNode("excel", new Document("Invoice.xsl", "20kb", "Excel"), home);

		//Downloads
		TreeNode spanish = new CheckboxTreeNode(new Document("Spanish", "10kb", "Folder"), downloads);
		TreeNode tutorial1 = new CheckboxTreeNode("document", new Document("tutorial-a1.txt", "5kb", "Text"), spanish);
		TreeNode tutorial2 = new CheckboxTreeNode("document", new Document("tutorial-a2.txt", "5kb", "Text"), spanish);
		TreeNode travel = new CheckboxTreeNode(new Document("Travel", "15kb", "Folder"), downloads);
		TreeNode hotelpdf = new CheckboxTreeNode("travel", new Document("Hotel.pdf", "10kb", "PDF"), travel);
		TreeNode flightpdf = new CheckboxTreeNode("travel", new Document("Flight.pdf", "5kb", "PDF"), travel);

		//Main
		TreeNode bin = new CheckboxTreeNode("document", new Document("bin", "50kb", "Link"), main);
		TreeNode etc = new CheckboxTreeNode("document", new Document("etc", "100kb", "Link"), main);
		TreeNode var = new CheckboxTreeNode("document", new Document("var", "100kb", "Link"), main);

		//Other
		TreeNode todotxt = new CheckboxTreeNode("document", new Document("todo.txt", "3kb", "Text"), other);
		TreeNode logopng = new CheckboxTreeNode("picture", new Document("logo.png", "2kb", "Picture"), other);

		//Pictures
		TreeNode barcelona = new CheckboxTreeNode("picture", new Document("barcelona.jpg", "90kb", "Picture"), pictures);
		TreeNode primeng = new CheckboxTreeNode("picture", new Document("primefaces.png", "30kb", "Picture"), pictures);
		TreeNode prime = new CheckboxTreeNode("picture", new Document("prime.jpg", "30kb", "Picture"), pictures);

		//Videos
		TreeNode primefacesmkv = new CheckboxTreeNode("video", new Document("primefaces.mkv", "1000kb", "Video"), videos);
		TreeNode introavi = new CheckboxTreeNode("video", new Document("intro.avi", "500kb", "Video"), videos);

        return root;
    }
}
