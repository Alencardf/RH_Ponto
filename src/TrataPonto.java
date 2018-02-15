import java.awt.geom.NoninvertibleTransformException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class TrataPonto {
	
	private String entradaNormal;
	private String saidaPNormal;
	private String entradaPNormal;
	private String saidaNormal;
	private double entradaDecimal;
	private double saidaPDecimal;
	private double entradaPDecimal;
	private double saidaDecimal;
	private final double NOTURNO = 22;
	private final double NOTURNODS = 7;
	private final double HrsNot2224 = 2;
	private final double HrsFimDia = 24;
	private double horaNoturna = 0;
	
	private double hrsnot = 0;
	private double hrstot = 0;
	private double hrsdia = 0;
	
	public TrataPonto(String entrada, String saidaP, String entradaP, String saida) {
		this.entradaNormal 	= entrada;
		this.saidaPNormal 	= saidaP;
		this.entradaPNormal = entradaP;
		this.saidaNormal 	= saida;
		this.entradaDecimal = TrataPonto.mudaDoubel(entradaNormal);
		this.saidaPDecimal = TrataPonto.mudaDoubel(saidaPNormal);
		this.entradaPDecimal = TrataPonto.mudaDoubel(entradaPNormal);
		this.saidaDecimal = TrataPonto.mudaDoubel(saida);
		this.hrstot = hrsTrab();
		this.hrsnot = hrsNot();
		this.setHrsdia(this.hrstot - this.hrsnot);
		
		
	}
	
	public static double mudaDoubel(String batida) {
		
		String hora 	= batida.substring(0, 2);
		String minuto 	= batida.substring(3, 5);
		
		double tempo = Double.parseDouble(hora);
		
		tempo = tempo + (Double.parseDouble(minuto)/60);
		
		return tempo;
	}
	
	public static String mudaString(double batidaDecimal) {
		
		DecimalFormat df = new DecimalFormat("##,##");
		DecimalFormatSymbols sym = DecimalFormatSymbols.getInstance();
		sym.setDecimalSeparator('.');
		df.setDecimalFormatSymbols(sym);
		df.setRoundingMode(RoundingMode.CEILING);
		
		long hora = (long) batidaDecimal;
		double part  = batidaDecimal - hora;
				
		part = part*60;
		long minuto = (long) part;
		String teste = df.format(part); 
		minuto = Long.parseLong(teste);
		
		if(minuto>=60) {
			minuto=0;
			hora = hora + 1;
		}
		
		String aux;
		
		if (Long.toString(hora).length() < 2) { // conversão de hora
			aux = "0" + Long.toString(hora) + ":";
		}else {
			aux = Long.toString(hora) + ":";
		}
		
		if (Long.toString(minuto).length() < 2) {//conversão de minuto
			return aux + "0" + minuto;
		}
		
		return aux + Long.toString(minuto);
			
	}
	
	private double hrsTrab() {
		
		double entrada = getEntradaDecimal();
		double saidaInt = getSaidaPDecimal();
		double entradaInt = getEntradaPDecimal();
		double saida = getSaidaDecimal();
		
		
		double soma = 0;
		
		if(saidaInt < entrada) {
			soma = soma + (24-entrada) + saidaInt;
		}else {
			soma = soma + (saidaInt - entrada);
		}
		
		if(saida < entradaInt) {
			soma = soma + (24-entradaInt) + saida;
		}else {
			soma = soma + (saida - entradaInt);
		}
		
		return soma;
	}
	
	public String hrsTrab(double saida) {
		double soma = getSaidaPDecimal() - getEntradaDecimal();
		soma = soma + (getSaidaDecimal() - getEntradaPDecimal());
		soma = soma - saida;
		return mudaString(soma);
	}
	
	double hrs;
	
	private double hrsNot() {
		
		hrs = 0;
		
		List<Double> marcacao = new ArrayList<>();
		
		marcacao.add(getEntradaDecimal());
		marcacao.add(getSaidaPDecimal());
		marcacao.add(getEntradaPDecimal());
		marcacao.add(getSaidaDecimal());
		
		for(int i=0; i<marcacao.size(); i++) {
			if(i%2==0) {
				
				double entrada = marcacao.get(i);
				double saida = marcacao.get(i+1);
				
				if(saida>NOTURNO & entrada<NOTURNO) {
					hrs = hrs + (saida - NOTURNO);
				}
				
				if(saida>NOTURNO & entrada>NOTURNO) {
					hrs = hrs + (saida - entrada);
				}
				
				if(saida<NOTURNODS & entrada<NOTURNO & i<2) { 
					hrs = hrs + (HrsNot2224 + saida);
				}
				
				if(saida<NOTURNODS & entrada>NOTURNO) {
					hrs = hrs + (HrsFimDia - entrada);
					hrs = hrs + saida;
				}
				
				if(saida>NOTURNODS & entrada>NOTURNO) {
					hrs = hrs + NOTURNODS;
				}
				
				if(saida>NOTURNODS & entrada<NOTURNODS) {
					hrs = hrs + (NOTURNODS - entrada);
				}
				
				if(saida<NOTURNODS & entrada<NOTURNODS) {
					hrs = hrs + (saida-entrada);
				}
				
			}
		}
		
		return hrs;
	}
	
	
	public String getEntradaNormal() {
		return entradaNormal;
	}

	public void setEntradaNormal(String entradaNormal) {
		this.entradaNormal = entradaNormal;
	}

	public String getSaidaPNormal() {
		return saidaPNormal;
	}

	public void setSaidaPNormal(String saidaPNormal) {
		this.saidaPNormal = saidaPNormal;
	}

	public String getEntradaPNormal() {
		return entradaPNormal;
	}

	public void setEntradaPNormal(String entradaPNormal) {
		this.entradaPNormal = entradaPNormal;
	}

	public String getSaidaNormal() {
		return saidaNormal;
	}

	public void setSaidaNormal(String saidaNormal) {
		this.saidaNormal = saidaNormal;
	}

	public double getEntradaDecimal() {
		return entradaDecimal;
	}

	public void setEntradaDecimal(double entradaDecimal) {
		this.entradaDecimal = entradaDecimal;
	}

	public double getSaidaPDecimal() {
		return saidaPDecimal;
	}

	public void setSaidaPDecimal(double saidaPDecimal) {
		this.saidaPDecimal = saidaPDecimal;
	}

	public double getEntradaPDecimal() {
		return entradaPDecimal;
	}

	public void setEntradaPDecimal(double entradaPDecimal) {
		this.entradaPDecimal = entradaPDecimal;
	}

	public double getSaidaDecimal() {
		return saidaDecimal;
	}

	public void setSaidaDecimal(double saidaDecimal) {
		this.saidaDecimal = saidaDecimal;
	}

	public double getHoraNoturna() {
		return horaNoturna;
	}

	public void setHoraNoturna(double horaNoturna) {
		this.horaNoturna = horaNoturna;
	}

	public double getHrsnot() {
		return hrsnot;
	}

	public void setHrsnot(double hrsnot) {
		this.hrsnot = hrsnot;
	}

	public double getHrstot() {
		return hrstot;
	}

	public void setHrstot(double hrstot) {
		this.hrstot = hrstot;
	}

	public double getHrsdia() {
		return hrsdia;
	}

	public void setHrsdia(double hrsdia) {
		this.hrsdia = hrsdia;
	}

}
