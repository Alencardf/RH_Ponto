import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;

public class CarregaPdf{
	private String path;
	private int page;
	private int npag;
	private PDDocument document;
	private List<Ponto> ponto = new ArrayList<Ponto>();
	private String texto_unif;
	private String [] texto;
	private String nome;
	private final int nomeIndice = 16;
	
	private double tothrstot = 0;
	private double tothrsnot = 0;
	private double tothrsdia = 0;
	
//	public CarregaPdf(String path) {
//		
//		this.path = path;
//		getPdfText();
//		
//		try {
//			document.close();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public CarregaPdf(String path, int page) {
		this.page = page;
		this.path = path;
		getPdfText();
		
		try {
			document.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getPdfText() {
		PDFTextStripper pdfTextStripper = null;
		try {
			pdfTextStripper = new PDFTextStripper();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file = new File(this.path);
		try {
			if(document != null) {
				document.close();
			}
			document = PDDocument.load(file);
			this.npag = document.getNumberOfPages();
		} catch (InvalidPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
				pdfTextStripper.setStartPage(this.page);
				pdfTextStripper.setEndPage(this.page);
				texto_unif = pdfTextStripper.getText(document);
				//document.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		texto = texto_unif.split("\n");
	}
	
	public List<Ponto> getPonto(){
		for(int i=0; i<texto.length; i++) {
			String msg = texto[i];
			msg = msg.replace("\r", "");
			
			if ((msg.contains("dom")|| msg.contains("seg")|| msg.contains("ter")|| 
					msg.contains("qua")|| msg.contains("qui")|| msg.contains("sex")|| 
					msg.contains("sáb"))&(msg.length()==65)) { //65 linhas com ponto batido corretamente
				
				
					Ponto wa_ponto = new Ponto();
					wa_ponto.setDia(msg.substring(0, 3));
					wa_ponto.setEntrada(msg.substring(4, 10));
					wa_ponto.setSaidap(msg.substring(15, 21));
					wa_ponto.setEntradap(msg.substring(26, 32));
					wa_ponto.setSaida(msg.substring(37, 43));
					wa_ponto.setChave(msg.substring(47, 57));
					wa_ponto.setData(msg.substring(57, 65));
					
					TrataPonto tp = new TrataPonto(wa_ponto.getEntrada(), wa_ponto.getSaidap(), wa_ponto.getEntradap(), wa_ponto.getSaida());
					
					wa_ponto.setHrsTrab(TrataPonto.mudaString(tp.getHrstot()));
					wa_ponto.setIndNot(TrataPonto.mudaString(tp.getHrsnot()));
					wa_ponto.setIndDia(TrataPonto.mudaString(tp.getHrsdia()));
					
					this.tothrsdia = this.tothrsdia + tp.getHrsdia();
					this.tothrsnot = this.tothrsnot + tp.getHrsnot();
					this.tothrstot = this.tothrstot + tp.getHrstot();
					
					
					ponto.add(wa_ponto);
					
		}
			
		if(i==nomeIndice) {
			String nome = texto[i];
			nome = nome.replace("\r", "");
			setNome(nome);
		}
			
			
		}
		
		return ponto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getTothrstot() {
		return tothrstot;
	}

	public void setTothrstot(double tothrstot) {
		this.tothrstot = tothrstot;
	}

	public double getTothrsnot() {
		return tothrsnot;
	}

	public void setTothrsnot(double tothrsnot) {
		this.tothrsnot = tothrsnot;
	}

	public double getTothrsdia() {
		return tothrsdia;
	}

	public void setTothrsdia(double tothrsdia) {
		this.tothrsdia = tothrsdia;
	}

	public int getNpag() {
		return npag;
	}

	public void setNpag(int npag) {
		this.npag = npag;
	}

}
