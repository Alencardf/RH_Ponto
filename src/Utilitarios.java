import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Utilitarios {
	
	public static int getPage(String path, String pesquisa) {
		File file = new File(path);
		PDDocument document; 
		try {
			PDFTextStripper stripper = new PDFTextStripper();
			document = PDDocument.load(file);
			
			String texto;
			int nPage = document.getNumberOfPages();
			
			for(int i=0;i<nPage; i++) {
				
				stripper.setStartPage(i);
				stripper.setEndPage(i);
				texto = stripper.getText(document);
				//document.close();
				if(texto.contains(pesquisa.toUpperCase())) {
					return i;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
